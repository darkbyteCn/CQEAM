package com.sino.ams.system.county.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.county.dao.EtsCountyDAO;
import com.sino.ams.system.county.dto.EtsCountyDTO;
import com.sino.ams.system.county.model.sybase.EtsCountyModelImpl;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
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
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsCountyServlet</p>
 * <p>Description:程序自动生成服务程序“EtsCountyServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author zz_jlc
 * @version 1.0
 */


public class EtsCountyServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(EtsCountyDTO.class.getName());
            EtsCountyDTO dtoParameter = (EtsCountyDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            EtsCountyDAO etsCountyDAO = new EtsCountyDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String cityOption = optProducer.getAllOrganization( dtoParameter.getOrganizationId(), true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String countyOption = optProducer.getGivenCountyOption(user.getOrganizationId(), StrUtil.nullToString(dtoParameter.getCountyCode()));
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                forwardURL = URLDefineList.COUNTY_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {      //查询操作 
                String cityOption = optProducer.getAllOrganization( dtoParameter.getOrganizationId(), true);    //获取条件1
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                
                String countyOption = optProducer.getGivenCountyOption( dtoParameter.getOrganizationId(), StrUtil.nullToString(dtoParameter.getCountyCode()));//条件2 
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);

                //TODO 201105
                BaseSQLProducer sqlProducer = new EtsCountyModelImpl(user, dtoParameter);       //实例化SQLModel对象, 同时关联 EtsCountyModel类
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();           
                forwardURL = URLDefineList.COUNTY_QUERY;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                EtsCountyDTO etsCounty = new EtsCountyDTO();
                String cityOption = optProducer.getAllOrganization( dtoParameter.getOrganizationId(), true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String countyOption = optProducer.getCountyOption( dtoParameter.getCountyCode()  );
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                req.setAttribute(WebAttrConstant.COUNTY_ATTR, etsCounty);
                forwardURL = URLDefineList.COUNTY_INFO;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
            	//TODO 201105
                etsCountyDAO.setDTOClassName(EtsCountyDTO.class.getName());
                EtsCountyDTO etsCounty = (EtsCountyDTO) etsCountyDAO.getDataByPrimaryKey();
                if (etsCounty == null) {
                    etsCounty = new EtsCountyDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(), true);
                req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
                String countyOption = optProducer.getCountyOption( dtoParameter.getCountyCode() );
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                req.setAttribute(WebAttrConstant.COUNTY_ATTR, etsCounty);
                forwardURL = URLDefineList.COUNTY_INFO;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                dtoParameter.setCompanyCode(user.getCompanyCode());
                etsCountyDAO.createData();
                message = etsCountyDAO.getMessage();
                message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
                message.addParameterValue("区县");
                forwardURL = URLDefineList.COUNTY_QUERY_SERVLET;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                etsCountyDAO.updateData();
                message = etsCountyDAO.getMessage();
                forwardURL = URLDefineList.COUNTY_QUERY_SERVLET;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                etsCountyDAO.deleteData();
                message = etsCountyDAO.getMessage();
                message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
                message.addParameterValue("区县");
                forwardURL = URLDefineList.COUNTY_QUERY_SERVLET;
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