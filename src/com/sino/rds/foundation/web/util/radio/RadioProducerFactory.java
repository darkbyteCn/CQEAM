package com.sino.rds.foundation.web.util.radio;

import com.sino.rds.foundation.web.util.option.OptionProduceRule;


public class RadioProducerFactory {

    /**
     * 功能：获取下拉框列表生成器
     *
     * @param produceRule OptionProduceRule
     * @return OptionProducer
     */
    public static RadioProducer getOptionProducer(RadioProduceRule produceRule) {
        RadioProducer radioProducer = null;
        int dataType = produceRule.getDataType();
        switch (dataType) {
            case OptionProduceRule.TYPE_DTO:
                radioProducer = new DTOSet2Radio();
                break;
            case OptionProduceRule.TYPE_LIST:
                radioProducer = new List2Radio();
                break;
            case OptionProduceRule.TYPE_ROW:
                radioProducer = new RowSet2Radio();
                break;
            case OptionProduceRule.TYPE_SQL:
                radioProducer = new SQL2Radio();
                break;
            default:
                radioProducer = new NoData2Radio();
        }
        radioProducer.setProduceRule(produceRule);
        return radioProducer;
    }
}
