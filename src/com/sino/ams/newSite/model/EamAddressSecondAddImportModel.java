package com.sino.ams.newSite.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 18, 2011 3:46:55 PM
 *          类说明:地点信息导入
 */
public class EamAddressSecondAddImportModel extends AMSSQLProducer {

	public EamAddressSecondAddImportModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }
	
	/**
     * 功能：删除接口表的数据。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel deleteImportModel() {
        SQLModel sqlModel = new SQLModel();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        List<String> sqlArgs = new ArrayList<String>();
        String sqlStr = "DELETE FROM"
                + " EAM_ADDRESS_ADD_L"
                + " WHERE"
                + " TRANS_ID = ?";

        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：插入到接口表。 EAM_ADDRESS_ADD_L
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        String sqlStr =
                "INSERT INTO EAM_ADDRESS_ADD_L\n" +
                        "  (LINE_ID,\n" +
                        "   TRANS_ID,\n" +
                        "   WORKORDER_OBJECT_CODE,\n" +
                        "   WORKORDER_OBJECT_NAME,\n" +
                        "   OBJECT_CATEGORY,\n" +
                        "   COUNTY_CODE,\n" +
                        "   AREA_TYPE,\n" +
                        "   CITY,\n" +
                        "   COUNTY,\n" +
                        "   REMARK,\n" +
                        "   ADDR_MAINTAIN_TYPE,\n" +
                        "	BTS_NO,\n" +
                        "   ORGANIZATION_ID," +
                        "	SHARE_TYPE) VALUES\n" +
                        "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?)";

        sqlArgs.add(dto.getLineId());
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getObjectCategory());
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getAreaType());
        sqlArgs.add(dto.getCity());
        sqlArgs.add(dto.getCounty());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getAddrMaintainType());
        sqlArgs.add(dto.getBtsNo());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getShareType());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取更新行数据检查信息的SQL
     *
     * @return 获取更新行数据检查信息的SQL
     */
    public SQLModel getImportErrorLogModel() {
        SQLModel sqlModel = new SQLModel();
        List<String> sqlArgs = new ArrayList<String>();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "UPDATE EAM_ADDRESS_ADD_L SET ERROR_MESSAGE = ? WHERE LINE_ID = ?";
        sqlArgs.add(lineDTO.getErrorMessage());
        sqlArgs.add(lineDTO.getLineId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取行政区划代码和描述的SQL
     *
     * @return 获取行政区划代码和描述的SQL
     */
    public SQLModel getAreaTypeExistModel() {
        SQLModel sqlModel = new SQLModel();
        List<String> sqlArgs = new ArrayList<String>();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT EFV.CODE, " +
                "EFV.VALUE FROM \n" +
                "ETS_FLEX_VALUES EFV, " +
                "ETS_FLEX_VALUE_SET EFVS " +
                "WHERE " +
                "EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID" +
                " AND EFV.ENABLED = 'Y' " +
                "AND EFVS.CODE='ADDR_AREA_TYPE' " +
                "AND EFV.CODE = ?";
        sqlArgs.add(lineDTO.getAreaType());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
    
    /**
     * 功能：获取地点专业是否存在的SQL
     *
     * @return SQLModel  获取地点专业是否存在的SQL
     */
    public SQLModel getObjectCategoryExistModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " 1"
                + " FROM"
                + " ETS_FLEX_VALUES EFV,"
                + " ETS_FLEX_VALUE_SET EFVS"
                + " WHERE"
                + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = 'OBJECT_CATEGORY'"
                + " AND EFV.ATTRIBUTE2=?";
        sqlArgs.add(lineDTO.getObjectCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    

    /**
     * 功能：获取表EAM_ADDRESS_ADD_L中本单据下数据非法记录的SQL
     *
     * @param transId 流程单据ID
     * @return 获取表EAM_ADDRESS_ADD_L中本单据下数据非法记录的SQL
     */
    public SQLModel getImportErrorModel(String transId) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EAM.LINE_ID,\n" +
                        "       EAM.TRANS_ID,\n" +
                        "       EAM.WORKORDER_OBJECT_CODE,\n" +
                        "       EAM.WORKORDER_OBJECT_NAME,\n" +
                        "       EAM.OBJECT_CATEGORY,\n" +
                        "       EAM.COUNTY_CODE,\n" +
                        "       EAM.AREA_TYPE,\n" +
                        "       EAM.CITY,\n" +
                        "       EAM.COUNTY,\n" +
                        "       EAM.ORGANIZATION_ID,\n" +
                        "		EAM.BTS_NO,\n" +
                        "       EAM.ERROR_MESSAGE,\n" +
                        "		EAM.SHARE_TYPE\n" +
                        "  FROM EAM_ADDRESS_ADD_L EAM\n" +
                        " WHERE EAM.TRANS_ID = ?\n" +
                        "   AND  " + SyBaseSQLUtil.isNotNull("EAM.ERROR_MESSAGE") + " ";

        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：获取表EAM_ADDRESS_ADD_L中本单据下数据合法记录的SQL
     *
     * @param transId 流程单据ID
     * @return 获取表EAM_ADDRESS_ADD_L中本单据下数据非法记录的SQL
     */
    public SQLModel getQueryImportModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EAM.LINE_ID,\n" +
                        "       EAM.TRANS_ID,\n" +
                        "       EAM.WORKORDER_OBJECT_CODE,\n" +
                        "       EAM.WORKORDER_OBJECT_NAME,\n" +
                        "       EAM.OBJECT_CATEGORY,\n" +
                        "       EAM.COUNTY_CODE,\n" +
                        "       EAM.AREA_TYPE,\n" +
                        "       EAM.CITY,\n" +
                        "       EAM.COUNTY,\n" +
                        "       EAM.ORGANIZATION_ID,\n" +
                        "		EAM.BTS_NO,\n" +
                        "       EAM.ERROR_MESSAGE,\n" +
                        "		EAM.SHARE_TYPE\n" +
                        "  FROM EAM_ADDRESS_ADD_L EAM\n" +
                        " WHERE EAM.TRANS_ID = ?\n" +
                        "   AND EAM.ERROR_MESSAGE " + SyBaseSQLUtil.isNullNoParam() + " ";

        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：构造地点是否存在的SQL
     *
     * @return SQLModel  构造地点是否存在的SQL
     */
    public SQLModel getObjectCodeExistModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_OBJECT_LOC2      EOL\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOL.LOC2_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(lineDTO.getWorkorderObjectCode());
//        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造根据地点编码获取OU组织ID的SQL
     *
     * @return 构造根据地点编码获取OU组织ID的SQL
     */
    public SQLModel getObjectCodeOrganizationIdModel() {
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ORGANBIZATION_ID\n" +
                "  FROM ETS_OBJECT      EO\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(lineDTO.getWorkorderObjectCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造检查地市是否存在的的SQL
     *
     * @return 构造检查地市是否存在的的SQL
     */
    public SQLModel getCityExistModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;

        String sqlStr = "SELECT 1\n" +
                "FROM   AMS_COUNTY AC\n" +
                "WHERE  AC.COUNTY_NAME = ?\n" +
                "       AND (AC.PARENT_CODE IS NULL OR AC.PARENT_CODE = '')";
        sqlArgs.add(lineDTO.getCity());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：构造检查区县是否存在的的SQL
     *
     * @return 构造检查区县是否存在的的SQL
     */
    public SQLModel getCountyExistModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;

        String sqlStr = "SELECT 1\n" +
                "FROM   AMS_COUNTY AC,\n" +
                "       AMS_COUNTY ACP\n" +
                "WHERE  AC.PARENT_CODE = ACP.COUNTY_CODE\n" +
                "       AND AC.COUNTY_NAME = ?\n" +
                "       AND ACP.COUNTY_NAME = ?\n" +
                "       AND (ACP.PARENT_CODE IS NULL OR ACP.PARENT_CODE = '')";
        sqlArgs.add(lineDTO.getCounty());
        sqlArgs.add(lineDTO.getCity());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    /**
     * 功能：构造判断基站或营业厅编号是否存在的SQL
     *
     * @return SQLModel 构造判断基站或营业厅编号是否存在的SQL
     */
    public SQLModel getObjectCodeByBTSNoModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT EOL.LOC2_CODE\n" +
                "FROM   ETS_OBJECT_LOC2   EOL,\n" +
                "       EAM_ADDRESS_ADD_L EAAL\n" +
                "WHERE  EOL.BTS_NO = EAAL.BTS_NO\n" +
                "       AND EOL.COMPANY_CODE = ?\n" +
                "       AND EAAL.LINE_ID = ?";
        sqlArgs.add(userAccount.getCompanyCode());
        sqlArgs.add(lineDTO.getLineId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：
     *
     * @return
     */
    public SQLModel getCountyCodeByAreaCodeModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT TOP 1 COUNTY_CODE FROM ETS_COUNTY WHERE COUNTY_CODE_MIS=?";
        sqlArgs.add(lineDTO.getCountyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取地点下是否有资产的SQL
     *
     * @return 获取地点下是否有资产的SQL
     */
    public SQLModel getLocationAssetsModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "FROM   ETS_OBJECT        EO,\n" +
                "       EAM_ADDRESS_ADD_L EAAL\n" +
                "WHERE  EO.ORGANIZATION_ID = ?\n" +
                "       AND EO.LOC2_CODE = EAAL.WORKORDER_OBJECT_CODE\n" +
                "       AND EAAL.LINE_ID = ?\n" +
                "       AND EXISTS (SELECT NULL\n" +
                "        FROM   AMS_OBJECT_ADDRESS AOA,\n" +
                "               ETS_ITEM_INFO      EII\n" +
                "        WHERE  EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "               AND AOA.ADDRESS_ID = EII.ADDRESS_ID)";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getLineId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：校验导入地点第二段描述是否存在
     * @return
     */
    public SQLModel getIsExistWorkorderObjectNameModel() {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " 1"
                + " FROM"
                + " ETS_OBJECT_LOC2"
                + " WHERE LOC2_DESC = ?";
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：根据物理地点代码获取物理地点描述
     * @return
     */
    public SQLModel getLoc2DescByLoc2CodeModel() {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " LOC2_DESC"
                + " FROM"
                + " ETS_OBJECT_LOC2"
                + " WHERE LOC2_CODE = ?";
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    
    /**
     * 功能：校验导入地点第二段描述是否存在单据中，单据状态为暂存、处理中
     * @return
     */
    public SQLModel getIsObjectNameInOrderModel() {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO dto = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = 
        	"SELECT 1\n" +
        	"  FROM EAM_ADDRESS_ADD_L EAAL, EAM_ADDRESS_ADD_H EAAH\n" + 
        	" WHERE EAAL.TRANS_ID = EAAH.TRANS_ID\n" + 
        	"   AND EAAL.WORKORDER_OBJECT_NAME = ?\n" + 
        	"   AND EAAH.TRANS_STATUS IN ('IN_PROCESS', 'SAVE_TEMP')" +
        	"	AND EAAH.TRANS_ID != ?";

        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
	 * 功能：构造判断基站或营业厅编号是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getBtsNoEsistModel(String btsNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OBJECT_LOC2"
						+ " WHERE"
						+ " BTS_NO = ?" ;
		sqlArgs.add(btsNo);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
