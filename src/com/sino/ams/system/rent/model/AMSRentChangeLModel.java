package com.sino.ams.system.rent.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-10
 * Time: 18:26:50
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeLModel  extends AMSSQLProducer {
    	public AMSRentChangeLModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
    public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String sqlStr = "INSERT INTO"
							+ " AMS_RENTASSETS_TRANS_L("
							+ " LINE_ID,"
							+ " TRANS_ID,"
							+ " BARCODE,"
							+ " OLD_LOCATION,"
							+ " OLD_RESPONSIBILITY_USER,"
							+ " OLD_RESPONSIBILITY_DEPT,"
							+ " OLD_DEPRECIATION_ACCOUNT,"
							+ " OLD_FA_CATEGORY_CODE,"
							+ " ASSIGNED_TO_LOCATION,"
							+ " RESPONSIBILITY_USER,"
							+ " RESPONSIBILITY_DEPT,"
							+ " DEPRECIATION_ACCOUNT,"
							+ " FA_CATEGORY_CODE,"
							+ " LINE_STATUS,"
							+ " LINE_TRANS_DATE,"
							+ " LINE_REASON, "
							+ " REMARK,"
							+ " NET_UNIT,"
							+ " ASSET_ID,"
							+ " SOFT_INUSE_VERSION,"
							+ " SOFT_DEVALUE_VERSION,"
							+ " DEPRECIATION,"
							+ " DEPRN_COST,"
							+ " PREPARE_DEVALUE," +
                    "        USER_NAME," +
                    "        ITEM_QTY"
							+ ") VALUES ("
							+ " NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getOldLocation());
			sqlArgs.add(dto.getOldResponsibilityUser());
			sqlArgs.add(dto.getOldResponsibilityDept());
			sqlArgs.add(dto.getOldDepreciationAccount());
			sqlArgs.add(dto.getOldFaCategoryCode());
			sqlArgs.add(dto.getAssignedToLocation());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getDepreciationAccount());
			sqlArgs.add(dto.getFaCategoryCode());
			sqlArgs.add(dto.getLineStatus());
			sqlArgs.add(dto.getLineTransDate());
			sqlArgs.add(dto.getLineReason());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(dto.getNetUnit());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSoftInuseVersion());
			sqlArgs.add(dto.getSoftDevalueVersion());
			sqlArgs.add(dto.getDepreciation());
			sqlArgs.add(dto.getDeprnCost());
			sqlArgs.add(dto.getPrepareDevalue());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getItemQty());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
   private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String transType = dto.getTransType();
        sqlStr="SELECT ARTL.BARCODE,\n" +
                "       ARTL.TRANS_ID,\n" +
                "       ARTL.RESPONSIBILITY_USER,\n" +
                "       ARTL.USER_NAME,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ARTL.ITEM_QTY,\n" +
                "       ARTL.LINE_TRANS_DATE,\n" +
                "       ARTL.REMARK,\n" +
                "       AME.USER_NAME RESPONSIBILITY_USER_NAME," +
                "       ARTL.ASSIGNED_TO_LOCATION," +
                "       EO.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME\n" +
                "FROM   AMS_RENTASSETS_TRANS_L ARTL,\n" +
                "       ETS_ITEM_INFO          EII,\n" +
                "       ETS_SYSTEM_ITEM        ESI,\n" +
                "       AMS_MIS_EMPLOYEE       AME," +
                "       ETS_OBJECT EO  \n" +
                "WHERE  ARTL.BARCODE = EII.BARCODE\n" +
                "       AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND AME.EMPLOYEE_ID = ARTL.RESPONSIBILITY_USER\n" +
                "      AND EO.WORKORDER_OBJECT_NO *= ARTL.ASSIGNED_TO_LOCATION" +
                "       AND ARTL.TRANS_ID = ?";
        sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}
    public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " LINE_ID = ?";
		sqlArgs.add(dto.getLineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
