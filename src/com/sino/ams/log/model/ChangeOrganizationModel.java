package com.sino.ams.log.model;


import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

import java.util.ArrayList;
import java.util.List;


public class ChangeOrganizationModel extends AMSSQLProducer {

    /**
     * 功能：用户个人收藏夹(EAM) ETS_FAVORITES 数据库SQL构造层构造函数
     * @param userAccount  BaseUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter DTO 本次操作的数据
     */

    public ChangeOrganizationModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.userAccount = (SfUserDTO)userAccount;
    }
    
    public SQLModel getOrganizationListByOANameModel(){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT \n" +
                "    EOCM.ORGANIZATION_ID,\n" +
                "    EOCM.COMPANY\n" +
                "FROM \n" +
                "    ETS_OU_CITY_MAP EOCM\n" +
                "WHERE\n" +
                "    EXISTS(\n" +
                "        SELECT \n" +
                "            NULL\n" +
                "        FROM\n" +
                "            SF_USER SU\n" +
                "        WHERE\n" +
                "            EOCM.ORGANIZATION_ID = SU.ORGANIZATION_ID\n" +
                "        AND SU.OA_NAME = ?\n" +
                "    )            \n" +
                " ";
        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(userAccount.getOaName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getLoginNameByOANameModel(){
        SQLModel sqlModel = new SQLModel();
        SfUserDTO dto = (SfUserDTO)dtoParameter;
        String sqlStr = "SELECT TOP 1\n" +
                "    SU.LOGIN_NAME\n" +
                "FROM \n" +
                "    SF_USER SU \n" +
                "WHERE \n" +
                "    SU.OA_NAME = ?\n" +
                "    AND SU.ORGANIZATION_ID = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(userAccount.getOaName());
        sqlArgs.add(dto.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

       return sqlModel;
    }

}