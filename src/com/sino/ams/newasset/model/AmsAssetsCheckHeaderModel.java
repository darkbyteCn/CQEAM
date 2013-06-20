package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsCheckHeaderModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsCheckHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCheckHeaderModel extends AMSSQLProducer {
    private String deptCodes = "";

	/**
	 * 功能：资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsCheckHeaderDTO 本次操作的数据
	 */
	public AmsAssetsCheckHeaderModel(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
        initDeptCodes();
	}

    private void initDeptCodes() {
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

	/**
	 * 功能：框架自动生成资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String sqlStr = "";
			if (dto.isFlow2End()) {
				sqlStr = "INSERT INTO "
						 + " AMS_ASSETS_CHECK_HEADER("
						 + " HEADER_ID,"
						 + " BATCH_ID,"
						 + " CHECK_LOCATION,"
						 + " TRANS_NO,"
						 + " IMPLEMENT_BY,"
						 + " START_TIME,"
						 + " IMPLEMENT_DAYS,"
						 + " ORGANIZATION_ID,"
						 + " ORDER_STATUS,"
						 + " ARCHIVED_BY,"
						 + " DISTRIBUTE_DATE,"
						 + " DISTRIBUTE_BY,"
						 + " CREATED_BY,"
						 + " ORDER_TYPE,"
						 + " CHECK_CATEGORY"
						 + ") VALUES ("
						 + " ?, ?, ?, ?, ?, ?, ?,?,?, ?, GETDATE(), ?, ?, ?, ?)";
				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBatchId());
				sqlArgs.add(dto.getCheckLocation());
				sqlArgs.add(dto.getTransNo());
				sqlArgs.add(dto.getImplementBy());
                if(dto.getStartTime().getCalendarValue().equals("")){
                    sqlArgs.add(null);
                } else {
                    sqlArgs.add(dto.getStartTime());
                }
				sqlArgs.add(dto.getImplementDays());
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getOrderStatus());
				sqlArgs.add(dto.getArchivedBy());
//				sqlArgs.add(dto.getDistributeDate());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getOrderType());
				sqlArgs.add(dto.getCheckCategory());
			} else {
				sqlStr = "INSERT INTO "
						 + " AMS_ASSETS_CHECK_HEADER("
						 + " HEADER_ID,"
						 + " BATCH_ID,"
						 + " CHECK_LOCATION,"
						 + " TRANS_NO,"
						 + " IMPLEMENT_BY,"
						 + " START_TIME,"
						 + " IMPLEMENT_DAYS,"
						 + " ORGANIZATION_ID,"
						 + " ORDER_STATUS,"
						 + " ARCHIVED_BY,"
						 + " CREATED_BY,"
						 + " ORDER_TYPE,"
						 + " CHECK_CATEGORY"
						 + ") VALUES ("
						 + " ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
				sqlArgs.add(dto.getHeaderId());
				sqlArgs.add(dto.getBatchId());
				sqlArgs.add(dto.getCheckLocation());
				sqlArgs.add(dto.getTransNo());
				sqlArgs.add(dto.getImplementBy());
                if(dto.getStartTime().getCalendarValue().equals("")){
                    sqlArgs.add(null);
                } else {
                    sqlArgs.add(dto.getStartTime());
                }
				sqlArgs.add(dto.getImplementDays());
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getOrderStatus());
				sqlArgs.add(dto.getArchivedBy());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getOrderType());
				sqlArgs.add(dto.getCheckCategory());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String sqlStr = "";
			if (dto.isFlow2End()) {
				sqlStr = "UPDATE AMS_ASSETS_CHECK_HEADER"
						 + " SET"
						 + " ORDER_STATUS = ?,"
						 + " CHECK_LOCATION = ?,"
						 + " IMPLEMENT_BY = ?,"
						 + " START_TIME = ?,"
						 + " IMPLEMENT_DAYS = ?,"
						 + " ARCHIVED_BY = ?,"
						 + " CHECK_CATEGORY = ?,"
						 + " DISTRIBUTE_DATE = GETDATE(),"
						 + " DISTRIBUTE_BY = ?,"
						 + " LAST_UPDATE_DATE = GETDATE(),"
						 + " LAST_UPDATE_BY = ?"
						 + " WHERE"
						 + " HEADER_ID = ?";

				sqlArgs.add(dto.getOrderStatus());
				sqlArgs.add(dto.getCheckLocation());
				sqlArgs.add(dto.getImplementBy());
                if(dto.getStartTime().getCalendarValue().equals("")){
                    sqlArgs.add(null);
                } else {
                    sqlArgs.add(dto.getStartTime());
                }
				sqlArgs.add(dto.getImplementDays());
				sqlArgs.add(dto.getArchivedBy());
				sqlArgs.add(dto.getCheckCategory());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getHeaderId());
			} else {
				sqlStr = "UPDATE AMS_ASSETS_CHECK_HEADER"
						 + " SET"
						 + " ORDER_STATUS = ?,"
						 + " CHECK_LOCATION = ?,"
						 + " IMPLEMENT_BY = ?,"
						 + " START_TIME = ?,"
						 + " IMPLEMENT_DAYS = ?,"
						 + " ARCHIVED_BY = ?,"
						 + " CHECK_CATEGORY = ?,"
						 + " LAST_UPDATE_DATE = GETDATE(),"
						 + " LAST_UPDATE_BY = ?"
						 + " WHERE"
						 + " HEADER_ID = ?";

				sqlArgs.add(dto.getOrderStatus());
				sqlArgs.add(dto.getCheckLocation());
				sqlArgs.add(dto.getImplementBy());
                if(dto.getStartTime().getCalendarValue().equals("")){
                    sqlArgs.add(null);
                } else {
                    sqlArgs.add(dto.getStartTime());
                }
				sqlArgs.add(dto.getImplementDays());
				sqlArgs.add(dto.getArchivedBy());
				sqlArgs.add(dto.getCheckCategory());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getHeaderId());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AACB.TASK_DESC,"
						+ " AMD.DEPT_NAME CHECK_DEPT_NAME,"
						+ " AACH.HEADER_ID,"
						+ " AACH.BATCH_ID,"
						+ " AACH.IMPLEMENT_DAYS,"
						+ " AACH.TRANS_NO,"
						+ " AACH.START_TIME,"
						+ " AACH.ORDER_STATUS,"
						+ " AACH.IMPLEMENT_BY,"
						+ " AACH.CREATION_DATE,"
						+ " AACH.CREATED_BY,"
						+ " SU.USERNAME CREATED_USER,"
						+ " AACH.DISTRIBUTE_DATE,"
						+ " AACH.DISTRIBUTE_BY,"
						+ " AACH.DOWNLOAD_DATE,"
						+ " AACH.DOWNLOAD_BY,"
						+ " AACH.UPLOAD_DATE,"
						+ " AACH.UPLOAD_BY,"
						+ " AACH.ARCHIVED_DATE,"
						+ " AACH.ARCHIVED_BY,"
						+ " AACH.ORDER_TYPE,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME,"
						+ " AACH.CHECK_CATEGORY,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME,"
						+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
						+ " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION,"
						+ " SU.USERNAME CREATE_USER,"
						+ " SU2.USERNAME IMPLEMENT_USER,"
						+ " SU3.USERNAME DISTRIBUTE_USER,"
						+ " SU4.USERNAME DOWNLOAD_USER,"
						+ " SU5.USERNAME UPLOAD_USER,"
						+ " SU6.USERNAME ARCHIVED_USER,"
						+ " EFV.VALUE STATUS_NAME"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACH,"
						+ " AMS_ASSETS_CHECK_BATCH  AACB,"
						+ " ETS_OBJECT              EO,"
						+ " SF_USER                 SU,"
						+ " SF_USER                 SU2,"
						+ " SF_USER                 SU3,"
						+ " SF_USER                 SU4,"
						+ " SF_USER                 SU5,"
						+ " SF_USER                 SU6,"
						+ " ETS_FLEX_VALUE_SET      EFVS,"
						+ " ETS_FLEX_VALUES         EFV,"
						+ " AMS_MIS_DEPT            AMD"
						+ " WHERE"
						+ " AACH.BATCH_ID = AACB.BATCH_ID"
						+ " AND AACB.CHECK_DEPT *= AMD.DEPT_CODE"
						+ " AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND AACH.CREATED_BY = SU.USER_ID"
						+ " AND AACH.IMPLEMENT_BY *= SU2.USER_ID"
						+ " AND AACH.DISTRIBUTE_BY *= SU3.USER_ID"
						+ " AND AACH.DOWNLOAD_BY *= SU4.USER_ID"
						+ " AND AACH.UPLOAD_BY *= SU5.USER_ID"
						+ " AND AACH.ARCHIVED_BY *= SU6.USER_ID"
						+ " AND AACH.ORDER_STATUS = EFV.CODE"
						+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFVS.CODE = ?"
						+ " AND AACH.HEADER_ID = ?";
		sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 batchId 构造查询数据SQL。
	 * 框架自动生成数据资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER详细信息查询SQLModel，请根据实际需要修改。
	 * @param batchId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBatchIdModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AACH.HEADER_ID,"
						+ " AACH.BATCH_ID,"
						+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
						+ " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION,"
						+ " SU.USER_ID IMPLEMENT_BY,"
						+ " SU.USERNAME IMPLEMENT_USER,"
						+ " SU2.USER_ID ARCHIVED_BY,"
						+ " SU2.USERNAME ARCHIVED_USER,"
						+ " AACH.IMPLEMENT_DAYS,"
						+ " AACH.TRANS_NO,"
						+ " AACH.START_TIME,"
						+ " AACH.ORDER_STATUS,"
						+ " AACH.ORDER_TYPE,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME,"
						+ " AACH.CHECK_CATEGORY,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME,"
						+ " EFV.VALUE STATUS_NAME"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACH,"
						+ " ETS_OBJECT              EO,"
						+ " SF_USER                 SU," //执行人
						+ " SF_USER                 SU2," //归档人
						+ " ETS_FLEX_VALUE_SET      EFVS,"
						+ " ETS_FLEX_VALUES         EFV"
						+ " WHERE"
						+ " AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND AACH.IMPLEMENT_BY *= SU.USER_ID" //可能是暂存的数据，此时没有指定执行人
						+ " AND AACH.ARCHIVED_BY *= SU2.USER_ID"
						+ " AND AACH.ORDER_STATUS = EFV.CODE"
						+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFVS.CODE = ?"
						+ " AND AACH.BATCH_ID = ?"
						+ " ORDER BY AACH.HEADER_ID";
		sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
		sqlArgs.add(batchId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		if (foreignKey.equals("batchId")) {
			sqlModel = getDataByBatchIdModel(dto.getBatchId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 batchId 构造数据删除SQL。
	 * 框架自动生成数据资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER数据删除SQLModel，请根据实际需要修改。
	 * @param batchId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBatchIdModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER"
						+ " WHERE"
						+ " BATCH_ID = ?";
		sqlArgs.add(batchId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据主键删除数据
	 * @return SQLModel
	 */
	public SQLModel getDeleteByPrimaryKeyModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		if (foreignKey.equals("batchId")) {
			sqlModel = getDeleteByBatchIdModel(dto.getBatchId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
        boolean isDptManager = userAccount.isDptAssetsManager();
        boolean isCompanyMgr = userAccount.isComAssetsManager();
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AACH.TRANS_NO,"
							+ " EOCM.COMPANY COMPANY_NAME,"
							+ " SG.GROUP_NAME,"
							+ " EO.WORKORDER_OBJECT_CODE LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_LOCATION CHECK_LOCATION,"
							+ " AACH.CHECK_CATEGORY,"
							+ " dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME,"
							+ " AACH.START_TIME,"
							+ " SU1.USERNAME IMPLEMENT_USER,"
							+ " AACH.IMPLEMENT_DAYS,"
							+ " SU2.USERNAME ARCHIVED_USER,"
							+ " AACH.ARCHIVED_DATE,"
							+ " EFV.VALUE ORDER_STATUS,"
							+ " AACB.TASK_DESC,"
							+ " AACH.HEADER_ID,"
							+ " AACH.CREATED_BY,"
							+ " AACH.CREATION_DATE,"
							+ " AACH.ORDER_TYPE,"
							+ " dbo.APP_GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " ETS_OU_CITY_MAP         EOCM,"
							+ " AMS_ASSETS_CHECK_BATCH  AACB,"
							+ " SF_GROUP                SG,"
							+ " ETS_OBJECT              EO,"
							+ " SF_USER                 SU1,"
							+ " SF_USER                 SU2,"
							+ " ETS_FLEX_VALUES         EFV,"
							+ " ETS_FLEX_VALUE_SET      EFVS"
//							+ " SINO_GROUP_MATCH        SGM,"
//							+ " SINO_MIS_DEPT           SMD"
							+ " WHERE"
							+ " AACH.BATCH_ID = AACB.BATCH_ID"
							+ " AND AACB.GROUP_ID = SG.GROUP_ID" ;
			
							if (!dto.getImplementUser().trim().equals("")) {
								sqlStr += " AND AACH.IMPLEMENT_BY = SU1.USER_ID";
							} else {
								sqlStr += " AND AACH.IMPLEMENT_BY *= SU1.USER_ID";
							}
							if (!dto.getArchivedUser().trim().equals("")) {
								sqlStr += " AND AACH.ARCHIVED_BY = SU2.USER_ID";
							} else {
								sqlStr += " AND AACH.ARCHIVED_BY *= SU2.USER_ID";
							}
							
							sqlStr = sqlStr
							//+ " AND AACH.IMPLEMENT_BY *= SU1.USER_ID"
							//+ " AND AACH.ARCHIVED_BY *= SU2.USER_ID"
							+ " AND AACH.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
							+ " AND AACH.ORDER_STATUS = EFV.CODE"
							+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
//							+ " AND SGM.GROUP_ID = SG.GROUP_ID"
//							+ " AND SMD.DEPT_ID = SGM.DEPT_ID"
							+ " AND EFVS.CODE = ?"
							+ " AND AACH.TRANS_NO LIKE dbo.NVL(?, AACH.TRANS_NO)"
							+ " AND AACH.ORDER_STATUS = dbo.NVL(?, AACH.ORDER_STATUS)"
//							+ " AND AACH.CREATION_DATE >= ISNULL(?, AACH.CREATION_DATE)"
//							+ " AND dateadd(day,-1,AACH.CREATION_DATE) <= ISNULL(?, AACH.CREATION_DATE)"
							+ " AND (? = '' OR AACH.CREATION_DATE >= ?)"
							+ " AND (? = '' OR AACH.CREATION_DATE <= ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU2.USERNAME LIKE ?)"
							+ " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
							+ " AND SU1.USERNAME LIKE dbo.NVL(?, SU1.USERNAME)"
							+ " AND CONVERT(VARCHAR,SG.GROUP_ID) = dbo.NVL(CONVERT(VARCHAR,?), CONVERT(VARCHAR,SG.GROUP_ID))";
			sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getStartDate().toString());
			sqlArgs.add(dto.getStartDate().toString());
			sqlArgs.add(dto.getSQLEndDate().toString());
			sqlArgs.add(dto.getSQLEndDate().toString());
			sqlArgs.add(dto.getArchivedUser());
			sqlArgs.add(dto.getArchivedUser());
			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getImplementUser());
			if("-1".equals(dto.getGroupId()+"")){
				sqlArgs.add("");
			}else{
				sqlArgs.add(dto.getGroupId());
			}
			if (!userAccount.isProvAssetsManager()) {
				sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlStr += " ORDER BY AACH.TRANS_NO DESC";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取某地点下的资产的SQL
	 * @return SQLModel
	 */
	public SQLModel getLocAssetsSaveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		String provinceCode = servletConfig.getProvinceCode();
		String itemCategorys = "('BSC', 'BTS', 'DATA', 'ELEC', 'EXCHG', 'NETOPT', 'TRANS', 'OTHERS')";

		if(!provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)){//非山西做法
            sqlStr = "INSERT INTO"
                    + " AMS_ASSETS_CHECK_LINE("
                    + " HEADER_ID,"
                    + " BATCH_ID,"
                    + " BARCODE,"
                    + " ITEM_CODE,"
                    + " ORGANIZATION_ID,"
                    + " RESPONSIBILITY_USER,"
                    + " RESPONSIBILITY_DEPT,"
                    + " START_DATE,"
                    + " MAINTAIN_USER,"
                    + " SYSTEM_STATUS,"
                    + " MANUFACTURER_ID,"
                    + " IS_SHARE,"
                    + " CONTENT_CODE,"
                    + " CONTENT_NAME,"
                    + " POWER,"
                    + " REPLACE_FLAG,"
                    + " NEW_TAG,"
                    + " CONSTRUCT_STATUS,"
                    + " LNE_ID,"
                    + " LNE_NAME,"
                    + " CEX_ID,"
                    + " CEX_NAME,"
                    + " OPE_ID,"
                    + " OPE_NAME,"
                    + " NLE_ID,"
                    + " NLE_NAME"
					+ " ) ("
					+ " SELECT"
					+ " ?,"
					+ " ?,"
					+ " EII.BARCODE,"
					+ " EII.ITEM_CODE,"
					+ " EII.ORGANIZATION_ID,"
					+ " EII.RESPONSIBILITY_USER,"
					+ " EII.RESPONSIBILITY_DEPT,"
					+ " EII.START_DATE,"
                    + " EII.MAINTAIN_USER,"
                    + " 'Y',"
                    + " EII.MANUFACTURER_ID,"
                    + " EII.IS_SHARE,"
                    + " EII.CONTENT_CODE,"
                    + " EII.CONTENT_NAME,"
                    + " EII.POWER,"
                    + " 'N',"
                    + " '',"
                    + " CONSTRUCT_STATUS,"
                    + " EII.LNE_ID,"
                    + " '' LNE_NAME,"
                    + " EII.CEX_ID,"
                    + " '' CEX_NAME,"
                    + " EII.OPE_ID,"
                    + " '' OPE_NAME,"
                    + " EII.NLE_ID,"
                    + " '' NLE_NAME"
                    + " FROM"
					+ " ETS_ITEM_INFO      EII,"
					+ " AMS_OBJECT_ADDRESS AOA,"
					+ " ETS_OBJECT         EO"
					+ " WHERE"
					+ " EII.ADDRESS_ID = AOA.ADDRESS_ID"
					+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
					+ " AND EO.WORKORDER_OBJECT_NO = ?"
					+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE())"
					+ " AND EII.ITEM_STATUS = ?"
					+ " AND EXISTS("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " ETS_SYSTEM_ITEM ESI"
					+ " WHERE"
					+ " ESI.ITEM_CODE = EII.ITEM_CODE"
					+ " AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY))"
					+ " AND NOT EXISTS("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_ASSETS_CHECK_LINE AACL"
					+ " WHERE"
					+ " AACL.HEADER_ID = ?"
					+ " AND AACL.BARCODE = EII.BARCODE"
					+ " ) )";
			sqlArgs.add(dto.getHeaderId());
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
			sqlArgs.add(dto.getCheckCategory());
			sqlArgs.add(dto.getHeaderId());
		} else {//山西做法
			sqlStr = "INSERT INTO"
					 + " AMS_ASSETS_CHECK_LINE("
					 + " HEADER_ID,"
					 + " BATCH_ID,"
					 + " BARCODE,"
					 + " ITEM_CODE,"
					 + " ORGANIZATION_ID,"
					 + " RESPONSIBILITY_USER,"
					 + " RESPONSIBILITY_DEPT,"
					 + " START_DATE,"
					 + " MAINTAIN_USER,"
					 + " SYSTEM_STATUS"
					 + " ) ("
					 + " SELECT"
					 + " ?,"
					 + " ?,"
					 + " EII.BARCODE,"
					 + " EII.ITEM_CODE,"
					 + " EII.ORGANIZATION_ID,"
					 + " EII.RESPONSIBILITY_USER,"
					 + " EII.RESPONSIBILITY_DEPT,"
					 + " EII.START_DATE,"
					 + " EII.MAINTAIN_USER,"
					 + " 'Y'"
					 + " FROM"
					 + " ETS_ITEM_INFO      EII,"
					 + " AMS_OBJECT_ADDRESS AOA,"
					 + " ETS_OBJECT         EO"
					 + " WHERE"
					 + " EII.ADDRESS_ID = AOA.ADDRESS_ID"
					 + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
					 + " AND EO.WORKORDER_OBJECT_NO = ?"
					 + " AND  " + SyBaseSQLUtil.isNotNull("EII.BARCODE") + ""
					 + " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE())"
					 + " AND EII.ITEM_STATUS = ?"

					 + " AND EXISTS("//加上设备分类限制
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSTEM_ITEM ESI"
					 + " WHERE"
					 + " ESI.ITEM_CODE = EII.ITEM_CODE"
					 + " AND ESI.ITEM_CATEGORY = dbo.NVL(LTRIM(?), ESI.ITEM_CATEGORY))"

					 + " AND EXISTS("//加入需要下载的限制，排除不扫描的设备，如线缆等
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSTEM_ITEM ESI"
					 + " WHERE"
					 + " EII.ITEM_CODE = ESI.ITEM_CODE"
					 + " AND ESI.ITEM_CATEGORY IN" + itemCategorys + ")"

					 + " AND NOT EXISTS("//已经存在的不需要插入
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_CHECK_LINE AACL"
					 + " WHERE"
					 + " AACL.HEADER_ID = ?"
					 + " AND AACL.BARCODE = EII.BARCODE)";

			sqlArgs.add(dto.getHeaderId());
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
			sqlArgs.add(dto.getCheckCategory());
			sqlArgs.add(dto.getHeaderId());
			if(!dto.getCostCenterCode().equals("")){
				sqlStr += " AND EXISTS(" //加入成本中心限制
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_COST_DEPT_MATCH ACDM"
					+ " WHERE"
					+ " ACDM.DEPT_CODE = EII.RESPONSIBILITY_DEPT"
					+ " AND ACDM.COST_CENTER_CODE = ?)";
				sqlArgs.add(dto.getCostCenterCode());
			}
			sqlStr += ")";
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：构造获取建单组别对应的部门的SQL
	 * @return SQLModel
	 */
	public SQLModel getMapDeptModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " DEPT_CODE"
						+ " FROM"
						+ " SF_GROUP_MATCH SGM"
						+ " WHERE"
						+ " SGM.GROUP_ID = ?";
		sqlArgs.add(dto.getGroupId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造下发盘点工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getDistributeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_CHECK_HEADER"
						+ " SET"
						+ " ORDER_STATUS = ?,"
						+ " DISTRIBUTE_DATE = GETDATE(),"
						+ " DISTRIBUTE_BY = ?"
						+ " WHERE"
						+ " BATCH_ID = ?";
		sqlArgs.add(dto.getOrderStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getBatchId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取指定单据下需要盘点的标签号
	 * @param includeAdded boolean 是否包含PDA扫描后，工单上传新加入的设备。
	 * @return SQLModel
	 */
	public SQLModel getOrderBarcodesModel(boolean includeAdded) {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AACL.BARCODE,"
						+ " AACL.ITEM_CODE,"
						+ " AACL.ORGANIZATION_ID,"
						+ " AACL.RESPONSIBILITY_USER,"
						+ " AACL.RESPONSIBILITY_DEPT"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_LINE AACL"
						+ " WHERE"
						+ " AACL.HEADER_ID = ?";
		if (!includeAdded) {
			sqlStr += " AND  AACL.SYSTEM_STATUS='Y'";
		}
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取判断所在地点是否有早期未归档工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getHasPreviousOrderModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OBJECT EO"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_NO = ?"
						+ " AND EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACH,"
						+ " AMS_ASSETS_CHECK_BATCH  AACB"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
						+ " AND AACH.BATCH_ID = AACB.BATCH_ID"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACH.HEADER_ID <> ?)"
						+ " AND AACH.ORDER_STATUS <> ?"
						+ " AND AACH.ORDER_STATUS <> ?"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACH.CHECK_CATEGORY = ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACB.COST_CENTER_CODE = ?))";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getCheckLocation());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(AssetsDictConstant.CHK_STATUS_CANCELED);
		sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
		sqlArgs.add(dto.getCheckCategory());
		sqlArgs.add(dto.getCheckCategory());
		sqlArgs.add(dto.getCostCenterCode());
		sqlArgs.add(dto.getCostCenterCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
