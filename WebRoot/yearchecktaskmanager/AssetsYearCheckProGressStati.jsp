<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckProGressStatiDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
    <title>����̵����ͳ�Ʊ�</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    AssetsYearCheckProGressStatiDTO dto = (AssetsYearCheckProGressStatiDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    if(dto==null){
    	dto = new AssetsYearCheckProGressStatiDTO();
    }
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String roleType= "";
	if(userAccount.isProvAssetsManager() || userAccount.isSysAdmin()){
		roleType = "1";//ȫʡ�ʲ�����Ա
	}else if(userAccount.isComAssetsManager()){
		roleType = "2"; //���й�˾�ʲ�����Ա
	}else{
		roleType="3";//�����ʲ�����Ա
	}
	System.out.println("roleType="+roleType);
	Row row = null;
	String pageTitle = "����̵����ͳ�Ʊ�";
%>
<body onkeydown="autoExeFunction('do_search()')" leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckProGressStatiServlet">
<jsp:include page="/message/MessageProcess"/>


<script type="text/javascript">
    printTitleBar("����̵����ͳ�Ʊ�");
    var ArrAction0 = new Array(true, "��ѯ", "action_view.gif", "��ѯ", "do_search");
    var ArrAction1 = new Array(true, "����", "toexcel.gif", "����", "do_Export");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    printToolBar();

</script>

<input type="hidden" name="act">
<table width="100%" topmargin="0" border="1" bgcolor="#EFEFEF"  style="width:100%;margin:7px">
	<tr>
		<td align="left" width="10%">��˾���ƣ�</td>
		<td align="left" width="10%">
		<select name="organizationId" id="organizationId" style="width:200px">
			<%=request.getAttribute("OU_OPTIONS") %>
		</select> 
		</td>
		</td>
		<td width="70%"></td>
	</tr>
</table>
<%
	if(userAccount.isProvAssetsManager()){//ȫʡ�ʲ�����Ա�����Կ���ȫʡ�Ľ���  �Լ�ÿ�����е��ܽ������
%>
<div id ="headDiv" style="position:absolute;width:100%;overflow:hidden;top:90px;padding:0px; margin:0px;">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="10%" align="center">��˾����</td>
            <td width="10%" align="center">�ʲ�����</td>
            <td width="10%" align="center">�����ʲ�����</td>
            <td width="10%" align="center">��ɰٷֱ�(���ʲ�)</td>
            <td width="10%" align="center">�ص�����</td>
            <td width="10%" align="center">���̵�ĵص�����</td>
            <td width="10%" align="center">��ɰٷֱ�(���ص�)</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:100%;position:absolute;top:83px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
       if (rows != null && rows.getSize() > 0) {
           for (int i = 0; i < rows.getSize(); i++) {
              row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("COMPANY")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_WORKORDER_OBJECT_CODE")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_WORKORDER_OBJECT_CODE")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_OBJECT")%>"></td>
</tr>
 <%
           }
        }
	}else if(userAccount.isComAssetsManager()){ //���й�˾�ʲ�����Ա,���Կ���ȫʡ�ܵģ������е��ܽ��ȣ��������ŵĽ���
%>
<div id ="headDiv" style="position:absolute;width:100%;overflow:hidden;top:90px;padding:0px; margin:0px;">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="20%" align="center">��������</td>
            <td width="10%" align="center">�ʲ�����</td>
            <td width="10%" align="center">�����ʲ�����</td>
            <td width="10%" align="center">��ɰٷֱ�(���ʲ�)</td>
            <td width="10%" align="center">�ص�����</td>
            <td width="10%" align="center">���̵�ĵص�����</td>
            <td width="10%" align="center">��ɰٷֱ�(���ص�)</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:100%;position:absolute;top:83px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
       if (rows != null && rows.getSize() > 0) {
           for (int i = 0; i < rows.getSize(); i++) {
              row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="20%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("RESP_DEPT_NAME")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_WORKORDER_OBJECT_CODE")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_WORKORDER_OBJECT_CODE")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_OBJECT")%>"></td>
		</tr>
 <%
           }
        }
	}else{ //�����ʲ�����Ա���ܿ���ȫʡ�ܵģ��������ܵģ������ŵ�
%>
<div id ="headDiv" style="position:absolute;width:100%;overflow:hidden;top:90px;padding:0px; margin:0px;">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="6%" align="center">��������</td>
            <td width="6%" align="center">�ʲ�����</td>
            <td width="10%" align="center">�����ʲ�����</td>
            <td width="8%" align="center">��ɰٷֱ�(���ʲ�)</td>
            <td width="20%" align="center">��ɰٷֱ�(���ʲ�)</td>
            <td width="10%" align="center">�ص�����</td>
            <td width="10%" align="center">���̵�ĵص�����</td>
            <td width="10%" align="center">��ɰٷֱ�(���ص�)</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:100%;position:absolute;top:83px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
       if (rows != null && rows.getSize() > 0) {
           for (int i = 0; i < rows.getSize(); i++) {
              row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <td width="20%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("RESP_DEPT_NAME")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_ASSETS")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_WORKORDER_OBJECT_CODE")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("SUM_CHECK_WORKORDER_OBJECT_CODE")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly="true" value="<%=row.getValue("PERCENT_BY_OBJECT")%>"></td>
		</tr>
<%
			}
       }
	}
%>
  </table>
    </div>
</form>
<div id="pageNaviDiv" style="position:absolute;top:460px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>

<script type="text/javascript">
	function do_search() {
		var roleType = "<%=roleType%>";
		var organizationId = document.getElementById("organizationId").value;
		if(roleType =="2" || roleType== "3" ){
			if(organizationId=="" || organizationId== -1){
				alert("��ѡ��˾���ѯ��");
				return;
			}
		}
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.act.value = "QUERY_ACTION";
		document.mainFrm.submit();
	}

    function do_Export() {
    	var roleType = "<%=roleType%>";
		var organizationId = document.getElementById("organizationId").value;
		if(roleType =="2" || roleType== "3" ){
			if(organizationId=="" || organizationId== -1){
				alert("��ѡ��˾���ѯ��");
				return;
			}
		}
        document.mainFrm.act.value = "EXPORT_ACTION";
        document.mainFrm.submit();
    }
    
</script>