package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.dto.SearchParameterBackDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.*;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemMaintainDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemMaintainModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 *
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemMaintainServlet extends BaseServlet {

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
			ItemMaintainDAO maintainDAO = new ItemMaintainDAO(user, dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String opt = "";
			if (action.equals("")) {
				opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
				opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
				dto.setMaintainCompanyOpt(opt);

				WebRadio webRadio = new WebRadio("attribute1");
				webRadio.addValueCaption("DG", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dto.getAttribute1());
				dto.setRadioData(webRadio.toString());

				String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
				req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
				String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
				req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
                req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
                String allDeptOption = optProducer.getALLUserAsssetsDeptOption(dto.getMaintainDept(),user.getCompanyCode());
				req.setAttribute("DEPT_OPTIONS3", allDeptOption);
                String financePropOption = optProducer.getAssetsPropOption(dto.getFinanceProp());
				req.setAttribute("FINANCE_PROP_OPTION", financePropOption);
                String shareOption = optProducer.getDictOption("SHARE_STATUS", dto.getShare());
				req.setAttribute("SHARE_OPTION", shareOption);
                String constructStatusOption = optProducer.getDictOption("CONSTRUCT_STATUS", dto.getConstructStatus());
				req.setAttribute("CONSTRUCT_OPTION", constructStatusOption);
                forwardURL = AssetsURLList.ITEM_BOTTOM_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
                String discardTypeOption = optProducer.getDictOption(AssetsDictConstant.DIS_TYPE, dto.getDiscardType());
				req.setAttribute(AssetsWebAttributes.ITEM_DISCARD_OPTIONS, discardTypeOption);
                String dealTypeOption = optProducer.getDictOption(AssetsDictConstant.DEAL_TYPE, dto.getDealType());
				req.setAttribute(AssetsWebAttributes.DEAL_TYPE_OPTIONS, dealTypeOption);
				forwardURL = AssetsURLList.ITEM_DATA_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {

                AmsAssetsAddressVDTO recoverParameter = (AmsAssetsAddressVDTO)req.getAttribute(QueryConstant.QUERY_DTO);
                if(recoverParameter != null){
                    dto = recoverParameter;
                }

				BaseSQLProducer sqlProducer = new ItemMaintainModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
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
                pageDAO.setPageSize(20);
//                pageDAO.setCountPages(false);
				pageDAO.produceWebData();

				opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
				opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
				dto.setMaintainCompanyOpt(opt);

				WebRadio webRadio = new WebRadio("attribute1");
				webRadio.addValueCaption("DG", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dto.getAttribute1());
				dto.setRadioData(webRadio.toString());

				String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
				req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
				String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
				req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
                req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
                String allDeptOption = optProducer.getALLUserAsssetsDeptOption(dto.getMaintainDept(),user.getCompanyCode());
				req.setAttribute("DEPT_OPTIONS3", allDeptOption);
                String financePropOption = optProducer.getAssetsPropOption(dto.getFinanceProp());
				req.setAttribute("FINANCE_PROP_OPTION", financePropOption);
                String shareOption = optProducer.getDictOption("SHARE_STATUS", dto.getShare());
				req.setAttribute("SHARE_OPTION", shareOption);
                String constructStatusOption = optProducer.getDictOption("CONSTRUCT_STATUS", dto.getConstructStatus());
				req.setAttribute("CONSTRUCT_OPTION", constructStatusOption);
                String discardTypeOption = optProducer.getDictOption(AssetsDictConstant.DIS_TYPE, dto.getDiscardType());
				req.setAttribute(AssetsWebAttributes.ITEM_DISCARD_OPTIONS, discardTypeOption);
                String dealTypeOption = optProducer.getDictOption(AssetsDictConstant.DEAL_TYPE, dto.getDealType());
				req.setAttribute(AssetsWebAttributes.DEAL_TYPE_OPTIONS, dealTypeOption);
                forwardURL = AssetsURLList.ITEM_BOTTOM_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.ITEM_DATA_PAGE;
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				maintainDAO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				dto = (AmsAssetsAddressVDTO) maintainDAO.getDataByPrimaryKey();
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
                int yczCount = maintainDAO.checkItemStatus(barcodes, "DISCARDED_YCZ");//报废已处置
                int yubfCount = maintainDAO.checkItemStatus(barcodes, "PRE_DISCARDE");//预报废
                int zhCount = maintainDAO.checkItemStatus(barcodes, "EXCHANGE");//置换
                int yzzCount = maintainDAO.checkItemStatus(barcodes, "PRE_ASSETS");//预转资
                int xsCount = maintainDAO.checkItemStatus(barcodes, "SELL");//销售
                int czCount = maintainDAO.checkItemStatus(barcodes, "RENT");//出租
                int zyCount = maintainDAO.checkItemStatus(barcodes, "NORMAL");//在用
                int ztCount = maintainDAO.checkItemStatus(barcodes, "ON_WAY");//在途
                int sxCount = maintainDAO.checkItemStatus(barcodes, "SEND_REPAIR");//送修
                int dxCount = maintainDAO.checkItemStatus(barcodes, "TO_REPAIR");//待修
                int dbfCount = maintainDAO.checkItemStatus(barcodes, "TO_DISCARD");//待报废
                int yibfCount = maintainDAO.checkItemStatus(barcodes, "DISCARDED");//已报废
                int yczfCount = maintainDAO.checkItemStatus(barcodes, "CLEARED");//已处置
                int xzCount = maintainDAO.checkItemStatus(barcodes, "FREE");//闲置
                int shCount = maintainDAO.checkItemStatus(barcodes, "DAMAGED");//损坏
                int yibfGsjCount = maintainDAO.checkItemStatus(barcodes, "DISCARDED_TRANS");//已报废-公司间调拨
                int jzCount = maintainDAO.checkItemStatus(barcodes, "DONATE");//捐赠
                int dbfGsjCount = maintainDAO.checkItemStatus(barcodes, "TO_DISCARD_TRANS");//待报废-公司间调拨
                boolean errorCount = true;
                if (!dto.getItemStatus().equals("") && dto.getItemStatus().equals("DISCARDED_YCZ")) {//报废已处置:只能修改状态是已报废的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || zyCount > 0 || ztCount > 0 || sxCount > 0 || dxCount > 0 || dbfCount > 0 || yczfCount > 0 || xzCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_YCZ_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("NORMAL")) {//在用：排除已报废、报废已处置、捐赠、出租、销售的资产
                    if (yubfCount > 0 || zhCount > 0 || yzzCount > 0 || zyCount > 0 || ztCount > 0 || sxCount > 0 || dxCount > 0 || dbfCount > 0 || yczfCount > 0 || xzCount > 0 || shCount > 0 || yibfGsjCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_ZY_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("FREE") ) {// 闲置：只能是在用的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dxCount > 0 || dbfCount > 0 || yibfCount > 0 || yczfCount > 0 || xzCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_XZ_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("DONATE") ) {//捐赠：只能是在用的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dxCount > 0 || dbfCount > 0 || yibfCount > 0 || yczfCount > 0 || xzCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_JZ_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("EXCHANGE") ) {//置换：只能是在用的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dxCount > 0 || dbfCount > 0 || yibfCount > 0 || yczfCount > 0 || xzCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_ZH_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("PRE_DISCARDE") ) {//预报废：只能是在用、闲置、待修的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dbfCount > 0 || yibfCount > 0 || yczfCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_YBF_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("RENT") ) {//出租：只能是在用、闲置、待修、已报废的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dbfCount > 0 || yczfCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_CZ_DATA);
                        message.setIsError(true);
                    }
                } else if(!dto.getItemStatus().equals("") && dto.getItemStatus().equals("SELL") ) {//销售：只能是在用、闲置、待修、已报废的资产
                    if (yczCount > 0 || yubfCount > 0 || zhCount > 0 || yzzCount > 0 || xsCount > 0 || czCount > 0 || ztCount > 0 || sxCount > 0 || dbfCount > 0 || yczfCount > 0 || shCount > 0 || yibfGsjCount > 0 || jzCount > 0 || dbfGsjCount > 0) {
                        errorCount = false;
                        message = getMessage(CustMessageKey.UPDATE_XS_DATA);
                        message.setIsError(true);
                    }
                }
                if (errorCount) {
                    maintainDAO.updateItems(barcodes);
                    maintainDAO.logItemChgHistory(barcodes);
				    message = maintainDAO.getMessage();
                }

                req2DTO.setDTOClassName(SearchParameterBackDTO.class.getName());
                SearchParameterBackDTO backParameter = (SearchParameterBackDTO) req2DTO.getDTO(req);
                backParameter.recoverParameter(dto);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                
				forwardURL = AssetsURLList.ITEM_MAINTAIN_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
                String excelType = req.getParameter("excelType");
                File file = maintainDAO.getExportFile(servletConfig,excelType);
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
		} catch (SQLModelException ex) {
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
}
