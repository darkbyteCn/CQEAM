package com.sino.ams.workorder.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.workorder.dto.ZeroImportDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

public class ZeroReadObjectInfo {

	private HSSFWorkbook book = null;

	private int startRowNum = 0;
	private int numberOfColumn = 0;

	public void setFileName(String fileName) throws IOException {
		FileInputStream fileIn = new FileInputStream(fileName);
		POIFSFileSystem fs = new POIFSFileSystem(fileIn);
		book = new HSSFWorkbook(fs);
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public void setNumberOfColumn(int numberOfColumn) {
		this.numberOfColumn = numberOfColumn;
	}

	// 读取指定sheet
	public DTOSet readXls(int indexOfSheet) throws DTOException {
		DTOSet orderDTOSet = new DTOSet();
		if (book != null) {
			int numberOfSheet = book.getNumberOfSheets();
			if (indexOfSheet < numberOfSheet) {
				HSSFSheet hssfSheet = book.getSheetAt(indexOfSheet);
				HSSFRow hssfRow = null;
				HSSFCell hssfCell = null;
				int row = 0;
				int row1 = hssfSheet.getLastRowNum();
				for (int a = 0; a < row1; a++) {
					HSSFRow hrow = hssfSheet.getRow(a); // 读取第一行
					HSSFCell hcell1 = hrow.getCell((short) 0);
					if (hcell1!=null&&hcell1.getCellType()==HSSFCell.CELL_TYPE_STRING) {
						 
						if (hcell1.getStringCellValue().trim().equals("其它记录：")) {//
							row=a;
							break;
						}
					}
					
				}
				
				
				for (int i = startRowNum; i < row; i++) {
					hssfRow = hssfSheet.getRow(i);
					if (hssfRow != null) {
						numberOfColumn = numberOfColumn == 0 ? hssfRow
								.getLastCellNum() : numberOfColumn;
						ZeroImportDTO lineDTO = new ZeroImportDTO();
						String excelLineId = String.valueOf(i + 1);
//						lineDTO.setExcelLineId(excelLineId);
						for (int k = 0; k <= numberOfColumn; k++) {
							hssfCell = hssfRow.getCell((short) k);
							String strValue = getStringValue(hssfCell);
							lineDTO = fillLineDTO(k, strValue, lineDTO);
						}
						boolean flag = true;
//						if (isExist(lineDTO)) {
							orderDTOSet.addDTO(lineDTO);
//						}
					}
				}
			}

		}
		return orderDTOSet;
	}

	private ZeroImportDTO fillLineDTO(int indexk, String strValue,
			ZeroImportDTO lineDTO) {
		if (strValue != null) {
			strValue = strValue.trim();
		}
		switch (indexk) {
		case 0:// 
			strValue = noIntType(strValue);
			lineDTO.setRowNum(Integer.parseInt(strValue));
			break;
		case 1:
			lineDTO.setBarcode(strValue);
			break;
		case 2:
			lineDTO.setItemName(strValue);
			break;
		case 3:
			lineDTO.setItemSpec(strValue);
			break;
		case 4:
			lineDTO.setManufacturerName(strValue);
			break;
		case 5:
			lineDTO.setContentCode(strValue);
			break;
		case 6:
			strValue = noIntType(strValue);
			lineDTO.setItemQty(strValue);
			break;
		case 7:
			
			lineDTO.setLifeInYears(strValue);
			break;
		case 8:
			strValue = noIntType(strValue);
			lineDTO.setCost(strValue);
			break;
		case 9:
			lineDTO.setStartDate(strValue);
			break;
		case 10:
			lineDTO.setOpeName(strValue);
			break;
		case 11:
			
			lineDTO.setNleName(strValue);
			break;
		case 12:
			lineDTO.setConstructStatus(strValue);
			break;
		case 13:
			
			lineDTO.setLocationSegment1(strValue);
			break;
		case 14:
			lineDTO.setLocationSegment2Name(strValue);
			break;
		case 15:
			lineDTO.setLocationSegment2(strValue);
			break;
		case 16:
			lineDTO.setLocationSegment3(strValue);
			break;
		case 17:
//			strValue = noIntType(strValue);
			lineDTO.setEmployeeNumber(strValue);
			break;
		case 18:
			lineDTO.setEmployeeName(strValue);
			break;
		case 19:
			lineDTO.setApplyType(strValue);
			break;
		case 20:
			lineDTO.setContactPerson(strValue);
			break;
		case 21:
//			strValue = noIntType(strValue);
			lineDTO.setContactNumber(strValue);
			break;
		case 22:
			lineDTO.setExpectArrivalTime(strValue);
			break;
	
		default:
		}
		return lineDTO;
	}

	private String getStringValue(HSSFCell cell) {
		String strValue = "";
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
				strValue = "";
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				strValue = String.valueOf(cell.getNumericCellValue());
			} else {
				strValue = cell.getRichStringCellValue().toString();
			}
		}
		return strValue;
	}

	// 去掉导入EXCEL中的空白行
	public boolean isExist(ZeroTurnLineDTO lineDTO) {
		boolean flag = true;
		if (lineDTO.getAssetsDescription().equals("")
				&& lineDTO.getBarcode().equals("")
				&& lineDTO.getCexId().equals("")
				&& lineDTO.getCompanyCode().equals("")
				&& lineDTO.getComputeDays().equals("")
				&& lineDTO.getContentName().equals("")
				&& lineDTO.getCostCenterCode().equals("")
				&& lineDTO.getIsBulid().equals("")
				&& lineDTO.getIsShare().equals("")
				&& lineDTO.getItemQty().equals("")
				&& lineDTO.getItemSpec().equals("")
				&& lineDTO.getLneId().equals("")
				&& lineDTO.getManufacturerName().equals("")
				&& lineDTO.getNleId().equals("")
				&& lineDTO.getObjectNo().equals("")
				&& lineDTO.getOpeId().equals("")
				&& lineDTO.getPrice().equals("")
				&& lineDTO.getProcureCode().equals("")
				&& lineDTO.getRemark().equals("")
				&& lineDTO.getResponsibilityDept().equals("")
				&& lineDTO.getResponsibilityUser().equals("")
				&& lineDTO.getSpecialityDept().equals("")
				&& lineDTO.getStartDate().equals("")
				&& lineDTO.getUnitOfMeasure().equals("")
				&& lineDTO.getWorkorderObjectName().equals("")
				&& lineDTO.getYears().equals("")) {
			flag = false;
		}
		return flag;
	}

	// 去掉数据性字符串中的.0样式
	public String noIntType(String strValue) {
		String newStr = strValue;
		if (!strValue.equals("")) {
			if (strValue.length() > 0) {
				int index = strValue.indexOf(".0");
				if (index >= 0) {
					newStr = strValue.substring(0, index);
				}
			}
		}
		return newStr;
	}

//	 public static void main(String[] args) {
//	 String value="3910-70000029,3910-70000030,3910-70000031,";
//	 String os="";
//	 String [] arg=value.split(",");
//	 for (int i = 0; i < arg.length; i++) {
//		 String a=arg[i];
//		if (i!=arg.length-1) {
//			os+="'"+a+"',";
//		}else {
//			os+="'"+a+"'";
//		}
//	}
//	 System.out.println(os);
//	 }

}
