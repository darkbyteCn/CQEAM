package com.sino.ams.newasset.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.bean.TransferRoadValidator;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.ArchiveHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class ArchiveHeaderDAO extends AMSBaseDAO {
	public ArchiveHeaderDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount  BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dtoPara = (AmsAssetsCheckHeaderDTO) dtoParameter;
		sqlProducer = new ArchiveHeaderModel((SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：归档盘点工单
	 * @param chkLines DTOSet
	 * @return boolean
	 */
	public boolean archiveChkOrder(DTOSet chkLines) {
		boolean operateResult = false;
		boolean hasPreviousOrder = false;
		boolean autoCommit = true;
		AmsAssetsCheckHeaderDTO headerDTO = (AmsAssetsCheckHeaderDTO) dtoParameter;
		try {
			AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(userAccount, headerDTO, conn);
			orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			AmsAssetsCheckHeaderDTO srcDTO = (AmsAssetsCheckHeaderDTO) orderDAO.getDataByPrimaryKey();
			TransferRoadValidator validator = new TransferRoadValidator();
			if (!validator.canExecuteAction(srcDTO, headerDTO)) {
				prodMessage(AssetsMessageKeys.INVALID_OPERATE);
				message.addParameterValue(srcDTO.getStatusName());
				message.addParameterValue(headerDTO.getStatusName());
				message.setIsError(true);
//			} else if (hasPreviousOrder()) {
//				hasPreviousOrder = true;
			} else {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
				SQLModel sqlModel = modelProducer.getChkOrderArchiveModel(); //工单状态变更为归档
				DBOperator.updateRecord(sqlModel, conn);
//				sqlModel = modelProducer.getArcNotScanBarcodesModel();//暂不考虑该情况
//				DBOperator.updateRecord(sqlModel, conn);
				if (chkLines != null && !chkLines.isEmpty()) {
					ArchiveLineDAO chkLineDAO = new ArchiveLineDAO(userAccount, null, conn);
					chkLineDAO.setServletConfig(servletConfig);
					chkLineDAO.setOrderItems(orderDAO.getOrderBarcodes(false)); //设置待盘点设备
					chkLineDAO.setLocItems(getLocBarcodes()); //设置本盘点地点下现有的设备
					chkLineDAO.setOrderHeader(headerDTO);
					AmsAssetsCheckLineDTO chkLineDTO = null;
					for (int i = 0; i < chkLines.getSize(); i++) {
						chkLineDTO = (AmsAssetsCheckLineDTO) chkLines.getDTO(i);
						chkLineDTO.setHeaderId(headerDTO.getHeaderId());
						chkLineDAO.setDTOParameter(chkLineDTO);
						chkLineDAO.archiveChkLine();
					}
				}
				operateResult = true;
			}
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
		} finally {
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(AssetsMessageKeys.ARCH_ORDER_SUCCESS);
				} else {
					conn.rollback();
					if (hasPreviousOrder) {
						prodMessage(AssetsMessageKeys.HAS_PREVIOUS_ORDER);
						message.addParameterValue(headerDTO.getObjectLocation());
					} else if (message == null ||
							StrUtil.isEmpty(message.getMessageValue())) {
						prodMessage(AssetsMessageKeys.ARCH_ORDER_FAILURE);
					}
				}
				conn.setAutoCommit(autoCommit);
				message.setIsError(!operateResult);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}
	
	/**
	 * 退回盘点工单
	 * @throws DataHandleException
	 */
	public void getBackOrder() throws DataHandleException {
		ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getUpdateBackOrderModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：判断待归档工单所在地点是否有早期未归档工单
	 * @return boolean
	 * @throws QueryException
	 */
	private boolean hasPreviousOrder() throws QueryException {
		ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getHasPreviousOrderModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}

	/**
	 * 功能：将系统中存在但PDA提交的设备中不存在的条码全部归档到在途库(暂不考虑该情况)
	 * @throws DataHandleException
	 */
	private void arcNotScanedBarcodes() throws DataHandleException {
		ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getArcNotScanBarcodesModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：导出工单头信息及行信息到Excel文件
	 * @return File
	 * @throws DataTransException
	 */
	public File exportArchiveData() throws DataTransException {
		File file = null;
		try {
			setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			setCalPattern(LINE_PATTERN);
			AmsAssetsCheckHeaderDTO orderHeader = (AmsAssetsCheckHeaderDTO)getDataByPrimaryKey();
			AmsAssetsCheckLineDTO checkLine = new AmsAssetsCheckLineDTO();
			checkLine.setHeaderId(orderHeader.getHeaderId());
			AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(userAccount, checkLine, conn);
			lineDAO.setCalPattern(LINE_PATTERN);
			lineDAO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
			DTOSet chkLines = (DTOSet) lineDAO.getDataByForeignKey("headerId");
			HSSFWorkbook wb = exportOrderHeader(orderHeader);
			wb = exportLineTitle(wb);
			wb = exportLineData(wb, chkLines);
			String fileName = "工单" + orderHeader.getTransNo() + "盘点信息.xls";
			String filePath = com.sino.config.SinoConfig.getExportHOME();//WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			file = new File(filePath);
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException ex) {
			Logger.logError(ex);
			throw new DataTransException(ex);
		} catch (IOException ex) {
			Logger.logError(ex);
			throw new DataTransException(ex);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		} catch (ReflectException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	/**
	 * 功能：导出盘点工单头数据
	 * @param orderHeader AmsAssetsCheckHeaderDTO
	 * @return HSSFWorkbook
	 * @throws CalendarException
	 */
	private HSSFWorkbook exportOrderHeader(AmsAssetsCheckHeaderDTO orderHeader) throws
			CalendarException {
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "工单" + orderHeader.getTransNo() + "盘点信息";
		String dataTitle = "工单" + orderHeader.getTransNo() + "系统数据与盘点数据对比信息";
		HSSFSheet sheet = wb.createSheet(sheetName);

		HSSFCellStyle csField = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Courier New");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		csField.setFont(font);
		csField.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		csField.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		csField.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csField.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csField.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csField.setBorderRight(HSSFCellStyle.BORDER_THIN);

		int rowIndex = 0;
		HSSFRow xlsRow = null;
		HSSFRichTextString cellValue = null;
		HSSFCell xlsCell = null;
		for (; rowIndex < 3; rowIndex++) {
			xlsRow = sheet.createRow(rowIndex);
			for (short j = 0; j < 13; j++) {
				xlsCell = xlsRow.createCell(j);
				xlsCell.setCellStyle(csField);
			}
		}
		Region region = new Region(0, (short) 0, 2, (short) 12);
		sheet.addMergedRegion(region); //指定合并区域
		xlsRow = sheet.getRow(0);
		xlsCell = xlsRow.getCell((short) 0);
		xlsCell.setCellValue(new HSSFRichTextString(dataTitle));
		xlsRow = sheet.createRow(rowIndex++);
		HSSFCellStyle csValue = wb.createCellStyle();
		csValue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		csValue.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		csValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderRight(HSSFCellStyle.BORDER_THIN);

		short cellIndex = 0;

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("盘点单号：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getTransNo());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("任务描述：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getTaskDesc());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("盘点部门：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getCheckDeptName());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("盘点地点：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getObjectLocation());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("开始日期：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getStartTime().
				getCalendarValue());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("执行周期(天)：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(String.valueOf(orderHeader.getImplementDays()));
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell((short) 12);
		xlsCell.setCellStyle(csField);
		cellValue = new HSSFRichTextString("----");
		xlsCell.setCellValue(cellValue);

		xlsRow = sheet.createRow(rowIndex++);
		cellIndex = 0;

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("执行人：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getImplementUser());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("单据状态：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getStatusName());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("创建时间：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getCreationDate().
				getCalendarValue());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("创建人：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getCreatedUser());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("下发时间：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getDistributeDate().
				getCalendarValue());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("下发人：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getDistributeUser());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell((short) 12);
		xlsCell.setCellStyle(csField);
		cellValue = new HSSFRichTextString("----");
		xlsCell.setCellValue(cellValue);

		xlsRow = sheet.createRow(rowIndex++);
		cellIndex = 0;
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("下载时间：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getDownloadDate().
				getCalendarValue());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("下载人：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getDownloadUser());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("上传时间：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getUploadDate().
				getCalendarValue());
		xlsCell.setCellValue(cellValue);

		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csField);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString("上传人：");
		xlsCell.setCellValue(cellValue);
		xlsCell = xlsRow.createCell(cellIndex++);
		xlsCell.setCellStyle(csValue);
		xlsCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellValue = new HSSFRichTextString(orderHeader.getUploadUser());
		xlsCell.setCellValue(cellValue);
		for (short i = cellIndex; i < 13; i++) {
			xlsCell = xlsRow.createCell((short) i);
			xlsCell.setCellStyle(csField);
			cellValue = new HSSFRichTextString("----");
			xlsCell.setCellValue(cellValue);
		}
		return wb;
	}

	/**
	 * 功能：导出盘点工单行数据
	 * @param wb HSSFWorkbook
	 * @return HSSFWorkbook
	 */
	private HSSFWorkbook exportLineTitle(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowIndex = 6;
		HSSFCellStyle csField = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Courier New");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		csField.setFont(font);
		csField.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		csField.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		csField.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csField.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csField.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csField.setBorderRight(HSSFCellStyle.BORDER_THIN);
		HSSFRow xlsRow = sheet.createRow(rowIndex++);
		HSSFCell xlsCell = null;
		HSSFRichTextString cellValue = null;
		for (short i = 0; i < 13; i++) {
			xlsCell = xlsRow.createCell(i);
			xlsCell.setCellStyle(csField);
		}
		Region region = new Region(6, (short) 0, 6, (short) 12);
		sheet.addMergedRegion(region); //指定合并区域
		xlsRow = sheet.getRow(6);
		xlsCell = xlsRow.getCell((short) 0);
		xlsCell.setCellValue(new HSSFRichTextString("盘点工单行数据"));

		xlsRow = sheet.createRow(rowIndex++);
		for (short i = 0; i < 13; i++) {
			xlsCell = xlsRow.createCell(i);
			xlsCell.setCellStyle(csField);
		}
		xlsRow = sheet.createRow(rowIndex++);
		String[] fieldDesc = {"", "专业", "名称", "型号", "责任人", "责任部门", "专业", "名称",
				"型号", "责任人", "责任部门", "系统", "扫描"};
		for (short i = 0; i < 13; i++) {
			xlsCell = xlsRow.createCell(i);
			xlsCell.setCellStyle(csField);
			cellValue = new HSSFRichTextString(fieldDesc[i]);
			xlsCell.setCellValue(cellValue);
		}
		region = new Region(7, (short) 0, 8, (short) 0);
		sheet.addMergedRegion(region); //指定合并区域
		region = new Region(7, (short) 1, 7, (short) 5);
		sheet.addMergedRegion(region); //指定合并区域
		region = new Region(7, (short) 6, 7, (short) 10);
		sheet.addMergedRegion(region); //指定合并区域
		region = new Region(7, (short) 11, 7, (short) 12);
		sheet.addMergedRegion(region); //指定合并区域

		xlsRow = sheet.getRow(7);
		cellValue = new HSSFRichTextString("标签号");
		xlsCell = xlsRow.getCell((short) 0);
		xlsCell.setCellValue(cellValue);
		cellValue = new HSSFRichTextString("系统属性");
		xlsCell = xlsRow.getCell((short) 1);
		xlsCell.setCellValue(cellValue);
		cellValue = new HSSFRichTextString("扫描属性");
		xlsCell = xlsRow.getCell((short) 6);
		xlsCell.setCellValue(cellValue);
		cellValue = new HSSFRichTextString("状态");
		xlsCell = xlsRow.getCell((short) 11);
		xlsCell.setCellValue(cellValue);
		return wb;
	}

	/**
	 * 功能：导出盘点工单行数据
	 * @param wb       HSSFWorkbook
	 * @param chkLines DTOSet
	 * @return HSSFWorkbook
	 * @throws ReflectException
	 */
	private HSSFWorkbook exportLineData(HSSFWorkbook wb, DTOSet chkLines) throws
			ReflectException {
		int rowIndex = 9;
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowCount = chkLines.getSize();
		HSSFCellStyle csValue = wb.createCellStyle();
		csValue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		csValue.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		csValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
		AmsAssetsCheckLineDTO chkLineDTO = null;
		HSSFRow xlsRow = null;
		HSSFCell xlsCell = null;
		String fieldValue = "";
		HSSFRichTextString cellValue = null;
		String[] fields = {"barcode", "itemCategoryName", "itemName",
				"itemSpec", "responsibilityUserName",
				"responsibilityDeptName", "scanItemCategoryName",
				"scanItemName", "scanItemSpec",
				"scanResponsibilityUserName",
				"scanResponsibilityDeptName", "systemStatusName",
				"scanStatusName"};
		for (int i = rowIndex; i < rowCount + rowIndex; i++) {
			chkLineDTO = (AmsAssetsCheckLineDTO) chkLines.getDTO(i - rowIndex);
			xlsRow = sheet.createRow(i);
			for (short j = 0; j < fields.length; j++) {
				xlsCell = xlsRow.createCell(j);
				fieldValue = String.valueOf(ReflectionUtil.getProperty(chkLineDTO, fields[j]));
				cellValue = new HSSFRichTextString(fieldValue);
				xlsCell.setCellStyle(csValue);
				xlsCell.setCellValue(cellValue);
			}
		}
		return wb;
	}

	/**
	 * 功能：获取盘点地点下的标签号(用于归档时)
	 * @return List
	 * @throws QueryException
	 */
	public DTOSet getLocBarcodes() throws QueryException {
		ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getLocBarcodesModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		try {
			ArchiveHeaderModel modelProducer = (ArchiveHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "待归档盘点工单";
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
		fieldMap.put("BATCH_NO", "任务批号");
		fieldMap.put("TASK_DESC", "任务描述");
		fieldMap.put("TRANS_NO", "工单编号");
		fieldMap.put("GROUPNAME", "建单组别");
		fieldMap.put("CREATED_USER", "下单人");
		fieldMap.put("CREATION_DATE", "创建日期");
		fieldMap.put("START_TIME", "开始日期");
		fieldMap.put("IMPLEMENT_DAYS", "任务天数");
		fieldMap.put("IMPLEMENT_USER", "执行人");
		fieldMap.put("LOCATION_CODE", "地点代码");
		fieldMap.put("CHECK_LOCATION", "所在位置");
		fieldMap.put("CHECK_CATEGORY_NAME", "扫描设备专业");
		fieldMap.put("COMPANY_NAME", "公司名称");
		fieldMap.put("ORDER_STATUS", "工单状态");
		return fieldMap;
	}
}
