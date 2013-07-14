function do_ImportExcel(){
    if(!excelType){
        excelType = "1";
    }
    var url = "";
    if(excelType == "1")  {
        url = "/yearchecktaskmanager/upFile.jsp";
    } 
    
    var factor = 0.5;
    var dialogWidth = window.screen.availWidth * factor;
    var dialogHeight = window.screen.availHeight * factor;
    var dialogStyle = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    return window.showModalDialog(url,"",dialogStyle);
}


function do_ImportClientExcel(){
    if(!excelType){
        excelType = "1";
    }
    var url = "";
    if(excelType == "1")  {
        url = "/yearchecktaskmanager/upClientFile.jsp";
    } 
    
    var factor = 0.5;
    var dialogWidth = window.screen.availWidth * factor;
    var dialogHeight = window.screen.availHeight * factor;
    var dialogStyle = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    return window.showModalDialog(url,"",dialogStyle);
}