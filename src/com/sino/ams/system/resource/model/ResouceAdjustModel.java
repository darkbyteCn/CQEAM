package com.sino.ams.system.resource.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfResDefineModel</p>
 * <p>Description:程序自动生成SQL构造器“SfResDefineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class ResouceAdjustModel extends AMSSQLProducer {

    /**
     * 功能：SF_RES_DEFINE 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfResDefineDTO 本次操作的数据
     */
    public ResouceAdjustModel(SfUserDTO userAccount, SfResDefineDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dtoParameter.setCreatedBy(userAccount.getUserId());
    }

    public SQLModel getChildrenModel(String resParId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SRD.RES_ID, SRD.RES_NAME\n" +
                "  FROM SF_RES_DEFINE SRD\n";
        if (StrUtil.isEmpty(resParId)) {
            sqlStr += " WHERE SRD.RES_PAR_ID " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                    "  ORDER BY SRD.SORT_NO";
        } else {
            sqlStr += " WHERE SRD.RES_PAR_ID = ?\n" +
                    "  ORDER BY SRD.SORT_NO";
            sqlArgs.add(resParId);
        }


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getUpdateResOrderModel(String resId, int sortNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_RES_DEFINE " +
                "   SET SORT_NO = ? " +
                "   WHERE RES_ID = ?";

        sqlArgs.add( sortNo );
//        sqlArgs.add(String.valueOf(sortNo));
        sqlArgs.add(resId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getResourceOptionModel(String resourceId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        String sqlStr = "SELECT SRD.RES_ID, SRD.RES_NAME, SRD.SORT_NO\n" +
//                "  FROM SF_RES_DEFINE SRD\n" +
//                " START WITH SRD.RES_PAR_ID " + SyBaseSQLUtil.isNull() + " \n" +
//                "CONNECT BY PRIOR SRD.RES_ID = SRD.RES_PAR_ID\n" +
//                " ORDER SIBLINGS BY SRD.SORT_NO";
        
        String sqlStr = "SELECT SRD.RES_ID, SRD.RES_NAME, SRD.SORT_NO\n" +
        "  FROM SF_RES_DEFINE SRD\n" +
        "  WHERE (SRD.RES_ID <> ? AND SRD.RES_ID NOT LIKE ?)" +
        "  OR ? = '' ORDER BY SRD.RES_ID,SRD.RES_PAR_ID";

        sqlArgs.add(resourceId);
        sqlArgs.add(resourceId + ".%");
        sqlArgs.add(resourceId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}