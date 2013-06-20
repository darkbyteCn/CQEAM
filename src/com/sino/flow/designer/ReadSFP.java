package com.sino.flow.designer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;

public class ReadSFP extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
    private static final String DOC_TYPE = null;
    private String m_fileName = "c:\\temp\\asflow\\sinoflo.sfp";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {

            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            if (DOC_TYPE != null) {
                out.println(DOC_TYPE);
            }
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
            out.println("<projects>  ");

            SinoFloEngine en = new SinoFloEngine(m_fileName);
            int prjnum = en.getProjNum();

            out.println("<ProcInfo>  ");
            out.println("<attribute name=\"prjnum\">" + String.valueOf(prjnum) + "</attribute>");


            SinoFloProject prj = en.getNthProj(0);

            SinoFloProcedure proc;//=prj.getFirstProcedure();

            proc = prj.getProcByName("基站工单任务审批流程(新建)");


            SinoDocField[] f = proc.FIELDS;

            for (int i = 0; i < f.length; i++) {
                out.println("<attribute name=\"" + f[i].getFieldName() + "\">" + f[i].getText() + "</attribute>");
            }
            out.println("</ProcInfo>  ");
            ////////////////////////////////////////////
            //proc = prj.getProcByName("工单处理流程");

            /////////////////////////////
            int num = proc.getTaskCount();
            SinoFloTask task;

            for (int mm = 0; mm < num; mm++) {
                task = proc.getNthTask(mm);

                out.println("<task" + String.valueOf(mm) + "  name=\"" + task.getText("Name") + "\" >  ");

                f = task.FIELDS;
                out.println("<taskinfo>");
                for (int i = 0; i < f.length; i++) {
                    out.println("<attribute name=\"" + f[i].getFieldName() + "\">" + task.getText(f[i].getFieldName()) + "</attribute>");
                }
                out.println("</taskinfo >");


                Vector vv = proc.getAllOutFlow(task);
                SinoFloTask nts = null;
                SinoFloInfo sinfo = null;

                for (int j = 0; j < vv.size(); j++) {

                    sinfo = (SinoFloInfo) vv.elementAt(j);

                    out.println("<Distribute" + String.valueOf(j) + " name=\"" + sinfo.getText("Name") + "\">  ");

                    f = sinfo.FIELDS;

                    for (int ii = 0; ii < f.length; ii++) {
                        out.println("<attribute name=\"" + f[ii].getFieldName() + "\">" + sinfo.getText(f[ii].getFieldName()) + "</attribute>");
                    }
                    out.println("</Distribute" + String.valueOf(j) + ">");
                }


                out.println("</task" + String.valueOf(mm) + ">  ");
            }

////////////////////////////////////////
            conn = DBManager.getDBConnection();
            com.sino.flow.designer.Flowfile ff = new com.sino.flow.designer.Flowfile(conn);
            Logger.logInfo("file=" + m_fileName);
            ff.OpenFile(m_fileName, "基站工单任务审批流程(新建)");
            com.sino.flow.designer.MTaskFlowInfo sfinfo = ff.getFlowInfo("12", "14");
            out.println("<nn>");
            if (sfinfo != null) {
                out.println("<attribute name=\"Desc\">" + sfinfo.Name + "</attribute>");
                out.println("<attribute name=\"mFlowControl\">" + sfinfo.mFlowControl + "</attribute>");
                out.println("<attribute name=\"mFlowCode\">" + sfinfo.mFlowCode + "</attribute>");
            }
            out.println("</nn>");
            Logger.logInfo("close file");
            ff.CloseFlo();
/////////////////////////////
            out.println("</projects>  ");

            out.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.logError("EXCEPTION:" + exception.toString());
        }
        finally {
            DBManager.closeDBConnection(conn);
        }

    }


}