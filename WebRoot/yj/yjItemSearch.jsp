<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>应急保障装备名称维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>

</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemCode = parser.getParameter("itemCode");
    String itemName = parser.getParameter("itemName");
%>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%--<%=WebConstant.EXPORT_TIP_MSG%>--%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet">
    <script type="text/javascript">
        printTitleBar("应急保障装备名称维护")
    </script>
    <table width="100%" topmargin="0" border="0" class="queryHeadBg">
        <input type="hidden" name="act">
        <!--<input type="hidden" name="isExp" value="0">-->
        <tr>
            <td width="10%" align="right">ID号：</td>
            <td width="10%"><input class="input_style1" style="width:100%" type="text" name="itemCode" value="<%=itemCode%>"></td>
            <td width="10%" align="right">装备名称：</td>
            <td width="30%"><input class="input_style1" style="width:100%" type="text" name="itemName" value="<%=itemName%>"></td>
            <td width="20%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img
                    src="/images/eam_images/new.jpg" alt="新增" onClick="do_add();"><img src="/images/eam_images/export.jpg" style="cursor:'hand'"
                                                                                   onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="4%" align="center">ID号</td>
                <td width="20%" align="center">装备名称</td>
                <td width="6%" align="center">字典类型</td>
                <td width="6%" align="center">创建人</td>
                <td width="10%" align="center">创建日期</td>
                <td width="6%" align="center">更新人</td>
                <td width="10%" align="center">更新日期</td>
                <td width="10%" align="center">失效日期</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;left:0px;width:100%;height:350px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')">
                <td width="4%" align="center"><%=row.getValue("ITEM_CODE")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("CREATE_USER")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("CREATION_DATE")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("LAST_UPDATE_USER")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("LAST_UPDATE_DATE")%>
                <td width="10%" align="left"><%=row.getValue("DISABLE_DATE")%>
                </td>
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
</html>

<script type="text/javascript">

    function do_search() {
       var itemCode =document.getElementsByName("itemCode")[0];
          if(itemCode.value!=""){
              if(isNaN(itemCode.value))
             {
              alert('"ID号" 请输入数字！');
              itemCode.value="";
              itemCode.focus();
              return false;   
             }
          }
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(itemCode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?itemCode=" + itemCode;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
//        var isExp = document.mainFrm.isExp.value;
//        if (isExp == 0) {
//            document.getElementById("$$$exportTipMsg$$$").style.visibility = "visible";
//            document.mainFrm.isExp.value = "1";
            document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
            document.mainFrm.submit();
//        } else {
//            alert("正在导出，请稍后！！");
//        }

    }

</script>