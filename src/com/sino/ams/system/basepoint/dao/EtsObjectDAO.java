package com.sino.ams.system.basepoint.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.basepoint.model.EtsObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
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
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
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


public class EtsObjectDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

	/**
	 * 功能：资产地点表(EAM) ETS_OBJECT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsObjectDAO(SfUserDTO userAccount, EtsObjectDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EtsObjectDTO dtoPara = (EtsObjectDTO)dtoParameter;
		super.sqlProducer = new EtsObjectModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入资产地点表(EAM)表“ETS_OBJECT”数据。
	 * @param objAttibute EtsObjectAttributeDTO
	 * @return boolean
	 */
	public boolean createData(EtsObjectAttributeDTO objAttibute) throws DataHandleException{                         //do _save 的 操作
		boolean operateResult = false;
        boolean autoCommit = false;
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
//
                objAttibute.setObjectNo(objDTO.getWorkorderObjectNo());
                EtsObjectAttributeDAO dao = new EtsObjectAttributeDAO(sfUser,objAttibute,conn);
                dao.createData();                                                  //根据WORKORDER_OBJECT_NO进行插入操作
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
        return operateResult;

    }



    private String getNextWorkorderObjectNo() throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return String.valueOf(seqProducer.getStrNextSeq("ETS_OBJECT_S"));
    }

	/**
	 * 功能：更新资产地点表(EAM)表“ETS_OBJECT”数据。
	 * @param workorderObjectNo String
	 * @param objAttibute EtsObjectAttributeDTO
	 * @return boolean
	 */
	public boolean updateData(String workorderObjectNo,EtsObjectAttributeDTO objAttibute) throws DataHandleException{
	    boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try{
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            super.updateData();                                                              //表修改操作
//            EtsObjectAttributeDTO objDTO2 = new EtsObjectAttributeDTO();
            objAttibute.setObjectNo(workorderObjectNo);
            EtsObjectAttributeDAO dao = new EtsObjectAttributeDAO(sfUser,objAttibute,conn);
            dao.deleteData();
//            EtsObjectAttributeDAO dao2 = new EtsObjectAttributeDAO(sfUser,objAttibute,conn);
            dao.createData();                                                  //插入操作
            operateResult = true;
            conn.commit();
            hasError = false;
            getMessage().addParameterValue("资产地点表");
        }catch(SQLException ex){
          Logger.logError(ex);
          prodMessage(MsgKeyConstant.SQL_ERROR);
        }finally{
           try{
              if(hasError){
                  conn.rollback();
              }
              conn.setAutoCommit(autoCommit);
           }catch(SQLException ex){
               Logger.logError(ex);
               prodMessage(MsgKeyConstant.SQL_ERROR);
           }
        }
		return operateResult;
	}

	/**
	 * 功能：修改资产地点表(EAM)表“ETS_OBJECT”数据，执行详细页面的单一失效操作。
	 * @return boolean
     *
	 */
	public void deleteData() throws DataHandleException{        //进行失效操作
//		boolean operateResult = super.deleteData();
		super.deleteData();
		getMessage().addParameterValue("资产地点");
//		return operateResult;
	}

  public void  disabledData(String[] workorderObjectNos) throws DataHandleException {        //执行批量失效操作
      EtsObjectModel etsObjectModel = (EtsObjectModel)sqlProducer;
      SQLModel sqlModel = etsObjectModel.getDisabledModel(workorderObjectNos);
      DBOperator.updateRecord(sqlModel, conn);
	}

  public void  efficientData(String[] workorderObjectNos) throws DataHandleException {        //执行批量生效效操作
      EtsObjectModel etsObjectModel = (EtsObjectModel)sqlProducer;
      SQLModel sqlModel = etsObjectModel.getEfficientModel(workorderObjectNos);
      DBOperator.updateRecord(sqlModel, conn);
	}

  public void inureData()throws DataHandleException{                           //执行生效操作
     EtsObjectModel etsObjectModel = (EtsObjectModel)sqlProducer;
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
			DataTransfer transfer = null;
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);

			String fileName = "基站地点统计表.xls";
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
			custData.setReportPerson(sfUser.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			/*rule.setSheetSize(1000);*/
			//设置分页显示
			TransferFactory factory = new TransferFactory();
			transfer = factory.getTransfer(rule);
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
    

	public void inAOAData() throws DataHandleException { //执行插入AOA表操作
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getAOACreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
}
