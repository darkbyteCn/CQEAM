package com.sino.ams.synchronize.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AmsSynTmpDTO extends SinoBaseObject implements DTO{
	private String sourceStr = "";
	private String targetStr = "";

	public AmsSynTmpDTO() {
		super();
	}

	public String getTargetStr() {
		return targetStr;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public void setTargetStr(String targetStr) {
		this.targetStr = targetStr;
	}
}
