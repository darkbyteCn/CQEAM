package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-21
 * Time: 16:18:24
 * Functon; 申请于接收差异报表。.
 */
public class SpareDiffModel  extends AMSSQLProducer {
    private SQLModel sqlModel = null;
    private AmsItemTransLDTO dtoParameter = null;
    private SfUserDTO sfUser = null;

    public SpareDiffModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsItemTransLDTO) dtoParameter;
    }

    /**
     * 得到查询所有的MODEL
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        String fromDate = "";
        String orgId = "";
        boolean hasSqlProduced = false;
        SQLModel sqlModel = new SQLModel();
        final String CONDITION_SQL = "{CONDITION_SQL}";
        AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
        List strArg = new ArrayList();
            String sqlStr = "SELECT ASDN.ORGANIZATION_ID,\n" +
                    "       dbo.APP_GET_ORGNIZATION_NAME(ASDN.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                    "       AMSC.BARCODE,\n" +
                    "       AMSC.ITEM_NAME,\n" +
                    "       AMSC.ITEM_SPEC,\n" +
                    "       AMSC.ITEM_CATEGORY,\n" +
                    "       AMSC.SPARE_USAGE,\n" +
                    "       ASV.VENDOR_ID,\n" +
                    "       ASV.VENDOR_NAME,\n" +
                    "       ASDN.APPLY_NUMBER,\n" +
                    "       ASDN.REC_NUMBER,\n" +
                    "       ABS(ASDN.APPLY_NUMBER - ASDN.REC_NUMBER) DIF_NUM\n" +
                    "  FROM AMS_SPARE_DIFF_NUM ASDN,\n" +
                    "       AMS_SPARE_CATEGORY AMSC,\n" +
                    "       AMS_SPARE_VENDORS  ASV\n" +
                    " WHERE ASDN.BARCODE = AMSC.BARCODE\n" +
                    "       AND AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "       AND (? =-1 OR ASDN.ORGANIZATION_ID = ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull() +" OR ASV.VENDOR_ID = ?)";

            strArg.add(Situsdto.getOrganizationId());
            strArg.add(Situsdto.getOrganizationId());
            strArg.add(Situsdto.getItemName());
            strArg.add(Situsdto.getItemName());
            strArg.add(Situsdto.getItemSpec());
            strArg.add(Situsdto.getItemSpec());
            strArg.add(Situsdto.getItemCategory());
            strArg.add(Situsdto.getItemCategory());
            strArg.add(Situsdto.getSpareUsage());
            strArg.add(Situsdto.getSpareUsage());
            strArg.add(Situsdto.getVendorId());
            strArg.add(Situsdto.getVendorId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
        return sqlModel;
    }

//   另外的一个sql (此SQL不包含有出错的工单但是总工单为0 的情况)
//      SELECT A.CNT A, B.CNT B, A.CNT / B.CNT CNT
//     FROM (SELECT SUV.USERID, SUV.USERNAME, COUNT(1) CNT
//             FROM ETS_WORKORDER EW, SF_USER_V SUV
//
//            WHERE EW.ORGANIZATION_ID = 85
//              AND EW.RESPONSIBILITY_USER = SUV.USERID
//              AND EW.RESPONSIBILITY_USER IS NOT NULL
//           --    AND    EW.ATTRIBUTE6 = 1     --1 flase(出错工单); 0 true (正确的工单)
//            GROUP BY SUV.USERID, SUV.USERNAME) A, --出错工单
//          (SELECT COUNT(*) CNT, EW.IMPLEMENT_BY
//             FROM ETS_WORKORDER EW
//            WHERE EW.IMPLEMENT_BY IS NOT NULL
//            GROUP BY EW.IMPLEMENT_BY) B --工单总数
//    WHERE A.USERID = B.IMPLEMENT_BY
}
