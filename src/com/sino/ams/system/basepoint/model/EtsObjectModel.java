package com.sino.ams.system.basepoint.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;


/**
 * <p>Title: EtsObjectModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsObjectModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectModel extends AMSSQLProducer {

/**
	 * 功能：资产地点表(EAM) ETS_OBJECT 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 */
	public EtsObjectModel(SfUserDTO userAccount, EtsObjectDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " ETS_OBJECT("
						+ " WORKORDER_OBJECT_NO,\n"
						+ " WORKORDER_OBJECT_CODE,\n"
						+ " WORKORDER_OBJECT_NAME,\n"
						+ " WORKORDER_OBJECT_LOCATION,\n"
						+ " ORGANIZATION_ID,\n"
						+ " COUNTY_CODE,\n"
						+ " REMARK,\n"
						+ " OBJECT_CATEGORY,\n"
						+ " ISALL,\n"
						+ " CREATION_DATE,\n"
+ " CREATED_BY,\n"
+ " PROJECT_ID,\n"
						+ " LOCATION_CODE,"
						+ " LAST_UPDATE_DATE\n"
						+ ") VALUES ("
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ?,  GETDATE(), ?, ?, ?,GETDATE())";

		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectLocation());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getCountyCode());
//		sqlArgs.add(dto.getDisableDate());
		sqlArgs.add(dto.getRemark());
		sqlArgs.add(dto.getObjectCategory());
		sqlArgs.add(dto.getIsall());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getProjectId());
		sqlArgs.add(dto.getWorkorderObjectCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_OBJECT"
						+ " SET"
						+ " WORKORDER_OBJECT_CODE = ?,"
						+ " WORKORDER_OBJECT_NAME = ?,"
						+ " WORKORDER_OBJECT_LOCATION = ?,"
						+ " ORGANIZATION_ID = ?,"
						+ " COUNTY_CODE = ?,"
						+ " DISABLE_DATE = ?,"
						+ " REMARK = ?,"
						+ " OBJECT_CATEGORY = ?,"
						+ " ISALL = ?,"
//			+ " IS_TEMP_ADDR = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " PROJECT_ID = ?"
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";

		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectLocation());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getCountyCode());
		try {
			sqlArgs.add(dto.getDisableDate());
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlArgs.add(dto.getRemark());
		sqlArgs.add(dto.getObjectCategory());
		sqlArgs.add(dto.getIsall());
//		sqlArgs.add(dto.getIsTempAddr());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getProjectId());
		sqlArgs.add(dto.getWorkorderObjectNo());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：执行失效操作。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "UPDATE "
						+ " ETS_OBJECT"
						+ " SET"
						+ " DISABLE_DATE = GETDATE(),LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY= " + userAccount.getUserId()
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() { //明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION,"
						+ " EO.ORGANIZATION_ID,"
						+ " EO.COUNTY_CODE,"
						+ " EC.COUNTY_NAME,"
						+ " EO.DISABLE_DATE,"
						+ " EO.REMARK,"
						+ " EO.OBJECT_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY_NAME,"
						+ " EO.ISALL,"
						+ " EO.IS_TEMP_ADDR,"
						+ " EO.CREATION_DATE,"
						+ " EO.CREATED_BY,"
						+ " EO.LAST_UPDATE_DATE,"
						+ " EO.LAST_UPDATE_BY,"
						+ " EO.PROJECT_ID,"
						+ " EPPA.NAME PROJECT_NAME"
						+ " FROM"
						+ " ETS_OBJECT EO,"
						+ " ETS_COUNTY EC,"
                        + " ETS_OU_CITY_MAP    EOCM,"
						+ " ETS_PA_PROJECTS_ALL EPPA"
						+ " WHERE"
						+ " EO.COUNTY_CODE *= EC.COUNTY_CODE"
                        + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n "
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n "
						+ " AND EO.PROJECT_ID *= EPPA.PROJECT_ID"
						+ " AND EO.WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(dto.getWorkorderObjectNo());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() { //查询
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION,"
						+ " EO.ORGANIZATION_ID,"
						+ " EC.COUNTY_NAME,"
						+ " EO.DISABLE_DATE,"
						+ " EO.REMARK,"
						+ " EO.OBJECT_CATEGORY,"
						+" CASE WHEN EO.ISALL=0 THEN '巡检本专业设备' WHEN EO.ISALL=1 THEN '巡检所有专业设备' END  IS ALL,"
						+ " EO.IS_TEMP_ADDR,"
						+ " EO.CREATION_DATE,"
						+ " EO.CREATED_BY,"
						+ " EO.LAST_UPDATE_DATE,"
						+ " EO.LAST_UPDATE_BY,"
						+ " EO.PROJECT_ID,"
						+ " EPPA.NAME PROJECT_NAME,"
						+ " EFV.VALUE CATEGORY_NAME"
						+ " FROM"
						+ " ETS_OBJECT EO,"
						+ " ETS_PA_PROJECTS_ALL EPPA,"
						+ " ETS_FLEX_VALUES EFV,"
						+ " ETS_FLEX_VALUE_SET EFVS,"
                        + " ETS_OU_CITY_MAP    EOCM,"
                        + " ETS_COUNTY EC"
						+ " WHERE"
						+ " EO.PROJECT_ID *= EPPA.PROJECT_ID"
						+ " AND EO.OBJECT_CATEGORY = EFV.CODE"
						+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFVS.CODE = ?"
						+ " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                        + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n "
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n "
						+ " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
						+ " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
						+ " AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.COUNTY_CODE = ?)"
						+ " AND EO.OBJECT_CATEGORY = ?"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.ORGANIZATION_ID = ?)"
						+ " ORDER BY EO.WORKORDER_OBJECT_CODE DESC";
//            + " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') ";
		sqlArgs.add(DictConstant.OBJECT_CATEGORY);
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectLocation());
		sqlArgs.add(dto.getCountyCode());
		sqlArgs.add(dto.getCountyCode());
//		sqlArgs.add(dto.getObjectCategory());
		sqlArgs.add(DictConstant.NETADDR_BTS);
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
//        if(userAccount.isProvinceUser()){
//            sqlStr += " AND EO.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EO.ORGANIZATION_ID)))";
//            sqlArgs.add(dto.getOrganizationId());
//        } else {
//            sqlStr += " AND EO.ORGANIZATION_ID = ?";
//            sqlArgs.add(userAccount.getOrganizationId());
//        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：执行批量失效操作。
	 * @param  workorderObjectNos 地点ID数组
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel getDisabledModel(String[] workorderObjectNos) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToSqlStr(workorderObjectNos);
		String sqlStr = "UPDATE "
						+ " ETS_OBJECT"
						+ " SET"
						+ " DISABLE_DATE = GETDATE(),LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY= " + userAccount.getUserId()
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO IN (" + orderno + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


/**
	 * 功能：执行批量失效操作。
	 * @param  workorderObjectNos 地点ID数组
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel getEfficientModel(String[] workorderObjectNos) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToSqlStr(workorderObjectNos);
		String sqlStr = "UPDATE "
						+ " ETS_OBJECT"
						+ " SET"
						+ " DISABLE_DATE = NULL,LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY= " + userAccount.getUserId()
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO IN (" + orderno + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：执行失效操作。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getInureModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "UPDATE "
						+ " ETS_OBJECT"
						+ " SET"
						+ " DISABLE_DATE = null,LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY= " + userAccount.getUserId()
						+ " WHERE"
						+ " WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断当前用户是否有权限执行数据编辑操作。BARCODE 的存在性
	 * @param workorderObjectCode 地点代码数组
	 * @return SQLModel
	 */
	public SQLModel getVerifyWorkNoModel(String workorderObjectCode) {
		SQLModel sqlModel = new SQLModel();
		List strArg = new ArrayList();
		String strSql = "SELECT 1 FROM ETS_OBJECT EO WHERE EO.WORKORDER_OBJECT_CODE = ? AND EO.ORGANIZATION_ID = ?";
		strArg.add(workorderObjectCode);
		strArg.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(strSql);
		sqlModel.setArgs(strArg);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getAOACreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_OBJECT_ADDRESS("
						+ " ADDRESS_ID,"
						+ " OBJECT_NO,"
						+ " ORGANIZATION_ID"
						+ ") VALUES ("
						+ "  NEWID() , ?, ?)";

		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
