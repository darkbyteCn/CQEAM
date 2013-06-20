<%--
  Created sunny song
  User: sunny song
  Date: 2008-3-13
  Time: 8:21:56
  To 资产条码变动同步
--%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.match.dto.BarcodeMatchDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>资产条码变动同步</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("资产条码变动同步")
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String action = parser.getParameter("act");
    Row row = null;
    EamSyschronizeDTO dto =(EamSyschronizeDTO)request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);

%>
<body onkeydown="autoExeFunction('do_search()')" >
<%=WebConstant.WAIT_TIP_MSG%>


<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.AssetsBarCodeUpdateServlet">
        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
   <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="position:absolute;top:23px;width:100%">

        <tr>
            <td align="right" width="10%">资产地点：</td>
            <td align="left" width="20%"><input type="text" name="newAssetsLocation" value="<%=dto.getNewAssetsLocation()%>"
                                     style="width:80%"></td>
            <td align="right" width="10%">原资产标签：</td>
            <td align="left" width="20%"><input type="text" name="tagNumberFrom" value="<%=dto.getTagNumberFrom()%>"
                                     style="width:80%"></td>
            <td align="right" width="10%">新资产标签：</td>
            <td align="left" width="20%"><input type="text" name="tagNumberTo" value="<%=dto.getTagNumberTo()%>"
                                     style="width:80%"></td>

            <td align="left">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'"
                     onclick="do_search();" alt="查询"></td>
            <td align="left">
                <img src="/images/button/synchronize.gif" style="cursor:'hand'"
                     onclick="do_syschronize();" alt="同步"></td>
        </tr>
     </table>
<div id ="headDiv" style="position:absolute;width:841px;overflow:hidden;top:48px;padding:0px; margin:0px;">
    <table width="140%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('titleCheck','systemids')"></td>
            <td width="10%" align="center">EAM新标签号</td>
            <td width="10%" align="center">MIS原资产标签号</td>
            <td width="10%" align="center">MIS资产编号</td>
            <td width="10%" align="center">资产描述</td>                  
            <td width="10%" align="center">资产型号</td>
            <td width="10%" align="center">EAM资产地点</td>
            <td width="10%" align="center">EAM责任部门</td>
            <td width="14%" align="center">EAM责任人</td>
        </tr>
    </table>
</div>
    
 <div style="overflow:scroll;height:72%;width:856px;position:absolute;top:78px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">

    <table width="140%" border="1" bordercolor="#666666">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="2%" align="center"><input id="systemids" type="checkbox" name="systemids" value="<%=row.getValue("ASSET_ID")%>"></td>
            <td width="10%" align="left" ><%=row.getValue("BARCODE")%></td>
            <td width="10%" align="left" ><%=row.getValue("TAG_NUMBER")%></td>
            <td width="10%" align="left" ><%=row.getValue("ASSET_NUMBER")%></td>
            <td width="10%" align="left" ><%=row.getValue("ITEM_NAME")%></td>
            <td width="10%" align="left" ><%=row.getValue("ITEM_SPEC")%></td>
            <td width="10%" align="left" ><%=row.getValue("ASSETS_LOCATION")%></td>
            <td width="10%" align="left" ><%=row.getValue("DEPT_NAME")%></td>
            <td width="14%" align="left" ><%=row.getValue("USER_NAME")%></td>
        </tr>
<%
        }
%>
  </table>
</div>
</form>

<%
    }
%>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>

<script type="text/javascript">

function do_search() {
//	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

/**
 * 功能：同步数据
 */
function do_syschronize(){
    var systemid =getCheckBoxValue("systemids");
    if(systemid== "") {
        alert("请选择同步是数据");
        return;
    } else {
     mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AssetsBarCodeUpdateServlet?act=SYSCHRONIZE_ACTION";
     mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
	 mainFrm.submit();
    }

   }




</script>