package com.sino.ams.net.equip.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.equip.dao.ItemInfoDAO;
import com.sino.ams.net.equip.dto.ItemInfoDTO;
import com.sino.ams.net.equip.model.ItemInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: ItemInfoServlet</p>
 * <p>Description:程序自动生成服务程序“ItemInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class ItemInfoServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            ItemInfoDTO dtoParameter = new ItemInfoDTO();
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ItemInfoDTO.class.getName());
            dtoParameter = (ItemInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            ItemInfoDAO itemInfoDAO = new ItemInfoDAO(user, dtoParameter, conn);

            OptionProducer optProducer = new OptionProducer(user, conn); //获取区县信息
            String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);

            String itemTypeOption = optProducer.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());   //专业
            req.setAttribute(WebAttrConstant.ITEM_TYPE_OPTION, itemTypeOption);

            String financePropOption = optProducer.getDictOption(DictConstant.FINANCE_PROP, dtoParameter.getFinanceProp());   //财务属性
            req.setAttribute(WebAttrConstant.FINANCE_PROP_OPTION, financePropOption);

            String objCateOption = optProducer.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());   //地点（专业）
            req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);

            String invTypeOption = optProducer.getDictOption(DictConstant.INV_TYPE, dtoParameter.getInvType());   //仓库地点（专业）
            req.setAttribute(WebAttrConstant.INV_TYPE_OPTION, invTypeOption);

            String daiweiOption = optProducer.getMainCorpOption(dtoParameter.getDaiwei());    //代维公司
            req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION,daiweiOption);

            String qryType = req.getParameter("qryType");

            if (action.equals("")) {
                if (dtoParameter.getQryType().equals(WebAttrConstant.BY_PROJECTID)) {
                    forwardURL = URLDefineList.QRY_BY_PROJ_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_BARCODE)) {
                    forwardURL = URLDefineList.QRY_BY_BARCODE_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_SPEC)) {
                    forwardURL = URLDefineList.QRY_BY_SPEC_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_CATEGORY)) {
                    forwardURL = URLDefineList.QRY_BY_CATE_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_LOCUS)) {
                    forwardURL = URLDefineList.QRY_BY_LOCUS_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_ALLOT)) {
                    forwardURL = URLDefineList.QRY_BY_ALLOT_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_DAIWEI)) {
                    forwardURL = URLDefineList.QRY_BY_DAIWEI_PAGE + "?qryType=" + dtoParameter.getQryType();
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new ItemInfoModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                if (dtoParameter.getQryType().equals(WebAttrConstant.BY_PROJECTID)) {
                    forwardURL = URLDefineList.QRY_BY_PROJ_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_BARCODE)) {
                    forwardURL = URLDefineList.QRY_BY_BARCODE_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_SPEC)) {
                    forwardURL = URLDefineList.QRY_BY_SPEC_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_CATEGORY)) {
                    forwardURL = URLDefineList.QRY_BY_CATE_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_LOCUS)) {
                    forwardURL = URLDefineList.QRY_BY_LOCUS_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_ALLOT)) {
                    forwardURL = URLDefineList.QRY_BY_ALLOT_PAGE + "?qryType=" + dtoParameter.getQryType();
                } else if (dtoParameter.getQryType().equals(WebAttrConstant.BY_DAIWEI)) {
                    forwardURL = URLDefineList.QRY_BY_DAIWEI_PAGE + "?qryType=" + dtoParameter.getQryType();
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = itemInfoDAO.exportFile();
                itemInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();

            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemInfoDAO.setDTOClassName(ItemInfoDTO.class.getName());
                ItemInfoDTO itemInfo = (ItemInfoDTO) itemInfoDAO.getDataByPrimaryKey();
                if (itemInfo == null) {
                    itemInfo = new ItemInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("详细数据属性，请根据实际情况修改", itemInfo);
                forwardURL = "com.sino.ams.net.equip.servlet.ItemInfoServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
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
        } catch (DataTransException e) {
            e.printLog();
            Logger.logError(e.toString());
        } catch (WebFileDownException e) {
            e.printLog();
            Logger.logError(e.toString());
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}