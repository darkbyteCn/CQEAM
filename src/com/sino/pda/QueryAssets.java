package com.sino.pda;//package pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-11
 * Time: 16:49:32
 * Function:查询设备信息
 */
public class QueryAssets extends BaseServlet {

    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();
        Connection conn = null;
        SfUserDTO userAccount = null;
        String barcode = StrUtil.nullToString(req.getParameter("barcode"));
        RequestParser reqPar = new RequestParser();
        try {
            reqPar.transData(req);
            conn = DBManager.getDBConnection();
            userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            Logger.logInfo("PDA run QueryAssets servlet begin....");
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
            out.println("<items>");

            String returnStr = getAssetInfo(conn, barcode);
            out.println(returnStr);

            out.println("</items>");
            out.close();
        } catch (PoolException e) {
            e.printStackTrace();
        } catch (UploadException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (ContainerException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * 获取资产信息
     * @param conn
     * @param barcode
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private String getAssetInfo(Connection conn, String barcode) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EFA.ASSETS_DESCRIPTION     NAME,\n" +
                "       EFA.MODEL_NUMBER           TYPE,\n" +
                "       EFA.FA_CATEGORY1           LEVEL1,\n" +
                "       EFA.FA_CATEGORY2           LEVEL2,\n" +
                "       EFA.COST                   ORGINAL_COST,\n" +
                "       EFA.DEPRN_COST             CURRENT_COST,\n" +
                "       EFA.DATE_PLACED_IN_SERVICE START_DATE,\n" +
                "       EFA.LIFE_IN_YEARS          YEARS\n" +
                "  FROM ETS_FA_ASSETS EFA\n" +
                " WHERE EFA.TAG_NUMBER = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);


        StringBuffer m_sb = new StringBuffer();
        int nAllCount = 0;

        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        Row row = null;
        if (simpleQuery.hasResult()) {
            RowSet rs = simpleQuery.getSearchResult();
            for (int i = 0; i < rs.getSize(); i++) {
                row = rs.getRow(i);
                m_sb.append("<item  name=\"").append(row.getStrValue("NAME")).append("\"");
                m_sb.append(" type=\"").append(row.getStrValue("TYPE")).append("\"");
                m_sb.append(" level1=\"").append(row.getStrValue("LEVEL1")).append("\"");
                m_sb.append(" level2=\"").append(row.getStrValue("LEVEL2")).append("\"");
                m_sb.append(" orginal_cost=\"").append(row.getStrValue("ORGINAL_COST")).append("\"");
                m_sb.append(" start_date=\"").append(row.getStrValue("START_DATE")).append("\"");
                m_sb.append(" years=\"").append(row.getStrValue("YEARS")).append("\"");
                m_sb.append(" />");
            }
        }

        return m_sb.toString();
    }
}