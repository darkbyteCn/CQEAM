package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 过程属性 SfProcedure</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfProcedureDTO extends CheckBoxDTO{

	private int projectId = 0;
	private int procedureId = 0;
	private String procedureName = "";
	private int trayType = 0;
	private int duration = 0;
	private String createdBy = "";
	private String creationDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdateDate = "";
	private String description = "";
	private String memo = "";
	private int mainProcedure = 0;
    private String defaultUrl = "";

    public SfProcedureDTO() {
		super();
	}

	/**
	 * 功能：设置过程属性属性 工程 ID
	 * @param projectId String
	 */
	public void setProjectId(int projectId){
		this.projectId = projectId;
	}

	/**
	 * 功能：设置过程属性属性 过程 ID
	 * @param procedureId String
	 */
	public void setProcedureId(int procedureId){
		this.procedureId = procedureId;
	}

	/**
	 * 功能：设置过程属性属性 过程名称
	 * @param procedureName String
	 */
	public void setProcedureName(String procedureName){
		this.procedureName = procedureName;
	}

	/**
	 * 功能：设置过程属性属性 工作栏类型
	 * @param trayType String
	 */
	public void setTrayType(int trayType){
		this.trayType = trayType;
	}

	/**
	 * 功能：设置过程属性属性 过程总耗
	 * @param duration String
	 */
	public void setDuration(int duration){
		this.duration = duration;
	}

	/**
	 * 功能：设置过程属性属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置过程属性属性 修改人
	 * @param lastUpdatedBy String
	 */
	public void setLastUpdatedBy(String lastUpdatedBy){
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * 功能：设置过程属性属性 过程描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置过程属性属性 备注
	 * @param memo String
	 */
	public void setMemo(String memo){
		this.memo = memo;
	}

	/**
	 * 功能：设置过程属性属性 是否主过程, 1:是 0:否
	 * @param mainProcedure String
	 */
	public void setMainProcedure(int mainProcedure){
		this.mainProcedure = mainProcedure;
	}

    /**
     * 功能：设置过程属性属性 是否主过程, 1:是 0:否
     * @param defaultUrl String
     */
    public void setDefaultUrl(String defaultUrl){
        this.defaultUrl = defaultUrl;
    }

	/**
	 * 功能：获取过程属性属性 工程 ID
	 * @return String
	 */
	public int getProjectId() {
		return this.projectId;
	}

	/**
	 * 功能：获取过程属性属性 过程 ID
	 * @return String
	 */
	public int getProcedureId() {
		return this.procedureId;
	}

	/**
	 * 功能：获取过程属性属性 过程名称
	 * @return String
	 */
	public String getProcedureName() {
		return this.procedureName;
	}

	/**
	 * 功能：获取过程属性属性 工作栏类型
	 * @return String
	 */
	public int getTrayType() {
		return this.trayType;
	}

	/**
	 * 功能：获取过程属性属性 过程总耗
	 * @return String
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * 功能：获取过程属性属性 创建人
	 * @return String
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}


	/**
	 * 功能：获取过程属性属性 修改人
	 * @return String
	 */
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	/**
	 * 功能：获取过程属性属性 过程描述
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 功能：获取过程属性属性 备注
	 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 功能：获取过程属性属性 是否主过程, 1:是 0:否
	 * @return String
	 */
	public int getMainProcedure() {
		return this.mainProcedure;
	}

    /**
     * 功能：获取过程属性属性 是否主过程, 1:是 0:否
     * @return defaultUrl String
     */
    public String getDefaultUrl() {
        return this.defaultUrl;
    }

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}