<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/spare/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件报废</title>
        <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
  </head>
  <body leftmargin="1" topmargin="1" onload="TotalMoney()">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String sectionRight = request.getParameter("sectionRight");
      float total = 0;
%>
  <form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareBFServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="submitFlag" value="0">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">来源仓库：</td>
                    <td width="25%"><select name="fromObjectNo" id = "fromObjectNo" onchange= "doDel();" class="blueBorderYellow" style="width:80%"><%=request.getAttribute("SPARE_INVBF")%></select>
                    </td>
                   <td width="11%" align="right">单据类型：</td>
                    <td width="20%">备件报废单
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">报废日期：</td>
                    <td><%=String.valueOf(amsItemTransH.getCreationDate()).substring(0,10)%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td>备件报废</td>
                </tr>
                 <tr height="22">
                    <td align="right">备 注：</td>
                    <td colspan="3" align="left"><textarea class="blueborder" cols="90" name="remark" style="width:90%" rows="2"><%=amsItemTransH.getRemark()%></textarea></td>
                    <td align="right">报废总金额（元）：</td>
                    <td><input style="width:100%" readonly type="text" class="noborderGray" id="attribute4" name="attribute4" value="<%=amsItemTransH.getAttribute4()%>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)&& amsItemTransH.getCreatedBy() == user.getUserId()){
        %>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_submit();">
        <%
            }
        %>
        <%if (amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)) {%>
            <img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
     <%}%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
<div id="headDiv" style="overflow:hidden;position:absolute;top:112px;left:1px;width:1258px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="2%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
                   <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)&& amsItemTransH.getCreatedBy() == user.getUserId()){
        %>
                <td width="5%" align="center">现有数量</td>
                     <%}%>
                <td width="5%" align="center">报废数量</td>
                <td width="5%" align="center">报废金额(元)</td>
				<td width="10%" align="center">原因</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
    <div id="dataDiv" style="overflow:scroll;height:500px;width:1275px;position:absolute;top:135px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%if (rows == null || rows.isEmpty()) {%>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
                </td>
                <td width="10%" align="center"><input type="text" name="itemName" id="itemName0" value="" class="finput" readonly></td>
                <td width="15%" align="center"><input type="text" name="itemSpec" id="itemSpec0" value="" class="finput" readonly></td>
                <td width="10%" align="center"><input type="text" name="itemCategory" id="itemCategory0" value="" class="finput" readonly></td>
                <td width="10%" align="center"><input type="text" name="spareUsage" id="spareUsage0" value="" class="finput" readonly></td>
                <td width="10%" align="center"><input type="text" name="vendorName" id="vendorName0" value="" class="finput" readonly></td>
              <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)&& amsItemTransH.getCreatedBy() == user.getUserId()){
        %>
                <td width="5%" align="center"><input type="text" name="onhandQty" id="onhandQty0" readonly value="" class="noborderGray" style="width:100%;text-align:right">
                </td>
                 <%}%>
                <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" onblur="checkQty(this);" value="" class="blueborderYellow" style="width:100%;text-align:right">
                </td>
                <td width="5%" align="center"><input type="text" name="bfje" id="bfje0" onchange="validatePrice();" onkeydown="floatOnly();" value="" class="blueborder" style="width:100%;text-align:right">
                </td>
                <td width="10%" align="left"><input type="text" name="remarkl" id="remarkl0" value="" class="blueborder" style="width:100%;text-align:left">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                </td>
            </tr>
            <%
                }else{
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                            String price = String.valueOf(row.getStrValue("BFJE"));
                            if (price.equals("")) {
                                price = "0";
                            }
                            total += Float.parseFloat(price);
            %>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
                </td>
                <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=row.getValue("SPARE_USAGE")%>
                </td>
                <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                 <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)&& amsItemTransH.getCreatedBy() == user.getUserId()){
        %>
                <td width="5%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>" readonly value="<%=row.getValue("ONHAND_QTY")%>" class="noborderGray" style="width:100%;text-align:right">
                </td>
                <%}%>
                <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" onblur="checkQty(this);" value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
                </td>
                <td width="5%" align="center"><input type="text" name="bfje" id="bfje<%=i%>" onchange="validatePrice();" value="<%=row.getValue("BFJE")%>" class="blueborder" style="width:100%;text-align:right">
                </td>
                <td width="10%" align="center"><input type="text" name="remarkl" id="remarkl<%=i%>" value="<%=row.getValue("REMARK")%>" class="blueborder" style="width:100%;text-align:left">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<div id="showMsg" style="color:red"><jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/></div>
 <OBJECT id=wb height=0 width=0  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
  </form>
<div id="$$$disableMsg$$$" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="100%" style="background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900"  height="60">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td bgcolor="#FFFFCC" align="center"><font color="#008000" size="2">正在提交数据，请稍候......</font><img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
  </body>
<script type="text/javascript">
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30,"organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
        if(projects){
            document.mainForm.fromObjectName.value = projects[0].workorderObjectName;
            document.mainForm.fromObjectNo.value = projects[0].workorderObjectNo;
        }
    }
            
    function do_SelectItem() {
        var fromObjectNo = document.mainForm.fromObjectNo.value;
        if(fromObjectNo == ""){
            alert("请先选择来源仓库!");
            return;
        }
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BJBF%>&organizationId=<%=user.getOrganizationId()%>&objectNo="+fromObjectNo;
        var popscript = "dialogWidth:65;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var items = window.showModalDialog(url, null, popscript);
        if (items) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < items.length; i++) {
                data = items[i];
                if(!isItemExist(data)){
                    appendDTO2Table(tab, data, false, "barcode");
                }
            }
        }
    }

      function isItemExist(itemObj) {
        var retVal = false;
        var tab = document.getElementById("dataTable");
        if (tab.rows) {
            var trObjs = tab.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var itemValue = itemObj.barcode;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[1].childNodes[0].value;
                if (itemValue == rowValue) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    function do_save(flag){
        if(flag == 1){
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        }else{
            document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        }
        document.mainForm.submit();
    }

    function do_submit(){
         if (!getvalues()) {
            return;
        }
        if (document.mainForm.submitFlag.value == "1") {
            alert("正在进行操作，请等待。");
            return;
        }
        document.mainForm.submitFlag.value="1";
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        document.mainForm.submit();
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }

    function checkQty(obj){
        var id = obj.id.substring(8,obj.id.length);
        var qtyObj = document.getElementById("quantity"+id);
        var onhandQty = document.getElementById("onhandQty"+id).value;
          if (isNaN(qtyObj.value)) {
                alert("报废数量必须是数字");
                 qtyObj.focus();
            }
         if (!(Number(qtyObj.value)>0)) {
                alert("报废数量大于0！");
                 qtyObj.focus();
            }
         if (qtyObj.value.indexOf(".")!== -1){
             alert("报废数量不能是小数吧！");
             qtyObj.focus();
         }
        if(Number(qtyObj.value)>Number(onhandQty)){
            alert("报废数量不能大于现有数量，请重新输入！");
            qtyObj.focus();
        }
    }



   function validatePrice() {
        var total = 0;
        var tab = document.getElementById("dataTable");
        if (tab.rows) {
            var trObjs = tab.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[8].childNodes[0].value;
                total += Number(rowValue);
            }
        }
     document.mainForm.attribute4.value= total;
    }

function TotalMoney() {
//      var transStatus = document.getElementById("fromObjectNo");
//        makeOptionsSelected(transStatus,'6420')
//        var totalMoney = document.getElementById("totalMoney");
        <%--totalMoney.innerText = '<%=total%>';--%>
    }


 function Money( obj) {
        var bfje = obj.value;
        var totalMoney = document.getElementById("totalMoney").innerText;
     document.getElementById("totalMoney").innerText = eval(Number(totalMoney) + Number(bfje));;
    }

  function doDel(){
      document.getElementById('titleCheck').checked = true;
      checkAll('titleCheck','subCheck')
      var tab = document.getElementById("dataTable");
      deleteTableRow1(tab, 'subCheck');
      document.getElementById('titleCheck').checked = false;
   }


function deleteTableRow1(tab, checkboxName){
	if(!tab || !checkboxName){
		return;
	}
	var rowCount = tab.rows.length;
	if (rowCount == 0) {
	    return;
	}
	var boxArr = getCheckedBox(checkboxName);
	var chkCount = boxArr.length;
	if(chkCount < 1){
        return;
	}
		var chkObj = null;
		for(var i = 0; i < chkCount; i++){
			chkObj = boxArr[i];
			if(tab.rows.length > 1){
				delTableRow(tab, chkObj);
			} else {
				clearContent(tab, chkObj);
				tab.rows[0].style.display = "none";
			}
		}
}

   function getvalues() {
        var tab = document.getElementById("dataTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }

   function do_print() {
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareBFServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
</script>
</html>