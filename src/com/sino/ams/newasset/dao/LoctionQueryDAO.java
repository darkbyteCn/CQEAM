package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.AmsAssetsAddressVModel;
import com.sino.ams.newasset.model.LocationQueryModel;
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
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsCheckBatchDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckBatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class LoctionQueryDAO extends AMSBaseDAO {

	/**
	 * 功能：资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public LoctionQueryDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		sqlProducer = new LocationQueryModel((SfUserDTO) userAccount,dto);
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			AmsAssetsAddressVModel modelProducer = new AmsAssetsAddressVModel(userAccount, dto);
			SQLModel sqlModel = modelProducer.getDataByForeignKeyModel("workorderObjectNo");
			String locationName = dto.getWorkorderObjectName();
			String reportTitle = "地点" + locationName + "下的资产";//标题名称太长，可能超过31个字符.
			reportTitle = "地点导出资产";
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
			throw new DataTransException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("BARCODE", "资产标签");
		fieldMap.put("ASSET_NUMBER", "资产编号");
		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("MODEL_NUMBER", "资产型号");
		fieldMap.put("COST", "资产原值");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("DEPRN_COST", "资产净值");
		fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
		fieldMap.put("DEPT_NAME", "责任部门");
		fieldMap.put("MAINTAIN_USER_NAME", "使用人");
		return fieldMap;
	}
}
