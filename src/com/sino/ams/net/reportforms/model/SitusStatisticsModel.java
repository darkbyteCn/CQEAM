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
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-12
 * Time: 10:29:53
 * To change this template use File | Settings | File Templates.
 */
public class SitusStatisticsModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private SitusStatisticsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;

//    public SitusStatisticsModel() {
//        this.sqlModel = new SQLModel();
//        this.dtoParameter = new SitusStatisticsDTO();
//    }

    public SitusStatisticsModel(SfUserDTO userAccount, DTO dtoParameter) {
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
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=10 THEN 1 ELSE 0 END ) TMPT_A,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=11 THEN 1 ELSE 0 END ) TMPT_B,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=12 THEN 1 ELSE 0 END ) TMPT_C,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=13 THEN 1 ELSE 0 END ) TMPT_D,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=14 THEN 1 ELSE 0 END ) TMPT_E,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=15 THEN 1 ELSE 0 END ) TMPT_F,"+
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=16 THEN 1 ELSE 0 END ) TMPT_G,"+
                    " FROM ETS_WORKORDER EW, \n" +
                    "       ETS_OBJECT EO\n" +
                    " WHERE EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "   AND (  " + SyBaseSQLUtil.isNull() + "  OR EW.ORGANIZATION_ID = ?)\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR  EO.WORKORDER_OBJECT_NAME  LIKE ?)" +
                     " AND (  " + SyBaseSQLUtil.isNull() + "  OR EO.OBJECT_CATEGORY LIKE ?) "+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE > = ?)"+
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE < = ?)" +
                    " GROUP BY EO.WORKORDER_OBJECT_CODE, EO.WORKORDER_OBJECT_NAME";

            strArg.add(sfUser.getOrganizationId());
            strArg.add(sfUser.getOrganizationId());
            strArg.add(dtoParameter.getWorkorderObjectName());
            strArg.add(dtoParameter.getWorkorderObjectName());
            strArg.add(dtoParameter.getObjectCategory());
            strArg.add(dtoParameter.getObjectCategory());
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
