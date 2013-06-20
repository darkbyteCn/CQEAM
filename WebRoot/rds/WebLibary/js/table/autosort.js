/**
 * 功能：单击表格中指定的列，使表格中的数据按该列排序
 * tableId：要排序的表格ID
 * startRowIndex：表格中开始排序的起始行号（1为起点）
 * columnIndex：表格中要排序的列号（1为起点）
 */
function autoSort(tableId, startRowIndex, columnIndex) {
    var ascent;
    var targetRow;
    var fromRow;
    startRowIndex = startRowIndex - 1;
    columnIndex = columnIndex - 1;
    var table = document.getElementById(tableId);
    var rowCount = table.childNodes[0].childNodes.length;
    if(rowCount > 0){
	    var rows = table.childNodes[0].childNodes;
	    if (getCellValue(rows[0].cells[columnIndex]) > getCellValue(rows[rowCount - 1].cells[columnIndex])) {
	        ascent = true;
	    } else {
	        ascent = false;
	    }
	    if (ascent) {
	        for (var i = startRowIndex; i < rowCount; i++) {
	            for (var j = 0; j < rowCount - i - 1; j++) {
	                targetRow = rows[j + 1];
	                fromRow = rows[j];
	                if (getCellValue(fromRow.cells[columnIndex]) > getCellValue(targetRow.cells[columnIndex])) {
	                    fromRow.swapNode(targetRow)
	                }
	            }
	        }
	    } else {
	        for (var i = startRowIndex; i < rowCount; i++) {
	            for (var j = 0; j < rowCount - i - 1; j++) {
	                targetRow = rows[j + 1];
	                fromRow = rows[j];
	                if (getCellValue(fromRow.cells[columnIndex]) < getCellValue(targetRow.cells[columnIndex])) {
	                    fromRow.swapNode(targetRow)
	                }
	            }
	        }
	    }
  	}
}
 /**
  * 功能：得到表格中指定列的值
  * cellObj：表格中的列对象
  */
function getCellValue(cellObj) {
    var cellValue = '';
    if (cellObj.childNodes.length > 0) {
        if (typeof(cellObj.childNodes[0].value) == undefined) {
            cellValue = cellObj.innerText;
        }
    }
    return cellValue;
}
