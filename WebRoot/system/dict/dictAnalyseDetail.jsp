<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.system.dict.dto.EtsFlexValuesDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>


<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>组别详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>

</head>
<body onload="initPage();"  onkeydown="autoExeFunction('do_SaveDictionary()');">
 <jsp:include page="/message/MessageProcess"/>
<%
    EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)request.getAttribute(WebAttrConstant.DICT_DATA);
    String flexValueSetName = (String)request.getAttribute("flexValueSetName");
    String title = "资产分析系统字典维护";
	if(flexValueSetName != null && !flexValueSetName.equals("")){
		title += ">>" + flexValueSetName;
	}
	String opartor = System.getProperty("file.separator");
	
    String remark = null;
    if(opartor.equals("\\")){
	     remark = new String(((String)request.getAttribute("remark")).getBytes("ISO-8859-1"),"GBK");
	}else{
	     remark = (String)request.getAttribute("remark");
	}
    
	String[] descs = remark.split(",");
	int len = descs.length;
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet">
<script type="text/javascript">
    printTitleBar("<%=title%>");
</script>
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right" height="22">字典代码：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="code" size="40" class="noemptyInput" style="width:100%" value="<%=flexValues.getCode()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">字典值：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="value" size="40" style="width:100%" value="<%=flexValues.getValue()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="25%" align="right" height="22">字典描述：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="description" size="40" style="width:100%" value="<%=flexValues.getDescription()%>"><td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">是否有效：</td>
            <td width="50%" align="left" height="22"><%=request.getAttribute(WebAttrConstant.ENABLED_RADIO)%></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">是否内置：</td>
            <td width="50%" align="left" height="22"><%=request.getAttribute(WebAttrConstant.IS_INNER_RADIO)%></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所属分类：</td>
            <td width="50%" align="left" height="22">
                <select name="flexValueSetId" style="width:100%"><%=request.getAttribute(WebAttrConstant.DICT_PARENT_OPT)%></select>
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">文件版本：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="fileVersion" size="40" style="width:100%" value="<%=flexValues.getFileVersion()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <% 
		  for(int i = 0; i < descs.length; i++){
			   if(!descs[i].equals("") && !descs[i].equals(" ")){
		 %>
		<tr>
            <td width="25%" align="right" height="22"><%=descs[i]%>：</td>
            <td width="50%" align="left" height="22">
            <% 
              if(i == 0){
            %>
			<input type="text" name="attribute1" size="40" style="width:100%" value="<%=flexValues.getAttribute1()%>"></td>
			<%
		     }else if(i == 1){
		    %>
		    <input type="text" name="attribute2" size="40" style="width:100%" value="<%=flexValues.getAttribute2()%>"></td>
			<%
		     }else if(i == 2){
		    %>
		    <input type="text" name="attribute3" size="40" style="width:100%" value="<%=flexValues.getAttribute3()%>"></td>
			<%
		     }else if(i == 3){
		    %>
		    <input type="text" name="attribute4" size="40" style="width:100%" value="<%=flexValues.getAttribute4()%>"></td>
			<%
		     }else if(i == 4){
		    %>
		    <input type="text" name="attribute5" size="40" style="width:100%" value="<%=flexValues.getAttribute5()%>"></td>
			<%
		     }else if(i == 5){
		    %><input type="text" name="attribute6" size="40" style="width:100%" value="<%=flexValues.getAttribute6()%>"></td>
			<%
		     }
		    %>
            <td width="25%" align="left" height="22"></td>
        </tr>
		<%
		    }
		  }
		%>
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
<% 
    //资产分析的字典可修改
	//if(!flexValues.getIsInner().equals("Y") || flexValues.getFlexValueId().equals("")){
%>
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDictionary(); return false;">&nbsp;
<%
	//}	
%>
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="window.history.back(-1);"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="flexValueId" value="<%=flexValues.getFlexValueId()%>">
    <input type="hidden" name="flexValueSetName" value="">
</form>
</body>
</html>
<script>

function initPage(){
	var selObj = mainFrm.flexValueSetId;
	var flexValueSetName = selObj.options[selObj.selectedIndex].text;
	var startIndex = flexValueSetName.indexOf("(") + 1;
	var endIndex = flexValueSetName.length - 1;
	flexValueSetName = flexValueSetName.substring(startIndex, endIndex);
	mainFrm.flexValueSetName.value = flexValueSetName;
}

function do_SaveDictionary() {
	var fieldNames = "code;value;flexValueSetId";
	var fieldLabels = "字典代码;字典值;所属分类";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.flexValueId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>";
		mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		code.value = "";
		value.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>";
		submit();
	}
}
</script>