package com.sino.ams.system.item.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.treeview.MzTree;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-22
 */
public class ItemRelationLeft extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=GBK");
        PrintWriter out = res.getWriter();
        Connection conn = null;
        out.println("<script language=\"javascript\" src=\"/WebLibary/js/MzTreeView10.js\"></script>");
        out.println("  <style>\n" +
                "A.MzTreeview\n" +
                "{\n" +
                "  font-size: 9pt;\n" +
                "  padding-left: 3px;\n" +
                "}\n" +
                "</style>");
        out.println("<script language=\"javascript\">");
        try {
            conn = getDBConnection(req);
            String sqlStr = "SELECT aoa.address_id id1,\n" +
                    "       eo.workorder_object_name text1,\n" +
                    "       aoa.address_no id2,\n" +
                    "       aoa.address_no text2,\n" +
                    "       eii.barcode id3,\n" +
                    "       eii.barcode text3\n" +
                    "  FROM ets_object eo, ams_object_address aoa, ets_item_info eii\n" +
                    " WHERE eo.workorder_object_no = aoa.object_no\n" +
                    "   AND aoa.address_id = eii.address_id\n" +
                    "   AND eii.is_parent = 'Y'";
            MzTree tree = new MzTree(conn);
            tree.setRootText("地点");
            tree.setIconPath("/images/mzTree/");
            tree.setTarget("itemMain");
            tree.setSqlStr(sqlStr);
            tree.setLevels(3);
            tree.setUrl("/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet");
            tree.setTransParaName("barcode");
            tree.produceTree2();
            out.println(tree.getTreeStr());
            out.println("parent.document.getElementById(\"$$$waitTipMsg$$$\").style.visibility = \"hidden\"");
            out.println("</script>");
        } catch (PoolPassivateException e) {
            e.printLog();
            out.println("Get Connection Error.");
        } finally {
            DBManager.closeDBConnection(conn);
            out.flush();
            out.close();
        }
    }
}
