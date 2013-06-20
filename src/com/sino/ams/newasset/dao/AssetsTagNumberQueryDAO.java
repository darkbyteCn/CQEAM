package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.model.AssetsTagNumberQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-31
 * Time: 14:45:04
 * To change this template use File | Settings | File Templates.
 */
public class AssetsTagNumberQueryDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    public AssetsTagNumberQueryDAO(SfUserDTO userAccount,
                                   AssetsTagNumberQueryDTO dtoParameter,
                                   Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AssetsTagNumberQueryDTO dtoPara = (AssetsTagNumberQueryDTO)
                                          dtoParameter;
        super.sqlProducer = new AssetsTagNumberQueryModel((SfUserDTO)
                userAccount, dtoPara);
    }

    public File exportFile(String excelType) throws DataTransException {
        File file = null;
        try {
        	AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) super.dtoParameter;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String reportTitle = "MIS资产标签统计报表";
    		if( dto.getAssetsType().equals( "EAM" )){
    			reportTitle = "EAM资产标签统计报表";
    		}
    		
    		if (!StrUtil.isNotEmpty(excelType)) {
    			excelType = "xls";
    		}
    		
    		String fileName = reportTitle + "." + excelType;
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ASSET_NUMBER", "资产编号");
            fieldMap.put("TAG_NUMBER", "资产标签号");
            fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
            fieldMap.put("MODEL_NUMBER", "资产型号");
            fieldMap.put("ASSETS_LOCATION_CODE", "地点");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "资产启用日期");
            fieldMap.put("FA_CATEGORY_CODE", "资产分类");
            fieldMap.put("ASSETS_LOCATION", "地点描述");
            fieldMap.put("ORGNIZATION_NAME", "公司名称");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
//            custData.setReportTitle(fileName);
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
