package com.sino.ams.newSite.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.config.SinoConfig;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.MIS_CONSTANT;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 18, 2011 3:46:55 PM
 *          类说明:地点信息导入
 */
public class EamAddressAddImportModel extends AMSSQLProducer {

    public EamAddressAddImportModel(BaseUserDTO userAccount, DTO dtoParameter) {
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

    @SuppressWarnings("unchecked")
    /**
     * 功能：插入到接口表。 EAM_ADDRESS_ADD_L
     *
     * @return SQLModel 返回数据插入用SQLModel
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
                        "   ORGANIZATION_ID) VALUES\n" +
                        "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?)";

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
                        "		 EAM.BTS_NO,\n" +
                        "       EAM.ERROR_MESSAGE\n" +
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
                        "		 EAM.BTS_NO,\n" +
                        "       EAM.ERROR_MESSAGE\n" +
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
                "  FROM ETS_OBJECT      EO\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(lineDTO.getWorkorderObjectCode());
//        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：构造地点是否存在单据中SQL，单据状态为暂存、处理中
     *
     * @return SQLModel  构造地点是否存在的SQL
     */
    public SQLModel getObjectCodeExistInOrderModel(String str) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = 
        	"SELECT 1\n" +
        	"  FROM EAM_ADDRESS_ADD_L EAAL, EAM_ADDRESS_ADD_H EAAH\n" + 
        	" WHERE EAAL.TRANS_ID = EAAH.TRANS_ID\n" + 
        	"	AND len(EAAL." + str + ")-charindex('.',reverse(EAAL." + str + "))-charindex('.',EAAL." + str + ") != -1 \n" +
        	"   AND (EAAL." + str + " = ? " +
        	"   OR SUBSTRING(EAAL." + str + ",charindex('.',EAAL." + str + ")+1," +
        	"	len(EAAL." + str + ")-charindex('.',reverse(EAAL." + str + "))-charindex('.',EAAL." + str + ")) = " +
        	"	SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)))\n" + 
        	"   AND EAAH.TRANS_STATUS IN ('IN_PROCESS', 'SAVE_TEMP')" +
        	"	AND EAAH.TRANS_ID != ?\n";
        
        if (str.equals("WORKORDER_OBJECT_CODE")) {
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        	sqlArgs.add(lineDTO.getWorkorderObjectCode());
        } else {
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        	sqlArgs.add(lineDTO.getWorkorderObjectName());
        }
        sqlArgs.add(lineDTO.getTransId());
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
        String sqlStr = "SELECT EO.ORGANBIZATION_ID\n" +
                "  FROM ETS_OBJECT      EO\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?\n";//+
//                "       AND EOCM.COMPANY_CODE = ?";
        sqlArgs.add(lineDTO.getWorkorderObjectCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造检查区域代码是否存在的的SQL
     *
     * @return 构造检查区域代码是否存在的的SQL
     */
    public SQLModel getAreaCodeExistModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String sqlStr = "";
        if (SinoConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN) || userAccount.getIsTt().equals("Y")) {//山西移动获取区域代码时与OU无关
            sqlStr = "SELECT FFV.FLEX_VALUE, " +
                    "FFV.DESCRIPTION \n" +
                    "  FROM M_FND_FLEX_VALUE_SETS FFVS, \n" +
                    "       M_FND_FLEX_VALUES     FFV \n" +
                    " WHERE FFVS.FLEX_VALUE_SET_ID = FFV.FLEX_VALUE_SET_ID \n" +
                    "   AND FFVS.FLEX_VALUE_SET_NAME = ? \n" +
                    "	AND FFVS.SOURCE = ? \n" +
                    "   AND FFV.ENABLED_FLAG = 'Y' \n" +
                    "   AND FFV.FLEX_VALUE = ? \n";
        } else {
            sqlStr = "SELECT FFV.FLEX_VALUE, " +
                    "FFV.DESCRIPTION \n" +
                    "  FROM M_FND_FLEX_VALUE_SETS FFVS, \n" +
                    "       M_FND_FLEX_VALUES     FFV, \n" +
                    "       ETS_OU_CITY_MAP       EOCM \n" +
                    " WHERE FFVS.FLEX_VALUE_SET_ID = FFV.FLEX_VALUE_SET_ID \n" +
                    "   AND FFVS.FLEX_VALUE_SET_NAME = ? \n" +
                    "	AND FFVS.SOURCE = ? \n" +
                    "   AND FFV.COMPANY_CODE = EOCM.COMPANY_CODE \n" +
                    "   AND FFV.ENABLED_FLAG = 'Y' \n" +
                    "   AND FFV.FLEX_VALUE = ? \n";
        }

        if (userAccount.getIsTd().equals("Y") || userAccount.getIsTt().equals("Y")) {
            sqlArgs.add(SinoConfig.getLoc1SetNameTD());
            sqlArgs.add(MIS_CONSTANT.SOURCE_TD);
        } else {
            sqlArgs.add(SinoConfig.getLoc1SetNameMis());
            sqlArgs.add(MIS_CONSTANT.SOURCE_MIS);
        }
        sqlArgs.add(lineDTO.getCountyCode());
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
     * 功能：构造根据地点名称第二段描述获取地点第二段代码的SQL
     *
     * @return 构造根据地点名称第二段描述获取地点第二段代码的SQL
     */
    public SQLModel getLocCode2ByDescModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String objectName = lineDTO.getWorkorderObjectName();
        int startIndex = objectName.indexOf(".");
        int endIndex = objectName.lastIndexOf(".");
        objectName = objectName.substring(startIndex + 1, endIndex);

        String sqlStr = "SELECT EBL.LOC2_CODE\n" +
                "FROM   ETS_OBJECT_LOC2 EBL\n" +
                "WHERE  EBL.LOC2_DESC = ?\n";
//                "       AND EBL.COMPANY_CODE = ?";
        sqlArgs.add(objectName);
//        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：构造根据地点代码第二段代码获取地点第二段描述的SQL
     *
     * @return 
     */
    public SQLModel getLocDesc2ByDescModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoParameter;
        String objectCode = lineDTO.getWorkorderObjectCode();
        int startIndex = objectCode.indexOf(".");
        int endIndex = objectCode.lastIndexOf(".");
        objectCode = objectCode.substring(startIndex + 1, endIndex);

        String sqlStr = "SELECT EBL.LOC2_DESC\n" +
                "FROM   ETS_OBJECT_LOC2 EBL\n" +
                "WHERE  EBL.LOC2_CODE = ?\n";
//                "       AND EBL.COMPANY_CODE = ?";
        sqlArgs.add(objectCode);
//        sqlArgs.add(userAccount.getCompanyCode());
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
                "       AND EO.WORKORDER_OBJECT_CODE = EAAL.WORKORDER_OBJECT_CODE\n" +
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
}
