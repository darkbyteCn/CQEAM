<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.resource.dto.SfResDefineDTO" %>
<html>
 <head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>资源详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<jsp:include page="/message/MessageProcess"/>
<body onkeydown="autoExeFunction('do_SaveResource()');">
<%
    SfResDefineDTO resource = new SfResDefineDTO();
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.resource.servlet.ResourceAdjustServlet">

    <table border="0" width="100%" id="table3">
        <tr>
            <td height="22" width="12%" align="right">上级栏目：</td>
            <td height="22" width="75%">
                <select name="resParId" class="select_style1" style="width:100%" onchange="getChildren();">
                    <%=request.getAttribute(WebAttrConstant.RESOURCE_OPTION)%>
                </select>
            </td>
            <td height="22" width="13%"></td>
        </tr>
        <tr>
            <td height="22" width="12%" align="right">子栏目：</td>
            <td height="22" width="75%">
                <select name="resIds" class="input_style1" style="width:100%" size="18" multiple="true">
                </select>
            </td>
            <td height="22" width="13%">
                <table width="55" height="160" border="0" align="left">
                    <tr height="20" valign="top">
                        <td width="42" align="left"><input name="button1" type="button" value="向上" onClick="up();"></td>
                        <td width="10">&nbsp;</td>
                    </tr>
                    <tr height="80">
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr height="20" valign='bottom'>

                        <td><input name="button1" type="button" value="向下" onClick="down();"></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveResource(); return false;">&nbsp;
               <!-- <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_HistoryBack(); return false;"> -->
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
</form>
</body>
</html>
<script type="text/javascript">
    function do_SaveResource() {
        var children = document.getElementById("resIds");
        selectAll("resIds");
        document.mainFrm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainFrm.submit();
    }

    function OnRemove() {
        var thisform = document.forms(0);
        var menu = thisform.resIds;
        for (var i = 0; i < menu.options.length; i++) {
            if (menu.options[i].selected) {
                menu.remove(i);
                i--;
            }
        }
    }
    function up() {
        var fd_selname = document.forms[0].resIds;
        var temptext = '';
        var tempvalue = '';
        for (var j = 0; j < fd_selname.length; j++)
        {
            if (j != 0) {
                if (fd_selname.options[j].selected) {
                    fd_selname.options[j].selected = false;
                    temptext = fd_selname.options[j - 1].text;
                    tempvalue = fd_selname.options[j - 1].value;
                    fd_selname.options[j - 1].text = fd_selname.options[j].text;
                    fd_selname.options[j - 1].value = fd_selname.options[j].value;
                    fd_selname.options[j].text = temptext;
                    fd_selname.options[j].value = tempvalue;
                    fd_selname.options[j - 1].selected = true;
                    if (fd_selname.options[j + 1]) {
                        if (fd_selname.options[j + 1].selected) {
                            fd_selname.options[j].selected == true;
                        }
                        else
                        {
                            fd_selname.options[j].selected == false;
                        }
                    }
                }
            }
        }
    }

    //下移
    function down() {
        var fd_selname = document.forms[0].resIds;
        var temptext = '';
        var tempvalue = '';
        for (j = fd_selname.length - 1; j >= 0; j--)
        {
            try {
                if (j != fd_selname.length - 1) {
                    if (fd_selname.options[j].selected == true) {
                        fd_selname.options[j].selected = false;
                        temptext = fd_selname.options[j + 1].text;
                        tempvalue = fd_selname.options[j + 1].value;
                        fd_selname.options[j + 1].text = fd_selname.options[j].text;
                        fd_selname.options[j + 1].value = fd_selname.options[j].value;
                        fd_selname.options[j].text = temptext;
                        fd_selname.options[j].value = tempvalue;
                        fd_selname.options[j + 1].selected = true;
                        if (fd_selname.options[j - 1]) {
                            if (fd_selname.options[j - 1].selected == true) {
                                fd_selname.options[j].selected == true;
                            }
                            else
                            {
                                fd_selname.options[j].selected == false;
                            }
                        }
                    }
                }
            } catch(e) {
            }
        }
    }
    var xmlHttp;
    function getChildren() {
        var resParId = document.mainFrm.resParId.value;
        if (document.mainFrm.resIds.options.length > 0) {
            dropOption("resIds", false);
        }
        var url = "/servlet/com.sino.ams.system.resource.servlet.ResourceAdjustServlet?act=small&resParId=" + resParId;
        xmlHttp = GetXmlHttpObject(setChildren);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("a=1");
    }
    function setChildren() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var flexValues = new Array();
            var descriptions = new Array();
            var resText = xmlHttp.responseText;
            var resArray = resText.parseJSON();
            if (resArray == "ERROR") {
                alert(resText);
            } else {
                if (resArray.length > 0) {
                    var children = document.getElementById("resIds");
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        var arr = retStr.split("$");
                        var option = new Option(arr[1], arr[0]);
                        children.add(option);
                    }
                }
            }
            xmlHttp = null;
        }
    }

</script>