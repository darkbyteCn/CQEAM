package com.sino.ams.system.rent.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.rent.model.AMSRentChangeQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2010-2-5
 * Time: 10:24:08
 * To change this template use File | Settings | File Templates.
 */
public  class AMSRentChangeQueryDAO extends AMSBaseDAO {
    
    public AMSRentChangeQueryDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        sqlProducer = new AMSRentChangeQueryModel((SfUserDTO) userAccount, dtoPara);
    }
    
}
