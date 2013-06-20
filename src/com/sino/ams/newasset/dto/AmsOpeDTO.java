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
public class AmsOpeDTO extends CommonRecordDTO {

	private String amsOpeId = "";
	private String opeCode = "";
	private String opeName = "";

	public String getAmsOpeId() {
		return amsOpeId;
	}

	public String getOpeCode() {
		return opeCode;
	}

	public String getOpeName() {
		return opeName;
	}

	public void setAmsOpeId(String amsOpeId) {
		this.amsOpeId = amsOpeId;
	}

	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}

	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}
}
