<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-6-9
  Time: 18:32:55
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<%
    AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    String orgId = Integer.toString(userAccount.getOrganizationId());
%>
    <title>低值易耗资产统计(公司)</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage()" onkeydown="autoExeFunction('do_Search();')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.newasset.report.servlet.DzyhAssetsReportServlet">
    <input type="hidden" name="act">
    <input type="hidden" name="companyName">
    <script type="text/javascript">
        printTitleBar("低值易耗资产统计（公司）")
    </script>
    <table width="100%" border="1" class="queryHeadBg">
        <tr height="15">
            <td align="right" width="8%">公司：</td>
            <td width="20%"><select style="width:100%" class="select_style1" name="organizationId"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select></td>
            <td align="right" width="72%">
            	<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
            	<img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();">
           	</td>
        </tr>
    </table>
</form>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:43px;left:1px;width:830px">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="25%" align="center">公司</td>
                <td width="25%" align="center">低值易耗总量</td>
                <td width="25%" align="center">非TD数量</td>
                <td width="25%" align="center">TD数量</td>                
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:330px;width:847px;position:absolute;top:67px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <%--注释掉部分为点击明细，占时不用--%>
            <%--<tr height="20" title="点击查看公司“<%=row.getValue("COMPANY")%>”低值易耗" onClick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("COMPANY")%>')">--%>
            <tr height="20">
                <td width="25%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("COMPANY")%>"></td>
                <td width="25%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("DZYH_COUNT")%>"></td>
                <td width="25%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("DZYH_NOTD_COUNT")%>"></td>
                <td width="25%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("DZYH_TD_COUNT")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
<%
	if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:400px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function initPage(){
        do_SetPageWidth();
    }
    function do_Search() {
        mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
        document.mainFrm.target = "_self";
        mainFrm.submit();
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }
    function do_Export(){
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.target = "_self";
        mainFrm.submit();
    }

    function do_ShowDetail(organizationId, companyName){
        mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.DzyhAssetsReportServlet";
        mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
        var selObj = mainFrm.organizationId;
        selectSpecialOptionByItem(selObj, organizationId);
        mainFrm.companyName.value = companyName;
        var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
        window.open("/public/waiting2.htm", "assWin", style);
        mainFrm.target = "assWin";
        mainFrm.submit();
    }




    function do_SelectLocation(){
        with(mainFrm){
            var lookUpName = "LOOK_UP_ADDRESS";
            var dialogWidth = 55;
            var dialogHeight = 30;
            userPara = "organizationId=<%=orgId%>";
            var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
            if (objs) {
                var obj = objs[0];
                objectCode.value = obj["workorderObjectCode"];
            } else {
                objectCode.value = "";
            }
        }
    }
    function do_SelectUser(userBox){
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 44;
        var dialogHeight = 29;
        var userPara = "organizationId=<%=orgId%>";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var boxName = userBox.name;
        if (objs) {
            var obj = objs[0];
            userBox.value = obj["userName"];
        } else {
            userBox.value = "";
        }
    }
</script>