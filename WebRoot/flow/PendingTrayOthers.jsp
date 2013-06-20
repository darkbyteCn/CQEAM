<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="java.util.List" %>

<%
//    List rowsetList;

//    if(request.getParameter("flag").equals("0")) {
    if(topFlag == 0) {
        rowsetList = (List)request.getAttribute(WebAttrConstant.SF_PENDINGTRAY_TOP_LIST);
    } else {
        rowsetList = (List)request.getAttribute(WebAttrConstant.SF_PENDINGTRAY_BOTTOM_LIST);
    }
    if(rowsetList != null) {
        RowSet rs;
        for(int outerLoop = 0; outerLoop < rowsetList.size(); outerLoop++) {
            rs = (RowSet)rowsetList.get(outerLoop);
            taskCount = 1;
            taskNumber = 1;
            String tridName = null, subIdName = null, btidName = null, btidSubName = null, procedureName = null, flowDescription = null;
            Row row;
            for(int innerLoop = 0 ; innerLoop < rs.getSize(); innerLoop++) {
                row = rs.getRow(innerLoop);
                String procName = (String)row.getValue("SFACT_PROC_NAME");
                String flowDesc;
                flowDesc = row.getStrValue("SFACT_APPL_MSG");
                String signDate = (String)row.getValue("SFACT_SIGN_DATE");
                String signDueDate = (String)row.getValue("SFACT_SIGN_DUE_DATE");
                String applColumn1 = row.getStrValue("SFACT_APPL_COLUMN_1");
                String applColumn2 = row.getStrValue("SFACT_APPL_COLUMN_2");
                String handlerDept = row.getStrValue("SFACT_HANDLER_GROUP").replace(".", "\\");
                String creator = row.getStrValue("SFACT_COMPOSE_USER");
                String URL = row.getStrValue("SFACT_URL");
                boolean newProc = false;
                if (!procName.equals(procedureName)) {
                    procCount++;
                    taskCount = 1;
                    tridName="tr_"+ procCount;
                    btidName="bt_"+ procCount;
                    procedureName = procName;
                    newProc = true;
%>
<%
                }
                String subName;
                if(!flowDesc.equals(flowDescription) || newProc) {
                    newProc = false;
                    taskNumber = 1;
                    subIdName = tridName + "_" + taskCount;
                    btidSubName = btidName + "_" + taskCount;
                    taskCount++;
%>
<%
                    flowDescription = flowDesc;
                } else {
                    taskNumber++;
                }
                subName = subIdName + "_" + taskNumber;
%>
<tr id='<%=subName%>'height=25 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
      <table border=0 width=100% style='cursor:pointer' align="left">
        <tr>
          <td nowrap align="top" onclick="OpenAppURL('<%=URL%>')"><img border="0" alt="priority" src="/images/normal.gif"><%=applColumn1%></td>
        </tr>
      </table>
      </font></td>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
      <table border=0 width=100% style='cursor:pointer' align="right">
        <tr align="right">
          <!--<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>-->
          <td width="260px" align="left"nowrap  onclick="OpenAppURL('<%=URL%>')" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=applColumn2%></td>
        </tr>
      </table>
      </font></td>
<td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
<table border=0 width=100% style='cursor:pointer' align="left">
  <tr>
    <td align="left" nowrap  onclick="OpenAppURL('<%=URL%>')" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=procName%></td>
  </tr>
</table>
</font></td>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
    <table border=0 width=100% style='cursor:pointer' align="left">
      <tr>
        <td align="left" nowrap  onclick="OpenAppURL('<%=URL%>')" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=flowDesc%></td>
      </tr>
    </table>
    </font></td>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
      <table border=0 width=100% style='cursor:pointer' align="center">
        <tr align="right">
          <!--<td></td>-->
          <td width="120px" align="center" nowrap onclick="OpenAppURL('<%=URL%>' )" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=handlerDept%></td>
        </tr>
      </table>
      </font></td>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
      <table border=0 width=100% style='cursor:pointer' align="center">
        <tr align="right">
          <!--<td></td>-->
          <td width="40px" align="center" nowrap onclick="OpenAppURL('<%=URL%>' )" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=creator%></td>
        </tr>
      </table>
      </font></td>
    <td bordercolordark=#c0c0c0 bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
      <table border=0 width=100% style='cursor:pointer'>
        <tr>
          <!--<td>&nbsp;&nbsp;&nbsp;</td>-->
          <td  align="center" nowrap  onclick="OpenAppURL('<%=URL%>')"><%=signDate%></td>
        </tr>
      </table>
      </font></td>
    <!--<td bordercolordark=white bordercolorlight=white nowrap align="right"><font color="red">
      <table border=0 width=100% style='cursor:pointer'>
        <tr>
          <td>&nbsp;&nbsp;&nbsp;</td>
          <td align="center" nowrap  onclick="OpenAppURL('<%=URL%>')"><%=signDueDate%></td>
        </tr>
      </table>
      </font></td>-->
<%
            }
        }
    }
%>
<script language=javascript>
    function OpenAppURL(URL) {
/*
        var DocLinkWinStyle="status=yes, toolbar=no, menubar=yes, location=no, resizable=yes, scrollbars=yes, width=640, height=480"
        window.open(URL, "", DocLinkWinStyle);
*/
        h = window.screen.height;
        w = window.screen.width;
        if(URL.indexOf('SfCopyServlet')>=0){
        	//for copy servlet
        	f1 = "fullscreen=0,scrollbars=yes,scroll=yes,resizable=yes,width=800,height=500,top=60,left=50";
        }else{
        	//for normal servlet
        	f1 = "fullscreen=1,scrollbars=yes,scroll=yes,resizable=yes";
        }
        
        //top=0,left=0,width=" + w + ",height=" + h + ",
        window.open(URL,"", f1);
    }
</script>
