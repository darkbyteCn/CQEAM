package com.sino.flow.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.ReqAttributeList;

/**
 * User: zhoujs
 * Date: 2009-5-27 15:29:45
 * Function:查看流程 （可以查看流转过程，如没有可根据流程名称查看流程图信息）
 */
public class ViewFlow extends PubServlet {
    private Vector mflow = new Vector();

    private String actId = "";
    private String procName = "";//过程名
    private String fromtaskid = "";//上一任务ID
    private String currTaskId = "";//当前任务ID
    private String allLogTaskId = "";//所有走过的任务ID，逗号分隔

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "/flow/view/viewFlow.jsp";
         mflow = new Vector();

        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            actId = req.getParameter("actId");
            procName = req.getParameter("procName");

            if(StrUtil.isNotEmpty(actId)){
                getCurrentTask(actId,conn);
            }

            /*导出并存储文件,modify by zzj 2008-08-07*/
            ////////////////////////////////////////////////

            String fileID = getFileId(conn);

            ServletContext scon = getServletContext();
            String filePath = scon.getRealPath("/");
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += "sinoflo" + fileID + ".sfp";

            storeFile(filePath, fileID, conn);

            req.setAttribute("curFileID", fileID);
            if (StrUtil.isNotEmpty(actId)) {
                getFlowInfo(actId,conn);
            }
            //////////////////////////////////////////////

            mflow.add(0, procName);
            mflow.add(1, fromtaskid);
            mflow.add(2, currTaskId);
            mflow.add(3, allLogTaskId);

            req.setAttribute("mflow", mflow);
        } catch (PoolException e) {
            Logger.logError(e);
            req.setAttribute(ReqAttributeList.ERROR_MSG, "取连接错误！");
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            Logger.logError(e);
            req.setAttribute(ReqAttributeList.ERROR_MSG, "查询出错！");
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (ContainerException e) {
            Logger.logError(e);
            req.setAttribute(ReqAttributeList.ERROR_MSG, "查询出错！");
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }

    /**
     * 取流程信息
     * @param actId 流程ID
     * @param conn 数据库连接
     * @throws QueryException
     * @throws ContainerException 
     */
    private void getCurrentTask(String actId, Connection conn) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr =
                "SELECT SA.PROC_NAME,\n" +
                        "       STD1.FILE_TASK_ID FROM_TASK_ID,\n" +
                        "       STD2.FILE_TASK_ID CUR_TASK_ID\n" +
                        "  FROM SF_ACT SA, SF_TASK_DEFINE STD1, SF_TASK_DEFINE STD2\n" +
                        " WHERE SA.FROM_TASK_ID = STD1.TASK_ID\n" +
                        "   AND SA.CUR_TASK_ID = STD2.TASK_ID AND SA.ACTID = ?";

        ArrayList sqlArgs = new ArrayList();
        sqlArgs.add(actId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            RowSet rs = sq.getSearchResult();
            Row row = rs.getRow(0);
            procName = row.getStrValue("PROC_NAME");
            fromtaskid = row.getStrValue("FROM_TASK_ID");
            currTaskId = row.getStrValue("CUR_TASK_ID");
        }
    }

    private String getFileId(Connection conn) throws QueryException, ContainerException {
        String fileId = "";
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SFS.PRJ_FILEID\n" +
                "FROM SF_FLOWFILE_VERSION SFV,SF_FLOWFILE_STORE SFS\n" +
                "WHERE SFV.PRJ_FILEID=SFS.PRJ_FILEID\n" +
                "AND SFV.PRJNAME=?";
        sqlModel.setSqlStr(sqlStr);
        ArrayList tmpArr = new ArrayList();
        tmpArr.add(procName);
        sqlModel.setArgs(tmpArr);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            RowSet rs = sq.getSearchResult();
            Row row = rs.getRow(0);
            fileId = row.getStrValue("PRJ_FILEID");
        }
        return fileId;
    }

    /**
     * 存储文件
     * @param filePath 文件路径
     * @param fileID  文件ID
     * @param conn  数据库连接
     */
    private void storeFile(String filePath, String fileID, Connection conn) {
        File fl = new File(filePath);
        if (!fl.exists()) {
            //存储文件 download all files
            String prjquery =
                    "SELECT SFS.FILENAME FROM SF_FLOWFILE_STORE SFS WHERE SFS.PRJ_FILEID = " + fileID;


            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(prjquery);
                while (rs.next()) {
                    oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("FILENAME");
                    InputStream fin = blob.getBinaryStream();
                    OutputStream fout = new FileOutputStream(filePath);
                    int nSize = 1024;
                    byte Buffer[] = new byte[nSize];
                    int mmm = 0;
                    mmm = fin.read(Buffer, 0, 1024);
                    while (mmm != -1) {
                        fout.write(Buffer, 0, mmm);
                        mmm = fin.read(Buffer, 0, 1024);
                    }
                    fin.close();
                    fout.close();
                }
                //取流程文件到filename 结束
            } catch (IOException e) {
                Logger.logError(e);
            } catch (SQLException e) {
                Logger.logError(e);
            } finally {
                DBManager.closeDBResultSet(rs);
                DBManager.closeDBStatement(stmt);
            }
        }
    }

    /**
     * 获取流程流转信息
     * @param actId 流程流转ID
     * @param conn  数据库连接
     * @throws QueryException
     * @throws ContainerException
     */
    private void getFlowInfo(String actId, Connection conn) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr =
                "SELECT * FROM\n" +
                        "  (SELECT STD.FILE_TASK_ID CUR_TASK_ID, SAL.COMPLETE_DT\n" +
                        "          FROM SF_ACT_LOG SAL, SF_TASK_DEFINE STD\n" +
                        "         WHERE SAL.CUR_TASK_ID = STD.TASK_ID\n" +
                        "           AND SAL.ACTID = ?\n" +
                        "        UNION ALL\n" +
                        "        SELECT STD.FILE_TASK_ID CUR_TASK_ID, GETDATE() COMPLETE_DT\n" +
                        "          FROM SF_ACT SA, SF_TASK_DEFINE STD\n" +
                        "         WHERE SA.CUR_TASK_ID = STD.TASK_ID\n" +
                        "           AND SA.ACTID = ?\n" +
                        "        )\n" +
                        " ORDER BY COMPLETE_DT";
        ArrayList sqlArgs = new ArrayList();
        sqlArgs.add(actId);
        sqlArgs.add(actId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();

        if (sq.hasResult()) {
            RowSet rs = sq.getSearchResult();
            for (int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);
                if (i == 0) {
                    allLogTaskId = row.getStrValue("CUR_TASK_ID");
                } else {
                    allLogTaskId += "," + row.getStrValue("CUR_TASK_ID");
                }
            }
        }
    }

}