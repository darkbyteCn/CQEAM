<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2007-12-23
  Time: 16:54:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件返库</title><link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>

</head>
<body leftmargin="1" topmargin="1"  onload="init();">
 <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute("EAM_ITEMH_REPAIR");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjfkServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.BJFK%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <%--<td width="9%" align="right">仓库类型：</td>
                    <td width="25%"><input type="radio" name="objectCategory" id="objectCategory0" value="71" style="background-color:#F2F9FF" checked><label for="objectCategory0">正常库</label>
                                    <input type="radio" name="objectCategory" id="objectCategory1" value="72" style="background-color:#F2F9FF"><label for="objectCategory1">待修库</label>
                                    <input type="radio" name="objectCategory" id="objectCategory2" value="73" style="background-color:#F2F9FF"><label for="objectCategory2">送修库</label>
                    </td>--%>
                    <td width="9%" align="right">仓库名称：</td>
                    <td width="25%"><%=amsItemTransH.getToObjectName()%>
                    </td>
                    <td width="9%" align="right"></td>
                    <td width="25%">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%></td>
                </tr>
                <tr height="50">
                        <td align="right">备注：</td>
                        <td colspan="6"><%=amsItemTransH.getRemark()%></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("","部件号","设备名称","规格型号","数量");
        var widthArr = new Array("2%","12%","15%","15%","8%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center">
                </td>
                <td width="12%" align="center"><input type="text" name="barcode" id="barcode0"
                                                                           value="" class="blueborderYellow"
                                                                           style="width:100%">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                </td>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" height="22" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">

                <td width="2%" align="center">
                </td>
                <td width="12%"><%=row.getValue("BARCODE")%>
                </td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><%=row.getValue("QUANTITY")%>
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
</form>
</body>
<script type="text/javascript">
    function init() {
    }
    function do_SelectObject() {
        var objectCategory = getRadioValue("objectCategory");
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30,"organizationId=<%=user.getOrganizationId()%>&objectCategory="+objectCategory);
        if(projects){
//            dto2Frm(projects[0], "form1");
            document.mainForm.toObjectName.value = projects[0].workorderObjectName;
            document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
//            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_SelectItem() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_SYSTEM_ITEM%>";
        var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var assets = window.showModalDialog(url, null, popscript);
        if (assets) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < assets.length; i++) {
                data = assets[i];
                appendDTORow(tab, data);
            }
        }
    }
    var sflag = null;
    function do_SavePo(flag){
        if (document.mainForm.toObjectNo.value == '') {
            alert("请选择仓库！");
            return false;
        }
        if(flag == 2){//submit
            var tb = document.getElementById("dataTable");
            if(tb.rows.length == 0){
                 alert("请选择设备！");
                return false;
            }
        }
        sflag = flag;
        if(isBarcodeRepeated()){
            alert("设备条码不能重复，请确认输入的设备条码！");
        }
    }

    var xmlHttp = null;
    var isRepeated = true;
    function isBarcodeRepeated(){
        var repeated = true;
        var barcodes = document.getElementsByName("barcodeNo");
        var barcodeArr = new Array();
        var barcodeArr2 = new Array();
        for(var i = 0; i < barcodes.length; i++){
            if(barcodes[i].value == ""){
                alert("请输入设备条码！");
                barcodes[i].focus();
                return;
            }
            barcodeArr[i] = barcodes[i].value;
            barcodeArr2[i] = barcodes[i].value;
        }
//        alert("barcodeArr="+barcodeArr)
        if(barcodeArr.length == barcodeArr2.uniqueStrArr().length){
            repeated = false;
        }
        if(!repeated){
            //到数据库验证是否有重复
            var url = "/servlet/com.sino.ams.spare2.servlet.CheckBarcodeServlet";
            xmlHttp = GetXmlHttpObject(checkBarcode);
            xmlHttp.open('POST', url, true);
            xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
            xmlHttp.send("barcodes=" + barcodeArr);
        }
        return repeated;
    }

    function checkBarcode() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var flexValues = new Array();
            var descriptions = new Array();
            var resText = xmlHttp.responseText;
            if (resText == "ERROR") {
                alert(resText);
            } else if (resText == "OK") {
                isRepeated = false;
                if(sflag == 1){
                    document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                }else{
                    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                    document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
                }
                document.mainForm.submit();
            } else {
                var resArray = resText.parseJSON();
                if (resArray.length > 0) {
                    var tabObj = document.getElementById("dataTable");
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        if (retStr == 1) {
                            tabObj.rows[i].cells[1].childNodes[0].style.color = "red";

                        }
                    }
                    alert("标记为红色的设备条码已存在，请修改！");
                }
            }
            xmlHttp = null;
        }
    }
</script>
</html>