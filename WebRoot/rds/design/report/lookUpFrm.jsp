<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>
<%
    String lookUpDataURL = contextPath + "/rds/lookUpProcess.do";
    lookUpDataURL += "?act=DETAIL_ACTION";
%>
<html>
<head>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<body>
<html:form action="/rds/lookUpFrm" method="post">
<script type="text/javascript">
    ArrActions[2][0] = true;
    ArrActions[4][0] = true;
    ArrActions[15][0] = true;
    printTitleBar("LOOKUP查找维护");
    printToolBar();
</script>
</html:form>
<table border="0" width="100%" height="100%">
    <tr style="height:22px;text-align:center">
        <td>已定义的LOOKUP查询列表</td>
        <td>LOOKUP查询列表详细信息</td>
    </tr>
	<tr>
		<td width="65%"><iframe id="lookUpListFrm" style="width:100%;height:100%" border="0" frameborder="0" src=""></iframe></td>
		<td width="35%"><iframe id="lookUpDataFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=lookUpDataURL%>"></iframe></td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
function do_Save(){
    var frm = document.getElementById("lookUpDataFrm");
    var childWin = frm.contentWindow;
    childWin.do_Save();
}

function do_Create(){
    var frm = document.getElementById("lookUpDataFrm");
    var childWin = frm.contentWindow;
    childWin.do_Create();
}

function do_Close(){
    var ajaxProcessor = new RDSAjaxProcessor(do_SetLookUp2Parent, false);
    ajaxProcessor.setServiceClass("com.sino.rds.share.util.RDSOptionCreateService");
    ajaxProcessor.setMethodName("getAllLookUpOptions");
    ajaxProcessor.performTask();
}

function do_SetLookUp2Parent(resText){
    var doc = window.opener.document;
    var thatLov = doc.getElementById("referenceLookUp");
    thatLov.outerHTML = "<select name=\"referenceLookUp\" id=\"referenceLookUp\" style=\"display:none\">"+resText+"</select>";
    var tab = doc.getElementById("dataTable");
    var rows = tab.rows;
    if(rows){
        var rowCount = rows.length;
        for(var i = 0; i < rowCount; i++){
            var tr = rows[i];
            var inputType = getTrNode(tr, "inputType").value;
            if(inputType != "<%=RDSDictionaryList.INPUT_TYPE_LOOKUP%>"){
                continue;
            }
            var node = getTrNode(tr, "lookUpId");
            var nodeName = node.name;
            var selectedValue = getSelectedValue(node);
            node.outerHTML = "<select name=\""+nodeName+"\" style=\"width:100%\" onchange=\"do_ProcessLookUp(this)\">"+resText+"</select>";
            node = getTrNode(tr, "lookUpId");
            selectSpecialOptionByItem(node, selectedValue);
        }
    }
    window.close();
}
</script>