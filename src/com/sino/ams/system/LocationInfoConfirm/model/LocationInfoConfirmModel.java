package com.sino.ams.system.LocationInfoConfirm.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.LocationInfoConfirm.dto.LocationInfoConfirmDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * @author Administrator
 *
 */
public class LocationInfoConfirmModel extends AMSSQLProducer {
	private LocationInfoConfirmDTO dto = null;

	/**
	 * 功能：设备分类表(EAM) ETS_SYSTEM_ITEM 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSystemItemDTO 本次操作的数据
	 */
	public LocationInfoConfirmModel(SfUserDTO userAccount, LocationInfoConfirmDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (LocationInfoConfirmDTO) dtoParameter;
	}

	/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"SELECT EO.WORKORDER_OBJECT_NO,\n" +
			"       EO.WORKORDER_OBJECT_NAME,\n" + 
			"       EO.WORKORDER_OBJECT_CODE,\n" + 
			"       ACH.NEW_LOCATION,\n" + 
			"       ACH.TRANS_NO\n" + 
			"  FROM AMS_ASSETS_CHECK_HEADER ACH, ETS_OBJECT EO\n" + 
			" WHERE ACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" + 
			"   AND ACH.ORDER_STATUS = 'ARCHIEVED'\n" + 
			"   AND dbo.NVL(ACH.NEW_LOCATION,EO.WORKORDER_OBJECT_NAME)<>EO.WORKORDER_OBJECT_NAME\n" +
			"   AND ACH.ORGANIZATION_ID = ?\n" + 
			"   AND (" + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
			"   AND (" + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
			"   AND ACH.TRANS_NO NOT IN (SELECT TRANS_NO FROM ETS_OBJECT_CONFIRM) ORDER BY ACH.TRANS_NO DESC";
		
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行批量确认操作。
	 * @param  itemCodes String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel confirmModel(String locationName,String workorderObjectNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"UPDATE ETS_OBJECT\n" +
			"   SET WORKORDER_OBJECT_NAME = ?, WORKORDER_OBJECT_LOCATION = ? WHERE WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(locationName);
		sqlArgs.add(locationName);
		sqlArgs.add(workorderObjectNo);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getWorkorderObjectNameModel(String workorderObjectNo,String transNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT NEW_LOCATION\n"
				+ "                                  FROM AMS_ASSETS_CHECK_HEADER\n"
				+ "                                 WHERE CHECK_LOCATION = ? AND TRANS_NO = ?";

		sqlArgs.add(workorderObjectNo);
		sqlArgs.add(transNo);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：执行插入表ETS_SYSTEM_ITEM_CHECK操作。
	 * 
	 * @param itemCode
	 *            String
	 * @param isCorrect
	 *            String
	 * @param matchReason
	 *            String
	 * @param remark
	 *            String
	 * @return SQLModel 返回数据用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel insertIntoCheck(String workorderObjectNo,String status) throws
		SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"INSERT INTO ETS_OBJECT_CONFIRM\n" + 
			"  (ETS_OBJECT_CONFIRM_ID,\n" + 
			"   WORKORDER_OBJECT_CODE,\n" + 
			"   WORKORDER_OBJECT_NAME,\n" + 
			"   TRANS_NO,\n" + 
			"   CONFIRM_STATUS,\n" + 
			"   CONFIRM_DATE,\n" + 
			"   CONFIRM_USER)\n" + 
			"  (SELECT NEWID(),\n" + 
			"          EO.WORKORDER_OBJECT_CODE,\n" + 
//			"          EO.WORKORDER_OBJECT_NAME,\n" + 
			"          ACH.NEW_LOCATION,\n" + 
			"          ACH.TRANS_NO,\n" + 
			"          '"+status+"',\n" + 
			"          GETDATE(),\n" + 
			"          ?\n" + 
			"     FROM AMS_ASSETS_CHECK_HEADER ACH, ETS_OBJECT EO\n" + 
			"    WHERE ACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" + 
			"      AND ACH.ORDER_STATUS = 'ARCHIEVED'\n" + 
			"      AND ACH.CHECK_LOCATION = ?)";
		
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(workorderObjectNo);		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getQueryPageData() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"SELECT EOC.WORKORDER_OBJECT_CODE,\n" +
			"       EOC.WORKORDER_OBJECT_NAME,\n" + 
			"       EOC.TRANS_NO,\n" + 
			"       EOC.CONFIRM_STATUS,\n" + 
			"       EOC.CONFIRM_DATE,\n" + 
			"       SF.USERNAME\n" + 
			"  FROM ETS_OBJECT_CONFIRM EOC, SF_USER SF\n" + 
			" WHERE SF.USER_ID = EOC.CONFIRM_USER" +
			"   AND (" + SyBaseSQLUtil.isNull() + " OR EOC.WORKORDER_OBJECT_CODE LIKE ?)\n" +
			"   AND (" + SyBaseSQLUtil.isNull() + " OR SF.USERNAME LIKE ?)\n" +
			"   AND (" + SyBaseSQLUtil.isNull() + " OR EOC.CONFIRM_DATE >= CONVERT(DATETIME,?)) ORDER BY EOC.TRANS_NO DESC\n";
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(dto.getConfirmUserName());
		sqlArgs.add(dto.getConfirmUserName());
		sqlArgs.add(dto.getConfirmDate());
		sqlArgs.add(dto.getConfirmDate());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
}
