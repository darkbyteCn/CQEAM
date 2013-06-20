package com.sino.ams.net.statistic.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dto.EquipStatDTO;
import com.sino.ams.net.statistic.model.EquipStatModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EquipStatDAO</p>
 * <p>Description:程序自动生成服务程序“EquipStatDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class EquipStatDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：EQUIP_STAT 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EquipStatDAO(SfUserDTO userAccount, EquipStatDTO dtoParameter, Connection conn) {
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
        EquipStatDTO dtoPara = (EquipStatDTO) dtoParameter;
        super.sqlProducer = new EquipStatModel((SfUserDTO) userAccount, dtoPara);
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
            EquipStatDTO dtoPara = (EquipStatDTO) dtoParameter;
            String qryType = dtoPara.getQryType();
            String fileName = "";
            Map fieldMap = new HashMap();
            if (qryType.equals(WebAttrConstant.BY_LOCUS)) {
                fileName = "设备现有量--按地点.xls";
                fieldMap.put("ORGANIZATION_NAME", "公司");
                fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
                fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
                fieldMap.put("ITEM_CATEGORY", "设备类别");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            } else if (qryType.equals(WebAttrConstant.BY_CATEGORY)) {
                fileName = "设备现有量--按分类.xls";
                fieldMap.put("ORGANIZATION_NAME", "公司");
                fieldMap.put("ITEM_CATEGORY", "设备类别");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            } else if (qryType.equals(WebAttrConstant.BY_VENDOR)) {
                fileName = "设备现有量--按厂家.xls";
                fieldMap.put("VENDOR_NAME", "供应商");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            }   else if (qryType.equals(WebAttrConstant.BY_NAME)) {
                fileName = "全省统计--按地点.xls";
//                fieldMap.put("ORGANIZATION_NAME", "公司");
                fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
//                fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
//                fieldMap.put("ITEM_CATEGORY", "设备类别");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            } else if (qryType.equals(WebAttrConstant.BY_CATEGORY+ "2")) {
                fileName = "全省统计--按状态.xls";
                fieldMap.put("ORGANIZATION_NAME", "公司");
                fieldMap.put("ITEM_CATEGORY", "设备类别");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            } else if (qryType.equals(WebAttrConstant.BY_VENDOR + "2")) {
                fileName = "全省统计--按厂家.xls";
                fieldMap.put("VENDOR_NAME", "供应商");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("CNT", "现有量");
            }

            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

//		fieldMap.put("IS_TEMP_ADDR", "是否临时地点");
//			fieldMap.put("CREATION_DATE", "创建日期");
//			fieldMap.put("CREATED_BY", "创建人");
//			fieldMap.put("PROJECT_NAME", "所属工程");

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