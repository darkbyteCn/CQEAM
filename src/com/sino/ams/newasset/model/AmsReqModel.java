package com.sino.ams.newasset.model;

import java.util.ArrayList;

import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-10-16
 * Time: 16:22:49
 * To change this template use File | Settings | File Templates.
 */
public class AmsReqModel {

    public SQLModel getItemCode(String iName, String iSpec) {
        ArrayList sqlArgs = new ArrayList();
        SQLModel sm = new SQLModel();
        String sql = "SELECT ESI.ITEM_CODE\n" +
                "  FROM ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_NAME = ?\n" +
                "   AND ESI.ITEM_SPEC = ?";
        sqlArgs.add(iName);
        sqlArgs.add(iSpec);
        sm.setSqlStr(sql);
        sm.setArgs(sqlArgs);
        return sm;
    }

    public SQLModel getAddreId(String addresName,int orgId) {
        ArrayList sqlArgs = new ArrayList();
        SQLModel sm = new SQLModel();
        String sql = "SELECT AOA.ADDRESS_ID\n" +
                "  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NAME = ?\n" +
                //新增管理资产对地点类别有要求吗？原条件为80其它地点，为测试，暂取消
                //"   AND EO.OBJECT_CATEGORY = '80'\n" +
                "   AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(addresName);
        sqlArgs.add(orgId);
        sm.setSqlStr(sql);
        sm.setArgs(sqlArgs);
        return sm;

    }
    public SQLModel getDept(String deptName,int orgId){
          ArrayList sqlArgs = new ArrayList();
        SQLModel sm = new SQLModel();
        String sql="SELECT AMD.DEPT_CODE\n" +
                "  FROM AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n" +
                "   AND AMD.DEPT_NAME = ?\n" +
                "   AND EOCM.ORGANIZATION_ID = ?";
        sqlArgs.add(deptName);
        sqlArgs.add(orgId);
        sm.setSqlStr(sql);
        sm.setArgs(sqlArgs);
        return sm;
    }
    public SQLModel getUserId(String useName,int orgId){
         ArrayList sqlArgs = new ArrayList();
        SQLModel sm = new SQLModel();
        String sql="SELECT AME.EMPLOYEE_ID\n" +
                "  FROM AMS_MIS_EMPLOYEE AME, AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AME.DEPT_CODE = AMD.DEPT_CODE\n" +
                "   AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n" +
                "   AND EOCM.ORGANIZATION_ID = ?\n" +
                "   AND AME.USER_NAME = ?";
        sqlArgs.add(orgId);
        sqlArgs.add(useName);
        sm.setSqlStr(sql);
        sm.setArgs(sqlArgs);
        return sm;
    }
}
