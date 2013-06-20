package com.sino.sinoflow.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 *
 * <p>Title: SinoCMS</p>
 * <p>Description: 河南移动合同管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChangeUserPasswordModel extends BaseSQLProducer {

	public ChangeUserPasswordModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dtoParameter = dtoParameter;
	}


	/**
	 * 功能：获取用户是否有效的SQL
	 * @return SQLModel
	 */
	public SQLModel getUserAuthenticateModel() {
		SQLModel sqlModel = new SQLModel();
		SfUserBaseDTO dto = (SfUserBaseDTO) dtoParameter;
		List strArg = new ArrayList();
		String strSql = "SELECT"
						+ " 1"
						+ " FROM"
						+ " SF_USER SF"
						+ " WHERE"
						+ " SF.USER_ID = ?"
						+ " AND SF.PASSWORD = ENCODE(?)";
		strArg.add(dto.getUserId());
		strArg.add(dto.getPassword());

		sqlModel.setArgs(strArg);
		sqlModel.setSqlStr(strSql);
		return sqlModel;
	}

	/**
	 * 得到修改用户密码的SQLMODEL
	 * @return SQLModel
	 */
	public SQLModel getChangeUserPasswordModel() {
		SQLModel sqlModel = new SQLModel();
		SfUserBaseDTO dto = (SfUserBaseDTO) dtoParameter;
		List strArg = new ArrayList();
		String strSql = "UPDATE"
						+ " SF_USER SF"
						+ " SET"
						+ " SF.PASSWORD = ENCODE(?)"
						+ " WHERE"
						+ " SF.USER_ID = ?";
		strArg.add(dto.getPassword());
		strArg.add(dto.getUserId());

		sqlModel.setArgs(strArg);
		sqlModel.setSqlStr(strSql);
		return sqlModel;
	}
	
	public SQLModel getChangeUserPasswordModelByLoginName() {
		SQLModel sqlModel = new SQLModel();
		SfUserBaseDTO dto = (SfUserBaseDTO) dtoParameter;
		List strArg = new ArrayList();
		String strSql = "UPDATE"
						+ " SF_USER SF"
						+ " SET"
						+ " SF.PASSWORD = ENCODE(?),"
						+ " SF.OA_PASSWORD = ?"
						+ " WHERE"
						+ " UPPER(SF.LOGIN_NAME) = UPPER(?)";
		strArg.add(dto.getPassword());
		strArg.add(dto.getPassword());
		strArg.add(dto.getLoginName());

		sqlModel.setArgs(strArg);
		sqlModel.setSqlStr(strSql);
		return sqlModel;
	}
	
	
	
}
