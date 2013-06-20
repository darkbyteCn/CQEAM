package com.sino.ams.spare.part.servlet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.db.util.SeqProducer;
import com.sino.ams.spare.part.dto.ImportSpareCategoryDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class ReadSpareCategoryInfo {
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

     public DTOSet readXls(int indexOfSheet, Connection conn) throws DTOException, SQLException {
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
                         ImportSpareCategoryDTO dto = new ImportSpareCategoryDTO();
                         String id = getNextCategoryBarcode(conn);
                         for (int k = 0; k <= numberOfColumn; k++) {
                             hssfCell = hssfRow.getCell((short) k);
                             String strValue = getStringValue(hssfCell);
                             dto.setId(String.valueOf(id));
                             dto = fillOrderDtlDTO(k, strValue, dto);
                         }
                         orderDTOSet.addDTO(dto);
                     }
                 }
             }
         }
         return orderDTOSet;
     }

     private ImportSpareCategoryDTO fillOrderDtlDTO(int indexk, String strValue, ImportSpareCategoryDTO dto) {
         if(strValue != null){
             strValue = strValue.trim();
         }
         switch (indexk) {
             case 0:
                 dto.setItemName(strValue);
                 break;
             case 1:
                 dto.setItemSpec(strValue);
                 break;
             case 2:
                 dto.setItemCategory(strValue);
                 break;
             case 3:
                 dto.setSpareUsage(strValue);
                 break;
             case 4:
                 dto.setVendorId(strValue);
                 break;
             case 5:
                 dto.setItemUnit(strValue);
                 break;
             default:
         }
         return dto;
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

     private String getNextCategoryBarcode(Connection conn) throws SQLException {
		SeqProducer seqProducer = new SeqProducer(conn);
//		return seqProducer.getStrNextSeq("AMS_SPARE_CATEGORY_IMPORT_S");
		return seqProducer.getGUID();
	}
}
