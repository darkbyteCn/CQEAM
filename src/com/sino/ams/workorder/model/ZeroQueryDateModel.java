package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

public class ZeroQueryDateModel {
	
	public SQLModel getDate(String oneBarcode) throws SQLModelException {
    	SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * \n"); 
		sb.append(" FROM ETS_ITEM_TURN "); 
		sb.append(" WHERE BARCODE = '"+oneBarcode+"'"); 
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
	
	public SQLModel saveDate(ZeroTurnLineDTO dtoParameter) throws SQLModelException {
    	SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE ETS_ITEM_TURN SET CONTENT_CODE = ?,ITEM_SPEC = ?,ITEM_QTY = CONVERT(decimal,?),UNIT_OF_MEASURE =? ,OBJECT_NO =? ,WORKORDER_OBJECT_NAME =? ,PROCURE_CODE = ?,START_DATE=?,DIFF_RESULT=? \n"); 
		sb.append(" WHERE BARCODE = '"+dtoParameter.getBarcode()+"'"); 
		sqlArgs.add(dtoParameter.getContentCode());
		sqlArgs.add(dtoParameter.getItemSpec());
		sqlArgs.add(dtoParameter.getItemQty());
		sqlArgs.add(dtoParameter.getUnitOfMeasure());
		sqlArgs.add(dtoParameter.getObjectNo());
		sqlArgs.add(dtoParameter.getWorkorderObjectName());
		sqlArgs.add(dtoParameter.getProcureCode());
		sqlArgs.add(dtoParameter.getStartDate());
		sqlArgs.add(dtoParameter.getDiffResult());
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
	
	//ÐÞ¸ÄETS_ITEM_INFO×´Ì¬
	@SuppressWarnings("unchecked")
	public SQLModel updateEtsItemStatus(String barcode,String status,String objectCode){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr=" UPDATE ETS_ITEM_INFO  \n"
        	         +" SET ITEM_STATUS =? ,\n"
        	         +"  ADDRESS_ID =(SELECT aoa.ADDRESS_ID FROM ETS_OBJECT eo,AMS_OBJECT_ADDRESS aoa WHERE  eo.WORKORDER_OBJECT_NO = aoa.OBJECT_NO AND  eo.WORKORDER_OBJECT_CODE = ?) \n"
        	         +" WHERE BARCODE =? \n";
        sqlArgs.add(status);
        sqlArgs.add(objectCode);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

}
