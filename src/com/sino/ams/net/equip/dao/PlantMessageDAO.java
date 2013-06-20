package com.sino.ams.net.equip.dao;

import java.sql.Connection;

import com.sino.ams.net.equip.dto.PlantMessageDTO;
import com.sino.ams.net.equip.model.PlantMessageModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-2-21
 * Time: 16:00:31
 * To change this template use File | Settings | File Templates.
 */
public class PlantMessageDAO extends BaseDAO {


    private SfUserDTO sfUser;

    {
        sfUser = null;
    }

    /**
     * 功能：构造函数。
     *
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public PlantMessageDAO(SfUserDTO userAccount, PlantMessageDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
         sfUser = userAccount;
    }
    /**
    * 功能：SQL生成器BaseSQLProducer的初始化。
    *
    * @param userAccount  BaseUserDTO 本系统最终操作用户类
    * @param dtoParameter DTO 本次操作的数据
    */

     protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
       PlantMessageDTO dtoPara = (PlantMessageDTO) dtoParameter;
       super.sqlProducer = new PlantMessageModel((SfUserDTO) userAccount, dtoPara);



    }
}
