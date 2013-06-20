package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderHeaderConfirmModel extends AMSSQLProducer {

	public OrderHeaderConfirmModel(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造获取仪表Web确认工单的归档SQL
	 * @return SQLModel
	 */
	public SQLModel getOrderArchiveModel(){
		SQLModel sqlModel = new SQLModel();
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " EAM_DH_CHECK_HEADER EDCH"
						+ " SET"
						+ " EDCH.ORDER_STATUS     = ?,"
						+ " EDCH.ARCHIVED_DATE    = GETDATE(),"
						+ " EDCH.ARCHIVED_BY      = ?,"
						+ " EDCH.LAST_UPDATE_DATE = GETDATE(),"
						+ " EDCH.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EDCH.HEADER_ID = ?"
						+ " AND EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM ("
						+ " SELECT"
						+ " EDCL.HEADER_ID,"
						+ " COUNT(1) LEFT_COUNT"
						+ " FROM"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " WHERE"
						+ " (EDCL.CONFIRM_DATE IS NULL OR EDCL.CONFIRM_DATE = '')"
						+ " AND EDCL.HEADER_ID = ?"
						+ " GROUP  BY"
						+ " EDCL.HEADER_ID) TMP_V"
						+ " WHERE"
						+ " EDCH.HEADER_ID = TMP_V.HEADER_ID"
						+ " AND TMP_V.LEFT_COUNT = 0)";
		sqlArgs.add(LvecDicts.ORDER_STS1_ARCHIEVED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
