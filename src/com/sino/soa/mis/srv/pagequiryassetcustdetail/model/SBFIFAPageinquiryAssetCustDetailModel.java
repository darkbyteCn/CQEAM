package com.sino.soa.mis.srv.pagequiryassetcustdetail.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 21:14:12
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAPageinquiryAssetCustDetailModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBFIFAPageinquiryAssetCustDetailModel(SfUserDTO userAccount, SBFIFAPageinquiryAssetCustDetailDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

    public SQLModel getEcouInforModel(String projectAssetId, String taskId, String projectId) {
    	int projectAssetIdInt = Integer.parseInt( projectAssetId );
    	int taskIdInt = Integer.parseInt( taskId );
    	int projectIdInt = Integer.parseInt( projectId );
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM ZTE_FA_CUST_DETAIL ZFCD WHERE (? = -1 OR ZFCD.PROJECT_ASSET_ID = ? ) AND (? = -1 OR ZFCD.TASK_ID = ? ) AND  (? = -1 OR ZFCD.PROJECT_ID = ? )";
		sqlArgs.add(projectAssetIdInt);
		sqlArgs.add(projectAssetIdInt);
		sqlArgs.add(taskIdInt);
		sqlArgs.add(taskIdInt);
		sqlArgs.add(projectIdInt);
		sqlArgs.add(projectIdInt);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataImpDeleteModel(String projectNum) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM ETS_FA_CUST_DETAIL_IMP WHERE CREATE_USER_ID = ? AND PROJRCT_NUMBER= ? ";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(projectNum);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataLogDeleteModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM ETS_AUTO_SYN_LOG WHERE SYN_TYPE = 'SYN_CUST_DETAIL' AND CREATED_BY = ?";
        sqlArgs.add(sfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAPageinquiryAssetCustDetailDTO srvAssetCategory = (SBFIFAPageinquiryAssetCustDetailDTO)dtoParameter;
        String sqlStr = "INSERT INTO "
			+ " ETS_FA_CUST_DETAIL_IMP("
			+ " TAG_NUMBER,"
			+ " ASSET_NAME,"
            + " ASSET_DESCRIPTION,"
            + " MODEL_NUMBER,"
            + " ASSET_CATEGORY,"
            + " ASSET_CATEGORY_DESC,"
            + " UNIT_OF_MEASURE,"
            + " EMPLOYEE_NUMBER,"
            + " LOCATION_CODE,"
            + " ASSET_LOCATION,"
            + " MANUFACTORER_NAME,"
            + " ATTRIBUTE4,"
            + " ATTRIBUTE5,"
            + " ATTRIBUTE6,"
            + " ATTRIBUTE7,"
            + " BOOK_TYPE_CODE,"
            + " PROJRCT_NUMBER,"
            + " ASSET_UNITS,"   //
            + " TASK_ID,"
            + " PROJECT_ID,"
            + " PROJECT_ASSET_ID,"
            + " CREATE_USER_ID,"
            + " CREATION_DATE,"
            + " START_DATE , "   //
            + " CUST_ID "
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), ?, GETDATE(), ? , NEWID())";
		sqlArgs.add(srvAssetCategory.getTagNumber());
        sqlArgs.add(srvAssetCategory.getAssetName());
		sqlArgs.add(srvAssetCategory.getAssetDescription());
        sqlArgs.add(srvAssetCategory.getModelNumber());
        sqlArgs.add(srvAssetCategory.getAssetCategory());
        sqlArgs.add(srvAssetCategory.getAssetCategoryDesc());
        sqlArgs.add(srvAssetCategory.getUnitOfMeasure());
        sqlArgs.add(srvAssetCategory.getEmployeeNumber());
        sqlArgs.add(srvAssetCategory.getLocationCode());
        sqlArgs.add(srvAssetCategory.getAssetLocation());
        sqlArgs.add(srvAssetCategory.getManufactorerName());
        sqlArgs.add(srvAssetCategory.getAttribute4());
        sqlArgs.add(srvAssetCategory.getAttribute5());
        sqlArgs.add(srvAssetCategory.getAttribute6());
        sqlArgs.add(srvAssetCategory.getAttribute7());
        sqlArgs.add(srvAssetCategory.getBookTypeCode());
		sqlArgs.add(srvAssetCategory.getProjectNumber());
        sqlArgs.add(srvAssetCategory.getAssetUnits());     //
        sqlArgs.add(srvAssetCategory.getTaskId());
        sqlArgs.add(srvAssetCategory.getProjectId());
        sqlArgs.add(srvAssetCategory.getProjectAssetId());
        sqlArgs.add(sfUser.getUserId());
        //增加start_date 
        sqlArgs.add( getHandleDateFromERP( srvAssetCategory.getDatePlacedInService() ) );   //
        
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 处理从ERP过来的这种格式“2011-08-15 00:00:00.000+08:00”时间 
	 * @param date
	 * @return
	 */
	private String getHandleDateFromERP( String date ){
		if( StrUtil.isEmpty( date ) || date.length() < 11 ){
			return date;
		}
		date = date.replace( "T" , " " );
		int pos = date.indexOf( "+" );
		if( pos > - 1 ){
			date = date.substring( 0 , pos );
		}
		return date;
	}
	
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIFAPageinquiryAssetCustDetailDTO srvAssetCategory = (SBFIFAPageinquiryAssetCustDetailDTO)dtoParameter;
		String sqlStr = "UPDATE ZTE_FA_CUST_DETAIL"
                    + " SET"
                    + " BOOK_TYPE_CODE = ?,"
                    + " PROJECT_NUMBER = ?,"
                    + " TASK_NUMBER = ?,"
                    + " TASK_NAME = ?,"
                    + " PROJECT_TYPE = ?,"
                    + " TAG_NUMBER = ?,"
                    + " ASSET_NAME = ?,"
                    + " ASSET_DESCRIPTION = ?,"
                    + " ASSET_NUMBER = ?,"
                    + " ASSET_CATEGORY = ?,"
                    + " ASSET_CATEGORY_DESC = ?,"
                    + " ASSET_LOCATION = ?,"
                    + " ASSET_UNITS = CONVERT(FLOAT, ?),"
                    + " UNIT_OF_MEASURE = ?,"
                    + " CAPITALIZED_COST = CONVERT(FLOAT, ?),"
                    + " CAPITALIZED_DATE = ?,"
                    + " ASSET_KEY_CCID_DESC = ?,"
                    + " ASSET_EMPLOYEE = CONVERT(FLOAT, ?),"
                    + " EMPLOYEE_NAME = ?,"
                    + " EMPLOYEE_NUMBER = ?,"
                    + " DEPRECIATION_EXPENSE_CCID = CONVERT(FLOAT, ?),"
                    + " MODEL_NUMBER = ?,"
                    + " MANUFACTURER_NAME = ?,"
                    + " DATE_PLACED_IN_SERVICE = ?,"
                    + " FA_PERIOD_NAME = ?,"
                    + " LOCATION_ID = CONVERT(FLOAT, ?),"
                    + " LOCATION_CODE = ?,"
                    + " TASK_ID = CONVERT(FLOAT, ?),"
                    + " PROJECT_ID = CONVERT(FLOAT, ?),"
                    + " PROJECT_ASSET_ID = CONVERT(FLOAT, ?),"
                    + " ATTRIBUTE1 = ?,"
                    + " ATTRIBUTE2 = ?,"
                    + " ATTRIBUTE3 = ?,"
                    + " ATTRIBUTE4 = ?,"
                    + " ATTRIBUTE5 = ?,"
                    + " ATTRIBUTE6 = ?,"
                    + " ATTRIBUTE7 = ?"
                    + " WHERE PROJECT_ASSET_ID = ?"
                    + " AND TASK_ID = ?"
                    + " AND PROJECT_ID = ?";
        sqlArgs.add(srvAssetCategory.getBookTypeCode());
		sqlArgs.add(srvAssetCategory.getProjectNumber());
		sqlArgs.add(srvAssetCategory.getTaskNumber());
		sqlArgs.add(srvAssetCategory.getTaskName());
		sqlArgs.add(srvAssetCategory.getProjectType());
		sqlArgs.add(srvAssetCategory.getTagNumber());
        sqlArgs.add(srvAssetCategory.getAssetName());
		sqlArgs.add(srvAssetCategory.getAssetDescription());
        sqlArgs.add(srvAssetCategory.getAssetNumber());
		sqlArgs.add(srvAssetCategory.getAssetCategory());
        sqlArgs.add(srvAssetCategory.getAssetCategoryDesc());
		sqlArgs.add(srvAssetCategory.getAssetLocation());
        sqlArgs.add(srvAssetCategory.getAssetUnits());
		sqlArgs.add(srvAssetCategory.getUnitOfMeasure());
        sqlArgs.add(srvAssetCategory.getCapitalizedCost());
        sqlArgs.add(srvAssetCategory.getCapitalizedDate());
		sqlArgs.add(srvAssetCategory.getAssetKeyCcidDesc());
        sqlArgs.add(srvAssetCategory.getAssetEmployee());
        sqlArgs.add(srvAssetCategory.getEmployeeName());
		sqlArgs.add(srvAssetCategory.getEmployeeNumber());
        sqlArgs.add(srvAssetCategory.getDepreciationExpenseCcid());
        sqlArgs.add(srvAssetCategory.getModelNumber());
        sqlArgs.add(srvAssetCategory.getManufactorerName());
        sqlArgs.add(srvAssetCategory.getDatePlacedInService());
        sqlArgs.add(srvAssetCategory.getFaPeriodName());
        sqlArgs.add(srvAssetCategory.getLocationId());
        sqlArgs.add(srvAssetCategory.getLocationCode());
        sqlArgs.add(srvAssetCategory.getTaskId());
        sqlArgs.add(srvAssetCategory.getProjectId());
        sqlArgs.add(srvAssetCategory.getProjectAssetId());
        sqlArgs.add(srvAssetCategory.getAttribute1());
        sqlArgs.add(srvAssetCategory.getAttribute2());
        sqlArgs.add(srvAssetCategory.getAttribute3());
        sqlArgs.add(srvAssetCategory.getAttribute4());
        sqlArgs.add(srvAssetCategory.getAttribute5());
        sqlArgs.add(srvAssetCategory.getAttribute6());
        sqlArgs.add(srvAssetCategory.getAttribute7());
        sqlArgs.add(srvAssetCategory.getProjectAssetId());
        sqlArgs.add(srvAssetCategory.getTaskId());
        sqlArgs.add(srvAssetCategory.getProjectId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SBFIFAPageinquiryAssetCustDetailDTO srvAssetCategory = (SBFIFAPageinquiryAssetCustDetailDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " CATEGORY_ID,"
            + " DESCRIPTION,"
            + " CATEGORY_TYPE,"
            + " SEGMENT1,"
            + " SEGMENT2,"
            + " SEGMENT3,"
            + " ASSET_COST_ACCOUNT_CCID,"
            + " RESERVE_ACCOUNT_CCID,"
            + " ASSET_CLEARING_ACCOUNT_CCID,"
            + " LIFE_IN_MONTHS,"
            + " PERCENT_SALVAGE_VALUE,"
            + " ENABLED_FLAG,"
            + " ATTRIBUTE1,"
            + " INVENTORIAL,"
            + " CAPITALIZE_FLAG,"
            + " BOOK_TYPE_CODE,"
            + " LAST_UPDATE_DATE"
            + " FROM"
            + " SRV_ASSET_CATEGORY"
            + " WHERE"
            + " (? IS NULL OR CATEGORY_ID LIKE ?)"
            + " AND (? IS NULL OR DESCRIPTION LIKE ?)"
            + " AND (? IS NULL OR CATEGORY_TYPE LIKE ?)"
            + " AND (? IS NULL OR SEGMENT1 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT2 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT3 LIKE ?)"
            + " AND (? IS NULL OR ASSET_COST_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR RESERVE_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR ASSET_CLEARING_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR LIFE_IN_MONTHS LIKE ?)"
            + " AND (? IS NULL OR PERCENT_SALVAGE_VALUE LIKE ?)"
            + " AND (? IS NULL OR ENABLED_FLAG LIKE ?)"
            + " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
            + " AND (? IS NULL OR INVENTORIAL LIKE ?)"
            + " AND (? IS NULL OR CAPITALIZE_FLAG LIKE ?)"
            + " AND (? IS NULL OR BOOK_TYPE_CODE LIKE ?)"
            + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)";
//        sqlArgs.add(srvAssetCategory.getBookTypeCode());
//        sqlArgs.add(srvAssetCategory.getBookTypeCode());
//        sqlArgs.add(srvAssetCategory.getLastUpdateDate());
//        sqlArgs.add(srvAssetCategory.getLastUpdateDate());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
     * 统计同步数量
     * @return
     */
    public SQLModel getSyncTotalCountModel(String projectNum) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) SYNC_TOTAL_COUNT \n" +
                "FROM ETS_FA_CUST_DETAIL_IMP EFCDI \n" +
                "WHERE EFCDI.CREATE_USER_ID = ? \n" +
                "AND PROJRCT_NUMBER= ? \n" +
                "AND EFCDI.TAG_NUMBER IS NOT NULL\n" +
                "AND NOT EXISTS \n" +
                "          (SELECT NULL\n" +
                "             FROM ETS_ITEM_INFO EII\n" +
                "            WHERE EFCDI.TAG_NUMBER = EII.BARCODE)";
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(projectNum);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getSyncErrorModel() {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) SYNC_ERROR_COUNT FROM ETS_AUTO_SYN_LOG EASL WHERE EASL.SYN_TYPE = 'SYN_CUST_DETAIL' AND EASL.CREATED_BY = ?";
		sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getErrorRowModel() {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EASL.SYN_TYPE,\n" +
                              " EASL.SYN_DATE,\n" +
                              " EASL.SYN_MSG\n" +
                         " FROM ETS_AUTO_SYN_LOG EASL\n" +
                        " WHERE EASL.SYN_TYPE = 'SYN_CUST_DETAIL'\n" +
                          " AND CONVERT(INT, CONVERT(CHAR, EASL.SYN_DATE, 112)) = CONVERT(INT, CONVERT(CHAR, GETDATE(), 112))\n" +
                          " AND EASL.CREATED_BY = ?";
		sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    
    public static void main(String[] args) {
    	SBFIFAPageinquiryAssetCustDetailModel model = new SBFIFAPageinquiryAssetCustDetailModel( null , null );
    	String str = model.getHandleDateFromERP( "2011-08-15T00:00:00.000+08:00" );
    	System.out.println( str );
    	str = "2011-08-15";
    	str = model.getHandleDateFromERP( str );
    	System.out.println( str );
    	str = "2011-08-15 00:00:00";
    	str = model.getHandleDateFromERP( str );
    	System.out.println( str );
    	str = "2011-08-15 00:00:00.000";
    	str = model.getHandleDateFromERP( str );
    	System.out.println( str );
	}
}