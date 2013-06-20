<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.system.fixing.dto.EtsItemInfoDTO" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  Created by HERRY.
  Date: 2007-11-22
  Time: 16:43:16
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>子设备</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
      <script language="javascript" src="/WebLibary/js/Constant.js"></script>
      <script language="javascript" src="/WebLibary/js/calendar.js"></script>
      <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
      <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
      <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
      <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
  </head>
  <%
      String barcode = StrUtil.nullToString(request.getParameter("barcode"));
      EtsItemInfoDTO itemInfo = (EtsItemInfoDTO) request.getAttribute("SUB_ITEMS");
      if(itemInfo == null){
          itemInfo = new EtsItemInfoDTO();
      }
  %>
  <body>
  <form action="/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet" name="mainForm" method="post">
      <input type="hidden" name="act" value="">
  <fieldset>
    <legend>
        父设备信息
    </legend>
  <table width="100%" class="queryheadBg">
      <tr height="25">
          <td width="10%" align="right">标签号：</td>
          <td width="20%"><input type="text" name="barcode" readonly value="<%=barcode%>" class="noborderGray"></td>
          <td width="10%" align="right">设备名称：</td>
          <td width="20%"><%=itemInfo.getItemName()%></td>
          <td width="10%" align="right">规格型号：</td>
          <td width="20%"><%=itemInfo.getItemSpec()%></td>

      </tr>
  </table>
      </fieldset>
  <fieldset>
    <legend>
        子设备信息
        <script type="text/javascript">
            var priv = parent.document.getElementById("priv").value;
            if(priv == "SETUP"){
                document.write("<img src=\"/images/eam_images/delete.jpg\" alt=\"删除子设备\" onclick=\"deleteSubItems()\"/>");
                document.write("&nbsp;<img src=\"/images/eam_images/new.jpg\" alt=\"增加子设备\" onclick=\"addSubItems()\"/>");
            }
        </script>

    </legend>
  <script type="text/javascript">
        var columnArr = new Array("checkbox","标签号","设备名称","规格型号");
        var widthArr = new Array("3%","12%","15%","18%");
        printTableHead(columnArr,widthArr);
  </script>
<div style="overflow-y:scroll;height:420px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);

                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" height="22" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("BARCODE")%>"></td>
                <td width="12%"><%=row.getValue("BARCODE")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="18%"><%=row.getValue("ITEM_SPEC")%>
                </td>

                
            </tr>
            <%
                    }
                }
            %>
        </table></form>
    </div>


      <div style=" right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<div style="position:absolute;bottom:1%"><jsp:include page="/message/MessageProcess"/></div>
      </fieldset>

  </body>
  <script type="text/javascript">
      function addSubItems(){
          var barcode = document.getElementById("barcode").value
          var url = "/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet?flag=ADD&parent_barcode="+barcode;
          var style = "width=800,height=550,top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
          window.open(url,"subItems",style);
      }
      function deleteSubItems(){
          document.mainForm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
          document.mainForm.submit();
      }
  </script>
</html>