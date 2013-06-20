var sortDesc = "↓";
var sortAsc = "↑";
/**
 * 功能：当wincodw窗口发生大小变动的时候，自行执行上述方法
 */
window["onresize"] = function() {
    do_SetPageWidth();
};


/**
 * 功能：设置页面表格的宽度。例如查询页面的使用
 * 该方法可使页面宽度根据屏幕分辨率自行调整
 * 要求：页面需要有headDiv和dataDiv两个层.如果要整个页面控制好,可在参数部分加searchDiv,导航条部分加上pageNaviDiv。
 * 表头部分的表ID为“headTable”，如果原来的JSP页面未设置，且页面较多的话，可通过headDiv获取其子元素的方式来获取该表格，周劲松可修改一下。
 */
function do_SetPageWidth() {
    var bodyWidth = document.body.clientWidth;
    var bodyHeight = document.body.clientHeight;
    var screenWidth = window.screen.width;
    var screenHeight = window.screen.height;
    if(bodyWidth + 10 > screenWidth){
        bodyWidth = screenWidth - 10;
    }
    if(bodyHeight + 72 > screenHeight){
        bodyHeight = screenHeight - 72;
    }
    var searchDiv = document.getElementById("searchDiv");
    var buttonDiv = document.getElementById("buttonDiv");
    var headDiv = document.getElementById("headDiv");
    var dataDiv = document.getElementById("dataDiv");
    var pageNaviDiv = document.getElementById("pageNaviDiv");
    var headTable = getHeadTable();
    var clientWidthObj = document.getElementById("clientWidth");
    if (clientWidthObj) {
        clientWidthObj.value = bodyWidth;
    }
    if (headDiv) {
        headDiv.style.position = "absolute";
        headDiv.style.overflowX = "hidden";
        headDiv.style.overflowY = "scroll";
        headDiv.style.width = bodyWidth;
        if (searchDiv != null && !searchDiv != undefined) {
            searchDiv.style.position = "absolute";
            searchDiv.style.width = bodyWidth;
            var searchHeight = searchDiv.offsetHeight;
            var searchTable = getChildTable(searchDiv);
            if (searchTable) {
                searchHeight = searchTable.offsetHeight;
            }
            var searchTop = searchDiv.style.top;
            if (!searchTop || searchTop == null || searchTop == undefined || searchTop == "") {
                searchTop = searchDiv.offsetTop;
            }
            var strHeight = String(searchHeight);
            if (strHeight.indexOf("px") > -1) {
                searchHeight = Number(strHeight.substring(0, strHeight.length - 2));
            }
            var strTop = String(searchTop);
            if (strTop.indexOf("px") > -1) {
                searchTop = Number(strTop.substring(0, strTop.length - 2));
            }
            if (buttonDiv) {
                buttonDiv.style.top = (searchTop + searchHeight + 8);
                headDiv.style.top = (searchTop + searchHeight + 34);
            } else {
                headDiv.style.top = (searchTop + searchHeight + 5);
            }
        }
    }
    var dataHeight = null;
    if (dataDiv != null && dataDiv != undefined) {
        dataDiv.style.width = bodyWidth;
        dataDiv.style.position = "absolute";
        dataDiv.style.overflow = "scroll";
        if (headTable != undefined && headTable != null) {
            dataDiv.style.top = headDiv.offsetTop + headTable.offsetHeight;
        } else {
            dataDiv.style.top = headDiv.offsetTop + headDiv.offsetHeight;
        }
        var dataTop = dataDiv.style.top.toLowerCase();
        dataHeight = dataDiv.style.height.toLowerCase();
        if (dataTop.indexOf("px") > -1) {
            dataTop = Number(dataTop.substring(0, dataTop.length - 2));
        } else if (dataTop == "") {
            dataTop = Number(dataDiv.offsetTop);
        }
        if (dataHeight.indexOf("px") > -1) {
            dataHeight = Number(dataHeight.substring(0, dataHeight.length - 2));
        } else if (dataHeight.indexOf("%") > -1) {
            dataHeight = "0." + dataHeight.replace("%", "");
            dataHeight = Number(dataHeight) * bodyHeight;
        }
        dataHeight = bodyHeight - dataTop;
        dataDiv.style.height = dataHeight;
    }
    if (pageNaviDiv != null && pageNaviDiv != undefined) {
        pageNaviDiv.style.position = "absolute";
        pageNaviDiv.style.left = 1;
        pageNaviDiv.style.width = bodyWidth;
        var pageDivHeight = pageNaviDiv.offsetHeight;
        if(!pageDivHeight){
            pageDivHeight = 50;
        }
        if(dataHeight){
            if (dataHeight > pageDivHeight) {
                dataDiv.style.height = dataHeight - pageDivHeight;
            }
            pageNaviDiv.style.top = dataDiv.offsetTop + dataDiv.offsetHeight;
        } else {
//            pageNaviDiv.style.top = bodyHeight - 20;
        }
    }
    do_ProcessAdvancedFeature();
    //关闭遮罩
    try{
		document.getElementById("amsprogressbar").style.display="none";
	}catch(ex){}
}

function do_ProcessAdvancedFeature() {
    var tableProcessed = do_FixTableLayout();
    if (tableProcessed) {
        var headTable = getHeadTable();
        if ( headTable && getDataTable() ) {
	        preProcessHeadTable(headTable);
	        initDragTable(headTable);
        }
    }
}

function do_FixTableLayout() {
    var headProcessed = false;
    var headTable = getHeadTable();
    if (headTable) {
        var rows = headTable.rows;
        if (rows) {
            var rowCount = rows.length;
            if (rowCount == 1) {
                var tr = rows[0];
                if (tr) {
                    setTableLayoutToFixed(headTable);
                    headProcessed = true;
                }
            }
        }
    }
    if (headProcessed) {
        var dataTable = getDataTable();
        setTableLayoutToFixed(dataTable);
    }
    return headProcessed;
}


function setTableLayoutToFixed(tableObj) {
    if (!tableObj) return;
    var headerTr = tableObj.rows[0];
    if (!headerTr) {
        return;
    }
//        var i = 0;
//        for (i = 0; i < headerTr.cells.length; i++) {
//            headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
//        }
//        for (i = 0; i < headerTr.cells.length; i++) {
//            headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
//        }
//        tableObj.style.tableLayout = 'fixed';
    tableObj.style.tableLayout = 'fixed';
    tableObj.style.wordWrap = "break-word";
    tableObj.style.wordBreak = "break-all";
}

function preProcessHeadTable(headTable) { 
    do_ProcessHeadTableSpan(headTable);
    //表格%转化为实际数字
    initTableWidthToNumber( headTable );
    initTableWidthToNumber( getDataTable() );
    do_ProcessHeadTableSort(headTable);
}

function do_ProcessHeadTableSpan(headTable) {
    var row = headTable.rows[0];
    if (row) {
        row.style.height = "23px";
        row.height = "23px";
        var cells = row.cells;
        if (cells) {
            var cellCount = cells.length;
            for (var i = 0; i < cellCount; i++) {
                var cell = cells[i];
                if (hasChkObj(cell)) {
                    continue;
                }
                if (hasSpanObj(cell)) {
                    continue;
                }
                cell.innerHTML = "<span class=\"resizeDivClass\"></span>" + cell.innerHTML;
            }
        }
    }
}

function do_ProcessHeadTableSort(headTable) {
    var row = headTable.rows[0];
    if (row) {
        var cells = row.cells;
        if (cells) {
            var cellCount = cells.length;
            for (var i = 0; i < cellCount; i++) {
                var cell = cells[i];
                if (hasChkObj(cell)) {
                    continue;
                }
                cell.attachEvent("onclick", do_SortTable);
                cell.title = cell.title + "点击可对该列排序";
                cell.style.cursor = "pointer";
            }
        }
    }
}

function hasChkObj(cell) {
    var cellHTML = cell.innerHTML.toLowerCase();
    return ((cellHTML.indexOf("checkbox") > -1) || (cellHTML.indexOf("radio") > -1));
}

function hasSpanObj(cell) {
    var cellHTML = cell.innerHTML.toLowerCase();
    return (cellHTML.indexOf("span") > -1);
}

function getHeadTable() {
    var headTable = document.getElementById("headTable");
    var headDiv = document.getElementById("headDiv");
    if (!headTable && headDiv) {
        headTable = getChildTable(headDiv);
    }
    return headTable;
}

function getDataTable() {
    var dataTable = document.getElementById("dataTable");
    var dataDiv = document.getElementById("dataDiv");
    if (!dataTable && dataDiv) {
        dataTable = getChildTable(dataDiv);
    }
    return dataTable;
}

function getChildTable(obj) {
    var childTable = null;
    if (obj) {
        var nodes = obj.childNodes;
        if (nodes && nodes.length) {
            var nodeCount = nodes.length;
            for (var i = 0; i < nodeCount; i++) {
                if (nodes[i].tagName == "TABLE") {
                    childTable = nodes[i];
                    break;
                } else {
                    childTable = getChildTable(nodes[i]);
                }
            }
        }
    }
    return childTable;
}


function getCellValue(ob){
    if (ob.textContent != null){
        return ob.textContent;
    }
    var returnValue = "";
    var childNodes = ob.childNodes;
    var nodeCount = childNodes.length;
    if(nodeCount > 0){
        for(var i = 0; i < nodeCount; i++){
            var node = childNodes[i];
            var nodeHtml = node.innerHTML;
            if(nodeHtml && nodeHtml.indexOf("SELECT") > -1){
                returnValue += node.value;
            } else {
                returnValue += getSingleNodeValue(node);
            }
        }
    } else {
        returnValue = ob.innerText;
    }
    return returnValue;
}

function getSingleNodeValue(node) {
    var nodeValue = "";
    var nodeType = node.nodeType;
    switch (nodeType) {
        case 1:{
            var type = node.nodeType;
            if (type == "checkbox" || type == "radio") {
                if (node.checked) {
                    nodeValue = node.value;
                }
            } else {
                nodeValue = node.value;
            }
            break;
        }
        case 3:
            nodeValue = node.nodeValue;
            break;
        default:
            nodeValue = node.innerHTML;
    }
    return nodeValue;
}

var isSort = true;
var oldTD = null;

function do_SortTable() {
    if(!isSort) {
        isSort = true;
        return;
    }
    var table = getDataTable();
    if (table) {
        var i;
        var clickTD = event.srcElement;
        while(clickTD.tagName != "TD"){
            clickTD = clickTD.parentNode;
        }
        var cellIndex = clickTD.cellIndex;
        var tbody = table.tBodies[0];
        var colRows = tbody.rows;
        var aTrs = new Array();
        //将得到的列放入数组，备用
        for (i = 0; i < colRows.length; i++) {
            aTrs[i] = colRows[i];
        }
        //判断上一次排列的列和现在需要排列的是否同一个。
        if (table.sortCol == cellIndex) {
            aTrs.reverse();
        } else {
            //如果不是同一列，使用数组的sort方法，传进排序函数
            aTrs.sort(compareEle(cellIndex));
        }
        var oFragment = document.createDocumentFragment();
        for (i = 0; i < aTrs.length; i++) {
            oFragment.appendChild(aTrs[i]);
        }
        tbody.appendChild(oFragment);
        //记录最后一次排序的列索引
        table.sortCol = cellIndex;
        var innerHTML = clickTD.innerHTML;
        if(innerHTML.indexOf(sortDesc) > -1){
            innerHTML = innerHTML.replace(sortDesc, sortAsc);
        } else if(innerHTML.indexOf(sortAsc) > -1){
            innerHTML = innerHTML.replace(sortAsc, sortDesc);
        } else {
            innerHTML += sortAsc;
//            clickTD.style.width = clickTD.offsetWidth + 20;
//            do_ExpendDataTdWidth(cellIndex);
        }
        clickTD.innerHTML = innerHTML;
        if(oldTD != null && oldTD != clickTD)
            oldTD.innerHTML = oldTD.innerHTML.replace(sortAsc, "").replace(sortDesc, "");
        oldTD = clickTD;
    }
}

function do_ExpendDataTdWidth(cellIndex){
    var table = getDataTable();
    var colRows = table.rows;
    var rowCount = colRows.length;
    for(var i = 0; i < rowCount; i++){
        var row = colRows[i];
        var cell = row.cells[cellIndex];
        cell.style.width = cell.offsetWidth + 20;
    }
}

//将列的类型转化成相应的可以排列的数据类型
function convert(sValue) {
    var returnValue = null;
    if(isInt(sValue)){
        returnValue = parseInt(sValue);
    } else if(isNumber(sValue)){
        returnValue = parseFloat(sValue);
    } else {
        returnValue = sValue.toString();
    }
    return returnValue;
}

//排序函数，iCol表示列索引
function compareEle(iCol) {
    return  function (oTR1, oTR2) {
        var vValue1 = convert(getCellValue(oTR1.cells[iCol]));
        var vValue2 = convert(getCellValue(oTR2.cells[iCol]));
        if (vValue1 < vValue2) {
            return -1;
        } else if (vValue1 > vValue2) {
            return 1;
        } else {
            return 0;
        }
    };
}


var dragSize = false;
var tbody, headRow;
var bDragMode = false;
var objDragItem;
var arrHitTest = new Array();
var iArrayHit = -1;
var ColumnCount = null;
var mustRefresh = false;
var dragColor = null;
var hitColor = null;
var HasTopMostPager = null;
var PowerTable = null;
var defaultTitleColor = null;
var dragTime = null;
//

function initDragTable(table) {
    PowerTable = table;
    // get THEAD - take the unique THEAD for the table
    var click = PowerTable.childNodes[0];
    if (!click) {
        return;
    }
    var dataTable = getDataTable();
    if (!dataTable) {
        return;
    }
    tbody = dataTable.childNodes[0];
    if (!tbody) {
        return;
    }
    var i;
    if (dragColor == null) {
        dragColor = "white";
    }
    if (hitColor == null) {
        hitColor = "lightblue";
    }
    // Determine the row to use (read from HasPager)
    if (HasTopMostPager == "true") {
        headRow = click.childNodes[1];
    } else {
        headRow = click.childNodes[0];
    }
    if (headRow.tagName != "TR") {
        return;
    }
    headRow.runtimeStyle.cursor = "pointer"; //"move";
    ColumnCount = headRow.childNodes.length;
    for (i = 0; i < ColumnCount; i++) {
        arrHitTest[i] = new Array();
    }
    var cx = 0;
    var cy = 0;
    var c;
    defaultTitleColor = headRow.childNodes[0].currentStyle.backgroundColor;
    for (i = 0; i < ColumnCount; i++) {
        var clickCell = headRow.childNodes[i];
        clickCell.selectIndex = i;
        c = clickCell.offsetParent;
        if (cx == 0 && cy == 0) {
            while (c.offsetParent != null) {
                cy += c.offsetTop;
                cx += c.offsetLeft;
                c = c.offsetParent;
            }
        }
        arrHitTest[i][0] = cx + clickCell.offsetLeft;
        arrHitTest[i][1] = cy + clickCell.offsetTop;
        arrHitTest[i][2] = clickCell;
        arrHitTest[i][3] = cx + clickCell.offsetLeft + clickCell.clientWidth;
        clickCell.attachEvent("onmousedown", onMouseDown);
    }
    PowerTable.attachEvent("onmousemove", onMouseMove);
    PowerTable.attachEvent("onmouseup", onMouseUp);

    if (arrHitTest[0][0] == arrHitTest[0][3]) {
        mustRefresh = true;
    }
}
// Init function.. Fills out variables with data
// loaded with oncontentready.

//将table初始化
function InitHeader() {
    var cx = 0;
    var cy = 0;
    var c;
    for (var i = 0; i < ColumnCount; i++) {
        var clickCell = headRow.childNodes[i];
        clickCell.selectIndex = i;
        c = clickCell.offsetParent;
        if (cx == 0 && cy == 0) {
            while (c.offsetParent != null) {
                cy += c.offsetTop;
                cx += c.offsetLeft;
                c = c.offsetParent;
            }
        }
        arrHitTest[i][0] = cx + clickCell.offsetLeft;
        arrHitTest[i][1] = cy + clickCell.offsetTop;
        arrHitTest[i][2] = clickCell;
        arrHitTest[i][3] = cx + clickCell.offsetLeft + clickCell.clientWidth;
    }
} 

function ChangeHeader(iChange) {
    for (var y = 0; y < arrHitTest.length; y++) {
        if (arrHitTest[y][2].currentStyle.backgroundColor == hitColor) {
            arrHitTest[y][2].style.backgroundColor = defaultTitleColor;
        }
    }
    if (iChange == -1) {
        return;
    }
    arrHitTest[iChange][2].style.backgroundColor = hitColor;
}

function onMouseUp(e) {
    if (!bDragMode) {
        return;
    }
    bDragMode = false;
    var iSelected = objDragItem.selectIndex;
    objDragItem.releaseCapture();
    objDragItem.removeNode(true);
    objDragItem = null;
    ChangeHeader(-1);
    if ((iArrayHit - 1) < 0 || iSelected < 0) {
        return; // default failure
    }
    // iSelected is the 0-based index of the column being moved
    // (iArrayHit-1) is the 0-based index of the column being replaced
    CopyRow(iSelected, (iArrayHit - 1));
    // Reset our variables
    iSelected = 0;
    iArrayHit = -1;
    stopBubble(e);
}

function onMouseDown(e) {
    if (dragSize) {
        dragSize = false;
        isSort = false;
        return;
    }
    // If the grid is contained in an invisible panel (other DHTML stuff)
    // the initialization step must be repeated to take real values
    if (mustRefresh) {
        InitHeader();
        mustRefresh = false;
    }
    bDragMode = true;
    var src = e.srcElement;
    var c = e.srcElement;
    while (src.tagName != "TD") {
        src = src.parentNode;
    }
    // Create our header on the fly
    objDragItem = document.createElement("DIV");
    objDragItem.innerHTML = src.innerHTML;
    objDragItem.style.height = src.offsetParent.clientHeight;
    objDragItem.style.width = src.clientWidth;
    objDragItem.style.background = dragColor;
    objDragItem.style.fontColor = src.currentStyle.fontColor;
    objDragItem.style.position = "absolute";
    objDragItem.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=75)";
    objDragItem.selectIndex = src.selectIndex;
    objDragItem.style.border = "1 solid black";
    while (c.offsetParent != null) {
        objDragItem.style.y += c.offsetTop;
        objDragItem.style.x += c.offsetLeft;
        c = c.offsetParent;
    }
    objDragItem.style.borderStyle = "dashed";
    objDragItem.style.borderWidth = "1px";
    objDragItem.style.display = "none";
    src.insertBefore(objDragItem);
    objDragItem.setCapture();
    stopBubble(e);
    dragTime = (new Date()).getTime();
}

function onMouseMove(e) {
    if (!bDragMode || !objDragItem) {
        return;
    }

    if(((new Date()).getTime() - dragTime) < 200)
        return;
    // If we aren''t dragging or our object is null, we return

    // Hardcoded value for height difference
    var midWObj = objDragItem.style.posWidth / 2;
    var midHObj = 12;
    // Save mouse''s position in the document
    var intTop = e.clientY + document.body.scrollTop;
    var scrollLeft = 0 ;
    if( document.getElementById('headDiv') ){
    	scrollLeft = document.getElementById('headDiv').scrollLeft;
    }
    var intLeft = e.clientX + document.body.scrollLeft + scrollLeft;
    
    //var intLeft = e.clientX + document.body.scrollLeft;
    var cx = 0,cy = 0;
    var elCurrent = objDragItem.offsetParent;
    if (elCurrent != null) {
        while (elCurrent.offsetParent != null) {
            cx += elCurrent.offsetTop;
            cy += elCurrent.offsetLeft;
            elCurrent = elCurrent.offsetParent;
        }
    }
    objDragItem.style.pixelTop = intTop - cx - midHObj;
    objDragItem.style.pixelLeft = intLeft - cy - midWObj;
    if (objDragItem.style.display == "none") {
        objDragItem.style.display = "";
    }
    iArrayHit = CheckHit(intTop, intLeft, e);
    stopBubble(e);
    e.cancelBubble = false;
    e.returnValue = false;
}

function CheckHit(x, y, e) {
    var midWObj = objDragItem.style.posWidth / 2;
    var midHObj = 12;
    for (var i = 0; i < ColumnCount; i++) {
        if( (y) > (arrHitTest[i][0]) && (y) < (arrHitTest[i][3] ) && (x) >= (arrHitTest[i][1]) && (x) <= (arrHitTest[i][1] + arrHitTest[i][2].clientHeight) ) {
            ChangeHeader(i);
            return i + 1;
        } else {
            ChangeHeader(-1);
        }
    }
    return -1;
}

//
// Copy from row to row.. Does the Header also.
//

function CopyRow(from, to) {
    if (from == to) {
        return;
    }
    var origfrom = from;
    var origto = to;
    var iDiff = 0;
    var saveObj = null;
    var saveObjWidth = null;
    var saveWidth = null;
    
    var fromWidth = null;
    
    if (from > to) {
        iDiff = from - to;
        saveObj = headRow.childNodes[from].innerHTML;
        saveWidth = headRow.childNodes[from].style.width;
        
        fromWidth = headRow.childNodes[from].width;
        
        for (var i = 0; i < iDiff; i++) {
            headRow.childNodes[from].innerHTML = headRow.childNodes[from - 1].innerHTML;
            headRow.childNodes[from].style.width = headRow.childNodes[from - 1].style.width;
            headRow.childNodes[from].width = headRow.childNodes[from - 1].width;
            from--;
        }
        headRow.childNodes[to].innerHTML = saveObj;
        headRow.childNodes[to].style.width = saveWidth;
        headRow.childNodes[to].width = fromWidth;
        
    } else {
        iDiff = to - from;
        saveObj = headRow.childNodes[from].innerHTML;
        
        fromWidth = headRow.childNodes[from].width;
        
        saveWidth = headRow.childNodes[from].style.width;
        for (var i = 0; i < iDiff; i++) {
            headRow.childNodes[from].innerHTML = headRow.childNodes[from + 1].innerHTML;
            headRow.childNodes[from].style.width = headRow.childNodes[from + 1].style.width;
            headRow.childNodes[from].width = headRow.childNodes[from + 1].width;
            from++;
        }
        headRow.childNodes[to].innerHTML = saveObj;
        headRow.childNodes[to].style.width = saveWidth;
        headRow.childNodes[to].width = fromWidth;
    }

    for (var i = 0; i < headRow.childNodes.length; i++) {
        headRow.childNodes[i].selectIndex = i;
    }
    InitHeader();
    for (var iRowInsert = 0; iRowInsert < tbody.rows.length; iRowInsert++) {
        from = origfrom;
        to = origto;
        if (from > to) {
            iDiff = from - to;
            try {
                saveObj = tbody.rows[iRowInsert].cells[from].innerHTML;
                saveObjWidth = tbody.rows[iRowInsert].cells[from].style.width;
                fromWidth = tbody.rows[iRowInsert].cells[from].width;
            } catch(e) {
                saveObj = null;
            }
            for (var i = 0; i < iDiff; i++) {
                try {
                    tbody.rows[iRowInsert].cells[from].innerHTML = tbody.rows[iRowInsert].cells[from - 1].innerHTML;
                    tbody.rows[iRowInsert].cells[from].style.width = tbody.rows[iRowInsert].cells[from - 1].style.width;
                    tbody.rows[iRowInsert].cells[from].width = tbody.rows[iRowInsert].cells[from - 1].width;
                    from--;
                } catch(e) {
                }
            }
            if (saveObj != null) {
                tbody.rows[iRowInsert].cells[to].innerHTML = saveObj;
            }
            tbody.rows[iRowInsert].cells[to].style.width = saveObjWidth;
            tbody.rows[iRowInsert].cells[to].width = fromWidth;
        } else {
            iDiff = to - from;
            try {
                saveObj = tbody.rows[iRowInsert].cells[from].innerHTML;
                saveObjWidth = tbody.rows[iRowInsert].cells[from].style.width;
                fromWidth = tbody.rows[iRowInsert].cells[from].width;
            } catch(e) {
            }
            for (var i = 0; i < iDiff; i++) {
                try {
                    tbody.rows[iRowInsert].cells[from].innerHTML = tbody.rows[iRowInsert].cells[from + 1].innerHTML;
                    tbody.rows[iRowInsert].cells[from].style.width = tbody.rows[iRowInsert].cells[from + 1].style.width;
                    tbody.rows[iRowInsert].cells[from].width = tbody.rows[iRowInsert].cells[from + 1].width;
                    from++;
                } catch(e) {
                }
            }
            try {
                if(saveObj != null) {
                    tbody.rows[iRowInsert].cells[to].innerHTML = saveObj;
                }
                tbody.rows[iRowInsert].cells[to].style.width = saveObjWidth;
                tbody.rows[iRowInsert].cells[to].width = fromWidth;
            } catch(e) {
            }
        }
        for (var i = 0; i < headRow.childNodes.length; i++) {
            var td = headRow.childNodes[i];
            var tmp = td.innerHTML;
            var pos = tmp.indexOf("<");
            if (pos > 0) {
                tmp = tmp.substring(0, pos);
            } else {
                if (pos == 0) {
                    tmp = td.innerText;
                }
            }
            buf += tmp;
            if (i < headRow.childNodes.length - 1) {
                buf += ",";
            }
        }
    }
    var buf = "";
}

function stopBubble(e) {
    //一般用在鼠标或键盘事件上
    if (e && e.stopPropagation) {
        //W3C取消冒泡事件
        e.stopPropagation();
    } else {
        //IE取消冒泡事件
        window.event.cancelBubble = true;
    }
}

HintProcessor={
    show:function(tag){
        if(!HintProcessor.area){
            HintProcessor.createArea();
        }
        var msg=tag.options[tag.selectedIndex].text;
        var o=tag,po,t=0,l=0,m=10,h=tag.offsetHeight,w=tag.offsetWidth;
        do{
            t+=o.offsetTop||0;
            l+=o.offsetLeft||0;
            po=o.offsetParent;
            if(po==document.body)break;
            o=po;
        }while(true);
        HintProcessor.area.style.top=t+h+m;
        HintProcessor.area.style.left=l;
        HintProcessor.area.innerHTML=msg;
        HintProcessor.area.style.display='block';
    },
    hidd:function(){
        HintProcessor.area.style.display='none';
    },
    createArea:function(){
        var w=350;
        var h=50;
        var _area;
        _area=document.createElement('DIV');
        _area.style.width=w+'px';
        _area.style.height=h+'px';
        _area.style.position='absolute';
        _area.zIndex='10';
        _area.style.display='none';
        _area.style.border='1px solid #ff9900';
        _area.style.backgroundColor = '#eeeeee';
        document.body.appendChild(_area);
        HintProcessor.area=_area;
    }
};

function getTableAllTdPercentWidth( tableObj ){
	var tableAllParam = new Object();
	var tbwidth = tableObj.offsetWidth ;
	var percentAll = 0 ;
	
	var tbody = tableObj.tBodies[0];
    var colRows = tbody.rows;
	var cellSize = colRows[0].cells.length;
	 
	for( j = 0; j < cellSize; j++) {
    	tdWidth = colRows[0].cells[j].width ;
    	var pos = tdWidth.indexOf("%" );
    	if( pos > -1 ){ 
    		percentAll += Number( tdWidth.substring(0, pos ) );
    		//tdWidth = Number( tbwidth ) * Number( tdWidth.substring(0, pos ) )/100 ;
    		//colRows[i].cells[j].width = tdWidth;
    	}else{
    		tbwidth -= Number( tdWidth );
   		}
    }
    
    tableAllParam.percentAll = percentAll;
    tableAllParam.tbwidth = tbwidth;
    return tableAllParam;
}

//将表格宽度中%号转化为数字
function initTableWidthToNumber( tableObj ){
    var tableWidthParam ;
	var allPercent = 0 ;
	var tbwidth = 0  ;
	if( tableObj && tableObj.offsetWidth ) {
		tableWidthParam = getTableAllTdPercentWidth( tableObj );
		tbwidth = tableWidthParam.tbwidth ;
		allPercent = tableWidthParam.percentAll;
		
		if(allPercent > 100 ){
			//alert( "请检查该表格的列的百分比，总计已经超过100%了，为" + allPercent );
		}
		//alert( tbwidth );
		var tbody = tableObj.tBodies[0];
	    var colRows = tbody.rows;
	    
	    if( colRows.length > 0 ){
		    var cellSize = colRows[0].cells.length;
		    var j ;
		    var tdWidth = 0 ;
		    for (i = 0; i < colRows.length; i++) {
		        for( j = 0; j < cellSize; j++) {
		        	tdWidth = colRows[i].cells[j].width ;
		        	var pos = tdWidth.indexOf("%" );
		        	if( pos > -1 ){
		        		tdWidth = Number( tbwidth ) * Number( tdWidth.substring(0, pos ) )/allPercent ;
		        		colRows[i].cells[j].width = tdWidth;
		        	}
		        }
		    }
	    }
	 }
}

function CalendarPostProcessor(postProcessorName){

    this.postProcessorName = postProcessorName;

    this.setArgumentType=function(argumentType){
        this.argumentType = argumentType;
    };
}


/**
 *  流程提交 
 */
function do_Complete_app_yy() {
	if(true){
        try{
            var actObj = document.getElementById("act");
			actObj.value = "SUBMIT_ACTION";
			document.forms[0].submit();
			document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
		}catch(ex){
		}finally{
		}
	}
}

/** 模块通用，申请创建流程不用这个方法  **/
function do_Save_app() {
	if( true ){
		try{
			var actObj = document.getElementById("act");
			actObj.value = "SAVE_ACTION";
			document.forms[0].submit();
			document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
		}catch(ex){
		}finally{
		}
	}
}

/**
 *  附件 -- 根据主键
 */
function setAttachmentConfig(){
    if( document.getElementById("transId") ){
    	var attachmentConfig = new AttachmentConfig();
    	attachmentConfig.setOrderPkName("transId");
    	return attachmentConfig;
    }else{
    	return null;
    }
}