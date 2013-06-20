package com.sino.ams.newasset.dto;

import com.sino.base.exception.CalendarException;
import com.sino.base.log.Logger;
import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.util.ReflectionUtil;

import java.lang.reflect.Field;

/**
 * <p>Title: 实物台账维护查询参数备份DTO</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class SearchParameterBackDTO extends SinoBaseObject implements DTO {
    private String itemCategoryBack = "";
    private String itemNameBack = "";
    private String fromBarcodeBack = "";
    private String toBarcodeBack = "";
    private String specialityDeptBack = "";
    private String itemSpecBack = "";
    private SimpleCalendar creationDateBack = null;
    private SimpleCalendar lastUpdateDateBack = null;
    private String responsibilityDeptBack = "";
    private String responsibilityUserNameBack = "";
    private SimpleCalendar startDateBack = null;
    private SimpleCalendar endDateBack = null;
    private String maintainDeptBack = "";
    private String maintainUserBack = "";
    private String workorderObjectNameBack = "";
    private String manufacturerNameBack = "";
    private String manufacturerIdBack = "";
    private String itemStatusBack = "";
    private String financePropBack = "";
    private String contentCodeBack = "";
    private String contentNameBack = "";
    private String projectNameBack = "";
    private String projectNumberBack = "";
    private String isShareBack = "";
    private String logNetEleBack = "";
    private String investCatNameBack = "";
    private String opeNameBack = "";
    private String constructStatusBack = "";
    private String lneNameBack = "";

    public SearchParameterBackDTO(){
        this.creationDateBack = new SimpleCalendar();
        this.lastUpdateDateBack = new SimpleCalendar();
        this.startDateBack = new SimpleCalendar();
        this.endDateBack = new SimpleCalendar();
    }


    public String getResponsibilityDeptBack() {
        return responsibilityDeptBack;
    }

    public void setResponsibilityDeptBack(String responsibilityDeptBack) {
        this.responsibilityDeptBack = responsibilityDeptBack;
    }

    public String getItemCategoryBack() {
        return itemCategoryBack;
    }

    public void setItemCategoryBack(String itemCategoryBack) {
        this.itemCategoryBack = itemCategoryBack;
    }

    public String getItemNameBack() {
        return itemNameBack;
    }

    public void setItemNameBack(String itemNameBack) {
        this.itemNameBack = itemNameBack;
    }

    public String getFromBarcodeBack() {
        return fromBarcodeBack;
    }

    public void setFromBarcodeBack(String fromBarcodeBack) {
        this.fromBarcodeBack = fromBarcodeBack;
    }

    public String getToBarcodeBack() {
        return toBarcodeBack;
    }

    public void setToBarcodeBack(String toBarcodeBack) {
        this.toBarcodeBack = toBarcodeBack;
    }

    public String getSpecialityDeptBack() {
        return specialityDeptBack;
    }

    public void setSpecialityDeptBack(String specialityDeptBack) {
        this.specialityDeptBack = specialityDeptBack;
    }

    public String getItemSpecBack() {
        return itemSpecBack;
    }

    public void setItemSpecBack(String itemSpecBack) {
        this.itemSpecBack = itemSpecBack;
    }

    public SimpleCalendar getCreationDateBack() {
        return creationDateBack;
    }

    public void setCreationDateBack(String creationDateBack) throws CalendarException {
        this.creationDateBack.setCalendarValue(creationDateBack);
    }

    public SimpleCalendar getLastUpdateDateBack() {
        return lastUpdateDateBack;
    }

    public void setLastUpdateDateBack(String lastUpdateDateBack) throws CalendarException {
        this.lastUpdateDateBack.setCalendarValue(lastUpdateDateBack);
    }

    public String getResponsibilityUserNameBack() {
        return responsibilityUserNameBack;
    }

    public void setResponsibilityUserNameBack(String responsibilityUserNameBack) {
        this.responsibilityUserNameBack = responsibilityUserNameBack;
    }

    public SimpleCalendar getStartDateBack() {
        return startDateBack;
    }

    public void setStartDateBack(String startDateBack) throws CalendarException {
        this.startDateBack.setCalendarValue(startDateBack);
    }

    public SimpleCalendar getEndDateBack() {
        return endDateBack;
    }

    public void setEndDateBack(String endDateBack) throws CalendarException {
        this.endDateBack.setCalendarValue(endDateBack);
    }

    public String getMaintainDeptBack() {
        return maintainDeptBack;
    }

    public void setMaintainDeptBack(String maintainDeptBack) {
        this.maintainDeptBack = maintainDeptBack;
    }

    public String getMaintainUserBack() {
        return maintainUserBack;
    }

    public void setMaintainUserBack(String maintainUserBack) {
        this.maintainUserBack = maintainUserBack;
    }

    public String getWorkorderObjectNameBack() {
        return workorderObjectNameBack;
    }

    public void setWorkorderObjectNameBack(String workorderObjectNameBack) {
        this.workorderObjectNameBack = workorderObjectNameBack;
    }

    public String getManufacturerNameBack() {
        return manufacturerNameBack;
    }

    public void setManufacturerNameBack(String manufacturerNameBack) {
        this.manufacturerNameBack = manufacturerNameBack;
    }

    public String getManufacturerIdBack() {
        return manufacturerIdBack;
    }

    public void setManufacturerIdBack(String manufacturerIdBack) {
        this.manufacturerIdBack = manufacturerIdBack;
    }

    public String getItemStatusBack() {
        return itemStatusBack;
    }

    public void setItemStatusBack(String itemStatusBack) {
        this.itemStatusBack = itemStatusBack;
    }

    public String getFinancePropBack() {
        return financePropBack;
    }

    public void setFinancePropBack(String financePropBack) {
        this.financePropBack = financePropBack;
    }

    public String getContentCodeBack() {
        return contentCodeBack;
    }

    public void setContentCodeBack(String contentCodeBack) {
        this.contentCodeBack = contentCodeBack;
    }

    public String getContentNameBack() {
        return contentNameBack;
    }

    public void setContentNameBack(String contentNameBack) {
        this.contentNameBack = contentNameBack;
    }

    public String getProjectNameBack() {
        return projectNameBack;
    }

    public void setProjectNameBack(String projectNameBack) {
        this.projectNameBack = projectNameBack;
    }

    public String getProjectNumberBack() {
        return projectNumberBack;
    }

    public void setProjectNumberBack(String projectNumberBack) {
        this.projectNumberBack = projectNumberBack;
    }

    public String getShareBack() {
        return isShareBack;
    }

    public void setShareBack(String shareBack) {
        isShareBack = shareBack;
    }

    public String getLogNetEleBack() {
        return logNetEleBack;
    }

    public void setLogNetEleBack(String logNetEleBack) {
        this.logNetEleBack = logNetEleBack;
    }

    public String getInvestCatNameBack() {
        return investCatNameBack;
    }

    public void setInvestCatNameBack(String investCatNameBack) {
        this.investCatNameBack = investCatNameBack;
    }

    public String getOpeNameBack() {
        return opeNameBack;
    }

    public void setOpeNameBack(String opeNameBack) {
        this.opeNameBack = opeNameBack;
    }

    public String getConstructStatusBack() {
        return constructStatusBack;
    }

    public void setConstructStatusBack(String constructStatusBack) {
        this.constructStatusBack = constructStatusBack;
    }

    public String getLneNameBack() {
        return lneNameBack;
    }

    public void setLneNameBack(String lneNameBack) {
        this.lneNameBack = lneNameBack;
    }

    public void recoverParameter(AmsAssetsAddressVDTO data) {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                fieldName = fieldName.substring(0, fieldName.length() - 4);
                field.setAccessible(true);
                ReflectionUtil.setProperty(data,  fieldName, field.get(this));
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
    }

    public static void main(String[] args) throws Throwable {
        Class cls = SearchParameterBackDTO.class;
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("<input type=\"hidden\" name=\"" + field.getName() + "\" id=\"" + field.getName() + "\">");
        }
    }
}