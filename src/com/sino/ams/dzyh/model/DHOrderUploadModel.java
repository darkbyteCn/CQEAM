package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DHOrderUploadModel extends AMSSQLProducer {

	public DHOrderUploadModel(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造更新当前用户上传工单的SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getOrderUploadModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
			String sqlStr = "UPDATE"
							+ " EAM_DH_CHECK_HEADER EDCH"
							+ " SET"
							+ " EDCH.ORDER_STATUS = ?,"
							+ " EDCH.SCANOVER_DATE = ?,"
							+ " EDCH.SCANOVER_BY = ?,"
							+ " EDCH.UPLOAD_DATE = GETDATE(),"
							+ " EDCH.UPLOAD_BY = ?,"
							+ " EDCH.LAST_UPDATE_DATE = GETDATE(),"
							+ " EDCH.LAST_UPDATE_BY = ?"
							+ " WHERE"
							+ " EDCH.HEADER_ID = ?";
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getScanoverDate());
			sqlArgs.add(dto.getScanoverBy());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getHeaderId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：获取指定单据下的标签号
	 * @param includeAdded boolean 是否包含PDA扫描后，工单上传新加入的设备。
	 * @return SQLModel
	 */
	public SQLModel getOrderBarcodesModel(boolean includeAdded) {
		SQLModel sqlModel = new SQLModel();
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EDCL.BARCODE,"
						+ " EDCL.ITEM_CODE,"
						+ " EDCL.RESPONSIBILITY_USER,"
						+ " EDCL.RESPONSIBILITY_DEPT"
						+ " FROM"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " WHERE"
						+ " EDCL.HEADER_ID = ?";
		if (!includeAdded) {
			sqlStr += " AND " + SyBaseSQLUtil.isNotNull("EDCL.BATCH_ID") + " ";
		}
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
