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
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
	<title>房屋土地信息查询</title>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String isRent = (String) reqParser.getAttribute(WebAttrConstant.IS_RENT_OPTION);
    String isCertificate = (String) request.getAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION);
    AmsHouseInfoDTO housedto= (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
    String hasLandCNo = (String) request.getAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.house.servlet.GetHouseLandInfoServlet" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("房屋土地信息查询")
</script>
<div id="marqueetipMsg" style="position:absolute;top:44px;left:-259px;width:743px; z-index:10; visibility:hidden">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="">
        <td align="right">租赁日期：</td>
        <td align="left"><input type="text" name="fromDate" value="<%=fromDate%>" readOnly style="width:85%"
                                class="input_style2" onclick="gfPop.fStartPop(fromDate,toDate);"><img src="/images/calendar.gif"
                                                                                                alt="点击选择日期"
                                                                                                onclick="gfPop.fStartPop(fromDate,toDate);">
        </td>
        <td align="right">到：</td>
        <td align="left"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:85%"
                                class="input_style2" onclick="gfPop.fEndPop(fromDate,toDate);"><img src="/images/calendar.gif"
                                                                                              alt="点击选择日期"
                                                                                              onclick="gfPop.fEndPop(fromDate,toDate);">
        </td>
    </table>
</div>
<table border="0" width="100%" class="queryTable">
    <tr>
        <td width="10%" align="right">条码：</td>
        <td width="20%" align="left"><input type="text" name="barcode" style="width:80%" class="input_style1"
                                            value="<%=housedto.getBarcode()%>">
        </td>
        <td width="10%" align="right">名称：</td>
        <td width="20%" align="left"><input name="itemName" class="input_style1" style="width:80%" value = "<%=housedto.getItemName()%>"></td>
        <td width="10%" align="right">型号：</td>
        <td width="20%" align="left"><input type="text" name="itemSpec" class="input_style1" style="width:80%" value="<%=housedto.getItemSpec()%>"></td>
    </tr>
    <tr>
       <td align="right" width="10%">是否基站：</td>
       <td width="20%" align="left">
                <select name="bts" class="select_style1" style="width:80%;" >
                    <option value="" <%=housedto.getOfficeUsage().equals("--请选择--")? "selected" : ""%>>--请选择--</option>
                    <option value="基站" <%=housedto.getOfficeUsage().equals("基站")? "selected" : ""%> >基站</option>
                    <option value="非基站" <%=housedto.getOfficeUsage().equals("非基站")? "selected" : ""%>>非基站</option>
                </select></td>
       <td align="right" width="10%">房屋土地类型：</td>
       <td width="20%" align="left"><select name="category" class="select_style1" style="width:80%;">
                    <option value="" <%=housedto.getOfficeType().equals("--请选择--")? "selected":""%>>--请选择--</option>
                    <option value="房屋" <%=housedto.getOfficeType().equals("房屋")? "selected":""%>>房屋</option>
                    <option value="土地" <%=housedto.getOfficeType().equals("土地")? "selected":""%>>土地</option>
                    <option value="房地合一" <%=housedto.getOfficeType().equals("房地合一")? "selected":""%>>房地合一</option>
                </select></td>
      <td width="10%" align="right">是否有房产证：</td>
      <td width="20%"><select name="isCertificate" class="select_style1" style="width:80%" <%--onchange="do_showCertificateNo();"--%>><%=isCertificate%></select></td>
    </tr>
    <tr <%--id="certificate" style="display:none;"--%>>
        <td align="right">房产证号：</td>
        <td align="left"><input type="text" name="houseCertificateNo" class="input_style1" style="width:80%;" value="<%=housedto.getHouseCertificateNo()%>"></td>
        <td align="right" >是否有土地证： </td>
        <td align="left"><select name = "hasLandNo" class="select_style1" style="width:80%"><%=hasLandCNo%></select></td>
        <!--<td align = "right"></td>-->
        <td align="right">土地证号：</td>
        <td align="left"><input type="text" name="landCertficateNo" class="input_style1" style="width:80%;" value ="<%=housedto.getLandCertficateNo()%>"></td>
    </tr>
    <tr id="calendar" style="display:none;">
        <td align="right">租赁日期：</td>
        <td align="left"><input type="text" name="fromDate" value="<%=fromDate%>" readOnly style="width:100%" class="input_style2" onclick="gfPop.fStartPop(fromDate,toDate);">
        </td>
        <td align="left"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fStartPop(fromDate,toDate);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到：
        </td>
        <td align="left"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:100%" class="input_style2" onclick="gfPop.fEndPop(fromDate,toDate);">
        </td>
        <td><img src="/images/calendar.gif"  alt="点击选择日期"  onclick="gfPop.fEndPop(fromDate,toDate);"></td>
        <td></td>
    </tr>
    <tr>
    	<td colspan="4"></td>
    	<td></td>
    	<td align="left">
    		<img src="/images/eam_images/search.jpg" alt="查询未处理信息" onClick="do_search(); return false;">
    		<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
    	</td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<script type="text/javascript">
    var columnArr = new Array("条码","名称","型号", "地点","是否基站","类型","是否处理");
    var widthArr = new Array("11%", "17%","17%","21%","11%","10%","8%");
    printTableHead(columnArr, widthArr);
</script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:245px;width:100%;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" >
          <td height="22" width="11%" align="center"><%=row.getValue("BARCODE")%>
            </td>
              <td height="22" width="17%" align="left"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="17%" align="left"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td height="22" width="21%" align="left"><%=row.getValue("HOUSE_ADDRESS")%>
            </td>
            <td height="22" width="11%" align="left"><%=row.getValue("OFFICE_USAGE")%>
            </td>
            <td height="22" width="10%" align="left"><%=row.getValue("OFFICE_TYPE")%>
            </td>
            <td height="22" width="8%" align="center"><%=row.getValue("ATTRIBUTE2")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetHouseLandInfoServlet";
        mainFrm.submit();
    }

    function show_detail(barcode, systemId,officeUsage,officeType) {
        <%--mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>

       if((officeUsage!=="")&&(officeType!=="")){

         var url = "/servlet/com.sino.ams.system.house.servlet.GetHouseLandInfoServlet?act=SUBMIT_ACTION&barcode=" + barcode;
//        var url = "/system/house/chooseHouseCategory.jsp";
        var winStyle = "height=530px,width=850px,top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes,help=no";
        window.open(url, "_blank", winStyle);

       }else{


        var url = "/servlet/com.sino.ams.system.house.servlet.GetHouseLandInfoServlet?act=SUBMIT_ACTION&barcode=" + barcode;
//        var url = "/system/house/chooseHouseCategory.jsp";
        var winStyle = "height=200,width=320, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,help=no,top=100,left=100";
        window.open(url, "_house", winStyle);

        <%--mainFrm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";--%>
//        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet?barcode=" + barcode + "&systemId=" + systemId;
//        mainFrm.submit();
    }       }

    function do_misIn() {   //从mis导入房屋土地的信息                WorkPersonServlet
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetHouseLandInfoServlet";
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
        //display:inline
    }

    function do_showCalendar() {
        if (mainFrm.isRent.value == "Y") {
            document.getElementById("calendar").style.display = "block";
        } else {
            document.getElementById("calendar").style.display = "none";
            document.mainFrm.fromDate.value = "";
            document.mainFrm.toDate.value = "";
        }
    }
</script>