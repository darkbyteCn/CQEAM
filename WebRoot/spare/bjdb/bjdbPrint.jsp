<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件调拨单</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    String cityOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjdbServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">调拨单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td align="right">调拨单创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate().toString().substring(0, 10)%>
                    </td>
                    <td align="right">经办人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <%--  <DIV align=center id='hiddenDiv' style="top:20px">
    <script type="text/javascript">
        printTitleBar("备件调拨单")
        var ArrAction1 = new Array(true, "打印页面设置", "act_trace.gif", "打印页面设置", "printSetup();");
        var ArrAction2 = new Array(true, "打印预览", "act_search.gif", "打印预览", "printView();");
        var ArrAction3 = new Array(true, "打印", "print.gif", "打印", "printIt();");
        var ArrAction4 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.close();");
        var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction3, ArrAction4);
        var ArrSinoViews = new Array();
        printToolBar();
    </script>
    </DIV>--%>
    <DIV align=center id='hiddenDiv' style="top:20px">
        <legend>
            <img src="/images/buttonbar/act_trace.gif" alt="打印页面设置" id="img3" onClick="printSetup();">
            <img src="/images/buttonbar/act_search.gif" alt="打印预览" id="img4" onClick="printView();">
            <img src="/images/buttonbar/print.gif" alt="打印" id="img1" onclick="printIt()">
            <img src="/images/button/toExcel.gif" alt="导出数据" id="img5" onclick="do_excle()">
            <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
        </legend>
    </DIV>
    <script type="text/javascript">
        var columnArr = new Array("设备名称", "部件号", "规格型号", "设备厂商", "原存放地", "现存放地", "操作时间");
        var widthArr = new Array("15%", "10%", "10%", "20%", "8%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="15%" align="center"><input type="text" name="barcode"
                                                      id="barcode<%=i%>" readonly
                                                      value="<%=row.getValue("ITEM_NAME")%>"
                                                      class="blueborderGray"
                                                      style="width:100%">
                </td>
                <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("BARCODE")%>
                </td>
                <td width="10%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="20%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td width="8%" name="spareUsage"
                    id="spareUsage<%=i%>"><%=row.getValue("FROM_ORGANIZATION_NAME").toString().substring(3)%>
                </td>
                <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                                     value="<%=row.getValue("TO_ORGANIZATION_NAME").toString().substring(3)%>"
                                                     class="noborderGray"
                                                     readonly
                                                     style="width:100%;text-align:right">
                </td>
                <td width="8%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" readonly
                                                     value="<%=row.getValue("CREATION_DATE").toString().substring(0,10)%>"
                                                     class="noborderInput"
                                                     style="width:100%;text-align:right">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
</form>
</body>
<script type="text/javascript">
    function printIt() {
        if (confirm('确定打印吗？')) {
            document.getElementById('hiddenDiv').style.visibility = 'hidden';
            document.getElementById('wb').execwb(6, 6);
            document.getElementById('hiddenDiv').style.visibility = 'visible';
        }
    }
    //打印设置函数
    function printSetup() {
        document.getElementById('wb').execwb(8, 1);
    }

    //打印预览函数
    function printView() {
        document.getElementById('hiddenDiv').style.visibility = 'hidden';
        document.getElementById('wb').execwb(7, 1);
        document.getElementById('hiddenDiv').style.visibility = 'visible';

    }
    function do_excle() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
</script>
</html>
<OBJECT id=wb height=0 width=0
        classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>