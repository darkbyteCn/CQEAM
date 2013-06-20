package com.sino.ams.match.amsMisLocMatch.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.amsMisLocMatch.dto.AmsMisLocMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-21
 * Time: 19:43:18
 * To change this template use File | Settings | File Templates.
 */
public class AmsMisLocMatchModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

	/**
	 * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsMisLocMatchModel(SfUserDTO userAccount, AmsMisLocMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws com.sino.base.exception.SQLModelException
     */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsMisLocMatchDTO amsHouseInfo = (AmsMisLocMatchDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_RENT_INFO("
                            + " RENT_ID,"
                            + " BARCODE,"
							+ " RENT_DATE,"
							+ " END_DATE,"
							+ " RENT_PERSON"
                            + ") VALUES ("
							+ "  NEWID() , ?, ?, ?, ?)";

			sqlArgs.add(amsHouseInfo.getBarcode());
//			sqlArgs.add(amsHouseInfo.getRentDate());
			sqlArgs.add(amsHouseInfo.getEndDate());
//			sqlArgs.add(amsHouseInfo.getRentPerson());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsMisLocMatchDTO rentDTO = (AmsMisLocMatchDTO) dtoParameter;
			String sqlStr = "UPDATE "
							+ " AMS_RENT_INFO"
							+ " SET"
							+ " RENT_PERSON = ?,"
							+ " RENT_DATE = ?,"
							+ " END_DATE = ?"
							+ " WHERE"
							+ " RENT_ID = ?";
//            sqlArgs.add(rentDTO.getRentPerson());
//            sqlArgs.add(rentDTO.getRentDate());
            sqlArgs.add(rentDTO.getEndDate());
//            sqlArgs.add(rentDTO.getRentId());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMisLocMatchDTO rentDTO = (AmsMisLocMatchDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " AMS_RENT_INFO"
			+ " WHERE"
			+ " RENT_ID = ?";
//		sqlArgs.add(rentDTO.getRentId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){                   //明晰
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMisLocMatchDTO rentDTO = (AmsMisLocMatchDTO)dtoParameter;
		String sqlStr = "SELECT ARI.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ARI.RENT_ID,\n" +
                "       ARI.RENT_PERSON,\n" +
                "       ARI.RENT_DATE,\n" +
                "       ARI.END_DATE\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_RENT_INFO ARI\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.BARCODE = ARI.BARCODE\n" +
                "   AND EII.ATTRIBUTE1 = 'RENT'\n" +
                "   AND EII.FINANCE_PROP = 'ASSETS'"+
                "   AND ARI.RENT_ID = ?";
//        sqlArgs.add(rentDTO.getRentId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：根据外键关联字段 barcodeNo 构造查询数据SQL。
	 * 框架自动生成数据租赁房屋(EAM) AMS_HOUSE_INFO详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcodeNo String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeNoModel(String barcodeNo){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " RENT_PERSON,"
			+ " RENT_DATE,"
			+ " HOUSE_ADDRESS,"
			+ " FLOOR_NUMBER,"
			+ " HOUSE_NO,"
			+ " HOUSE_AREA,"
			+ " AREA_UNIT,"
			+ " RENTAL,"
			+ " MONEY_UNIT,"
			+ " PAY_TYPE,"
			+ " END_DATE"
			+ " FROM"
			+ " AMS_HOUSE_INFO"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(barcodeNo);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		AmsMisLocMatchDTO amsHouseInfo = (AmsMisLocMatchDTO)dtoParameter;
		if(foreignKey.equals("barcodeNo")){
			sqlModel = getDataByBarcodeNoModel(amsHouseInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcodeNo 构造数据删除SQL。
	 * 框架自动生成数据租赁房屋(EAM) AMS_HOUSE_INFO 数据删除SQLModel，请根据实际需要修改。
	 * @param barcodeNo String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByBarcodeNoModel(String barcodeNo){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_HOUSE_INFO"
						+ " WHERE"
						+ " BARCODE = ?";
		sqlArgs.add(barcodeNo);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		AmsMisLocMatchDTO amsHouseInfo = (AmsMisLocMatchDTO)dtoParameter;
		if(foreignKey.equals("barcodeNo")){
			sqlModel = getDeleteByBarcodeNoModel(amsHouseInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：列出未匹配EAM地点。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{             // 列出未匹配EAM地点
		SQLModel sqlModel = new SQLModel();
//		try {
			int orgid = sfUser.getOrganizationId();
            List sqlArgs = new ArrayList();
			AmsMisLocMatchDTO locDTO = (AmsMisLocMatchDTO) dtoParameter;
			 String etsSql="	SELECT EO.WORKORDER_OBJECT_NO , " +
                      "        EO.WORKORDER_OBJECT_CODE, "  +
                      " 	   EO.WORKORDER_OBJECT_LOCATION  " +
					  "	FROM   ETS_OBJECT EO " +
					  //<70或等于80不是仓库地点
					  " WHERE ( EO.OBJECT_CATEGORY <=70 OR EO.OBJECT_CATEGORY  = 80)" +
                      "	AND  EO.IS_TEMP_ADDR = 0 "+
					  " AND  (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='')  "+
					  "	AND  NOT EXISTS "+
					  "        (SELECT 1 FROM ETS_MIS_LOCATION_MATCH EMLM " +
					  "			WHERE EMLM.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO " +
					  "			AND EMLM.ORGANIZATION_ID="+orgid+")"+
					  " AND  EO.ORGANIZATION_ID="+orgid+
                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EO.WORKORDER_OBJECT_LOCATION  LIKE ?) " +
                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EO.WORKORDER_OBJECT_CODE  LIKE ?) " +
                      " ORDER BY EO.WORKORDER_OBJECT_LOCATION  ";

            sqlArgs.add(locDTO.getEAMworkObjectLocation());
            sqlArgs.add(locDTO.getEAMworkObjectLocation());
            sqlArgs.add(locDTO.getWorkorderObjectCode());
            sqlArgs.add(locDTO.getWorkorderObjectCode());

            sqlModel.setSqlStr(etsSql);
			sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
		return sqlModel;
	}


	/**
	 * 功能：列出未匹配MIS地点。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getMISLoc() throws SQLModelException{             // 列出未匹配MIS地点
		SQLModel sqlModel = new SQLModel();
            int orgid = sfUser.getOrganizationId();
            List sqlArgs = new ArrayList();
			AmsMisLocMatchDTO locDTO = (AmsMisLocMatchDTO) dtoParameter;
		    String misSql=" SELECT " +
                      " EFA.ASSETS_LOCATION_CODE," +
                      " EFA.ASSETS_LOCATION  " +
					  " FROM ETS_FA_ASSETS_LOCATION EFA ,ETS_OU_CITY_MAP EOCM "+
					  " WHERE SUBSTRING(EFA.BOOK_TYPE_CODE,-4,4)=EOCM.COMPANY_CODE "+
		              " AND EOCM.ORGANIZATION_ID="+orgid+
		              " AND  NOT EXISTS "+
		              "  	(SELECT 1 FROM ETS_MIS_LOCATION_MATCH EMLM "+
		              "       WHERE EMLM.ASSETS_LOCATION=EFA.ASSETS_LOCATION "+
		              "       AND EMLM.ORGANIZATION_ID="+orgid+
		              " 	)"	+
                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION_CODE LIKE ?)"+
                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)"+
                      "	GROUP BY  EFA.ASSETS_LOCATION_CODE , EFA.ASSETS_LOCATION ";
//                      " AND  ( " + SyBaseSQLUtil.isNull() + "  OR  EFA.ASSETS_LOCATION LIKE ?)";
             sqlArgs.add(locDTO.getAssetsLocationCode());
             sqlArgs.add(locDTO.getAssetsLocationCode());

            sqlArgs.add(locDTO.getMISAssetsLocation());
            sqlArgs.add(locDTO.getMISAssetsLocation());

            sqlModel.setSqlStr(misSql);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


    /**
	 * 列出已匹配的地点信息
	 * @return webData
	 */
    public SQLModel getMatchedModel(int orgId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsMisLocMatchDTO locDTO = (AmsMisLocMatchDTO) dtoParameter;
       String matchedSql=	" SELECT " +
                        "        EMLM.WORKORDER_OBJECT_NO||'@@@'||EMLM.ASSETS_LOCATION  NO_AND_LOCATION,"+
                        "        EMLM.WORKORDER_OBJECT_NO ," +
						" 		 EO.WORKORDER_OBJECT_LOCATION ," +
						" 	     EMLM.ASSETS_LOCATION " +
						" FROM 	 ETS_MIS_LOCATION_MATCH EMLM,ETS_OBJECT EO " +
						" WHERE  EMLM.ORGANIZATION_ID="+orgId +
                        "	AND  EO.IS_TEMP_ADDR = 0 "+
                        "   AND  EMLM.WORKORDER_OBJECT_NO=EO.WORKORDER_OBJECT_NO";
        sqlModel.setSqlStr(matchedSql);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


     /**
	 * 匹配ETSMIS地点
	 * @return boolean
	 */
	public boolean matchETSMIS(String[] location ,String orgid){
		Connection conn=null;
		int org=Integer.parseInt(orgid);
		String sql="INSERT INTO ETS_MIS_LOCATION_MATCH" +
				"(WORKORDER_OBJECT_NO,ASSETS_LOCATION,ORGANIZATION_ID) " +
				" VALUES(?,?,?)";
		try{
			conn= DBManager.getDBConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql);
			for(int i=0;i<location.length;i++){
				conn.setAutoCommit(false);
				String[] loc= StrUtil.splitStr(location[i],"----");
				String etsNO=loc[0];
				String misLocation=loc[1];
				int no=Integer.parseInt(etsNO);
				pstmt.setInt(1,no);
				pstmt.setString(2,misLocation);
				pstmt.setInt(3,org);
				pstmt.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;
		}catch(Exception e){
			Logger.logError(e);
			try{
				conn.rollback();
			}catch(Exception ee){
				Logger.logError(ee);
			}
			return false;
		}finally{
            DBManager.closeDBConnection(conn);
        }
	}



	/**
	 *功能; 匹配ETSMIS地点
	 * @return boolean
	 */
	public SQLModel matchETSMISLoc() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AmsMisLocMatchDTO LocDTO = (AmsMisLocMatchDTO) dtoParameter;
			String sqlStr="INSERT INTO ETS_MIS_LOCATION_MATCH" +
				"(WORKORDER_OBJECT_NO,ASSETS_LOCATION,ORGANIZATION_ID) " +
				" VALUES(?,?,?)";
			sqlArgs.add(LocDTO.getWorkorderObjectNo());
			sqlArgs.add(LocDTO.getAssetsLocation());
			sqlArgs.add(sfUser.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 解除匹配
	 * @param arr
	 * @return  boolean
	 */
	public boolean dematch(String[] arr){
		Connection conn=null;
		String sql="DELETE FROM ETS_MIS_LOCATION_MATCH " +
				"	WHERE WORKORDER_OBJECT_NO=? " +
				"	AND ASSETS_LOCATION=?";
		try{
			conn=DBManager.getDBConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql);
			for(int i=0;i<arr.length;i++){
				conn.setAutoCommit(false);
				String[] loc=StrUtil.splitStr(arr[i],"----");
				String etsNO=loc[0];
				String misLocation=loc[1];
				int no=Integer.parseInt(etsNO);
				pstmt.setInt(1,no);
				pstmt.setString(2,misLocation);
				pstmt.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;
		}catch(Exception e){
			Logger.logError(e);
			try{
				conn.rollback();
			}catch(Exception ee){
				Logger.logError(ee);
			}
			return false;
		}finally{
            DBManager.closeDBConnection(conn);
        }
	}

  /*
  **功能；进行解除匹配操作。
   */
    public SQLModel getDelMatchModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsMisLocMatchDTO locDTO = (AmsMisLocMatchDTO) dtoParameter;
        String sqlStr="DELETE FROM ETS_MIS_LOCATION_MATCH " +
				"	WHERE WORKORDER_OBJECT_NO=? " +
				"	AND ASSETS_LOCATION=?";
       sqlArgs.add(locDTO.getWorkorderObjectNo());
       sqlArgs.add(locDTO.getAssetsLocation());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getupdataEIIModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsMisLocMatchDTO rentDTO = (AmsMisLocMatchDTO) dtoParameter;
        String sqlStr = " UPDATE" +
                "  ETS_ITEM_INFO EII\n" +
                "  SET" +
                "  EII.ATTRIBUTE1 = 'RENT'\n" +
                "  WHERE " +
                "  EII.BARCODE = ?\n" +
          sqlArgs.add(rentDTO.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
