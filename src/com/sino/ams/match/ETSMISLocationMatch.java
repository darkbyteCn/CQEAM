package com.sino.ams.match;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

/**
 * User: Zyun
 * Date: 2007-11-21
 * Time: 20:32:36
 */
public class ETSMISLocationMatch {

    /**
     *
     */
    public ETSMISLocationMatch() {
        super();
    }

    /**
     * @param orgid orgid
     * @return GridQuery;
     *         列出未匹配EAMS地点
     */
    public RowSet listETSLocation(String orgid) {
        Connection conn = null;
        String etsSql = "	SELECT eo.workorder_object_no etsNo, " +
                " 	   eo.workorder_object_location etsLocation " +
                "	FROM   ets_object eo " +
                //<70不是仓库地点
                " WHERE ( eo.object_category <70 or eo.object_category  = 80 or eo.object_category  = 71)" + //71为备件正常库 added by Herry2008-4-14

                "	AND eo.is_temp_addr = 0 " +
                "   AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  " +
                "	AND NOT EXISTS " +
                "        (select 1 from ets_mis_location_match emlm " +
                "			where emlm.workorder_object_no=eo.workorder_object_no " +
                "			and emlm.organization_id=" + orgid + ")" +
                " AND  eo.Organization_Id=" + orgid +
                " order by eo.workorder_object_location  ";
        Logger.logInfo("列出未匹配EAM地点SQL:" + etsSql);
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = new SQLModel();
            sqlModel.setSqlStr(etsSql);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rowset = null;
            rowset = simpleQuery.getSearchResult();
            return rowset;
//	  		GridQuery gq=new GridQuery (etsSql,conn);
//	  		gq.executeQuery();
//	  		return gq;
        } catch (Exception e) {
            Logger.logError(e);
            return null;
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * 列出未匹配MIS地点
     * @param orgid orgid
     * @return GridQuery
     */
    public RowSet listMISLocation(String orgid) {
        Connection conn = null;
        SQLModel sqlModel = new SQLModel();
        String misSql = " SELECT efa.assets_location misLocation " +
                "  FROM ets_fa_assets_location efa ,ets_ou_city_map eocm " +
                " WHERE substr(efa.book_type_code,-4,4)=eocm.company_code " +
                "   AND eocm.organization_id=" + orgid +
                "   AND NOT EXISTS " +
                "  	(select 1 from ets_mis_location_match emlm " +
                "       where emlm.assets_location=efa.assets_location " +
                "       and emlm.organization_id=" + orgid +
                " 	)" +
                "	GROUP BY efa.Assets_Location ";
        Logger.logError("列出未匹配MIS地点SQL:" + misSql);

        try {
            conn = DBManager.getDBConnection();
            sqlModel.setSqlStr(misSql);
            RowSet rs = null;
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            rs = simpleQuery.getSearchResult();
            return rs;
//            GridQuery gq=new GridQuery(misSql,conn);
//			gq.executeQuery();
//			return gq;
        } catch (Exception e) {
            Logger.logError(e);
            return null;
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * 列出已匹配的地点信息
     * @param orgid   orgId
     * @param request HttpServletRequest
     */
    public void listMatchETSLocation(String orgid, HttpServletRequest request) {
        Connection conn = null;
        String etsSql2 = " SELECT emlm.workorder_object_no etsNo2,  " +
                " 		 eo.workorder_object_location etsLocation2," +
                " 	     emlm.assets_location misLocation2 " +
                " FROM 	 ets_mis_location_match emlm,ets_object eo " +
                " WHERE  emlm.organization_id=" + orgid +
                "	AND  eo.is_temp_addr = 0 " +
                "   AND  emlm.workorder_object_no=eo.workorder_object_no";
        Logger.logInfo("列出已匹配的ETS地点SQL:" + etsSql2);
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = new SQLModel();
            sqlModel.setSqlStr(etsSql2);
            WebPageView wpv = new WebPageView(request, conn);
            wpv.produceWebData(sqlModel);
        } catch (QueryException e) {
            e.printLog();
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * 匹配EAMMIS地点
     * @param location String[]
     * @param orgid    String
     * @return boolean
     */
    public boolean matchETSMIS(String[] location, String orgid,String userid) {
        Connection conn = null;
        int org = Integer.parseInt(orgid);
        String sql = "insert into ets_mis_location_match" +
                "(WORKORDER_OBJECT_NO,ASSETS_LOCATION,ORGANIZATION_ID,CREATION_DATE,CREATED_BY) " +
                " values(?,?,?,GETDATE(),?)";
        try {
            conn = DBManager.getDBConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < location.length; i++) {
                conn.setAutoCommit(false);
                String[] loc = StrUtil.splitStr(location[i], "----");
                String etsNO = loc[0];
                String misLocation = loc[1];
                int no = Integer.parseInt(etsNO);
                pstmt.setInt(1, no);
                pstmt.setString(2, misLocation);
                pstmt.setInt(3, org);
                pstmt.setString(4,userid);
                pstmt.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
            return true;
        } catch (Exception e) {
            Logger.logError(e);
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ee) {
                Logger.logError(ee);
            }
            return false;
        } finally {
            DBManager.closeDBConnection(conn);
        }


    }

    /**
     * 解除匹配
     * @param arr
     * @return boolean
     */
    public boolean dematch(String[] arr) {
        Connection conn = null;
        String sql = "DELETE FROM ets_mis_location_match emlm " +
                "	WHERE emlm.workorder_object_no=? " +
                "	AND emlm.assets_location=?";
        try {
            conn = DBManager.getDBConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < arr.length; i++) {
                conn.setAutoCommit(false);
                String[] loc = StrUtil.splitStr(arr[i], "----");
                String etsNO = loc[0];
                String misLocation = loc[1];
                int no = Integer.parseInt(etsNO);
                pstmt.setInt(1, no);
                pstmt.setString(2, misLocation);
                pstmt.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
            return true;
        } catch (Exception e) {
            Logger.logError(e);
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ee) {
                Logger.logError(ee);
            }
            return false;
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }
}
