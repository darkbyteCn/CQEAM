<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.vendor.dto.SrvVendorInfoDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>

</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('query();')">
<%
	SrvVendorInfoDTO dtoOpt = (SrvVendorInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsType = dtoOpt.getAssetsType();
	String pageTitle = "同步供应商信息";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD供应商信息";
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>

<form action="<%=SrvURLDefineList.SRV_VENDOR_INFO_SERVLET1 %>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="15%" align="right">供应商编号：</td>
            <td width="15%"><input type="text"  name="vendorNumber" value ="<%=dtoOpt.getVendorNumber()%>" style="width:100%" ></td>
            <td width="10%" align="right">供应商名称：</td>
            <td width="15%"><input type="text"  name="vendorName" value ="<%=dtoOpt.getVendorName()%>" style="width:100%" ></td>
            <td width="10%" align="right">有效标识：</td>
            <td width="20%"><input type="text"  name="vatFlag" style="width:80%" value ="<%=dtoOpt.getVatFlag()%>"></td>
   <%if(!(AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){%>
            <td width="20%"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();"></td>
   <%}%>
        </tr>
        <tr>
             <td width="15%" align="right">供应商类别：</td>
             <td width="15%"><input type="text"  name="vendorTypeDisp" value ="<%=dtoOpt.getVendorTypeDisp()%>" style="width:100%" ></td>
             <%-- <td width="10%" align="right">供应商别名：</td>
             <td width="15%"><input type="text"  name="vendorNameAlt" value ="<%=dtoOpt.getVendorNameAlt()%>" style="width:100%" ></td> --%>
             <td width="10%" align="right">最后更新日期：</td>
             <td width="15%">
                <input  name="lastUpdateDate"   style="width:80%" title="点击选择日期" readonly class="readonlyInput" value ="<%=dtoOpt.getLastUpdateDate()%>" onclick="gfPop.fPopCalendar(lastUpdateDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fPopCalendar(lastUpdateDate)">
            </td>
            <td width="10%" align="right">&nbsp; </td>
            <td width="20%">&nbsp;</td>
             <%-- <td width="10%" align="right">供应商别名：</td>
             <td width="15%"><input type="text"  name="vendorNameAlt" value ="<%=dtoOpt.getVendorNameAlt()%>" style="width:100%" ></td> --%>
            <td width="20%">
                <img src="/images/eam_images/synchronize.jpg" alt="点击同步" onclick="do_SubmitSyn();">
            </td>
        </tr>
    </table>

</form>
<%
	DTOSet ds=(DTOSet)request.getAttribute(SrvWebActionConstant.ASSETCATEGORYSEAR);
	if(!(AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
 %>
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
			<td align=center width="10%">供应商名称</td>
			<td align=center width="8%">供应商别名</td>      
			<td align=center width="8%">供应商编号</td>
			<td align=center width="8%">供应商类别</td>
			<td align=center width="8%">纳税人标识</td>
			<td align=center width="8%">供应商创建时间</td>
		</tr>
	</table>
</div>
<%
	if(ds!=null&&ds.getSize()>0){		
 %>
<div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:92px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%      SrvVendorInfoDTO dto=new SrvVendorInfoDTO();

		for(int i=0;i<ds.getSize();i++){
		 dto=(SrvVendorInfoDTO)ds.getDTO(i);
		
 %>
        <tr class="dataTR" >

			<td align="center" width="10%"><%=dto.getVendorName() %></td>
			<td align="center" width="8%"><%=dto.getVendorNameAlt() %></td>
			<td align="center" width="8%"><%=dto.getVendorNumber() %></td>
			<td align="center" width="8%"><%=dto.getVendorTypeDisp() %></td>
			<td align="center" width="8%"><%=dto.getVatFlag() %></td>
			<td align="center" width="8%"><%=dto.getVendorCreationDate() %></td>
			
<%	
		}
 } else{
 %>
 <div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
 <%
 	}
 	 }
  %>
    </table>
</div>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
//	String.prototype.trim = function()   
//	{   
//	    return this.replace(/(^\s*)|(\s*$)/g, "");   
//	}   

	function do_SearchOrder() {
	   var vendorNumber =document.getElementsByName("vendorNumber")[0];
	   var vendorName = document.getElementsByName("vendorName")[0];
	   var lastUpdateDate = document.getElementsByName("lastUpdateDate")[0];
	   var vatFlag = document.getElementsByName("vatFlag")[0];
	   
	   if(vendorNumber.value=="" && vendorName.value=="" && lastUpdateDate.value==""){
	      alert("请输入'供应商编号/供应商名称'或'最后更新日期' 条件其一进行查询！");
	      document.getElementsByName("vendorNumber")[0].focus();
	    return false;
	   }   
//	    if(vatFlag.value!=""){
//	       if(vatFlag.value!="Y"||vatFlag.value!="N"){
//	       	 alert("有效标识只能为'Y'或'N',请重新输入");
//	         vatFlag.focus();
//	         return false;
//	       }
//	       alert("测试！");
//	    }
	   
		if(mainFrm.transferType){
			mainFrm.transferType.disabled = false;
		}
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}
	
	function do_SubmitSyn() {
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    mainFrm.submit();
	}
	
    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>