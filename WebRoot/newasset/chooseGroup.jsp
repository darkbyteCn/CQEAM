<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
	<title>选择当前组别</title>
<head>
<body leftmargin="0" topmargin="0"  background="/images/HeaderSlice.jpg" onload="auto_SelectDept();">
<table border="0" width="100%" id="table1">
	<tr>
		<td width="5%" height="160">　</td>
		<td width="90%" height="160">
			<select id="chooseGroup" size="10" style="width:100%;height:100%" ondblclick="do_SelectDept()" title="双击选择该部门">
			<%=request.getAttribute(AssetsWebAttributes.GROUP_OPTIONS)%>
			</select>
		</td>
		<td width="5%" height="160">
			<img border="0" src="/images/eam_images/ok.jpg" onclick="do_SelectDept();"><br><br><br><br>
			<img border="0" src="/images/eam_images/close.jpg" onclick="self.close();">
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
   function do_SelectDept(){
	   var obj = document.getElementById("chooseGroup");
	   var idx = obj.selectedIndex;

	   if (idx > -1) {
		   var curGroupId = obj.options[idx].value;
           var retGroups = curGroupId.split(",");
		   var curGroupName = obj.options[idx].text;
		   var groupObj = new Object();
		   groupObj.fromGroup = retGroups[0];
		   groupObj.fromGroupName = retGroups[1];
		   groupObj.deptCode = retGroups[2];
		   groupObj.deptName = retGroups[3];
		   window.returnValue = groupObj;
		  window.close();
	   } else {
		   alert("请选择你的组别！");
	   }
   }
   function auto_SelectDept(){
	   var obj = document.getElementById("chooseGroup");

	   if (obj.length==1) {
           var idx=0;
		  var curGroupId = obj.options[idx].value;
           var retGroups = curGroupId.split(",");
		   var curGroupName = obj.options[idx].text;
		   var groupObj = new Object();
		   groupObj.fromGroup = retGroups[0];
		   groupObj.fromGroupName = retGroups[1];
		   groupObj.deptCode = retGroups[2];
		   groupObj.deptName = retGroups[3];
		   window.returnValue = groupObj;
		   window.close();
	   }
   }
</script>