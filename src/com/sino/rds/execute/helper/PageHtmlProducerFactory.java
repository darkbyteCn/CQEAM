package com.sino.rds.execute.helper;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.execute.helper.commonlist.CommonHtmlProducer;
import com.sino.rds.execute.helper.intersect.IntersectDataHTMLProducer;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportExecuteFrm;
import com.sino.rds.share.form.SearchParameterFrm;

import java.sql.Connection;

public abstract class PageHtmlProducerFactory {

    public static PageHtmlProducer getPageHtmlProducer(BaseUserDTO userAccount,
                                                       Connection conn,
                                                       ReportExecuteFrm executeFrm) {
        PageHtmlProducer htmlProducer = null;
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        SearchParameterFrm searchFrm = executeFrm.getSearchFrm();
        if (definedReport.isIntersectReport()) {
            htmlProducer = new IntersectDataHTMLProducer(userAccount, searchFrm, conn);
        } else {
            htmlProducer = new CommonHtmlProducer(userAccount, searchFrm, conn);
        }
        htmlProducer.setExecuteFrm(executeFrm);
        return htmlProducer;
    }
}