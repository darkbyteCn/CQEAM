package com.sino.ams.system.specialty.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.system.specialty.dto.OtherDTO;
import com.sino.ams.system.specialty.model.OtEqVindicateModel;
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
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-20
 * Time: 11:52:56
 * To change this template use File | Settings | File Templates.
 */
public class OtEqVindicateDAO extends AMSBaseDAO {
     private SfUserDTO sfUser = null;


    /**
     * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public OtEqVindicateDAO(SfUserDTO userAccount, OtherDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        OtherDTO dtoPara = (OtherDTO) dtoParameter;
        super.sqlProducer = new OtEqVindicateModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入标签号信息(EAM)表“ETS_ITEM_INFO”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("标签号信息(EAM)");
    }

    /**
     * 功能：更新标签号信息(EAM)表“ETS_ITEM_INFO”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("标签号信息(EAM)");
    }

    /**
     * 功能：删除标签号信息(EAM)表“ETS_ITEM_INFO”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("标签号信息(EAM)");
    }

    public String insertData() throws SQLException, DataHandleException, QueryException {
        String msg = "";
        try {
            conn.setAutoCommit(false);
            OtherDTO dto = (OtherDTO) dtoParameter;
            OtEqVindicateModel model = new OtEqVindicateModel(sfUser, dto);
            SimpleQuery sq = new SimpleQuery(model.selectItemInfo(), conn);  //检查 表ETS_SYSTEM_ITEM 的唯一性
            sq.executeQuery();

            if (sq.getSearchResult().getSize() > 0) {
                DBOperator.updateRecord(model.updateModel(), conn);          //如果有即对表 ETS_SYSTEM_ITEM 执行修改操作

//                DBOperator.updateRecord(model.insertIntoD(), conn);
            } else {
                SeqProducer seq = new SeqProducer(conn);
                String itemCode = StrUtil.nullToString(seq.getStrNextSeq("ETS_SYSTEM_ITEM_S"));
                dto.setItemCode(itemCode);
                DBOperator.updateRecord(model.insertIntoItem(), conn);     //对表 ETS_SYSTEM_ITEM 执行增加操作
                SimpleQuery sq1 = new SimpleQuery(model.selectDis(itemCode), conn);  //对表 ETS_SYSITEM_DISTRIBUTE 进行判断
                sq1.executeQuery();
                if (sq1.getSearchResult().getSize() > 0) {
                } else {
                    DBOperator.updateRecord(model.insertIntoDis(itemCode), conn);   //增加信息到表 ETS_SYSITEM_DISTRIBUTE
                }
               DBOperator.updateRecord(model.insertIntoApp(itemCode), conn);     //对表AMS_APPLY_SYSTEM_ITEM 进行新增操作                                                   //对表AMS_APPLY_SYSTEM_ITEM 新增操作
            }
            createData();                                 //无论无何都对表 ETS_ITEM_INFO  进行新增的操作
            conn.commit();

        } catch (SQLException e) {
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
        return msg;
    }

    public String simpleQueryData(String itemCategory) throws QueryException {
        String itemCategroyDesc = "";
        OtherDTO etsItemInfoDTO = (OtherDTO) dtoParameter;
        OtEqVindicateModel a = new OtEqVindicateModel(sfUser, etsItemInfoDTO);
        SQLModel sModel = a.getItemCategoryModel(itemCategory);
        SimpleQuery sQuery = new SimpleQuery(sModel, conn);
        sQuery.executeQuery();
        if (sQuery.hasResult()) {
            RowSet row = sQuery.getSearchResult();
            try {
                itemCategroyDesc = (String) row.getRow(0).getValue("ITEM_CATEGORY_DESC");
            } catch (ContainerException e) {
                e.printStackTrace();
                throw new QueryException();
            }
        }

        return itemCategroyDesc;
    }


    public String getCode() throws QueryException {
        String companyCode = "";
        OtherDTO etsItemInfoDTO = (OtherDTO) dtoParameter;
        OtEqVindicateModel model1 = new OtEqVindicateModel(sfUser, etsItemInfoDTO);
        SQLModel sModel = model1.getCode();
        SimpleQuery sQuery = new SimpleQuery(sModel, conn);
        sQuery.executeQuery();
        if (sQuery.hasResult()) {
            RowSet row = sQuery.getSearchResult();
            try {
                companyCode = (String) row.getRow(0).getValue("COMPANY_CODE");
            } catch (ContainerException e) {
                e.printStackTrace();
                throw new QueryException();
            }
        }
        return companyCode;
    }

    /**
     * 功能：失效选定设备
     * @param systemIds String[]
     */
    public void disableItem(String systemIds) {
        try {
            OtEqVindicateModel a = (OtEqVindicateModel) sqlProducer;
            SQLModel sModel = a.getDisableModel(systemIds);
            DBOperator.updateRecord(sModel, conn);
            prodMessage(CustMessageKey.DISABLE_SUCCESS);
            getMessage().addParameter("设备");
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(CustMessageKey.DISABLE_FAILURE);
            getMessage().addParameter("设备");
        }
    }

    /**
     * 功能：生效选定设备
     * @param systemIds String[]
     */
    public void enableItem(String systemIds) {
        try {
            OtEqVindicateModel a = (OtEqVindicateModel) sqlProducer;
            SQLModel sModel = a.getEnableModel(systemIds);
            DBOperator.updateRecord(sModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameter("设备");
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameter("设备");
        }
    }

    /**
     * 功能：导出Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            OtherDTO itemDTO = (OtherDTO) dtoParameter;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = itemDTO.getItemCategoryDesc() + "统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE1", "标签号");
            fieldMap.put("ITEM_QTY", "设备数量");
            fieldMap.put("DISABLE_DATE", "失效日期");
            fieldMap.put("VENDOR_NAME", "供应商");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("ITEM_CATE_GORY_DESC", "设备类别");
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

     public boolean verifyBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
//        try {
        OtEqVindicateModel otModel = (OtEqVindicateModel) sqlProducer;
        SQLModel sqlModel = otModel.getVerifyBarcodeModel(barcode);

        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        success = sq.hasResult();

        return success;
    }
}
