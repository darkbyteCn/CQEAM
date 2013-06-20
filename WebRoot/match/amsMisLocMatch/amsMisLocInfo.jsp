<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.match.ETSMISLocationMatch" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  User: Zyun
  Date: 2007-11-21
  Time: 20:06:51
--%>
<!--本页面是用来显示已匹配的地点信息和解除已匹配的地点-->
<head>
    <title>EAM-MIS匹配地点</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript">
        var ArrAction1 = new Array(true, "关闭", "act_query.gif", "关闭", "return1");
        var ArrAction2 = new Array(true, "解除匹配", "act_query.gif", "解除匹配", "dodematch");
        var ArrActions = new Array(ArrAction1, ArrAction2);
        var ArrSinoViews = new Array();
    </script>
    <script language="javascript">
        function return1() {
            window.opener.document.forms[0].action = "/match/amsMisLocMatch/amsMisLocMatch.jsp";
            window.opener.document.forms[0].submit();
            window.close();
            //            window.opener.parent.location.reload();self.close()
            //            window.opener.location.href;
        }
        function dodematch() {
            //        alert("11111");
            if (!getCheckBoxValue("sysid", ";")) {
                alert("请选择地点！");
                return;
            }
            if (confirm("确定要解除匹配？")) {
                document.forms[0].dematch.value = "1"
                document.forms[0].submit();
                window.parent.submit();
            }
        }
        function CheckListEx() {
            reverseCheck('forms[0].sysid');
        }
    </script>
    <%

        SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
        String orgid = userAccount.getOrganizationId();
        String de = request.getParameter("dematch");
        ETSMISLocationMatch elm = new ETSMISLocationMatch();
        elm.listMatchETSLocation(orgid, request);
        RowSet webgrid = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        String[] arr = request.getParameterValues("sysid");
        if (de != null && de.equals("1")) {
            boolean success = elm.dematch(arr);
            if (success) {
    %>
    <script language="javascript">
        alert("解除匹配成功！");
    </script>
    <%
    } else {
    %>
    <script language="javascript">
        alert("解除匹配失败！");
    </script>
    <%
            }
        }
    %>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<script language="javascript">
    printTitleBar("EAM-MIS匹配地点");
    printToolBar();
</script>
<table width="82%" border="1" bordercolor="#333333">
    <form>
        <script language="javascript">
            var ArrSinoViews = new Array();
            var ArrSinoTitle1 = new Array("", true, "2%");
            var ArrSinoTitle2 = new Array("EAM地点", false, "49%");
            var ArrSinoTitle3 = new Array("MIS地点", false, "49%");
            var ArrSinoTitles = new Array(ArrSinoTitle1, ArrSinoTitle2, ArrSinoTitle3);
            printViewTitleHead();
            printViewTitle();
        </script>
        <input type="hidden" name="dematch" value="">
        <%
            Row row = null;

            for (int i = 0; i < webgrid.getSize(); i++) {
                row = webgrid.getRow(i);
                String pk = row.getValue("ETSNO2") + "----" + row.getValue("MISLOCATION2");
        %>
        <tr style="cursor:'hand'" onMouseOver="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#ffffff'">
            <td width="2%"><input type="checkbox" name="sysid" value="<%=pk%>"></td>
            <td width="49%">
                <%=row.getValue("ETSLOCATION2") %>
            </td>
            <td width="49%">
                <%=row.getValue("MISLOCATION2") %>
            </td>
        </tr>
        <%
            }
        %>
        <script language="javascript">
            printViewTitleBottom();
        </script>

    </form>
</table>

<table width="100%">
    <tr>
        <td align="right" width="100%"><%out.print(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML));%></td>
    </tr>
</table>


<script language="javascript">

    //    if (document.forms[1].dematch != null)
    //    {
    //        document.forms[1].dematch.value = "";
    //    }
</script>
</body>
</html>
