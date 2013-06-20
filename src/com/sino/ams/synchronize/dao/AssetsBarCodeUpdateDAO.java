package com.sino.ams.synchronize.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.AssetsBarCodeUpdateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-14
 * Time: 6:29:04
 * To change this template use File | Settings | File Templates.
 */
public class AssetsBarCodeUpdateDAO extends AMSBaseDAO {
      public SfUserDTO sfUser = null;

    /**
     * 功能：eAM新增地点同步 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsBarCodeUpdateDAO(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter, Connection conn) {
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
        EamSyschronizeDTO dtoPara = (EamSyschronizeDTO) dtoParameter;
        super.sqlProducer = new AssetsBarCodeUpdateModel((SfUserDTO) userAccount, dtoPara);
    }

   public void syschronizeLocus(String systemId) {
        CallableStatement cs = null;
        String callStr = "{CALL ams_syn_pkg.syn_asset_entry_change(?, ?, ?, ?)}";

        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, sfUser.getOrganizationId());
            cs.setString(2, systemId);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
        }
    }
}
