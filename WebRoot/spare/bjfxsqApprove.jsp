<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<%@ include file="/spare/headerInclude.htm"%>
<html>
<head><title>备件返修申请审批页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<%
    RequestParser rp = new RequestParser();
    rp.transData(request);
    String sectionRight = rp.getParameter("sectionRight");
    String hiddenRight = rp.getParameter("hiddenRight");
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String firstN = "";
    if (sectionRight.equals("0")) {
         firstN = "1";
    }
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjfxsqServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<div id="fxsqHideDiv"
     style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
    <table width=100% height=100%
           style="background-color:#777;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
        <tr><td></td></tr>
    </table>
</div>

<div id="fxsqNextTaskDiv" style="position:absolute; bottom:220px; left:20px; z-index:100; visibility:hidden;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr align=center>
            <td width="20%" style="border:none"></td>
            <td align=center style="border:none">
                <table width="250px" border="1" cellspacing="1" cellpadding="0" bgcolor="#eeeeee"
                       style="border-bottom-color:#ff9900;border-left-color:#ff9900;border-right-color:#ff9900;border-top-color:#ff9900">
                    <tr height=20px><td bgcolor="#5BBBEF" style="border:none"><strong>请选择下一流向：</strong></td></tr>
                    <tr height=20px>
                        <td style="border:none">
                            <select id="fxsqNextTasksSelect" style="width:100%;">
                                <option value="">---请选择--</option>
                                <!--<option value="1">提交PDA扫描</option>-->
                                <option value="2">提交备件部门领导审批</option>
                                <option value="3">提交省公司备件管理员审批</option>
                            </select>
                        </td>
                    </tr>
                    <tr height=20px align=right>
                        <td style="border:none">
                            <%--<a href="#" onclick="do_selectFlow1()"--%>
                            <a href="#" onclick="subPanduan();"
                               style="cursor:pointer;text-decoration:underline;color:blue">[确定]</a>
                            <a href="#" onclick="hideFxsqTasksDiv();cancelFxsqDisable();"
                               style="cursor:pointer;text-decoration:underline;color:blue">[关闭]</a>

                        </td></tr>
                </table>
            </td>
            <td width="50%"></td>
        </tr>
    </table>
</div>

<input type="hidden" name="act" value="">
<input type="hidden" name = "firstN" value = "<%=firstN%>">
<input type="hidden" name="barcode1" value="">
<input type="hidden" name="orgvalue" value="">
<input type="hidden" name="lineId1" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="fromDept" value="<%=amsItemTransH.getFromDept()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">申请公司：</td>
                    <td width="25%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                    <%
                    if(sectionRight.equals("OUT")){
                    %>
                    <td width="9%" align="right">调拨类型：</td>
                    <td ><input type="radio" name="org" id="org1" checked  value="0">只能省公司调拨</td>
                    <td ><input type="radio" name="org" id="org2" value="1">允许其他地市调拨</td>
                    <%}%>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td colspan="2"><%=amsItemTransH.getTransStatusName()%>
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11"><%=amsItemTransH.getRemark()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
<legend>
<!---------------原始------------->
    <%--<%if(sectionRight.equals("1")){%>--%>
   <%--<img src="/images/button/submit.gif" alt="提交" id="img3" onClick="do_scaned();">--%>
    <%--<%}else if(sectionRight.equals("0")){ %>--%>
    <%--<img src="/images/button/submit.gif" alt="提交" id="img3" onClick="do_selectFlow1();">--%>
    <%--<img src="/images/button/repeal.gif" alt="撤消" onClick="do_CancelOrder();">--%>
    <%--<%}else{%>--%>
    <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
    <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
    <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">--%>
    <%--<%}%>--%>
    <%--<%if(sectionRight.equals("OUT")){%>  --%>
    <%--<img src="/images/button/lookno.gif" alt="查询序列号" id="img1" onclick="do_search()">--%>
    <%--<%}%>--%>
     <%--<img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">--%>
    <%--<img src="/images/button/viewFlow.gif" alt="查阅流程" id="img5" onClick="viewFlow();">--%>
    <%--<img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">--%>
    <%--<img src="/images/button/close.gif" alt="关闭" onClick="window.close();">--%>
<!------------------------------>
<%
    if (sectionRight.equals("PRINT")) {
%>
    <img src="/images/button/submit.gif" alt="提交" id="img3" onClick="do_Approve(1);">
    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
<%
    } else {
%>
    <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">
    <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">
<%
    }
%>
    <img src="/images/button/viewFlow.gif" alt="查阅流程" id="img5" onClick="viewFlow();">
    <img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">

</legend>
<% if(sectionRight.equals("OUT") || hiddenRight.equals("SHOW")){%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:90px;left:1px;width:990px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
			<td width="2%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
			<td width="10%" align="center">设备名称</td>
			<td width="10%" align="center">设备型号</td>
			<td width="10%" align="center">设备类型</td>
			<td width="10%" align="center">用途</td>
			<td width="10%" align="center">厂商</td>
			<td width="10%" align="center">故障地点</td>
			<td width="5%" align="center">返修数量</td>
			<td width="6%" align="center">工程待修数</td>
			<td width="6%" align="center">正常待修数</td>
			<td width="5%" align="center">实分数量</td>
			<td style="display:none">隐藏域字段</td>
		</tr>
	</table>
</div>
<%}else {%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:90px;left:1px;width:990px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
			<td width="2%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
			<td width="10%" align="center">设备名称</td>
			<td width="15%" align="center">设备型号</td>
			<td width="10%" align="center">设备类型</td>
			<td width="10%" align="center">用途</td>
			<td width="10%" align="center">厂商</td>
			<td width="10%" align="center">故障地点</td>
			<td width="5%" align="center">返修数量</td>
			<td style="display:none">隐藏域字段</td>
		</tr>
	</table>
</div>
<%}%>
<%--<div style="overflow-y:scroll;height:500px;width:100%;left:1px;top:120px;margin-left:0"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">--%>
<div id="dataDiv" style="overflow:scroll;height:540px;width:1007px;position:absolute;top:113px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
    if (sectionRight.equals("OUT") || hiddenRight.equals("SHOW")) {
%>
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0" style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'" onclick="do_allot(this)">

        <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck10" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId0">
            <input type="hidden" name="holdCount" id="holdCount0">
            <input type="hidden" name="">
        </td>
        <td width="10%" name="itemName" id="itemName10"></td>
        <td width="15%" name="itemSpec" id="itemSpec10"></td>
        <td width="10%" name="itemCategory" id="itemCategory10"></td>
        <td width="10%" name="spareUsage" id="spareUsage10"></td>
        <td width="10%" name="vendorName" id="vendorName10"></td>
        <td width="10%"><input type="text" name="reasons" id="reasons10" value="" class="blueBorder" readonly style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity10" readonly value="" class="blueborderGray" style="width:100%;text-align:right">
        </td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId10" value="">
            <input type="hidden" name="barcode" id="barcode10" value="">
        </td>
    </tr>
    <%
    } else {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
    %>
    <%--<tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" onclick="do_allot(this)">--%>
    <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
        <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck1<%=i%>" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>">
            <input type="hidden" name="holdCount" id="holdCount<%=i%>">
            <input type="hidden" name="">
        </td>
        <td width="10%" name="itemName" id="itemName1<%=i%>"><%=row.getValue("ITEM_NAME")%>
        </td>
        <td width="10%" name="itemSpec" id="itemSpec1<%=i%>"><%=row.getValue("ITEM_SPEC")%>
        </td>
        <td width="10%" name="itemCategory" id="itemCategory1<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
        </td>
        <td width="10%" name="spareUsage" id="spareUsage1<%=i%>"><%=row.getValue("SPARE_USAGE")%>
        </td>
        <td width="10%" name="vendorName" id="vendorName1<%=i%>"><%=row.getValue("VENDOR_NAME")%>
        </td>
        <td width="10%" align="center"><input type="text" name="reasons1" id="reasons<%=i%>" value="<%=row.getValue("REASONS")%>" class="blueBorder" readonly style="width:100%;text-align:left"></td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" readonly value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right"></td>
        <%
          if (hiddenRight.equals("SHOW")) {
        %>
        <td width="6%" align="center"><input type="text" name="proQty" id="proQty<%=i%>" value="<%=row.getValue("PRO_QTY")%>" class="blueBorder" readonly style="width:100%;text-align:right"></td>
        <td width="6%" align="center"><input type="text" name="ordQty" id="ordQty<%=i%>" value="<%=row.getValue("ORD_QTY")%>" class="blueBorder" readonly style="width:100%;text-align:right"></td>
        <%
          } else {
        %>
        <td width="6%" align="center"><input type="text" name="proQty" id="proQty<%=i%>" value="<%=row.getValue("PRO_QTY")%>" class="blueborderYellow" style="width:100%;text-align:right" onblur="validateData(this);"></td>
        <td width="6%" align="center"><input type="text" name="ordQty" id="ordQty<%=i%>" value="<%=row.getValue("ORD_QTY")%>" class="blueborderYellow" style="width:100%;text-align:right" onblur="validateData2(this);"></td>
        <%}%>
        <td width="5%"><input type="text" name="actualQty" value="<%=row.getValue("ACTUAL_QTY")%>" readonly  class="blueborderGray" style="width:100%" onclick="do_allot(<%=row.getValue("BARCODE")%>,<%=row.getValue("LINE_ID")%>,<%=row.getValue("QUANTITY")%>)"></td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId1<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input type="hidden" name="barcode" id="barcode1<%=i%>" value="<%=row.getValue("BARCODE")%>"><input type="hidden" name="spareId" id="spareId<%=i%>" value="<%=row.getValue("SPARE_ID")%>" >
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
} else {
%>
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0" style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'">

        <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
        </td>
        <td width="10%" name="itemName" id="itemName0"></td>
        <td width="15%" name="itemSpec" id="itemSpec0"></td>
        <td width="10%" name="itemCategory" id="itemCategory0"></td>
        <td width="10%" name="spareUsage" id="spareUsage0"></td>
        <td width="10%" name="vendorName" id="vendorName0"></td>
        <td width="10%"><input type="text" name="reasons" id="reasons0" value="" class="blueBorder" readonly style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity0"   <%if(sectionRight.equals("0")){%>onblur="checkQty();"<%}else{%> readonly<%}%> value="" class="blueborderYellow"  onblur="" style="width:100%;text-align:right">
        </td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId0" value="">
            <input type="hidden" name="barcode" id="barcode0" value="">
        </td>
    </tr>
    <%
    } else {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
    %>
    <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'">
        <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
        </td>
        <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
        </td>
        <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
        </td>
        <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
        </td>
        <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=row.getValue("SPARE_USAGE")%>
        </td>
        <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
        </td>
        <td width="10%" name="reasons" id="reasons<%=i%>"><%=row.getValue("REASONS")%>
        </td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" <%if(sectionRight.equals("0")){%>onblur="checkQty();"<%}else{%> readonly<%}%> value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
        </td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
            <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
            <input type="hidden" name="proQty" id="proQty<%=i%>" value="<%=row.getValue("PRO_QTY")%>">
            <input type="hidden" name="ordQty" id="ordQty<%=i%>" value="<%=row.getValue("ORD_QTY")%>">
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
    }
%>
</div>
</fieldset>
</form>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
    function do_Approve(flag) {
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = document.mainForm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "";
        if (<%=sectionRight.equals("OUT")%>) {
            if (validateQty()) {
                paramObj.submitH = "submitH()";
                assign(paramObj);
            } else {
                alert("工程待修库与正常待修库之和必须等于返修数量！");
            }
        } else {
            paramObj.submitH = "submitH()";
            assign(paramObj);
        }

    }

    function validateQty() {
        var hasError = true;
        var fieldName = "proQty";
        var fieldNames = document.getElementsByName(fieldName);
        for(var i = 0; i < fieldNames.length; i++){
            var proQty = document.getElementById("proQty" + i).value;
            var ordQty = document.getElementById("ordQty" + i).value;
            var quantity = document.getElementById("quantity" + i).value;
            if (Number(proQty) + Number(ordQty) != Number(quantity)) {
                hasError = false;
                document.getElementById("proQty" + i).focus();
                break;
            }
        }
        return hasError;
    }

    function submitH() {
        var actVal = "";
        if (mainForm.firstN.value == "1") {
            actVal = "firstN";
        } else {
            actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
        }
        document.mainForm.act.value = actVal;
         mainForm.firstN.value = "";
        document.mainForm.submit();
    }

   function do_scaned() {
        if(confirm("请确认是已扫描?")){
            var paramObj = new Object();
            paramObj.orgId = "<%=user.getOrganizationId()%>";
            paramObj.useId = "<%=user.getUserId()%>";
            paramObj.groupId = document.mainForm.fromDept.value;
            paramObj.procdureName = "备件返修申请流程";
            paramObj.flowCode = "";
            paramObj.submitH = "submitH2()";
            assign(paramObj);
        }
    }

   function submitH2() {
        var actVal = "";
        actVal = "SCANED";
        document.mainForm.act.value = actVal;
        document.mainForm.submit();
    }


    function do_select() {
//        var url = "/workorder/bts/upFile.jsp";
        var url = "/workorder/bts/upFile.jsp";
        var dialogStyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
        var reval = window.showModalDialog(url, "", dialogStyle);
    }

   function do_selectFlow1(){
//        alert("3212111");
//      var reval = document.getElementById("fxsqNextTasksSelect").value;
       disablefxsqDocument();
       showNextfxsqTasksDiv();
//       subPanduan(reval);
//       alert("");

//       subPanduan();

   }

function  subPanduan(){//判断是否提交省公司备品备件管理员审批
    var reval = document.getElementById("fxsqNextTasksSelect").value;
    if (reval == "") {
        alert("请先选择下一任务流向，然后点击确定！");
        return;
    }
    if(reval=="1"){//提交pda扫描
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = document.mainForm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "SCANING";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    } else if(reval=="2") {  //提交备件部门领导审批
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = document.mainForm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "2";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    } else if (reval=="3") { //提交省公司备件管理员审批
        do_verifyTransNo();
       }
    hideFxsqTasksDiv();
    cancelFxsqDisable();
}


  function province(){
       var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = document.mainForm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "1";
        paramObj.submitH = "submitH()";
        assign(paramObj);
  }

  function hideFxsqTasksDiv() {
    var nextTaskDiv = document.getElementById("fxsqNextTaskDiv");
    nextTaskDiv.style.visibility = 'hidden';
}

  function cancelFxsqDisable() {
    var hideDiv = document.getElementById("fxsqHideDiv");
    hideDiv.style.visibility = 'hidden';
}

    function do_Approve2() {
        addApproveContent(10);
        mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        mainForm.submit();
    }

    function do_Approve1() {
        document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        document.mainForm.submit();
    }

    function do_allot(barcode,lineId,qty) {
        if (<%=hiddenRight.equals("OUT")%>) {
            var org = (document.getElementById("org1").checked)?0:1;
            var transId = mainForm.transId.value;
            var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "ALLOT" + "&barcode1=" + barcode + "&transId=" + transId + "&lineId1=" + lineId+"&sqty="+qty+"&orgvalue="+org;
            var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
            window.open(url, "bjOrder", popscript);
        }
    }
//    function do_allot(obj) {
//        var proQty = obj.cells[8].childNodes[0].value;
//        var ordQty = obj.cells[9].childNodes[0].value;
//        if (proQty && ordQty) {
//            var barcode;
//            var transId;
//            var lineId;
//            var qty;
//            var org;
//            org=(document.getElementById("org1").checked)?0:1;
//            barcode = obj.cells[11].childNodes[1].value;
//            transId = mainForm.transId.value;
//            lineId = obj.cells[11].childNodes[0].value;
//            qty=obj.cells[7].childNodes[0].value;
//            var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "ALLOT" + "&barcode1=" + barcode + "&transId=" + transId + "&lineId1=" + lineId+"&sqty="+qty+"&orgvalue="+org;
//            var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
//            window.open(url, "bjOrder", popscript);
//        }
//    }

    function validateData(obj) {
        var validate = false;
        var fieldNames = "proQty";
        var fieldLabels = "工程待修数";
        var validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
//        do_change(obj);
        return validate;
    }

    function validateData2(obj) {
        var validate = false;
        var fieldNames = "ordQty";
        var fieldLabels = "正常待修数";
        var validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
//        do_change(obj);
        return validate;
    }

    function do_change(obj) {
        var id = obj.id.substring(7, obj.id.length);
        var proQty = document.getElementById("proQty" + id).value;
        var ordQty = document.getElementById("ordQty" + id).value;
        var quantity = document.getElementById("quantity" + id).value;
        if (Number(proQty) + Number(ordQty) != Number(quantity)) {
            alert("工程待修库与正常待修库之和必须等于返修数量！");
            document.getElementById("proQty" + id).value = "";
            document.getElementById("ordQty" + id).value = "";
        }
    }

    function do_search(){
        var transId;
        transId = mainForm.transId.value;
        var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "SEARCH" + "&transId=" + transId ;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "bjOrder", popscript);
    }



//将整个页面Disabled
//用来存放原始属性
function disablefxsqDocument() {
    //disable整个页面
    var hideDiv = document.getElementById("fxsqHideDiv");
    hideDiv.style.visibility = 'visible';
}

//显示下一流向的DIV
function showNextfxsqTasksDiv() {
    var nextTaskDiv = document.getElementById("fxsqNextTaskDiv");
    nextTaskDiv.style.position = 'absolute';

    if (event) {//有可能是没有这个值
        mouse_x = document.body.scrollLeft + event.clientX;
        mouse_y = document.body.scrollTop + event.clientY;
    } else {
        mouse_x = document.body.scrollLeft + 50;
        mouse_y = document.body.scrollTop + 50;
    }
    nextTaskDiv.style.left = mouse_x;
    nextTaskDiv.style.top = mouse_y;
    nextTaskDiv.style.visibility = 'visible';
}


var xmlHttp;
function do_verifyTransNo() {
    var url = "";
    createXMLHttpRequest();
    if (document.mainForm.transId.value) {
        url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=DO_EQUALS&transId=" + document.mainForm.transId.value;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

function createXMLHttpRequest() {     //创建XMLHttpRequest对象
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
//            alert(xmlHttp.responseText);
            if (xmlHttp.responseText == 'Y') {
                province();
            } else {
                province();
//                alert("请确认申请数量和扫描数量！");
                //                  alert("yes");

            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function checkQty() {
    var validate = false;
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    var validateType = EMPTY_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
    if (validate) {
        validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
    }
    return validate;
}


function do_print() {
   var headerId=document.mainForm.transId.value;
   var url="/servlet/com.sino.ams.spare.servlet.SpareDiffServlet?act=print&transId="+headerId;
   var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
   window.open(url, "", style);
}

function do_CancelOrder() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		mainForm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
		mainForm.submit();
	}
}

</script>
</html>