package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderUpLoadModel extends AmsAssetsCheckHeaderModel {

	public ChkOrderUpLoadModel(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}



	/**
	 * 功能：构造更新当前用户上传工单的SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getUploadChkOrdersModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "UPDATE"
							+ " AMS_ASSETS_CHECK_HEADER"
							+ " SET"
							+ " ORDER_STATUS = ?,"
							+ " SCANOVER_DATE = ?,"
							+ " SCANOVER_BY = ?,"
							+ " NEW_LOCATION = ?,"
							+ " UPLOAD_DATE = GETDATE(),"
							+ " UPLOAD_BY = ?"
							+ " WHERE"
							+ " HEADER_ID = ?";
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getScanoverDate());
			sqlArgs.add(dto.getScanoverBy());
			sqlArgs.add(dto.getNewLocation());
			sqlArgs.add(dto.getUploadBy());
			sqlArgs.add(dto.getHeaderId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
