package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;

/**
 * <p>Title: AdminConfirmModel</p>
 * <p>Description:程序自动生成SQL构造器“AdminConfirmModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TdAdminConfirmModel extends AMSSQLProducer {

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS_TD 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransLineDTO 本次操作的数据
	 */
	public TdAdminConfirmModel(SfUserDTO userAccount, TdAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造获取个人待确认资产的SQL
	 * @throws SQLModelException
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String deptCode = "('";
			DTOSet depts = userAccount.getPriviDeptCodes();
			if (depts != null && depts.getSize() > 0) {
				AmsMisDeptDTO dept = null;
				int deptCount = depts.getSize();
				for (int i = 0; i < deptCount; i++) {
					dept = (AmsMisDeptDTO) depts.getDTO(i);
					deptCode += dept.getDeptCode() + "', '";
				}
			}
			deptCode += "')";
			TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AATH.TRANS_ID,"
							+ " AATH.TRANS_NO,"
							+ " AATH.TRANSFER_TYPE,"
							+ " EFA.ASSET_NUMBER,"
							+ " EFA.ASSETS_DESCRIPTION,"
							+ " EFA.MODEL_NUMBER,"
							+ " ISNULL(EFA.CURRENT_UNITS, 1) CURRENT_UNITS,"
							+ " EFA.COST,"
							+ " EFA.DEPRN_COST,"
							+ " EFA.DATE_PLACED_IN_SERVICE,"
							+ " AATL.BARCODE,"
							+ " dbo.NVL(AATL.NEW_BARCODE, AATL.BARCODE) NEW_BARCODE,"
							+ " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
							+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
							+ " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
							+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
							+ " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
							+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
							+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
							+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
							+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
							+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
							+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
							+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
							+ " AOAO.ADDRESS_ID OLD_ADDRESS_ID,"
							+ " AOAN.ADDRESS_ID,"
							+ " AATH.FROM_ORGANIZATION_ID,"
							+ " AATH.TO_ORGANIZATION_ID,"
							+ " AATL.REMARK"
							+ " FROM"
							+ " ETS_ITEM_INFO           EII,"
							+ " ETS_ITEM_MATCH_TD          EIM,"
							+ " ETS_FA_ASSETS_TD           EFA,"
							+ " TD_ASSETS_TRANS_HEADER AATH,"
							+ " AMS_MIS_EMPLOYEE        AMEO,"
							+ " AMS_MIS_DEPT            AMDO,"
							+ " ETS_OBJECT              EOO,"
							+ " AMS_OBJECT_ADDRESS      AOAO,"
							+ " TD_ASSETS_TRANS_LINE   AATL,"
							+ " AMS_MIS_EMPLOYEE        AMEN,"
							+ " AMS_MIS_DEPT            AMDN,"
							+ " ETS_OBJECT              EON,"
							+ " AMS_OBJECT_ADDRESS      AOAN"
							+ " WHERE"
							+ " EFA.ASSET_ID = EIM.ASSET_ID"
							+ " AND EIM.SYSTEMID = EII.SYSTEMID"
							+ " AND EII.BARCODE = AATL.BARCODE"
							+ " AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
							+ " AND EOO.WORKORDER_OBJECT_NO = AOAO.OBJECT_NO"
							+ " AND  " + SyBaseSQLUtil.isNotNull("AATL.OLD_RESPONSIBILITY_USER") + " "
							+ " AND  " + SyBaseSQLUtil.isNotNull("AATL.OLD_RESPONSIBILITY_DEPT") + " "
							+ " AND AATL.ASSIGNED_TO_LOCATION = EON.WORKORDER_OBJECT_NO"
							+ " AND EON.WORKORDER_OBJECT_NO = AOAN.OBJECT_NO"
							+ " AND AATL.RESPONSIBILITY_USER = AMEN.EMPLOYEE_ID"
							+ " AND AATL.RESPONSIBILITY_DEPT = AMDN.DEPT_CODE"
							+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
							+ " AND ((AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE = ?) OR (AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE <> ?))"
							+ " AND AATL.RESPONSIBILITY_DEPT IN " + deptCode
							+ " AND AATL.CONFIRM_DATE  " + SyBaseSQLUtil.isNullNoParam() + " "
							+ " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
							+ " AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)"
							+ " AND AATH.APPROVED_DATE >= dbo.NVL(?, AATH.APPROVED_DATE)"
							+ " AND AATH.APPROVED_DATE <= dbo.NVL(?, AATH.APPROVED_DATE)"
							+ " ORDER BY AATH.TRANS_NO DESC";
			sqlArgs.add(AssetsDictConstant.APPROVED);
			sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
			sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
			sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成固定资产当前信息(EAM) AMS_FA_ASSETS多条数据信息查询SQLModel，请根据实际需要修改。
	 * @param checkedAssets 选中的资产
	 * @return SQLModel 返回资产导出用SQL
	 * @throws SQLModelException
	 */
	public SQLModel getExpCheckedAssetsModel(String[] checkedAssets) throws SQLModelException {
		SQLModel sqlModel = getPageQueryModel();
		String barcodes = ArrUtil.arrToSqlStr(checkedAssets);
		String sqlStr = sqlModel.getSqlStr();
		sqlStr = "SELECT * FROM (" + sqlStr +
				 ") TMP_V WHERE TMP_V.BARCODE IN (" + barcodes + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
