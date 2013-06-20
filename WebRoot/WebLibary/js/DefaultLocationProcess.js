var tab = null;
var rows = null;
var rowCount = 0;

function do_InitTable(){
    tab = document.getElementById("dataTable");
    rows = tab.rows;
    rowCount = rows.length;
}

function do_SetDefaultLocation(){
    do_InitTable();
    var src = event.srcElement;
    var checked = src.checked;
    if(rowCount == 1){
        var row = rows[0];
        if(row.style.display == "none"){
            alert("请先选择调拨资产。");
            src.checked = !checked;
            return;
        }
        do_ProcessDefaultLocation(checked);
    } else {
        do_ProcessDefaultLocation(checked);
    }
}

function do_ProcessDefaultLocation(checked){
    if(checked){
        do_AssignDefaultLocation();
    } else {
        do_ClearDefaultLocation();
    }
}

function do_AssignDefaultLocation(){
    var objectNos = "'";
    for(var i = 0; i < rowCount; i++){
        var row = rows[i];
        var oldLocationName = getTrNode(row, "oldLocationName");
        var oldLocation = getTrNode(row, "oldLocation");
        var assignedToLocationName = getTrNode(row, "assignedToLocationName");
        var assignedToLocation = getTrNode(row, "assignedToLocation");
        var addressId = getTrNode(row, "addressId");
        assignedToLocationName.value = oldLocationName.value;
        assignedToLocation.value = oldLocation.value;
        objectNos += oldLocation.value;
        objectNos += "'";
        if(i < rowCount - 1){
            objectNos += ",'";
        }
    }
    do_SetDefaultAddressId(objectNos);
}

function do_SetDefaultAddressId(objectNos){
    var actionURL = "/servlet/com.sino.ams.system.object.servlet.AmsObjectAddressServlet";
    var ajaxProcessor = new AjaxProcessor(actionURL, do_ProcessAddressResponse, false);
    ajaxProcessor.addParameter("objectNos", objectNos);
    ajaxProcessor.performTask();
}

function do_ProcessAddressResponse(resText){
    if(resText != ""){
        var dtoArr = eval(resText);
        var dataCount = dtoArr.length;
        for(var i = 0; i < rowCount; i++){
            var row = rows[i];
            var location = getTrNode(row, "assignedToLocation");
            var locationValue = location.value;
            var addressId = getTrNode(row, "addressId");
            for(var j = 0; j < dataCount; j++){
                var dto = dtoArr[j];
                if(locationValue == dto["objectNo"]){
                    addressId.value = dto["addressId"];
                    break;
                }
            }
        }
    }
}

function do_ClearDefaultLocation(){
    for(var i = 0; i < rowCount; i++){
        var row = rows[i];
        var assignedToLocationName = getTrNode(row, "assignedToLocationName");
        var assignedToLocation = getTrNode(row, "assignedToLocation");
        var addressId = getTrNode(row, "addressId");
        assignedToLocationName.value = "";
        assignedToLocation.value = "";
        addressId.value = "";
    }
}