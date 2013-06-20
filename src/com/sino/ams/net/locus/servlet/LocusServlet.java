package com.sino.ams.net.locus.servlet;


import java.io.File;
import java.io.IOException;
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
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.locus.dao.LocusDAO;
import com.sino.ams.net.locus.dto.LocusDTO;
import com.sino.ams.net.locus.model.LocusModel;
import com.sino.ams.net.locus.model.showEqpModel;
import com.sino.ams.system.basepoint.dao.EtsObjectAttributeDAO;
import com.sino.ams.system.basepoint.dao.EtsObjectDAO;
import com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: LocusServlet</p>
 * <p>Description:程序自动生成服务程序“LocusServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class LocusServlet extends BaseServlet {

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
            LocusDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(LocusDTO.class.getName());
            dtoParameter = (LocusDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            LocusDAO locusDAO = new LocusDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn); //获取区县信息
            //  String
            String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode().toString());
            req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                                  String cat = optProducer.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getCategory());
			req.setAttribute("CATEGORY", cat);
            //按专业重用servlet
            String objCategory = dtoParameter.getObjectCategory();

            if (action.equals("")) {
                if (objCategory.equals(WebAttrConstant.UNCHECK)) {    //  //未巡检

                    forwardURL = URLDefineList.LOCUS_UNCHECK_QUERY_PAGE;
                } else {
                    forwardURL = URLDefineList.LOCUS_QUERY_PAGE;
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new LocusModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                if (objCategory.equals(WebAttrConstant.UNCHECK)) {
                    forwardURL = URLDefineList.LOCUS_UNCHECK_QUERY_PAGE;
                } else {
                    forwardURL = URLDefineList.LOCUS_QUERY_PAGE;
                }
            } else if (action.equals("SHOW_EQP")) {
                BaseSQLProducer sqlProducer = new showEqpModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "/net/locus/showEquip.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {

                EtsObjectDTO etsObject = new EtsObjectDTO();
                etsObject.setWorkorderObjectNo(dtoParameter.getWorkorderObjectNo().toString());
                EtsObjectDAO etsObjectDAO = new EtsObjectDAO(user, etsObject, conn);
                etsObjectDAO.setDTOClassName(EtsObjectDTO.class.getName());
                etsObject = (EtsObjectDTO) etsObjectDAO.getDataByPrimaryKey();

                EtsObjectAttributeDTO dtoParameter2 = new EtsObjectAttributeDTO();
                dtoParameter2.setObjectNo(dtoParameter.getWorkorderObjectNo().toString());
                EtsObjectAttributeDAO etsObjectAttributeDAO = new EtsObjectAttributeDAO(user, dtoParameter2, conn);
                etsObjectAttributeDAO.setDTOClassName(EtsObjectAttributeDTO.class.getName());
                EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO) etsObjectAttributeDAO.getDataByPrimaryKey();

                if (etsObject == null) {
                    etsObject = new EtsObjectDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                countyOption = optProducer.getCountyOption(etsObject.getCountyCode());
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
                String patrolOption = optProducer.getDictOption(DictConstant.CHECK_MODLE, StrUtil.nullToString(etsObject.getIsall()));
                req.setAttribute(WebAttrConstant.CHECK_OPTION, patrolOption);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                if (etsObjectAttribute == null) {
                    etsObjectAttribute = new EtsObjectAttributeDTO();
                }
                req.setAttribute(WebAttrConstant.ETS_OBJECT_ATTRIBUTE_DTO, etsObjectAttribute);
                if (objCategory.equals(DictConstant.NETADDR_BTS)) {
                    forwardURL = URLDefineList.LOCUS_DETAIL_BTS_PAGE + "?objectCategory=" + dtoParameter.getObjectCategory();
                } else if (objCategory.equals(DictConstant.NETADDR_DATA) ||
                        objCategory.equals(DictConstant.NETADDR_EXCHG) ||
                        objCategory.equals(DictConstant.NETADDR_TRANS) ||
                        objCategory.equals(DictConstant.NETADDR_BSC) ||
                        objCategory.equals(DictConstant.NETADDR_NETOPT) ||
                        objCategory.equals(DictConstant.NETADDR_ELE)) {
                    forwardURL = URLDefineList.LOCUS_DETAIL_COMM_PAGE + "?objectCategory=" + dtoParameter.getObjectCategory();
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = locusDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
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
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
            if (!forwardURL.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}