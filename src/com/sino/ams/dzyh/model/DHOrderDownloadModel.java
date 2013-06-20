package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DHOrderDownloadModel extends AMSSQLProducer {

	public DHOrderDownloadModel(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造获取当前用户可下载的所有盘点工单的SQL
	 * <B>含低耗盘点工单、低耗补扫工单、仪器仪表中需要PDA确认的工单</B>
	 * @return SQLModel
	 */
	public SQLModel getDownloadOrdersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderStatus = "'"
							 + LvecDicts.CHK_STATUS_DISTRUIBUTED
							 + "', '"
							 + LvecDicts.CHK_STATUS_DOWNLOADED
							 + "'";
		String sqlStr = "SELECT"
						+ " AAST.TASK_NAME,"
						+ " EDCH.HEADER_ID,"
						+ " EDCH.ORDER_NO,"
						+ " EDCH.CHECK_TASK_ID,"
						+ " EDCH.BATCH_ID,"
						+ " EDCH.START_TIME,"
						+ " EDCH.IMPLEMENT_DAYS,"
						+ " dbo.APP_GET_USER_NAME(EDCH.CREATED_BY) CREATED_USER,"
						+ " dateadd(day,EDCH.IMPLEMENT_DAYS,EDCH.START_TIME) DEADLINE_DATE,"
						+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
						+ " EO.WORKORDER_OBJECT_NAME LOCATION_NAME,"
						+ " EDCH.CREATION_DATE,"
						+ " SU.LOGIN_NAME CREATED_LOGIN_USER,"
						+ " EDCH.DISTRIBUTE_DATE,"
						+ " EDCH.ORDER_TYPE,"
						+ " dbo.APP_GET_FLEX_VALUE(EDCH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_VALUE"
						+ " FROM"
						+ " ETS_OBJECT                 EO,"
						+ " SF_USER                    SU,"
						+ " EAM_CHECK_TASK             AAST,"
						+ " EAM_DH_CHECK_HEADER        EDCH"
						+ " WHERE"
						+ " EDCH.CHECK_TASK_ID = AAST.CHECK_TASK_ID"
						+ " AND EDCH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND EDCH.CREATED_BY = SU.USER_ID"
						+ " AND ((EDCH.ORDER_TYPE = 'YQYB-CHK' AND EDCH.CHECK_TOOLS = 0)"
						+ " OR (EDCH.ORDER_TYPE <> 'YQYB-CHK'))"
						+ " AND EDCH.ORDER_STATUS IN ("
						+ orderStatus
						+ ")"
						+ " AND EDCH.IMPLEMENT_BY = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造更新当前用户下载的工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getOrderDownloadModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " EAM_DH_CHECK_HEADER EDCH"
						+ " SET"
						+ " EDCH.ORDER_STATUS = ?,"
						+ " EDCH.DOWNLOAD_DATE = GETDATE(),"
						+ " EDCH.DOWNLOAD_BY = ?"
						+ " WHERE"
						+ " EDCH.HEADER_ID = ?";
		sqlArgs.add(dto.getOrderStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取当前用户某一盘点工单下的所有待盘点资产SQL
	 * @return SQLModel
	 */
	public SQLModel getOrderAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EDCL.HEADER_ID,"
						+ " EDCL.BARCODE,"
						+ " EDCL.ITEM_CODE,"
						+ " EDCL.ITEM_CATEGORY,"
						+ " EDCL.ITEM_NAME,"
						+ " EDCL.ITEM_SPEC,"
						+ " EDCL.ITEM_CATEGORY2,"
						+ " EDCL.VENDOR_BARCODE,"
						+ " EDCL.PRICE,"
						+ " EDCL.START_DATE,"
						+ " EDCL.RESPONSIBILITY_USER,"
						+ " EDCL.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " EDCL.MAINTAIN_USER,"
						+ " EDCL.VENDOR_ID,"
						+ " EDCL.VENDOR_NAME"
						+ " FROM"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " WHERE"
						+ " EDCL.BARCODE = EDCL.BARCODE"
						+ " AND EDCL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
