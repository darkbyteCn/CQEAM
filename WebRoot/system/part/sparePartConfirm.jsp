<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.part.dto.PartConfirmDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-12-28
  Time: 9:22:33
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>设备分类确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("设备分类确认");
</script>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	PartConfirmDTO dto = (PartConfirmDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	boolean hasData = (rows != null && rows.getSize() > 0);
    Row row = null;
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="initPage();">
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<table width="100%" topmargin="0" border="0" >
    <form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet">
        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
        <tr>
            <td width="10%" align="right">设备类型：</td>
            <td width="20%"><select name="itemCategory" class="select_style1" style="width:100%"><%=request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION)%></select></td>
            <td width="10%" align="right">设备名称：</td>
            <td width="25%"><input  class="input_style1" style="width:90%" type="text" name="itemName" value="<%=dto.getItemName()%>"></td>
            <td width="10%" align="center"><img src="/images/eam_images/confirm.jpg" alt="确认" onClick="do_confirm();"></td>
        </tr>
        <tr>
            <td width="10%" align="right">设备型号：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="itemSpec" value="<%=dto.getItemSpec()%>"></td>
            <td width="10%" align="right">生产厂家：</td>
            <td width="25%"><input type="text" name="vendorName" value="<%=dto.getVendorName()%>" class="input_style1" style="width:90%"><a href="#" onClick="SelectVendorId(); " title="点击选择生产厂家"  class="linka">[…]</a></td>
            <td width="10%" align="center"><img src="/images/eam_images/search.jpg" align="middle" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
<input type="hidden" name="vendorId" value="<%=dto.getVendorId()%>">
<input type="hidden" name="itemType1" >
<input type="hidden" name="itemCode1" >
<input type="hidden" name="itemName1" >
<input type="hidden" name="itemSpec1" >
<input type="hidden" name="items" >

<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:830px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22" onClick="executeClick(this)"title="点击全选或取消全选" style="cursor:hand">
            <td width="3%" align="center"><input type="checkbox" name="titleCheck" class="headCheckbox" id="controlBox" onclick="checkAll('titleCheck','itemCodes')"></td>
            <td width="10%" align="center">设备类型</td>
            <td width="20%" align="center">设备名称</td>
            <td width="20%" align="center">规格型号</td>
            <td width="5%" align="center">单位</td>
            <td width="10%" align="center">使用年限</td>
            <td width="20%" align="center">生产厂家</td>
			<td style="display:none">隐藏域字段列</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:300px;;width:847px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		Object itemType = "";
		Object itemName = "";
		Object itemSpec = "";
		Object itemCode = "";
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
			itemType = row.getValue("ITEM_TYPE");
			itemName = row.getValue("ITEM_NAME");
			itemSpec = row.getValue("ITEM_SPEC");
			itemCode = row.getValue("ITEM_CODE");
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="3%" align="center"><input type="checkbox" name="itemCodes" value="<%=itemCode%>"></td>
            <td width="10%" align="center" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=itemType%></td>
            <td width="20%" align="left" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=itemName%></td>
            <td width="20%" align="left" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=itemSpec%></td>
            <td width="5%" align="left" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=row.getValue("ITEM_UNIT")%></td>
            <td width="10%" align="right" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=row.getValue("YEARS")%></td>
            <td width="20%" align="left" onclick="show_detail('<%=itemCode%>','<%=itemType%>','<%=itemName%>','<%=itemSpec%>')"><%=row.getValue("VENDOR_NAME")%></td>
			<td style="display:none">
				<INPUT TYPE = "hidden"  id = "code_<%=i%>" value = "<%=itemCode%>">
				<INPUT TYPE = "hidden"  id = "name_<%=i%>" value = "<%=itemName%>">
				<INPUT TYPE = "hidden"  id = "spec_<%=i%>" value = "<%=itemSpec%>">
				<INPUT TYPE = "hidden"  id = "items_<%=i%>" value = "<%=row.getValue("ITEMS")%>">
			</td>
        </tr>
<%
		}
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:400px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(p_itemCode,p_itemType, p_itemName,p_itemSpec) {
		with(mainFrm){
			itemCode1.value  = p_itemCode;
			itemType1.value  = p_itemType;
			itemName1.value  = p_itemName;
			itemSpec1.value  = p_itemSpec;
			act.value = "<%=WebActionConstant.NEW_ACTION%>";
			submit();
		}
    }


    function do_confirm() {
        var checkedCount = getCheckedBoxCount("itemCodes");
        if (checkedCount < 1) {
            alert("请至少选择一行数据！");
            return;
        } else {
            mainFrm.act.value = "<%=AMSActionConstant.CONFIRM_ACTION%>";
            mainFrm.submit();
        }
    }


    function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainFrm");
            }
        }else{
           mainFrm.vendorName.value ="";
           mainFrm.vendorId.value ="";
        }
    }

    function initPage() {
		do_SetPageWidth();
    }

   function rejigger(){
      var url = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet?act=<%=WebActionConstant.NEW_ACTION%>";
      mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
      mainFrm.submit();
   }


    function ddoo() {
        var checkedCount = getCheckedBoxCount("itemCodes");
        if (checkedCount == 1) {
//            alert("数据一条！");
            var checkBox = document.all["itemCodes"];
            var checkedi = 0;
            for (var i = 0; i < checkBox.length; i++) {
                if (checkBox[i].type == "checkbox" && checkBox[i].checked) {
                    checkedi = i;
                }
            }
             mainFrm.itemCode1.value  = document.getElementById("code_" + checkedi).value;
             mainFrm.itemName1.value  = document.getElementById("name_" + checkedi).value;
             mainFrm.itemSpec1.value  = document.getElementById("spec_" + checkedi).value;
             mainFrm.items.value = document.getElementById("items_"+ checkedi).value;
            var url = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet?itemCode1="+mainFrm.itemCode1.value+"&itemName1="+mainFrm.itemName1.value+"&itemSpec1="+mainFrm.itemSpec1.value;
            mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
            mainFrm.submit();
            return;
        } else {
            alert("要选择一条数据！");
        }
    }

    function distriConfirm(){
        mainFrm.act.value = "<%=WebActionConstant.EDIT_ACTION%>";
        mainFrm.submit();
    }
</script>