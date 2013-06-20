package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsCheckExportDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckExportModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.query.GridQuery;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
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
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-11-14
 * Time: 11:14:34
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsCheckExportSelvet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCJYCDTO.class.getName());
            AmsAssetsCJYCDTO dtoParameter = (AmsAssetsCJYCDTO) req2DTO.getDTO(req);
            AmsAssetsCheckExportDAO dao = new AmsAssetsCheckExportDAO(user, dtoParameter, conn);

            String action = req.getParameter("act");
            action = StrUtil.nullToString(action);
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = StrUtil.strToInt(dtoParameter.getBookTypeCode());
            String companySelect = op.getAllOrganization(organizationId, true);
            req.setAttribute("OU", companySelect);

            String bookTypeCode = dtoParameter.getBookTypeCode();
            String bookSelect = op.getAllBookTypeName(bookTypeCode, true);
            req.setAttribute("BOOK_TYPE_CODE", bookSelect);
            String dept=op.getDeptOption(dtoParameter.getDeptCode());
            req.setAttribute("DEPT",dept);
            req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
            if (action.equals("")) {
                forwardURL = "/newasset/assetsCheckExport.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsCheckExportModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);
                forwardURL = "/newasset/assetsCheckExport.jsp";
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
//                export(req, res);
                    File file = dao.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }

        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }

    }


    public void export(HttpServletRequest req, HttpServletResponse res) {
        Connection conn = null;
        String title = "资产盘点报表";//标题
        Map nameMap = new HashMap();//列名map

        SQLModel model = null;
        HttpSession session = req.getSession();
        nameMap = (HashMap) (session.getAttribute("EXPORT_NAMEMAP"));
        res.setContentType("application/vnd.ms-excel");
        com.f1j.ss.BookModelImpl book = new com.f1j.ss.BookModelImpl();
        try {
            ServletOutputStream out = res.getOutputStream();
            book.initWorkbook();
            book.getLock();
            book.setBorder(true);

//            book.read()
            try {
                conn = DBManager.getDBConnection();
                SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
                Request2DTO req2DTO = new Request2DTO();
                req2DTO.setDTOClassName(AmsAssetsCJYCDTO.class.getName());
                AmsAssetsCJYCDTO dtoParameter = (AmsAssetsCJYCDTO) req2DTO.getDTO(req);
                AmsAssetsCheckExportModel aModel = new AmsAssetsCheckExportModel(user, dtoParameter);
                model = aModel.getPageQueryModel();
                GridQuery gridBean = new GridQuery(model, conn);
                SimpleQuery sq = new SimpleQuery(model, conn);
                gridBean.setPageSize(2000);
                 gridBean.executeQuery();
                 sq.executeQuery();
                 RowSet rs = sq.getSearchResult();
                while (rs != null && !rs.isEmpty()) {
                      Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
					row = rs.getRow(i);
                     nameMap.put(row.getStrValue("ASSET_ID"), "资产编号");
                } }
//                int res_row = 0;

                String[] FieldArr = null;

                int FieldNum = 0;
                TransRule rule = new TransRule();
                rule.setDataSource(model);
                rule.setSourceConn(conn);

//                DataRange range = new DataRange();
//                rule.setDataRange(range);

//                nameMap.put("ASSET_ID", "资产编号");
//                nameMap.put("TAG_NUMBER", "资产条码");
//                nameMap.put("COST", "资产原值");
                rule.setFieldMap(nameMap);

                if (nameMap != null) {
                    if (nameMap.size() > 0) {
                        FieldArr = StrUtil.splitStr(nameMap.get("EXPORT_FIELDS").toString(), ",");
                        FieldNum = FieldArr.length;
                    }
                }

                //set report header
                String[] FieldDesc = new String[FieldNum];
                for (int i = 0; i < FieldNum; i++)
                    FieldDesc[i] = nameMap.get(FieldArr[i]).toString();

                for (int i = 0; i < FieldNum; i++)
                    book.setText(0, i, FieldDesc[i]);


//                while (gridBean.nextPage()) {
////                    for (int ri = 0; ri < gridBean.getCurrPageRecord(); ri++) {
////                        res_row++;
////                        String tmpField = "";
////                        for (int i = 0; i < FieldNum; i++) {
////                            tmpField = FieldArr[i];
////                            String tmpValue= gridBean.getValue(ri, tmpField);
////                            book.setText(res_row, i,tmpValue );
////                        }
////                    }
//                }
                book.setColWidthAuto(0, 0, 50, FieldNum, true);
                book.setFixedRow(1);
                book.setFixedCol(1);
                session.removeAttribute("EXPORT_SQL");
            } catch (Exception ex) {
                Logger.logError(ex);
            } finally {
//                try {
                DBManager.closeDBConnection(conn);
                /*}*/ /*catch (SQLException e) {
                    Logger.logError(e);
                }*/
            }


            book.recalc();
            book.write(out, new com.f1j.ss.WriteParams(book.eFileExcel97));
            out.close();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        finally {
            book.releaseLock();
        }

    }
}
