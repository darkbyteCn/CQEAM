package com.sino.flow.example.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-20
 * Time: 17:38:12
 */
public class NewModel {
    public static SQLModel saveModel(HttpServletRequest req, String appId) {
        String sql = "INSERT INTO SF_FLOW_TEST\n" +
                "  (APP_ID, APP_NUM, APP_TEXT1, APP_TEXT2)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?)";
        ArrayList al = new ArrayList();
        al.add(appId);
        al.add(StrUtil.nullToString(req.getParameter("appNum")));
        al.add(StrUtil.nullToString(req.getParameter("appText1")));
        al.add(StrUtil.nullToString(req.getParameter("appText2")));
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
    public static SQLModel findModel(String appId){
        String sql="SELECT SFT.APP_ID, SFT.APP_NUM, SFT.APP_TEXT1, SFT.APP_TEXT2\n" +
                "  FROM SF_FLOW_TEST SFT\n" +
                " WHERE SFT.APP_ID = ?";
        ArrayList al=new ArrayList();
        al.add(appId);
        SQLModel sm=new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
}
