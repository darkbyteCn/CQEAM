package com.sino.ams.newasset.scrap.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;

public class TransDTO extends AMSFlowDTO implements DTO {
	AmsAssetsTransHeaderDTO headerDTO = null;
	DTOSet lines = null;
	public String act = "";

	public AmsAssetsTransHeaderDTO getHeaderDTO() {
		return headerDTO;
	}

	public void setHeaderDTO(AmsAssetsTransHeaderDTO headerDTO) {
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
