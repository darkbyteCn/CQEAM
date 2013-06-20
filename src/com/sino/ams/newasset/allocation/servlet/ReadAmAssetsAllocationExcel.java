package com.sino.ams.newasset.allocation.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

/** 
 *
 * @version 创建时间：Apr 18, 2011 4:27:34 PM 
 * 类说明:解析Excel文件，返回DOTSet集合 
 *
 */
public class ReadAmAssetsAllocationExcel {
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

		 //读取指定sheet
		 public DTOSet readXls(int indexOfSheet) throws DTOException {
			 DTOSet orderDTOSet = new DTOSet();
			 if (book != null) {
				 int numberOfSheet = book.getNumberOfSheets();
				 if (indexOfSheet < numberOfSheet) {
					 HSSFSheet hssfSheet = book.getSheetAt(indexOfSheet);
					 HSSFRow hssfRow = null;
					 HSSFCell hssfCell = null;
					 int row = hssfSheet.getLastRowNum();
					 for (int i = startRowNum; i <= row; i++) {
						 hssfRow = hssfSheet.getRow(i);
						 if (hssfRow != null) {
							 numberOfColumn = numberOfColumn == 0 ? hssfRow.getLastCellNum() : numberOfColumn;
							 AmsAssetsTransLineDTO onNetDtlDTO = new AmsAssetsTransLineDTO();
							 String excelLineId=String.valueOf(i+1);
							 onNetDtlDTO.setExcelLineId(excelLineId);
							 for (int k = 0; k <= numberOfColumn; k++) {
								 hssfCell = hssfRow.getCell((short) k);
								 String strValue = getStringValue(hssfCell);
								 onNetDtlDTO = fillOrderDtlDTO(k, strValue, onNetDtlDTO);
							 }
	                         orderDTOSet.addDTO(onNetDtlDTO);
	                     }
					 }
				 }

			 }
			 return orderDTOSet;
		 }

		 private AmsAssetsTransLineDTO fillOrderDtlDTO(int indexk, String strValue, AmsAssetsTransLineDTO onNetDtlDTO) {
			 if(strValue != null){
				 strValue = strValue.trim();
			 }
	         switch (indexk) {
				 case 0://资产条码
					 onNetDtlDTO.setBarcode(strValue);
					 break;
				 case 1://调入地点编码
					 onNetDtlDTO.setAssignedToLocation(strValue);   
					 break;
				 case 2://新责任人编号
					 onNetDtlDTO.setResponsibilityUser(strValue);
					 break;
			 }
	         return onNetDtlDTO;
		 }

		 private String getStringValue(HSSFCell cell) {
			 String strValue = "";
			 if (cell != null) {
				 if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					 strValue = "";
				 } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					 double a = cell.getNumericCellValue();
					 int b=(int)a;
					 strValue = b+"";
				 } else {
					 strValue = cell.getRichStringCellValue().toString();
				 }
			 }
			 return strValue;
		 }
}