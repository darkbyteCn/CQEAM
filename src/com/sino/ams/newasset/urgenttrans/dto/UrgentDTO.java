package com.sino.ams.newasset.urgenttrans.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;

/**
 * 
 * @系统名称: 紧急调拨单
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class UrgentDTO extends AMSFlowDTO implements DTO{
	UrgentHeaderDTO headerDTO = null;
	DTOSet lines = null;   
	String transId;
	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String act = "" ; 
	
	public UrgentHeaderDTO getHeaderDTO() {
		return headerDTO;
	}

	public void setHeaderDTO(UrgentHeaderDTO headerDTO) {
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
