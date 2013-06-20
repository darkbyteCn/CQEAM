<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%@ page import="com.sino.ams.newasset.dto.ItemFinanceReportDTO" %>

<html>
<body leftmargin="0" topmargin="0">
<%
    DTOSet dtos = (DTOSet)request.getAttribute("financeReport");
    if(dtos != null && !dtos.isEmpty()){
        int dtoCount = dtos.getSize();
%>
    <table border=1 width="100%">
        <tr style="height:21px">
<%
        for(int i = 0; i < dtoCount; i++){
            ItemFinanceReportDTO dto = (ItemFinanceReportDTO)dtos.getDTO(i);
%>
            <td width="9%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif"><%=dto.getFinanceProp()%></td>
<%
        }
%>
        </tr>
        <tr style="height:21px">
<%
        for(int i = 0; i < dtoCount; i++){
            ItemFinanceReportDTO dto = (ItemFinanceReportDTO)dtos.getDTO(i);
%>
            <td width="9%" align="center" ><input type="text" readonly class="finput3" value="<%=dto.getItemCount()%>"></td>
<%
        }
%>
        </tr>
<%
    }
%>
    </body>
</html>