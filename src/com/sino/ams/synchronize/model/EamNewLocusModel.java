package com.sino.ams.synchronize.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-11
 * Time: 8:34:20
 * To change this template use File | Settings | File Templates.
 */
public class EamNewLocusModel extends AMSSQLProducer {
	private EamSyschronizeDTO dto = null;

	/**
	 * 功能：eAM新增地点同步 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemMatchDTO 本次操作的数据
	 */
	public EamNewLocusModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
		super(userAccount, dtoParameter);
		dto = dtoParameter;
	}

	/**
	 * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr;

        sqlStr = "SELECT " +
//                "DISTINCT \n" +
                "                EO.WORKORDER_OBJECT_NO,\n" +
                "                EO.WORKORDER_OBJECT_CODE,\n" +
                "                EO.WORKORDER_OBJECT_LOCATION,\n" +
                "                EO.LOCATION_CODE,\n" +
                "                TT.ASSETS_LOCATION_CODE,\n" +
                "                TT.ASSETS_LOCATION,\n" +
                "                dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY,\n" +
                "                                           'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" +
                "                EO.ORGANIZATION_ID,\n" +
                "                EO.CREATED_BY,\n" +
                "                EO.WORKORDER_OBJECT_NAME,\n" +
                "                EO.LAST_UPDATE_DATE,\n" +
                "                SU.USERNAME,\n" +
                "                EO.REMARK,\n" +
                "                EO.CREATION_DATE\n" +
                "  FROM ETS_OBJECT EO, ETS_FA_ASSETS_LOCATION TT, SF_USER SU\n" +
                " WHERE (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') \n" +
                "   AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%.无效'\n" +
                "   AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%.缺省'\n" +
                "   AND EO.LOCATION_CODE *= TT.ASSETS_LOCATION_CODE\n" +
                "   AND EO.ORGANIZATION_ID *= TT.ORG_ID\n" +
                "   AND EO.LAST_UPDATE_BY *= SU.USER_ID\n" +
                "   AND (EO.OBJECT_CATEGORY < '70' OR EO.OBJECT_CATEGORY = '80')\n" +
                "   AND (NOT EXISTS\n" +
                "        (SELECT NULL\n" +
                "           FROM ETS_FA_ASSETS_LOCATION T\n" +
                "          WHERE T.ASSETS_LOCATION_CODE = EO.LOCATION_CODE\n" +
                "            AND T.ORG_ID = EO.ORGANIZATION_ID) OR EXISTS\n" +
                "        (SELECT NULL\n" +
                "           FROM ETS_FA_ASSETS_LOCATION T\n" +
                "          WHERE T.ASSETS_LOCATION_CODE = EO.LOCATION_CODE\n" +
                "            AND T.ORG_ID = EO.ORGANIZATION_ID\n" +
                "            AND T.ASSETS_LOCATION <> EO.WORKORDER_OBJECT_NAME))\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT NULL\n" +
                "          FROM ETS_FA_NEW_LOC EFC\n" +
                "         WHERE EFC.LOCATION = EO.WORKORDER_OBJECT_LOCATION\n" +
                "           AND EFC.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "           AND (EFC.STATUS = 0 OR\n" +
                "               (EFC.STATUS = 1 AND CONVERT(VARCHAR,EFC.CREATION_DATE, 110) =\n" +
                "               CONVERT(VARCHAR,GETDATE(), 110))))\n" +
                "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
                "   AND (EO.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  OR EO.DISABLE_DATE >= GETDATE() OR EO.DISABLE_DATE IS NULL)\n" +
                "   AND EO.ORGANIZATION_ID = ?\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.CREATION_DATE >= ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR dateadd(day,-1,EO.CREATION_DATE) < ?)\n" +
                " ORDER BY EO.CREATION_DATE DESC";
        try {
            sqlArgs.add(dto.getNewAssetsLocation());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(userAccount.getOrganizationId());

            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getEndDate());
        } catch (CalendarException e) {
            e.printStackTrace();  
        }
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：同步把查询到的内容插入另两个表里
	 * @return SQLModel
	 */
//	public SQLModel getSyschronizeModel() {
//        SQLModel sqlModel = new SQLModel();
//        EamSyschronizeDTO dto = (EamSyschronizeDTO) dtoParameter;
//        List sqlArgs = new ArrayList();
//        String sqlStr = "INSERT INTO"
//						+ " ETS_MISFA_UPDATE_BATCH ("
//						+ " BATCH_ID,"
//						+ " REMARK,"
//						+ " ORGANIZATION_ID,"
//						+ " CREATION_DATE,"
//						+ " CREATED_BY"
//						+ " ) VALUES (?, ?, ?, ?, ?)";
//        sqlArgs.add(dto.getLogId());
//        sqlArgs.add(userAccount.getUserId());
//        sqlArgs.add(dto.getLocation());
//        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
//        return sqlModel;
//    }
}
