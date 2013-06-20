package com.sino.ams.synchronize.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.synchronize.dto.AmsSynTmpDTO;
import com.sino.ams.synchronize.model.AmsSynTmpModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AmsSynTmpDAO extends AMSBaseDAO {
	public AmsSynTmpDAO(BaseUserDTO userAccount, AmsSynTmpDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 * @todo Implement this com.sino.framework.dao.BaseDAO method
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsSynTmpDTO dto = (AmsSynTmpDTO)dtoParameter;
		SfUserDTO user = (SfUserDTO)userAccount;
		sqlProducer = new AmsSynTmpModel(user, dto);
	}

	/**
	 * 功能：删除数据
	 * <B>此处需做同步处理</B>
	 * @throws DataHandleException
	 */
	public synchronized void deleteData() throws DataHandleException {
		super.deleteData();
	}
}
