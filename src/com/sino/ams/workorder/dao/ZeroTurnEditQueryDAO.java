package com.sino.ams.workorder.dao;

import java.sql.Connection;

import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.dto.EamYbBorrowLogDTO;
import com.sino.ams.dzyh.model.BorrowApproveModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.ams.workorder.model.ZeroQueryDateModel;
import com.sino.ams.workorder.model.ZeroTurnEditQueryModel;
import com.sino.ams.workorder.model.ZeroTurnLineModel;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

public class ZeroTurnEditQueryDAO extends BaseDAO{

	private SfUserDTO user = null;
	public ZeroTurnEditQueryDAO(SfUserDTO userAccount, ZeroTurnLineDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		user = userAccount;
        initSQLProducer(userAccount, dtoParameter);
	}
	
	@Override
	protected void initSQLProducer(BaseUserDTO dto, DTO dtoParameter) {
		ZeroTurnLineDTO ffDTO=(ZeroTurnLineDTO) dtoParameter;
        super.sqlProducer = new ZeroTurnEditQueryModel((SfUserDTO)userAccount, ffDTO);
        
	}
	
	public RowSet getDate(String oneBarcode){
			SQLModel sqlModel =null;
		 	ZeroQueryDateModel model = new ZeroQueryDateModel();
	        try {
				sqlModel = model.getDate(oneBarcode);
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				return simpleQuery.getSearchResult();
			} catch (QueryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
	}
	
	public boolean saveDate(ZeroTurnLineDTO dtoParameter){
		boolean operateResult = false;
		try {
	 	ZeroQueryDateModel model = new ZeroQueryDateModel();
	 	SQLModel sqlModel;
		sqlModel = model.saveDate(dtoParameter);
		DBOperator.updateRecord(sqlModel, conn);
		operateResult = true;
		} catch (DataHandleException e) {
			e.printStackTrace();
		}catch (SQLModelException e) {
			e.printStackTrace();
		}
		return operateResult;
	}
	
	public boolean updateEtsIntemInfo(ZeroTurnLineDTO dtoParameter){
		boolean operateResult = false;
		try {
			ZeroQueryDateModel model = new ZeroQueryDateModel();
			SQLModel sqlModel;
			String barcode=dtoParameter.getBarcode();
			String status="PRE_ASSETS";
			String objectCode=dtoParameter.getObjectNo();
			sqlModel = model.updateEtsItemStatus(barcode, status,objectCode);
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException e) {
			e.printStackTrace();
		}
		return operateResult;
	}
	
	
}
