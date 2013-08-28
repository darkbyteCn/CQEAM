package com.sino.ams.yearchecktaskmanager.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsRespMapLocationDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsRespMapLocationModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsRespMapLocationDAO extends AMSBaseDAO {

private SfUserDTO sfUser = null;
	
	public AssetsRespMapLocationDAO(SfUserDTO userAccount,AssetsRespMapLocationDTO dtoParameter,Connection conn){
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
		
	}
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsRespMapLocationDTO dtoPara = (AssetsRespMapLocationDTO) arg1;
		super.sqlProducer = new AssetsRespMapLocationModel((SfUserDTO) arg0,dtoPara);

	}
	//ɾ����ʱ������
	public void deleteTmpByUser()throws SQLModelException, QueryException,DataHandleException{
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getDeleteTmpModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
	 //���ݱ��浽��ʱ��
	public void  saveData(DTOSet dtoSet)throws SQLModelException,DataHandleException{
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = null;
		for(int i=0;i<dtoSet.getSize();i++){
			AssetsRespMapLocationDTO dto = (AssetsRespMapLocationDTO)dtoSet.getDTO(i);
			sqlModel = abuModel.getSaveDataModel(dto);
			DBOperator.updateRecord(sqlModel, conn);
		}
	}
	
	 //���ݱ��浽��ʽ��
	public void  saveOrUpdateData(DTOSet dtoSet)throws SQLModelException,DataHandleException{
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = null;
		for(int i=0;i<dtoSet.getSize();i++){
			AssetsRespMapLocationDTO dto = (AssetsRespMapLocationDTO)dtoSet.getDTO(i);
			sqlModel = abuModel.getsaveOrUpdateDataModel(dto);
			DBOperator.updateRecord(sqlModel, conn);
		}
	}
	
	//��ȡ��ϸ��Ϣ
	public RowSet getImportDataDetail() throws QueryException{
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getImportDataDetal();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		return rs;
	}
	
	//д�������Ϣ
	public void updateError(AssetsRespMapLocationDTO dto,String errorMsg) throws DataHandleException{
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getUpdateErrorModel(dto,errorMsg);
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	//У��WorkOrderObjectCode�Ƿ��ظ�
	public boolean validateWorkOrderObjectCodehasRepeat(String WorkOrderObjectCode)throws SQLModelException, QueryException{
		boolean result = false;
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getVaidateWorkOrderObjectCodeHasRepeat(WorkOrderObjectCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			result = true;
		}
		return result;
		
	}
	
	//У��ص���ϱ���
	public boolean validateWorkOrderObjectCode(String workOrderObjectCode)throws SQLModelException, QueryException{
		boolean result = false;
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getVaidateWorkOrderObjectCode(workOrderObjectCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			result = true;
		}
		return result;
		
	}
	
	//У��Ա�����
	public boolean validateEmployeeNumber(String employeeNumber)throws SQLModelException, QueryException{
		boolean result = false;
		AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
		SQLModel sqlModel = abuModel.getVaidateEmployeeNumber(employeeNumber);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			result = true;
		}
		return result;
		
	}
	
	@SuppressWarnings("rawtypes")
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
			SQLModel sqlModel = abuModel.getLocationsModel();
			String reportTitle = "";

			reportTitle = "������������ص㼯ֵ";
			String fileName = reportTitle + ".xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(((SfUserDTO) userAccount).getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("WORKORDER_OBJECT_CODE", "�ص���ϱ���");
		fieldMap.put("WORKORDER_OBJECT_NAME", "�ص��������");
		return fieldMap;
	}
	
	@SuppressWarnings("rawtypes")
	public File getExportPersonsFile() throws DataTransException {
		File file = null;
		try {
			AssetsRespMapLocationModel abuModel = (AssetsRespMapLocationModel)sqlProducer;
			SQLModel sqlModel = abuModel.getCheckPersonsModel();
			String reportTitle = "";

			reportTitle = "�����������ʲ��̵������˼�ֵ";
			String fileName = reportTitle + ".xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getCheckPersonsFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(((SfUserDTO) userAccount).getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getCheckPersonsFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("USERNAME", "�̵�����������");
		fieldMap.put("EMPLOYEE_NUMBER", "�̵�������Ա�����");
		fieldMap.put("DEPT_NAME","�̵������˵Ĳ���");
		return fieldMap;
	}
}
