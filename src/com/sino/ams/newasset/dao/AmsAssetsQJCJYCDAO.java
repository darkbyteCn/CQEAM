package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsQJCJYCModel;
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
 * Date: 2008-3-17
 * Time: 18:11:36
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsQJCJYCDAO extends AMSBaseDAO {
    SfUserDTO sfUser=null;
    public AmsAssetsQJCJYCDAO(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser=userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCJYCDTO dtoPara = (AmsAssetsCJYCDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsQJCJYCModel((SfUserDTO) userAccount, dtoPara);
   }
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "资产类别的区间折旧预测报表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("FA_CAT_NAME_1", "应用领域");
            fieldMap.put("FA_CAT_NAME_2", "分类目录");
            fieldMap.put("FA_CAT_NAME_3", "名称");
            fieldMap.put("DEPRN_COST_1", "1月");
            fieldMap.put("DEPRN_COST_2", "2月");
            fieldMap.put("DEPRN_COST_3", "3月");
            fieldMap.put("DEPRN_COST_4", "4月");
            fieldMap.put("DEPRN_COST_5", "5月");
            fieldMap.put("DEPRN_COST_6", "6月");
            fieldMap.put("DEPRN_COST_7", "7月");
            fieldMap.put("DEPRN_COST_8", "8月");
            fieldMap.put("DEPRN_COST_9", "9月");
            fieldMap.put("DEPRN_COST_10", "10月");
            fieldMap.put("DEPRN_COST_11", "11月");
            fieldMap.put("DEPRN_COST_12", "12月");
            fieldMap.put("DEPRN_COST_13", "13月");
            fieldMap.put("DEPRN_COST_14", "14月");
            fieldMap.put("DEPRN_COST_15", "15月");
            fieldMap.put("DEPRN_COST_16", "16月");
            fieldMap.put("DEPRN_COST_17", "17月");
            fieldMap.put("DEPRN_COST_18", "18月");
            fieldMap.put("DEPRN_COST_19", "19月");
            fieldMap.put("DEPRN_COST_20", "20月");
            fieldMap.put("DEPRN_COST_21", "21月");
            fieldMap.put("DEPRN_COST_22", "22月");
            fieldMap.put("DEPRN_COST_23", "23月");
            fieldMap.put("DEPRN_COST_24", "24月");
            fieldMap.put("DEPRN_COST_25", "25月");
            fieldMap.put("DEPRN_COST_26", "26月");
            fieldMap.put("DEPRN_COST_27", "27月");
            fieldMap.put("DEPRN_COST_28", "28月");
            fieldMap.put("DEPRN_COST_29", "29月");
            fieldMap.put("DEPRN_COST_30", "30月");
            fieldMap.put("DEPRN_COST_31", "31月");
            fieldMap.put("DEPRN_COST_32", "32月");
            fieldMap.put("DEPRN_COST_33", "33月");
            fieldMap.put("DEPRN_COST_34", "34月");
            fieldMap.put("DEPRN_COST_35", "35月");
            fieldMap.put("DEPRN_COST_36", "36月");
            fieldMap.put("DEPRN_COST_37", "37月");
            fieldMap.put("DEPRN_COST_38", "38月");
            fieldMap.put("DEPRN_COST_39", "39月");
            fieldMap.put("DEPRN_COST_40", "40月");
            fieldMap.put("DEPRN_COST_41", "41月");
            fieldMap.put("DEPRN_COST_42", "42月");
            fieldMap.put("DEPRN_COST_43", "43月");
            fieldMap.put("DEPRN_COST_44", "44月");
            fieldMap.put("DEPRN_COST_45", "45月");
            fieldMap.put("DEPRN_COST_46", "46月");
            fieldMap.put("DEPRN_COST_47", "47月");
            fieldMap.put("DEPRN_COST_48", "48月");
            fieldMap.put("DEPRN_COST_49", "49月");
            fieldMap.put("DEPRN_COST_50", "50月");
            fieldMap.put("DEPRN_COST_51", "51月");
            fieldMap.put("DEPRN_COST_52", "52月");
            fieldMap.put("DEPRN_COST_53", "53月");
            fieldMap.put("DEPRN_COST_54", "54月");
            fieldMap.put("DEPRN_COST_55", "55月");
            fieldMap.put("DEPRN_COST_56", "56月");
            fieldMap.put("DEPRN_COST_57", "57月");
            fieldMap.put("DEPRN_COST_58", "58月");
            fieldMap.put("DEPRN_COST_59", "59月");
            fieldMap.put("DEPRN_COST_60", "60月");
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
