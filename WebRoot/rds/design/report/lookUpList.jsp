<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.form.LookUpDefineFrm" %>
<html>
<head>
    <title>LookUp查询维护</title>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<html:form action="/rds/lookUpProcess" method="post">
    <html:hidden property="act"/>
    <html:hidden property="lookUpIds" styleId="lookUpIds"/>
    </html:form>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:0px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center;cursor:pointer" onclick="executeClick(this)">

                <td width="10%">查询代码</td>
                <td width="10%">查询名称</td>
                <td width="18%">来源报表</td>
                <td width="14%">页面标题</td>

                <td width="10%">返回字段</td>
                <td width="15%">其他返回值</td>
                <td width="10%">是否有效</td>
                <td width="10%">创建日期</td>
            </tr>
        </table>
    </div>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:23px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            LookUpDefineFrm frm = (LookUpDefineFrm)dtos.getDTO(i);
%>
            <tr id="<%=frm.getLookUpId()%>"  onclick="do_ViewLookUpDetail(this)" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" class="dataTR">

                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getLookUpCode()%>"></td>
                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getLookUpName()%>"></td>
                <td width="18%"><input type="text" readonly class="tableInput1" value="<%=frm.getReportName()%>"></td>
                <td width="14%"><input type="text" readonly class="tableInput1" value="<%=frm.getLookUpTitle()%>"></td>

                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getReturnFieldName()%>"></td>
                <td width="15%"><input type="text" readonly class="tableInput2" value="<%=frm.getOtherReturnValue()%>"></td>
                <td width="10%"><input type="text" readonly class="tableInput2" value="<%=frm.getEnabledName()%>"></td>
                <td width="10%"><input type="text" readonly class="tableInput2" value="<%=frm.getCreationDate()%>"></td>
            </tr>
<%
        }
%>
        </table>
       </div>
<%
    }
%>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>
<script type="text/javascript">

function do_ViewLookUpDetail(obj){
    var actionURL = "/rds/lookUpProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&lookUpId=" + obj.id;
    window.parent.frames["lookUpDataFrm"].location = actionURL;
}

function do_Check_Enable(){
    var isValid = false;
    var modelIds = getCheckBoxValue("subCheck", ",");
    if(modelIds == ""){
        alert("未选中数据记录，不能执行该操作。");
    }  else {
        document.getElementById("lovIds").value = modelIds;
        isValid = true;
    }
    return isValid;
}

function do_Check_Disable(){
    return do_Check_Enable();
}


function do_ProcessButton(obj){
    var disableBtn = document.getElementById("disableBtn");
    var enableBtn = document.getElementById("enableBtn");
    var tr = obj;
    while(tr.tagName != "TR"){
        tr = tr.parentNode;
    }
    var cell = tr.cells[tr.cells.length - 2];
    var enabledName = cell.childNodes[0].value;
    if(obj.checked){
        if(enabledName == "是"){
            disableBtn.disabled = false;
            enableBtn.disabled = true;
        } else {
            disableBtn.disabled = true;
            enableBtn.disabled = false;
        }
    } else {
        disableBtn.disabled = false;
        enableBtn.disabled = false;
    }
}
</script>
