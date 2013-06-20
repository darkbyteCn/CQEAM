package com.sino.ams.sampling.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.ams.sampling.model.AmsAssetsSamplingLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsSamplingLineDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsSamplingLineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingLineDAO extends AMSBaseDAO {

	/**
	 * 功能：抽查工单行表 AMS_ASSETS_SAMPLING_LINE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingLineDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsSamplingLineDAO(SfUserDTO userAccount, AmsAssetsSamplingLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO)dtoParameter;
		super.sqlProducer = new AmsAssetsSamplingLineModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：上载设备。
	 * @param isExist boolean
	 * @throws DataHandleException
	 */
//	public void uploadLine(boolean isExist) throws DataHandleException {
//		try {
//			AmsAssetsSamplingLineModel modelProducer = (AmsAssetsSamplingLineModel) sqlProducer;
//			SQLModel sqlModel = null;
//			if (!isExist) {
//				sqlModel = modelProducer.getUploadCreateModel();
//			} else {
//				sqlModel = modelProducer.getUploadUpdateModel();
//			}
//			DBOperator.updateRecord(sqlModel, conn);
//		} catch (SQLModelException ex) {
//			ex.printLog();
//			throw new DataHandleException(ex);
//		}
//	}
}
