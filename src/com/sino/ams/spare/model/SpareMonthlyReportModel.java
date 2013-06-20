package com.sino.ams.spare.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
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
 * User: su
 * Date: 2010-2-4
 * Time: 10:25:15
 * To change this template use File | Settings | File Templates.
 */
public class SpareMonthlyReportModel extends AMSSQLProducer {

	public SpareMonthlyReportModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			SpareHistoryDTO dto = (SpareHistoryDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT ASCC.BARCODE,\n" +
                    "       ASCC.ITEM_NAME,\n" +
                    "       ASCC.ITEM_SPEC,\n" +
                    "       ASCC.ITEM_CATEGORY,\n" +
                    "       ASCC.SPARE_USAGE,\n" +
                    "       ASV.VENDOR_NAME,\n" +
                    "       TOTAL_1.QUANTITY TOTAL1_QUANTITY,\n" +
                    "       TOTAL_2.QUANTITY TOTAL2_QUANTITY,\n" +
                    "       TOTAL_3.QUANTITY TOTAL3_QUANTITY,\n" +
                    "       TOTAL_4.QUANTITY TOTAL4_QUANTITY,\n" +
                    "       TOTAL_5.QUANTITY TOTAL5_QUANTITY,\n" +
                    "       TOTAL_6.QUANTITY TOTAL6_QUANTITY,\n" +
                    "       TOTAL_7.QUANTITY TOTAL7_QUANTITY,\n" +
                    "       TOTAL_8.QUANTITY TOTAL8_QUANTITY,\n" +
                    "       TOTAL_9.QUANTITY TOTAL9_QUANTITY,\n" +
                    "       TOTAL_10.QUANTITY TOTAL10_QUANTITY,\n" +
                    "       TOTAL_11.QUANTITY TOTAL11_QUANTITY,\n" +
                    "       TOTAL_12.QUANTITY TOTAL12_QUANTITY,\n" +
                    "       TOTAL_13.QUANTITY TOTAL13_QUANTITY\n" +
                    "  FROM (SELECT TOTAL.BARCODE,\n" +
                    "               SUM(TOTAL.QUANTITY) QUANTITY\n" +
                    "          FROM (SELECT AITL.BARCODE,\n" +
                    "                       AITL.QUANTITY\n" +
                    "                  FROM AMS_ITEM_TRANS_H AITH,\n" +
                    "                       AMS_ITEM_TRANS_L AITL,\n" +
                    "                       ETS_OBJECT       EO\n" +
                    "                 WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "                       AND AITH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                       AND AITH.TRANS_TYPE = 'BJRK'\n" +
                    "                       AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "                       AND EO.WORKORDER_OBJECT_CODE = '4110BJ01'\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE >= ?)\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE <= ?)\n" +
                    "                UNION ALL\n" +
                    "                SELECT AITL.BARCODE,\n" +
                    "                       AITL.QUANTITY\n" +
                    "                  FROM AMS_ITEM_TRANS_H AITH,\n" +
                    "                       AMS_ITEM_TRANS_L AITL,\n" +
                    "                       ETS_OBJECT       EO\n" +
                    "                 WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "                       AND AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                       AND AITH.TRANS_TYPE = 'JCHG'\n" +
                    "                       AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "                       AND EO.WORKORDER_OBJECT_CODE = '4110BJ02'\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE >= ?)\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE <= ?)\n" +
                    "                UNION ALL\n" +
                    "                SELECT AIAD.BARCODE,\n" +
                    "                       AIAD.QUANTITY\n" +
                    "                  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "                       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "                       ETS_OBJECT          EO\n" +
                    "                 WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "                       AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                       AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "                       AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "                       AND EO.WORKORDER_OBJECT_CODE = '4110BJ01'\n" +
                    "                       AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "                       AND (? IS NULL OR AIAH.CREATION_DATE <= ?)) TOTAL\n" +
                    "         GROUP BY TOTAL.BARCODE) TOTAL_1,\n" +
                    "       (SELECT TOTAL.BARCODE,\n" +
                    "               SUM(TOTAL.QUANTITY) QUANTITY\n" +
                    "          FROM (SELECT AITL.BARCODE,\n" +
                    "                       AITL.QUANTITY\n" +
                    "                  FROM AMS_ITEM_TRANS_H AITH,\n" +
                    "                       AMS_ITEM_TRANS_L AITL,\n" +
                    "                       ETS_OBJECT       EO\n" +
                    "                 WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "                       AND AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                       AND AITH.TRANS_TYPE = 'BJCK'\n" +
                    "                       AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "                       AND EO.WORKORDER_OBJECT_CODE = '4110BJ01'\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE >= ?)\n" +
                    "                       AND (? IS NULL OR AITH.CREATION_DATE <= ?)\n" +
                    "                UNION ALL\n" +
                    "                SELECT AIAD.BARCODE,\n" +
                    "                       AIAD.QUANTITY\n" +
                    "                  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "                       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "                       ETS_OBJECT          EO1,\n" +
                    "                       ETS_OBJECT          EO2\n" +
                    "                 WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "                       AND AIAH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO\n" +
                    "                       AND AIAH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO\n" +
                    "                       AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "                       AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "                       AND EO1.WORKORDER_OBJECT_CODE = '4110BJ01'\n" +
                    "                       AND EO2.OBJECT_CATEGORY = 71\n" +
                    "                       AND EO2.ORGANIZATION_ID = 82\n" +
                    "                       AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "                       AND (? IS NULL OR AIAH.CREATION_DATE <= ?)) TOTAL\n" +
                    "         GROUP BY TOTAL.BARCODE) TOTAL_2,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 84\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_3,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 87\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_4,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 88\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_5,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 89\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_6,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 90\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_7,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 92\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_8,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 94\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_9,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 98\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_10,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 101\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_11,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 103\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_12,\n" +
                    "       (SELECT AIAD.BARCODE BARCODE,\n" +
                    "               SUM(AIAD.QUANTITY) QUANTITY\n" +
                    "          FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "               AMS_ITEM_ALLOCATE_D AIAD,\n" +
                    "               ETS_OBJECT          EO\n" +
                    "         WHERE AIAH.TRANS_ID = AIAD.TRANS_ID\n" +
                    "               AND AIAH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "               AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "               AND AIAH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND EO.OBJECT_CATEGORY = 71\n" +
                    "               AND EO.ORGANIZATION_ID = 118\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE >= ?)\n" +
                    "               AND (? IS NULL OR AIAH.CREATION_DATE <= ?)\n" +
                    "         GROUP BY AIAD.BARCODE) TOTAL_13,\n" +
                    "       AMS_SPARE_CATEGORY ASCC,\n" +
                    "       AMS_SPARE_VENDORS ASV\n" +
                    " WHERE ASCC.BARCODE = TOTAL_1.BARCODE(+)\n" +
                    "       AND ASCC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "       AND ASCC.BARCODE = TOTAL_2.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_3.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_4.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_5.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_6.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_7.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_8.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_9.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_10.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_11.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_12.BARCODE(+)\n" +
                    "       AND ASCC.BARCODE = TOTAL_13.BARCODE(+)\n" +
                    "       AND (? IS NULL OR ASCC.BARCODE LIKE ?)\n" +
                    "       AND (? IS NULL OR ASCC.ITEM_NAME LIKE ?)\n" +
                    "       AND (? IS NULL OR ASCC.ITEM_SPEC LIKE ?)\n" +
                    "       AND (? IS NULL OR ASCC.ITEM_CATEGORY LIKE ?)\n" +
                    "       AND (? IS NULL OR ASCC.SPARE_USAGE LIKE ?)\n" +
                    "       AND (? IS NULL OR ASCC.VENDOR_ID = ?)";
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getBarcode());
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
