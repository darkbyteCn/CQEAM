package com.sino.ams.instrument.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.instrument.model.AmsInstrumentInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
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
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsInstrumentInfoDAO</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class AmsInstrumentInfoDAO extends AMSBaseDAO {
    AmsInstrumentInfoModel  modelProducer = null;
    /**
     * 功能：仪器仪表管理(EAM) AMS_INSTRUMENT_INFO 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsInstrumentInfoDAO(SfUserDTO userAccount, AmsInstrumentInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        modelProducer = (AmsInstrumentInfoModel)sqlProducer;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInstrumentInfoDTO dtoPara = (AmsInstrumentInfoDTO) dtoParameter;
        super.sqlProducer = new AmsInstrumentInfoModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入仪器仪表管理(EAM)表“AMS_INSTRUMENT_INFO”数据。
     * @return boolean
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("仪器仪表管理(EAM)");
    }

    public String getAddressId() throws QueryException {
        String addresId = "";
        AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
        AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
        SimpleQuery sq = new SimpleQuery(model.selectAddressId(), conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            RowSet row = sq.getSearchResult();
            try {
                addresId = (String) row.getRow(0).getValue("ADDRESS_ID");
            } catch (ContainerException e) {
                e.printStackTrace();
                throw new QueryException();
            }
        }
        return addresId;
    }
   //修改操作
    public void update(Connection conn) throws SQLException, DataHandleException {
        try {
            conn.setAutoCommit(false);
            AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
            AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
            DBOperator.updateRecord(model.update(), conn);
            DBOperator.updateRecord(model.updateIfo(), conn);
            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
//            e.printStackTrace();
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
        }
    }


    public String insertData(Connection conn, String itemcode1,String addressId) throws SQLException, DataHandleException, QueryException {
        String mes = "";
        try {
            conn.setAutoCommit(false);
            AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
            AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
            SimpleQuery sq = new SimpleQuery(model.selectItemInfo(), conn);  //检查ITEM的基本信息
            sq.executeQuery();

            SimpleQuery s = new SimpleQuery(model.selectInfo(), conn);   //检查仪器信息
            s.executeQuery();

            /*SimpleQuery siq = new SimpleQuery(model.selectNO(), conn);    //检查BARCODENO
            siq.executeQuery();*/


            if (s.getSearchResult().getSize() > 0) {
                mes = "此仪具你已有！";
            } else {
                if (sq.getSearchResult().getSize() > 0) {
                    DBOperator.updateRecord(model.updateItem(), conn);                          //ETS_SYSTEM_ITEM  (更改供应商id)
                    DBOperator.updateRecord(model.insertDataNo(itemcode1,addressId), conn);     // 新增ETS_ITEM_INFO  (插入addressId)
                    DBOperator.updateRecord(model.getDataCreateModel(), conn);                  //新增AMS_INSTRUMENT_INFO
                } else {
                    SeqProducer seq = new SeqProducer(conn);
                    String itemCode = StrUtil.nullToString(seq.getStrNextSeq("ETS_SYSTEM_ITEM_S"));
                    DBOperator.updateRecord(model.insertDataBase(itemCode), conn);            //ETS_SYSTEM_ITEM
                    DBOperator.updateRecord(model.insertDistrbute(itemCode), conn);           //ETS_SYSITEM_DISTRIBUTE (分配给自己)
                    DBOperator.updateRecord(model.insertDataNo(itemCode,addressId), conn);    //ETS_ITEM_INFO （插入addressId）
                    DBOperator.updateRecord(model.getDataCreateModel(), conn);                //AMS_INSTRUMENT_INFO （仪具用途）
                }

            }
            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
//            e.printStackTrace();
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
        }
        return mes;
    }

    /**
     * 功能：更新仪器仪表管理(EAM)表“AMS_INSTRUMENT_INFO”数据。
     * @return boolean
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("仪器仪表管理(EAM)");
    }

    /**
     * 功能：删除仪器仪表管理(EAM)表“AMS_INSTRUMENT_INFO”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("仪器仪表管理(EAM)");
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "仪器仪表台账.xls";
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
            custData.setReportTitle("仪器仪表台账");
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
     * 功能：仪器仪表责任人查询的导出
     * @return
     * @throws DataTransException
     */
    public File exportRes() throws DataTransException {
        File file = null;
//        try {
            SQLModel sqlModel = modelProducer.getQueryRespModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            String fileName = "仪器仪表台账.xls";
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
            custData.setReportTitle("仪器仪表台账");
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
        return file;
    }


      //主要是对仪具用途进行维护
  public String creatData(Connection conn, String itemcode1,String addressId) throws SQLException, DataHandleException, QueryException {
        String mes = "";
        try {
            conn.setAutoCommit(false);
            AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
            AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
//            SimpleQuery sq = new SimpleQuery(model.selectItemInfo(), conn);  //检查ITEM的基本信息
//            sq.executeQuery();

//            SimpleQuery s = new SimpleQuery(model.selectInfo(), conn);   //检查仪器信息
//            s.executeQuery();

            /*SimpleQuery siq = new SimpleQuery(model.selectNO(), conn);    //检查BARCODENO
            siq.executeQuery();*/


//            if (s.getSearchResult().getSize() > 0) {
//                mes = "此仪具你已有！";
//            } else {
//                if (sq.getSearchResult().getSize() > 0) {
//                    DBOperator.updateRecord(model.updateItem(), conn);                          //ETS_SYSTEM_ITEM
//                    DBOperator.updateRecord(model.insertDataNo(itemcode1,addressId), conn);     // 新增ETS_ITEM_INFO
//                    DBOperator.updateRecord(model.getDataCreateModel(), conn);                  //新增AMS_INSTRUMENT_INFO
//                } else {
                    SeqProducer seq = new SeqProducer(conn);
//                    String itemCode = seq.getStrNextSeq("ETS_SYSTEM_ITEM_S");
//                    DBOperator.updateRecord(model.insertDataBase(itemCode), conn);            //ETS_SYSTEM_ITEM（VENDOR_ID  INSTRUMENT）
//                    DBOperator.updateRecord(model.insertDistrbute(itemCode), conn);           //ETS_SYSITEM_DISTRIBUTE (分配给自己)
                    DBOperator.updateRecord(model.insertDataNo(itemcode1,addressId), conn);    //ETS_ITEM_INFO （插入addressId ,ATTRIBUTE3）
//                    DBOperator.updateRecord(model.getDataCreateModel(), conn);                //AMS_INSTRUMENT_INFO （仪具用途）
//                    DBOperator.updateRecord(model.getDataCreateModel(), conn);                //AMS_INSTRUMENT_INFO （仪具用途）
//                }

//            }
            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            e.printStackTrace();
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
        }
        return mes;
    }

//修改仪器仪表的相关数据
    public void updateDat(Connection conn) throws SQLException, DataHandleException {
        try {
            conn.setAutoCommit(false);
            AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
            AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
//            DBOperator.updateRecord(model.update(), conn);                   //修改  ETS_SYSTEM_ITEM（VENDOR_ID，ITEM_NAME，ITEM_SPEC）
            DBOperator.updateRecord(model.getUpusageModel(), conn);           // ETS_ITEM_INFO.ATTRIBUTE3 更改仪具用途
            conn.commit();
        }
        catch (SQLException e) {
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
        }
    }


  public File exportFile2() throws DataTransException {
        File file = null;
//        try {

//            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) dtoParameter;
            AmsInstrumentInfoModel model = new AmsInstrumentInfoModel(userAccount, amsInstrumentInfo);
            SQLModel sqlModel = model.getSQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            String fileName = "仪器仪表统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "仪器条码");
            fieldMap.put("ITEM_NAME", "仪器名称");
            fieldMap.put("ITEM_SPEC", "仪器类型");
            fieldMap.put("MAINTAIN_NAME", "借用人");
            fieldMap.put("RESPONSIBILITY_NAME", "责任人");
            fieldMap.put("VENDOR_NAME", "供应商");
            fieldMap.put("BORROW_DATE", "借用日期");
            fieldMap.put("DAYS", "借用天数");
//            fieldMap.put("CREATION_DATE", "创建时间");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("仪器仪表借用报表");
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
//        } catch (SQLModelException ex) {
//            ex.printLog();
//            throw new DataTransException(ex);
//        }
        return file;
    }
}