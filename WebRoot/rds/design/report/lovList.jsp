<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>
<%@ page import="com.sino.rds.share.form.LovDefineFrm" %>

<html>
<head>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<html:form action="/rds/lovProcess" method="post">
    <html:hidden property="act"/>
    <html:hidden property="lovIds" styleId="lovIds"/>
    </html:form>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:0px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center;cursor:pointer" title="点击对全选进行控制" onclick="executeClick(this)">

                <td width="10%">列表名称</td>
                <td width="10%">列表类型</td>
                <td width="47%">数据来源</td>

                <td width="10%">添加空选项</td>
                <td width="10%">数据源</td>
                <td width="10%">创建日期</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:98px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" class="gridTable" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        String dataSource = "";
        for (int i = 0; i < dtos.getSize(); i++) {
            LovDefineFrm frm = (LovDefineFrm)dtos.getDTO(i);
            if(frm.getLovType().equals(RDSDictionaryList.LOV_TYPE_SQL)){
                dataSource = frm.getLovSql();
            } else {
                dataSource = frm.getLovValue();
            }
%>
            <tr id="<%=frm.getLovId()%>" onclick="do_ViewLovDetail(this)" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" class="dataTR">

                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getLovName()%>"></td>
                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getLovTypeName()%>"></td>
                <td width="47%"><input type="text" readonly class="tableInput1" value="<%=dataSource%>"></td>

                <td width="10%"><input type="text" readonly class="tableInput2" value="<%=frm.getAddBlankName()%>"></td>
                <td width="10%"><input type="text" readonly class="tableInput1" value="<%=frm.getConnectionName()%>"></td>
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
function do_ViewLovDetail(obj){
    var actionURL = "/rds/lovProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&lovId=" + obj.id;
    window.parent.frames["lovDataFrm"].location = actionURL;
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
