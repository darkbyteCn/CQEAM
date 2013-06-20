package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-8-20
 * Time: 13:23:42
 * To change this template use File | Settings | File Templates.
 */
public class ItemStatusErrorModel extends AMSSQLProducer {

	public ItemStatusErrorModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamSyschronizeDTO dto = (EamSyschronizeDTO) dtoParameter;
//		String provinceCode = servletConfig.getProvinceCode();
		String sqlStr;
		sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       AMD.DEPT_NAME,\n" +
                "       AME.USER_NAME,\n" +
                "       AMS_PUB_PKG.GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME\n" +
                "  FROM ETS_FA_ASSETS      EFA,\n" +
                "       ETS_ITEM_INFO      EII,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "       AMS_MIS_DEPT       AMD,\n" +
                "       AMS_MIS_EMPLOYEE   AME\n" +
                " WHERE (EFA.TAG_NUMBER = EII.BARCODE OR EFA.MIS_TAG_NUMBER = EII.BARCODE)\n" +
                "       AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "       AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "       AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "       AND EFA.IS_RETIREMENTS = 1\n" +
                "       AND EII.ITEM_STATUS <> 'DISCARDED'\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)";
		sqlArgs.add(dto.getBarCode());
		sqlArgs.add(dto.getBarCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

    public SQLModel changeItemStatus(String barcode) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "UPDATE ETS_ITEM_INFO EII\n" +
                "   SET EII.ITEM_STATUS = 'DISCARDED'\n" +
                " WHERE EII.BARCODE = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
