package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckClientDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearClientDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckClientModel;
import com.sino.ams.yearchecktaskmanager.util.ReadAssetsExcel;
import com.sino.ams.yearchecktaskmanager.util.ReadClientExcel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 
 * @author agenty 记录客户端资产
 * 
 *         任务完成进度： 新建盘点确认表
 */
public class AssetsYearClientCheckServlet extends BaseServlet {
	private static final int startRowNum = 4;
	private static final int columnNum = 5;

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AssetsYearClientDTO.class.getName());
			AssetsYearClientDTO dto = (AssetsYearClientDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsYearCheckClientDAO assetsDAO = new AssetsYearCheckClientDAO(user,
					dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			assetsDAO.setServletConfig(getServletConfig(req));
			if (action.equals("")) {
				dto.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/yearchecktaskmanager/assetsYearClientCheck.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				 BaseSQLProducer sqlProducer = new AssetsYearCheckClientModel(user, dto);
                 PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                 pageDAO.setWebCheckProp(checkProp);
                 pageDAO.setPageSize(21);
                 pageDAO.setCalPattern(LINE_PATTERN);
                 pageDAO.setCountPages(true);
                 pageDAO.produceWebData();
                 req.setAttribute(QueryConstant.QUERY_DTO, dto);
                 dto.setCalPattern(LINE_PATTERN);
				 forwardURL = "/yearchecktaskmanager/assetsYearClientCheck.jsp";
			} else if (action.equals(AssetsActionConstant.CONFIRM_ACTION)) {
//		        String str=req.getParameter("Str");
		        String str=req.getParameter("typeStr");
		        assetsDAO.conClient(str);
		        BaseSQLProducer sqlProducer = new AssetsYearCheckClientModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setPageSize(21);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setCountPages(true);
                pageDAO.produceWebData();
		        req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/yearchecktaskmanager/assetsYearClientCheck.jsp";
			}else if (action.equals("EXPORT")) { //导出全年非实地盘点地点资产信息 String
				String exportType = dto.getExportType();
                File file = null;
                file = assetsDAO.exportClientFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
			}else if(action.equals("excel")){//导入全年非实地盘点地点资产信息
                String excel2 = req.getParameter("excel");
                DTOSet dtoSet = null;
                boolean parseError = false;
                try {
                    dto.setExcel(excel2);
                    dtoSet = readDataFrmExcel(req);
                } catch (Throwable ex) {
                    message = new Message();
                    message.setMessageValue("解析Excel数据失败");
                    message.setIsError(true);
                    parseError = true;
                }
                if(!parseError){
                	//暂存数据
                	assetsDAO.svaeTmp(dtoSet);
                }
                dto.setIsImport("1");
                BaseSQLProducer sqlProducer = new AssetsYearCheckClientModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setPageSize(21);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.setCountPages(true);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/yearchecktaskmanager/assetsYearClientCheck.jsp";
		}else {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebFileDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
	
	/**
     * 功能：读取Excel数据到DTOSet
     *
     * @param req 页面请求对象
     * @return DTOSet对象
     * @throws DTOException
     */
    private DTOSet readDataFrmExcel(HttpServletRequest req) throws DTOException {
        DTOSet dtoSet = null;
        try {
            String fileName = req.getParameter("excelPath");     //文件路径
            ReadClientExcel xlsUtil = new ReadClientExcel();
            xlsUtil.setFileName(fileName);
            xlsUtil.setNumberOfColumn(columnNum);      //设置列数8
            xlsUtil.setStartRowNum(startRowNum);       //开始行
            dtoSet = xlsUtil.readXls(0);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        }
        return dtoSet;
    }
}
