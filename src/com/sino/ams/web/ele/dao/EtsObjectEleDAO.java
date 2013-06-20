package com.sino.ams.web.ele.dao;


import java.sql.Connection;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.ele.dto.EtsObjectEleDTO;
import com.sino.ams.web.ele.model.EtsObjectEleModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsObjectEleDAO</p>
 * <p>Description:程序自动生成服务程序“EtsObjectEleDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsObjectEleDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：基站电费维护表(EAM) ETS_OBJECT_ELE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectEleDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsObjectEleDAO(SfUserDTO userAccount, EtsObjectEleDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsObjectEleDTO dtoPara = (EtsObjectEleDTO)dtoParameter;
		super.sqlProducer = new EtsObjectEleModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入基站电费维护表(EAM)表“ETS_OBJECT_ELE”数据。

	 */
	public  void createData() throws DataHandleException {
 super.createData();
        getMessage().addParameterValue("基站电费维护表(EAM)");

    }

	/**
	 * 功能：更新基站电费维护表(EAM)表“ETS_OBJECT_ELE”数据。

	 */
	public void updateData() throws DataHandleException {
     super.updateData();
        getMessage().addParameterValue("基站电费维护表(EAM)");

    }

	/**
	 * 功能：删除基站电费维护表(EAM)表“ETS_OBJECT_ELE”数据。

	 */
	public void deleteData() throws DataHandleException {
      super.deleteData();
        getMessage().addParameterValue("基站电费维护表(EAM)");

    }

}