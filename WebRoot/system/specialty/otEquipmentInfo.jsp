<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-12-20
  Time: 11:58:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.system.specialty.dto.OtherDTO" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    OtherDTO dto = (OtherDTO) request.getAttribute(WebAttrConstant.ETS_ITEM_DTO);
//    SfUserDTO userDTO = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = parser.getParameter("act");

    ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(application);
//    ServletConfigDTO servletConfig=(ServletConfigDTO)getServletConfig();
    String isShare = (String)request.getAttribute(WebAttrConstant.IS_SHARE_OPTION);
%>
<head><title>其它设备详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("其它详细信息")
</script>
<body onload="" topMargin=0 leftMargin=0>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet" name="mainForm" method="post">
    <table border="0" width="100%" style="width:100%">
<tr>
     <td align="center" colspan="2"  width="45%" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该条码已存在！</td>
</tr>
<%
   if(servletConfig.getProvinceCode().equals("40")){
%>
        <tr>
            <td align="right" width="10%">标签号：</td>
            <td width="35%" align="left"><input name="barcode1" type="text" class="noEmptyInput"
                                                value="<%=dto.getBarcode1()%>"       onblur="do_verifybarcodeNo();"
<%
       if (!dto.getSystemid().equals("")) {
%>
       readonly
<%
      }
%>
                                                style="width:46%"></td>
        </tr>
<%
    }else{
%>
        <tr>
            <td align="right" width="10%">标签号：</td>
            <td width="35%" align="left"><input name="barcode1" type="text" class="noEmptyInput" readonly
                                                value="<%=dto.getBarcode1()%>"
                                                style="width:46%"></td>
        </tr>
<%
    }
%>
        <tr>
            <td align="right" width="10%">设备类型：</td>
            <td width="35%" align="left"><input name="itemCategoryDesc" type="text" readonly class="readonlyInput"
                                                value="<%=dto.getItemCategoryDesc()%>"
                                                style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="10%">设备名称：</td>
            <td width="35%" align="left"><input type="text" name="itemName" style="width:46%" class="noEmptyInput"  readonly
                                                value="<%=dto.getItemName()%>"><a
                    class="linka"
                    style="cursor:'hand'"
                    onclick="do_SelectSystemItem();">[…]</a>
            </td>
        </tr>
        <tr>
            <td align="right" width="10%">规格型号：</td>
            <td width="35%" align="left"><input type="text" name="itemSpec" class="readonlyInput" style="width:46%"  readonly
                                                value="<%=dto.getItemSpec()%>">
            </td>
        </tr>
<%--<tr>--%>
    <%--<td align="right" width="10%">数量</td>--%>
    <%--<td width="35%" align="left"><input name="itemQty" type="text" class="noEmptyInput"--%>
                                        <%--value="<%=dto.getItemQty()%>"--%>
                                        <%--style="width:46%"></td>--%>

<%--</tr>--%>
        <tr>
            <td align="right" width="10%">启用日期：</td>
            <td width="35%"><input type="text" name="startDate" class="readonlyInput" value="<%=dto.getStartDate()%>"
                                   style="width:46%"
                                   title="点击选择日期" readonly  onclick="gfPop.fPopCalendar(startDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(startDate)"></td>

        </tr>
        <tr>
            <td align="right" width="10%">供应商：</td>
            <td width="35%"><input type="text" style="width:46%" name="vendorName" class="readonlyInput" readonly
                                   value="<%=dto.getVendorName()%>">
                <a class="linka" style="cursor:'hand'" onclick="do_selectNameVendor();">[…]</a>
            </td>
        </tr>
        <tr>
            <td align="right" width="10%">厂商：</td>
            <td width="35%"><input type="text" style="width:46%" name="manufacturerName" class="readonlyInput" readonly
                                   value="<%=dto.getManufacturerName()%>">
                <a class="linka" style="cursor:'hand'" onclick="do_selectNameManufacturer();">[…]</a>
                <input type="hidden" name="manufacturerId" value="">
            </td>
        </tr>
        <tr>
            <td align="right" width="10%">是否共享：</td>
            <td width="35%"><select type= "text" name="isShare" style="width:50%"><%=isShare%></select></td>
        </tr>
        <tr>
            <td align="right" width="10%">目录代码：</td>
            <td width="35%"><input type="text" style="width:46%" name="contentCode"
                                   value="<%=dto.getContentCode()%>">
            </td>
        </tr>
        <tr>
            <td align="right" width="10%">目录名称：</td>
            <td width="35%"><input type="text" style="width:46%" name="contentName"
                                   value="<%=dto.getContentName()%>">
            </td>
        </tr>
        <tr>
            <td align="right" width="5%">备注：</td>
            <td width="35%"><textarea rows="5" cols="50" name="remark"><%=dto.getRemark()%></textarea></td>
        </tr>
        <tr>
            <td align="right" width="5%">地点简称：</td>
            <td width="35%"><input type="text" style="width:46%" name="workorderObjectName" readonly class="noEmptyInput"
                                   value="<%=dto.getWorkorderObjectName()%>"><a
                    class="linka"
                    style="cursor:'hand'"
                    onclick="do_selectNameAddress();">[…]</a>
            </td>
        </tr>

        <%
            if (dto.getSystemid().equals("")) {
        %>
        <tr>
            <td align="right">所属公司：</td>
            <td width="35%">
                <select name="company" style="width:46%">
                    <%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
                </select>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td align="right"></td>
            <td align="left">

                <img src="/images/eam_images/save.jpg" alt="保存设备信息"
                     onClick="do_savePlan(); return false;">

                <img src="/images/eam_images/back.jpg" alt="返回"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="vendorId" value="<%=dto.getVendorId()%>">
    <input type="hidden" name="systemid" value="<%=dto.getSystemid()%>">
    <input type="hidden" name="addressId" value="<%=dto.getAddressId()%>">
    <input type="hidden" name="prjId" value="<%=dto.getPrjId()%>">
    <input type="hidden" name="itemCode" value="<%=dto.getItemCode()%>">
    <input type="hidden" name="itemCategory" value="<%=dto.getItemCategory()%>">
    <input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">

</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script type="text/javascript">
    function do_savePlan() {
        var fieldNames = "barcode1;itemName;workorderObjectName";
        var fieldLabels = "标签号;设备名称;地点";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            var Names = "barcode1";
            var Labels = "13$标签号$14";
            var Type = LENGTH_VALIDATE;
            var leng = formValidate(Names, Labels, Type);
            if (leng) {
                with (mainForm) {
                    if (systemid.value == "") {
                        act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                        action = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet";
                        submit();
                        var d = window.opener.document.workForm;
                        d.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
                        d.submit();
                        window.close();
                    } else {
                        act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                        action = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet";
                        submit();

                    }

                }
            }
        }
    }
    function do_concel() {
        with (mainForm) {
            window.close();
        }
    }

  function do_verifyLenght(){
            var Names = "barcode1";
            var Labels = "13$标签号$13";
//            var leng = formValidate(Names, Labels, LENGTH_VALIDATE);
          if (!formValidate(Names, Labels, LENGTH_VALIDATE)) {
    }
  }

    function do_SelectSystemItem() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var userPara = "itemCategory=" + mainForm.itemCategory.value;
        //LOOKUP传参数 必须和DTO中一致
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }

    function do_selectNameVendor() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_PURVEY%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
    function do_selectNameAddress() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
    function do_selectNameProject() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
    function do_selectNameManufacturer() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_MANUFACTURER%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }

var xmlHttp;
function do_verifybarcodeNo() {
//    do_verify9()
    do_verifyLenght()
    var url = "";
    var segment1 = document.mainForm.barcode1.value;
    createXMLHttpRequest();
    if (document.mainForm.barcode1.value) {
        url = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet?act=verifyBarcode&barcode=" + document.mainForm.barcode1.value;
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
            if (xmlHttp.responseText == 'Y') {
                document.mainForm.barcode1.style.color = "red";
                document.getElementById("barcodeNo11").style.visibility = "visible"
                document.mainForm.barcode1.focus();
            } else {
                document.mainForm.barcode1.style.color = "black";
                document.getElementById("barcodeNo11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
function do_verify9() {
    var barcode1 = "";
    var barcode = document.mainForm.barcode1.value;
    var barcode2 = barcode.substring(0, 5);
    if (barcode2 == barcode1) {
        var barcode3 = barcode.substring(5);
        if (barcode3.length == 8) {
            for (var i = 0; i < barcode3.length; i++)
            {
                if (barcode3.charAt(i) < '0' || barcode3.charAt(i) > '9')
                {
                    alert("在" + barcode1 + "后输入8位只能是“数字”！");
                    document.mainForm.barcode1.focus();
                    return false;
                }
            }
        } else {
            alert("请在" + barcode1 + "后输入“8位”的数字！");
            document.mainForm.barcode1.focus();
            return false;
        }
    } else {
        alert("请输入" + barcode1 + "加上8位数字！");
        document.mainForm.barcode1.focus();
        return false;
    }
}
</script>