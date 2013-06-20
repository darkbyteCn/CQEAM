<%@ page contentType="text/html;charset=GBK" language="java" %>
<script type="text/javascript">
function openExportMsgDiv(){
	document.getElementById("$$$disableExportMsg$$$").style.visibility = "visible";
}
function closeExportMsgDiv(){
	var disabledDIV = document.getElementById("$$$disableExportMsg$$$");
	disabledDIV.style.visibility = "hidden";
}

function closeExportDiv(){
	var _d = document.getElementById("ddDiv");
    _d.style.visibility = "hidden";
}
</script>

<div id="$$$disableExportMsg$$$" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="100%" style="background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=80,finishOpacity=20,style=2)">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900"  height="60">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td bgcolor="#FFFFFF" align="center" ><font color="#008000" size="2" onclick="closeExportMsgDiv();">正在导出数据，请稍候......</font><img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
</div>