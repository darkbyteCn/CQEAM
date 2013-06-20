<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-19
  Time: 10:36:20
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>仪器仪表维护查询页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language = "javascript" src = "/WebLibary/js/RadioProcess.js"></script>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<jsp:include page="/message/MessageProcess"/>
<body onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet" name="mainForm" method="post">
    <script type="text/javascript">
    printTitleBar("仪器仪表维护页面")
</script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td align="right">地市：</td>
            <td><select style="width:100%"  name="organizationId" ><%=organizationId%></select></td>
            <td align="right">仪器仪表条码：</td>
            <td align="left"><input type="text" name="barcode" value="<%=dto.getBarcode()%>"></td>
            <td align="right">仪器仪表名称：</td>
            <td align="left"><input type="text" name="itemName" value="<%=dto.getItemName()%>"></td>
             <td align="right">仪器仪表型号：</td>
            <td align="left"><input type="text" name="itemSpec" value="<%=dto.getItemSpec()%>"></td>
        </tr>
        <tr>
          <td align="right">责任人：</td>
          <td align="left"><input type= "text" name="responsibilityName" value="<%=dto.getResponsibilityName()%>"></td>
          <td align= "right">使用人：</td>
          <td align="left"><input type = "text" name = "maintainUser" value="<%=dto.getMaintainUser()%>"></td>
          <td align="right">仪器仪表状态：</td>
          <td align="left"><select style= "width:100%" name="itemStatus">
        <option value="" <%=dto.getItemStatus().equals("") ? "selected" : ""%>>--请选择--</option>
        <option value="NORMAL" <%=dto.getItemStatus().equals("NORMAL") ? "selected" : ""%>>正常</option>
        <option value="BORROW" <%=dto.getItemStatus().equals("BORROW") ? "selected" : ""%>>借用</option>
        <option value="SEND_REPAIR" <%=dto.getItemStatus().equals("SEND_REPAIR") ? "selected" : ""%>>送修</option>
        <option value="DISCARDED" <%=dto.getItemStatus().equals("DISCARDED") ? "selected" : ""%>>报废</option>
          </select></td>

          <td></td>
          <td><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export();"><img src = "/images/eam_images/detail_info.jpg" id = "particularImg" style = "cursor:'hand'" onclick = "do_history()" alt = "详细信息"></td>
        </tr>
        <tr>
          <td align= "right">厂商：</td>
            <td align="left"><input type="text" name="vendorName" value="<%=dto.getVendorName()%>"></td>
            <td align="right"></td>
            <td align="left"></td>
            <td align="right"></td>
            <td align="left"></td>
            <td align= "right"></td>
            <td align="left"><img src="/images/eam_images/search.jpg" alt="查询设备" onClick="do_Search(); return false;"><img src="/images/eam_images/new_add.jpg" alt="新增设备" onClick="do_Create(''); return false;"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="barcodeNo">
    <input type="hidden" name="itemCategory">
   <div style="position:absolute;left:1px;width:834px;overflow:hidden" id="headDiv">
    <table  border="1" width="2500" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="1%" align="center" style="padding:0"></td>
            <td width="5%" align="center">公司</td>
            <td width="5%" align="center">条码</td>
            <td width="5%" align="center">仪器仪表名称</td>
            <td width="5%" align="center">规格型号</td>
            <td width="5%" align="center">仪表厂家</td>
            <td width="9%" align="center">使用部门</td>
            <td width="2%" align="center">数量</td>
            <td width="3%" align="center">用途</td>
            <td width="8%" align="center">使用地点</td>
            <td width="5%" align="center">使用人员</td>
            <td width="5%" align="center">仪表性能</td>
            <td width="3%" align="center">仪表状态</td>
            <td width="5%" align="center">责任人</td>
            <td width="9%" align="center">责任部门</td>
            <td width="4%" align="center">单价</td>
            <td width="4%" align="center">总价</td>
        </tr>
    </table>
</div>
     <div style="position:absolute;left:1px;top:119px;width:850px;height:357px;overflow:scroll" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="2500" border="1" bordercolor="#666666" id="dataTab">
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
            <%
                Row row = null;
                int  itemQty=0;
                int  unitPri=0;
                int  sumQty =0;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    if ((!StrUtil.isEmpty(row.getStrValue("ITEM_QTY"))) && (!StrUtil.isEmpty(row.getStrValue("ATTRIBUTE2")))) {
                        itemQty = Integer.parseInt(row.getStrValue("ITEM_QTY"));
                        unitPri = Integer.parseInt(row.getStrValue("ATTRIBUTE2"));
                    }
                    sumQty = itemQty * unitPri;
            %>
            <tr class="dataTR" >
                <td width = "1%" align = "center"><input type = "radio" id = "barcode1" name = "barcode1" value = "<%=row.getValue("BARCODE")%>"></td>
                <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("ORGNIZATION_NAME")%>
                </td>
                <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td height="22" width="5%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" ><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" ><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td height="22" width="9%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" ><%=row.getValue("MAINTAIN_DEPT_NAME")%>
                </td>
                <td height="22" width="2%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align = "center"><%=row.getValue("ITEM_QTY")%>
                </td>
                <td height="22" width="3%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" ><%=row.getValue("ATTRIBUTE3")%>
                </td>
                <td height="22" width="8%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" ><%=row.getValue("OBJECT_NAME")%>
                </td>
                <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("MAINTAIN_USER")%>
                </td>
                 <td height="22" width="5%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("REMARK")%>
                </td>
                 <td height="22" width="3%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("ITEM_STATUS")%>
                </td>
                 <td height="22" width="5%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("RESPONSIBILITY_NAME")%>
                </td>
                 <td height="22" width="9%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>
                </td>
                 <td height="22" width="4%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=row.getValue("ATTRIBUTE2")%>
                </td>
                 <td height="22" width="4%" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')" align="center"><%=sumQty%>
                </td>
            </tr>
            <%
                }     }
            %>
        </table>
    </div>
</form>

<div style="position:absolute;top:94%;left:0;right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
</html>
<script type="text/javascript">
function do_Search() {
    with (mainForm) {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        submit();
    }
}

function do_Create(itemCategory) {
    var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=<%=WebActionConstant.NEW_ACTION%>&itemCategory=" + itemCategory ;
    var popscript = 'width=500,height=485,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'planWin', popscript);
}

function do_export() {
    mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainForm.submit();
}

function do_ShowDetail(barcodeNo, itemCategory) {
    mainForm.barcode.value = barcodeNo;
    mainForm.itemCategory.value = itemCategory;
    var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=" + barcodeNo + "&itemCategory=" + itemCategory;
    var popscript = 'width=500,height=483,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'planWin', popscript);
}

function do_history() {
    var barcode1 = getRadioValue("barcode1");
    if (barcode1 != "") {
        var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=HISTORY_QUERY&barcode1=" + barcode1;
        var popscript = 'width=717,height=400,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, 'planWin', popscript);
    } else {
        alert("请选择一个条码!");
    }
}

</script>