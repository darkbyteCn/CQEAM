package com.sino.ams.task.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.config.SinoConfig;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：模型层对象</p>
 * <p>描述: 调度任务中需要用到的公共模型构造类</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class TaskModelProducer {

    /**
     * <p>功能说明：构造获取所有资产账簿的模型对象 </p>
     *
     * @param isTd 是否TD,是传Y，或者传N
     * @return 获取所有资产账簿的模型对象
     */
    public static SQLModel getBookTypeCodeModel(String isTd) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT ACB.BOOK_TYPE_CODE\n" +
                "  FROM AMS_COMPANY_BOOK ACB\n" +
                " WHERE (ACB.BOOK_TYPE_CODE LIKE '%FA%' OR ACB.BOOK_TYPE_CODE LIKE '%IA%')\n" +
                "   AND EXISTS (SELECT NULL\n" +
                "          FROM ETS_OU_CITY_MAP EOCM\n" +
                "         WHERE ACB.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "           AND EOCM.IS_TD = ?)";

        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(isTd);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * <p>功能说明：构造获取所有OU组织的模型对象 </p>
     *
     * @param isTd 是否TD,是传Y，或者传N
     * @return 获取所有资产账簿的模型对象
     */
    public static SQLModel getCompanyListModel(String isTd) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       EOCM.COMPANY_CODE,\n" +
                "       EOCM.BOOK_TYPE_CODE,\n" +
                "       EOCM.BOOK_TYPE_NAME\n" +
                "FROM   ETS_OU_CITY_MAP EOCM\n" +
                "WHERE  EOCM.IS_TD = ?\n" +
                "ORDER  BY EOCM.ORGANIZATION_ID";

        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(isTd);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * <p>功能说明：构造获取值集名称的模型对象  </p>
     *
     * @param source 值集名称来源，可选值为MIS或者TDMIS
     * @return 获取所有资产账簿的模型对象
     */
    public static SQLModel getFlexValueSetModel(String source) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT MFFVS.FLEX_VALUE_SET_NAME,\n" +
                "       MFFVS.FLEX_VALUE_SET_NAME\n" +
                "FROM   M_FND_FLEX_VALUE_SETS MFFVS\n" +
                "       WHERE MFFVS.SOURCE = ?";

        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(source);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }


    /**
     * <p>功能说明：构造获取定时任务执行人的模型对象  </p>
     *
     * @return 获取定时任务执行人的模型对象
     * @throws UnsupportedEncodingException 根据SinoConfig获取系统管理员角色出错时抛出该异常
     */
    public static SQLModel getTaskExecutorModel() throws UnsupportedEncodingException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT TOP 1 SU.*\n" +
                "FROM   SF_USER SU\n" +
                "WHERE  EXISTS (SELECT NULL\n" +
                "        FROM   SF_USER_AUTHORITY SUA\n" +
                "        WHERE  SU.USER_ID = SUA.USER_ID\n" +
                "               AND SUA.ROLE_NAME = ?)";
        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(SinoConfig.getSystemAdminRole());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * <p>功能说明：构造获取定时任务执行人的模型对象 </p>
     *
     * @param organizationId OU组织ID
     * @return 获取定时任务执行人的模型对象
     */
    public static SQLModel getOUTaskExecutorModel(int organizationId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT TOP 1 SU.USER_ID,\n" +
                "       SU.USERNAME,\n" +
                "       SU.LOGIN_NAME,\n" +
                "       SU.ENABLED,\n" +
                "       SU.ORGANIZATION_ID,\n" +
                "       SU.EMPLOYEE_NUMBER\n" +
                "FROM   SF_USER                    SU,\n" +
                "       ETS_MISFA_TRANSACTION_RESP EMTR\n" +
                "WHERE  SU.EMPLOYEE_NUMBER = EMTR.EMPLOYEE_NUMBER\n" +
                "       AND SU.ORGANIZATION_ID = ?\n";

        List<Integer> sqlArgs = new ArrayList<Integer>();
        sqlArgs.add(organizationId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * <p>功能说明：构造获取指定资产账簿最新的关闭的会计期间的模型对象 </p>
     *
     * @param bookTypeCode 资产账簿代码
     * @return 获取获取指定资产账簿最新的关闭的会计期间的模型对象
     */
    public static SQLModel getPeriodNameModel(String bookTypeCode) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT MAX(SAPS.MIS_PERIOD_NAME) MIS_PERIOD_NAME\n" +
                "FROM   SRV_ASSET_PERIOD_STATUS SAPS\n" +
                "WHERE  SAPS.PERIOD_STATUS = 'CLOSE'\n" +
                "       AND SAPS.BOOK_TYPE_CODE = ?";
        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(bookTypeCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * <p>功能说明：构造获取最新关闭的会计期间的模型对象 </p>
     *
     * @return 构造获取最新关闭的会计期间的模型对象
     */
    public static SQLModel getMaxPeriodNameModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT MAX(MIS_PERIOD_NAME) MIS_PERIOD_NAME FROM SRV_ASSET_PERIOD_STATUS " +
                "WHERE PERIOD_NAME IN (SELECT MAX(PERIOD_NAME) FROM SRV_ASSET_PERIOD_STATUS WHERE PERIOD_STATUS='CLOSE')";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
