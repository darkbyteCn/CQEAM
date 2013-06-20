package com.sino.ams.system.comparison.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.comparison.dao.EtsObjectCategoryDAO;
import com.sino.ams.system.comparison.dto.EtsObjectCategoryDTO;
import com.sino.ams.system.comparison.model.EtsObjectCategoryModel;
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
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectCategoryServlet</p>
 * <p>Description:程序自动生成服务程序“EtsObjectCategoryServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectCategoryServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsObjectCategoryDTO.class.getName());
            EtsObjectCategoryDTO dtoParameter = (EtsObjectCategoryDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            EtsObjectCategoryDAO etsObjectCategoryDAO = new EtsObjectCategoryDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            if (action.equals("")) {
                String cityOption = prd.getAllOrganization( -1, true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String objCateOption = prd.getDictOption3(DictConstant.OBJECT_CATEGORY, "", true);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                forwardURL = URLDefineList.COMPAR_SEARCH;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EtsObjectCategoryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String cityOption = prd.getAllOrganization( dtoParameter.getCompany() , true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String objCateOption = prd.getDictOption3( DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory(), true);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                forwardURL = URLDefineList.COMPAR_SEARCH;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				EtsObjectCategoryDTO etsObjectCategory = (EtsObjectCategoryDTO)req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
//				if(etsObjectCategory == null){
//					etsObjectCategory= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.system.comparison.dto.EtsObjectCategoryDTO的构造函数确定
//				}
                String objCateOption = prd.getDictOption3(DictConstant.OBJECT_CATEGORY, "", false);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                String LeftCategoryOption = prd.getLeftCategoryOption( "-1", 0);
                req.setAttribute(WebAttrConstant.LEFT_CATEGORY_OPTION, LeftCategoryOption);
                String RightCategoryOption = prd.getRightCategoryOption(  "-1", 0);
                req.setAttribute(WebAttrConstant.RIGHT_CATEGORY_OPTION, RightCategoryOption);

                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_DTO, dtoParameter);
                forwardURL = URLDefineList.COMPAR_INFO;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                etsObjectCategoryDAO.setDTOClassName(EtsObjectCategoryDTO.class.getName());
                EtsObjectCategoryDTO etsObjectCategory = (EtsObjectCategoryDTO) etsObjectCategoryDAO.getDataByPrimaryKey();
                if (etsObjectCategory == null) {
                    etsObjectCategory = new EtsObjectCategoryDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String objCateOption = prd.getDictOption3( DictConstant.OBJECT_CATEGORY , etsObjectCategory.getObjectCategory() , false);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                String LeftCategoryOption = prd.getLeftCategoryOption( etsObjectCategory.getObjectCategory(), user.getOrganizationId());
                req.setAttribute(WebAttrConstant.LEFT_CATEGORY_OPTION, LeftCategoryOption);
                String RightCategoryOption = prd.getRightCategoryOption(  etsObjectCategory.getObjectCategory() , user.getOrganizationId());
                req.setAttribute(WebAttrConstant.RIGHT_CATEGORY_OPTION, RightCategoryOption);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_DTO, etsObjectCategory);
                forwardURL = URLDefineList.COMPAR_INFO;
            } else if (action.equals(WebActionConstant.CHECK_ACTION)) {    //onchang操作
                String systemid = req.getParameter("systemid");
                String objectCategory = req.getParameter("objectCategory");
                etsObjectCategoryDAO.setDTOClassName(EtsObjectCategoryDTO.class.getName());
                EtsObjectCategoryDTO etsObjectCategory = (EtsObjectCategoryDTO) etsObjectCategoryDAO.getDataByPrimaryKey();
                if (etsObjectCategory == null) {
                    etsObjectCategory = new EtsObjectCategoryDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String objCateOption = prd.getDictOption3(DictConstant.OBJECT_CATEGORY, objectCategory, false);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                String LeftCategoryOption = prd.getLeftCategoryOption(objectCategory , user.getOrganizationId());
                req.setAttribute(WebAttrConstant.LEFT_CATEGORY_OPTION, LeftCategoryOption);
                String RightCategoryOption = prd.getRightCategoryOption(objectCategory , user.getOrganizationId());
                req.setAttribute(WebAttrConstant.RIGHT_CATEGORY_OPTION, RightCategoryOption);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_DTO, etsObjectCategory);
//                etsObjectCategoryDAO.createData(objectCategory,codes);
                forwardURL = URLDefineList.COMPAR_INFO;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                String[] codes = req.getParameterValues("codes");
                String objectCategory = req.getParameter("objectCategory");
                String ret = etsObjectCategoryDAO.updateData(objectCategory, codes);
                String cityOption = prd.getAllOrganization( -1 , true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String objCateOption = prd.getDictOption3(DictConstant.OBJECT_CATEGORY, "", true);
                req.setAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION, objCateOption);
                forwardURL = URLDefineList.COMPAR_SEARCH + "?ret=" + ret;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                etsObjectCategoryDAO.deleteData();
                forwardURL = URLDefineList.COMPAR_SEARCH;
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
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}