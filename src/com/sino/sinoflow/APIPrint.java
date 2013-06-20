package com.sino.sinoflow;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.log.Logger;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ContainerException;
import com.sino.sinoflow.model.SfApiModel;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class APIPrint {
    private static final boolean onlyLattestProject = true;
    private static String apiFunctions[][] = {{"SFQUERYOPEN","SFQueryOpen"}, {"SFPOSTOPEN","SFPostOpen"},
            {"SFQUERYSIGN","SFQuerySign"}, {"SFPOSTSIGN","SFPostSign"},
            {"SFQUERYCOMPLETE","SFQueryComplete"}, {"SFGROUPREVIEW","SFGroupReview"},
            {"SFQUERYCYCLEREVIEW","SFQueryCycleReview"},
            {"SFQUERYCONDITIONALFLOW","SFQueryConditionalFlow"},
            {"SFQUERYGROUP","SFQueryGroup"}, {"SFPARALLELFLOW","SFParallelFlow"},
            {"SFQUERYASSISTFLOW","SFQueryAssistFlow"}, {"SFQUERYDISTRIBUTE","SFQueryDistribute"},
            {"SFQUERYGOBACK","SFQueryGoBack"}, {"SFQUERYSAVE","SFQuerySave"},
            {"SFPOSTSAVE","SFPostSave"}};

    public static void main(String[] args) {
        String path = "D:\\EAM_API.TXT";
        Connection conn = null;
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = getApiModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            String prjName = "";
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                for(int i = 0; i < rs.getSize(); i++) {
                    Row row = rs.getRow(i);
                    if(!row.getStrValue("PROJECT_NAME").equals(prjName)) {
                        prjName = row.getStrValue("PROJECT_NAME");
                        pw.println("工程名称:" + prjName + "\r\n");
                    }
                    pw.println("-----------------------------------------------------------------");
                    pw.println("接口程序名称:" + row.getStrValue("API_NAME"));
                    String procNames = getCallProc(row.getStrValue("API_NAME"), conn);
                    pw.println("呼叫此接口的流程任务:-");
                    pw.println(procNames);
                    pw.println("-----------------------------------------------------------------");
                    List l = getApiFunctions(row);
                    for(int j = 0; j < apiFunctions.length; j++) {
                        if(!l.get(j).equals("")) {
                            pw.println("事件:" + apiFunctions[j][1]);
                            pw.println("function " + apiFunctions[j][1] + "() {");
                            pw.println("  Error_Msg = \"\";");
                            pw.print("  Launch_Continue = true;\r\n  ");
                            pw.println(((String)l.get(j)).replace("\r\n", "\r\n  "));
                            pw.println("}\r\n");
                        }
                    }
                }
                pw.close();
                fw.close();
            }
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    public static SQLModel getApiModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SA.API_ID,"
            + " SA.PROJECT_NAME,"
            + " SA.API_CONTROL,"
            + " SA.SFQUERYOPEN,"
            + " SA.SFPOSTOPEN,"
            + " SA.SFQUERYSIGN,"
            + " SA.SFPOSTSIGN,"
            + " SA.SFQUERYCOMPLETE,"
            + " SA.SFGROUPREVIEW,"
            + " SA.SFQUERYCYCLEREVIEW,"
            + " SA.SFQUERYCONDITIONALFLOW,"
            + " SA.SFQUERYGROUP,"
            + " SA.SFPARALLELFLOW,"
            + " SA.SFQUERYASSISTFLOW,"
            + " SA.SFQUERYDISTRIBUTE,"
            + " SA.SFQUERYGOBACK,"
            + " SA.SFQUERYSAVE,"
            + " SA.SFPOSTSAVE,"
            + " SA.API_NAME,"
            + " SA.ENABLED"
            + " FROM"
            + " SF_API SA"
            + " ORDER BY SA.PROJECT_NAME, SA.API_NAME";

        sqlModel.setSqlStr(sqlStr);

       sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public static List getApiFunctions(Row r) throws ContainerException {

        List resultList = new ArrayList();
        String contents = "";
        for(int i = 0; i < apiFunctions.length; i++) {
            contents = r.getStrValue(apiFunctions[i][0]);
            resultList.add(contents);
        }
        return resultList;
    }

    public static String getCallProc(String api, Connection conn) throws QueryException, ContainerException {
        String result = "";
        SQLModel sqlModel;
        if(onlyLattestProject)
            sqlModel = getOnlyLatestCallProcModel(api);
        else
            sqlModel = getCallProcModel(api);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()) {
            RowSet rs = simpleQuery.getSearchResult();
            for(int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);
                if(result.equals("")) {
                    result = row.getStrValue("PROCEDURE_NAME") + "-" + row.getStrValue("TASK_NAME");
                } else {
                    result += "\r\n" + row.getStrValue("PROCEDURE_NAME") + "-" + row.getStrValue("TASK_NAME");
                }
            }
        }
        return result;
    }

    public static SQLModel getCallProcModel(String api) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME"
            + " FROM"
            + " SF_PROCEDURE SP, SF_TASK ST"
            + " WHERE ST.API = ? AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " ORDER BY SP.PROCEDURE_NAME, ST.TASK_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(api);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public static SQLModel getOnlyLatestCallProcModel(String api) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME"
            + " FROM"
            + " SF_PROCEDURE SP, SF_TASK ST, SF_PROJECT_V SPV"
            + " WHERE ST.API = ? AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " ORDER BY SP.PROCEDURE_NAME, ST.TASK_NAME";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(api);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}