package com.sino.ams.spare.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.model.SpareVendorModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.ContainerException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.base.util.StrUtil;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareVendorDAO extends AMSBaseDAO {

	public SpareVendorDAO(SfUserDTO userAccount, AmsVendorInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
		sqlProducer = new SpareVendorModel(user, dto);
	}

    public boolean doVerify(String vendorName) throws QueryException {
       boolean success = false;
       SpareVendorModel spareModel = (SpareVendorModel) sqlProducer;
       SQLModel sqlModel = spareModel.doVerify(vendorName);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       success = sq.hasResult();
       return success;
   }

   public String getVendorId() throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        SpareVendorModel eoModel = (SpareVendorModel) sqlProducer;
        SQLModel sqlModel = eoModel.getVendorIdModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            employeeId = StrUtil.nullToString(row.getValue("VENDOR_ID"));
        }
        return employeeId;
    }
}
