<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-15
  Time: 13:30:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
	<title>房屋信息查询</title>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String barcode = StrUtil.nullToString(request.getParameter("barcode"));
    String isRent = (String) request.getAttribute(WebAttrConstant.IS_RENT_OPTION);
    String isCertificate = (String) request.getAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION);
    String houseStatus = (String) request.getAttribute(WebAttrConstant.HOUSE_STATUS_OPTION);
%>

<body onkeydown="autoExeFunction('do_search()');" onload="initPage()">

<form action="/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("房屋信息查询")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table border="0" width="100%" class="queryTable">
        <tr>                    
            <td width="10%" align="right">房屋条码：</td>
            <td width="15%" align="left"><input type="text" name="barcode" class="input_style1" style="width:80%" value="<%=barcode%>">
            </td>

            <td width="11%" align="right">是否有产权证：</td>
            <td width="15%" align="left"><select name="isCertificate" class="select_style1" style="width:80%"><%=isCertificate%>
            </select></td>
            <td width="15%" align="left">
             <img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
            </td>
        </tr>
        <tr>
            <td  align="right">房屋状态：</td>
            <td ><select name="houseStatus" class="select_style1" style="width:80%"><%=houseStatus%>
            </select></td>
            <td align="right">是否租赁：</td>
            <td align="left" ><select style="width:80%" name="isRent" class="select_style1" onchange="do_showCalendar();"><%=isRent%>
            </select></td>
            <td  align="left">
                <img src="/images/eam_images/search.jpg" alt="查询房屋" onClick="do_search(); return false;">
                <!--<img src="/images/eam_images/new_add.jpg"
                                                                                                         alt="新增房屋"
                                                                                                         onClick="do_add(); return false;">
                -->                                                                                                         
            </td>
        </tr>
        <tr id="calendar" style="display:none;">
            <td align="right">租赁日期：</td>
            <td align="left"><input type="text" name="fromDate" value="<%=fromDate%>" readOnly class="input_style2" 
                                    style="width:80%"
                                    onclick="gfPop.fStartPop(fromDate,toDate);"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,toDate);"></td>
            <td align="right"> 到：
            </td>
            <td align="left" ><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:80%" class="input_style2" 
                                                 onclick="gfPop.fEndPop(fromDate,toDate);">

           <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate);"></td>
            <td>&nbsp;</td>
            
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">


    <script type="text/javascript">
        var columnArr = new Array("房屋条码", "产权证号", "所属区县", "所在地点", "是否租赁", "出租人", "租金", "租赁日期", "截至日期");
        var widthArr = new Array("11%", "8%", "8%", "18%", "7%", "10%", "5%", "11%", "11%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="show_detail('<%=row.getValue("BARCODE")%>','<%=row.getValue("SYSTEMID")%>')">
                <td height="22" width="11%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td height="22" width="8%" align="right"><%=row.getValue("HOUSE_CERTIFICATE_NO")%>
                </td>
                <td height="22" width="8%" align="center"><%=row.getValue("COUNTY_NAME")%>
                </td>
                <td height="22" width="18%"><%=row.getValue("HOUSE_ADDRESS")%>
                </td>
                <td height="22" width="7%" align="center"><%=row.getValue("IS_RENT")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("RENT_PERSON")%>
                </td>
                <td height="22" width="5%" align="right"><%=row.getValue("RENTAL")%>
                </td>
                <%--<td height="22" width="11%"><%=row.getValue("PAY_TYPE")%>--%>
                <%--</td>--%>
                <td height="22" width="11%" align="center"><%=row.getValue("RENT_DATE")%>
                </td>
                <td height="22" width="11%" align="center"><%=row.getValue("END_DATE")%>
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
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
        mainFrm.submit();
    }

    function show_detail(barcode, systemId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?barcode=" + barcode + "&systemId=" + systemId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
        mainFrm.submit();
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
            function initPage(){
        if (mainFrm.isRent.value == "Y") {
            document.getElementById("calendar").style.display = "block";
        }
    }
    //display:inline
</script>