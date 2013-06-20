package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.model.EquipmentInfoQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-1
 * Time: 14:56:52
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentInfoQueryDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AssetsTagNumberQueryDTO dto = null;

    public EquipmentInfoQueryDAO(SfUserDTO userAccount, AssetsTagNumberQueryDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AssetsTagNumberQueryDTO dtoPara = (AssetsTagNumberQueryDTO) dtoParameter;
        super.sqlProducer = new EquipmentInfoQueryModel((SfUserDTO) userAccount, dtoPara);
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "设备统计报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            if (dto.getType().equals("task")) {
                 fieldMap.put("BARCODE", "设备条码");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "设备型号");
            fieldMap.put("USER_NAME", "责任人");
            fieldMap.put("DEPT_NAME", "责任部门");
            fieldMap.put("WORKORDER_OBJECT_NAME", "设备地点");
            fieldMap.put("FINANCE_PROP_NAME", "财务属性");
            fieldMap.put("WORKORDER_BATCH", "任务编号");
            } else if(dto.getType().equals("dept")) {
             fieldMap.put("BARCODE", "设备条码");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "设备型号");
            fieldMap.put("USER_NAME", "责任人");
            fieldMap.put("DEPT_NAME", "责任部门");
            fieldMap.put("WORKORDER_OBJECT_NAME", "设备地点");
            fieldMap.put("FINANCE_PROP_NAME", "财务属性");
            }else{
                fieldMap.put("BARCODE", "设备条码");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "设备型号");
            fieldMap.put("USER_NAME", "责任人");
            fieldMap.put("MAINTAIN_USER", "使用人");
            fieldMap.put("DEPT_NAME", "责任部门");
            fieldMap.put("WORKORDER_OBJECT_NAME", "设备地点");
            fieldMap.put("FINANCE_PROP_NAME", "财务属性");
            fieldMap.put("SYN_STATUS", "同步状态");
            fieldMap.put("TRANS_NAME", "调拨状态");
            fieldMap.put("TAG_NUMBER", "MIS标签");
            fieldMap.put("ASSETS_DESCRIPTION", "MIS名称");
            fieldMap.put("MODEL_NUMBER", "MIS型号");
            fieldMap.put("ASSIGNED_TO_NAME", "MIS责任人");
            fieldMap.put("ASSETS_LOCATION", "MIS地点");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "MIS启用日期");
            fieldMap.put("COST", "原值");
            }

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
}

