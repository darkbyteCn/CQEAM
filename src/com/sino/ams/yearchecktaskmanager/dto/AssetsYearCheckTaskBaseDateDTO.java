package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

/**
 * 
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskBaseDateDTO extends AMSBaseDTO {
	
	private String baseDateId="";
	private String baseDateYear="";//基准日所属年度
	private String chkYearTaskOrderNumber="";//全年盘点任务编号
	private String baseDateType = "";//基准日类型，1 表示省公司定的基准日范围 2表示 地市定的基准日期
	private String checkBaseDateFrom="" ;//省公司定的盘点基准日的开始日期 只有当类型为1时才有值
	private String checkBaseDateEnd="" ;//省公司定的盘点基准日的结束日期 只有当类型为1时才有值
	private String checkTaskDateFrom="";//盘点任务工单的开始日期
	private String checkTaskDateEnd="";//盘点任务工单的结束日期
	private String checkBaseDateCity="";//各个地市定的盘点基准日期
	private String enabled="";
	
	private String softWareMethod = "";//软件类下发方式CODE
	private String softWareMethodName = "";//软件类下发方式NAME
	private String clientMethod = "";//客户端下发方式
	private String clientMethodName = "";//客户端下发方式Name
	private String pipeLineMethod = "";//管道下发方式
	private String pipeLineMethodName = "";//管道下发方式
	
	public String toString(){
		return "[baseDateId="+this.baseDateId+",baseDateType="+this.baseDateType+
		",checkBaseDateFrom="+this.checkBaseDateFrom+",checkBaseDateEnd="+this.checkBaseDateEnd+
		",checkTaskDateFrom="+this.checkTaskDateFrom+",checkTaskDateEnd="+this.checkTaskDateEnd+
		",checkBaseDateCity="+this.checkBaseDateCity+",softWareMethod="+softWareMethod+"]";
		      
	}
	
	public String getSoftWareMethodName() {
		return softWareMethodName;
	}

	public void setSoftWareMethodName(String softWareMethodName) {
		this.softWareMethodName = softWareMethodName;
	}

	public String getClientMethodName() {
		return clientMethodName;
	}

	public void setClientMethodName(String clientMethodName) {
		this.clientMethodName = clientMethodName;
	}

	public String getPipeLineMethodName() {
		return pipeLineMethodName;
	}

	public void setPipeLineMethodName(String pipeLineMethodName) {
		this.pipeLineMethodName = pipeLineMethodName;
	}

	public String getSoftWareMethod() {
		return softWareMethod;
	}

	public void setSoftWareMethod(String softWareMethod) {
		this.softWareMethod = softWareMethod;
	}

	public String getClientMethod() {
		return clientMethod;
	}

	public void setClientMethod(String clientMethod) {
		this.clientMethod = clientMethod;
	}

	public String getPipeLineMethod() {
		return pipeLineMethod;
	}

	public void setPipeLineMethod(String pipeLineMethod) {
		this.pipeLineMethod = pipeLineMethod;
	}

	public String getChkYearTaskOrderNumber() {
		return chkYearTaskOrderNumber;
	}


	public void setChkYearTaskOrderNumber(String chkYearTaskOrderNumber) {
		this.chkYearTaskOrderNumber = chkYearTaskOrderNumber;
	}


	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getBaseDateId() {
		return baseDateId;
	}

	public void setBaseDateId(String baseDateId) {
		this.baseDateId = baseDateId;
	}

	public String getBaseDateYear() {
		return baseDateYear;
	}

	public void setBaseDateYear(String baseDateYear) {
		this.baseDateYear = baseDateYear;
	}
	public String getBaseDateType() {
		return baseDateType;
	}
	public void setBaseDateType(String baseDateType) {
		this.baseDateType = baseDateType;
	}
	public String getCheckBaseDateFrom() {
		return checkBaseDateFrom;
	}
	public void setCheckBaseDateFrom(String checkBaseDateFrom) {
		this.checkBaseDateFrom = checkBaseDateFrom;
	}
	public String getCheckBaseDateEnd() {
		return checkBaseDateEnd;
	}
	public void setCheckBaseDateEnd(String checkBaseDateEnd) {
		this.checkBaseDateEnd = checkBaseDateEnd;
	}
	public String getCheckTaskDateFrom() {
		return checkTaskDateFrom;
	}
	public void setCheckTaskDateFrom(String checkTaskDateFrom) {
		this.checkTaskDateFrom = checkTaskDateFrom;
	}
	public String getCheckTaskDateEnd() {
		return checkTaskDateEnd;
	}
	public void setCheckTaskDateEnd(String checkTaskDateEnd) {
		this.checkTaskDateEnd = checkTaskDateEnd;
	}
	public String getCheckBaseDateCity() {
		return checkBaseDateCity;
	}
	public void setCheckBaseDateCity(String checkBaseDateCity) {
		this.checkBaseDateCity = checkBaseDateCity;
	}
	
	
}
