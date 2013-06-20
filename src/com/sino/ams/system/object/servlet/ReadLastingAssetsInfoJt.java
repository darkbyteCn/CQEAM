package com.sino.ams.system.object.servlet;

import com.sino.ams.system.object.dto.LastingAssetsDTOJt;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DateException;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.log.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-26
 * Time: 11:37:20
 * To change this template use File | Settings | File Templates.
 */
public class ReadLastingAssetsInfoJt {
    private HSSFWorkbook book = null;
    private int startRowNum = 0;
    private int numberOfColumn = 0; 
    private final static String[] fieldNames = {
            "companyCode", "barcode", "itemName", "itemSpec",
            "employeeNumber", "employeeName", "workorderObjectCode",
            "workorderObjectName", "itemQty", "manufacturerName",
            "equipmentPerformance", "contentCode", "contentName",
            "specialityDept", "maintainUser", "rentStartDate",
            "rentEndDate", "rentPerson", "contractNumber", "contractName",
            "tenancy", "yearRental", "monthReantal", "remark"
    };

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
        DTOSet orderDTOSet = null;
        try {
            if (book != null) {
                int numberOfSheet = book.getNumberOfSheets();
                if (indexOfSheet < numberOfSheet) {
                    orderDTOSet = new DTOSet();
                    HSSFSheet hssfSheet = book.getSheetAt(indexOfSheet);
                    HSSFRow hssfRow = null;
                    HSSFCell hssfCell = null;
                    int row = hssfSheet.getLastRowNum();
                    if (row >= startRowNum) {
                        int fieldCount = fieldNames.length;
                        for (int i = startRowNum; i <= row; i++) {
                            hssfRow = hssfSheet.getRow(i);
                            if (hssfRow != null) {
                                numberOfColumn = hssfRow.getLastCellNum();
                                LastingAssetsDTOJt onNetDtlDTO = new LastingAssetsDTOJt();
                                for (int k = 0; k < numberOfColumn; k++) {
                                    hssfCell = hssfRow.getCell((short) k);
                                    if (k < fieldCount) {
                                        String strValue = getStringValue(hssfCell, fieldNames[k]);
                                        fillOrderDtlDTO(fieldNames[k], strValue, onNetDtlDTO);
                                    }
                                }
                                orderDTOSet.addDTO(onNetDtlDTO);
                            }
                        }
                    }
                }
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new DTOException(ex);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new DTOException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DTOException(ex.getMessage());
        }
        return orderDTOSet;
    }

    private void fillOrderDtlDTO(String fieldName, String strValue, LastingAssetsDTOJt onNetDtlDTO) throws ReflectException {
        if (strValue != null) {
            strValue = strValue.trim();
            if (fieldName.equals("companyCode")) {
                if (strValue != null && strValue.indexOf(".") > -1) {
                    strValue = strValue.substring(0, strValue.indexOf("."));
                }
            } else if (fieldName.equals("itemQty")) {
                if (StrUtil.isEmpty(strValue)) {
                    strValue = "1";
                }
                if (strValue.indexOf(".") > -1) {
                    strValue = strValue.substring(0, strValue.indexOf("."));
                }
            } else if (fieldName.equals("specialityDept")) {
                if (strValue.indexOf(".") > -1) {
                    strValue = strValue.substring(0, strValue.indexOf("."));
                }
            } else if (fieldName.equals("tenancy")) {
                if (strValue.indexOf(".") > -1) {
                    strValue = strValue.substring(0, strValue.indexOf("."));
                }
            }
        }
        ReflectionUtil.setProperty(onNetDtlDTO, fieldName, strValue);
    }

    private String getStringValue(HSSFCell cell, String fieldName) throws CalendarException {
        String strValue = "";
        try {
            if (cell != null) {
                if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                    strValue = "";
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    if (fieldName.equals("rentStartDate") || fieldName.equals("rentEndDate")) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            double d = cell.getNumericCellValue(); 
                            Date date = HSSFDateUtil.getJavaDate(d); 
                            SimpleCalendar cal = new SimpleCalendar(date.getTime());
                            strValue = cal.getSimpleDate().getDateValue();
                        } else {
                            strValue = NumberFormat.getNumberInstance().format(cell.getNumericCellValue());
                            strValue = strValue.replaceAll(",", "");
                            if (!CalendarUtil.isValidCalendar(strValue) && !CalendarUtil.isValidDate(strValue)) {
                                double d = Double.parseDouble(strValue);
                                Date date = HSSFDateUtil.getJavaDate(d);
                                SimpleCalendar cal = new SimpleCalendar(date.getTime());
                                strValue = cal.getSimpleDate().getDateValue();
                            }
                        }
                    } else {
                        strValue = String.valueOf(cell.getNumericCellValue());
                        if (strValue.indexOf("E") > -1) {
                            strValue = StrUtil.formatScienceNum(strValue);
                        }
                    }
                } else {
                    strValue = cell.getRichStringCellValue().toString();
                    if (fieldName.equals("rentStartDate") || fieldName.equals("rentEndDate")) {
                        if (CalendarUtil.isValidCalendar(strValue)) {
                            SimpleCalendar cal = new SimpleCalendar(strValue);
                            cal.setCalPattern(CalendarConstant.CAL_PATT_14);
                            strValue = cal.getCalendarValue();
                        } else if (CalendarUtil.isValidDate(strValue)) {
                            SimpleCalendar cal = new SimpleCalendar(strValue);
                            cal.setCalPattern(CalendarConstant.CAL_PATT_14);
                            strValue = cal.getSimpleDate().getDateValue();
                        }
                    }
                }
            }
        } catch (DateException ex) {
            ex.printLog();
            throw new CalendarException(ex);
        }
        return strValue;
    }
}
