package com.sino.ams.others.dao;

import java.sql.Connection;

import com.sino.ams.others.dto.NoBarcodeDTO;
import com.sino.ams.others.model.NoBarcodeQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-9
 * Time: 16:05:46
 * To change this template use File | Settings | File Templates.
 */
public class NoBarcodeQueryDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private NoBarcodeDTO amsItemTransH = null;

    public NoBarcodeQueryDAO(SfUserDTO userAccount, NoBarcodeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.amsItemTransH = (NoBarcodeDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        NoBarcodeDTO dtoPara = (NoBarcodeDTO) dtoParameter;
        super.sqlProducer = new NoBarcodeQueryModel((SfUserDTO) userAccount, dtoPara);
    }
}
