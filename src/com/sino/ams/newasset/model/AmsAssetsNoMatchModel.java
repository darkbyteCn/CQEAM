package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-14
 * Time: 16:01:16
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsNoMatchModel extends AMSSQLProducer {
	private AmsAssetsNoMatchDTO dto = null;

	public AmsAssetsNoMatchModel(SfUserDTO userAccount,
								 AmsAssetsNoMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (AmsAssetsNoMatchDTO) dtoParameter;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sb = new StringBuffer();
		try {

//			sqlStr = "SELECT EFA.ORGANIZATION_ID,\n" );
			sb.append( " SELECT \n " );
			sb.append( " 	EFA.ORGANIZATION_ID, \n " ); 
			sb.append( "    dbo.APP_GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGANIZATION_NAME," );
			sb.append( "    EFA.FA_CATEGORY_CODE,\n" );
			sb.append( "    COUNT(EFA.ASSET_ID) NO_MATCH_QTY\n" );
			sb.append( "  FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n" );
			sb.append( "  WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" );
			sb.append( "    AND NOT EXISTS\n" );
			sb.append( "  (SELECT 1 FROM ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2) WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" );
			sb.append( "    AND NOT EXISTS\n" );
			sb.append( "  (SELECT 1\n" );
			sb.append( "           FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM (INDEX ETS_ITEM_MATCH_ASSIST_MIS_PK) \n" );
			sb.append( "          WHERE EIMAM.ASSET_ID = EFA.ASSET_ID)\n" );
			
			int orgId = dto.getOrganizationId();
			if( orgId > 0 ){
				sb.append( "    AND ORGANIZATION_ID = ? \n" );
				sqlArgs.add( orgId );
			}
			
			sb.append( "    AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE >= ? )\n" );
			sb.append( "    AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE <= ? )\n" );
			sb.append( "  GROUP BY EFA.ORGANIZATION_ID, EFA.FA_CATEGORY_CODE " );
			
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getToDate());
			sqlArgs.add(dto.getToDate());
			
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
