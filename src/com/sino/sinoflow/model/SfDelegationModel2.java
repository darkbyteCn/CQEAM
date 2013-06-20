package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfDelegationDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfDelegationModel</p>
 * <p>Description:程序自动生成SQL构造器“SfDelegationModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfDelegationModel2 extends BaseSQLProducer {

	/**
	 * 功能：委派定义 SF_DELEGATION 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfDelegationDTO 本次操作的数据
	 */
	public SfDelegationModel2(SfUserBaseDTO userAccount, SfDelegationDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成委派定义 SF_DELEGATION页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	
			List sqlArgs = new ArrayList();
			SfDelegationDTO sfDelegation = (SfDelegationDTO)dtoParameter;

            String sqlStr = "SELECT "
				+ " CONVERT(INT,D.DELEGATION_ID) DELEGATION_ID,"
				+ " D.USER_ID,"
				+ " D.DELEGATE_TO,"
				+ " D.STATUS_CTL,"
				+ " D.START_DATE,"
				+ " D.END_DATE,"
				+ " US.USERNAME S_NAME,"
				+ " UE.USERNAME E_NAME"
				+ " FROM"
				+ " SF_DELEGATION D,"
				+ " SF_USER US,"
				+ " SF_USER UE"
				+ " WHERE"
				+ " (? <= 0 OR D.DELEGATION_ID = ?)"
				+ " AND (? <= 0 OR D.USER_ID = ?)"
				+ " AND (? <= 0 OR D.DELEGATE_TO = ?)"
				+ " AND (? <= 0 OR D.STATUS_CTL = ?)"
                + " AND D.USER_ID = ?"
                + " AND D.USER_ID = US.USER_ID"
				+ " AND D.DELEGATE_TO = UE.USER_ID";

            sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getDelegationId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getUserId());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getDelegateTo());
			sqlArgs.add(sfDelegation.getStatusCtl());
			sqlArgs.add(sfDelegation.getStatusCtl());
            sqlArgs.add(((SfUserBaseDTO)this.userAccount).getUserId());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
}