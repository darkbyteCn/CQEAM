<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.fixing.dto.EtsItemInfoDTO" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseInfoDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-18
  Time: 15:57:18
  Functin:租赁房屋土地维护。
--%>
<html>
<head>
	<title>租赁房屋土地维护</title>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String rentUnit = StrUtil.nullToString(request.getParameter("rentUnit"));
    String isRent = (String) reqParser.getAttribute(WebAttrConstant.IS_RENT_OPTION);
    String isCertificate = (String) request.getAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION);
    String hasLandCNo = (String) request.getAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION);
    AmsHouseInfoDTO housedto = (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
%>
<body onkeydown="autoExeFunction('do_search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("租赁房屋土地维护")
</script>
<table border="0" width="100%" class="queryTable">
    <tr>
        <td width="10%" align="right">条码：</td>
        <td width="14%" align="left"><input type="text" name="barcode" style="width:100%" class="input_style1"
                                            value="<%=housedto.getBarcode()%>">
        </td>
        <td width="12%" align="right">名称：</td>
        <td width="14%" align="left"><input name="itemName" style="width:100%" class="input_style1" value = "<%=housedto.getItemName()%>"></td>
        <td width="12%" align="right">型号：</td>
        <td width="14%" align="left"><input name="itemSpec" style="width:100%" class="input_style1" value = "<%=housedto.getItemSpec()%>"></td>
        <td width = "10%" align="right">土地性质：</td>
        <td width = "14%" ><select name="landType" class="select_style1" style="width:100%">
                    <option value= "">--请选择--</option>
                    <option value="出让" <%=housedto.getLandType().equals("出让")? "selected":""%>>出让</option>
                    <option value="划拨" <%=housedto.getLandType().equals("划拨")? "selected":""%>>划拨</option>
                    <option value="其它" <%=housedto.getLandType().equals("其它")? "selected":""%>>其它</option>
                </select>
        </td>
    </tr>
     <tr>
       <td align="right" >是否基站：</td>
       <td align="left">
                <select name="officeUsage" class="select_style1" style="width:100%" >
                    <option value="">--请选择--</option>
                    <option value="基站" <%=housedto.getOfficeUsage().equals("基站")? "selected":""%> >基站</option>
                    <option value="非基站" <%=housedto.getOfficeUsage().equals("非基站")? "selected" : ""%>>非基站</option>
                </select></td>
       <td align="right" >房屋土地类型：</td>
       <td  align="left"><select name="officeType" class="select_style1" style="width:100%;">
                    <option value="">--请选择--</option>
                    <option value="房屋" <%=housedto.getOfficeType().equals("房屋")? "selected":""%>>房屋</option>
                    <option value="土地" <%=housedto.getOfficeType().equals("土地")? "selected":""%>>土地</option>
                    <option value="房地合一" <%=housedto.getOfficeType().equals("房地合一")? "selected":""%>>房地合一</option>
                </select></td>
      <td  align="right">房产证号：</td>
      <td align="right"><input type="text" name="houseCertificateNo" class="input_style1" style="width:100%;" value="<%=housedto.getHouseCertificateNo()%>"></td>
      <td align= "right">土地证号：</td>
      <td align = "right"><input type="text" name="landCertficateNo" class="input_style1" style="width:100%;" value ="<%=housedto.getLandCertficateNo()%>"></td>
    </tr>
    <tr>
        <td align="right">租赁截至日期：</td>
        <td align="left"><input type="text" name="fromDate" value="<%=fromDate%>" readOnly  class= "input_style2"
                                    style="width:100%" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,toDate);"></td>
        <td align="right">到：</td>
        <td align="right"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:100%"  class= "input_style2"
                                               alt="点击选择日期"  onclick="gfPop.fEndPop(fromDate,toDate);"></td>
        <td align="right">出租单位：</td>
        <td align="right"><input type="text" name = "rentUnit" class="input_style1" style="width:100%" value="<%=rentUnit%>"></td>
        <td align="right" colspan="2">
        	<img src="/images/eam_images/search.jpg"  alt="查询未处理信息" onClick="do_search(); return false;">
       	 	<img  src="/images/eam_images/new_add.jpg" alt="新增信息" onClick=" do_creat(); return false;">
        	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
        </td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<div id="headDiv" style="position:absolute;top:97px;left:1px;width:840px">
    <table class="headerTable" border="1" width="200%" >
        <tr>
            <td height="22" width="7%" align="center">条码</td>
            <td height="22" width="14%" align="center">名称</td>
            <td height="22" width="14%" align="center">型号</td>
            <td height="22" width="14%" align="center">地点</td>
            <td height="22" width="5%" align="center">是否基站</td>
            <td height="22" width="5%" align="center">类型</td>
            <td height="22" width="6%" align="center">房产证号</td>
            <td height="22" width="6%" align="center">土地证号</td>
            <td height="22" width="6%" align="center">土地性质</td>
            
            <td height="22" width="5%" align="center">租金</td>
            <td height="22" width="5%" align="center">出租单位</td>

            <td height="22" width="5%" align="center">起始日期</td>
            <td height="22" width="5%" align="center">截至日期</td>
        </tr>

    </table>
</div>

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div id="dataDiv" style="overflow:scroll;height:66%;width:857px;position:absolute;top:120px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
     <table width="200%" border="1" bordercolor="#666666" >
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="show_detail('<%=row.getValue("BARCODE")%>','<%=row.getValue("SYSTEMID")%>','<%=row.getValue("OFFICE_USAGE")%>','<%=row.getValue("OFFICE_TYPE")%>','<%=row.getValue("ATTRIBUTE2")%>','<%=row.getValue("RENT_ID")%>')">
             <td height="22" width="7%" align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td height="22" width="14%" align="left"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="14%" align="left"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td height="22" width="14%" align="left"><%=row.getValue("HOUSE_ADDRESS")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("OFFICE_USAGE")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("OFFICE_TYPE")%>
            </td>
            <td height="22" width="6%" align="left"><%=row.getValue("HOUSE_CERTIFICATE_NO")%>
            </td>
            <td height="22" width="6%" align="left"><%=row.getValue("LAND_CERTFICATE_NO")%>
            </td>
            <td height="22" width="6%" align="center"><%=row.getValue("LAND_TYPE")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("RENT_FEE")%>
            </td>
            <td height="22" width="5%" align="center"><%=row.getValue("RENT_UNIT")%>
            </td>
             <td height="22" width="5%" align="center"><%=row.getValue("RENT_START_DATE").toString().substring(0,10)%>
            </td>
             <td height="22" width="5%" align="center"><%=row.getValue("RENT_END_DATE").toString().substring(0,10)%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
<div style="position:absolute;top:458px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<iframe name="downFrm" src="" style="display:none"></iframe>
<script type="text/javascript">

function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet";
    mainFrm.submit();
}

function do_creat() {
//    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    <%--mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";--%>
//    mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet";
//    mainFrm.submit();
       var url = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=CREATE_ACTION";
       var winStyle = "height=125,width=320, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,help=no,top=200,left=200";
       window.open(url, "_house", winStyle);
}

function show_detail(barcode, systemId, officeUsage, officeType, temp,rentId) {
    var height = "250px";
    if (((officeUsage == "非基站") && (officeType == "房屋")) || ((officeUsage == "非基站") && (officeType == "房地合一"))) {
        height = "420px";
    }
//    if (temp == "未处理") {
        var url = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=SUBMIT_ACTION&barcode=" + barcode + "&bts=" + officeUsage + "&category=" + officeType + "&rentId=" + rentId;
        var winStyle = "height=125,width=320, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,help=no,top=200,left=200";
        window.open(url, "_house", winStyle);
}

function do_misIn() {   //从mis导入房屋土地的信息                WorkPersonServlet
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet";
    mainFrm.submit();
}

function do_Export() {                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet";
    mainFrm.submit();
}

function do_showCalendar1() {
    var isRent = document.all["isRent"].options;
    for (var i = 0; i < isRent.length; i++) {
        if (isRent[i].selected && isRent[i].value == "Y") {
            //        mainFrm.rentInfo.style.display = "none";
            //        document.all["rentInfo"].style.display = "inline";
            document.getElementById("marqueetipMsg").style.visibility = "visible";
        } else if (isRent[i].selected && isRent[i].value == "N") {
            document.getElementById("marqueetipMsg").style.visibility = "hidden";
            document.mainFrm.fromDate.value = "";
            document.mainFrm.toDate.value = "";

        } else if (isRent[i].selected && isRent[i].value == "") {
            document.getElementById("marqueetipMsg").style.visibility = "hidden";
            document.mainFrm.fromDate.value = "";
            document.mainFrm.toDate.value = "";
        }
    }
}

function do_showCertificateNo() {
    if (mainFrm.isCertificate.value == "Y") {
        document.getElementById("certificate").style.display = "block";
    } else {
        document.getElementById("certificate").style.display = "none";
        document.mainFrm.fromDate.value = "";
        document.mainFrm.toDate.value = "";
    }
}
</script>