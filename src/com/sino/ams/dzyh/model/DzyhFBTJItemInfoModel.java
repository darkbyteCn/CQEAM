package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: DzyhFBTJItemInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“DzyhFBTJItemInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class DzyhFBTJItemInfoModel extends AMSSQLProducer {

	/**
	 * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoDTO 本次操作的数据
	 */
	public DzyhFBTJItemInfoModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " ESI.ITEM_CATEGORY2 EII_ITEM_CATEGORY2,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_NAME EII_ITEM_NAME,"
			+ " ESI.ITEM_SPEC EII_ITEM_SPEC,"
			+ " EII.ITEM_QTY,"
			+ " EII.PRICE,"
			+ " EII.RESPONSIBILITY_DEPT,"
			+ " AMD.DEPT_NAME EII_DEPT_NAME,"
			+ " EII.RESPONSIBILITY_USER,"
			+ " AME.USER_NAME EII_USER_NAME,"
			+ " EII.ADDRESS_ID,"
			+ " EO.WORKORDER_OBJECT_NAME EII_WORKORDER_OBJECT_NAME,"
			+ " EII.MAINTAIN_USER,"
			+ " EII.LAST_LOC_CHG_DATE,"
			+ " EII.ATTRIBUTE3,"
			+ " EII.REMARK"
			+ " FROM"
			+ " ETS_SYSTEM_ITEM ESI,"
			+ " ETS_ITEM_INFO EII,"
			+ " AMS_MIS_DEPT AMD,"
			+ " ETS_OBJECT EO,"
			+ " AMS_OBJECT_ADDRESS AOA,"
			+ " AMS_MIS_EMPLOYEE AME"
			+ " WHERE"
			+ " ESI.ITEM_CODE=EII.ITEM_CODE"
			+ " AND EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"
			+ " AND EII.ADDRESS_ID=AOA.ADDRESS_ID"
			+ " AND AOA.BOX_NO='0000'"
			+ " AND AOA.NET_UNIT='0000'"
			+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
			+ " AND EII.RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
//			+ " AND ESI.ITEM_CATEGORY2 = ?"
//			+ " AND ESI.ITEM_NAME = ?"
//			+ " AND ESI.ITEM_SPEC = ?"
			+ " AND SYSTEMID = ?";
//		sqlArgs.add(dto.getEiiItemCategory2());
//		sqlArgs.add(dto.getEiiItemName());
//		sqlArgs.add(dto.getEiiItemSpec());
		sqlArgs.add(dto.getSystemid());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：低值易耗品的信息查询(EAM) ETS_ITEM_INFO页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " ESI.ITEM_CATEGORY2 EII_ITEM_CATEGORY2,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_NAME EII_ITEM_NAME,"
			+ " ESI.ITEM_SPEC EII_ITEM_SPEC,"
			+ " EII.ITEM_QTY,"
			+ " ESI.ITEM_UNIT,"
			+ " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) EII_DEPT_NAME"
			+ " FROM"
			+ " ETS_SYSTEM_ITEM ESI,"
			+ " ETS_ITEM_INFO EII,"
			+ " ETS_OBJECT EO,"
			+ " AMS_OBJECT_ADDRESS AOA"
			+ " WHERE"
			+ " ESI.ITEM_CODE=EII.ITEM_CODE"
			+ " AND EII.FINANCE_PROP='DZYH'"
			+ " AND EII.ADDRESS_ID=AOA.ADDRESS_ID"
			+ " AND AOA.BOX_NO='0000'"
			+ " AND AOA.NET_UNIT='0000'"
			+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY2 LIKE ?)"
			+ " AND EII.RESPONSIBILITY_DEPT LIKE dbo.NVL(? ,EII.RESPONSIBILITY_DEPT)";
		
		sqlArgs.add(dto.getEiiItemCategory2());
		sqlArgs.add(dto.getEiiItemCategory2());
		sqlArgs.add(dto.getResponsibilityDept());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

}