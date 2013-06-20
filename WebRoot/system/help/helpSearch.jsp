<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.appbase.help.dto.HelpDTO"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<% 
	String helpCode = request.getParameter("helpCode");
	String helpKeyName = request.getParameter("helpKeyName");
	String treeId = request.getParameter("treeId");
	if (helpKeyName == null || helpKeyName.equals("") || helpKeyName.equals("null")) {
		helpKeyName = "";
	}
	if (helpCode == null || helpCode.equals("")) {
		helpCode = "noFind.htm";
	}
%>

</head>
<body leftmargin="1" topmargin="0" style= "overflow-x:hidden;overflow-y:hidden;" onload="helpExpand();">
<%
    String action = StrUtil.nullToString(request.getParameter("act"));
%>
<form name="mainFrm" method="POST" action="" >
    <input type="hidden" name="act" value="<%=action%>">
	<iframe name="right" marginwidth=0 marginheight=0 width=100% height=100% src="<%=request.getAttribute("helpCode")%>?helpKeyName=<%=request.getAttribute("helpKeyName")%>&treeId=<%=request.getAttribute("treeId")%>" frameborder=0 scrolling="auto"></iframe>

</form>
</body>
</html>
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
    	var treeId = "<%=treeId%>";
    	//alert(treeId);
    	var arrA = treeId.split(",");
    	
    	Pause(this,1000);    
 			this.NextStep = function() {   
 				//var t = setTimeout("location.reload()",2000);
		    	for (var i = 0; i < arrA.length; i++ ) {
		    		self.parent.frames["contents"].tree.expand(arrA[i]);
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



