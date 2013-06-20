package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AATConfirmYNModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsFaAssetsDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFaAssetsDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */

public class AATConfirmYNDAO extends AMSBaseDAO {

	private String confirmYN;

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AATConfirmYNDAO(SfUserDTO userAccount,
						   AmsAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsTransLineDTO dtoPara = (AmsAssetsTransLineDTO) dtoParameter;
		super.sqlProducer = new AATConfirmYNModel((SfUserDTO) userAccount, dtoPara, confirmYN);
	}

	/**
	 * 功能：进行资产的确认。
	 * @param confirmAssets DTOSet
	 * @return boolean
	 */
	public boolean confirmAssets(DTOSet confirmAssets) {
		boolean operateResult = false;
		AssetsConfirmDAO confirmDAO = null;
		try {
			confirmDAO = new AssetsConfirmDAO(userAccount, null, conn);
			operateResult = confirmDAO.confirmAssets(confirmAssets);
		} finally {
			if (confirmDAO != null) {
				message = confirmDAO.getMessage();
			}
		}
		return operateResult;
	}

	/**
	 * 功能：根据选择的资产导出数据
	 * @param barcodes String[]
	 * @return String 返回导出Excel文件
	 * @throws DataTransException
	 */
	public File exportCheckedAssets(String[] barcodes) throws
			DataTransException {
		File file = null;
		try {
			AATConfirmYNModel modelProducer = (AATConfirmYNModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getExpCheckedAssetsModel(barcodes);
			file = getExportFile(sqlModel);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws DataTransException
	 */
	public File exportQueryAssets(String confirmYN) throws DataTransException {
		File file = null;
		try {
			AATConfirmYNModel modelProducer = (AATConfirmYNModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel2(confirmYN);
			file = getExportFile(sqlModel);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	/**
	 * 功能：根据SQL导出文件
	 * @param sqlModel SQLModel
	 * @return File
	 * @throws DataTransException
	 */
	private File getExportFile(SQLModel sqlModel) throws DataTransException {
		File file = null;
		String reportTitle = "调拨资产是否确认";
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
		Map fieldMap = getConfirmAssetsMap();
		rule.setFieldMap(fieldMap);
		CustomTransData custData = new CustomTransData();
		custData.setReportTitle(reportTitle);
		custData.setReportPerson(userAccount.getUsername());
		custData.setNeedReportDate(true);
		rule.setCustData(custData);
//		rule.setSheetSize(2000);
		rule.setCalPattern(LINE_PATTERN);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		file = (File) transfer.getTransResult();
		return file;
	}

	/**
	 * 功能：获取待确认资产的导出表头
	 * @return Map
	 */
	private static Map getConfirmAssetsMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("TRANS_NO", "调拨单号");
		fieldMap.put("BARCODE", "资产标签");
		fieldMap.put("ASSET_NUMBER", "资产编号");
		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("MODEL_NUMBER", "资产型号");
		fieldMap.put("COST", "资产原值");
		fieldMap.put("DEPRN_COST", "累计折旧");
		fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
		fieldMap.put("CURRENT_UNITS", "数量");
		fieldMap.put("OLD_LOCATION_NAME", "原地点");
		fieldMap.put("OLD_RESPONSIBILITY_USER_NAME", "原责任人");
		fieldMap.put("OLD_RESPONSIBILITY_DEPT_NAME", "原责任部门");
		fieldMap.put("ASSIGNED_TO_LOCATION_NAME", "调入地点");
		fieldMap.put("RESPONSIBILITY_USER_NAME", "新责任人");
		fieldMap.put("RESPONSIBILITY_DEPT_NAME", "调入部门");
		fieldMap.put("CONFIRM_YN", "确认状态");
		fieldMap.put("REMARK", "摘要");
		return fieldMap;
	}
}
