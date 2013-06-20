package com.sino.ams.dzyh.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.dzyh.model.EtsObjectModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsObjectDAO</p>
 * <p>Description:程序自动生成服务程序“EtsObjectDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EtsObjectDAO extends AMSBaseDAO {


/**
	 * 功能：资产地点表(EAM) ETS_OBJECT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsObjectDAO(SfUserDTO userAccount, EtsObjectDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

    /**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		super.sqlProducer = new EtsObjectModel( (SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：根据ObjectCategory获得 categoryName
	 * @return String
	 * @throws QueryException
	 */
	public String getCategoryName() throws QueryException {
        String categoryName = "";
        EtsObjectDTO etsObjectDTO = (EtsObjectDTO) dtoParameter;
        EtsObjectModel eomodel = new EtsObjectModel(userAccount, etsObjectDTO);
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

	/**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File exportFile() throws DataTransException {
		File file = null;
		try {
			EtsObjectDTO etsObjectDTO = (EtsObjectDTO) dtoParameter;
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
			String fileName = etsObjectDTO.getCategoryName() + "统计表.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
			fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
			fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
			fieldMap.put("ORGANIZATION_ID", "组织ID");
			fieldMap.put("COUNTY_NAME", "所在区县");
			fieldMap.put("DISABLE_DATE", "失效日期");
			fieldMap.put("CATEGORY_NAME", "地点类别");
//		fieldMap.put("IS_TEMP_ADDR", "是否临时地点");
			fieldMap.put("CREATION_DATE", "创建日期");
			fieldMap.put("CREATED_BY", "创建人");
			fieldMap.put("PROJECT_NAME", "所属工程");

			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(fileName);
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

        public boolean doVerifyWorkNo(String workorderObjectCode) throws QueryException { //执行校验workorderObjectCode操作
        boolean success = false;
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getVerifyWorkNoModel(workorderObjectCode);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

        return success;
    }

        public boolean doVerifyWorkBarcode(String[] workorderObjectNos) throws QueryException { //执行校验该地点是否存在设备
        boolean success = false;
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getVerifyBarcode(workorderObjectNos);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

        return success;
    }


  public String  createData2() throws DataHandleException{                         //do _save 的 操作
		boolean operateResult = false;
        boolean autoCommit = true;
        boolean hasError = true;
 
        try {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                EtsObjectDTO objDTO = (EtsObjectDTO)dtoParameter;
                objDTO.setWorkorderObjectNo(getNextWorkorderObjectNo());
                setDTOParameter(objDTO);
                super.createData();                                       //插入数据
                inAOAData();
//                EtsObjectAttributeDTO objDTO2 = new EtsObjectAttributeDTO();
//                objDTO2.setObjectNo(objDTO.getWorkorderObjectNo());                          //获取 WORKORDER_OBJECT_NO
//
                operateResult = true;
                conn.commit();
                hasError = false;
                getMessage().addParameterValue("资产地点表");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } finally{
            try {
                if(hasError){
                    conn.rollback();                      //回滚
                }
                conn.setAutoCommit(autoCommit);          //恢复以前状态
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return String.valueOf(operateResult);

    }

    private String getNextWorkorderObjectNo() throws SQLException {
//    	return SyBaseSQLUtil.generateGUID();
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getGUID();
    }

    public void inAOAData() throws DataHandleException { //执行插入AOA表操作
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getAOACreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}



}
