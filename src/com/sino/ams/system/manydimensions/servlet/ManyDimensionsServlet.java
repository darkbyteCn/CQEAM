package com.sino.ams.system.manydimensions.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.*;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemMaintainDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.manydimensions.dao.ManyDimensionsDAO;
import com.sino.ams.system.manydimensions.model.ManyDimensionsModel;

/**
 * <p>Description:  陕西移动资产管理系统</p>
 * <p>Company:      北京思诺博信息技术有限公司</p>
 * @author          李轶
 * @date            2009-08-04
 */
public class ManyDimensionsServlet extends BaseServlet {

	/**
	 * 功能：多维度信息维护(维护多维度信息为空)
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
		ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
			if(dto.getAttribute1().equals("")){
				dto.setAttribute1(AssetsDictConstant.STATUS_NO);
			}
			String action = dto.getAct();
			conn = getDBConnection(req);
			ManyDimensionsDAO dao = new ManyDimensionsDAO(user, dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String opt = "";

			if (action.equals("")) {
				opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
				req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);

				forwardURL = AssetsURLList.MANY_DIMENSION_BOTTOM_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.MANY_DIMENSION_DATA_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                String manyDimensionIsNull[] = req.getParameterValues("manyDimensionsIsNull");
                this.setManyDimensionsIsNull(dto, manyDimensionIsNull);
				BaseSQLProducer sqlProducer = new ManyDimensionsModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BARCODE");
				EventHandlers handlers = new EventHandlers();
				EventHandler handler = new EventHandler();
				handler.setFunName("do_TransData");
				handler.setEventName("onClick");
				handlers.addHandler(handler);
				checkProp.setHandlers(handlers);
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();

                opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
				req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);

				forwardURL = AssetsURLList.MANY_DIMENSION_BOTTOM_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.MANY_DIMENSION_DATA_PAGE;
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				dao.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				dto = (AmsAssetsAddressVDTO) dao.getDataByPrimaryKey();
				AmsItemCorrectLogDTO logDTO = new AmsItemCorrectLogDTO();
				logDTO.setBarcode(dto.getBarcode());
				AmsItemCorrectLogDAO logDAO= new AmsItemCorrectLogDAO(user, logDTO, conn);
				logDAO.setDTOClassName(AmsItemCorrectLogDTO.class.getName());
				DTOSet barcodeLogs = (DTOSet)logDAO.getDataByForeignKey("barcode");
				req.setAttribute(AssetsWebAttributes.BARCODE_LOGS, barcodeLogs);
				req.setAttribute(AssetsWebAttributes.ITEM_INFO_DTO, dto);
				forwardURL = AssetsURLList.ITEM_DETAIL_PAGE;
			} else if (action.equals(AssetsActionConstant.UPDATE_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] barcodes = parser.getParameterValues("barcode");
				dao.updateItems(barcodes);
				message = dao.getMessage();
				forwardURL = AssetsURLList.MANY_DIMENSION_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                String manyDimensionIsNull[] = req.getParameterValues("manyDimensionsIsNull");
                this.setManyDimensionsIsNull(dto, manyDimensionIsNull);
				File file = dao.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
            } else {
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
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (HandlerException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

    public void setManyDimensionsIsNull(AmsAssetsAddressVDTO dto, String[] manyDimensionIsNull){
        if(manyDimensionIsNull != null && manyDimensionIsNull.length > 0){
            HashSet<String> values = new HashSet<String>(0);
            values.add("lne");
            values.add("cex");
            values.add("ope");
            values.add("nle");
            values.add("sn");
            for(int i = 0; i < manyDimensionIsNull.length; i++){
                String manyDimension = manyDimensionIsNull[i];
                if(manyDimension.equals("lne")){
                    values.remove("lne");
                } else if(manyDimension.equals("cex")){
                    values.remove("cex");
                } else if(manyDimension.equals("ope")){
                    values.remove("ope");
                } else if(manyDimension.equals("nle")){
                    values.remove("nle");
                } else if(manyDimension.equals("sn")){
                    values.remove("sn");
                }
            }
            for(String manyDimension : values){
                if(manyDimension.equals("lne")){
                    dto.setLneIsNull(false);
                } else if(manyDimension.equals("cex")){
                    dto.setCexIsNull(false);
                } else if(manyDimension.equals("ope")){
                    dto.setOpeIsNull(false);
                } else if(manyDimension.equals("nle")){
                    dto.setNleIsNull(false);
                } else if(manyDimension.equals("sn")){
                    dto.setSnIsNull(false);
                }
            }
        } else {
            dto.setLneIsNull(false);
            dto.setCexIsNull(false);
            dto.setOpeIsNull(false);
            dto.setNleIsNull(false);
            dto.setSnIsNull(false);
        }



    }
}