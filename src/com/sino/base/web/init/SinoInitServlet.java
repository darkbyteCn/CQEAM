package com.sino.base.web.init;

import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.sino.base.config.ConfigListener;
import com.sino.base.config.ConfigLoader;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.pool.PoolManager;
import com.sino.base.db.query.RecycleServer;
import com.sino.base.dto.ServletConfig2DTO;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.message.MessageLoader;
import com.sino.base.util.PrintUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.validate.ServletConfigValidator;
import com.sino.ams.workorderDefine.service.WorkorderDefineService;
import com.sino.config.SinoConfig;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.hn.sms.service.HnEamMsgService;
import com.sino.hn.todo.job.OaTodoThread;
import com.sino.hn.todo.util.HnOAConfig;
import com.sino.sms.service.SinoEamMsgService;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class SinoInitServlet extends HttpServlet {
    private ServletConfigDTO servletDTO = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initConfig();
    }

    private void initConfig() throws ServletException {
        try {
            ServletContext serContext = getServletContext();
            String attrName = WebConstant.SERVLET_DTO;
            servletDTO = (ServletConfigDTO) serContext.getAttribute(attrName);
            if (servletDTO == null) {
                ServletConfig config = getServletConfig();
                ServletConfig2DTO config2DTO = new ServletConfig2DTO(config);
                config2DTO.setDTOClassName(ServletConfigDTO.class.getName());
                servletDTO = (ServletConfigDTO) config2DTO.getDTO();
                ServletConfigValidator.validateServletConfig(servletDTO, servletDTO.getIgnoreFields());
                SessionUtil.saveServletConfig(serContext, servletDTO);
            }
            if (servletDTO.isStartListener()) {
                ConfigListener configListener = new ConfigListener();
                configListener.setListenFrequency(servletDTO.getListenFrequency());
                configListener.start();
            }
            if (servletDTO.isLoadConnPools()) {
                PoolManager.loadConnPools();
            }
            if (servletDTO.isLoadMessages()) {
                ServletContext context = getServletContext();
                String encoding = SessionUtil.getEncoding(context);
                if (StrUtil.isEmpty(encoding)) {
                    encoding = "GBK";
                }
                MessageLoader.setEncoding(encoding);
                MessageLoader.loadMessageResource();
            }
            if (servletDTO.isStartRecycleServer()) {
                RecycleServer server = new RecycleServer();
                server.start();
            }
            executeRunner();
            ConfigLoader.loadSinoConfigs();
            
            //短信
            if (servletDTO.isStartSMSService()) {
//            	if( SinoConfig.getProvinceCode().equals( HNConstant.PROVINCE_CODE ) ){
            	if( SinoConfig.getFlowHnOpen() ){ //河南个性化
            		HnEamMsgService eamService = new HnEamMsgService();
            		eamService.start();
            	}else{
            		SinoEamMsgService smsServer = new SinoEamMsgService();
                    smsServer.setSMSConfig(ConfigLoader.loadSMSConfig());
                    smsServer.setServletConfig(servletDTO);
                    smsServer.start();
            	}
            }
            //河南待办服务推送进程
            if( SinoConfig.getFlowHnOpen() ){	//河南个性化
            	if( HnOAConfig.startOatodo() ){	
            		OaTodoThread oaTodoThread = new OaTodoThread();
                	Thread thread = new Thread( oaTodoThread );
            		thread.start();
            	}
            }
            //巡检工单
            if (servletDTO.isStartWorkorderDefineService()) {
            	WorkorderDefineService workorderServer = new WorkorderDefineService();
            	workorderServer.start();
            }

            ConfigLoader.allConfigLoaded = true;
            PrintUtil.print("SinoProf Server " + serContext.getServletContextName() + " loaded successfully");
        } catch (ConfigException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } catch (DTOException ex) {
            ex.printLog();
            throw new ServletException(ex);
        }
    }

    /**
     * 功能：运行根据项目启动的一次性初始化程序
     * @throws ServletException
     */
    private void executeRunner() throws ServletException {
        String runCls = "";
        Connection conn = null;
        try {
            SinoInitialRunner runner = null;
            runCls = servletDTO.getInitRunner();
            if (servletDTO.isLoadConnPools()) {
                if (!StrUtil.isEmpty(runCls)) {
                    Object[] args = new Object[2];
                    conn = DBManager.getDBConnection();
                    if (conn == null) {
                        String errorMsg = "数据库连接池加载失败，"
                                + "请检查连接池配置文件是否正确，"
                                + "或者检查Oracle服务器是否正常运行";
                        throw new ServletException(errorMsg);
                    } else {
                        args[0] = conn;
                        args[1] = servletDTO;
                        runner = (SinoInitialRunner) ReflectionUtil.getInstance(runCls, args);
                        runner.run();
                    }
                }
            } else {
                String errorMsg = "没有加载数据库连接池，不能动态执行初始化类“"
                        + runCls
                        + "”";
                throw new ServletException(errorMsg);
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } catch (PoolException ex) {
            ex.printLog();
            throw new ServletException(ex);
        } finally {
            if (servletDTO.isLoadConnPools() && !StrUtil.isEmpty(runCls)) {
                DBManager.closeDBConnection(conn);
            }
        }
    }

    public void destroy() {
        super.destroy();
        try {
            if (servletDTO.isLoadConnPools()) {
                DBManager.unLoadConnPools();
                Logger.logInfo("卸载数据库连接池成功。");
            }
        } catch (PoolException ex) {
            String errorMsg = "卸载数据库连接池发生错误";
            Logger.logError(errorMsg + ":" + ex.getMessage());
        } finally {
            System.exit(0);
        }
    }
}
