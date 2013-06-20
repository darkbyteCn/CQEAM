package com.sino.ams.task.model.soa;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：模型层对象</p>
 * <p>描述: 构造获取指定OU组织下地点组合发生变动的模型对象</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class LocationCombinationSearchModel extends BaseSQLProducer {


    /**
     * <p>功能说明：EAM新增地点同步 数据库SQL构造层构造函数
     *
     * @param userAccount  BaseUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter DTO 本次操作的数据
     */
    public LocationCombinationSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * <p>功能说明：构造获取指定OU组织下地点组合发生变动的模型对象</p>
     *
     * @param organizationId OU组织ID
     * @return SQLModel 返回指定OU组织下地点组合发生变动的模型对象
     */
    public SQLModel getChangedLocationCombinationModel(int organizationId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TOP 500 EO.WORKORDER_OBJECT_NO\n" +
                "FROM   ETS_OBJECT             EO,\n" +
                "       ETS_FA_ASSETS_LOCATION TT\n" +
                "WHERE  EO.WORKORDER_OBJECT_NAME NOT LIKE '%.无效'\n" +
                "       AND EO.WORKORDER_OBJECT_CODE *= TT.ASSETS_LOCATION_CODE\n" +
                "       AND EO.ORGANIZATION_ID *= TT.ORG_ID\n" +
                "       AND (CONVERT(INT, EO.OBJECT_CATEGORY) < 70 OR\n" +
                "       CONVERT(INT, EO.OBJECT_CATEGORY) = 80)\n" +
                "       AND (NOT EXISTS\n" +
                "        (SELECT NULL\n" +
                "             FROM   ETS_FA_ASSETS_LOCATION T\n" +
                "             WHERE  T.ASSETS_LOCATION_CODE = EO.WORKORDER_OBJECT_CODE\n" +
                "                    AND T.ORG_ID = EO.ORGANIZATION_ID) OR EXISTS\n" +
                "        (SELECT NULL\n" +
                "             FROM   ETS_FA_ASSETS_LOCATION T\n" +
                "             WHERE  T.ASSETS_LOCATION_CODE = EO.WORKORDER_OBJECT_CODE\n" +
                "                    AND T.ORG_ID = EO.ORGANIZATION_ID\n" +
                "                    AND T.ASSETS_LOCATION <> EO.WORKORDER_OBJECT_NAME))\n" +
                "       AND NOT EXISTS\n" +
                " (SELECT NULL\n" +
                "        FROM   ETS_FA_NEW_LOC EFC\n" +
                "        WHERE  EFC.LOCATION = EO.WORKORDER_OBJECT_NAME\n" +
                "               AND EFC.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "               AND EFC.STATUS = 1\n" +
                "               AND DATEDIFF(DD, EO.CREATION_DATE, GETDATE()) = 0)\n" +
                "       AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '' OR\n" +
                "       EO.DISABLE_DATE >= GETDATE())\n" +
                "       AND EO.ORGANIZATION_ID = ?\n" +
                "ORDER  BY EO.CREATION_DATE DESC ";

        sqlArgs.add(organizationId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
