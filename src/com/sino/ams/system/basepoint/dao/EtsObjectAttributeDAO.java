package com.sino.ams.system.basepoint.dao;


import java.sql.Connection;

import com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO;
import com.sino.ams.system.basepoint.model.EtsObjectAttributeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsObjectAttributeDAO</p>
 * <p>Description:程序自动生成服务程序“EtsObjectAttributeDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectAttributeDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectAttributeDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsObjectAttributeDAO(SfUserDTO userAccount, EtsObjectAttributeDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsObjectAttributeDTO dtoPara = (EtsObjectAttributeDTO)dtoParameter;
		super.sqlProducer = new EtsObjectAttributeModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入资产地点属性扩展弹性域表“ETS_OBJECT_ATTRIBUTE”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException {
//		boolean operateResult = super.createData();
		 super.createData();
		getMessage().addParameterValue("资产地点属性扩展弹性域");
//		return operateResult;
	}

	/**
	 * 功能：更新资产地点属性扩展弹性域表“ETS_OBJECT_ATTRIBUTE”数据。
	 * @return boolean
	 */
	public void updateData()throws DataHandleException{
//		boolean operateResult = super.updateData();
		 super.updateData();
		getMessage().addParameterValue("资产地点属性扩展弹性域");
//		return operateResult;
	}

	/**
	 * 功能：删除资产地点属性扩展弹性域表“ETS_OBJECT_ATTRIBUTE”数据。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
//		boolean operateResult = super.deleteData();
		super.deleteData();
		getMessage().addParameterValue("资产地点属性扩展弹性域");
//		return operateResult;
	}

}