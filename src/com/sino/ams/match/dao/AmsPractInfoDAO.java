package com.sino.ams.match.dao;

import java.sql.Connection;

import com.sino.ams.match.dto.AmsAssetsInfoDTO;
import com.sino.ams.match.model.AmsAccountInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-7-21
 * Time: 15:17:58
 * To change this template use File | Settings | File Templates.
 */

public class AmsPractInfoDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

    public AmsPractInfoDAO(SfUserDTO userAccount, AmsAssetsInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsInfoDTO dtoPara = (AmsAssetsInfoDTO) dtoParameter;
        super.sqlProducer = new AmsAccountInfoModel((SfUserDTO) userAccount, dtoPara);
    }
}
