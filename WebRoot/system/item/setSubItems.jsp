<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by HERRY.
  Date: 2007-11-23
  Time: 14:18:06
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>添加子设备</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
</head>
<%
    String flag = StrUtil.nullToString(request.getParameter("flag"));
    String parent_barcode = StrUtil.nullToString(request.getParameter("parent_barcode"));
    String barcode = StrUtil.nullToString(request.getParameter("barcode"));
    String itemName = StrUtil.nullToString(request.getParameter("itemName"));
    String itemSpec = StrUtil.nullToString(request.getParameter("itemSpec"));
%>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_query()');">
<form action="/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet" name="mainForm" method="post">
    <input type="hidden" name="flag" value="<%=flag%>">
    <input type="hidden" name="parent_barcode" value="<%=parent_barcode%>">
    <input type="hidden" name="act">
<script type="text/javascript">
    printTitleBar("添加子设备")
</script>
  <table width="100%" class="queryHeadBg">
      <tr>
          <td align="right">标签号：</td>
          <td><input type="text" name="barcode"  value="<%=barcode%>"></td>
          <td align="right">设备名称：</td>
          <td><input type="text" name="itemName" value="<%=itemName%>"></td>
          <td align="right">规格型号：</td>
          <td><input type="text" name="itemSpec" value="<%=itemSpec%>"></td>
          <td><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_query();">
            <img src="/images/eam_images/save.jpg" alt="点击保存" onclick="do_save();"></td>
      </tr>
  </table>
  <script type="text/javascript">
        var columnArr = new Array("checkbox","标签号","设备名称","规格型号");
        var widthArr = new Array("3%","12%","15%","18%");
        printTableHead(columnArr,widthArr);
  </script>
<div style="overflow-y:scroll;height:430px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);

                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>">

                <td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>

                <td width="12%"><%=row.getValue("BARCODE")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="18%"><%=row.getValue("ITEM_SPEC")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
    </form>
<div style="right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
    function do_query(){
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainForm.submit();
    }
    function do_save(){
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        document.mainForm.submit();
    }
</script>
</html>