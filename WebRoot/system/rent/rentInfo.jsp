<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.rent.dto.RentDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>

<%--
  Created by IntelliJ IDEA.
  Author: 李轶
  Date: 2009-05-14
  Time: 09:50:28
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>租赁信息维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<%
    RentDTO rentDTO = (RentDTO) request.getAttribute(WebAttrConstant.RENT_DTO);
    SfUserDTO sfUser = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<script type="text/javascript">
    printTitleBar("租赁信息维护")
</script>
<body leftmargin="0" >
<jsp:include page="/message/MessageProcess"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="isExist">
    
    <table width="60%" align="center" border= "0">
        <tr>
        <td align="center" colspan="2"  width="45%" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该条码已存在！</td>
                </tr>
        <tr>
            <td width="8%" align="right">条码：</td>
            <td width="40%"><input name="barcode"  type="text" id="barcode" value="<%=rentDTO.getBarcode()%>" class='noEmptyInput'
            <% if(StrUtil.isEmpty(rentDTO.getBarcode())){%>
               onblur="do_verifybarcodeNo();"
              <%} else{%>
              readonly
             <%}%>         
                                   style="width:80%">
            </td>
        </tr >
        <tr>
            <td align="right">名称：</td>
            <td><input name="itemName" type="text" readonly id="itemName" value="<%=rentDTO.getItemName()%>" class='noEmptyInput'
                       style="width:80%"><a href="#" class= "linka" onclick="selectSysitem();">
  <%-- <% if(StrUtil.isEmpty(rentDTO.getRentId())){%>
                […]
    <%}%>--%>
            </a></td>
        </tr>

        <tr>
            <td align="right">型号：</td>
            <td><input name="itemSpec" type="text" readonly id="itemSpec" value="<%=rentDTO.getItemSpec()%>" style="width:80%"
                       class="noEmptyInput"></td>
        </tr>

        <tr>
            <td align="right" width="15%">责任部门:</td>
            <td width="35%"><input name="responsibilityDept" type="text" class="noEmptyInput"  readonly alert = "点击选择责任部门"  onclick="do_selectDept();"
                                             value="<%=rentDTO.getResponsibilityDept()%>" style="width:80%"><a class="linka" style="cursor:'hand'" alert = "点击选择责任部门"  onclick="do_selectDept();">[…]</a></td>
        </tr>
        <tr>
            <td align="right" width="15%">责任人:</td>
            <td width="35%"><input name="username" type="text" class="noEmptyInput" readonly value="<%=rentDTO.getUsername()%>"  style="width:80%"> </td>
            </tr>
         <tr>
            <td align="right" width="15%">使用地点:</td>
            <td width="35%"><input name="addressloc" type="text" class="noEmptyInput"  readonly onclick="lookAddressId();" alert = "点击选择使用地点"
                                             value="<%=rentDTO.getAddressloc()%>" style="width:80%"><a class="linka" style="cursor:'hand'" onclick="lookAddressId();" alert = "点击选择使用地点">[…]</a></td>
        </tr>
        <tr>
            <td align="right" width="15%">使用部门:</td>
            <td width="35%"><input name="maintainDeptName" type="text"   readonly readonly onclick="lookDept();" alert = "选择使用部门"
                                             value="<%=rentDTO.getMaintainDeptName()%>" style="width:80%"><a class="linka" style="cursor:'hand'" alert = "选择使用部门" onclick="lookDept();">[…]</a></td>
        </tr>
        <tr>
            <td align="right" width="15%">使用人:</td>
            <td width="35%"><input name="maintainUser" type="text" value="<%=rentDTO.getMaintainUser()%>" style="width:80%"></td>
        </tr>
         <input type = "hidden" name = "historyId" value="<%=rentDTO.getHistoryId()%>">
         <tr>
            <td align="right">签约单位：</td>
            <td>
                <input name="rentPerson"   style="width:80%" value="<%=rentDTO.getRentPerson()%>" onblur="do_verify1();">
            </td>
        </tr>
        <tr>
            <td align="right">租赁日期：</td>
            <td>
                <input class='noEmptyInput' readonly name="rentDate" alert="点击选择起始日期" onclick="gfPop.fPopCalendar(rentDate)" style="width:80%" value="<%=rentDTO.getRentDate()%>"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(rentDate)">
            </td>
        </tr>
        <tr>
            <td align="right">租赁截止日期：</td>
            <td>
                <input name="endDate" readonly class='noEmptyInput'  type="text" id="endDate" value="<%=rentDTO.getEndDate()%>" alt="点击选择结束日期" onclick="gfPop.fEndPop(rentDate,endDate);"
                       style="width:80%"><img src="/images/calendar.gif" alt="点击选择结束日期" onclick="gfPop.fEndPop(rentDate,endDate);">
            </td>
        </tr>
        <tr>
            <td align="right">总租金：</td>
            <td><input name="rental" type="text"  id="rental" value="<%=rentDTO.getRental()%>" style="width:80%"   onblur="do_verifyTen()"
                       ></td>
        </tr>
        <tr>
            <td align="right">租期(年)：</td>
            <td><input name="tenancy" type="text"  id="tenancy" value="<%=rentDTO.getTenancy()%>" style="width:80%"   onblur="do_verifyTen()"
                       ></td>
        </tr>

        <tr>
            <td align="right">年租金(元)：</td>
            <td><input name="yearRental" type="text"  id="yearRental" value="<%=rentDTO.getYearRental()%>" style="width:80%"   onblur="do_verifyYear()"
                      ></td>
        </tr>

        <tr>
            <td align="right">月租金(元)：</td>
            <td><input name="monthReantal" type="text"  id="monthReantal" value="<%=rentDTO.getMonthReantal()%>" style="width:80%"  onblur="do_verifyMonth()"
                      ></td>
        </tr>
        <tr>
            <td align="right" width="15%">备 注:</td>
            <td width="35%"><textarea name="remark" rows = "3" onblur="do_verify4();"  style="width:80%"><%=rentDTO.getRemark()%> </textarea> </td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" style="cursor:'hand'" onClick="do_submit();">&nbsp;&nbsp;
                <img border="0" src="/images/eam_images/change_history.jpg" title="点击查看租赁更改历史" onclick="do_ShowHistory()">&nbsp;&nbsp;
                <img src="/images/eam_images/back.jpg" onClick="do_back();" alt="返回">
            </td>
        </tr>
    </table>
<input type="hidden" name = "itemCode">
<input type="hidden" name ="rentId" value="<%=rentDTO.getRentId()%>">
<input type="hidden" name="userId" value="<%=rentDTO.getUserId()%>">
<input type="hidden" name="addressId" value="<%=rentDTO.getAddressId()%>">
<input type="hidden" name ="maintainDept" value="<%=rentDTO.getMaintainDept()%>">
<input type="hidden" name ="deptId" value="<%=rentDTO.getDeptId()%>">
    <input type="hidden" name ="systemId" value="<%=rentDTO.getSystemId()%>">

</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_back() {
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>" ;
        mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet";
        mainFrm.submit();
    }

    function do_submit() {
        var fieldNames = "barcode;itemName;itemSpec;rentDate;addressloc;responsibilityDept;username;endDate";
        var fieldLabels = "条码;名称;型号;租赁日期;使用地点;责任部门;责任人;租赁截止日期";
        if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
            with (mainFrm) {
            	var rentDateInput = mainFrm.rentDate.value; 
            	var endDateInput = mainFrm.endDate.value;
            	var tenancyInput = mainFrm.tenancy.value;    
            	var yearRentalInput = mainFrm.yearRental.value;
            	var monthReantalInput = mainFrm.monthReantal.value;
            	var rentPersonInput = mainFrm.rentPerson.value;
<%--            	var rentalInput = mainFrm.rental.value;--%>
            	var remarkInput = mainFrm.remark.value;
            	if ("<%=rentDTO.getRentDate() %>" == rentDateInput && "<%=rentDTO.getEndDate()%>" == endDateInput && "<%=rentDTO.getRentPerson()%>" == rentPersonInput
            		&& "<%=rentDTO.getTenancy()%>" == tenancyInput && "<%=rentDTO.getYearRental()%>" == yearRentalInput && "<%=rentDTO.getMonthReantal()%>" == monthReantalInput){
            		act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            	} else {
            		act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            	}            	
                action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet";
                submit();
            }
   	 	}
   }
  
    function isZLBar() {
        var str = document.mainFrm.barcode.value;
        var patrn = /^(\d{4}\-ZL)?\d{6}$/;
        if (patrn.exec(str)) {
            return true;
        } else{
            alert("请输入正确的条码！——四位公司代码+‘-ZL’+六位数字，例如：“4110-ZL123456”。");
            document.mainFrm.barcode.focus();
            return false;
        }
    }

    function choosePrj() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BARCODENO%>";
        var dialogWidth = 50.6;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            dto2Frm(projects[0], "mainFrm");
        }
        //    if (projects) {
        //            var project = null;
        //            for (var i = 0; i < projects.length; i++) {
        //                project = projects[i];
        //                dto2Frm(project, "mainFrm");
        //            }
        //        }
    }


    function do_delete() {
        document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet";
        document.mainFrm.submit();
    }

    function do_efficient() {
        document.mainFrm.act.value = "<%=AMSActionConstant.INURE_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet";
        document.mainFrm.submit();
    }


	function lookDept() {          //查找使用部门
	    <%--var lookUpName = "<%=LookUpConstant.LOOK_UP_DEPT%>";--%>
	    var lookUpName = "<%=LookUpConstant.LOOK_MAINTAIN_DEPT%>";
	    var dialogWidth = 48;
	    var dialogHeight = 30;
	    var userPara = "organizationId=<%=sfUser.getOrganizationId()%>";
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight,userPara);
	    if(projects){
	           var user = null;
	            for (var i = 0; i < projects.length; i++) {
	                mainFrm.maintainDept.value = projects[0].groupId;
	                mainFrm.maintainDeptName.value = projects[0].groupName;
	            }
	    }
	//    alert(mainForm.vendorCode.value);
	 }

 	 function selectSysitem() {   //查找名称型号
        //alert(mainForm.itemCategory.value);
        var lookUpName = "<%=LookUpConstant.LOOK_UP_INSTR_ITEM%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
//        var userPara = "itemCategory=" + mainForm.itemCategory.value;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
     }

	 function do_selectDept(){   //查找mis的责任人和责任部门
	    <%--var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY%>";--%>
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_MIS_INFO%>";
	    var dialogWidth = 48;
	    var dialogHeight = 30;
	    //LOOKUP传参数 必须和DTO中一致
	    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if (users) {
	        var user = null;
	        for (var i = 0; i < users.length; i++) {
	            user = users[i];
	            document.mainFrm.userId.value =  user.employeeId;
	            document.mainFrm.username.value = user.userName;
	            document.mainFrm.deptId.value = user.deptCode;
	            document.mainFrm.responsibilityDept.value = user.deptName;
	//            dto2Frm(user, "mainFrm");
	        }
	    }
	}


	var xmlHttp;
	function do_verifybarcodeNo() {
	//    do_verify9()
	//    do_verifyLenght()
	    var url = "";
	    var segment1 = document.mainFrm.barcode.value;
	    createXMLHttpRequest();
	    if (document.mainFrm.barcode.value) {
	        url = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet?act=verifyBarcode&barcode=" + document.mainFrm.barcode.value;
	        xmlHttp.onreadystatechange = handleReadyStateChange1;
	        xmlHttp.open("post", url, true);
	        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	        xmlHttp.send(null);
	    }
	}
	
	function do_verify1(){
	    var fieldNames = "rentPerson";
	    var fieldLabels = "0$出租人$200";
	    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
	    }
	}
	function do_verify2(){
	    var fieldNames = "remark";
	    var fieldLabels = "0$备注$255";
	    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
	    }
	}

	function do_verifyLenght(){
        var Names = "barcode1";
        var Labels = "13$设备条码$13";
        if (!formValidate(Names, Labels, LENGTH_VALIDATE)) {
    	}
  	}

    function do_verifyTen() {
        var Names = "tenancy";
        var Labels = "租期";
        if (!formValidate(Names, Labels, POSITIVE_VALIDATE)) {
        }
    }


    function do_verifyYear() {
        var Names = "yearRental";
        var Labels = "年租金";
        if (!formValidate(Names, Labels, POSITIVE_VALIDATE)) {
        }
    }


    function do_verifyMonth() {
        var Names = "monthReantal";
        var Labels = "月租金";
        if (!formValidate(Names, Labels, POSITIVE_VALIDATE)) {
        }
    }


    function createXMLHttpRequest() {     //创建XMLHttpRequest对象
	    try {
	        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
	    } catch(e) {
	        try {
	            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
	        } catch(e) {
	            try {
	                xmlHttp = new XMLHttpRequest();
	            } catch(e) {
	                alert("创建XMLHttpRequest对象失败！");
	            }
	        }
	    }
	}

	function handleReadyStateChange1() {
	    if (xmlHttp.readyState == 4) {
	        if (xmlHttp.status == 200) {
	            if (xmlHttp.responseText == 'Y') {
	                document.mainFrm.barcode.style.color = "red";
	                document.getElementById("barcodeNo11").style.visibility = "visible"
	                document.mainFrm.barcode.focus();
	            } else {
	                document.mainFrm.barcode.style.color = "black";
	                document.getElementById("barcodeNo11").style.visibility = "hidden";
	            }
	        } else {
	            alert(xmlHttp.status);
	        }
	    }
	}


    function isBar() {
        var str = document.mainFrm.barcode.value;
        var patrn = /^(\d{4}\-ZL)?\d{6}$/;
        if (patrn.exec(str)) {
//            alert("222");
            return true;
        } else{
            alert("请输入正确的条码！——四位公司代码+‘-ZL’+六位数字，例如：“4110-ZL123456”。");
//            document.mainFrm.barcode.focus();
            return false;
        }
    }


	function lookAddressId() {    //查找地点
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
	    var dialogWidth = 48;
	    var dialogHeight = 30;
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if(projects){
	           var user = null;
	            for (var i = 0; i < projects.length; i++) {
	                mainFrm.addressId.value = projects[0].addressId;
	                mainFrm.addressloc.value = projects[0].workorderObjectName;
	            }
	    }
	//    alert(mainForm.vendorCode.value);
	}
	
	function do_ShowHistory(){
		var url = "/servlet/com.sino.ams.system.rent.servlet.RentServlet?act=" + "<%=WebActionConstant.TREE_ACTION %>" + "&barcode=<%=rentDTO.getBarcode()%>";
		var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
		window.open(url, 'historyWin', style);
	}
</script>