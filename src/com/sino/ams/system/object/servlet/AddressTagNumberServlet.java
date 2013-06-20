package com.sino.ams.system.object.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.dao.CommonObjectDAO;
import com.sino.ams.system.object.model.CommonObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AddressTagNumberServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this com.sino.base.PubServlet method
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
			EtsObjectDTO dto = (EtsObjectDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String opt = optProducer.getAllOu(dto.getOrganizationId());
			dto.setOrganizationOption(opt);
			opt = optProducer.getCountyOption(dto.getCountyCode());
			dto.setCountyOption(opt);
			opt = optProducer.getObjectCategoryOption(String.valueOf(dto.getObjectCategory()));
			dto.setObjCategoryOption(opt);
			String areaType = optProducer.getDictOption("ADDR_AREA_TYPE", "");
			dto.setAreaTypeOption(areaType);
			CommonObjectDAO objectDAO = new CommonObjectDAO(user, dto, conn);
			if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.ADDRESS_TAG_PAGE;
			} else if (action.equals(AMSActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new CommonObjectModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("WORKORDER_OBJECT_NO");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.ADDRESS_TAG_PAGE;
			} else if (action.equals(AMSActionConstant.EXPORT_ACTION)) {      //导出到Excel
				File file = objectDAO.getExportFile2();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AMSActionConstant.CHANGE_COUNTY)) {
				String countyOption = optProducer.getOuCountyOption(dto.getOrganizationId());
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print(countyOption);
				out.close();
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] objectNos = parser.getParameterValues("workorderObjectNo");
				objectDAO.disableObjects(objectNos);
				message = objectDAO.getMessage();
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.ENABLE_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] objectNos = parser.getParameterValues("workorderObjectNo");
				objectDAO.enableObjects(objectNos);
				message = objectDAO.getMessage();
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.DETAIL_ACTION)) {
				EtsObjectDTO objectDTO = (EtsObjectDTO)req.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
				if(objectDTO == null){
					String objectNo = dto.getWorkorderObjectNo();
					if(objectNo.equals("")){
						objectDTO = dto;
                        objectDTO.setAreaTypeOption(optProducer.getDictOption("ADDR_AREA_TYPE", objectDTO.getAreaType()));
                    } else {
                        objectDAO.setDTOClassName(EtsObjectDTO.class.getName());
                        objectDTO = (EtsObjectDTO)objectDAO.getDataByPrimaryKey();
                        opt = optProducer.getAllOu(objectDTO.getOrganizationId());
                        objectDTO.setOrganizationOption(opt);
                        opt = optProducer.getCountyOption(objectDTO.getCountyCode());
                        objectDTO.setCountyOption(opt);
                        objectDTO.setAreaTypeOption(optProducer.getDictOption("ADDR_AREA_TYPE", objectDTO.getAreaType()));
						opt = optProducer.getObjectCategoryOption(String.valueOf(objectDTO.getObjectCategory()));
						objectDTO.setObjCategoryOption(opt);
					}
				}
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, objectDTO);
				forwardURL = URLDefineList.ADDRESS_TAG_PAGE;
			} else if (action.equals(AMSActionConstant.VALIDATE_ACTION)) {                                          //验证barcode是否存在
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				String accessType = req.getParameter("accessType");				
				if(accessType != null && !accessType.equals("") && accessType.equals("validateworkorderObjectName") && (dto.getWorkorderObjectName().indexOf("'")) < 0){
					String result = objectDAO.getAddress(dto.getWorkorderObjectName());
					out.print(result);
				}else {
					boolean isExist = objectDAO.existObject();
					System.out.println("isExist = " + isExist);
					if (isExist) {
						out.print("Y");
					} else {
						out.print("N");
					}					
				}
				out.close();
			} else if (action.equals(AMSActionConstant.SAVE_ACTION)) {
				if(objectDAO.saveObject()){
					dto = (EtsObjectDTO) objectDAO.getDTOParameter();
				}
				message = objectDAO.getMessage();
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dto);
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.DETAIL_ACTION;
				forwardURL += "&workorderObjectNo=" + dto.getWorkorderObjectNo();
			}
//			else if (action.equals("SELECT_ADDRESS")){
//				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dto);
//				RowSet rows = objectDAO.getAddress(dto.getWorkorderObjectName());
//				req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
//				forwardURL = URLDefineList.OBJECT_LIST_PAGE;
//			}
			else {
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
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException e) {
			e.printStackTrace();
		} catch (SQLModelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
			DBManager.closeDBConnection(conn);
			if(!forwardURL.equals("")){
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
