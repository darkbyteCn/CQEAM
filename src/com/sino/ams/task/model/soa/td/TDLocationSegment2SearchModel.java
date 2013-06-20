package com.sino.ams.task.model.soa.td;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.config.SinoConfig;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：模型层对象</p>
 * <p>描述: 构造获取指定OU组织下无理地点发生变动的模型对象</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDLocationSegment2SearchModel extends BaseSQLProducer {


    /**
     * <p>功能说明：EAM新增地点同步 数据库SQL构造层构造函数
     *
     * @param userAccount  BaseUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter DTO 本次操作的数据
     */
    public TDLocationSegment2SearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * <p>功能说明：构造获取指定OU组织下无理地点发生变动的模型对象</p>
     *
     * @param organizationId OU组织ID
     * @return SQLModel 返回指定OU组织下无理地点发生变动的模型对象
     */
    public SQLModel getChangedLocationSegment2Model(int organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT TOTAL.LOC2_CODE,\n" +
                        "       TOTAL.LOC2_DESC,\n" +
                        "       TOTAL.OLD_LOC_DESC,\n" +
                        "       TOTAL.COMPANY_CODE,\n" +
                        "       TOTAL.ORGANIZATION_ID,\n" +
                        "       TOTAL.COMPANY_NAME,\n" +
                        "       TOTAL.WORKORDER_CATEGORY,\n" +
                        "       TOTAL.AREA_TYPE_NAEM,\n" +
                        "       TOTAL.CITY,\n" +
                        "       TOTAL.COUNTY,\n" +
                        "       TOTAL.LOC_TYPE\n" +
                        "  FROM (SELECT EOL.LOC2_CODE,\n" +
                        "               EOL.LOC2_DESC,\n" +
                        "               '' OLD_LOC_DESC,\n" +
                        "               EOL.COMPANY_CODE,\n" +
                        " EOCM.MATCH_ORGANIZATION_ID ORGANIZATION_ID,\n" +
                        "               EOCM.COMPANY COMPANY_NAME,\n" +
                        "               dbo.GET_FLEX_VALUE(EOL.OBJECT_CATEGORY, 'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" +
                        "               dbo.GET_FLEX_VALUE(EOL.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAEM,\n" +
                        "               EOL.CITY,\n" +
                        "               EOL.COUNTY,\n" +
                        "               '新增' LOC_TYPE\n" +
                        "          FROM ETS_OBJECT_LOC2 EOL, ETS_OU_CITY_MAP EOCM\n" +
                        "         WHERE EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                        "	AND (EOL.SHARE_TYPE='3' OR EOL.SHARE_TYPE='2')\n" +
                        "           AND NOT EXISTS\n" +
                "         (SELECT 1\n" +
                "                  FROM M_FND_FLEX_VALUE_SETS MFFVS, M_FND_FLEX_VALUES MFFV\n" +
                "                 WHERE MFFVS.FLEX_VALUE_SET_ID = MFFV.FLEX_VALUE_SET_ID\n" +
                "                   AND MFFVS.FLEX_VALUE_SET_NAME = ?\n" +
                "                   AND EOL.LOC2_CODE = MFFV.FLEX_VALUE \n" +
                        "           AND MFFVS.SOURCE='TDMIS')\n" +
                "           AND EOCM.MATCH_ORGANIZATION_ID = ?\n";
        sqlStr +=
                "        UNION ALL\n" +
                        "        SELECT EOL.LOC2_CODE,\n" +
                        "               EOL.LOC2_DESC,\n" +
                        "               MFFV.DESCRIPTION OLD_LOC_DESC,\n" +
                        "               EOL.COMPANY_CODE,\n" +
                        " EOCM.MATCH_ORGANIZATION_ID ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY COMPANY_NAME,\n" +
                        "               dbo.GET_FLEX_VALUE(EOL.OBJECT_CATEGORY, 'OBJECT_CATEGORY') WORKORDER_CATEGORY,\n" +
                        "               dbo.GET_FLEX_VALUE(EOL.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAEM,\n" +
                        "               EOL.CITY,\n" +
                        "               EOL.COUNTY,\n" +
                        "               '修改' LOC_TYPE\n" +
                        "          FROM ETS_OBJECT_LOC2       EOL,\n" +
                        "               M_FND_FLEX_VALUE_SETS MFFVS,\n" +
                        "               M_FND_FLEX_VALUES     MFFV,\n" +
                        "               ETS_OU_CITY_MAP       EOCM\n" +
                        "         WHERE MFFVS.FLEX_VALUE_SET_ID = MFFV.FLEX_VALUE_SET_ID\n" +
                        "           AND MFFVS.FLEX_VALUE_SET_NAME = ?\n" +
                        "           AND EOL.LOC2_CODE = MFFV.FLEX_VALUE\n" +
                        "         AND MFFVS.SOURCE='TDMIS'\n" +
                "	     AND EOCM.MATCH_ORGANIZATION_ID = ?" +
                "	     AND (EOL.SHARE_TYPE='3' OR EOL.SHARE_TYPE='2')\n" +
                        "     AND EOL.LOC2_DESC <> MFFV.DESCRIPTION\n" +
                "           AND EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "           ) TOTAL\n" +
                " WHERE NOT EXISTS (SELECT NULL\n" +
                "          FROM ETS_FA_NEW_LOC EFC\n" +
                "         WHERE EFC.LOCATION = TOTAL.LOC2_DESC\n" +
                "           AND EFC.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID\n" +
                "           AND EFC.STATUS = 1\n" +
                "           AND CONVERT(INT, CONVERT(CHAR, EFC.CREATION_DATE, 112)) =\n" +
                "               CONVERT(INT, CONVERT(CHAR, GETDATE(), 112)))";
        sqlArgs.add(SinoConfig.getFlexValueSetNameTD());
        sqlArgs.add(organizationId);
        sqlArgs.add(SinoConfig.getFlexValueSetNameTD());
        sqlArgs.add(organizationId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
