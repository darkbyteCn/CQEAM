package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.sinoflow.appbase.model.AMSSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class ZeroTurnLineModel extends AMSSQLProducer {

	public ZeroTurnLineModel(SfUserBaseDTO userAccount, ZeroTurnLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
		// TODO Auto-generated constructor stub
	}
	
	  /**
     * 功能：插入到接口表。 ZERO_TURN_ADD_L
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    @SuppressWarnings("unchecked")
	public SQLModel getDataCreateModel() {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ZeroTurnLineDTO lineDTO = (ZeroTurnLineDTO) dtoParameter;
        String sqlStr=" INSERT INTO ZERO_TURN_ADD_L" +
		"  (LINE_ID,TRANS_ID, COMPANY_CODE, BARCODE, CONTENT_CODE, CONTENT_NAME, " +
		"   ASSETS_DESCRIPTION, ITEM_SPEC, ITEM_QTY, UNIT_OF_MEASURE, MANUFACTURER_NAME, " +
		"   OBJECT_NO, WORKORDER_OBJECT_NAME, RESPONSIBILITY_DEPT, RESPONSIBILITY_USER, SPECIALITY_DEPT," +
		"   START_DATE, YEARS, PRICE, PROCURE_CODE, COST_CENTER_CODE, IS_SHARE, " +
		"   IS_BULID, LNE_ID, OPE_ID, CEX_ID, NLE_ID, REMARK,UNIT_MANAGER,COMPUTE_DAYS, TRANS_STATUS, TRANS_STATUS_VAL,ZERO_NO,RECORD) "
	 		+"  VALUES(NEWID(),?,?,?,?,?,?,?,CONVERT(decimal, ?),?,?,?,?,?,?,?,?,CONVERT(INT, ?),CONVERT(decimal, ?),?,?,?,?,?,?,?,?,?,?,CONVERT(INT, ?),?,?,?,?)";
		 sqlArgs.add(lineDTO.getTransId());
		 sqlArgs.add(lineDTO.getCompanyCode());
		 sqlArgs.add(lineDTO.getBarcode());
		 sqlArgs.add(lineDTO.getContentCode());
		 sqlArgs.add(lineDTO.getContentName());
		 sqlArgs.add(lineDTO.getAssetsDescription());
		 sqlArgs.add(lineDTO.getItemSpec());
		 sqlArgs.add(lineDTO.getItemQty());
		 sqlArgs.add(lineDTO.getUnitOfMeasure());
		 sqlArgs.add(lineDTO.getManufacturerName());
		 sqlArgs.add(lineDTO.getObjectNo());
		 sqlArgs.add(lineDTO.getWorkorderObjectName());
		 sqlArgs.add(lineDTO.getResponsibilityDept());
		 sqlArgs.add(lineDTO.getResponsibilityUser());
		 sqlArgs.add(lineDTO.getSpecialityDept());
		 sqlArgs.add(lineDTO.getStartDate());
		 sqlArgs.add(lineDTO.getYears());
		 sqlArgs.add(lineDTO.getPrice());
		 sqlArgs.add(lineDTO.getProcureCode());
		 sqlArgs.add(lineDTO.getCostCenterCode());
		 sqlArgs.add(lineDTO.getIsShare());
		 sqlArgs.add(lineDTO.getIsBulid());
		 sqlArgs.add(lineDTO.getLneId());
		 sqlArgs.add(lineDTO.getOpeId());
		 sqlArgs.add(lineDTO.getCexId());
		 sqlArgs.add(lineDTO.getNleId());
		 sqlArgs.add(lineDTO.getRemark());
		 sqlArgs.add(lineDTO.getUnitManager());
		 sqlArgs.add(lineDTO.getComputeDays());
		 sqlArgs.add("PRE_ASSETS");
		 sqlArgs.add("预转资");
		 sqlArgs.add(lineDTO.getZeroNo());
		 sqlArgs.add(lineDTO.getRecord());
		 sqlModel.setSqlStr(sqlStr);
		 sqlModel.setArgs(sqlArgs);
         return sqlModel;
    }

	public SQLModel updateHeader() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
