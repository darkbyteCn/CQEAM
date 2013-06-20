package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.SpareOrderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareOrderModel extends AMSSQLProducer {

	public SpareOrderModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			SpareOrderDTO dto = (SpareOrderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT TMP_ORDER.*\n" +
                    "  FROM (SELECT AITH.CREATED_BY,\n" +
                    "               dbo.APP_GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                    "               AITH.CREATION_DATE,\n" +
                    "               AITH.FROM_DEPT,\n" +
                    "               AITH.FROM_OBJECT_NO,\n" +
                    "               AITH.FROM_ORGANIZATION_ID,\n" +
                    "               AITH.FROM_USER,\n" +
                    "               AITH.LAST_UPDATE_BY,\n" +
                    "               AITH.LAST_UPDATE_DATE,\n" +
                    "               AITH.RCV_USER,\n" +
                    "               AITH.SOURCE_ID,\n" +
                    "               AITH.TO_DEPT,\n" +
                    "               AITH.TO_OBJECT_NO,\n" +
                    "               AITH.TO_ORGANIZATION_ID,\n" +
                    "               AITH.TO_USER,\n" +
                    "               AITH.TRANS_DATE,\n" +
                    "               AITH.TRANS_ID,\n" +
                    "               AITH.TRANS_NO,\n" +
                    "               AITH.TRANS_STATUS,\n" +
                    "               AITH.TRANS_TYPE,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               dbo.APP_GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                    "          FROM AMS_ITEM_TRANS_H AITH,\n" +
                    "               SF_USER          SU\n" +
                    "         WHERE AITH.TRANS_NO LIKE dbo.NVL(?, AITH.TRANS_NO)\n" +
                    "               AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)\n" +
                    "               AND AITH.CREATED_BY = SU.USER_ID\n" +
                    "               AND AITH.TRANS_TYPE = dbo.NVL(?, AITH.TRANS_TYPE)\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE >= ?)\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE <= ?)\n" +
                    "               AND (? =-1 OR SU.ORGANIZATION_ID = ?)\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR SU.USERNAME LIKE ?)\n" +
                    "               AND EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                    "                       AMS_SPARE_CATEGORY AMSC\n" +
                    "                 WHERE AITL.TRANS_ID = AITH.TRANS_ID\n" +
                    "                       AND AMSC.BARCODE = AITL.BARCODE\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.VENDOR_ID = ?))\n" +
                    "        UNION ALL\n" +
                    "        SELECT AIAH.CREATED_BY,\n" +
                    "               dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                    "               AIAH.CREATION_DATE,\n" +
                    "               AIAH.FROM_DEPT,\n" +
                    "               AIAH.FROM_OBJECT_NO,\n" +
                    "               AIAH.FROM_ORGANIZATION_ID,\n" +
                    "               AIAH.FROM_USER,\n" +
                    "               AIAH.LAST_UPDATE_BY,\n" +
                    "               AIAH.LAST_UPDATE_DATE,\n" +
                    "               AIAH.RCV_USER,\n" +
                    "               AIAH.SOURCE_ID,\n" +
                    "               AIAH.TO_DEPT,\n" +
                    "               AIAH.TO_OBJECT_NO,\n" +
                    "               AIAH.TO_ORGANIZATION_ID,\n" +
                    "               AIAH.TO_USER,\n" +
                    "               AIAH.TRANS_DATE,\n" +
                    "               AIAH.TRANS_ID,\n" +
                    "               AIAH.TRANS_NO,\n" +
                    "               AIAH.TRANS_STATUS,\n" +
                    "               AIAH.TRANS_TYPE,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AIAH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               SF_USER             SU\n" +
                    "         WHERE AIAH.TRANS_NO LIKE dbo.NVL(?, AIAH.TRANS_NO)\n" +
                    "               AND AIAH.TRANS_STATUS = dbo.NVL(?, AIAH.TRANS_STATUS)\n" +
                    "               AND AIAH.CREATED_BY = SU.USER_ID\n" +
                    "               AND AIAH.TRANS_TYPE = dbo.NVL(?, AIAH.TRANS_TYPE)\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR AIAH.CREATION_DATE <= ?)\n" +
                    "               AND ((? =-1 OR AIAH.FROM_ORGANIZATION_ID = ?) OR (? =-1 OR AIAH.TO_ORGANIZATION_ID = ?))\n" +
                    "               AND ("+SyBaseSQLUtil.isNull()+" OR SU.USERNAME LIKE ?)\n" +
                    "               AND EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                  FROM AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "                       AMS_SPARE_CATEGORY  AMSC\n" +
                    "                 WHERE AIAD.TRANS_ID = AIAH.TRANS_ID\n" +
                    "                       AND AMSC.BARCODE = AIAD.BARCODE\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                    "                       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.VENDOR_ID = ?))) TMP_ORDER\n" +
                    " ORDER BY TMP_ORDER.CREATION_DATE DESC";

			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getSpareUsage());
			sqlArgs.add(dto.getSpareUsage());
			sqlArgs.add(dto.getVendorId());
			sqlArgs.add(dto.getVendorId());

			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getSpareUsage());
			sqlArgs.add(dto.getSpareUsage());
            sqlArgs.add(dto.getVendorId());
			sqlArgs.add(dto.getVendorId());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
