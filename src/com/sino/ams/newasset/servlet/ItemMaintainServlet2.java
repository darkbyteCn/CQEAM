package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemMaintainDAO2;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemMaintainModel2;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 陕西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author suhp
 */
public class ItemMaintainServlet2 extends BaseServlet {

    /**
     * 功能：资产台账维护
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
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
            ItemMaintainDAO2 maintainDAO = new ItemMaintainDAO2(user, dto, conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String opt = "";
            if (action.equals("")) {
                opt = optProducer.getItemCategoryOption(dto.getItemCategory());
                dto.setItemCategoryOpt(opt);
                opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
                dto.setMaintainCompanyOpt(opt);

                //是否代管现不用
//                WebRadio webRadio = new WebRadio("attribute1");
//                webRadio.addValueCaption("DG", "是");
//                webRadio.addValueCaption("N", "否");
//                webRadio.setCheckedValue(dto.getAttribute1());
//                dto.setRadioData(webRadio.toString());

                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
                req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
                dto.setShareOption(optProducer.getDictOption("SHARE_STATUS",dto.getShareStatus()));
                req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
                forwardURL = AssetsURLList.ITEM_BOTTOM_PAGE;
                String financePropOption = optProducer.getDictOption(DictConstant.FINANCE_PROP, dto.getFinanceProp());   //资产种类
                req.setAttribute(WebAttrConstant.FINANCE_PROP_OPTION, financePropOption);

                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/newasset/itemData2.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new ItemMaintainModel2(user, dto);
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
                pageDAO.setPageSize(20);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();

                opt = optProducer.getItemCategoryOption(dto.getItemCategory());
                dto.setItemCategoryOpt(opt);
                opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
                dto.setMaintainCompanyOpt(opt);
                dto.setShareOption(optProducer.getDictOption("SHARE_STATUS",dto.getShareStatus()));

                //统计资产属性的数量
                Map map = maintainDAO.getFincePropCount(dto);
                req.setAttribute(AssetsWebAttributes.FINCE_PROP_MAPS,map);

                //是否代管现不用
//                WebRadio webRadio = new WebRadio("attribute1");
//                webRadio.addValueCaption("DG", "是");
//                webRadio.addValueCaption("N", "否");
//                webRadio.setCheckedValue(dto.getAttribute1());
//                dto.setRadioData(webRadio.toString());

                String deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
                req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
                String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
                req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
                String financePropOption = optProducer.getDictOption(DictConstant.FINANCE_PROP, dto.getFinanceProp());   //资产种类
                req.setAttribute(WebAttrConstant.FINANCE_PROP_OPTION, financePropOption);
                forwardURL = AssetsURLList.ITEM_BOTTOM_PAGE;
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL ="/newasset/itemData2.jsp";
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
                maintainDAO.updateItems(barcodes);
                message = maintainDAO.getMessage();
                forwardURL = AssetsURLList.ITEM_MAINTAIN_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = maintainDAO.getExportFile();
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
        } catch (ContainerException ex) {
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
