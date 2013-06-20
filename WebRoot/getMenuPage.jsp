<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-10-15
  Time: 10:39:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String resParId = reqParser.getParameter("resParId");
    String action = reqParser.getParameter("act");
    String selectAllValues = (String) request.getAttribute("selectAllValues");
    String menuOption = (String) request.getAttribute("MENU_OPTION");
    String smallMenuOption = (String) request.getAttribute("SMALL_MENU_OPTION");
    String selectedMenuOption=(String) request.getAttribute("SELECTED_MENU_OPTION");
%>
<html>
<head>
    <title>定制我的收藏夹</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
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
<base target="_self">
<body onload="" oncontextmenu="return(false);">
<table class=hei width="100%" border="0" cellpadding="0" cellspacing="0"
       style="border-width:0; BORDER-COLLAPSE: collapse" borderColor="#3366CC">
    <tr height="10">
        <td align="right"></td>
    </tr>
</table>
<form method="post" action="/servlet/com.sino.ams.log.servlet.EtsFavoritesServlet" name="mainForm">
    <input type="hidden" name="myaction" >
    <input type="hidden" name="resParId" value="<%=resParId%>" id="resParId">
    <input type="hidden" name="act" value="<%=action%>">
    <table border="1" cellspacing="0" cellpadding="6" align="center" style="border-width:0; BORDER-COLLAPSE: collapse"
           borderColor="#3366CC">
        <tr>
            <th width="185"><FONT COLOR="0000ff" size='2'>待定制的栏目</FONT></th>
            <th width="60">&nbsp;</th>
            <th width="185"><FONT COLOR="0000ff" size='2'>已定制的栏目(限10项)</FONT></th>
            <th width="50">&nbsp;</th>
        </tr>
        <tr>
            <td>

            </td>
            <td></td>
        </tr>
        <tr>
            <td>
                <select name="menu" style="width:100%" onchange="getSmallMenu()">
                    <%=menuOption%>
                </select>
                <select name="smallMenu" multiple size="10" style="width:185px" id="smallMenu" ondblclick="amsOnAdd()">
                    <%=smallMenuOption%>
                    <%
                        for (int i = 0; i < smallMenuOption.length(); i++) {

                        }
                    %>
                </select>
            </td>
            <td>
                <p align="center">
                    <input type="button" value="增加-->" onClick="amsOnAdd();"><br><br>
                    <input type="button" value="<--删除" onClick="OnRemove();">
                </p>
            </td>
            <td>


                <select name="selectedMenu" multiple size="10" style="width:185px" ondblclick="OnRemove();">
                      <%=selectedMenuOption%>
                </select>


            </td>
            <td align="left">
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
        <tr>
            <td colspan="4">
                <p align="center">
                    <input name="button1" type="button" value="确 定" onClick="OnSubmit();">
                    <input type="button" value="关 闭" onClick="window.close();">
                </p>
            </td>
        </tr>
    </table>
    <input type="hidden" name="selectAllValues" value="<%=selectAllValues%>">
    <%

    %>
</form>
</body>
</html>
<Script language="JavaScript">
function amsOnAdd() {
    var thisform = document.forms(0);
    if (getSelectedCount("smallMenu") + thisform.selectedMenu.options.length > 10) {
        alert('收藏夹内的项目不能超过10项！');
        return;
    }
    MoveTo(thisform.smallMenu, thisform.selectedMenu);
}

function OnRemove() {
    var thisform = document.forms(0);
    //    MoveTo(thisform.selectedMenu, thisform.smallMenu);
    var menu = thisform.selectedMenu;
    for (var i = 0; i < menu.options.length; i++) {
        if (menu.options[i].selected) {
            menu.remove(i);
            i--;
        }
    }
}

function MoveTo(src, dest) {
    for (var i = 0; i < src.options.length; i++) {
        if (src[i].selected) {
            if (check(src[i].text, src[i].value)) {
                dest.options[dest.options.length] = new Option(src[i].text, src[i].value);
                src.options[i] = null;
                i--;
            }
        }
    }
}

function check(text, val) {
    var opt = document.forms[0].selectedMenu;
    for (var i = 0; i < opt.length; i++) {
        if (text == opt.options[i].text && val == opt.options[i].value) {
            return false;
        }
    }
    return true;
}
function selectAll(dest, isSelectAll) {
    for (var i = 0; i < dest.options.length; i++) {
        dest.options[i].selected = isSelectAll;
    }
}
function OnSubmit() {
    var thisform = document.forms(0);
    var sel = thisform.selectedMenu;
    thisform.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
    selectAll(document.mainForm.selectedMenu, true);
    thisform.submit();
}
//上移
function up() {
    var fd_selname = document.forms[0].selectedMenu;
    var temptext = '';
    var tempvalue = '';
    for (j = 0; j < fd_selname.length; j++)
    {
        if (j != 0) {
            if (fd_selname.options[j].selected == true) {
                fd_selname.options[j].selected = false;
                temptext = fd_selname.options[j - 1].text;
                tempvalue = fd_selname.options[j - 1].value;
                fd_selname.options[j - 1].text = fd_selname.options[j].text;
                fd_selname.options[j - 1].value = fd_selname.options[j].value;
                fd_selname.options[j].text = temptext;
                fd_selname.options[j].value = tempvalue;
                fd_selname.options[j - 1].selected = true;
                if (fd_selname.options[j + 1]) {
                    if (fd_selname.options[j + 1].selected == true) {
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
    var fd_selname = document.forms[0].selectedMenu;
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
function getSmallMenu() {
    var smallMenuObj = document.getElementById("smallMenu")  ;

    if (smallMenuObj.options.length > 0) {
        dropOption('smallMenu', false);
    }

    var resParId = document.getElementById("menu").value ;
    var url = "/servlet/com.sino.ams.log.servlet.EtsFavoritesServlet?act=smallMenu&resParId=" + resParId;
    xmlHttp = GetXmlHttpObject(setSmallMenu);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
    xmlHttp.send("a=1");


}
function setSmallMenu() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var flexValues = new Array();
        var descriptions = new Array();
        var resText = xmlHttp.responseText;
        var resArray = resText.parseJSON();
        if (resArray == "ERROR") {
            alert(resText);
        } else {
            if (resArray.length > 0) {

                var littleCategoryObj = document.getElementById("smallMenu");
                //                var emptyOption = new Option("--请选择--", "");
                //                littleCategoryObj.add(emptyOption)
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    var arr = retStr.split("$");
                    var option = new Option(arr[1], arr[0]);
                    littleCategoryObj.add(option)
                }
            }
        }
        xmlHttp = null;
    }
}
</Script>