<%@ page import="com.sino.ams.yj.dto.AmsYjPlanDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
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
    <title>应急预案体系详细信息</title>
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
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>

<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsYjPlanDTO amsYjPlanDTO = (AmsYjPlanDTO) request.getAttribute("AMS_YJ_PLAN");
    boolean isNew = StrUtil.isEmpty(amsYjPlanDTO.getPlanName());
%>
<script type="text/javascript">
    printTitleBar("应急预案体系详细信息");
</script>

<form name="mainFrm" action="/servlet/com.sino.ams.yj.servlet.AmsYjPlanServlet" method="post">
<input type="hidden" name="show" value="show">
<input type="hidden" name="act" value="">
<input type="hidden" name="planId" value="<%=amsYjPlanDTO.getPlanId()%>">
<input type="hidden" name="isExist">
<table border="0" width="100%" id="table1">
<tr>
    <td width="6%" colspan="3" align="right">公司名称：</td>
    <td width="11%"><select class="select_style1" style="width:50%" name="organizationId"><%=amsYjPlanDTO.getOrganizationOption()%>
    </select></td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">预案名称：</td>
    <td width="35%" align="left" height="22"><input class="input_style1" type="text" name="planName" size="40"
                                                    style="width:50%" value="<%=amsYjPlanDTO.getPlanName()%>">&nbsp;<font color="red">*</font> </td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">预案级别：</td>
    <td width="35%" align="left" height="22">
        <select name="planLevel" class="select_style1" style="width:50%">
            <option value="总体预案" <%=amsYjPlanDTO.getPlanLevel().equals("总体预案") ? "selected" : ""%>>总体预案</option>
            <option value="专项预案" <%=amsYjPlanDTO.getPlanLevel().equals("专项预案") ? "selected" : ""%>>专项预案</option>
            <option value="应急方案" <%=amsYjPlanDTO.getPlanLevel().equals("应急方案") ? "selected" : ""%>>应急方案</option>
        </select>
    </td>

</tr>
<tr>
    <td width="6%" colspan="3" align="right">省或地市：</td>
    <td width="35%" align="left" height="22">
        <select name="proCity" class="select_style1" style="width:50%">
            <option value="省" <%=amsYjPlanDTO.getProCity().equals("省") ? "selected" : ""%>>省</option>
            <option value="地市" <%=amsYjPlanDTO.getProCity().equals("地市") ? "selected" : ""%>>地市</option>
        </select>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">预案编号：</td>
    <td width="35%" align="left" height="22">
        <input type="text" name="planNo" size="40"
             class="input_style1" style="width:50%" value="<%=amsYjPlanDTO.getPlanNo()%>">
    </td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">预案类别：</td>
    <td width="35%" align="left" height="22">
        <select name="planType" class="select_style1" style="width:50%">
            <option value="自然灾害类（A）" <%=amsYjPlanDTO.getPlanType().equals("自然灾害类（A）") ? "selected" : ""%>>自然灾害类（A）</option>
            <option value="事故灾难类（B）" <%=amsYjPlanDTO.getPlanType().equals("事故灾难类（B）") ? "selected" : ""%>>事故灾难类（B）</option>
            <option value="公共卫生事件（C）" <%=amsYjPlanDTO.getPlanType().equals("公共卫生事件（C）") ? "selected" : ""%>>公共卫生事件（C）</option>
            <option value="社会安全事件（D）" <%=amsYjPlanDTO.getPlanType().equals("社会安全事件（D）") ? "selected" : ""%>>社会安全事件（D）</option>
            <option value="重大活动类（E）" <%=amsYjPlanDTO.getPlanType().equals("重大活动类（E）") ? "selected" : ""%>>重大活动类（E）</option>
        </select>
    </td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">印发时间：</td>
    <td width="35%" align="left" height="22">
        <input class="input_style1" readonly name="printDate" style="width:50%" value="<%=StrUtil.nullToString(amsYjPlanDTO.getPrintDate())%>"><img
            src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(printDate)">

    </td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">知晓范围(职位/岗位)：</td>
    <td width="35%" align="left" height="22"><input type="text" name="knowPost" size="40"
                                                    class="input_style1"  style="width:50%" value="<%=amsYjPlanDTO.getKnowPost()%>"></td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">知晓人的数量：</td>
    <td width="35%" align="left" height="22"><input type="text" name="quantity" size="40"
                                                    class="input_style1"  style="width:50%" value="<%=amsYjPlanDTO.getQuantity()%>"></td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">预案启动决策人的岗位/职位：</td>
    <td width="35%" align="left" height="22"><input type="text" name="leaderPost" size="40"
                                                    class="input_style1"  style="width:50%" value="<%=amsYjPlanDTO.getLeaderPost()%>"></td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">该预案是否演练过：</td>
    <td width="35%" align="left" height="22">
        <select name="isDrill" class="select_style1" style="width:50%">
            <option value="是" <%=amsYjPlanDTO.getIsDrill().equals("是") ? "selected" : ""%>>是</option>
            <option value="否" <%=amsYjPlanDTO.getIsDrill().equals("否") ? "selected" : ""%>>否</option>
        </select>
    </td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right">备注：</td>
    <td width="35%" align="left" height="22"><input type="text" name="remark" size="40"
                                                    class="input_style1" style="width:50%" value="<%=amsYjPlanDTO.getRemark()%>"></td>
</tr>
<tr>
    <td width="6%" colspan="3" align="right"></td>
    <td width="35%" align="left" height="22">
        <img src="/images/eam_images/save.jpg" alt="点击保存" onClick="do_Save(); return false;">&nbsp;
        <%
            if (!amsYjPlanDTO.getPlanName().equals("")) {
        %>
        <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_Delete(); return false;">&nbsp;
        <%
            }
        %>
        <img src="/images/eam_images/back.jpg" alt="点击取消" onClick="do_Back(); return false;"></td>
</tr>

</table>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script>
    function do_Save() {
        var fieldNames = "planName";
        var fieldLabels = "预案名称";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

//        do_verifyItemName();       判断演练名称是否重复，如若需要验证，只需要启用该方法，需根据实际情况修改

        if (isValid) {
            if (mainFrm.isExist.value == "Y") {
                alert("该预案名称已存在！");
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
        var planId = document.mainFrm.planId.value;
        if (confirm("确认删除该演练信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjPlanServlet?drillId=" + planId;
            document.mainFrm.submit();
        }
    }


    function do_Back() {
        document.mainFrm.planId.value = "";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjPlanServlet";
        document.mainFrm.submit();
    }

    var xmlHttp;
    function do_verifyItemName() {
        var url = "";
        createXMLHttpRequest();
        url = "/servlet/com.sino.ams.yj.servlet.AmsYjPlanServlet?act=verifyTeamName&planId=" + document.mainFrm.planId.value;
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


</SCRIPT>