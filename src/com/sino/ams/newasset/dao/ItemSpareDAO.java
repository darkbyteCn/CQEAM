package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.model.ItemSpareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA. User: T_suhuipeng Date: 2011-6-10 Time: 22:42:38 To
 * change this template use File | Settings | File Templates.
 */
public class ItemSpareDAO extends AMSBaseDAO {
	private AmsItemCorrectLogDAO logDAO = null;
	private AmsItemInfoHistoryDAO hisLogDAO = null;
	private SimpleQuery simp = null;

	public ItemSpareDAO(SfUserDTO userAccount,
			AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		logDAO = new AmsItemCorrectLogDAO(userAccount, null, conn);
		hisLogDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		sqlProducer = new ItemSpareModel((SfUserDTO) userAccount, dto);
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
				// barcodeNos[i]-->address_id, responsibilityUser,
				// responsibilityDetpt(系统)
				setDTOParameter(dto);
				logDAO.setDTOParameter(getLogDTO());
				updateData();
				
				logDAO.createData();
				hisLogDAO.setDTOParameter(getHisLogDTO());
				hisLogDAO.recordHistory();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
		} finally {
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

	/**
	 * 功能：构造台帐维护日志
	 * 
	 * @return AmsItemCorrectLogDTO
	 * @throws QueryException
	 */
	private AmsItemCorrectLogDTO getLogDTO() throws QueryException {
		AmsItemCorrectLogDTO logDTO = null;
		try {
			AmsAssetsAddressVDTO newDTO = (AmsAssetsAddressVDTO) dtoParameter;
			ItemSpareModel modelProducer = (ItemSpareModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPrimaryKeyDataModel();
			String logId = new SeqProducer(conn).getGUID();
			if (simp == null) {
				simp = new SimpleQuery(sqlModel, conn);
				simp.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				simp.setCalPattern(LINE_PATTERN);
			} else {
				simp.setSql(sqlModel);
			}
			simp.executeQuery();
			if (simp.hasResult()) {
				AmsAssetsAddressVDTO oldDTO = (AmsAssetsAddressVDTO) simp.getFirstDTO();
				
				logDTO = new AmsItemCorrectLogDTO();
				logDTO.setBarcode(newDTO.getBarcode());
				logDTO.setLogId(logId);
				StringBuffer correctContent = new StringBuffer();
				String[] fieldNames = { "ITEM_CODE", "ITEM_CATEGORY",
						"ITEM_CATEGORY_NAME", "ITEM_NAME", "ITEM_SPEC",
						"ADDRESS_ID", "WORKORDER_OBJECT_CODE",
						"WORKORDER_OBJECT_NAME", "RESPONSIBILITY_USER",
						"EMPLOYEE_NUMBER", "RESPONSIBILITY_USER_NAME",
						"RESPONSIBILITY_DEPT", "RESPONSIBILITY_DEPT_NAME",
						"MAINTAIN_USER", "MAINTAIN_DEPT_NAME", "START_DATE",
						"SPECIALITY_DEPT" };
				String[] fieldDescs = { "设备分类代码", "设备专业代码", "设备专业名称", "设备名称",
						"设备型号", "地点ID", "地点代码", "地点名称", "责任人员工ID", "责任人员工编号",
						"责任人姓名", "责任部门代码", "责任部门名称", "使用人", "使用部门", "启用日期",
						" 实物部门" };
				String fieldName = "";
				String javaField = "";
				String oldValue = "";
				String newValue = "";
				for (int i = 0; i < fieldNames.length; i++) {
					fieldName = fieldNames[i];
					javaField = StrUtil.getJavaField(fieldName);
					oldValue = String.valueOf(ReflectionUtil.getProperty(
							oldDTO, javaField));
					if (oldValue.equals("")) {
						oldValue = "无";
					}
					newValue = String.valueOf(ReflectionUtil.getProperty(
							newDTO, javaField));
					if (!oldValue.equals(newValue) && !newValue.equals("")) {
						correctContent.append(fieldDescs[i]);
						correctContent.append(":");
						correctContent.append(oldValue);
						correctContent.append("-->>");
						correctContent.append(newValue);
						correctContent.append(WorldConstant.ENTER_CHAR);
					}
				}
				logDTO.setCorrectContent(correctContent.toString());
			}else{
				logDTO = new AmsItemCorrectLogDTO();
				logDTO.setLogId(logId);
				logDTO.setBarcode(newDTO.getBarcode());
				//logDTO.setCorrectContent("");
				logDTO.setCreatedBy(userAccount.getUserId());
			}
		} catch (ReflectException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return logDTO;
	}

	/**
	 * 功能：构造台帐维护的变动历史
	 * 
	 * @return AmsItemCorrectLogDTO
	 * @throws QueryException
	 */
	private AmsItemInfoHistoryDTO getHisLogDTO() throws QueryException {
		AmsItemInfoHistoryDTO logDTO = new AmsItemInfoHistoryDTO();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String currTime = CalendarUtil.getCurrCalendar(CAL_PATT_45);
		String remark = userAccount.getUsername() + "在" + currTime
				+ "通过实物台帐维护更改";
		logDTO.setRemark(remark);
		logDTO.setBarcode(dto.getBarcode());
		logDTO.setAddressId(dto.getAddressId());
		logDTO.setItemCode(dto.getItemCode());
		logDTO.setResponsibilityDept(dto.getResponsibilityDept());
		logDTO.setResponsibilityUser(dto.getResponsibilityUser());
		logDTO.setOrderNo("实物台账维护更改");
		logDTO.setOrderCategory("0");
		return logDTO;
	}

	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			ItemSpareModel modelProducer = (ItemSpareModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "备品备件台帐导出设备";
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
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	private Map getFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司");
		fieldMap.put("BARCODE", "设备条码");
		fieldMap.put("ITEM_NAME", "设备名称");
		fieldMap.put("ITEM_SPEC", "规格型号");
		fieldMap.put("ITEM_STATUS_NAME", "设备状态");
		fieldMap.put("IS_ABATE", "是否失效");
		fieldMap.put("ITEM_UNIT", "计量单位");
		fieldMap.put("START_DATE", "入库日期");
		fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
		fieldMap.put("MANUFACTURER_CODE", "厂商代码");
		fieldMap.put("MANUFACTURER_NAME", "厂商");
		fieldMap.put("MAINTAIN_USER", "使用人");
		fieldMap.put("SPECIALITY_DEPT", "实物部门");
		fieldMap.put("USERNAME", "专业责任人");
		fieldMap.put("CONTENT_CODE", "目录代码");
		fieldMap.put("CONTENT_NAME", "目录名称");
		fieldMap.put("REMARK", "备注");
		fieldMap.put("ITEM_CATEGORY", "资产类别");
		fieldMap.put("ITEM_CODE", "资产代码");
		fieldMap.put("ITEM_QTY", "数量");
		fieldMap.put("PRICE", "单价");
		fieldMap.put("IS_TD", "是否TD");
		return fieldMap;
	}

	public int checkItemStatus(String[] barcodes, String itemStatus)
			throws QueryException, SQLModelException {
		int count = 0;
		ItemSpareModel modelProducer = (ItemSpareModel) sqlProducer;
		for (int i = 0; i < barcodes.length; i++) {
			String barcode = barcodes[i];
			SQLModel sqlModel = modelProducer.getCheckItemStatusModel(barcode,
					itemStatus);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				count++;
			}
		}
		return count;
	}

    public void logItemChgHistory(String [] barcodes){
        AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount,null,conn);
        String orderURL = "/servlet/com.sino.ams.newasset.servlet.ItemSpareServlet"; //需要苏慧鹏确认此处是否有单据操作，有的话就写入单据的详细信息地址，否则应当为空

        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];

            AmsItemInfoHistoryDTO historyDTO=new AmsItemInfoHistoryDTO();
            historyDTO.setOrderCategory("3");
            historyDTO.setOrderNo("");
            historyDTO.setCreatedBy(userAccount.getUserId());
//            historyDTO.setOrderDtlUrl(orderURL);
            historyDTO.setBarcode(barcode);
            historyDAO.setDTOParameter(historyDTO);
            historyDAO.recordHistory();
        }
    }}