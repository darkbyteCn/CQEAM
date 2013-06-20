package com.sino.ams.yj.resource.dao;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.base.util.ArrUtil;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.datatrans.*;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.resource.dto.AmsYjCommunicateResourceDTO;
import com.sino.ams.yj.resource.model.AmsYjCommunicateResourceModel;

/**
 * <p>Title: AmsYjCommunicateResourceDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjCommunicateResourceDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-战备应急通信资源
 */

public class AmsYjCommunicateResourceDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjCommunicateResourceDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsYjCommunicateResourceDAO(SfUserDTO userAccount, AmsYjCommunicateResourceDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsYjCommunicateResourceDTO dtoPara = (AmsYjCommunicateResourceDTO) dtoParameter;
        super.sqlProducer = new AmsYjCommunicateResourceModel((SfUserDTO) userAccount, dtoPara);
    }

    public boolean saveData(boolean isNew) {
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

    public boolean deleteAllData(String ids) {
        boolean flag = false;
        AmsYjCommunicateResourceModel resourceModel=(AmsYjCommunicateResourceModel)sqlProducer;
        SQLModel sqlModel=resourceModel.getDeleteDataModel(ids);
        try {
            DBOperator.updateRecord(sqlModel,conn);
            flag=true;
        } catch (DataHandleException e) {
            Logger.logError(e);
        }

        return flag;
    }

    public File getExportFile() throws DataTransException {
        File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "战备应急通信资源信息表";
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
			fieldMap.put("RESOURCE_ID", "序号");
			fieldMap.put("DEPT_NAME", "单位名称 ");
			fieldMap.put("EQUIPMENT_NAME", "装备名称");
			fieldMap.put("RESOURCE_QTY", "数量");
			fieldMap.put("LOCATION", "置放位置");
			fieldMap.put("MODEL", "规格");
			fieldMap.put("NORMAL_STATUS", "基本性能");
			fieldMap.put("CHARGE_DEPT", "主管部门");
			fieldMap.put("CHARGER", "负责人");
			fieldMap.put("CHARGER_MOBILE", "手机");
			fieldMap.put("KEEPER", "保管人");
			fieldMap.put("KEEPER_MOBILE", "手机");
			fieldMap.put("REMARK", "备注");

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
     * 统计数量
     * @return
     * @throws DataTransException
     * @throws SQLModelException
     */
    public File getExpFile() throws DataTransException, SQLModelException {
           AmsYjCommunicateResourceModel modelProducer = (AmsYjCommunicateResourceModel) sqlProducer;
           SQLModel sqlModel = modelProducer.getExportModel();
           String reportTitle = "战备应急通信资源统计表";
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

           fieldMap.put("COMPANY", "公司名称");
           fieldMap.put("EQUIPMENT_NAME", "装备名称");
           fieldMap.put("QTY", "数量");
      
           return fieldMap;
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

}