/**
 * 功能：将数组appendData中的数据加入到表tab的新加行。
 * tab：表格对象
 * appendData：准备加入的数据传输对象
 * useOldData：在DTO对象没有对应属性时，是否利用上一行值。true表示新增行将利用原值，false表示新值为空。
 */
function appendDTORow(tab, dtoObj, useOldData) {
    if (!tab) {
        alert("表格对象不存在，系统无法处理");
        return;
    }
    if (!tab.rows) {
        alert("表格对象没有参考数据，系统无法处理");
        return;
    }
    if (!dtoObj) {
        alert("DTO数据传输对象为空，系统无法处理");
        return;
    }
    if (useOldData == "undefined" || useOldData == null) {
        useOldData = false;
    }
    var rows = tab.rows;
    var rowCount = rows.length;
    var row = rows[rowCount - 1];
    var newRow = null;
    if (rowCount == 1 && row.style.display == "none") {
        newRow = row;
    } else {
        newRow = row.cloneNode(true);
    }
    var children = newRow.childNodes;
    var child = null;
    var tagName = "";
    var nodeType = "";
    var srcId = "";
    var newId = "";
    var fieldNodes = null;
    var node = null;
    var nodeHtm = "";
    var foundDate = false;
    var index = -1;
    var elementName = "";
    var cellValue = "";
    for (var i = 0; i < children.length; i++) {//单元格循环
        child = children[i];
        tagName = child.tagName;
        if (tagName.toUpperCase() != "TD") {
            continue;
        }
        elementName = child.name;
        if (elementName) {
            var breaked = false;
            for (prop in dtoObj) {
                if (elementName == String(prop)) {
                    child.innerText = dtoObj[prop];
                    breaked = true;
                    break;
                }
            }
            if (!breaked && !useOldData) {
                child.innerText = "";
            }
        } else {
            cellValue = "";
            fieldNodes = child.childNodes;
            if (fieldNodes.length == 1 && fieldNodes[0].nodeName == "#text") {
                child.innerText = "";
            } else {
                for (var j = 0; j < fieldNodes.length; j++) {//单元格内表单域循环
                    node = fieldNodes[j];
                    srcId = node.id;
                    tagName = node.tagName;
                    nodeType = node.type;
                    elementName = node.name;
                    if (!srcId) {
                        continue;
                    }
                    index = srcId.indexOf(elementName);
                    if (index != 0) {
                        alert("表单域的ID必须以名称开头，请检查表单域“" + srcId + "”");
                        return;
                    }
                    var num = srcId.substring(elementName.length);
                    newId = elementName + (Number(num) + 1);
                    node.id = newId;
                    if (tagName == "SELECT") {
                        for (prop in dtoObj) {
                            if (elementName == String(prop)) {
                                selectSpecialOptionByItem(node, dtoObj[prop]);
                                break;
                            }
                        }

                    } else {
                        var breaked = false;
                        for (prop in dtoObj) {
                            if (elementName == String(prop)) {
                                node.value = dtoObj[prop];
                                breaked = true;
                                break;
                            }
                        }
                        if (!breaked && !useOldData && !foundDate) {
                            node.value = "";
                        }
                    }
                    if (nodeType == "checkbox" || nodeType == "radio") {
                        node.checked = false;
                    }
                    nodeHtm = node.outerHTML;
                    if (nodeHtm.indexOf("gfPop") > -1) {
                        foundDate = true;
                    }
                }
                if (foundDate) {//对日期特殊处理，因为其函数参数的问题
                    nodeHtm = node.outerHTML;
                    var oldGfPopHtm = findGfPop(nodeHtm);
                    var newGfPopHtm = new String(oldGfPopHtm);
                    var fromIndex = oldGfPopHtm.indexOf("(") + 1;
                    var middleIndex = oldGfPopHtm.indexOf(",");
                    var endIndex = oldGfPopHtm.indexOf(")");
                    var startDate = "";
                    var endDate = "";
                    var startValue = false;
                    var endValue = false;
                    if (middleIndex != -1) {
                        startDate = oldGfPopHtm.substring(fromIndex, middleIndex);
                        endDate = oldGfPopHtm.substring(middleIndex + 1, endIndex);
                        endDate = replaceStr(endDate, " ", "");
                    } else {
                        startDate = oldGfPopHtm.substring(fromIndex, endIndex);
                    }
                    if (isValidDate(startDate)) {
                        startValue = true;
                    }
                    if (isValidDate(endDate)) {
                        endValue = true;
                    }
                    var srcId = "";
                    if (middleIndex != -1) {
                        if (!startValue) {
                            srcId = getNumFromText(startDate);
                        } else {
                            srcId = getNumFromText(endDate);
                        }
                    } else {
                        if (!startValue) {
                            srcId = getNumFromText(startDate);
                        }
                    }
                    var newStartDate = "";
                    var newEndDate = "";
                    if (srcId != "") {
                        var index1 = -1;
                        var index2 = -1;
                        if (middleIndex != -1) {
                            if (!startValue) {
                                index1 = startDate.indexOf(srcId);
                            }
                            if (!endValue) {
                                index2 = endDate.indexOf(srcId);
                            }
                            if (startValue) {
                                newStartDate = startDate;
                            } else {
                                newStartDate = startDate.substring(0, index1) + (Number(srcId) + 1);
                            }
                            if (endValue) {
                                newEndDate = endDate;
                            } else {
                                newEndDate = endDate.substring(0, index2) + (Number(srcId) + 1);
                            }
                        } else {
                            if (!startValue) {
                                index1 = startDate.indexOf(srcId);
                                newStartDate = startDate.substring(0, index1) + (Number(srcId) + 1);
                            } else {
                                newStartDate = startDate;
                            }
                        }
                    } else {
                        if (startValue) {
                            newStartDate = startDate;
                        } else {
                            newStartDate = startDate + "1";
                        }
                        if (endValue) {
                            newEndDate = endDate;
                        } else {
                            newEndDate = endDate + "1";
                        }
                    }
                    newGfPopHtm = newGfPopHtm.replace(startDate, newStartDate);
                    newGfPopHtm = newGfPopHtm.replace(endDate, newEndDate);
                    nodeHtm = nodeHtm.replace(oldGfPopHtm, newGfPopHtm);
                    node.outerHTML = nodeHtm;
                    foundDate = false;
                }
            }
        }
    }
    newRow.style.display = "block";
    row.parentNode.appendChild(newRow);
}


/**
 * 功能：将数组appendData中的数据加入到表tab的新加行。
 * tab：表格对象
 * appendData：准备加入的数据传输对象
 * useOldData：在DTO对象没有对应属性时，是否利用上一行值。true表示新增行将利用原值，false表示新值为空。
 * uniqueField：唯一性字段，设置了该值时选择的重复数据将被过滤掉，如果未设置或
 */
function appendDTO2Table(tab, dtoObj, useOldData, uniqueField) {
    if (uniqueField == "undefined" || uniqueField == null) {
        return;
    }
    if (dtoObj[uniqueField] != "undefined") {
        var dataExist = false;
        var newAssets = dtoObj[uniqueField];
        var objs = document.getElementsByName(uniqueField);
        if (objs) {
            if (objs.length) {
                for (var i = 0; i < objs.length; i++) {
                    if (objs[i].value == newAssets) {
                        dataExist = true;
                        break;
                    }
                }
            } else {
                if (objs.value == newAssets) {
                    dataExist = true;
                }
            }
        }
        if (!dataExist) {
            appendDTORow(tab, dtoObj, useOldData);
        }
    } else {
        appendDTORow(tab, dtoObj, useOldData);
    }
}

/**
 * 功能：将数组appendData中的数据加入到表tab的新加行。
 * tab：表格对象
 * appendData：准备加入的数据数组(用于列显示)
 * appendIndex：数据加入列索引数组(用于列显示)
 * hiddenFields：隐藏域名称数组
 * hiddenValues：隐藏域值数组
 */
function appendRow(tab, dataArr, indexArr, hiddenNames, hiddenValues) {
    if (!tab) {
        alert("表格对象不存在，系统无法处理");
        return;
    }
    if (!tab.rows) {
        alert("表格对象没有参考数据，系统无法处理");
        return;
    }
    if (!(typeof(dataArr) == "object" && typeof(dataArr.length))) {
        alert("加入的数据不是数组，系统无法处理");
        return;
    }
    if (!(typeof(indexArr) == "object" && typeof(indexArr.length))) {
        alert("列索引值不是数组，系统无法处理");
        return;
    }
    if (dataArr.length != indexArr.length) {
        alert("数据数组和列索引数组长度不一致，系统无法处理");
        return;
    }
    if (hiddenNames) {
        if (!(typeof(hiddenNames) == "object" && typeof(hiddenNames.length))) {
            alert("加入的隐藏域名称数组，系统无法处理");
            return;
        }
        if (!(typeof(hiddenValues) == "object" && typeof(hiddenValues.length))) {
            alert("加入的隐藏域值不是数组，系统无法处理");
            return;
        }
        if (hiddenNames.length != hiddenValues.length) {
            alert("隐藏域名称和隐藏域值数组长度不一致，系统无法处理");
            return;
        }
    }
    var rows = tab.rows;
    var rowCount = rows.length;
    var row = rows[rowCount - 1];
    var newRow = row.cloneNode(true);
    var children = newRow.childNodes;
    var child = null;
    var tagName = "";
    var nodeType = "";
    var srcId = "";
    var newId = "";
    var fieldNodes = null;
    var node = null;
    var nodeHtm = "";
    var foundDate = false;
    var index = -1;
    var cellValue = "";
    for (var i = 0; i < children.length; i++) {//单元格循环
        child = children[i];
        tagName = child.tagName;
        if (tagName.toUpperCase() != "TD") {
            continue;
        }
        cellValue = "";
        fieldNodes = child.childNodes;
        index = findIndexOfArr(indexArr, i);
        if (index != -1) {
            cellValue = dataArr[index];
        }
        if (!fieldNodes) {
            if (index != -1) {
                child.innerText = cellValue;
            } else {
                child.innerText = "";
            }
        } else {
            for (var j = 0; j < fieldNodes.length; j++) {//单元格内表单域循环
                index = -1;
                node = fieldNodes[j];
                srcId = node.id;
                if (!srcId) {
                    continue;
                }
                var num = getNumFromText(srcId);
                if (num == "") {
                    newId = srcId + "1";
                } else {
                    newId = srcId.substring(0, srcId.indexOf(num)) + (Number(num) + 1);
                }
                node.id = newId;
                tagName = node.tagName;
                nodeType = node.type;
                if (tagName == "SELECT") {
                    selectSpecialOptionByItem(node, cellValue);
                } else if (nodeType != "hidden") {
                    node.value = cellValue;
                } else if (hiddenNames) {//隐藏域赋值
                    var fieldName = node.name;
                    index = findIndexOfArr(hiddenNames, fieldName);
                    if (index != -1) {
                        node.value = hiddenValues[index];
                    }
                }
                if (nodeType == "checkbox" || nodeType == "radio") {
                    node.checked = false;
                }
                nodeHtm = node.outerHTML;
                if (nodeHtm.indexOf("gfPop") > -1) {
                    foundDate = true;
                }
            }
            if (foundDate) {//对日期特殊处理，因为其函数参数的问题
                nodeHtm = node.outerHTML;
                var fromIndex = nodeHtm.indexOf("(") + 1;
                var middleIndex = nodeHtm.indexOf(",");
                var endIndex = nodeHtm.indexOf(")");
                var startDate = nodeHtm.substring(fromIndex, middleIndex);
                var endDate = nodeHtm.substring(middleIndex + 1, endIndex);
                endDate = replaceStr(endDate, " ", "");
                srcId = getNumFromText(startDate);
                var newStartDate = "";
                var newEndDate = "";
                if (srcId != "") {
                    var index1 = startDate.indexOf(srcId);
                    var index2 = endDate.indexOf(srcId);
                    newStartDate = startDate.substring(0, index1) + (Number(srcId) + 1);
                    newEndDate = endDate.substring(0, index2) + (Number(srcId) + 1);
                } else {
                    newStartDate = startDate + "1";
                    newEndDate = endDate + "1";
                }
                nodeHtm = replaceStr(nodeHtm, startDate, newStartDate);
                nodeHtm = replaceStr(nodeHtm, endDate, newEndDate);
                node.outerHTML = nodeHtm;
                foundDate = false;
            }
        }
    }
    newRow.style.display = "block";
    row.parentNode.appendChild(newRow);
}


/**
 * 功能：执行表格行中的checkbox或者radio的click功能。
 */
function executeClick(tr) {
//    if (tr) {
//        var cells = tr.cells;
//        var cell = null;
//        var children = null;
//        var child = null;
//        var childType = null;
//        if (cells && cells.length) {
//            for (var i = 0; i < cells.length; i++) {
//                cell = cells[i];
//                children = cell.childNodes;
//                if (children) {
//                    for (var j = 0; j < children.length; j++) {
//                        child = children[j];
//                        childType = child.type;
//                        if (childType && (childType == "checkbox" || childType == "radio")) {
//                            child.click();
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//    }
}

/**
 * 功能：将表中chkObj所在行删除
 * tableId：表的ID；
 * chkObj：复选框对象。
 *
 */
function delTableRow(tab, chkObj) {
    if (!tab || !chkObj) {
        return;
    }
    var trObj = chkObj;
    var trHtm = "";
    for (var i = 0; ; i++) {
        trHtm = trObj.outerHTML;
        var index = trHtm.indexOf("<TR");
        if (index > -1) {
            tab.deleteRow(trObj.rowIndex);
            return;
        }
        trObj = trObj.parentNode;
    }
}

/**
 * 功能：将表中chkObj所在行内容清除
 * tab：表对象；
 * chkObj：复选框对象。
 *
 */
function clearContent(tab, chkObj) {
    if (!tab || !chkObj) {
        return;
    }
    var trObj = chkObj.parentNode.parentNode;
    var cells = trObj.cells;
    var cell = null;
    var fields = null;
    for (var i = 0; i < cells.length; i++) {
        cell = cells[i];
        fields = cell.childNodes;
        if (fields.length) {
            var field = null;
            var nodeType = "";
            for (var j = 0; j < fields.length; j++) {
                field = fields[j];
                nodeType = field.type;
                if (!nodeType) {
                    continue;
                }
                if (nodeType == "checkbox" || nodeType == "radio") {
                    field.checked = false;
                    field.value = "";
                } else if (nodeType.indexOf("select") == -1) {
                    field.value = "";
                }
            }
        } else {
            cell.innerText = "";
        }
    }
}

/**
 * 功能：将表中选中行删除。
 * tab：表格对象；
 * checkboxName：复选框名称。
 */
function deleteTableRow(tab, checkboxName) {
    if (!tab || !checkboxName) {
        return;
    }
    var rowCount = tab.rows.length;
    if (rowCount == 0) {
        alert("不存在要删除的行。");
        return;
    }
    var boxArr = getCheckedBox(checkboxName);
    var chkCount = boxArr.length;
    if (chkCount < 1) {
        alert("请先选择要删除的行！");
        return;
    }
    if (confirm("确定要删除选中的行吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        var chkObj = null;
        for (var i = 0; i < chkCount; i++) {
            chkObj = boxArr[i];
            if (tab.rows.length > 1) {
                delTableRow(tab, chkObj);
            } else {
                clearContent(tab, chkObj);
                tab.rows[0].style.display = "none";
            }
        }
    }
}
function deleteTableRow2(tab, checkboxName) {   //与deleteTableRow仅是删除了第一行
    if (!tab || !checkboxName) {
        return;
    }
    var rowCount = tab.rows.length;
    if (rowCount == 0) {
        alert("不存在要删除的行。");
        return;
    }
    var boxArr = getCheckedBox(checkboxName);
    var chkCount = boxArr.length;
    if (chkCount < 1) {
        alert("请先选择要删除的行！");
        return;
    }
    if (confirm("确定要删除选中的行吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        var chkObj = null;
        for (var i = 0; i < chkCount; i++) {
            chkObj = boxArr[i];
            delTableRow(tab, chkObj);
        }
    }
}
/**
 * 功能：将表中选中行删除，不加提示，用于程序想要自行控制时.
 * tab：表格对象；
 * checkboxName：复选框名称。
 * <B>前提：表中各行必须含有复选框</B>
 */
function deleteRow(tab) {
    if (!(tab && tab.rows)) {
        return;
    }
    var tr = tab.rows[0];
    var checkboxName = "";
    var cells = tr.cells;
    var cell = null;
    var fields = null;
    for (var i = 0; i < cells.length; i++) {
        cell = cells[i];
        fields = cell.childNodes;
        var hasFound = false;
        if (fields.length) {
            var field = null;
            var nodeType = "";
            for (var j = 0; j < fields.length; j++) {
                field = fields[j];
                nodeType = field.type;
                if (!nodeType) {
                    continue;
                }
                if (nodeType == "checkbox") {
                    field.checked = false;
                    checkboxName = field.name;
                    hasFound = true;
                    break;
                }
            }
        } else {
            checkboxName = fields.name;
        }
        if (hasFound) {
            break;
        }
    }
    setCheckBoxState(checkboxName, true);
    var boxArr = getCheckedBox(checkboxName);
    var chkCount = boxArr.length;
    var chkObj = null;
    for (var i = 0; i < chkCount; i++) {
        chkObj = boxArr[i];
        if (tab.rows.length > 1) {
            delTableRow(tab, chkObj);
        } else {
            clearContent(tab, chkObj);
            tab.rows[0].style.display = "none";
        }
    }
}
/**
 * 功能：自动合并表格的相邻行的text相同的cells。
 * tableID 表格ID
 * columnNumber 需要合并的列数，从左开始
 */
function autoSpan(tableID, columnNumber) {
    var obj = document.all[tableID];
    var head = new Array(columnNumber);
    var rows = obj.rows.length;
    if (rows > 1) {
        var cols = obj.rows[0].cells.length;
        for (j = 0; j < columnNumber; j++) {
            head[j] = obj.rows[0].cells[j].innerText;
        }
        for (i = 1; i < rows; i++) {
            var flag = 0;
            for (j = 0; j < columnNumber; j++) {
                if (head[j] == obj.rows[i].cells[j].innerText && flag == 0) {
                    obj.rows[i].cells[j].innerText = "";
                } else {
                    flag = 1;
                    head[j] = obj.rows[i].cells[j].innerText;
                    //continue;
                }
            }
        }
        //modified by wwb.2009-05-07,从columnNumber - 1行开始
        for (j = columnNumber - 1; j >= 0; j--) {
            var cell = obj.rows[0].cells[j];
            var span = 1;
            for (i = 1; i < rows; i++) {
                if (obj.rows[i].cells[j].innerText == "") {
                    span++;
                    obj.rows[i].deleteCell(j);
                    cell.rowSpan = span;
                } else {
                    cell = obj.rows[i].cells[j];
                    span = 1;
                }
            }
        }
    }
}

/**
 * 功能：自动合并表格的相邻行的text相同的cells。
 * tableID 表格ID
 * fromNumber 合并开始的列数，
 * toNumber 合并结束的列数，
 */
function autoFromToSpan(tableID, fromNumber, toNumber) {
    var obj = document.all[tableID];
    var head = new Array(toNumber - fromNumber);
    var rows = obj.rows.length;
    if (rows > 1) {
        var cols = obj.rows[0].cells.length;
        for (j = fromNumber; j < toNumber; j++) {
            head[j - fromNumber] = obj.rows[0].cells[j].innerText;
        }
        for (i = 1; i < rows; i++) {
            var flag = 0;
            for (j = fromNumber; j < toNumber; j++) {
                if (head[j - fromNumber] == obj.rows[i].cells[j].innerText && flag == 0) {
                    obj.rows[i].cells[j].innerText = "";
                } else {
                    flag = 1;
                    head[j - fromNumber] = obj.rows[i].cells[j].innerText;
                    //continue;
                }
            }
        }
        for (j = toNumber; j >= fromNumber; j--) {
            var cell = obj.rows[0].cells[j];
            var span = 1;
            for (i = 1; i < rows; i++) {
                if (obj.rows[i].cells[j].innerText == "") {
                    span++;
                    obj.rows[i].deleteCell(j);
                    cell.rowSpan = span;
                } else {
                    cell = obj.rows[i].cells[j];
                    span = 1;
                }
            }
        }
    }
}

function findGfPop(inputHtm) {
    var gfPopHtm = "";
    if (inputHtm) {
        var index = inputHtm.indexOf("gfPop");
        if (index > -1) {
            var endHtm = inputHtm.substring(index);
            var sinChar = "";
            for (var i = 0; i < endHtm.length; i++) {
                sinChar = endHtm.substring(i, i + 1);
                gfPopHtm += sinChar;
                if (sinChar == ")") {
                    break;
                }
            }
        }
    }
    return gfPopHtm;
}

/**
 * 增加一行，复制最后一行
 * @param tab
 */
function appendNewRow(tab, row) {
    if (!tab) {
        alert("表格对象不存在，系统无法处理");
        return;
    }
    var cellCount,newRow,cell;
    if (row) {
        cellCount = row.cells.length;
        newRow = tab.insertRow();
        for (var k = 0; k < cellCount; k++)
        {
            cell = newRow.insertCell();
            cell.innerHTML = row.cells[k].innerHTML;
        }
    } else {
        var rowCount = tab.rows.length;
        var lastRow = tab.rows[rowCount - 1];

        cellCount = tab.rows(0).cells.length;

        newRow = tab.insertRow();
        for (var i = 0; i < cellCount; i++)
        {
            cell = newRow.insertCell();
            cell.innerHTML = lastRow.cells[i].innerHTML;
        }
    }
}

function delteAllRow(tab) {
    if (!tab) {
        alert("表格对象不存在，系统无法处理");
        return;
    }
    var rowCount = tab.rows.length;

    for (var i = rowCount - 1; i > -1; i--)
    {
        tab.deleteRow(i);
    }
}


/**
 * 功能：获取指定行指定名称的表单元素对象
 * @param tr
 * @param objName
 */
function getTrNode(tr, objName) {
    var obj = null;
    var cells = tr.cells;
    var index = -1;
    for (var i = 0; i < cells.length; i++) {
        var cell = cells[i];
        var nodes = cell.childNodes;
        var breaked = false;
        for (var j = 0; j < nodes.length; j++) {
            var node = nodes[j];
            var nodeName = node.name;
            if (!nodeName) {
                continue;
            }
            if(nodeName != objName){
                index = nodeName.indexOf("###");
                var offset = 3;
                if (index == -1) {
                    offset = 2;
                    index = nodeName.indexOf("]");
                }
                if(index == -1){
                    continue;
                }
                nodeName = nodeName.substring(index + offset);
            }
            if (nodeName == objName) {
                obj = node;
                breaked = true;
                break;
            }
        }
        if (breaked) {
            break;
        }
    }
    return obj;
}
