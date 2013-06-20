package com.sino.ams.newasset.lose.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;

/**
 * 
 * @系统名称: 挂失
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LoseDTO extends AMSFlowDTO implements DTO{
	LoseHeaderDTO headerDTO = null;
	DTOSet lines = null;   
	public String act = "" ; 
	
	public LoseHeaderDTO getHeaderDTO() {
		return headerDTO;
	}

	public void setHeaderDTO(LoseHeaderDTO headerDTO) {
		this.headerDTO = headerDTO;
	}

	public DTOSet getLines() {
		return lines;
	}

	public void setLines(DTOSet lines) {
		this.lines = lines;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}
}
