package com.sino.ams.newasset.dao;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.freeflow.AmsAssetsFreeDTO;
import com.sino.ams.newasset.model.AmsAssetsShareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;
import com.sino.framework.dto.BaseUserDTO;


public class AmsAssetsShareDAO extends AMSBaseDAO {
    public AmsAssetsShareDAO(BaseUserDTO userAccount, AmsAssetsFreeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsFreeDTO ffDTO=(AmsAssetsFreeDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsShareModel((SfUserDTO)userAccount, ffDTO);
    }
    
    public String getAllShareStatus(String selectedValue) throws QueryException{
    	AmsAssetsShareModel modelProducer = (AmsAssetsShareModel) sqlProducer;
    	SQLModel sqlModel = modelProducer.getAllShareStatus(conn);
    	DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
		return optProducer.getOptionHtml(selectedValue, true);
    }
    
    public boolean getDistributeShareStatus(String shareStatus, DTOSet dtos) throws DataHandleException, SQLException {
        boolean operateResult = false;
        if (dtos != null && dtos.getSize() > 0) {
            operateResult = true;
            int dtoCount = dtos.getSize();
            AmsAssetsShareModel modelProducer = (AmsAssetsShareModel) sqlProducer;
            try{
//            	for (int i = 0; i < dtoCount; i++) {
//            		AmsAssetsFreeDTO dto = (AmsAssetsFreeDTO) dtos.getDTO(i);
//                	SQLModel sqlModel = modelProducer.getDataUpdateModel(dto);
//                	DBOperator.updateRecord(sqlModel, conn);
//                	conn.commit();
//                }
            	String params = "(";
            	for (int i = 0; i < dtoCount; i++) {
            		AmsAssetsFreeDTO dto = (AmsAssetsFreeDTO) dtos.getDTO(i);
            		params += "'" + dto.getBarcode() + "',";
            	}
            	params = params.substring(0, params.length()-1) + ")";
            	SQLModel sqlModel = modelProducer.getDataUpdateModel(shareStatus, params);
            	DBOperator.updateRecord(sqlModel, conn);
            	conn.commit();
            }catch(Exception e){
            	e.printStackTrace();
            	conn.rollback();
            }
            if(conn != null){
            	conn.close();
            }
        }
        return operateResult;
    }
}
