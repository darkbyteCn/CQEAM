package com.sino.ams.spare.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.model.BjVendorModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjVendorDAO extends AMSBaseDAO {

	public BjVendorDAO(SfUserDTO userAccount, AmsVendorInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
		sqlProducer = new BjVendorModel(user, dto);
	}

    public boolean doVerify(String vendorId, String vendorName) throws QueryException {//执行校验该地点是否存在设备
       boolean success = false;
       BjVendorModel spareModel = (BjVendorModel) sqlProducer;
       SQLModel sqlModel = spareModel.doVerify(vendorId, vendorName);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       success = sq.hasResult();
       return success;
   }
}
