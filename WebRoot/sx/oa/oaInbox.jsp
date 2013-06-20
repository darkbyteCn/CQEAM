<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.sso.constant.SSOURLDefineList" %>
<%@ page import="com.sino.sso.constant.SSOWebAttributes" %>
<%--
  Date: 2006-12-6
  Time: 10:34:50
  Function:OA页面首页我的工作
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>收件箱</title>
    <%
        String uid=StrUtil.nullToString(request.getParameter("uid"));
        String nextPage="/servlet/com.sino.sso.servlet.SSOForward?uid="+uid+"&url=";
        RequestParser rp = new RequestParser();
        rp.transData(request);
        boolean hasAssetsUpdate = false;
        boolean hasAssetsCommit = false;
        boolean hasAssetsCommitCOMP = false;
        boolean hasAssetsConfirmCommit = false;
        boolean hasFlow=true;
        try {
            hasAssetsUpdate = ((String) request.getAttribute(SSOWebAttributes.SYNC_CHANGES)).equalsIgnoreCase("TRUE");
            hasAssetsCommit = ((String) request.getAttribute(SSOWebAttributes.SYNC_TRANS_RESULT)).equalsIgnoreCase("TRUE");
            hasAssetsCommitCOMP = ((String) request.getAttribute(SSOWebAttributes.SYNC_TRANS_IN_COMP)).equalsIgnoreCase("TRUE");
            hasAssetsConfirmCommit = ((String) request.getAttribute(SSOWebAttributes.ASSETS_CONFIRM_INFO)).equalsIgnoreCase("TRUE");
        } catch (Exception e) {

        }
    %>
</head>

<body topmargin="0" leftmargin="0">
<form action="/servlet/com.sino.sso.servlet.OAInboxServlet" method="post" name="mainForm">

    <input type="hidden" name="flag" value="">

    <input type="hidden" name="applyId" value="">


    <div style="overflow-y:scroll;width:100%;height:380px">
        <br>
        <%
            RowSet rows = null;
            try {
                rows = (RowSet) request.getAttribute(ReqAttributeList.INBOX_DATA);
            } catch (Exception e) {

            }
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    String fromDate = row.getStrValue("SFACT_FROM_DATE");
                    String procName = row.getStrValue("CATEGORY_NAME");
                    String applyNumber = row.getStrValue("SFACT_APPL_COLUMN_1");
                    String fromUser = row.getStrValue("SFACT_FROM_TASK_USER");
                    String composeUser = row.getStrValue("SFACT_COMPOSE_USER");
                    fromUser=StrUtil.isEmpty(fromUser)?composeUser:fromUser;

                    String title = fromDate + " " + procName + "[" + applyNumber + "]   " + fromUser;

                    String caseId = row.getStrValue("SFACT_CASE_ID");
                    if (row.getStrValue("SFACT_DOC_TYPE").equals("98")) {
                        int index = caseId.indexOf(":");
                        if (index >= 0)
                            caseId = caseId.substring(index + 1);
                    }
                    String bitMask = row.getStrValue("ALLOW_OPERATION");
                    String url = "/servlet/com.sino.sinoflow.servlet.ProcessCopy?sf_copyID='" + caseId + "'&sf_appMask=" + bitMask + "&signAct=0";

        %>
        <a href="#" title="<%=title%>" onclick="doStep('<%=nextPage+URLEncoder.encode(url)%>')"><%=title%>
        </a>

        <%
                    if (i < rows.getSize() - 1) {
                        out.println("<br>");
                    }
                }
            } else {
                hasFlow = false;
            }
        %>
        <%
            if (hasAssetsUpdate) {
                String title = "资产变动直接同步";
        %>
        <a href="#" title="<%=title%>" onclick="doStep('<%=nextPage+URLEncoder.encode(SSOURLDefineList.SYNC_CHANGES_URL)%>')"><%=title%>
        </a>
        <%
            }
            if (hasAssetsCommit) {
                String title = "资产调拨结果同步";
        %>
        <a href="#" title="<%=title%>" onclick="doStep('<%=nextPage+URLEncoder.encode(SSOURLDefineList.SYNC_TRANS_RESULT_URL)%>')"><%=title%>
        </a>
        <%
            }
            if (hasAssetsCommitCOMP) {
                String title = "公司间调拨同步";
        %>
        <a href="#" title="<%=title%>" onclick="doStep('<%=nextPage+URLEncoder.encode(SSOURLDefineList.SYNC_TRANS_IN_COMP_URL)%>')"><%=title%>
        </a>
        <%
            }
            if (hasAssetsConfirmCommit) {
                String title = "资产确认工单确认";
        %>
        <a href="#" title="<%=title%>" onclick="doStep('<%=nextPage+URLEncoder.encode(SSOURLDefineList.ASSETS_CONFIRM_INFO_URL)%>')"><%=title%>
        </a>
        <%
            }
        %>
    </div>
</form>
</body>
</html>
