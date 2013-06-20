<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.yj.dto.AmsYjUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.yj.constant.YjLookUpConstant" %>
<%@ include file="/newasset/headerInclude.htm" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>应急保障队伍详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsYjUserDTO amsYjUserDTO = (AmsYjUserDTO) request.getAttribute("AMS_YJ_USER");
    boolean isNew = StrUtil.isEmpty(amsYjUserDTO.getUserName());
%>
<script type="text/javascript">
    printTitleBar("应急保障人员详细信息");
</script>

<form name="mainFrm" action="/servlet/com.sino.ams.yj.servlet.AmsYjUserServlet" method="post">
    <input type="hidden" name="show" value="show">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="userId" value="<%=amsYjUserDTO.getUserId()%>">
    <input type="hidden" name="isExist">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="6%" colspan="3" align="right">公司名称：</td>
            <td width="11%"><select class="select_style1" style="width:50%" name="organizationId"><%=amsYjUserDTO.getOrganizationOption()%>
            </select></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">队伍ID：</td>
            <td width="35%" align="left" height="22">
             <input class="input_style1" type="text" name="teamId" size="40" readonly  style="width:50%" value="<%=amsYjUserDTO.getTeamId()%>">
             <a href="#"  onClick="SelectTeam(); "  title="点击选择队伍名称" class="linka">[…]</a>
            </td>

        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">姓名：</td>
            <td width="35%" align="left" height="22"><input type="text" name="userName" size="40"
                                                            class="input_style1" style="width:50%" value="<%=amsYjUserDTO.getUserName()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">手机：</td>
            <td width="35%" align="left" height="22"><input type="text" name="tel" size="40"
                                                            class="input_style1" style="width:50%" value="<%=amsYjUserDTO.getTel()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">职务：</td>
            <td width="35%" align="left" height="22"><input type="text" name="post" size="40"
                                                            class="input_style1" style="width:50%" value="<%=amsYjUserDTO.getPost()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">专业：</td>
            <td width="35%" align="left" height="22"><input type="text" name="category" size="40"
                                                            class="input_style1" style="width:50%" value="<%=amsYjUserDTO.getCategory()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">属性：</td>
            <td width="35%" align="left" height="22">
                <select name="attribute" class="select_style1" style="width:50%" >
                    <option value="自维" <%=amsYjUserDTO.getAttribute().equals("自维")?"selected":""%>>自维</option>
                    <option value="代维" <%=amsYjUserDTO.getAttribute().equals("代维")?"selected":""%>>代维</option>
                </select>
             </td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">备注：</td>
            <td width="35%" align="left" height="22"><input type="text" name="remark" size="40"
                                                            class="input_style1" style="width:50%" value="<%=amsYjUserDTO.getRemark()%>"></td>
        </tr>

        <tr>
            <td width="6%" colspan="3" align="right">&nbsp;</td>
            <td width="35%" align="left" height="22">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_Save(); return false;">&nbsp;
                <%
                    if (!amsYjUserDTO.getUserName().equals("")) {
                %>
                <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_Delete(); return false;">&nbsp;
                <%
                    }
                %>
                <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
</form>
</body>
</html>
<script>
    function do_Save() {
      var teamId1 = document.getElementsByName("teamId")[0];
      
        if(teamId1.value==null ||teamId1.value==""){
           alert("请选择队伍名称！");
           teamId1.focus();
           return false; 
        }   
        
        var fieldNames = "userName";
        var fieldLabels = "姓名";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

//        do_verifyItemName();       判断队伍名称是否重复，如若需要验证，只需要启用该方法

        if (isValid) {
            if (mainFrm.isExist.value == "Y") {
                alert("该人已存在！");
                return;
            }
            var action = "<%=WebActionConstant.CREATE_ACTION%>";
        <%if(isNew){%>
            document.mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        <%}else{%>
            document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        <%}%>
            document.mainFrm.submit();
        }
    }

    function do_Delete() {
        var userId = document.mainFrm.userId.value;
        if (confirm("确认删除该人员吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjUserServlet?userId=" + userId;
            document.mainFrm.submit();
        }
    }


    function do_Back() {
        document.mainFrm.userId.value = "";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjUserServlet";
        document.mainFrm.submit();
    }

    var xmlHttp;
    function do_verifyItemName() {
        var url = "";
        createXMLHttpRequest();
        url = "/servlet/com.sino.ams.yj.servlet.AmsYjTeamServlet?act=verifyTeamName&userId=" + document.mainFrm.userId.value;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, false);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }

    function createXMLHttpRequest() {//创建XMLHttpRequest对象
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
                if (xmlHttp.responseText == 'Y') {
                    document.mainFrm.isExist.value = 'Y';
                } else {
                    document.mainFrm.isExist.value = 'N';
                }
            } else {
                alert(xmlHttp.status);
            }
        }
    }

      function SelectTeam() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_TEAM%>&organizationId="+document.mainFrm.organizationId.value;
        var popscript = "dialogWidth:47.5;dialogHeight:28;center:yes;status:no;scrollbars:no";
        var teamNames = window.showModalDialog(url, null, popscript);
        if (teamNames) {
            var vendorName = null;
            for (var i = 0; i < teamNames.length; i++) {
                teamName = teamNames[i];
                dto2Frm(teamName, "mainFrm");
            }
        }
    }
    

</SCRIPT>