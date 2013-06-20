package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
public class ItemAdminConfirmModel extends AMSSQLProducer {
    private boolean isAdminConfirm=false;

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 */
	public ItemAdminConfirmModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
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
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String sqlStr = "SELECT AATH.TRANS_ID,\n" +
                    "       AATH.TRANS_NO,\n" +
                    "       AATH.TRANSFER_TYPE,\n" +
                    "       '' ASSET_NUMBER,\n" +
                    "       ESI.ITEM_NAME ASSETS_DESCRIPTION,\n" +
                    "       ESI.ITEM_SPEC MODEL_NUMBER,\n" +
                    "       '' CURRENT_UNITS,\n" +
                    "       '' COST,\n" +
                    "       '' DEPRN_COST,\n" +
                    "       '' DATE_PLACED_IN_SERVICE,\n" +
                    "       AATL.BARCODE,\n" +
                    "       dbo.NVL(AATL.NEW_BARCODE, AATL.BARCODE) NEW_BARCODE,\n" +
                    "       EOO.WORKORDER_OBJECT_NO OLD_LOCATION,\n" +
                    "       EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,\n" +
                    "       AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,\n" +
                    "       AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,\n" +
                    "       AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,\n" +
                    "       AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,\n" +
                    "       EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,\n" +
                    "       EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,\n" +
                    "       AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,\n" +
                    "       AMEN.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
                    "       AMDN.DEPT_CODE RESPONSIBILITY_DEPT,\n" +
                    "       AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                    "       AOAO.ADDRESS_ID OLD_ADDRESS_ID,\n" +
                    "       AOAN.ADDRESS_ID,\n" +
                    "       AATH.FROM_ORGANIZATION_ID,\n" +
                    "       AATH.TO_ORGANIZATION_ID,\n" +
                    "       AATL.REMARK\n" +
                    "  FROM ETS_ITEM_INFO           EII,\n" +
                    "       ETS_SYSTEM_ITEM         ESI,\n" +
                    "       AMS_ASSETS_TRANS_HEADER AATH,\n" +
                    "       AMS_MIS_EMPLOYEE        AMEO,\n" +
                    "       AMS_MIS_DEPT            AMDO,\n" +
                    "       ETS_OBJECT              EOO,\n" +
                    "       AMS_OBJECT_ADDRESS      AOAO,\n" +
                    "       AMS_ASSETS_TRANS_LINE   AATL,\n" +
                    "       AMS_MIS_EMPLOYEE        AMEN,\n" +
                    "       AMS_MIS_DEPT            AMDN,\n" +
                    "       ETS_OBJECT              EON,\n" +
                    "       AMS_OBJECT_ADDRESS      AOAN\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EII.BARCODE = AATL.BARCODE\n" +
                    "   AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO\n" +
                    "   AND EOO.WORKORDER_OBJECT_NO = AOAO.OBJECT_NO\n" +
                    "   AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID\n" +
                    "   AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE\n" +
                    "   AND AATL.ASSIGNED_TO_LOCATION = EON.WORKORDER_OBJECT_NO\n" +
                    "   AND EON.WORKORDER_OBJECT_NO = AOAN.OBJECT_NO\n" +
                    "   AND AATL.RESPONSIBILITY_USER = AMEN.EMPLOYEE_ID\n" +
                    "   AND AATL.RESPONSIBILITY_DEPT = AMDN.DEPT_CODE" +
                    "   AND AATH.TRANS_TYPE='ITEM-RED'\n" +
                    "   AND AATL.TRANS_ID = AATH.TRANS_ID\n" +
                    "   AND AATL.LINE_STATUS = ?";
            sqlArgs.add(AssetsDictConstant.APPROVED);
            if (!isAdminConfirm) {
                sqlStr += "   AND AATL.RESPONSIBILITY_USER = ?";
                sqlArgs.add(userAccount.getEmployeeId());
            } else {
                sqlStr += "   AND AATL.RESPONSIBILITY_DEPT IN " + deptCode;
            }
            sqlStr +=
                    "   AND (AATL.CONFIRM_DATE IS NULL OR AATL.CONFIRM_DATE = '')\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)\n" +
                    "   AND (?='' OR  AATH.APPROVED_DATE>=?)\n" +
                    "   AND (?='' OR  AATH.APPROVED_DATE<=?)\n" +
                    " ORDER BY AATH.TRANS_NO DESC";
            sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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

    public void setAdminConfirm(boolean adminConfirm) {
        isAdminConfirm = adminConfirm;
    }
}