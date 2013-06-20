package com.sino.ams.net.reportforms.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by       李轶
 * Date:            2009-07-24
 * Time:            10:26:55
 * Function:        工单统计--地点(陕西)
 */
public class OrderStatisticsModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private SitusStatisticsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;

//    public SitusStatisticsModel() {
//        this.sqlModel = new SQLModel();
//        this.dtoParameter = new SitusStatisticsDTO();
//    }

    public OrderStatisticsModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (SitusStatisticsDTO) dtoParameter;
    }

//    public SitusStatisticsDTO getDtoParameter() {
//        return dtoParameter;
//    }
//
//    public void setDtoParameter(SitusStatisticsDTO dtoParameter) {
//        this.dtoParameter = dtoParameter;
//    }

    /**
     * 得到查询所有的MODEL
     *
     * @return
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List strArg = new ArrayList();
            String strSql = "SELECT" +
                    " EO.WORKORDER_OBJECT_CODE,\n" +
                    " EO.WORKORDER_OBJECT_NAME,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE='18' THEN 1 ELSE 0 END ) TMPT_A,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE='12' THEN 1 ELSE 0 END  ) TMPT_B,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE='13' THEN 1 ELSE 0 END ) TMPT_C,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE='14' THEN 1 ELSE 0 END  ) TMPT_D,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE='17' THEN 1 ELSE 0 END ) TMPT_E,\n" +
                    " 0 TMPT_F\n" +           //报废
                    " FROM ETS_WORKORDER EW, \n" +
                    "       ETS_OBJECT EO\n" +
                    " WHERE EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EW.ORGANIZATION_ID = ?)\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR  EO.WORKORDER_OBJECT_NAME  LIKE ?)" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE > = ?)"+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE < = ?)" +
                    " GROUP BY EO.WORKORDER_OBJECT_CODE, EO.WORKORDER_OBJECT_NAME";

            strArg.add(dtoParameter.getOrganizationId()+"");
            strArg.add(dtoParameter.getOrganizationId());
            strArg.add(dtoParameter.getWorkorderObjectName());
            strArg.add(dtoParameter.getWorkorderObjectName());
            strArg.add(dtoParameter.getFromDate());
            strArg.add(dtoParameter.getFromDate());
            strArg.add(dtoParameter.getToDate());
            strArg.add(dtoParameter.getToDate());
            sqlModel.setSqlStr(strSql);
            sqlModel.setArgs(strArg);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


}