package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.newasset.model.AmsCostMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-25
 * Time: 14:58:50
 * To change this template use File | Settings | File Templates.
 */
public class AmsCostMatch extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/newasset/amsCostMatch.jsp";
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));

        Request2DTO req2dto = new Request2DTO();
        try {
            req2dto.setDTOClassName(EtsFaAssetsDTO.class.getName());
            EtsFaAssetsDTO itemInfo = (EtsFaAssetsDTO) req2dto.getDTO(req);
            conn = getDBConnection(req);
            OptionProducer op = new OptionProducer(user, conn);
            ServletConfigDTO configDTO = getServletConfig(req);
            req.setAttribute("COUNTY_OPTION", op.getCountyOptionMIS(itemInfo.getCountyCodeMis()));
            req.setAttribute("MIS_HEADER", itemInfo);
            if (act.equals(WebActionConstant.QUERY_ACTION)) {
                AMSSQLProducer sqlProducer = new AmsCostMatchModel(user, itemInfo);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(configDTO);
                pageDAO.setPageSize(100);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
            } else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                forwardURL = "";
                AmsCostMatchModel sqlProducer = new AmsCostMatchModel(user, itemInfo);
                sqlProducer.setServletConfig(configDTO);
                SQLModel sqlMode2 = sqlProducer.getPageQueryModel();

                File file = exportFile(sqlMode2,user, itemInfo, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } catch (DataTransException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } catch (WebFileDownException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        }catch(SQLModelException e){
              e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
    } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }
    }

    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile(SQLModel sqlMode2,SfUserDTO user, EtsFaAssetsDTO itemInfo2, Connection conn) throws DataTransException {
        File file = null;
        TransRule rule = new TransRule();
        rule.setDataSource(sqlMode2);
        rule.setSourceConn(conn);
        String fileName = "成本中心.xls";
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY", "公司");
        fieldMap.put("COUNTY_CODE", "成本中心代码");
        fieldMap.put("COUNTY_NAME", "成本中心名称");
        rule.setFieldMap(fieldMap);
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle("成本中心");
        custData.setReportPerson(user.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }

}
