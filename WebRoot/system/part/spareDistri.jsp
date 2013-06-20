<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-2
  Time: 15:59:31
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
    <title>设备分配确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemCode = parser.getParameter("itemCode");
%>
<body topmargin="0" leftmargin="0" onLoad="window.focus();">
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet">
    <script type="text/javascript">
        printTitleBar("设备分配确认")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act">
    <input type="hidden" name="itemCode" value="<%=itemCode%>">
    <img src="/images/eam_images/confirm.jpg" alt="确认" onClick="do_distribute();">
    <div style="position:absolute;top:53px;left:0px;width:533px">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox"
                                                                       id="controlBox" onclick="checkAll('titleCheck','Ids')"></td>
                <td width="10%" align="center">地市</td>
                <td width="20%" align="center">设备状态</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;position:absolute;top:75px;left:0px;width:550px;height:70%">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center"><input type="checkbox" name="Ids" value="<%=row.getValue("SYSTEM_ID")%>"></td>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("IS_TMP")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%
    }
%>
</body>
</html>
<script type="text/javascript">
    function do_distribute() {
    <%--mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";--%>
        var checkedCount = getCheckedBoxCount("Ids");
        if (checkedCount < 1) {
            alert("请至少选择一行数据！");
            return;
        } else {
            mainFrm.action = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet?act=<%=WebActionConstant.CREATE_ACTION%>";
            mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            mainFrm.submit();
            window.opener.mainFrm.act.value = "<%=AMSActionConstant.DISTRIBUTE_ACTION%>";
            window.opener.mainFrm.submit();
            window.close();
        }
    }

    function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:35;dialogHeight:28;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainFrm");
            }
        }
    }

</script>