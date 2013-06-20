/**
 * @author mshtang
 */

//===============================================================================================
//第四部分：以下处理下拉框操作
//===============================================================================================
/**
 * 功能：将某个select组件下的所有元素复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function copyAllOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, false, true);
}

/**
 * 功能：以对象的形式进行复制操作，将第一个select组件下的所有元素复制到第二个select组件中
 * fromSelect：源select组件
 * toSelect：目标select组件
 */
function copyObjOptions(fromObj, toObj) {
    if (fromObj.hasChildNodes()) {
        var option = null;
        for (var i = 0; i < fromObj.length; i++) {
            option = new Option(fromObj.options[i].text, fromObj.options[i].value);
            if (haveChild(toObj, option)) {
                alert("该选项已经存在，不允许重复添加！");
                continue;
            }
            toObj.options[toObj.length] = option;
        }
    }
}
/**
 * 功能：将某个select组件下的选中元素复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function copySelectedOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, true, true);
}

function copySelectedOptionNm(fromSelect, toSelect) {
    processOptionNm(fromSelect, toSelect, true, true);
}

/**
 * 功能：将某个select组件下的所有元素移动到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function moveAllOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, false, false);
}

/**
 * 功能：将某个select组件下的选中元素移动到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function moveSelectedOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, true, false);
}

/**
 *【注】：本函数是为上述四个函数而写的，一般调用时不需要调用本函数，调用上面的函数即可
 * 功能：将某个select组件下的元素移动或者复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 * allOrSelected：true表示选中元素，false表示所有元素
 * copyOrMove：true表示复制元素，false表示移动元素
 */
function processOption(fromSelect, toSelect, allOrSelected, copyOrMove) {
    var selects = document.getElementsByTagName("select");
    var fromObj = null;
    var toObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == fromSelect) {
            fromObj = selects[i];
        } else if (selects[i].name == toSelect) {
            toObj = selects[i];
        }
        if (fromObj != null && toObj != null) {
            break;
        }
    }
    if (fromObj.hasChildNodes()) {
        var option = null;
        for (var i = 0; i < fromObj.length; i++) {
            if (allOrSelected && !fromObj.options[i].selected) {
                continue;
            }
            option = new Option(fromObj.options[i].text, fromObj.options[i].value);
            if (haveChild(toObj, option)) {
                alert("该选项已经存在，不允许重复添加！");
                return;
            }
            if (!copyOrMove) {
                fromObj.remove(i);
                i--;
            }
            toObj.options[toObj.length] = option;
        }
    }
}
/**【注】与上面processOption方法的区别在于重复元素直接跳过不拷贝且不提示该元素已经存在*/
function processOptionNm(fromSelect, toSelect, allOrSelected, copyOrMove) {
    var selects = document.getElementsByTagName("select");
    var fromObj = null;
    var toObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == fromSelect) {
            fromObj = selects[i];
        } else if (selects[i].name == toSelect) {
            toObj = selects[i];
        }
        if (fromObj != null && toObj != null) {
            break;
        }
    }
    if (fromObj.hasChildNodes()) {
        var option = null;
        for (var i = 0; i < fromObj.length; i++) {
            if (allOrSelected && !fromObj.options[i].selected) {
                continue;
            }
            option = new Option(fromObj.options[i].text, fromObj.options[i].value);
            /*
            if (haveChild(toObj, option)) {
                alert("该选项已经存在，不允许重复添加！");
                return;
            }
            */
            if (!copyOrMove) {
                fromObj.remove(i);
                i--;
            }
            if (!haveChild(toObj, option)) {
            	toObj.options[toObj.length] = option;
            }
        }
    }
}

/**
 * 功能：判断某个select组件是否含有某个Option选项
 * selObj：select组件对象
 * optObj：option组件对象
 */
function haveChild(selObj, optObj) {
    var retVal = false;
    var optValue = optObj.value;
    var optText = optObj.text;
    var opt = null;
    for (var i = 0; i < selObj.length; i++) {
        opt = selObj.item(i);
        if (opt.value == optValue && opt.text == optText) {
            retVal = true;
            break;
        }
    }
    return retVal;
}
/**
 * 功能：select中某指定选项的下标
 * select：select组件
 * code：待查找的选项值
 */
function indexInOptions(select, code) {
    var selObj = select;
    for (var i = 0; i < selObj.length; i++) {
        if (selObj.options[i].value == code) {
            return i;
        }
    }
    return -1;
}
/**
 * 功能：删除某个select组件下的元素
 * select：select组件名
 * allOrSelected：true表示选中元素，false表示所有元素
 */
function dropOption(selectName, allOrSelected) {
    var selObj = document.getElementById(selectName);
    if (selObj) {
        for (var i = 0; i < selObj.length; i++) {
            if (allOrSelected) {
                if (selObj.options[i].selected) {
                    selObj.remove(i);
                    i--;
                }
            } else {
                selObj.remove(i);
                i--;
            }
        }
    }

}

/**
 * 功能：删除某个select组件下的选中元素
 * selectName：select组件名
 */
function dropSelectedOption(selectName) {
    dropOption(selectName, true);
}
/**
 * 功能：删除某个select组件下的所有元素
 * selectName：select组件名
 */
function dropAllOption(selectName) {
    dropOption(selectName, true);
}
/**
 * 功能：清除下拉列表中对应值选项
 * selectName：select组件名
 * optionValue：特定项值
 */
function dropSpecialOption(selectName, optionValue) {
    var optionObj = document.all[selectName].options;
    var selectedValueArr = optionValue.split(";");
    if (selectedValueArr) {
        var valueCount = selectedValueArr.length;
        var tempValue = "";
        for (var i = 0; i < optionObj.length; i++) {
            tempValue = optionObj[i].value;
            for (var j = 0; j < valueCount; j++) {
                if (tempValue == selectedValueArr[j]) {
                    optionObj[i] = null;
                    i--;
                    break;
                }
            }
        }
    }
}
/**
 * 功能：清除下拉列表中对应值选项
 * selectName：select组件名
 * optionValue：特定项值
 */
function dropSpecialOption(select, optionValue) {
    var optionObj = select.options;
    var selectedValueArr = optionValue.split(";");
    if (selectedValueArr) {
        var valueCount = selectedValueArr.length;
        var tempValue = "";
        for (var i = 0; i < optionObj.length; i++) {
            tempValue = optionObj[i].value;
            for (var j = 0; j < valueCount; j++) {
                if (tempValue == selectedValueArr[j]) {
                    optionObj[i] = null;
                    i--;
                    break;
                }
            }
        }
    }
}
/**
 * 功能：将某个select组件下的所有选项都选中
 * selectName：select组件名
 */
function selectAll(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = true;
    }
}

/**
 * 功能：将某个select组件下的所有选项置于非选中状态
 * selectName：select组件名
 */
function unSelectAll(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = false;
    }
}

function unSelectAllById(selectId) {
    var select = document.getElementById(selectId);
    for (var i = 0; i < select.length; i++) {
        select.options[i].selected = false;
    }
}

/**
 * 功能：将某个select组件下的所有选项的选中状态置反
 * selectName：select组件名
 */
function reverseSeletStatus(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = !selObj.options[i].selected;
    }
}
/**
 * 功能：获取下拉列表中被选中项的值。应用于下拉框可多选和单选的场合。
 * selectName：select组件名
 * splitor：分隔符
 */

function getSelectValue(selectName, splitor) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    if(splitor == "undefined" || splitor == null){
		splitor = ";";
	}

    if (optionCount) {
        for (var i = 0; i < optionCount; i++) {
            if (optionObj[i].selected && !isEmpty(optionObj[i].value)) {
                retVal += optionObj[i].value + splitor;
            }
        }
        retVal = retVal.substring(0, retVal.length - splitor.length);
    }
    return retVal;
}
/**
 * 获得选中的项目数
 * shaffer
 */
function getSelectedCount(selectName) {
    var retVal = 0;
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    if (optionCount) {
        for (var i = 0; i < optionCount; i++) {
            if (optionObj[i].selected) {
                retVal++;
            }
        }
    }
    return retVal;
}
/**
 * 功能：获取下拉列表中被选中项的文本。应用于下拉框可多选和单选的场合。
 * selectName：select组件名
 * splitor：分隔符
 */
function getSelectText(selectName, splitor) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    splitor = (splitor == null) ? ";" : splitor;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].selected && !isEmpty(optionObj[i].value)) {
            retVal += optionObj[i].text + splitor;
        }
    }
    return (endsWith(retVal, splitor)) ? retVal.substring(0, retVal.length - splitor.length) : retVal;
}

/**
 * 功能：获取下拉列表中特定项的文本
 * selectName：select组件名
 * optionValue：特定项值
 */
function getSpecialOptionText(selectName, optionValue) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].value == optionValue) {
            retVal += optionObj[i].text;
            break;
        }
    }
    return retVal;
}
/**
 * 功能：使下拉列表中对应值处于选中状态
 * selectName：select组件名
 * optionValue：特定项值
 */
function selectSpecialOption(selectName, optionValue) {
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    var selectedValueArr = optionValue.split(";");
    var valueCount = selectedValueArr.length;
    var tempValue = "";
    for (var i = 0; i < optionCount; i++) {
        tempValue = optionObj[i].value;
        for (var j = 0; j < valueCount; j++) {
            if (tempValue == selectedValueArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}

function selectSpecialOptionByItem(select, optionValue) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var selectedValueArr = optionValue.split(";");
    var valueCount = selectedValueArr.length;
    var tempValue = "";
    for (var i = 0; i < optionCount; i++) {
        tempValue = optionObj[i].value;
        for (var j = 0; j < valueCount; j++) {
            if (tempValue == selectedValueArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}

/**
 * 功能：使下拉列表中对应Text的Option处于选中状态
 * selectName：select组件对象
 * optionValue：特定项值
 */
function makeOptionsSelected(select, optionText) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var selectedTextArr = optionText.split(";");
    var textCount = selectedTextArr.length;
    var tempText = "";
    for (var i = 0; i < optionCount; i++) {
        tempText = optionObj[i].text;
        for (var j = 0; j < textCount; j++) {
            if (tempText == selectedTextArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}
function makeOptionSelected(select, optionText) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var tempText = "";
    for (var i = 0; i < optionCount; i++) {
        tempText = optionObj[i].text;
        if (tempText == optionText) {
            optionObj[i].selected = true;
            break;
        }
    }
}

/**
 * 功能：使选中的项目上移
 * @param {Object} oSelect 源列表框
 * @param {Object} isToTop 是否移至选择项到顶端，其它依次下移。true为移动到顶端，false反之，默认为false
 */	
function moveUp(oSelect,isToTop){   
	if(!isToTop){//默认状态不是移动到顶端
		isToTop = false;
	}
    var strTempValue = "";
    var strTempText = "";
	if(oSelect.multiple){//如果是多选
		for(var i = 0; i < oSelect.options.length; i++){   
			if(isToTop){//如果设置了移动到顶端标志   
				if(oSelect.options[i].selected){   
					var transferIndex = i;
					while(transferIndex > 0 && !oSelect.options[transferIndex - 1].selected){
						strTempValue = oSelect.options.item(transferIndex).value;
						strTempText = oSelect.options.item(transferIndex).text;
				        oSelect.options.item(transferIndex).value = oSelect.options.item(transferIndex - 1).value;
				        oSelect.options.item(transferIndex).text = oSelect.options.item(transferIndex - 1).text;
				        oSelect.options.item(transferIndex - 1).value = strTempValue;
				        oSelect.options.item(transferIndex - 1).text = strTempText;
						oSelect.options.item(transferIndex - 1).selected = true;
						oSelect.options.item(transferIndex).selected = false;
						transferIndex--;   
					}
				}
			} else {//没有设置移动到顶端标志   
				if(oSelect.options[i].selected){   
					if(i > 0){   
						if(!oSelect.options[i - 1].selected){
							strTempValue = oSelect.options.item(i).value;
							strTempText = oSelect.options.item(i).text;
					        oSelect.options.item(i).value = oSelect.options.item(i - 1).value;
					        oSelect.options.item(i).text = oSelect.options.item(i - 1).text;
					        oSelect.options.item(i - 1).value= strTempValue;
					        oSelect.options.item(i - 1).text    = strTempText;
					        oSelect.options.item(i - 1).selected = true;
					        oSelect.options.item(i).selected = false;
						}
					}
				}
			}
		}
	} else {//如果是单选
		var i = oSelect.selectedIndex;   
		if(i <= 0){ 
			return;
		}
		if(isToTop){//如果设置了移动到顶端标志
			while(i > 0){   
				strTempValue = oSelect.options.item(i).value;
				strTempText = oSelect.options.item(i).text;
		        oSelect.options.item(i).value = oSelect.options.item(i - 1).value;
		        oSelect.options.item(i).text = oSelect.options.item(i - 1).text;
		        oSelect.options.item(i - 1).value = strTempValue;
		        oSelect.options.item(i - 1).text = strTempText;
		        oSelect.options.item(i - 1).selected = true;
		        oSelect.options.item(i).selected = false;
			}   
		} else{//没有设置移动到顶端标志
			strTempValue = oSelect.options.item(i).value;
			strTempText = oSelect.options.item(i).text;
	        oSelect.options.item(i).value    = oSelect.options.item(i - 1).value;
	        oSelect.options.item(i).text        = oSelect.options.item(i - 1).text;
	        oSelect.options.item(i - 1).value= strTempValue;
	        oSelect.options.item(i - 1).text    = strTempText;
	        oSelect.options.item(i - 1).selected = true;
	        oSelect.options.item(i).selected = false;
		}
	}   
}   

/**
 * 功能：使选中的项目下移
 * @param {Object} oSelect 源列表框
 * @param {Object} isToBottom 是否移至选择项到底端，其它依次上移。true为移动到底端，false反之，默认为false
 */	
  function moveDown(oSelect,isToBottom){   
	if(!isToBottom){//默认状态不是移动到底部
		isToBottom = false;   
	}
    var strTempValue = "";
    var strTempText = "";
	var selLength = oSelect.options.length - 1;
	if(oSelect.multiple){//如果是多选   
		for(var i = oSelect.options.length - 1; i >= 0; i--){   
			if(isToBottom){//如果设置了移动到顶端标志   
				if(oSelect.options[i].selected){   
					var transferIndex = i;   
					while(transferIndex < selLength && !oSelect.options[transferIndex + 1].selected){
						strTempValue = oSelect.options.item(transferIndex).value;
						strTempText = oSelect.options.item(transferIndex).text;
				        oSelect.options.item(transferIndex).value = oSelect.options.item(transferIndex + 1).value;
				        oSelect.options.item(transferIndex).text = oSelect.options.item(transferIndex + 1).text;
				        oSelect.options.item(transferIndex + 1).value = strTempValue;
				        oSelect.options.item(transferIndex + 1).text = strTempText;
						oSelect.options.item(transferIndex + 1).selected = true;
						oSelect.options.item(transferIndex).selected = false;
					
//						oSelect.options[transferIndex].swapNode(oSelect.options[transferIndex + 1]);   
						transferIndex++;
					}   
				}   
			} else {//没有设置移动到顶端标志
				if(oSelect.options[i].selected){  
					if(i < selLength){
						if(!oSelect.options[i + 1].selected){
							strTempValue = oSelect.options.item(i).value;
							strTempText = oSelect.options.item(i).text;
					        oSelect.options.item(i).value = oSelect.options.item(i + 1).value;
					        oSelect.options.item(i).text = oSelect.options.item(i + 1).text;
					        oSelect.options.item(i + 1).value= strTempValue;
					        oSelect.options.item(i + 1).text    = strTempText;
					        oSelect.options.item(i + 1).selected = true;
					        oSelect.options.item(i).selected = false;
						
//							oSelect.options[i].swapNode(oSelect.options[i + 1]);
						}
					}
				}
			}
		}   
	} else {//如果是单选   
		var i = oSelect.selectedIndex;   
		if(i >= selLength - 1){   
			return;
		}
		if(isToBottom) {//如果设置了移动到顶端标志   
			while(i < elLength - 1){   
//				oSelect.options[i].swapNode(oSelect.options[i + 1]);   
				strTempValue = oSelect.options.item(i).value;
				strTempText = oSelect.options.item(i).text;
		        oSelect.options.item(i).value = oSelect.options.item(i + 1).value;
		        oSelect.options.item(i).text = oSelect.options.item(i + 1).text;
		        oSelect.options.item(i + 1).value = strTempValue;
		        oSelect.options.item(i + 1).text = strTempText;
		        oSelect.options.item(i + 1).selected = true;
		        oSelect.options.item(i).selected = false;
				i++;   
			}   
		} else{//没有设置移动到顶端标志                   
			strTempValue = oSelect.options.item(i).value;
			strTempText = oSelect.options.item(i).text;
	        oSelect.options.item(i).value = oSelect.options.item(i + 1).value;
	        oSelect.options.item(i).text = oSelect.options.item(i + 1).text;
	        oSelect.options.item(i + 1).value = strTempValue;
	        oSelect.options.item(i + 1).text = strTempText;
	        oSelect.options.item(i + 1).selected = true;
	        oSelect.options.item(i).selected = false;
//			oSelect.options[i].swapNode(oSelect.options[i + 1]);
		}
	}   
}

function addText2Value(oSelect, splitor){
	if(!splitor){
		splitor = ";";
	}
	var optObjs = oSelect.options;
	var optObj = null;
	for(var i = 0; i < optObjs.length; i++){
		optObj = optObjs[i];
		if(optObj.selected){
			optObj.value = optObj.value + splitor + optObj.text;
		}
	}
}

/**
 * 功能：返回选中下拉列表的Text 与 value
 * @param select
 */	
function getSelected(obj){
	var arr = new Array();
   	var tp = obj.options;
   	for(var i=0;i<tp.length;i++){
   		if(tp[i].selected){
   			arr[arr.length] = new Array(tp[i].text,tp[i].value);
   		}
   	}
   	return arr;
}

/**
 * 功能：返回下拉列表所有的Text 与 value
 * @param obj
 */	
function selectedAll(obj){
	var arr = new Array();
	if(obj.length == 0){
		return arr;
	}
	var tp = obj.options;
   	for(var i=0;i<obj.length;i++){
   		arr[arr.length] = new Array(tp[i].text,tp[i].value);
   	}
   	return arr;
}

function do_ProcessOptionWidth(selectObj){
    var newSelectObj = document.createElement("select");
    newSelectObj = selectObj.cloneNode(true);
    newSelectObj.selectedIndex = selectObj.selectedIndex;
    newSelectObj.onmouseover = null;
    var e = selectObj;
    var absTop = e.offsetTop;
    var absLeft = e.offsetLeft;
    while(e = e.offsetParent){
        absTop += e.offsetTop;
        absLeft += e.offsetLeft;
    }
    newSelectObj.style.position = "absolute";
    newSelectObj.style.top = absTop + "px";
    newSelectObj.style.left = absLeft + "px";
    newSelectObj.style.width = "auto";

    var rollback = function(){
		rollbackWidth(selectObj, newSelectObj);
	};
    if(window.addEventListener){
        newSelectObj.addEventListener("blur", rollback, false);
        newSelectObj.addEventListener("change", rollback, false);
    } else{
        newSelectObj.attachEvent("onblur", rollback);
        newSelectObj.attachEvent("onchange", rollback);
    }
    selectObj.style.visibility = "hidden";
    document.body.appendChild(newSelectObj);
    newSelectObj.focus();
}

function rollbackWidth(selectObj, newSelectObj){
    selectObj.selectedIndex = newSelectObj.selectedIndex;
    selectObj.style.visibility = "visible";
    document.body.removeChild(newSelectObj);
}


function disabledSelect(){
	var selects = document.getElementsByTagName("SELECT"); 
	for( var i =0 ; i, selects.length; i++){
		selects[i].disabled = true;
	}
}