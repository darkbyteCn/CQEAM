package com.sino.ams.system.assetcatelogMaintenanceLNE.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.assetcatelogMaintenanceLNE.dto.AssetcatelogMLneDTO;
import com.sino.ams.system.assetcatelogMaintenanceLNE.model.AssetcatelogMlneModel;
import com.sino.ams.system.place.dto.PlaceInfoDTO;
import com.sino.ams.system.place.model.PlaceInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

public class AssetcatelogMlneDAO extends AMSBaseDAO {

	public AssetcatelogMlneDAO(BaseUserDTO userAccount, AssetcatelogMLneDTO dtoParameter, Connection conn) {
		super(userAccount,dtoParameter, conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AssetcatelogMLneDTO dto = (AssetcatelogMLneDTO)dtoParameter;
		sqlProducer = new AssetcatelogMlneModel(user, dto);
		
	}
}
