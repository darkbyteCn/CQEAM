package com.sino.rds.execute.helper.commonlist;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.StrException;
import com.sino.base.web.CheckRadioProp;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.execute.helper.DefaultPageHtmlProducer;
import com.sino.rds.execute.helper.PageHtmlProducer;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.LookUpDefineFrm;
import com.sino.rds.share.form.ReportViewFrm;

import java.sql.Connection;
import java.util.List;

public class CommonHtmlProducer extends DefaultPageHtmlProducer implements PageHtmlProducer {
    private String tdAverageWidth = "";

    public CommonHtmlProducer(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    private void initReportTableWidth() {
        List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
        int columnCount = definedViews.size();
        if(columnCount < 10){
            columnCount = 10;
        }
        reportWidth = String.valueOf(columnCount * 10) + "%";
        tdAverageWidth = String.valueOf(100 / columnCount) + "%";
    }

    protected String getHeadHTML() {
        initReportTableWidth();
        StringBuilder headHTML = new StringBuilder();
        headHTML.append(getStartHeaderDIV());
        headHTML.append(getStartHeadTable());
        headHTML.append(getStartHeadTr());
        LookUpDefineFrm lookUpFrm = executeFrm.getLookUpFrm();
        if (lookUpFrm != null) {
            headHTML.append("<td width=\"2%\"></td>");
        }
        headHTML.append(produceHeadColumns());

        headHTML.append(getEndTr());
        headHTML.append(getEndTable());
        headHTML.append(getEndDIV());
        return headHTML.toString();
    }

    private String produceHeadColumns() {
        StringBuilder columnHTML = new StringBuilder();
        List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
        for (ReportViewFrm viewFrm : definedViews) {
            viewFrm.setFieldWidth(tdAverageWidth);
            columnHTML.append(getStartTd(viewFrm, false));
            columnHTML.append(viewFrm.getFieldDesc());
            columnHTML.append(getEndTd());
        }
        return columnHTML.toString();
    }

    protected String getBodyHTML() throws ContainerException {
        StringBuilder bodyHTML = new StringBuilder();

        bodyHTML.append(getStartDataDIV());
        bodyHTML.append(getStartDataTable());

        bodyHTML.append(produceDataRows());

        bodyHTML.append(getEndTable());
        bodyHTML.append(getEndDIV());


        return bodyHTML.toString();
    }


    private String produceDataRows() throws ContainerException {
        StringBuilder bodyHTML = new StringBuilder();
        RowSet searchResult = executeFrm.getSearchResult();
        LookUpDefineFrm lookUpFrm = executeFrm.getLookUpFrm();
        for (int i = 0; i < searchResult.getSize(); i++) {
            Row row = searchResult.getRow(i);
            bodyHTML.append(getStartDataTr());
            if (lookUpFrm != null) {
                bodyHTML.append("<td width=\"2%\" align=\"center\">");
                bodyHTML.append(getCheckRadio(row));
                bodyHTML.append("</td>");
            }
            bodyHTML.append(produceDataColumns(row));
            bodyHTML.append(getEndTr());
        }
        return bodyHTML.toString();
    }

    private CheckRadioProp getCheckRadio(Row row) throws ContainerException {
        CheckRadioProp radio = null;
        try {
            radio = new CheckRadioProp("subRadio");
            LookUpDefineFrm lookUpFrm = executeFrm.getLookUpFrm();
            String returnField = lookUpFrm.getReturnFieldName();
            String radioValue = row.getStrValue(returnField);
            radio.setValue(radioValue);
        } catch (StrException ex) {
            ex.printLog();
            throw new ContainerException(ex);
        }
        return radio;
    }

    private String produceDataColumns(Row data) throws ContainerException {
        StringBuilder columnHTML = new StringBuilder();
        List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
        for (ReportViewFrm viewFrm : definedViews) {
            viewFrm.setFieldWidth(tdAverageWidth);
            String fieldName = viewFrm.getFieldName();
            String fieldValue = data.getStrValue(fieldName);
            columnHTML.append(getStartTd(viewFrm, true));

            String className = "tableInput1";
            String fieldAlign = viewFrm.getFieldAlign();
            if (fieldAlign.equals(RDSDictionaryList.H_ALIGN_CENTER)) {
                className = "tableInput2";
            } if (fieldAlign.equals(RDSDictionaryList.H_ALIGN_RIGHT)) {
                className = "tableInput3";
            }
            columnHTML.append("<input value=\"");
            columnHTML.append(fieldValue);
            columnHTML.append("\" readonly class=\"");
            columnHTML.append(className);
            columnHTML.append("\">");
            columnHTML.append(getEndTd());
        }
        return columnHTML.toString();
    }

    private String getStartHeaderDIV() {
        StringBuilder dataHTML = new StringBuilder();
        dataHTML.append("<div id=\"headDiv\" style=\"position:absolute;left:1px;top:");
        dataHTML.append(headDIVTop);
        dataHTML.append("px;width:100%;overflow-y:scroll;overflow-x:hidden;\">");
        return dataHTML.toString();
    }

    private String getEndDIV() {
        StringBuilder dataHTML = new StringBuilder();
        dataHTML.append("</div>");
        return dataHTML.toString();
    }

    private String getStartDataDIV() {
        StringBuilder dataHTML = new StringBuilder();
        dataHTML.append("<div id=\"dataDIV\" style=\"overflow-y:scroll;overflow-x:auto;height:450px;width:100%;position:absolute;top:");
        dataHTML.append(dataDIVTop);
        dataHTML.append("px;left:1px;\" onscroll=\"document.getElementById('headDiv').scrollLeft=this.scrollLeft\">");
        return dataHTML.toString();
    }

    private StringBuilder getStartHeadTable() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<table id=\"headTable\" border=\"1\" bordercolor=\"#666666\" style=\"width:");
        tableHTML.append(reportWidth);
        tableHTML.append("\">");
        return tableHTML;
    }

    private StringBuilder getEndTable() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("</table>");
        return tableHTML;
    }

    private StringBuilder getStartHeadTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<tr class=\"headerTR\">");
        return tableHTML;
    }

    private StringBuilder getStartDataTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<tr class=\"dataTR\"");
        LookUpDefineFrm lookUpFrm = executeFrm.getLookUpFrm();
        if (lookUpFrm != null) {
            tableHTML.append(" onclick=\"executeClick(this)\"");
        }
        tableHTML.append(">");
        return tableHTML;
    }

    private StringBuilder getEndTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("</tr>");
        return tableHTML;
    }

    private StringBuilder getStartTd(ReportViewFrm viewFrm, boolean isData) {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        if (isData) {//构造数据表格
            tableHTML.append("<td width=\"");
            tableHTML.append(viewFrm.getFieldWidth());
            tableHTML.append("%\" align=\"");
            tableHTML.append(viewFrm.getFieldAlign());
            tableHTML.append("\"");
            tableHTML.append(">");
        } else {//构造头部表格
            tableHTML.append("<td width=\"");
            tableHTML.append(viewFrm.getFieldWidth());
            tableHTML.append("%\" align=\"center\"");
            tableHTML.append(">");
            tableHTML.append("<span class=\"resizeDivClass\"></span>");
        }
        return tableHTML;
    }

    private StringBuilder getEndTd() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("</td>");
        return tableHTML;
    }


    private StringBuilder getStartDataTable() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append("<table id=\"dataTable\" border=\"1\" bordercolor=\"#666666\" style=\"TABLE-LAYOUT:fixed;word-break:break-all;width:");
        tableHTML.append(reportWidth);
        tableHTML.append("\">");
        return tableHTML;
    }
}