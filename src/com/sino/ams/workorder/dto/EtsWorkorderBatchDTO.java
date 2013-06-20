package com.sino.ams.workorder.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 工单批表(EAM) EtsWorkorderBatch</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsWorkorderBatchDTO extends CheckBoxDTO{

	private String systemid = "";
	private String workorderBatch = "";
	private String workorderBatchName = "";
	private String remark = "";
	private String prjId;
	private String workorderType = "";
	private int status;
	private int archflag;
	private String actid = "";
	private String caseid = "";
	private int distributeGroupId ;
	private Timestamp creationDate = null;
	private int createdBy;
    private int lastUpdateBy;
    private Timestamp lastUpdateDate = null;

    private String prjName ="";
    private String workorderTypeDesc="";
    private String distributeGroupName="";
    private String createUser="";


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getWorkorderTypeDesc() {
        return workorderTypeDesc;
    }

    public void setWorkorderTypeDesc(String workorderTypeDesc) {
        this.workorderTypeDesc = workorderTypeDesc;
    }

    public String getDistributeGroupName() {
        return distributeGroupName;
    }

    public void setDistributeGroupName(String distributeGroupName) {
        this.distributeGroupName = distributeGroupName;
    }

    /**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param systemid String
	 */
	public void setSystemid(String systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param workorderBatch String
	 */
	public void setWorkorderBatch(String workorderBatch){
		this.workorderBatch = workorderBatch;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param workorderBatchName String
	 */
	public void setWorkorderBatchName(String workorderBatchName){
		this.workorderBatchName = workorderBatchName;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param prjId String
	 */
	public void setPrjId(String prjId){
		this.prjId = prjId;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param workorderType String
	 */
	public void setWorkorderType(String workorderType){
		this.workorderType = workorderType;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param status String
	 */
	public void setStatus(int status){
		this.status = status;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param archflag String
	 */
	public void setArchflag(int archflag){
		this.archflag = archflag;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param actid String
	 */
	public void setActid(String actid){
		this.actid = actid;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param caseid String
	 */
	public void setCaseid(String caseid){
		this.caseid = caseid;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param distributeGroupId String
	 */
	public void setDistributeGroupId(int distributeGroupId){
		this.distributeGroupId = distributeGroupId;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param lastUpdateDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置工单批表(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getSystemid(){
		return this.systemid;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getWorkorderBatch(){
		return this.workorderBatch;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getWorkorderBatchName(){
		return this.workorderBatchName;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getRemark(){
		return this.remark;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getPrjId(){
		return this.prjId;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getWorkorderType(){
		return this.workorderType;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getStatus(){
		return this.status;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getArchflag(){
		return this.archflag;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getActid(){
		return this.actid;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public String getCaseid(){
		return this.caseid;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getDistributeGroupId(){
		return this.distributeGroupId;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取工单批表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}