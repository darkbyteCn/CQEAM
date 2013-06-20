package com.sino.ams.net.equip.dto;

import com.sino.base.dto.CalendarUtililyDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-1-28
 * Time: 11:45:55
 * To change this template use File | Settings | File Templates.
 */
public class IntegratedDTO extends  CalendarUtililyDTO {


    private int userId;
	private String fieldName = "";
	private String fieldDesc = "";
	private String fieldUsage = "";
	private int sortNo;
	
    /**
     * 功能：设置资产综合查询设置属性 用户ID
     * @param userId String
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

	/**
	 * 功能：设置资产综合查询设置属性 字段名称
	 * @param fieldName String
	 */
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}

	/**
	 * 功能：设置资产综合查询设置属性 字段用途
	 * @param fieldUsage String
	 */
	public void setFieldUsage(String fieldUsage){
		this.fieldUsage = fieldUsage;
	}

	/**
	 * 功能：设置资产综合查询设置属性 排序号
	 * @param sortNo String
	 */
	public void setSortNo(int sortNo){
		this.sortNo = sortNo;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	/**
	 * 功能：获取资产综合查询设置属性 用户ID
	 * @return String
	 */
	public int getUserId() {
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
