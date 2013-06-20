package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsRcvLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: AmsAssetsRcvLineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsRcvLineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsRcvLineModel extends AMSSQLProducer {

	/**
	 * 功能：资产调拨接收行表(用于部门间和公司间资产调拨) AMS_ASSETS_RCV_LINE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsRcvLineDTO 本次操作的数据
	 */
	public AmsAssetsRcvLineModel(SfUserDTO userAccount, AmsAssetsRcvLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产调拨接收行表(用于部门间和公司间资产调拨) AMS_ASSETS_RCV_LINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsRcvLineDTO dto = (AmsAssetsRcvLineDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_RCV_LINE("
							+ " RECEIVE_LINE_ID,"
							+ " RECEIVE_HEADER_ID,"
							+ " TRANS_LINE_ID,"
							+ " CREATION_DATE,"
							+ " CREATED_BY"
							+ ") VALUES ("
							+ "  NEWID() , ?, ?, ?, ?)";
			sqlArgs.add(dto.getReceiveHeaderId());
			sqlArgs.add(dto.getTransLineId());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getCreatedBy());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 外键字段名称对应的DTO属性。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) throws
			SQLModelException {
		SQLModel sqlModel = null;
		if (foreignKey.equals("receiveHeaderId")) {
			sqlModel = getDataByRcvHeaderIdModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：根据接收单头ID获取接收单行数据
	 * @return SQLModel
	 */
	private SQLModel getDataByRcvHeaderIdModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsRcvLineDTO dto = (AmsAssetsRcvLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AATL.TRANS_ID,"
						+ " AATL.LINE_ID,"
						+ " AATL.BARCODE,"
						+ " AATL.REMARK,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
						+ " AAAV.COST,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.DEPRECIATION,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " AAAV.LIFE_IN_YEARS,"
						+ " AAAV.SCRAP_VALUE,"
						+ " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
						+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
						+ " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
						+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
						+ " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
						+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
						+ " AATL.OLD_FA_CATEGORY_CODE,"
						+ " AATL.OLD_DEPRECIATION_ACCOUNT,"
						+ " AATL.FA_CATEGORY_CODE,"
						+ " AATL.DEPRECIATION_ACCOUNT,"
						+ " AATL.LINE_TRANS_DATE,"
						+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
						+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
						+ " AMS_ASSETS_PKG.GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,"
						+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
						+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " (SELECT"
						+ " AOA.ADDRESS_ID"
						+ " FROM"
						+ " AMS_OBJECT_ADDRESS AOA"
						+ " WHERE"
						+ " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " ) ADDRESS_ID"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_LINE AATL,"
						+ " AMS_ASSETS_RCV_LINE   AARL,"
						+ " AMS_MIS_EMPLOYEE      AMEO,"
						+ " AMS_MIS_DEPT          AMDO,"
						+ " ETS_OBJECT            EOO,"
						+ " AMS_MIS_EMPLOYEE      AMEN,"
						+ " AMS_MIS_DEPT          AMDN,"
						+ " ETS_OBJECT            EON,"
						+ " AMS_ASSETS_ADDRESS_V  AAAV"
						+ " WHERE"
						+ " AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
						+ " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
						+ " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
						+ " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
						+ " AND AATL.BARCODE = AAAV.BARCODE"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AATL.ASSIGNED_DATE") + ""
						+ " AND AATL.LINE_ID = AARL.TRANS_LINE_ID"
						+ " AND AARL.RECEIVE_HEADER_ID = ?";
		sqlArgs.add(dto.getReceiveHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
