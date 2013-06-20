<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>代维公司职责维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">    
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

<style>
    * {
        margin: 0;
        padding: 0;
        font-size: 12px;
    }

    html, body {
        height: 100%;
        width: 100%;
    }

    #marqueetipMsg {
        border: 1px solid #369;
        width: 300px;
        height: 50px;
        background: #e2ecf5;
        z-index: 1000;
        position: absolute;
        display: none;
    }

    #marqueetipMsg h4 {
        height: 20px;
        background: #369;
        color: #fff;
        padding: 5px 0 0 5px;
    }

    #marqueetipMsg h4 span {
        float: left;
    }

    #marqueetipMsg p {
        padding: 12px 0 0 30px;
    }

    #marqueetipMsg p select {
        width: 170px;
        margin-left: 5px;
    }

    #marqueetipMsg p a {
        margin-left: 40px;
    }
</style>
</head>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_SearchResource()');">

<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String[] subChecks = reqParser.getParameterValues("subCheck");
    if (subChecks != null) {
        for (int i = 0; i < subChecks.length; i++) {
            System.out.print(subChecks[i] + ";");
        }
    }
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.trust.servlet.AmsMaintainResponsibilityServlet">
    <%=WebConstant.WAIT_TIP_MSG%>

    <div id="fxsqHideDiv"
         style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
        <table width=100% height=100%
               style="background-color:#777;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
            <tr>
                <td></td>
            </tr>
        </table>
    </div>
    <div id="marqueetipMsg" style="position:absolute; display:block; left:25%;top:20%;marginTop:-75px; marginLeft:-150px; z-index:30; visibility:hidden">
        <h4><span>代维公司</span></h4>

        <p><label>代维公司：</label><select name="companyId" style="border:1px solid #ccc;height:16px;" onmouseover="this.style.border='1px solid #f60'"
                                       onfoucs="this.style.border='1px solid #f60'"
                                       onblur="this.style.border='1px solid #ccc'"><%=request.getAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION)%>
        </select></p>

        <p><a href="#" onclick="do_CreateMatch()">[确定]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cancelDis();">[关闭]</a></p>
    </div>
    <script type="text/javascript">
        printTitleBar("代维公司职责维护")
    </script>
    <table border="0" width="100%" id="table1" >
        <tr>
            <%--<td width="10%" align="right">所在区县：</td>--%>
            <%--<td width= "10%"><select name="countyCode" style="width:80%"><%=(String)request.getAttribute(WebAttrConstant.COUNTY_OPTION)%>--%>
            <%--</select></td>--%>
            <td align="right" width="8%">地点编号：</td>
            <td width="17%"><input type="text" name="workorderObjectName" value="<%=reqParser.getParameter("objectName")%>" class="input_style1" style="width:75%">
                <input type="hidden" name="objectName" value="<%=reqParser.getParameter("objectName")%>">
                <a class="linka" style="cursor:'hand'"  onclick="do_selectName('<%=LookUpConstant.LOOK_UP_BTS%>');">[…]</a>
            </td>
            <td align="right" width="8%">代维公司：</td>
            <td width="20%"><select class="select_style1" style="width:100%" name="companyId2"><%=(String) request.getAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION)%>
            </select></td>
            <td width="8%" align="right">是否关联：</td>
            <td width="8%"><select name="isall" class="select_style1" style="width:100%">
                <option value="">请选择</option>
                <option value="1" <%=reqParser.getParameter("isall").equals("1") ? "selected" : ""%>>--是--</option>
                <option value="0" <%=reqParser.getParameter("isall").equals("0") ? "selected" : ""%>>--否--</option>
            </select></td>
            <td width="30%" align="center">&nbsp;
              <img src="/images/eam_images/search.jpg" alt="查询职责"    onClick="do_SearchCompany(); return false;">
              <img src="/images/eam_images/relation.jpg"   alt="点击关联"    onClick="do_relation(); return false;">
              <img src="/images/eam_images/delete.jpg" alt="删除关联"    onClick="do_DeleteMatch(); return false;">
              <img src="/images/eam_images/export.jpg" alt="导出EXCEL"  onclick="do_Export(); return false;">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "地点编号", "地点简称", "所属区县", "代维公司");
        var widthArr = new Array("5%", "10%", "35%", "20%", "30%");
        printTableHead(columnArr, widthArr);
    </script>
   
</form>
 <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:305px; width:100%;;left:1px;margin-left:0px">
        <table width="100%" border="1" >
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onClick="executeClick(this)">
                <td width="5%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%>
                </td>
                <td height="20" width="10%" align="center"><%=row.getValue("OBJECT_CODE")%>
                </td>
                <td height="20" width="35%" align="center"><%=row.getValue("OBJECT_NAME")%>
                </td>
                <td height="20" width="20%" align="center"><%=row.getValue("COUNTY_NAME")%>
                </td>
                <td height="20" width="30%" align="center"><%=row.getValue("COMPANY_NAME")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
<%
    }
%>
<div><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
<script language="javascript">

    function do_SearchCompany() {        //查询条件查询
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.objectName.value = document.mainFrm.workorderObjectName.value;
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_CreateMatch() {        //新增关联

        if (document.mainFrm.companyId.value == "") {
            alert("代维公司不许为空");
            return;
        }
        if (getCheckedBoxCount("subCheck") == 0) {
            alert("请选择要匹配的地点");
            return;
        }
        mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        mainFrm.submit();
    }
    function do_DeleteMatch() {          //解除关联
        if (getCheckedBoxCount("subCheck") == 0) {
            alert("请选择要删除的匹配");
            return;
        }
        if (confirm("确认删除该吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.submit();
        }
    }
    function do_selectName(lookUpName) {
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (objs) {
            var obj = null;
            for (var i = 0; i < objs.length; i++) {
                obj = objs[i];
                dto2Frm(obj, "mainFrm");
            }
        } else {
            //            document.mainFrm.objectName.value ="";
            document.mainFrm.workorderObjectName.value = "";
        }
    }

    function do_relation() {

        if (getCheckedBoxCount("subCheck") == 0) {
            alert("请选择要匹配的地点");
            return;
        }
        document.getElementById("fxsqHideDiv").style.visibility = "visible";
        document.getElementById("marqueetipMsg").style.visibility = "visible";
    }

   function do_Export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }

    //取消Disable
    function cancelDis() {
        var hideDiv = document.getElementById("marqueetipMsg");
        hideDiv.style.visibility = 'hidden';
        document.getElementById("fxsqHideDiv").style.visibility = "hidden";
    }
</script>
</html>