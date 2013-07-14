package com.sino.ams.yearchecktaskmanager.util;



import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.yearchecktaskmanager.dto.AssetsYearClientDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

/** 
 *
 * @version ����ʱ�䣺Apr 18, 2011 4:27:34 PM 
 * ��˵��:����Excel�ļ�������DOTSet���� 
 *
 */
public class ReadClientExcel {
	   private HSSFWorkbook book = null;

		 private int startRowNum = 0;
		 private int numberOfColumn = 0;

		  public void setFileName(String fileName) throws IOException {
			 FileInputStream fileIn = new FileInputStream(fileName);
			 POIFSFileSystem fs = new POIFSFileSystem(fileIn);
			 book = new HSSFWorkbook(fs);
//	        book=new HSSFWorkbook(fileIn);
		 }

		 public void setStartRowNum(int startRowNum) {
			 this.startRowNum = startRowNum;
		 }

		 public void setNumberOfColumn(int numberOfColumn) {
			 this.numberOfColumn = numberOfColumn;
		 }

		 //��ȡָ��sheet
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
							 AssetsYearClientDTO onNetDtlDTO = new AssetsYearClientDTO();
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

		 private AssetsYearClientDTO fillOrderDtlDTO(int indexk, String strValue, AssetsYearClientDTO onNetDtlDTO) {
			 if(strValue != null){
				 strValue = strValue.trim();
			 }
             int dotIndex = strValue.indexOf(".");
	         switch (indexk) {
				 case 0: //�ʲ���ǩ
					 onNetDtlDTO.setBarcode(strValue);
					 break;
				 case 1://�ʲ�����
					 onNetDtlDTO.setAssetsDescription(strValue);
					 break;
	             case 2://Ӧ����������
					 onNetDtlDTO.setFaCategory1(strValue);
					 break;
				 case 3://�ص����
					 onNetDtlDTO.setWorkorderObjectCode(strValue);   
					 break;
				 case 4://�ص�����
					 onNetDtlDTO.setWorkorderObjectName(strValue);
	             default:
			 }
	         return onNetDtlDTO;
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
}
