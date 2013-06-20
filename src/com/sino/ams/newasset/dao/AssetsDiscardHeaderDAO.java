package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.AssetsDiscardHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

public class AssetsDiscardHeaderDAO extends AMSBaseDAO {

	public AssetsDiscardHeaderDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

    public void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter){
        this.sqlProducer = new AssetsDiscardHeaderModel(userAccount, dtoParameter);
    }
}
