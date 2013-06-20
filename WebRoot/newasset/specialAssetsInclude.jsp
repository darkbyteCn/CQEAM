<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ page import="com.sino.ams.newasset.dto.AmsSpecialAssetsDTO" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String transStatus = headerDTO.getTransStatus();
    String name = "添加附件";
//    String name = "";
//    if (transStatus.equals("")||transStatus.equals("SAVE_TEMP") || transStatus.equals("REJECTED")){
//        name="添加附件" ;
//    }else{
//       name="查看附件" ;
//
//    }
%>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<table border="1"  bgcolor="#E9EAE9" class=table2 cellpadding=0 cellspacing=0 style="position:relative;top:auto;left:1px;width:100%;">
    <!--<tr height="25">-->
        <!--<td width=16% rowspan=2 align=ceter>附件列表</td>-->
        <!--<td width=47% align=center class=td2>一般附件</td>-->
        <!--<td width=47% align=center class=td2>扫描件</td>-->
    <!--</tr>-->
    <!--<tr height="22">-->
        <%--<td><input type="button" name="" value="<%=name%>" class=button2 onclick="addFile1();"></td>--%>
        <%--<td><input type="button" name="" value="<%=name%>" class=button2 onclick="addFile2();"></td>--%>
    <!--</tr>-->
    <tr height="25">
        <td width=16% rowspan=2 align=ceter>附件</td>
    </tr>
    <tr height="22">
        <td><input type="button" name="" value="<%=name%>" class=button2 onclick="addFile1();"></td>
    </tr>
    <input type="hidden" name="transStatus" value="<%=headerDTO.getTransStatus()%>">
</table>
<script type="text/javascript">
    var fileType;
    var transId;
    var transStatus=document.mainFrm.transStatus.value;
    var dType = "<%=AssetsDictConstant.DOC_TYPE%>";
    var transType;
    //一般附件
    function addFile1() {
        transId = document.mainFrm.transId.value;
        fileType = "<%=AssetsDictConstant.FILE_TYPE_OTHERS%>";
        if (transId == '') {
            requestAjax("GET_HEADER_ID", getId, null, null);
        } else {
            openFilePage();
        }
    }
    //扫描件
    function addFile2() {
        transId = document.mainFrm.transId.value;
        fileType = "<%=AssetsDictConstant.FILE_TYPE_SCANNED%>";
        if (transId == '') {
            requestAjax("GET_HEADER_ID", getId, null, null);
        } else {
            openFilePage();
        }
    }

    function getId() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '') {
                transId = ret;
                document.mainFrm.transId.value = ret;
                openFilePage();
            }
        }
    }
    //打开页面
    function openFilePage() {
          transType=mainFrm.transType.value;
        var url = "/servlet/com.sino.ams.newasset.servlet.FileServlet?transId=" +
                  transId + "&fileType=" + fileType + "&docType=" + dType+"&transType="+transType+"&transStatus="+transStatus;
        var style = "width=620px,height=380,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no";
        window.open(url, "pickMtlWin", style);
    }
</script>