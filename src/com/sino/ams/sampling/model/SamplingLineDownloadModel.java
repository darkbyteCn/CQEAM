package com.sino.ams.sampling.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingLineDownloadModel extends AMSSQLProducer {

	public SamplingLineDownloadModel(BaseUserDTO userAccount, AmsAssetsSamplingLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造获取当前用户某一抽查工单下的所有待抽查资产SQL
	 * @return SQLModel
	 */
	public SQLModel getDownloadAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASL.HEADER_ID,"
						+ " AASL.BARCODE,"
						+ " AASL.ITEM_CODE,"
						+ " AASL.START_DATE,"
						+ " AASL.ITEM_CATEGORY,"
						+ " AASL.ITEM_NAME,"
						+ " AASL.ITEM_SPEC,"
						+ " AASL.RESPONSIBILITY_USER,"
						+ " AASL.RESPONSIBILITY_DEPT"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE AASL"
						+ " WHERE"
						+ " AASL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
