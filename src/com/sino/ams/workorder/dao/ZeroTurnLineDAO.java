package com.sino.ams.workorder.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newSite.model.EamAddressAddLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.ZeroTurnLineModel;
import com.sino.ams.workorder.model.ZeroTurnModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;

public class ZeroTurnLineDAO extends AMSBaseDAO {

	public ZeroTurnLineDAO(BaseUserDTO userAccount, DTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO dtoParameter) {
		ZeroTurnLineDTO dtoPara =(ZeroTurnLineDTO)dtoParameter;
		sqlProducer = new ZeroTurnLineModel((SfUserDTO)userAccount, dtoPara);	
	}
	
	public void updateLineData(){
		 try {
    		 ZeroTurnLineModel model = (ZeroTurnLineModel) sqlProducer;
    		 SQLModel updateModel = model.updateHeader();
			 DBOperator.updateRecord(updateModel, conn);
		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
