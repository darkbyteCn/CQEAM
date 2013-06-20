package com.sino.ams.newasset.urgenttrans.pda.dao;

import java.sql.Connection;

import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

public class UrgentOrderOutDownloadDAO extends UrgentOrderDownloadDAO {

	public UrgentOrderOutDownloadDAO(BaseUserDTO userAccount, DTO dtoParameter,
			Connection conn) { 
		super(userAccount, dtoParameter, conn); 
		super.setType( UrgentAppConstant.DOWNLOAD_TYPE_OUT );
	}
	
}
