<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsStatisDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-16
  Time: 20:52:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>统计查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')" >
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsAssetsStatisDTO dto = (AmsAssetsStatisDTO) request.getAttribute("AMSBJTRANSNOHDTO");
            //    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsStatisServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("<%=request.getAttribute("title") %>")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <%
              if (request.getAttribute("trans").equals("scanspot"))
              {
            %>
            <td width=10% align="left"> 地点名称：</td>
            <td width="22%" align="left"><input name="organizationId" id="organizationId"
                                    style="width:100%" />    
            </td>
             <% 
              } else {
             %>
             <td width=10% align="left"> 公司名称：</td>
            <td width="22%" align="left"><select name="organizationId" id="organizationId"
                                    style="width:100%"><%=request.getAttribute("OU")%>    
            </select></td>
             <%
              }
             %> 
             <%
              if (request.getAttribute("trans").equals("weekspot")  || request.getAttribute("trans").equals("weekspot_m") 
 					|| request.getAttribute("trans").equals("weekscan_m")  || request.getAttribute("trans").equals("weekscan")
 					|| request.getAttribute("trans").equals("weekscan_b")  || request.getAttribute("trans").equals("weekspot_b")
 					|| request.getAttribute("trans").equals("weekscan_n")  || request.getAttribute("trans").equals("weekspot_n"))
              {
            %>
            <td width="15%" align="right">开始日期：</td>
            <td><input type="text" name="fromDate" value="<%=dto.getFromDate()%>" width="60"
                       title="点击选择开始日期" readonly class="readonlyInput"
                       onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onClick="gfPop.fStartPop(fromDate, fromDate)" >
            </td>
            <td width="7%" align="right">截止日期：</td>
            <td><input type="text" name="toDate" value="<%=dto.getToDate()%>"  width="60"
                         title="点击选择截止日期" readonly class="readonlyInput"
                       onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onClick="gfPop.fEndPop(toDate, toDate)">
            </td>
             <%
              } else if (request.getAttribute("trans").equals("finishspot") || request.getAttribute("trans").equals("scan")  ||
 					  request.getAttribute("trans").equals("finishspot_m") || request.getAttribute("trans").equals("scan_m")
 					  || request.getAttribute("trans").equals("scan_b")  || request.getAttribute("trans").equals("scan_n") 
 					  ||  request.getAttribute("trans").equals("finishspot_n")){
            %> 
            <td width="37%" align="right">截止日期：</td>
            <td><input type="text" name="toDate" value="<%=dto.getToDate()%>"  width="60"
                         title="点击选择截止日期" readonly class="readonlyInput"
                       onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onClick="gfPop.fEndPop(toDate, toDate)">
            </td>
            <%
             }else {
            %>  
            <td width="45%" align="right"></td>
            <%
            }
            %>
            <td width=10% align="center"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
        </tr>
    </table>
    <%--<input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <input type="hidden" name="title" value="">
    <script type="text/javascript">
        var  trans="<%=request.getAttribute("trans")%>";
        if (trans=="scan"  ||  trans=="weekscan"   ||  trans=="scanspot"
            ||  trans=="scan_m" || trans=="weekscan_m"
            ||  trans=="scan_b"  || trans=="weekscan_b"
            ||  trans=="scan_n"  || trans=="weekscan_n")
        {
           var columnArr = new Array("公司名称", "扫描条码总数");   
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="finishspot"   ||   trans=="weekspot" ||  
            trans=="finishspot_m"  ||  trans=="weekspot_m" 
            ||  trans=="spot_b"  ||  trans=="weekspot_b"  
            ||  trans=="finishspot_n"  ||  trans=="weekspot_n")
        {
           var columnArr = new Array("公司名称", "完工地点总数"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="spot_m" )
        {
           var columnArr = new Array("公司名称", "地点总数-机房"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="asset_b" )
        {
           var columnArr = new Array("公司名称", "资产总数-基站"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="dayspot_b")
        {
          var columnArr = new Array("公司名称", "日   期","地点总数-基站"); 
          var widthArr = new Array("30%", "20%","30%");
          printTableHead(columnArr, widthArr); 
        }else if (trans=="spot_n" )
        {
           var columnArr = new Array("公司名称", "地点总数-网优"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="asset")
        {
           var columnArr = new Array("公司名称", "资产总数-管理资产"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }else if (trans=="spot")
        {
           var columnArr = new Array("公司名称", "地点总数- 管理资产"); 
           var widthArr = new Array("40%", "40%");
           printTableHead(columnArr, widthArr); 
        }
    </script>
    <div style="overflow-y:scroll;height:312px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" >
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>  
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <%
                  if (request.getAttribute("trans").equals("dayspot_b")){
                %>
                <td width="30%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="20%" align="center"><%=row.getValue("DD")%></td>
                <td width="30%" align="center"><%=row.getValue("TOTAL")%> </td>
                <%
                  }else {
                 %>
                <td width="40%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="40%" align="center"><%=row.getValue("TOTAL")%> </td>
               <%
                }
               %>
            </tr>
            <%
               }
             }
            %>
        </table>
    </div>

</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        
         //document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
         mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
         mainForm.transType.value = "<%=request.getAttribute("trans")%>";
          mainForm.title.value = "<%=request.getAttribute("title")%>";
         mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_drop() {
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS');
        //        dropSpecialOption(transStatus, 'APPROVED');
    }
    function do_ShowDetail(transId, transType) {
     //   mainForm.transId.value = transId;
      //  mainForm.transType.value = transType;
      //  var url = "/servlet/com.sino.ams.spare.query.servlet.AmsBjTransQueryServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId + "&transType=" + transType;
      //  var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
     //   window.open(url, "instrum", popscript);
    }
</script>