package com.sino.ams.newSite.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 1:42:34 PM
 *          类说明:新增地点流程 表单头信息 Model
 */
public class EamAddressSecondAddHModel extends AMSSQLProducer {

    private SfUserDTO user = null;

    public EamAddressSecondAddHModel(SfUserDTO userAccount, EamAddressAddHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.user = (SfUserDTO) userAccount;
    }
    
    public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr =	" SELECT EAT.TRANS_ID,\n" +
						"       EAT.TRANS_NO,\n" + 
						"       dbo.APP_GET_FLEX_VALUE(EAT.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,\n" + 
						"       dbo.APP_GET_ORGNIZATION_NAME(EAT.ORGANIZATION_ID) ORGANIZATION_NAME,\n" + 
						"		dbo.APP_GET_DEPT_NAME(EAT.DEPT) DEPT_NAME,\n" +
						"       EAT.CREATION_DATE,\n" + 
						"       SU.USERNAME CREATED_BY_NAME,\n" +
						"		EAT.EMERGENT_LEVEL" +
						"  FROM EAM_ADDRESS_ADD_H EAT,\n" + 
						"       SF_USER           SU\n" + 
						" WHERE EAT.CREATED_BY = SU.USER_ID\n" + 
						"	AND EAT.IS_SECOND = 'Y' \n" +
						"	AND EAT.ORGANIZATION_ID = ? \n" +
						"	AND EAT.CREATED_BY = ? \n" + 
						"	AND ( ? = '' OR EAT.TRANS_NO LIKE dbo.NVL(?, EAT.TRANS_NO)) " +
						" 	AND ( ? = '' OR EAT.CREATION_DATE >= ISNULL(?, EAT.CREATION_DATE) )" +
						"	AND ( ? = '' OR EAT.CREATION_DATE <= ISNULL(?, EAT.CREATION_DATE) ) ORDER BY EAT.CREATION_DATE DESC" ;

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(userAccount.getUserId() );
		sqlArgs.add(dto.getTransNo());
		sqlArgs.add(dto.getTransNo());
		try {
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		sqlArgs.add(dto.getSQLEndDate());
		sqlArgs.add(dto.getSQLEndDate());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

    /**
     * 修改流程状态
     */
    @SuppressWarnings("unchecked")
    public SQLModel updateTransStatus() {
        SQLModel sqlModel = new SQLModel();
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE EAM_ADDRESS_ADD_H SET TRANS_STATUS=? WHERE TRANS_ID=? ";

        sqlArgs.add(dto.getTransStatus());
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 修改新增类型的地点编号
     * @param workorderObjectCode
     * @param lineId
     * @return
     */
    public SQLModel updateWorkorderObjectCode(String workorderObjectCode,String lineId) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE EAM_ADDRESS_ADD_L SET WORKORDER_OBJECT_CODE=? WHERE LINE_ID=? ";

        sqlArgs.add(workorderObjectCode);
        sqlArgs.add(lineId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 增加  ****
     */
    @SuppressWarnings("unchecked")
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO EAM_ADDRESS_ADD_H\n" +
                        "  (TRANS_ID,\n" +
                        "   TRANS_NO,\n" +
                        "   TRANS_TYPE,\n" +
                        "   TRANS_STATUS,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   DEPT,\n" +
                        "   CREATED_REASON,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "	EMERGENT_LEVEL,\n" +
                        "	IS_SECOND)\n" +
                        "VALUES\n" +
                        "  (?," +
                        "   ? , ?, ?, ?, ?, ?, GETDATE(), ?, ?,'Y')";
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getTransType());
        sqlArgs.add(dto.getTransStatus());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(dto.getDept());
        sqlArgs.add(dto.getCreatedReason());
        sqlArgs.add(user.getUserId());
        sqlArgs.add(dto.getEmergentLevel());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据主键获取对象   表单头*****
     */
    @SuppressWarnings("unchecked")
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT T.TRANS_ID,\n" +
                        "       T.TRANS_NO,\n" +
                        "       T.TRANS_TYPE,\n" +
                        "       T.TRANS_STATUS,\n" +
                        "       T.ORGANIZATION_ID,\n" +
                        "       dbo.APP_GET_ORGNIZATION_NAME(T.ORGANIZATION_ID) ORGANIZATION_NAME ,\n" +
                        "       AMD.DEPT_NAME  ,\n" +
                        "       T.CREATED_REASON,\n" +
                        "       T.CREATION_DATE,\n" +
                        "       T.CREATED_BY,\n" +
                        "       U.USERNAME CREATED_BY_NAME,\n" +
                        "       U.DEPT_CODE  DEPT_CODE,\n" +
                        "	    T.EMERGENT_LEVEL\n" +
                        "  FROM EAM_ADDRESS_ADD_H T, SF_USER U ,AMS_MIS_DEPT AMD\n " +
                        " WHERE T.TRANS_ID = ?\n" +
                        "   AND T.CREATED_BY = U.USER_ID\n" +
                        "   AND T.DEPT = AMD.DEPT_CODE";

        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;

    }

    /**
     * 功能：构造地点专业SQL
     * @param obCategory String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel OCModel(String obCategory) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE"
                + " FROM"
                + " ETS_FLEX_VALUES EFV,"
                + " ETS_FLEX_VALUE_SET EFVS"
                + " WHERE"
                + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = 'OBJECT_CATEGORY'"
                + " AND EFV.ATTRIBUTE2=?";
        sqlArgs.add(obCategory);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    @Override
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE EAM_ADDRESS_ADD_H \n" +
                        " SET TRANS_STATUS=?,\n" +
                        "     CREATED_REASON=?,\n" +
                        "     LAST_UPDATE_DATE=GETDATE(),\n" +
                        "     LAST_UPDATE_BY=?\n" +
                        "WHERE TRANS_ID=?";
        
        sqlArgs.add(dto.getTransStatus());
        sqlArgs.add(dto.getCreatedReason());
        sqlArgs.add(user.getUserId());
        sqlArgs.add(dto.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取行信息  地点列表 ****
     * @param dto
     * @return
     */
    public SQLModel getLineDataModel(EamAddressAddHDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT L.LINE_ID,\n" +
                        "       L.TRANS_ID,\n" +
                        "       L.WORKORDER_OBJECT_CODE,\n" +
                        "       L.WORKORDER_OBJECT_NAME,\n" +
                        "       L.OBJECT_CATEGORY,\n" +
                        "       L.COUNTY_CODE,\n" +
                        "       L.AREA_TYPE,\n" +
                        "       L.CITY,\n" +
                        "       L.COUNTY,\n" +
                        "       L.ORGANIZATION_ID,\n" +
                        "       L.ADDR_MAINTAIN_TYPE,\n" +
                        "		L.BTS_NO,\n" +
                        "		L.ERROR_MESSAGE,\n" +
                        "		L.SHARE_TYPE\n" +
                        "  FROM EAM_ADDRESS_ADD_L L\n" +
                        " WHERE L.TRANS_ID = ?";

        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 增加地点信息ETS_OBJECT_LOC2 (Excel地点第二段信息导入)
     */
    public SQLModel createDoEtsObjectLoc(EtsObjectDTO eoDTO) {
    	SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"INSERT INTO ETS_OBJECT_LOC2\n" +
			"  (LOC2_CODE,\n" + 
			"   LOC2_DESC,\n" + 
			"	COMPANY_CODE,\n" +
			"   DISABLE_DATE,\n" + 
			"   OBJECT_CATEGORY,\n" + 
			"   OBJECT_CATEGORY_NAME,\n" + 
			"   AREA_TYPE,\n" + 
			"   CITY,\n" + 
			"   COUNTY,\n" + 
			"   BTS_NO,\n" + 
			"   LATITUDE_LONGITUDE,\n" + 
			"   AUXILIARY_INFO,\n" + 
			"   REMARK,\n" + 
			"   CREATION_DATE,\n" + 
			"   CREATED_BY,\n" + 
			"   LAST_UPDATE_DATE,\n" + 
			"   LAST_UPDATE_BY,\n" +
			"	SHARE_TYPE)\n" + 
			"VALUES\n" + 
			"  (?,?,?,NULL,?,?,?,?,?,?,?,?,?,GETDATE(),?,NULL,NULL,?)";
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getCompanyCode());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getObjectCategoryName());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getLatitudeLongitude());
        sqlArgs.add(eoDTO.getAuxiliaryInfo());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(eoDTO.getCreatedBy());
        sqlArgs.add(eoDTO.getShareType());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 修改地点信息
     */
    public SQLModel updateEtsObjectInfo(EtsObjectDTO eoDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();        
        String sqlStr =
                " UPDATE ETS_OBJECT" +
                        " SET WORKORDER_OBJECT_NAME = STR_REPLACE(WORKORDER_OBJECT_NAME, SUBSTRING(WORKORDER_OBJECT_NAME,charindex('.',WORKORDER_OBJECT_NAME)+1,len(WORKORDER_OBJECT_NAME)-4 - charindex('.',WORKORDER_OBJECT_NAME)), ?), " +
                        " WORKORDER_OBJECT_LOCATION	= STR_REPLACE(WORKORDER_OBJECT_LOCATION, SUBSTRING(WORKORDER_OBJECT_LOCATION,charindex('.',WORKORDER_OBJECT_LOCATION)+1,len(WORKORDER_OBJECT_LOCATION)-4 - charindex('.',WORKORDER_OBJECT_LOCATION)), ?)," +
                        " LOC2_DESC = ? ," +
                        " OBJECT_CATEGORY = ?," +
                        " AREA_TYPE = ?," +
                        " CITY = ?," +
                        " COUNTY = ?," +
                        " LAST_UPDATE_BY = ?," +
                        " LAST_UPDATE_DATE = GETDATE()," +
                        " BTS_NO = ?" +
                        " WHERE patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].___',WORKORDER_OBJECT_CODE)<>0 " +
                        " AND LOC2_CODE = ?";
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(eoDTO.getCreatedBy());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改地点信息
     */
    public SQLModel updateEtsObjectLocInfo(EtsObjectDTO eoDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        String sqlStr =
                " UPDATE ETS_OBJECT_LOC2" +
                        " SET LOC2_DESC = ?, " +
                        " OBJECT_CATEGORY = ?," +
                        " AREA_TYPE = ?," +
                        " CITY = ?," +
                        " COUNTY = ?," +
                        " LAST_UPDATE_BY = ?," +
                        " LAST_UPDATE_DATE = GETDATE()," +
                        " BTS_NO = ?," +
                        " SHARE_TYPE = ?" +
                        " WHERE LOC2_CODE=? ";
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(eoDTO.getCreatedBy());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getShareType());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());       
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改地点失效日期
     */
    public SQLModel disabledEtsObject(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                " UPDATE ETS_OBJECT" +
                        " SET DISABLE_DATE= GETDATE()" +
                        " WHERE patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].___',WORKORDER_OBJECT_CODE)<>0 " +
                        " AND LOC2_CODE = ?";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 修改地点第二段代码的失效日期
     * @param workId
     * @return
     */
    public SQLModel disabledEtsLocObject(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                " UPDATE ETS_OBJECT_LOC2" +
                        " SET DISABLE_DATE= GETDATE()" +
                        " WHERE LOC2_CODE=? ";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel delImportModel(String tranId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " EAM_ADDRESS_ADD_L"
                + " WHERE"
                + " TRANS_ID = ? AND ERROR_MESSAGE IS NOT NULL";

        sqlArgs.add(tranId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel deleteImportModel() {
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " EAM_ADDRESS_ADD_L"
                + " WHERE"
                + " TRANS_ID = ?";

        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //获取表单行信息
    public SQLModel getAllQueryImportModel(String transId) throws SQLModelException {
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
                        "       EAM.ADDR_MAINTAIN_TYPE,\n" +
                        "       EAM.BTS_NO,\n" +
                        "       EAM.ERROR_MESSAGE,\n" +
                        "		EAM.SHARE_TYPE\n" +
                        "  FROM EAM_ADDRESS_ADD_L EAM\n" +
                        " WHERE EAM.TRANS_ID = ?\n";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取区县代码
     * @param strCode
     * @return
     * @throws SQLModelException
     */
    public SQLModel getCountyCode(String strCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        	"SELECT TOP 1 COUNTY_CODE FROM AMS_COUNTY WHERE COUNTY_NAME=?";
        sqlArgs.add(strCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 获取区域代码
     * @param str
     * @return
     * @throws SQLModelException
     */
    public SQLModel getAreaCountyCode(String str) throws SQLModelException {
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String sqlStr = "SELECT TOP 1 COUNTY_CODE FROM ETS_COUNTY WHERE COUNTY_CODE_MIS=?";
    	sqlArgs.add(str);
    	sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 获取地点第二段代码
     * @param provinceCode
     * @return
     */
    public SQLModel getNextObjectCode2Model(int organizationId,String objectCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT dbo.GET_OBJECT_CODE2(?, ?, ?) OBJECT_CODE";
        sqlArgs.add(organizationId);
        sqlArgs.add(objectCategory);
//        sqlArgs.add(SinoConfig.getProvinceCode());
        sqlArgs.add(SinoConfig.getFlexValueSetNameMis());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    
    /**
     * 物理地点查询
     * @param provinceCode
     * @return
     */
    public SQLModel getEtsObjectLocQueryModel(EtsObjectDTO eoDto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr =	"SELECT EOL.LOC2_CODE,\n" +
						"       EOL.LOC2_DESC,\n" + 
						"       EOL.COMPANY_CODE,\n" + 
						"       dbo.APP_GET_FLEX_VALUE(EOL.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY_NAME,\n" + 
						"       AC.COUNTY_NAME CITY_NAME,\n" + 
						"       ACC.COUNTY_NAME COUNTY_NAME,\n" + 
						"       EFV.VALUE AREA_TYPE_NAME,\n" + 
						"       EOL.BTS_NO,\n" + 
						"       EOL.LATITUDE_LONGITUDE,\n" + 
						"       (CASE EOL.SHARE_TYPE\n" + 
						"         WHEN '1' THEN\n" + 
						"          '仅上市公司使用'\n" + 
						"         WHEN '3' THEN\n" + 
						"          '仅TD公司使用'\n" + 
						"         ELSE\n" + 
						"          '上市和TD共用'\n" + 
						"       END) SHARE_TYPE,\n" + 
						"       EOL.DISABLE_DATE,\n" + 
						"       EOL.CREATION_DATE\n" + 
						"  FROM ETS_OBJECT_LOC2 EOL, ETS_FLEX_V EFV, AMS_COUNTY AC, AMS_COUNTY ACC\n" + 
						" WHERE EOL.AREA_TYPE *= EFV.CODE\n" + 
						"   AND EOL.CITY *= AC.COUNTY_CODE\n" + 
						"   AND EOL.COUNTY *= ACC.COUNTY_CODE\n" +
						"	AND EOL.COMPANY_CODE = ?\n" +
						"	AND ( ? = '' OR EOL.LOC2_CODE LIKE ISNULL(?, EOL.LOC2_CODE))\n" +
						"	AND ( ? = '' OR EOL.LOC2_DESC LIKE ISNULL(?, EOL.LOC2_DESC))\n" +
						"	AND EOL.CREATION_DATE >= ISNULL(?, EOL.CREATION_DATE)\n" +
						"	AND EOL.CREATION_DATE <= ISNULL(?, EOL.CREATION_DATE)\n" +
						"	AND ( ? = '' OR EOL.OBJECT_CATEGORY = ISNULL(?, EOL.OBJECT_CATEGORY))\n" +
						"	AND EOL.SHARE_TYPE = ?";
		
		sqlArgs.add(eoDto.getCompanyCode());
		sqlArgs.add(eoDto.getWorkorderObjectCode());
		sqlArgs.add(eoDto.getWorkorderObjectCode());
		sqlArgs.add(eoDto.getWorkorderObjectName());
		sqlArgs.add(eoDto.getWorkorderObjectName());
		try {
			if (eoDto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(eoDto.getStartDate());
			}
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		if (eoDto.getSQLEndDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(eoDto.getSQLEndDate());
		}
		sqlArgs.add(eoDto.getObjectCategory());	
		sqlArgs.add(eoDto.getObjectCategory());	
		sqlArgs.add(eoDto.getShareType());
				
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}

