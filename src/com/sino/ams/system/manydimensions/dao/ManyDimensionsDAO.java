package com.sino.ams.system.manydimensions.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.model.ItemMaintainModel;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.manydimensions.model.ManyDimensionsModel;
import com.sino.base.util.CalendarUtil;

/**
 * <p>Description:  陕西移动资产管理系统</p>
 * <p>Company:      北京思诺博信息技术有限公司</p>
 * @author          李轶
 * @date            2009-08-04
 */
public class ManyDimensionsDAO extends AMSBaseDAO {
	private AmsItemCorrectLogDAO logDAO = null;
	private SimpleQuery simp = null;

	public ManyDimensionsDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter,Connection conn) {
		super(userAccount, dtoParameter, conn);
		logDAO = new AmsItemCorrectLogDAO(userAccount, null, conn);
	}

    /**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
		sqlProducer = new ManyDimensionsModel((SfUserDTO)userAccount, dto);
	}

	public boolean updateItems(String[] barcodeNos) {
		boolean operateResult = false;
		boolean autoCommit = true;
		String barcodes = "";
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			for (int i = 0; i < barcodeNos.length; i++) {
				barcodes += barcodeNos[i] + ",";
				dto.setBarcode(barcodeNos[i]);
				setDTOParameter(dto);
				updateData();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					prodMessage(AssetsMessageKeys.ITEM_UPDATE_SUCCESS);
					conn.commit();
				} else {
					prodMessage(AssetsMessageKeys.ITEM_UPDATE_FAILURE);
					conn.rollback();
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}

	public File getExportFile() throws DataTransException {
		ManyDimensionsModel modelProducer = (ManyDimensionsModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getPageQueryModel();
		String reportTitle = "多维度信息维护";
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
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "设备型号");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
		fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人姓名");
		fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
        fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
        fieldMap.put("INVEST_CAT_NAME", "投资分类");
        fieldMap.put("OPE_NAME", "业务平台");
        fieldMap.put("LNE_NAME", "网络层次");
        return fieldMap;
	}
}