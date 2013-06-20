package com.sino.rds.foundation.web.util.option;


public class OptionProducerFactory {

    /**
     * 功能：获取下拉框列表生成器
     *
     * @param produceRule OptionProduceRule
     * @return OptionProducer
     */
    public static OptionProducer getOptionProducer(OptionProduceRule produceRule) {
        OptionProducer optionProducer = null;
        int dataType = produceRule.getDataType();
        switch (dataType) {
            case OptionProduceRule.TYPE_DTO:
                optionProducer = new DTOSet2Options();
                break;
            case OptionProduceRule.TYPE_LIST:
                optionProducer = new List2Options();
                break;
            case OptionProduceRule.TYPE_ROW:
                optionProducer = new RowSet2Options();
                break;
            case OptionProduceRule.TYPE_SQL:
                optionProducer = new SQL2Options();
                break;
            default:
                optionProducer = new NoData2Options();
        }
        optionProducer.setProduceRule(produceRule);
        return optionProducer;
    }
}
