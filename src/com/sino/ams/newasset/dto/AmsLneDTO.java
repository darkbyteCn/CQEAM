package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: CMCCEAM</p>
 * <p>Description: 中国移动集团资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AmsLneDTO extends CommonRecordDTO {
	private String amsLneId = "";
	private String netCategory1 = "";
	private String netCategory2 = "";
	private String netUnitCode = "";
	private String logNetEle = "";
	private String engAb = "";

	public String getAmsLneId() {
		return amsLneId;
	}

	public String getEngAb() {
		return engAb;
	}

	public String getLogNetEle() {
		return logNetEle;
	}

	public String getNetCategory1() {
		return netCategory1;
	}

	public String getNetCategory2() {
		return netCategory2;
	}

	public String getNetUnitCode() {
		return netUnitCode;
	}

	public void setAmsLneId(String amsLneId) {
		this.amsLneId = amsLneId;
	}

	public void setEngAb(String engAb) {
		this.engAb = engAb;
	}

	public void setLogNetEle(String logNetEle) {
		this.logNetEle = logNetEle;
	}

	public void setNetCategory1(String netCategory1) {
		this.netCategory1 = netCategory1;
	}

	public void setNetCategory2(String netCategory2) {
		this.netCategory2 = netCategory2;
	}

	public void setNetUnitCode(String netUnitCode) {
		this.netUnitCode = netUnitCode;
	}
}
