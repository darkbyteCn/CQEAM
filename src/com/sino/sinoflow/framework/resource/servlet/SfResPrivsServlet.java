package com.sino.sinoflow.framework.resource.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.framework.resource.dao.SfResPrivsDAO;
import com.sino.sinoflow.framework.resource.dto.SfResDefineDTO;
import com.sino.sinoflow.framework.resource.dto.SfResPrivsDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SfResPrivsServlet</p>
 * <p>Description:程序自动生成服务程序“SfResPrivsServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfResPrivsServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserBaseDTO userAccount = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            SfResPrivsDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SfResPrivsDTO.class.getName());
            dtoParameter = (SfResPrivsDTO)req2DTO.getDTO(req);
            conn = DBManager.getDBConnection();
            SfResPrivsDAO sfResPrivsDAO = new SfResPrivsDAO(userAccount, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(userAccount, conn);
            if(action.equals("")){
                action = WebActionConstant.QUERY_ACTION;
            }
            if (action.equals(WebActionConstant.QUERY_ACTION)) {
                SfResDefineDTO resource = sfResPrivsDAO.getResourceById();
                dtoParameter.setSystemId(resource.getSystemId());
                req.setAttribute(WebAttrConstant.RES_DATA, resource);
                req.setAttribute("arrData", sfResPrivsDAO.getInitDate());
                OptionProducer op = new OptionProducer(userAccount, conn);
                req.setAttribute("group",op.getGroupOption(resource.getSystemId()));
//                String allRoleOption = optProducer.getAllRoleOption(resource.getSystemId());
//                String viewRoleOption = optProducer.getViewRoleOption(dtoParameter.getResId());
//                String str = "<option value=\"*\">*</option>";
//                if(viewRoleOption.indexOf("*") == -1 && allRoleOption.indexOf("*") == -1){
//                	allRoleOption = str+allRoleOption;
//                }
//                req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
//                req.setAttribute(WebAttrConstant.VIEW_ROLE_OPTION, viewRoleOption);


//                String allGroupOption = optProducer.getAllGroupOption(resource.getSystemId());
//                String viewGroupOption = optProducer.getViewGroupOption(dtoParameter.getResId());
//                if(viewGroupOption.indexOf("*") == -1 && allGroupOption.indexOf("*") == -1){
//                	allGroupOption = str+allGroupOption;
//                }
//                req.setAttribute(WebAttrConstant.ALL_GROUP_OPTION, allGroupOption);
//                req.setAttribute(WebAttrConstant.VIEW_GROUP_OPTION, viewGroupOption);



                forwardURL = URLDefineList.RES_PRIVI_QUERY;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                String tp = dtoParameter.getHiValue();
                dtoParameter.setResId(dtoParameter.getSystemId());
                DTOSet dtoSet = null;
                if(!(tp == null || tp.equals(""))){
                    dtoSet = getResPrivisDTOSet(dtoParameter);
                    sfResPrivsDAO.saveResPrivis(dtoSet,dtoParameter.getSystemId());
                    message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
                    message.addParameterValue("权限");
                }else{
                    sfResPrivsDAO.deleteExistPrivi(dtoParameter.getSystemId());
                }
                forwardURL = URLDefineList.PRIVI_QUERY_SERVLET;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    private DTOSet getResPrivisDTOSet(SfResPrivsDTO dto) throws DTOException {
        DTOSet priviDTOs = new DTOSet();
        String str = dto.getHiValue();
        String[] strArr = str.split(";");
        for(int i = 0; i < strArr.length; i++){
        	String[] sp = strArr[i].split(",");
            SfResPrivsDTO PriDto = new SfResPrivsDTO();
            PriDto.setSystemId(dto.getSystemId());
            PriDto.setResId(dto.getResId());
            PriDto.setGroupName(sp[0]);
            PriDto.setRoleName(sp[1]);
            priviDTOs.addDTO(PriDto);
        }
        return priviDTOs;
    }
}
