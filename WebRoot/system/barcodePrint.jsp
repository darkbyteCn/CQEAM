<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@page import="com.sino.ams.constant.WebAttrConstant"%>

<%
String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
%>
<%--
  Created by HERRY.
  Date: 2008-3-30
  Time: 16:54:00
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>生成新标签号</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
     <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
</head>
<body>
<script type="text/javascript">
    printTitleBar( "<%= allResName %>" );
</script>
<%=WebConstant.WAIT_TIP_MSG%>
<%
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String showMsg = StrUtil.nullToString(request.getAttribute("showMsg"));
%>
<form action="/servlet/com.sino.ams.util.BarcodePrintServlet" name="mainForm" method="post">
    <input type="hidden" name="act">
    <input type="hidden" name="companyCode" value="<%=user.getCompanyCode()%>">
    <table width="60%" align="center" bordercolor="#9FD6FF" border="1">
        <tr height="22">
            <td width="40%" align="right">公司名称：</td>
            <td><%=user.getCompany()%>
            </td>
        </tr>
        <tr height="22">
            <td align="right">公司账簿代码：</td>
            <td><%=user.getBookTypeCode()%></td>
        </tr>
        <tr height="22">
            <td align="right">实物类型：</td>
            <td>
                <select name="assetType" style="width:40%;border-style:none">
                    <option value="">自有资产</option>
<%
				if ("Y".equals(user.getIsTd()) && !"Y".equals(user.getIsTt())) { 
%>
                    <option value="TD">TD固定资产-空白</option>
                    <option value="LS">TD固定资产-临时</option>
<%
				} else if ("Y".equals(user.getIsTt())) {
%>
                    <option value="TT">铁通城域网资产-空白</option>
                    <option value="LS">铁通城域网资产-临时</option>
<%					
				} else {
%>
                    <option value="MIS">固定资产-空白</option>
                    <option value="LS">固定资产-临时</option>
                    <option value="ZL">租赁资产</option>
                    <option value="DH">重要低值易耗品</option>
                    <option value="TF">通服资产</option>
                    <option value="BJ">备品备件</option>
<%
				}
%>
                </select>
            </td>
        </tr>
        <tr height="22">
            <td align="right">生成数量：</td>
            <td><input type="text"  style="width:40%" name="quantity" value="" class="blueborderYellow" onblur="do_Check(this);"><font color="red">*</font></td>
        </tr>
        <tr height="22">
            <td colspan="2" align="center"><a href="#" class="linka" onclick="doBuild();">生成</a></td>
        </tr>
    </table>
    <%=showMsg%>
</form>
</body>
<script type="text/javascript">
    function doBuild() {
        var quantity = document.mainForm.quantity.value ;
        if (quantity == "") {
            alert("请填写需要打印的标签数量！");
            return;
        }
        if(quantity>2000){
            alert("为方便打印请确认数量在2000之内！！");
            return;
        }
        document.mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainForm.submit();
    }

    function do_Check(object) {
    if (object.value != "") {
        if (isNaN(object.value)) {
            alert("请输入合法数字！");
            object.value = "";
			object.focus();
        } else {
            if(object.value<=0){
                alert("请输入正数！");
                object.value = "";
                object.focus();
                return;
            }     			
		}
	}
}
</script>
</html>