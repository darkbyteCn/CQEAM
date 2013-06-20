<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.appbase.help.dto.HelpDTO"%>
<%@ page import="com.sino.base.calen.SimpleCalendar" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<html>
<head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
    <title></title>
</head>
<%
	SimpleCalendar cal = new SimpleCalendar();
	long accessTime = cal.getTimeInMillis();
    HelpDTO resource = (HelpDTO) request.getAttribute(com.sino.ams.constant.WebAttrConstant.RESOURCE_DATA);
    
	String helpCode = request.getParameter("helpCode");
	String helpKeyName = request.getParameter("helpKeyName");
	String treeId = request.getParameter("treeId");
	if (helpKeyName == null || helpKeyName.equals("") || helpKeyName.equals("null")) {
		helpKeyName = "";
	}
	if (helpCode == null || helpCode.equals("")) {
		helpCode = "/system/help/noFind.htm";
	}
	
	String action = StrUtil.nullToString(request.getParameter("act"));
%>

<body style="overflow-x:auto;overflow-y:hidden;" onload="helpExpand();">
<form name="mainFrm" method="POST" action="" >
<input type="hidden" name="act" value="<%=action%>">
<table border="0" width="100%" id="queryTable" class="queryTable">
	<tr>
        <td align="right">
            <img align="middle" src="/images/act_prepage.gif" alt="后退" name="imgHt" onclick="do_ht();">
        </td>
        <td align="left">
            <img align="middle" src="/images/act_nextpage.gif" alt="前进" name="imgQj" onclick="do_qj();">
        </td>
     	<td width="60%" align="right">主题搜索：</td>
     	<td width="36%"><input class="input_style1" type="text" name="helpKeyName" style="width:100%" value="<%=helpKeyName%>"></td>
        <td width="100%" align="right">
            <img align="middle" src="/images/zoom.png" alt="查询" onclick="do_search();">
        </td>
	</tr>
</table>

<script type="text/javascript" src="/WebLibary/js/MzTreeView10.js"></script> 
<script type="text/javascript">
    function do_search() {  

		var helpKeyName = document.getElementById("helpKeyName").value;
		if (helpKeyName == "" || helpKeyName == null) {
			alert("请输入关键字");
			return;
		}
        document.mainFrm.act.value = "QUERY_HELP_KEY";
        document.mainFrm.action = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?helpKeyName=" + helpKeyName;
        document.mainFrm.submit();
        document.getElementById("helpKeyName").value = helpKeyName;
    }
    
    function do_ht() {
    	var historyLength= window.history.length;
    	history.go(-1);
//    	if (historyLength == undefined || historyLength == null || historyLength == 0) {
//    	} else {
//    	}
    }
    
    function do_qj() {
    	var historyLength = window.history.length;
    	history.go(1);
    } 
    
    
    function helpExpand() {
    	var treeId = "<%=request.getAttribute("treeId")%>";
    	//alert(treeId);
    	var arrA = treeId.split(",");
    	
    	Pause(this,1000);    
 			this.NextStep = function() {   
 				//var t = setTimeout("location.reload()",2000);
		    	for (var i = 0; i < arrA.length; i++ ) {
		    		self.frames["contents"].tree.expand(arrA[i]);
		    		//alert(arrA[i]);
		    	} 
  			}
    	
    	//for (var i = 0; i < arrA.length; i++ ) {
    	//	self.parent.frames["contents"].tree.expand(arrA[i]);
    	//	//alert(arrA[i]);
    	//}
    	
    	//self.parent.frames["contents"].tree.expand('2');
    	//self.parent.frames["contents"].tree.expand('3');
    	//self.parent.frames["contents"].tree.expand('17');
    	//self.parent.frames["contents"].tree.expand('27');
    	
    	//self.parent.frames["contents"].tree.expand('2');
    	//self.parent.frames["contents"].tree.expand('3');
    	//self.parent.frames["contents"].tree.expand('7');
    	//self.parent.frames["contents"].tree.expand('32');
    	//self.parent.frames["contents"].tree.expand('8');
    	//self.parent.frames["contents"].tree.expand('24');
    	//self.parent.frames["contents"].tree.expand('22');
    	//self.parent.frames["contents"].tree.expand('26');
    	
    	//self.parent.frames["contents"].tree.expand('17');

    	//self.parent.frames["contents"].tree.expand('23');

    	//return;
        
	}
	
	function Pause(obj,iMinSecond) {    
		if (window.eventList == null) window.eventList = new Array();    
		var ind = -1;    
		for (var i = 0; i < window.eventList.length; i++){    
			if (window.eventList[i] == null) {    
				window.eventList[i] = obj;    
				ind = i;    
				break;    
			}    
		}    
		if (ind == -1) {    
			ind=window.eventList.length;    
			window.eventList[ind]=obj;    
		}    
		setTimeout("GoOn(" + ind + ")",iMinSecond);    
	}    
   
	function GoOn(ind) {    
		var obj = window.eventList[ind];    
		window.eventList[ind] = null;    
		if (obj.NextStep) obj.NextStep();    
		else obj();    
	}  
    
</script>

<iframe width="25%" frameborder="0" height="100%" scrolling="yes" marginwidth="0" marginheight="0" name="contents" target="left" src="/servlet/com.sino.appbase.help.servlet.HelpTreeServlet?action=<%=com.sino.ams.constant.AMSActionConstant.TREE_ACTION%>" >
<iframe width="75%" frameborder="0" height="100%" scrolling="yes" marginwidth="0" marginheight="0" name="right" src="<%=request.getAttribute("helpCode")%>" >

</form>
</body>
</html>
