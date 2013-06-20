<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新增物资</title>
 <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
 <script language="javascript" src="/WebLibary/js/Constant.js"></script>
 <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
 <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
 <script language="javascript" src="/WebLibary/js/calendar.js"></script>
 <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
 <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
 <script language="javascript" src="/WebLibary/js/calendar.js"></script>
 <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
 <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
 <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
 <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">


<%
	//DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	//String creationDate = format.format(new Date());
	
	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.nm.ams.newasset.servlet.InformationMaterialManageServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("信息部物资管理-->>新增物资")
</script>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="80%" id="table2" cellspacing="1">
                <tr>
                	<td align="left" valign="bottom" colspan="4">
	                	<img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
	                    <img src="/images/button/deleteLine.gif" alt="删除行"
		        		    onClick="deleteTableRow(document.getElementById('dataTable'),'subCheckGroupt');">
	                 	<img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_Save(2);">
	                    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
	<input readonly name="act" type="hidden">
<script type="text/javascript">
function printTableHeadInfo(columnArr,widthArr){
    document.write("<div id=\"headDiv\" style=\"overflow:hidden;position:absolute;top:47px;left:1px;width:1060px\" align=\"left\">");
    document.write("<table class=\"headerTable\" border=\"1\" width=\"300%\">");
    document.write("<tr height=\"22\">");
    if(columnArr[0] == "checkbox"){
         document.write("<td width=\""+widthArr[0]+"\" align=\"center\"><input type=\"checkBox\" name=\"titleCheck\" onclick=\"checkAll('titleCheck','subCheckGroupt');\"></td>");
    } else if(columnArr[0] == "radio"){
         document.write("<td width=\""+widthArr[0]+"\" align=\"center\"></td>");
    } else{
	     document.write("<td width=\""+widthArr[0]+"\" align=\"center\">"+columnArr[0]+"</td>")
    }
    for(var i=1;i<columnArr.length;i++){
        document.write("<td width=\""+widthArr[i]+"\" align=\"center\">"+columnArr[i]+"</td>")
    }
    document.write("</tr></table></div>")

}

    var columnArr = new Array("checkbox",  "物资大类别", "物资二级类别","资产标签", "资产名称", "资产型号", "资产状态", "资产品牌", "资产序列号", "创建人", "承载系统", "产品号", "责任人", "责任部门", "内存", "CPU", "IP", "硬盘信息", "操作系统", "托管类型", "重要程度", "是否有效", "失效日期","物资三级类别", "扩展属性1", "扩展属性2", "扩展属性3");
    var widthArr = new Array( "1%",      "3%",       "3%",   "4%",     "4%",      "4%",     "2%",      "4%",     "4%",        "4%",    "4%",     "4%",    "4%",    "4%",      "3%",  "3%",  "4%", "3%",     "3%",      "2%",     "4%",             "2%",      "4%",     "3%",        "4%",       "4%",      "4%");
    printTableHeadInfo(columnArr, widthArr);
</script>

<div id="dataDiv" style="overflow:scroll;height:350px;width:1060px;position:absolute;top:69px;left:1px;" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="300%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;">
          
           <tr height="22" style="cursor:'hand';" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
         	  
         	   <td width="1%" align="center">
					<input type="checkbox" name="subCheckGroupt" id="subCheck0" value="TRANS_ID:2789">
			   </td>
               	<td width="4%" align="center"><input type="text" name="barcode" value="" style="width:100%"></td>
               <td width="3%" align="center"><select   style="width:100%" onchange="do_SetType(this)" name="itemCategory1" id="itemCategory10" class="blueborderYellow"></select></td>
				<td width="3%" align="center"><input type="text" name="itemCategory2" value="" style="width:100%"></td>
                <td width="4%" align="center"><input type="text" name="itemName" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="itemSpec" value="" style="width:100%"></td>
				<td width="2%" align="center"><input type="text" name="itemStatus" value="" style="width:100%"></td> 
				<td width="4%" align="center"><input type="text" name="itemBrand" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="itemSerial" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="createdBy" value="<%=user.getUserNumber() %>" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="useBySystem" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="productId" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="responsibilityUser" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="responsibilityDept" value="" style="width:100%"></td>
				<td width="3%" align="center"><input type="text" name="memory" value="" style="width:100%"></td>
				<td width="3%" align="center"><input type="text" name="cpu" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="ipAddress" value="" style="width:100%"></td>
				<td width="3%" align="center"><input type="text" name="diskInformation" value="" style="width:100%"></td>
				<td width="3%" align="center"><input type="text" name="systemName" value="" style="width:100%"></td>
				<td width="2%" align="center"><input type="text" name="trusteeshipType" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="importantLevel" value="" style="width:100%"></td>

				<td width="2%" align="center"><input type="text" name="enabled" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="disableDate" value="" style="width:100%"></td>
				<td width="3%" align="center"><input type="text" name="itemCategory3" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="attribute1" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="attribute2" value="" style="width:100%"></td>
				<td width="4%" align="center"><input type="text" name="attribute3" value="" style="width:100%"></td>
          </tr>
	</table>
 </div>
 
 </form>
</body>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>

<script type="text/javascript">

function createLine(){
	var dataTable = document.getElementById("dataTable");
	var tr = dataTable.getElementsByTagName("tr")[0];
	var trClone = tr.cloneNode(true);
	trClone.style.display = "block";
	var tds = trClone.childNodes;
	
	for(var i = 0; i < tds.length; i++){
		if(""!=tds[i].childNodes[0].value || null!=tds[i].childNodes[0].value){
			tds[i].childNodes[0].value = "";
		}
	}
	dataTable.firstChild.appendChild(trClone);
}

function do_SelectItem(){
	createLine();	
}

function checkAll(checkBoxName, checkBoxGroup) {
	var objs = document.all[checkBoxName];
	if(!objs){
		return;
	}
	var checkAttr = 0;
	if(objs.length){
		var obj = null;
		for(var i = 0; i < objs.length; i++){
			obj = objs[i];
			if(obj.type == "checkbox"){
				checkAttr = obj.checked;
				break;
			}
		}
	} else {
		checkAttr = objs.checked;
	}
    try {
        isChecked = checkAttr;
        if (!setCheckBoxState(checkBoxGroup, isChecked)) {
            document.all[checkBoxName].checked = !isChecked;
        }
    } catch(exception) {
        alert(exception);
    }
}

function do_Search(){
	mainFrm.act.value = "";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_CreateOrder() {
    //var url = "?act=<&transType=" + transType;
    var url = "?act=";
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, "transferWin", style);
}

function do_Export() {
    mainFrm.act.value = "";
    mainFrm.submit();
}
var sflag = null;
function do_Save(flag) {
   var tb = document.getElementById("dataTable");
   var rcount=tb.rows.length;
   if (tb.rows.length == 0 || (tb.rows[0].style.display == 'none' && tb.rows.length == 1)) {
     alert("没有选择行数据，请选择行数据！");
     return;
   }
   
    if (flag == 2) {
       mainFrm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    } else {  
       mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    }
    
    mainFrm.submit();
    
}
</script>