/**
 * 功能：将从Excel复制的数据粘贴到数据表格对象dataTable
 * headerTable 标题表格对象(其单元格文字描述与Excel单元格描述一致)
 * dataTable 数据表格对象
 * uniqueField 唯一性字段。如果该字段在Excel中的数据出现重复行，则重复数据会被过滤。
 * 作者：唐明胜
 */
function pasteData(headerTable, dataTable, uniqueField){
	var text = window.clipboardData.getData("text");
	if (text == null || text == "") {
		alert("请先在EXCEL摸板里复制包括标题行在内的数据。然后再粘贴！");
		return;
	}
	if(!headerTable){
		alert("没有指定标题表格对象！");
		return;
	}
	if(!dataTable){
		alert("没有指定数据表格对象！");
		return;
	}
	var rows = text.split('\n');

	var dataDescs = getDataDescs(headerTable);
	var fieldNames = getFieldNames(dataTable);
	var excelFields = getXLSFields(rows[0]);

	var rowCount = rows.length;
	var columnCount = excelFields.length;
	var index = -1;
//    alert("rowCount = " + rowCount);
    for(var i = 1; i < rowCount; i++){
		var rowData = rows[i];
//        alert("rowData = " + rowData);
        if(rowData == ""){
			continue;
		}
		var cols = rowData.split(String.fromCharCode(9));
		if(cols.length != columnCount){
			alert("复制的数据不合法，不能粘贴");
			return;
		}
		var valueDTO = new Object();
		for(var j = 0; j < columnCount; j++){
			index = findIndexOfArr(dataDescs, excelFields[j]);
//            alert("index = " + index);
            if(index != -1){
				valueDTO[fieldNames[index]] = cols[j];
			}
		}
        if(document.getElementById(uniqueField)){
            appendDTO2Table(dataTable, valueDTO, false, uniqueField);
        } else {
            appendDTORow(dataTable, valueDTO, false);
        }
    }
}

function getXLSFields(rowData){
	var excelFields = new Array();
	var colData = "";
	var cols = rowData.split(String.fromCharCode(9));
	var columnCount = cols.length;
	for(var i = 0; i < columnCount; i++){
		colData = cols[i];
		if(i == columnCount - 1){
			colData = colData.replace("\n", "");
			colData = colData.replace("\r", "");
		}
		excelFields[i] = colData;
    }
	return excelFields;
}

function getDataDescs(headerTable){
	var dataDescs = new Array();
	var rows = headerTable.rows;
	var rowCount = rows.length;
	var fieldRow = rows[rowCount - 1];
	var children = fieldRow.childNodes;
	var child = null;
	for(var i = 0; i < children.length; i++){
		child = children[i];
		dataDescs[i] = child.innerText;
    }
	return dataDescs;
}


function getFieldNames(dataTable){
	var fieldNames = new Array();
	var rows = dataTable.rows;
	var rowCount = rows.length;
	var fieldRow = rows[rowCount - 1];
	var children = fieldRow.childNodes;
	var child = null;
	var elementName = null;
	for(var i = 0; i < children.length; i++){
		child = children[i];
		var tagName = child.tagName;
		if(tagName.toUpperCase() != "TD"){
			continue;
		}
		elementName = child.name;
		if(elementName){//表格本身有名称
			fieldNames[i] = elementName;
		} else {//否则检查表格是否有表单域
			var fieldNodes = child.childNodes;
			for(var j = 0; j < fieldNodes.length; j++){//单元格内表单域循环
				var node = fieldNodes[j];
				elementName = node.name;
				if(elementName){
					fieldNames[i] = elementName;
				}
			}
		}
    }
	return fieldNames;
}