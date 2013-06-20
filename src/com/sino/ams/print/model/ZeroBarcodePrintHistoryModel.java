package com.sino.ams.print.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.print.dto.ZeroBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class ZeroBarcodePrintHistoryModel extends BaseSQLProducer{
	
	private SfUserDTO userAccount = null;

	public ZeroBarcodePrintHistoryModel(BaseUserDTO userAccount,
			ZeroBarcodePrintHistoryDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.userAccount = (SfUserDTO) userAccount;
	}

	/**
	 * Function 新增标签打印确认
	 * 
	 * @return boolean SQLModel
	 */
	public SQLModel getDataCreateModel() {
		ZeroBarcodePrintHistoryDTO dto = (ZeroBarcodePrintHistoryDTO) super.dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT \n");
		sb.append(" INTO	\n");
		sb.append(" ETS_BARCODE_PRINT_HISTORY	\n");
		sb.append(" (EBPH_ID , \n");
		sb.append("  ORG_ID,	\n");
		sb.append("  BARCODE,	\n");
		sb.append("  TYPE,	\n");
		sb.append("  CREATION_DATE , \n");
		sb.append("  CREATED_BY) \n");
		sb.append("  VALUES \n");
		sb.append("  ( NEWID() , ?,?,?, GETDATE(), ? ) \n");

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getType());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * Function: 得到选定的标签打印确认维护记录
	 * 
	 * @return SQLModel  
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsBarcodePrintHistoryDTO dto = (EtsBarcodePrintHistoryDTO) super.dtoParameter;
		String sqlStr = "SELECT EBP.BARCODE FROM_BARCODE, EBP.BARCODE_PRINT_NUM\n"
				+ "  FROM ETS_BARCODE_PRINT_HISTORY EBP\n"
				+ " WHERE EBP.BARCODE = ? AND EBP.ORG_ID = ? ";
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * Function: 判断ETS_BARCODE_PRINT_HISTORY表中是否有该条码的记录
	 * 
	 */
	public SQLModel getBarcodePrintCount( int orgId , String barcode ) {
		EtsBarcodePrintHistoryDTO dto = (EtsBarcodePrintHistoryDTO) super.dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT COUNT(1) FROM ETS_BARCODE_PRINT_HISTORY EBP WHERE EBP.BARCODE = ? AND EBP.ORG_ID = ?";
		sqlArgs.add( orgId );
		sqlArgs.add( barcode );
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
