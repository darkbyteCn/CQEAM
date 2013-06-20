function MouseDownToResize(obj) {
    setTableLayoutToFixed("headTable");
    setTableLayoutToFixed("dataTable");
    obj.mouseDownX = event.clientX;
    obj.pareneTdW = obj.parentElement.offsetWidth;
    var objTable = document.getElementById("dataTable");
    if (objTable) {
        obj.pareneTableW = objTable.offsetWidth;
    }
    obj.setCapture();
}

function MouseMoveToResize(obj) {
    if (!obj.mouseDownX) {
        return;
    }
    var newWidth = obj.pareneTdW + event.clientX - obj.mouseDownX;
    if (newWidth > 10) {
        obj.parentElement.style.width = newWidth;
        var headerTable = document.getElementById("headTable");
        headerTable.style.width = newWidth;
        var objTable = document.getElementById("dataTable")
        if (objTable) {
            objTable.style.width = newWidth;
            objTable.cells[obj.parentElement.cellIndex].style.width = newWidth;
        }
    }
}

function MouseUpToResize(obj) {
    obj.releaseCapture();
    obj.mouseDownX = 0;
}

function setTableLayoutToFixed(tableName) {
    var theObjTable = document.getElementById(tableName);
    if (!theObjTable) return;
//    if(theObjTable.style && theObjTable.style.tableLayout=='fixed') {
//        return;
//    }
    var headerTr = theObjTable.rows[0];
    for (var i = 0; i < headerTr.cells.length; i++) {
        headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
    }

    for (var i = 0; i < headerTr.cells.length; i++) {
        headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
    }
    theObjTable.style.tableLayout = 'fixed';
}
