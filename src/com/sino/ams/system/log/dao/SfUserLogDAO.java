package com.sino.ams.system.log.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.log.dto.SfUserLogDTO;
import com.sino.ams.system.log.model.SfUserLogModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfUserLogDAO</p>
 * <p>Description:程序自动生成服务程序“SfUserLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfUserLogDAO extends AMSBaseDAO {

	/**
	 * 功能：用户URL访问日志表(EAM) SF_USER_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfUserLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfUserLogDAO(SfUserBaseDTO userAccount, SfUserLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		SfUserLogDTO dtoPara = (SfUserLogDTO)dtoParameter;
		sqlProducer = new SfUserLogModel((SfUserBaseDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：根据SQL导出文件
	 * @return File
	 * @throws DataTransException
	 */
	public File getExportFile(String excelType) throws DataTransException {
		File file = null;
		try {
			SfUserLogModel modelProducer = (SfUserLogModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "用户操作日志";
			if (!StrUtil.isNotEmpty(excelType)) {
				excelType = "xls";
			}
			String fileName = reportTitle + "." + excelType;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			rule.setPageSize(2000);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
//			rule.setCalPattern("YYYY年MM月DD日  HH24时MI分SS秒 ");
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	/**
	 * 功能：获取待确认资产的导出表头
	 * @return Map
	 */
	private static Map getFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("USER_ID", "用户ID");
		fieldMap.put("USERNAME", "用户名称");
		fieldMap.put("USER_ACCOUNT", "用户账号");
		fieldMap.put("CLIENT_IP", "客户端IP");
		fieldMap.put("RES_NAME", "请求资源名称");
		fieldMap.put("SERVER", "应用服务器");
		fieldMap.put("REQ_URL", "请求URL");
		fieldMap.put("ACTION_TYPE", "操作类型");
		fieldMap.put("LOG_TIME", "请求时间");
		return fieldMap;
	}
}
