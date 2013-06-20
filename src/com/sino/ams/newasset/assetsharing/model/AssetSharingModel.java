package com.sino.ams.newasset.assetsharing.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingHeaderDTO;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * 资产共享SQLMODEL
 *
 * @author xiaohua
 */
public class AssetSharingModel extends AMSSQLProducer {
    private AssetSharingHeaderDTO headerDTO;

    public AssetSharingModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDTO = (AssetSharingHeaderDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " AATH.TRANS_NO,AATH.TRANS_ID,"
                + " AATH.TRANS_TYPE,"
                + " AATH.TRANS_STATUS,dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                + " AATH.FROM_ORGANIZATION_ID,"
                + " EOCM.COMPANY,"
                + " AATH.FROM_DEPT,"
                + " AMD.DEPT_NAME,"
                //+ " AATH.CREATED_BY,CONVERT(VARCHAR(100),AATH.CREATION_DATE,105) CREATION_DATE, "
                + " SU.USERNAME USER_NAME,"
                + " AATH.CREATED_BY,AATH.CREATION_DATE  CREATION_DATE, "
                + " AATH.CREATED_REASON REMARK,"
                + " SU.EMAIL,SU.MOBILE_PHONE"
                + " FROM"
                + " AMS_ASSETS_TRANS_HEADER AATH,SF_USER SU,ETS_OU_CITY_MAP EOCM,"
                + " AMS_MIS_DEPT AMD"
                + " WHERE AATH.TRANS_TYPE='" + AssetSharingConstant.ASSET_SHARE_CODE + "'"
                + " AND AATH.CREATED_BY *= SU.USER_ID"
                + " AND AATH.FROM_ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
                + " AND AATH.FROM_DEPT*=AMD.DEPT_CODE"
                + " AND ( ? = '' OR AATH.TRANS_STATUS = '" + AssetsDictConstant.APPROVED + "')"
                + " AND ( ? = '' OR AATH.TRANS_NO LIKE ?)"
                + " AND ( ? IS NULL OR ? = '' OR AATH.CREATION_DATE >= ? )"
                + " AND ( ? IS NULL OR ? = '' OR dateadd(day,-1,AATH.CREATION_DATE) <= ? )";
        ArrayList args = new ArrayList();
        args.add(headerDTO.getTransStatus());
        args.add(this.headerDTO.getTransNo());
        args.add(this.headerDTO.getTransNo());
        try {
            args.add(headerDTO.getStartDate());
            args.add(headerDTO.getStartDate());
            args.add(headerDTO.getStartDate());
            args.add(headerDTO.getEndDate());
            args.add(headerDTO.getEndDate());
            args.add(headerDTO.getEndDate());
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }

        sqlStr += " AND AATH.CREATED_BY = ?";
        args.add(userAccount.getUserId());
        sqlStr += " ORDER BY AATH.CREATION_DATE DESC ";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel saveLineModel(AssetSharingLineDTO line) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO AMS_ASSETS_TRANS_LINE("
                + "	TRANS_ID,LINE_ID,BARCODE, REJECT_TYPE) "
                + "	VALUES(?,?,?,?)";
        ArrayList args = new ArrayList();
        args.add(line.getTransId());
        args.add(line.getLineId());
        args.add(line.getBarcode());
        // args.add(line.getLineStatus());
        args.add(line.getShareStatus());
        sqlModel.setArgs(args);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel saveHeaderModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO " + " AMS_ASSETS_TRANS_HEADER("
                + " TRANS_ID," + " TRANS_NO," + " TRANS_TYPE,"
                + " TRANS_STATUS," + " FROM_ORGANIZATION_ID," + " FROM_DEPT,"
                + " FROM_PERSON," + " CREATED_REASON," + " CREATION_DATE,"
                + " CREATED_BY,EMERGENT_LEVEL"
                // + " RECEIVED_USER"
                + ") VALUES (" + " ?,?,?,?,?,?, ?, ?, GETDATE(), ?, ?)";
        ArrayList args = new ArrayList();
        args.add(headerDTO.getTransId());
        args.add(headerDTO.getTransNo());
        args.add(headerDTO.getTransType());
        args.add(headerDTO.getTransStatus());
        args.add(headerDTO.getFromOrganizationId());
        args.add(headerDTO.getFromDept());
        args.add(headerDTO.getCheckUser());
        args.add(headerDTO.getRemark());
        args.add(headerDTO.getCreatedBy());
        args.add(headerDTO.getEmergentLevel());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel getHeaderModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " AATH.TRANS_NO,AATH.TRANS_ID,"
                + " AATH.TRANS_TYPE,"
                + " AATH.TRANS_STATUS,dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                + " AATH.FROM_ORGANIZATION_ID,"
                + " EOCM.COMPANY,"
                + " AATH.FROM_DEPT,"
                + " AATH.FROM_GROUP GROUP_ID,"
                + " AMD.DEPT_NAME,"
                + " AATH.CREATED_BY,"
                + " SU.USERNAME USER_NAME,"
                + " AATH.CREATED_REASON REMARK,"
                + " SU.EMAIL,SU.MOBILE_PHONE,SU.USERNAME CURR_USER_NAME,AATH.CREATION_DATE,AATH.EMERGENT_LEVEL"
                + " FROM"
                + "	AMS_ASSETS_TRANS_HEADER AATH,SF_USER SU,ETS_OU_CITY_MAP EOCM,"
                + " AMS_MIS_DEPT AMD" + " WHERE AATH.TRANS_ID=?"
                + " AND AATH.CREATED_BY*=SU.USER_ID"
                + " AND AATH.FROM_ORGANIZATION_ID*=EOCM.ORGANIZATION_ID"
                + " AND AATH.FROM_DEPT*=AMD.DEPT_CODE"
                + " AND AATH.TRANS_TYPE='ASS-SHARE'";
        ArrayList args = new ArrayList();
        args.add(headerDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel getLinesModel(String headerId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + "	  AATL.BARCODE,"
                + "	  AATL.REJECT_TYPE SHARE_STATUS,dbo.APP_GET_FLEX_VALUE(AATL.REJECT_TYPE, 'SHARE_STATUS') SHARE_STATUS_DESC,"
                + "	  AATL.LINE_ID,"
                + "   EII.ITEM_CODE,"
                + "   ESI.ITEM_NAME,ESI.ITEM_SPEC,"
                + "   EII.ADDRESS_ID,"
                + "   EO.LOCATION,EO.WORKORDER_OBJECT_LOCATION,"
                + "   EII.RESPONSIBILITY_USER,"
                + "   AME.USER_NAME USER_NAME,"
                + "   AME.EMPLOYEE_NUMBER,"
//				+ "   SU.USERNAME USER_NAME,SU.EMPLOYEE_NUMBER,"
                + "   EII.CONTENT_CODE,"
                + "   EII.CONTENT_NAME,"
                + "   EII.START_DATE"
                + " FROM"
                + "	AMS_ASSETS_TRANS_LINE AATL ,"
                + " ETS_ITEM_INFO EII,"
                + " ETS_SYSTEM_ITEM ESI,"
                + " AMS_MIS_EMPLOYEE AME,"
//				+ " SF_USER SU,"
                + " AMS_OBJECT_ADDRESS AOA,"
                + " ETS_OBJECT EO"
                + " WHERE"
                + "	AATL.TRANS_ID = ? "
                + " AND AATL.BARCODE = EII.BARCODE"
                + " AND EII.ITEM_CODE = ESI.ITEM_CODE"
                + " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID  \n"
//				+ " AND CONVERT(VARCHAR,EII.RESPONSIBILITY_USER)*=CONVERT(VARCHAR,SU.USER_ID)"
                + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO";
        ArrayList args = new ArrayList();
        args.add(headerId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel updateHeaderModel(String headerId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = " UPDATE  AMS_ASSETS_TRANS_HEADER  SET "
                + " TRANS_STATUS=?, FROM_ORGANIZATION_ID=?,"
                + " FROM_DEPT=?, CREATED_REASON=?,"
                + " LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY=?"
                + " WHERE TRANS_ID=? AND TRANS_TYPE=?";
        ArrayList args = new ArrayList();
        args.add(headerDTO.getTransStatus());
        args.add(headerDTO.getFromOrganizationId());
        args.add(headerDTO.getFromDept());
        args.add(headerDTO.getRemark());
        args.add(headerDTO.getLastUpdateBy());
        args.add(headerDTO.getTransId());
        args.add(headerDTO.getTransType());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    /**
     * 删除行数据
     *
     * @param headerId 删除(true)或是失效(false)譔數據
     * @return
     */
    public SQLModel deleteLinesModel(String headerId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE  AMS_ASSETS_TRANS_LINE  WHERE TRANS_ID=?";
        ArrayList args = new ArrayList();
        args.add(headerId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel deleteHeaderModel(boolean reallyDelete) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE  AMS_ASSETS_TRANS_HEADER  WHERE TRANS_ID=?";
        ArrayList args = new ArrayList();
        args.add(headerDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel updateHeaderStatus() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE " +
                "AMS_ASSETS_TRANS_HEADER   " +
                "SET   " +
                "TRANS_STATUS = ?, " +
                "LAST_UPDATE_DATE = GETDATE(), " +
                "LAST_UPDATE_BY = ?" +
                "WHERE TRANS_ID=?";
        ArrayList args = new ArrayList();

        args.add(headerDTO.getTransStatus());
        args.add(userAccount.getUserId());
        args.add(headerDTO.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel getOrderCompleteModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE " +
                "AMS_ASSETS_TRANS_HEADER   " +
                "SET   " +
                "TRANS_STATUS = ?, " +
                "LAST_UPDATE_DATE = GETDATE(), " +
                "LAST_UPDATE_BY = ?, " +
                "APPROVED_DATE = GETDATE(), " +
                "APPROVED_BY = ?, " +
                "TRANS_DATE = GETDATE() " +
                "WHERE TRANS_ID=?";
        ArrayList args = new ArrayList();

        args.add(headerDTO.getTransStatus());
        args.add(userAccount.getUserId());
        args.add(userAccount.getUserId());
        args.add(headerDTO.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel updateAssetStatus(AssetSharingLineDTO line) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE ETS_ITEM_INFO  SET SHARE_STATUS=? WHERE BARCODE=?";
        ArrayList args = new ArrayList();
        args.add(line.getShareStatus());
        args.add(line.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel getPageQueryHeaderModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " AATH.TRANS_NO,AATH.TRANS_ID,"
                + " AATH.TRANS_TYPE,"
                + " AATH.TRANS_STATUS,dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                + " AATH.FROM_ORGANIZATION_ID,"
                + " EOCM.COMPANY,"
                + " AATH.FROM_DEPT,"
                + " AMD.DEPT_NAME,"
                + " AATH.CREATED_BY,CONVERT(VARCHAR(100),AATH.CREATION_DATE,105) CREATION_DATE,"
                + " SU.USERNAME USER_NAME,"
                + " AATH.CREATED_REASON REMARK,"
                + " SU.EMAIL,SU.MOBILE_PHONE,AATH.EMERGENT_LEVEL"
                + " FROM"
                + "   AMS_ASSETS_TRANS_HEADER AATH,SF_USER SU,ETS_OU_CITY_MAP EOCM,"
                + "  AMS_MIS_DEPT AMD" + " WHERE   AATH.TRANS_TYPE='" + AssetSharingConstant.ASSET_SHARE_CODE + "'"
                + "  AND AATH.CREATED_BY*=SU.USER_ID"
                + " AND AATH.FROM_ORGANIZATION_ID*=EOCM.ORGANIZATION_ID"
                + " AND AATH.FROM_DEPT*=AMD.DEPT_CODE"
                + " AND (?='' OR AATH.TRANS_NO LIKE ?)"
                + " AND (?='' OR AATH.CREATION_DATE<=?)"
                + " AND (?='' OR AATH.CREATION_DATE>=?)"
                + " ORDER BY AATH.CREATION_DATE DESC ";
        ArrayList args = new ArrayList();
        args.add(this.headerDTO.getTransNo());
        args.add(this.headerDTO.getTransNo());
        try {
            args.add(headerDTO.getEndDate());
            args.add(headerDTO.getEndDate());
            args.add(headerDTO.getStartDate());
            args.add(headerDTO.getStartDate());
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(args);
        return sqlModel;
    }

    public SQLModel deleteReserveModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ASSETS_RESERVED WHERE TRANS_ID = ?";
        sqlArgs.add(this.headerDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateItemStatusModel(String barcode, String shareStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "update ETS_ITEM_INFO set SHARE_STATUS=? where BARCODE=?";
        sqlArgs.add(shareStatus);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

  //判断是否属于实物部门
    public SQLModel getIsSpecialGroupModel(int fromGroup) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT *\n" +
                "  FROM SF_GROUP      SG,\n" +
                "       SF_USER       SU,\n" +
                "       SF_USER_RIGHT SUR\n" +
                " WHERE SG.GROUP_ID = SUR.GROUP_ID\n" +
                "   AND SU.USER_ID = SUR.USER_ID\n" +
                "   AND SG.SPECIALITY_DEPT = 'Y'\n" +
                "   AND SU.USER_ID = ?" +
                "   AND SG.GROUP_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(fromGroup);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
