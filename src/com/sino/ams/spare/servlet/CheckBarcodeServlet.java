package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;

import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.spare.assistant.CheckBarcode;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 贾龙川
 * @version 0.1
 *          Date: 2008-3-17
 */
public class CheckBarcodeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        PrintWriter pw = null;
        JSONArray retArray = new JSONArray();
        String retStr = "";
        String barcodes = req.getParameter("barcodes");
        boolean notRepeated = true;
        try {
            if (barcodes != null) {
                conn = DBManager.getDBConnection();
                String[] barcodeArr = StrUtil.splitStr(barcodes, ",");
                for (int i = 0; i < barcodeArr.length; i++) {
                    String barcode = barcodeArr[i];
                    if (CheckBarcode.isBarcodeRepeated(barcode, conn)) {
                        retArray.put(i, 1);
                        notRepeated = false;
                    } else {
                        retArray.put(i, 0);
                    }
                }
                if (notRepeated) {
                    retStr = "OK";
                }
            }
        } catch (PoolException e) {
            retStr = "ERROR";
        } catch (QueryException e) {
            retStr = "ERROR";
        } catch (JSONException e) {
            retStr = "ERROR";
        } finally {
            DBManager.closeDBConnection(conn);
            res.setContentType("text/xml;charset=GBK");
            pw = res.getWriter();
            if (notRepeated) {
                pw.print(retStr);
            } else {
                pw.print(retArray.toString());
            }
            pw.flush();
            pw.close();
        }
    }
}
