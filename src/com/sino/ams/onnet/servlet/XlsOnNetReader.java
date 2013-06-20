package com.sino.ams.onnet.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-4
 * Time: 20:40:22
 * To change this template use File | Settings | File Templates.
 */
public class XlsOnNetReader {
    private HSSFWorkbook book = null;

     private int startRowNum = 0;
     private int numberOfColumn = 0;

      public void setFileName(String fileName) throws IOException {
         FileInputStream fileIn = new FileInputStream(fileName);
         POIFSFileSystem fs = new POIFSFileSystem(fileIn);
         book = new HSSFWorkbook(fs);
//        book=new HSSFWorkbook(fileIn);
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
                 int col = hssfSheet.getPhysicalNumberOfRows();

                 for (int i = startRowNum; i <= row; i++) {
                     hssfRow = hssfSheet.getRow(i);
                     if (hssfRow != null) {
                         numberOfColumn = numberOfColumn == 0 ? hssfRow.getLastCellNum() : numberOfColumn;
                         AmsItemOnNetDTO onNetDtlDTO = new AmsItemOnNetDTO();
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

     private AmsItemOnNetDTO fillOrderDtlDTO(int indexk, String strValue, AmsItemOnNetDTO onNetDtlDTO) {
         switch (indexk) {
             case 0: //设备部件号
                 onNetDtlDTO.setPartNo(strValue);
                 break;
             case 1://数量
                 onNetDtlDTO.setQuantity(StrUtil.strToInt(strValue));
                 break;
             case 2: //组织id
                 onNetDtlDTO.setOrganizationId(StrUtil.strToInt(strValue));
                 break;
             case 3://备注
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
