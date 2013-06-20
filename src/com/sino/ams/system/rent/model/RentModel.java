package com.sino.ams.system.rent.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.rent.dto.RentDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: AmsHouseInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsHouseInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class RentModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public RentModel(SfUserDTO userAccount, RentDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataCreateModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			RentDTO rentdto = (RentDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_RENT_INFO ("
                            + " RENT_ID,"
                            + " BARCODE,"
                            + " RENT_PERSON,"      //对方签约单位
							+ " RENT_DATE,"        //起始日期			
//							+ " RENTAL,"		   //租金
							
							+ " CREATED_BY,"
							+ " CREATION_DATE,"
							+ " END_DATE,"         //终止日期
                            + " TENANCY,"          //租期
                            + " YEAR_RENTAL,"        //年租金
                            + " MONTH_REANTAL,"       //月租金
                            + " REMARK"
                            + ") VALUES ("
							+ "  NEWID() , ?, ?, ?, ? , " + SyBaseSQLUtil.getCurDate() + " , ?, ?, ?, ?,?)";

			sqlArgs.add(rentdto.getBarcode());
            sqlArgs.add(rentdto.getRentPerson());
            sqlArgs.add(rentdto.getRentDate());
            sqlArgs.add(sfUser.getUserId());

            sqlArgs.add(rentdto.getEndDate());
            sqlArgs.add(rentdto.getTenancy());
            sqlArgs.add(rentdto.getYearRental());
            sqlArgs.add(rentdto.getMonthReantal());
            sqlArgs.add(rentdto.getRemark());

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
	public SQLModel getDataUpdateModel() throws SQLModelException{     //修改操作
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			RentDTO rentDTO = (RentDTO) dtoParameter;
			String sqlStr =  " UPDATE" +
                    "   AMS_RENT_INFO \n" +
                    "   SET RENT_PERSON   = dbo.NVL(?,RENT_PERSON),\n" +
                    "       TENANCY       = dbo.NVL(?, TENANCY ),\n" +
                    "       YEAR_RENTAL   = dbo.NVL(?, YEAR_RENTAL),\n" +
                    "       MONTH_REANTAL = dbo.NVL(?, MONTH_REANTAL),\n" +
                    "       RENT_DATE     = ISNULL(?, RENT_DATE),\n" +
                    "       END_DATE      = ISNULL(?, END_DATE),\n" +
                    "		RENTAL		  = dbo.NVL(?, RENTAL),\n"	+
                    "       REMARK        = dbo.NVL(?, REMARK)\n" +
                    " WHERE RENT_ID = ?";
            sqlArgs.add(rentDTO.getRentPerson());
            sqlArgs.add(rentDTO.getTenancy());
            sqlArgs.add(rentDTO.getYearRental());
            sqlArgs.add(rentDTO.getMonthReantal());
            sqlArgs.add(rentDTO.getRentDate());
            sqlArgs.add(rentDTO.getEndDate());
            sqlArgs.add(rentDTO.getRental());
            sqlArgs.add(rentDTO.getRemark());
            sqlArgs.add(rentDTO.getRentId());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	

 /**
     * 功能：修改eii表的相关信息。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getUpdteEII() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
     	RentDTO rentDTO = (RentDTO) dtoParameter;
      	String sqlStr = "\n" +
                  "UPDATE ETS_ITEM_INFO \n" +
                  "   SET LAST_UPDATE_DATE    = " + SyBaseSQLUtil.getCurDate() + " ,\n" +
                  "       LAST_UPDATE_BY      = ?,\n" +
                  "       MAINTAIN_DEPT       = dbo.NVL(?,MAINTAIN_DEPT),\n" +
                  "       RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),\n" +
                  "       RESPONSIBILITY_DEPT = dbo.NVL(?, RESPONSIBILITY_DEPT),\n" +
                  "       ADDRESS_ID          = dbo.NVL(?, ADDRESS_ID)," +
                  "       MAINTAIN_USER		  = dbo.NVL(?, MAINTAIN_USER)\n" +
                  " WHERE BARCODE = ?";

       sqlArgs.add(sfUser.getUserId());
//       sqlArgs.add(rentDTO.getMaintainDept());
       sqlArgs.add(rentDTO.getMaintainDept());
       sqlArgs.add(rentDTO.getUserId());
       sqlArgs.add(rentDTO.getDeptId());
       sqlArgs.add(rentDTO.getAddressId()) ;
       sqlArgs.add(rentDTO.getMaintainUser()) ;
       sqlArgs.add(rentDTO.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }



    /**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		RentDTO rentDTO = (RentDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " AMS_RENT_INFO"
			+ " WHERE"
			+ " RENT_ID = ?";
		sqlArgs.add(rentDTO.getRentId());
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
		RentDTO rentDTO = (RentDTO)dtoParameter;
		
        String sqlStr ="SELECT EII.BARCODE,\n" +
                "       EII.SYSTEMID SYSTEM_ID,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_UNIT,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ARI.RENT_PERSON,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       dbo.APP_GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) ADDRESSLOC,\n" +
                "       AMD.DEPT_NAME RESPONSIBILITY_DEPT,\n" +
                "       dbo.APP_GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT_NAME,\n" +
                "       EII.MAINTAIN_DEPT,\n" +
                "		ARI.RENTAL,\n"		+
                "       ARI.TENANCY,\n" +
//                "       ARI.RENT_DATE,\n" +
                "       ARI.YEAR_RENTAL,\n" +
                "       ARI.MONTH_REANTAL,\n" +
                "       ARI.END_DATE,\n" +
                "       EII.RESPONSIBILITY_USER USER_ID,\n" +
                "       AME.USER_NAME USERNAME,\n" +
                "       EII.RESPONSIBILITY_DEPT DEPT_ID,\n" +
                "       ARI.REMARK,\n" +
                "       ARI.RENT_ID,\n" +
                "       EII.MAINTAIN_USER,\n" +
                "       EII.START_DATE RENT_DATE,\n" +
                "       ARIH.HISTORY_ID,"		+
                "       EII.ITEM_QTY,\n" +
                "       1 NOWQTY,\n" +
                "       0 CYSL\n" +
                "  FROM ETS_ITEM_INFO    EII,\n" +
                "       ETS_SYSTEM_ITEM  ESI,\n" +
                "       AMS_RENT_INFO    ARI,\n" +
                "       AMS_MIS_EMPLOYEE AME,\n" +
                "       AMS_RENT_INFO_HISTORY ARIH, \n" +
                "       AMS_MIS_DEPT     AMD\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.BARCODE *= ARI.BARCODE\n" +
                "	AND EII.BARCODE *= ARIH.BARCODE\n" +
                "	AND (ARIH.DISABLED " + SyBaseSQLUtil.isNullNoParam() + "  OR ARIH.DISABLED = 'Y')" +
                "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "   AND EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
                "   AND EII.BARCODE = ?";
        sqlArgs.add(rentDTO.getBarcode());

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
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		RentDTO rentDTO = (RentDTO)dtoParameter;
		String sqlStr = "SELECT EII.BARCODE,\n" +
						 "		 ESI.ITEM_NAME,\n" 	+
						 "		 ESI.ITEM_SPEC,\n"	+
						 "		 EII.CONTENT_NAME,\n" +
						 "       ARIH.RENT_PERSON,\n"  +
						 "       ARIH.RENT_DATE,\n" +
				         "       ARIH.END_DATE,\n" +
				         "       ARIH.TENANCY,\n" +
				         "       ARIH.YEAR_RENTAL,\n" +
				         "		 ARIH.RENTAL,\n"	+
				         "       ARIH.MONTH_REANTAL,\n" +
				         "       ARIH.DISABLED,\n" +
				         "       ARIH.REMARK\n" +
				         "  FROM ETS_ITEM_INFO EII, AMS_RENT_INFO_HISTORY ARIH, ETS_SYSTEM_ITEM  ESI\n" +
				         " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
				         "   AND EII.BARCODE *= ARIH.BARCODE\n" +
				         "   AND EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
				         "	 AND ARIH.BARCODE = ? " +
				         " ORDER BY ARIH.HISTORY_ID";
		sqlArgs.add(rentDTO.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
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
	 * 
	 * Function:    	仅修改租赁表中的备注字段
	 * @return			SQLModel
	 * @throws SQLModelException
	 * @author  		李轶
	 * @throws CalendarException 
	 * @Version 		0.1
	 * @Date:   		May 14, 2009
	 */
    public SQLModel updateAmsRentInfo()throws SQLModelException, CalendarException{
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentdto = (RentDTO) dtoParameter;
        
        String sqlStr = "UPDATE AMS_RENT_INFO " +
        		"			SET RENT_PERSON = dbo.NVL(?, RENT_PERSON)," +
        		"             RENT_DATE = ?,"		+
        		"				END_DATE = ?,"		+
        		"				RENTAL	 = dbo.NVL(?, RENTAL),"	+
        		"				TENANCY = dbo.NVL(?, TENANCY),"	+
        		"				YEAR_RENTAL = dbo.NVL(?, YEAR_RENTAL),"	+
        		"				MONTH_REANTAL = dbo.NVL(?, MONTH_REANTAL),"	+
        		"				REMARK = dbo.NVL(?, REMARK), " +
        		"				LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + " , " +
        		"				LAST_UPDATE_BY = ?" +
        		"		 WHERE BARCODE = ? ";
        sqlArgs.add(rentdto.getRentPerson());
        sqlArgs.add(rentdto.getRentDate());
        sqlArgs.add(rentdto.getEndDate());
        sqlArgs.add(rentdto.getRental());
        sqlArgs.add(rentdto.getTenancy());
        sqlArgs.add(rentdto.getYearRental());
        sqlArgs.add(rentdto.getMonthReantal());        
		sqlArgs.add(rentdto.getRemark());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(rentdto.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel updateAmsRentInfoHistoryRemark()throws SQLModelException{
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentdto = (RentDTO) dtoParameter;
        String sqlStr = "UPDATE" +
                " AMS_RENT_INFO_HISTORY" +
                " SET " +
                "REMARK = dbo.NVL(?,REMARK), " +
                "RENTAL = dbo.NVL(?, RENTAL)" +
        		"		 WHERE HISTORY_ID = ?";
        sqlArgs.add(rentdto.getRemark());
        sqlArgs.add(rentdto.getRental());
		sqlArgs.add(rentdto.getHistoryId());
		
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 
     * Function:		失效一条资产租赁历史记录
     * @return			SQLModel
     * @throws SQLModelException
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		May 14, 2009
     */
    public SQLModel disabledAmsRentInfoHistory()throws SQLModelException{
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentdto = (RentDTO) dtoParameter;
        String sqlStr = "UPDATE " +
                "AMS_RENT_INFO_HISTORY " +
                "SET DISABLED = 'N'" +
        		"		 WHERE HISTORY_ID = ?";
        
		sqlArgs.add(rentdto.getHistoryId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    
    /**
     * 
     * Function:		新增租赁资产变动历史
     * @return			SQLModel
     * @throws SQLModelException
     * @throws CalendarException 
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		May 14, 2009
     */
    public SQLModel insertAmsRentInfoHistory()throws SQLModelException, CalendarException{
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentdto = (RentDTO) dtoParameter;
     
        String sqlStr =   " INSERT INTO AMS_RENT_INFO_HISTORY"	+
							"			(HISTORY_ID,"			+
							"			BARCODE,"				+
							"			RENT_DATE,"				+
							"			END_DATE,"				+
							"			TENANCY,"				+
							"			YEAR_RENTAL,"			+
							"			MONTH_REANTAL,"			+
							"			REMARK,"				+
							"           RENT_PERSON,"			+
							"			DISABLED,"				+
							"           LAST_UPDATE_DATE,"		+
							"           LAST_UPDATE_BY)"		+
//							"			RENTAL)"				+
							"	VALUES"					+
							"		(NEWID() ,"	+
							"			?,"		+
							"			?,"		+
							"			?,"		+   
							"			?, "	+   
							"			?,"	+
							"			?, "	+
							"			?,"		+
							"           ?,"	+	
							"           'Y',"					+	
							"           " + SyBaseSQLUtil.getCurDate() + " ,"				+
							"           ?)";
//							"			dbo.NVL(?, RENTAL))";
        
        sqlArgs.add(rentdto.getBarcode());
        sqlArgs.add(rentdto.getRentDate());
        sqlArgs.add(rentdto.getEndDate());
        sqlArgs.add(rentdto.getTenancy());
        sqlArgs.add(rentdto.getYearRental());
        sqlArgs.add(rentdto.getMonthReantal());
        sqlArgs.add(rentdto.getRemark());
        sqlArgs.add(rentdto.getRentPerson());
        sqlArgs.add(sfUser.getUserId());
//        sqlArgs.add(rentdto.getRental());
		
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
		RentDTO amsHouseInfo = (RentDTO)dtoParameter;
		if(foreignKey.equals("barcodeNo")){
			sqlModel = getDeleteByBarcodeNoModel(amsHouseInfo.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租赁房屋(EAM) AMS_HOUSE_INFO页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{             //查询用的sql
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			RentDTO rentDTO = (RentDTO) dtoParameter;
			String sqlStr = "SELECT 1 ROWNUM,\n" +
		        		 "   	 EOCM.COMPANY,\n"		+
		        		 "       EII.BARCODE,\n" +
		                "      ESI.ITEM_NAME,\n" +
		                "      ESI.ITEM_SPEC,\n" +
		                "      ESI.ITEM_UNIT,\n" +
		                
		                "      AM.MANUFACTURER_NAME,\n" +
		                "	   EII.POWER,\n" +
		                "	   EII.OTHER_INFO," +
		                "	   EII.CONTENT_CODE," +
		                "	   EII.CONTENT_NAME," +
		                "	   EII.RESPONSIBILITY_USER," +		                
		                "      DATEADD( DD , 731 ,EII.START_DATE ) END_RENT_DATE,\n" +
		                "       AME.USER_NAME,\n" +
		                "       dbo.APP_GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) OBJECT_NAME,\n" +
		                "       EII.MAINTAIN_USER,\n" +
		                "       SG.GROUP_NAME MAINTAIN_DEPT,\n" +
		                "       ARI.RENT_DATE,\n" +
		                "       ARI.END_DATE,\n" +
		                "       1 ITEM_QTY,\n" +
		                "       1 NOWQTY,\n" +
		                "       0 CYSL,\n" +
		                
//		                "       EII.MAINTAIN_DEPT,\n" +
		                "      ARI.RENT_PERSON,\n" +
		                "       ARI.TENANCY,\n" +
		                "       ARI.MONTH_REANTAL,\n" +
		                "       ARI.YEAR_RENTAL,\n" +		                
		                
		                "       AMD.DEPT_NAME GROUP_NAME,\n" +
		                "       EII.RESPONSIBILITY_DEPT,\n" +
		                "       ARI.REMARK,\n" +
		                "       ARI.RENT_ID,\n" +
		                
		                "       EII.SYSTEMID SYSTEM_ID\n" +
		                "  FROM ETS_ITEM_INFO    EII,\n" +
		                "       ETS_SYSTEM_ITEM  ESI,\n" +
		                "       AMS_RENT_INFO    ARI,\n" +
		                "       AMS_MIS_DEPT     AMD,\n" +
		                "       AMS_MIS_EMPLOYEE AME,\n" +
		                "	    AMS_MANUFACTURER AM,\n"	+
		                "	    ETS_OU_CITY_MAP  EOCM,\n" +	
		                "       SF_GROUP         SG\n" +
		                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
		                "   AND EII.BARCODE *= ARI.BARCODE  \n" +
		                "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE   \n" +
		                "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID  \n" +
		                "   AND EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
		                "   AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID " +
		                "   AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
		                "   AND EII.MAINTAIN_DEPT *= CONVERT(VARCHAR,SG.GROUP_ID) \n" +
		                "   AND ( " + SyBaseSQLUtil.nullIntParam() + " OR EII.ORGANIZATION_ID = ? )\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EII.BARCODE LIKE ?)\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR\n" +
		                "       dbo.APP_GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ?)\n" +
		                
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR SG.GROUP_NAME LIKE ?)\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR AMD.DEPT_NAME LIKE ? )\n" +
		                "   AND ( " + SyBaseSQLUtil.nullStringParam() + "  OR AME.USER_NAME LIKE ? )\n" +
		                "   AND ( " + SyBaseSQLUtil.nullSimpleCalendarParam() + "  OR ARI.RENT_DATE >= ?)\n" +
		                "   AND ( " + SyBaseSQLUtil.nullSimpleCalendarParam() + "  OR ARI.RENT_DATE <= ?)"	+
		                
		                "	AND ( " + SyBaseSQLUtil.nullStringParam() + " OR AMD.DEPT_NAME = ? )" +
		                "	AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EOCM.COMPANY = ? )";
		
	//          sqlArgs.add(rentDTO.getMaintainDeptName());
	//          sqlArgs.add(rentDTO.getMaintainDeptName());
			
			SyBaseSQLUtil.nullIntParamArgs(sqlArgs, rentDTO.getOrganizationId() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getBarcode() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getItemName() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getItemSpec() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getAddressloc() );
			
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getMaintainDeptName() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getResponsibilityDept() );
			SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getUsername() );
			SyBaseSQLUtil.nullSimpleCalendarParamArgs( sqlArgs, rentDTO.getFromDate() );
			SyBaseSQLUtil.nullSimpleCalendarParamArgs( sqlArgs, rentDTO.getToDate() );
//	          sqlArgs.add(rentDTO.getOrganizationId());
//	          sqlArgs.add(rentDTO.getBarcode());
//	          sqlArgs.add(rentDTO.getBarcode());
//	          sqlArgs.add(rentDTO.getItemName());
//	          sqlArgs.add(rentDTO.getItemName());
//	          sqlArgs.add(rentDTO.getItemSpec());
//	          sqlArgs.add(rentDTO.getItemSpec());
//	          sqlArgs.add(rentDTO.getAddressloc());
//	          sqlArgs.add(rentDTO.getAddressloc());
//	          sqlArgs.add(rentDTO.getMaintainDeptName());
//	          sqlArgs.add(rentDTO.getMaintainDeptName());
//	         //sqlArgs.add(rentDTO.getUsername());
//	          sqlArgs.add(rentDTO.getResponsibilityDept());
//	          sqlArgs.add(rentDTO.getUsername());
//	
//	          sqlArgs.add(rentDTO.getFromDate());
//	          sqlArgs.add(rentDTO.getFromDate());
//	          sqlArgs.add(rentDTO.getToDate());
//	          sqlArgs.add(rentDTO.getToDate());
	          
	          if(!rentDTO.getResponsibilityDeptName().equals("nothing")){
//	        	  sqlArgs.add(rentDTO.getResponsibilityDeptName()); 
	        	  SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getResponsibilityDeptName() );
	          } else {
	        	  SyBaseSQLUtil.nullStringParamArgs( sqlArgs, "" );
//	        	  sqlArgs.add("");
	          }
	          if(!rentDTO.getCompanyName().equals("nothing")){
	        	  SyBaseSQLUtil.nullStringParamArgs( sqlArgs, rentDTO.getCompanyName() );
//	        	  sqlArgs.add(rentDTO.getCompanyName()); 
	          } else {
	        	  SyBaseSQLUtil.nullStringParamArgs( sqlArgs, "" );
//	        	  sqlArgs.add("");
	          }
	          
             if(rentDTO.getToRentTime() != null && !"".equals(rentDTO.getToRentTime())){
            	 sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR MONTHS_BETWEEN(ARI.END_DATE, " + SyBaseSQLUtil.getCurDate() + " ) <=  ?) ORDER BY ARI.END_DATE";
            	 sqlArgs.add(rentDTO.getToRentTime());
   	          	 sqlArgs.add(rentDTO.getToRentTime());
             }else{
            	 sqlStr += " AND " + SyBaseSQLUtil.getCurDate() + "  >= ARI.END_DATE ORDER BY EOCM.COMPANY, EII.ITEM_CODE";
             }
             sqlModel.setSqlStr(sqlStr);
			 sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


    public SQLModel getupdataEIIModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentDTO = (RentDTO) dtoParameter;
        String sqlStr = " UPDATE" +
                " ETS_ITEM_INFO EII\n" +
                " SET" +
                " EII.ATTRIBUTE1 = 'RENT'\n" +
                " WHERE " +
                " EII.BARCODE = ?";
          sqlArgs.add(rentDTO.getBarcode());      
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


      public SQLModel insertDataNo() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        RentDTO rentDTO = (RentDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_ITEM_INFO\n" +
                "  (BARCODE,\n" +
                "   SYSTEMID,\n" +
                "   ITEM_CODE,\n" +
                "   CREATED_BY,\n" +
                "   ORGANIZATION_ID," +
                "   ADDRESS_ID," +
                "   RESPONSIBILITY_USER," +
                "   RESPONSIBILITY_DEPT," +
                "   MAINTAIN_DEPT," +
                "   ATTRIBUTE1 " +
                ")\n" +
                "   VALUES\n" +
                "  (?, NEWID() ,?,?,?,?,?,?,?,'RENT')";
        sqlArgs.add(rentDTO.getBarcode());
        sqlArgs.add(rentDTO.getItemCode());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(rentDTO.getAddressId());
        sqlArgs.add(rentDTO.getUserId());
//        sqlArgs.add(rentDTO.getResponsibilityDept());
        sqlArgs.add(rentDTO.getDeptId());
        sqlArgs.add(rentDTO.getMaintainDept());
//        sqlArgs.add(rentDTO.getAddressId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
