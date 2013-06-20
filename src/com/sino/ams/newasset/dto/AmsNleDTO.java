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
public class AmsNleDTO extends CommonRecordDTO {

	private String amsLneId = "";
	private String lneCode = "";
	private String lneName = "";
	public String getAmsLneId() {
		return amsLneId;
	}

	public String getLneCode() {
		return lneCode;
	}

	public String getLneName() {
		return lneName;
	}

	public void setAmsLneId(String amsLneId) {
		this.amsLneId = amsLneId;
	}

	public void setLneCode(String lneCode) {
		this.lneCode = lneCode;
	}

	public void setLneName(String lneName) {
		this.lneName = lneName;
	}
}
