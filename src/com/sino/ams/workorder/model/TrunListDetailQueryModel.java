package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class TrunListDetailQueryModel extends BaseSQLProducer {
	EtsWorkorderDTO workorderDTO = null;
	SfUserDTO sfUser = null;

	public TrunListDetailQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
		sfUser = (SfUserDTO) userAccount;
	}

	/**
	 * 获取工单明细查询的SQLModel
	 * 
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT EPPA.SEGMENT1,EPPA.NAME,\n"
				+ " dbo.APP_GET_ORGNIZATION_NAME(EW.ORGANIZATION_ID) ORG_NAME,\n"
				+ " dbo.APP_GET_OBJECT_CODE(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_CODE,\n"
				+ " dbo.APP_GET_OBJECT_NAME(EW.WORKORDER_OBJECT_NO) WORKORDER_OBJECT_NAME,\n"
				+ " EW.DIFFERENCE_REASON,EW.WORKORDER_NO,\n"
				+ " dbo.APP_GET_USER_NAME(EW.IMPLEMENT_BY) IMPLEMENT_USER,\n"
				+ " dbo.APP_GET_USER_NAME(EW.CHECKOVER_BY) CHECKOVER_USER,\n"
				+ " EWDD.BARCODE,ESI.ITEM_NAME,ESI.ITEM_SPEC,\n"
				+ " dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EWDD.ITEM_STATUS), 'ORDER_ITEM_STATUS') ITEM_STATUS_NAME,\n"
				+ " '1' ITEM_QTY,\n"
				+ " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC,\n"
				+ " AMD.DEPT_NAME, AMP.USER_NAME,\n"
				+ " dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_FLAG, 'WORKORDER_STATUS') WORKORDER_FLAG_DESC \n"
				+ " FROM ETS_WORKORDER  EW,ETS_WORKORDER_BATCH    EWB,\n"
				+ " SF_USER  SU, "
				+ " ETS_OBJECT EO,"
				+ " ETS_PA_PROJECTS_ALL    EPPA,\n"
				+ " ETS_WORKORDER_DTL EWDD, "
				+ "ETS_SYSTEM_ITEM  ESI,"
				+ "ETS_ITEM_INFO  EII,\n"
				+ " AMS_MIS_DEPT  AMD,"
				+ "AMS_MIS_EMPLOYEE  AMP \n"
				+ " WHERE \n"/*EW.ARCHFLAG = 1 AND */
				// + " AND " + SyBaseSQLUtil.isNotNull("EW.SCANOVER_DATE") + " \n" // 查询已扫描并且已归档的信息
				+ " EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH \n"
				+ " AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"
				+ " AND EWDD.BARCODE = EII.BARCODE \n"
				+ " AND EWDD.WORKORDER_NO = EW.WORKORDER_NO \n";
		if (!StrUtil.isEmpty(workorderDTO.getItemName()) || !StrUtil.isEmpty(workorderDTO.getItemSpec())){
			sqlStr += " AND EWDD.ITEM_CODE = ESI.ITEM_CODE \n";
		}else{
			sqlStr += " AND EWDD.ITEM_CODE *= ESI.ITEM_CODE \n";
		}
		if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
			sqlStr += " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n";
		}else {
			sqlStr += " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n";
		}
		if (!StrUtil.isEmpty(workorderDTO.getEmployeeNumber())) {
			sqlStr += " AND EII.RESPONSIBILITY_USER = AMP.EMPLOYEE_ID \n";
		}else{
			sqlStr += " AND EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID \n";
		}
		if( !StrUtil.isEmpty( workorderDTO.getPrjId() )){
			sqlStr += " AND EW.PRJ_ID = EPPA.PROJECT_ID \n";
		}else{
			sqlStr += " AND EW.PRJ_ID *= EPPA.PROJECT_ID \n";
		}
		if (!StrUtil.isEmpty(workorderDTO.getExecuteUserName())){
			sqlStr += " AND EW.IMPLEMENT_BY = SU.USER_ID \n";
		}else{
			sqlStr += " AND EW.IMPLEMENT_BY *= SU.USER_ID \n";
		}
		sqlStr += " AND ( " + SyBaseSQLUtil.isNull()
				+ "  OR EW.WORKORDER_BATCH LIKE ?) \n" + " AND ( "
				+ SyBaseSQLUtil.isNull()
				+ "  OR EO.WORKORDER_OBJECT_CODE = ?) \n" + " AND ( "
				+ SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR EW.WORKORDER_FLAG = ?) \n"
				+ " AND ( ? IS NULL OR ? = -1 OR EW.ORGANIZATION_ID = ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR EPPA.PROJECT_ID = ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR AMP.EMPLOYEE_NUMBER = ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR EWDD.BARCODE = ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR ESI.ITEM_NAME = ?) \n"
				+ " AND ( ? IS NULL OR ? = '' OR ESI.ITEM_SPEC = ?) \n";
		
		sqlArgs.add(workorderDTO.getWorkorderBatch());
		sqlArgs.add(workorderDTO.getWorkorderBatch());

		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		sqlArgs.add(workorderDTO.getWorkorderObjectCode());

		sqlArgs.add(workorderDTO.getExecuteUserName());
		sqlArgs.add(workorderDTO.getExecuteUserName());
		sqlArgs.add(workorderDTO.getWorkorderFlag());
		sqlArgs.add(workorderDTO.getWorkorderFlag());
		sqlArgs.add(workorderDTO.getWorkorderFlag());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getOrganizationId());
		sqlArgs.add(workorderDTO.getPrjId());
		sqlArgs.add(workorderDTO.getPrjId());
		sqlArgs.add(workorderDTO.getPrjId());
		sqlArgs.add(workorderDTO.getEmployeeNumber());
		sqlArgs.add(workorderDTO.getEmployeeNumber());
		sqlArgs.add(workorderDTO.getEmployeeNumber());
		sqlArgs.add(workorderDTO.getBarcode());
		sqlArgs.add(workorderDTO.getBarcode());
		sqlArgs.add(workorderDTO.getBarcode());
		sqlArgs.add(workorderDTO.getItemName());
		sqlArgs.add(workorderDTO.getItemName());
		sqlArgs.add(workorderDTO.getItemName());
		sqlArgs.add(workorderDTO.getItemSpec());
		sqlArgs.add(workorderDTO.getItemSpec());
		sqlArgs.add(workorderDTO.getItemSpec());
		if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
			sqlStr += " AND AMD.DEPT_CODE IN ("+ZeroTurnDifferentCheckModel.retStrArray(workorderDTO.getDeptCode())+") \n";
		}
		sqlStr +=" ORDER BY EPPA.SEGMENT1, EW.WORKORDER_NO, EWDD.BARCODE";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
