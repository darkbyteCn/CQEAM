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
public class SpareVendorModel extends AMSSQLProducer {

	public SpareVendorModel(SfUserDTO userAccount, AmsVendorInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel doVerify(String vendorName) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql ="SELECT 1 FROM AMS_SPARE_VENDORS ASV WHERE ASV.VENDOR_NAME = ?";
        strArg.add(vendorName);
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr =  "DELETE FROM AMS_SPARE_VENDORS WHERE VENDOR_ID = ?";
        sqlArgs.add(dto.getVendorId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = " UPDATE AMS_SPARE_VENDORS\n" +
                "    SET VENDOR_NAME = ISNULL(?, VENDOR_NAME)\n" +
                "  WHERE VENDOR_ID = ?";
        sqlArgs.add(dto.getVendorName());
        sqlArgs.add(dto.getVendorId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_SPARE_VENDORS\n" +
                "  (VENDOR_ID,\n" +
                "   VENDOR_NAME)\n" +
                "VALUES\n" +
                "  (?,?)";
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getVendorName());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "SELECT ASV.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME\n" +
                "  FROM AMS_SPARE_VENDORS ASV\n" +
                " WHERE ASV.VENDOR_ID = ?";
        sqlArgs.add(dto.getVendorId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getVendorIdModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT CASE MAX(CAST(ASV.VENDOR_ID AS INT)) + 1 WHEN NULL THEN 1 ELSE MAX(CAST(ASV.VENDOR_ID AS INT)) + 1 END VENDOR_ID FROM AMS_SPARE_VENDORS ASV";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        AmsVendorInfoDTO dto = (AmsVendorInfoDTO) dtoParameter;
        String sqlStr = "SELECT ASV.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME\n" +
                "  FROM AMS_SPARE_VENDORS ASV\n" +
                " WHERE ("+ SyBaseSQLUtil.isNull()+" OR ASV.VENDOR_NAME LIKE ?)";
        sqlArgs.add(dto.getVendorName());
        sqlArgs.add(dto.getVendorName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
