package com.sino.ams.match.servlet;

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
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.match.model.ManualCtMatchMISModel;
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
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 *
 * @author 于士博
 * @version 0.1
 *          Date: 2008-12-08
 */
public class ManualCtMatchMIS extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "/ct/match/manualCtMatchMIS.jsp";
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
            /*
            if (configDTO.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
                req.setAttribute("COUNTY_OPTION", op.getCountyMIS(itemInfo.getCountyCodeMis()));
                req.setAttribute("MIS_HEADER", itemInfo);
            } else {
                req.setAttribute("COUNTY_OPTION", op.getCountyOptionMIS(itemInfo.getCountyCodeMis()));
                req.setAttribute("MIS_HEADER", itemInfo);
            }
            */
            req.setAttribute("MIS_HEADER", itemInfo);
            if (act.equals(WebActionConstant.QUERY_ACTION)) {
            	
                AMSSQLProducer sqlProducer = new ManualCtMatchMISModel(user, itemInfo);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(configDTO);
                pageDAO.setPageSize(100);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
            } else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                forwardURL = "";
                ManualCtMatchMISModel sqlProducer = new ManualCtMatchMISModel(user, itemInfo);
                sqlProducer.setServletConfig(configDTO);
               SQLModel sqlMode2 = sqlProducer.getPageQueryModel();

                File file = exportFile(sqlMode2,user, itemInfo, conn);
//                amsHouseInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
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
//        try {
//            SQLModel sqlModel = sqlProducer.getPageQueryModel();
//            ManualMatchMISModel sqlProducer = new ManualMatchMISModel(user, itemInfo2);
//            SQLModel sqlMode2 = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlMode2);
//            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "村通MIS设备信息.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("TAG_NUMBER", "资产条码");
            fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
            fieldMap.put("MODEL_NUMBER", "规格型号");
            fieldMap.put("DEPT_NAME", "部门");
            fieldMap.put("ASSIGNED_TO_NAME", "责任人");
            fieldMap.put("ASSETS_LOCATION", "地点");
            //fieldMap.put("NO_MATCH_UNITS", "数量");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
            
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("村通MIS设备信息");
            custData.setReportPerson(user.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
        return file;
    }
}
