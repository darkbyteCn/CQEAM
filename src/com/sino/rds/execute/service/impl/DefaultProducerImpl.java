package com.sino.rds.execute.service.impl;

import com.sino.base.constant.db.DataTypeConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.rds.execute.service.IntersectProducer;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.IntersectReportExecuteFrm;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportViewFrm;

import java.util.ArrayList;
import java.util.List;

public class DefaultProducerImpl implements IntersectProducer {
    protected IntersectReportExecuteFrm executeFrm = null;
    protected RowSet searchResult = null;

    protected List<ReportCategoryFrm> aboveCategories = null;
    protected List<ReportCategoryFrm> leftCategories = null;

    protected RowSet aboveDimensions = null;
    protected RowSet leftDimensions = null;

    protected RowSet bottomExpressions = null;
    protected RowSet verticalExpressions = null;

    protected List<ReportViewFrm> definedViews = null;//报表维护定义的数据字段，含纬度字段和度量值字段
    protected List<ReportViewFrm> measureValueFields = null;// 度量值字段

    protected int measureDataCount = 0;

    protected String[][] reportValue = null;

    private boolean oneTimeDataProcessed = false;

    protected int leftCategoryCount = 0;
    protected int aboveCategoryCount = 0;

    protected int leftDimensionCount = 0;
    protected int aboveDimensionCount = 0;
    protected int totalRowCount = 0;
    protected int totalColumnCount = 0;

    public DefaultProducerImpl(IntersectReportExecuteFrm executeFrm) {
        initGlobalVariable();
        initReportExecuteFrm(executeFrm);
    }

    private void initGlobalVariable() {
        this.aboveCategories = new ArrayList<ReportCategoryFrm>();
        this.leftCategories = new ArrayList<ReportCategoryFrm>();
        this.measureValueFields = new ArrayList<ReportViewFrm>();
    }

    private void initReportExecuteFrm(IntersectReportExecuteFrm executeFrm) {
        this.executeFrm = executeFrm;
        this.definedViews = executeFrm.getDefinedViews();
        measureValueFields.addAll(definedViews);
        this.searchResult = executeFrm.getSearchResult();
        this.aboveDimensions = executeFrm.getAboveDimensions();
        this.leftDimensions = executeFrm.getLeftDimensions();
        this.bottomExpressions = executeFrm.getBottomExpressions();
        this.verticalExpressions = executeFrm.getVerticalExpressions();
    }

    public void processIntersectData() throws ReportException {
        try {
            processDataOneTime();
            if (searchResult != null && !searchResult.isEmpty()) {
                processReportData();
                produceLeftCategoryTitle();
                processIntersectValues();
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
    }

    private void processDataOneTime() throws ContainerException, ReportException {
        if (!oneTimeDataProcessed) {
            processCategoryFields();
            processAboveDimensions();
            processLeftDimensions();
            prepareReportValueArray();
            produceReportEmptyArea();
            produceExpressionArea();
            produceAboveColumnHeader();
            produceMeasureColumnHeader();
            produceLeftColumnHeader();
            processBottomExpressionValues();
            processVerticalExpressionValues();
            oneTimeDataProcessed = true;
        }
    }

    protected void processAboveDimensions() throws ReportException {
        if (aboveDimensions != null && !aboveDimensions.isEmpty()) {
            aboveDimensionCount = aboveDimensions.getSize();
        }
    }

    protected void processLeftDimensions() throws ReportException {
        if (leftDimensions != null && !leftDimensions.isEmpty()) {
            leftDimensionCount = leftDimensions.getSize();
        }
    }

    private void processCategoryFields() {
        List<ReportCategoryFrm> categories = executeFrm.getDefinedCategories();
        for (ReportCategoryFrm category : categories) {
            String viewLocation = category.getViewLocation();
            if (viewLocation.equals(RDSDictionaryList.VIEW_LOCATION_ABOVE)) {
                aboveCategories.add(category);
            } else if (viewLocation.equals(RDSDictionaryList.VIEW_LOCATION_LEFT)) {
                leftCategories.add(category);
            }
            processDataFrms(category);
        }
        if (leftCategories != null && !leftCategories.isEmpty()) {
            leftCategoryCount = leftCategories.size();
        }
        if (aboveCategories != null && !aboveCategories.isEmpty()) {
            aboveCategoryCount = aboveCategories.size();
        }
    }

    /**
     * 功能：构造报表的度量值字段。
     *
     * @param category 分组字段
     */
    private void processDataFrms(ReportCategoryFrm category) {
        String fieldName = category.getFieldName();
        for (ReportViewFrm viewFrm : definedViews) {
            if (viewFrm.getFieldName().equals(fieldName)) {
                measureValueFields.remove(viewFrm);
                break;
            }
        }
    }

    private void prepareReportValueArray() {
        if (!executeFrm.hasData()) {
            return;
        }
        List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
        measureDataCount = definedViews.size() - leftCategoryCount - aboveCategoryCount;
        totalRowCount = leftDimensionCount + aboveCategoryCount + 1;//暂时未加入合计，此处加1是度量值字段的描述信息所需
        if(leftDimensionCount == 0){
            totalRowCount++;//数据行仅一行            
        }
        if (aboveDimensionCount > 0) {
            totalColumnCount = measureDataCount * aboveDimensionCount + leftCategoryCount;//暂时未加入合计
        } else {
            totalColumnCount = measureDataCount + leftCategoryCount;//暂时未加入合计
        }

        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();

        if (sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)) {
            totalRowCount++;//加入合计,合计数据在底部显示
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)) {
            totalColumnCount += measureDataCount;//加入合计,合计数据在右边显示
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)) {
            totalColumnCount += measureDataCount;//加入合计,合计数据在右边显示
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
            totalRowCount++;//加入合计,合计数据在底部显示
            totalColumnCount += measureDataCount;//加入合计,合计数据在右边显示
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {
            totalRowCount++;//加入合计,合计数据在底部显示
            totalColumnCount += measureDataCount;//加入合计,合计数据在右边显示
        }
        reportValue = new String[totalRowCount][totalColumnCount];
    }

    private void produceAboveColumnHeader() throws ContainerException {
        if (!executeFrm.hasData()) {
            return;
        }
        int dataStartColumn = leftCategoryCount;

        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
            dataStartColumn += measureDataCount;//跳过合计列
        }
        for (int i = 0; i < aboveDimensionCount; i++) {
            Row row = aboveDimensions.getRow(i);
            for (int j = 0; j < aboveCategoryCount; j++) {
                String fieldName = aboveCategories.get(j).getFieldName();
                String fieldValue = row.getStrValue(fieldName);
                for (int k = 0; k < measureDataCount; k++) {
                    int columnIndex = i * measureDataCount + k + dataStartColumn;
                    reportValue[j][columnIndex] = fieldValue;
                }
            }
        }
    }


    /**
     * 功能：//填充保报表的空白区域
     */
    private void produceReportEmptyArea() {
        if (!executeFrm.hasData()) {
            return;
        }
        for (int i = 0; i <= aboveCategoryCount; i++) { //报表左上角
            for (int j = 0; j < leftCategoryCount; j++) {
                reportValue[i][j] = "";
            }
        }

        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {
            int rowIndex = reportValue.length - 1;
            int columnIndex = reportValue[rowIndex].length - 1;
            reportValue[rowIndex][columnIndex] = "";//报表最右下脚单元格
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
            int rowIndex = reportValue.length - 1;
            int columnIndex = leftCategoryCount;
            reportValue[rowIndex][columnIndex] = "";//报表最右下脚单元格
        }
    }

    /**
     * 功能：填充报表的表达式合计区域
     */
    private void produceExpressionArea() {
        if (!executeFrm.hasData()) {
            return;
        }
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)) {//合计值位于：底部区域
            for (int j = 0; j < leftCategoryCount; j++) {
                reportValue[totalRowCount - 1][j] = "合计";
            }
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)) { //合计值位于：左部区域
            for (int i = 0; i < aboveCategoryCount; i++) {
                for (int j = 0; j < measureDataCount; j++) {
                    reportValue[i][leftCategoryCount + j] = "合计";
                }
            }
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)) {//合计值位于：右部区域
            int columnNumber = totalColumnCount - leftCategoryCount;
            if(leftCategoryCount > 0){
                columnNumber -= measureDataCount;
            }
            for (int i = 0; i < aboveCategoryCount; i++) {
                for (int j = 0; j < measureDataCount; j++) {
                    reportValue[i][columnNumber + j] = "合计";
                }
            }
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {//合计值位于：底部和左部
            for (int j = 0; j < leftCategoryCount; j++) {
                reportValue[totalRowCount - 1][j] = "合计";
            }

            for (int i = 0; i < aboveCategoryCount; i++) {
                for (int j = 0; j < measureDataCount; j++) {
                    reportValue[i][leftCategoryCount + j] = "合计";
                }
            }
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) { //合计值位于：底部和右部
            for (int j = 0; j < leftCategoryCount; j++) {
                reportValue[totalRowCount - 1][j] = "合计";
            }
            int columnNumber = totalColumnCount - leftCategoryCount;
            if(leftCategoryCount > 0){
                columnNumber -= measureDataCount;
            }
            for (int i = 0; i < aboveCategoryCount; i++) {
                for (int j = 0; j < measureDataCount; j++) {
                    reportValue[i][columnNumber + j] = "合计";
                }
            }
        }
    }

    private void produceMeasureColumnHeader() {
        if (!executeFrm.hasData()) {
            return;
        }
        produceMeasureDataColumnHeader();
        produceMeasureExpressionColumnHeader();
    }


    private void produceMeasureDataColumnHeader() {
        int dataStartColumn = leftCategoryCount;
        int dataEndColumn = totalColumnCount;
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();

        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) { //合计值位于：左部区域
            dataStartColumn += measureDataCount;//跳过合计列
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {//合计值位于：右部区域
            dataEndColumn--;
        }
        for (int i = dataStartColumn; i < dataEndColumn; i++) {
            int index = (i - dataStartColumn) % measureDataCount;
            ReportViewFrm viewFrm = measureValueFields.get(index);
            reportValue[aboveCategoryCount][i] = viewFrm.getFieldDesc();
        }
    }

    private void produceMeasureExpressionColumnHeader() {
        int dataStartColumn = leftCategoryCount;
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        boolean needProcess = false;
        if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) { //合计值位于：左部区域、左部和下部
            needProcess = true;
        } else if (sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {//合计值位于：右部区域、右部和下部
            dataStartColumn = totalColumnCount - measureDataCount;
            needProcess = true;
        }
        if (needProcess) {
            int dataEndColumn = dataStartColumn + measureDataCount;
            for (int i = dataStartColumn; i < dataEndColumn; i++) {
                int index = (i - dataStartColumn) % measureDataCount;
                ReportViewFrm viewFrm = measureValueFields.get(index);
                reportValue[aboveCategoryCount][i] = viewFrm.getFieldDesc();
            }
        }
    }

    private void produceLeftColumnHeader() throws ContainerException {
        if (!executeFrm.hasData()) {
            return;
        }
        int startRow = aboveCategoryCount + 1;
        int endColumn = leftCategoryCount;
        for (int i = 0; i < leftDimensionCount; i++) {
            Row row = leftDimensions.getRow(i);
            for (int j = 0; j < endColumn; j++) {
                String fieldName = leftCategories.get(j).getFieldName();
                String fieldValue = row.getStrValue(fieldName);
                reportValue[i + startRow][j] = fieldValue;
            }
        }
    }

    private void produceLeftCategoryTitle(){
        if(leftCategoryCount > 0){
            if(aboveCategoryCount > 0){
                for(int i = 0; i < leftCategoryCount; i++){
                    for(int j = 0; j <= aboveCategoryCount; j++){
                        reportValue[j][i] = leftCategories.get(i).getFieldDesc();
                    }
                }
            } else {
                for(int i = 0; i < leftCategoryCount; i++){
                    reportValue[0][i] = leftCategories.get(i).getFieldDesc();
                }
            }
        }
    }

    private void processReportData() throws ReportException {
        try {
            int startRow = aboveCategoryCount + 1;
            int rowCount = leftDimensionCount;
            rowCount += startRow;
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            String sumPosition = definedReport.getSumPosition();
            if (leftDimensionCount > 0 && aboveDimensionCount > 0) {//双向分组
                for (int i = startRow; i < rowCount; i++) {
                    Row leftRow = leftDimensions.getRow(i - startRow);
                    for (int j = 0; j < aboveDimensionCount; j++) {
                        Row aboveRow = aboveDimensions.getRow(j);
                        Row row = getDoubleIntersectRow(leftRow, aboveRow);
                        populateDoubleIntersectRow2Report(row, i, j);
                    }
                }
            } else if (leftDimensionCount > 0 && aboveDimensionCount == 0) {//仅纵向分组
                int startColumn = leftCategoryCount;
                if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                        || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) { //合计值位于：左部区域
                    startColumn += measureDataCount;
                }
                for (int i = startRow; i < rowCount; i++) {
                    Row leftRow = leftDimensions.getRow(i - startRow);
                    Row row = getVerticalIntersectRow(leftRow);
                    for (int j = 0; j < measureDataCount; j++) {
                        String fieldName = measureValueFields.get(j).getFieldName();
                        String fieldValue = StrUtil.nullToString(row.getValue(fieldName));
                        if(fieldValue.length() == 0){
                            fieldValue = "0";
                        }
//                        reportValue[i][startColumn + j] = row.getStrValue(fieldName);
                        reportValue[i][startColumn + j] = fieldValue;
                    }
                }
            } else if (leftDimensionCount == 0 && aboveDimensionCount > 0) {//仅横向分组
                int startColumn = leftCategoryCount;
                if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                        || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) { //合计值位于：左部区域
                    startColumn += measureDataCount;
                }
                for (int i = 0; i < aboveDimensionCount; i++) {
                    Row aboveRow = aboveDimensions.getRow(i);
                    Row row = getAboveIntersectRow(aboveRow);
                    for (int j = 0; j < measureDataCount; j++) {
                        String fieldName = measureValueFields.get(j).getFieldName();
                        int columnIndex = (i * measureDataCount) + startColumn + j;

                        String fieldValue = StrUtil.nullToString(row.getValue(fieldName));
                        if(fieldValue.length() == 0){
                            fieldValue = "0";
                        }
//                        reportValue[aboveCategoryCount + 1][columnIndex] = row.getStrValue(fieldName);
                        reportValue[aboveCategoryCount + 1][columnIndex] = fieldValue;
                    }
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        }
    }

    private Row getDoubleIntersectRow(Row leftRow, Row aboveRow) throws ReportException {
        Row dataRow = null;
        try {
            Row referenceRow = new Row();
            List<String> fieldNames = leftRow.getFieldNames();
            for (String fieldName : fieldNames) {
                String fieldValue = leftRow.getStrValue(fieldName);
                referenceRow.addField(fieldName, fieldValue);
            }

            fieldNames = aboveRow.getFieldNames();
            for (String fieldName : fieldNames) {
                String fieldValue = aboveRow.getStrValue(fieldName);
                referenceRow.addField(fieldName, fieldValue);
            }
            int dataCount = searchResult.getSize();
            fieldNames = referenceRow.getFieldNames();
            for (int i = 0; i < dataCount; i++) {
                boolean found = true;
                Row row = searchResult.getRow(i);
                for (String fieldName : fieldNames) {
                    if (!row.getValue(fieldName).equals(referenceRow.getValue(fieldName))) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    dataRow = row;
                    break;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return dataRow;
    }


    private Row getVerticalIntersectRow(Row leftRow) throws ReportException {
        Row dataRow = null;
        try {
            Row referenceRow = new Row();
            List<String> fieldNames = leftRow.getFieldNames();
            for (String fieldName : fieldNames) {
                String fieldValue = leftRow.getStrValue(fieldName);
                referenceRow.addField(fieldName, fieldValue);
            }
            int dataCount = searchResult.getSize();
            fieldNames = referenceRow.getFieldNames();
            for (int i = 0; i < dataCount; i++) {
                boolean found = true;
                Row row = searchResult.getRow(i);
                for (String fieldName : fieldNames) {
                    if (!row.getValue(fieldName).equals(referenceRow.getValue(fieldName))) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    dataRow = row;
                    break;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return dataRow;
    }


    private Row getAboveIntersectRow(Row aboveRow) throws ReportException {
        Row dataRow = null;
        try {
            Row referenceRow = new Row();
            List<String> fieldNames = aboveRow.getFieldNames();
            for (String fieldName : fieldNames) {
                String fieldValue = aboveRow.getStrValue(fieldName);
                referenceRow.addField(fieldName, fieldValue);
            }
            int dataCount = searchResult.getSize();
            fieldNames = referenceRow.getFieldNames();
            for (int i = 0; i < dataCount; i++) {
                boolean found = true;
                Row row = searchResult.getRow(i);
                for (String fieldName : fieldNames) {
                    if (!row.getValue(fieldName).equals(referenceRow.getValue(fieldName))) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    dataRow = row;
                    break;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return dataRow;
    }

    private void populateDoubleIntersectRow2Report(Row row, int rowNumber, int columnFieldNumber) throws ReportException {
        try {
            int dataFrmCount = measureValueFields.size();
            int startColumn = leftCategoryCount;
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            String sumPosition = definedReport.getSumPosition();
            if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                    || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                startColumn += measureDataCount;
            }
            for (int i = 0; i < dataFrmCount; i++) {
                ReportViewFrm dataFrm = measureValueFields.get(i);
                int columnNumber = columnFieldNumber * dataFrmCount + startColumn + i;
                if (row == null) {
                    String fieldType = dataFrm.getFieldType();
                    if (fieldType.equals(DataTypeConstant.NUMBER) || fieldType.equals("INT")||fieldType.equals("DECIMAL")) {
                        reportValue[rowNumber][columnNumber] = "0";
                    } else {
                        reportValue[rowNumber][columnNumber] = "";
                    }
                } else {
                    String fieldName = dataFrm.getFieldName();
                    String fieldValue = StrUtil.nullToString(row.getValue(fieldName));
                    if(fieldValue.length() == 0){
                        fieldValue = "0";
                    }
                    reportValue[rowNumber][columnNumber] = fieldValue;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
    }

    private void processBottomExpressionValues() throws ContainerException {
        if (!executeFrm.hasData()) {
            return;
        }
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        String sumPosition = definedReport.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)
                ) {
            int dimCount = aboveDimensionCount;
            int categoryCount = leftCategoryCount;
            int lastLine = totalRowCount - 1;
            int columnOffSet = 0;
            if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                columnOffSet += measureDataCount;
            }
            if (leftDimensionCount > 0 && aboveDimensionCount > 0) {
                for (int i = 0; i < dimCount; i++) {
                    Row row = aboveDimensions.getRow(i);
                    Row expRow = findExpressionRow(row, bottomExpressions);
                    for (int j = 0; j < measureDataCount; j++) {
                        ReportViewFrm viewFrm = measureValueFields.get(j);
                        String fieldName = viewFrm.getFieldName();
                        String fieldValue = expRow.getStrValue(fieldName);
                        int columnIndex = i * measureDataCount + categoryCount + columnOffSet + j;
                        reportValue[lastLine][columnIndex] = fieldValue;
                    }
                }
            } else if (leftDimensionCount > 0 && aboveDimensionCount == 0) {
                int startColumn = leftCategoryCount;
                if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                    startColumn += measureDataCount;
                }
                Row row = bottomExpressions.getRow(0);
                for (int i = 0; i < measureDataCount; i++) {
                    ReportViewFrm viewFrm = measureValueFields.get(i);
                    String fieldName = viewFrm.getFieldName();
                    String fieldValue = row.getStrValue(fieldName);
                    reportValue[lastLine][startColumn + i] = fieldValue;
                }
            } else if (leftDimensionCount == 0 && aboveDimensionCount > 0) {
                int startColumn = leftCategoryCount;
                if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                        || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                    startColumn += measureDataCount;
                }
                for (int i = 0; i < aboveDimensionCount; i++) {
                    Row aboveRow = bottomExpressions.getRow(i);
                    for (int j = 0; j < measureDataCount; j++) {
                        String fieldName = measureValueFields.get(j).getFieldName();
                        int columnIndex = (i * measureDataCount) + startColumn + j;
                        reportValue[totalRowCount - 1][columnIndex] = aboveRow.getStrValue(fieldName);
                    }
                }
            }
        }
    }

    private Row findExpressionRow(Row dimensionRow, RowSet expressions) throws ContainerException {
        int rowCount = expressions.getSize();
        List<String> fieldNames = dimensionRow.getFieldNames();
        for (int i = 0; i < rowCount; i++) {
            Row expRow = expressions.getRow(i);
            boolean foundData = true;
            for (String fieldName : fieldNames) {
                if (!expRow.getStrValue(fieldName).equals(dimensionRow.getValue(fieldName))) {
                    foundData = false;
                    break;
                }
            }
            if (foundData) {
                return expRow;
            }
        }
        return null;
    }


    /**
     * 功能:构造报表的合计值。可能在报表右边，即报表最后一列，也可能在报表左边，紧接着分组值之后。
     * 是对报表的每一行求和．
     *
     * @throws ContainerException 容器异常
     */
    private void processVerticalExpressionValues() throws ContainerException {
        if (!executeFrm.hasData()) {
            return;
        }
        if (verticalExpressions != null && !verticalExpressions.isEmpty()) {
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            String sumPosition = definedReport.getSumPosition();
            int offsetRow = aboveCategoryCount + 1;
            int startColumn = 0;
            int endRow = totalRowCount;
            if (sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)
                    || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {
                startColumn = totalColumnCount - measureDataCount;
                if(sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)){
                    endRow--;
                }
            } else if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                    || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                startColumn = leftCategoryCount;
                if(sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)){
                    endRow--;
                }
            }
            for (int i = offsetRow; i < endRow; i++) {
                Row row = verticalExpressions.getRow(i - offsetRow);
                for (int j = startColumn; j < startColumn + measureDataCount; j++) {
                    ReportViewFrm rvf = measureValueFields.get(j - startColumn);
                    String fieldName = rvf.getFieldName();
                    String fieldValue = row.getStrValue(fieldName);
                    reportValue[i][j] = fieldValue;
                }
            }
        }
    }

    private void processIntersectValues() {
        executeFrm.setAboveCategories(aboveCategories);
        executeFrm.setLeftCategories(leftCategories);
        executeFrm.setMeasureValueFields(measureValueFields);
        executeFrm.setReportValue(reportValue);
    }
}