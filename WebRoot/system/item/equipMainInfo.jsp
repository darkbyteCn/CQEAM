<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.ams.system.item.dto.EtsSystemItemDTO" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-9-20
  Time: 13:14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">		
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <style type="text/css">
        <!--
        .STYLE1 {
            color: #0033FF
        }
        -->
    </style>
</head>
<script type="text/javascript">
    printTitleBar("设备分类分配维护详细")
</script>
<%
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    EtsSystemItemDTO itemDTO = (EtsSystemItemDTO) request.getAttribute(WebAttrConstant.ETS_SYSTEM_ITEM_DTO);
    String itemCategory = (String) request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION);
    System.out.println("itemDTO.getItemCategory() = " + itemDTO.getItemCategory());
    String showou = "";
     if((!user.isProvinceUser()) && (!user.isSysAdmin())){
           showou ="N";
     }
%>
<body topmargin="0" leftmargin="0" onLoad="do_init()">
<jsp:include page="/message/MessageProcess"/>
<form action="" method="post" name="mainFrm">
<fieldset style="margin-left:0;height:400px; overflow: scroll">
<legend>
<span class="STYLE1">设备分类分配维护页面</span></legend>
  <table width="100%" align="center" border="0">
<tr>
    <td width="25%" align="right">设备类型：</td>
    <td width="25%">
        <select class="select_style1" name="itemCategory" style="width:96%"><%=itemCategory%></select>&nbsp;<font style="color=red">*</font>
    </td>
    <td width="11%" align="right">设备名称：</td>
    <td width="39%">
        <input class="input_style1" type="text" name="itemName" value="<%=itemDTO.getItemName()%>" style="width:61%">
        &nbsp;<font style="color=red">*</font>
    </td>
</tr>
<tr>
   <td align="right" width="25%">计量单位：</td>
    <td width="25%">
        <select class="select_style1" name="itemUnit"  style="width:96%"><%=request.getAttribute(WebAttrConstant.ITEM_UNIT_OPTION)%></select>&nbsp;<font style="color=red">*</font>
    </td>
    <td width="11%" align="right">规格型号：</td>
    <td width="39%">
        <input type="text" name="itemSpec" value="<%=itemDTO.getItemSpec()%>" class="input_style1" style="width:61%">
        &nbsp;<font style="color=red">*</font>
    </td>
</tr>
<%
    String yesChecked = "checked";
    String noChecked = "";
    if (itemDTO.getIsFixedAssets().equals("N")) {
        yesChecked = "";
        noChecked = "checked";
    }
%>
<tr>
    <td height="31" align="right">是否固定资产：</td>
    <td><input type="radio" name="isFixedAssets" id="yes1" value="Y" <%=yesChecked%>><label for="yes1">是</label>
        <input type="radio" name="isFixedAssets" id="no1" value="N"  <%=noChecked%>><label for="no1">否</label></td>
    <td align="right">MIS物料编码：</td>
    <td><input type="text" name="misItemCode" value="<%=itemDTO.getMisItemCode()%>" class="input_style1"  style="width:61%">
    </td>
</tr>
<tr>
    <td align="right">使用年限(年)：</td>
    <td>
        <input type="text" name="years" id="years" value="<%=itemDTO.getYears()%>" class="input_style1"  style="width:96%" onblur="do_verify1();">
    </td>
    <td align="right">供应商：</td>
    <td><input readonly class="input_style1" type="text" name="vendorName" value="<%=itemDTO.getVendorName()%>" style="width:61%">
         <a href="#"  onClick="SelectVendorId(); " title="点击选择生产厂家"  class="linka">[…]</a>
    </td>
</tr>
<%
    yesChecked = "checked";
    noChecked = "";
    if (itemDTO.getEnabled().equals("N")) {
        yesChecked = "";
        noChecked = "checked";
    }
%>
<tr>
    <td align="right">是否有效：</td>
    <td><input type="radio" name="enabled" id="yes" value="Y" <%=yesChecked%>><label for="yes">是</label>
        <input type="radio" name="enabled" value="N" id="no"  <%=noChecked%>><label for="no">否</label></td>
    <td align="right">上次修改人：</td>
    <td><input type="text"  class="input_style1" name="lastUpdateByName" value="<%=itemDTO.getLastUpdateByName()%>" style="width:61%" readonly></td>
</tr>
<tr>
    <td align="right">备注：</td>
    <td rowspan="2" colspan="3"><textarea rows="3" cols="134" class="input_style1" name="memo" value="<%=itemDTO.getMemo()%>"><%=itemDTO.getMemo()%></textarea></td>
</tr>
<%
    if((user.isProvinceUser())||(user.isSysAdmin())){     //省公司
%>
<tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>

<tr>
    <td></td>
    <td align="center"><strong>可选OU</strong></td>
    <td></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>分配OU</strong></td>
</tr>
<tr>
    <td></td>
    <td>
        <select name="allOrgs" size="13" multiple style="width:200" ondblclick="do_AddOrgs();">
            <%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
        </select>
    </td>
    <td align="center">
        <img src="/images/eam_images/new.jpg" alt="增加" style="cursor:'hand'" onClick="do_AddOrgs();"><br><br>
        <img src="/images/eam_images/add_all.jpg" style="cursor:'hand'" onClick="do_AddAllOrgs();" alt="添加全部"><br><br>
        <img src="/images/eam_images/delete.jpg" style="cursor:'hand'" onClick="do_DeleteOrg();" alt="删除"><br><br>
        <img src="/images/eam_images/delete_all.jpg" style="cursor:'hand'" onClick="do_DeleteAllOrgs();" alt="删除全部"><br><br>
    </td>
    <td>
        <select size="13" name="organizationId" multiple style="width:200" ondblclick="do_DeleteOrg()">
            <%=request.getAttribute(WebAttrConstant.DIS_OU_OPTION)%>
        </select>
    </td>
</tr>
<%
    }else{
%>

<%
    }
%>
<tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>

<tr>
    <td colspan="2">
        <br></td>
</tr>

<tr>
    <td align="center"></td>
    <td align="center"><img src="/images/eam_images/save.jpg" alt="保存" style="cursor:'hand'" onClick="do_submit();">&nbsp;&nbsp;</td>
    <td>
        <%
            if (!itemDTO.getItemCode().equals("")) {
        %>
        &nbsp;&nbsp;<img src="/images/eam_images/disable.jpg" onClick="do_delete(); return false;" alt="失效" id="delete">
        <%
            }
        %>
    </td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <a style="cursor:'hand'" onClick="do_back();"><img src="/images/eam_images/back.jpg"  alt="返回"></a>
    </td>
</tr>
</table>
</fieldset>
<input type="hidden" name="act">
<input type="hidden" name="showou" value = "<%=showou%>">
<input type="hidden" name="itemCode" value="<%=itemDTO.getItemCode()%>">
<input type="hidden" name="vendorId" value="<%=itemDTO.getVendorId()%>">
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>

<script type="text/javascript">
    function do_back() {
        mainFrm.itemName.value = "";
        mainFrm.itemCategory.value = "";
        mainFrm.misItemCode.value = "";
        mainFrm.vendorId.value = "";
        mainFrm.itemSpec.value = "";
        mainFrm.vendorName.value = "";

        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet";
        document.mainFrm.submit();
    }

    function do_delete() {
        var itemCode = mainFrm.itemCode.value;
        if (confirm("确认失效该设备分类吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet?itemCode=" + itemCode;
            document.mainFrm.submit();
        }
    }
    function do_init() {

    }


    function do_AddOrgs() {
        moveSelectedOption("allOrgs", "organizationId");
    }

    function do_DeleteOrg() {
        moveSelectedOption("organizationId", "allOrgs");
    }

    function do_AddAllOrgs() {
        moveAllOption("allOrgs", "organizationId");
    }

    function do_DeleteAllOrgs() {
        moveAllOption("organizationId", "allOrgs");
    }

	function validOrgs(){
		var allOrgs = document.getElementById( "organizationId" );
		if( allOrgs.hasChildNodes() && allOrgs.length > 0 ) {
			return true;
		}else{
			alert( "分配OU必须至少一个" );
			return false;
		}
	}

    function do_submit() {
        if(document.mainFrm.showou.value=="") {
        	selectAll("organizationId");
        }
        document.mainFrm.itemCategory.disabled = false;
        var fieldNames = "itemName;itemCategory;itemUnit";
        var fieldLabels = "设备名称;设备分类;计量单位"; 
        if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        	if(document.mainFrm.showou.value=="") {
	        	if( !validOrgs()   ){
	        		return ;
	        	}
        	}
        	
            fieldNames = "years";
            fieldLabels = "使用年限";
            if (formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
                with (mainFrm) {
                    act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                    if (itemCode.value != "") {
                        act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                    }
                    action = "/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet";
                    submit();
                }
            }
        }
    }

    function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:47.5;dialogHeight:28;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainFrm");
            }
        }
    }

    function do_verify1() {
        var fieldNames = "years";
        var fieldLabels = "使用年限";
        if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
            //          alert("使用年限必须为数字！");
        }
    }

    function do_verify(obj) {
        var itemName = obj.value ;
        if (itemName == '') {
            alert("设备名称不允许为空！");
            obj.focus();
            return false;
        }
    }
</script>