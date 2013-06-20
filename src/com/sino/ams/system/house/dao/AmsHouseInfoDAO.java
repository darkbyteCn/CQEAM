package com.sino.ams.system.house.dao;


import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.model.AmsHouseInfoModel;
import com.sino.ams.system.house.model.AmsItemFilesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
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
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsHouseInfoDAO</p>
 * <p>Description:程序自动生成服务程序“AmsHouseInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsHouseInfoDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    private int orderLength;

    /**
     * 功能：租赁房屋(EAM) AMS_HOUSE_INFO 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsHouseInfoDAO(SfUserDTO userAccount, AmsHouseInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsHouseInfoDTO dtoPara = (AmsHouseInfoDTO) dtoParameter;
        super.sqlProducer = new AmsHouseInfoModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     * @return boolean
     */
    public boolean createData(EtsItemInfoDTO itemInfoDTO, String systemId, String[] filePaths, DTOSet lineSet) throws DataHandleException, QueryException, SQLModelException {          //do_save操作
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            String barcode = houseDTO.getBarcode();
            itemInfoDTO.setItemCode(houseDTO.getItemCode());          //分类编码
//            itemInfoDTO.setBarcode(getOrderNum());
            itemInfoDTO.setBarcode(houseDTO.getBarcode());         //获得Barcode
            itemInfoDTO.setAddressId( houseDTO.getAddressId() );        //获得 address_Id
//            itemInfoDTO.setAttribute1(DictConstant.HOUSE);

//            itemInfoDTO.setBarcode(getBarcode1(houseDTO));         //获得加工的Barcode
            EtsItemInfoDAO itemInfoDAO = new EtsItemInfoDAO(sfUser, itemInfoDTO, conn);


            boolean hasBarcode = hasBarcode(houseDTO.getBarcode());
            if (hasBarcode) {                                       //如果在表ETS_ITEM_INFO存在Barcode即对其进行修改，否则进行插入操作
                itemInfoDAO.updateAttribute1(houseDTO.getBarcode());             //跟据Barcode对表ETS_ITEM_INFO进行修改操作，即对 ‘ATTRIBUTE1’的值改为"HOUSE"  改用 Barcode


            } else {
                itemInfoDAO.createData();                   //对表 ETS_ITEM_INFO 进行插入操作
            }

//             itemInfoDAO.createData();

//            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO)dtoParameter;
//            houseDTO.setBarcode(itemInfoDTO.getBarcode());     //获的与表 ETS_ITEM_INFO 相同的barcode
//            setDTOParameter(houseDTO);                            //重设dto

            super.createData();                             //对表 AMS_HOUSE_INFO 进行插入操作
            saveUses(lineSet);
            if (houseDTO.getIsRent().equals("Y")) {                                          //判断表  AMS_HOUSE_INFO 中的字段 ‘IS_RENT’的值 ；1，若=‘Y’对表 AMS_RENT_INFO 进行插入操作

                creatRentData();                              //插入表AMS_RENT_INFO字段 barcode 对其主键 RENT_ID 自动生成
            }

            //---对表AMS_ITEM_FILES进行操作
            if (!StrUtil.isEmpty(filePaths)) {
                for (int i = 0; i < filePaths.length; i++) {
                    AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
                    fileDTO.setBarcode(barcode);
//                 String filedp[]= filePaths[i].split("$");
                    String filedp[] = StrUtil.splitStr(filePaths[i], "$");
                    fileDTO.setFileDesc(filedp[0]);
                    fileDTO.setFilePath(filedp[1]);
//                 System.out.println("--------------------------------------------------");
//                 System.out.println("filedp[0]&filedp[1]="+filedp[0]+"&"+filedp[1]);
                    AmsItemFilesDAO amsItemFilesDAO = new AmsItemFilesDAO(userAccount, fileDTO, conn);
                    amsItemFilesDAO.setDTOClassName(AmsItemFilesDTO.class.getName());
                    amsItemFilesDAO.createData();
//                forwardURL="/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?barcode=UPLOAD_ACTION&fileDesc="+filePath[i];
                }

            }


            operateResult = true;
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
            getMessage().addParameterValue("房屋信息");
        } catch (SQLException ex) {
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

    public void saveUses(DTOSet lineSet) throws DataHandleException, QueryException {
        AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
        if (lineSet != null && !lineSet.isEmpty()) {
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsHouseUsesDTO lineData = (AmsHouseUsesDTO) lineSet.getDTO(i);
                lineData.setBarcode(houseDTO.getBarcode());
                if (!(lineData.getArea().equals("") || lineData.getUsage().equals(""))) {
                    AmsHouseInfoModel model = new AmsHouseInfoModel(sfUser, houseDTO);
                    SQLModel sqlModel = model.insertUsesInfo(lineData);
                    DBOperator.updateRecord(sqlModel, conn);
                }

            }
        }
    }

    public DTOSet getUses() throws QueryException {
        AmsHouseInfoModel model = (AmsHouseInfoModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getUsesInfo(), conn);
        sq.setDTOClassName(AmsHouseUsesDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    public void upAttribute1Data(String systemId) { //执行ETS_ITEM_INFO 表的 ATTRIBUTE1字段的修改操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.getAttribute1Model(systemId);
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }

    /**
     * 功能：更新租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     * @return boolean
     */
    public boolean updateData(EtsItemInfoDTO itemInfoDTO, AmsItemFilesDTO fileDTO, String[] filePaths, DTOSet lineSet) throws DataHandleException, SQLModelException, QueryException {                     //修改数据

//        boolean operateResult = super.updateData();

//        getMessage().addParameterValue("租赁房屋数据");
//		return operateResult;
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsHouseInfoDTO houseDTO = (AmsHouseInfoDTO) dtoParameter;
            AmsHouseInfoModel model = new AmsHouseInfoModel(sfUser, houseDTO);
            SQLModel sqlModel1 = model.deleteUsesInfo();
            DBOperator.updateRecord(sqlModel1, conn);

            sqlModel1 = model.getDataDeleteModel();
            DBOperator.updateRecord(sqlModel1, conn);
            String barcode = houseDTO.getBarcode();
            if (houseDTO.getIsRent().equals("Y")) {
                DBOperator.updateRecord(model.getAttribute1Model(barcode), conn);
            } else if (houseDTO.getIsRent().equals("N")) {
                DBOperator.updateRecord(model.getAttribute1NotModel(barcode), conn);
            }
            sqlModel1 = model.getDataCreateModel();
            DBOperator.updateRecord(sqlModel1, conn);
//            houseDTO.setBarcode(getBarcode());
//            setDTOParameter(houseDTO);

//            super.updateData();            //对表AMS_HOUSE_INFO进行修改操作
//            itemInfoDTO.setBarcode(houseDTO.getBarcode());       // 标签号
//            itemInfoDTO.setItemCode(houseDTO.getItemCode());          //分类编码
//            itemInfoDTO.setSystemid(houseDTO.getSystemId());          // 　根据SYSTEMID进行修改；
//            EtsItemInfoDAO itemInfoDAO = new EtsItemInfoDAO(sfUser,itemInfoDTO,conn);
//            itemInfoDAO.updateData();              //对表ETS_ITEM_INFO进行修改操作

            saveUses(lineSet);
            if (houseDTO.getIsRent().equals("Y")) {        //表 AMS_RENT_INFO （RENT_ID ） 修改  先删除 再增加
                deleteRentData();
                creatRentData();

            } else {
                deleteRentData();             //对表  AMS_RENT_INFO 进行删除操作
            }

            //--------------对表 AMS_ITEM_FILES  进行修改操作   先删除再增加
            AmsItemFilesModel sqlModel = new AmsItemFilesModel(sfUser, fileDTO);
            DBOperator.updateRecord(sqlModel.getDeleteModel(barcode), conn);
            if (!StrUtil.isEmpty(filePaths)) {
                for (int i = 0; i < filePaths.length; i++) {
//                 AmsItemFilesDTO fileDTO= new AmsItemFilesDTO();
                    fileDTO.setBarcode(barcode);
//                 String filedp[]= filePaths[i].split("$");
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
            getMessage().addParameterValue("房屋信息");
        } catch (SQLException ex) {
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

    /**
     * 功能：删除租赁房屋(EAM)表“AMS_HOUSE_INFO”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
//		boolean operateResult = super.deleteData();
        super.deleteData();
        getMessage().addParameterValue("租赁房屋数据");
//		return operateResult;
    }


    private int getBarcode() throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getStrNextSeq("AMS_HOUSE_INFO_S");
    }


    private String getBarcode1(AmsHouseInfoDTO houseDTO) throws SQLException {
        String barcode1 = sfUser.getCompanyCode();
        String barcode2 = barcode1 + '-';
        String barcode3 = houseDTO.getBarcode();
        String barcode4 = barcode2 + barcode3;
        return barcode4;
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
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "房屋信息表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "房屋条码");
            fieldMap.put("HOUSE_CERTIFICATE_NO", "产权证号");
            fieldMap.put("COUNTY_NAME", "所在区县");
            fieldMap.put("HOUSE_ADDRESS", "所在地址");
            fieldMap.put("IS_RENT", "是否租赁");
            fieldMap.put("RENT_PERSON", "出租人");
            fieldMap.put("RENT_DATE", "租赁日期");
            fieldMap.put("RENTAL", "租金");
            fieldMap.put("END_DATE", "截至日期");
            rule.setFieldMap(fieldMap);
            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("房屋信息表");
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


    public boolean uploadFiles(UploadFile[] files) {
        boolean operateResult = false;
//        getMessage().addParameterValue("");
        return operateResult;
    }


    public void disabledData(String[] systemIds) { //执行批量失效操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.getDisableModel(systemIds);
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.DISABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.DISABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }

    public void efficientData(String[] systemIds) { //执行批量生效效操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.getEnableModel(systemIds);
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }


    public void creatRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.doCreatRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }

    public void deleteRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的新增操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.doDeleteRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }


    public void updateRentData() throws SQLModelException { //执行AMS_RENT_INFO 表的修改操作
        try {
            AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
            SQLModel sqlModel = amsHouseInfoModel.doUpdateRentData();
            DBOperator.updateRecord(sqlModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("租赁房屋数据");
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("租赁房屋数据");
            ex.printLog();
        }
    }

    public boolean verifyBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
//        try {
        AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
        SQLModel sqlModel = amsHouseInfoModel.getVerifyBarcodeModel(barcode);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

//                  DBOperator.updateRecord(sqlModel, conn);
//			prodMessage(CustMessageKey.DISABLE_SUCCESS);
//			getMessage().addParameterValue("租赁房屋数据");
//             success = true;
//        } catch (DataHandleException ex) {
//			prodMessage(CustMessageKey.DISABLE_FAILURE);
//			getMessage().addParameterValue("租赁房屋数据");
//			ex.printLog();

//        } catch (QueryException ex) {
//            prodMessage(CustMessageKey.DISABLE_FAILURE);
//            getMessage().addParameterValue("租赁房屋数据");
//            ex.printLog();
//        }
        return success;
    }

    public boolean hasBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
        AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
        SQLModel sqlModel = amsHouseInfoModel.getVerifyBarcodeModel(barcode);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

        return success;
    }

//      public boolean verifyProjectSegment1() throws QueryException {
//        boolean success = false;
//        sqlModel = projectModel.getVerifyProjectSegment1Model();
//        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
//        sq.setDTOClassName(ProjectDTO.class.getName());
//        sq.executeQuery();
//        ProjectDTO projectDTO = (ProjectDTO) sq.getFirstDTO();
//        if (!StrUtil.isEmpty(projectDTO)) {
//            success = true;
//        }
//        return success;
//    }
//
//       public boolean hasManager() throws QueryException {
//          boolean hasManager = false;
//          sqlModel = projectMaintenceModel.getHasPriviledgeModel();
//          SimpleQuery sq = new SimpleQuery(sqlModel, conn);
//          sq.executeQuery();
//          hasManager = sq.hasResult();
//          return  hasManager;
//      }
    /**
     * 功能：生成Barcode单据号。
     *
     * @return ORDER_NUMBER
     * @throws
     */
    public String getOrderNum() {
        String no = null;
        String companyCode = "";
        companyCode = sfUser.getCompanyCode();
        CallableStatement cst = null;
        String type = null; //生成什么类型的编号
        String sqlStr = "{CALL dbo.AONP_GET_ORDER_NO(?, ?, ?, ?)}";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.setString(1, companyCode);
            cst.setString(2, type);
            cst.setInt(3, orderLength);
            cst.registerOutParameter(4, Types.VARCHAR);
            conn.setAutoCommit(true);            
            cst.execute();
            no = cst.getString(4);
        } catch (SQLException e) {
            Logger.logError(e);

        } finally {
            DBManager.closeDBStatement(cst);
        }
        return no;
    }
}
