package com.sino.ams.system.object.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;
import com.sino.config.SinoConfig;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CommonObjectModel extends AMSSQLProducer {
	public CommonObjectModel(SfUserDTO userAccount, EtsObjectDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造翻页查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append( " SELECT \n" );
		sb.append( " dbo.AWP_CAN_DISABLE_OBJECT(EO.WORKORDER_OBJECT_NO) CAN_DISABLE,\n" );
		sb.append( " EO.WORKORDER_OBJECT_NO ,\n" );
		sb.append( " EO.WORKORDER_OBJECT_CODE ,\n" );
		sb.append( " EO.WORKORDER_OBJECT_NAME ,\n" );
		sb.append( " EO.WORKORDER_OBJECT_LOCATION ,\n" );
		sb.append( " EO.ORGANIZATION_ID ,\n" );
		sb.append( " (case EO.IS_TD when 'Y' then '是' else '否' end ) IS_TD ,\n" );
		sb.append( " EO.BTS_NO ,\n" );
		sb.append( " EOCM.COMPANY ,\n" );
		sb.append( " ' 'AS B ,\n" );
		sb.append( " ' 'AS C ,\n" );
		sb.append( " ' 'AS D ,\n" );
		sb.append( " ' 'AS E ,\n" );
		sb.append( " ' 'AS F ,\n" );
		sb.append( " EO.COUNTY_CODE ,\n" );
		sb.append( " EO.AREA_TYPE ,\n" );
		sb.append( " AC.COUNTY_NAME ,\n" );
		sb.append( " EO.REMARK ,\n" );
		sb.append( " EO.OBJECT_CATEGORY ,\n" );
		sb.append( " dbo.APP_GET_FLEX_VALUE( convert( varchar, EO.OBJECT_CATEGORY ), 'OBJECT_CATEGORY') OBJECT_CATEGORY_NAME,\n" );
		sb.append( " SU.USERNAME CREATION_USER ,\n" );
		sb.append( " EO.CREATION_DATE ,\n" );
		sb.append( " EO.DISABLE_DATE ,\n" );
		sb.append( " SU2.USERNAME UPDATED_USER ,\n" );
		sb.append( " EO.LAST_UPDATE_DATE ,\n" );
		sb.append( " dbo.APP_GET_ORGNIZATION_NAME(EO.ORGANIZATION_ID) ORG_NAME, \n");
		sb.append( " dbo.GET_PRINT_HISTORY_COUNT( EO.ORGANIZATION_ID , EO.WORKORDER_OBJECT_NO ) PRINT_NUM , \n"); //打印次数
		sb.append( " EFV.VALUE, \n" );
		sb.append( " EO.LATITUDE_LONGITUDE, \n" );
		sb.append( " EO.AUXILIARY_INFO \n" );
		sb.append( " FROM \n" );
		sb.append( " ETS_OBJECT EO (INDEX ETS_OBJECT_N5)  \n" );
		sb.append( " left join SF_USER SU on EO.CREATED_BY = SU.USER_ID \n" );
		sb.append( " left join SF_USER SU2 on  EO.LAST_UPDATE_BY = SU2.USER_ID \n" );
		sb.append( " left join ETS_FLEX_V EFV on EO.AREA_TYPE = EFV.CODE \n" );
		sb.append( " left join AMS_COUNTY AC on EO.COUNTY = AC.COUNTY_CODE \n" );
		sb.append( " left join ETS_OU_CITY_MAP EOCM (INDEX ETS_OU_CITY_MAP_PK) on EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" );
		sb.append( " WHERE \n" );             		  
		sb.append( " (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80') \n" ); 
		sb.append( " AND ISNULL( EFV.PAR_CODE, 'ADDR_AREA_TYPE') = 'ADDR_AREA_TYPE' \n" );
		sb.append( " AND ISNULL( EFV.ENABLED, 'Y' ) = 'Y' \n" );
		
/*		sb.append( " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EO.AREA_TYPE = ?) \n" ); 
	    sb.append( " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)");
	    sb.append( " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)");
		sb.append( " AND (? = 0 OR EO.CREATED_BY = ?) \n" ); 
		sb.append( " AND (? = '' OR  EO.CREATION_DATE >= dbo.NVL(?, EO.CREATION_DATE) )");
		sb.append( " AND (? = '' OR  EO.CREATION_DATE <= dbo.NVL(?, EO.CREATION_DATE) )");
		sb.append( " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EO.COUNTY_CODE = ?) \n" );  
		sb.append( " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EO.OBJECT_CATEGORY = ?) \n" ); 
		sb.append( " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EO.IS_TD = ?) \n" ); */
		
		sb.append( " AND EO.AREA_TYPE = ISNULL(?, EO.AREA_TYPE) \n" );
	    sb.append( " AND EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE) \n" );
	    sb.append( " AND EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME) \n" );
		sb.append( " AND (? = 0 OR EO.CREATED_BY = ?) \n" );
		sb.append( " AND EO.CREATION_DATE >= ISNULL(?, EO.CREATION_DATE) \n" );
		sb.append( " AND EO.CREATION_DATE <= ISNULL(?, EO.CREATION_DATE) \n" );
		sb.append( " AND patindex('[0-9][0-9][0-9][0-9][0-9][0-9].______________.___',EO.WORKORDER_OBJECT_CODE)>0\n");
		sb.append( " AND SUBSTRING(WORKORDER_OBJECT_CODE,1,charindex('.',WORKORDER_OBJECT_CODE)-1) = ISNULL(?, EO.COUNTY_CODE) \n" );
		sb.append( " AND EO.OBJECT_CATEGORY = ISNULL(?, EO.OBJECT_CATEGORY) \n" );
		sb.append( " AND EO.IS_TD = ISNULL(?, EO.IS_TD) \n" );
		
		sb.append( " AND EO.ORGANIZATION_ID = ISNULL(?, EO.ORGANIZATION_ID) \n" );
		 			
		//SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getAreaType() );
		
		if (dto.getAreaType().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getAreaType());
		}
		if (dto.getWorkorderObjectCode().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getWorkorderObjectCode());
		}
		if (dto.getWorkorderObjectName().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getWorkorderObjectName());
		}
		sqlArgs.add(dto.getCreatedBy());
		sqlArgs.add(dto.getCreatedBy());
		try {
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		if (dto.getSQLEndDate().toString().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getSQLEndDate());
		}
		if (dto.getCountyCode().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getCountyCode());
		}
		if (dto.getObjectCategory().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getObjectCategory());
		}
		if (dto.getIsTd().equals("")) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getIsTd());
		}
		//SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getCountyCode() );
		//SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getObjectCategory());
		//SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getIsTd() );
		
		if (dto.getOrganizationId() == 	SyBaseSQLUtil.NULL_INT_VALUE) {
			sqlArgs.add(null);
		} else {
			sqlArgs.add(dto.getOrganizationId());
		}
		
		if(dto.getEnabled().equals("Y")){
			sb.append( "  AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE ='' OR EO.DISABLE_DATE > GETDATE()) " );
		} else if(dto.getEnabled().equals("N")){
			sb.append( " AND EO.DISABLE_DATE IS NOT NULL" );
			sb.append( " AND EO.DISABLE_DATE <> ''" );
			sb.append( " AND EO.DISABLE_DATE < GETDATE()" );
		}
		if(dto.isAreaTypeIsNull() == true){
			sb.append( " AND EO.AREA_TYPE IS NULL\n " );
		}
		sb.append( " ORDER BY EO.WORKORDER_OBJECT_CODE DESC " );
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造翻页查询SQL
	 * @return SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION,"
						+ " EO.ORGANIZATION_ID,"
						+ " EO.IS_TD,"
						+ " EOCM.COMPANY,"
						+ " EO.COUNTY_CODE,"
						+ " EO.AREA_TYPE,"
						+ " EO.REMARK,"
						+ " EO.OBJECT_CATEGORY,"
						+ " EO.CREATION_DATE,"
						+ " EPPA.PROJECT_ID,"
						+ " EO.CITY ,"
						+ " EO.COUNTY ,"
						+ " EO.LOCATION ,"
						+ " EPPA.NAME PROJECT_NAME,"
						+ "	EO.BTS_NO ," 
						+ " EO.LATITUDE_LONGITUDE,"
						+ " EO.AUXILIARY_INFO"
						+ " FROM"
						+ " ETS_OU_CITY_MAP     EOCM,"
						+ " ETS_OBJECT          EO,"
						+ " ETS_PA_PROJECTS_ALL EPPA"
						+ " WHERE"
						+ " EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND EO.PROJECT_ID *= EPPA.PROJECT_ID"
						+ " AND EO.WORKORDER_OBJECT_NO = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造地点创建SQL
	 * @return SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_OBJECT("
						+ " WORKORDER_OBJECT_NO,"
						+ " WORKORDER_OBJECT_CODE,"
						+ " WORKORDER_OBJECT_NAME,"
						+ " WORKORDER_OBJECT_LOCATION,"
						+ " ORGANIZATION_ID,"
						+ " COUNTY_CODE,"
						+ " AREA_TYPE,"
						+ " REMARK,"
						+ " OBJECT_CATEGORY,"
						+ " IS_TEMP_ADDR,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " PROJECT_ID,"
						+ " IS_TD,"
						+ " LOCATION_CODE,"
						+ " CITY,"
						+ " COUNTY,"
						+ " LOCATION,"
						+ "	BTS_NO,"
						+ "	LATITUDE_LONGITUDE,"
						+ " AUXILIARY_INFO,"
						+ " LOC2_CODE,"
						+ " LOC2_DESC"
						+ " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, " + SyBaseSQLUtil.getCurDate() + " , ?, " + SyBaseSQLUtil.getCurDate() + " , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ "SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4),SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 3))";
		
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getCountyCode());
		sqlArgs.add(dto.getAreaType());
		sqlArgs.add(dto.getRemark());
		sqlArgs.add(dto.getObjectCategory());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getProjectId());
		sqlArgs.add(dto.getIsTd());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getCity());
		sqlArgs.add(dto.getCounty());
		sqlArgs.add(dto.getLocation());
		sqlArgs.add(dto.getBtsNo());
		sqlArgs.add(dto.getLatitudeLongitude());
		sqlArgs.add(dto.getAuxiliaryInfo());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造地点更新SQL
	 * @return SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_OBJECT"
						+ " SET"
						+ " WORKORDER_OBJECT_CODE = ?,"
						+ " WORKORDER_OBJECT_NAME = ?,"
						+ " WORKORDER_OBJECT_LOCATION = ?,"
						+ " OBJECT_CATEGORY = ?,"
						+ " COUNTY_CODE = ?,"
						+ " AREA_TYPE = ?,"
						+ " IS_TD = ?,"
						+ " REMARK = ?,"
						+ " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + " ,"
						+ " LAST_UPDATE_BY = ?,"
						+ " CITY = ?,"
						+ " COUNTY = ?,"
						+ " LOCATION = ?,"
						+ "	BTS_NO = ?,"
						+ "	LATITUDE_LONGITUDE = ?,"
						+ " AUXILIARY_INFO = ?"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getObjectCategory());
		sqlArgs.add(dto.getCountyCode());
		sqlArgs.add(dto.getAreaType());
		sqlArgs.add(dto.getIsTd());
		sqlArgs.add(dto.getRemark());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getCity());
		sqlArgs.add(dto.getCounty());
		sqlArgs.add(dto.getLocation());
		sqlArgs.add(dto.getBtsNo());
		sqlArgs.add(dto.getLatitudeLongitude());
		sqlArgs.add(dto.getAuxiliaryInfo());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
     * 修改地点信息
     */
    public SQLModel updateEtsObjectInfo() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;       
        String sqlStr =
                " UPDATE ETS_OBJECT" +
                        " SET WORKORDER_OBJECT_NAME = STR_REPLACE(WORKORDER_OBJECT_NAME, SUBSTRING(WORKORDER_OBJECT_NAME,charindex('.',WORKORDER_OBJECT_NAME)+1,len(WORKORDER_OBJECT_NAME)-4 - charindex('.',WORKORDER_OBJECT_NAME)), SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?))), " +
                        " WORKORDER_OBJECT_LOCATION	= STR_REPLACE(WORKORDER_OBJECT_LOCATION, SUBSTRING(WORKORDER_OBJECT_LOCATION,charindex('.',WORKORDER_OBJECT_LOCATION)+1,len(WORKORDER_OBJECT_LOCATION)-4 - charindex('.',WORKORDER_OBJECT_LOCATION)), SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)))," +
                        " LOC2_DESC = SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?))," +
                        " COUNTY_CODE = ?," +
                        " OBJECT_CATEGORY = ?," +
                        " AREA_TYPE = ?," +
                        " CITY = ?," +
                        " COUNTY = ?," +
                        " LAST_UPDATE_BY = ?," +
                        " LAST_UPDATE_DATE = GETDATE()," +
                        " BTS_NO = ?" +
                        " WHERE patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].___',WORKORDER_OBJECT_CODE)<>0 " +
                        " AND LOC2_CODE = SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)";
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getCountyCode());
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
	
	/**
     * 修改物理地点信息
     */
    public SQLModel updateEtsObjectLocInfo() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO eoDTO = (EtsObjectDTO) dtoParameter;
        String sqlStr =
                " UPDATE ETS_OBJECT_LOC2" +
                        " SET LOC2_DESC = SUBSTRING(?,charindex('.',?)+1,len(?)-charindex('.',reverse(?))-charindex('.',?)), " +
                        " OBJECT_CATEGORY = ?," +
                        " AREA_TYPE = ?," +
                        " CITY = ?," +
                        " COUNTY = ?," +
                        " LAST_UPDATE_BY = ?," +
                        " LAST_UPDATE_DATE = GETDATE()," +
                        " BTS_NO = ?," +
                        " SHARE_TYPE = ?" +
                        " WHERE LOC2_CODE= SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)";
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        
        sqlArgs.add(eoDTO.getObjectCategory());
        sqlArgs.add(eoDTO.getAreaType());
        sqlArgs.add(eoDTO.getCity());
        sqlArgs.add(eoDTO.getCounty());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(eoDTO.getBtsNo());
        sqlArgs.add(eoDTO.getShareType());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

	/**
	 * 功能：构造地点失效SQL
	 * @return SQLModel
	 */
	public SQLModel getDisableModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_OBJECT "
						+ " SET"
						+ " DISABLE_DATE = " + SyBaseSQLUtil.getCurDate() + " ,"
						+ " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + " ,"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?"
						+ " AND (SELECT COUNT(*)"
						+ "	FROM ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA, ETS_ITEM_INFO EII"
						+ "	WHERE EO.WORKORDER_OBJECT_NO = ?"
						+ "	AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO"
						+ "	AND AOA.ADDRESS_ID = EII.ADDRESS_ID) = 0";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getDisableCountModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr =	" SELECT COUNT(*)"
						+ "	FROM ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA, ETS_ITEM_INFO EII"
						+ "	WHERE EO.WORKORDER_OBJECT_NO = ?"
						+ "	AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO"
						+ "	AND AOA.ADDRESS_ID = EII.ADDRESS_ID ";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造地点生效SQL
	 * @return SQLModel
	 */
	public SQLModel getEnableModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_OBJECT "
						+ " SET"
						+ " DISABLE_DATE = NULL,"
						+ " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + " ,"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造判断地点是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getObjectEsistModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OBJECT EO"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_CODE = ?"
						+ " AND EO.ORGANIZATION_ID = ?";
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(userAccount.getOrganizationId());
		String objectNo = dto.getWorkorderObjectNo();
		if(!objectNo.equals("")){
			sqlStr += " AND EO.WORKORDER_OBJECT_NO <> ?";
			sqlArgs.add(objectNo);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造判断基站或营业厅编号是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getBtsNoEsistModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OBJECT EO"
						+ " WHERE"
						+ " EO.BTS_NO = ?" ;
		sqlArgs.add(dto.getBtsNo());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造判断地点名称是否重复的SQL
	 * @return
	 */
	public SQLModel getWorkorderObjectNameEsistModel() {
		SQLModel sqlModel = new SQLModel();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OBJECT EO"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_NAME = ?" ;
		try {
			sqlArgs.add(java.net.URLDecoder.decode(dto.getWorkorderObjectName(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getAddress(String addressName){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT DISTINCT SUBSTRING(EO.WORKORDER_OBJECT_NAME,"	+
                       "				 CHARINDEX(EO.WORKORDER_OBJECT_NAME, '.', 1) + DATALENGTH('.'),"	+
                       "				 (CHARINDEX(EO.WORKORDER_OBJECT_NAME, '.', 1, 2) -"	+
                       "				 CHARINDEX(EO.WORKORDER_OBJECT_NAME, '.', 1, 1)) -"	+
                       "				 DATALENGTH('.')) WORKORDER_OBJECT_NAME"	+

                       " FROM ETS_OBJECT EO "	+
                       " WHERE EO.WORKORDER_OBJECT_NAME LIKE '%" + addressName + "%' " +
                       	"  AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.ORGANIZATION_ID = ?) " +
                       	"  AND ROWNUM <= 15";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	public SQLModel getLocation(String location){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "SELECT DISTINCT TOP 5 WORKORDER_OBJECT_NAME LOCATION"	+
		
		" FROM ETS_OBJECT EO "	+
		" WHERE EO.WORKORDER_OBJECT_NAME LIKE '%" + location + "%' " ;
		//"  AND ( ? IS NULL OR EO.ORGANIZATION_ID = ?) ";
		//sqlArgs.add(dto.getOrganizationId());
		//sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	  public SQLModel getMatchORG() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        String sqlStr = "SELECT EOCM.COMPANY_CODE\n" +
	                "  FROM ETS_OU_CITY_MAP EOCM\n" +
	                " WHERE EOCM.MATCH_ORGANIZATION_ID = ?";
	        sqlArgs.add(dto.getOrganizationId());
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }

	    public SQLModel getCostCode(String countyCode) {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        String sqlStr = "SELECT EC.COUNTY_CODE_MIS COST_CODE FROM ETS_COUNTY EC WHERE EC.COUNTY_CODE =?";
	        sqlArgs.add(countyCode);
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }

	    public SQLModel getInsertToModel(String[] workorderObjectNos) {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        String orderno = ArrUtil.arrToStr(workorderObjectNos, "','");
	        String sqlStr = "INSERT INTO  EAM_SYN_OBJECT(BARCODE,CREATE_DATE,CREATE_BY,ORG_ID)\n" +
	                "  (SELECT EO.WORKORDER_OBJECT_CODE, GETDATE(), " + userAccount.getUserId() + "," + userAccount.getOrganizationId() +
	                "     FROM ETS_OBJECT EO\n" +
	                "    WHERE EO.WORKORDER_OBJECT_NO  IN ('" + orderno + "'))";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
	    
	    /**
		 * 功能：构造判断匹配地点编码是否存在的SQL
		 * @return SQLModel
		 */
		public SQLModel getMatchObjectCodeExistModel() {
			SQLModel sqlModel = new SQLModel();
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = 
				"SELECT 1\n" +
				"  FROM ETS_OBJECT\n" + 
				" WHERE IS_TD = ?\n" + 
				"   AND ORGANIZATION_ID = ?\n" + 
				"   AND WORKORDER_OBJECT_CODE =\n" + 
				"       (SELECT WORKORDER_OBJECT_CODE\n" + 
				"          FROM ETS_OBJECT\n" + 
				"         WHERE WORKORDER_OBJECT_NO = ?\n" + 
				"           AND ORGANIZATION_ID = ?\n" + 
				"           AND IS_TD = ?)";

			sqlArgs.add(dto.getIsTd());
			sqlArgs.add(dto.getOrganizationId());			
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(userAccount.getIsTd());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
		
		/**
		 * 获取匹配的OrganizationId
		 * @return
		 */
		public SQLModel getMatchOrgIdModel(int orgId){
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT MATCH_ORGANIZATION_ID FROM ETS_OU_CITY_MAP WHERE ORGANIZATION_ID= ?";
			sqlArgs.add(orgId);
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
	    
		/**
		 * 地点传送
		 * @return
		 */
	    public SQLModel getTransferToModel() {
	        SQLModel sqlModel = new SQLModel();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        List sqlArgs = new ArrayList();
	        String sqlStr = 
	        	"INSERT INTO ETS_OBJECT\n" +
	        	"  (WORKORDER_OBJECT_NO,\n" + 
	        	"   WORKORDER_OBJECT_CODE,\n" + 
	        	"   WORKORDER_OBJECT_NAME,\n" + 
	        	"   WORKORDER_OBJECT_LOCATION,\n" + 
	        	"   ORGANIZATION_ID,\n" + 
	        	"   COUNTY_CODE,\n" + 
	        	"   DISABLE_DATE,\n" + 
	        	"   REMARK,\n" + 
	        	"   ISALL,\n" + 
	        	"   IS_TEMP_ADDR,\n" + 
	        	"   CREATION_DATE,\n" + 
	        	"   CREATED_BY,\n" + 
	        	"   LAST_UPDATE_DATE,\n" + 
	        	"   LAST_UPDATE_BY,\n" + 
	        	"   PROJECT_ID,\n" + 
	        	"   LOCATION_CODE,\n" + 
	        	"   COST_CODE,\n" + 
	        	"   AREA_TYPE,\n" + 
	        	"   BUSINESS_CATEGORY,\n" + 
	        	"   DEPT_CODE,\n" + 
	        	"   IS_TD,\n" + 
	        	"   CITY,\n" + 
	        	"   COUNTY,\n" + 
	        	"   LOCATION,\n" + 
	        	"   OBJECT_CATEGORY_NAME,\n" + 
	        	"   OBJECT_CATEGORY,\n" + 
	        	"   BTS_NO,\n" + 
	        	"   LATITUDE_LONGITUDE,\n" + 
	        	"   AUXILIARY_INFO,\n" + 
	        	"   LOC2_CODE,\n" + 
	        	"   LOC2_DESC)\n" + 
	        	"  SELECT NEWID(),\n" + 
	        	"         WORKORDER_OBJECT_CODE,\n" + 
	        	"         WORKORDER_OBJECT_NAME,\n" + 
	        	"         WORKORDER_OBJECT_LOCATION,\n" + 
	        	"         ?,\n" + 
	        	"         COUNTY_CODE,\n" + 
	        	"         DISABLE_DATE,\n" + 
	        	"         REMARK,\n" + 
	        	"         ISALL,\n" + 
	        	"         IS_TEMP_ADDR,\n" + 
	        	"         GETDATE(),\n" + 
	        	"         ?,\n" + 
	        	"         NULL,\n" + 
	        	"         NULL,\n" + 
	        	"         PROJECT_ID,\n" + 
	        	"         LOCATION_CODE,\n" + 
	        	"         COST_CODE,\n" + 
	        	"         AREA_TYPE,\n" + 
	        	"         BUSINESS_CATEGORY,\n" + 
	        	"         DEPT_CODE,\n" + 
	        	"         ?,\n" + 
	        	"         CITY,\n" + 
	        	"         COUNTY,\n" + 
	        	"         LOCATION,\n" + 
	        	"         OBJECT_CATEGORY_NAME,\n" + 
	        	"         OBJECT_CATEGORY,\n" + 
	        	"         BTS_NO,\n" + 
	        	"         LATITUDE_LONGITUDE,\n" + 
	        	"         AUXILIARY_INFO,\n" + 
	        	"         LOC2_CODE,\n" + 
	        	"         LOC2_DESC\n" + 
	        	"    FROM ETS_OBJECT\n" + 
	        	"   WHERE WORKORDER_OBJECT_NO = ?\n" +
	        	"	  AND (DISABLE_DATE IS NULL OR DISABLE_DATE ='' OR DISABLE_DATE > GETDATE())\n" + //失效的地点不能传送
	        	"	  AND OBJECT_CATEGORY <> '74'";  //地点传送不能传送在途库

	        sqlArgs.add(dto.getOrganizationId());
	        sqlArgs.add(userAccount.getUserId());
	        sqlArgs.add(dto.getIsTd());
	        sqlArgs.add(dto.getWorkorderObjectNo());
			       
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}

	    public SQLModel getNextObjectCodeModel(String provinceCode) {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        String sqlStr = "SELECT dbo.GET_OBJECT_CODE(?, ?, ?, ?, ?) OBJECT_CODE";
	        sqlArgs.add(dto.getOrganizationId());
	        sqlArgs.add(dto.getObjectCategory());
	        sqlArgs.add(dto.getCountyCode().substring(0, 6));
	        sqlArgs.add(provinceCode);
	        sqlArgs.add(SinoConfig.getFlexValueSetNameMis());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}

	    public SQLModel getCityByOrgIdModel() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        String sqlStr = "SELECT AC.COUNTY_CODE \n" +
	                "FROM   AMS_COUNTY AC\n" +
	                "WHERE  (AC.PARENT_CODE IS NULL OR AC.PARENT_CODE = '' )\n" +
	                "       AND AC.ORGANIZATION_ID = ?";
	        sqlArgs.add(dto.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}


	    public SQLModel geIsTDByOrgIdModel() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	        String sqlStr = "SELECT EOCM.IS_TD FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.ORGANIZATION_ID = ?";
	        sqlArgs.add(dto.getOrganizationId());
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
				"  (SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 4)," +
				"	SUBSTRING(?,dbo.CHARINDEX_3('.', ?, 1) + 1,LEN(?) - dbo.CHARINDEX_3('.', ?, 1) - 3)," +
				" ?,NULL,?,?,?,?,?,?,?,?,?,GETDATE(),?,NULL,NULL,?)";
	        sqlArgs.add(eoDTO.getWorkorderObjectCode());
	        sqlArgs.add(eoDTO.getWorkorderObjectCode());
	        sqlArgs.add(eoDTO.getWorkorderObjectCode());
	        sqlArgs.add(eoDTO.getWorkorderObjectCode());
	        sqlArgs.add(eoDTO.getWorkorderObjectName());
	        sqlArgs.add(eoDTO.getWorkorderObjectName());
	        sqlArgs.add(eoDTO.getWorkorderObjectName());
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
	        if (userAccount.getIsTd().equals("N")) {
	        	sqlArgs.add("1");
	        } else {
	        	sqlArgs.add("3");
			}

	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    /**
	     * 查询地点第二段信息
	     * @return
	     */
	    public SQLModel getEtsObjectLoc2() {
	    	EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	    	SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			String sqlStr = 
				"SELECT OBJECT_CATEGORY,\n" + 
				"   	OBJECT_CATEGORY_NAME,\n" + 
				"   	AREA_TYPE,\n" + 
				"   	CITY,\n" + 
				"   	COUNTY,\n" + 
				"  		BTS_NO,\n" + 
				"   	LATITUDE_LONGITUDE,\n" + 
				"   	AUXILIARY_INFO,\n" + 
				"   	REMARK\n" + 
				"  FROM ETS_OBJECT_LOC2\n" + 
				" WHERE LOC2_CODE = ?\n"; 
			
			sqlArgs.add(dto.getLoc2Code());			
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    public SQLModel getEtsObjectLocInfo() {
	    	EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	    	SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			String sqlStr = 
				"SELECT         EO.COUNTY_CODE,\n" +
				"                SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" + 
				"                                dbo.CHARINDEX_3('.',EO.WORKORDER_OBJECT_CODE,1) + 1,\n" + 
				"                                LEN(EO.WORKORDER_OBJECT_CODE) - dbo.CHARINDEX_3('.',EO.WORKORDER_OBJECT_CODE,1) -4) LOC2_CODE,\n" + 
				"                SUBSTRING(EO.WORKORDER_OBJECT_NAME,1,dbo.CHARINDEX_3('.',EO.WORKORDER_OBJECT_NAME,1)-1) COUNTY_NAME,\n" + 
				"                            SUBSTRING(EO.WORKORDER_OBJECT_NAME,\n" + 
				"                                dbo.CHARINDEX_3('.',EO.WORKORDER_OBJECT_NAME,1) + 1,\n" + 
				"                                LEN(EO.WORKORDER_OBJECT_NAME) - dbo.CHARINDEX_3('.',EO.WORKORDER_OBJECT_NAME,1) -3) LOCATION\n" + 
				" FROM ETS_OBJECT EO WHERE EO.WORKORDER_OBJECT_NO= ? ";

			
			sqlArgs.add(dto.getWorkorderObjectNo());			
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    /**
	     * 获取workorderObjectNo
	     * @return
	     */
	    public SQLModel getWorkorderObjectNo() {
	    	EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
	    	SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			String sqlStr = 
				"SELECT EO.WORKORDER_OBJECT_NO,AOA.ADDRESS_ID\n" +
				" FROM ETS_OBJECT EO,AMS_OBJECT_ADDRESS AOA WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO AND EO.WORKORDER_OBJECT_CODE= ? ";
			
			sqlArgs.add(dto.getWorkorderObjectCode());			
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    /**
	     * 查询地点代码第二段是否存在
	     * @param code
	     * @return
	     * @throws SQLModelException
	     */
	    public SQLModel getLoc2CodeIsExistsModel(String code) throws SQLModelException {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        String sqlStr = "SELECT 1\n" +
	                "  FROM ETS_OBJECT_LOC2      EOC\n" +
	                " WHERE EOC.LOC2_CODE = SUBSTRING(?,dbo.CHARINDEX_3('.',?,1) + 1,LEN(?) - dbo.CHARINDEX_3('.',?,1) -4) \n";
	        sqlArgs.add(code);
	        sqlArgs.add(code);
	        sqlArgs.add(code);
	        sqlArgs.add(code);
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
}
