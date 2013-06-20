package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsAssetsAddressVModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsAddressVModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsAddressVModel extends AMSSQLProducer {

	/**
	 * 功能：AMS_ASSETS_ADDRESS_V 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 */
	public AmsAssetsAddressVModel(SfUserDTO userAccount,
								  AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：根据外键获取数据
	 * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
	 * @param foreignKey 外键字段名称对应的DTO属性。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) throws
			SQLModelException {
		SQLModel sqlModel = null;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		if (foreignKey.equals("workorderObjectNo")) {
			sqlModel = getDataByWorkobjectNoModel(dto.getWorkorderObjectNo());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据地点编号获取下面的设备
	 * @param workorderObjectNo String
	 * @return SQLModel
	 */
	private SQLModel getDataByWorkobjectNoModel(String workorderObjectNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ "  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + ""
						+ " AND AAAV.WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(workorderObjectNo);
		String mtlMgrProps = userAccount.getMtlMgrProps();
		if (!userAccount.isComAssetsManager()) {
			if (userAccount.isDptAssetsManager()) {
				DTOSet depts = userAccount.getPriviDeptCodes();
				if (depts != null && !depts.isEmpty()) {
					AmsMisDeptDTO dept = null;
					String deptCodes = "'";
					for (int i = 0; i < depts.getSize(); i++) {
						dept = (AmsMisDeptDTO) depts.getDTO(i);
						deptCodes += dept.getDeptCode() + "', '";
					}
					deptCodes += "'";
					sqlStr += " AND AAAV.DEPT_CODE IN (" + deptCodes + ")";
				}
			} else {
				sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
				sqlArgs.add(userAccount.getEmployeeId());
			}
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
