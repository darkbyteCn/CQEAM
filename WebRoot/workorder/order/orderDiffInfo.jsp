<%--
  User: zhoujs
  Date: 2007-11-8
  Time: 11:27:13
  Function:工单归档页面
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.log.Logger" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.AmsOrderConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<%@ page import="com.sino.ams.workorder.dto.OrderDiffDTO" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    EtsWorkorderBatchDTO batchDTO = (EtsWorkorderBatchDTO) request.getAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO);
    EtsWorkorderDTO workorderDTO = (EtsWorkorderDTO) request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);
//    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);

    RowSet prevDtlRows = (RowSet) request.getAttribute(WebAttrConstant.PRE_SCAN_DTL);
    RowSet currDtlRows = (RowSet) request.getAttribute(WebAttrConstant.CUR_SCAN_DTL);
    DTOSet diffDTOSet = (DTOSet) request.getAttribute(WebAttrConstant.DIFF_DTOSET);

    String showAllDiv = "N";
    if (workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK) || workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)) {
        showAllDiv = "Y";
    }

    String firstPendingOrder = (String) request.getAttribute("firstPendingOrder");

    if (StrUtil.isEmpty(firstPendingOrder)) {
        firstPendingOrder = "";
    }
    String boundenHtml = (String) request.getAttribute(WebAttrConstant.BOUNDEN);
    if (StrUtil.isEmpty(boundenHtml)) {
        boundenHtml = "";
    }
    String diffCount = (String) request.getAttribute("diffCount");
    int diffCountint = diffCount.equalsIgnoreCase("true") ? 1 : 0;
%>
<html>
<head>
    <title>待归档工单</title>

    <link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/css.css">
	<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/tab.js"></script>
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
    <script type="text/javascript">
        var rightArr = new Array();
        var groupArr = new Array();
        var roleArr = new Array();
        var winstyle = "dialogWidth:20.1;dialogHeight:14.8;center:yes;status:no";


        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
        var ArrAction1 = new Array(false, "暂存", "action_save.gif", "暂存", "save");
        var ArrAction2 = new Array(true, "归档", "nmw.gif", "归档", "doAchieve");
        var ArrAction3 = new Array(false, "设备数量差异", "t_stat.gif", "设备数量差异", "showNum");
        var ArrAction4 = new Array(false, "退回", "arrow_pleft.gif", "退回", "do_Back");
        var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4);

        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
        <%if(showAllDiv.equals("Y")){%>
        //        ShowSinoButton(3);
        <%}%>

    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 onload="initPage();" style="overflow:auto">
<script>
    var tabBox = new TabBox("tttt");
    tabBox.addtab("orderInfo", "工单信息");
    tabBox.addtab("currDt", "本次扫描结果");
    <%
    if(showAllDiv.equals("Y")){
        if(!workorderDTO.getWorkorderType().equals("18")){
    %>
    		tabBox.addtab("prevDt", "上次巡检结果");
    <%
    	}
    %>
    	tabBox.addtab("diffDt", "差异情况");
    <%
    }
    %>
    printTitleBar("待归档工单");
    printToolBar();
    tabBox.init();
</script>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.OrderAchieveServlet">

<div id="orderInfo" style="">

    <table align="center" width='98%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666"
           bordercolordark="#FFFFFF">
        <tr>
            <td>
                <table id="p1" width="95%" border="1" cellpadding="2" cellspacing="0" bordercolor="#666666"
                       bordercolordark="#FFFFFF">
                    <tr>
                        <td width="12%" scope="col">工单批号:</td>
                        <td scope="col"><input name="workorder_batch_no" type="text" readonly class="input_style2" size="60"
                                               value="<%=workorderDTO.getWorkorderBatch()%>"></td>
                        <td>任务名称:</td>
                        <td><input readonly class="input_style2" name="workorder_batch_name" type="text" size="60"
                                   value="<%=batchDTO.getWorkorderBatchName()%>"></td>
                    </tr>
                    <tr>
                        <td width="12%" scope="col">所属工程：</td>
                        <td colspan="3" scope="col"><input readonly class="input_style2" size="60" type="text"
                                                           name="batchPrj"
                                                           value="<%=workorderDTO.getPrjName()%>"></td>
                    </tr>
                    <tr>
                        <td width="12%" scope="col">说明:</td>
                        <td colspan="3" scope="col"><textarea rows="3" cols="60" name="workorder_batch_remark" class="input_style1"
                                                              readonly="true"><%=batchDTO.getRemark()%>
                        </textarea></td>
                    </tr>
                </table>
                <table id="p2" width="95%" border="1" cellpadding="2" cellspacing="0" bordercolor="#666666"
                       bordercolordark="#FFFFFF">
                    <tr>
                        <td width="12%">工单号:</td>
                        <td><input readonly class="input_style2" name="workorderNo" type="text" size="60"
                                   value="<%=workorderDTO.getWorkorderNo()%>">
                        </td>
                        <td>工单类型:</td>
                        <td><input readonly class="input_style2" name="workorderTypeDesc" type="text" size="60" value="<%=workorderDTO.getWorkorderTypeDesc()%>">
                            <input readonly class="input_style2" name="workorderType" type="hidden" value="<%=workorderDTO.getWorkorderType()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">地点编号:</td>
                        <td><input readonly class="input_style2" type="text" name="workorderObjectCode" size="60"
                                   value="<%=workorderDTO.getWorkorderObjectCode()%>">
                        </td>
                        <td>工单状态:</td>
                        <td><input readonly class="input_style2" name="workorderFlagDes" type="text" size="60"
                                   value="<%=workorderDTO.getWorkorderFlagDesc()%>">
                            <input readonly class="input_style2" name="workorderFlag" type="hidden"
                                   value="<%=workorderDTO.getWorkorderFlag()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">地点简称:</td>
                        <td colspan="3"><input readonly class="input_style2" size="60"
                                               name="workorderObjectName" type="text"
                                               value="<%=workorderDTO.getWorkorderObjectName()%>"></td>
                    </tr>
                    <%if (workorderDTO.getWorkorderType().equals("14")) {//搬迁%>
                    <tr>
                        <td width="12%">搬迁至地点编号:</td>
                        <td colspan="3"><input readonly class="input_style2" name="transCode" type="text" size="60"
                                               value="<%=workorderDTO.getTransObjectCode()%>"></td>
                    </tr>
                    <tr>
                        <td width="12%">搬迁至地点简称:</td>
                        <td colspan="3"><input readonly class="input_style2" style="width:300px" name="transLoc" size="60"
                                               type="text"
                                               value="<%=workorderDTO.getTransObjectName()%>"></td>
                    </tr>
                    <%}%>
                    <tr>
                        <td width="12%">开始时间:</td>
                        <td><input readonly class="input_style2" name="startDate" type="text" size="60"
                                   value="<%=workorderDTO.getStartDate()%>"></td>
                        <td>实施周期:</td>
                        <td><input readonly class="input_style2" name="deadlineDate" type="text" size="60"
                                   value="<%=workorderDTO.getImplementDays()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">接单部门:</td>
                        <td><input readonly class="input_style2" type="text" name="group" size="60"
                                   value="<%=workorderDTO.getGroupName()%>"></td>
                        <td>执行人:</td>
                        <td><input readonly class="input_style2" name="implementUser" type="text" size="60"
                                   value="<%=workorderDTO.getImplementUser()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">工单状态:</td>
                        <td><input readonly class="input_style2" name="workorderFlagDesc" type="text" size="60"
                                   value="<%=workorderDTO.getWorkorderFlagDesc()%>">
                            <input readonly class="input_style2" name="workorderFlag" type="hidden"
                                   value="<%=workorderDTO.getWorkorderFlag()%>">
                        </td>
                        <td>所属专业:</td>
                        <td><input readonly class="input_style2" name="category" type="text" size="60"
                                   value="<%=workorderDTO.getAttribute4()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%"> 说 明:</td>
                        <td colspan="3"><textarea rows="3" cols="60" name="remark" class="input_style1"><%=workorderDTO.getRemark()%>
                        </textarea>
                        </td>
                    </tr>
                </table>

                <table id="p3" width="95%" border="1" cellpadding="2" cellspacing="0" bordercolor="#666666"
                       bordercolordark="#FFFFFF">
                    <tr>
                        <td width="12%" scope="col">下发日期:</td>
                        <td scope="col"><input readonly class="input_style2" name="distribudate" type="text" size="60"
                                               value="<%=workorderDTO.getDistributeDate()%>">
                        </td>
                        <td>完成日期:</td>
                        <td><input readonly class="input_style2" name="uploadDate" type="text" size="60"
                                   value="<%=workorderDTO.getUploadDate()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">下载日期:</td>
                        <td><input readonly class="input_style2" name="downloadDate" type="text" size="60"
                                   value="<%=workorderDTO.getDownloadDate()%>">
                        </td>
                        <td>归档日期:</td>
                        <td><input readonly class="input_style2" name="checkoverDate" type="text" size="60"
                                   value="<%=workorderDTO.getCheckoverDate()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">扫描日期:</td>
                        <td><input readonly class="input_style2" name="scanoverDate" type="text" size="60"
                                   value="<%=workorderDTO.getScanoverDate()%>">
                        </td>
                        <td>责任人:</td>
                        <td><input readonly class="input_style2" name="responsibilityUser" type="text" size="60"
                                   value="<%=workorderDTO.getResponsibilityUser()%>">
                        </td>
                    </tr>
                    <tr>
                        <td width="12%">差异原因分析:</td>
                        <td><input readonly class="input_style2" name="differenceReason" type="text" size="60"
                                   id="differenceReason"
                                   value="<%=workorderDTO.getDifferenceReason()%>"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>

    </table>
</div>

<input type="hidden" name="systemid" value="<%=workorderDTO.getSystemid()%>">
<input type="hidden" name="workorderNo" value="<%=workorderDTO.getWorkorderNo()%>">
<input type="hidden" name="attribute1" value="<%=workorderDTO.getAttribute1()%>">
<input type="hidden" name="workorderObjectNo" value="<%=workorderDTO.getWorkorderObjectNo()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="showAllDiv" value="<%=showAllDiv%>">
<input type="hidden" name="firstPendingOrder" value="<%=firstPendingOrder%>">
<input type="hidden" name="prjId" value="<%=workorderDTO.getPrjId()%>">

<div id="currDt" style="display:none">
    <table border="1" style="border-style:dotted;border:1px;border-color:white;" cellpadding="1" width="100%">
        <tr class="headerTR" bgcolor="#FEFFE8" height="22">
            <td width="8%"><font color="white">条形码</font></td>
            <td width="6%" height="22"><font color="white">扫描状态</font></td>
            <td width="5%" height="22"><font color="white">数量</font></td>
            <td width="18%" height="22"><font color="white">设备名称</font></td>
            <td width="12%" height="22"><font color="white">规格型号</font></td>
            <td width="8%" height="22"><font color="white">设备专业</font></td>
            <td width="20%" height="22"><font color="white">责任部门</font></td>
            <td width="8%" height="22"><font color="white">责任人</font></td>
            <td width="8%" height="22"><font color="white">使用人</font></td>
        </tr>
        <%
            Row currDtRow = null;
            for (int i = 0; currDtlRows != null && i < currDtlRows.getSize(); i++) {
                currDtRow = currDtlRows.getRow(i);
        %>
        <tr class="dataTR">
            <td height="20"><%=currDtRow.getValue("BARCODE")%></td>
            <td height="20"><%=currDtRow.getValue("ITEM_STATUS_NAME")%></td>
            <td height="20"><%=currDtRow.getValue("ITEM_QTY")%></td>
            <td height="20"><%=currDtRow.getValue("ITEM_NAME")%></td>
            <td height="20"><%=currDtRow.getValue("ITEM_SPEC")%></td>
            <td height="20"><%=currDtRow.getValue("ITEM_CATEGORY_DESC")%></td>
            <td height="20"><%=currDtRow.getValue("DEPT_NAME")%></td>
            <td height="20"><%=currDtRow.getValue("USER_NAME")%></td>
            <td height="20"><%=currDtRow.getValue("MAINTAIN_USER")%></td>
        </tr>
        <%
            }
        %>
        <tr bgcolor="#829AD5">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>

<div id="prevDt" style='display:none'>
    <table bgcolor="#829AD5" style="border-style:dotted;border:1px;border-color:white;" border="1" cellpadding="1" width="100%">
        <tr class="headerTR" align="left" bgcolor="#FEFFE8">
            <td width="8%"><font color="white">条形码</font></td>
            <td width="6%" height="22"><font color="white">扫描状态</font></td>
            <td width="5%" height="22"><font color="white">数量</font></td>
            <td width="18%" height="22"><font color="white">设备名称</font></td>
            <td width="12%" height="22"><font color="white">规格型号</font></td>
            <td width="8%" height="22"><font color="white">设备专业</font></td>
            <td width="20%" height="22"><font color="white">责任部门</font></td>
            <td width="8%" height="22"><font color="white">责任人</font></td>
            <td width="8%" height="22"><font color="white">使用人</font></td>
        </tr>
        <%
            Row prevDtRow = null;
            for (int i = 0; prevDtlRows != null && i < prevDtlRows.getSize(); i++) {
                prevDtRow = prevDtlRows.getRow(i);
        %>
        <tr class="dataTR">
            <td height="20"><%=prevDtRow.getValue("BARCODE")%></td>
            <td height="20"><%=prevDtRow.getValue("ITEM_STATUS_NAME")%></td>
            <td height="20"><%=prevDtRow.getValue("ITEM_QTY")%></td>
            <td height="20"><%=prevDtRow.getValue("ITEM_NAME")%></td>
            <td height="20"><%=prevDtRow.getValue("ITEM_SPEC")%></td>
            <td height="20"><%=prevDtRow.getValue("ITEM_CATEGORY_DESC")%></td>
            <td height="20"><%=prevDtRow.getValue("DEPT_NAME")%></td>
            <td height="20"><%=prevDtRow.getValue("USER_NAME")%></td>
            <td height="20"><%=prevDtRow.getValue("MAINTAIN_USER")%></td>
        </tr>
        <%
            }
        %>
        <tr bgcolor="#829AD5">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>

<div id="diffDt" style='display:none'>
    <table border="0" cellpadding="1" width="100%">
        <tr>
            <td height="22" colspan="7">
                <fieldset style="border:1px solid #226E9B;width:auto">
                    <legend>差异分析</legend>
                    <table width="100%" border="0" align="left" cellspacing="" bgcolor="#FFFFFF">
                        <tr>
                            <td><span>责任人:</span></td>
                            <td>
                                <select name="respUser" style="width:275" class="select_style1">
                                    <option value="">--请选择--</option>
                                    <option value="-100">无责任人</option>
                                    <%=boundenHtml%>
                                </select>
                            </td>

                        </tr>
                        <tr>
                            <td><span>差异原因:</span></td>
                            <td>
                                <span>
                                <textarea name="diffReason" rows="3" class="intput_style1" cols="50"><%=workorderDTO.getDifferenceReason()%></textarea>
                                </span>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>

        <tr>
            <td height="22" colspan="7">
                <fieldset style="border:1px solid #226E9B;width:auto;height:450">
                    <legend>设备明细差异<img src="/images/eam_images/scan_result.jpg" onclick="chgDealWin(true);"> <img src="/images/eam_images/system_status.jpg" onclick="chgDealWin(false);"><input type="checkbox" name="setAllcheckedSpecialDept" onclick="chooseSpecialDeptForChecked();"/>统一设置专业管理部门 </legend>
                <div id="headDiv" style="overflow:scroll;left:1px;height:88%;width:100%">
                    <table border="1" align="left" cellpadding="1" id="dataT" style="border-style:dotted;border:1px;border-color:white;width:150%">
                        <tr class="eamDbHeaderTable" bgcolor="#FEFFE8" height="22">
                            <td height="22" rowspan="2"><input type="checkbox" name="checkCtrl" value='' onclick="checkAll(this.name,'barcodes');"/></td>
                            <td height="22" rowspan="2"><font color="white">条形码</font></td>
                            <td height="22" rowspan="2"><font color="white">核实结果</font></td>
							<td height="22" rowspan="2"><font color="white">专业管理部门</font></td>
                            <td height="22" colspan="1" align="center"><font color="white">状态</font></td>
                            <td height="22" colspan="3" align="center"><font color="white">扫描属性</font></td>
                            <%if(workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)){%>
                            <td height="22" colspan="3" align="center"><font color="white">系统属性</font></td>
                            <%}%>
                            <td height="22" rowspan="2"><font color="white">备注</font></td>
                            <td height="22"  rowspan="2"><font color="white">变动历史</font></td>
                        </tr>
                        <tr class="eamDbHeaderTable" bgcolor="#FEFFE8" height="22">
                            <td height="22"><font color="white">扫描状态</font></td>
                            <!-- <td height="22"><font color="white">系统状态</font></td> -->
                            <td height="22"><font color="white">设备名称</font></td>
                            <td height="22"><font color="white">规格型号</font></td>
                            <td height="22"><font color="white">责任人</font></td>
                            <%if(workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)){%>
                            <td height="22"><font color="white">设备名称</font></td>
                            <td height="22"><font color="white">规格型号</font></td>
                            <td height="22"><font color="white">责任人</font></td>
                                <%}%>
                        </tr>

                        <%
                            try {
                                OrderDiffDTO orderDiffDTO = new OrderDiffDTO();
                                if (diffDTOSet != null && diffDTOSet.getSize() > 0) {
                                    for (int i = 0; i < diffDTOSet.getSize(); i++) {
                                        orderDiffDTO = (OrderDiffDTO) diffDTOSet.getDTO(i);
                                        String jsPara = "";
                                        String bgColor = orderDiffDTO.getScanStatus().equals("6") ? "#FFFF00" : "#FEFFE8";
                        %>
                        <tr class="dataTR" bgcolor="white">
                            <td height="22" onclick="aa();">
                                <input type="checkbox" name="barcodes" value="<%=orderDiffDTO.getBarcode()%>"/>
                            </td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getBarcode()%></td>
                            <td height="20" onclick="<%=jsPara%>">
                                <input type="text" name="dealResult" class="input_style2" onclick="chooseResult(this);" value="<%=AmsOrderConstant.CONFIRM_NONE%>" readOnly="true" size='15'>
                            </td>
							<td>

                            	<input type="text" name="specialDept" class="input_style2" onclick="chooseSpecialDept(this);" value="<%=AmsOrderConstant.CONFIRM_SPECIAL_DEPT%>" readOnly="true" size="40">
                            </td>
                            <td height="20" onclick="<%=jsPara%>">
                                <%=orderDiffDTO.getScanStatusDesc()%>
                                <input type="hidden" name="itemStatus"  value="<%=orderDiffDTO.getItemStatus()%>">
                            </td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getItemName()%></td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getItemSpec()%></td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getResponsibilityUserName()%></td>
                            <%if(workorderDTO.getWorkorderType().equals(DictConstant.ORDER_TYPE_HDV)){%>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getSystemItemName()%></td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getSystemItemSpec()%></td>
                            <td height="20" onclick="<%=jsPara%>"><%=orderDiffDTO.getSystemResponsibilityUserName()%></td>
                               <%}%>
                            <td height="20" onclick="<%=jsPara%>">
                                <input type="text" name="remarks" class="input_style1" style="width:100%;text-align:left">
                            </td>
                            <td height="20"><a href="#" class="linka" onclick="queryHis('<%=orderDiffDTO.getBarcode()%>')">查询</a></td>
                        </tr>
                        <%
                                    }
                                }
                            } catch (Exception e) {
                                Logger.logError(e);
                            }
                        %>
                    </table>
                    </div>
                </fieldset>
            </td>
        </tr>

        <tr bgcolor="#829AD5">
            <td height="0" colspan="7" align="center">
            </td>
        </tr>
    </table>
</div>
<input type="hidden" name="checkId" value="">
<input type="hidden" name="diffCount" value="<%=diffCountint%>">
<input type="hidden" name="isInitScan" value="<%=(request.getAttribute("isInitScan")==null)?(""):("true")%>">
</form>
</body>
</html>
<script>
    top.document.title = '<%="待归档工单："+workorderDTO.getWorkorderNo()%>';
</script>
<script language="javascript">
    function aa() {

    }

    function initPage() {
        var firstPendingOrder = document.mainFrm.firstPendingOrder.value;
        if (firstPendingOrder != "") {
            setFrmDisable("mainFrm");
        }

        tabBox.inithidetab(1);
        selectSpecialOption("respUser", "<%=workorderDTO.getResponsibilityUser()%>");
    }
    var initScanMsg = "本工单由于以下原因不需要修改，请直接归档!\n   1.目前系统中该地点没有任何设备。\n   2.本次扫描结果将作为该地点初始数据。";

    function save() {
        var firstPendingOrder = document.mainFrm.firstPendingOrder.value;
        if (firstPendingOrder != "") {
            alert("请先处理该地点下的工单:" + firstPendingOrder);
            return;
        }
        document.mainFrm.action.value = "save";
        if (document.mainFrm.diffReason.value.length > 200) {
            alert("'差异原因'长度不能超过120个汉字!");
            return;
        }
        document.mainFrm.action.value = "save";
        document.mainFrm.submit()
    }
    function doAchieve() {
        document.mainFrm.act.value = "confirm";
        if (confirm("确定要归档？")) {
            if (!checkDeal()) {
                return;
            }
			
			if(!checkDeptCode()) {
            	return;
            }
			
            document.mainFrm.act.value = "confirm";
            document.getElementById("checkCtrl").checked = true;
            checkAll("checkCtrl", "barcodes");
            document.mainFrm.submit()
        }
    }
	//zhanghuan
    function checkDeptCode() {
    	var allCheckObj = document.mainFrm.barcodes;//document.all["barcodes"];
    	var allDeptObj = document.mainFrm.specialDept;//document.all["specialDept"];
    	var checkboxLength = allCheckObj.length;
	    if (checkboxLength) 
	    {
	        for (var i = 0; i < checkboxLength; i++) 
	        {
		        if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) 
		        {
		        	if(allDeptObj[i].value == "<%=AmsOrderConstant.CONFIRM_SPECIAL_DEPT%>")
		           	{
		        		alert("请先输入专业管理部门");
		        		allDeptObj[i].focus();
		        		return false;
		           	}
		        }
	        }
	    }
	    return true;
    }
    function checkDeal() {
        //    if (document.all('isInitScan').value != '') {
        //        return true;
        //    }
        var firstPendingOrder = document.mainFrm.firstPendingOrder.value;
        if (firstPendingOrder != "") {
            alert("请先处理该地点下的工单:" + firstPendingOrder);
            return false;
        }
        if (document.mainFrm.workorderType.value == "12") {
            var result = document.all("dealResult");

            if (result) {
                if (document.mainFrm.respUser.value == "") {
                    alert("\"责任人\"不能为空!");
                    return false;
                }

                if (document.mainFrm.diffReason.value.length > 200) {
                    alert("\"差异原因\"长度不能超过120个汉字!");
                    return false;
                }

                if (result.length) {
                    for (var i = 0; i < result.length; i++) {
                        if (result[i].value == "未处理") {
                            alert("请先处理该工单所有差异!");
                            return false;
                        }
                    }
                } else {
                    if (result.value == "未处理") {
                        alert("请先处理该工单所有差异!!");
                        return false;
                    }
                }
            }

        } else {
        	if (document.all("dealResult")) {
        		var result = document.all("dealResult");
                if (result.length) {
                    for (var i = 0; i < result.length; i++) {
                        if (result[i].value == "未处理") {
                            alert("请先处理该工单所有差异!");
                            return false;
                        }
                    }
                } else {
                    if (result.value == "未处理") {
                        alert("请先处理该工单所有差异!!");
                        return false;
                    }
                }
        	}
        }
        return true;
    }

    function chooseResult(obj) {//选择确认结果（单条记录）
        var winstyle = "dialogWidth:20.1;dialogHeight:10.8;center:yes;status:no";
        var result = window.showModalDialog(targetAction, null, winstyle);
        if (result) obj.value = result;
    }
	
	//统一选择专业管理部门（所有选中的记录）
    function chooseSpecialDeptForChecked() 
    {
    	if(!mainFrm.setAllcheckedSpecialDept.checked) return;
    	
        var workorderNo = "<%=workorderDTO.getWorkorderNo()%>";
        var lookUpName = "<%=LookUpConstant.LOOK_UP_SPECIALITY_DEPT%>";
        var winstyle = "dialogWidth:40.1;dialogHeight:35.8;center:yes;status:no";
        var targetAction = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=" + lookUpName + "&workorderNo=" + workorderNo;
        //the result is deptid or deptname, or a struct including both.
        var result = window.showModalDialog(targetAction, null, winstyle);
        //set specialDept for all of the checked record.

		//var chkArr = document.mainFrm.barcodes;
		if(!result) return;
		
		var deptCode=result[0].deptCode;
		var deptName=result[0].deptName;
    	var allCheckObj = document.mainFrm.barcodes;//document.all["barcodes"];
    	var allDeptObj = document.mainFrm.specialDept;//document.all["specialDept"];
    	var checkboxLength = allCheckObj.length;
	    if (checkboxLength) 
	    {
	        for (var i = 0; i < checkboxLength; i++) 
	        {
		        if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) 
		        {
		            allDeptObj[i].value = result[0].deptName+" ["+result[0].deptCode+"]";
		        }
	        }
	    } 
		else 
		{
		    alert("请选择一条记录后，再执行本操作！");
		}
	}
    
    //选择专业管理部门（单条记录）
    function chooseSpecialDept(obj) {
        var workorderNo = "<%=workorderDTO.getWorkorderNo()%>";
        var lookUpName = "<%=LookUpConstant.LOOK_UP_SPECIALITY_DEPT%>";
        var winstyle = "dialogWidth:40.1;dialogHeight:35.8;center:yes;status:no";
        var targetAction = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=" + lookUpName +"&workorderNo=" + workorderNo;
        var result = window.showModalDialog(targetAction, null, winstyle);
        if(result)
        {
        	obj.value = result[0].deptName+" ["+result[0].deptCode+"]";
        }
        else
        {
        	obj.value="<%=AmsOrderConstant.CONFIRM_SPECIAL_DEPT%>";
        }
    }

    function to_def() {

        var tb = document.getElementById("dataT").rows.length;
        var tbObj = document.getElementById("dataT");
        for (var i = 3; i < tb; i++) {
            if (tbObj.rows[i].cells[4].childNodes[0].value == '6') {
                tbObj.rows[i].cells[2].childNodes[0].value = '扫描结果为准';
            } else if (tbObj.rows[i].cells[4].childNodes[0].value == '5') {
                tbObj.rows[i].cells[2].childNodes[0].value = '系统数据为准';
            }
        }

    }
    function chgDealWin(onScan) {//选择以扫描或系统结果为准
        //    var firstPendingOrder = document.mainFrm.firstPendingOrder.value;
        //    if (firstPendingOrder != "") {
        //        alert("请先处理该地点下的工单:" + firstPendingOrder);
        //        return;
        //    }
        //    if (document.all("isInitScan").value != "") {
        //        alert(initScanMsg);
        //        return;
        //    }

        var orderId = document.mainFrm.workorderNo.value;
        var chkArr = document.mainFrm.barcodes;
        if (getCheckedBoxCount("barcodes") == 0) {
            alert("请选择一条记录后，再执行本操作！");
            return;
        }
        if (getCheckBoxCount("barcodes") == 1) {
            if (onScan) {
                resultName = "扫描结果为准";
            } else {
                resultName = "系统数据为准";
            }
            mainFrm.dealResult.value = resultName;
        } else {
            var paraArr = new Array();
            var idArr = new Array();
            var a;
            for (var i = 0; i < chkArr.length; i++) {
                if (chkArr[i].checked && chkArr[i].value) {
                    //            paraArr[paraArr.length] = chkArr[i].value.split(",")[0];
                    idArr[idArr.length] = i;
                }
            }

            var resultName = "";
            var result = "qaaaa";
            if (result) {
                if (onScan) {
                    resultName = "扫描结果为准";
                } else {
                    resultName = "系统数据为准";
                }
                for (i = 0; i < idArr.length; i++) {
                    document.mainFrm.dealResult[idArr[i]].value = resultName;
                }
            }
        }
    }
    function showDealWin(barCode, scanStatus, currStatus, object_box_id, box_locator_id, net_unit_id,
                         pre_object_box_id, pre_box_locator_id, pre_net_unit_id, index) {
        var firstPendingOrder = document.mainFrm.firstPendingOrder.value;
        if (firstPendingOrder != '') {
            alert("请先处理该地点下的工单:" + firstPendingOrder);
            return;
        }
        if (document.all('isInitScan').value != '') {
            alert(initScanMsg);
            return;
        }
        var orderNo = document.mainFrm.workorderNo.value;
        var targetAction = "/servlet/SinoETS.WorkOrder.order.DiffEntityServlet?orderId=" + orderId
                + "&barCode=" + barCode + "&scanStatus=" + scanStatus + "&currStatus=" + currStatus
                + "&object_box_id=" + object_box_id + "&box_locator_id=" + box_locator_id + "&net_unit_id=" + net_unit_id
                + "&pre_object_box_id=" + pre_object_box_id + "&pre_box_locator_id=" + pre_box_locator_id
                + "&pre_net_unit_id=" + pre_net_unit_id;
        var winstyle = "dialogWidth:20.1;dialogHeight:16.4;center:yes;status:no";
        var result = window.showModalDialog(targetAction, "", winstyle);
        var resultName = "";
        if (result == 0) {
            resultName = "扫描结果为准";
            document.mainFrm.dealResult[index].value = resultName;
        } else if (result == 1) {
            resultName = "系统数据为准";
            document.mainFrm.dealResult[index].value = resultName;
        } else {
            //resultName='未处理';
        }

    }
    function queryHis(barCode) {
        var winstyle = "dialogWidth:800px;dialogHeight:640px;top:0;left:0;help:no;status:no;toolbar:no;menubar:no;resizable:no;scrollbars:yes";
        var targetAction = "/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet?act=DETAIL_ACTION&barcode=" + barCode;
        window.showModalDialog(targetAction, '', winstyle);
    }
    function checkme() {
        return true;
    }
    function CheckListEx() {
        setCheckBoxState("checkId", document.mainFrm.checkCtrl.checked);
    }
    //巡检工单退回
	function do_Back() {
		if (confirm("本次巡检数据将删除，是否确认退回？")) {
	      	document.mainFrm.act.value = "workorderBack";
            document.mainFrm.submit();
	    }
	}
	
</script>