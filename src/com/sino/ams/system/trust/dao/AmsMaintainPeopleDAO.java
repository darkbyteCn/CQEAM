package com.sino.ams.system.trust.dao;


import java.sql.Connection;

import com.sino.ams.system.trust.dto.AmsMaintainPeopleDTO;
import com.sino.ams.system.trust.model.AmsMaintainPeopleModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsMaintainPeopleDAO</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainPeopleDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainPeopleDAO extends BaseDAO {

    private AmsMaintainPeopleModel amsMaintainPeopleModel = null;
    private SfUserDTO SfUser = null;

    /**
     * 功能：代维人员表(EAM) AMS_MAINTAIN_PEOPLE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsMaintainPeopleDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsMaintainPeopleDAO(SfUserDTO userAccount, AmsMaintainPeopleDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsMaintainPeopleDTO dtoPara = (AmsMaintainPeopleDTO) dtoParameter;
        super.sqlProducer = new AmsMaintainPeopleModel((SfUserDTO) userAccount, dtoPara);
        amsMaintainPeopleModel = (AmsMaintainPeopleModel) sqlProducer;
    }

    /**
     * 功能：插入代维人员表(EAM)表“AMS_MAINTAIN_PEOPLE”数据。

     */
    public void createData() throws DataHandleException {
    	AmsMaintainPeopleDTO dto=(AmsMaintainPeopleDTO)dtoParameter;
    	SeqProducer seq = new SeqProducer(conn);
    	dto.setUserId(seq.getGUID());
        super.createData();
        getMessage().addParameterValue("代维人员表");
    }

    /**
     * 功能：更新代维人员表(EAM)表“AMS_MAINTAIN_PEOPLE”数据。

     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("代维人员表");
    }

    /**
     * 功能：删除代维人员表(EAM)表“AMS_MAINTAIN_PEOPLE”数据。

     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("代维人员表");
	}

}