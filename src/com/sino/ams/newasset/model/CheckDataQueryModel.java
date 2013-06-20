package com.sino.ams.newasset.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.base.dto.DTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.CalendarException;

import java.util.List;
import java.util.ArrayList;

public class CheckDataQueryModel extends AMSSQLProducer {

    public CheckDataQueryModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        try {
            String sqlStr = "SELECT AACH.TRANS_NO,\n" +
                    "       AACL.BARCODE,\n" +
                    "       EOCM.COMPANY COMPANY_NAME,\n" +
                    "       SG.GROUP_NAME GROUPNAME,\n" +
                    "       EO.WORKORDER_OBJECT_CODE LOCATION_CODE,\n" +
                    "       EO.WORKORDER_OBJECT_LOCATION CHECK_LOCATION,\n" +
                    "       AACH.CHECK_CATEGORY,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(AACH.CHECK_CATEGORY, 'ITEM_TYPE') CHECK_CATEGORY_NAME,\n" +
                    "       AACH.START_TIME,\n" +
                    "       SU1.USERNAME IMPLEMENT_USER,\n" +
                    "       AACH.IMPLEMENT_DAYS,\n" +
                    "       SU2.USERNAME ARCHIVED_USER,\n" +
                    "       AACH.ARCHIVED_DATE,\n" +
                    "       EFV.VALUE ORDER_STATUS,\n" +
                    "       AACH.HEADER_ID,\n" +
                    "       AACH.CREATED_BY,\n" +
                    "       AACH.CREATION_DATE,\n" +
                    "       AACH.ORDER_TYPE,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME,\n" +
                    "		AACH.DISTRIBUTE_DATE \n" +	
                    "FROM   AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "       ETS_OU_CITY_MAP         EOCM,\n" +
                    "       AMS_ASSETS_CHECK_BATCH  AACB,\n" +
                    "       SF_GROUP                SG,\n" +
                    "       ETS_OBJECT              EO,\n" +
                    "       SF_USER                 SU1,\n" +
                    "       SF_USER                 SU2,\n" +
                    "       ETS_FLEX_VALUES         EFV,\n" +
                    "       ETS_FLEX_VALUE_SET      EFVS,\n" +
                    "       AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "WHERE  AACH.BATCH_ID = AACB.BATCH_ID\n" +
                    "AND    AACB.GROUP_ID = SG.GROUP_ID\n" +
                    "AND    AACH.IMPLEMENT_BY *= SU1.USER_ID\n" +
                    "AND    AACH.ARCHIVED_BY *= SU2.USER_ID\n" +
                    "AND    AACH.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND    AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
                    "AND    AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "AND    AACH.ORDER_STATUS = EFV.CODE\n" +
                    "AND    EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                    "AND    AACH.ORDER_STATUS NOT IN ('ARCHIEVED', 'CANCELED')\n" +
                    "AND    AACL.ARCHIVE_STATUS IS NULL\n" +
                    "AND    EFVS.CODE = ?\n" +
                    "AND    AACH.TRANS_NO LIKE dbo.NVL(LTRIM(?), AACH.TRANS_NO)\n" +
                    "AND    AACH.ORDER_STATUS = dbo.NVL(LTRIM(?), AACH.ORDER_STATUS)\n" ;
                    if (!dto.getStartDate().toString().equals("")) {
                    	sqlStr += "AND    AACH.CREATION_DATE >= dbo.NVL(LTRIM(?), AACH.CREATION_DATE)\n" ;	
                    	//sqlStr += "AND    AACH.DISTRIBUTE_DATE >= dbo.NVL(LTRIM(?), AACH.DISTRIBUTE_DATE)\n" ;
                    }
                    if (!dto.getSQLEndDate().toString().equals("")) {
                    	sqlStr += "AND    AACH.CREATION_DATE <= dbo.NVL(LTRIM(?), AACH.CREATION_DATE)\n" ;
                    	//sqlStr += "AND    AACH.DISTRIBUTE_DATE <= dbo.NVL(LTRIM(?), AACH.DISTRIBUTE_DATE)\n" ;
                    }
                    sqlStr +=
                    "AND    (LTRIM(?) IS NULL OR SU2.USERNAME LIKE ?)\n" +
                    "AND    EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(LTRIM(?), EO.WORKORDER_OBJECT_CODE)\n" +
                    "AND    SU1.USERNAME LIKE dbo.NVL(LTRIM(?), SU1.USERNAME)\n" +
                    "AND    SG.GROUP_ID = ISNULL((CASE ? WHEN 0 THEN NULL WHEN -1 THEN NULL ELSE ? END), SG.GROUP_ID)\n" +
                    "AND    (? IS NULL OR ? = -1 OR ? = 0 OR AACH.ORGANIZATION_ID = ?)\n" +
                    "AND    (LTRIM(?) IS NULL OR AACL.BARCODE LIKE ?)";
            sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getOrderStatus());
            
            if (!dto.getStartDate().toString().equals("")) {
            	sqlArgs.add(dto.getStartDate());
            }
            if (!dto.getSQLEndDate().toString().equals("")) {
            	sqlArgs.add(dto.getSQLEndDate());
            }

            sqlArgs.add(dto.getArchivedUser());
            sqlArgs.add(dto.getArchivedUser());
            sqlArgs.add(dto.getObjectCode());
            sqlArgs.add(dto.getImplementUser());
            sqlArgs.add(dto.getGroupId());
            sqlArgs.add(dto.getGroupId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getQryBarcode());
            sqlArgs.add(dto.getQryBarcode());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
    }
}
