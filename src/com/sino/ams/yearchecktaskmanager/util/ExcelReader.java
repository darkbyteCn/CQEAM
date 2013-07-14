package com.sino.ams.yearchecktaskmanager.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.yearchecktaskmanager.dto.AssetsRespMapLocationDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

public class ExcelReader {
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
                        AssetsRespMapLocationDTO amlDto = new AssetsRespMapLocationDTO();
                        amlDto.setExcelLineId(String.valueOf(i+1));
                        for (int k = 0; k <= numberOfColumn; k++) {
                            hssfCell = hssfRow.getCell((short) k);
                            String strValue = getStringValue(hssfCell);
                            amlDto = fillDTO(k, strValue, amlDto);
                        }
                        orderDTOSet.addDTO(amlDto);
                    }
                }
            }

        }
        return orderDTOSet;
    }
    
    private AssetsRespMapLocationDTO fillDTO(int indexk, String strValue, AssetsRespMapLocationDTO amlDto) {
        if(strValue != null){
            strValue = strValue.trim();
        }
        switch (indexk) {
            case 0:
            	amlDto.setUserName(strValue);
                break;
            case 1:
            	amlDto.setEmployeeNumber(strValue);
                break;
            case 2:
            	amlDto.setDeptName(strValue);
            case 3:
            	amlDto.setWorkOrderObjectCode(strValue);
           	 break;
            case 4:
            	amlDto.setWorkOrderObjectName(strValue);
            default:
        }
        return amlDto;
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
        return strValue.trim();
    }

}
