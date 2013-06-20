package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroImportDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class ZeroTurnImportModel extends BaseSQLProducer{
	ZeroImportDTO zeroImportDTO=null;
	SfUserDTO sfUser = null;
	public ZeroTurnImportModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
        this.zeroImportDTO = (ZeroImportDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
	}
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr="	SELECT ROW_NUM, BARCODE, ITEM_NAME, ITEM_SPEC," +
        		" 	MANUFACTURER_NAME, CONTENT_CODE, ITEM_QTY, LIFE_IN_YEARS," +
        		" 	COST, START_DATE, OPE_NAME, NLE_NAME, CONSTRUCT_STATUS, " +
        		"	LOCATION_SEGMENT1, LOCATION_SEGMENT2_NAME, LOCATION_SEGMENT2," +
        		" 	LOCATION_SEGMENT3, EMPLOYEE_NUMBER, EMPLOYEE_NAME, APPLY_TYPE, " +
        		"	CONTACT_PERSON, CONTACT_NUMBER, EXPECT_ARRIVAL_TIME, ERROR_MSG " +
        		"	FROM dbo.ZERO_TURN_REIMBURSE_IMPORT";
        
        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}   
	public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr="	DELETE  FROM ZERO_TURN_REIMBURSE_IMPORT";
        
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
	}   
	public SQLModel insertImportModel(ZeroImportDTO zeroDTO) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr="		INSERT INTO dbo.ZERO_TURN_REIMBURSE_IMPORT(ROW_NUM, " +
        		"	BARCODE, ITEM_NAME, ITEM_SPEC, MANUFACTURER_NAME, CONTENT_CODE," +
        		" 	ITEM_QTY, LIFE_IN_YEARS, COST, START_DATE, OPE_NAME, NLE_NAME," +
        		" 	CONSTRUCT_STATUS, LOCATION_SEGMENT1, LOCATION_SEGMENT2_NAME, " +
        		"	LOCATION_SEGMENT2, LOCATION_SEGMENT3, EMPLOYEE_NUMBER, EMPLOYEE_NAME, " +
        		"	APPLY_TYPE, CONTACT_PERSON, CONTACT_NUMBER, EXPECT_ARRIVAL_TIME) " +
        		"	VALUES(?, ?, ?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?, ?)";
        sqlArgs.add(zeroDTO.getRowNum());
        sqlArgs.add(zeroDTO.getBarcode());
        sqlArgs.add(zeroDTO.getItemName());
        sqlArgs.add(zeroDTO.getItemSpec());
        sqlArgs.add(zeroDTO.getManufacturerName());
        sqlArgs.add(zeroDTO.getContentCode());
        sqlArgs.add(zeroDTO.getItemQty());
        sqlArgs.add(zeroDTO.getLifeInYears());
        sqlArgs.add(zeroDTO.getCost());
        sqlArgs.add(zeroDTO.getStartDate());
        sqlArgs.add(zeroDTO.getOpeName());
        sqlArgs.add(zeroDTO.getNleName());
        sqlArgs.add(zeroDTO.getConstructStatus());
        sqlArgs.add(zeroDTO.getLocationSegment1());
        sqlArgs.add(zeroDTO.getLocationSegment2Name());
        sqlArgs.add(zeroDTO.getLocationSegment2());
        sqlArgs.add(zeroDTO.getLocationSegment3());
        sqlArgs.add(zeroDTO.getEmployeeNumber());
        sqlArgs.add(zeroDTO.getEmployeeName());
        sqlArgs.add(zeroDTO.getApplyType());
        sqlArgs.add(zeroDTO.getContactPerson());
        sqlArgs.add(zeroDTO.getContactNumber());
        sqlArgs.add(zeroDTO.getExpectArrivalTime());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
	} 
}
