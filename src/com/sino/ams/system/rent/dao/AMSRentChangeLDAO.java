package com.sino.ams.system.rent.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.rent.model.AMSRentChangeLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-10
 * Time: 18:26:38
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeLDAO extends AMSBaseDAO {
    public AMSRentChangeLDAO(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsTransLineDTO dtoPara = (AmsAssetsTransLineDTO) dtoParameter;
		sqlProducer = new AMSRentChangeLModel((SfUserDTO) userAccount,dtoPara);
	}
}
