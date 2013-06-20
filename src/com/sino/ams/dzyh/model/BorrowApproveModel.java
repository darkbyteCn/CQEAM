package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamYbBorrowLogDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: BorrowApproveModel</p>
 * <p>Description:程序自动生成SQL构造器“EamYbBorrowLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class BorrowApproveModel extends EamYbBorrowLogModel {

	/**
	 * 功能：仪器仪表借用日志 EAM_YB_BORROW_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamYbBorrowLogDTO 本次操作的数据
	 */
	public BorrowApproveModel(BaseUserDTO userAccount, EamYbBorrowLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造待审批仪器仪表借用申请SQL
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
//		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EDCV.CATALOG_NAME,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EII.ORGANIZATION_ID,"
						+ " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY,"
						+ " EII.START_DATE,"
						+ " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " EII.MAINTAIN_USER,"
						+ " EYBL.BORROW_LOG_ID,"
						+ " EYBL.STATUS,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EYBL.STATUS, 'YB_BORROW_STATUS') STATUS_VALUE,"
						+ " EYBL.CREATED_BY,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EYBL.CREATED_BY) CREATED_USER,"
						+ " EYBL.BORROW_DATE,"
						+ " EYBL.RETURN_DATE_PLAN,"
						+ " EYBL.BORROW_USER_ID,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EYBL.BORROW_USER_ID) BORROW_USER"
						+ " FROM"
						+ " AMS_MIS_DEPT          AMD,"
						+ " AMS_MIS_EMPLOYEE      AME,"
						+ " EAM_YB_BORROW_LOG     EYBL,"
						+ " ETS_OBJECT            EO,"
						+ " AMS_OBJECT_ADDRESS    AOA,"
						+ " EAM_DH_CATALOG_VALUES EDCV,"
						+ " ETS_SYSTEM_ITEM       ESI,"
						+ " ETS_ITEM_INFO         EII"
						+ " WHERE"
						+ " EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.BARCODE = EYBL.BARCODE"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " AND ESI.ITEM_CATEGORY = ?"
						+ " AND EII.ORGANIZATION_ID = ?"
						+ " AND EYBL.STATUS = ?"
						+ " AND EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " SF_USER_RIGHT SUR,"
						+ " SF_ROLE       SR"
						+ " WHERE"
						+ " EYBL.GROUP_ID = SUR.GROUP_ID"
						+ " AND SUR.ROLE_ID = SR.ROLE_ID"
						+ " AND SR.ROLE_NAME = ?"
						+ " AND SUR.USER_ID = ?)";

		sqlArgs.add(LvecDicts.CATEGORY_YQYB);
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(LvecDicts.YB_BR_STATUS1_WAPPROVE);
		sqlArgs.add(LvecDicts.ROLE_INSTR_APP_NM);
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造仪器仪表申请审批SQL
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getBorrowApproveModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
		String sqlStr = "UPDATE EAM_YB_BORROW_LOG"
						+ " SET"
						+ " STATUS = ?,"
						+ " APPROVE_CONTENT = ?,"
						+ " APPROVE_DATE = GETDATE(),"
						+ " APPROVE_USER_ID = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " BORROW_LOG_ID = ?";

		sqlArgs.add(dto.getStatus());
		sqlArgs.add(dto.getApproveContent());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getBorrowLogId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
