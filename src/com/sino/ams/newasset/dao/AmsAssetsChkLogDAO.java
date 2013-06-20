package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsChkLogDTO;
import com.sino.ams.newasset.model.AmsAssetsChkLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsChkLogDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsChkLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsChkLogDAO extends AMSBaseDAO {

	/**
	 * 功能：资产盘点记录 AMS_ASSETS_CHK_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsChkLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsChkLogDAO(SfUserDTO userAccount,
							  AmsAssetsChkLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsChkLogDTO dtoPara = (AmsAssetsChkLogDTO) dtoParameter;
		super.sqlProducer = new AmsAssetsChkLogModel((SfUserDTO) userAccount,
				dtoPara);
	}

	/**
	 * 功能：保存最新的资产盘点信息
	 * @throws DataHandleException
	 */
	public void saveCheckLogData() throws DataHandleException {
		try {
			AmsAssetsChkLogModel modelProducer = (AmsAssetsChkLogModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getHasDataModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				updateData();
			} else {
				createData();
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}
}
