package com.sino.ams.system.user.dao;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.AmsSynRightDTO;
import com.sino.ams.system.user.model.AmsSynRightModel;
import com.sino.ams.constant.CustMessageKey;
import com.sino.base.dto.DTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.DataHandleException;
import com.sino.base.util.StrUtil;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-6-12
 * Time: 11:19:50
 * To change this template use File | Settings | File Templates.
 */
public class AmsSynRightDAO extends BaseDAO {

    /**
     * 功能：SF_GROUP 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfGroupDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsSynRightDAO(SfUserDTO userAccount, AmsSynRightDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsSynRightDTO dtoPara = (AmsSynRightDTO) dtoParameter;
        super.sqlProducer = new AmsSynRightModel((SfUserDTO) userAccount, dtoPara);
    }

    public boolean updateByUser(String organizationIds[]) {
        boolean operateResult = false;
        try {
            AmsSynRightDTO dtouser = (AmsSynRightDTO) dtoParameter;
            AmsSynRightModel model = (AmsSynRightModel) sqlProducer;
            SQLModel sqlModel = model.deletByUser();
            DBOperator.updateRecord(sqlModel, conn);
            for (int i = 0; i < organizationIds.length; i++) {
                AmsSynRightDTO dto = new AmsSynRightDTO();
                dto.setUserId(dtouser.getUserId());
                dto.setOrganizationId(StrUtil.strToInt(organizationIds[i]));
                SQLModel sm = model.insertByUser(dto);
                DBOperator.updateRecord(sm, conn);
            }
            operateResult = true;
            prodMessage(CustMessageKey.PRIVI_SAVE_SUCCESS);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(CustMessageKey.RRIVI_SAVE_FAILURE);
        }
        return operateResult;
    }
}