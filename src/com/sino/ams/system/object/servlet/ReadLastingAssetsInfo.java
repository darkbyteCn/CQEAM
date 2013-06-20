package com.sino.ams.system.object.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.system.object.dto.LastingAssetsDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-21
 * Time: 15:51:25
 * To change this template use File | Settings | File Templates.
 */
public class ReadLastingAssetsInfo {
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
                         LastingAssetsDTO onNetDtlDTO = new LastingAssetsDTO();
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

     private LastingAssetsDTO fillOrderDtlDTO(int indexk, String strValue, LastingAssetsDTO onNetDtlDTO) {
         if(strValue != null){
             strValue = strValue.trim();
         }
//         try {
             switch (indexk) {
                 case 0:
                     onNetDtlDTO.setCompanyCode(strValue);
                     break;
                 case 1:
                     onNetDtlDTO.setBarcode(strValue);
                     break;
                 case 2:
                     onNetDtlDTO.setItemName(strValue);
                     break;
                 case 3:
                     onNetDtlDTO.setItemSpec(strValue);
                     break;
                 case 4:
                     onNetDtlDTO.setEmployeeNumber(strValue);
                     break;
                 case 5:
                     onNetDtlDTO.setEmployeeName(strValue);
                     break;
                 case 6:
                     onNetDtlDTO.setWorkorderObjectCode(strValue);
                     break;
                 case 7:
                     onNetDtlDTO.setPower(strValue);
                     break;
                 case 8:
                     onNetDtlDTO.setEquipmentPerformance(strValue);
                     break;
                 case 9:
                     onNetDtlDTO.setContentCode(strValue);
                     break;
                 case 10:
                     onNetDtlDTO.setContentName(strValue);
                     break;
                 case 11:
                     onNetDtlDTO.setWorkorderObjectName(strValue);
                     break;
                 case 12:
                     onNetDtlDTO.setSpecialityDept(strValue);
                     break;
                 case 13:
                     onNetDtlDTO.setMaintainUser(strValue);
                     break;
                 case 14:
                     onNetDtlDTO.setMaintainDept(strValue);
                     break;
                 case 15:
                     onNetDtlDTO.setRentStartDate(strValue);
                     break;
                 case 16:
                     onNetDtlDTO.setRentEndDate(strValue);
                     break;
                 case 17:
                     onNetDtlDTO.setRentPerson(strValue);
                     break;
                 case 18:
                     onNetDtlDTO.setTenancy(strValue);
                     break;
                 case 19:
                     onNetDtlDTO.setYearRental(strValue);
                     break;
                 case 20:
                     onNetDtlDTO.setMonthReantal(strValue);
                     break;
                 case 21:
                     onNetDtlDTO.setRemark(strValue);
                     break;
                 default:
             }
//         } catch (CalendarException ex) {
//            ex.printLog();
//         }
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
