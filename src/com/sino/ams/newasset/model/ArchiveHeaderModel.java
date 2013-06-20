package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ArchiveHeaderModel extends AMSSQLProducer {
	public ArchiveHeaderModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造盘点工单归档的SQL
	 * @return SQLModel
	 */
	public SQLModel getChkOrderArchiveModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_CHECK_HEADER"
						+ " SET"
						+ " ORDER_STATUS = ?,"
						+ " DIFFERENCE_REASON = ?,"
						+ " ARCHIVED_DATE = GETDATE(),"
						+ " ARCHIVED_BY = ?"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(dto.getOrderStatus());
		sqlArgs.add(dto.getDifferenceReason());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
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
						+ " AACH.DIFFERENCE_REASON,"
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
						+ " EFV.VALUE STATUS_NAME,"
						+ " AACH.CHECK_CATEGORY,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME"
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
						+ " AND AACH.CREATED_BY *= SU.USER_ID"
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
	 * 功能：框架自动生成资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String sqlStr = "SELECT"
							+ " AACB.BATCH_NO,"
							+ " AACB.TASK_DESC,"
							+ " AACH.HEADER_ID,"
							+ " AACH.TRANS_NO,"
							+ " SG.GROUP_NAME,"
							+ " AACH.CREATED_BY,"
							+ " SU.USERNAME CREATED_USER,"
							+ " AACH.CREATION_DATE,"
							+ " AACH.START_TIME,"
							+ " AACH.IMPLEMENT_DAYS,"
							+ " SU2.USERNAME IMPLEMENT_USER,"
							+ " EO.WORKORDER_OBJECT_CODE LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_LOCATION CHECK_LOCATION,"
							+ " AACH.CHECK_CATEGORY,"
							+ " dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME,"
							+ " AACH.ARCHIVED_DATE,"
							+ " SU3.USERNAME ARCHIVED_USER,"
							+ " EOCM.COMPANY COMPANY_NAME,"
							+ " EFV.VALUE ORDER_STATUS"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " ETS_OU_CITY_MAP         EOCM,"
							+ " AMS_ASSETS_CHECK_BATCH  AACB,"
							+ " SF_GROUP                SG,"
							+ " ETS_OBJECT              EO,"
							+ " SF_USER                 SU,"
							+ " SF_USER                 SU2,"
							+ " SF_USER                 SU3,"
							+ " ETS_FLEX_VALUES         EFV,"
							+ " ETS_FLEX_VALUE_SET      EFVS"
							+ " WHERE"
							+ " AACH.BATCH_ID = AACB.BATCH_ID"
							+ " AND AACB.GROUP_ID = SG.GROUP_ID"
							+ " AND AACH.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
							+ " AND AACH.ORDER_STATUS = EFV.CODE"
							+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
							+ " AND EFVS.CODE = ?"
							+ " AND AACH.ORDER_STATUS = ?"
							+ " AND AACB.CREATED_BY = SU.USER_ID"
							+ " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
							+ " AND AACH.IMPLEMENT_BY = SU2.USER_ID"
							+ " AND SU2.USERNAME LIKE dbo.NVL(?, SU2.USERNAME)"
							+ " AND AACH.ARCHIVED_BY *= SU3.USER_ID"
							+ " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACB.TASK_DESC LIKE ?)"
							+ " AND AACH.ORGANIZATION_ID = ?"
							+ " AND AACH.TRANS_NO LIKE dbo.NVL(?, AACH.TRANS_NO)"
							+ " AND AACH.START_TIME >= dbo.NVL(?, AACH.START_TIME)"
							+ " AND (? ='' OR AACH.START_TIME <= ?)"
							+ " AND (AACH.ARCHIVED_BY = ?"
							+ " OR (AACH.ARCHIVED_BY =-1"
							+ " AND EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " SF_USER_RIGHT SUR,"
							+ " SF_ROLE SR"
							+ " WHERE"
							+ " AACB.GROUP_ID = SUR.GROUP_ID"
							+ " AND SUR.ROLE_ID = SR.ROLE_ID"
							+ " AND SR.ROLE_NAME LIKE ?"
							+ " AND SUR.USER_ID = ?)))";

			sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getTaskDesc());
			sqlArgs.add(dto.getTaskDesc());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(AssetsDictConstant.ACHIEVE_ROLE);
			sqlArgs.add(userAccount.getUserId());
			sqlStr += " ORDER BY AACH.HEADER_ID DESC";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取判断待归档工单所在地点是否有早期未归档工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getHasPreviousOrderModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACP,"
						+ " AMS_ASSETS_CHECK_HEADER AACH"
						+ " WHERE"
						+ " AACH.HEADER_ID = ?"
						+ " AND AACH.CHECK_LOCATION = AACP.CHECK_LOCATION"
						+ " AND AACP.SCANOVER_DATE < AACH.SCANOVER_DATE"
						+ " AND AACP.ORDER_STATUS = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取将系统中存在但PDA提交的设备中不存在的条码全部归档到在途库的SQL
	 * @return SQLModel
	 */
	public SQLModel getArcNotScanBarcodesModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ADDRESS_ID       = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM ("
						+ " SELECT"
						+ " BARCODE"
						+ " FROM"
						+ " ETS_ITEM_INFO      EII2,"
						+ " AMS_OBJECT_ADDRESS AOA,"
						+ " ETS_OBJECT         EO"
						+ " WHERE"
						+ " EII2.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " AND EO.WORKORDER_OBJECT_NO = ?"
						+ " MINUS"
						+ " SELECT"
						+ " AACL.BARCODE"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_LINE AACL"
						+ " WHERE"
						+ " AACL.HEADER_ID = ?) TMP"
						+ " WHERE"
						+ " TMP.BARCODE = EII.BARCODE)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getTmpAddressId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getCheckLocation());
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 将盘点工单退回
	 * @return
	 */
	public SQLModel getUpdateBackOrderModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr =	"UPDATE AMS_ASSETS_CHECK_HEADER\n" +
						"   SET ORDER_STATUS = 'DOWNLOADED'\n" + 
						" WHERE TRANS_NO = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getTransNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取盘点地点下的标签号(用于归档时)SQL
	 * @return SQLModel
	 */
	public SQLModel getLocBarcodesModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " EII.ITEM_CODE,"
						+ " EII.ORGANIZATION_ID,"
						+ " EII.RESPONSIBILITY_USER,"
						+ " EII.RESPONSIBILITY_DEPT"
						+ " FROM"
						+ " ETS_ITEM_INFO           EII,"
						+ " AMS_OBJECT_ADDRESS      AOA,"
						+ " ETS_OBJECT              EO,"
						+ " AMS_ASSETS_CHECK_HEADER AACH"
						+ " WHERE"
						+ " EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
						+ " AND AACH.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
