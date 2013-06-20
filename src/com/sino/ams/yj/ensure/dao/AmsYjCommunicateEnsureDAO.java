package com.sino.ams.yj.ensure.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.*;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.ensure.dto.AmsYjCommunicateEnsureDTO;
import com.sino.ams.yj.ensure.model.AmsYjCommunicateEnsureModel;


/**
 * <p>Title: AmsYjCommunicateEnsureDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjCommunicateEnsureDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急通信保障情况
 */


public class AmsYjCommunicateEnsureDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjCommunicateEnsureDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsYjCommunicateEnsureDAO(SfUserDTO userAccount, AmsYjCommunicateEnsureDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsYjCommunicateEnsureDTO dtoPara = (AmsYjCommunicateEnsureDTO) dtoParameter;
        super.sqlProducer = new AmsYjCommunicateEnsureModel((SfUserDTO) userAccount, dtoPara);
    }

    public boolean saveDate(boolean isNew) {
        boolean flag = false;
        try {
            if (isNew) {
                createData();
            } else {
                updateData();
            }
            flag = true;
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
        return flag;
    }

    public void deleteAllData(String ids) throws DataHandleException {
        AmsYjCommunicateEnsureModel ensureModel = (AmsYjCommunicateEnsureModel) sqlProducer;
        SQLModel sqlModel = ensureModel.getDeleteAllData(ids);
        DBOperator.updateRecord(sqlModel, conn);
    }

    public File getExportFile() throws DataTransException, SQLModelException {
        AmsYjCommunicateEnsureModel modelProducer = (AmsYjCommunicateEnsureModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getExportModel();
        String reportTitle = "应急通信保障事件统计表";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        rule.setFieldMap(getFieldMap());
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle(reportTitle);
        custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        rule.setCalPattern(LINE_PATTERN);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        
        fieldMap.put("DEPT_NAME", "单位");
        fieldMap.put("EVENT_1", "政治经济事件类");
        fieldMap.put("EVENT_2", "节假日保障类");
        fieldMap.put("EVENT_3", "自然灾害类");
        fieldMap.put("EVENT_4", "事故灾难类");
        fieldMap.put("EVENT_5", "公共卫生事件类");
        fieldMap.put("EVENT_6", "社会安全事件类");
        fieldMap.put("ITEM_1", "投入人数");
        fieldMap.put("ITEM_2", "投入人次");
        fieldMap.put("ITEM_3", "应急车数量");
        fieldMap.put("ITEM_4", "应急车车次");
        fieldMap.put("ITEM_5", "应急通信设备数");
        fieldMap.put("ITEM_6", "应急通信设备套次");

        return fieldMap;
    }

     public File getExpFile() throws DataTransException {
        File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "应急通信保障事件信息表";
			String fileName = reportTitle + ".xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = new HashMap();
			fieldMap.put("COMMUNICATE_ID", "序号");
			fieldMap.put("DEPT_NAME", "单位");
			fieldMap.put("ENSURE_NAME", "通信保障名称");
			fieldMap.put("EVENT_TYPE", "事件类型");
			fieldMap.put("ENSURE_DATE_FROM", "保障时间从");
			fieldMap.put("ENSURE_DATE_TO", "保障时间到");
			fieldMap.put("ENSURE_LOCATION", "保障地点");
			fieldMap.put("MANPOWER_QTY", "投入人数");
			fieldMap.put("MANPOWER_TIMES", "投入人次");
			fieldMap.put("COMVAN_QTY", "应急车数量");
			fieldMap.put("COMVAN_TIMES", "应急车车次");
			fieldMap.put("EQUIPMENT_QTY", "应急通信设备数");
			fieldMap.put("EQUIPMENT_UNIT", "应急通信设备套次");
			fieldMap.put("BLOCK_DEGREE", "通信阻断程度");
			fieldMap.put("LOSS_CONDITION", "损失情况");
			fieldMap.put("ENSURE_MEASURE", "应急保障措施");
			fieldMap.put("RECOVER_SITUATION", "通信恢复情况及时间");
			fieldMap.put("GOVERNMENT_EVALUATE", "地方政府整体评价");
			fieldMap.put("REASON_AFFECT", "事件原因及影响范围");
			fieldMap.put("QUESTION", "存在的问题");
			fieldMap.put("GUARD_MEASURE", "未来防范措施");

			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(sfUser.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
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
      * 字符串转换
      */
     public String appendIntComvanId(String[] srcArr, String linkStr) {
         String retStr = "";
         if (srcArr != null && srcArr.length > 0) {
             boolean hasProcessed = false;
             for (int i = 0; i <= srcArr.length - 1; i++) {
             	retStr += "CONVERT(FLOAT, "+srcArr[i]+")"+ linkStr;
                 hasProcessed = true;
             }
             if (hasProcessed) {
                 retStr = retStr.substring(0, retStr.length() - linkStr.length());
             }
         }
         return retStr;
     }  
     
     /**
      * 取当前应急类型的最大编号
      * @return
      */
 	public int getYjManagerMax(String yj_type) {
     	int isSyn= 0;
         CallableStatement cStmt = null;
         String sqlStr = "{call dbo.YJ_MANAGE_GET_MAX(?,?)}";
         try {
 			cStmt = conn.prepareCall(sqlStr);
 			cStmt.setString(1,yj_type);
 			cStmt.registerOutParameter(2,java.sql.Types.INTEGER);
             cStmt.execute();
             isSyn= cStmt.getInt(2);
             System.out.println("yyyyyy====="+isSyn);
 		} catch (SQLException e) {
 			e.printStackTrace();
 			isSyn= -1;
 		} finally {
             DBManager.closeDBStatement(cStmt);
         }
         return isSyn;
     }
     
 
     
     
}