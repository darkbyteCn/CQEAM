package com.sino.sinoflow.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.sinoflow.user.dto.PersonOrderDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */


public class PersonOrderModel extends AMSSQLProducer {
    /**
     * 功能：用户维护Model构造函数
     * @param userAccount  BaseUserDTO 代表执行当前操作的用户
     * @param dtoParameter SfUserBaseDTO 代表当前操作的数据
     */
    public PersonOrderModel(SfUserBaseDTO userAccount, PersonOrderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        PersonOrderDTO dto = (PersonOrderDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =

                "SELECT\n" +
                        "  SAA.SFACT_PROC_NAME,\n" +
                        "  SAA.SFACT_APPL_COLUMN_1,\n" +
                        "  SAA.SFACT_APPL_ID,\n" +
                        "  SAA.SFACT_CREATE_DT,\n" +
                        "  SU.USERNAME,\n" +
                        "  COUNT(1) CNT\n" +
                        "FROM\n" +
                        "  SF_ACT_ARCHIVE SAA ,\n" +
                        "  SF_USER SU\n" +
                        "WHERE\n" +
                        "  SAA.SFACT_COMPOSE_USER=SU.LOGIN_NAME\n" +
                        "  AND (?='' OR SAA.SFACT_PROC_NAME=?)\n" +
                        "  AND (?='' OR SAA.SFACT_APPL_COLUMN_1=?)\n" +
                        "  AND (SAA.SFACT_COMPLETE_USER=? OR SAA.SFACT_SIGN_USER=? OR SAA.SFACT_PICK_USER=?)\n" +
                        "GROUP BY\n" +
                        "  SAA.SFACT_PROC_NAME,\n" +
                        "  SAA.SFACT_APPL_COLUMN_1,\n" +
                        "  SAA.SFACT_APPL_ID,\n" +
                        "  SFACT_CREATE_DT,\n" +
                        "  SAA.SFACT_COMPOSE_USER,\n" +
                        "  SU.USERNAME\n"+
                        " ORDER BY SAA.SFACT_PROC_NAME,SAA.SFACT_APPL_COLUMN_1";

        sqlArgs.add(dto.getTransName());
        sqlArgs.add(dto.getTransName());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(userAccount.getLoginName());
        sqlArgs.add(userAccount.getLoginName());
        sqlArgs.add(userAccount.getLoginName());
        

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
