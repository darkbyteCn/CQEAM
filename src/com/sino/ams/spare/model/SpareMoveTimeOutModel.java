package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.CalendarException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: wangzp
 * Date: 2011-12-01
 * Function;调拨批准后调出入超时统计.
 */ 
public class SpareMoveTimeOutModel extends AMSSQLProducer {
    private SQLModel sqlModel = null;
    private AmsItemTransHDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public SpareMoveTimeOutModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsItemTransHDTO) dtoParameter;
    }


    /**
     * 得到查询所有的MODEL
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsItemTransHDTO Situsdto = (AmsItemTransHDTO) dtoParameter;
        List strArg = new ArrayList();
        try {
            String sqlStr = "SELECT AIAH.CREATED_BY,\n" +
                    "       AIAH.TRANS_NO,\n" + 
                    "       AIAH.CREATION_DATE,\n" +
                    "       dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                    "       AIAH.FROM_DEPT,\n" +
                    "       AIAH.FROM_OBJECT_NO,\n" +
                    "       AIAH.FROM_ORGANIZATION_ID,\n" +
                    "       AIAH.FROM_USER,\n" +
                    "       AIAH.LAST_UPDATE_BY,\n" +
                    "       AIAH.LAST_UPDATE_DATE,\n" +
                    "       AIAH.RCV_USER,\n" +
                    "       AIAH.SOURCE_ID,\n" +
                    "       AIAH.TO_DEPT,\n" +
                    "       AIAH.TO_OBJECT_NO,\n" +
                    "       AIAH.TO_ORGANIZATION_ID,\n" +
                    "       AIAH.TO_USER,\n" +
                    "       AIAH.TRANS_DATE,\n" +
                    "       AIAH.TRANS_ID,\n" +
                    "       AIAH.TRANS_STATUS,\n" +
                    "       AIAH.TRANS_TYPE,\n" +
                    "       dbo.APP_GET_TRANS_TYPE(AIAH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                    "       DATEDIFF(DAY, AIAH.TRANS_DATE, GETDATE())  TIME_OUT\n" +
                    "  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                    "       SF_USER             SU\n" +
                    " WHERE AIAH.CREATED_BY = SU.USER_ID\n" +
                    "       AND AIAH.TRANS_TYPE = 'BJDB'\n" +
                    "       AND AIAH.TRANS_NO LIKE dbo.NVL(?,AIAH.TRANS_NO)\n" +
                    "       AND AIAH.TRANS_STATUS = dbo.NVL(?,AIAH.TRANS_STATUS)\n" +
                    "       AND (? =-1 OR AIAH.FROM_ORGANIZATION_ID = ?)\n" +
                    "       AND (? =-1 OR AIAH.TO_ORGANIZATION_ID = ?)\n" +
                    "       AND ("+ SyBaseSQLUtil.isNull() +" OR DATEDIFF(DAY, AIAH.TRANS_DATE, GETDATE()) > CONVERT(INT,?))\n" +
                    "       AND ("+ SyBaseSQLUtil.isNull() +" OR SU.USERNAME LIKE ?)\n" +     
                    "       AND AIAH.CREATION_DATE >= dbo.NVL(?,AIAH.CREATION_DATE)\n" +
                    "       AND AIAH.CREATION_DATE <= dbo.NVL(?,AIAH.CREATION_DATE)\n" +
                    "       AND AIAH.RCV_USER IS NULL\n" +
                    " ORDER BY CREATION_DATE DESC";
            strArg.add(Situsdto.getTransNo());
            strArg.add(Situsdto.getTransStatus());
            strArg.add(Situsdto.getOrganizationId());
            strArg.add(Situsdto.getOrganizationId());
            strArg.add(Situsdto.getToOrganizationId());
            strArg.add(Situsdto.getToOrganizationId());
            strArg.add(Situsdto.getAttribute1());
            strArg.add(Situsdto.getAttribute1());
            strArg.add(Situsdto.getCreatedUser());
            strArg.add(Situsdto.getCreatedUser());
            strArg.add(Situsdto.getFromDate());
            strArg.add(Situsdto.getSQLEndDate());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
        } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}

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
