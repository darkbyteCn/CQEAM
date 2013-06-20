<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-20
  Time: 10:14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>所有地市</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<%
    String transId = StrUtil.nullToString(request.getParameter("transId"));
    String transNo = StrUtil.nullToString(request.getParameter("transNo"));
//    String detailId = StrUtil.nullToString(request.getParameter("detailId"));
    String itemCode = request.getParameter("itemCode");
    String itemAmount = request.getParameter("itemAmount");
    String barcode = StrUtil.nullToString(request.getParameter("barcode"));
    String lineId1 = StrUtil.nullToString(request.getParameter("lineId1"));
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("trId");
    AmsItemTransLDTO dto = (AmsItemTransLDTO) request.getAttribute("LDTO");
//    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.ALLOT_D_DTO);
%>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<body leftmargin="0" topmargin="0" onload="do_values()">
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjfxsqServlet" method="post">
    <div id="fxsqfpHideDiv"
     style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
    <table width=100% height=100%
           style="background-color:#777;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
        <tr><td></td></tr>
    </table>
</div>

    <table class="headertable" width="100%" border="1">
        <tr height="20">
            <td width="10%" align="center">地市</td>
            <td width="12%" align="center">仓库</td>
            <td width="15%" align="center">备件名称</td>
            <td width="10%" align="center">规格型号</td>
            <td width="5%" align="center">现有量</td>
            <td width="5%" align="center">可用留量</td>
            <td width="5%" align="center">分配数量</td>
        </tr>
    </table>
    <table width="100%" border="1" id="mtlTable" borderColor="#9FD6FF">
        <%
            RowSet rows = (RowSet) request.getAttribute("ALLOTBARCODE");
            int count = 0;
            int quty = 0;
            String alot="";
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
//                quty = row.getStrValue("ORGANIZATION_ID").equals("")?0:Integer.parseInt(row.getStrValue("ORGANIZATION_ID"));
        %>
        <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="10%" name="company" id="company<%=i%>"><%=row.getValue("COMPANY")%>
            </td>
            <td width="12%" name="objectName" id="objectName<%=i%>"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="10%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="5%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                                 value="<%=row.getValue("QUANTITY")%>"
                                                 class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="5%"><input type="text" name="canUseQty" id="canUseQty<%=i%>"
                                  value="<%=row.getValue("USE_QUANTITY")%>"
                                  class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="5%"><input type="text" name="holdCount" id="holdCount<%=i%>" onblur="do_load(this)"
            <%-- <%if(sectionRight.equals("OUT")){%> onclick="get_bacode(this)"<%}%>--%>
                                  value="<%=row.getValue("HOLD_COUNT")%>"
                                  style="width:100%;text-align:center">
            </td>
            <td style="display:none">
                <input type="hidden" name="organizationId" id="organizationId<%=i%>"
                       value="<%=row.getValue("ORGANIZATION_ID")%>">
                <input type="hidden" name="detailId" id="detailId<%=i%>"
                       value="<%=row.getValue("DETAIL_ID")%>">
                <input type="hidden" name="objectNo" id="objectNo<%=i%>"
                       value="<%=row.getValue("WORKORDER_OBJECT_NO")%>">
            </td>
        </tr>
        <%
                }
            }else {
                    alot="a";
                }
        %>
        <tr >
            <td align="right">返修数量</td>
            <td align="center"><%=dto.getQuantity()%></td>
            <td colspan=3 align="right">合计</td>
            <td align=center>分配总数</td>
            <td align="center"><input type="text" name="allAmount" value="" class=input2 readonly style="width:100%">
            </td>
        </tr>
        <tr>
            <td colspan=11 align=right>
                <input id="submit1" type="button" name="submit1" value="确定" class=button2 onclick="do_getback();">
                <input id="closed" type="button" name="closed" value="关闭" class=button2 onclick="do_closed();">
            </td>
        </tr>
    </table>

    <input type="hidden" name="itemAmount" value="<%=itemAmount%>">
    <input type="hidden" name="barcode1" value="<%=dto.getBarcode()%>">
    <input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
    <input type="hidden" name="transNo" value="<%=transNo%>">
    <%--<input type="hidden" name="detailId" value="<%=detailId%>">--%>
    <%-- <input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="lineId1" value="<%=dto.getLineId()%>">
    <input type="hidden" name="quantity" value="<%=dto.getQuantity()%>">
    <input type="hidden" name="act">
    <input type="hidden" name="count" id="count" value="<%=count%>">
    <input type="hidden" name="value1" id="value1" value="">

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

 function validateData(){
        var validate = false;
        var fieldNames = "holdCount";
        var fieldLabels = "分配数量";
        var validateType = INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if(validate){
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }


function do_validate(){
   var validate = true;
  var allQty = 0 ;
   var tab = document.getElementById("mtlTable");
   var count = tab.rows.length - 2;
   for (var i = 0; i < count; i++) {
       var x = Number(document.getElementById("holdCount" + i).value);
       allQty = allQty + x;
   }
   mainForm.allAmount.value = allQty;
   var qty=mainForm.quantity.value;
   //   alert(qty);  申请数量
   if(Number(qty)<allQty){
      alert("请确认分配数量!")
   //        qtyObj.focus();
      validate = false;
   }
      return validate;
 }


function do_load(obj) {
   if (validateData()){

   var id = obj.id.substring(9, obj.id.length);
    var qtyObj = document.getElementById("holdCount" + id);
    var onhandQty = document.getElementById("canUseQty" + id).value;

    if (Number(qtyObj.value) > Number(onhandQty)){
        alert("分配数必须是数字且不能大于可用数，请重新输入！");
        qtyObj.focus();
        return;
    }
   }
}

//function do_load(obj) {
//
//    //    var count1 = document.getElementById("onhandQty"+obj).value;
//    //    var count2 =document.getElementById("holdCount"+obj).value;
//
//
//    var id = obj.id.substring(9, obj.id.length);
//    var qtyObj = document.getElementById("holdCount" + id);
//    var onhandQty = document.getElementById("onhandQty" + id).value;
//    if (isNaN(qtyObj.value)) {
//        alert("分配数量必须是数字");
//        qtyObj.focus;
//    }
//    if (!(Number(qtyObj.value) > 0)) {
//        alert("分配数量大于0！");
//        qtyObj.focus;
//    }
//    if (qtyObj.value.indexOf(".") !== -1) {
//        alert("分配数量不能是小数！");
//        qtyObj.focus;
//    }
//    if (Number(qtyObj.value) > Number(onhandQty)) {
//        alert("分配数不能大于可用数，请重新输入！");
//        qtyObj.focus();
//    }
//    var allQty = 0 ;
//    var tab = document.getElementById("mtlTable");
//    var count = tab.rows.length - 2;
//    for (var i = 0; i < count; i++) {
//        var x = Number(document.getElementById("holdCount" + i).value);
//        allQty = allQty + x;
//    }
//    mainForm.allAmount.value = allQty;
//    var qty=mainForm.quantity.value;
////   alert(qty);
//    if(Number(qty)<allQty){
//       alert("请确认分配数量!")
//        qtyObj.focus();
//    }
//
//}
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
<%--function do_getback() {--%>

       <%--if (<%=alot.equals("")%>) {--%>
        <%--mainForm.act.value = "write";--%>
        <%--mainForm.submit();--%>
    <%--}--%>
    <%--else {--%>
        <%--window.close();--%>
    <%--}--%>

<%--}--%>


function do_getback() {
    var countsObj = document.getElementsByName("holdCount");
    var totalCount = 0;
    disablefxsqfpDocument();
    for (var i = 0; i < countsObj.length; i++) {
        var count = countsObj[i].value;
        if (count != "") {
            totalCount += Number(count);
        }
    }
    if (<%=alot.equals("")%>) {
        if (totalCount == 0) {
            alert("请填写分配数量并分配！");
            cancelFxsqfpDisable();
            return false;

        }
    }
    if (do_validate()) {
        if (<%=alot.equals("")%>) {

            if(confirm("请确定分配数量,分配后将不能修改！继续请点击“确定”按钮？否则请点击“取消”按钮")){
                document.mainForm.act.value = "write";
                document.mainForm.submit();
            }
        } else {
            window.close();
        }
            cancelFxsqfpDisable();
    }
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

    var submit1 = document.getElementById("submit1");
	var closed = document.getElementById("closed");
    if (allQty == 0) {
        submit1.style.display = "";
		closed.style.display = "none";
    } else {
        submit1.style.display = "none";
		closed.style.display = "";
    }
}

function do_closed() {
    window.close();
}

function disablefxsqfpDocument() {
    //disable整个页面
    var hideDiv = document.getElementById("fxsqfpHideDiv");
    hideDiv.style.visibility = 'visible';
}

 function cancelFxsqfpDisable() {
    var hideDiv = document.getElementById("fxsqfpHideDiv");
    hideDiv.style.visibility = 'hidden';
}
</script>
