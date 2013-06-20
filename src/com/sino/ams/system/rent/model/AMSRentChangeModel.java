package com.sino.ams.system.rent.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-10
 * Time: 9:56:53
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeModel extends AMSSQLProducer {
    public AMSRentChangeModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }
    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO)dtoParameter;
			String sqlStr = "SELECT AATH.TRANS_ID,\n" +
                    "       AATH.TRANS_NO,\n" +
                    "       AATH.TRANS_TYPE,\n" +
                    "       AATH.TRANSFER_TYPE,\n" +
                    "       AATH.TRANS_STATUS,\n" +
                    "       AATH.FROM_ORGANIZATION_ID,\n" +
                    "       AATH.LOSSES_NAME,\n" +
                    "       AATH.LOSSES_DATE,\n" +
                    "       EOCM.COMPANY,\n" +
                    "       dbo.NVL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,\n" +
                    "       AATH.RECEIVED_USER,\n" +
                    "       AATH.CREATION_DATE,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'transfer_type') TRANS_TYPE_VALUE,\n" +
                    "       SU.USERNAME CREATED\n" +
                    "FROM   AMS_RENTASSETS_TRANS_H AATH,\n" +
                    "       AMS_MIS_DEPT           AMD,\n" +
                    "       ETS_OU_CITY_MAP        EOCM,\n" +
                    "       SF_USER                SU\n" +
                    "WHERE  AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "       AND AATH.FROM_DEPT *= AMD.DEPT_CODE \n" +
                    "       AND AATH.CREATED_BY = SU.USER_ID\n" +
                    "       AND AATH.TRANS_STATUS IN('SAVE_TEMP','REJECTED') " +
                    "       AND AATH.CREATED_BY = ?\n" +
                    "       AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_NO LIKE ?)\n" +
                    "       AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)\n" +
                    "       AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)\n"
//                    + "ORDER  BY AATH.TRANSFER_TYPE,\n" +
//                    "          AATH.TRANS_NO"
                    ;
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getTransNo());
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
    public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_RENTASSETS_TRANS_H("
							+ " TRANS_ID,"
							+ " TRANS_NO,"
							+ " TRANS_TYPE,"
							+ " TRANSFER_TYPE,"
							+ " TRANS_STATUS,"
							+ " FROM_ORGANIZATION_ID,"
							+ " TO_ORGANIZATION_ID,"
							+ " FROM_DEPT,"
							+ " FROM_GROUP,"
							+ " TO_DEPT,"
							+ " FROM_OBJECT_NO,"
							+ " TO_OBJECT_NO,"
							+ " FROM_PERSON,"
							+ " TO_PERSON,"
							+ " CREATED_REASON,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " RECEIVED_USER,"
							+ " TRANS_DATE,"
							+ " FA_CONTENT_CODE,"
							+ " LOSSES_NAME,"
							+ " LOSSES_DATE," +
                    "         TO_GROUP "
							+ ") VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + SyBaseSQLUtil.getCurDate() + ", ?, ?, ?, ?, ?, ?,?)";
			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getFromOrganizationId());
			sqlArgs.add(dto.getToOrganizationId());
			sqlArgs.add(userAccount.getDeptCode());
			sqlArgs.add(dto.getFromGroup());
			sqlArgs.add(dto.getToDept());
			sqlArgs.add(dto.getFromObjectNo());
			sqlArgs.add(dto.getToObjectNo());
			sqlArgs.add(dto.getFromPerson());
			sqlArgs.add(dto.getToPerson());
			sqlArgs.add(dto.getCreatedReason());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getReceivedUser());
			sqlArgs.add(dto.getTransDate());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getLossesName());
			sqlArgs.add(dto.getLossesDate());
			sqlArgs.add(dto.getToGroup());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "SELECT AATH.TRANS_ID,\n" +
                "       AATH.TRANS_NO,\n" +
                "       AATH.FROM_DEPT,\n" +
                "       AATH.TO_DEPT,\n" +
                "       AATH.TRANS_TYPE,\n" +
                "       AATH.TRANSFER_TYPE,\n" +
                "       AATH.TRANS_STATUS,\n" +
                "       AATH.TRANS_DATE,\n" +
                "       AATH.CREATION_DATE,\n" +
                "       AATH.CREATED_BY,\n" +
                "       AATH.LAST_UPDATE_DATE,\n" +
                "       AATH.LAST_UPDATE_BY,\n" +
                "       AATH.CANCELED_DATE,\n" +
                "       AATH.CANCELED_REASON,\n" +
                "       AATH.CREATED_REASON,\n" +
                "       AATH.APPROVED_DATE,\n" +
                "       AATH.FROM_ORGANIZATION_ID,\n" +
                "       AATH.FROM_GROUP,\n" +
                "       AATH.FA_CONTENT_CODE,\n" +
                "       AATH.LOSSES_NAME,\n" +
                "       AATH.LOSSES_DATE,\n" +
                "       AMD.DEPT_NAME FROM_DEPT_NAME,\n" +
                "       AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,\n" +
                "       AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,\n" +
                "       AMS_PUB_PKG.GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,\n" +
                "       SU.USERNAME CREATED,\n" +
                "       SU.EMAIL,\n" +
                "       SU.MOBILE_PHONE PHONE_NUMBER,\n" +
                "       SG.GROUP_NAME FROM_GROUP_NAME,\n" +
                "       0 GROUP_PROP,\n" +
                "       EOCM.BOOK_TYPE_CODE,\n" +
                "       EOCM.BOOK_TYPE_NAME,\n" +
                "       EOCM.COMPANY FROM_COMPANY_NAME,\n" +
                "       AMD2.DEPT_NAME USER_DEPT_NAME,\n" +
                "       AATH.TO_ORGANIZATION_ID,\n" +
                "       EOCM2.COMPANY TO_COMPANY_NAME,\n" +
                "       AATH.TO_DEPT,\n" +
                "       AMD3.DEPT_NAME TO_DEPT_NAME,\n" +
                "       AATH.TO_GROUP,\n" +
                "       SG1.GROUPNAME TO_GROUP_NAME\n" +
                "  FROM AMS_RENTASSETS_TRANS_H AATH,\n" +
                "       ETS_OU_CITY_MAP        EOCM,\n" +
                "       AMS_MIS_DEPT           AMD,\n" +
                "       SF_GROUP               SG,\n" +
                "       SF_USER                SU,\n" +
                "       AMS_MIS_EMPLOYEE       AME,\n" +
                "       AMS_MIS_DEPT           AMD2,\n" +
                "       ETS_OU_CITY_MAP        EOCM2,\n" +
                "       AMS_MIS_DEPT           AMD3,\n" +
                "       SF_GROUP               SG1\n" +
                " WHERE AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND AATH.FROM_DEPT *= AMD.DEPT_CODE\n" +
                "   AND AATH.FROM_GROUP *= SG.GROUP_ID\n" +
                "   AND AATH.CREATED_BY = SU.USER_ID\n" +
                "   AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                "   AND AME.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                "   AND AATH.TO_DEPT *= AMD3.DEPT_CODE\n" +
                "   AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID\n" +
                "   AND AATH.TO_GROUP *= SG1.GROUP_ID\n" +
                "   AND TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
      public SQLModel updateStatusModel(String status) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsTransHeaderDTO amsItemTransH = (AmsAssetsTransHeaderDTO) dtoParameter;
        String sqlStr ="UPDATE AMS_RENTASSETS_TRANS_H\n" +
                "   SET TRANS_STATUS = ?, LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + ", LAST_UPDATE_BY = ?\n" +
                " WHERE TRANS_ID = ?";
        sqlArgs.add(status);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
      public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsTransHeaderDTO amsItemTransH = (AmsAssetsTransHeaderDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_RENTASSETS_TRANS_L"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel deleteHModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsTransHeaderDTO amsItemTransH = (AmsAssetsTransHeaderDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_RENTASSETS_TRANS_H"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "UPDATE AMS_RENTASSETS_TRANS_H"
							+ " SET"
							+ " TRANS_NO = ?,"
							+ " TRANS_STATUS = ?,"
							+ " TO_ORGANIZATION_ID = ?,"
							+ " FROM_DEPT = ?,"
							+ " TO_DEPT = ?,"
							+ " FROM_OBJECT_NO = ?,"
							+ " TO_OBJECT_NO = ?,"
							+ " FROM_PERSON = ?,"
							+ " TO_PERSON = ?,"
							+ " CREATED_REASON = ?,"
							+ " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + ","
							+ " LAST_UPDATE_BY = ?,"
							+ " RECEIVED_USER = ?,"
							+ " FA_CONTENT_CODE = ?,"
							+ " TRANS_DATE = ?,"
							+ " LOSSES_DATE = ?,"
							+ " LOSSES_NAME = ?," +
                              "  TO_GROUP=?"
							+ " WHERE"
							+ " TRANS_ID = ?";

			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(AssetsDictConstant.IN_PROCESS);
			sqlArgs.add(dto.getToOrganizationId());
			sqlArgs.add(dto.getFromDept());
			sqlArgs.add(dto.getToDept());
			sqlArgs.add(dto.getFromObjectNo());
			sqlArgs.add(dto.getToObjectNo());
			sqlArgs.add(dto.getFromPerson());
			sqlArgs.add(dto.getToPerson());
			sqlArgs.add(dto.getCreatedReason());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getReceivedUser());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getTransDate());
			sqlArgs.add(dto.getLossesDate());
			sqlArgs.add(dto.getLossesName());
			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getToGroup());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
