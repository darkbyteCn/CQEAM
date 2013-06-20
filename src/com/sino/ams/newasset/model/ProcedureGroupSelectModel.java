package com.sino.ams.newasset.model;


import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: ProcedureGroupSelectModel</p>
 * <p>Description:程序自动生成SQL构造器“AdminConfirmModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class ProcedureGroupSelectModel extends AMSSQLProducer {

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter DTO 本次操作的数据
	 */
	public ProcedureGroupSelectModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel getProSpecialGroupModel(){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SG.GROUP_NAME\n" +
                "FROM   SF_GROUP SG\n" +
                "WHERE  SG.SPECIALITY_DEPT = 'Y'\n" +
                "       AND SG.ENABLED = 'Y'\n" +
                "       AND SG.ORGANIZATION_ID = " + servletConfig.getProvinceOrgId();
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
