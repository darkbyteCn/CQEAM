package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckExportModel;
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
 * User: yuyao
 * Date: 2008-11-14
 * Time: 11:14:46
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsCheckExportDAO extends AMSBaseDAO {
      SfUserDTO sfUser=null;
    public AmsAssetsCheckExportDAO(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser=userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCJYCDTO dtoPara = (AmsAssetsCJYCDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsCheckExportModel((SfUserDTO) userAccount, dtoPara);
   }
       public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
           String fileName = "资产盘点统计.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

             Map fieldMap = new HashMap();
            fieldMap.put("ASSET_ID", "资产编号");
            fieldMap.put("TAG_NUMBER", "资产标签");
            fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
            fieldMap.put("MODEL_NUMBER", "规格型号");
            fieldMap.put("UNIT_OF_MEASURE", "计量单位");
            fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
            fieldMap.put("LIFE_IN_YEARS", "折旧年限");
            fieldMap.put("COST", "资产原值");
            fieldMap.put("DEPRN_RESERVE", "累计折旧");
            fieldMap.put("DEPRN_COST", "资产净值");
            fieldMap.put("CURRENT_UNITS", "账面数量");
            fieldMap.put("NOW_COUNT", "实物数量");
            fieldMap.put("DEPT_NAME", "责任部门");
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
