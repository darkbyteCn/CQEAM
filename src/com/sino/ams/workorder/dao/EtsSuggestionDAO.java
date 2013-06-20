package com.sino.ams.workorder.dao;


import java.sql.Connection;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsSuggestionDTO;
import com.sino.ams.workorder.model.EtsSuggestionModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsSuggestionDAO</p>
 * <p>Description:程序自动生成服务程序“EtsSuggestionDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsSuggestionDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：工单流程意见处理(EAM) ETS_SUGGESTION 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSuggestionDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsSuggestionDAO(SfUserDTO userAccount, EtsSuggestionDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsSuggestionDTO dtoPara = (EtsSuggestionDTO)dtoParameter;
		super.sqlProducer = new EtsSuggestionModel((SfUserDTO)userAccount, dtoPara);
	}

}