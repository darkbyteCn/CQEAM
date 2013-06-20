package com.sino.ams.system.user.dao;


import java.sql.Connection;

import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfGroupModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: SfGroupDAO</p>
 * <p>Description:程序自动生成服务程序“SfGroupDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfGroupDAO extends BaseDAO {

	/**
	 * 功能：SF_GROUP 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfGroupDAO(SfUserDTO userAccount, SfGroupDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}
	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		SfGroupDTO dtoPara = (SfGroupDTO)dtoParameter;
		super.sqlProducer = new SfGroupModel((SfUserDTO)userAccount, dtoPara);
	}

    /**
	 * 功能：将三级部门所选择的父组别的GROUP_THRED字段设置成自己的GROUP_ID
	 * @return boolean
	 */
	public boolean updateThirdGroup(){
	    boolean exist = false;
        try {
            SfGroupModel model = (SfGroupModel)sqlProducer;
            SQLModel sqlModel = model.getUpdateThirdGroupModel();
            DBOperator.updateRecord(sqlModel, conn);
            exist = true;
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
		return exist;
	}
}
