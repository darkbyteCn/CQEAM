package com.sino.rds.foundation.web.util.radio;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;

public class SQL2Radio extends RadioProducer {


    /**
     * 功能：获取单选按钮列表
     *
     * @return WebOptions
     */
    public WebRadio getWebRadio() throws WebException {
        WebRadio webRadio = null;
        try {
            SQLModel sqlModel = (SQLModel) produceRule.getDataSource();
            SimpleQuery searcher = new SimpleQuery(sqlModel, produceRule.getConnection());
            searcher.executeQuery();
            RowSet listData = searcher.getSearchResult();
            String valueField = produceRule.getValueField();
            String descField = produceRule.getDescField();
            if (listData != null && !StrUtil.isEmpty(valueField) && !StrUtil.isEmpty(descField)) {
                webRadio = new WebRadio(produceRule.getCheckBoxName());
                int dataCount = listData.getSize();
                for (int i = 0; i < dataCount; i++) {
                    Row row = listData.getRow(i);
                    String value = row.getStrValue(valueField);
                    String label = row.getStrValue(descField);
                    webRadio.addValueCaption(value, label);
                }
                webRadio.addEventHandlers(produceRule.getHandlers());
                webRadio.setCheckedValue(produceRule.getCheckedValue());
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new WebException(ex.getMessage());
        }
        return webRadio;
    }
}