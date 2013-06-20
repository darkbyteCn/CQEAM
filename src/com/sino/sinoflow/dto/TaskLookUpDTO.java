package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

public class TaskLookUpDTO extends CheckBoxDTO {
	
	private String companyName = "";    //公司
	private String procedureName = "";   //流程名称
    private String taskName="";         //任务节点名称
    private String processTime = "";  //平均审批时间
	private String startDate = "";  //开始时间
	private String endDate = "";  //结束时间
    private String companyNameOpt = ""; //合同大类下拉框
    private String procedureNameOpt = ""; //合同大类下拉框
    private String taskNameOpt = "";    //任务节点下拉框
    private float hoursPerDay = 8;      //一天工作时
    private String act = "";

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    public String getCompanyNameOpt() {
        return companyNameOpt;
    }

    public void setCompanyNameOpt(String companyNameOpt) {
        this.companyNameOpt = companyNameOpt;
    }

    public String getProcedureNameOpt() {

        return procedureNameOpt;
    }

    public void setProcedureNameOpt(String procedureNameOpt) {

        this.procedureNameOpt = procedureNameOpt;
    }

    public String getTaskNameOpt() {

        return taskNameOpt;
    }

    public void setTaskNameOpt(String taskNameOpt) {

        this.taskNameOpt = taskNameOpt;
    }

    public float getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(float hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
