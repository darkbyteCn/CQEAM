<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-10-15
  Time: 23:46:13
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
</head>
<%
    SBFIFAPageinquiryAssetCustDetailDTO dtoOpt= (SBFIFAPageinquiryAssetCustDetailDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	String pageTitle = "MISϵͳת���ʲ��嵥(��ҳ)";
    String action = dtoOpt.getAct();
%>
 <%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">
<form action="/servlet/com.sino.soa.mis.srv.pagequiryassetcustdetail.servlet.SBFIFAPageinquiryAssetCustDetailServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="15%" align="right">�ʲ��˲���</td>
            <td width="25%"><select name="bookTypeCode" style="width:100%" size="1" class="noEmptyInput"><%=dtoOpt.getOrgOption()%></select></td>
            <td width="15%" align="right">��Ŀ��ţ�</td>
            <td width="25%"><input type="text"  name="projectNumber" value="<%=dtoOpt.getProjectNumber()%>" style="width:80%" class="noEmptyInput"><a href = # title = "���ѡ����Ŀ��Ϣ" class = "linka" onclick = "do_SelectProj();">[��]</a></td>
            <td width="10%" align="right"><img align="bottom" src="/images/eam_images/synchronize.jpg" alt="���ͬ��" onclick="do_SubmitSyn();"></td>
            <%
                if (action.equals("INFORSYN") && dtoOpt.getError().equals("Y")) {
            %>
            <td width="10%" align="right"><img align="bottom" src="/images/eam_images/search.jpg" alt="����鿴������Ϣ" onclick="do_show();"></td>
            <%
                }
            %>
       </tr>
    </table>

</form>

</body>

<script type="text/javascript">

	function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}
     function do_SelectProj() {
	   var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT_AUTHORIZED %>";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (objs) {
            var obj = objs[0];
            dto2Frm(obj, "mainFrm");
           	document.mainFrm.projectNumber.value = obj["projectNumber"];            
        }else {
            document.mainFrm.projectNumber.value = "";
        }
    }
	function do_SubmitSyn() {
        var bookTypeCode = document.mainFrm.bookTypeCode.value;
        var projectNumber = document.mainFrm.projectNumber.value;
        if (bookTypeCode == "") {
            alert("��ѡ���ʲ��˲���");
            return false;
        }
        if (projectNumber == "") {
            alert("��Ŀ��Ų���Ϊ�գ�");
            return false;
        }
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    mainFrm.submit();
	}

    function do_show(id) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        var url = "/servlet/com.sino.soa.mis.srv.pagequiryassetcustdetail.servlet.SBFIFAPageinquiryAssetCustDetailServlet?act=DETAIL_ACTION";
        var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, 'orderWin', style);
    }

</script>
</html>