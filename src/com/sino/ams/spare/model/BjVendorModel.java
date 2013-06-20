package com.sino.ams.spare.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjVendorModel extends AMSSQLProducer {

	public BjVendorModel(SfUserDTO userAccount, AmsVendorInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel doVerify(String vendorId, String vendorName) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql ="SELECT 1 FROM AMS_VENDOR_INFO AVI WHERE AVI.VENDOR_ID = ? AND AVI.VENDOR_NAME = ?";
        strArg.add(vendorId);
        strArg.add(vendorName);
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr =  "DELETE FROM AMS_VENDOR_INFO WHERE VENDOR_CODE = ?";
        sqlArgs.add(dto.getVendorCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_VENDOR_INFO\n" +
                "   SET VENDOR_NAME = ISNULL(?, VENDOR_NAME),\n" +
                "       ADDRESS     = ISNULL(?, ADDRESS),\n" +
                "       CONTACT     = ISNULL(?, CONTACT),\n" +
                "       PHONE       = ISNULL(?, PHONE),\n" +
                "       VENDOR_ID   = ISNULL(?, VENDOR_ID),\n" +
                "       FAX         = ISNULL(?, FAX)\n" +
                " WHERE VENDOR_CODE = ?";
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getAddress());
            sqlArgs.add(dto.getContact());
            sqlArgs.add(dto.getPhone());
            sqlArgs.add(dto.getVendorId());
            sqlArgs.add(dto.getFax());
            sqlArgs.add(dto.getVendorCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_VENDOR_INFO\n" +
                "  (VENDOR_CODE,\n" +
                "   VENDOR_NAME,\n" +
                "   ADDRESS,\n" +
                "   CONTACT,\n" +
                "   PHONE,\n" +
                "   VENDOR_ID,\n" +
                "   FAX)\n" +
                "VALUES\n" +
                "(NEWID(), ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(dto.getVendorName());
        sqlArgs.add(dto.getAddress());
        sqlArgs.add(dto.getContact());
        sqlArgs.add(dto.getPhone());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getFax());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "SELECT AVI.VENDOR_CODE,\n" +
                "       AVI.VENDOR_NAME,\n" +
                "       AVI.ADDRESS,\n" +
                "       AVI.CONTACT,\n" +
                "       AVI.PHONE,\n" +
                "       AVI.FAX,\n" +
                "       AVI.VENDOR_ID\n" +
                "  FROM AMS_VENDOR_INFO AVI\n" +
                " WHERE AVI.VENDOR_CODE = ?";
        sqlArgs.add(dto.getVendorCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "SELECT AVI.VENDOR_CODE,\n" +
                "       AVI.VENDOR_NAME,\n" +
                "       AVI.ADDRESS,\n" +
                "       AVI.CONTACT,\n" +
                "       AVI.PHONE,\n" +
                "       AVI.FAX,\n" +
                "       ASV.VENDOR_NAME SPARE_VENDOR_NAME\n" +
                "  FROM AMS_VENDOR_INFO   AVI,\n" +
                "       AMS_SPARE_VENDORS ASV\n" +
                " WHERE AVI.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AVI.VENDOR_NAME LIKE ?)";
            sqlArgs.add(dto.getVendorName());
            sqlArgs.add(dto.getVendorName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
