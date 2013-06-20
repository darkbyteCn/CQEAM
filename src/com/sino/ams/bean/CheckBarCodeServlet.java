package com.sino.ams.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONUtil;

import com.sino.ams.spare.assistant.CheckBarcode;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-16
 */
public class CheckBarCodeServlet extends BaseServlet {
    Connection conn = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = null;
        String retValue = "";

        JSONArray barcodeArr = null;
        JSONArray quantityArr = null;
        try {
            String jsonString = JSONUtil.readJSONString(req);
            String[] jsArr = StrUtil.splitStr(jsonString,"$$$");

            String objectNo = StrUtil.nullToString(req.getParameter("objectNo"));//注意:用request取值只能在读取流之后进行
            barcodeArr = new JSONArray(jsArr[0]);
            quantityArr = new JSONArray(jsArr[1]);
            conn = DBManager.getDBConnection();
            retValue = checkBarcode(barcodeArr, quantityArr,objectNo);
            retValue = retValue.equals("") ? "OK" : retValue;
        } catch (PoolException e) {
            e.printLog();
            retValue = "ERROR";
        } catch (QueryException e) {
            e.printLog();
            retValue = "ERROR";
        } catch (JSONException e) {
            Logger.logError(e);
            retValue = "ERROR";
        } finally {
            DBManager.closeDBConnection(conn);
            res.setContentType("text/xml;charset=GBK");
            pw = res.getWriter();
            pw.print(retValue);
            pw.flush();
            pw.close();
        }
    }

    private String checkBarcode(JSONArray barcodeArr,JSONArray quantityArr, String objectNo) throws QueryException, JSONException {
        String barcodes = "";
        for (int i = 0; i < barcodeArr.length(); i++) {
            if (!CheckBarcode.isQuantityEnough(barcodeArr.getString(i),quantityArr.getString(i), objectNo, conn)) {
                barcodes += barcodeArr.getString(i) + ";";
            }
        }
        return barcodes;
    }
}
