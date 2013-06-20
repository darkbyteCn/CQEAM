package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.ArrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.match.dto.EtsItemMatchMisDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-26
 * Time: 20:21:22
 * To change this template use File | Settings | File Templates.
 */

public class EtsItemMatchMisModel extends AMSSQLProducer {

	public EtsItemMatchMisModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 得到查询ETS设备的MODEL
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemMatchMisDTO dto = (EtsItemMatchMisDTO) dtoParameter;
		
		StringBuffer sb = new StringBuffer();
		sb.append( " SELECT \n ");
		sb.append( "  	EII.BARCODE, \n ");
		sb.append( "  	EII.SYSTEMID SYSTEM_ID, \n ");
		sb.append( "  	dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY, \n ");
		sb.append( "  	EII.FINANCE_PROP, \n ");
		sb.append( "  	EII.ITEM_CODE, \n ");
		sb.append( "  	ESI.ITEM_NAME, \n ");
		sb.append( "  	ESI.ITEM_SPEC, \n ");
		sb.append( "  	EO.WORKORDER_OBJECT_CODE, \n ");
		sb.append( "  	EO.WORKORDER_OBJECT_NAME, \n ");
		sb.append( "  	AMD.DEPT_NAME, \n ");
		sb.append( "  	ACCV.COST_CENTER_NAME \n ");
		sb.append( "  FROM \n ");
		sb.append( "  	ETS_ITEM_INFO       EII (INDEX ETS_ITEM_INFO_110708_N7), \n ");
		sb.append( "  	AMS_OBJECT_ADDRESS  AOA (INDEX AMS_OBJECT_ADDRESS_N), \n ");
		sb.append( "  	ETS_OBJECT          EO (INDEX ETS_OBJECT_11089119911), \n ");
		sb.append( "  	ETS_SYSTEM_ITEM     ESI (INDEX ETS_SYSTEM_ITEM_PK), \n ");
		sb.append( " 	AMS_MIS_DEPT        AMD (INDEX AMS_MIS_DEPT_PK) , \n ");
		sb.append( "  	AMS_COST_DEPT_MATCH ACDM (INDEX AMS_COST_DEPT_MATCH_PK), \n ");
		sb.append( "  	AMS_COST_CENTER_V   ACCV \n ");
		sb.append( "  WHERE  \n ");
		sb.append( "  	ESI.ITEM_CODE = EII.ITEM_CODE \n ");
		sb.append( "  	AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n ");
		sb.append( "  	AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n ");
		sb.append( "  	AND ACCV.ORGANIZATION_ID=EII.ORGANIZATION_ID  \n ");
		sb.append( "  	AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE  \n ");
		sb.append( "  	AND EII.RESPONSIBILITY_DEPT = ACDM.DEPT_CODE  \n ");
		sb.append( "  	AND ACDM.COST_CENTER_CODE = ACCV.COST_CENTER_CODE  \n ");
		sb.append( "  	AND (  CONVERT( INT , EO.OBJECT_CATEGORY ) <= 70 OR  CONVERT( INT , EO.OBJECT_CATEGORY ) = 80 )  \n ");
//						sb.append( "  AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)"
		sb.append( "  	AND (? = '' OR EII.BARCODE LIKE ? ) \n ");
		sb.append( "  	AND (? = '' OR ESI.ITEM_NAME LIKE ? ) \n ");
		sb.append( "  	AND (? = '' OR ESI.ITEM_SPEC LIKE ?) \n ");
		sb.append( "  	AND (? = '' OR ACCV.COST_CENTER_NAME LIKE ? OR ACCV.COST_CENTER_CODE LIKE ?) \n ");
		sb.append( "  	AND EII.ORGANIZATION_ID = ? \n ");
		sb.append( "  	AND EII.FINANCE_PROP =  ? \n ");

		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());

		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(DictConstant.FIN_PROP_OTHER);

		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 得到查询MIS资产的MODEL
	 * @return SQLModel
	 */
	public SQLModel getMisPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemMatchMisDTO dto = (EtsItemMatchMisDTO) dtoParameter;
		String sqlStr = "SELECT DISTINCT"
						+ " EFA.TAG_NUMBER,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " EFA.FA_CATEGORY1,"
						+ " EFA.FA_CATEGORY2,"
						+ " EIMAM.REMARK,"
						+ " EIMAM.ASSET_ID"
						+ " FROM"
						+ " ETS_FA_ASSETS EFA,"
						+ " ETS_ITEM_MATCH_ASSIST_MIS EIMAM"
						+ " WHERE"
						+ " EFA.ASSET_ID = EIMAM.ASSET_ID"
						+ " AND EFA.ORGANIZATION_ID = ?"
						+ " AND (? = '' OR  EFA.TAG_NUMBER  LIKE ?)"
						+ " AND (? = '' OR  EFA.ASSETS_DESCRIPTION  LIKE ?)"
						+ " AND (? = '' OR  EFA.MODEL_NUMBER LIKE ?)";

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：执行撤消MIS资产的操作。
	 * @param systemids String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel getDisabledEtsModel(String[] systemids) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToSqlStr(systemids);
		String sqlStr = "DELETE "
						+ " FROM"
						+ " ETS_ITEM_MATCH_ASSIST"
						+ " WHERE"
						+ " SYSTEMID IN (?)";
		sqlArgs.add(orderno);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行撤消MIS资产的操作。
	 * @param assetIds String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel getDisabledMisModel(String[] assetIds) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToStr(assetIds, ", ");
		String sqlStr = "DELETE "
						+ " FROM"
						+ " ETS_ITEM_MATCH_ASSIST_MIS"
						+ " WHERE"
						+ " ASSET_ID IN (?)";
		sqlArgs.add(orderno);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
