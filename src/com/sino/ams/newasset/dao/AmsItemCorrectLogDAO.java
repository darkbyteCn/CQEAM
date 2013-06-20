package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.AmsItemCorrectLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsItemCorrectLogDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemCorrectLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsItemCorrectLogDAO extends AMSBaseDAO {

	/**
	 * 功能：资产台账维护日志 AMS_ITEM_CORRECT_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemCorrectLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemCorrectLogDAO(SfUserDTO userAccount, AmsItemCorrectLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsItemCorrectLogDTO dto = (AmsItemCorrectLogDTO)dtoParameter;
		super.sqlProducer = new AmsItemCorrectLogModel((SfUserDTO)userAccount, dto);
	}

	public File getExportFile() throws DataTransException {
		AmsItemCorrectLogDTO dto = (AmsItemCorrectLogDTO)dtoParameter;
		AmsItemCorrectLogModel modelProducer = (AmsItemCorrectLogModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getDataByForeignKeyModel("barcode");
        String reportTitle = dto.getBarcode() + "台账维护历史记录";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(userAccount.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("CREATED_USER", "修改人");
		fieldMap.put("CREATION_DATE", "修改时间");
		fieldMap.put("CORRECT_CONTENT", "修改内容");
		return fieldMap;
	}
}
