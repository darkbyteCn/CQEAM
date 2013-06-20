package com.sino.rds.share.util;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOption;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.util.option.OptionProduceRule;
import com.sino.rds.foundation.web.util.option.OptionProducer;
import com.sino.rds.foundation.web.util.option.OptionProducerFactory;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import com.sino.rds.share.form.FixedCategoryFrm;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.form.ReportCategoryFrm;

import java.sql.Connection;
import java.util.List;

public class RDSOptionCreateService extends RDSBaseService {

    public RDSOptionCreateService(BaseUserDTO userAccount, Connection conn) {
        this(userAccount, null, conn);
    }

    public RDSOptionCreateService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：生成字典下拉框
     *
     * @param dictionaryCode 字典代码
     * @param selectedValue  选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getDictionaryOptions(String dictionaryCode, String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("CODE");
        optRule.setDescField("VALUE");
        SQLModel sqlModel = RDSOptionSQLProducer.getAllDictionaryModel(dictionaryCode);
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    /**
     * 功能：生成字典下拉框
     *
     * @param dictionaryCode 字典代码
     * @param selectedValue  选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getEnabledDictionaryOptions(String dictionaryCode, String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("CODE");
        optRule.setDescField("VALUE");
        SQLModel sqlModel = RDSOptionSQLProducer.getEnabledDictionaryModel(dictionaryCode);
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }


    /**
     * 功能：生成字典下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getOracleUserOptions(String selectedValue) throws WebException {
        WebOptions options = null;
        if(isOracleDBProduct()){
            OptionProduceRule optRule = new OptionProduceRule();
            optRule.setConnection(conn);
            optRule.setValueField("USERNAME");
            optRule.setDescField("USERNAME");
            SQLModel sqlModel = RDSOptionSQLProducer.getEnabledOracleUsersModel();
            optRule.setDataSource(sqlModel);
            optRule.setSelectedValue(selectedValue);
            optRule.setAddBlank(true);
            OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
            options = optProducer.getOptions();
        }
        return options;
    }

    /**
     * 功能：生成数据模型下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getAllDataModelOptions(String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("MODEL_ID");
        optRule.setDescField("MODEL_NAME");
        SQLModel sqlModel = RDSOptionSQLProducer.getAllDataModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }


    /**
     * 功能：生成数据报表下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getAllReportOptions(String selectedValue) throws WebException {
        return getAllReportOptions(selectedValue, true);
    }

    /**
     * 功能：生成数据报表下拉框
     *
     * @param selectedValue 选中项值
     * @param addBlank      是否添加空选项
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getAllReportOptions(String selectedValue, boolean addBlank) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("REPORT_ID");
        optRule.setDescField("REPORT_NAME");
        SQLModel sqlModel = RDSOptionSQLProducer.getAllReportModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(addBlank);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    /**
     * 功能：生成数据报表下拉框
     *
     * @param selectedValue 选中项值
     * @param addBlank      是否添加空选项
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getIntersectReportOptions(String selectedValue, boolean addBlank) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("REPORT_ID");
        optRule.setDescField("REPORT_NAME");
        optRule.addAttribute("reportType", "REPORT_TYPE");
        SQLModel sqlModel = RDSOptionSQLProducer.getIntersectReportModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(addBlank);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    /**
     * 功能：生成是否有效下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public static WebOptions getEnableOptions(String selectedValue) throws WebException {
        WebOptions options = new WebOptions();

        WebOption option = new WebOption();
        option.setValue(RDSDictionaryList.TRUE_VALUE);
        option.setLabel("有效");
        option.setSelected(selectedValue.equals(RDSDictionaryList.TRUE_VALUE));
        options.addOption(option);

        option = new WebOption();
        option.setValue(RDSDictionaryList.FALSE_VALUE);
        option.setLabel("无效");
        option.setSelected(selectedValue.equals(RDSDictionaryList.FALSE_VALUE));
        options.addOption(option);

        return options;
    }

    public WebOptions getFieldsOptions(List<? extends ModelFieldFrm> fields) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("fieldId");
        optRule.setDescField("fieldName");
        optRule.setDataSource(fields);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    /**
     * 功能：生成值列表下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getLovOptions(String selectedValue) throws WebException {
        WebOptions options = null;
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("LOV_ID");
        optRule.setDescField("LOV_NAME");
        SQLModel sqlModel = RDSOptionSQLProducer.getLovModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        options = optProducer.getOptions();
        if(options == null){
            options = new WebOptions();
        }
        WebOption webOption = new WebOption();
        webOption.setValue("ADD_NEW_LOV");
        webOption.setLabel("维护LOV值列表");
        options.addOption(webOption);
        return options;
    }

    /**
     * 功能：生成值列表下拉框
     * @param selectedValue 选中项值 
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getConnectionOptions(String selectedValue) throws WebException {
        WebOptions options = null;
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("CONNECTION_ID");
        optRule.setDescField("CONNECTION_NAME");
        optRule.setSelectedValue(selectedValue);
        SQLModel sqlModel = RDSOptionSQLProducer.getAllConnectionModel();
        optRule.setDataSource(sqlModel);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        options = optProducer.getOptions();
        if(options == null){
            options = new WebOptions();
        }
        WebOption webOption = new WebOption();
        webOption.setValue("ADD_NEW_CONN");
        webOption.setLabel("维护数据源");
        options.addOption(webOption);
        return options;
    }

    /**
     * 功能：生成值列表下拉框
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getConnectionOptions() throws WebException {
        return getConnectionOptions("");
    }

    /**
     * 功能：生成值列表下拉框
     *
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getAllLovOptions() throws WebException {
        return getLovOptions("");
    }

    /**
     * 功能：生成LookUp下拉框
     *
     * @param selectedValue 选中项值
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getLookUpOptions(String selectedValue) throws WebException {
        WebOptions options = null;
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("LOOK_UP_ID");
        optRule.setDescField("LOOK_UP_NAME");
        SQLModel sqlModel = RDSOptionSQLProducer.getLookUpModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        options = optProducer.getOptions();
        if(options == null){
            options = new WebOptions();
        }
        WebOption webOption = new WebOption();
        webOption.setValue("ADD_NEW_LOOK_UP");
        webOption.setLabel("维护LOOK_UP查找");
        options.addOption(webOption);
        return options;
    }

    /**
     * 功能：生成LookUp下拉框
     *
     * @return Web选项对象集合
     * @throws WebException
     */
    public WebOptions getAllLookUpOptions() throws WebException {
        return getLookUpOptions("");
    }

    public WebOptions getCategoryOptions(List<ReportCategoryFrm> categories, String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setValueField("categoryId");
        optRule.setDescField("fieldName");
        optRule.addAttribute("fieldName", "fieldName");
        optRule.addAttribute("fieldDesc", "fieldDesc");
        optRule.addAttribute("viewLocation", "viewLocation");
        optRule.addAttribute("viewLocationName", "viewLocationName");
        optRule.setDataSource(categories);
        optRule.setSelectedValue(selectedValue);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    public WebOptions getFixedCategoryReportOptions(String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setConnection(conn);
        optRule.setValueField("REPORT_ID");
        optRule.setDescField("REPORT_NAME");
        optRule.addAttribute("reportType", "REPORT_TYPE");
        SQLModel sqlModel = RDSOptionSQLProducer.getFixedCategoryReportModel();
        optRule.setDataSource(sqlModel);
        optRule.setSelectedValue(selectedValue);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    public WebOptions getParentValueOptions(List<FixedCategoryFrm> parentValueFrms, String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setValueField("defineId");
        optRule.setDescField("defineValue");
        optRule.setDataSource(parentValueFrms);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }

    public WebOptions getMyFavoriteOptions(List<FavoriteHeaderFrm> favorites, String selectedValue) throws WebException {
        OptionProduceRule optRule = new OptionProduceRule();
        optRule.setValueField("headerId");
        optRule.setDescField("favoriteName");
        optRule.setDataSource(favorites);
        optRule.setSelectedValue(selectedValue);
        optRule.setAddBlank(true);
        OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
        return optProducer.getOptions();
    }
}
