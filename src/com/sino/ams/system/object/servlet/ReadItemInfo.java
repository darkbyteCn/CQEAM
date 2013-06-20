package com.sino.ams.system.object.servlet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.FileInputStream;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.ams.system.object.dto.EtsItemDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-4-26
 * Time: 11:51:27
 * To change this template use File | Settings | File Templates.
 */
public class ReadItemInfo {
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

     public DTOSet readXls(int indexOfSheet ) throws DTOException {
    	 return readXls( indexOfSheet , false );
     }
     //∂¡»°÷∏∂®sheet
     public DTOSet readXls(int indexOfSheet , boolean isNew ) throws DTOException {
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
                         EtsItemDTO onNetDtlDTO = new EtsItemDTO();
                         onNetDtlDTO.setExcelLineId(String.valueOf(i+1));
                         for (int k = 0; k <= numberOfColumn; k++) {
                             hssfCell = hssfRow.getCell((short) k);
                             String strValue = getStringValue(hssfCell);
                             if( isNew ){
                            	 onNetDtlDTO = fillOrderDtlDTOForNew(k, strValue, onNetDtlDTO);
                             }else{
                            	 onNetDtlDTO = fillOrderDtlDTO(k, strValue, onNetDtlDTO);
                             }
                             
                         }
                         orderDTOSet.addDTO(onNetDtlDTO);
                     }
                 }
             }

         }
         return orderDTOSet;
     }
     
     private EtsItemDTO fillOrderDtlDTOForNew(int indexk, String strValue, EtsItemDTO onNetDtlDTO) {
         if(strValue != null){
             strValue = strValue.trim();
         }
         switch (indexk) {
             case 0:
                 onNetDtlDTO.setBookTypeCode(strValue);
                 break;
             case 1:
                 onNetDtlDTO.setBarcode(strValue);
                 break;
             case 2:
            	 onNetDtlDTO.setContentCode(strValue);
            	 break;
             case 3:
                 onNetDtlDTO.setNewItemName(strValue);
                 break;
             case 4:
                 onNetDtlDTO.setNewItemSpec(strValue);
                 break;
             case 5:
                 onNetDtlDTO.setNewObjectCode(strValue);
                 break;
             case 6:
                 onNetDtlDTO.setNewResponsibilityDept(strValue);
                 break;
             case 7:
                 onNetDtlDTO.setNewEmployeeNumber(strValue);
                 break;
             case 8:
                 onNetDtlDTO.setNewSpecialityDept(strValue);
                 break;
             case 9:
                 onNetDtlDTO.setNewMaintainDept(strValue);
                 break;
             case 10:
                 onNetDtlDTO.setNewMaintainUser(strValue);
                 break;
             case 11:
                 onNetDtlDTO.setNewManufacturerId(strValue);
                 break;
             case 12:
                 onNetDtlDTO.setNewLneId(strValue);
                 break;
             case 13:
                 onNetDtlDTO.setNewCexId(strValue);
                 break;
             case 14:
                 onNetDtlDTO.setNewOpeId(strValue);
                 break;
             case 15:
                 onNetDtlDTO.setNewNleId(strValue);
                 break;
             case 16:
                 onNetDtlDTO.setNewRemark1(strValue);
                 break;
             case 17:
                 onNetDtlDTO.setNewConstructStatus(strValue);
                 break;
             case 18:
                 onNetDtlDTO.setNewShareStatus(strValue);
                 break;
             default:
         }
         return onNetDtlDTO;
     }

     private EtsItemDTO fillOrderDtlDTO(int indexk, String strValue, EtsItemDTO onNetDtlDTO) {
         if(strValue != null){
             strValue = strValue.trim();
         }
         switch (indexk) {
             case 0:
                 onNetDtlDTO.setBookTypeCode(strValue);
                 break;
             case 1:
                 onNetDtlDTO.setBarcode(strValue);
                 break;
             case 2:
            	 onNetDtlDTO.setOldContentCode(strValue);
            	 break;
             case 3:
            	 onNetDtlDTO.setContentCode(strValue);
            	 break;
             case 4:
                 onNetDtlDTO.setOldItemName(strValue);
                 break;
             case 5:
                 onNetDtlDTO.setNewItemName(strValue);
                 break;
             case 6:
                 onNetDtlDTO.setOldItemSpec(strValue);
                 break;
             case 7:
                 onNetDtlDTO.setNewItemSpec(strValue);
                 break;
             case 8:
                 onNetDtlDTO.setOldObjectCode(strValue);
                 break;
             case 9:
                 onNetDtlDTO.setNewObjectCode(strValue);
                 break;
             case 10:
                 onNetDtlDTO.setOldResponsibilityDept(strValue);
                 break;
             case 11:
                 onNetDtlDTO.setNewResponsibilityDept(strValue);
                 break;
             case 12:
                 onNetDtlDTO.setOldEmployeeNumber(strValue);
                 break;
             case 13:
                 onNetDtlDTO.setNewEmployeeNumber(strValue);
                 break;
             case 14:
                 onNetDtlDTO.setOldSpecialityDept(strValue);
                 break;
             case 15:
                 onNetDtlDTO.setNewSpecialityDept(strValue);
                 break;
             case 16:
                 onNetDtlDTO.setOldMaintainDept(strValue);
                 break;
             case 17:
                 onNetDtlDTO.setNewMaintainDept(strValue);
                 break;
             case 18:
                 onNetDtlDTO.setOldMaintainUser(strValue);
                 break;
             case 19:
                 onNetDtlDTO.setNewMaintainUser(strValue);
                 break;
             case 20:
                 onNetDtlDTO.setOldManufacturerId(strValue);
                 break;
             case 21:
                 onNetDtlDTO.setNewManufacturerId(strValue);
                 break;
             case 22:
                 onNetDtlDTO.setOldLneId(strValue);
                 break;
             case 23:
                 onNetDtlDTO.setNewLneId(strValue);
                 break;
             case 24:
                 onNetDtlDTO.setOldCexId(strValue);
                 break;
             case 25:
                 onNetDtlDTO.setNewCexId(strValue);
                 break;
             case 26:
                 onNetDtlDTO.setOldOpeId(strValue);
                 break;
             case 27:
                 onNetDtlDTO.setNewOpeId(strValue);
                 break;
             case 28:
                 onNetDtlDTO.setOldNleId(strValue);
                 break;
             case 29:
                 onNetDtlDTO.setNewNleId(strValue);
                 break;
             case 30:
                 onNetDtlDTO.setOldRemark1(strValue);
                 break;
             case 31:
                 onNetDtlDTO.setNewRemark1(strValue);
                 break;
             case 32:
                 onNetDtlDTO.setOldConstructStatus(strValue);
                 break;
             case 33:
                 onNetDtlDTO.setNewConstructStatus(strValue);
                 break;
             case 34:
                 onNetDtlDTO.setOldShareStatus(strValue);
                 break;
             case 35:
                 onNetDtlDTO.setNewShareStatus(strValue);
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
