package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-11-14
 * Time: 11:14:55
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsCheckExportModel extends AMSSQLProducer {
    private AmsAssetsCJYCDTO dto = null;

    public AmsAssetsCheckExportModel(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsCJYCDTO) dtoParameter;
    }


    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT EFA.ASSET_ID,\n" +
                    "       EFA.TAG_NUMBER,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.LIFE_IN_YEARS,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_RESERVE,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    " CALSE WHEN dbo.ADP_GET_ITEM_COUNT(EFA.ASSET_ID)='' THEN 0 ELSE dbo.ADP_GET_ITEM_COUNT(EFA.ASSET_ID) END NOW_COUNT ,"+
                    "        AMD.DEPT_NAME \n" +
                    "  FROM ETS_FA_ASSETS EFA, AMS_MIS_DEPT AMD, AMS_MIS_EMPLOYEE AME\n" +
                    " WHERE EFA.BOOK_TYPE_CODE = dbo.NVL(?, EFA.BOOK_TYPE_CODE)\n" +
                    "   AND AME.DEPT_CODE = AMD.DEPT_CODE\n" +
                    "   AND AMD.COMPANY_CODE = ?\n" +
                    "   AND AME.EMPLOYEE_NUMBER = EFA.ASSIGNED_TO_NUMBER\n" +
                    "   AND AMD.DEPT_CODE = dbo.NVL(?, AMD.DEPT_CODE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n";

            sqlArgs.add(userAccount.getBookTypeCode());
            sqlArgs.add(userAccount.getCompanyCode());
            sqlArgs.add(dto.getDeptCode());
            sqlArgs.add(dto.getFromDate());
//            sqlArgs.add(dto.getFromDate());
            sqlArgs.add(dto.getToDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        }
        catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }
}
