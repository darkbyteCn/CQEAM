package com.sino.ams.apd.dao;

import java.sql.Connection;

import com.sino.ams.apd.dto.EtsItemCheckDTO;
import com.sino.ams.apd.model.EtsItemCheckModel;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.ZeroTurnModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

public class EtsItemCheckDAO extends AMSBaseDAO{

	public EtsItemCheckDAO(SfUserDTO userAccount, EtsItemCheckDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		// TODO Auto-generated method stub
		EtsItemCheckDTO dtoPara = (EtsItemCheckDTO) dtoParameter;
		sqlProducer = new EtsItemCheckModel((SfUserDTO) userAccount,dtoPara);
	}
	
//	   public DTOSet getLineData() throws QueryException {
//		   EtsItemCheckDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
//	        ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
//	        SQLModel sqlModel = model.getLineDataModel(dto);
//	        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//	        simp.setDTOClassName(ZeroTurnLineDTO.class.getName());
//	        simp.executeQuery();
//	        return simp.getDTOSet();
//	    }

}
