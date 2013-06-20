package com.sino.ams.system.house.dao;


import java.sql.Connection;

import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.model.AmsItemFilesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsItemFilesDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemFilesDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsItemFilesDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：设备相关附件(EAM) AMS_ITEM_FILES 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemFilesDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemFilesDAO(SfUserDTO userAccount, AmsItemFilesDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsItemFilesDTO dtoPara = (AmsItemFilesDTO)dtoParameter;
		super.sqlProducer = new AmsItemFilesModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入设备相关附件(EAM)表“AMS_ITEM_FILES”数据。
	 * @return boolean
	 */
	public void createData()throws DataHandleException {
		 super.createData();
		getMessage().addParameterValue("租赁相关附件");
//		return operateResult;
	}

	/**
	 * 功能：更新设备相关附件(EAM)表“AMS_ITEM_FILES”数据。
	 * @return boolean
	 */
	public void updateData()throws DataHandleException{
		 super.updateData();
		getMessage().addParameterValue("租赁相关附件");
//		return operateResult;
	}

	/**
	 * 功能：删除设备相关附件(EAM)表“AMS_ITEM_FILES”数据。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
//		boolean operateResult = super.deleteData();
         super.deleteData();
        getMessage().addParameterValue("租赁相关附件");
//		return operateResult;
	}

}