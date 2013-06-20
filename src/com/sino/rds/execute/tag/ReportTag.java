package com.sino.rds.execute.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ReportTag extends TagSupport {
    private String reportId = "";
    private String reportCode = "";

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }

    private void init(){

    }

    public int doEndTag() throws JspException {
        try {
            init();
            JspWriter out = pageContext.getOut();
            StringBuilder reportHTML = null;
            out.println(reportHTML);
        } catch (IOException ex) {
            throw new JspException(ex);
        }
        return EVAL_PAGE;
    }


}
