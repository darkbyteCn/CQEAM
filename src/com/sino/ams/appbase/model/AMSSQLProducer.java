package com.sino.ams.appbase.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class AMSSQLProducer extends BaseSQLProducer {//该类的存在是为了免去其他SQLProducer类处处定义用户对象
	protected SfUserDTO userAccount = null;

	public AMSSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.userAccount = (SfUserDTO)userAccount;
	}
}
