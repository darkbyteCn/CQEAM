package com.sino.ams.yj.dao;


import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjIsatphoneDTO;
import com.sino.ams.yj.model.AmsYjIsatphoneModel;


/**
 * <p>Title: AmsYjIsatphoneDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjIsatphoneDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-卫星电话维护
 */


public class AmsYjIsatphoneDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：卫星电话信息表 AMS_YJ_ISATPHONE 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjIsatphoneDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsYjIsatphoneDAO(SfUserDTO userAccount, AmsYjIsatphoneDTO dtoParameter, Connection conn) {
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
        AmsYjIsatphoneDTO dtoPara = (AmsYjIsatphoneDTO) dtoParameter;
        super.sqlProducer = new AmsYjIsatphoneModel((SfUserDTO) userAccount, dtoPara);
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
            DataTransfer transfer = null;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);

            String fileName = "卫星电话信息.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ORGANIZATION_NAME", "公司名称");
            fieldMap.put("ISATPHONE_ID", "序号");
            fieldMap.put("ISATPHONE_NAME", "储备物资名称");
            fieldMap.put("ISATPHONE_TYPE", "类别");
            fieldMap.put("ISATPHONE_MODEL", "型号");
            fieldMap.put("ISATPHONE_ADDRESS", "存储地点或单位");
            fieldMap.put("TEL", "电话号码");
            fieldMap.put("BUYING_TIME", "购置时间");
            fieldMap.put("COST", "资产原值(万元)");
            fieldMap.put("USED_NUMBER", "使用次数（年）");
//            fieldMap.put("CREATE_USER", "创建人");
//            fieldMap.put("CREATION_DATE", "创建日期");
//            fieldMap.put("LAST_UPDATE_USER", "更新人");
//            fieldMap.put("LAST_UPDATE_DATE", "更新日期");

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
}