package com.sino.ams.system.rent.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.rent.dto.RentDTO;
import com.sino.ams.system.rent.model.RentModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
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
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsHouseInfoDAO</p>
 * <p>Description:程序自动生成服务程序“AmsHouseInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author Zyun
 * @version 1.0
 */


public class RentDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    

    /**
     * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public RentDAO(SfUserDTO userAccount, RentDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        RentDTO dtoPara = (RentDTO) dtoParameter;
        super.sqlProducer = new RentModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     *
     * @return boolean
     */
    public void createData() throws DataHandleException {          //do_save操作
//        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            RentDTO dtopara = (RentDTO) dtoParameter;
            RentModel sqlModel = new RentModel((SfUserDTO) userAccount, dtopara);
//            updateEIIData();                              //对表ETS_ITEM_INFO进行修改操作 1，EII.ATTRIBUTE1 = 'RENT'

//             DBOperator.updateRecord(sqlModel.insertDataNo(),conn);  //对表   eii 进行新增操作
//            super.createData();
            DBOperator.updateRecord(sqlModel.insertAmsRentInfoHistory(),conn) ;
            if(!dtopara.getHistoryId().equals("")){
            	DBOperator.updateRecord(sqlModel.disabledAmsRentInfoHistory(),conn) ;
            }
            if(!dtopara.getRentId().equals("")){
            	DBOperator.updateRecord(sqlModel.updateAmsRentInfo(),conn) ;
            } else {
            	super.createData();
            }
            DBOperator.updateRecord(sqlModel.getUpdteEII(), conn);
            //对表   AMS_RENT_INFO 进行新增操作
//            operateResult = true;
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
          	getMessage().addParameterValue("租赁资产");
            
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        }catch(SQLModelException e){
              Logger.logError(e);
              prodMessage(MsgKeyConstant.SQL_ERROR);
    } catch (CalendarException e) {
			e.printStackTrace();
		} finally {
            if (hasError) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    Logger.logError(ex);
                    prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
                }
            }
        }
//        return operateResult;
    }

    /**
     * 功能：更新租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     *
     * @return boolean
     */
    public void updateData() throws DataHandleException {                     //修改数据
        RentDTO dtopara = (RentDTO) dtoParameter;
        super.updateData();
        prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
        getMessage().addParameterValue("租赁资产");
    }


    //修改租赁的相关数据
    public void updateDatT(Connection conn) throws SQLException, DataHandleException, SQLModelException {
        try {
            conn.setAutoCommit(false);
            RentDTO rentDTO = (RentDTO) dtoParameter;
            RentModel model = new RentModel(sfUser, rentDTO);
            DBOperator.updateRecord(model.getUpdteEII(), conn);
            DBOperator.updateRecord(model.getDataUpdateModel(), conn);
            if(!rentDTO.getHistoryId().equals("")){
            	DBOperator.updateRecord(model.updateAmsRentInfoHistoryRemark(), conn);
            }else {
            	DBOperator.updateRecord(model.insertAmsRentInfoHistory(), conn);
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        }
        catch (DataHandleException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        } catch (CalendarException e) {
			e.printStackTrace();
		}
    }


    public void updateEIIData() throws DataHandleException {
        RentModel rentModel = (RentModel)sqlProducer;
        SQLModel sqlModel = rentModel.getupdataEIIModel();
        DBOperator.updateRecord(sqlModel,conn);
    }

      public File exportFile() throws DataTransException {
        File file = null;
        RentDTO rentDTO = (RentDTO) dtoParameter;
        try {
        	SQLModel sqlModel = null;
        	 String fileName = "";
             if("AMS_RENT_INFO_HISTORY".equals(rentDTO.getAccessType())){
             	fileName = "租赁资产历史信息";
             	sqlModel = sqlProducer.getDataByForeignKeyModel("barcode");
             }else{
             	fileName = "经营租赁到期资产";
             	sqlModel = sqlProducer.getPageQueryModel();
             }
             
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            
           
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath + ".xls");

            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();

            if("AMS_RENT_INFO_HISTORY".equals(rentDTO.getAccessType())){
	            fieldMap.put("BARCODE", "租赁资产标签号");
	            fieldMap.put("ITEM_NAME", "资产名称");
	            fieldMap.put("ITEM_SPEC", "规格型号");
	            
	            fieldMap.put("CONTENT_NAME", "资产类别描述");
	            fieldMap.put("RENT_DATE", "起始日期");
	            fieldMap.put("END_DATE", "到期时间");
	            fieldMap.put("RENT_PERSON", "出租方");
	            
	            fieldMap.put("TENANCY", "租期(年)");
	            fieldMap.put("RENTAL", "总租金");
	            fieldMap.put("YEAR_RENTAL", "年租金(元)");
	            fieldMap.put("MONTH_REANTAL", "月租金");
	            fieldMap.put("REMARK", "备注");
            }else {
	            fieldMap.put("ROWNUM", "序号");
	            fieldMap.put("COMPANY", "公司OU");
	            fieldMap.put("BARCODE", "租赁资产标签号");
	            fieldMap.put("ITEM_NAME", "资产名称");
	            fieldMap.put("ITEM_SPEC", "规格型号");
	            fieldMap.put("ITEM_UNIT", "单位");
	            
	            fieldMap.put("MANUFACTURER_NAME", "生产厂商名称");
	            fieldMap.put("POWER", "额定功率");
	            fieldMap.put("OTHER_INFO", "设备性能");
	            fieldMap.put("CONTENT_CODE", "资产类别代码组合");
	            fieldMap.put("CONTENT_NAME", "资产类别描述");
	            fieldMap.put("RESPONSIBILITY_USER", "责任人编号");
	            
	            fieldMap.put("USER_NAME", "责任人姓名");
	            fieldMap.put("OBJECT_NAME", "资产地点");
	            fieldMap.put("MAINTAIN_USER", "使用人");
	            fieldMap.put("MAINTAIN_DEPT", "使用部门");
	            fieldMap.put("RENT_DATE", "起始日期");
	            fieldMap.put("END_DATE", "到期时间");
	            fieldMap.put("RENT_PERSON", "出租方");
	            
	            fieldMap.put("TENANCY", "租期(年)");
	            fieldMap.put("YEAR_RENTAL", "年租金(元)");
	            fieldMap.put("MONTH_REANTAL", "月租金");	            
	            fieldMap.put("MONTH_REANTAL", "月租金");
//	            fieldMap.put("LAST_YEAR_RATE", "较去年同期增长率");
            }
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(sfUser.getUsername());
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
      
      public RowSet getRentAssetsByReport() throws SQLModelException, QueryException{
    	  RentModel rentModel = (RentModel)sqlProducer;
          SQLModel sqlModel = rentModel.getPageQueryModel();
	      SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
	      simpleQuery.executeQuery();
	      return simpleQuery.getSearchResult();
      }
}
