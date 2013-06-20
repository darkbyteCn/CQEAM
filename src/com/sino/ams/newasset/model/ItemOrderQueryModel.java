package com.sino.ams.newasset.model;
 import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

public class ItemOrderQueryModel extends AMSSQLProducer {

	/**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 */
	public ItemOrderQueryModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = " SELECT"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANS_TYPE,"
						+ " AATH.TRANS_STATUS,"
						+ " AATH.FROM_DEPT,"
						+ " AATH.TO_DEPT,"
						+ " AATH.FROM_OBJECT_NO,"
						+ " AATH.TO_OBJECT_NO,"
						+ " AATH.TRANS_DATE,"
						+ " AATH.TO_ORGANIZATION_ID,"
						+ " AATH.CREATION_DATE,"
						+ " AATH.CREATED_BY,"
						+ " AATH.LAST_UPDATE_DATE,"
						+ " AATH.LAST_UPDATE_BY,"
						+ " AATH.FROM_PERSON,"
						+ " AATH.CANCELED_DATE,"
						+ " AATH.CANCELED_REASON,"
						+ " AATH.TO_PERSON,"
						+ " AATH.CREATED_REASON,"
						+ " AATH.APPROVED_DATE,"
						+ " AATH.FROM_ORGANIZATION_ID,"
						+ " AATH.FROM_GROUP,"
						+ " AATH.TO_GROUP,"
						+ " AMD2.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
						+ " SU.USERNAME CREATED,"
						+ " EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,"
						+ " EOCM.COMPANY TO_COMPANY_NAME,"
						+ " AMD.DEPT_NAME TO_DEPT_NAME,"
						+ " SG.GROUP_NAME FROM_GROUP_NAME,"
						+ " SU3.USERNAME APPROVED_USER,"
						+ " SU2.USERNAME RECEIVED_USER_NAME"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " ETS_OBJECT              EO,"
						+ " ETS_OU_CITY_MAP         EOCM,"
						+ " AMS_MIS_DEPT            AMD2,"
						+ " AMS_MIS_DEPT            AMD,"
						+ " SF_GROUP                SG,"
						+ " SF_USER                 SU,"
						+ " SF_USER                 SU2,"
						+ " SF_USER                 SU3"
						+ " WHERE"
						+ " AATH.CREATED_BY = SU.USER_ID"
						+ " AND AATH.FROM_GROUP = SG.GROUP_ID"
						+ " AND AATH.FROM_DEPT = AMD2.DEPT_CODE(+)"
						+ " AND AATH.TO_DEPT = AMD.DEPT_CODE(+)"
						+ " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE(+)"
						+ " AND AATH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)"
						+ " AND AATH.RECEIVED_USER = SU2.USER_ID(+)"
						+ " AND AATH.APPROVED_BY = SU3.USER_ID(+)"
						+ " AND TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AATH.TRANS_ID,"
							+ " AATH.TRANS_NO,"
							+ " AATH.TRANS_TYPE,"
							+ " AATH.TRANSFER_TYPE,"
							+ " AATH.TRANS_STATUS,"
							+ " '' TRANSFER_TYPE_DESC,"
							+ " AATH.FROM_ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " dbo.NVL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,"
							+ " AATH.RECEIVED_USER,"
							+ " AATH.CREATION_DATE,"
							+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
							+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
							+ " SU.USERNAME CREATED"
							+ " FROM"
							+ " AMS_ASSETS_TRANS_HEADER AATH,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " ETS_OU_CITY_MAP    EOCM,"
							+ " SF_USER            SU"
							+ " WHERE"
							+ " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
							+ " AND AATH.CREATED_BY = SU.USER_ID"
							+ " AND AATH.TRANS_TYPE = ?"
							+ " AND (?= '' OR AATH.CREATION_DATE >= ?)\n"
                            + " AND (?= '' OR AATH.CREATION_DATE <= ?)"
							+ " AND (?='' OR AATH.TRANSFER_TYPE = dbo.NVL(?, AATH.TRANSFER_TYPE))";
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransferType());

			/*    2009-09-21  libo 因需要严格控制查询权限而注释。*/
			/*if (!userAccount.isProvinceUser()) {
				sqlStr += " AND AATH.FROM_ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
			}*/

			sqlStr = sqlStr
					 + " AND AATH.TRANS_STATUS = dbo.NVL(?, AATH.TRANS_STATUS)"
					 + " AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)";
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getTransNo());
//			if(dto.getControlPrivi().equals("Y")){//加入权限控制，该权限控制由栏目定义提供的参数进行
//				sqlStr += " AND ((AATH.TRANS_STATUS IN ('SAVE_TEMP', 'CANCELED')"
//					+ " AND AATH.CREATED_BY = ?)"
//					+ " OR EXISTS ("
//					+ " SELECT"
//					+ " NULL"
//					+ " FROM"
//					+ " SF_ACT_LOG SAL"
//					+ " WHERE"
//					+ " AATH.TRANS_ID = SAL.APP_ID"
//					+ " AND AATH.TRANS_NO = SAL.APPLY_NUMBER"
//					+ " AND (SAL.CUR_USERID = ? OR SAL.COMPLETE_USER = ?)))";
//				sqlArgs.add(userAccount.getUserId());
//				sqlArgs.add(userAccount.getUserId());
//				sqlArgs.add(userAccount.getUserId());
//			}

			/*    2009-09-21  libo 因需要严格控制查询权限而添加。*/
//			if (userAccount.isComAssetsManager()) {
//				sqlStr += " AND (AATH.FROM_ORGANIZATION_ID = ? OR AATH.TO_ORGANIZATION_ID = ?)";
//				sqlArgs.add(userAccount.getOrganizationId());
//				sqlArgs.add(userAccount.getOrganizationId());
//			} else {
//				sqlStr += " AND (AATH.FROM_ORGANIZATION_ID = ? OR AATH.TO_ORGANIZATION_ID = ?)";
//				sqlArgs.add(userAccount.getOrganizationId());
//				sqlArgs.add(userAccount.getOrganizationId());
//			}
            sqlStr += " AND AATH.CREATED_BY = ?";
            sqlArgs.add(userAccount.getUserId());

			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) {
				sqlStr = sqlStr
						 + " ORDER BY"
						 + " AATH.TRANSFER_TYPE,"
						 + " AATH.CREATION_DATE DESC";
			} else {
				sqlStr += " ORDER BY AATH.CREATION_DATE DESC";
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}