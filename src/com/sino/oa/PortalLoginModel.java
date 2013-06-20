package com.sino.oa;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-10-13
 * Time: 上午1:33
 * To change this template use File | Settings | File Templates.
 */
public class PortalLoginModel extends BaseSQLProducer {
    private SfUserBaseDTO dto = null;

    public PortalLoginModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = (SfUserBaseDTO) userAccount;
    }

    /**
     * 获取密码过期天数,>0表示已过期
     * @return SQLModel
     */
    public SQLModel getCheckPswdDateModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT ROUND(SYSDATE - SU.PASSWORD_DATE) DAYS  FROM  SF_USER SU\n" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        strArg.add(dto.getLoginName());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }


    public SQLModel getLoginErrCountModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT LOGIN_ERR_COUNT FROM  SF_USER SU\n" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        strArg.add(dto.getLoginName());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    /**
     * 将登录错误次数加1
     * @return SQLModel
     */
    public SQLModel getAddLoginErrCountModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "UPDATE   SF_USER SU\n" +
                "  SET  LOGIN_ERR_COUNT = LOGIN_ERR_COUNT + 1" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        strArg.add(dto.getLoginName());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    /**
     * 将登录错误次数清零
     * @return SQLModel
     */
    public SQLModel getClearLoginErrCountModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "UPDATE   SF_USER SU\n" +
                "  SET  LOGIN_ERR_COUNT = 0" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        strArg.add(dto.getLoginName());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    /**
     * 判断密码是否为初始密码
     * @return SQLModel
     */
    public SQLModel getIsDefaultPasswordModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT 1 FROM   SF_USER SU\n" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)" +
                "   AND SU.PASSWORD = dbo.SFK_ENCODE(?)";
        strArg.add(dto.getLoginName());
        strArg.add("123");
//        strArg.add(WebAttrConstant.DEFAULT_PASSWORD);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }


}
