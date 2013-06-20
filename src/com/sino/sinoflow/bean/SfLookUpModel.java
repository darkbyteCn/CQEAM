package com.sino.sinoflow.bean;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.constant.LookUpConstant;
import com.sino.sinoflow.dto.SinoMisDeptDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Title: SinoAMS
 * </p>
 * <p>
 * Description: 山西移动实物资产管理系统
 * </p>
 * <p>
 * Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 1.0
 */
public class SfLookUpModel extends LookUpModel {

	private SfUserBaseDTO user = null;

	public SfLookUpModel(BaseUserDTO userAccount, DTO dtoParameter,
			LookUpProp lookProp) {
		super(userAccount, dtoParameter, lookProp);
		this.user = (SfUserBaseDTO) userAccount;
	}

	/**
	 * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		List<Object> sqlArgs = new ArrayList<Object>();
		String sqlStr = "";
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals(LookUpConstant.LOOK_UP_PARENT_DEPT)) { // 查询SF_USER用户
            SinoMisDeptDTO dto = (SinoMisDeptDTO) dtoParameter;
            sqlStr = "SELECT DEPT_ID, DEPT_NAME, DEPT_CODE"
                    + " FROM SINO_MIS_DEPT SMD"
                    + " WHERE"
                    + " (? = '' OR DEPT_NAME LIKE ?)"
                    + " AND (? = '' OR DEPT_CODE LIKE ?)"
                    + " AND ORG_ID = ?";
            sqlArgs.add(dto.getDeptName());
            sqlArgs.add("%" + dto.getDeptName() + "%");
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add("%" + dto.getDeptCode() + "%");
            sqlArgs.add(dto.getOrgId());
        }
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
}
