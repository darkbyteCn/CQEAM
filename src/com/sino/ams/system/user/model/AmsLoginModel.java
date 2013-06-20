package com.sino.ams.system.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-24
 */
public class AmsLoginModel extends AMSSQLProducer {
    private SfUserDTO dto = null;

    public AmsLoginModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = (SfUserDTO) dtoParameter;
    }

    /**
     * 获取密码过期天数,>0表示已过期
     * @return SQLModel
     */
    public SQLModel getCheckPswdDateModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT SU.PASSWORD_DATE  FROM  SF_USER SU\n" +
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
     public SQLModel getDisableModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT 'A'\n" +
                "  FROM SF_USER T\n" +
                " WHERE dbo.NVL(T.DISABLE_DATE, GETDATE()) >= GETDATE()\n" +
                "   AND UPPER(T.LOGIN_NAME) = UPPER(?)";
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
        String strSql = "UPDATE SF_USER\n" +
                "  SET  LOGIN_ERR_COUNT = LOGIN_ERR_COUNT + 1" +
                " WHERE UPPER(LOGIN_NAME) = UPPER(?)";
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
        strArg.add(WebAttrConstant.DEFAULT_PASSWORD);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }


}
