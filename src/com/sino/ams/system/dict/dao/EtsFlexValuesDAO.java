package com.sino.ams.system.dict.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.system.dict.dto.EtsFlexValuesDTO;
import com.sino.ams.system.dict.model.EtsFlexValuesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsFlexValuesDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFlexValuesDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsFlexValuesDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：字典分类表(EAM) ETS_FLEX_VALUES 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsFlexValuesDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsFlexValuesDAO(SfUserDTO userAccount, EtsFlexValuesDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsFlexValuesDTO dtoPara = (EtsFlexValuesDTO) dtoParameter;
        super.sqlProducer = new EtsFlexValuesModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入字典分类表(EAM)表“ETS_FLEX_VALUES”数据。
     * @return boolean
     */
    public void createData() {
        saveData(DBActionConstant.INSERT);
    }

    /**
     * 功能：更新字典分类表(EAM)表“ETS_FLEX_VALUES”数据。
     * @return boolean
     */
    public void updateData() {
        saveData(DBActionConstant.UPDATE);
    }

    /**
     * 功能：保存字典分类表(EAM)表“ETS_FLEX_VALUES”数据。
     * @param dbAction String
     * @return boolean
     */
    public boolean saveData(String dbAction) {
        boolean operateResult = false;
        try {
            String tableName = "ETS_FLEX_VALUES";
//            DataUniqueChecker dataChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
//            dataChecker.setDBAction(dbAction);
//            dataChecker.setServletConfig(servletConfig);
//            if (dataChecker.isDataValid()) {
                if (dbAction.equals(DBActionConstant.INSERT)) {
                    super.createData();
                } else {
                    super.updateData();
                }
                getMessage().addParameterValue("字典");
//            } else {
//                EtsFlexValuesDTO dtoPara = (EtsFlexValuesDTO) dtoParameter;
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR_2);
//                message.addParameterValue(dtoPara.getValue());
//                message.addParameterValue(dtoPara.getFlexValueSetName());
//                message.setIsError(true);
//            }
        } 
//        catch (ValidateException ex) {
//            ex.printLog();
//            prodMessage(MsgKeyConstant.COMMON_ERROR);
//            message.setIsError(true);
//        } 
        catch (DataHandleException e) {
            e.printStackTrace();
        }
        return operateResult;
    }

    /**
     * 功能：删除字典分类表(EAM)表“ETS_FLEX_VALUES”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("字典");
	}
    /**
     * 功能：导出Excel文件。
     * @return File
     * @throws DataTransException
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {

            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
            
            String title = "系统字典维护";
            String fileName = "系统字典维护.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("DICT_TYPE_CODE", "分类代码");
            fieldMap.put("DICT_TYPE_NAME", "分类名称");
            fieldMap.put("CODE", "字典代码");
            fieldMap.put("VALUE", "字典值");
            fieldMap.put("DESCRIPTION", "字典描述");
            fieldMap.put("ENABLED", "是否有效");
            fieldMap.put("IS_INNER", "是否内置");
            fieldMap.put("MAINTAIN_FLAG", "可否维护");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(title);
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
}
