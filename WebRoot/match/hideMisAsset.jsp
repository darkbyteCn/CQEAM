<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-12-5
  Time: 10:56:56
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
    <title>条码匹配</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript">
        <%
//         BarcodeMatchDTO barcodeMatchDTO=(BarcodeMatchDTO)request.getAttribute(WebAttrConstant.BARCODE_MATCH_DTO);
       String assetsLocation = (String)request.getAttribute(WebAttrConstant.COUNTY_OPTION);
        %>
        var ArrAction1 = new Array(true, "屏蔽", "act_query.gif", "屏蔽MIS", "hideMis");
        var ArrActions = new Array(ArrAction1);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
</script>
</head>
<script type="text/javascript">
    printTitleBar("MIS资产屏蔽")
</script>
<script>
  printToolBar();
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String assetsDescription = parser.getParameter("assetsDescription");
    String workorderObjectName = parser.getParameter("workorderObjectName");
    String domethod = parser.getParameter("domethod");
    String ret = parser.getParameter("ret");
%>
<body onkeydown="autoExeFunction('do_search()')" onload ="do_in();">
<%=WebConstant.WAIT_TIP_MSG%>
<table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF">
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.match.servlet.HideMisAssetServlet">
        <input type="hidden" name="act">
        <input type="hidden" name="reMark">
        <tr>
            <td width="15%" align="right">查询关键字：</td>
            <td width="16%"><input style="width:100%" type="text" name="assetsDescription" value="<%=assetsDescription%>"></td>
            <td width="10%" align="right">县：</td>
            <td width="15%"><select style="width:100%" type="text" name="assetsLocation" ><%=assetsLocation%></select></td>
            <td align="right" width="13%">地 点：</td>
            <td width="20%"><input type="text" name="workorderObjectName" value="<%=workorderObjectName%>" style="width:85%"><a href="#"
                                                                                                              onClick="SelectVendorId(); "
                                                                                                              title="点击选择地点"
                                                                                                              class="linka">[…]</a>
            </td>
            <td align="center">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'"
                     onclick="do_search();" alt="查询"></td>
        </tr>
</table>
<input type="hidden" name="domethod" value="<%=domethod%>">
<input type="hidden" name="ret" value="<%=ret%>">
<div style="position:absolute;top:73px;left:1px;width:833px">
    <table width="100%" class="headerTable" border="1">
        <tr height="20">
            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('titleCheck','assetIds')"></td>
            <td width="12%" align="center">地点</td>
            <td width="10%" align="center">资产条码</td>
            <td width="13%" align="center">设备描述</td>
            <td width="13%" align="center">设备型号</td>
            <td width="7%" align="center">原值</td>
            <td width="7%" align="center">剩余数量</td>
            <td width="4%" align="center">单位</td>
            <td width="9%" align="center">资产分类1</td>
            <td width="9%" align="center">资产分类2</td>
            <td width="14%" align="center">所属项目</td>
        </tr>
    </table>
</div>
<div style="overflow-y:scroll;position:absolute;top:95px;left:0px;width:850px;height:70%">
    <table width="100%" border="1" bordercolor="#666666">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="2%" align="center"><input type="checkbox" name="assetIds" value="<%=row.getValue("ASSET_ID")%>"></td>
            <td width="12%" align="center" ><%=row.getValue("MISLOCATION")%></td>
            <td width="10%" align="left" ><%=row.getValue("TAG_NUMBER")%></td>
            <td width="13%" align="left" ><%=row.getValue("ASSETS_DESCRIPTION")%></td>
            <td width="13%" align="center" ><%=row.getValue("MODEL_NUMBER")%></td>
            <td width="7%" align="right" ><%=row.getValue("COST")%></td>
            <td width="7%" align="right" ><%=row.getValue("CURRENT_UNITS")%></td>
            <td width="4%" align="center" ><%=row.getValue("UNIT_OF_MEASURE")%></td>
            <td width="9%" align="center" ><%=row.getValue("FA_CATEGORY1")%></td>
            <td width="9%" align="center" ><%=row.getValue("FA_CATEGORY2")%></td>
            <td width="14%" align="left" ><%=row.getValue("PROJECT_NAME")%></td>
        </tr>
<%
        }
%>
  </table>
</div>
</form>
<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

function show_detail(itemCode) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    var url = "/servlet/com.sino.ams.system.item.servlet.EquipDistriServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&itemCode=" + itemCode;
    var popscript = 'width=500,height=300,top=100,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes';
    window.open(url, 'equip', popscript);
//    mainFrm.submit();
}

function SelectVendorId(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BTS%>";
    var popscript = "dialogWidth:47;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
        for(var i = 0; i < vendorNames.length; i++){
            vendorName = vendorNames[i];
            dto2Frm(vendorName, "mainFrm");
        }
    }
}


function itemMatch(){    //进行匹配操作
    mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
	mainFrm.submit();
}

function  batchMatch() {  //进行批量匹配操作
    mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
	mainFrm.submit();
}

function method1() {
//    if (working)
//    {
//        alert('正在处理中，请稍候...');
//        return false;
//    }
    document.forms[0].domethod.value = "1";
    document.forms[0].submit();
//    working = true;
}
function method2() {
//    if (working)
//    {
//        alert('正在处理中，请稍候...');
//        return false;
//    }
    document.forms[0].domethod.value = "2";
    document.forms[0].submit();
//    working = true;
}

function do_in(){

  if (document.forms[0].ret.value == "Y")
    {
         alert("屏蔽成功!")
      document.forms[0].ret.value ="";

    } else  if (document.forms[0].ret.value == "N"){
      alert("所选设备中存在其它用户已经屏蔽的设备！");
      document.forms[0].ret.value ="";
    }
      }

function hideMis() {
    var checkedCount = getCheckedBoxCount("assetIds");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
        return;
    } else {
//        alert(1);
    <%--var url = "/servlet/com.sino.ams.match.servlet.HideMisAssetServlet?act=<%=WebActionConstant.NEW_ACTION%>";--%>
        var url = "/match/chooseRemark.jsp";
        var popscript = "dialogWidth=290px;dialogHeight=130px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
        var retValue = window.showModalDialog(url, 'hidden', popscript);
//                alert(retValue);
        mainFrm.reMark.value = retValue;
//                alert(retValue);
        if (retValue ) {
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            mainFrm.submit();
        }

    }
}
</script>