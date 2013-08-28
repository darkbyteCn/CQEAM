<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@page import="java.util.List"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-24
  Time: 15:26:45
  To change this template use File | Settings | File Templates.
--%>
<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "No-cache");
%>
<html>
<base target="_self">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Excel盘点地点导入</title>
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "提交", "action_save.gif", "上传", "doSub");
        var ArrAction1 = new Array(true, "粘贴", "action_sign.gif", "粘贴", "aa");
        var ArrActions = new Array(ArrAction0, ArrAction1);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
    </script>
</head>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	List<String> list = (List) request.getAttribute("NOLOCATIOND_DATA");
	String isYear=request.getAttribute("isYear");//jeffery
	Row row = null;
	boolean hasData = (rows != null && rows.getSize() > 0);
%>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<form name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="flag" value="0">
    <input type="hidden" name="act" value="">
    <script type="text/javascript">
        printTitleBar("Excel盘点地点导入")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table border = "0" width="100%">
        <tr>
            <td width="80%">
            <td width="20%">
            <td></td>
        </tr>
        <tr>
            <td width="80%">
            <input type="file" name="flName" class="select_style1" style="width:45%"><input type="button" name="sub" value="提交Excel" onclick="doSub();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img src="/images/right-3.jpg" width="5" height="9"/>
            <a href="/document/template/LocationImportTem.zip" onclick="window.open('/document/template/LocationImportTem.zip');" style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">Excel盘点地点导入模版</FONT></a><td width="20%">
            <td><img src="/images/eam_images/ok.jpg" alt="确定" onClick="do_TransData();">&nbsp;<img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();"></td>
        </tr>
    </table>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:48px;left:1px">
	<table class="headerTable" border="1">
		<tr height="20" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
		<td width="3%" align="center" style="padding:0">
			<input type="checkbox" name="titleCheck" onclick="checkAll('titleCheck','subCheck')" class="headCheckBox">
		</td>
		<td width="8%" align="center">公司名称</td>
		<td width="10%" align="center">成本中心</td>
		<td width="5%" align="center">地点专业</td>
		<td width="12%" align="center">地点代码</td>
		<td width="28%" align="center">地点名称</td>
        </tr>
	</table>
</div>
	<div id="dataDiv" style="overflow:scroll;height:355px;%>px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
		<%
			if (hasData) {
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
		%>
		<tr onMouseOver="this.style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" STYLE="cursor:'hand'" onclick="executeClick(this);" title="点击选择或取消选择本条数据">
			<td height="22" align="center" width="3%"><input type="checkbox" name="subCheck" id ="subCheck<%=i %>" value="<%=row.getStrValue("CHECK_LOCATION")%>@@@<%=row.getStrValue("OBJECT_CODE")%>@@@<%=row.getStrValue("OBJECT_NAME")%>@@@<%=row.getStrValue("OBJECT_LOCATION")%>)"></td>
			<td height="22" width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" name="companyName" id="companyName<%=i%>" readonly onClick="do_CheckData(this)" value="<%=row.getStrValue("COMPANY_NAME") %>"></td>
			<td height="22" width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" name="countyName" id="countyName<%=i%>" readonly onClick="do_CheckData(this)" value="<%=row.getStrValue("COUNTY_NAME") %>"></td>
			<td height="22" width="5%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" name="objectCategory" id="objectCategory<%=i%>" readonly onClick="do_CheckData(this)" value="<%=row.getStrValue("OBJECT_CATEGORY")%>"></td>
			<td height="22" width="12%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" name="objectCode" id="objectCode<%=i%>" readonly onClick="do_CheckData(this)" value="<%=row.getStrValue("OBJECT_CODE")%>"></td>
			<td height="22" width="28%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" name="objectName" id="objectName<%=i%>" readonly onClick="do_CheckData(this)" value="<%=row.getStrValue("OBJECT_NAME")%>"></td>
    	</tr>
	<%		
			}
		}
	%>
	<%
		if (list !=null && list.size() > 0) {
			for (int j=0;j<list.size();j++) {
	%>
		<tr onMouseOver="this.style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" STYLE="cursor:'hand'" onclick="executeClick(this);" title="点击选择或取消选择本条数据">
			<td height="22" align="center" width="3%"><input type="checkbox" name="NsubCheck" disabled="disabled"></td>
			<td height="22" width="8%"></td>
			<td height="22" width="10%"></td>
			<td height="22" width="5%"></td>
			<td height="22" width="12%"><input type="text" style="width:100%; border: 1px solid #FFFFFF" readonly value="<%=list.get(j) %>"></td>
			<td height="22" width="28%"><font color="red">未找到该地点代码信息</font></td>
    	</tr>
	<%				
			}
		}
	%>
		</table>
	</div>
</form>

</body>
<script type="text/javascript">
    function doSub() {
//jeffery
alert("<%=isYear%>");
        
    	var excelName = document.mainFrm.flName.value;
    	if (excelName.indexOf('.xls') == -1) {
    		alert("请确保您导入的文件格式为xls！");
    		return;
    	}
        if (document.mainFrm.flag.value == "1") {
            alert("正在提交数据，请等待......");
            return;
        }
        if (document.mainFrm.flName.value !== "") {
            document.mainFrm.flag.value="1";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
          	//mainFrm.act.value = "<%=AssetsActionConstant.IMP_LOCATION_CODE_ACTION %>";
            mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet?act=IMP_LOCATION_CODE_ACTION";
            mainFrm.submit();
        } else {
            alert("请输入文件！");
        }
    }
    
    function do_TransData(){
			window.returnValue = getDTOArr();
			self.close();
		
	}
	
	function do_CheckData(box){
		trObj = box.parentNode.parentNode;
		chkObj = trObj.cells[0].childNodes[0];
		if(chkObj){
			chkObj.click();
		}
	}
	function DTO(checkLocation, objectCode, objectName, objectLocation){
		this.checkLocation = checkLocation;
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectLocation = objectLocation;
	}
	function getDTOArr() {
    var DTOArr = null;
    var count = getCheckBoxCount("subCheck");
    var len=getCheckedBoxCount("subCheck");
    DTOArr = new Array(count);
    for (var k = 0; k < count; k++) {
        var chkbox = document.getElementById("subCheck" + k);
        if (chkbox.checked) {
            var transValue = chkbox.value;
                var tmpArr = null;
                    tmpArr = transValue.split("@@@");
                    dto = new DTO();
                    
<!--                    var j = 0;-->
<!--                    for (prop in dto) {-->
<!--                        if (tmpArr[j] == "EMPTY_CONTENT") {-->
<!--                            tmpArr[j] = "";-->
<!--                        }-->
<!--                        dto[prop] = tmpArr[j];-->
<!--                        j++;-->
<!--                    }-->
					dto["checkLocation"]=tmpArr[0];
					dto["objectCode"]=tmpArr[1];
					dto["objectName"]=tmpArr[2];
					dto["objectLocation"]=tmpArr[3];
                    DTOArr[k] = dto;
        }
    }
    return DTOArr;
}



</script>
</html>