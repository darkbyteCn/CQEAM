package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.mis.srv.assetTagNumber.dto.SBFIFAInquiryAssetTagNumberDTO;
import com.sino.soa.mis.srv.assetTagNumber.srv.SBFIFAInquiryAssetTagNumberSrv;
import com.sino.soa.td.srv.assetTagNumber.dto.SBFIFATdInquiryAssetTagNumberDTO;
import com.sino.soa.td.srv.assetTagNumber.srv.SBFIFATdInquiryAssetTagNumberSrv;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-11-18
 * Time: 16:08:13
 * To change this template use File | Settings | File Templates.
 */
public class NewTagAssetsAddServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        boolean hasError = true;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            conn = getDBConnection(req);
            res.setContentType("text/html;charset=GBK");
            PrintWriter out = res.getWriter();

            String organizationId = req.getParameter("toOrganizationId");
            int count = Integer.parseInt(req.getParameter("count"));

            DTOSet ds = genNewBarcodes(organizationId, count, conn);
            SBFIFAInquiryAssetTagNumberDTO dto = null;
            StringBuffer responseContent = new StringBuffer();
            for (int i = 0; i < ds.getSize(); i++) {
                if (user.getIsTd().equals("Y")) {
                    responseContent.append(((SBFIFATdInquiryAssetTagNumberDTO) ds.getDTO(i)).getTagNumber());
                } else {
                    responseContent.append(((SBFIFAInquiryAssetTagNumberDTO)ds.getDTO(i)).getTagNumber());
                }
                if (i < ds.getSize() - 1) {
                    responseContent.append("&&&");
                }
            }
            out.println(responseContent.toString());
            hasError = false;
        } catch (PoolPassivateException ex) {
            ex.printLog();
        } catch (ContainerException e) {
            e.printLog();
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            closeDBConnection(conn);
            if (hasError) {
                message = getMessage(AssetsMessageKeys.COMMON_ERROR);
                message.setIsError(true);
                setHandleMessage(req, message);
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(MessageConstant.MSG_PRC_SERVLET);
            }
        }
    }

    public DTOSet genNewBarcodes(String organizationId, int count, Connection conn) throws QueryException, ContainerException {
        DTOSet ds = new DTOSet();
        String companyCode = "";
        String bookTypeCode = "";
        boolean isTd = false;

        SQLModel sqlModel = new SQLModel();
        sqlModel = getOrganizationModel(organizationId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = null;
        if (sq.hasResult()) {
            row = sq.getFirstRow();
            companyCode = row.getStrValue("COMPANY_CODE");
            bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
            isTd = row.getStrValue("IS_TD").equalsIgnoreCase("Y");
            if (isTd) {
                ds = this.genNewBarcodesOfTD(bookTypeCode, companyCode, count);
            } else {
                ds = this.genNewBarcodesOfMIS(bookTypeCode, companyCode, count);
            }
        }
        return ds;
    }

    private SQLModel getOrganizationModel(String organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT BOOK_TYPE_CODE,COMPANY_CODE,IS_TD  from ETS_OU_CITY_MAP where ORGANIZATION_ID=?";
        sqlArgs.add(Integer.valueOf(organizationId));
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    private DTOSet genNewBarcodesOfTD(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFATdInquiryAssetTagNumberSrv srv = new SBFIFATdInquiryAssetTagNumberSrv();
        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }
        return ds;
    }

    private DTOSet genNewBarcodesOfMIS(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFAInquiryAssetTagNumberSrv srv = new SBFIFAInquiryAssetTagNumberSrv();
        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }
        return ds;
    }


}