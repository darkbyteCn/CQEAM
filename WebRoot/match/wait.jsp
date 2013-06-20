<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by HERRY.
  Date: 2007-11-28
  Time: 17:10:38
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>数据处理中...</title></head>
<%
    String sysid = StrUtil.nullToString(request.getParameter("systemid"));
    String assid = StrUtil.nullToString(request.getParameter("assetId"));
    String batchid = StrUtil.nullToString(request.getParameter("batchid"));
    String act = request.getParameter("act");
%>
<script language="javascript">
    function openSrc() {
        retMsg.innerHTML = eee.document.getElementById("retMsg").value;
        wait.style.display = "none";
        ok.style.display = "";
        //            window.close();
        //            alert(eee.document.getElementById("retMsg").value);
    }
</script>
<body leftmargin=0 topmargin=0>
<div id="wait">
    <center>
        <table border="1" cellspacing="0" cellpadding="4" style="border-collapse: collapse" bgcolor="#FFFFEC"
               height="90">
            <tr>
                <td bgcolor="#3399FF" style="font-size:12px;color:#ffffff" height=24>数据处理中...</td>
            </tr>
            <tr>
                <td style="font-size:12px;line-height:200%" align=center>正在处理数据，请稍候...

                    <marquee style="border:1px solid #000000" direction="right" width="300" scrollamount="5"
                             scrolldelay="10" bgcolor="#ECF2FF">
                        <table cellspacing="1" cellpadding="0">
                            <tr height=8>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                            </tr>
                        </table>
                    </marquee>
                </td>
            </tr>
        </table>
    </center>
</div>

    <table  id="ok" style='display:none' width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="20"><td></td></tr>
        <tr>
            <td width="10%"></td>
            <td bgcolor="#ff9900">
                <table width="100%" height="60">
                    <tr>
                        <td align="center" bgcolor="#eeeeee"><label id="retMsg"></label></td>
                    </tr>

                </table>
            </td>
            <td width="10%"></td>
        </tr>
        <tr><td width="10%"></td>
            <td align="center"><img src="/images/eam_images/ok.jpg" alt="点击关闭" onclick="window.close()"></td>
            <td width="10%"></td>
        </tr>
    </table>


<iframe name='eee' FRAMEBORDER="0" MARGINWIDTH="0" MARGINHEIGHT="0" scrolling="yes" width="1%" height="1%"
        onload="openSrc();"
        src='/servlet/com.sino.ams.match.servlet.ManualMatchServlet?systemid=<%=sysid%>&assetId=<%=assid%>&act=<%=act%>&batchid=<%=batchid%>'/>
</body>

</html>