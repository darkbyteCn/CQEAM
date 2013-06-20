package com.sino.soa.mis.srv.valueinfo.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.valueinfo.dto.SBSYSYInquiryVSetValueInfoDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 13:31:54
 * To change this template use File | Settings | File Templates.
 */
public class SBSYSYInquiryVSetValueInfoModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public SBSYSYInquiryVSetValueInfoModel(SfUserDTO userAccount, SBSYSYInquiryVSetValueInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " M_FND_FLEX_VALUES("
                    + " FLEX_VALUE_ID,"
                    + " FLEX_VALUE_SET_ID,"
                    + " FLEX_VALUE,"
                    + " FLEX_VALUE_MEANING,"
                    + " DESCRIPTION,"
                    + " PARENT_FLEX_VALUE_LOW,"
                    + " PARENT_FLEX_VALUE_HIGH,"
                    + " ENABLED_FLAG,"
                    + " SUMMARY_FLAG,"
                    + " START_DATE_ACTIVE,"
                    + " END_DATE_ACTIVE"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            sqlArgs.add(mFndFlexValues.getFlexValueId());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getDescription());
            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getStartDateActive());
            sqlArgs.add(mFndFlexValues.getEndDateActive());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成M_FND_FLEX_VALUES数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
            String sqlStr = "UPDATE M_FND_FLEX_VALUES"
                    + " SET"
                    + " FLEX_VALUE_MEANING = ?,"
                    + " DESCRIPTION = ?,"
//                    + " PARENT_FLEX_VALUE_LOW = ?,"
//                    + " PARENT_FLEX_VALUE_HIGH = ?,"
                    + " ENABLED_FLAG = ?,"
//                    + " SUMMARY_FLAG = ?,"
                    + " START_DATE_ACTIVE = ?,"
                    + " END_DATE_ACTIVE = ?"
                    + " WHERE"
                    + " FLEX_VALUE = ?"
                    + " AND FLEX_VALUE_SET_ID = ?";

            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getDescription());
//            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
//            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
//            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getStartDateActive());   
            sqlArgs.add(mFndFlexValues.getEndDateActive());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成M_FND_FLEX_VALUES数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " M_FND_FLEX_VALUES"
                + " WHERE"
                + " FLEX_VALUE_ID = ?";
        sqlArgs.add(mFndFlexValues.getFlexValueId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：得到值集名称
     * @return SQLModel
     */
    public SQLModel getAllFlexValues(String source) {
        SQLModel sqlModel = new SQLModel();
        List<String> sqlArgs = new ArrayList<String>();
        String sqlStr = "SELECT MFVS.FLEX_VALUE_SET_ID," +
                "MFVS.FLEX_VALUE_SET_NAME \n" +
                "FROM \n" +
                "M_FND_FLEX_VALUE_SETS MFVS \n" +
                "WHERE MFVS.SOURCE=?\n" +
                " AND (MFVS.FLEX_VALUE_SET_NAME LIKE '%LOC_1' OR MFVS.FLEX_VALUE_SET_NAME LIKE '%LOC_2')";
        sqlArgs.add(source);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成M_FND_FLEX_VALUES数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " FLEX_VALUE_ID,"
                + " FLEX_VALUE_SET_ID,"
                + " FLEX_VALUE,"
                + " FLEX_VALUE_MEANING,"
                + " DESCRIPTION,"
                + " PARENT_FLEX_VALUE_LOW,"
                + " PARENT_FLEX_VALUE_HIGH,"
                + " ENABLED_FLAG,"
                + " SUMMARY_FLAG,"
                + " START_DATE_ACTIVE,"
                + " END_DATE_ACTIVE"
                + " FROM"
                + " M_FND_FLEX_VALUES"
                + " WHERE"
                + " FLEX_VALUE_ID = ?";
        sqlArgs.add(mFndFlexValues.getFlexValueId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成M_FND_FLEX_VALUES多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " FLEX_VALUE_ID,"
                    + " FLEX_VALUE_SET_ID,"
                    + " FLEX_VALUE,"
                    + " FLEX_VALUE_MEANING,"
                    + " DESCRIPTION,"
                    + " PARENT_FLEX_VALUE_LOW,"
                    + " PARENT_FLEX_VALUE_HIGH,"
                    + " ENABLED_FLAG,"
                    + " SUMMARY_FLAG,"
                    + " START_DATE_ACTIVE,"
                    + " END_DATE_ACTIVE"
                    + " FROM"
                    + " M_FND_FLEX_VALUES"
                    + " WHERE"
                    + " ( ? = -1 OR FLEX_VALUE_ID = ?)"
                    + " AND ( ? = -1 OR FLEX_VALUE_SET_ID = ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR FLEX_VALUE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR FLEX_VALUE_MEANING LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR DESCRIPTION LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR PARENT_FLEX_VALUE_LOW LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR PARENT_FLEX_VALUE_HIGH LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR ENABLED_FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR SUMMARY_FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR START_DATE_ACTIVE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR END_DATE_ACTIVE LIKE ?)";
            sqlArgs.add(mFndFlexValues.getFlexValueId());
            sqlArgs.add(mFndFlexValues.getFlexValueId());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getDescription());
            sqlArgs.add(mFndFlexValues.getDescription());
            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getStartDateActive());
            sqlArgs.add(mFndFlexValues.getStartDateActive());
            sqlArgs.add(mFndFlexValues.getEndDateActive());
            sqlArgs.add(mFndFlexValues.getEndDateActive());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成M_FND_FLEX_VALUES页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            SBSYSYInquiryVSetValueInfoDTO mFndFlexValues = (SBSYSYInquiryVSetValueInfoDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " FLEX_VALUE_ID,"
                    + " FLEX_VALUE_SET_ID,"
                    + " FLEX_VALUE,"
                    + " FLEX_VALUE_MEANING,"
                    + " DESCRIPTION,"
                    + " PARENT_FLEX_VALUE_LOW,"
                    + " PARENT_FLEX_VALUE_HIGH,"
                    + " ENABLED_FLAG,"
                    + " SUMMARY_FLAG,"
                    + " START_DATE_ACTIVE,"
                    + " END_DATE_ACTIVE"
                    + " FROM"
                    + " M_FND_FLEX_VALUES"
                    + " WHERE"
                    + " ( ? = -1 OR FLEX_VALUE_ID = ?)"
                    + " AND ( ? = -1 OR FLEX_VALUE_SET_ID = ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR FLEX_VALUE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR FLEX_VALUE_MEANING LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR DESCRIPTION LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR PARENT_FLEX_VALUE_LOW LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR PARENT_FLEX_VALUE_HIGH LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR ENABLED_FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR SUMMARY_FLAG LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR START_DATE_ACTIVE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + " OR END_DATE_ACTIVE LIKE ?)";
            sqlArgs.add(mFndFlexValues.getFlexValueId());
            sqlArgs.add(mFndFlexValues.getFlexValueId());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlArgs.add(mFndFlexValues.getFlexValueSetId());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValue());
            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getFlexValueMeaning());
            sqlArgs.add(mFndFlexValues.getDescription());
            sqlArgs.add(mFndFlexValues.getDescription());
            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
            sqlArgs.add(mFndFlexValues.getParentFlexValueLow());
            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getParentFlexValueHigh());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
            sqlArgs.add(mFndFlexValues.getEnabledFlag());
            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getSummaryFlag());
            sqlArgs.add(mFndFlexValues.getStartDateActive());
            sqlArgs.add(mFndFlexValues.getStartDateActive());
            sqlArgs.add(mFndFlexValues.getEndDateActive());
            sqlArgs.add(mFndFlexValues.getEndDateActive());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel existsSetValueModel(String flexValue, int flexValueSetId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM M_FND_FLEX_VALUES MFV WHERE MFV.FLEX_VALUE = ? AND MFV.FLEX_VALUE_SET_ID = ?";
        sqlArgs.add(flexValue);
        sqlArgs.add(flexValueSetId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
	}
}