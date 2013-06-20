package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.model.AssetsDiscardLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

public class AssetsDiscardLineDAO extends AMSBaseDAO {

	public AssetsDiscardLineDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

    public void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter){
        sqlProducer = new AssetsDiscardLineModel(userAccount, dtoParameter);
    }
}
