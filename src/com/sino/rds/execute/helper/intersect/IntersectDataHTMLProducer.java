package com.sino.rds.execute.helper.intersect;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.execute.helper.DefaultPageHtmlProducer;
import com.sino.rds.execute.helper.PageHtmlProducer;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.*;

import java.sql.Connection;
import java.util.List;

public class IntersectDataHTMLProducer extends DefaultPageHtmlProducer implements PageHtmlProducer {
    private boolean hasData = false;
    private Object[][] reportValue = null;

    private List<ReportCategoryFrm> leftCategories = null;
    private List<ReportViewFrm> measureValueFields = null;

    private int measureFieldCount = 0;
    private int dataStartRow = 0;
    private int dataStartColumn = 0;

    private int headDIVHeight = 23;
    private static final int lineHeight = 30;

    public IntersectDataHTMLProducer(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void setHeadDIVTop(int headDIVTop) {
        if (headDIVTop > 0) {
            this.headDIVTop = headDIVTop;
        }
    }

    public void setExecuteFrm(ReportExecuteFrm executeFrm) {
        this.executeFrm = executeFrm;
        IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
        initIntersectDataFrm(intersectFrm);
        initReportTableWidth(intersectFrm);
        initReportTdWidth();
    }

    private void initIntersectDataFrm(IntersectReportExecuteFrm executeFrm) {
        if (executeFrm != null) {
            this.reportValue = executeFrm.getReportValue();
            this.dataStartRow = executeFrm.getDataStartRow();
            this.leftCategories = executeFrm.getLeftCategories();
            this.dataStartColumn = executeFrm.getDataStartColumn();
            this.measureFieldCount = executeFrm.getDataFrmCount();
            this.measureValueFields = executeFrm.getMeasureValueFields();
            this.headDIVHeight = lineHeight * dataStartRow;
            this.dataDIVTop = headDIVTop + headDIVHeight;
            if (reportValue != null) {
                hasData = true;
            }
        }
    }

    private void initReportTableWidth(IntersectReportExecuteFrm executeFrm) {
        if (!executeFrm.hasData()) {
            return;
        }
        int columnCount = 0;
        if (leftCategories != null && !leftCategories.isEmpty()) {
            for (ReportCategoryFrm category : leftCategories) {
                if (!category.getDisplayFlag().equals("N")) {
                    columnCount++;
                }
            }
        }
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                || sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)
                ) {
            columnCount++;
        }
        int dimensionCount = 1;
        RowSet aboveDimensions = executeFrm.getAboveDimensions();
        if (aboveDimensions != null && !aboveDimensions.isEmpty()) {
            dimensionCount = aboveDimensions.getSize();
        }
        dimensionCount *= measureValueFields.size();
        columnCount += dimensionCount;
        String strCliengtWidth = executeFrm.getSearchFrm().getClientWidth();
        if (strCliengtWidth.equals("")) {
            strCliengtWidth = "1024";
        }
        int clientWidth = Integer.parseInt(strCliengtWidth);
        reportWidth = String.valueOf(clientWidth / 8 * columnCount);
    }

    private void initReportTdWidth() {
        if (!hasData) {
            return;
        }
        int leftCategoryCount = 0;
        int hideCategoryCount = 0;
        if (leftCategories != null && !leftCategories.isEmpty()) {
            leftCategoryCount = leftCategories.size();
            for (ReportCategoryFrm leftCategory : leftCategories) {
                String displayFlag = leftCategory.getDisplayFlag();
                if (displayFlag.equals(RDSDictionaryList.DISPLAY_FLAG_N)) {
                    hideCategoryCount++;
                }
            }
        }
        int dataTdCount = reportValue[0].length - leftCategoryCount;
        int reportNumberWidth = Integer.parseInt(reportWidth);
        int averageTDWidth = reportNumberWidth / (reportValue[0].length - hideCategoryCount);
        if (measureValueFields != null && !measureValueFields.isEmpty()) {
            for (ReportViewFrm dataFrm : measureValueFields) {
                dataFrm.setFieldWidth(String.valueOf(averageTDWidth));
            }
        }
        int leftWidth = reportNumberWidth - dataTdCount * averageTDWidth;
        if (leftCategories != null && !leftCategories.isEmpty()) {
            for (int i = leftCategoryCount - 1; i >= 0; i--) {
                ReportCategoryFrm leftCategory = leftCategories.get(i);
                String displayFlag = leftCategory.getDisplayFlag();
                if (displayFlag.equals(RDSDictionaryList.DISPLAY_FLAG_N)) {
                    continue;
                }
                leftWidth -= averageTDWidth;
                leftCategory.setFieldWidth(String.valueOf(averageTDWidth));
            }
            if (leftWidth > 0) {
                for (int i = 0; i < leftCategoryCount; i++) {
                    ReportCategoryFrm leftCategory = leftCategories.get(i);
                    String displayFlag = leftCategory.getDisplayFlag();
                    if (displayFlag.equals(RDSDictionaryList.DISPLAY_FLAG_N)) {
                        continue;
                    }
                    String fieldWidth = leftCategory.getFieldWidth();
                    leftWidth += Integer.parseInt(fieldWidth);
                    leftCategory.setFieldWidth(String.valueOf(leftWidth));
                    break;
                }
            }
        }
    }

    protected String getHeadHTML() throws ContainerException {
        StringBuilder headHTML = new StringBuilder();
        headHTML.append(getStartHeaderDIV());
        headHTML.append(getStartHeadTable());
        headHTML.append(produceHeaderRows());
        headHTML.append(getEndTable());
        headHTML.append(getEndDIV());
        return headHTML.toString();
    }

    private String produceHeaderRows() throws ContainerException {
        StringBuilder headerHTML = new StringBuilder();
        if (reportValue != null) {
            int columnCount = reportValue[0].length;
            IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
            List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
            for (int i = 0; i < dataStartRow; i++) {
                boolean isDisplay = true;
                if (i < dataStartRow - 1) {
                    ReportCategoryFrm leftCategory = aboveCategories.get(i);
                    isDisplay = leftCategory.getDisplayFlag().equals(RDSDictionaryList.DISPLAY_FLAG_Y);
                } else if (i == dataStartRow - 1) {
                    isDisplay = true;
                }
                headerHTML.append(getStartHeadTr(isDisplay));
                Object[] reportRow = reportValue[i];
                for (int j = 0; j < columnCount; j++) {
                    isDisplay = true;
                    if (j < dataStartColumn) {
                        ReportCategoryFrm leftCategory = leftCategories.get(j);
                        String displayFlag = leftCategory.getDisplayFlag();
                        isDisplay = displayFlag.equals(RDSDictionaryList.DISPLAY_FLAG_Y);
                    }
                    headerHTML.append(getStartTd(i, j, isDisplay));
                    Object cellValue = reportRow[j];
                    cellValue = StrUtil.nullToString(cellValue);
                    String className = "tableInput2";
                    headerHTML.append("<input type=\"text\" value=\"");
                    headerHTML.append(cellValue);
                    headerHTML.append("\" readonly class=\"");
                    headerHTML.append(className);
                    headerHTML.append("\">");

                    headerHTML.append(getEndTd());
                }
                headerHTML.append(getEndTr());
            }
        }
        return headerHTML.toString();
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
        if (reportValue != null) {
            int rowCount = reportValue.length;
            int columnCount = reportValue[0].length;
            for (int i = dataStartRow; i < rowCount; i++) {
                bodyHTML.append(getStartDataTr());
                Object[] reportRow = reportValue[i];
                for (int j = 0; j < columnCount; j++) {
                    boolean isDisplay = true;
                    if (j < dataStartColumn) {
                        ReportCategoryFrm leftCategory = leftCategories.get(j);
                        String displayFlag = leftCategory.getDisplayFlag();
                        isDisplay = displayFlag.equals(RDSDictionaryList.DISPLAY_FLAG_Y);
                    }
                    bodyHTML.append(getStartTd(i, j, isDisplay));
                    Object cellValue = reportRow[j];
                    cellValue = StrUtil.nullToString(cellValue);

                    String className = "tableInput1";
                    if (j < dataStartColumn) {
                        className = "tableInput1";
                    } else {
                        int viewFrmIndex = (j - dataStartColumn) % measureFieldCount;
                        ReportViewFrm viewFrm = measureValueFields.get(viewFrmIndex);
                        String align = viewFrm.getFieldAlign();
                        if (align.equals(RDSDictionaryList.H_ALIGN_LEFT)) {
                            className = "tableInput1";
                        } else if (align.equals(RDSDictionaryList.H_ALIGN_CENTER)) {
                            className = "tableInput2";
                        } else if (align.equals(RDSDictionaryList.H_ALIGN_RIGHT)) {
                            className = "tableInput3";
                        }
                    }
                    bodyHTML.append("<input type=\"text\" value=\"");
                    bodyHTML.append(cellValue);
                    bodyHTML.append("\" readonly class=\"");
                    bodyHTML.append(className);
                    bodyHTML.append("\">");

                    bodyHTML.append(getEndTd());
                }
                bodyHTML.append(getEndTr());
            }
        }
        return bodyHTML.toString();
    }

    private String getStartHeaderDIV() {
        StringBuilder dataHTML = new StringBuilder();
        dataHTML.append("<div id=\"headDiv\" style=\"position:absolute;left:1px;top:");
        dataHTML.append(headDIVTop);
        dataHTML.append("px;height:");
        dataHTML.append(headDIVHeight);
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
        tableHTML.append("<table id=\"headTable\" border=\"1\" bordercolor=\"#666666\" style=\"TABLE-LAYOUT:fixed;word-break:break-all\" style=\"width:");
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

    private StringBuilder getStartHeadTr(boolean isDisplay) {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        if (isDisplay) {
            tableHTML.append("<tr class=\"crossReportHeaderTR\">");
        } else {
            tableHTML.append("<tr style=\"height:0px\">");
        }
        return tableHTML;
    }

    private StringBuilder getStartDataTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<tr class=\"dataTR\">");
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

    private StringBuilder getStartTd(int rowIndex, int columnIndex, boolean isDisplay) {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        if (columnIndex < dataStartColumn) {
            ReportCategoryFrm leftCategory = leftCategories.get(columnIndex);
            tableHTML.append("<td width=\"");
            if (isDisplay) {
                tableHTML.append(leftCategory.getFieldWidth());
            } else {
                tableHTML.append("0");
            }
            tableHTML.append("\">");
        } else {
            int viewFrmIndex = (columnIndex - dataStartColumn) % measureFieldCount;
            ReportViewFrm viewFrm = measureValueFields.get(viewFrmIndex);
            if (rowIndex >= dataStartRow) {
                tableHTML.append("<td width=\"");
                if (isDisplay) {
                    tableHTML.append(viewFrm.getFieldWidth());
                } else {
                    tableHTML.append("0");
                }
                tableHTML.append("\" align=\"");
                tableHTML.append(viewFrm.getFieldAlign());
                tableHTML.append("\"");
                tableHTML.append(">");
            } else {
                tableHTML.append("<td width=\"");
                if (isDisplay) {
                    tableHTML.append(viewFrm.getFieldWidth());
                } else {
                    tableHTML.append("0");
                }
                tableHTML.append("\" align=\"center\">");
            }
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
        tableHTML.append("<table id=\"dataTable\" border=\"1\" bordercolor=\"#666666\" style=\"TABLE-LAYOUT:fixed;word-break:break-all\" style=\"width:");
        tableHTML.append(reportWidth);
        tableHTML.append("\" onclick=\"do_DrillDownReport()\">");
        return tableHTML;
    }
}