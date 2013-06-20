package com.sino.ams.spare.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.SpareHistoryDTO;
import com.sino.base.dto.DTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.CalendarException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareHistoryModel extends AMSSQLProducer {

	public SpareHistoryModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			SpareHistoryDTO dto = (SpareHistoryDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT TMP_ORDER.*\n" +
                    "  FROM (SELECT AITH.TRANS_NO,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               AITL.BARCODE,\n" +
                    "               ASSC.ITEM_NAME,\n" +
                    "               ASSC.ITEM_SPEC,\n" +
                    "               ASSC.ITEM_CATEGORY,\n" +
                    "               ASSC.SPARE_USAGE,\n" +
                    "               ASV.VENDOR_NAME,\n" +
                    "               AITL.QUANTITY,\n" +
                    "               AITH.CREATION_DATE,\n" +
                    "               EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                    "               EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                    "               EOCM1.COMPANY FROM_COMPANY,\n" +
                    "               EOCM2.COMPANY TO_COMPANY,\n" +
                    "               dbo.APP_GET_USER_NAME(AITH.CREATED_BY) CREATED_USER\n" +
                    "          FROM AMS_ITEM_TRANS_H   AITH,\n" +
                    "               AMS_ITEM_TRANS_L   AITL,\n" +
                    "               AMS_SPARE_CATEGORY ASSC,\n" +
                    "               ETS_OBJECT         EO1,\n" +
                    "               ETS_OBJECT         EO2,\n" +
                    "               ETS_OU_CITY_MAP    EOCM1,\n" +
                    "               ETS_OU_CITY_MAP    EOCM2,\n" +
                    "               AMS_SPARE_VENDORS  ASV,\n" +
                    "               SF_USER            SU\n" +
                    "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "           AND AITL.BARCODE = ASSC.BARCODE\n" +
                    "           AND AITH.FROM_OBJECT_NO *= EO1.WORKORDER_OBJECT_NO \n" +
                    "           AND AITH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n" +
                    "           AND AITH.FROM_ORGANIZATION_ID *= EOCM1.ORGANIZATION_ID \n" +
                    "           AND AITH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID \n" +
                    "           AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "           AND AITH.CREATED_BY = SU.USER_ID\n" +
                    "           AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "           AND AITH.TRANS_TYPE = 'BJFH'\n" +
                    "           AND EO1.WORKORDER_OBJECT_NAME NOT LIKE '%工程%'\n" +
                    "           AND (? =-1 OR SU.ORGANIZATION_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_NO LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_TYPE = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_NAME LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_SPEC LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_CATEGORY LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.SPARE_USAGE LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.VENDOR_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE >= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE <= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR SU.USERNAME LIKE ?)\n" +
                    "           AND (? = -1 OR EOCM1.ORGANIZATION_ID = ?)\n" +
                    "           AND (? = -1 OR EOCM2.ORGANIZATION_ID = ?)\n" +

                    "        UNION ALL\n" +
                    "        SELECT AITH.TRANS_NO,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               AITL.BARCODE,\n" +
                    "               ASSC.ITEM_NAME,\n" +
                    "               ASSC.ITEM_SPEC,\n" +
                    "               ASSC.ITEM_CATEGORY,\n" +
                    "               ASSC.SPARE_USAGE,\n" +
                    "               ASV.VENDOR_NAME,\n" +
                    "               AITL.QUANTITY,\n" +
                    "               AITH.CREATION_DATE,\n" +
                    "               EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                    "               EOCM3.COMPANY TO_OBJECT_NAME,\n" +
                    "               EOCM1.COMPANY FROM_COMPANY,\n" +
                    "               EOCM2.COMPANY TO_COMPANY,\n" +
                    "               dbo.APP_GET_USER_NAME(AITH.CREATED_BY) CREATED_USER\n" +

                    "          FROM AMS_ITEM_TRANS_H   AITH,\n" +
                    "               AMS_ITEM_TRANS_L   AITL,\n" +
                    "               AMS_SPARE_CATEGORY ASSC,\n" +
                    "               ETS_OBJECT         EO1,\n" +
                    "               ETS_OU_CITY_MAP    EOCM1,\n" +
                    "               ETS_OU_CITY_MAP    EOCM2,\n" +
                    "               ETS_OU_CITY_MAP    EOCM3,\n" +
                    "               AMS_SPARE_VENDORS  ASV,\n" +
                    "               SF_USER            SU\n" +
                    "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "           AND AITL.BARCODE = ASSC.BARCODE\n" +
                    "           AND AITH.FROM_OBJECT_NO *= EO1.WORKORDER_OBJECT_NO\n" +
                    "           AND AITH.TO_OBJECT_NO *= EOCM3.COMPANY_CODE\n" +
                    "           AND AITH.FROM_ORGANIZATION_ID *= EOCM1.ORGANIZATION_ID\n" +
                    "           AND AITH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID\n" +
                    "           AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "           AND AITH.CREATED_BY = SU.USER_ID\n" +
                    "           AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "           AND AITH.TRANS_TYPE = 'BJFH'\n" +
                    "           AND EO1.WORKORDER_OBJECT_NAME LIKE '%工程%'\n" +
                    "           AND (? =-1 OR SU.ORGANIZATION_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_NO LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_TYPE = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_NAME LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_SPEC LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_CATEGORY LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.SPARE_USAGE LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.VENDOR_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE >= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE <= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR SU.USERNAME LIKE ?)\n" +
                    "           AND (? =-1 OR EOCM1.ORGANIZATION_ID = ?)\n" +
                    "           AND (? =-1 OR EOCM2.ORGANIZATION_ID = ?)\n" +

                    "        UNION ALL\n" +
                    "        SELECT AITH.TRANS_NO,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               AITL.BARCODE,\n" +
                    "               ASSC.ITEM_NAME,\n" +
                    "               ASSC.ITEM_SPEC,\n" +
                    "               ASSC.ITEM_CATEGORY,\n" +
                    "               ASSC.SPARE_USAGE,\n" +
                    "               ASV.VENDOR_NAME,\n" +
                    "               AITL.QUANTITY,\n" +
                    "               AITH.CREATION_DATE,\n" +
                    "               EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                    "               EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                    "               EOCM1.COMPANY FROM_COMPANY,\n" +
                    "               EOCM2.COMPANY TO_COMPANY,\n" +
                    "               dbo.APP_GET_USER_NAME(AITH.CREATED_BY) CREATED_USER\n" +

                    "          FROM AMS_ITEM_TRANS_H   AITH,\n" +
                    "               AMS_ITEM_TRANS_L   AITL,\n" +
                    "               AMS_SPARE_CATEGORY ASSC,\n" +
                    "               ETS_OBJECT         EO1,\n" +
                    "               ETS_OBJECT         EO2,\n" +
                    "               ETS_OU_CITY_MAP    EOCM1,\n" +
                    "               ETS_OU_CITY_MAP    EOCM2,\n" +
                    "               AMS_SPARE_VENDORS  ASV,\n" +
                    "               SF_USER            SU\n" +
                    "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "           AND AITL.BARCODE = ASSC.BARCODE\n" +
                    "           AND AITH.FROM_OBJECT_NO *= EO1.WORKORDER_OBJECT_NO \n" +
                    "           AND AITH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n" +
                    "           AND AITH.FROM_ORGANIZATION_ID *= EOCM1.ORGANIZATION_ID \n" +
                    "           AND AITH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID \n" +
                    "           AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "           AND AITH.CREATED_BY = SU.USER_ID\n" +
                    "           AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "           AND AITH.TRANS_TYPE <> 'BJFH'\n" +
                    "           AND (? =-1 OR SU.ORGANIZATION_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_NO LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AITH.TRANS_TYPE = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_NAME LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_SPEC LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_CATEGORY LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.SPARE_USAGE LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.VENDOR_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE >= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AITH.CREATION_DATE <= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR SU.USERNAME LIKE ?)\n" +
                    "           AND (? =-1 OR EOCM1.ORGANIZATION_ID = ?)\n" +
                    "           AND (? =-1 OR EOCM2.ORGANIZATION_ID = ?)\n" +

                    "        UNION ALL\n" +
                    "        SELECT AIAH.TRANS_NO,\n" +
                    "               dbo.APP_GET_TRANS_TYPE(AIAH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "               AIAD.BARCODE,\n" +
                    "               ASSC.ITEM_NAME,\n" +
                    "               ASSC.ITEM_SPEC,\n" +
                    "               ASSC.ITEM_CATEGORY,\n" +
                    "               ASSC.SPARE_USAGE,\n" +
                    "               ASV.VENDOR_NAME,\n" +
                    "               AIAD.QUANTITY,\n" +
                    "               AIAH.CREATION_DATE,\n" +
                    "               EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                    "               EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                    "               EOCM1.COMPANY FROM_COMPANY,\n" +
                    "               EOCM2.COMPANY TO_COMPANY,\n" +
                    "               dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER\n" +

                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               AMS_SPARE_CATEGORY  ASSC,\n" +
                    "               ETS_OBJECT          EO1,\n" +
                    "               ETS_OBJECT          EO2,\n" +
                    "               ETS_OU_CITY_MAP     EOCM1,\n" +
                    "               ETS_OU_CITY_MAP     EOCM2,\n" +
                    "               AMS_SPARE_VENDORS   ASV,\n" +
                    "               SF_USER             SU\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "           AND AIAD.BARCODE = ASSC.BARCODE\n" +
                    "           AND AIAH.FROM_OBJECT_NO *= EO1.WORKORDER_OBJECT_NO \n" +
                    "           AND AIAH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n" +
                    "           AND AIAH.FROM_ORGANIZATION_ID *= EOCM1.ORGANIZATION_ID \n" +
                    "           AND AIAH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID \n" +
                    "           AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "           AND AIAH.CREATED_BY = SU.USER_ID\n" +
                    "           AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "           AND (? =-1 OR SU.ORGANIZATION_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AIAH.TRANS_NO LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR AIAH.TRANS_TYPE = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_NAME LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_SPEC LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.ITEM_CATEGORY LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.SPARE_USAGE LIKE ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR ASSC.VENDOR_ID = ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AIAH.CREATION_DATE >= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull()+" OR AIAH.CREATION_DATE <= ?)\n" +
                    "           AND ("+SyBaseSQLUtil.isNull() +" OR SU.USERNAME LIKE ?)\n" +
                    "           AND (? =-1 OR EOCM1.ORGANIZATION_ID = ?)\n" +
                    "           AND (? =-1 OR EOCM2.ORGANIZATION_ID = ?)\n" +
                    ") TMP_ORDER\n" +
                    " ORDER BY TMP_ORDER.CREATION_DATE DESC";
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransType());
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
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getToCompany());
			sqlArgs.add(dto.getToCompany());

            sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransType());
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
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getToCompany());
			sqlArgs.add(dto.getToCompany());

            sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransType());
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
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getToCompany());
			sqlArgs.add(dto.getToCompany());

            sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransType());
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
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getCreatedUser());
			sqlArgs.add(dto.getCreatedUser());
            sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getFromCompany());
			sqlArgs.add(dto.getToCompany());
			sqlArgs.add(dto.getToCompany());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
