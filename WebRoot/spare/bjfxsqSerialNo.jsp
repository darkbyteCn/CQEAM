<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-20
  Time: 10:14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-8
  Time: 8:21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件序列号查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>
<%

%>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<body leftmargin="0" topmargin="0" >
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjfxsqServlet" method="post">

    <table class="headertable" width="100%" border="1">
        <tr height="20">
            <td width="10%" align="center">序列号</td>
            <td width="15%" align="center">备件名称</td>
            <td width="8%" align="center">部件号</td>
            <td width="10%" align="center">规格型号</td>
            <td width="8%" align="center">设备类别</td>
            <td width="8%" align="center">厂商名称</td>
        </tr>
    </table>
    <table width="100%" border="1" id="mtlTable" borderColor="#9FD6FF">
        <%
            RowSet rows = (RowSet) request.getAttribute("SERIAL_NO");
            int count = 0;
            int quty = 0;
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>


        <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#ffffff'"
                >
            <td width="10%" name="company" id="company<%=i%>"><%=row.getValue("SERIAL_NO")%>
            </td>
            <td width="15%" name="company" id="company<%=i%>"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                                 value="<%=row.getValue("BARCODE")%>"
                                                 class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="10%" name="company" id="company<%=i%>"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="8%"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                  value="<%=row.getValue("SPARE_USAGE")%>"
                                  class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="8%"><input type="text" name="holdCount" id="holdCount<%=i%>" readonly
            <%-- <%if(sectionRight.equals("OUT")){%> onclick="get_bacode(this)"<%}%>--%>
                                  value="<%=row.getValue("VENDOR_NAME")%>"
                                  style="width:100%;text-align:center">
            </td>

            <td style="display:none">
            </td>
        </tr>
        <%
                }
            }
        %>
        <tr>
            <td colspan=11 align=right><input type="button" name="" value="确定" class=button2 onclick="window.close()">
            </td>
        </tr>
    </table>

</form>
</body>
</html>
<script type="text/javascript">
function get_bacode(obj) {
    var lookUpName = "<%=LookUpConstant.SELECT_BARCODE%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "itemCode=" + mainForm.itemCode.value;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var tab = document.getElementById("mtlTable")
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            appendDTORow(tab, user);
        }
    }
}
function do_load(obj) {

    //    var count1 = document.getElementById("onhandQty"+obj).value;
    //    var count2 =document.getElementById("holdCount"+obj).value;


    var id = obj.id.substring(9, obj.id.length);
    var qtyObj = document.getElementById("holdCount" + id);
    var onhandQty = document.getElementById("onhandQty" + id).value;
    if (isNaN(qtyObj.value)) {
        alert("分配数量必须是数字");
        qtyObj.focus;
    }
    if (!(Number(qtyObj.value) > 0)) {
        alert("分配数量大于0！");
        qtyObj.focus;
    }
    if (qtyObj.value.indexOf(".") !== -1) {
        alert("分配数量不能是小数！");
        qtyObj.focus;
    }
    if (Number(qtyObj.value) > Number(onhandQty)) {
        alert("分配数不能大于可用数，请重新输入！");
        qtyObj.focus();
    }
    var allQty = 0 ;
    var tab = document.getElementById("mtlTable");
    var count = tab.rows.length - 2;
    for (var i = 0; i < count; i++) {
        var x = Number(document.getElementById("holdCount" + i).value);
        allQty = allQty + x;
    }
    mainForm.allAmount.value = allQty;
    var qty=mainForm.quantity.value;
   alert(qty);
    if(Number(qty)<allQty){
       alert("请确认分配数量!")
        qtyObj.focus();
    }

}
function get_barcode(obj) {
    var hh = document.getElementById("detailId" + obj).value;
    var rows = document.getElementById("mtlTable").rows;
    /* for (var i = 0; i < rows.length; i++) {
      rows[i].style.backgroundColor = "#FFFFFF"
  }
  rows[obj].style.backgroundColor = "#9FD6FF";*/
    var url = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet?act=" + "barcode" + "&itemCode=" + mainForm.itemCode.value + "&transId=" + mainForm.transId.value + "&detailId=" + hh;
    //         var popscript;
    //    popscript = "dialogWidth:1020"
    //
    //            + ";dialogHeight:700"
    //
    //            + ";center:yes;status:no;scrollbars:no;help:no";
    var popscript = 'width=1020,height=700,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    //        window.showModalDialog(url, null, popscript);
    window.open(url, 'planWin', popscript);

}
function do_y() {
    var countsObj = document.getElementsByName("holdCount");
    var totalCount = 0;
    for (var i = 0; i < countsObj.length; i++) {
        var count = countsObj[i].value;
        if (count != "") {
            totalCount += Number(count);
        }
    }

}
/*function do_check() {
    var tab = document.getElementById("mtlTable");
    var trcount;
    var va = false;
    trcount = tab.rows.length
    for (var i = 0; i < trcount; i++) {
        var onqty = Number(document.getElementById("onhandQty" + i).value);
        var qty = Number(document.getElementById("holdCount" + i).value) ;
        if (qty > onqty) {
            alert("请确认返修数量!");
            return va ;
        }else {
            va=true;
        }


    }
    return va;
}*/
function do_getback() {


    mainForm.act.value = "write";
    mainForm.submit();


}
function do_getback1() {
    var n = (mtlTable.rows.length - 3) / 2;
    opener.document.getElementById("idtAmount").value = Number(document.getElementById("barcode").value);
    opener.document.getElementById("holdCount").value = Number(document.getElementById("holdCount").value);
    opener.document.getElementById("organizationId").value = Number(document.getElementById("organizationId").value);
    var orgid = ""   ;
    var barcode = ""    ;
    var qty = "";
    for (i = 0; i < n; i++) {
        qty += document.getElementById("holdCount" + i).value + "@@@";
        barcode += document.getElementById("barcode" + i).value + "@@@";
        orgid += document.getElementById("organizationId" + i).value + "@@@";
    }
    opener.document.getElementById("holdCount").value = qty;
    opener.document.getElementById("barcode").value = barcode;
    opener.document.getElementById("organizationId").value = orgid;
}
function do_change(obj) {
    var count1 = obj.value;
    var count2 = mainForm.itemAmount.value;
    var countsObj = document.getElementsByName("holdCount");
    var totalCount = 0;
    for (var i = 0; i < countsObj.length; i++) {
        var count = countsObj[i].value;
        if (count != "") {
            totalCount += Number(count);
        }
    }
    if (count1 > count2) {
        alert("请确认数量，分配数量不能超过现有量!");
    }
    else {
        if (totalCount > count2) {
            alert("请确认数量，分配总数量不能超过现有量!");
        } else {
            mainForm.value1.value = 0;
        }
    }
}
function do_values(){
     var allQty = 0 ;
    var tab = document.getElementById("mtlTable");
    var count = tab.rows.length - 2;
    for (var i = 0; i < count; i++) {
        var x = Number(document.getElementById("holdCount" + i).value);
        allQty = allQty + x;
    }
    mainForm.allAmount.value = allQty;
}
</script>