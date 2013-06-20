package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO;
import com.sino.ams.newasset.model.AmsAssetsNoMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-14
 * Time: 16:01:36
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsNoMatchDAO extends AMSBaseDAO {
    public AmsAssetsNoMatchDAO(SfUserDTO userAccount,
                               AmsAssetsNoMatchDTO dtoParameter,
                               Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsNoMatchDTO dtoPara = (AmsAssetsNoMatchDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsNoMatchModel((SfUserDTO) userAccount,
                dtoPara);
    }

}
