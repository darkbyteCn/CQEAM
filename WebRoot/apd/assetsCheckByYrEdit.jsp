<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.apd.dto.AmsAssetsCheckByYrHeaderDTO"%>
<%@ page import="com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
	<head>
		<%--
  Function:		创建盘点任务
--%>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<script language="javascript" src="/WebLibary/js/jslib.js"></script>
	    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
	     <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
	    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
	    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
	    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/help.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/util.js"></script>

		<%
			RequestParser reqParser = new RequestParser();
			reqParser.transData(request);
			AmsAssetsCheckByYrHeaderDTO headerDTO = (AmsAssetsCheckByYrHeaderDTO) reqParser.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
		%>

	</head>

	<body leftmargin="0" topmargin="0"
		onkeydown="autoExeFunction('do_Search();')"
		onload="initPage();helpInit('4.4.4');">
		<input type="hidden" name="helpId" value="">
		<jsp:include page="/message/MessageProcess" />
		<jsp:include page="/public/exportMsg.jsp" />
		
		<script>
		 printTitleBar("省公司下发盘点任务");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.apd.servlet.AmsAssetsCheckByYrServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="organizationId" value="<%=headerDTO.getOrganizationId()%>">
  			<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
  			<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
			<table width="100%" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
				<tr>
					<td align=right width=13%>
						创建人：
					</td>
					<td width=37%>
						<input name="createdByName" class="input_style2" readonly
							style="width: 100%;" value="<%=headerDTO.getCreatedByName()%>">
					</td>

					<td align=right width=13%>
						任务类型：
					</td>
					<td width=37%>
						<input name="transTypeValue" class="input_style2" readonly
							style="width: 100%;" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
				</tr>

				<tr>
					<td width="10%" align="right">
						基准日期间 从：
					</td>
					<td width="90%">
						<input style="width: 100%" type="text" class="finputNoEmpty"
							name="startDate" value="<%=headerDTO.getStartDate()%>"
							title="点击选择起始日期" readonly
							onclick="gfPop.fStartPop(startDate, endDate)">
					</td>
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 100%" type="text" class="finputNoEmpty"
							name="endDate" value="<%=headerDTO.getEndDate()%>"
							title="点击选择截至日期" readonly
							onclick="gfPop.fEndPop(startDate, endDate)">
					</td>
				</tr>
				
				<tr>
					<td width="10%" align="right">
						执行期间 从：
					</td>
					<td width="90%">
						<input style="width: 100%" type="text" class="finputNoEmpty"
							name="assetsCreationDate" value="<%=headerDTO.getAssetsCreationDate()%>"
							title="点击选择起始日期" readonly
							onclick="gfPop.fStartPop(assetsCreationDate, assetsEndDate)">
					</td>
					
					
					
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 100%" type="text" class="finputNoEmpty"
							name="assetsEndDate" value="<%=headerDTO.getAssetsEndDate()%>"
							title="点击选择截至日期" readonly
							onclick="gfPop.fEndPop(assetsCreationDate, assetsEndDate)">
					</td>
				</tr>

			</table>
			
        <div id="buttonDiv" style="position:absolute;top:110px;left:1px;width:100%">
           <img src="/images/eam_images/choose.jpg" alt="创建盘点任务" onClick="do_create(); return false;">
           <img src="/images/eam_images/delete_line.jpg" alt="下发盘点任务" onClick="do_send(); return false;">
         
         </div>
         
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:130px;left:0px;width:100%">
		    <table class="eamHeaderTable" border="1" width="100%">
		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
		            <td align=center width="17%">公司编码</td>
		            <td align=center width="20%">公司名称</td>
		            <td align=center width="20%">任务接受人</td>
		            <td align=center width="20%">资产账簿编码</td>
		            <td align=center width="20%">资产账簿名称</td>
					<td style="display:none">隐藏域字段</td>
		        </tr>
		    </table>
		</div>
         
         
      <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
      %>
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:141px;left:0px;height:250px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" style="table-layout:fixed;">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    String iStr = String.valueOf(i);
                    
                    String misProcureCodeL = "companyCode" + iStr;
                    String misProcureCodeV = row.getStrValue("COMPANY_CODE");//采购单号
                    
                    String barcodeL = "company" + iStr;
                    String barcodeV = row.getStrValue("COMPANY");//资产标签号
                    
                    String isReceivedL = "receivdBy" + iStr;
                    String isReceivedV =row.getStrValue("RECEIVD_BY");//
                    
                    String receivdByNameL="receivdByName"+iStr;
                    String receivdByNameV=row.getStrValue("RECEIVD_BY_NAME");
                   
                    String manufacturerNameL = "bookTypeCode" + iStr;
                    String manufacturerNameV = row.getStrValue("BOOK_TYPE_CODE"); // 
                    
                    String arrivalStatusL = "bookTypeName" + iStr;
                    String arrivalStatusV = row.getStrValue("BOOK_TYPE_NAME"); //报账状态
            %>
          
            <tr class="dataTR">
                 <td width="3%" align="left" id="disableTR<%=i%>">
			     <input type="checkbox" name="subCheck" > 
			     </td>
                <td width="17%" style="cursor:pointer" ><input type="text" name="companyCode" id="<%=misProcureCodeL%>" value="<%=misProcureCodeV%>" class="finput" readonly></td>
                <td width="20%" style="cursor:pointer" ><input type="text" name="company" id="<%=barcodeL%>" value="<%=barcodeV%>" class="finput" readonly></td>
                <td width="20%" style="cursor:pointer" ><input type="text" name="receivdByName" id="<%=receivdByNameL%>" value="<%=receivdByNameV%>" readonly class="finput" onClick="do_SelectPerson(this)" title="点击选择或更改接收人" style="cursor:pointer"></td>
                <td width="20%" style="cursor:pointer" ><input type="text" name="bookTypeCode" id="<%=manufacturerNameL%>" value="<%=manufacturerNameV%>" readonly class="finput"></td>
                <td width="20%" style="cursor:pointer" ><input type="text" name="bookTypeName" id="<%=arrivalStatusL%>" value="<%=arrivalStatusV%>" readonly class="finput"></td>
            </tr>
            
            <td style="display:none">
                <input type="hidden" name="receivdBy" id="<%=isReceivedL%>" value="<%=isReceivedV%>">
            </td>
            <%
                    }
                }
            %>
        </table>
    </div>
         
		</form>

	</body>
	<iframe width=174 height=189 name="gToday:normal:calendar.js"
		id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;">
	</iframe>
</html>

<script language="javascript">
	function initPage(){
		do_SetPageWidth();
	}

	function do_create(){
		document.mainFrm.act.value = "DO_CREATE";
	    document.mainFrm.submit();
	}

	//选择盘任务点接收人
	function do_SelectPerson(lineBox){
		var objName = lineBox.name;
		var objId = lineBox.id;
		var idNumber = objId.substring(objName.length);
		var companyCode=document.getElementById("companyCode" + idNumber).value;
		var lookUpName = "LOOK_UP_MANAGER";
		var userPara="companyCode="+companyCode;
		var dialogWidth = 50;
		var dialogHeight = 28;
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
		if (objs) {
			var obj = objs[0];
			document.getElementById("receivdBy" + idNumber).value=obj["userId"];
			document.getElementById("receivdByName" + idNumber).value=obj["userName"];
		}
	}

	//下发盘点任务
	function do_send(){
        var startDate=document.mainFrm.startDate.value;
        var endDate=document.mainFrm.endDate.value;
        var assetsCreationDate=document.mainFrm.assetsCreationDate.value;
        var assetsEndDate=document.mainFrm.assetsEndDate.value;
        if(startDate==null||endDate==null||startDate==""||endDate==""){
			alert("请选择基准日范围");
			return;
        }
        if(assetsCreationDate==null||assetsEndDate==null||assetsCreationDate==""||assetsEndDate==""){
			alert("请选择执行期间范围");
			return;
        }
        if(assetsCreationDate<endDate){
			alert("执行期间开始日期不能小于基准日结束日期");
			return;
        }
        var checkArr = document.getElementsByName("subCheck");
        var count=checkArr.length;
        var flag=0;
        for(var i=0;i<count;i++){
			var recevdByName=document.getElementById("receivdByName" + i).value;
			if(recevdByName==null||recevdByName==""){
				var ii=i+1;
				alert("第"+ii+"行请选择任务接受人");
				flag=1;
				break;
			}
        }
        if(flag==0){
	        document.mainFrm.act.value = "DO_SEND";
		    document.mainFrm.submit();
        }
        
	}
		
	

    
</script>