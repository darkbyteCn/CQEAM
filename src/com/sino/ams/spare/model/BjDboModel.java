package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DateException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class BjDboModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public BjDboModel(SfUserDTO userAccount, AmsItemAllocateHDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_ITEM_ALLOCATE_H("
                + " TRANS_ID,"
                + " TRANS_NO,"
                + " TRANS_TYPE,"
                + " TRANS_STATUS,"
                + " FROM_USER,"
                + " TO_USER,"
                + " FROM_DEPT,"
                + " TO_DEPT,"
                + " FROM_OBJECT_NO,"
                + " TO_OBJECT_NO,"
                + " FROM_ORGANIZATION_ID,"
                + " TO_ORGANIZATION_ID,"
                + " TRANS_DATE,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " REMARK "
                + ") VALUES ("
                + "  ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?)";
        sqlArgs.add(amsItemTransH.getTransId());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getTransDate());
        sqlArgs.add(amsItemTransH.getCreatedBy());
        sqlArgs.add(amsItemTransH.getRemark());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据更新SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ITEM_ALLOCATE_H"
				+ " SET"
				+ " TRANS_NO = ?,"
				+ " TRANS_TYPE = ?,"
				+ " TRANS_STATUS = ?,"
				+ " FROM_USER = ?,"
				+ " TO_USER = ?,"
				+ " FROM_DEPT = ?,"
				+ " TO_DEPT = ?,"
				+ " FROM_OBJECT_NO = ?,"
				+ " TO_OBJECT_NO = ?,"
				+ " FROM_ORGANIZATION_ID = ?,"
				+ " TO_ORGANIZATION_ID = ?,"
				+ " TRANS_DATE = GETDATE(),"
				+ " LAST_UPDATE_DATE = GETDATE(),"
				+ " LAST_UPDATE_BY = ?,"
				+ "   REMARK=? "
				+ " WHERE"
				+ " TRANS_ID = ?";

	    sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getFromUser());
        sqlArgs.add(amsItemTransH.getToUser());
        sqlArgs.add(amsItemTransH.getFromDept());
        sqlArgs.add(amsItemTransH.getToDept());
        sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsItemTransH.getRemark());
        sqlArgs.add(amsItemTransH.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据删除SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_ITEM_TRANS_H"
				+ " WHERE"
				+ " TRANS_ID = ?";
		sqlArgs.add(amsItemTransH.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H数据详细信息查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       AITH.FROM_OBJECT_NO,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
                "       AITH.REMARK,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO2.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION TO_OBJECT_LOCATION,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OBJECT         EO2,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.FROM_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND AITH.FROM_ORGANIZATION_ID *= EOCM.ORGANIZATION_ID\n" +
                "   AND AITH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'   \n" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H页面翻页查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
		String sqlStr = "SELECT AITH.TRANS_ID,\n" +
				"       AITH.TRANS_NO,\n" +
				"       AITH.CREATED_BY,\n" +
				"       AITH.ATTRIBUTE4,\n" +
				"       AITH.CREATION_DATE CREATION_DATE,\n" +
				"       AITH.TRANS_DATE TRANS_DATE,\n" +
				"       SUV.USERNAME CREATED_USER,\n" +
				"       EO.WORKORDER_OBJECT_NAME,\n" +
				"       EFV.VALUE ORDER_STATUS_NAME\n" +
				"  FROM AMS_ITEM_TRANS_H   AITH,\n" +
				"       ETS_OBJECT         EO,\n" +
				"       SF_USER_V          SUV,\n" +
				"       ETS_FLEX_VALUES    EFV,\n" +
				"       ETS_FLEX_VALUE_SET EFVS\n" +
				" WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
				"   AND AITH.CREATED_BY = SUV.USER_ID\n" +
				"   AND AITH.TRANS_STATUS = EFV.CODE\n" +
				"   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
				"   AND EFVS.CODE = 'ORDER_STATUS'\n" +
			    "   AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_NO LIKE ?)\n" +
				"   AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_STATUS = ?)\n";
		if (amsItemTransH.getTransType().equals("FXSQ")) {
			sqlStr += " AND AITH.FROM_ORGANIZATION_ID = ?\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.TRANS_TYPE = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE >= ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE <= ?)\n" +
					" ORDER BY AITH.CREATION_DATE DESC";
		} else {
			sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_NO LIKE ?)\n" +
					"   AND (? = -1 OR TO_ORGANIZATION_ID = ?)\n" +
					"   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.TRANS_TYPE = ?)\n" +
					"   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE >= ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE <= ?)\n" +
					" ORDER BY AITH.CREATION_DATE DESC";
		}

		sqlArgs.add(amsItemTransH.getTransNo());
		sqlArgs.add(amsItemTransH.getTransNo());
		sqlArgs.add(amsItemTransH.getTransStatus());
		sqlArgs.add(amsItemTransH.getTransStatus());
		if (amsItemTransH.getTransType().equals("FXSQ")) {
			sqlArgs.add(sfUser.getOrganizationId());
		} else {
			sqlArgs.add(amsItemTransH.getToObjectNo());
			sqlArgs.add(amsItemTransH.getToObjectNo());
			sqlArgs.add(sfUser.getOrganizationId());
			sqlArgs.add(sfUser.getOrganizationId());
		}
		sqlArgs.add(amsItemTransH.getTransType());
		sqlArgs.add(amsItemTransH.getTransType());
		try {
			sqlArgs.add(amsItemTransH.getFromDate());
			sqlArgs.add(amsItemTransH.getFromDate());

			SimpleCalendar sc = amsItemTransH.getToDate();
			SimpleDate sd = sc.getSimpleDate();
			if (!sd.getDateValue().equals("")) {
				sd.adjust(DateConstant.DATE, 1);
				sc.setDate(sd);
			}
			sqlArgs.add(sc);
			sqlArgs.add(sc);
		} catch (CalendarException e) {
			throw new SQLModelException(e);
		} catch (DateException e) {
			e.printStackTrace();
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


    /**
     * 功能：框架自动生成备件事务头表(AMS) AMS_ITEM_TRANS_H页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getbjdbQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE CREATION_DATE,\n" +
                "       AITH.TRANS_DATE TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_NO LIKE ?)\n" +
				"   AND ( " + SyBaseSQLUtil.isNull() + " OR TRANS_STATUS = ?)\n";
            sqlStr +=  "   AND (? = -1 OR FROM_ORGANIZATION_ID = ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.TRANS_TYPE = ?)\n" +
					"   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE >= ?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + " OR AITH.CREATION_DATE <= ?)\n" +
                    " ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());

            SimpleCalendar sc = amsItemTransH.getToDate();
            SimpleDate sd = sc.getSimpleDate();
            if (!sd.getDateValue().equals("")) {
                sd.adjust(DateConstant.DATE, 1);
                sc.setDate(sd);
            }
            sqlArgs.add(sc);
            sqlArgs.add(sc);
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        } catch (DateException e) {
            e.printStackTrace();
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDeleteByTransIdModel(String transId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_ITEM_ALLOCATE_D"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(transId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataByTransIdModel(String transId){//明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="SELECT AIAD.DETAIL_ID,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.BARCODE,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_SPARE_CATEGORY  ASSC,\n" +
                "       AMS_SPARE_INFO      ASI,\n" +
                "       ETS_OBJECT          EO,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_SPARE_VENDORS   ASV\n" +
                " WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                "       AND ASSC.BARCODE = AIAD.BARCODE\n" +
                "       AND AIAH.FROM_ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "       AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "       AND    ASI.OBJECT_NO=AIAH.FROM_OBJECT_NO\n" +
                "       AND ASI.BARCODE = AIAD.BARCODE\n" +
                "       AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND EO.OBJECT_CATEGORY = '71'\n" +
                "       AND AIAD.TRANS_ID = ?";
		sqlArgs.add(transId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	 public SQLModel getModel(String transId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="SELECT " +
				"       AIAH.TRANS_NO," +
				"       ASSC.ITEM_NAME,\n" +
				"       AIAD.BARCODE,\n" +
				"       ASSC.ITEM_SPEC,\n" +
				"       dbo.APP_GET_VENDOR_NAME(ASSC.VENDOR_ID) VENDOR_NAME,\n" +
				"       dbo.APP_GET_ORGNIZATION_NAME(AIAH.FROM_ORGANIZATION_ID) FROM_ORGANIZATION_NAME,\n" +
				"       dbo.APP_GET_ORGNIZATION_NAME(AIAH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME,\n" +
				"       AIAH.CREATION_DATE,\n" +
				"       ASI.QUANTITY ONHAND_QTY,\n" +
				"       ASSC.SPARE_USAGE,\n" +
				"       AIAD.DETAIL_ID,\n" +
				"       AIAD.QUANTITY\n" +
				"  FROM AMS_ITEM_ALLOCATE_D AIAD,\n" +
				"       AMS_SPARE_CATEGORY  ASSC,\n" +
				"       AMS_SPARE_INFO      ASI,\n" +
				"       ETS_OBJECT          EO,\n" +
				"       AMS_ITEM_ALLOCATE_H AIAH\n" +
				" WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
				"   AND ASSC.BARCODE = AIAD.BARCODE\n" +
				"   AND AIAH.FROM_ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
				"   AND EO.OBJECT_CATEGORY = '71'\n" +
				"   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				"   AND ASI.BARCODE = AIAD.BARCODE\n" +
				"   AND AIAD.TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	  public SQLModel getUpdateHStatusModel(String transId){        //明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr ="UPDATE AMS_ITEM_ALLOCATE_H SET TRANS_STATUS = 'ALLOTING' WHERE TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	 public SQLModel updateStatusModel(String status) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) dtoParameter;
		String sqlStr ="UPDATE AMS_ITEM_TRANS_H\n" +
				"   SET TRANS_STATUS = ?, LAST_UPDATE_DATE = GETDATE(), LAST_UPDATE_BY = ?\n" +
				" WHERE TRANS_ID = ?";
		sqlArgs.add(status);
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(amsItemTransH.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
