package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f1j.ss.BookModel;
import com.f1j.util.F1Exception;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.newasset.model.NewAssetsTagPrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 打印调入方新标签号</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-10-14
 */
public class NewAssetsTagPrintServlet extends BaseServlet {
    ServletOutputStream out = null;
    String showMsg = "";
    protected com.f1j.ss.BookModelImpl book = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean success = false;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsMisTagChgDTO.class.getName());
            AmsMisTagChgDTO dtoParameter = (AmsMisTagChgDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            if (action.equals("")) {
                forwardURL = "/newasset/report/newAssetsTagPrint.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new NewAssetsTagPrintModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "/newasset/report/newAssetsTagPrint.jsp";
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                String tagNumbers[] = req.getParameterValues("subCheck");
                res.setContentType("application/vnd.ms-excel");
                // for this request, get the responses's output stream
                String header = "attachment; filename=BTWnewAssetsTag.xls";
                res.setHeader("Content-Disposition", header);
                out = res.getOutputStream();
                success = doExport(tagNumbers);
                if (success) {
                    out.flush();
                    out.close();
                } else {
                    forwardURL = "/newasset/report/newAssetsTagPrint.jsp";
                }
                req.setAttribute("showMsg", showMsg);
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
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }

    private boolean doExport(String[] tagNumbers) {
        boolean success = false;
        int num = tagNumbers.length;
        book = new com.f1j.ss.BookModelImpl();
        try {
            book.initWorkbook();
            book.getLock();
            book.setBorder(true);
            String barcode = "";
            //set report content
            book.setText(0, 0, "资产编号");
            book.setText(0, 1, "资产标签号");
            for (int i = 1; i <= num; i++) {
                book.setText(i, 0, String.valueOf(i));
                book.setText(i, 1, tagNumbers[i-1]);
            }

            book.setColWidthAuto(0, 0, num, 2, true);
//            book.setFixedRow(0);
//            book.setFixedCol(1);

            ////////////////////////////////
            // since we change the contents of the book we need to force a recalculation
            book.recalc();
            //writes the book to the output stream in an Excel file format
            book.write(out, new com.f1j.ss.WriteParams(BookModel.eFileExcel97));
            //must close the outputstream to flush the buffer contents
            out.close();

            success = true;
        } catch (IOException e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        } catch (F1Exception e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        }

        return success;
    }
}
