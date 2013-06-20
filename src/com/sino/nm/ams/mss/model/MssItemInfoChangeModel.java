package com.sino.nm.ams.mss.model;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-27
 * Time: 14:07:12
 * To change this template use File | Settings | File Templates.
 */
public class MssItemInfoChangeModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public MssItemInfoChangeModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
         public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
            EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.ITEM_ID,\n" +
                "       EII.ITEM_CATEGORY1,\n" +
                "       EII.ITEM_CATEGORY2,\n" +
                "       EII.BARCODE,\n" +
                "       EII.ITEM_NAME,\n" +
                "       EII.MSS_BARCODE,\n" +
                "       EII.ITEM_SPEC,\n" +
                "       EII.RESPONSIBILITY_USER,\n" +
                "       EII.MAINTAIN_USER,\n" +
                "       EII.RESPONSIBILITY_DEPT,\n" +
                "       EII.ADDRESS WORKORDER_OBJECT_NAME,\n" +
                "       EII.ITEM_STATUS,\n" +
                "       EII.USER_LEVEL,\n" +
                "       EII.SECURE_LEVEL,\n" +
                "       EII.COMPLETENESS_LEVEL,\n" +
//                "       DECODE(EII.ENABLED, 'Y', 'га', 'Зё')ENABLED,\n" +
                "		(CASE WHEN EII.ENABLED = 'Y' THEN 'га' WHEN EII.ENABLED = 'N' THEN 'Зё' END) AS ENABLED,\n"+
                "       EII.USE_BY_SYSTEM," +
                "       EII.MEMORY," +
                "       EII.CPU," +
                "       EII.IP_ADDRESS," +
                "       EII.DISK_INFORMATION," +
                "       EII.SYSTEM_NAME," +
                "       EII.TRUSTEESHIP_TYPE," +
                "       EII.UPDATE_VERSION" +
                "  FROM EAM_MSS_ITEM_INFO EII\n" +
//                " WHERE (? IS NULL OR EII.ITEM_NAME LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.ITEM_SPEC LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.MSS_BARCODE LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.ADDRESS LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.RESPONSIBILITY_USER LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.MAINTAIN_USER LIKE ?)\n" +
//                "   AND (? IS NULL OR EII.ENABLED LIKE ?)";
                " WHERE (EII.ITEM_NAME LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.ITEM_SPEC LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.MSS_BARCODE LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.ADDRESS LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.RESPONSIBILITY_USER LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.MAINTAIN_USER LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")\n" +
                "   AND (EII.ENABLED LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() +")";

             sqlArgs.add(dto.getItemName());
             sqlArgs.add(dto.getItemName());
             sqlArgs.add(dto.getItemName());
             
             sqlArgs.add(dto.getItemSpec());
             sqlArgs.add(dto.getItemSpec());
             sqlArgs.add(dto.getItemSpec());
             
               sqlArgs.add(dto.getBarcode1());
               sqlArgs.add(dto.getBarcode1());
               sqlArgs.add(dto.getBarcode1());
               
             sqlArgs.add(dto.getWorkorderObjectName());
             sqlArgs.add(dto.getWorkorderObjectName());
             sqlArgs.add(dto.getWorkorderObjectName());
             
             sqlArgs.add(dto.getResponsibilityUserName());
             sqlArgs.add(dto.getResponsibilityUserName());
             sqlArgs.add(dto.getResponsibilityUserName());
             
             sqlArgs.add(dto.getMaintainUser());
             sqlArgs.add(dto.getMaintainUser());
             sqlArgs.add(dto.getMaintainUser());
             
             sqlArgs.add(dto.getDisable());
             sqlArgs.add(dto.getDisable());
             sqlArgs.add(dto.getDisable());
             
               sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
         return sqlModel;
         }
     public SQLModel getHasUnknowModel(String sysId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT 'A' FROM AMS_ITEM_DEPT_LOG T WHERE T.DEPT_CODE = ?";
        sqlArgs.add(sysId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
       public SQLModel updateLogModel(EtsItemInfoDTO dto)throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "   SET RESPONSIBILITY_DEPT = dbo.NVL(?, RESPONSIBILITY_DEPT),\n" +
                "       RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),\n" +
                "       ADDRESS_ID          = dbo.NVL(?, ADDRESS_ID),\n" +
                "       MAINTAIN_USER       = dbo.NVL(?, MAINTAIN_USER),\n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ?\n" +
                " WHERE SYSTEMID = ?";
        sqlArgs.add(dto.getNewResponsibilityDept());
        sqlArgs.add(dto.getNewResponsibilityUser());
        sqlArgs.add(dto.getNewAddressId());
        sqlArgs.add(dto.getNewMaintainUser());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemId());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
     public SQLModel updateMSSModel(EtsItemInfoDTO dto)throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE EAM_MSS_ITEM_INFO \n" +
                "   SET ITEM_STATUS = dbo.NVL(?, ITEM_STATUS),\n" +
                "       RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),\n" +
                "       ADDRESS          = dbo.NVL(?, ADDRESS),\n" +
                "       MAINTAIN_USER       = dbo.NVL(?, MAINTAIN_USER),\n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ?\n" +
                " WHERE ITEM_ID = ?";
        sqlArgs.add(dto.getNewResponsibilityDept());
        sqlArgs.add(dto.getNewResponsibilityUser());
        sqlArgs.add(dto.getNewAddressId());
        sqlArgs.add(dto.getNewMaintainUser());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemId());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
     public SQLModel enableMSSModel(EtsItemInfoDTO dto)throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE EAM_MSS_ITEM_INFO\n" +
                "   SET ENABLED ='Y',\n" +
                "       DISABLE_DATE = NULL,\n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ?\n" +
                " WHERE ITEM_ID = ?";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemId());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
     public SQLModel disableMSSModel(EtsItemInfoDTO dto)throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE EAM_MSS_ITEM_INFO\n" +
                "   SET ENABLED ='N',\n" +
                "       DISABLE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       LAST_UPDATE_BY      = ?\n" +
                " WHERE ITEM_ID = ?";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(dto.getSystemId());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    public SQLModel getCreateHistoryModel(EtsItemInfoDTO dto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
						+ " AMS_ITEM_INFO_HISTORY("
						+ " HISTORY_ID,"
						+ " BARCODE,"
						+ " ADDRESS_ID,"
						+ " ITEM_CODE,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " ORDER_NO,"
						+ " ORDER_CATEGORY,"
						+ " ORDER_DTL_URL,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " REMARK"
						+ ") VALUES ("
						+ " NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";

		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getNewAddressId());
		sqlArgs.add(dto.getItemCode());
		sqlArgs.add(dto.getNewResponsibilityUser());
		sqlArgs.add(dto.getNewResponsibilityDept());
		sqlArgs.add("");
		sqlArgs.add("4");
		sqlArgs.add("/servlet/com.sino.ams.newasset.servlet.AmsItemInfoChangeServlet");
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(dto.getRemark());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
     public SQLModel insertLogModel(String oldDept,String newDept)throws  SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_DEPT_LOG\n" +
                "  (LOG_ID,\n" +
                "   OLD_DEPT_CODE,\n" +
                "   NEW_DEPT_CODE,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   DEPT_CODE)\n" +
                "   VALUES(NEWID(),?,?,GETDATE(),?,?)";
        sqlArgs.add(oldDept);
        sqlArgs.add(newDept);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(oldDept);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    public SQLModel insertLogInfoModel(String oldDept,String newDept)throws  SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_DEPT_LOG_INFO\n" +
                "  (LOG_ID,\n" +
                "   OLD_DEPT_CODE,\n" +
                "   NEW_DEPT_CODE,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   DEPT_CODE)\n" +
                "   VALUES(NEWID(),?,?,GETDATE(),?,?)";
        sqlArgs.add(oldDept);
        sqlArgs.add(newDept);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(oldDept);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
