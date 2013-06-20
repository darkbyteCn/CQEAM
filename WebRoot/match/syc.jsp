<%--
  Created by IntelliJ IDEA.
  User: V-yuanshuai
  Date: 2008-2-28
  Time: 1:10:02
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<html>
<head>
    <title>资产信息变动总览</title>

    <script type = "text/javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/FormValidate.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/RadioProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/SelectProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/TableProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/datepicker.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/Constant.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>

    <script type = "text/javascript">
        function match() {
        }                 </script>
</head>

<body topmargin = "1" leftmargin = "0">

<form name = "form1" method = "post" action = "">
<script>
    printTitleBar(">>资产信息变动总览");
</script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="9%" align="right">标签号：</td>
            <td width="22%" align="left"><input name=workorderObjectName type=text style="width:80%"><input type=hidden name=workorderObjectNo></td>
            <td width="10%" align="right">设备名称：</td>
            <td width="20%"><input name="itemName" style="width:100%" type="text"></td>
            <td width="10%" align="right">规格型号：</td>
            <td width="10%"><input type=text name=itemSpec></td>
            <td align="center" valign="top"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td align="center"><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_match();" alt="确认">
            </td>
        </tr>
    </table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:850px">
    <table class="headerTable" border="1" width="150%">
        <tr bgcolor="#0073BF" height="22">
            <td rowspan="2" width="4%"><input type="checkbox" name="chk"></td>
            <td colspan = "6" width="48%" align="center"><font color='white'>MIS系统</font></td>
            <td colspan = "6" width="4%" align="center"><font color='white'>EAM系统</font></td>
        </tr>

        <tr bgcolor="#0073BF" height="22">
            <td width="8%"><font color='white'>资产标签</font></td>
            <td width="8%"><font color='white'>资产名称</font></td>
            <td width="8%"><font color='white'>资产型号</font></td>
            <td width="8%"><font color='white'>责任人 </font>   </td>
            <td width="8%"><font color='white'>责任部门</font></td>
            <td width="8%"><font color='white'>所在地点</font></td>
            <td width="8%"><font color='white'>标签号</font></td>
            <td width="8%"><font color='white'>设备名称</font></td>
            <td width="8%"><font color='white'>设备型号</font></td>
            <td width="8%"><font color='white'>责任人 </font>  </td>
            <td width="8%"><font color='white'>责任部门</font></td>
            <td width="8%"><font color='white'>所在地点</font></td>
        </tr>
       </table>
</div>
<div style="overflow:scroll;height:75%;width:867px;position:absolute;top:95px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck1" value="ON"></TD>
			<TD width="8%" height="22">4089-00000002</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">WJDFJ03C</TD>
			<TD width="8%" height="22">姬亚莉,</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">892500.XANX092.000</TD>
			<TD width="8%" height="22">4089-00000002</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">丰锦WJDFJ03C</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">西安营业部周至县营业部</TD>
			<TD width="8%" height="22">892500.南新街营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck2" value="ON"></TD>
			<TD width="8%" height="22">4089-00000012</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">WJDFG03C</TD>
			<TD width="8%" height="22">周志莉,</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">892700.XANX271.000</TD>
			<TD width="8%" height="22">4089-00000012</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">丰锦WJDFG03C</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">西安营业部杨凌区营业部</TD>
			<TD width="8%" height="22">892700.康乐西路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck3" value="ON"></TD>
			<TD width="8%" height="22">4089-00000016</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">WJDFJ03C</TD>
			<TD width="8%" height="22">景西玲</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">897400.XANX616.000</TD>
			<TD width="8%" height="22">4089-00000016</TD>
			<TD width="8%" height="22">点钞机</TD><TD width="8%" height="22">丰锦WJDFJ03C</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部高新分部</TD>
			<TD width="8%" height="22">897400.软件园营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck4" value="ON"></TD>
			<TD width="8%" height="22">4089-00000023</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000023</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck5" value="ON"></TD>
			<TD width="8%" height="22">4089-00000024</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000024</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck6" value="ON"></TD>
			<TD width="8%" height="22">4089-00000025</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000025</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck7" value="ON"></TD>
			<TD width="8%" height="22">4089-00000026</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000026</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck8" value="ON"></TD>
			<TD width="8%" height="22">4089-00000027</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000027</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck9" value="ON"></TD>
			<TD width="8%" height="22">4089-00000028</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000028</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck10" value="ON"></TD>
			<TD width="8%" height="22">4089-00000029</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000029</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck11" value="ON"></TD>
			<TD width="8%" height="22">4089-00000030</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000030</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck12" value="ON"></TD>
			<TD width="8%" height="22">4089-00000031</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">黑新</TD>
			<TD width="8%" height="22">刘坤</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000031</TD>
			<TD width="8%" height="22">显示屏排队机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22">
			<input type="checkbox" name="subCheck13" value="ON"></TD>
			<TD width="8%" height="22">4089-00000046</TD>
			<TD width="8%" height="22">排队叫号机</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">刘军平,</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">896900.XANX410.000</TD>
			<TD width="8%" height="22">4089-00000046</TD>
			<TD width="8%" height="22">排队叫号机</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">陕西西安营业部钟楼分部</TD>
			<TD width="8%" height="22">896900.尚朴路营业厅.000</TD></TR>
        <TR><TD width="4%" height="22"><input type="checkbox" name="subCheck"></TD>
			<TD width="8%" height="22">4089-00000067</TD>
			<TD width="8%" height="22">打印机网卡</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">董婕,</TD><TD width="8%" height="22">　</TD>
			<TD width="8%" height="22">895700.XANX719.000</TD>
			<TD width="8%" height="22">4089-00000067</TD>
			<TD width="8%" height="22">网卡</TD><TD width="8%" height="22">未知品牌未知型号</TD>
			<TD width="8%" height="22">　</TD><TD width="8%" height="22">西安营业部综合办公室</TD>
			<TD width="8%" height="22">895700.综合部七楼库房.000</TD></TR>
    </table>
</div>
</form>
</body>
</html>
<script type="text/javascript">
    function do_search(){
        window.location.href = "/match/syc.jsp";
    }
</script>