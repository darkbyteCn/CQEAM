package com.sino.ams.dzyh.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EamDhChgLogDTO;
import com.sino.ams.dzyh.model.EamDhChgLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhChgLogDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhChgLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EamDhChgLogDAO extends AMSBaseDAO {


	/**
	 * 功能：低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhChgLogDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhChgLogDAO(SfUserDTO userAccount, EamDhChgLogDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamDhChgLogDTO dto = (EamDhChgLogDTO)dtoParameter;
		sqlProducer = new EamDhChgLogModel(userAccount, dto);
	}

	/**
	 * 功能：获取低值易耗设备变动日志历史
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getChgLogHistory() throws QueryException {
		EamDhChgLogModel modelProducer = (EamDhChgLogModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getBarcodeChgHisModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		simp.setDTOClassName(EamDhChgLogDTO.class.getName());
		return simp.getDTOSet();
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		EamDhChgLogModel modelProducer = null;
		try {
			modelProducer = (EamDhChgLogModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "低值易耗设备变动日志历史信息";
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
			Map fieldMap = getFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("ITEM_CATEGORY_VALUE", "设备类别");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "设备型号");
		fieldMap.put("SET_CODE", "目录分类代码");
		fieldMap.put("SET_NAME", "目录分类名称");
		fieldMap.put("CATALOG_CODE", "目录编号代码");
		fieldMap.put("CATALOG_NAME", "目录编号名称");
		fieldMap.put("CHG_TYPE_VALUE", "变更类型");
		fieldMap.put("FROM_DEPT_NAME", "变更前责任部门");
		fieldMap.put("TO_DEPT_NAME", "变更后责任部门");
		fieldMap.put("FROM_LOCATION_CODE", "变更前地点代码");
		fieldMap.put("TO_LOCATION_CODE", "变更后地点代码");
		fieldMap.put("FROM_LOCATION_NAME", "变更前地点名称");
		fieldMap.put("TO_LOCATION_NAME", "变更后地点代码");
		fieldMap.put("FROM_RESPONSIBILITY_USER_NAME", "变更前责任人");
		fieldMap.put("TO_RESPONSIBILITY_USER_NAME", "变更后责任人");
		fieldMap.put("FROM_MAINTAIN_USER", "变更前保管人");
		fieldMap.put("TO_MAINTAIN_USER", "变更后保管人");
		fieldMap.put("CREATION_DATE", "变更时间");
		fieldMap.put("CREATED_USER", "日志创建人");
		fieldMap.put("REF_NO", "变更单据");
		return fieldMap;
	}
}
