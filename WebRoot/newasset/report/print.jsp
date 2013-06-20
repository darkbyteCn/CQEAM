<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<div id="buttonSet" >
	<script type="text/javascript">
	var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    var ArrAction1 = new Array(true, "打印设置", "download.gif", "打印设置", "do_SetupPrint(); return false;");
    var ArrAction2 = new Array(true, "打印预览", "action_viewstatus.gif", "打印预览", "do_PrevPrint(); return false;");
    var ArrAction3 = new Array(true, "打印全部", "print.gif", "打印全部", "do_PrintOrder_all(); return false;"); 
    var ArrAction4 = new Array(true, "打印调入方标签", "print.gif", "打印调入方标签", "do_PrintOrder_2(); return false;"); 
    var ArrAction5 = new Array(true, "导出调入方标签", "export.gif", "导出调入方标签", "do_Export(); return false;"); 
    var ArrActions = new Array(); 
   	ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2 , ArrAction3 , ArrAction4 , ArrAction5);  
     	
   	var ArrSinoViews = new Array();
    printToolBar();
       
    var hasExport = false;
    function do_SetupPrint(){
    	if( checkHasExport() ){
    		return false;
    	}
		webbrowser.execwb(8,0);
	}
	
	function do_PrevPrint(){
		if( checkHasExport() ){
    		return false;
    	}
		document.getElementById("buttonSet").style.display = "none";
		webbrowser.execwb(7,0);
		document.getElementById("buttonSet").style.display = "block";
	}
	
	function do_PrintOrder(){
		document.getElementById("buttonSet").style.display = "none";
		webbrowser.execwb(6,6);
		document.getElementById("buttonSet").style.display = "block";
	}
	
	function do_Close(){ 
		top.close();
		return false;
	} 
	
	function checkHasExport(){
		if( hasExport ){
			alert("导出或打印调入方标签后需要刷新页面，否则控件无法正常使用");
			location.reload();
		} 
		return hasExport;
	}
	
	function do_PrintOrder_all(){
		if( checkHasExport() ){
    		return false;
    	}
		var labelTable = document.getElementById("labelTable");
		var tHead = labelTable.tHead;
		var tbody = labelTable.tBodies[0];
		tHead.rows[0].cells[0].style.display = "block";
		
		var dataRows = tbody.rows;
		var len = dataRows.length;
		for( var i=0 ; i< len; i ++ ){
			dataRows[i].cells[0].style.display = "block";
		}
		
		do_PrintOrder();
	}
	function do_PrintOrder_2(){ 
		if( checkHasExport() ){
    		return false;
    	}
    	document.submitForm.target = "_Blank";
    	document.submitForm.act.value = "PRINT_BARCODE_ACTION";
        document.submitForm.submit();
        hasExport = true ;
        //location.reload();
        return false;
	}
	
	function do_Export(){
		document.submitForm.target = "submitFrm";
		document.submitForm.act.value = "EXPORT_ACTION";
        document.submitForm.submit();
        submitForm.reset();
        hasExport = true ;
        return false;
	}
   </script>
</div>
<object id="webbrowser" width="0" height="0"
	classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>