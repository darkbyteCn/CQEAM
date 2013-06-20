<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-8-18
  Time: 11:33:42
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>多维度信息维护</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>

</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('1.N');" onkeydown="autoExeFunction('do_Search();');">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
<%
    AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    String[] manydiments = (String[])request.getAttribute("MANYDIMENTSON");
    int dimenCount = 0;
    if(manydiments != null){
        dimenCount = manydiments.length;
    }

    String userOption = (String) request.getAttribute(WebAttrConstant.USER_OPTION);
    
    String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
	if( null == allResName ){
		allResName = "多维度信息维护";
	}
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1">
    <%=WebConstant.WAIT_TIP_MSG%>
    <script type="text/javascript">
        printTitleBar( "<%= allResName %>" );
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close()");
        var ArrAction1 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");
        var ArrAction2 = new Array(true, "导出", "toexcel.gif", "关闭", "do_ShowExcel()");
        var ArrAction3 = new Array(true, "批量更新", "action_edit.gif", "批量更新", "do_UpdateItem()");
        var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
        printToolBar();
    </script>
<div id="searchDiv">
    <table width="100%" border="0" class="queryTable">
        <tr>
            <td width="10%" align="right">多维度类型：</td>
            <td width="15%">
                <select class="select_style1" name="manyDimensionsType" style="width:100%" onclick="do_saveOldChangeProjectPrep()" onchange="do_ChangeManyDimensions()">
                    <option value="ZCLB" <%=(dto.getManyDimensionsType().equals("") || dto.getManyDimensionsType().equals("ZCLB"))?"selected":""%>>资产类别</option>
                    <option value="WLYS" <%=dto.getManyDimensionsType().equals("WLYS")?"selected":""%>>逻辑网络元素</option>
                    <option value="TZFL" <%=dto.getManyDimensionsType().equals("TZFL")?"selected":""%>>投资分类</option>
                    <option value="YWPT" <%=dto.getManyDimensionsType().equals("YWPT")?"selected":""%>>业务平台</option>
                    <option value="WLCC" <%=dto.getManyDimensionsType().equals("WLCC")?"selected":""%>>网络层次</option>
                </select>
            </td>
            <td width="10%" align="right">资产类别：</td>
            <td width="15%">
                <input class="input_style1" type="text" name="faContentCode" style="width:80%" value="<%=dto.getFaContentCode()%>" readonly><a href="" title="点击选择资产类别" onclick="do_SelectContent(); return false;">[…]</a>
            </td>
            <td width="10%" align="right">类别描述：</td>
            <td width="15%" align="left">
                <input class="input_style1" type="text" name="faContentName" style="width:100%" value="<%=dto.getFaContentName()%>" readonly>
            </td>
            <td width="10%" align="right">资产名称：</td>
            <td width="15%" align="left">
                <input class="input_style1" type="text" name="itemName" style="width:80%" value="<%=dto.getItemName()%>" onClick="do_SelectItemCode();" ><a href="" title="点击选择资产名称" onclick="do_SelectItemCode(); return false;">[…]</a>
            </td>
        </tr>
        <tr>
            <td width="10%" align="right">责任部门：</td>
            <td width="15%">
                <select class="select_style1" name="responsibilityDept" style="width:100%" onmouseover="do_ProcessOptionWidth(this);" ><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
            </td>
            <td width="10%" align="right">地点：</td>
            <td width="15%">
                <input class="input_style1" type="text" name="workorderObjectName" style="width:80%" value="<%=dto.getWorkorderObjectName()%>" readonly><a href="" title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a>
            </td>
            <td width="10%" align="right">标签号：</td>
            <td width="15%" align="left">
                <input class="input_style1" type="text" name="fromBarcode" style="width:100%" value="<%=dto.getFromBarcode()%>">
            </td>
            <td width="10%" align="right">到：</td>
            <td width="15%" align="left">
                <input class="input_style1" type="text" name="toBarcode" style="width:80%" value="<%=dto.getToBarcode()%>">
            </td>
        </tr>
        <tr>
            <td width="10%" align="right">责任人：</td>
            <td width="15%">
                <input type="tex" id="responsibilityUserName" name="responsibilityUserName" style="width:80%" readonly="true" value="<%=dto.getResponsibilityUserName()%>"><a href="" title="点击选择责任人" onclick="do_SelectPerson(); return false;">[…]</a>
            </td>
            <td width="10%" align="right">项目名称：</td>
            <td width="15%">
                <input class="input_style1" type="text" name="prjName" style="width:80%" value="<%=dto.getPrjName()%>" readonly><a href="" title="点击选择工程项目" onclick="do_SelectProject(); return false;">[…]</a>
            </td>
            
            <td colspan="4" width="50%">&nbsp;&nbsp;
                <div id="dis1" style="display:none">
                    资产类别未修改：<input type="checkbox" id="content" name="manyDimensionsIsNull" value="content">&nbsp;
                    统一设置资产类别：<input type="checkbox" name="allContent" id="allContent">
                </div>
                <div id="dis2" style="display:none">
                    逻辑网络元素为空：<input type="checkbox" id="lne" name="manyDimensionsIsNull" value="lne">
                    统一设置网络元素：<input type="checkbox" name="allLne" id="allLne">
                </div>
                <div id="dis3" style="display:none">
                    投资分类信息为空：<input type="checkbox" id="cex" name="manyDimensionsIsNull" value="cex">
                    统一设置投资分类：<input type="checkbox" name="allCex" id="allCex">
                </div>
                <div id="dis4" style="display:none">
                    业务平台信息为空：<input type="checkbox" id="ope" name="manyDimensionsIsNull" value="ope">
                    统一设置业务平台：<input type="checkbox" name="allOpe" id="allOpe">
                </div>
                <div id="dis5" style="display:none">
                    网络层次信息为空：<input type="checkbox" id="nle" name="manyDimensionsIsNull" value="nle">
                    统一设置网络层次：<input type="checkbox" name="allNle" id="allNle">
                </div>
            </td>
        </tr>
    </table>
</div>    
    <div id="ddDiv" style="position:absolute;z-index:2;top:230px;left:500px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
        <table border = "0" width="100%">
           <tr style="cursor:move;background:#0997F7;color:white;font:bold;height:20px">
                <td>&nbsp;&nbsp;<span key="PleaseSelectFunction"/></td>
                <td align="right"><div style="padding-right:10px"><font face="webdings" style="cursor:pointer" onclick="do_ShowExcel()">r</font></div></td>
            </tr>
           <tr>
               <td width="80%" nowrap="nowrap" align="center">
                    <input type="button" value="导出EXCEL" onclick="do_Export('xls')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <input type="button" value="导出CSV" onclick="do_Export('csv')"/>
               </td>
           </tr>
         </table>
         <iframe   src="" frameborder="0"  style="position:absolute;   visibility:inherit;   top:0px;   left:0px;  width:expression(this.parentNode.offsetWidth);   height:expression(this.parentNode.offsetHeight);   z-index:-1;"></iframe>
    </div>

    <input name="act" type="hidden">
    <input type="hidden" name="excelType"/>
    <input type="hidden" id="responsibilityUser" name="responsibilityUser" value="<%=dto.getResponsibilityUser()%>">
<%
    String widthArr[] = {"2%" , "5%" , "7%" , "12%" , "7%" , "20%" , "4%" , "9%" , "15%", "9%", "0%"};
    int index = 0;
    String tbWidth = "190%";
    if(!dto.getManyDimensionsType().equals("") && !dto.getManyDimensionsType().equals("ZCLB")){
        tbWidth = "230%";        
    }
%>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:110px;left:0px;width:100%">
    <table id="$$$headerTable$$$" class="eamHeaderTable" border="1" width="<%=tbWidth %>" >
        <tr height="23px">
            <td width="<%= widthArr[index++] %>" align=center><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
            <td width="<%= widthArr[index++] %>" align="center">标签号</td>
            <td width="<%= widthArr[index++] %>" align="center">资产名称</td>
            <td width="<%= widthArr[index++] %>" align="center">规格型号</td>
            <td width="<%= widthArr[index++] %>" align="center">资产类别</td>
            <td width="<%= widthArr[index++] %>" align="center">类别描述</td>
<%
    if (dto.getManyDimensionsType().equals("WLYS")) {
%>
            <td width="10%" align="center">逻辑网络元素</td>
<%
    } else if (dto.getManyDimensionsType().equals("TZFL")) {
%>
            <td width="10%" align="center">投资分类</td>
<%
    } else if (dto.getManyDimensionsType().equals("YWPT")) {
%>
            <td width="10%" align="center">业务平台</td>
<%
    } else if (dto.getManyDimensionsType().equals("WLCC")) {
%>
            <td width="10%" align="center">网络层次</td>
<%
    }
%>
            <td width="<%= widthArr[index++] %>" align="center">责任人</td>
            <td width="<%= widthArr[index++] %>" align="center">地点代码</td>
            <td width="<%= widthArr[index++] %>" align="center">地点描述</td>
            <td width="<%= widthArr[index++] %>" align="center">项目名称</td>
            <td width="<%= widthArr[index] %>" style="display:none" align="center">旧资产类别</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:520px;width:100%;position:absolute;top:113px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="<%=tbWidth %>" border="1" bordercolor="#666666" style="table-layout:fixed;word-break:break-all">
<%
    if (hasData) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            index = 0 ;
            row = rows.getRow(i);
            String barcode = row.getStrValue("BARCODE");
            String contentCode = row.getStrValue("CONTENT_CODE");
            String oldContentCode = row.getStrValue("OLD_CONTENT_CODE");
            String contentName = row.getStrValue("CONTENT_NAME");
            String lneId = row.getStrValue("LNE_ID");
            String cexId = row.getStrValue("CEX_ID");
            String opeId = row.getStrValue("OPE_ID");
            String nleId = row.getStrValue("NLE_ID");
            String itemName = row.getStrValue("ITEM_NAME");
            String itemSpec = row.getStrValue("ITEM_SPEC");
            String objectCode = row.getStrValue("WORKORDER_OBJECT_CODE");
            String objectName = row.getStrValue("WORKORDER_OBJECT_NAME");
            String prjName = row.getStrValue("NAME");
            String userName = row.getStrValue("USER_NAME");

%>
        <tr onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="<%= widthArr[index++] %>" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
            <td width="<%= widthArr[index++] %>" align="center"><%=barcode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=itemName%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=itemSpec%></td>
<%
            if (dto.getManyDimensionsType().equals("ZCLB")) {
%>
            <td width="<%= widthArr[index++] %>" height="100%" align="center"><input type="text" style="text-align: center" name="contentCode" id="contentCode<%=i%>" class="inputNoEmptySelect" readonly value="<%=contentCode%>" onClick="do_SelectContentLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择资产类别"><input type="hidden" name="barcode1" id="barcode1<%=i%>" value="<%=barcode%>"><input type="hidden" name="contentCodeBar" id="contentCodeBar<%=i%>" value="<%=barcode+";"+contentCode%>"><input type="hidden" name="lneId" id="lneId<%=i%>" value="<%=lneId%>"><input type="hidden" name="cexId" id="cexId<%=i%>" value="<%=cexId%>"><input type="hidden" name="opeId" id="opeId<%=i%>" value="<%=opeId%>"><input type="hidden" name="nleId" id="nleId<%=i%>" value="<%=nleId%>"></td>
            <td width="<%= widthArr[index++] %>" height="100%" align="left"><input type="text" name="contentName" id="contentName<%=i%>" class="inputNoEmptySelect" readonly value="<%=contentName%>" onClick="do_SelectContentLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择资产类别"></td>
<%
            } else if (dto.getManyDimensionsType().equals("WLYS")) {
%>
            <td width="<%= widthArr[index++] %>" align="center"><%=contentCode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=contentName%></td>
            <td width="10%" align="left" height="100%"><input type="text" name="lneName" id="lneName<%=i%>" class="inputNoEmptySelect" readonly value="<%=row.getValue("LNE_NAME")%>" onClick="do_SelectLneLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择逻辑网络元素"><input type="hidden" name="barcode1" id="barcode1<%=i%>" value="<%=barcode%>"><input type="hidden" name="contentCode" id="contentCode<%=i%>" value="<%=contentCode%>"><input type="hidden" name="contentCodeBar" id="contentCodeBar<%=i%>" value="<%=barcode+";"+contentCode%>"><input type="hidden" name="contentName" id="contentName<%=i%>" value="<%=contentName%>"><input type="hidden" name="lneId" id="lneId<%=i%>" value="<%=lneId%>"><input type="hidden" name="cexId" id="cexId<%=i%>" value="<%=cexId%>"><input type="hidden" name="opeId" id="opeId<%=i%>" value="<%=opeId%>"><input type="hidden" name="nleId" id="nleId<%=i%>" value="<%=nleId%>"></td>
<%
            } else if (dto.getManyDimensionsType().equals("TZFL")) {
%>
            <td width="<%= widthArr[index++] %>" align="center"><%=contentCode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=contentName%></td>
            <td width="10%" align="left" height="100%"><input type="text" name="cexName" id="cexName<%=i%>" class="inputNoEmptySelect" readonly value="<%=row.getValue("CEX_NAME")%>" onClick="do_SelectCexLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择投资分类"><input type="hidden" name="barcode1" id="barcode1<%=i%>" value="<%=barcode%>"><input type="hidden" name="contentCode" id="contentCode<%=i%>" value="<%=contentCode%>"><input type="hidden" name="contentCodeBar" id="contentCodeBar<%=i%>" value="<%=barcode+";"+contentCode%>"><input type="hidden" name="contentName" id="contentName<%=i%>" value="<%=contentName%>"><input type="hidden" name="lneId" id="lneId<%=i%>" value="<%=lneId%>"><input type="hidden" name="cexId" id="cexId<%=i%>" value="<%=cexId%>"><input type="hidden" name="opeId" id="opeId<%=i%>" value="<%=opeId%>"><input type="hidden" name="nleId" id="nleId<%=i%>" value="<%=nleId%>"></td>
<%
            } else if (dto.getManyDimensionsType().equals("YWPT")) {
%>
            <td width="<%= widthArr[index++] %>" align="center"><%=contentCode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=contentName%></td>
            <td width="10%" align="left" height="100%"><input type="text" name="opeName" id="opeName<%=i%>" class="inputNoEmptySelect" readonly value="<%=row.getValue("OPE_NAME")%>" onClick="do_SelectOpeLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择业务平台"><input type="hidden" name="barcode1" id="barcode1<%=i%>" value="<%=barcode%>"><input type="hidden" name="contentCode" id="contentCode<%=i%>" value="<%=contentCode%>"><input type="hidden" name="contentCodeBar" id="contentCodeBar<%=i%>" value="<%=barcode+";"+contentCode%>"><input type="hidden" name="contentName" id="contentName<%=i%>" value="<%=contentName%>"><input type="hidden" name="lneId" id="lneId<%=i%>" value="<%=lneId%>"><input type="hidden" name="cexId" id="cexId<%=i%>" value="<%=cexId%>"><input type="hidden" name="opeId" id="opeId<%=i%>" value="<%=opeId%>"><input type="hidden" name="nleId" id="nleId<%=i%>" value="<%=nleId%>"></td>
<%
            } else if (dto.getManyDimensionsType().equals("WLCC")) {
%>
            <td width="<%= widthArr[index++] %>" align="center"><%=contentCode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=contentName%></td>
            <td width="10%" align="left" height="100%"><input type="text" name="nleName" id="nleName<%=i%>" class="inputNoEmptySelect" readonly value="<%=row.getValue("NLE_NAME")%>" onClick="do_SelectNleLine(this)" onMouseMove="style.backgroundColor='#99ffff'" onMouseOut="style.backgroundColor='#FFFF99'" title="点击选择网络层次"><input type="hidden" name="barcode1" id="barcode1<%=i%>" value="<%=barcode%>"><input type="hidden" name="contentCode" id="contentCode<%=i%>" value="<%=contentCode%>"><input type="hidden" name="contentCodeBar" id="contentCodeBar<%=i%>" value="<%=barcode+";"+contentCode%>"><input type="hidden" name="contentName" id="contentName<%=i%>" value="<%=contentName%>"><input type="hidden" name="lneId" id="lneId<%=i%>" value="<%=lneId%>"><input type="hidden" name="cexId" id="cexId<%=i%>" value="<%=cexId%>"><input type="hidden" name="opeId" id="opeId<%=i%>" value="<%=opeId%>"><input type="hidden" name="nleId" id="nleId<%=i%>" value="<%=nleId%>"></td>
<%
            }
%>
            <td width="<%= widthArr[index++] %>" align="left"><%=userName%></td>
            <td width="<%= widthArr[index++] %>" align="center"><%=objectCode%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=objectName%></td>
            <td width="<%= widthArr[index++] %>" align="left"><%=prjName%></td>
            <td width="<%= widthArr[index] %>" style="display:none" align="left"><input type="hidden" name="oldContentCode" id="oldContentCode<%=i%>" class="inputNoEmptySelect" readonly value="<%=oldContentCode%>"></td>
        </tr>
<%
        }
    }
%>
    </table>
</div>
</form>
<%
    if (hasData) {
%>
<div id="pageNaviDiv" style="position:absolute;top:640px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>
</body>

</html>
<script type="text/javascript">
    function initPage() {
        var manyDimensionsType = mainFrm.manyDimensionsType.value;
        if (manyDimensionsType == "ZCLB") {
            if ("<%=dimenCount%>" == "0") {
                document.getElementById("content").checked = false;
            } else {
                document.getElementById("content").checked = true;
            }
            document.getElementById('dis1').style.display = "block";
        } else if (manyDimensionsType == "WLYS") {
            if ("<%=dimenCount%>" == "0") {
                document.getElementById("lne").checked = false;
            } else {
                document.getElementById("lne").checked = true;
            }
            document.getElementById('dis2').style.display = "block";
        } else if (manyDimensionsType == "TZFL") {
            if ("<%=dimenCount%>" == "0") {
                document.getElementById("cex").checked = false;
            } else {
                document.getElementById("cex").checked = true;
            }
            document.getElementById('dis3').style.display = "block";
        } else if (manyDimensionsType == "YWPT") {
            if ("<%=dimenCount%>" == "0") {
                document.getElementById("ope").checked = false;
            } else {
                document.getElementById("ope").checked = true;
            }
            document.getElementById('dis4').style.display = "block";
        } else if (manyDimensionsType == "WLCC") {
            if ("<%=dimenCount%>" == "0") {
                document.getElementById("nle").checked = false;
            } else {
                document.getElementById("nle").checked = true;
            }
            document.getElementById('dis5').style.display = "block";
        }
        do_SetPageWidth();
    }

	//部门发生变化,查询相应的用户，实现菜单联动
    function getDeptUserOpt(obj) {
//    	var url = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1?act=CHOOSE_DEPT&dept=" + obj.value;
//		do_ProcessSimpleAjax(url, null);
	}

	function do_ProcessResponse(responseContent){
		mainFrm.responsibilityUser.outerHTML = "<select style=\"width:100%\" name=\"responsibilityUser\">" + responseContent + "</select>";
	}



    var selectedDimensionsIndex;
    function do_saveOldChangeProjectPrep() {
        selectedDimensionsIndex = document.mainFrm.manyDimensionsType.selectedIndex;
    }

    function do_ChangeManyDimensions() {
        if (confirm("更改多维度类型将刷新当前页面!是否继续？")) {
            var manyDimensionsType = document.mainFrm.manyDimensionsType.value;
            document.mainFrm.faContentCode.value = "";
            document.mainFrm.faContentName.value = "";
            if (manyDimensionsType == "ZCLB") {
                document.getElementById("content").checked = true;
                document.getElementById("lne").checked = false;
                document.getElementById("cex").checked = false;
                document.getElementById("ope").checked = false;
                document.getElementById("nle").checked = false;
            } else if (manyDimensionsType == "WLYS") {
                document.getElementById("content").checked = false;
                document.getElementById("lne").checked = true;
                document.getElementById("cex").checked = false;
                document.getElementById("ope").checked = false;
                document.getElementById("nle").checked = false;
            } else if (manyDimensionsType == "TZFL") {
                document.getElementById("content").checked = false;
                document.getElementById("lne").checked = false;
                document.getElementById("cex").checked = true;
                document.getElementById("ope").checked = false;
                document.getElementById("nle").checked = false;
            } else if (manyDimensionsType == "YWPT") {
                document.getElementById("content").checked = false;
                document.getElementById("lne").checked = false;
                document.getElementById("cex").checked = false;
                document.getElementById("ope").checked = true;
                document.getElementById("nle").checked = false;
            } else if (manyDimensionsType == "WLCC") {
                document.getElementById("content").checked = false;
                document.getElementById("lne").checked = false;
                document.getElementById("cex").checked = false;
                document.getElementById("ope").checked = false;
                document.getElementById("nle").checked = true;
            }
            document.mainFrm.act.value = "";
            document.mainFrm.target = "_self";
            document.mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1";
            document.mainFrm.submit();
        } else {
            document.mainFrm.manyDimensionsType.selectedIndex = selectedDimensionsIndex;
        }
    }

    function do_SelectProject(){
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (objs) {
            var obj = objs[0];
            dto2Frm(obj, "mainFrm");
            document.mainFrm.prjName.value = obj["projectName"];
        } else {
            document.mainFrm.prjName.value = "";
        }
    }

    function do_SelectAddress(){
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (objs) {
            var obj = objs[0];
            dto2Frm(obj, "mainFrm");
            document.mainFrm.workorderObjectName.value = obj["workorderObjectLocation"];
        } else {
            document.mainFrm.workorderObjectName.value = "";
        }
    }

    function do_SelectContent(){
        var lookUpName = "";
        var manyDimensionsType = document.mainFrm.manyDimensionsType.value;
        if (manyDimensionsType == "ZCLB") {
            lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_CONTENT%>";
        } else if (manyDimensionsType == "WLYS") {
            lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_LNE%>";
        } else if (manyDimensionsType == "TZFL") {
            lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_CEX%>";
        } else if (manyDimensionsType == "YWPT") {
            lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_OPE%>";
        } else if (manyDimensionsType == "WLCC") {
            lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_NLE%>";
        }
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (objs) {
            var obj = objs[0];
            dto2Frm(obj, "mainFrm");
            document.mainFrm.faContentCode.value = obj["contentCode"];
            document.mainFrm.faContentName.value = obj["contentName"];
        } else {
            document.mainFrm.faContentCode.value = "";
            document.mainFrm.faContentName.value = "";
        }
    }

    function do_SelectContentLine(obj){
        var objName = obj.name;
        var objId = obj.id;
        var idNumber = objId.substring(objName.length);
        var lookUpName = "";
        var contentCode = document.getElementById("contentCode"+idNumber).value; 
        var barcode = document.getElementById("barcode1"+idNumber).value;
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_CONTENT_LINE%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "faContentCode="+contentCode;
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var contentChk = document.mainFrm.allContent;
        var isChecked = false;
        if (!contentChk.checked) {
            if (objs) {
                var obj2 = objs[0];
                document.getElementById("contentCode"+idNumber).value = obj2["contentCode"];
                document.getElementById("contentCodeBar"+idNumber).value = barcode+";"+obj2["contentCode"];
                document.getElementById("contentName"+idNumber).value = obj2["contentName"];
            }
        } else {
            var count = document.getElementsByName("contentCodeBar").length;
            for(var i = 0; i < count; i++){
                if (objs) {
                    var obj3 = objs[0];
                    if (document.getElementById("subCheck"+i)) {
                    	isChecked = document.getElementById("subCheck"+i).checked;
                    }
                    if (isChecked == true) {
	                    if (document.getElementById("contentCodeBar"+i)) { 
	                    	if (document.getElementById("contentCode"+i).value == contentCode) { //有些记录可选的资产类别不一样，业务上要求不能统一设置，要过滤掉
		                        document.getElementById("contentCode"+i).value = obj3["contentCode"];
		                        document.getElementById("contentCodeBar"+i).value = document.getElementById("barcode1"+i).value + ";" + obj3["contentCode"];
		                        document.getElementById("contentName"+i).value = obj3["contentName"];
		                    }
	                    }
                    }
                }
            }
        }
    }

    function do_SelectLneLine(obj){
        var objName = obj.name;
        var objId = obj.id;
        var idNumber = objId.substring(objName.length);
        var lookUpName = "";
        var contentCode = document.getElementById("contentCode"+idNumber).value;
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_LNE_LINE%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "faContentCode="+contentCode;
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var lneChk = document.mainFrm.allLne;
		var isChecked = false;
        if (!lneChk.checked) {
            if (objs) {
                var obj2 = objs[0];
                document.getElementById("lneId"+idNumber).value = obj2["lneId"];
                document.getElementById("lneName"+idNumber).value = obj2["lneName"];
            }
        } else {
            var count = document.getElementsByName("contentCodeBar").length;
            for(var i = 0; i < count; i++){
                if (objs) {
                    var obj3 = objs[0];
                    if (document.getElementById("subCheck"+i)) {
                    	isChecked = document.getElementById("subCheck"+i).checked;
                    }
                    if (isChecked == true) {
	                    if (document.getElementById("contentCodeBar"+i)) {
	                        document.getElementById("lneId"+i).value = obj3["lneId"];
	                        document.getElementById("lneName"+i).value = obj3["lneName"];
	                    }
	                }
                }
            }
        }
    }

    function do_SelectCexLine(obj){
        var objName = obj.name;
        var objId = obj.id;
        var idNumber = objId.substring(objName.length);
        var lookUpName = "";
        var contentCode = document.getElementById("contentCode"+idNumber).value;
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_CEX_LINE%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "faContentCode="+contentCode;
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var cexChk = document.mainFrm.allCex;
		var isChecked = false;
        if (!cexChk.checked) {
            if (objs) {
                var obj2 = objs[0];
                document.getElementById("cexId"+idNumber).value = obj2["cexId"];
                document.getElementById("cexName"+idNumber).value = obj2["cexName"];
            }
        } else {
            var count = document.getElementsByName("contentCodeBar").length;
            for(var i = 0; i < count; i++){
                if (objs) {
                    var obj3 = objs[0];
                    if (document.getElementById("subCheck"+i)) {
                    	isChecked = document.getElementById("subCheck"+i).checked;
                    }
                    if (isChecked == true) {
	                    if (document.getElementById("contentCodeBar"+i)) {
	                        document.getElementById("cexId"+i).value = obj3["cexId"];
	                        document.getElementById("cexName"+i).value = obj3["cexName"];
	                    }
	                }
                }
            }
        }
    }

    function do_SelectOpeLine(obj){
        var objName = obj.name;
        var objId = obj.id;
        var idNumber = objId.substring(objName.length);
        var lookUpName = "";
        var contentCode = document.getElementById("contentCode"+idNumber).value;
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_OPE_LINE%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "faContentCode="+contentCode;
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var opeChk = document.mainFrm.allOpe;
        var isChecked = false;
        if (!opeChk.checked) {
            if (objs) {
                var obj2 = objs[0];
                document.getElementById("opeId"+idNumber).value = obj2["opeId"];
                document.getElementById("opeName"+idNumber).value = obj2["opeName"];
            }
        } else {
            var count = document.getElementsByName("contentCodeBar").length;
            for(var i = 0; i < count; i++){
                if (objs) {
                    var obj3 = objs[0];
                    if (document.getElementById("subCheck"+i)) {
                    	isChecked = document.getElementById("subCheck"+i).checked;
                    }
                    if (isChecked == true) {
	                    if (document.getElementById("contentCodeBar"+i)) {
	                        document.getElementById("opeId"+i).value = obj3["opeId"];
	                        document.getElementById("opeName"+i).value = obj3["opeName"];
	                    }
	                }
                }
            }
        }
    }

    function do_SelectNleLine(obj){
        var objName = obj.name;
        var objId = obj.id;
        var idNumber = objId.substring(objName.length);
        var lookUpName = "";
        var contentCode = document.getElementById("contentCode"+idNumber).value;
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_NLE_LINE%>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "faContentCode="+contentCode;
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var nleChk = document.mainFrm.allNle;
        var isChecked = false;
        if (!nleChk.checked) {
            if (objs) {
                var obj2 = objs[0];
                document.getElementById("nleId"+idNumber).value = obj2["nleId"];
                document.getElementById("nleName"+idNumber).value = obj2["nleName"];
            }
        } else {
            var count = document.getElementsByName("contentCodeBar").length;
            for(var i = 0; i < count; i++){
                if (objs) {
                    var obj3 = objs[0];
                    if (document.getElementById("subCheck"+i)) {
                    	isChecked = document.getElementById("subCheck"+i).checked;
                    }
                    if (isChecked == true) {
	                    if (document.getElementById("contentCodeBar"+i)) {
	                        document.getElementById("nleId"+i).value = obj3["nleId"];
	                        document.getElementById("nleName"+i).value = obj3["nleName"];
	                    }
	                }
                }
            }
        }
    }

    function do_Search() {
    	var dept = document.getElementById('responsibilityDept').value;
        document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
        document.mainFrm.target = "_self";
        document.mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1?dept=" + dept;
        document.mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }

	function do_ShowExcel() {
		var _d = document.getElementById("ddDiv");
		var left = event.clientX;
		var top = event.clientY;
		_d.style.position = "absolute";
		_d.style.top = top + event.srcElement.offsetHeight - 10;
		_d.style.left = left - 180;
		if (_d.style.visibility == "hidden") {
			_d.style.visibility = "visible";
		}else {
			_d.style.visibility = "hidden";
		}
	}
	
    function do_Export(type) {
    	var dept = document.getElementById('responsibilityDept').value;
        document.mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
        document.mainFrm.target = "_self";
        document.mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1?dept=" + dept;
        document.mainFrm.excelType.value = type;
        document.mainFrm.submit();
        
        openExportMsgDiv();
		closeExportDiv();
    }

    function do_UpdateItem(){
    	var dept = document.getElementById('responsibilityDept').value;
        if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
            alert("请先执行查询操作，选中相应资产后再批量更新！");
            return;
        }
        if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
            alert("没有选中一条资产，不能执行保存操作！");
            return;
        }
        if (confirm("确保要更新选中设备的数据吗？继续请点击“确定”按钮？否则请点击“取消”按钮")) {
            document.mainFrm.act.value = "<%=AssetsActionConstant.UPDATE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1?dept=" + dept;
            document.mainFrm.submit();
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        }
    }
	    
	function do_SelectItemCode(){
		with(mainFrm){
			var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SYS_ITEM%>";
			var dialogWidth = 48;
			var dialogHeight = 30;
			var userPara = "itemCategory=" ;//设备专业
			var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
			if (objs) {
			    var obj = objs[0];
				dto2Frm(obj, "mainFrm");
			} else {
			}
		}
	}

    function do_SelectPerson(){
        with(mainFrm){
            var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
            var dialogWidth = 47;
            var dialogHeight = 30;
            var userPara = "deptCode=" + mainFrm.responsibilityDept.value;
            var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
            if (users) {
                var user = users[0];
                responsibilityUserName.value = user["userName"];
                responsibilityUser.value = user["employeeId"];
            } else {
                responsibilityUserName.value = "";
                responsibilityUser.value = "";
            }
        }
    }
</script>