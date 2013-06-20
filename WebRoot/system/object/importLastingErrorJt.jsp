<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-5-26
  Time: 11:35:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>租赁资产导入错误信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.ETS_SPARE_DTO);
    Row row = null;
//    String qryType = request.getParameter("qryType");
%>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.object.servlet.ImportLastingAssetsServletJt">
    <input type="hidden" name="act" value="QUERY_ACTION">
    <script type="text/javascript">
      printTitleBar("租赁资产导入错误信息")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <img src="/images/eam_images/export.jpg" id="exportImg" style="cursor:pointer" onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:50px;left:1px;width:100%">
        <table width="400%" class="headerTable" border="1">
            <tr>
                <td  height="24px"width="6%" align="center">错误信息</td>
                <td  height="24px"width="3%" align="center">公司代码</td>
                <td  height="24px"width="3%" align="center">租赁资产标签号</td>
                <td  height="24px"width="5%" align="center">资产名称</td>
                <td  height="24px"width="5%" align="center">规格型号</td>
                <td  height="24px"width="3%" align="center">责任人编号</td>
                <td  height="24px"width="3%" align="center">责任人姓名</td>
                <td  height="24px"width="6%" align="center">地点组合编码</td>
                <td  height="24px"width="3%" align="center">数量</td>
                <td  height="24px"width="3%" align="center">厂商</td>
                <td  height="24px"width="3%" align="center">额定功率</td>
                <td  height="24px"width="3%" align="center">设备性能</td>
                <td  height="24px"width="4%" align="center">资产类别代码组合</td>
                <td  height="24px"width="4%" align="center">资产类别描述</td>
                <td  height="24px"width="6%" align="center">资产地点描述</td>
                <td  height="24px"width="3%" align="center">实物部门代码</td>
                <td  height="24px"width="3%" align="center">使用人姓名</td>
                <td  height="24px"width="3%" align="center">使用部门代码</td>
                <td  height="24px"width="3%" align="center">起始日期</td>
                <td  height="24px"width="3%" align="center">终止日期</td>
                <td  height="24px"width="3%" align="center">签约单位</td>
                <td  height="24px"width="3%" align="center">租期</td>
                <td  height="24px"width="3%" align="center">年租金</td>
                <td  height="24px"width="3%" align="center">月租金</td>
                <td  height="24px"width="6%" align="center">备注</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:450px;width:100%;position:absolute;top:73px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="400%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:pointer" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="6%" align="left"><%=row.getValue("ERROR")%></td>
                <td width="3%" align="left"><%=row.getValue("COMPANY_CODE")%></td>
                <td width="3%" align="left"><%=row.getValue("BARCODE")%></td>
                <td width="5%" align="left"><%=row.getValue("ITEM_NAME")%></td>
                <td width="5%" align="left"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="3%" align="left"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
                <td width="3%" align="left"><%=row.getValue("EMPLOYEE_NAME")%></td>
                <td width="6%" align="left"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td width="3%" align="left"><%=row.getValue("ITEM_QTY")%></td>
                <td width="3%" align="left"><%=row.getValue("MANUFACTURER_ID")%></td>
                <td width="3%" align="left"><%=row.getValue("POWER")%></td>
                <td width="3%" align="left"><%=row.getValue("EQUIPMENT_PERFORMANCE")%></td>
                <td width="4%" align="left"><%=row.getValue("CONTENT_CODE")%></td>
                <td width="4%" align="left"><%=row.getValue("CONTENT_NAME")%></td>
                <td width="6%" align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td width="3%" align="left"><%=row.getValue("SPECIALITY_DEPT")%></td>
                <td width="3%" align="left"><%=row.getValue("MAINTAIN_USER")%></td>
                <td width="3%" align="left"><%=row.getValue("MAINTAIN_DEPT")%></td>
                <td width="3%" align="left"><%=row.getValue("START_DATE")%></td>
                <td width="3%" align="left"><%=row.getValue("END_DATE")%></td>
                <td width="3%" align="left"><%=row.getValue("RENT_PERSON")%></td>
                <td width="3%" align="left"><%=row.getValue("TENANCY")%></td>
                <td width="3%" align="left"><%=row.getValue("YEAR_RENTAL")%></td>
                <td width="3%" align="left"><%=row.getValue("MONTH_REANTAL")%></td>
                <td width="6%" align="left"><%=row.getValue("REMARK")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%--<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>--%>
</body>
</html>
<script type="text/javascript">
    function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    function do_concel() {
        mainFrm.action = "/system/object/importLastingAssetsJt.jsp";
        mainFrm.submit();
    }

    /**
     * 功能：设置页面表格的宽度。例如查询页面的使用
     * 该方法可使页面宽度根据屏幕分辨率自行调整
     * 要求：页面需要有headDiv和dataDiv两个层
     */
    function do_SetPageWidth() {
        var bodyWidth = document.body.clientWidth;
        var bodyHeight = document.body.clientHeight;
        var searchDiv = document.getElementById("searchDiv");
        var headDiv = document.getElementById("headDiv");
        var dataDiv = document.getElementById("dataDiv");
        var pageNaviDiv = document.getElementById("pageNaviDiv");
        var headTable = document.getElementById("headTable");
        if (headDiv) {
            headDiv.style.width = bodyWidth;
        }
        if (dataDiv != null && !dataDiv != undefined) {
            dataDiv.style.width = bodyWidth;
            if(headTable != null){
                dataDiv.style.top = headDiv.offsetTop + headTable.offsetHeight;
            }
            var dataTop = dataDiv.style.top.toLowerCase();
            var dataHeight = dataDiv.style.height.toLowerCase();
            if(dataTop.indexOf("px") > -1){
                dataTop = Number(dataTop.substring(0, dataTop.length - 2));
            } else if(dataTop == ""){
                dataTop = Number(dataDiv.offsetTop);
            }
            if(dataHeight.indexOf("px") > -1){
                dataHeight = Number(dataHeight.substring(0, dataHeight.length - 2));
            } else if(dataHeight.indexOf("%") > -1){
                dataHeight = "0." + dataHeight.replace("%", "");
                dataHeight = Number(dataHeight) * bodyHeight;
            }
            if(searchDiv != null && !searchDiv != undefined){
                var searchHeight = searchDiv.style.height;
                var searchTop = searchDiv.style.top;
                if(searchHeight.indexOf("px") > -1){
                    searchHeight = Number(searchHeight.substring(0, searchHeight.length - 2));
                }
                if(searchTop.indexOf("px") > -1){
                    searchTop = Number(searchTop.substring(0, searchTop.length - 2));
                }
                headDiv.style.top = (searchTop + searchHeight + 3);
            }
            dataHeight = bodyHeight - dataTop;
            dataDiv.style.height = dataHeight;
            if(pageNaviDiv != null && !pageNaviDiv != undefined){
                dataDiv.style.height = dataHeight - 30;
            }
        }
        if(pageNaviDiv != null && !pageNaviDiv != undefined){
            pageNaviDiv.style.width = bodyWidth;
            pageNaviDiv.style.position = "absolute";
            pageNaviDiv.style.top = bodyHeight - 25;
        }
    }

    /**
     * 功能：当wincodw窗口发生大小变动的时候，自行执行上述方法
     */
    window["onresize"] = function() {
        do_SetPageWidth();
    };

</script>