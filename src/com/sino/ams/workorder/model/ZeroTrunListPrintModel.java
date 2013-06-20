package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.print.dto.ZeroBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class ZeroTrunListPrintModel extends BaseSQLProducer {
	SfUserDTO sfUser = null;
	public ZeroTrunListPrintModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = (SfUserDTO) userAccount;
    }
    
	public SQLModel getPageQueryModel() throws SQLModelException {
    	SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EBPH.CREATION_DATE AS PRINT_TIME, \n"); 
		sb.append("SU.USERNAME \n");
		sb.append("FROM  ETS_BARCODE_PRINT_HISTORY EBPH, SF_USER SU \n");
		sb.append(" WHERE EBPH.CREATED_BY = SU.USER_ID AND EBPH.BARCODE = ? \n");
		
		ZeroBarcodePrintHistoryDTO dto = (ZeroBarcodePrintHistoryDTO)dtoParameter;
		if( !StrUtil.isEmpty( dto.getBarcode() ) ){
			sqlArgs.add(dto.getBarcode());
        }
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}