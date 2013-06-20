package com.sino.ams.spare.inv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.*;
import com.sino.ams.spare.inv.dao.EtsObjectDAO;
import com.sino.ams.spare.inv.model.EtsObjectModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class EtsObjectServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");

        String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");
        action = StrUtil.nullToString(action);
        Connection conn = null;

        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsObjectDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
            dtoParameter = (EtsObjectDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EtsObjectDAO etsObjectDAO = new EtsObjectDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            //区县列表
            String countyOption = optProducer.getSpareCountyOption(dtoParameter.getCountyCode());
            req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
            //仓库类型列表
            String warehouseTypeOption = optProducer.getWarehouseTypeOption(dtoParameter.getObjectCategory());
            req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);
            ServletConfigDTO configDTO=SessionUtil.getServletConfigDTO(req);
            dtoParameter.setProvinceCode(configDTO.getProvinceCode());
            if (action.equals("")) {
                forwardURL = URLDefineList.WAREHOUSE_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EtsObjectModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.WAREHOUSE_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                EtsObjectDTO etsObject = (EtsObjectDTO) req.getAttribute(WebAttrConstant.WAREHOUSE_ATTR);
                if (etsObject == null) {
                    etsObject = dtoParameter;
                }
                req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, etsObject);
                forwardURL = URLDefineList.WAREHOUSE_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                etsObjectDAO.setDTOClassName(EtsObjectDTO.class.getName());
                EtsObjectDTO etsObject = (EtsObjectDTO) etsObjectDAO.getDataByPrimaryKey();

                //区县列表
                countyOption = optProducer.getSpareCountyOption(etsObject.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                //仓库类型列表
                warehouseTypeOption = optProducer.getWarehouseTypeOption(etsObject.getObjectCategory());
                req.setAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION, warehouseTypeOption);

                req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, etsObject);
                forwardURL = URLDefineList.WAREHOUSE_DETAIL_PAGE;
            } else if (action.equals("CHECK_CATEGORY_ACTION")) {//INV_NORMAL以外类型的仓库一个OU只允许有一个
                String objCategory = req.getParameter("objectCategory");
                PrintWriter out = res.getWriter();
                if (!objCategory.equals(DictConstant.INV_NORMAL)) {
                    String objectCode = etsObjectDAO.checkCategory(objCategory);
                    if (!objectCode.equals("")) {
                        out.print(objectCode);
                    } else out.print(WebAttrConstant.CATEGORY_NOT_EXIST);
                } else out.print(WebAttrConstant.CATEGORY_NOT_EXIST);
                out.flush();
                out.close();
            } else if (action.equals("CHECK_CODE_ACTION")) {//INV_NORMAL以外类型的仓库一个OU只允许有一个
                String objCode = req.getParameter("workorderObjectCode");
                PrintWriter out = res.getWriter();
                boolean hasBeen = etsObjectDAO.checkCode(objCode);
                if (hasBeen) out.print(WebAttrConstant.CODE_EXIST);
                else out.print(WebAttrConstant.CODE_NOT_EXIST);
                out.flush();
                out.close();
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                etsObjectDAO.createData();
                message = etsObjectDAO.getMessage();
                req.setAttribute(WebAttrConstant.WAREHOUSE_ATTR, dtoParameter);
                forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                etsObjectDAO.updateData();
                message = etsObjectDAO.getMessage();
                forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            } else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {//批量失效
                etsObjectDAO.disabledData(workorderObjectNos);
                message = etsObjectDAO.getMessage();
                forwardURL = URLDefineList.WAREHOUSE_QUERY_PAGE;
            } else if (action.equals(AMSActionConstant.EFFICIENT_ACTION)) {//批量生效
                etsObjectDAO.efficientData(workorderObjectNos);
                message = etsObjectDAO.getMessage();
                forwardURL = URLDefineList.WAREHOUSE_QUERY_PAGE;
           } else if (action.equals("verifyObjectNos")) {//验证barcode是否存在
                 String[] workorderObjectNoes = req.getParameterValues("workorderObjectNos");
                 boolean success = etsObjectDAO.doVerifyWorkBarcode(workorderObjectNoes);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if(!StrUtil.isEmpty(forwarder)){
                forwarder.forwardView(forwardURL);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}
