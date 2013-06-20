<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-8-25
  Time: 15:30:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
<head><title>成本中心</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
     <script language="javascript" src="/WebLibary/js/calendar.js"></script>
     <script language="javascript" src="/WebLibary/js/datepicker.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>

</head>
<%
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("MIS_HEADER");
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
    ServletConfigDTO configDto = SessionUtil.getServletConfigDTO(request);
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.newasset.servlet.AmsCostMatch" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("成本中心")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act" value="<%=act%>">
    <table width="100%" class="queryHeadBg" id="tb" border = "0">
        <tr>
            <td width="15%" align="right">成本中心：</td>
            <td width="25%"><input type="text" class="blueBorder" name="prjName" style="width:80%" value ="<%=dto.getPrjName()%>"><a href="#"  onClick="SelectCountyCode();"  class="linka">[…]</a></td>
            <td width = "60%" align="right">
                <img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel">
                <img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()">
            </td>
        </tr>
    </table>
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:44px;left:0px;width:487px">
        <table width="100%" border="1" class="headerTable"  title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr height="22">
                <td align="center" width="2%"></td>
                <td align="center" width="9%">公司</td>
                <td align="center" width="5%">成本中心代码</td>
                <td align="center" width="10%">成本中心名称</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:75%;width:504px;position:absolute;top:67px;left:0px;height:530px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	if (rows != null && rows.getSize() > 0) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td height="22" width="2%" align="center"><input type="radio" name="countyCode" value="<%=row.getValue("COUNTY_CODE")%>;<%=row.getValue("COMPANY_CODE")%>"></td>
                <td height="22" width="9%"><input readonly class="finput2" value="<%=row.getValue("COMPANY")%>"></td>
                <td height="22" width="5%"><input readonly class="finput" value="<%=row.getValue("COUNTY_CODE")%>"></td>
                <td height="22" width="10%"><input readonly class="finput" value="<%=row.getValue("COUNTY_NAME")%>"></td>
            </tr>

<%
		}
	}
%>
        </table>
    </div>
</form>
<div style="position:absolute;top:600px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function init() {
        if (document.getElementById("dataTable")) {
            document.getElementById("scrollTb").height = document.getElementById("dataTable").offsetHeight;
        }
    }
    function do_query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.QUERY_ACTION%>"
        document.forms[0].submit();
    }

    function do_export() {
        document.forms[0].act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
        document.forms[0].submit();
    }

    window["onscroll"] = function() {
        if (document.getElementById('scrollDiv')) {
            //    if(/safari/i.test(navigator.userAgent)){
            document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
            //    }else{
            //        document.getElementById('scrollDiv').style.left=document.documentElement.scrollLeft+document.getElementById("scrollDiv").offsetHeight/3+"px";
            //    }
        }
    }
    window["onresize"] = function() {
        if (document.getElementById('scrollDiv')) {
            document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
        }
    }

function SelectCountyCode(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_COUNTY%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
       document.forms[0].prjName.value = vendorNames[0].countyName;
    }
}
</script>

</html>
