<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.td.srv.dealProjectDiversity.dto.DealTdProjectDiversityCheckDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: admini
  Date: 2012-2-13
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
</head>
<%
	DealTdProjectDiversityCheckDTO dto= (DealTdProjectDiversityCheckDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String pageTitle = "��Ŀת�ʲ���˶�(TD)";
	String isDisplay = "";
    String action = dto.getAct();
%>
 <%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="0" topmargin="0" onLoad="do_SetPageWidth();do_onLoad();" onkeydown="autoExeFunction('query();')">

<form action="/servlet/com.sino.soa.td.srv.dealProjectDiversity.servlet.DealTdProjectDiversityCheckServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="16%" align="right">�ʲ��˲���</td>
            <td width="25%"><select name="bookTypeCode" style="width:100%" size="1" class="noEmptyInput" onchange="do_onLoad()"><%=dto.getBookTypeCodeOption()%></select></td>
            <td width="16%" align="right">��Ŀ��ţ�</td>
            <td width="20%"><input type="text"  name="projectNumber2" id="projectNumber2" value="<%=dto.getProjectNumber2()%>" style="width:68%" class="noEmptyInput">�磺B1139319</td>
            <td width="16%" align="right"><img align="bottom" src="/images/eam_images/diversity_check.jpg" alt="�������˶�" onClick="do_SubmitSyn();"></td>
            <%
                if (action.equals("INFORSYN") && dto.getError().equals("Y")) {
            %>
            <td width="10%" align="right"><img align="bottom" src="/images/eam_images/search.jpg" alt="����鿴������Ϣ" onClick="do_show();"></td>
            <%
                }
            %>
       </tr>
	
		   <tr height="40">
		       
			   <td id="diffType" align="right">�������ͣ�</td>
			   <td><input type="text" name="diffType" value="<%=dto.getDiffType() %>"><a href="" onclick="do_SelectDiversity(); return false;" title="���ѡ���������">[��]</a>
			   	   <input type="hidden" name="diffTypeCode" value="<%=dto.getDiffTypeCode() %>">
			   </td>
			   <td id="projectNo" align="right">��Ŀ��ţ�</td>
			   <td id="projectNoText" ><input type="text" name="projectNumber" disabled="disabled" value="<%=dto.getProjectNumber() %>"><a href="" onclick="do_SelectProjectNo(); return false;" title="���ѡ����Ŀ���">[��]</a>
			       <input type="hidden" name="misProjectId" value="<%=dto.getMisProjectId() %>">
			   </td>
			    <td colspan="6" align="right">
		        	<img src="/images/eam_images/search.jpg" alt="�����ѯ" onclick="do_Search();">&nbsp;
		        	<img src="/images/eam_images/export.jpg" alt="�������" onclick="do_Export();">&nbsp;
		        </td>
		   </tr>
	</table>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:88px;left:1px;width:800px">
		<table width="100%" border="1" bordercolor="#666666" class="headerTable">
		   <tr height="22">
		       <td width="12%" align="center">�ʲ��˲�</td>
			   <td width="10%" align="center">��Ŀ���</td>
			   <td width="18%" align="center">��Ŀ����</td>
			   <td width="15%" align="center">�ʲ���ǩ��</td>
			   <td width="15%" align="center">��������</td>
			   <td width="15%" align="center">PAֵ</td>
			   <td width="15%" align="center">EAMֵ</td>
		   </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:70%;width:800px;position:absolute;top:114px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
	    	<%
			if(hasData){
				Row row = null;
				for(int i = 0; i < rows.getSize(); i++){
					row = rows.getRow(i);
			%>	
	    		<tr height="22">
					<td width="12%" align="center"><input class="finput2" readonly value="<%=row.getValue("BOOK_TYPE_CODE")%>"></td>
					<td width="10%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT1")%>"></td>
					<td width="18%" align="center"><input class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
					<td width="15%" align="center"><input class="finput" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
					<td width="15%" align="center"><input class="finput" readonly value="<%=row.getValue("DIFF_TYPE")%>"></td>
					<td width="15%" align="center"><input class="finput2" readonly value="<%=row.getValue("PA_VALUE")%>"></td>
					<td width="15%" align="center"><input class="finput2" readonly value="<%=row.getValue("EAM_VALUE")%>"></td>
				</tr>
			<%
					}
				}
			%>	
		</table>
	</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:475px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

<script type="text/javascript">
	function do_Search(){
		var id = document.getElementById("misProjectId").value;
		var code = document.getElementById("diffTypeCode").value;
		
		if(id != "" && code != ""){
			mainFrm.action = "/servlet/com.sino.soa.mis.srv.dealProjectDiversity.servlet.DealTdProjectDiversityCheckServlet?misProjectId="+id+"&diffTypeCode="+code;
			mainFrm.act.value = "QUERY_ACTION";
			mainFrm.submit();
		}else{
			alert("����ѡ����Ŀ��źͲ�������");
		}
	}

	function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}

	function do_SubmitSyn() {
        var bookTypeCode = document.mainFrm.bookTypeCode.value;
        var projectNum2 = document.mainFrm.projectNumber2.value;
        if (bookTypeCode == "") {
            alert("��ѡ���ʲ��˲���");
            return false;
        }
       if (projectNum2 == "") {
            alert("��Ŀ��Ų���Ϊ�գ�");
          return false;
       }
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    mainFrm.submit();
	}

    function do_show(id) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        var url = "/servlet/com.sino.soa.mis.srv.pagequiryassetcustdetail.servlet.SBFIFATdPageinquiryAssetCustDetailServlet?act=DETAIL_ACTION";
        var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, 'orderWin', style);
    }
    
    function do_SelectProjectNo(){
    	var bookTypeCode = document.mainFrm.bookTypeCode.value;
    	if (bookTypeCode == "") {
            alert("��ѡ���ʲ��˲���");
            return false;
        }
    	var lookUpName = "PROJECT_NO_AUTHORIZED";
	    var dialogWidth = 55;
	    var dialogHeight = 30;
	    var userParameters = "multipleChose=true&bookTypeCode="+bookTypeCode;
	    var returnValues = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userParameters);
	    var project = new Object();
	    project["projectNumber"] = "";
	    project["misProjectId"] = "";
	    if (returnValues) {
	        var valueCount = returnValues.length;
	        for (var i = 0; i < valueCount; i++) {
	            var returnValue = returnValues[i];
	            project["projectNumber"] += "" + returnValue["projectNumber"] + "";
	            project["misProjectId"] += "'" + returnValue["misProjectId"] + "'";
	            if(i < valueCount - 1){
	                project["projectNumber"] += ", ";
	                project["misProjectId"] += ", ";
	            }
	        }
	    }
    	dto2Frm(project, "mainFrm");
    }
    
    function do_SelectDiversity(){
    	var lookUpName = "LOOK_UP_DIVERSITY";
	    var dialogWidth = 48;
	    var dialogHeight = 30;
	    var userParameters = "multipleChose=true";
	    var returnValues = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userParameters);
	    var content = new Object();
	    content["diffTypeCode"] = "";
	    content["diffType"] = "";
	    if (returnValues) {
	        var valueCount = returnValues.length;
	        for (var i = 0; i < valueCount; i++) {
	            var returnValue = returnValues[i];
	            content["diffTypeCode"] += "'" + returnValue["diffTypeCode"] + "'";
	            content["diffType"] += "" + returnValue["diffType"] + "";
	            if(i < valueCount - 1){
	                content["diffTypeCode"] += ", ";
	                content["diffType"] += ", ";
	            }
	        }
	    }
	    dto2Frm(content, "mainFrm");
	}
    
    function do_onLoad(){
    	var currSelectIndex = document.all.bookTypeCode.options.selectedIndex;
    	var currSelectValue = document.all.bookTypeCode.options[currSelectIndex];
    	var printvalue = currSelectValue.value;
    	//if(printvalue == ""){
			//document.getElementById("projectNo").style.display = "none";
			//document.getElementById("projectNoText").style.display = "none";
		//}else{
		//	document.getElementById("projectNo").style.display = "block";
		//	document.getElementById("projectNoText").style.display = "block";
		//}
    }
	function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
</script>
</html>