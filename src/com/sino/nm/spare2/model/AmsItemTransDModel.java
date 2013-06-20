package com.sino.nm.spare2.model;



import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemTransDDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemTransDModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemTransDModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Herui
 * @version 1.0
 */


public class AmsItemTransDModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件业务明细表(AMS) AMS_ITEM_TRANS_D 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemTransDDTO 本次操作的数据
	 */
	public AmsItemTransDModel(SfUserDTO userAccount, AmsItemTransDDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " AMS_ITEM_TRANS_D("
			+ " TRANS_ID,"
			+ " LINE_ID,"
			+ " DETAIL_ID,"
			+ " ORGANIZATION_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " CONFIRM_QUANTITY,"
			+ " CUR_ONHAND_QTY"
			+ ") VALUES ("
			+ " ?, ?, AMS_ITEM_TRANS_D_S.NEXTVAL, ?, ?, ?, ?, ?)";

		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_ITEM_TRANS_D"
			+ " SET"
			+ " TRANS_ID = ?,"
			+ " LINE_ID = ?,"
			+ " ORGANIZATION_ID = ?,"
			+ " ITEM_CODE = ?,"
			+ " QUANTITY = ?,"
			+ " CONFIRM_QUANTITY = ?,"
			+ " CUR_ONHAND_QTY = ?"
			+ " WHERE"
			+ " DETAIL_ID = ?";

		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());
		sqlArgs.add(amsItemTransD.getDetailId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_ITEM_TRANS_D"
				+ " WHERE"
				+ " DETAIL_ID = ?";
			sqlArgs.add(amsItemTransD.getDetailId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " LINE_ID,"
			+ " DETAIL_ID,"
			+ " ORGANIZATION_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " CONFIRM_QUANTITY,"
			+ " CUR_ONHAND_QTY"
			+ " FROM"
			+ " AMS_ITEM_TRANS_D"
			+ " WHERE"
			+ " DETAIL_ID = ?";
		sqlArgs.add(amsItemTransD.getDetailId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " LINE_ID,"
			+ " DETAIL_ID,"
			+ " ORGANIZATION_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " CONFIRM_QUANTITY,"
			+ " CUR_ONHAND_QTY"
			+ " FROM"
			+ " AMS_ITEM_TRANS_D"
			+ " WHERE"
			+ " (? IS NULL OR TRANS_ID LIKE ?)"
			+ " AND (? IS NULL OR LINE_ID LIKE ?)"
			+ " AND (? IS NULL OR DETAIL_ID LIKE ?)"
			+ " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
			+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
			+ " AND (? IS NULL OR QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR CONFIRM_QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR CUR_ONHAND_QTY LIKE ?)";
		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getDetailId());
		sqlArgs.add(amsItemTransD.getDetailId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务明细表(AMS) AMS_ITEM_TRANS_D页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemTransDDTO amsItemTransD = (AmsItemTransDDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " LINE_ID,"
			+ " DETAIL_ID,"
			+ " ORGANIZATION_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " CONFIRM_QUANTITY,"
			+ " CUR_ONHAND_QTY"
			+ " FROM"
			+ " AMS_ITEM_TRANS_D"
			+ " WHERE"
			+ " (? IS NULL OR TRANS_ID LIKE ?)"
			+ " AND (? IS NULL OR LINE_ID LIKE ?)"
			+ " AND (? IS NULL OR DETAIL_ID LIKE ?)"
			+ " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
			+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
			+ " AND (? IS NULL OR QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR CONFIRM_QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR CUR_ONHAND_QTY LIKE ?)";
		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getTransId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getLineId());
		sqlArgs.add(amsItemTransD.getDetailId());
		sqlArgs.add(amsItemTransD.getDetailId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getOrganizationId());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getItemCode());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getConfirmQuantity());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());
		sqlArgs.add(amsItemTransD.getCurOnhandQty());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}