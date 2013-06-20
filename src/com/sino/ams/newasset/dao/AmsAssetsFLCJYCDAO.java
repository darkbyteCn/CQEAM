package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.newasset.model.AmsAssetsFLCJYCModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 20:55:55
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsFLCJYCDAO extends AMSBaseDAO {
    public AmsAssetsFLCJYCDAO(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCJYCDTO dtoPara = (AmsAssetsCJYCDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsFLCJYCModel((SfUserDTO) userAccount, dtoPara);
    }

}
