package com.sino.ams.system.important.servlet;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

/**
 * User: T_zhoujinsong
 * Date: 2011-3-14 17:26:16
 * Function:
 */
public class PublishInfoServlet {

    /**
     * 首页取得重要信息
     * @return RowSet
     * @throws QueryException
     */
    public static RowSet getHomeImportantInfo() throws QueryException {
        Connection conn = null;
        SimpleQuery gq = null;
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = new SQLModel();
            String sqlStr =
                    "SELECT TOP 12 *\n" +
                            "  FROM (" +
                            "SELECT PUBLISH_ID, TITLE, PUBLISH_DATE \n" +
                            //"          FROM AMS_INFO_PUBLISH AIP, SF_USER SU\n" +
                            "          FROM AMS_INFO_PUBLISH \n" +
                            "         WHERE INFO_TYPE = '1' " +
                            //"           AND AMP.PUBLISH_USER_ID = SU.USER_ID" +
                            "           AND DISABLED='N' \n" +
                            //" 			AND SU.ORGANIZATION_ID = ? \n" +
                            //"          AND PURCHASE_GUIDE_TYP " + SyBaseSQLUtil.isNull() + "  \n" +
                            //"          AND SEE_USER_TYPE = ?\n" +
//                           "         ORDER BY PUBLISH_DATE DESC" +
                            ") T ORDER BY PUBLISH_DATE DESC \n";
            List sqlArgs = new ArrayList();
            //sqlArgs.add(userType);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            gq = new SimpleQuery(sqlModel, conn);
            gq.setCalPattern(CalendarConstant.LINE_PATTERN);
            gq.executeQuery();
        } catch (PoolException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);

        }
        if (gq != null) {
            return gq.getSearchResult();
        } else {
            return new RowSet();
        }
    }

    public static HashMap getHelpDocs(String filePath) throws UnsupportedEncodingException {
        ResourceBundle bundle = ResourceBundle.getBundle("config.help");
        HashMap helpMap = new HashMap();
        HashMap countMap = new HashMap();
        HashMap mp = new HashMap();
        //动态构造help目录下的文件清单，仅限使用zip文件
        File tempFile = null;
        File parentDirectory = new File(filePath);
        int fileCount = 0;
        if (parentDirectory.isDirectory()) {
            File[] files = parentDirectory.listFiles();
            for (int i = 0; i < files.length; i++) {
                tempFile = files[i];
                if (!tempFile.isDirectory()) {
                    if (fileCount < 9) {
                        String fileName = tempFile.getName();
                        fileName = new String(fileName.getBytes(), "GBK");
                        String realName = "";
                        try {
                            realName = new String(bundle.getString(fileName).getBytes("ISO-8859-1"), "GBK");
                        } catch (Exception e) {
                            realName = "";
                        }
                        realName = StrUtil.isEmpty(realName) ? fileName : realName;
                        helpMap.put(fileName, realName);
                        countMap.put(fileCount, fileName);
                        fileCount++;
                    } else {
                        break;
                    }
                }
            }
        }

        mp.put(0, countMap);
        mp.put(1, helpMap);

        return mp;
    }
}
