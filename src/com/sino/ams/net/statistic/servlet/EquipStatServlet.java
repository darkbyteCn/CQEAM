package com.sino.ams.net.statistic.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dao.EquipStatDAO;
import com.sino.ams.net.statistic.dto.EquipStatDTO;
import com.sino.ams.net.statistic.model.EquipStatModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EquipStatServlet</p>
 * <p>Description:程序自动生成服务程序“EquipStatServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class EquipStatServlet extends BaseServlet {

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
            EquipStatDTO dtoParameter;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EquipStatDTO.class.getName());
            dtoParameter = (EquipStatDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EquipStatDAO equipStatDAO = new EquipStatDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);

            String objCateOption = optProducer.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());   //地点类型
            String invTypeOption = optProducer.getDictOption2(DictConstant.INV_TYPE, dtoParameter.getInvType());           //  仓库类型
            req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption + invTypeOption);

            String itemCateOption = optProducer.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());   //设备类型
            req.setAttribute(WebAttrConstant.ITEM_CATEGORY_OPTION, itemCateOption);
            String cityOption = optProducer.getAllOrganization( dtoParameter.getOrganizationId()  , true);    //获取地市信息
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
            String countyOption = optProducer.getGivenCountyOption(dtoParameter.getOrganizationId(), StrUtil.nullToString(dtoParameter.getCountyCode()));
            req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
            String financeProp = optProducer.getDictOption(DictConstant.FINANCE_PROP, dtoParameter.getFinaceProp());   //财务属性
            req.setAttribute(WebAttrConstant.FINANCE_PROP_OPTION, financeProp);
            String itemStatus = optProducer.getDictOption(DictConstant.ITEM_STATUS, dtoParameter.getItemStatus());   //设备状态
            req.setAttribute(WebAttrConstant.ITEM_STATUS_OPTION, itemStatus);


            String qryType = dtoParameter.getQryType();
            if (action.equals("")) {
                String countyOption1 = optProducer.getGivenCountyOption(user.getOrganizationId(), StrUtil.nullToString(dtoParameter.getCountyCode()));
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption1);
                if (qryType.equals(WebAttrConstant.BY_LOCUS)) {       //设备现有量--按地点

                    forwardURL = URLDefineList.STAT_EQP_BY_LOCUS_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_CATEGORY)) { //设备现有量--按状态
                    forwardURL = URLDefineList.STAT_EQP_BY_CATE_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_VENDOR)) {  //设备现有量--按厂家
                    forwardURL = URLDefineList.STAT_EQP_BY_VENDOR_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_CATEGORY + "2")) {  //全省统计--按状态
                    forwardURL = URLDefineList.STAT_EQP_BY_CATE_PAGE2;
                } else if (qryType.equals(WebAttrConstant.BY_VENDOR + "2")) { //全省统计--按厂家
                    forwardURL = URLDefineList.STAT_EQP_BY_VENDOR_PAGE2;
                } else if (qryType.equals(WebAttrConstant.BY_NAME)) {   //全省统计--按地点
                    forwardURL = URLDefineList.STAT_EQP_BY_NAME_PAGE;
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EquipStatModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                if (qryType.equals(WebAttrConstant.BY_LOCUS)) {

                    forwardURL = URLDefineList.STAT_EQP_BY_LOCUS_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_CATEGORY)) {
                    forwardURL = URLDefineList.STAT_EQP_BY_CATE_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_NAME)) {
                    forwardURL = URLDefineList.STAT_EQP_BY_NAME_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_VENDOR)) {
                    forwardURL = URLDefineList.STAT_EQP_BY_VENDOR_PAGE;
                } else if (qryType.equals(WebAttrConstant.BY_CATEGORY + "2")) {
                    forwardURL = URLDefineList.STAT_EQP_BY_CATE_PAGE2;
                } else if (qryType.equals(WebAttrConstant.BY_VENDOR + "2")) {
                    forwardURL = URLDefineList.STAT_EQP_BY_VENDOR_PAGE2;
                }
                req.setAttribute(WebAttrConstant.ITEM_INFO_DTO, dtoParameter);
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = equipStatDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("GET_GIVEN_COUNTY_OPTION")) { //获取区县信息
                String companyId = req.getParameter("companyId");
                String countyOptionX = optProducer.getGivenCountyOption(ConvertUtil.String2Int( companyId ), "");
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(countyOptionX);
                out.flush();
                out.close();
            } else if (action.equals("GET_GIVEN_INV_OPTION")) {//获取仓库信息
                String companyId = req.getParameter("companyId");
                String invOption = optProducer.getInvOption2(companyId, "");
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(invOption);
                out.flush();
                out.close();
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        } catch (DataHandleException ex) {
            ex.printLog();
            //请根据实际情况处理消息
            forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);

                //根据实际情况修改页面跳转代码。
            }
        }
    }       
}