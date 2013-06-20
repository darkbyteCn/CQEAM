package com.sino.ams.dzyh.model;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamItemDisposeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;


/**
 * <p>Title: DzyhDisposeModel</p>
 * <p>Description:程序自动生成SQL构造器“DzyhDisposeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class DzyhDisposeModel extends AMSSQLProducer {

	/**
	 * 功能：物资处置单据(EAM) EAM_ITEM_DISPOSE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamItemDisposeDTO 本次操作的数据
	 */
	public DzyhDisposeModel(SfUserDTO userAccount, EamItemDisposeDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成物资处置单据(EAM) EAM_ITEM_DISPOSE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamItemDisposeDTO dto = (EamItemDisposeDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " EAM_ITEM_DISPOSE("
				+ " DISPOSE_ID,"
				+ " BARCODE,"
				+ " SYSTEMID,"
				+ " DISPOSE_REASON,"
				+ " DISPOSE_TYPE,"
				+ " REMARK,"
				+ " CREATED_BY,"
				+ " CREATION_DATE,"
				+ " LAST_UPDATE_BY,"
				+ " LAST_UPDATE_DATE"
				+ ") VALUES ("
				+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getSystemid());
			sqlArgs.add(dto.getDisposeReason());
			sqlArgs.add(dto.getDisposeType());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(dto.getCreatedBy());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getLastUpdateBy());
			sqlArgs.add(dto.getLastUpdateDate());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成物资处置单据(EAM) EAM_ITEM_DISPOSE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamItemDisposeDTO dto = (EamItemDisposeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_CATEGORY2,"
			+ " ESI.ITEM_NAME,"
			+ " ESI.ITEM_SPEC,"
			+ " EII.ITEM_QTY,"
			+ " EII.PRICE,"
			+ " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) DEPT_NAME,"
			+ " EII.RESPONSIBILITY_USER,"
			+ " AME.USER_NAME,"
			+ " EII.ADDRESS_ID,"
			+ " EO.WORKORDER_OBJECT_NAME,"
			+ " EII.MAINTAIN_USER,"
			+ " EII.LAST_LOC_CHG_DATE,"
			+ " EII.ATTRIBUTE3,"
			+ " EII.REMARK EII_REMARK"
			+ " FROM"
			+ " ETS_ITEM_INFO EII,"
			+ " ETS_SYSTEM_ITEM ESI,"
			+ " ETS_OBJECT EO,"
			+ " AMS_OBJECT_ADDRESS AOA,"
			+ " AMS_MIS_EMPLOYEE AME"
			+ " WHERE EII.ITEM_CODE=ESI.ITEM_CODE"
			+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
			+ " AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO "
			+ " AND AOA.BOX_NO = '0000'"
			+ " AND AOA.NET_UNIT = '0000'"
			+ " AND EII.RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
			+ " AND SYSTEMID = ?";
		sqlArgs.add(dto.getSystemid());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成物资处置单据(EAM) EAM_ITEM_DISPOSE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamItemDisposeDTO dto = (EamItemDisposeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_CATEGORY2,"
			+ " ESI.ITEM_NAME,"
			+ " ESI.ITEM_SPEC,"
			+ " EII.ADDRESS_ID,"
			+ " EO.WORKORDER_OBJECT_NAME,"
			+ " EII.RESPONSIBILITY_DEPT,"
			+ " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) DEPT_NAME,"
			+ " EII.MAINTAIN_USER"
			+ " FROM"
			+ " ETS_ITEM_INFO      EII,"
			+ " ETS_SYSTEM_ITEM    ESI,"
			+ " AMS_OBJECT_ADDRESS AOA,"
			+ " ETS_OBJECT         EO"
			+ " WHERE EII.ITEM_CODE=ESI.ITEM_CODE"
			+ " AND EII.ITEM_STATUS='NORMAL'"
			+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
			+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
			+ " AND AOA.BOX_NO = '0000'"
			+ " AND AOA.NET_UNIT = '0000'"
			+ " AND AOA.NET_UNIT = '0000'"
			+ " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
			+ " AND ESI.ITEM_CATEGORY2 LIKE dbo.NVL(?, ESI.ITEM_CATEGORY2)"
			+ " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
			+ " AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC)";
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemCategory2());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
	
		return sqlModel;
	}

	public int createDisposeUpdateEii(Connection conn,EamItemDisposeDTO disponseDto) throws SQLException{
	
		int success=0;
		String sqlStr="BEGIN ? :=EAM_ITEM_DISPOSE_PKG.insertEamItemDispose(?,?,?,?,?,?,?); END;";
		CallableStatement cst = null;
		
		try {
			cst= conn.prepareCall(sqlStr);

			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, disponseDto.getBarcode());
			cst.setInt(3, disponseDto.getSystemid() );
			cst.setString(4, disponseDto.getDisposeReason());
			cst.setString(5, disponseDto.getDisposeType());
			cst.setString(6, disponseDto.getEiiRemark());
			cst.setInt(7,  userAccount.getUserId() );
			cst.registerOutParameter(8, Types.VARCHAR);
			
			Logger.logInfo(sqlStr);
			cst.execute();
			success=cst.getInt(1);
			
			if(success==0){
				Logger.logError(cst.getString(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBStatement(cst);
		}
		return success;
	}
}
