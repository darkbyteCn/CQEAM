package com.sino.appbase.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;

/**
 * User: T_zhoujinsong
 * Date: 11-11-12 下午3:33
 * Function: 生成sybase创建sql及空函数创建sql
 */
public class GenSybaseObject {

    /**
     * 生成所有对象至指定目录下
     * @param conn
     * @param fl
     */
    public static void genAllObjectsSql(Connection conn, File fl) throws IOException, ContainerException, QueryException {
        Map mp = new HashMap();
        mp.put("V", "all_view.sql");
        mp.put("SF", "all_function.sql");
        mp.put("P", "all_procedure.sql");
        if (fl.isDirectory()) {
            for (Object o : mp.keySet()) {
                genObjectsSql(conn, new File(fl.getAbsolutePath() + System.getProperty("file.separator") + mp.get(o).toString()), o.toString(), false);
            }
        }
    }

    /**
     * 生成所有对象至指定目录下
     * @param conn
     * @param fl
     */
    public static void genAllEmptyObjectsSql(Connection conn, File fl) throws IOException, ContainerException, QueryException {
        Map mp = new HashMap();
        mp.put("SF", "all_empty_function.sql");
        mp.put("P", "all_empty_procedure.sql");
        if (fl.isDirectory()) {
            for (Object o : mp.keySet()) {
                genObjectsSql(conn, new File(fl.getAbsolutePath() + System.getProperty("file.separator") + mp.get(o).toString()), o.toString(), true);
            }
        }
    }


    /**
     * 生成所有该类型对象至指定文件
     * @param conn
     * @param fl
     * @param type
     * @throws QueryException
     * @throws ContainerException
     * @throws IOException
     */
    public static void genObjectsSql(Connection conn, File fl, String type, boolean empty) throws QueryException, ContainerException, IOException {
        if (fl.exists()) {
            fl.delete();
            fl.createNewFile();
        } else {
            fl.createNewFile();
        }
        RowSet rs_p = getAllObjects(conn, type);
        for (int i = 0; i < rs_p.getSize(); i++) {
            Row row = rs_p.getRow(i);
            genSingleObjectSql(conn, row.getStrValue(0), fl, type, empty);
        }
    }

    public static void genSingleObjectSql(Connection conn, String objectName, File fl, String type, boolean empty) throws IOException, QueryException, ContainerException {
        genObjectTitle(objectName, fl, type);
        SQLModel sqlModel = new SQLModel();
        sqlModel = GenSybaseObjectModel.getObjectModel(objectName, type);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            RowSet rs = sq.getSearchResult();
            Row row = null;
            if (empty) {
                StringBuffer sb = new StringBuffer();
                String txt = "";
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    txt = row.getStrValue("text");
                    if (txt.indexOf("BEGIN") > -1 || txt.indexOf("begin") > -1) {
                        int idx = txt.indexOf("BEGIN") > -1 ? txt.indexOf("BEGIN") : txt.indexOf("begin");

                        sb.append(txt.substring(0, idx));
                        appendFile(fl, sb.toString());
                        appendFile(fl, "\nBEGIN\n");
                        if(sb.toString().indexOf("RETURNS")>-1){
                            txt=sb.toString().substring(sb.toString().indexOf("RETURNS"),sb.toString().length());
                            int asIdx=txt.indexOf("AS");
                            asIdx=asIdx>-1?asIdx:txt.indexOf("as");
                            txt=txt.substring(0,asIdx);
                            if(txt.indexOf("INT")>-1){
                               appendFile(fl," RETURN 0");
                            }else{
                                appendFile(fl," RETURN ''");
                            }
                        }
                        appendFile(fl, "\nEND\n");
                        break;
                    } else {
                        sb.append(txt);
                    }


                }
                appendFile(fl, "\nGO\n");
            } else {

                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    appendFile(fl, row.getStrValue("text"));
                }
                appendFile(fl, "\nGO\n");
            }
        }
    }

    /**
     * 生成判断对象是否存在，如存在则drop掉的sql
     * @param objectName
     * @param fl
     * @param type
     * @throws IOException
     */

    public static void genObjectTitle(String objectName, File fl, String type) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("IF EXISTS ( SELECT name FROM sysobjects WHERE name = '");
        sb.append(objectName);
        sb.append("' AND type = '" + type + "')\n");
        if (type.equals("V")) {
            sb.append("\tDROP VIEW ");
        } else if (type.equals("SF")) {
            sb.append("\tDROP FUNCTION ");
        } else {
            sb.append("\tDROP PROCEDURE ");
        }
        sb.append(objectName + "\n");
        sb.append("GO\n");
        appendFile(fl, sb.toString());
    }

    public static void appendFile(File fl, String content) throws IOException {
        FileWriter writer = new FileWriter(fl, true);
        writer.write(content);
        writer.close();
    }

    /**
     * 获取需要生成sql的对象清单
     * @param conn
     * @param type
     * @return
     * @throws QueryException
     */
    public static RowSet getAllObjects(Connection conn, String type) throws QueryException {
        RowSet rs_p = new RowSet();
        SQLModel sqlModel = new SQLModel();
        sqlModel = GenSybaseObjectModel.getAllObjectsModel(type);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            rs_p = sq.getSearchResult();
        }

        return rs_p;
    }

    /**
     * 调用主函数，调用后会在指定目录生成view、function、procedure的创建脚本，function和procedure的空方法创建脚本
     * @param args
     * @throws ContainerException
     * @throws IOException
     * @throws QueryException
     */
    public static void main(String[] args) throws ContainerException, IOException, QueryException {
        Connection conn = DBManager.getDBConnection();
        genAllEmptyObjectsSql(conn, new File("d:/res1/"));
        genAllObjectsSql(conn, new File("d:/res1/"));
    }

}
