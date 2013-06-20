package com.sino.ams.print.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.print.dto.BarcodeReceiveDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
//import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.appbase.model.AMSSQLProducer;

public class BarcodeReceiveModel extends AMSSQLProducer {

	public BarcodeReceiveModel(BaseUserDTO userAccount, BarcodeReceiveDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 
	 * Function:		根据OU编号得到该OU下的所有部门
	 * @param organizationId	OU编号
	 * @return			SQLModel
	 */
	public SQLModel getDeptByOu(int organizationId){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AMD.DEPT_CODE, AMD.DEPT_NAME\n" +
                "  FROM AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "   	AND AMD.ENABLED = 'Y'" +
                "		AND EOCM.ORGANIZATION_ID = ?" + 
                " 		ORDER BY AMD.DEPT_CODE";      
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

	/**
	 * 
	 * Function:		查询所有标签领用记录分页数据
	 * @return			SQLModel   返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO) super.dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT ERCB.FROM_BARCODE, \n" +
						"		ERCB.TO_BARCODE, \n"	+
						"		ERCB.BARCODE_QTY, \n"	+
						"		ERCB.RECEIVE_DEPT, \n" +
						"		AMD.DEPT_NAME RECEIVE_DEPT_NNAME, \n"	+
						"		ERCB.RECEIVE_USER, \n"	+
						"		SU1.USERNAME RECEIVE_USER_NAME, \n"	+
						"		ERCB.RECEIVE_DATE, \n"	+
						"		ERCB.PRINT_USER, \n"	+
						"		SU2.USERNAME PRINT_USER_NAME, \n"	+
						"		ERCB.PRINT_DATE, \n"	+
						"		ERCB.ORGANIZATION_ID, \n"		+
						"		EOCM.COMPANY, \n" 	+
						"		ERCB.BARCODE_RECEIVE_ID, \n"	+
						"		ERCB.RECEIVE_REMARK, \n" +
						"       ERCBL.BARCODE \n" +
//						"		ERCB.BARCODE_PRINT_NUM "	+
						" FROM  ETS_ROLL_CALL_BARCODE ERCB, \n" +
						"		ETS_ROLL_CALL_BARCODE_L ERCBL, \n" +
						"		ETS_OU_CITY_MAP EOCM, \n" +
						"		AMS_MIS_DEPT AMD, \n" +
						"		SF_USER SU1, \n" +
						"		SF_USER SU2 \n" +
						" WHERE ERCB.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" +
						"       AND ERCB.BARCODE_RECEIVE_ID = ERCBL.BARCODE_RECEIVE_ID \n" +
						"		AND AMD.DEPT_CODE = ERCB.RECEIVE_DEPT \n"	+
						" 		AND SU1.USER_ID = ERCB.RECEIVE_USER \n" +
						" 		AND SU2.USER_ID =* ERCB.PRINT_USER \n" +
						" 		AND (? = -1 OR ERCB.ORGANIZATION_ID = ?) \n" +
						//"		AND ERCB.FROM_BARCODE LIKE dbo.NVL(?, ERCB.FROM_BARCODE)" +
						//"		AND ERCB.TO_BARCODE LIKE dbo.NVL(?, ERCB.TO_BARCODE)" +
						"		AND ERCBL.BARCODE >= dbo.NVL(?, ERCBL.BARCODE) \n" +
						"		AND ERCBL.BARCODE <= dbo.NVL(?, ERCBL.BARCODE) \n" +
						"		AND ERCB.RECEIVE_DEPT LIKE dbo.NVL(? ,ERCB.RECEIVE_DEPT) \n" +
						"		AND SU1.USERNAME LIKE dbo.NVL(? , SU1.USERNAME) \n" +	//领用人
						"		AND (? IS NULL OR ? = '' OR ERCB.RECEIVE_DATE >= ?) \n"	+
						"		AND (? IS NULL OR ? = '' OR ERCB.RECEIVE_DATE <= ?) \n" +
						"		AND SU2.USERNAME LIKE dbo.NVL(?, SU2.USERNAME) \n"  + //打印人
						"		AND (? IS NULL OR ? = '' OR ERCB.PRINT_DATE >= ?) \n" +
						"		AND (? IS NULL OR ? = '' OR ERCB.PRINT_DATE <= ?) \n" +
//						"		AND ERCB.RECEIVE_REMARK LIKE dbo.NVL(?, ERCB.RECEIVE_REMARK)";
						" ORDER BY ERCBL.BARCODE \n";
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getFromBarcode());
		sqlArgs.add(dto.getToBarcode());
		sqlArgs.add(dto.getReceiveDept());
		sqlArgs.add(dto.getReceiveUserName());
		sqlArgs.add(dto.getReceiveDateBegin());
		sqlArgs.add(dto.getReceiveDateBegin());
		sqlArgs.add(dto.getReceiveDateBegin());
		sqlArgs.add(dto.getReceiveDateEnd());
		sqlArgs.add(dto.getReceiveDateEnd());
		sqlArgs.add(dto.getReceiveDateEnd());
		sqlArgs.add(dto.getPrintUserName());
		sqlArgs.add(dto.getPrintDateBegin());
		sqlArgs.add(dto.getPrintDateBegin());
		sqlArgs.add(dto.getPrintDateBegin());
		sqlArgs.add(dto.getPrintDateEnd());
		sqlArgs.add(dto.getPrintDateEnd());
		sqlArgs.add(dto.getPrintDateEnd());
//		sqlArgs.add(dto.getReceiveRemark());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * Function			新增标签领用记录
	 * @return boolean	SQLModel
	 */	
	public SQLModel getDataCreateModel(){
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO) super.dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_ROLL_CALL_BARCODE"
								+	"(FROM_BARCODE,"
								+	"TO_BARCODE,"
								+	"BARCODE_QTY,"
								+	"RECEIVE_DEPT,"
								+	"RECEIVE_USER,"
								+	"RECEIVE_DATE,"
								+	"PRINT_USER,"
								+	"PRINT_DATE,"
								+	"ORGANIZATION_ID,"
								+	"RECEIVE_REMARK,"
//								+	"BARCODE_PRINT_NUM,"
								+	"BARCODE_RECEIVE_ID)"
								+	"VALUES"
								+	"(?, "    //起始标签
								+	"?, "	  //结束标签
								+	"?, "	//标签数量
								+	"?, "	//领用部门
								+	"?, "	//领用人
								+	"CASE ? WHEN ? THEN NULL ELSE GETDATE() END, " //领用日期
								+	"?, "	//打印人
								+	"CASE ? WHEN ? THEN NULL ELSE GETDATE() END, " //打印日期
								+	"?, "	//OU
								+	"?,"	//领用原因
//								+	"?,"	//标签打印次数
								+	"  NEWID() )";
		sqlArgs.add(dto.getFromBarcode() );
		sqlArgs.add(dto.getToBarcode());
		sqlArgs.add(dto.getBarcodeQty());
		sqlArgs.add(dto.getReceiveDept());
		sqlArgs.add(dto.getReceiveUser());
		sqlArgs.add(dto.getReceiveDate());
		sqlArgs.add(dto.getReceiveDate());
		sqlArgs.add(dto.getPrintUser());
		sqlArgs.add(dto.getPrintDate());
		sqlArgs.add(dto.getPrintDate());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getReceiveRemark());
//		sqlArgs.add(dto.getBarcodePrintNum());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：	通过框架自动根据SQLModel, 删除标签领用维护记录。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO) super.dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " ETS_ROLL_CALL_BARCODE"
			+ " WHERE"
			+ " 	BARCODE_RECEIVE_ID = ?";
		sqlArgs.add(dto.getBarcodeReceiveId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;		
	}
	
	/**
	 * Function:			得到选定的标签领用维护记录
	 * @return SQLModel 	删除用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO) super.dtoParameter;
		String sqlStr =  "SELECT ERCB.FROM_BARCODE," +
								"	ERCB.TO_BARCODE,"	+
								"	ERCB.BARCODE_QTY,"	+
								"	ERCB.RECEIVE_DEPT," +
								"	AMD.DEPT_NAME RECEIVE_DEPT_NNAME,"	+
								"	ERCB.RECEIVE_USER,"	+
								"	SU1.USERNAME RECEIVE_USER_NAME,"	+
								"	ERCB.RECEIVE_DATE,"	+
								"	ERCB.PRINT_USER,"	+
								"	SU2.USERNAME PRINT_USER_NAME,"	+
								"	ERCB.PRINT_DATE,"	+
								"	ERCB.ORGANIZATION_ID,"		+
								"	EOCM.COMPANY," 	+
								"	ERCB.BARCODE_RECEIVE_ID,"	+
								"	ERCB.RECEIVE_REMARK,"+
								"	ERCB.BARCODE_PRINT_NUM "	+
							" FROM  ETS_ROLL_CALL_BARCODE ERCB, ETS_OU_CITY_MAP EOCM, AMS_MIS_DEPT AMD, SF_USER SU1, SF_USER SU2" +
							" WHERE ERCB.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" +
							"		AND AMD.DEPT_CODE = ERCB.RECEIVE_DEPT"	+
							" 		AND SU1.USER_ID = ERCB.RECEIVE_USER" +
							" 		AND SU2.USER_ID = ERCB.PRINT_USER" +
							"		AND ERCB.BARCODE_RECEIVE_ID = ? ";	
		sqlArgs.add(dto.getBarcodeReceiveId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	

    /**
	 * Function：		修改指定标签领用记录。
	 * @return SQLModel	修改用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList(0);
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO)super.dtoParameter; 
		String sqlStr = "UPDATE ETS_ROLL_CALL_BARCODE"
								+ " SET"
								+ " FROM_BARCODE = ?,"
								+ " TO_BARCODE = ?,"
								+ " BARCODE_QTY = ?,"
								+ " RECEIVE_DEPT = dbo.NVL(?, RECEIVE_DEPT), "
								+ " RECEIVE_USER = ?,"
								+ " RECEIVE_DATE = ISNULL(?, RECEIVE_DATE) , "
								+ " BARCODE_PRINT_NUM = ?, "
								+ " PRINT_DATE = ISNULL(?, PRINT_DATE),"
								+ "	ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, ORGANIZATION_ID))),"
								+ " RECEIVE_REMARK = dbo.NVL(?, RECEIVE_REMARK)"
								+ " WHERE"
								+ "  	BARCODE_RECEIVE_ID= ?";
		
		sqlArgs.add(dto.getFromBarcode());
		sqlArgs.add(dto.getToBarcode());
		sqlArgs.add(dto.getBarcodeQty());
		sqlArgs.add(dto.getReceiveDept());
		sqlArgs.add(dto.getReceiveUser());
		sqlArgs.add(dto.getReceiveDate());
		sqlArgs.add(dto.getBarcodePrintNum());
		sqlArgs.add(dto.getPrintDate());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getReceiveRemark());
		sqlArgs.add(dto.getBarcodeReceiveId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 
	 * Function:		通过OU编号查找该OU对应的的公司编号
	 * @param organizationID		OU编号
	 * @return			SQLModel
	 */
	public SQLModel getCompanyCodeByOrganization(int organizationID){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList(0);
		BarcodeReceiveDTO dto = (BarcodeReceiveDTO)super.dtoParameter; 
		String sqlStr = " SELECT EOC.COMPANY_CODE " +
							"FROM ETS_OU_CITY_MAP EOC " +
							"WHERE EOC.ORGANIZATION_ID = ?";
		sqlArgs.add(organizationID);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_RECEIVE_BARCODE_IMPORT "
                + " WHERE"
                + " IMP_USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
	
    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        BarcodeReceiveDTO brDTO = (BarcodeReceiveDTO) dtoParameter;
        String sqlStr = 
        	    "INSERT INTO ETS_RECEIVE_BARCODE_IMPORT \n" +
                "  (BARCODE,\n" +
                "   ORGANIZATION,\n" +
                "   RECEIVE_DEPT,\n" +
                "   RECEIVE_USER,\n" +
                "   RECEIVE_DATE,\n" +
                "   PRINT_DATE,\n" +
                "   PRINT_USER,\n" +
                "   RECEIVE_REMARK,\n" +
                "   IMP_USER_ID,\n" +
                "   ERBI_ID) \n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NEWID()) \n";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel getImportErrorModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        	    "SELECT ERBI.IMP_ERROR, \n" +
        	    "		ERBI.BARCODE, \n" +
                "       ERBI.ORGANIZATION,\n" +
                "       ERBI.RECEIVE_DEPT,\n" +
                "       ERBI.RECEIVE_USER,\n" +
                //"       ERBI.RECEIVE_DATE,\n" +
                //"       ERBI.PRINT_DATE, \n" +
                "       ERBI.PRINT_USER, \n" +
                "       ERBI.RECEIVE_REMARK \n" +
                "FROM   ETS_RECEIVE_BARCODE_IMPORT ERBI \n" +
                "WHERE  ERBI.IMP_USER_ID = ? \n" +
                "AND    LTRIM(ERBI.IMP_ERROR) IS NOT NULL \n" +
                "ORDER  BY ERBI.BARCODE \n";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
}
