package com.sino.pda;

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
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-14
 * Time: 15:30:49
 * Function:非网络资产下载
 */
public class BillDownload extends BaseServlet {
     private static final String CONTENT_TYPE = "application/xml; charset=GBK";
	private static final String DOC_TYPE = null;
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		Connection conn = null;
		SfUserDTO sfUser = new SfUserDTO();
		SfUserDTO userAccount = null;
		RequestParser reqPar = new RequestParser();
		try {
			reqPar.transData(req);
			conn = DBManager.getDBConnection();
			userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            Logger.logInfo("PDA run BillDownload servlet begin....");
    		out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
			out.println("<bills>");

            out.println(getHeadDate(conn));

            out.println("</bills>");
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
     * PDA下载单据
     * @return
     */
    public String getHeadDate(Connection conn) {
        SQLModel sqlModel = new SQLModel();
        StringBuffer returnStr = new StringBuffer("");
        try {
            List sqlArgs = new ArrayList();
            String sqlStr =  "SELECT" +
                    "       ACBB.BATCH_ID,\n" +
                    "       ACBB.BATCH_NO,\n" +
                    "       ACBH.BILL_TYPE,\n" +
                    "       ACBH.CHECK_LOCATION, --地点编号\n" +
                    "       AMS_PUB_PKG.GET_OBJECT_CODE(ACBH.CHECK_LOCATION), -- 地点\n" +
                    "       AMS_PUB_PKG.GET_OBJECT_NAME(ACBH.CHECK_LOCATION), -- 地点名称\n" +
                    "       ACBH.CREATED_BY,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(ACBH.CREATED_BY), --创建人\n" +
                    "       ACBH.CREATION_DATE,\n" +
                    "       ACBH.START_TIME,\n" +
                    "       ACBH.DOWNLOAD_DATE, --签收日期\n" +
                    "       ACBH.DOWNLOAD_BY, --签收人\n" +
                    "       ACBH.IMPLEMENT_BY, --执行人\n" +
                    " CASE WHEN ACBH.IMPLEMENT_BY='' THEN '否' ELSE '是' END IMPLEMENT_BY,"+//根据是否有执行人判断 是否发到组别
                    "       ACBH.IMPLEMENT_DAYS, --执行周期\n" +
                    "       ACBB.BATCH_IMPLEMENT_DAYS, --执行周期\n" +
                    "       ACBB.BATCH_START_TIME, --开始时间\n" +
                    "       dbo.AWP_GET_DEADLINE_DATE(ACBB.BATCH_START_TIME,\n" +
                    "                                                   ACBB.BATCH_IMPLEMENT_DAYS)  DEADLINE_DATE --最迟完成时间\n" +
                    "  FROM AMS_CHK_BILL_BATCH ACBB, AMS_CHK_BILL_HEAD ACBH \n" +
                    " WHERE ACBB.BATCH_ID = ACBH.BATCH_ID";
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
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
                    String creationDate = row.getStrValue("CREATION_DATE");
                    String creationUser = row.getStrValue("CREATION_USER");
                    returnStr.append("<bill  transNo=\"").append(transNo).append("\"");
                    returnStr.append(" transType=\"").append(transType).append("\"");
                    returnStr.append(" creationDate=\"").append(creationDate).append("\"");
                    returnStr.append(" creationUser=\"").append(creationUser).append("\"");      //
                    returnStr.append(" vendor=\"").append("").append("\"");
                    returnStr.append(" > \n");
                    returnStr = getLineDate(returnStr, conn, transId);     //获得行信息
                    returnStr.append("</bill>\n");
                }
            }

        } catch (QueryException e) {
            e.printStackTrace();
            Logger.logError("获取非网络资产信息失败！" + e.toString());
        } catch (ContainerException e) {
            Logger.logError("获取非网络资产信息失败！" + e.toString());
            e.printStackTrace();
        }
        return returnStr.toString();
    }


    /**
     * 获取行信息
     * @return StringBuffer
     */
    private StringBuffer getLineDate(StringBuffer strBuff, Connection conn, String transId) {
            SQLModel sqlModel = new SQLModel();
            String sql = "";
            List sqlArgs = new ArrayList();
            try {
                String sqlstr = "";
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
                        strBuff.append("<item  lineId=\"").append(row.getStrValue("LINE_ID")).append("\"");
                        strBuff.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
                        strBuff.append(" item_code=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
                        strBuff.append(" name=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
                        strBuff.append(" type=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
                        strBuff.append(" quantity=\"").append(row.getStrValue("QUANTITY")).append("\"");
                        strBuff.append("> \n");
                        strBuff.append("</item>\n");
                    }
                }
            } catch (QueryException e) {
                Logger.logError("获取非网络资产信息失败：" + e.toString());
            } catch (ContainerException e) {
                Logger.logError("获取非网络资产信息失败：" + e.toString());
            }
        return strBuff;
    }
}
