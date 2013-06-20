package com.sino.ams.spare.hjck.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-2-25
 * Time: 15:06:12
 * To change this template use File | Settings | File Templates.
 */
public class AmsBjhjckModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjhjckModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
     public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       TO_CHAR(AITH.CREATION_DATE,'YYYY-MM-DD') CREATION_DATE,\n" +
                "       TO_CHAR(AITH.TRANS_DATE,'YYYY-MM-DD') TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n"
                + "AND (? IS NULL OR TRANS_NO = ?)"
                + "AND (? IS NULL OR TRANS_STATUS = ?)"
                + "AND (? IS NULL OR TO_OBJECT_NO = ?)"
                + "AND (? IS NULL OR FROM_ORGANIZATION_ID = ?)"
                + "AND AITH.TRANS_TYPE = NVL(?,AITH.TRANS_TYPE)"
                + "AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)"
                + "AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
