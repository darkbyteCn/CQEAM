package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsStatisDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class AmsAssetsStatisFinishSpotModel extends AMSSQLProducer {
    private AmsAssetsStatisDTO dto = null;
    private String transType;

    public AmsAssetsStatisFinishSpotModel(SfUserDTO userAccount, AmsAssetsStatisDTO dtoParameter, String transType) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsStatisDTO) dtoParameter;
        this.transType = transType;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        try {
            sqlStr = getSqlText();
            if (transType.equals("weekspot") || transType.equals("weekspot_m")
                    || transType.equals("weekscan_m") || transType.equals("weekscan")
                    || transType.equals("weekscan_b") || transType.equals("weekspot_b")
                    || transType.equals("weekscan_n") || transType.equals("weekspot_n")) {
                if (dto.getOrganizationId() !=  0)
                    sqlArgs.add(dto.getOrganizationId());
                    sqlArgs.add(dto.getOrganizationId());
                if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                    sqlArgs.add(dto.getFromDate().toString());
                    sqlArgs.add(dto.getToDate().toString());
                }
            } else if (transType.equals("finishspot") || transType.equals("scan") ||
                    transType.equals("finishspot_m") || transType.equals("scan_m")
                    || transType.equals("scan_b") || transType.equals("scan_n")
                    || transType.equals("finishspot_n")) {
            	if (dto.getOrganizationId() !=  0)
                    sqlArgs.add(dto.getOrganizationId());
            	    sqlArgs.add(dto.getOrganizationId());
                if (!dto.getToDate().toString().equals(""))
                    sqlArgs.add(dto.getToDate().toString());
            } else {
            	if (dto.getOrganizationId() !=  0)
                    sqlArgs.add(dto.getOrganizationId());
            	    sqlArgs.add(dto.getOrganizationId());
            }
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public String getSqlText() throws CalendarException {
        String sql = "";
        if (transType.equals("asset")) {
            sql = "SELECT   EOCM.COMPANY AS COMPANY,COUNT(*)  AS TOTAL  \n" +
                    "FROM ETS_FA_ASSETS EFA,ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE EFA.IS_RETIREMENTS=0\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + "AND EFA.BOOK_TYPE_CODE=EOCM.BOOK_TYPE_CODE AND  (-1=? OR EOCM.ORGANIZATION_ID=?)  \n ";
            } else {
                sql = sql + "AND EFA.BOOK_TYPE_CODE=EOCM.BOOK_TYPE_CODE AND EOCM.ORGANIZATION_ID=?\n ";
            }
            sql = sql + "AND SUBSTRING(EFA.SEGMENT2,1,2) IN ('04','06','07','09','03')\n" +
                    "GROUP BY EOCM.COMPANY \n";
        } else if (transType.equals("spot")) {
            sql = "SELECT  EOCM.COMPANY AS COMPANY,COUNT(*) AS TOTAL\n" +
                    "FROM ETS_OBJECT EO,\n" +
                    "ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EO.OBJECT_CATEGORY='80'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n" +
                    "GROUP BY EOCM.COMPANY \n";
        } else if (transType.equals("finishspot")) {
            sql = "SELECT  B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT EOCM.COMPANY, EO.WORKORDER_OBJECT_NAME \n" +
                    " FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "   ETS_OU_CITY_MAP EOCM,\n" +
                    "    ETS_OBJECT EO\n" +
                    " WHERE AACH.ORGANIZATION_ID=EOCM.ORGANIZATION_ID \n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
                sql = sql + " AND  (-1=? OR EOCM.ORGANIZATION_ID=?  )  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + " AND  " + SyBaseSQLUtil.isNotNull("AACH.UPLOAD_DATE") + "  \n" +
                    " AND AACH.CHECK_LOCATION=EO.WORKORDER_OBJECT_NO \n";
            if (!dto.getToDate().toString().equals("")) {
            	 sql = sql + " AND AACH.UPLOAD_DATE <=? \n ";
            }
            sql = sql + " GROUP BY EOCM.COMPANY ,EO.WORKORDER_OBJECT_NAME) \n "+
            		"B GROUP BY B.COMPANY" ;

        } else if (transType.equals("scan")) {
            sql = "SELECT  EOCM.COMPANY AS COMPANY,COUNT(*) AS TOTAL \n" +
                    "FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "AMS_ASSETS_CHECK_LINE AACL,\n" +
                    "ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE AACH.ORGANIZATION_ID=EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND AACH.HEADER_ID=AACL.HEADER_ID\n" +
                    "AND  " + SyBaseSQLUtil.isNotNull("AACH.UPLOAD_DATE") + " \n";
            if (!dto.getToDate().toString().equals("")) {
            	  sql = sql + " AND AACH.UPLOAD_DATE <=?\n ";
            }
            sql = sql + "GROUP BY EOCM.COMPANY  \n";
        } else if (transType.equals("weekspot")) {
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT  EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME \n" +
                    "FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "ETS_OU_CITY_MAP EOCM,\n" +
                    "ETS_OBJECT EO\n" +
                    "WHERE AACH.ORGANIZATION_ID=EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql = sql + " AND  (-1=? OR EOCM.ORGANIZATION_ID=?  )  \n";
            } else {
                sql = sql + " AND  EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND AACH.CHECK_LOCATION=EO.WORKORDER_OBJECT_NO\n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND AACH.UPLOAD_DATE BETWEEN ? AND \n" +
                        " ?\n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME )\n"+
            			"B GROUP BY B.COMPANY" ;
        } else if (transType.equals("weekscan")) {
            sql = "SELECT EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL  \n" +
                    "FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "AMS_ASSETS_CHECK_LINE AACL,\n" +
                    "ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE AACH.ORGANIZATION_ID=EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND AACH.HEADER_ID=AACL.HEADER_ID\n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND AACH.UPLOAD_DATE BETWEEN ?\n" +
                        " AND  ?\n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY\n";
        } else if (transType.equals("scanspot")) {
            sql = " select cm.company as company ,count(*) as total \n" +
                    "from ams_assets_check_header t,\n" +
                    "ams_assets_check_line l,\n" +
                    "ets_ou_city_map cm,\n" +
                    "ets_object o\n" +
                    "where t.organization_id=cm.organization_id\n";
            sql = sql + "and t.header_id=l.header_id\n" +
                    "and  " + SyBaseSQLUtil.isNotNull("t.upload_date") + " \n" +
                    "and t.check_location=o.workorder_object_no\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " and  " + SyBaseSQLUtil.isNotNull("o.workorder_object_name") + "  \n";
            } else {
                sql = sql + "and o.workorder_object_name  like ?  \n";
            }
            sql = sql + "group by cm.company \n ";
        } else if (transType.equals("spot_m")) {   //机房的报表sql
            sql = "SELECT EOCM.COMPANY AS COMPANY,COUNT(*)  AS TOTAL \n" +
                    "FROM ETS_OBJECT EO,\n" +
                    "ETS_OU_CITY_MAP EOCM \n" +
                    "WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EO.OBJECT_CATEGORY >='20'" +
                    "AND  EO.OBJECT_CATEGORY <='60'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +
                    "GROUP BY EOCM.COMPANY \n";
        } else if (transType.equals("finishspot_m")) {
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT   EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME\n" +
                    " FROM ETS_OU_CITY_MAP EOCM,\n" +
                    " ETS_OBJECT EO,\n" +
                    " ETS_WORKORDER EW,SF_USER SU\n" +
                    " WHERE EW.ORGANIZATION_ID=EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql = sql + " AND  (-1=? OR EOCM.ORGANIZATION_ID=?  )  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + " AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    " AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    " AND EO.OBJECT_CATEGORY >='20'\n" +
                    " AND EO.OBJECT_CATEGORY <='60'\n" +
                    " AND CONVERT(INT,EW.WORKORDER_FLAG)>=13\n";
            if (!dto.getToDate().toString().equals("")) {
                sql = sql + " AND EW.UPLOAD_DATE <=?\n";
            }
            sql = sql + " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +
                    " GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME)"+
                    "B GROUP BY B.COMPANY";
        } else if (transType.equals("scan_m")) {
            sql = "SELECT  EOCM.COMPANY AS COMPANY ,COUNT(*) AS TOTAL  \n" +
                    "FROM ETS_WORKORDER_DTL EWD,\n" +
                    "ETS_WORKORDER EW, \n" +
                    "ETS_OU_CITY_MAP EOCM,\n" +
                    "ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO \n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND  EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY >='20'\n" +
                    "AND EO.OBJECT_CATEGORY <='60'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n";
            if (!dto.getToDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE <=?\n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY\n";
        } else if (transType.equals("weekspot_m")) {
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME \n" +
                    "FROM ETS_WORKORDER EW,ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO,SF_USER SU\n" +
                    "WHERE EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql=sql+" AND (-1=? OR EOCM.ORGANIZATION_ID=?) \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    "AND EO.OBJECT_CATEGORY >='20'\n" +
                    "AND EO.OBJECT_CATEGORY <='60'\n" +
                    "AND EW.WORKORDER_FLAG>='13'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND  ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME) \n"+
            			"B GROUP BY B.COMPANY" ;
        } else if (transType.equals("weekscan_m")) {
            sql = "SELECT EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL  \n" +
                    "FROM ETS_WORKORDER_DTL EWD,\n" +
                    "ETS_WORKORDER EW, \n" +
                    "ETS_OU_CITY_MAP EOCM,\n" +
                    "ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY >='20'\n" +
                    "AND EO.OBJECT_CATEGORY <='60'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND  ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY";
        } else if (transType.equals("asset_b")) {//基站的报表sql  ------资产数
            sql = "SELECT  EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL  \n" +
                    "FROM ETS_FA_ASSETS_2 EFA2,ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE EFA2.IS_RETIREMENTS=0 \n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EFA2.BOOK_TYPE_CODE=EOCM.BOOK_TYPE_CODE\n" +
                    "AND SUBSTRING(EFA2.SEGMENT2,1,2) IN ('01','02','03','05','08')\n" +
                    "GROUP BY EOCM.COMPANY \n";
        } else if (transType.equals("spot_b")) {//基站的报表sql  ------已完成基站数
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL  FROM \n"+
            		"(SELECT EOCM.COMPANY ,EO.WORKORDER_OBJECT_NO \n" +
                    "FROM ETS_WORKORDER EW,\n" +
                    "ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO,SF_USER SU\n" +
                    "WHERE EW.ORGANIZATION_ID=EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
            	sql = sql + " AND  (-1=? OR EOCM.ORGANIZATION_ID=?  )  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + " AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    "AND EW.WORKORDER_FLAG>='13'\n" +
                    "AND EO.OBJECT_CATEGORY='10'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +
                    "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NO) \n"+
                    "B GROUP BY B.COMPANY" ;
        } else if (transType.equals("scan_b")) {//基站的报表sql  ------扫描条码总数
            sql = "SELECT  EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL \n" +
                    "FROM ETS_WORKORDER_DTL EWD,\n" +
                    "ETS_WORKORDER EW,ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY='10'\n";
            if (!dto.getToDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE <=? \n";
            }
            sql = sql + "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n" +
                    "GROUP BY EOCM.COMPANY\n";
        } else if (transType.equals("weekspot_b")) {//基站的报表sql  ------最近一周已完成基站数
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT  EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME \n" +
                    "FROM ETS_WORKORDER EW,ETS_OU_CITY_MAP EOCM, ETS_OBJECT EO,SF_USER SU\n" +
                    "WHERE EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql=sql+" AND (-1=? OR EOCM.ORGANIZATION_ID=?) \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    "AND EO.OBJECT_CATEGORY='10'\n" +
                    "AND EW.WORKORDER_FLAG>='13'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME) \n"+
            			"B GROUP BY B.COMPANY" ;
        } else if (transType.equals("weekscan_b")) {//基站的报表sql  ------最近一周扫描条码总数
            sql = "SELECT EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL \n" +
                    "FROM ETS_WORKORDER_DTL EWD,\n" +
                    "ETS_WORKORDER EW,ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND  EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY='10'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND  ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY \n";
        } else if (transType.equals("dayspot_b")) {//基站的报表sql  ------每日完成基站数
            sql = "select company,dd,count(*) as total \n" +
                    "from(select cm.company,eo.workorder_object_name,\n" +
                    " wo.upload_date  dd\n" +
                    "from ets_workorder wo, \n" +
                    "ets_ou_city_map cm,ets_object eo,sf_user u\n" +
                    "where wo.organization_id = cm.organization_id\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            } else {
                sql = sql + " and cm.organization_id=?\n";
            }
            sql = sql + "and wo.workorder_object_no=eo.workorder_object_no\n" +
                    "and wo.implement_by=u.user_id\n" +
                    "and EO.OBJECT_CATEGORY='10'\n" +
                    "and wo.WORKORDER_FLAG>=13\n" +
                    "and (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +
                    "group by cm.company,eo.workorder_object_name, wo.upload_date \n" +
                    ")group by company,dd\n";
        } else if (transType.equals("spot_n")) {//网优的报表sql -----网优地点总数
            sql = "SELECT EOCM.COMPANY AS COMPANY ,COUNT(*)  AS TOTAL\n" +
                    "FROM ETS_OBJECT EO,\n" +
                    "ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EO.OBJECT_CATEGORY='15'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n" +
                    "GROUP BY EOCM.COMPANY";
        } else if (transType.equals("finishspot_n")) {//网优的报表sql -----已完成网优地点总数
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM \n"+
            		"(SELECT EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME \n" +
                    "FROM ETS_WORKORDER EW, \n" +
                    "ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO,SF_USER SU \n" +
                    "WHERE EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql = sql + " AND  (-1=? OR EOCM.ORGANIZATION_ID=?  )  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    "AND EO.OBJECT_CATEGORY='15'\n" +
                    "AND EW.WORKORDER_FLAG>='13'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n";
            if (!dto.getToDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE <=? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME) \n"+
            			"B GROUP BY B.COMPANY" ;
        } else if (transType.equals("scan_n")) {//网优的报表sql -----扫描条码总数
            sql = "SELECT EOCM.COMPANY AS COMPANY,COUNT(*) AS TOTAL \n" +
                    "FROM ETS_WORKORDER_DTL EWD,\n" +
                    "ETS_WORKORDER EW,ETS_OU_CITY_MAP EOCM,ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY='15'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE <=? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY\n";
        } else if (transType.equals("weekspot_n")) {//网优的报表sql -----最近一周已完成地点数
            sql = "SELECT B.COMPANY,COUNT(*) AS TOTAL FROM\n"+
            		"(SELECT  EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME\n" +
                    "FROM ETS_WORKORDER EW, ETS_OU_CITY_MAP EOCM, ETS_OBJECT EO, SF_USER SU\n" +
                    "WHERE EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n";
            if (dto.getOrganizationId() !=  0) {
                //sql = sql + " and  " + SyBaseSQLUtil.isNotNull("cm.organization_id") + "  \n";
            	sql=sql+" AND (-1=? OR EOCM.ORGANIZATION_ID=?) \n";
            } else {
                sql = sql + " AND  EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EW.IMPLEMENT_BY=SU.USER_ID\n" +
                    "AND EO.OBJECT_CATEGORY='15' \n" +
                    "AND EW.WORKORDER_FLAG>='13'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY,EO.WORKORDER_OBJECT_NAME) \n"+
            			"B GROUP BY B.COMPANY";
        } else if (transType.equals("weekscan_n")) {//网优的报表sql -----最近一周扫描条码总数
            sql = "SELECT EOCM.COMPANY,COUNT(*) AS TOTAL  \n" +
                    "FROM ETS_WORKORDER_DTL EWD, ETS_WORKORDER EW, ETS_OU_CITY_MAP EOCM, ETS_OBJECT EO\n" +
                    "WHERE EWD.WORKORDER_NO=EW.WORKORDER_NO\n";
            if (dto.getOrganizationId() !=  0) {
                sql = sql + " AND (-1=? OR EOCM.ORGANIZATION_ID=?)  \n";
            } else {
                sql = sql + " AND  EOCM.ORGANIZATION_ID=?\n";
            }
            sql = sql + "AND EW.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND EW.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO\n" +
                    "AND EO.OBJECT_CATEGORY='15'\n" +
                    "AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  \n";
            if (!dto.getToDate().toString().equals("") && !dto.getFromDate().toString().equals("")) {
                sql = sql + "AND EW.UPLOAD_DATE BETWEEN ? AND ? \n";
            }
            sql = sql + "GROUP BY EOCM.COMPANY\n";
        }
        return sql;
    }
}
