package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-12
 * Time: 9:20:40
 * Function:单据下载(备件)
 */
public class GetSpareOrders extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();
        Connection conn = null;
        SfUserDTO userAccount = null;
        RequestParser reqPar = new RequestParser();
        try {
            reqPar.transData(req);
            conn = DBManager.getDBConnection();
            userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            Logger.logInfo("PDA run GetSpareOrders servlet begin....");
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
            out.println("<spareOrders>");

            out.println(getHeadDate(conn,userAccount));

            out.println("</spareOrders>");
            out.close();
        } catch (PoolException e) {
            e.printStackTrace();
//		} catch (QueryException e) {
//			e.printStackTrace();
        } catch (UploadException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }


    /**
     * PDA下载单据(只下载)
     * @param conn
     * @param userAccout
     * @return
     */
    public String getHeadDate(Connection conn, SfUserDTO userAccout) {
        SQLModel sqlModel = new SQLModel();
        StringBuffer returnStr = new StringBuffer("");
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                    "       AITH.TRANS_NO,\n" +
                    "       AITH.TRANS_TYPE,\n" +
                    "       AMS_PUB_PKG.GET_FLEX_VALUE(AITH.TRANS_TYPE,'ORDER_TYPE_SPARE') TRANS_TYPE_DESC,\n" +
                    "       AITH.CREATION_DATE,\n" +
                    "       AITH.CREATED_BY,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CREATED_BY) CREATION_USER\n" +
                    "  FROM AMS_ITEM_TRANS_H AITH\n" +
                    "  WHERE AITH.TRANS_STATUS = 'SCANING'";

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            AmsItemTransHDTO amHDTO = new AmsItemTransHDTO();
//            AmsItemTransLDTO amLDTO = new AmsItemTransLDTO();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    String transId = row.getStrValue("TRANS_ID");
                    String transNo = row.getStrValue("TRANS_NO");
                    String transType = row.getStrValue("TRANS_TYPE");
                    String transTypeDesc = row.getStrValue("TRANS_TYPE_DESC");
                    String creationDate = row.getStrValue("CREATION_DATE");
                    String creationUser = row.getStrValue("CREATION_USER");
                    returnStr.append("<spareOrder  transNo=\"").append(transNo).append("\"");
                    returnStr.append(" transType=\"").append(transType).append("\"");
                    returnStr.append(" transTypeDesc=\"").append(transTypeDesc).append("\"");
                    returnStr.append(" creationDate=\"").append(creationDate).append("\"");
                    returnStr.append(" creationUser=\"").append(creationUser).append("\"");      //根据需要取人的登录名
                    returnStr.append(" vendor=\"").append("").append("\"");
                    returnStr.append(" > \n");
                    returnStr = getLineDate(returnStr, conn, transId);
                    returnStr.append("</spareOrder>\n");
//                    List sqlModList = new ArrayList();
                }
            }

//            if (!isSuccess) {
//                returnStr = new StringBuffer("");
//            }
//        } catch (DataHandleException e) {
//            Logger.logError("获取备件信息失败！" + e.toString());
        } catch (QueryException e) {
            e.printStackTrace();
            Logger.logError("获取备件信息失败！" + e.toString());
        } catch (ContainerException e) {
            Logger.logError("获取备件信息失败！" + e.toString());
            e.printStackTrace();
        }
        return returnStr.toString();
    }


    /**
     * 获取备件的行信息
     * @return StringBuffer
     */
    private StringBuffer getLineDate(StringBuffer strBuff, Connection conn, String transId) {
        SQLModel sqlModel = new SQLModel();
        String sql = "";
        List sqlArgs = new ArrayList();
        try {
            String sqlstr = "SELECT AITL.TRANS_ID,\n" +
                    "       AITL.LINE_ID,\n" +
                    "       AITL.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AITL.QUANTITY,\n" +
                    "       AITL.BARCODE\n" +
                    "  FROM AMS_ITEM_TRANS_L AITL, AMS_SPARE_CATEGORY ESI\n" +
                    " WHERE AITL.BARCODE = ESI.BARCODE\n" +
                    "   AND AITL.TRANS_ID = ?";
            sqlArgs.add(transId);
            sqlModel.setSqlStr(sqlstr);
            sqlModel.setArgs(sqlArgs);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                RowSet rowSet = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rowSet.getSize(); i++) {
                    row = rowSet.getRow(i);
                    strBuff.append("<config  lineId=\"").append(row.getStrValue("LINE_ID")).append("\"");
                    strBuff.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
                    strBuff.append(" itemCode=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
                    strBuff.append(" itemName=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
                    strBuff.append(" itemSpec=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
                    strBuff.append(" quantity=\"").append(row.getStrValue("QUANTITY")).append("\"");
                    strBuff.append("> \n");
                    strBuff.append("</config>\n");
                }
            }
        } catch (QueryException e) {
            Logger.logError("获取备件信息错误：" + e.toString());
        } catch (ContainerException e) {
            Logger.logError("获取备件信息错误：" + e.toString());
        }
        return strBuff;
    }


}
