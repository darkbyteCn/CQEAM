<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.constant.URLDefineList"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderTmpDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%--
  User: zhoujs
  Date: 2007-11-5
  Time: 17:02:53
  Function:添加及修改工单信息界面
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    EtsWorkorderTmpDTO etsWorkorderTmp=(EtsWorkorderTmpDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_TMP_DTO);

    boolean showCurScheme=!(etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_CHECK)||etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS));
    String userOtionHtml=(String )request.getAttribute(WebAttrConstant.USER_OPTION);
    RowSet rowSet=(RowSet)request.getAttribute(WebAttrConstant.ETS_WO_SCHEME_RST);
    RowSet schemeRowSet=(RowSet)request.getAttribute(WebAttrConstant.CUR_OBJ_SCHEME_RST);
    boolean isFirstNode= StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");

    String transClass=etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS)?"noEmptyInput":"readonlyInput";
    String category=StrUtil.nullToString(request.getParameter("objectCategory"));
    boolean isPortage  = false;
    if ((isFirstNode)&&etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS)){
          isPortage = true;
}
%>

<html>
<base target="_self">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>工单信息--交换专业</title>

    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
    <link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">
    <link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript">
    <%
     if(isFirstNode){
    %>
    var ArrAction0 = new Array(true, "保存", "action_save.gif", "保存", "do_save();");
    var ArrAction1 = new Array(true, "关闭", "action_sign.gif", "关闭", "window.close();");
    var ArrActions = new Array(ArrAction0,ArrAction1);
    <%
      }else{
    %>
    var ArrAction0 = new Array(true, "关闭", "action_sign.gif", "关闭", "window.close();");
    var ArrActions = new Array(ArrAction0);
    <%
      }
    %>
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();

</script>
<style type="text/css" rel="stylesheet">
    .finput {BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 1px ridge;font-size: 12px;border-bottom-color:blue;}
    .noneinput {BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
    .bkinput {border-style:dotted;}
</style>

</head>

<body topmargin="1" style="overflow:auto" >
<form  method="post" name="mainFrm" action="<%=URLDefineList.WORKORDER_TMP_SERVLET%>">
<%
    if(!isPortage){
%>
<script type="text/javascript">
    printTitleBar("工单详细信息")
</script>
<%
    }
%>
<script>
  printToolBar();
</script>
    <input type="hidden" name="act" value="<%=WebActionConstant.SAVE_ACTION%>">
    <input name="systemid" type="hidden" value="<%=etsWorkorderTmp.getSystemid()%>">
    <input name="objectCategory" type="hidden" value="<%=category%>" >
    <input name="attribute2" type="hidden" >
    <input name="attribute3" type="hidden" >
    <input name="attribute4" type="hidden" >
    <input name="attribute6" type="hidden" >
 <table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF"  style="border-collapse: collapse">
    <tr>
        <td>
    <table width="100%" border="0" cellspacing="0" cellspacing="1" bordercolor="#9FD6FF" bgcolor="#F2F9FF" style="border-collapse: collapse">
        <tr>
            <td align="right" width="12%">工 单 号：</td>
            <td align="left" width="38%"><input class="readonlyInput" style="background-color:#F2F9FF;border:none;width:70%"  name="workorderNo" type="text"  readonly value="<%=etsWorkorderTmp.getWorkorderNo()%>"></td>
            <td align="right" width="12%">工单类型：</td>
            <td align="left" width="38%"><input class="readonlyInput" style="width:70%" name="workorderTypeDesc" type="text" readonly value="<%=etsWorkorderTmp.getWorkorderTypeDesc()%>" style="background-color:#F2F9FF;border:none;width:70%"></td>
        </tr>
        <tr>
            <td align="right" width="12%">地点编号：</td>
            <td align="left"  width="38%">
                <input class="readonlyInput" name="workorderObjectCode" type="text"  value="<%=etsWorkorderTmp.getWorkorderObjectCode()%>" style="width:70%"  readonly="true" style="background-color:#F2F9FF;border:none;width:70%">
                <a class="linka" style="cursor:'hand'" onclick="getBatchNo();"></a>
            </td>
            <td align="right" width="12%">地点名称：</td>
            <td  align="left"  width="38%"><input class="readonlyInput" style="width:70%"    name="objectLoc" type="text" value="<%=etsWorkorderTmp.getWorkorderObjectName()%>" readonly="true" style="border:none;background-color:#F2F9FF;width:100%"></td>
        </tr>
<%
  if(isPortage){
%>
         <tr>
          <td align="right" width="12%">搬迁地点代码：</td>
          <td align="left"  width="38%"><input class = "<%=transClass%>" readonly type = "text" name = "transObjectCode"  value = "<%=etsWorkorderTmp.getTransObjectCode()%>" style="border:solid 1px;border-color:#9FD6FF;width:50%">
                <a href ="#" class = "linka" title = "点击选择搬迁地点" onclick = "SelectsSitue();" >[…]</a>
          </td >
          <td align="right">搬迁地点名称：</td>
          <td><input class="readonlyInput"    name="transObjectName" type="text" value="<%=etsWorkorderTmp.getTransObjectName()%>" readonly="true" style="border:none;background-color:#F2F9FF;width:70%"></td>
         </tr>
        <input type = "hidden" name = "attribute1" value = "<%=etsWorkorderTmp.getAttribute1()%>">
<%
    }
%>
        <tr>
          <td align="right" width="12%">开始时间：</td>
          <td align="left"  width="38%">
              <input class="readonlyInput" name="startDate" type="text"  readonly value="<%=etsWorkorderTmp.getStartDate()%>" style="width:50%" style="border:solid 1px;border-color:#9FD6FF">
              <img src="/images/calendar.gif" alt="选择时间" id="calendar1"  onClick="getDateTime('mainFrm.startDate');">
          </td>
          <td align="right" width="12%">实施周期：</td>
          <td align="left"  width="38%"><input name="implementDays" type="text" class="noEmptyInput" value="<%=etsWorkorderTmp.getImplementDays()%>" style="border:solid 1px;border-color:#9FD6FF;width:50%"  >（天）</td>
        </tr>
        <tr>
           <td align="right" width="12%">接单部门：</td>
           <td align="left"  width="38%">
                <input type="hidden" name="organizationId" id="organizationId" value="<%=etsWorkorderTmp.getOrganizationId()%>">
                <input type="hidden" name="groupId" value="<%=etsWorkorderTmp.getGroupId()%>">
                <input class="readonlyInput"   name="groupName" type="text"  value="<%=etsWorkorderTmp.getGroupName()%>" readonly="true" style="width:70%" style="border:none;background-color:#F2F9FF;width:100%">
            </td>
            <td align="right" width="12%">执 行 人：</td>
            <td align="left"  width="38%">
                <select class ="box2"  name="implementBy" style="border:solid 1px;border-color:#9FD6FF;font-family:Verdana,Arial; font-size: 9pt; color: #FF0000; width:50%" ><%=userOtionHtml%></select>
            </td>
        </tr>
    </table>
        </td>
    </tr>
</table>
<%--<%if(isFirstNode){%>--%>
    <!--<img src="/images/eam_images/save.jpg" alt="保存" onclick="do_save();">-->
<%--<%}%>--%>
    <!--<img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); ">-->
<%if (!etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_NEW)) {
       if (schemeRowSet != null && !schemeRowSet.isEmpty()) {
         Row schemeRow = null;
%>
<fieldset style="position:absolute;left:1px;top:138px;width:100%;height:270px">
   <legend align="left">
    <font size="2" color="blue">当前配置信息 <%=etsWorkorderTmp.getAttribute5()%></font>
  </legend>

    <%--<div style="left:2px;width:100%;overflow-y:scroll" id="headDiv1" >--%>
        <%--<table class="headerTable" border=1 style="width:100%">--%>
            <%--<tr height=20px >--%>
                <%--<td width="45%" align ="center">设备名称 </td>--%>
                <%--<td width="45%" align ="center">规格型号</td>--%>
                <%--<td width="10%" align ="center" >数量</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>
    <script type="text/javascript">
            var columnArr = new Array("设备名称","规格型号","数量");
            var widthArr = new Array("45%","45%","10%");
            printTableHead(columnArr,widthArr);
        </script>

    <div style="left:0px;width:100%;overflow-y:scroll;position:absolute;height:76%">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="contentTab1">
<%


    for (int i = 0; i < schemeRowSet.getSize(); i++) {
        schemeRow = schemeRowSet.getRow(i);
 %>
            <tr style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="45%"><%=schemeRow.getStrValue("ITEM_NAME")%></td>
                <td width="45%"><%=schemeRow.getStrValue("ITEM_SPEC")%></td>
                <td width="10%"><%=schemeRow.getStrValue("ITEM_QTY")%></td>
            </tr>
<%
    }
%>
        </table>
    </div>
</fieldset>
<%
    }
    }
    if(showCurScheme){
%>
    <table><tr></tr></table>
     <fieldset  style="position:absolute;left:1px;width:100%;height:280px"
<%
    if(!etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_NEW)) {
       if (schemeRowSet != null && !schemeRowSet.isEmpty()) {
%>
       style="top:410px"
<%
    }
}else{
      System.out.println("111111111111");

%>
   style="position:absolute;top:138px;left:1px;width:100%;height:380px"
<%
    }
%>
              >
  <legend align="left">
    <%if(isFirstNode){%>
      <img src="/images/eam_images/choose.jpg" alt="选择" onClick="choose_Item(); return false;">
      <img src="/images/eam_images/delete_line.jpg" alt="删除选择行" onClick="remove_item(); return false;">
      <font size="2" color="blue">计划配置信息：<input style="border:solid 1px;border-color:#9FD6FF" name="attribute5" type="text" size="50"  value="<%=etsWorkorderTmp.getAttribute5()%>"></font>
      <%}%>
  </legend>

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll" id="headDiv">
    <table class="headerTable" border=1 style="width:100%" cellpadding="0" cellspacing="0">
        <tr height=20px >
            <td width="3%" align="center"><input class="headCheckbox" type="checkbox" name="checkCtl" onclick="checkAll(this.name,'ctl');" > </td>
            <td width="40%" align="center">设备名称 </td>
            <td width="40%" align="center" >规格型号</td>
            <td width="17%" align="center" >数量</td>
            <td style="display:none;" >&nbsp;</td>
        </tr>
    </table>
    </div>
    <div style="left:0px;width:100%;overflow-y:scroll;position:absolute;height:80% "  >
        <table width="100%" border="1" bordercolor="#9FD6FF" id="contentTab">
            <tr style="display:none">
                <td width="3%" align="center"><input type="checkbox" name="ctl" id="ctl0"> </td>
                <td width="40%"><input type="text" readonly class="noneinput" style="width:80%" name="itemName" id="itemName0" value=""></td>
                <td width="40%"><input type="text" readonly class="noneinput" style="width:80%" name="itemSpec" id="itemSpec0" value=""></td>
                <td width="17%"><input type="text" class="finput" name="itemQty" id="itemQty0" value="0"></td>
                <td style="display:none;"><input type="hidden" name="itemCode" id="itemCode0"></td>
            </tr>
<%
    if (rowSet != null && !rowSet.isEmpty()) {
        Row row = null;
        for (int i = 0; i < rowSet.getSize(); i++) {
            row = rowSet.getRow(i);
%>
            <tr>
                <td width="3%" align="center"><input type="checkbox" name="ctl" id="ctl<%=i%>"></td>
                <td width="40%"><input type="text" readonly class="noneinput" style="width:80%" name="itemName" id="itemName<%=i%>" value="<%=row.getStrValue("ITEM_NAME")%>"></td>
                <td width="40%"><input type="text" readonly class="noneinput" style="width:80%" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getStrValue("ITEM_SPEC")%>"></td>
                <td width="17%"><input type="text" class="finput" name="itemQty" id="itemQty<%=i%>" value="<%=row.getStrValue("ITEM_QTY")%>"></td>
                <td style="display:none;"><input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getStrValue("ITEM_CODE")%>" class="noEmptyInput"></td>
            </tr>
<%
        }
    }
%>
        </table>
    </div>
</fieldset>
<%
    }
%>

</form>

</body>
</html>
<script type="text/javascript">
    function choose_Item(){
       var lookUpName = "<%=LookUpConstant.LOOK_UP_SYSITEM%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var retValue = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
		if(retValue){
			var btsObj = null;
			var tab = document.getElementById("contentTab");
			for(var i = 0; i < retValue.length; i++){
				btsObj = retValue[i];

                appendDTORow(tab, btsObj);
			}
		}
    }

    function remove_item(){
        var tab = document.getElementById("contentTab");
        deleteTableRow(tab,"ctl");
    }

    function do_save(){
        if (<%=etsWorkorderTmp.getWorkorderType().equals(DictConstant.ORDER_TYPE_TRANS)%>) {
            if (document.mainFrm.transObjectCode.value == "") {
                alert("搬迁地点不能为空！")
                return;
            }
        }

        var fieldNames = "implementDays";
        var fieldLabels = "实施周期";
        var emptyValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        var numberValid = formValidate(fieldNames, fieldLabels, NUMBER_VALIDATE);
    <%if(showCurScheme){%>
        emptyValid = emptyValid & formValidate("itemQty", "数量", EMPTY_VALIDATE);
        numberValid = numberValid & formValidate("itemQty", "数量", NUMBER_VALIDATE);
    <%}%>
        var isValid=emptyValid&&numberValid;
        if (isValid) {
            document.mainFrm.submit();
        } else {
            return;
        }
    }

 function SelectsSitue()  {
    var lookUpName = "<%=LookUpConstant.LOOKE_UP_PORTAGE%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
        dto2Frm(projects[0], "mainFrm");
    }
 }


</script>
