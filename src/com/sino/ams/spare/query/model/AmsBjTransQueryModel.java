package com.sino.ams.spare.query.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-12-17
 */
public class AmsBjTransQueryModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjTransQueryModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
     public SQLModel getLineData(String transId){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT AITD.DETAIL_ID LINE_ID,\n" +
                "       AITD.ITEM_CODE,\n" +
                "       AITD.QUANTITY,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC\n" +
                "  FROM AMS_ITEM_TRANS_D AITD, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITD.TRANS_ID = ?";
        List argList = new ArrayList();
        argList.add(transId);
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO headerDto = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.CREATED_BY,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.FROM_DEPT,\n" +
                "       AITH.FROM_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.FROM_USER,\n" +
                "       AITH.LAST_UPDATE_BY,\n" +
                "       AITH.LAST_UPDATE_DATE,\n" +
                "       AITH.RCV_USER,\n" +
                "       AITH.SOURCE_ID,\n" +
                "       AITH.TO_DEPT,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
                "       AITH.TO_USER,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AMS_PUB_PKG.GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AITH.TO_OBJECT_NO) TO_OBJECT_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AITH.From_Object_No) from_OBJECT_NAME,\n" +
                "       ams_pub_pkg.GET_ORGNIZATION_NAME(aith.to_organization_id) to_Organization_Name,\n" +
                "       ams_pub_pkg.GET_ORGNIZATION_NAME(aith.from_organization_id) from_Organization_Name\n" +
                "  FROM AMS_ITEM_TRANS_H AITH\n" +
                " WHERE AITH.TRANS_ID = ?\n" +
                "UNION\n" +
                "SELECT AIAH.CREATED_BY,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.CREATION_DATE,\n" +
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
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AMS_PUB_PKG.GET_TRANS_TYPE(AIAH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AIAH.TO_OBJECT_NO) TO_OBJECT_NAME,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(AIAH.From_Object_No) from_OBJECT_NAME,\n" +
                "       ams_pub_pkg.GET_ORGNIZATION_NAME(AIAH.to_organization_id) to_Organization_Name,\n" +
                "       ams_pub_pkg.GET_ORGNIZATION_NAME(AIAH.from_organization_id) from_Organization_Name\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH\n" +
                " WHERE AIAH.TRANS_ID = ?";
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(headerDto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT ESI.ITEM_NAME,\n" +
                "       AITL.BARCODE,\n" +
                "       AITL.QUANTITY,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CODE\n" +
                "  FROM AMS_ITEM_TRANS_L AITL, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = null;

            AmsBjsAllotHDTO headerDto = (AmsBjsAllotHDTO) dtoParameter;
            sqlStr = "SELECT AITH.CREATED_BY,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                    "       AITH.CREATION_DATE,\n" +
                    "       AITH.FROM_DEPT,\n" +
                    "       AITH.FROM_OBJECT_NO,\n" +
                    "       AITH.FROM_ORGANIZATION_ID,\n" +
                    "       AITH.FROM_USER,\n" +
                    "       AITH.LAST_UPDATE_BY,\n" +
                    "       AITH.LAST_UPDATE_DATE,\n" +
                    "       AITH.RCV_USER,\n" +
                    "       AITH.SOURCE_ID,\n" +
                    "       AITH.TO_DEPT,\n" +
                    "       AITH.TO_OBJECT_NO,\n" +
                    "       AITH.TO_ORGANIZATION_ID,\n" +
                    "       AITH.TO_USER,\n" +
                    "       AITH.TRANS_DATE,\n" +
                    "       AITH.TRANS_ID,\n" +
                    "       AITH.TRANS_NO,\n" +
                    "       AITH.TRANS_STATUS,\n" +
                    "       AITH.TRANS_TYPE,\n" +
                    "       AMS_PUB_PKG.GET_TRANS_TYPE(AITH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                    "  FROM AMS_ITEM_TRANS_H AITH,SF_USER SU\n" +
                    " WHERE AITH.TRANS_NO LIKE NVL(?, AITH.TRANS_NO)\n" +
                    "   AND AITH.TRANS_STATUS = NVL(?, AITH.TRANS_STATUS)\n" +
                    "   AND AITH.CREATED_BY = SU.USER_ID\n" +
                    "   AND AITH.TRANS_TYPE = NVL(?, AITH.TRANS_TYPE)\n" +
                    "   AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)\n" +
                    "   AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)\n" +
                    "   AND SU.ORGANIZATION_ID = ?\n" +
                    "UNION\n" +
                    "SELECT AIAH.CREATED_BY,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                    "       AIAH.CREATION_DATE,\n" +
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
                    "       AIAH.TRANS_NO,\n" +
                    "       AIAH.TRANS_STATUS,\n" +
                    "       AIAH.TRANS_TYPE,\n" +
                    "       AMS_PUB_PKG.GET_TRANS_TYPE(AIAH.TRANS_TYPE) TRANS_TYPE_NAME,\n" +
                    "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                    "  FROM AMS_ITEM_ALLOCATE_H AIAH,SF_USER SU\n" +
                    " WHERE AIAH.TRANS_NO LIKE NVL(?, AIAH.TRANS_NO)\n" +
                    "   AND AIAH.TRANS_STATUS = NVL(?, AIAH.TRANS_STATUS)\n" +
                    "   AND AIAH.CREATED_BY = SU.USER_ID\n" +
                    "   AND AIAH.TRANS_TYPE = NVL(?, AIAH.TRANS_TYPE)\n" +
                    "   AND AIAH.CREATION_DATE >= NVL(?, AIAH.CREATION_DATE)\n" +
                    "   AND AIAH.CREATION_DATE <= NVL(?, AIAH.CREATION_DATE)\n"+
                    "   AND SU.ORGANIZATION_ID = ?" +
                    "   ORDER BY CREATION_DATE DESC";
            sqlArgs.add(headerDto.getTransNo());
            sqlArgs.add(headerDto.getTransStatus());
            sqlArgs.add(headerDto.getTransType());
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getToDate());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(headerDto.getTransNo());
            sqlArgs.add(headerDto.getTransStatus());
            sqlArgs.add(headerDto.getTransType());
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getToDate());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }
}
