package com.sino.ams.system.item.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.item.dao.EtsSystemItemDAO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.item.model.EtsSystemItemModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsSystemItemServlet</p>
 * <p>Description:程序自动生成服务程序“EtsSystemItemServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsSystemItemServlet extends BaseServlet {

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
        String itemCode = StrUtil.nullToString(req.getParameter("itemCode"));
        String systemId = StrUtil.nullToString(req.getParameter("systemId"));
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsSystemItemDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsSystemItemDTO.class.getName());
            dtoParameter = (EtsSystemItemDTO) req2DTO.getDTO(req);
            conn = DBManager.getDBConnection();
            EtsSystemItemDAO itemDAO = new EtsSystemItemDAO(user, dtoParameter, conn);
            itemDAO.setServletConfig(getServletConfig(req));
            OptionProducer prd = new OptionProducer(user, conn);
            if (action.equals("")) {
                String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                String ouOption = prd.getAllOU( user.getOrganizationId() , true );
                req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                
                forwardURL = URLDefineList.EQUIP_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                String ouOption = prd.getAllOU( user.getOrganizationId() , true );
                req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                
                BaseSQLProducer sqlProducer = new EtsSystemItemModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.EQUIP_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {   //创建页面  新建空
                String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                String ouOption = prd.getItemLeftOrgs(dtoParameter.getItemCode());
                req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                String existOrgOption = prd.getItemExistOrgs(dtoParameter.getItemCode());
                req.setAttribute(WebAttrConstant.DIS_OU_OPTION, existOrgOption);
                dtoParameter = new EtsSystemItemDTO();
                req.setAttribute(WebAttrConstant.ETS_SYSTEM_ITEM_DTO, dtoParameter);
                String itemUnitOpt = prd.getDictOption(DictConstant.UNIT_OF_MEASURE, dtoParameter.getItemUnit());
                req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                forwardURL = URLDefineList.EQUIP_DETAIL_PAGE;	
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {       //详细页面
                
            	itemDAO.setDTOClassName(EtsSystemItemDTO.class.getName());
                itemDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                EtsSystemItemDTO itemDTO = (EtsSystemItemDTO) itemDAO.getDataByPrimaryKey();
                if (itemDTO == null) {
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                } else {
                    String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, itemDTO.getItemCategory());
                    req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                    
                    String ouOption = prd.getItemLeftOrgs(itemDTO.getItemCode());
                    req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                    
                    String existOrgOption = prd.getItemExistOrgs(itemDTO.getItemCode());
                    req.setAttribute(WebAttrConstant.DIS_OU_OPTION, existOrgOption);
                    
                    String itemUnitOpt = prd.getItemUnitOption(itemDTO.getItemUnit());
                    req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                    
                    req.setAttribute(WebAttrConstant.ETS_SYSTEM_ITEM_DTO, itemDTO);
                }
                forwardURL = URLDefineList.EQUIP_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {         //保存新增数据  act=do save
                String[] orgIds;
//                itemDAO.setServletConfig(getServletConfig(req));
                if ((!user.isProvinceUser()) && (!user.isSysAdmin())) { //地市公司
                    orgIds = new String[]{ ConvertUtil.int2String( user.getOrganizationId() ) };
                } else {
                    orgIds = req.getParameterValues("organizationId");
                }
                boolean operateResult = itemDAO.createData(orgIds);
                message = itemDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    dtoParameter = new EtsSystemItemDTO();
                    String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                    req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                    BaseSQLProducer sqlProducer = new EtsSystemItemModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.EQUIP_QUERY_SERVLET;
                } else {
                    dtoParameter.setItemCode("");
                    String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                    req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                    String ouOption = prd.getItemLeftOrgs(dtoParameter.getItemCode());
                    req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                    String existOrgOption = prd.getItemExistOrgs(dtoParameter.getItemCode());
                    req.setAttribute(WebAttrConstant.DIS_OU_OPTION, existOrgOption);
                    req.setAttribute(WebAttrConstant.ETS_SYSTEM_ITEM_DTO, dtoParameter);
                    String itemUnitOpt = prd.getDictOption(DictConstant.UNIT_OF_MEASURE, dtoParameter.getItemUnit());
                    req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                    forwardURL = URLDefineList.EQUIP_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {        //修改数据

//                String[] orgIds = req.getParameterValues("organizationId");
                String[] orgIds;
//                itemDAO.setServletConfig(getServletConfig(req));
                if ((!user.isProvinceUser()) && (!user.isSysAdmin())) { //地市公司
                    orgIds = new String[]{ ConvertUtil.int2String( user.getOrganizationId() ) };
                } else {
                    orgIds = req.getParameterValues("organizationId");
                }
                itemDAO.setServletConfig(getServletConfig(req));
                boolean operateResult = itemDAO.updateData(orgIds, itemCode);
                message = itemDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    dtoParameter = new EtsSystemItemDTO();
                    String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                    req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                    BaseSQLProducer sqlProducer = new EtsSystemItemModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.EQUIP_QUERY_SERVLET;
                } else {
                    dtoParameter.setItemCode("");
                    String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dtoParameter.getItemCategory());
                    req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
                    String ouOption = prd.getItemLeftOrgs(dtoParameter.getItemCode());
                    req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
                    String existOrgOption = prd.getItemExistOrgs(dtoParameter.getItemCode());
                    req.setAttribute(WebAttrConstant.DIS_OU_OPTION, existOrgOption);
                    req.setAttribute(WebAttrConstant.ETS_SYSTEM_ITEM_DTO, dtoParameter);
                    String itemUnitOpt = prd.getDictOption(DictConstant.UNIT_OF_MEASURE, dtoParameter.getItemUnit());
                    req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                    forwardURL = URLDefineList.EQUIP_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {             //删除
                boolean operateResult = itemDAO.deleteData(itemCode);
                message = itemDAO.getMessage();
                message.setIsError(!operateResult);
                forwardURL = URLDefineList.EQUIP_QUERY_PAGE;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.CONN_ERROR);
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
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}
