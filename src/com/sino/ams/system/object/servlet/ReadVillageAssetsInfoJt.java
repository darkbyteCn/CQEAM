package com.sino.ams.system.object.servlet;

import com.sino.ams.system.object.dto.ImportVillageAssetsDTOJt;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-25
 * Time: 10:03:27
 * To change this template use File | Settings | File Templates.
 */
public class ReadVillageAssetsInfoJt {
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
                         ImportVillageAssetsDTOJt onNetDtlDTO = new ImportVillageAssetsDTOJt();
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

     public ImportVillageAssetsDTOJt fillOrderDtlDTO(int indexk, String strValue, ImportVillageAssetsDTOJt onNetDtlDTO) {
         if(strValue != null){
             strValue = strValue.trim();
         }
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
                 onNetDtlDTO.setWorkorderObjectName(strValue);
                 break;
             case 8:
                 onNetDtlDTO.setFinanceProp(strValue);
                 break;
             case 9:
                 if (strValue.equals("")) {
                         strValue = "1";
                     }
                 onNetDtlDTO.setItemQty(strValue);
                 break;
             case 10:
                 onNetDtlDTO.setActualQty(strValue);
                 break;
             case 11:
                 onNetDtlDTO.setEquipmentPerformance(strValue);
                 break;
             case 12:
                 onNetDtlDTO.setContentCode(strValue);
                 break;
             case 13:
                 onNetDtlDTO.setContentName(strValue);
                 break;
             case 14:
                 onNetDtlDTO.setSpecialityDept(strValue);
                 break;
             case 15:
                 onNetDtlDTO.setMaintainUser(strValue);
                 break;
             case 16:
                 onNetDtlDTO.setPrice(strValue);
                 break;
             case 17:
                 onNetDtlDTO.setVillageStartDate(strValue);
                 break;
             case 18:
                 onNetDtlDTO.setTfDepreciation(strValue);
                 break;
             case 19:
                 onNetDtlDTO.setTfNetAssetValue(strValue);
                 break;
             case 20:
                 onNetDtlDTO.setTfDeprnCost(strValue);
                 break;
             case 21:
                 onNetDtlDTO.setDeprnAmount(strValue);
                 break;
             case 22:
                 onNetDtlDTO.setImpairAmount(strValue);
                 break;
             case 23:
                 onNetDtlDTO.setYtdImpairment(strValue);
                 break;
             case 24:
                 onNetDtlDTO.setYtdDeprn(strValue);
                 break;
             case 25:
                 onNetDtlDTO.setDeprnLeftMonth(strValue);
                 break;
             case 26:
                 onNetDtlDTO.setScrapValue(strValue);
                 break;
             case 27:
                 onNetDtlDTO.setImpairReserve(strValue);
                 break;
             case 28:
                 onNetDtlDTO.setDeprnReserve(strValue);
                 break;
             case 29:
                 onNetDtlDTO.setProjectid(strValue);
                 break;
             case 30:
                 onNetDtlDTO.setProjectName(strValue);
                 break;
             case 31:
                 onNetDtlDTO.setConstructStatus(strValue);
                 break;
             case 32:
                 onNetDtlDTO.setShare(strValue);
                 break;
             case 33:
                 onNetDtlDTO.setManufacturerId(strValue);
                 break;
             case 34:
                 onNetDtlDTO.setRemark(strValue);
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