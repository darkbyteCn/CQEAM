<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    EtsWorkorderDTO order = (EtsWorkorderDTO) request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);
    EtsWorkorderBatchDTO orderBatch = (EtsWorkorderBatchDTO) request.getAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO);

    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);

    RowSet prevDtlRows = (RowSet) request.getAttribute(WebAttrConstant.PRE_SCAN_DTL);
    RowSet currDtlRows = (RowSet) request.getAttribute(WebAttrConstant.CUR_SCAN_DTL);
    RowSet fixNewRows = (RowSet) request.getAttribute(WebAttrConstant.CUR_OBJ_SCHEME_RST);
    RowSet diffDtRows = (RowSet) request.getAttribute(WebAttrConstant.DIFF_SCAN_DTL);
    DTOSet diffDTOSet = (DTOSet) request.getAttribute(WebAttrConstant.DIFF_DTOSET);

    String isCheck = order.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK) ? "Y" : "N";

    String firstPendingOrder = (String) request.getAttribute("firstPendingOrder");

    if (StrUtil.isEmpty(firstPendingOrder)) {
        firstPendingOrder = "";
    }
    String boundenHtml = (String) request.getAttribute(WebAttrConstant.BOUNDEN);
    if (StrUtil.isEmpty(boundenHtml)) {
        boundenHtml = "";
    }
    String diffCount = (String) request.getAttribute("diffCount");

    boolean showScheme = false;
    String scheme_title = "";
    if (order.getWorkorderType().equals("") || order.getWorkorderType().equals("12") || order.getWorkorderType().equals("14")) {
        showScheme = false;
    } else if (order.getWorkorderType().equals("13")) {//维修
        showScheme = true;
        scheme_title = "领用计划";
    } else {
        showScheme = true;
        scheme_title = "计划配置";
    }

%>
<html>
<head>
    <title>代维巡检责任</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
    <script language="javascript" src="/WebLibary/js/tab.js"></script>
    <link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
    <link href="/WebLibary/css/styles.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/flow/flow.js"></script>
    <script type="text/javascript">
        var rightArr = new Array();
        var groupArr = new Array();
        var roleArr = new Array();
        var winstyle = "dialogWidth:20.1;dialogHeight:14.8;center:yes;status:no";


        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
        var ArrAction1 = new Array(false, "审批过程", "addpop.gif", "审批过程", "viewFlow");
        var ArrAction2 = new Array(false, "设备数量差异", "t_stat.gif", "设备数量差异", "showNum");
        var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);

        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
        <%if(isCheck.equals("Y")){%>
        ShowSinoButton(3);
        <%}%>

    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<script>
    var tabBox = new TabBox("tab")
    tabBox.addtab("batchInfo", "任务信息")
    tabBox.addtab("orderInfo", "工单信息")
    tabBox.addtab("currDt", "本次扫描结果")

    <%if(showScheme){%>
    tabBox.addtab("fixNew", "<%=scheme_title%>")
    <%}%>
    <%
      if(isCheck.equals("Y")){
    %>
    tabBox.addtab("prevDt", "上次巡检结果")
    tabBox.addtab("diffDt", "差异情况")
    <%
      }
    %>
    printTitleBar("<%=order.getWorkorderFlagDesc()%>工单");
    printToolBar();
    tabBox.init();
</script>

<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.OrderDetailServlet">
<input type="hidden" name="workorderNo" value="<%=order.getWorkorderNo()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="systemid" value="<%=order.getSystemid()%>">
<input type="hidden" name="isCheck" value="<%=isCheck%>">
<input type="hidden" name="firstPendingOrder" value="<%=firstPendingOrder%>">

<table align="center" width='98%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666"
       bordercolordark="#FFFFFF">
<tr>
<td>
<div id="batchInfo" style='display:none'>
    <table width="95%" border="1" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
        <tr>
            <td scope="col">工单批号:</td>
            <td scope="col"><input name="workorderBatch" type="text" readonly class="readonlyInput"
                                   value="<%=orderBatch.getWorkorderBatch()%>"></td>
            <td>任务名称:</td>
            <td><input readonly class="readonlyInput" name="workorderBatchName" type="text"
                       value="<%=orderBatch.getWorkorderBatchName()%>"></td>
        </tr>
        <tr>
            <td scope="col">所属工程：</td>
            <td colspan="3" scope="col"><input readonly class="readonlyInput" size="60" type="text" name="prjId"
                                               value="<%=orderBatch.getPrjName()%>"></td>
        </tr>
        <tr>
            <td scope="col">任务描述：</td>
            <td colspan="3" scope="col"><textarea rows="3" cols="100" name="remark" readonly
                                                  class="readonlyInput"><%=orderBatch.getRemark()%></textarea></td>
        </tr>
    </table>
</div>

<div id="orderInfo" style='display:none'>
<table width="95%" border="1" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
<tr class="hei">
    <td>工单号:</td>
    <td><input name="workorderNo" type="text" readonly class="readonlyInput"
               value="<%=order.getWorkorderNo()%>">
    </td>
    <td>工单类型:</td>
    <td><input readonly class="readonlyInput" name="workorderTypeDesc" type="text"
               value="<%=order.getWorkorderTypeDesc()%>">
        <input readonly class="readonlyInput" name="workorderType" type="hidden"
               value="<%=order.getWorkorderType()%>">
    </td>
</tr>
<tr>
    <td>地点编号:</td>
    <td><input type="text" name="workorderObjectCode" readonly class="readonlyInput"
               value="<%=order.getWorkorderObjectCode()%>">
    </td>
    <td>工单状态:</td>
    <td><input readonly class="readonlyInput" name="workorderFlagDesc" type="text"
               value="<%=order.getWorkorderFlagDesc()%>">
        <input readonly class="readonlyInput" name="workorderFlag" type="hidden"
               value="<%=order.getWorkorderFlag()%>">
    </td>
</tr>
<tr>
    <td>地点简称:</td>
    <td colspan="3"><input style="width:300px" name="workorderObjectName" type="text" readonly
                           class="readonlyInput" value="<%=order.getWorkorderObjectName()%>"></td>
</tr>
<%if (order.getWorkorderType().equals("14")) {//搬迁%>
<tr>
    <td>搬迁至地点编号:</td>
    <td colspan="3"><input readonly class="readonlyInput" name="transObjectCode" type="text"
                           value="<%=order.getTransObjectCode()%>"></td>
</tr>
<tr>
    <td>搬迁至地点简称:</td>
    <td colspan="3"><input readonly class="readonlyInput" style="width:300px" name="transObjectName" type="text"
                           value="<%=order.getTransObjectName()%>"></td>
</tr>
<%}%>
<tr>
    <td>开始时间:</td>
    <td><input readonly class="readonlyInput" name="startDate" type="text" value="<%=order.getStartDate()%>">
    </td>
    <td>实施周期(天):</td>
    <td><input readonly class="readonlyInput" name="implementDays" type="text"
               value="<%=order.getImplementDays()%>"></td>
</tr>
<tr>
    <td>接单部门:</td>
    <td><input readonly class="readonlyInput" type="text" name="groupName" value="<%=order.getGroupName()%>">
    </td>
    <td>执行人:</td>
    <td><input readonly class="readonlyInput" name="implementUser" type="text"
               value="<%=order.getImplementUser()%>">
    </td>
</tr>
<tr>
    <td width="12%">工单状态:</td>
    <td><input readonly class="readonlyInput" name="workorderFlagDesc" type="text"
               value="<%=order.getWorkorderFlagDesc()%>">
        <input readonly class="readonlyInput" name="workorderFlag" type="hidden"
               value="<%=order.getWorkorderFlag()%>">
    </td>
    <td>所属专业:</td>
    <td><input readonly class="readonlyInput" name="attribute4" type="text" value="<%=order.getAttribute4()%>">
    </td>
</tr>
<tr>
    <td> 说 明:</td>
    <td colspan="3"><textarea readonly class="readonlyInput" rows="3" cols="105"
                              name="remark"><%=order.getRemark()%></textarea>
    </td>
</tr>
<tr class="hei">
    <td scope="col">下发日期:</td>
    <td scope="col"><input readonly class="readonlyInput" name="distributeDate" type="text"
                           value="<%=order.getDistributeDate()%>">
    </td>
    <td>完成日期:</td>
    <td><input readonly class="readonlyInput" name="uploadDate" type="text" value="<%=order.getUploadDate()%>">
    </td>
</tr>
<tr>
    <td>下载日期:</td>
    <td><input readonly class="readonlyInput" name="downloadDate" type="text"
               value="<%=order.getDownloadDate()%>">
    </td>
    <td>归档日期:</td>
    <td><input readonly class="readonlyInput" name="checkoverDate" type="text"
               value="<%=order.getCheckoverDate()%>">
    </td>
</tr>
<tr>
    <td>扫描日期:</td>
    <td><input readonly class="readonlyInput" name="scanoverDate" type="text"
               value="<%=order.getScanoverDate()%>">
    </td>
    <td>责任人:</td>
    <td><input readonly class="readonlyInput" name="responsibilityUser" type="text"
               value="<%=order.getResponsibilityUser()%>">
    </td>
</tr>
<tr>
    <td width="12%">差异原因分析:</td>
    <td><input readonly class="readonlyInput" name="differenceReason" type="text" id="differenceReason"
               value="<%=order.getDifferenceReason()%>"></td>
    <td>　</td>
    <td>　</td>
</tr>
</table>
</div>


<input type="hidden" name="workorderNo" value="<%=order.getWorkorderNo()%>">
<input type="hidden" name="act" value="present">
<input type="hidden" name="systemId" value="<%=order.getSystemid()%>">
<input type="hidden" name="showAllDiv" value="<%=isCheck%>">
<input type="hidden" name="firstPendingOrder" value="<%=firstPendingOrder%>">

<div id="currDt" style="display:none">
    <table bgcolor="#829AD5" border="1" style="border-style:dotted;border:1px;border-color:white;" cellpadding="0"
           cellspacing="0" width="100%">
        <tr class="headerTR" height="22">
            <td width="12%" align="center"><font color="white">条码</font></td>
            <td width="7%"  align="center"><font color="white">扫描状态</font></td>
            <td width="3%"  align="center"><font color="white">数量</font></td>
            <td width="18%" align="center"><font color="white">设备名称</font></td>
            <td width="14%" align="center"><font color="white">规格型号</font></td>
            <td width="10%" align="center"><font color="white">设备类别</font></td>
            <td width="9%"  align="center"><font color="white">供应商</font></td>
            <td width="9%"  align="center"><font color="white">所属机柜</font></td>
            <td width="9%"  align="center"><font color="white">所属网元</font></td>
        </tr>
        <%
            Row currDtRow = null;
            for (int i = 0; currDtlRows != null && i < currDtlRows.getSize(); i++) {
                currDtRow = currDtlRows.getRow(i);
        %>
        <tr bgcolor="#FEFFE8">
            <td height="20"><%=currDtRow.getValue("BARCODE")%>
            </td>
            <td height="20"><%=currDtRow.getValue("ITEM_STATUS_NAME")%>
            </td>
            <td height="20"><%=currDtRow.getValue("ITEM_QTY")%>
            </td>
            <td height="20"><%=currDtRow.getValue("ITEM_NAME")%>
            </td>
            <td height="20"><%=currDtRow.getValue("ITEM_SPEC")%>
            </td>
            <td height="20"><%=currDtRow.getValue("ITEM_CATEGORY_DESC")%>
            </td>
            <td height="20"><%=currDtRow.getValue("VENDOR_NAME")%>
            </td>
            <td height="20"><%=currDtRow.getValue("BOX_NO")%>
            </td>
            <td height="20"><%=currDtRow.getValue("NET_UNIT")%>
            </td>
        </tr>
        <%
            }
        %>
        <tr bgcolor="#829AD5" class="hei">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>

<%if (showScheme) {%>

<div id="fixNew" style="display:none">
    <table bgcolor="#829AD5" border="1" style="border-style:dotted;border:1px;border-color:white;" cellpadding="0"
           cellspacing="0" width="100%">
        <!--<tr>-->
        <%--<td colspan="4">计划配置描述：<input style="background-color:'#EFEFEF';width:500px" name="desc"--%>
        <%--value="<%=scheme_desc%>"></td>--%>
        <!--</tr>-->
        <tr class="headerTR" height="22">
            <td width="20%" >工单号</td>
            <td width="30%" >设备名称</td>
            <td width="30%" >规格型号</td>
            <td width="20%" >数量</td>

        </tr>
        <%

            Row fixNewRow = null;
            for (int i = 0; fixNewRows != null && i < fixNewRows.getSize(); i++) {
                fixNewRow = fixNewRows.getRow(i);
        %>
        <tr bgcolor="#FEFFE8" height="20">
            <td><%=fixNewRow.getValue("WORKORDER_NO")%>
            </td>
            <td><%=fixNewRow.getValue("ITEM_NAME")%>
            </td>
            <td><%=fixNewRow.getValue("ITEM_SPEC")%>
            </td>
            <td><%=fixNewRow.getValue("ITEM_QTY")%>
            </td>
        </tr>
        <%
            }
        %>
        <tr bgcolor="#829AD5" class="hei">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>
<%}%>

<%if (isCheck.equals("Y")) {%>
<div id="prevDt" style='display:none'>
    <table bgcolor="#829AD5" border="1" style="border-style:dotted;border:1px;border-color:white;" cellpadding="0"
           cellspacing="0" width="100%">
        <tr class="headerTR" height="22">
            <td width="12%" align="center"><font color="white">条码</font></td>
            <td width="7%"  align="center"><font color="white">扫描状态</font></td>
            <td width="3%"  align="center"><font color="white">数量</font></td>
            <td width="18%" align="center"><font color="white">设备名称</font></td>
            <td width="14%" align="center"><font color="white">规格型号</font></td>
            <td width="10%" align="center"><font color="white">设备类别</font></td>
            <td width="9%"  align="center"><font color="white">供应商</font></td>
            <td width="9%"  align="center"><font color="white">所属机柜</font></td>
            <td width="9%"  align="center"><font color="white">所属网元</font></td>
        </tr>
        <%
            Row prevDtRow = null;
            for (int i = 0; prevDtlRows != null && i < prevDtlRows.getSize(); i++) {
                prevDtRow = prevDtlRows.getRow(i);
        %>
        <tr bgcolor="#FEFFE8" height="20">
            <td><%=prevDtRow.getValue("BARCODE")%>
            </td>
            <td><%=prevDtRow.getValue("ITEM_STATUS_NAME")%>
            </td>
            <td><%=prevDtRow.getValue("ITEM_QTY")%>
            </td>
            <td><%=prevDtRow.getValue("ITEM_NAME")%>
            </td>
            <td><%=prevDtRow.getValue("ITEM_SPEC")%>
            </td>
            <td><%=prevDtRow.getValue("ITEM_CATEGORY_DESC")%>
            </td>
            <td><%=prevDtRow.getValue("VENDOR_NAME")%>
            </td>
            <td><%=prevDtRow.getValue("BOX_NAME")%>
            </td>
            <td><%=prevDtRow.getValue("NET_UNIT_NAME")%>
            </td>
        </tr>
        <%}%>
        <tr bgcolor="#829AD5" class="hei">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>

<div id="diffDt" style='display:none'>
    <%if (order.getWorkorderFlag().equals(DictConstant.WORKORDER_STATUS_ACHIEVED)) {%>

    <table bgcolor="#829AD5" border="0" cellpadding="1" width="100%">
        <tr bgcolor="#829AD5" class="hei">
            <td height="22" colspan="7">
                <fieldset style="width: auto">
                    <legend style="color: #CECECE">差异分析</legend>
                    <table width="100%" border="0" align="left" cellspacing="" bgcolor="#FFFFFF">
                        <tr bgcolor="#CECECE">
                            <td><span>责任人:</span></td>
                            <td>
                                <input name="responsibilityUser" type="text" id="responsibilityUser" readonly
                                       value="<%=order.getResponsibilityUser()%>">
                            </td>

                        </tr>
                        <tr bgcolor="#CECECE">
                            <td><span>差异原因:</span></td>
                            <td><span><TEXTAREA readonly NAME="diffReason" ROWS="3"
                                                COLS="50"><%=order.getDifferenceReason()%>
                            </TEXTAREA></span></td>

                        </tr>
                    </table>
                </fieldset>
            </td>
            </tr>
        <tr bgcolor="#829AD5"  class="hei">
            <td height="22" colspan="7">
                <filedset style="width: auto">
                  <legend style="color: #CECECE">设备明细差异</legend>
                    <table width="100%" border="0" align="left" cellspacing="0" bgcolor="#829AD5">
                        <tr class="hei" height="22">
                            <td>条码</td>
                            <td>核实结果</td>
                            <td>扫描状态</td>
                            <td>系统状态</td>
                            <td>设备名称</td>
                            <td>规格型号</td>
                            <td>设备类别</td>
                            <td>供应商</td>
                        </tr>
                        <%

                            Row diffDtRow = null;
                            for (int i = 0; diffDtRows != null && i < diffDtRows.getSize(); i++) {
                                diffDtRow = diffDtRows.getRow(i);
                        %>
                        <tr bgcolor="#FEFFE8" height="20">
                            <td><%=diffDtRow.getValue("BARCODE")%>
                            </td>
                            <td><%=diffDtRow.getValue("VERIFY_RESULT")%>
                            </td>
                            <td><%=diffDtRow.getValue("SCAN_STATUS_DESC")%>
                            </td>
                            <td><%=diffDtRow.getValue("ITEM_STATUS_DESC")%>
                            </td>
                            <td><%=diffDtRow.getValue("ITEM_NAME")%>
                            </td>
                            <td><%=diffDtRow.getValue("ITEM_SPEC")%>
                            </td>
                            <td><%=diffDtRow.getValue("ITEM_CATEGORY_DESC")%>
                            </td>
                            <td><%=diffDtRow.getValue("VENDOR_NAME")%>
                        </tr>
                        <%}%>
                        <tr bgcolor="#829AD5" class="hei">
                            <td height="0" colspan="7" align="center">
                            </td>
                        </tr>
                    </table>
                </filedset>
            </td>
        </tr>
    </table>

    <%--<div id="diffDt" style="display:none">--%>
    <!--<iframe height="600" name="woDtl" frameborder="0" width="100%" scrolling="auto"-->
    <%--src="/WorkOrder/order/wait_c.jsp?val=<%=order.getWorkorderNo()%>">--%>
    <%----%>
    <%--<!--src="/servlet/SinoETS.WorkOrder.order.OrderDiffViewServlet?workorderNo=<%=order.getWorkorderNo()%>" -->--%>
    <%----%>
    <!--</iframe>-->

    <%} else {%>
    <table width="95%" border="0" cellpadding="2" cellspacing="0" bordercolor="#666666"
           bordercolordark="#FFFFFF">
        <tr>
            <td height='100' width='20%'></td>
            <td width='20%'>本工单尚未归档！
            </td>
            <td width='20%'></td>
            <td width='20%'>
            </td>
        </tr>
    </table>
    <%}%>
</div>


<%}%>
</td>

</tr>
</table>

</form>
</body>
</html>

<script language="javascript">
    //    function viewFlow(){
    //        var batchNo=document.forms[0].workorder_batch_no.value;
    //        if(batchNo==null||batchNo==""){
    //            return false;
    //        }
    //        var screenHeight = window.screen.height;
    //        var screenWidth = window.screen.width;
    //        var url="/WorkOrder/order/viewFlowInfo.jsp?batchNo="+batchNo;
    //        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,help=no,status=no,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no,center=yes";
    //        window.open(url, "", winstyle);
    //    }
    function initPage() {
            document.all("batchInfo").style.display = "";
            document.all("orderInfo").style.display = "none";
            document.all("currDt").style.display = "none";
//            document.all("prevDt").style.display = "none";
//            document.all("diffDt").style.display = "none";
           <%if(showScheme){%>
        document.all("fixNew").style.display = "none";
    <%}%>
    <%
      if(isCheck.equals("Y")){ %>
        document.all("prevDt").style.display = "none";
        document.all("diffDt").style.display = "none";
    <%
      }
    %>
        tabBox.inithidetab(1);
    }
    function showNum() {
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;
        url = "/WorkOrder/order/deaDiff/NumDiff.jsp?systemid=<%=order.getSystemid()%>";
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,help=no,status=no,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,center=yes";
        window.open(url, "", winstyle);
        /*comment by wangwenbin 2006-5-23
        num.style.display="";
        HideSinoButton(3);
        ShowSinoButton(4);
        */
    }
</script>
