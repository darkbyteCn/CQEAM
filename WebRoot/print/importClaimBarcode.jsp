<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>

<%@ page import="java.util.List"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "No-cache");
%>
<html>
<base target="_self">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Excel标签号领用导入</title>
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "提交", "action_save.gif", "上传", "doSub");
        var ArrAction1 = new Array(true, "粘贴", "action_sign.gif", "粘贴", "aa");
        var ArrActions = new Array(ArrAction0, ArrAction1);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<form name="mainFrm" action="/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=UPLOAD_ACTION" method="post" enctype="multipart/form-data">
    <input type="hidden" name="flag" value="0">
    <input type="hidden" name="act">
    <script type="text/javascript">
        printTitleBar("Excel标签号领用导入")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table border = "0" width="100%">
        <tr>
            <td width="80%">
            <td width="20%">
            <td></td>
        </tr>
        <tr>
            <td width="80%">
            <input type="file" name="flName" class="select_style1" style="width:45%"><input type="button" name="sub" value="提交Excel" onclick="doSub();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img src="/images/right-3.jpg" width="5" height="9"/>
            <a href="/document/template/ClaimBarcodeImportTem.zip" onclick="window.open('/document/template/ClaimBarcodeImportTem.zip');" style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">Excel标签号导入模版</FONT></a><td width="20%">
            <!-- <td><img src="/images/eam_images/ok.jpg" alt="确定" onClick="do_TransData();">&nbsp;<img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();"></td> -->
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    function doSub() {
    	var excelName = document.mainFrm.flName.value;
    	if (excelName.indexOf('.xls') == -1) {
    		alert("请确保您导入的文件格式为xls！");
    		return;
    	}
        if (document.mainFrm.flag.value == "1") {
            alert("正在提交数据，请等待......");
            return;
        }
        if (document.mainFrm.flName.value !== "") {
            document.mainFrm.flag.value="1";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            mainFrm.action = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=IMP_BARCODE_ACTION";
            mainFrm.submit();
        } else {
            alert("请输入文件！");
        }
    }
    
    function do_TransData(){
			window.returnValue = getDTOArr();
			self.close();
		
	}
	
	function do_CheckData(box){
		trObj = box.parentNode.parentNode;
		chkObj = trObj.cells[0].childNodes[0];
		if(chkObj){
			chkObj.click();
		}
	}
	function DTO(checkLocation, objectCode, objectName, objectLocation){
		this.checkLocation = checkLocation;
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectLocation = objectLocation;
	}
	function getDTOArr() {
    var DTOArr = null;
    var count = getCheckBoxCount("subCheck");
    var len=getCheckedBoxCount("subCheck");
    DTOArr = new Array(count);
    for (var k = 0; k < count; k++) {
        var chkbox = document.getElementById("subCheck" + k);
        if (chkbox.checked) {
            var transValue = chkbox.value;
                var tmpArr = null;
                    tmpArr = transValue.split("@@@");
                    dto = new DTO();
                    
<!--                    var j = 0;-->
<!--                    for (prop in dto) {-->
<!--                        if (tmpArr[j] == "EMPTY_CONTENT") {-->
<!--                            tmpArr[j] = "";-->
<!--                        }-->
<!--                        dto[prop] = tmpArr[j];-->
<!--                        j++;-->
<!--                    }-->
					dto["checkLocation"]=tmpArr[0];
					dto["objectCode"]=tmpArr[1];
					dto["objectName"]=tmpArr[2];
					dto["objectLocation"]=tmpArr[3];
                    DTOArr[k] = dto;
        }
    }
    return DTOArr;
	}

</script>
</html>