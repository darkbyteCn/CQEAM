package com.sino.ams.system.manydimensions.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.ams.system.manydimensions.model.LneModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: 李轶
 * Date: 2009-6-16
 * Time: 17:32:55
 * Function:		网络层次属性维护
 */
public class LneDAO extends AMSBaseDAO {

	private LneModel lneModel = null;

    public LneDAO(SfUserDTO userAccount, AmsElementMatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        lneModel = new LneModel((SfUserDTO) userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    	AmsElementMatchDTO dtoPara = (AmsElementMatchDTO) dtoParameter;
        sqlProducer = new LneModel(userAccount, dtoPara);
    }
    
    /**
	 * 功能:检查逻辑网络元素编码属性是否存在
	 * @return boolean
	 */
	public boolean existObject(){
		boolean exist = false;
		try {
			SQLModel sqlModel = lneModel.getObjectEsistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	/**
	 * 功能:检查逻辑网络元素ID属性是否被使用
	 * @return boolean
	 */
	public boolean validity(){
		boolean exist = false;
		try {
			SQLModel sqlModel = lneModel.isValidity();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	
	public boolean validityNetUnitCode(){
		boolean exist = false;
		try {
			SQLModel sqlModel = lneModel.isValidityNetUnitCode();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	public boolean validityCode2(){
		boolean exist = false;
		try {
			SQLModel sqlModel = lneModel.isValidityCode2();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	public void insertNetUnitCode(){
			SQLModel sqlModel = lneModel.insertNetUnitCode();
			try {
				DBOperator.updateRecord(sqlModel, conn);
			} catch (DataHandleException e) {
				e.printStackTrace();
			}
	}
	
	public void insertCode2(){
		SQLModel sqlModel = lneModel.insertCode2();
		try {
			DBOperator.updateRecord(sqlModel, conn);
		} catch (DataHandleException e) {
			e.printStackTrace();
		}
}
    
    
    /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportFile() throws DataTransException {     
			File file = null;
			SQLModel sqlModel = lneModel.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "逻辑网络元素属性维护.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("ROWNUM", "序号");
			fieldMap.put("NET_CATEGORY1", "网络专业1");
			fieldMap.put("NET_CATEGORY2", "网络专业2");
			fieldMap.put("NET_UNIT_CODE", "网元编码");
			fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
			fieldMap.put("ENG_AB", "英文缩写");

			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("逻辑网络元素属性维护");
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
			return file;
	}
 
}
