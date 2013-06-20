package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO;
import com.sino.ams.newasset.model.AmsAssetsNoMatchDetailModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 11:20:14
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsNoMatchDetailDAO extends AMSBaseDAO {
    public AmsAssetsNoMatchDetailDAO(SfUserDTO userAccount,
                                     AmsAssetsNoMatchDTO dtoParameter,
                                     Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsNoMatchDTO dtoPara = (AmsAssetsNoMatchDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsNoMatchDetailModel((SfUserDTO)
                userAccount, dtoPara);
    }

}
