package com.sino.ams.system.intangible.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.intangible.dto.IntangibleDTO;
import com.sino.ams.system.intangible.model.IntangibleModel;
import com.sino.ams.system.switches.model.EtsObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsObjectDAO</p>
 * <p>Description:程序自动生成服务程序“EtsObjectDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class IntangibleDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

/**
	 * 功能：资产地点表(EAM) ETS_OBJECT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public IntangibleDAO(SfUserDTO userAccount, IntangibleDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

    /**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		IntangibleDTO dtoPara = (IntangibleDTO) dtoParameter;
		super.sqlProducer = new IntangibleModel( (SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：根据ObjectCategory获得 categoryName
	 * @return String
	 * @throws QueryException
	 */
	public String getCategoryName() throws QueryException {
        String categoryName = "";
        EtsObjectDTO etsObjectDTO = (EtsObjectDTO) dtoParameter;
        EtsObjectModel eomodel = new EtsObjectModel(sfUser, etsObjectDTO);
        SQLModel sModel = eomodel.getCategoryNameModel();
        SimpleQuery sQuery = new SimpleQuery(sModel, conn);
        sQuery.executeQuery();
        if (sQuery.hasResult()) {
            RowSet row = sQuery.getSearchResult();
            try {
                categoryName = (String) row.getRow(0).getValue("VALUE");
            } catch (ContainerException e) {
                e.printStackTrace();
                throw new QueryException();
            }
        }
        return categoryName;
    }

    /**
	 * 功能：插入资产地点表(EAM)表“ETS_OBJECT”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException{
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
	    super.createData();
		getMessage().addParameterValue(dtoPara.getCategoryName());
//		return operateResult;
	}

    /**
	 * 功能：执行单个失效操作。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
		 super.deleteData();
		getMessage().addParameterValue("地点信息");
//		return operateResult;
	}

    /**
	 * 功能：更新资产地点表(EAM)表“ETS_OBJECT”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException{
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		 super.updateData();
		getMessage().addParameterValue(dtoPara.getCategoryName());
//		return operateResult;
	}

	public void disabledData(String[] workorderObjectNos){ //执行批量失效操作
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		try {
			EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
			SQLModel sqlModel = etsObjectModel.getDisabledModel(workorderObjectNos);
			DBOperator.updateRecord(sqlModel, conn);
			prodMessage(CustMessageKey.DISABLE_SUCCESS);
			getMessage().addParameterValue(dtoPara.getCategoryName());
		} catch (DataHandleException ex) {
			prodMessage(CustMessageKey.DISABLE_FAILURE);
			getMessage().addParameterValue(dtoPara.getCategoryName());
			ex.printLog();
		}
	}

	public void efficientData(String[] workorderObjectNos){ //执行批量生效效操作
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		try {
			EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
			SQLModel sqlModel = etsObjectModel.getEfficientModel(workorderObjectNos);
			DBOperator.updateRecord(sqlModel, conn);
			prodMessage(CustMessageKey.ENABLE_SUCCESS);
			getMessage().addParameterValue(dtoPara.getCategoryName());
		} catch (DataHandleException ex) {
			prodMessage(CustMessageKey.ENABLE_FAILURE);
			getMessage().addParameterValue(dtoPara.getCategoryName());
			ex.printLog();
		}
	}

	public void inureData() throws DataHandleException { //执行生效操作
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getInureModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

   public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "无形资产.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("ASSET_NUMBER", "资产编号");
            fieldMap.put("BARCODE", "资产条码");
            fieldMap.put("ITEM_NAME", "资产名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("LIFE_IN_YEARS", "摊销年限");
            fieldMap.put("DEPRN_COST", "折旧金额");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
            fieldMap.put("VENDOR_NAME", "供应商");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("无形资产");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
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
}
