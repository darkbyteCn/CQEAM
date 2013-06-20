package com.sino.ams.apd.model;

import com.sino.ams.apd.dto.EtsItemCheckDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class EtsItemCheckModel extends BaseSQLProducer {
	
	private SfUserDTO user = null;

	public EtsItemCheckModel(SfUserDTO userAccount, EtsItemCheckDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = userAccount;
		// TODO Auto-generated constructor stub
	}

}
