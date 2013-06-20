package com.sino.ams.expand.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.conn.DBManager;
import com.sino.framework.dto.BaseUserDTO;

import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.model.AmsItemFilesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.expand.dto.EtsItemLandInfoDTO;
import com.sino.ams.expand.model.EtsItemLandInfoModel;
import com.sino.ams.appbase.dao.AMSBaseDAO;


/**
 * <p>Title: EtsItemLandInfoDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemLandInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 张星
 * @version 1.0
 */


public class EtsItemLandInfoDAO extends AMSBaseDAO {

    /**
     * 功能：租赁土地资产(AMS) AMS_LAND_INFO 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemLandInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsItemLandInfoDAO(SfUserDTO userAccount, EtsItemLandInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemLandInfoDTO dtoPara = (EtsItemLandInfoDTO) dtoParameter;
        super.sqlProducer = new EtsItemLandInfoModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：删除租赁土地资产(AMS)表“AMS_LAND_INFO”数据。
     *
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("土地资产(AMS)");
//		return operateResult;
    }

    /**
     * 功能：修改租赁土地资产(AMS)表“AMS_LAND_INFO”数据。
     *
     * @return boolean
     */
    public boolean updateData(AmsItemFilesDTO fileDTO, String[] filePaths) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            EtsItemLandInfoDTO landDTO = (EtsItemLandInfoDTO) dtoParameter;
            String barcode = landDTO.getBarcode();

            EtsItemLandInfoModel model = new EtsItemLandInfoModel(userAccount, landDTO);

//            SQLModel landModel= model.getDeleteLandModel();
//            DBOperator.updateRecord(landModel, conn);
//
//            landModel = model.getCreateLandModel();
//            DBOperator.updateRecord(landModel, conn);
            
            SQLModel landModel = model.getUpdateLandModel();
            DBOperator.updateRecord(landModel, conn);

            //super.updateData();            //对表AMS_LAND_INFO进行修改操作
            if (landDTO.getIsRent().equals("Y")) {        //表 AMS_RENT_INFO （RENT_ID ） 修改  先删除 再增加
//                deleteRentData();
//                creatRentData();
            	  updateRentData();
//                 DBOperator.updateRecord( model.getAttribute1Model(barcode),conn  );
            } else {
                deleteRentData();          //对表  AMS_RENT_INFO 进行删除操作
//                 DBOperator.updateRecord( model.getAttribute1NotModel(barcode),conn  );
            }
            //--------------对表 AMS_ITEM_FILES  进行修改操作   先删除再增加
            AmsItemFilesModel sqlModel = new AmsItemFilesModel(userAccount, fileDTO);
            DBOperator.updateRecord(sqlModel.getDeleteModel(barcode), conn);
            if (!StrUtil.isEmpty(filePaths)) {
                for (int i = 0; i < filePaths.length; i++) {
                    fileDTO.setBarcode(barcode);
                    String filedp[] = StrUtil.splitStr(filePaths[i], "$");
                    fileDTO.setFileDesc(filedp[0]);
                    fileDTO.setFilePath(filedp[1]);
                    DBOperator.updateRecord(sqlModel.getCreateModel(), conn);
                }
            }
            operateResult = true;
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
            getMessage().addParameterValue("土地管理信息");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } finally {
            try {
                if (hasError) {
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

    public void creatRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
        try {
        	EtsItemLandInfoModel amsLandInfoModel = (EtsItemLandInfoModel) sqlProducer;
            SQLModel sqlModel = amsLandInfoModel.doCreatRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("土地数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("土地数据");
            ex.printLog();
        }
    }

    public void updateRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
        try {
        	EtsItemLandInfoModel amsLandInfoModel = (EtsItemLandInfoModel) sqlProducer;
            SQLModel sqlModel = amsLandInfoModel.doUpdateRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("土地数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("土地数据");
            ex.printLog();
        }
    }
    
    public void deleteRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的删除操作
        try {
        	EtsItemLandInfoModel landInfoModel = (EtsItemLandInfoModel) sqlProducer;
            SQLModel sqlModel = landInfoModel.doDeleteRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("土地数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("土地数据");
            ex.printLog();
        }
    }
    
    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();     //获得查询的sql
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "土地信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "土地条码");
            fieldMap.put("LAND_AREA", "土地面积");
            fieldMap.put("AREA_UNIT", "地积单位");
            fieldMap.put("LAND_CERTFICATE_NO", "土地证号");
            fieldMap.put("COUNTY_NAME", "区县");
            fieldMap.put("IS_RENT", "是否租赁");
            fieldMap.put("RENT_PERSON", "出租人");
            fieldMap.put("RENT_DATE", "租赁日期");
            fieldMap.put("END_DATE", "截至日期");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("土地信息表");
            custData.setReportPerson(userAccount.getUsername());
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


    public boolean verifyBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        EtsItemLandInfoModel amsLandInfoModel = (EtsItemLandInfoModel) sqlProducer;
        SQLModel sqlModel = amsLandInfoModel.getVerifyBarcodeModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
    }


    /**
     * 功能：生成Barcode单据号。
     *
     * @return ORDER_NUMBER
     * @throws
     */
    public String getOrderNum() {
        String no = null;
        String companyCode = "";
        companyCode = userAccount.getCompanyCode();
        CallableStatement cst = null;
        String sqlStr = "begin ? := AMS_BARCODE_PKG.GETASSETBARCODE(?); end;";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setString(2, companyCode);
            cst.execute();
            no = cst.getString(1);
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return no;
    }

    public boolean hasBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        EtsItemLandInfoModel amsLandInfoModel = (EtsItemLandInfoModel) sqlProducer;
        SQLModel sqlModel = amsLandInfoModel.getVerifyBarcodeModel(barcode);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

        return success;
    }
}