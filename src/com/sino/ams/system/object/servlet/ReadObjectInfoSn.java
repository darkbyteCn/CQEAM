package com.sino.ams.system.object.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.system.object.dto.ImportObjectDTOSn;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-1
 * Time: 17:48:44
 * To change this template use File | Settings | File Templates.
 */
public class ReadObjectInfoSn {
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
						 ImportObjectDTOSn onNetDtlDTO = new ImportObjectDTOSn();
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

	 private ImportObjectDTOSn fillOrderDtlDTO(int indexk, String strValue, ImportObjectDTOSn onNetDtlDTO) {
		 if(strValue != null){
			 strValue = strValue.trim();
		 }
         switch (indexk) {
			 case 0: //地点代码
				 onNetDtlDTO.setLocationCode(strValue);
				 break;
			 case 1://地市
				 onNetDtlDTO.setCity(strValue);
				 break;
             case 2://区县
				 onNetDtlDTO.setCounty(strValue);
				 break;
             case 3://地点描述
				 onNetDtlDTO.setLocation(strValue);
				 break;
             case 4: //专业代码
				 onNetDtlDTO.setObjectCategory(strValue);
				 break;
			 case 5://成本中心代码
				 onNetDtlDTO.setCountyCode(strValue);
				 break;
             case 6://行政区划
				 onNetDtlDTO.setAreaType(strValue);
				 break;
             case 7://是否TD
				 onNetDtlDTO.setIsTd(strValue);
				 break;
             case 8://公司代码
				 onNetDtlDTO.setCompanyCode(strValue);
				 break;
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