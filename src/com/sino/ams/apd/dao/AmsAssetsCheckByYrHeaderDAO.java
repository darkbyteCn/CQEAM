package com.sino.ams.apd.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.apd.dto.AmsAssetsCheckByYrHeaderDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO;
import com.sino.ams.apd.dto.EtsItemCheckDTO;
import com.sino.ams.apd.model.AmsAssetsCheckByYrModel;
import com.sino.ams.apd.model.AmsAssetsCheckOrderModel;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroturnLineBursurDTO;
import com.sino.ams.workorder.model.ZeroTurnBursurModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

public class AmsAssetsCheckByYrHeaderDAO extends AMSBaseDAO {

	public AmsAssetsCheckByYrHeaderDAO(SfUserDTO userAccount,
			AmsAssetsCheckByYrHeaderDTO dtoParameter, Connection conn) {
		    super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckByYrHeaderDTO dtoPara = (AmsAssetsCheckByYrHeaderDTO) dtoParameter;
		sqlProducer = new AmsAssetsCheckByYrModel((SfUserDTO) userAccount,dtoPara);
	}
	
    public void sendWorkTask(DTOSet deptLines){
        boolean autoCommit = true;
    	try {
    		autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
    	    saveHeader();
    	    saveLine(deptLines);
    	 } catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CalendarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            try {
//                if (!operateResult) {
//                    conn.rollback();
//                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
//                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
//                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("盘点任务下发");
//                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
    	
    	
    }
    
    public void saveHeader() throws CalendarException, DataHandleException{
    	AmsAssetsCheckByYrHeaderDTO dtoPara = (AmsAssetsCheckByYrHeaderDTO) dtoParameter;
    	String transId = dtoPara.getTransId();
        if (StrUtil.isEmpty(transId)) {
            SeqProducer seq = new SeqProducer(conn);
            transId = StrUtil.nullToString(seq.getGUID());
            dtoPara.setTransId(transId);
        }
        dtoPara.setTransStatus("DO_SEND");
        dtoPara.setTransStatusValue("已下发");
        
        AmsAssetsCheckByYrModel model = (AmsAssetsCheckByYrModel) sqlProducer;
        SQLModel sqlModel = new SQLModel();
        sqlModel=model.getWorkHeaderInsert(dtoPara);
	    DBOperator.updateRecord(sqlModel, conn);
    }
    
    public void saveLine(DTOSet deptLines) throws DataHandleException{
    	AmsAssetsCheckByYrHeaderDTO dtoPara = (AmsAssetsCheckByYrHeaderDTO) dtoParameter;
    	String transId = dtoPara.getTransId();
	   	if(deptLines == null){
	         return;
		}else {
	        for(int i = 0; i < deptLines.getSize(); i++) {
	        	AmsAssetsCheckByYrLineDTO  line = (AmsAssetsCheckByYrLineDTO)deptLines.getDTO(i);
	        	line.setTransId(transId);
	        	AmsAssetsCheckByYrModel model = (AmsAssetsCheckByYrModel) sqlProducer;
	            SQLModel sqlModel = new SQLModel();
	            sqlModel=model.getWorkLineInsert(line);
			    DBOperator.updateRecord(sqlModel, conn);
	        }
		}
    }
    
    /**
     * 获取表单行信息
     */
    public DTOSet getLineData() throws QueryException {
    	AmsAssetsCheckByYrHeaderDTO dto = (AmsAssetsCheckByYrHeaderDTO) dtoParameter;
    	AmsAssetsCheckByYrModel model = (AmsAssetsCheckByYrModel) sqlProducer;
        SQLModel sqlModel = model.getLineModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(AmsAssetsCheckByYrLineDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }
    
    

}
