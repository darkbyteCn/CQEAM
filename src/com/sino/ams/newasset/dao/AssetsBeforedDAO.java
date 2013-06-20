package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.newasset.model.AssetsBeforedModel;
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
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-4-9
 * Time: 2:01:53
 * To change this template use File | Settings | File Templates.
 */
public class AssetsBeforedDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    public AmsInstrumentInfoDTO dto = null;

    public AssetsBeforedDAO(SfUserDTO userAccount,
                            AmsInstrumentInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsInstrumentInfoDTO)super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInstrumentInfoDTO dtoPara = (AmsInstrumentInfoDTO) dtoParameter;
        super.sqlProducer = new AssetsBeforedModel((SfUserDTO) userAccount,
                dtoPara);
    }

    public File exportFile() throws DataTransException {
        File file = null;

        AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO)
                                                 dtoParameter;
        AssetsBeforedModel model = new AssetsBeforedModel(sfUser,
                amsInstrumentInfo);
        SQLModel sqlModel = model.getPageQueryModel();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setCalPattern(CalendarConstant.LINE_PATTERN);
        rule.setSourceConn(conn);
        String fileName = "预龄资产查询信息.xls";
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);
        Map fieldMap = new HashMap();
        fieldMap.put("COMPANY_NAME", "公司");
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("BARCODE", "资产条码");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_CATEGORY", "资产类别");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
        fieldMap.put("LIFE_IN_YEARS", "折旧年限");
        fieldMap.put("VENDOR_NAME", "供应商");
        rule.setFieldMap(fieldMap);
        CustomTransData custData = new CustomTransData();
        custData.setReportTitle("预龄资产查询信息");
        custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }
}
