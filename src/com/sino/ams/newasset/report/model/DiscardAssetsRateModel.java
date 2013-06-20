package com.sino.ams.newasset.report.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.report.dto.DeptAssetsReportDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-10
 * Time: 19:32:31
 * To change this template use File | Settings | File Templates.
 */
public class DiscardAssetsRateModel extends AMSSQLProducer {

    public DiscardAssetsRateModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
    }

/**
	 * 功能：获取盘点统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        DeptAssetsReportDTO dto = (DeptAssetsReportDTO) dtoParameter;
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       AA.A DIS_QTY,\n" +
                "       BB.B AREDY_QTY,\n" +
                /* "       (DECODE(TRUNC(100 * BB.B / AA.A),\n" +
                "               0,\n" +
                "               REPLACE(ROUND(100 * BB.B / AA.A, 3), '.', '0.'),\n" +
                "               TO_CHAR(ROUND(100 * BB.B / AA.A, 3))) || '%') ASSETS_RATE\n" +*/
                "       STR(CONVERT(FLOAT, BB.B) / CONVERT(FLOAT, AA.A) * 100, 18, 2) || '%' ASSETS_RATE "+
                "FROM   ETS_OU_CITY_MAP EOCM,\n" +
                "       (SELECT EFA.ORGANIZATION_ID,\n" +
                "               COUNT(EII.ITEM_QTY) A\n" +
                "        FROM   ETS_ITEM_INFO  EII,\n" +
                "               ETS_FA_ASSETS  EFA,\n" +
                "               ETS_ITEM_MATCH EIM\n" +
                "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                "               AND EII.ITEM_STATUS IN\n" +
                "               ('TO_DISCARD', 'DISCARDED', 'DONATE', 'SELL', 'RENT')\n" +
                //"               AND GETDATE() > DATEADD(MM, ?, EFA.DATE_RETIRED)\n" +
                "               AND GETDATE() > DATEADD(MM, ?, (CASE EFA.DATE_RETIRED WHEN NULL THEN '1900' ELSE EFA.DATE_RETIRED END)) \n " +
                "        GROUP  BY EFA.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EFA.ORGANIZATION_ID,\n" +
                "               COUNT(EII.ITEM_QTY) B\n" +
                "        FROM   ETS_ITEM_INFO  EII,\n" +
                "               ETS_FA_ASSETS  EFA,\n" +
                "               ETS_ITEM_MATCH EIM\n" +
                "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
                "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
                "               AND EII.ITEM_STATUS IN ('DONATE', 'SELL', 'RENT')\n" +
                //"               AND GETDATE() > DATEADD(MM, ?, EFA.DATE_RETIRED)\n" +
                "               AND GETDATE() > DATEADD(MM, ?, (CASE EFA.DATE_RETIRED WHEN NULL THEN '1900' ELSE EFA.DATE_RETIRED END)) \n " +
                "        GROUP  BY EFA.ORGANIZATION_ID) BB\n" +
                "WHERE  EOCM.ORGANIZATION_ID = AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID = BB.ORGANIZATION_ID";
        sqlArgs.add(Integer.parseInt(dto.getMonth()));
        sqlArgs.add(Integer.parseInt(dto.getMonth()));
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
	}
}
