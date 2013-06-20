package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-20
 * Time: 16:05:20
 * To change this template use File | Settings | File Templates.
 */
public class UnusedAssetsModel extends BaseSQLProducer {
	EtsItemMatchDTO itemMatchDTO = null;
	  SfUserDTO sfUser = null;

	public UnusedAssetsModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.itemMatchDTO = (EtsItemMatchDTO) dtoParameter;
		  sfUser = (SfUserDTO) userAccount;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " +
//                "       EFA.ASSET_ID,\n" +
				"       EO.WORKORDER_OBJECT_CODE,\n" +
				"       EO.WORKORDER_OBJECT_NAME,\n" +
				"       EO.WORKORDER_OBJECT_LOCATION,\n" +
				"       EII.BARCODE,\n" +
                "       EFA.TAG_NUMBER\n" +
				"\n" +
				"  FROM ETS_ITEM_INFO      EII,\n" +
				"       ETS_OBJECT         EO,\n" +
                "       ETS_FA_ASSETS      EFA,\n" +
				"       AMS_OBJECT_ADDRESS AOA\n" +
				"\n" +
				" WHERE EII.ORGANIZATION_ID = ?\n" +
				"   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
				"   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND EFA.ASSET_ID = EII.ASSET_ID\n" +
				"   AND EO.OBJECT_CATEGORY = '74'\n" +
				"      \n" +
				"   AND NOT EXISTS (SELECT 1\n" +
				"          FROM ETS_WORKORDER EW, ETS_WORKORDER_DTL EWD \n" +
				"         WHERE EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
//                "           AND EW.WORKORDER_TYPE = '12'\n" +
				"           AND EWD.BARCODE = EII.BARCODE)\n" +
				"      \n" +
				"   AND NOT EXISTS (SELECT 1\n" +
				"          FROM AMS_ITEM_INFO_HISTORY AIIH \n" +
				"         WHERE AIIH.BARCODE = EII.BARCODE)";

		sqlArgs.add(sfUser.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataDeleteModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE\n" +
				"\n" +
				"FROM ETS_ITEM_INFO \n" +
				" WHERE ORGANIZATION_ID = ?\n" +
				"   AND EXISTS\n" +
				" (SELECT 1\n" +
				"          FROM ETS_FA_ASSETS EFA, ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA\n" +
				"         WHERE EFA.ASSET_ID = ASSET_ID\n" +
				"           and EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
				"           AND EO.OBJECT_CATEGORY = '74'\n" +              //在途
				"           AND ADDRESS_ID = AOA.ADDRESS_ID)\n" +
				"      \n" +
				"   AND NOT EXISTS (SELECT 1\n" +
				"          FROM ETS_WORKORDER EW, ETS_WORKORDER_DTL EWD\n" +
				"         WHERE EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
				"           AND EW.WORKORDER_TYPE = '12'\n" +         //巡检
				"           AND EWD.BARCODE = BARCODE)\n" +
				"   AND NOT EXISTS (SELECT 1\n" +                    //地点变更
				"          FROM AMS_ITEM_INFO_HISTORY AIIH\n" +
				"         WHERE AIIH.BARCODE = BARCODE)" ;

		sqlArgs.add(sfUser.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	public SQLModel do_deleteMatched() {      //
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE\n" +
				"   FROM ETS_ITEM_MATCH EIM\n" +
				"  WHERE EXISTS (SELECT 1\n" +
				"           FROM (SELECT EII.SYSTEMID\n" +
				"                   FROM ETS_ITEM_INFO EII\n" +
				"                  WHERE EXISTS (SELECT 1\n" +
				"                           FROM ETS_ITEM_MATCH EIM\n" +
				"                          WHERE EIM.SYSTEMID = EII.SYSTEMID)\n" +
				"                    AND EXISTS\n" +
				"                  (SELECT 1\n" +
				"                           FROM ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA\n" +
				"                          WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
				"                            AND EO.OBJECT_CATEGORY = '74' -- //在途\n" +
				"                            AND EII.ADDRESS_ID = AOA.ADDRESS_ID)\n" +
				"                    AND NOT EXISTS\n" +
				"                  (SELECT 1\n" +
				"                           FROM ETS_WORKORDER EW, ETS_WORKORDER_DTL EWD\n" +
				"                          WHERE EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
				"                            AND EW.WORKORDER_TYPE = '12' -- //巡检\n" +
				"                            AND EWD.BARCODE = EII.BARCODE)\n" +
				"                    AND NOT EXISTS\n" +
				"                  (SELECT 1\n" +
				"                           FROM AMS_ITEM_INFO_HISTORY AIIH --//设备状态为在途\n" +
				"                          WHERE AIIH.BARCODE = EII.BARCODE)) TEM_A\n" +
				"          WHERE TEM_A.SYSTEMID = EIM.SYSTEMID)";
//        sqlArgs.add();

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
