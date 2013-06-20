package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsChangeZJQueryModel;
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
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-9
 * Time: 17:18:14
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeZJQueryDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;
    private AmsAssetsCJYCDTO dto = null;

    public AmsAssetsChangeZJQueryDAO(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsAssetsCJYCDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCJYCDTO dtoPara = (AmsAssetsCJYCDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsChangeZJQueryModel((SfUserDTO) userAccount, dtoPara);
    }
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "资产折旧年限统计报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ORGNIZATION_NAME", "公司名称");
            fieldMap.put("ASSET_NUMBER", "资产编号");
            fieldMap.put("FA_CATEGORY1", "资产分类");
            fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
            fieldMap.put("MODEL_NUMBER", "资产型号");
            fieldMap.put("NEW_YEARS", "新折旧年限");
            fieldMap.put("CHANGE_AMOUNT", "新折旧金额");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "资产启用日期");
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
