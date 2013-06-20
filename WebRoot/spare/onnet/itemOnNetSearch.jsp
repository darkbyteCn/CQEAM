<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.onnet.dto.AmsItemOnNetDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>设备现网量维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemOnNetDTO onNetDTO = (AmsItemOnNetDTO) request.getAttribute(WebAttrConstant.ON_NET_DTO);
    String totalCount = StrUtil.nullToString(request.getAttribute(WebAttrConstant.DIFF_COUNT));
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet">
<script type="text/javascript">
    printTitleBar("设备现网量维护")
</script>
<table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="9%" align="right">公司名称：</td>
            <td width="18%"><select class="select_style1" style="width:100%"  name="organizationId"><%=request.getAttribute(WebAttrConstant.CITY_OPTION)%></select></td>
            <td width="9%" align="right">设备名称：</td>
            <td width="18%"><input type="text" name="itemName" class="input_style1" style="width:100%" value= "<%=onNetDTO.getItemName()%>"></td>
            <td width="9%" align="right">设备型号：</td>
            <td width="18%"><input type="text" name="itemSpec" class="input_style1" style="width:100%" value="<%=onNetDTO.getItemSpec()%>"></td>
            <td width="9%" align="right">设备类型：</td>
            <td width="18%"><input type="text" name="itemCategory" class="input_style1" style="width:100%" value="<%=onNetDTO.getItemCategory()%>"></td>
        </tr>
       <tr>
            <td width="9%" align="right">用途：</td>
            <td width="18%"><input type="text" name="spareUsage" class="input_style1" style="width:100%" value="<%=onNetDTO.getSpareUsage()%>"></td>
            <td width="9%" align="right">厂商：</td>
            <td width="18%"><select name="vendorId" style="width:100%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
            <td width="9%" align="right">ID号：</td>
            <td width="18%"><input type="text" name="partNo" class="input_style1" style="width:100%" value="<%=onNetDTO.getPartNo()%>"></td>
            <td width="27%" align="right" colspan=2><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"><img src="/images/eam_images/imp_from_excel.jpg"  style="cursor:'hand'" onclick="do_import();" alt="导入"></td>
        </tr>
</table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" class="headerTable" border="1">
        <tr height="20">
            <td width="10%" align="center">公司</td>
            <td width="5%" align="center">ID号</td>
            <td width="10%" align="center">设备名称</td>
            <td width="10%" align="center">设备型号</td>
            <td width= "10%" align= "center">设备类型</td>
            <td width= "10%" align= "center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="5%" align="center">数量</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">

<div style="overflow-y:scroll;left:0px;width:100%;height:330px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("ORGNIZATION_NAME")%></td>
            <td width="5%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("PART_NO")%></td>
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("ITEM_NAME")%></td>
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width= "10%" align= "left" onclick= "show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="10%" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="5%" align="left" onclick="show_detail('<%=row.getValue("ID")%>')"><%=row.getValue("QUANTITY")%></td>
        </tr>
<%
	    } }
%>
    </table>
</div>
</form>
<div>【数量共：<%=totalCount%>】</div>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    mainFrm.submit();
}

function show_detail(id) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet?&id="+id ;
    mainFrm.submit();
}

function do_add() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    mainFrm.submit();
}

function do_disabled(){             //批量失效
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
     } else {
        do_verifyObjectNos();
      }
}

function do_efficient(){             //批量生效
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
        } else {
        if(confirm(ENABLE_MSG))  {
            mainFrm.act.value = "<%=AMSActionConstant.EFFICIENT_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
            mainFrm.submit();
            }
       }
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    mainFrm.submit();
}

function do_import(){
     var url = "/spare/onnet/importOnNet.jsp";
    var popscript = 'width=870,height=700,top=1,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'onnet', popscript);
}
</script>