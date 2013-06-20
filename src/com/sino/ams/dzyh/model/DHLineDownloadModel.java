package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
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
public class DHLineDownloadModel extends AMSSQLProducer {

	public DHLineDownloadModel(BaseUserDTO userAccount, EamDhCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造获取当前用户某一抽查工单下的所有待抽查资产SQL
	 * @return SQLModel
	 */
	public SQLModel getDownloadAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EDCL.HEADER_ID,"
						+ " EDCL.BARCODE,"
						+ " EDCL.ITEM_CODE,"
						+ " EDCL.ITEM_CATEGORY,"
						+ " EDCL.ITEM_NAME,"
						+ " EDCL.ITEM_SPEC,"
						+ " EDCL.ITEM_CATEGORY2,"
						+ " EDCL.PRICE,"
						+ " EDCL.MAINTAIN_USER,"
						+ " EDCL.VENDOR_ID,"
						+ " EDCL.VENDOR_NAME,"
						+ " EDCL.RESPONSIBILITY_USER,"
						+ " EDCL.RESPONSIBILITY_DEPT,"
						+ " EDCL.START_DATE"
						+ " FROM"
						+ " EAM_DH_CHECK_LINE EDCL"
						+ " WHERE"
						+ " EDCL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
