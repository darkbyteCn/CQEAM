<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    EamSyschronizeDTO sysDTO = (EamSyschronizeDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    DTOSet lineSet = (DTOSet) request.getAttribute("DTOSET");
    String barcode=null;
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String action = parser.getParameter("act");
%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
	<script language="javascript" src="/WebLibary/js/Constant.js"></script>
	<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script language="javascript" src="/WebLibary/js/jslib.js"></script>
	<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.TransDisposeServlet">
    <script type="text/javascript">
       printTitleBar("同步详细信息");
    </script>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:20px;left:1px;width:98.5%">
        <table class="headerTable" border="1" width="140%">
            <tr height=20px>
                <td align=center width="5%">状态</td>
                <td align=center width="8%">错误信息</td>
                <td align=center width="8%">原资产条码</td>
                <td align=center width="8%">新资产条码</td>
                <td align=center width="5%">资产编号</td>
                <td align=center width="8%">资产名称</td>
                <td align=center width="6%">EAM资产型号</td>
                <td align=center width="6%">MIS资产型号</td>
                <td align=center width="10%">EAM资产地点</td>
                <td align=center width="10%">MIS资产地点</td>
                <td align=center width="8%">EAM责任人</td>
                <td align=center width="8%">MIS责任人</td>
                <td align=center width="10%">备注</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:40px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666">
            <%
                if (lineSet != null && !lineSet.isEmpty()) {
                    EamSyschronizeDTO lineDTO = null;
                    for (int i = 0; i < lineSet.getSize(); i++) {
                        lineDTO = (EamSyschronizeDTO) lineSet.getDTO(i);
                        barcode=lineDTO.getTagNumberFrom();
            %>
            <tr class="dataTR" >
                <td width="5%" align="left"><%=lineDTO.getTransStatus()%></td>
                <td width="8%" align="left"><%=lineDTO.getMsg()%></td>
                <td width="8%" align="left"><%=lineDTO.getTagNumberFrom()%></td>
                <td width="8%" align="left"><%=lineDTO.getTagNumberTo()%></td>
                <td width="5%" align="left"><%=lineDTO.getAssetId()%></td>
                <td width="8%" align="left"><%=lineDTO.getNameTo()%></td>
                <td width="6%" align="left"><%=lineDTO.getModelFrom()%></td>
                <td width="6%" align="left"><%=lineDTO.getModelTo()%></td>
                <td width="10%" align="left"><%=lineDTO.getLocationFrom()%></td>
                <td width="10%" align="left"><%=lineDTO.getLocationTo()%></td>
                <td width="8%" align="left"><%=lineDTO.getOldUserName()%></td>
                <td width="8%" align="left"><%=lineDTO.getNewUserName()%></td>
                <td width="10%" align="left"><%=lineDTO.getTransErrorMsg()%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <table align='center'>
            <tr align='center'>
                <td align='center'><p align="center"><img border="0" src="/images/eam_images/close.jpg"  onclick="self.close()"></p></td>
            </tr>
        </table>
        <!--/div>
   <div id="dd" style="overflow:hidden;position:absolute;top:<%=(lineSet.getSize() +1)*25 + 50%>px;left:1px;width:990px">
    <table align='center'>
        <tr align='center'>
            <td align='center'><p align="center"><img border="0" src="/images/eam_images/close.jpg" onclick="self.close()"></p></td>
        </tr>
    </table>
     </div-->
        <input type="hidden" name="act">
        <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
                src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
                style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
        </iframe>
</form>
</body>
</html>
<script type="text/javascript">
    function do_show(logId) {
        var bar='<%=barcode%>'  ;
        if(bar==null){

        }else{
        mainFrm.act.value = "show";
        var url = "/servlet/com.sino.ams.synchronize.servlet.TransDisposeServlet";
        url += "?logId=" + logId+"&act="+"show";
        var style = "dialogleft:100px;dialogtop:100px;dialogwidth:800px;dialogheight:600px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
        window.showModalDialog(url, null, style);  }
    }
</script>

