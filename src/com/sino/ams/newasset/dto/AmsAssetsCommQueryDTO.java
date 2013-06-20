package com.sino.ams.newasset.dto;

import com.sino.base.dto.CalendarUtililyDTO;

/**
 * <p>Title: 资产综合查询设置 AmsAssetsCommQuery</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCommQueryDTO extends CalendarUtililyDTO {

    private String userId = "";
    private String fieldName = "";
    private String fieldDesc = "";
    private String fieldUsage = "";
    private int sortNo;

    public AmsAssetsCommQueryDTO() {
        super();
    }

    /**
     * 功能：设置资产综合查询设置属性 用户ID
     * @param userId String
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置资产综合查询设置属性 字段名称
     * @param fieldName String
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 功能：设置资产综合查询设置属性 字段用途
     * @param fieldUsage String
     */
    public void setFieldUsage(String fieldUsage) {
        this.fieldUsage = fieldUsage;
    }

    /**
     * 功能：设置资产综合查询设置属性 排序号
     * @param sortNo String
     */
    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    /**
     * 功能：获取资产综合查询设置属性 用户ID
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取资产综合查询设置属性 字段名称
     * @return String
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * 功能：获取资产综合查询设置属性 字段用途
     * @return String
     */
    public String getFieldUsage() {
        return this.fieldUsage;
    }

    /**
     * 功能：获取资产综合查询设置属性 排序号
     * @return String
     */
    public int getSortNo() {
        return this.sortNo;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }
}
