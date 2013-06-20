package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class ZeroTurnEditQueryModel extends BaseSQLProducer {

	SfUserDTO sfUser = null;
	public ZeroTurnEditQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = (SfUserDTO) userAccount;
        
    }
	public SQLModel getPageQueryModel() throws SQLModelException {
    	SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ZeroTurnLineDTO dto = (ZeroTurnLineDTO)dtoParameter;
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * \n"); 
		sb.append(" FROM ETS_ITEM_TURN "); 
		sb.append(" WHERE 1=1 "); 
		sb.append(" AND BARCODE IN( SELECT TAG_NUMBER FROM EAM_DIFF_PA_VS_EII_ZERO_TURN )\n");
		
		if( !StrUtil.isEmpty( dto.getBarcode() ) ){
			sb.append("  AND  BARCODE = ? "); 
			sqlArgs.add(dto.getBarcode());
        }
		if(!StrUtil.isEmpty( dto.getCostCenterCode())){
			sb.append("  AND  COST_CENTER_CODE = ? "); 
			sqlArgs.add(dto.getCostCenterCode());
		}
		if(!StrUtil.isEmpty( dto.getProcureCode())){
			sb.append("  AND  PROCURE_CODE = ? "); 
			sqlArgs.add(dto.getProcureCode());
		}
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
	
}
