package com.sino.sinoflow.dao;


import java.sql.Connection;

import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfAdminAuthorityDTO;
import com.sino.sinoflow.model.SfAdminAuthorityModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfAdminAuthorityDAO</p>
 * <p>Description:程序自动生成服务程序“SfAdminAuthorityDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfAdminAuthorityDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：管理员权限信息 SF_ADMIN_AUTHORITY 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfAdminAuthorityDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfAdminAuthorityDAO(SfUserBaseDTO userAccount, SfAdminAuthorityDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfAdminAuthorityDTO dtoPara = (SfAdminAuthorityDTO)dtoParameter;
		super.sqlProducer = new SfAdminAuthorityModel((SfUserBaseDTO)userAccount, dtoPara);
	}
	
	/**
	 * 功能：保存管理员。
	 */
	public String[] spliptStr(String str) {
		String[] arr = null;
		if(str != null){
			arr = str.split(";");
		}
		return arr;
	}	
	
	/**
	 * 功能：批删除应用
	 * @param ids String[]
	 * @throws DataHandleException 
	 */
	public void del(String[] ids) throws DataHandleException {
		DBOperator.updateRecord((( SfAdminAuthorityModel)sqlProducer).del(ids), conn);
	}

    public void add(String loginName) throws DataHandleException {
        DBOperator.updateRecord((( SfAdminAuthorityModel)sqlProducer).add(loginName), conn);
    }
}