package com.sino.sinoflow.user.dao;

import java.sql.Connection;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.model.ChangeUserPasswordModel;

/**
 *
 * <p>Title: SinoCMS</p>
 * <p>Description: 河南移动合同管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChangeUserPasswordDAO extends BaseDAO {

	public ChangeUserPasswordDAO(SfUserBaseDTO userAccount, SfUserBaseDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		sqlProducer = new ChangeUserPasswordModel(userAccount, dtoParameter);
	}

	/**
	 * 功能：判断用户是否输入正确的原始密码
	 * @return boolean
	 */
	public boolean authenticateUser() {
		boolean authenticate = false;
		try {
			ChangeUserPasswordModel modelProducer = (ChangeUserPasswordModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getUserAuthenticateModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			authenticate = simp.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return authenticate;
	}

	/**
	 * 功能：修改用户密码
	 * @throws DataHandleException 当发生数据库异常时抛出该异常
	 */
	public void changeUserPassword() throws DataHandleException {
	   ChangeUserPasswordModel modelProducer =(ChangeUserPasswordModel)sqlProducer;
	   SQLModel sqlModel = modelProducer.getChangeUserPasswordModel();
	   DBOperator.updateRecord(sqlModel, conn);
	}
	
	/**
	 * 功能：修改用户密码根据用户名
	 * @throws DataHandleException 当发生数据库异常时抛出该异常
	 */
	public void changeUserPasswordByLoginName() throws DataHandleException {
	   ChangeUserPasswordModel modelProducer =(ChangeUserPasswordModel)sqlProducer;
	   SQLModel sqlModel = modelProducer.getChangeUserPasswordModelByLoginName();
	   DBOperator.updateRecord(sqlModel, conn);
	}
}
