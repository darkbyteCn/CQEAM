package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemSpareDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemSpareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-10
 * Time: 22:36:03
 * To change this template use File | Settings | File Templates.
 */
public class ItemSpareServlet extends BaseServlet {

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
			ItemSpareDAO itemSpareDAO = new ItemSpareDAO(user, dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String opt = "";
            String cityOption = optProducer.getAllOrganization(dto.getOrganizationId(), true);
            req.setAttribute(AssetsWebAttributes.CITY_OPTION, cityOption);
            OptionProducer optP = new OptionProducer(user, conn);
            if (action.equals("")) {
				opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
				opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
				dto.setMaintainCompanyOpt(opt);
                String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
			    req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
                req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
                req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
                forwardURL = "/newasset/itemSpareBottom.jsp";
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/itemSpareData.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new ItemSpareModel(user, dto);
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
				pageDAO.produceWebData();

				opt = optProducer.getItemCategoryOption(dto.getItemCategory());
				dto.setItemCategoryOpt(opt);
				opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
				dto.setMaintainCompanyOpt(opt);

                String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
			    req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);

                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
				req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
				req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
                forwardURL = "/newasset/itemSpareBottom.jsp";
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/itemSpareData.jsp";
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				itemSpareDAO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				dto = (AmsAssetsAddressVDTO) itemSpareDAO.getDataByPrimaryKey();
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
                int yczCount = itemSpareDAO.checkItemStatus(barcodes, "DISCARDED_YCZ");//报废已处置
                int yubfCount = itemSpareDAO.checkItemStatus(barcodes, "PRE_DISCARDE");//预报废
                int zhCount = itemSpareDAO.checkItemStatus(barcodes, "EXCHANGE");//置换
                int yzzCount = itemSpareDAO.checkItemStatus(barcodes, "PRE_ASSETS");//预转资
                int xsCount = itemSpareDAO.checkItemStatus(barcodes, "SELL");//销售
                int czCount = itemSpareDAO.checkItemStatus(barcodes, "RENT");//出租
                int zyCount = itemSpareDAO.checkItemStatus(barcodes, "NORMAL");//在用
                int ztCount = itemSpareDAO.checkItemStatus(barcodes, "ON_WAY");//在途
                int sxCount = itemSpareDAO.checkItemStatus(barcodes, "SEND_REPAIR");//送修
                int dxCount = itemSpareDAO.checkItemStatus(barcodes, "TO_REPAIR");//待修
                int dbfCount = itemSpareDAO.checkItemStatus(barcodes, "TO_DISCARD");//待报废
                int yibfCount = itemSpareDAO.checkItemStatus(barcodes, "DISCARDED");//已报废
                int yczfCount = itemSpareDAO.checkItemStatus(barcodes, "CLEARED");//已处置
                int xzCount = itemSpareDAO.checkItemStatus(barcodes, "FREE");//闲置
                int shCount = itemSpareDAO.checkItemStatus(barcodes, "DAMAGED");//损坏
                int yibfGsjCount = itemSpareDAO.checkItemStatus(barcodes, "DISCARDED_TRANS");//已报废-公司间调拨
                int jzCount = itemSpareDAO.checkItemStatus(barcodes, "DONATE");//捐赠
                int dbfGsjCount = itemSpareDAO.checkItemStatus(barcodes, "TO_DISCARD_TRANS");//待报废-公司间调拨
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
                    itemSpareDAO.updateItems(barcodes);
                    itemSpareDAO.logItemChgHistory(barcodes);
				    message = itemSpareDAO.getMessage();
                }
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.ItemSpareServlet";
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				File file = itemSpareDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if(action.equals("GET_DEPT_OPTION")){
                String organizationId = req.getParameter("organizationId");
                String deptOptionX = optP.getResDeptOption(organizationId,"");
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(deptOptionX);
                out.flush();
                out.close();
            }
            else {
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
		} catch (ContainerException ex) {
			ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
