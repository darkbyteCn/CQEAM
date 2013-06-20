package com.sino.ams.workorderDefine.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;
import com.sino.ams.workorderDefine.model.WorkorderDefineModel;
import com.sino.framework.dto.BaseUserDTO;

public class WorkorderDefineDAO extends AMSBaseDAO {

	public WorkorderDefineDAO(SfUserDTO userAccount,
			WorkorderDefineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		WorkorderDefineDTO dtoPara = (WorkorderDefineDTO) dtoParameter;
		sqlProducer = new WorkorderDefineModel((SfUserDTO) userAccount, dtoPara);
	}

	/**
     * 功能：插入字典分类表(EAM)表“ETS_WORKORDER_DEFINE”数据。
     * @return
     */
	public void createData() {
		saveData(DBActionConstant.INSERT);
	}

	/**
     * 功能：更新字典分类表(EAM)表“ETS_WORKORDER_DEFINE”数据。
     * @return 
     */
	public void updateData() {
		saveData(DBActionConstant.UPDATE);
	}

	/**
     * 功能：保存字典分类表(EAM)表“ETS_WORKORDER_DEFINE”数据。
     * @param dbAction String
     * @return boolean
     */
	public boolean saveData(String dbAction) {
		boolean operateResult = false;
		try {
				if (dbAction.equals(DBActionConstant.INSERT)) {
					if (!validateDate()) { 
						super.createData();
					} else {
						prodMessage(AssetsMessageKeys.WORKORDER_DEFINE_FAILURE);
					}					
				} else {
					super.updateData();
				}
				prodMessage(AssetsMessageKeys.WORKORDER_DEFINE_SUCCESS);
				
		} catch (DataHandleException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (SQLModelException e) {
			e.printStackTrace();
		}
		return operateResult;
	}
	
	public boolean validateDate() throws QueryException, SQLModelException {
		WorkorderDefineDTO dto = (WorkorderDefineDTO) dtoParameter;
		WorkorderDefineModel model = (WorkorderDefineModel) sqlProducer;
		SQLModel sqlModel = model.getValidateDateModel();
		SimpleQuery simp = new SimpleQuery(sqlModel,conn);
        simp.executeQuery();
        return simp.hasResult();
	}
	
	/**
	 * 创建巡检自定工单
	 * @param defineDTO
	 * @return
	 * @throws DataHandleException 
	 * @throws QueryException 
	 * @throws ContainerException 
	 */
	public boolean createWorkorder(WorkorderDefineDTO defineDTO,Connection conn) throws DataHandleException, QueryException, ContainerException {
		boolean operatorResult = true;
		SQLModel sqlModel = new SQLModel();
		List sqlModList = new ArrayList();
		List workorderSqlModList = new ArrayList();
		String workorderBatchNo = WorkOrderUtil.getWorkorderBatchNo(conn);
		Long workorderNo = Long.valueOf(WorkOrderUtil.getWorkorderNo(workorderBatchNo, conn));
		WorkorderDefineModel model = (WorkorderDefineModel) sqlProducer;
		SQLModel workorderObjectNoModel = model.getCountWorkorderModel(defineDTO);
		SimpleQuery simp = new SimpleQuery(workorderObjectNoModel,conn);
        simp.executeQuery();
        RowSet rowSet = simp.getSearchResult();
        Row row = null;
        
		
		//下发工单巡检批表
		sqlModList.add(model.getCreateWorkorderBatchModel(workorderBatchNo,defineDTO));

		//下发工单
		for (int i = 0; i < rowSet.getSize(); i++) {
            row = rowSet.getRow(i);
            String workorderObjectNo = row.getValue("WORKORDER_OBJECT_NO").toString();
            workorderNo ++;     
            workorderSqlModList.add(model.getCreateWorkorderDataModel(workorderBatchNo, StrUtil.nullToString(workorderNo), workorderObjectNo, defineDTO)); 
		}
		
		operatorResult = DBOperator.updateBatchRecords(workorderSqlModList, conn);
		
		//修改下次执行时间
		sqlModList.add(model.getUpdateWorkorderDefine(defineDTO.getWorkorderDefineId()));
		
		if (operatorResult) {
			operatorResult = DBOperator.updateBatchRecords(sqlModList, conn);
			Logger.logError("本次工单下发数量:"+rowSet.getSize());
		}
		
		return operatorResult;
	}

    public boolean  validateWorkorder(WorkorderDefineDTO defineDTO) throws QueryException, ContainerException {
        boolean isValidate=false;
        WorkorderDefineModel model = (WorkorderDefineModel) sqlProducer;
        SQLModel sqlModel=new SQLModel();
        sqlModel    =model.getCountDefineModel(defineDTO);
        SimpleQuery sq=new SimpleQuery(sqlModel,conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            int count=Integer.valueOf(sq.getSearchResult().getRow(0).getStrValue("ROW_COUNT"));
            isValidate=count<400;
        }

        return  isValidate;
    }

}
