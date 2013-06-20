package com.sino.ams.system.manufacturer.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.manufacturer.EtsManufacturerDTO;
import com.sino.ams.system.manufacturer.model.ManufacturerModel;
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
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * User: 李轶
 * Date: 2009-12-14
 * Time: 11:42:55
 * Function:		供应商维护
 */
public class ManufacturerDAO extends AMSBaseDAO {

	private ManufacturerModel model = null;

    public ManufacturerDAO(SfUserDTO userAccount, EtsManufacturerDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        model = new ManufacturerModel((SfUserDTO) userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    	EtsManufacturerDTO dtoPara = (EtsManufacturerDTO) dtoParameter;
        sqlProducer = new ManufacturerModel(userAccount, dtoPara);
    }

    /**
	 * 功能:检查逻辑网络元素编码属性是否存在
	 * @return boolean
	 */
	public boolean existObject(){
		boolean exist = false;
		try {
			SQLModel sqlModel = model.getManufacturerExistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}


    /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *                                        p
	 */
	public File exportFile() throws DataTransException {
			File file = null;
			SQLModel sqlModel = model.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "厂商信息维护.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("MANUFACTURER_CODE", "厂商描述");
			fieldMap.put("ENABLE", "是否有效");
			fieldMap.put("CREATE_BY", "创建人");
			fieldMap.put("CREATE_DATE", "创建日期");
			fieldMap.put("LAST_UPDATE_BY", "最后更新人");
			fieldMap.put("LAST_UPDATE_DATE", "最后更新时间");

			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("厂商信息维护");
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
			return file;
	}

    /**
	 * 功能：失效或生效厂商
	 * @return boolean
	 */
	public boolean disable(){
	    boolean exist = false;
        try {
            SQLModel sqlModel = model.getDisableOrEnableModel();
            DBOperator.updateRecord(sqlModel, conn);
            exist = true;
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
		return exist;	
	}

}