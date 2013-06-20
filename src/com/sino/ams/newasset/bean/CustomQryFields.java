package com.sino.ams.newasset.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CustomQryFields {
    private static Map fieldMap = null;

    static {
        fieldMap = new HashMap();
        fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
        fieldMap.put("ASSETS_LOCATION", "资产地点");
        fieldMap.put("ASSETS_STATUS", "资产状态");
        fieldMap.put("ASSET_ID", "资产ID");
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
        fieldMap.put("COMPANY", "公司名称");
        fieldMap.put("COMPANY_CODE", "公司代码");
        fieldMap.put("COST", "原值");
        fieldMap.put("COUNTY_NAME", "区县名称");
        fieldMap.put("CURRENT_UNITS", "数量");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
        fieldMap.put("DEPRECIATION_ACCOUNT", "折旧账户");
        fieldMap.put("DEPRN_COST", "资产净值");
        fieldMap.put("DEPT_CODE", "部门代码");
        fieldMap.put("DEPT_NAME", "部门名称");
        fieldMap.put("FA_CATEGORY1", "一级分类");
        fieldMap.put("FA_CATEGORY2", "二级分类");
        fieldMap.put("FA_CATEGORY_CODE", "资产类别代码");
        fieldMap.put("ITEM_CATEGORY_NAME", "设备分类");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "设备型号");
        fieldMap.put("ITEM_STATUS", "设备状态");
        fieldMap.put("LIFE_IN_YEARS", "服务年限");
        fieldMap.put("MAINTAIN_USER", " 维护人员");
        fieldMap.put("MODEL_NUMBER", "资产型号");
        fieldMap.put("PROJECT_NAME", "所属工程");
        fieldMap.put("RESPONSIBILITY_USER", "责任人");
        fieldMap.put("SEGMENT1", "类别1代码");
        fieldMap.put("SEGMENT2", "类别2代码");
        fieldMap.put("UNIT_OF_MEASURE", "计量单位");
        fieldMap.put("VENDOR_BARCODE", "厂商条码");
        fieldMap.put("VENDOR_NAME", "生产厂家");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
        fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
    }

    /**
     * 功能：获取字段名称与含义的映射关系
     * @return Map
     */
    public static Map getFieldsMap() {
        return fieldMap;
    }
}
