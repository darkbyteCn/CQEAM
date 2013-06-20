package com.sino.ams.spare.part.dao;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.datatrans.*;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.part.dto.AmsSpareCategoryDTO;
import com.sino.ams.spare.part.model.AmsSpareCategoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class AmsSpareCategoryDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public AmsSpareCategoryDAO(SfUserDTO userAccount, AmsSpareCategoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsSpareCategoryDTO dtoPara = (AmsSpareCategoryDTO)dtoParameter;
		super.sqlProducer = new AmsSpareCategoryModel((SfUserDTO)userAccount, dtoPara);
	}

    public boolean doVerifyBarcode(AmsSpareCategoryDTO dto) throws QueryException { //执行校验该地点是否存在设备
       boolean success = false;
       AmsSpareCategoryModel spareModel = (AmsSpareCategoryModel) sqlProducer;
       SQLModel sqlModel = spareModel.getVerifyWorkNoModel(dto);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       success = sq.hasResult();
       return success;
    }

    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "备件设备分类.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "ID号");
            fieldMap.put("ITEM_NAME", "设备名称");
            fieldMap.put("ITEM_SPEC", "设备型号");
            fieldMap.put("ITEM_CATEGORY", "设备类型");
            fieldMap.put("SPARE_USAGE", "用途");
            fieldMap.put("VENDOR_NAME", "厂商");
            fieldMap.put("ITEM_UNIT", "单位");
            fieldMap.put("ENABLED", "是否有效");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("备件设备分类");
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
}