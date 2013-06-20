package com.sino.rds.foundation.web.util.option;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOption;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

public class SQL2Options extends OptionProducer {

    /**
     * 功能：获取下拉列表
     *
     * @return WebOptions
     * @throws WebException
     */
    public WebOptions getOptions() throws WebException {
        WebOptions options = new WebOptions();
        try {
            SQLModel sqlModel = (SQLModel) produceRule.getDataSource();
            SimpleQuery searcher = new SimpleQuery(sqlModel, produceRule.getConnection());
            searcher.executeQuery();
            RowSet listData = searcher.getSearchResult();
            List fieldNames = listData.getFieldNames();
            String valueField = produceRule.getValueField();
            String descField = produceRule.getDescField();
            if ((valueField.equals("") || descField.equals("")) && fieldNames.size() >= 2) {
                valueField = String.valueOf(fieldNames.get(0));
                descField = String.valueOf(fieldNames.get(1));
            }
            if (!StrUtil.isEmpty(valueField) && !StrUtil.isEmpty(descField)) {
                int dataCount = listData.getSize();
                if (produceRule.isAddBlank()) {
                    options.addBlankOption();
                }
                String EMPTY_STR = "&nbsp;&nbsp;&nbsp;&nbsp;"; //两个空格
                Object firstValue = null;
                Object secondValue = null;
                String selectedValue = produceRule.getSelectedValue();
                boolean rightStep = produceRule.isRightStep();
                String containStr = produceRule.getRightStepStr();
                produceAttributeNames();
                for (int i = 0; i < dataCount; i++) {
                    Row row = listData.getRow(i);
                    firstValue = row.getStrValue(valueField);
                    secondValue = row.getStrValue(descField);
                    if (firstValue != null) {
                        WebOption option = new WebOption();
                        option.setValue(firstValue.toString());
                        String strValue = secondValue.toString();
                        if (rightStep) {
                            int containNumber = StrUtil.containNum(firstValue.toString(), containStr);
                            strValue = StrUtil.getStrByCount(EMPTY_STR, containNumber) + strValue;
                        }
                        option.setLabel(strValue);
                        if (selectedValue != null && firstValue.toString().equals(selectedValue)) {
                            option.setSelected(true);
                        }
                        if (hasAttribute) {
                            for (int j = 0; j < attributeNames.length; j++) {
                                String attributeName = attributeNames[j];
                                String srcProperty = srcProperties[j];
                                String attProperty = row.getStrValue(srcProperty);
                                option.addAttribute(attributeName, attProperty);
                            }
                        }
                        options.addOption(option);
                    }
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new WebException(ex);
        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
        return options;
    }
}