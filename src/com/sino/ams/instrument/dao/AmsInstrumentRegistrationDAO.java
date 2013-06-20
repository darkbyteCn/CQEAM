package com.sino.ams.instrument.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.instrument.model.AmsInstrumentRegistrationModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsInstrumentRegistrationDAO</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentRegistrationDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yushibo
 * @version 1.0
 */
public class AmsInstrumentRegistrationDAO extends AMSBaseDAO {
	
	AmsInstrumentRegistrationModel modelProducer = null;

	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public AmsInstrumentRegistrationDAO(SfUserDTO userAccount, AmsInstrumentRegistrationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentRegistrationModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentRegistrationDTO dtoPara = (AmsInstrumentRegistrationDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentRegistrationModel((SfUserDTO) userAccount, dtoPara);
	}
	
	/**
     * 功能：插入仪器仪表管理(EAM)表“ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE”数据。
     * @return boolean
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("仪器仪表管理(EAM)");
    }
    
    /**
     * 功能：设置仪器仪表失效(EAM)表“ETS_ITEM_INFO 中的DISABLE_DATE字段”数据。
     * @return boolean
     */
    public void updateData(String barcode) throws DataHandleException {
		boolean operateResult = false;
		SQLModel sqlModel = null;
		sqlModel = modelProducer.getDataUpdateModel(barcode);
		if (sqlModel != null && !sqlModel.isEmpty()) {
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		}
	}
    
    /**
     * 功能：批量设置仪器仪表失效(EAM)表“ETS_ITEM_INFO 中的DISABLE_DATE字段”数据。
     * @return boolean
     */
    public void updateDatas(String[] barcodes) throws DataHandleException {
		boolean operateResult = false;
		SQLModel sqlModel = null;
		for(int i=0; i<barcodes.length; i++){
			sqlModel = modelProducer.getDataUpdateModel(barcodes[i]);
			if (sqlModel != null && !sqlModel.isEmpty()) {
				DBOperator.updateRecord(sqlModel, conn);
				operateResult = true;
			}
		}
	}
	
	public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "仪器仪表登记卡.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("ORGNIZATION_NAME", "公司");
            fieldMap.put("BARCODE", "条码");
            fieldMap.put("ITEM_NAME", "仪器仪表名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("VENDOR_NAME", "仪表厂家");
            fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门");
            fieldMap.put("ITEM_QTY", "数量");
            fieldMap.put("ATTRIBUTE3", "用途");
            fieldMap.put("OBJECT_NAME", "使用地点");
            fieldMap.put("MAINTAIN_USER", "使用人员");
            fieldMap.put("REMARK", "仪表性能");
            fieldMap.put("ITEM_STATUS", "仪表状态");
            fieldMap.put("RESPONSIBILITY_NAME", "责任人");
            fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
            fieldMap.put("ATTRIBUTE2", "单价");
            fieldMap.put("TOTAL_PR", "总价");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("仪器仪表登记卡");
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }

	/**
	 * 功能：插入仪器仪表登记卡记录(EAM)表"ETS_ITEM_INFO"数据。
	 * @throws CalendarException 
	 * 
	 */
	public void createCardData(Connection conn, String barcode, String vendorCode, String itemQty,
			String price, String attribute3, String responsibilityDept, 
			String responsibilityUser, String startDate, String workorderObjectNo,
			String itemStatus, String maintainUser, String remark, String ifpage, String itemCode) throws DataHandleException, CalendarException {
		
		boolean autoCommit = true;
		DataHandleException error = null;
		boolean operateResult = false;
		
		try {
			autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        int success = 0;
	        success = modelProducer.createEtsItemInfoCard(conn, barcode, vendorCode, itemQty, price, attribute3, responsibilityDept, responsibilityUser, startDate, workorderObjectNo, itemStatus, maintainUser, remark, ifpage, itemCode);
	        if(success > 0){
	        	operateResult = true;
	        }
		} catch (SQLException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage("INSTRUMENT_CARD_CREATE_SUCCESS");
				} else {
					conn.rollback();
					prodMessage("INSTRUMENT_CARD_CREATE_FAILURE");
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
				if(!operateResult){
					throw error;
				}
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}
	
	/**
	 * 功能：更新仪器仪表登记卡记录(EAM)表"ETS_ITEM_INFO"数据。
	 * @throws CalendarException 
	 * 
	 */
	public void updateCardData(Connection conn, String oldBarcode, String barcode, String vendorCode, String itemQty,
			String price, String attribute3, String responsibilityDept, 
			String responsibilityUser, String startDate, String workorderObjectNo,
			String itemStatus, String maintainUser, String remark, String ifpage, String itemCode) throws DataHandleException, CalendarException {
		
		boolean autoCommit = true;
		DataHandleException error = null;
		boolean operateResult = false;
		
		try {
			autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        int success = 0;
	        success = modelProducer.updateEtsItemInfoCard(conn, oldBarcode, barcode, vendorCode, itemQty, price, attribute3, responsibilityDept, responsibilityUser, startDate, workorderObjectNo, itemStatus, maintainUser, remark, ifpage, itemCode);
	        if(success > 0){
	        	operateResult = true;
	        }
		} catch (SQLException ex) {
			Logger.logError(ex);
			error = new DataHandleException(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage("INSTRUMENT_CARD_UPDATE_SUCCESS");
				} else {
					conn.rollback();
					prodMessage("INSTRUMENT_CARD_UPDATE_FAILURE");
					message.setIsError(true);
				}
				conn.setAutoCommit(autoCommit);
				if(!operateResult){
					throw error;
				}
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}
}
