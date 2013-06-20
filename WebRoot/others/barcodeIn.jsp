<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by HERRY.
  Date: 2007-11-11
  Time: 20:35:54
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>条码设备入库</title></head>
  <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
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
    
<body leftmargin="1" topmargin="1"  onload="init();">
 <jsp:include page="/message/MessageProcess"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
 <form name="mainForm" action="/servlet/com.sino.ams.others.servlet.BarcodeInServlet" method="post">
 <input type="hidden" name="act" value="">
 <input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.TMRK%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="9%" align="right">仓库类型：</td>
                    <td width="25%"><input type="radio" name="objectCategory" id="objectCategory0" value="71" style="background-color:#F2F9FF" checked><label for="objectCategory0">正常库</label>
                                    <input type="radio" name="objectCategory" id="objectCategory1" value="72" style="background-color:#F2F9FF"><label for="objectCategory1">待修库</label>
                    </td>
                    <td width="9%" align="right">仓库名称：</td>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>

                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                                           readonly
                                           class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:80%"
                                           value="<%=amsItemTransH.getCreationDate()%>"
                                           class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" readonly
                                           value="<%=amsItemTransH.getTransStatusName()%>"
                                           class="blueborderGray"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)){%>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/eam_images/save.jpg" alt="保存" id="img3" onClick="do_SavePo(1);">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_SavePo(2);">
        <%}%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
   <%-- <div style="left:1px;width:100%;overflow-y:scroll" id="headDiv">
        <table id="headerTable" border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center"><input type="checkBox" name="titleCheck"
                                                     onclick="checkAll('titleCheck','subCheck');" class="headCheckbox">
                </td>
                <td width="12%" align="center">标签号</td>
                <td width="15%" align="center">设备名称</td>
                <td width="15%" align="center">规格型号</td>
            </tr>
        </table>
    </div>--%>
    <script type="text/javascript">
        var columnArr = new Array("checkbox","标签号","设备名称","规格型号");
        var widthArr = new Array("2%","12%","15%","15%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:600px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center"><input type="text" name="barcode" id="barcode0"
                                                                           value="" class="blueborderYellow"
                                                                           style="width:100%;text-align:center">
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
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center"><input type="text" name="barcode"
                                                                           id="barcode<%=i%>"
                                                                           value="<%=row.getValue("BARCODE")%>"
                                                                           class="blueborderYellow"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
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
        }
    }
    function do_SelectItem() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";
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
            alert("标签号不能重复，请确认输入的标签号！");
        }
    }

    var xmlHttp = null;
    var isRepeated = true;
    function isBarcodeRepeated(){
        var repeated = true;
        var barcodes = document.getElementsByName("barcodeNo");
        var barcodeArr = new Array();
        var barcodeArr2 = new Array();
        var tab=document.getElementById("dataTable");
        var rCount=tab.rows.length;
        for(var j=0;j<rCount;j++){
           var barcode=  tab.rows[j].cells[1].childNodes[0].value  ;
            if(barcode==""){
                alert("请输入标签号！");
               tab.rows[j].cells[1].childNodes[0].focus();
                return;
            }
           barcodeArr[j] =tab.rows[j].cells[1].childNodes[0].value;
            barcodeArr2[j] = tab.rows[j].cells[1].childNodes[0].value;
        }
//        for(var i = 0; i < barcodes.length; i++){
//            alert(barcodes[1].value)
//            if(barcodes[i].value == ""){
//                alert("请输入标签号！");
//                barcodes[i].focus();
//                return;
//            }
//            barcodeArr[i] = barcodes[i].value;
//            barcodeArr2[i] = barcodes[i].value;
//        }
//        alert("barcodeArr="+barcodeArr)
        if(barcodeArr.length == barcodeArr2.uniqueStrArr().length){
            repeated = false;
        }
        if(!repeated){
            //到数据库验证是否有重复
            var url = "/servlet/com.sino.ams.spare.servlet.CheckBarcodeServlet";
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
                    alert("标记为红色的标签号为非网络标签号，请修改！");
                }
            }
            xmlHttp = null;
        }
    }
</script>
</html>