package com.sino.ams.system.trust.dao;


import java.sql.Connection;

import com.sino.ams.system.trust.dto.AmsMaintainFilesDTO;
import com.sino.ams.system.trust.model.AmsMaintainFilesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsMaintainFilesDAO</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainFilesDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainFilesDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：代维公司相关文件 AMS_MAINTAIN_FILES 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsMaintainFilesDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsMaintainFilesDAO(SfUserDTO userAccount, AmsMaintainFilesDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsMaintainFilesDTO dtoPara = (AmsMaintainFilesDTO)dtoParameter;
		super.sqlProducer = new AmsMaintainFilesModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入代维公司相关文件表“AMS_MAINTAIN_FILES”数据。

	 */
	public void createData() throws DataHandleException {
      super.createData();
        getMessage().addParameterValue("代维公司相关文件");

    }

	/**
	 * 功能：更新代维公司相关文件表“AMS_MAINTAIN_FILES”数据。

	 */
	public void updateData()throws DataHandleException{
  super.updateData();
		getMessage().addParameterValue("代维公司相关文件");

	}

	/**
	 * 功能：删除代维公司相关文件表“AMS_MAINTAIN_FILES”数据。

	 */
	public void deleteData()throws DataHandleException{
	  super.deleteData();
		getMessage().addParameterValue("代维公司相关文件");

	}

}