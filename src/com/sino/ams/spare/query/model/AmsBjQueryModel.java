package com.sino.ams.spare.query.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.query.dto.AmsBjQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-23
 */
public class AmsBjQueryModel extends BaseSQLProducer {
    private SfUserDTO SfUser = null;
    private AmsBjQueryDTO dto = null;


    public AmsBjQueryModel(SfUserDTO userAccount, AmsBjQueryDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.dto = (AmsBjQueryDTO) dtoParameter;
    }
     public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " AWP.PLAN_ID,"
                        + " AWP.PLAN_NAME,"
                        + " AWP.PLAN_DESC,"
                        + " AWP.EXECUTE_TIME,"
                        + " SU.USER_ID EXECUTE_USER,"
                        + " SU.USERNAME EXECUTE_USER_NAME,"
                        + " AWP.PLAN_STATUS"
                        + " FROM"
                        + " AMS_WORK_PLAN AWP,"
                        + " SF_USER SU"
                        + " WHERE"
                        + " SU.USER_ID = AWP.EXECUTE_USER"
                        + " AND AWP.PLAN_ID = ?";
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /*public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       EII.BARCODE,\n" +
                "       EFV.VALUE ITEM_CATEGORY_NAME,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EMVP.VENDOR_NAME,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       ESI.ITEM_CODE\n" +
                "  FROM ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_MIS_PO_VENDORS EMVP,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_ITEM_INFO      EII\n" +
                " WHERE EMVP.VENDOR_ID = ESI.VENDOR_ID\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EFV.CODE = ESI.ITEM_CATEGORY\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = 1\n" +
                "   AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "   AND EII.FINANCE_PROP = 'SPARE'\n" +
                "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.ORGANIZATION_ID = NVL(?, EII.ORGANIZATION_ID)\n" +
                "   AND ESI.ITEM_NAME LIKE NVL(?, ESI.ITEM_NAME)\n" +
                "   AND ESI.ITEM_SPEC LIKE NVL(?, ESI.ITEM_SPEC)\n" +
                "   AND EII.BARCODE LIKE NVL(?, EII.BARCODE)\n" +
                "   AND ESI.ITEM_CATEGORY = NVL(?, ESI.ITEM_CATEGORY)\n" +
                "   AND EII.ADDRESS_ID = NVL(?, EII.ADDRESS_ID)" ;

        sqlArgs.add(dto.getCompany());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getItemCategoryName());
        sqlArgs.add(dto.getAddressId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}*/

    /**
     * V2
     * @return SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.BARCODE,\n" +
                "       ASI.QUANTITY,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EMVP.VENDOR_NAME,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       EFV.VALUE ITEM_CATEGORY_NAME" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_MIS_PO_VENDORS EMVP,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_FLEX_VALUES    EFV" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND ESI.VENDOR_ID = EMVP.VENDOR_ID(+)\n" +
                "   AND ASI.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND EFV.CODE = ESI.ITEM_CATEGORY" +
                "   AND EFV.FLEX_VALUE_SET_ID = 1" +
                "   AND ASI.ORGANIZATION_ID = NVL(?, ASI.ORGANIZATION_ID)\n" +
                "   AND EO.WORKORDER_OBJECT_NAME LIKE NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
                "   AND ESI.ITEM_NAME LIKE NVL(?, ESI.ITEM_NAME)\n" +
                "   AND ESI.ITEM_SPEC LIKE NVL(?, ESI.ITEM_SPEC)\n" +
                "   AND ASI.BARCODE LIKE NVL(?, ASI.BARCODE)\n" +
                "   AND ESI.ITEM_CATEGORY = NVL(?, ESI.ITEM_CATEGORY)" ;

        sqlArgs.add(dto.getCompany());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getItemCategoryName());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
