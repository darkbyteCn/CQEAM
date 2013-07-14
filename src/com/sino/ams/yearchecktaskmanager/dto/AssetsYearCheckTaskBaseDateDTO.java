package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

/**
 * 
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskBaseDateDTO extends AMSBaseDTO {
	
	private String baseDateId="";
	private String baseDateYear="";//��׼���������
	private String chkYearTaskOrderNumber="";//ȫ���̵�������
	private String baseDateType = "";//��׼�����ͣ�1 ��ʾʡ��˾���Ļ�׼�շ�Χ 2��ʾ ���ж��Ļ�׼����
	private String checkBaseDateFrom="" ;//ʡ��˾�����̵��׼�յĿ�ʼ���� ֻ�е�����Ϊ1ʱ����ֵ
	private String checkBaseDateEnd="" ;//ʡ��˾�����̵��׼�յĽ������� ֻ�е�����Ϊ1ʱ����ֵ
	private String checkTaskDateFrom="";//�̵����񹤵��Ŀ�ʼ����
	private String checkTaskDateEnd="";//�̵����񹤵��Ľ�������
	private String checkBaseDateCity="";//�������ж����̵��׼����
	private String enabled="";
	
	private String softWareMethod = "";//������·���ʽCODE
	private String softWareMethodName = "";//������·���ʽNAME
	private String clientMethod = "";//�ͻ����·���ʽ
	private String clientMethodName = "";//�ͻ����·���ʽName
	private String pipeLineMethod = "";//�ܵ��·���ʽ
	private String pipeLineMethodName = "";//�ܵ��·���ʽ
	
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
