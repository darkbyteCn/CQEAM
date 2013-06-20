package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.OverplusEquipmentDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-23
 * Time: 21:30:20
 * To change this template use File | Settings | File Templates.
 */

public class OverplusEquipmentModel extends AMSSQLProducer {

	public OverplusEquipmentModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：获取未匹配的EAM设备SQL
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		OverplusEquipmentDTO dto = (OverplusEquipmentDTO) dtoParameter;
		String costCenterName = dto.getCostCenterName();
		StringBuffer sb = new StringBuffer();
		
//		String sqlStr = 
		sb.append( "SELECT \n" );
//						sb.append( " ROWNUM,\n"
		sb.append( " EII.BARCODE \n ," );  
		sb.append( " ESI.ITEM_NAME \n ," ); 
		sb.append( " ESI.ITEM_SPEC \n ," );  
		sb.append( " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY \n ," );  
		sb.append( " EO.WORKORDER_OBJECT_CODE \n ," );
		sb.append( " EO.WORKORDER_OBJECT_NAME \n ," );
		sb.append( " ACCV.COST_CENTER_NAME \n " );
		sb.append( " FROM \n " );
		sb.append( " ETS_SYSTEM_ITEM     ESI (INDEX ETS_SYSTEM_ITEM_PK) \n ," );
		sb.append( " ETS_ITEM_INFO       EII (INDEX ETS_ITEM_INFO_110708_N7) \n ," );
		sb.append( " ETS_OBJECT          EO (INDEX ETS_OBJECT_11089119911) \n ," );
		sb.append( " AMS_OBJECT_ADDRESS  AOA (INDEX AMS_OBJECT_ADDRESS_N) \n ," );
		sb.append( " AMS_COST_DEPT_MATCH ACDM (INDEX AMS_COST_DEPT_MATCH_PK) \n ," );
		sb.append( " AMS_COST_CENTER_V   ACCV \n " );
		sb.append( " WHERE \n " );
		sb.append( " EII.ITEM_CODE = ESI.ITEM_CODE \n " );
		sb.append( " AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n " );
		if( StrUtil.isEmpty( costCenterName ) ){
			sb.append( " AND EII.ORGANIZATION_ID *= ACCV.ORGANIZATION_ID \n " );
		}else{
			sb.append( " AND EII.ORGANIZATION_ID = ACCV.ORGANIZATION_ID \n " );
		}
		sb.append( " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n " );
		if( StrUtil.isEmpty( costCenterName ) ){
			sb.append( " AND EII.RESPONSIBILITY_DEPT *= ACDM.DEPT_CODE \n " );
			sb.append( " AND ACDM.COST_CENTER_CODE *= ACCV.COST_CENTER_CODE \n " );
			sb.append( " AND ACCV.ORGANIZATION_ID = ? \n " ); 
		}else{
			sb.append( " AND EII.RESPONSIBILITY_DEPT = ACDM.DEPT_CODE \n " );
			sb.append( " AND ACDM.COST_CENTER_CODE = ACCV.COST_CENTER_CODE \n " );
			sb.append( " AND EII.ORGANIZATION_ID = ACCV.ORGANIZATION_ID \n " ); 
		}
		
		sb.append( " AND (  CONVERT( INT , EO.OBJECT_CATEGORY ) <= 70 OR  CONVERT( INT , EO.OBJECT_CATEGORY ) = 80 ) \n " );
		sb.append( " AND NOT EXISTS ( \n " );
		sb.append( " SELECT \n " );
		sb.append( " NULL \n " );
		sb.append( " FROM \n " );
		sb.append( " ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_U1) \n " );
		sb.append( " WHERE \n " );
		sb.append( " EIM.SYSTEMID = EII.SYSTEMID) \n " );
		//TODO SJ 
		sb.append( " AND NOT EXISTS ( \n " );
		sb.append( " SELECT \n " );
		sb.append( " NULL \n " );
		sb.append( " FROM \n " );
		sb.append( " dbo.ETS_NO_MATCH ENM (INDEX ETS_NO_MATCH_S1) \n " );
		sb.append( " WHERE \n " );
		sb.append( " ENM.SYSTEMID = EII.SYSTEMID ) \n " );
        sb.append( " AND EII.FINANCE_PROP = 'UNKNOW' \n " );
        sb.append( " AND ( ? = '' OR ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) ) \n " );
		sb.append( " AND ( ? = ''  OR ESI.ITEM_SPEC LIKE ?) \n " );
		sb.append( " AND (? = '' OR EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE) \n " );
		sb.append( " OR EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)) \n " );
		sb.append( " AND (( ? = ''  OR ACCV.COST_CENTER_NAME LIKE ?) \n " );
		sb.append( " OR ( ? = ''  OR ACCV.COST_CENTER_CODE LIKE ?)) \n " );
		sb.append( " AND EII.ORGANIZATION_ID = ? \n " ); 
		
		if( StrUtil.isEmpty( costCenterName ) ){
			sqlArgs.add(userAccount.getOrganizationId());
		}
		
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());

		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());

		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
