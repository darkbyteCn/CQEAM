package com.sino.ams.system.house.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.match.dao.BarcodeMatchDAO;
import com.sino.ams.match.dto.BarcodeMatchDTO;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.business.SystemItemHouse;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.model.AmsHouseInfoModel;
import com.sino.ams.system.house.model.AmsItemFilesModel;
import com.sino.ams.system.house.model.RentHouseModel;
import com.sino.ams.system.switches.dao.EtsObjectDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.GenBarcode;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-14
 * Time: 18:26:07
 * Function:租赁房屋土地维护.
 */
public class RentHouseDAO extends AMSBaseDAO {
    RentHouseModel modelProducer = null;
    String showMsg = "";

    /**
     * 功能：租赁土地资产(EAM) AMS_LAND_INFO 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public RentHouseDAO(SfUserDTO userAccount, AmsHouseInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        modelProducer = (RentHouseModel) sqlProducer;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsHouseInfoDTO dtoPara = (AmsHouseInfoDTO) dtoParameter;
        super.sqlProducer = new RentHouseModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入租赁土地资产(EAM)表“AMS_LAND_INFO”数据。
     * @return boolean
     */
    public boolean createData(EtsItemInfoDTO itemInfoDTO, String[] filePaths) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsHouseInfoDTO landDTO = (AmsHouseInfoDTO) dtoParameter;
            String barcode = landDTO.getBarcode();
            itemInfoDTO.setItemCode(landDTO.getItemCode()); //分类编码
            itemInfoDTO.setBarcode(landDTO.getBarcode()); //获得Barcode
            EtsItemInfoDAO itemInfoDAO = new EtsItemInfoDAO(userAccount, itemInfoDTO, conn);
            boolean hasBarcode = hasBarcode(landDTO.getBarcode());
            if (hasBarcode) { //如果在表ETS_ITEM_INFO存在Barcode即对其进行修改，否则进行插入操作
                itemInfoDAO.updateAttribute1(landDTO.getBarcode()); //跟据Barcode对表ETS_ITEM_INFO进行修改操作，即对 ‘ATTRIBUTE1’的值改为"RENT"  改用 Barcode
            } else {
                itemInfoDAO.createData(); //对表 ETS_ITEM_INFO 进行插入操作
            }
            super.createData(); //对表 AMS_LAND_INFO 进行插入操作
            if (landDTO.getIsRent().equals("Y")) { //判断表  AMS_LAND_INFO 中的字段 ‘IS_RENT’的值 ；1，若=‘Y’对表 AMS_RENT_INFO 进行插入操作
                creatRentData(); //插入表AMS_RENT_INFO字段 barcode 对其主键 RENT_ID 自动生成
            }
            //---对表EAM_ITEM_FILES进行操作
            if (!StrUtil.isEmpty(filePaths)) {
                for (int i = 0; i < filePaths.length; i++) {
                    AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
                    fileDTO.setBarcode(barcode);
                    String filedp[] = StrUtil.splitStr(filePaths[i], "$");
                    fileDTO.setFileDesc(filedp[0]);
                    fileDTO.setFilePath(filedp[1]);
                    AmsItemFilesDAO amsItemFilesDAO = new AmsItemFilesDAO(userAccount, fileDTO, conn);
                    amsItemFilesDAO.setDTOClassName(AmsItemFilesDTO.class.getName());
                    amsItemFilesDAO.createData();
                }
            }
            operateResult = true;
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
            getMessage().addParameterValue("土地数据");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (QueryException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
            try {
                if (hasError) {
                    conn.rollback(); //回滚
                }
                conn.setAutoCommit(autoCommit); //恢复以前状态
            } catch (SQLException e) {
                Logger.logError(e);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }


    /**
     * 功能：修改租赁的房屋土地数据。
     * @return boolean
     */
    public boolean updateData(EtsItemInfoDTO itemInfoDTO, AmsItemFilesDTO fileDTO, String[] filePaths, DTOSet lineSet) throws
            DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = false;
//        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsHouseInfoDTO HLDTO = (AmsHouseInfoDTO) dtoParameter;
            AmsHouseInfoModel model = new AmsHouseInfoModel(userAccount, HLDTO);
            SQLModel sqlModel1 = model.deleteUsesInfo();
            DBOperator.updateRecord(sqlModel1, conn);
            String barcode = HLDTO.getBarcode();
//			super.updateData(); //对表EAM_house_INFO进行修改操作
            EtsItemInfoDAO itemInfoDAO = new EtsItemInfoDAO(userAccount, itemInfoDTO, conn);
            boolean hasBarcode = hasBarcode(barcode);
            if (hasBarcode) { //如果在表ETS_ITEM_INFO存在Barcode即对其进行修改，否则进行插入操作
//				itemInfoDAO.updateAttribute1(barcode); //跟据Barcode对表ETS_ITEM_INFO进行修改操作，即对 ‘ATTRIBUTE1’的值改为"RENT"  改用 Barcode
                updateItemInfo();
            } else {
//               modelProducer.getItemCreateModel(itemInfoDTO);
                insertItemInfoData(itemInfoDTO);
//                itemInfoDAO.createData(); //对表 ETS_ITEM_INFO 进行插入操作
            }
            boolean hasHouseBar = hasHouseBarcode(barcode);
            if (hasHouseBar) {
                super.updateData();
            } else {
                super.createData();
            }

            if (StrUtil.isEmpty(HLDTO.getRentId())) {
                creatHouseRentData();
            } else {
                boolean isRentDate = hasIsRent(barcode);
                if (isRentDate) {
                    updateHouseRentData();
                } else {
                    updateRentDisData();
                    creatHouseRentData();
                }
            }

//          itemInfoDTO.setBarcode(HLDTO.getBarcode()); // 标签号
//			itemInfoDTO.setItemCode(HLDTO.getItemCode()); //分类编码
//			itemInfoDTO.setSystemid(HLDTO.getSystemId()); // 　根据SYSTEMID进行修改；

//			if (!HLDTO.getTemp().equals("Y")) {
//				RentHouseModel updateEII = new RentHouseModel(userAccount, HLDTO);
//				SQLModel eiiSqlModel = updateEII.getEiiData(HLDTO.getBarcode());
//				DBOperator.updateRecord(eiiSqlModel, conn);
//			}

//            EtsItemInfoDAO itemInfoDAO = new EtsItemInfoDAO(userAccount,itemInfoDTO,conn);
//            itemInfoDAO.updateData();              //对表ETS_ITEM_INFO进行修改操作
//            if (landDTO.getIsRent().equals("Y")) {        //表 AMS_RENT_INFO （RENT_ID ） 修改  先删除 再增加
//                deleteRentData();
//                creatRentData();
//            } else {
//                deleteRentData();          //对表  AMS_RENT_INFO 进行删除操作
//            }
            saveUses(lineSet);
            //--------------对表 EAM_ITEM_FILES  进行修改操作   先删除再增加
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
            conn.commit();
            operateResult = true;
//            hasError = false;
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
            getMessage().addParameterValue("未处置信息");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (SQLModelException e) {
            Logger.logError(e);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback(); //回滚
                }
                conn.setAutoCommit(autoCommit); //恢复以前状态
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能:修改表  EAM_HOUSE_RENT
     */
    public void insertItemInfoData(EtsItemInfoDTO itemInfoDTO) throws SQLModelException { //执行EAM_HOUSE_RENT 表的新增操作
        try {
            SQLModel sqlModel = modelProducer.getItemCreateModel(itemInfoDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            ex.printLog();
        }
    }


    /**
     * 功能:插入到表  EAM_HOUSE_RENT
     */
    public void creatHouseRentData() throws SQLModelException { //执行EAM_HOUSE_RENT 表的新增操作
        try {
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            SQLModel sqlModel = modelProducer.doCreatRentData(houseDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            ex.printLog();
        }
    }


    /**
     * 功能:插入到表  EAM_HOUSE_RENT
     */
    public void updateItemInfo() throws SQLModelException { //执行ets_item_info表的修改操作
        try {
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            SQLModel sqlModel = modelProducer.getItInfoModel(houseDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            ex.printLog();
        }
    }


    /**
     * 功能:修改表  EAM_HOUSE_RENT
     */
    public void updateHouseRentData() throws SQLModelException { //执行EAM_HOUSE_RENT 表的新增操作
        try {
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            SQLModel sqlModel = modelProducer.doUpdteRentData(houseDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            ex.printLog();
        }
    }


    /**
     * 功能:修改表  EAM_HOUSE_RENT
     */
    public void updateRentDisData() throws SQLModelException { //执行EAM_HOUSE_RENT 表的新增操作
        try {
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            SQLModel sqlModel = modelProducer.doUpdteDisData(houseDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            ex.printLog();
        }
    }


    /**
     * 功能：修改数据。
     * @return boolean
     */
    public boolean deleteNullData() throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsHouseInfoDTO HLDTO = (AmsHouseInfoDTO) dtoParameter;
            AmsHouseInfoModel model = new AmsHouseInfoModel(userAccount, HLDTO);
            SQLModel sqlModel1 = model.deleteUsesInfo();
            DBOperator.updateRecord(sqlModel1, conn);

            String barcode = HLDTO.getBarcode();

//            super.updateData();            //对表EAM_house_INFO进行修改操作

            RentHouseModel updateEII = new RentHouseModel(userAccount, HLDTO);
            SQLModel nullSqlModel = updateEII.updateNull(HLDTO.getBarcode());
            DBOperator.updateRecord(nullSqlModel, conn);

//            itemInfoDTO.setBarcode(HLDTO.getBarcode());       // 标签号
//            itemInfoDTO.setItemCode(HLDTO.getItemCode());          //分类编码
//            itemInfoDTO.setSystemid(HLDTO.getSystemId());          // 　根据SYSTEMID进行修改；

            //--------------对表 EAM_ITEM_FILES  进行修改操作   先删除再增加
            AmsItemFilesModel sqlModel = new AmsItemFilesModel(userAccount, null);
            DBOperator.updateRecord(sqlModel.getDeleteModel(barcode), conn);
            operateResult = true;
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
            getMessage().addParameterValue("未处置信息");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } finally {
            try {
                if (hasError) {
                    conn.rollback(); //回滚
                }
                conn.setAutoCommit(autoCommit); //恢复以前状态
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean isTempSave(String barcode) throws QueryException {
        boolean has = false;
        RentHouseModel tsqlModel = new RentHouseModel(userAccount, null);
        SQLModel temModel = tsqlModel.isTempDate(barcode);
        SimpleQuery sqt = new SimpleQuery(temModel, conn);
        sqt.executeQuery();
        if (sqt.hasResult()) {
            has = true;
        }
//         isTempDate
        return has;
    }


    /**
     * 功能：删除租赁土地资产(EAM)表“AMS_LAND_INFO”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("土地资产(EAM)");
//		return operateResult;
    }


    /**
     * 功能：导出Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
//            SQLModel sqlModel = sqlProducer.getPageQueryModel();     //获得查询的sql
            AmsHouseInfoDTO HLDTO = (AmsHouseInfoDTO) dtoParameter;
            RentHouseModel updateEII = new RentHouseModel(userAccount, HLDTO);
            SQLModel sqlModel = updateEII.getHouseLandModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "已处理房屋土地信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "条码");
            fieldMap.put("ITEM_NAME", "名称");
            fieldMap.put("ITEM_SPEC", "型号");
            fieldMap.put("HOUSE_ADDRESS", "地点");
            fieldMap.put("LAND_CERTFICATE_NO", "土地证号");
            fieldMap.put("HOUSE_AREA", "房屋面积");
            fieldMap.put("OCCUPY_AREA", "占地面积");
            fieldMap.put("OFFICE_USAGE", "用途");
            fieldMap.put("OFFICE_TYPE", "类型");
            fieldMap.put("ATTRIBUTE2", "是否处理");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("已处理房屋土地信息表");
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

    /**
     * 功能：导出Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFileM() throws DataTransException {
        File file = null;
        try {
//            SQLModel sqlModel = sqlProducer.getPageQueryModel();     //获得查询的sql
            AmsHouseInfoDTO HLDTO = (AmsHouseInfoDTO) dtoParameter;
            RentHouseModel updateEII = new RentHouseModel(userAccount, HLDTO);
            SQLModel sqlModel = updateEII.getDispositionModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "租赁房屋土地信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "条码");
            fieldMap.put("ITEM_NAME", "名称");
            fieldMap.put("ITEM_SPEC", "型号");
            fieldMap.put("HOUSE_ADDRESS", "地点");
            fieldMap.put("HOUSE_AREA", "房屋面积(㎡)");
            fieldMap.put("OCCUPY_AREA", "土地面积(㎡)");
            fieldMap.put("OFFICE_USAGE", "用途");
            fieldMap.put("OFFICE_TYPE", "类型");
            fieldMap.put("HOUSE_CERTIFICATE_NO", "房产证号");
            fieldMap.put("LAND_CERTFICATE_NO", "土地证号");
            fieldMap.put("RENT_START_DATE", "租赁起始日期");
            fieldMap.put("RENT_END_DATE", "租赁截至日期");
            fieldMap.put("RENT_FEE", "总体租金");
            fieldMap.put("RENT_UNIT", "出租单位");
            fieldMap.put("CONTACT_PERSON", "联系人");
            fieldMap.put("CONTACT_PHONE", "联系电话");
//			fieldMap.put("ATTRIBUTE2", "是否处理");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("租赁房屋土地信息表");
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


    /**
     * 功能：导出未处理的Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportMisFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel(); //获得查询的sql
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "未导入房屋土地信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("TAG_NUMBER", "条码");
            fieldMap.put("ASSETS_DESCRIPTION", "名称");
            fieldMap.put("MODEL_NUMBER", "型号");
            fieldMap.put("ASSIGNED_TO_NAME", "责任人");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
            fieldMap.put("ASSETS_LOCATION", "地点");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("信息表");
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
        RentHouseModel getMisHousInfoModel = (RentHouseModel) sqlProducer;
        SQLModel sqlModel = getMisHousInfoModel.getVerifyBarcodeModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
    }


    public void creatRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
//		try {
        RentHouseModel getMisHousInfoModel = (RentHouseModel) sqlProducer;
//			SQLModel sqlModel = getMisHousInfoModel.doCreatRentData();
//			DBOperator.updateRecord(sqlModel, conn);
        prodMessage(CustMessageKey.ENABLE_SUCCESS);
        getMessage().addParameterValue("土地数据");
//		} catch (DataHandleException ex) {
        prodMessage(CustMessageKey.ENABLE_FAILURE);
        getMessage().addParameterValue("土地数据");
//			ex.printLog();
//		}
    }

    public void deleteRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
        try {
            RentHouseModel getMisHousInfoModel = (RentHouseModel) sqlProducer;
            SQLModel sqlModel = getMisHousInfoModel.doDeleteRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("土地数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("土地数据");
            ex.printLog();
        }
    }


    public boolean hasBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        RentHouseModel getMisHousInfoModel = (RentHouseModel) sqlProducer;
        SQLModel sqlModel = getMisHousInfoModel.getVerifyBarcodeModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
    }

    /**
     * 功能:判断最新的租赁信息是否在租期内。
     * @param barcode
     * @return
     * @throws QueryException
     */
    public boolean hasIsRent(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        RentHouseModel getMisHousInfoModel = (RentHouseModel) sqlProducer;
        SQLModel sqlModel = getMisHousInfoModel.isRentModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
    }

    /**
     * @param barcode
     * @throws QueryException
     */
    public boolean hasHouseBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        SQLModel sqlModel = modelProducer.getHouseModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();
        return success;
    }

    /**
     * 功能：导入mis的房屋和土地的信息
     * @return boolean
     */
    public boolean getMisInfo() throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            DTOSet dtos = getImportData();
            toEAMhouse(dtos);
            operateResult = true;
            conn.commit();
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (QueryException ex) {
            ex.printLog();
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback(); //回滚
                    prodMessage(MsgKeyConstant.SQL_ERROR);
                }
                conn.setAutoCommit(autoCommit); //恢复以前状态
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }


    /**
     * 功能：获得查询的ets_fa_assetsDTOSet.
     */
    public DTOSet getImportData() throws QueryException {
        SimpleQuery sq = new SimpleQuery(modelProducer.getPageQueryModel(), conn);
        sq.setDTOClassName(EtsFaAssetsDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }


    /**
     * 提交工单
     * @param dtoSet
     * @return
     * @throws QueryException
     * @throws DataHandleException
     */
    private boolean toEAMhouse(DTOSet dtoSet) throws DataHandleException {
        boolean operatorResult = false;
        try {
//            SystemItemUtil systemItemUtil = new SystemItemUtil();
            SystemItemHouse systemItemHouse = new SystemItemHouse();
            if (dtoSet != null && dtoSet.getSize() > 0) {
                EtsFaAssetsDTO faDTO = null;
                EtsItemInfoDTO itemDTO = null;
                AmsHouseInfoDTO houseDTO = null;
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    faDTO = (EtsFaAssetsDTO) dtoSet.getDTO(i);
                    itemDTO = new EtsItemInfoDTO();
                    itemDTO.setBarcode(faDTO.getTagNumber());
                    itemDTO.setItemName(faDTO.getAssetsDescription());
                    itemDTO.setItemSpec(faDTO.getModelNumber());
                    itemDTO.setItemQty(StrUtil.nullToString(faDTO.getCurrentUnits()));
                    itemDTO.setStartDate(faDTO.getDatePlacedInService().toString());
                    itemDTO.setItemCategory("HOUSE");
                    itemDTO.setAssetId(faDTO.getAssetId());
                    itemDTO.setOrganizationId(Integer.valueOf(faDTO.getOrganizationId()));
                    itemDTO.setFinanceProp("ASSETS");
//                    itemDTO = systemItemUtil.checkSysItem(conn, itemDTO);
                    itemDTO = systemItemHouse.checkSysItem(conn, itemDTO);
                    appendProjIdData(itemDTO, faDTO);
                    appendUserData(itemDTO, faDTO);
                    appendAddressData(itemDTO, faDTO);
                    if ( StrUtil.isEmpty( itemDTO.getAddressId() ) ) {
                        addAddressData(faDTO);
                        appendAddressData(itemDTO, faDTO);
                    }
                    houseDTO = new AmsHouseInfoDTO();
                    houseDTO.setBarcode(faDTO.getTagNumber());
                    houseDTO.setHouseAddress(faDTO.getAssetsLocation());
                    houseDTO.setHremark("house信息导入");
                    itemDTO.setSystemId(getNextSystemId());
                    itemDTO.setRemark("house信息导入");
                    if (!isExist(faDTO)) {
                        insertEtsItemInfo(itemDTO); // IN  ams_item_info
                    }
                    insertHouesInfo(houseDTO); // IN ams_house_info

                    matchData(itemDTO, faDTO);                                   //进行匹配操作
                }
            }
            operatorResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (ContainerException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
        return operatorResult;
    }

    /**
     * 功能：插入ets_item_info
     * @throws QueryException
     * @throws DataHandleException
     */
    private void insertEtsItemInfo(EtsItemInfoDTO etsItemInfo) throws DataHandleException {
        SQLModel sqlModel = null;
        try {
            sqlModel = modelProducer.getItemCreateModel(etsItemInfo);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：插入ETS_PA_PROJECTS_ALL
     * @throws QueryException
     * @throws DataHandleException
     */
    private void insertProjectInfo(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws DataHandleException {
        SQLModel sqlModel = null;
        try {
            sqlModel = modelProducer.getProjectCreateModel(itemDTO, faDTO);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：插入ams_house_info
     * @throws QueryException
     * @throws DataHandleException
     */
    private void insertHouesInfo(AmsHouseInfoDTO houseDTO) throws DataHandleException {
        SQLModel sqlModel = modelProducer.insertHouse(houseDTO);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：获得MIS的员工信息
     * @throws QueryException
     * @throws ContainerException
     */
    private void appendUserData(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws QueryException {
        try {
            SQLModel sqlModel = modelProducer.getMISemployee(faDTO.getAssignedToNumber());
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                itemDTO.setResponsibilityDept(row.getStrValue("DEPT_CODE"));
                itemDTO.setResponsibilityUser(row.getStrValue("EMPLOYEE_ID"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 功能：获得MIS的员工信息
     * @throws QueryException
     * @throws ContainerException
     */
    private void appendAddressData(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws QueryException {
        try {
            SQLModel sqlModel = modelProducer.getLocId(faDTO.getAssetsLocationCode(), String.valueOf(faDTO.getOrganizationId()));
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                itemDTO.setAddressId((String)row.getValue("ADDRESS_ID"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 功能：获得ProjectId
     * @throws QueryException
     * @throws ContainerException
     */
    private void appendProjIdData(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws QueryException {
        try {
            SQLModel sqlModel = modelProducer.getProId(faDTO.getProjectid());
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                itemDTO.setProjectid((String)row.getValue("PROJECT_ID"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 功能:判断barcode的存在性。
     * @return
     */

    private boolean isExist(EtsFaAssetsDTO faDTO) throws QueryException {
        boolean hasResule = false;
        SQLModel sqlModel = modelProducer.hasBarcode(faDTO.getTagNumber());
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            hasResule = true;
        }
        return hasResule;
    }

    /**
     * 功能:判断项目id的存在性。
     * @return
     */

    private boolean isProjectId(EtsFaAssetsDTO faDTO) throws QueryException {
        boolean hasResule = false;
        SQLModel sqlModel = modelProducer.hasProjId(faDTO.getProjectid());
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            hasResule = true;
        }
        return hasResule;
    }

    public void saveUses(DTOSet lineSet) throws DataHandleException, QueryException {
        AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
        if (lineSet != null && !lineSet.isEmpty()) {
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsHouseUsesDTO lineData = (AmsHouseUsesDTO) lineSet.getDTO(i);
                lineData.setBarcode(houseDTO.getBarcode());
                if (!(lineData.getArea().equals("") || lineData.getUsage().equals(""))) {
                    RentHouseModel model = new RentHouseModel(userAccount, houseDTO);
                    SQLModel sqlModel = model.insertUsesInfo(lineData);
                    DBOperator.updateRecord(sqlModel, conn);
                }

            }
        }
    }

    public DTOSet getUses() throws QueryException {
        RentHouseModel model = (RentHouseModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getUsesInfo(), conn);
        sq.setDTOClassName(AmsHouseUsesDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }


    private String getNextSystemId() throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getGUID();
    }


    /**
     * 功能：插入地点信息
     * @throws DataHandleException
     */
    private void addAddressData(EtsFaAssetsDTO faDTO) throws DataHandleException, QueryException {
        try {
            EtsObjectDTO obDTO = new EtsObjectDTO();
            obDTO.setWorkorderObjectCode(faDTO.getAssetsLocationCode());
            obDTO.setWorkorderObjectName(faDTO.getAssetsLocation());
            obDTO.setWorkorderObjectLocation(faDTO.getAssetsLocation());
            obDTO.setObjectCategory("81");
            SQLModel sqlModel = modelProducer.getCount(faDTO.getDepreciationAccount().substring(5, 11));
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (sq.hasResult()) {
                Row row = sq.getFirstRow();
                obDTO.setCountyCode(row.getStrValue("COUNTY_CODE"));
            }
            EtsObjectDAO etsObjectDAO = new EtsObjectDAO(userAccount, obDTO, conn);
            etsObjectDAO.createData2();
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 功能：插入地点信息
     * @throws DataHandleException
     */
    private void matchData(EtsItemInfoDTO itemDTO, EtsFaAssetsDTO faDTO) throws DataHandleException {
        String[] systemids;
        BarcodeMatchDTO baDTO = new BarcodeMatchDTO();
        systemids = new String[]{itemDTO.getSystemId() + "@@@" + faDTO.getAssetId()};
        BarcodeMatchDAO barcodeMatchDAO = new BarcodeMatchDAO(userAccount, baDTO, conn);
        barcodeMatchDAO.doMatch(systemids);
    }

    /**
     * 功能:生成租赁房屋土地的标签号。
     * @param assetsType
     * @param quantity
     */

    public String doExport(String assetsType, int quantity) {
        String barcode = "";
        try {
            int firstAssetNumber = GenBarcode.getAssetNumber(conn, userAccount.getCompanyCode(), assetsType, quantity);
            for (int i = 1; i <= quantity; i++) {
                DecimalFormat df = new DecimalFormat("00000000");
                if (assetsType.equals("")) {
                    barcode = userAccount.getCompanyCode() + "-" + df.format(firstAssetNumber);
                } else {
                    df = new DecimalFormat("000000");
                    barcode = userAccount.getCompanyCode() + "-" + assetsType + df.format(firstAssetNumber);
                }
                firstAssetNumber++;
            }
        } catch (SQLException e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        }
        return barcode;
    }

    /**
     * 功能：获取详细数据。
     * @return Object DTO的实现类或者Row，获取后根据是否设置DTOClassName进行类型强制转换。
     * @throws QueryException
     */
    public AmsHouseInfoDTO getRentInfo() throws QueryException {
        AmsHouseInfoDTO houseDTO = null;
        SQLModel sqlModel = modelProducer.getRentInfoModel();
        SimpleQuery spl = new SimpleQuery(sqlModel, conn);
        spl.executeQuery();
        if (spl.hasResult()) {
            houseDTO = (AmsHouseInfoDTO) spl.getFirstDTO();
        }
        return houseDTO;
    }

    /**
     * 功能:判断是否全省租赁管理员;
     * @return
     */
    public String isProvince() throws QueryException {
        String isProvince = "N";
        SQLModel sqlModel = null;
        RentHouseModel sqlProducer = new RentHouseModel(userAccount, null);
        sqlModel = sqlProducer.isProvince();
        SimpleQuery spl = new SimpleQuery(sqlModel, conn);
        spl.executeQuery();
        if (spl.hasResult()) {
            isProvince = "Y";
        }
        return isProvince;
    }


}
