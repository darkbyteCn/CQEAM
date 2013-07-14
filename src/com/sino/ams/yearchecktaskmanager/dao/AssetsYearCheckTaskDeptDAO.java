package com.sino.ams.yearchecktaskmanager.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckTaskCityModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskOrderGeneretor;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsYearCheckTaskDeptDAO extends AMSProcedureBaseDAO{
	
	private AssetsYearCheckTaskCityModel myModel;
	public AssetsYearCheckTaskDeptDAO(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		myModel = new  AssetsYearCheckTaskCityModel((SfUserDTO) userAccount,dtoParameter);
	}

	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsYearCheckTaskHeaderDTO dtoPara = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckTaskCityModel((SfUserDTO) userAccount,dtoPara);
	}
	
	public boolean createHeader(AssetsYearCheckTaskHeaderDTO headerDto) throws DataHandleException{
		//AssetsYearCheckTaskCityModel model = (AssetsYearCheckTaskCityModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		headerDTO.setHeaderId(CommonUtil.getUUID());
		setDTOParameter(headerDTO);
		SQLModel headerMdoel = myModel.getcreateTaskHeaderModel(headerDto);
		return DBOperator.updateRecord(headerMdoel, conn);
	}
	public boolean createOrderLines(DTOSet lineSet)throws Exception{
		boolean result = true;
		//AssetsYearCheckTaskCityModel model = (AssetsYearCheckTaskCityModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		AssetsCheckTaskOrderGeneretor orderGeneretor = new AssetsCheckTaskOrderGeneretor(conn,userAccount.getCompanyCode(),AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);
		if(this.createHeader(headerDTO)){
			for (int i = 0; i < lineSet.getSize(); i++){
	         	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
	         	lineDto.setTaskOrderId(CommonUtil.getUUID());
	         	lineDto.setHeaderId(headerDTO.getHeaderId());
	         	lineDto.setOrderNumber(orderGeneretor.getOrderNum());
	         	lineDto.setOrderStatus(AssetsCheckTaskConstant.DO_CREATE);
	         	lineDto.setOrderLevel("4");
	         	lineDto.setIsLastLevel("Y");
	         	lineDto.setImplementRoleName("系统用户");
	         	SQLModel headerModel = myModel.getCreateTaksLineModel(lineDto);
				boolean singleResult = DBOperator.updateRecord(headerModel, conn);
				if(singleResult){
					continue;
				}else{
					result = false;
					break;
				}
	         }
		}else{
			result = false;
		}
		return result;
	}
	public boolean createOrder(DTOSet lineSet) {
		boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            
            createOrderLines(lineSet); //创建下发任务
            
            operateResult= true;
        }catch (Exception ex) {
            ex.printStackTrace();
            operateResult = false;
        }  finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
	}
}
