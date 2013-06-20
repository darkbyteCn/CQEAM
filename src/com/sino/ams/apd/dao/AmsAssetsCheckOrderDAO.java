package com.sino.ams.apd.dao;

import java.sql.Connection;

import com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO;
import com.sino.ams.apd.dto.EtsItemCheckDTO;
import com.sino.ams.apd.model.AmsAssetsCheckOrderModel;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

public class AmsAssetsCheckOrderDAO extends AMSBaseDAO {

	public AmsAssetsCheckOrderDAO(SfUserDTO userAccount, AmsAssetsCheckOrderDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AmsAssetsCheckOrderDTO dtoPara = (AmsAssetsCheckOrderDTO) dtoParameter;
		sqlProducer = new AmsAssetsCheckOrderModel((SfUserDTO) userAccount,dtoPara);
	}
	
	//根据用户信息获取结果集
	public AmsAssetsCheckOrderDTO getTraskUserModel(SfUserDTO user,Connection conn) throws QueryException {
		   DTOSet dtoSet=new DTOSet();
		   AmsAssetsCheckOrderDTO lineDTO=new AmsAssetsCheckOrderDTO();
		   AmsAssetsCheckOrderModel modelProducer = (AmsAssetsCheckOrderModel) sqlProducer;
		   SQLModel sqlModel = modelProducer.getCheckOrderModel(user);
	       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		   simp.setDTOClassName(AmsAssetsCheckOrderDTO.class.getName());
		   simp.executeQuery();
		   dtoSet=simp.getDTOSet();
		   if(dtoSet.getSize()>0){
			   lineDTO=(AmsAssetsCheckOrderDTO) dtoSet.getDTO(0);
		   }
		return lineDTO;
	}
	
	 
    /**
     * 获取表单行信息
     */
    public DTOSet getLineData() throws QueryException {
    	AmsAssetsCheckOrderDTO dto = (AmsAssetsCheckOrderDTO) dtoParameter;
    	AmsAssetsCheckOrderModel model = (AmsAssetsCheckOrderModel) sqlProducer;
        SQLModel sqlModel = model.getLineModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(EtsItemCheckDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }

}
