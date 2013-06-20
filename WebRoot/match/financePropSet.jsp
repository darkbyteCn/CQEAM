<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
--%>
<html>
<head>
    <title></title>
</head>
<body onkeydown="autoExeFunction('do_search()');">
<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("systemId");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String matchType = reqParser.getParameter("matchType");
    String itemName = reqParser.getParameter("itemName");
    String barcode = reqParser.getParameter("barcode");
    String workorderObjectNo = reqParser.getParameter("workorderObjectNo");
    String workorderObjectName = reqParser.getParameter("workorderObjectName");
    String item_code = reqParser.getParameter("item_code");
    String itemSpec = reqParser.getParameter("itemSpec");
    String prjId = reqParser.getParameter("prjId");
    String prjName = reqParser.getParameter("prjName");
%>

<form method="post" name="mainFrm">
    <script type="text/javascript">
        if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_SPARE)%>) {
            printTitleBar("备件确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_SPARE_RET)%>) {
            printTitleBar("备件撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL)%>) {
            printTitleBar("在建工程确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)%>) {
            printTitleBar("在建工程撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_OTHER )%>) {
            printTitleBar("设备屏蔽")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_0THER_RET)%>) {
            printTitleBar("撤销设备屏蔽")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_RENT)%>) {
            printTitleBar("租赁资产确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_RENT_RET)%>) {
            printTitleBar("租赁资产撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_DG)%>) {
            printTitleBar("村通资产确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_DG_RET)%>) {
            printTitleBar("村通资产撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_LC)%>) {
            printTitleBar("低值易耗资产确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_LC_RET)%>) {
            printTitleBar("低值易耗资产撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_TD)%>) {
            printTitleBar("TD资产确认")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_TD_RET)%>) {
            printTitleBar("TD资产撤销")
        } else if (<%=matchType.equals(WebAttrConstant.MATCH_MODE_CT)%>) {
            printTitleBar("村通资产确认")
        }
    </script>
    <table width="100%" border="0" class="queryTable">
    
    <%
    	if(matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL) || matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)){
    %>
    	<tr>
            <td width="8%" align="right">标签号：</td>
            <td width="25%" align="left"><input name="barcode" class="input_style1" style="width:80%" type="text" value="<%=barcode%>"></td>
            <td width="8%" align="right">设备名称：</td>
            <td width="25%"><input name="itemName" class="input_style1" style="width:80%" type="text" value="<%=itemName%>"></td>
            <td width="8%" align="right">设备型号：</td>
            <td width="20%">
                <input type=text name=itemSpec class="input_style1" style="width:80%" value="<%=itemSpec%>">
                <input type="hidden" name=item_code value="<%=item_code%>"><a href=# title="点击选择设备型号" class="linka" onclick="do_SelectSpec();">[…]</a>
            </td>
            
        </tr>
        <tr>
            <td width="8%" align="right">地点简称：</td>
            <td width="20%" align="left">
                <input type=hidden name=workorderObjectNo value="<%=workorderObjectNo%>">
                <input name=workorderObjectName type=text class="input_style1" style="width:80%" value="<%=workorderObjectName%>"><a href=# title="点击选择地点" class="linka" onclick="do_SelectProj();">[…]</a>
            </td>
            <td width="8%" align="right">项目名称：</td>
            <td width="20%" align="left">
                <input type="text" name="prjName" value="<%=prjName %>" style="width:80%" class="input_style2" readonly onclick="do_SelectProject(); "><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a>
            	<input type= "hidden" name = "prjId" value = "<%=prjId %>">
            </td>
            <td width="8%"></td>
            <td align="center"  align = "right" width="20%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;&nbsp;
            <%
                if (matchType.equals(WebAttrConstant.MATCH_MODE_OTHER)) {
            %>
            	<img src="/images/eam_images/hide.jpg" style="cursor:'hand'" onclick="do_match();" alt="屏蔽">
            <%} else if(matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_RENT_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_DG_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_TD_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_LC_RET)) {%>
            	<img src="/images/eam_images/revoke.jpg" style="cursor:'hand'" onclick="cancleDistributePrj();" alt="撤销">
            <% } else {
            %>
            	<img src="/images/eam_images/ok.jpg" style="cursor:'hand'" onclick="distributePrj();" alt="确认">
            <%}%>
            </td>
        </tr>
    <%		
    	} else {
    %>
        <tr>
            <td width="10%" align="right">标签号：</td>
            <td width="25%" align="left"><input name="barcode" class="input_style1" style="width:80%" type="text" value="<%=barcode%>"></td>
            <td width="10%" align="right">设备名称：</td>
            <td width="25%"><input name="itemName" class="input_style1" style="width:80%" type="text" value="<%=itemName%>"></td>
            <td align="center" valign="top" width="10%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
        <tr>
            <td width="10%" align="right">地点简称：</td>
            <td width="25%" align="left">
                <input type=hidden name=workorderObjectNo value="<%=workorderObjectNo%>">
                <input name=workorderObjectName type=text class="input_style1" style="width:80%" value="<%=workorderObjectName%>"><a href=# title="点击选择地点" class="linka" onclick="do_SelectProj();">[…]</a>
            </td>
            <td width="10%" align="right">设备型号：</td>
            <td width="25%">
                <input type=text name=itemSpec class="input_style1" style="width:80%" value="<%=itemSpec%>">
                <input type="hidden" name=item_code value="<%=item_code%>"><a href=# title="点击选择设备型号" class="linka" onclick="do_SelectSpec();">[…]</a>
            </td>
            <%
                if (matchType.equals(WebAttrConstant.MATCH_MODE_OTHER)) {
            %>
            <td align="center"><img src="/images/eam_images/hide.jpg" style="cursor:'hand'" onclick="do_match();" alt="屏蔽"></td>
            <%} else if(matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_RENT_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_DG_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_TD_RET)
                        || matchType.equals(WebAttrConstant.MATCH_MODE_LC_RET)) {%>
            <td align="center"><img src="/images/eam_images/revoke.jpg" style="cursor:'hand'" onclick="do_match();" alt="撤销"></td>
            <% } else {
            %>
            <td align="center"><img src="/images/eam_images/ok.jpg" style="cursor:'hand'" onclick="do_match();" alt="确认">
            </td>
            <%}%>
        </tr>
        <%} %>
    </table>
    <input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">


	  <%
      	if(matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL) || matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)){
      %>
      	<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
            <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
            <tr>
                <td width="1%" align="center">
                    <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'systemId')">
                </td>
                <td height="20" width="4%" align="center">标签号</td>
                <td height="20" width="6%" align="center">设备名称</td>
                <td height="20" width="6%" align="center">设备型号</td>
                <td height="20" width="6%" align="center">设备专业</td>
                <td height="20" width="10%" align="center">地点简称</td>
                <td height="20" width="8%" align="center">所属项目名称</td>
            </tr>

        </table>
    </div>
    <% } else { %>
    	<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
            <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
            <tr>
                <td width="5%" align="center">
                    <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'systemId')">
                </td>
                <td height="20" width="10%" align="center">标签号</td>
                <td height="20" width="17%" align="center">设备名称</td>
                <td height="20" width="20%" align="center">设备型号</td>
                <td height="20" width="8%" align="center">设备专业</td>
                <td height="20" width="25%" align="center">地点简称</td>
            </tr>

        </table>
    </div>
    <% } %>
    
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
        	if(matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL) || matchType.equals(WebAttrConstant.MATCH_MODE_PRJMTL_RET)){
    %>
    
    <div style="overflow-y:scroll;height:302px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onClick="executeClick(this)">
                <td width="1%" align="center"><input type="checkbox" name="systemId" id="systemId<%=i %>" value="<%=row.getStrValue("SYSTEM_ID")%>">
                                                     <!--onPropertyChange="setCheckBoxPropValue(this)">-->
                </td>
                <td style="word-wrap:break-word" height="20" width="4%"
                    align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="6%"
                    align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="6%"
                    align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="6%"
                    align="center"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="10%"
                    align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="8%" align="center">
                	<input type="text" id = "name<%=i %>" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NAME")%>"></td>
                <%
                	}
                %>
            </tr>
	   	</table>
	   </div>
      <%
      	} else {
      %>	
       <div style="overflow-y:scroll;height:302px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onClick="executeClick(this)">
                <td width="5%" align="center"><input type="checkbox" name="systemId" id="systemId0" value="<%=row.getStrValue("SYSTEM_ID")%>">
                                                     <!--onPropertyChange="setCheckBoxPropValue(this)">-->
                </td>
                <td style="word-wrap:break-word" height="20" width="10%"
                    align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="17%"
                    align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="20%"
                    align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="8%"
                    align="center"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td style="word-wrap:break-word" height="20" width="25%"
                    align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
      <%	
      		}
      	}
      %>
</form>

<div style="position:absolute;top:400px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_SERVLET%>?matchType=<%=matchType%>";
        mainFrm.submit();
    }
    
    //author : 李轶
    function distributePrj(){
    	var prjId = mainFrm.prjId.value;
		var prjName = mainFrm.prjName.value;
		if(prjId == null || prjId == "" || prjName == null || prjName == ""){
			alert("您未选择要分配的项目!");
			return;		
		}
		var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } 
		var systemIds = document.getElementsByName("systemId");
		for(var x = 0; x < systemIds.length; x++){
			if(systemIds[x].value != null && systemIds[x].value != "" && systemIds[x].checked == true){
				var name = document.getElementById("name" + x).value;
				if(name != null && name != ""){
					if(confirm("你当前执行分配操作的数据中已分配过相应项目，是否确认继续执行该操作？")){
					 	mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_SERVLET%>?act=<%=AMSActionConstant.MATCH_ACTION%>&matchType=<%=matchType%>";
            			mainFrm.submit();
            			return;
					}
				}
			}
		}
		mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_SERVLET%>?act=<%=AMSActionConstant.MATCH_ACTION%>&matchType=<%=matchType%>";
        mainFrm.submit();
    }
    
    function cancleDistributePrj(){
    	var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } 
		var systemIds = document.getElementsByName("systemId");
		for(var x = 0; x < systemIds.length; x++){
			if(systemIds[x].value != null && systemIds[x].value != "" && systemIds[x].checked == true){
				var name = document.getElementById("name" + x).value;
				if(name != null && name != ""){
					if(confirm("你当前执行分配操作的数据中已分配过相应项目，是否确认继续执行该操作？")){
					 	mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_SERVLET%>?act=<%=AMSActionConstant.MATCH_ACTION%>&matchType=<%=matchType%>";
            			mainFrm.submit();
            			return;
					}
				}
			}
		}
    }
        
    function do_match() {
    	
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
//            mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
            mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_SERVLET%>?act=<%=AMSActionConstant.MATCH_ACTION%>&matchType=<%=matchType%>";
            mainFrm.submit();
        }
    }
    
    function do_SelectProj() {
        var lookUpAddr = "<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var Addrs = getLookUpValues(lookUpAddr, dialogWidth, dialogHeight);
        if (Addrs) {
            var Addr = null;
            for (var i = 0; i < Addrs.length; i++) {
                Addr = Addrs[i];
                dto2Frm(Addr, "mainFrm");
            }
        }
    }    
    
    function do_SelectProject() {
        var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
        if (projs) {
            var proj = null;
            for (var i = 0; i < projs.length; i++) {
                proj = projs[i];
                dto2Frm(proj, "mainFrm");
            }
        }else{
        	mainFrm.prjId.value = "";
        	mainFrm.prjName.value = "";
        }
    }
    
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50.5;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
    }
    
   
</script>