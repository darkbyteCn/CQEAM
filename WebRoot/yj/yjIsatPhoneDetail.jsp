<%@ page import="com.sino.ams.yj.constant.YjLookUpConstant" %>
<%@ page import="com.sino.ams.yj.dto.AmsYjIsatphoneDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
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
    <title>卫星电话详细信息</title>
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
    <script type="text/javascript" src="/yj/yjLookUp.js"></script>
</head>

<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    RequestParser reqParser = new RequestParser();
	reqParser.transData(request);
    AmsYjIsatphoneDTO amsYjIsatphoneDTO = (AmsYjIsatphoneDTO) request.getAttribute("AMS_YJ_ISATPHONE");
    boolean isNew = StrUtil.isEmpty(amsYjIsatphoneDTO.getIsatphoneName());
%>
<script type="text/javascript">
    printTitleBar("卫星电话详细信息");
</script>

<form name="mainFrm" action="/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet" method="post">
    <input type="hidden" name="show" value="show">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="isatphoneId" value="<%=amsYjIsatphoneDTO.getIsatphoneId()%>">
    <input type="hidden" name="isExist">
    <input type="hidden" name="resourceId" value="<%=amsYjIsatphoneDTO.getResourceId()%>">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="6%" colspan="3" align="right">公司名称：</td>
            <td width="11%"><select class="select_style1" style="width:50%" name="organizationId"><%=amsYjIsatphoneDTO.getOrganizationOption()%>
            </select></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">储备物资名称：</td>
            <td width="35%" align="left" height="22"><input type="text" name="isatphoneName" size="40" 
                                                            class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getIsatphoneName()%>">&nbsp;<font color="red">*</font> </td>

        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">类别：</td>
            <td width="35%" align="left" height="22">
                <select name="isatphoneType" class="select_style1" style="width:50%">
                    <option value="全球星" <%=amsYjIsatphoneDTO.getIsatphoneType().equals("全球星") ? "selected" : ""%>>全球星</option>
                    <option value="海事" <%=amsYjIsatphoneDTO.getIsatphoneType().equals("海事") ? "selected" : ""%>>海事</option>
                    <option value="铱星" <%=amsYjIsatphoneDTO.getIsatphoneType().equals("铱星") ? "selected" : ""%>>铱星</option>
                    <option value="欧星" <%=amsYjIsatphoneDTO.getIsatphoneType().equals("欧星") ? "selected" : ""%>>欧星</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">型号：</td>
            <td width="35%" align="left" height="22"><input type="text" name="isatphoneModel" size="40"
                                                           class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getIsatphoneModel()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">存储地点或单位：</td>
            <td width="35%" align="left" height="22"><input type="text" name="isatphoneAddress" size="40"
                                                           class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getIsatphoneAddress()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">电话号码：</td>
            <td width="35%" align="left" height="22"><input type="text" name="tel" size="40"
                                                          class="input_style1" class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getTel()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">购置时间：</td>
            <td width="35%" align="left" height="22"><input type="text" name="buyingTime" size="40"
                                                           class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getBuyingTime()%>"></td>
        </tr>

        <tr>
            <td width="6%" colspan="3" align="right">资产原值（万元）：</td>
            <td width="35%" align="left" height="22"><input type="text" name="cost" size="40"
                                                           class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getCost()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">使用次数（年）：</td>
            <td width="35%" align="left" height="22"><input type="text" name="usedNumber" size="40"
                                                           class="input_style1" style="width:50%" value="<%=amsYjIsatphoneDTO.getUsedNumber()%>"></td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right">应急战备资源：</td>
            <td width="35%" align="left" height="22"><input type="text" name="equipmentName" size="40"
                                                            value="<%=amsYjIsatphoneDTO.getEquipmentName()%>"
                                                            style="width:50%;cursor:hand" readonly title="点击选择资源" class="readonlyInput"
                                                            onclick="choose_resource();">
            </td>
        </tr>
        <tr>
            <td width="6%" colspan="3" align="right"></td>
            <td width="35%" align="left" height="22">
                <img src="/images/eam_images/save.jpg" alt="点击保存" onClick="do_Save(); return false;">&nbsp;
                <%
                    if (!amsYjIsatphoneDTO.getIsatphoneName().equals("")) {
                %>
                <img src="/images/eam_images/delete.jpg" alt="点击删除" onClick="do_Delete(); return false;">&nbsp;
                <%
                    }
                %>
                <img src="/images/eam_images/back.jpg" alt="点击取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
</form>
</body>
</html>
<script>
    function do_Save() {
       var isatphoneName = document.getElementsByName("isatphoneName")[0]; 
       var cost =document.getElementsByName("cost")[0];
       var usedNumber =document.getElementsByName("usedNumber")[0];
        if(isatphoneName.value==null || isatphoneName.value==""){
          alert("储备物资名称不能为空！");
          isatphoneName.focus();
          return false;
        
        }
           var reg = /^[0-9]+$/;   
	      if(cost.value!=""&&!reg.test(cost.value)){   
	        alert('"资产原值" 请输入数字！');   
	        cost.value = "";   
	        cost.focus();   
	        return false;   
	      }  
	      if(usedNumber.value!=""&&!reg.test(usedNumber.value)){   
	        alert('"使用次数" 请输入数字！');   
	        usedNumber.value = "";   
	        usedNumber.focus();   
	        return false;   
	      }  
    
        var fieldNames = "isatphoneName";
        var fieldLabels = "储备物资名称";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

//        do_verifyItemName();       判断名称是否重复，如若需要验证，只需要启用该方法 并根据实际情况修改servlet

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
        var isatphoneId = document.mainFrm.isatphoneId.value;
        if (confirm("确认删除该人员吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?isatphoneId=" + isatphoneId;
            document.mainFrm.submit();
        }
    }


    function do_Back() {
        document.mainFrm.isatphoneId.value = "";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet";
        document.mainFrm.submit();
    }

    var xmlHttp;
    function do_verifyItemName() {
        var url = "";
        createXMLHttpRequest();
        url = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?act=verifyTeamName&isatphoneId=" + document.mainFrm.isatphoneId.value;
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
    function choose_resource() {
        var lookUpName = "<%=YjLookUpConstant.LOOK_UP_RESOURCE%>";
        var dialogWidth = 50.6;
        var dialogHeight = 30;
        var retValue = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (retValue) {
            document.mainFrm.resourceId.value = retValue[0].resourceId;
            document.mainFrm.equipmentName.value = retValue[0].equipmentName;
        }
    }

</SCRIPT>