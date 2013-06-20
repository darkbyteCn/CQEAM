<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  created by Zyun
  Date: 2007-09-26
  Time: 8:23:30
--%>

<html>
<head>
    <title>资产管理指标维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<body>
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String kpiTypeOptions = (String) request.getAttribute("KPI_TYPE_OPTIONS");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.kpi.servlet.KpiDefineServlet">
<script type="text/javascript">
    printTitleBar("资产管理指标维护")
</script>
<table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="65%" align="left">&nbsp;&nbsp;
            	<img src="/images/eam_images/back.jpg" alt="返回查询页" onClick="history.go(-1);">
			</td>
            <td width="35%" align="center">&nbsp;&nbsp;     
            	<img src="/images/eam_images/add_data.jpg" style="cursor:'hand'" onclick="do_addLine();" alt="添加数据">&nbsp;&nbsp;
            	<img src="/images/eam_images/save.jpg" style="cursor:'hand'" onclick="do_save();" alt="数据保存">&nbsp;&nbsp;
            	<img src="/images/eam_images/reset.jpg" alt="数据重置" onClick="do_reset();">
            </td>
        </tr>        
</table>
<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" class="headerTable" border="1">
        <tr height="20">
            <td width="12%" align="center">指标代码</td>
            <td width="18%" align="center">指标名称</td>
            <td width="40%" align="center">指标计算描述</td>
            <td width="9%" align="center">指标统计方式</td>
            <td width="7%" align="center">指标权重(%)</td>
            <td width="6%" align="center">是否有效</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">    

<div style="overflow-y:scroll;left:0;width:100%;height:372px">
    <table width="100%" border="1" bordercolor="#666666" id="dataTable">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    String isEnable=StrUtil.nullToString(row.getValue("IS_ENABLE"));
        %>
        <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" id="row<%=i%>">
        	<td width="12%" >
            	<%=row.getValue("KPI_CODE")%>
            	<input type="hidden" name="kpiCode" id="kpiCode<%=i%>" value="<%=row.getValue("KPI_CODE")%>" />
            	<input type="hidden" id="lineNo<%=i%>" name="isChangeFlag" value="N" />
            </td>
            <td width="18%">
            	<input type="text"  name="kpiName" id="kpiName<%=i%>" value="<%=row.getValue("KPI_NAME")%>" style="width:100%" <% if (!isEnable.equals("N")) {%> class="noEmptyInput" <%} %> onchange="setIsChangeFlag(this,'<%=row.getValue("KPI_NAME")%>','<%=i%>');"/>
            </td>
            <td width="40%">
            	<input type="text" name="kpiDesc" id="kpiDesc<%=i%>" value="<%=row.getValue("KPI_DESC")%>" style="width:100%" onchange="setIsChangeFlag(this,'<%=row.getValue("KPI_DESC")%>','<%=i%>');"/>
            </td>
            <td width="9%" >
            	<select name="kpiType" id="kpiType<%=i%>" style="width:100%"  <% if (!isEnable.equals("N")) {%> class="noEmptyInput" <%} %> onchange="setIsChangeFlag(this,'<%=row.getValue("KPI_TYPE")%>','<%=i%>');">
            		<%=row.getValue("KPI_TYPE_OPTIONS")%>
            	</select>
            </td>
            <td width="7%" >
            	<input type="text" name="kpiValue" id="kpiValue<%=i%>" value="<%=row.getValue("KPI_VALUE")%>" style="width:100%;"  <% if (!isEnable.equals("N")) {%> class="noEmptyInput" <%} %>  onchange="setIsChangeFlag(this,'<%=row.getValue("KPI_VALUE")%>','<%=i%>');"/>
            </td>
            <td width="6%">
            	<select name="isEnable" id="isEnable<%=i%>" style="width:100%"  <% if (!isEnable.equals("N")) {%> class="noEmptyInput" <%} %>  onchange="setIsChangeFlag(this,'<%=row.getValue("IS_ENABLE")%>','<%=i%>');">
            		<option value="">--请选择--</option>
            		<option value="Y" <% if ("Y".equalsIgnoreCase((String)row.getValue("IS_ENABLE"))) {%> selected <%} %> >有效</option>
            		<option value="N" <% if ("N".equalsIgnoreCase((String)row.getValue("IS_ENABLE"))) {%> selected <%} %> >无效</option>
            	</select>
            </td>
        </tr>
<%
    } }
%>
    </table>
</div>
</form>

<jsp:include page="/message/MessageProcess"/>

</body>
</html>
<script type="text/javascript">
String.prototype.trim = function() {
   return this.replace(/^\s+|\s+$/g,"");
}

function do_reset() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.EDIT_ACTION%>";
    mainFrm.submit();
}
function show_detail(projectId) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.kpi.servlet.KpiDefineServlet?projectId=" + projectId;
    mainFrm.submit();
}
function do_save() {
	var kpiCode="";
	var kpiName="";
	var kpiType="";
	var kpiValue="";
	var isEnable="";
	var kpiValueTotal=0;
	var lineCount=document.getElementById("dataTable").rows.length;
	for (var i=0;i<lineCount;i++) {
		if (document.getElementById("isEnable"+i).value.trim()=="") {
				alert("是否有效不能为空！");
				document.getElementById("isEnable"+i).focus();
				return;
		} else if (document.getElementById("isEnable"+i).value.trim()=="Y") {
			if (document.getElementById("kpiCode"+i).value.trim()=="") {
				alert("有效指标的指标代码不能为空！");
				document.getElementById("kpiCode"+i).focus();
				return;
			}
			if (document.getElementById("kpiName"+i).value.trim()=="") {
				alert("有效指标的指标名称不能为空！");
				document.getElementById("kpiName"+i).focus();
				return;
			}
			if (document.getElementById("kpiType"+i).value.trim()=="") {
				alert("有效指标的指标类型不能为空！");
				document.getElementById("kpiType"+i).focus();
				return;
			}
			if (document.getElementById("kpiValue"+i).value.trim()=="") {
				alert("有效指标的指标权重不能为空！");
				document.getElementById("kpiValue"+i).focus();
				return;
			} else if (!isNumber(document.getElementById("kpiValue"+i).value)) {
				alert("有效指标的指标权重应是数字！");
				document.getElementById("kpiValue"+i).focus();
				return;
			} else if (parseFloat(document.getElementById("kpiValue"+i).value)<0) {
			  	alert("有效指标的指标权重不能小于零！");
				document.getElementById("kpiValue"+i).focus();
				return;
			}else {
				kpiValueTotal=kpiValueTotal+parseFloat(document.getElementById("kpiValue"+i).value);		
			}		
		}
			
	}
	if (kpiValueTotal!=100) {
		alert("有效指标的所有指标权重和应为100%!");
		return;
	}
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
}

function setIsChangeFlag(obj,oldValue,lineNo) {
	var newValue=obj.value
	if (obj=document.getElementById("isEnable"+lineNo)) {
		if (newValue=="N") {
			document.getElementById("kpiName"+lineNo).className="";
			document.getElementById("kpiType"+lineNo).className="";
			document.getElementById("kpiValue"+lineNo).className="";
			document.getElementById("isEnable"+lineNo).className="";
		} else {
			document.getElementById("kpiName"+lineNo).className="noEmptyInput";
			document.getElementById("kpiType"+lineNo).className="noEmptyInput";
			document.getElementById("kpiValue"+lineNo).className="noEmptyInput";
			document.getElementById("isEnable"+lineNo).className="noEmptyInput";	
		}	
	}
	if(!(newValue==oldValue)) {
		document.getElementById("lineNo"+lineNo).value="Y";		
	}	
}

function do_addLine() {
	var tab=document.getElementById("dataTable");
	var lineCount=tab.rows.length;
	var newRow=tab.insertRow(lineCount);
	newRow.id="row"+lineCount;
	var cell0=newRow.insertCell(0);
	cell0.innerHTML='<input type="text" name="kpiCode" id="kpiCode'+lineCount+'" value="" style="width:100%" class="noEmptyInput" onblur="checkIsExists(this);"/>'+
            	    '<input type="hidden" id="lineNo'+lineCount+'" name="isChangeFlag" value="Y" />';
	var cell1=newRow.insertCell(1);
	cell1.innerHTML='<input type="text" name="kpiName" id="kpiName'+lineCount+'" value="" style="width:100%" class="noEmptyInput"/>';
	var cell2=newRow.insertCell(2);
	cell2.innerHTML='<input type="text" name="kpiDesc" id="kpiDesc'+lineCount+'" value="" style="width:100%"/>';
	var cell3=newRow.insertCell(3);
	cell3.innerHTML='<select name="kpiType" id="kpiType'+lineCount+'" style="width:100%" class="noEmptyInput">'+
            		'<%=kpiTypeOptions%>'+
            	    '</select>';
		
	var cell4=newRow.insertCell(4);
	cell4.innerHTML='<input type="text" name="kpiValue" id="kpiValue'+lineCount+'" value="0" style="width:100%;" class="noEmptyInput" />';
	var cell5=newRow.insertCell(5);
	cell5.innerHTML='<select name="isEnable" id="isEnable'+lineCount+'" style="width:100%" class="noEmptyInput" onchange="setNewLineIsChangeFlag(this);">'+
            		'<option value="">--请选择--</option>'+
            		'<option value="Y" selected>有效</option>'+
            		'<option value="N" >无效</option>'+
            	'</select>';
}

function setNewLineIsChangeFlag(obj) {
	var lineNo=parseInt(obj.id.substring(8));
	var newValue=obj.value;
		if(newValue=="N") {
			document.getElementById("lineNo"+lineNo).value="N";
			document.getElementById("kpiCode"+lineNo).className="";
			document.getElementById("kpiName"+lineNo).className="";
			document.getElementById("kpiType"+lineNo).className="";
			document.getElementById("kpiValue"+lineNo).className="";
			document.getElementById("isEnable"+lineNo).className="";
		} else {
			document.getElementById("lineNo"+lineNo).value="Y";
			document.getElementById("kpiCode"+lineNo).className="noEmptyInput";
			document.getElementById("kpiName"+lineNo).className="noEmptyInput";
			document.getElementById("kpiType"+lineNo).className="noEmptyInput";
			document.getElementById("kpiValue"+lineNo).className="noEmptyInput";
			document.getElementById("isEnable"+lineNo).className="noEmptyInput";	
		}
}

function checkIsExists(obj) {
	var currLine=parseInt(obj.id.substring(7));
	var tab=document.getElementById("dataTable");
	var newValue=obj.value;
	var oldColor;
	var lineCount=tab.rows.length;
	for (var i=0;i<lineCount;i++) {		
		if (newValue && i!=currLine && document.getElementById("kpiCode"+i).value.trim()==newValue.trim()) {
			oldColor=document.getElementById("row"+i).style.backgroundColor;
			document.getElementById("row"+i).style.backgroundColor="red";
			alert("该指标代码已存在，请编辑系统原有指标！");
			if (document.getElementById("row"+i)) {
				document.getElementById("row"+i).style.backgroundColor=oldColor;
			}	
			
			tab.deleteRow(currLine);
			for (var j=currLine+1;j<lineCount;j++) {	
				document.getElementById("row"+j).id="row"+(j-1);
				document.getElementById("kpiCode"+j).id="kpiCode"+(j-1);
				document.getElementById("lineNo"+j).id="lineNo"+(j-1);
				document.getElementById("kpiName"+j).id="kpiName"+(j-1);
				document.getElementById("kpiDesc"+j).id="kpiDesc"+(j-1);
				document.getElementById("kpiType"+j).id="kpiType"+(j-1);
				document.getElementById("kpiValue"+j).id="kpiValue"+(j-1);
				document.getElementById("isEnable"+j).id="isEnable"+(j-1);
				
			}
			lineCount=tab.rows.length;
			return;
		}
	}
}
</script>