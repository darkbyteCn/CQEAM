package com.sino.ams.instrument.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentRegistrationDAO;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.instrument.model.AmsInstrumentRegistrationModel;
import com.sino.ams.inv.storeman.base.constant.web.WebInvActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: AmsInstrumentRegistrationServlet</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentRegistrationServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yushibo
 * @version 1.0
 */
public class AmsInstrumentRegistrationServlet extends BaseServlet {

	/**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        //String itemCategory = StrUtil.nullToString(req.getParameter("type"));
        action = StrUtil.nullToString(action);
        Connection conn = null;
        String showMsg = "";
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsInstrumentRegistrationDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsInstrumentRegistrationDTO.class.getName());
            dtoParameter = (AmsInstrumentRegistrationDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsInstrumentRegistrationDAO amsInstrumentInfoDAO = new AmsInstrumentRegistrationDAO(user, dtoParameter, conn);
            //dtoParameter.setItemCategory(itemCategory);
            OptionProducer op= new OptionProducer(user, conn);
            String cityOption1 = op.getAllOrganization(0, true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);

            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, dtoParameter);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsInstrumentRegistrationModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("BARCODE");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, dtoParameter);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
            	AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                //amsInstrumentInfo.setItemQty("1"); 
                req.setAttribute("ifpage", "new");
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, amsInstrumentInfo);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION_DETAIL;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
            	AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                String barcode = req.getParameter("barcode");
                amsInstrumentInfoDAO.updateData(barcode);
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, amsInstrumentInfo);
                showMsg = "仪器仪表登记卡删除成功!";
//              forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentRegistrationServlet?act=" + WebActionConstant.QUERY_ACTION;
                forwardURL += "&barcode=";
            } else if (action.equals(WebInvActionConstant.DELETE_ACTION_DISABLE)) {
            	AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] barcodes = parser.getParameterValues("barcode");
				amsInstrumentInfoDAO.updateDatas(barcodes);
				message = amsInstrumentInfoDAO.getMessage();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, amsInstrumentInfo);
                //showMsg = "仪器仪表登记卡批量删除成功!";
//              forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentRegistrationServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsInstrumentInfoDAO.setDTOClassName(AmsInstrumentRegistrationDTO.class.getName());
                amsInstrumentInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) amsInstrumentInfoDAO.getDataByPrimaryKey();    
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = new AmsInstrumentRegistrationDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("ifpage", "detail");
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REGISTRATION, amsInstrumentInfo);
                forwardURL = URLDefineList.INSTRUMENT_REGISTRATION_DETAIL;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
            	String ifpage = req.getParameter("ifpage");
            	String barcode = req.getParameter("barcode");
            	String vendorCode = req.getParameter("vendorBarcode");
            	String itemQty = req.getParameter("itemQty");
            	String price = req.getParameter("price");
            	String attribute3 = req.getParameter("attribute3");
            	String responsibilityDept = req.getParameter("deptCode");
            	String responsibilityUser = req.getParameter("employeeId");
            	String startDate = req.getParameter("startDate");
//            	String startdate = req.getParameter("startDate");
//            	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//            	Date startDate = format.parse(startdate);
            	String workorderObjectNo = req.getParameter("workorderObjectNo");
            	String itemStatus = req.getParameter("itemStatus");
            	String maintainUser = req.getParameter("maintainUser");
            	String remark = req.getParameter("remark");
            	String itemCode = req.getParameter("itemCode");
            	amsInstrumentInfoDAO.createCardData(conn, barcode, vendorCode, itemQty, price, attribute3, responsibilityDept, responsibilityUser, startDate, workorderObjectNo, itemStatus, maintainUser, remark, ifpage, itemCode);
                showMsg = "仪器仪表登记卡创建成功!";
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentRegistrationServlet?act=" + WebActionConstant.QUERY_ACTION;
//                forwardURL += "&itemStatus=" + "NORMAL";
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
            	String ifpage = req.getParameter("ifpage");
            	String oldBarcode = req.getParameter("oldBarcode");
            	String barcode = req.getParameter("barcode");
            	String vendorCode = req.getParameter("vendorBarcode");
            	String itemQty = req.getParameter("itemQty");
            	String price = req.getParameter("price");
            	String attribute3 = req.getParameter("attribute3");
            	String responsibilityDept = req.getParameter("deptCode");
            	String responsibilityUser = req.getParameter("employeeId");
            	String startDate = req.getParameter("startDate");
//            	String startdate = req.getParameter("startDate");
//            	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//            	Date startDate = format.parse(startdate);
            	String workorderObjectNo = req.getParameter("workorderObjectNo");
            	String itemStatus = req.getParameter("itemStatus");
            	String maintainUser = req.getParameter("maintainUser");
            	String remark = req.getParameter("remark");
            	String itemCode = req.getParameter("itemCode");
            	amsInstrumentInfoDAO.updateCardData(conn, oldBarcode, barcode, vendorCode, itemQty, price, attribute3, responsibilityDept, responsibilityUser, startDate, workorderObjectNo, itemStatus, maintainUser, remark, ifpage, itemCode);
                showMsg = "仪器仪表登记卡更新成功!";
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentRegistrationServlet?act=" + WebActionConstant.QUERY_ACTION;
                forwardURL += "&vendorBarcode=" + "&barcode=";
            } else if (action.equals("barcode")) {
                //String barcode = amsInstrumentInfoDAO.getOrderNum();
            	AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                //amsInstrumentInfo.setBarcode(barcode);
                req.setAttribute("BARCODE", amsInstrumentInfo);
                //  forwardURL = URLDefineList.INSTRUMENT_DETAIL;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = amsInstrumentInfoDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
              } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {     //新增仪器仪表
                String itemcode = req.getParameter("itemCode");
                String addressId = req.getParameter("addressId");
                //amsInstrumentInfoDAO.creatData(conn, itemcode, addressId);
                showMsg = "仪器创建成功!";
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=" + WebActionConstant.QUERY_ACTION;
              } else if (action.equals(AMSActionConstant.INSTEAD_ACTION)) {   //修改仪器仪表
                //amsInstrumentInfoDAO.updateDat(conn);
                showMsg = "仪器更新成功!";
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=" + WebActionConstant.QUERY_ACTION;
              } else if (action.equals(AMSActionConstant.STATISTICS_ACTION)) {   //仪器仪表统计操作
                  AmsInstrumentRegistrationModel sqlModel = new AmsInstrumentRegistrationModel(user, dtoParameter);
                  //SQLModel tmodel = sqlModel.getSQueryModel();
                  WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                  //wpv.produceWebData(tmodel);
                  forwardURL = URLDefineList.INSTRUMENT_QUERY;
               } else if (action.equals("EXPORT_ACTION2")) {
                 //File file = amsInstrumentInfoDAO.exportFile2();
                 WebFileDownload fileDown = new WebFileDownload(req, res);
                 //fileDown.setFilePath(file.getAbsolutePath());
                 fileDown.download();
                 //file.delete();
                } else if (action.equals("HISTORY_QUERY")) {   //仪器仪表变动历史
                  AmsInstrumentRegistrationModel sqlModel = new AmsInstrumentRegistrationModel(user, dtoParameter);
                  //SQLModel tmodel = sqlModel.getHQueryModel();
                  WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                  //wpv.produceWebData(tmodel);
                  forwardURL = "/instrument/instrHistory.jsp";
               } else if (action.equals("QUERY_ACTION2")){ //责任人的查询操作
            	   AmsInstrumentRegistrationModel sqlModel = new AmsInstrumentRegistrationModel(user, dtoParameter);
                  //SQLModel tmodel = sqlModel.getQueryRespModel();
                  WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                  //wpv.produceWebData(tmodel);
                 req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, dtoParameter);
                 forwardURL = "/instrument/instrumentRespQuery.jsp";
             
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }
        catch (DataHandleException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (CalendarException e) {
			e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException e) {
			e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException e) {
			e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}
        finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                if (showMsg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
            //根据实际情况修改页面跳转代码。
        }
	}

}
