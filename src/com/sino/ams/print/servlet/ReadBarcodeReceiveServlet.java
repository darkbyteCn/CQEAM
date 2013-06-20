package com.sino.ams.print.servlet;

import com.sino.ams.print.dto.BarcodeReceiveDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadBarcodeReceiveServlet {
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

     //∂¡»°÷∏∂®sheet
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
                         BarcodeReceiveDTO barcodeReceiveDTO = new BarcodeReceiveDTO();
                         for (int k = 0; k <= numberOfColumn; k++) {
                             hssfCell = hssfRow.getCell((short) k);
                             String strValue = getStringValue(hssfCell);
                             barcodeReceiveDTO = fillOrderDtlDTO(k, strValue, barcodeReceiveDTO);
                         }
                         orderDTOSet.addDTO(barcodeReceiveDTO);
                     }
                 }
             }

         }
         return orderDTOSet;
     }

     private BarcodeReceiveDTO fillOrderDtlDTO(int indexk, String strValue, BarcodeReceiveDTO barcodeReceiveDTO) {
         if(strValue != null){
             strValue = strValue.trim();
         }
         switch (indexk) {
             case 0:
            	 barcodeReceiveDTO.setBarcode(strValue);
                 break;
             case 1:
            	 barcodeReceiveDTO.setOrganization(strValue);
                 break;
             case 2:
            	 barcodeReceiveDTO.setReceiveDeptName(strValue);
                 break;
             case 3:
            	 barcodeReceiveDTO.setReceiveUserName(strValue);
                 break;
             case 4:
            	 try {
					barcodeReceiveDTO.setReceiveDate(strValue);
				} catch (CalendarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 break;
             case 5:
            	 try {
					barcodeReceiveDTO.setPrintDate(strValue);
				} catch (CalendarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 break;
             case 6:
            	 barcodeReceiveDTO.setPrintUserName(strValue);
                 break;
             case 7:
            	 barcodeReceiveDTO.setReceiveRemark(strValue);
             default:
         }
         return barcodeReceiveDTO;
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