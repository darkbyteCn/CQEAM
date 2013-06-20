<%@ page import = "com.sino.ams.constant.LookUpConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO" %>
<%@ page import = "com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.util.StrUtil" %>
<%@ page import = "com.sino.ams.constant.AMSActionConstant" %>
<%@ page import = "com.sino.ams.constant.DictConstant" %>
<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%--
  created by ys
  Date: 2007-09-27
  Time: 18:23:30
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <title>基站地点详细信息</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/calendar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormValidate.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>
</head>
<%--<script type="text/javascript">--%>
<%--printTitleBar("基站地点详细信息")--%>
<%--</script>--%>
<%
    String category = request.getParameter("objectCategory");
    EtsObjectDTO spotDTO = (EtsObjectDTO) request.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
    EtsObjectAttributeDTO attriDTO = (EtsObjectAttributeDTO) request.getAttribute(WebAttrConstant.ETS_OBJECT_ATTRIBUTE_DTO);
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String isall = (String) request.getAttribute(WebAttrConstant.CHECK_OPTION);

%>
<BODY TEXT = "000000" BGCOLOR = "FFFFFF" leftmargin = 0 topmargin = 0 class = "STYLE1" onload = "window.focus()">
<form name = "form1" method = "post">
<script type = "text/javascript">
    printTitleBar("基站专业--地点信息");
</script>
<fieldset style = "margin-left:0;height:1350">
<legend><img src = "/images/eam_images/close.jpg" onClick = "do_close();" alt = "关闭"></legend>

<div style = "overflow-y:scroll;position:absolute;top:50px;left:0px;width:100%;height:610px ">
<table>
<tr>
<td width = "20px">　</td>
<td>
<table class = "MsoTableGrid" border = "1" cellspacing = "0" cellpadding = "0" width = "800" style = " border-collapse: collapse; border: medium none" id = "table1" bordercolor = "#666666">

<tr>
    <td width = "4%" rowspan = "8" valign = "middle">
        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">基</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">站</span></p></td>
    <td width = "36%">基站号</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "workorderObjectCode" type = "text" id = "workorderObjectCode" value = "<%=spotDTO.getWorkorderObjectCode()%>" style = "width:100%" maxlength = '20'></td>
    <td valign = "middle" width = "20%">基站名</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "workorderObjectName" type = "text" id = "workorderObjectName" value = "<%=spotDTO.getWorkorderObjectName()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">基站地点</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput size = "55" name = "workorderObjectLocation" type = "text" id = "workorderObjectLocation" value = "<%=spotDTO.getWorkorderObjectLocation()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">　</td>
    <td valign = "middle" width = "20%">　</td>
</tr>
<tr>
    <td width = "36%">所属BSC</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute59" type = "text" id = "attribute59" value = "<%=attriDTO.getAttribute59()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">所属工程</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "projectName" type = "text" id = "projectName" value = "<%=spotDTO.getProjectName()%>" style = "width:85%">
    </td>
</tr>
<tr>
    <td width = "36%">所属区县</td>
    <td valign = "middle" width = "20%"><select class = 'readonlyInput' name = "countyCode" style = "width:100%"><%=countyCode%></select></td>
    <td valign = "middle" width = "20%">工程期数</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute1" type = "text" id = "attribute1" value = "<%=attriDTO.getAttribute1()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">基站类型</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute60" type = "text" id = "attribute60" value = "<%=attriDTO.getAttribute60()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">配置信息</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute65" type = "text" id = "attribute65" value = "<%=attriDTO.getAttribute65()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">经度</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute2" type = "text" id = "attribute2" value = "<%=attriDTO.getAttribute2()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">纬度</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute3" type = "text" id = "attribute3" value = "<%=attriDTO.getAttribute3()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">MMS信息第一条<br></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute63" type = "text" value = "<%=attriDTO.getAttribute63()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">MMS信息第二条<br></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute64" type = "text" value = "<%=attriDTO.getAttribute64()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">巡检模式</td>
    <td valign = "middle" width = "20%"><select name = "isall" class = readonlyInput style = "width:100%"><%=isall%></select></td>
    <td valign = "middle" width = "20%">　</td>
    <td valign = "middle" width = "20%">　</td>
</tr>
<tr>
    <td rowspan = "9" width = "4%">
        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">天</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">馈</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">线</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">部</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">分</span></td>
    <td width = "36%">安装类型</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute4" type = "text" id = "attribute4" value = "<%=attriDTO.getAttribute4()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%">铁塔厂家</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute5" type = "text" id = "attribute5" value = "<%=attriDTO.getAttribute5()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">挂高（米）</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput size = "55" name = "attribute6" type = "text" id = "attribute6" value = "<%=attriDTO.getAttribute6()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"> 　</td>
    <td valign = "middle" width = "20%"> 　</td>
</tr>
<tr>
    <th width = "36%">　</th>
    <th valign = "middle" width = "20%">1s</th>
    <th valign = "middle" width = "20%">2s</th>
    <th valign = "middle" width = "20%">3s</th>
</tr>
<tr>
    <td width = "36%">水平方向角</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute7" type = "text" id = "attribute7" value = "<%=attriDTO.getAttribute7()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute8" type = "text" id = "attribute8" value = "<%=attriDTO.getAttribute8()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute9" type = "text" id = "attribute9" value = "<%=attriDTO.getAttribute9()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">天线倾角</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute10" type = "text" id = "attribute10" value = "<%=attriDTO.getAttribute10()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute11" type = "text" id = "attribute11" value = "<%=attriDTO.getAttribute11()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute12" type = "text" id = "attribute12" value = "<%=attriDTO.getAttribute12()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">天面或铁塔平台上能再安装的天线数量</td>
    <td valign = "middle" width = "60%" colspan = "3"><input readonly class = readonlyInput size = "55" name = "attribute13" type = "text" id = "attribute13" value = "<%=attriDTO.getAttribute13()%>" style = "width:100%"></td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">是否与联通GSM/CDMA共天面 </td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute14" type = "text" id = "attribute14" value = "<%=attriDTO.getAttribute14()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">是否与PHS（小灵通）共天面</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute15" type = "text" id = "attribute15" value = "<%=attriDTO.getAttribute15()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">备注</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute16" type = "text" id = "attribute16" value = "<%=attriDTO.getAttribute16()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td rowspan = "6" width = "4%">
        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">机</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">房</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">部</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center"><span style = "font-family: '宋体'">分</span></td>
    <td width = "36%">建设方式</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute17" type = "text" id = "attribute17" value = "<%=attriDTO.getAttribute17()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">机房楼层（F）</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute18" type = "text" id = "attribute18" value = "<%=attriDTO.getAttribute18()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">机房面积（平方米）</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute19" type = "text" id = "attribute19" value = "<%=attriDTO.getAttribute19()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">是否与其他电信运营商共享</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute20" type = "text" id = "attribute20" value = "<%=attriDTO.getAttribute20()%>" style = "width:100%"></p></td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">是否准备于2008年底搬出</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute21" type = "text" id = "attribute21" value = "<%=attriDTO.getAttribute21()%>" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">备注</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><textarea name = "attribute22" class = readonlyInput cols = "55" rows = "4" id = "attribute22" style = "width:100%"><%=attriDTO.getAttribute22()%></textarea>
    </p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td rowspan = "7" width = "4%">
        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">主</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">设</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">备</span></td>
    <th width = "36%"><span class = "STYLE5">扇区</span></th>
    <th valign = "middle" width = "20%"><span class = "STYLE4">1</span></th>
    <th valign = "middle" width = "20%"><span class = "STYLE4">2</span></th>
    <th valign = "3" width = "20%"><span class = "STYLE4">3</span></th>
</tr>
<tr>
    <td width = "36%">机型柜号</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute23" type = "text" id = "attribute23" value = "<%=attriDTO.getAttribute23()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute29" type = "text" id = "attribute29" value = "<%=attriDTO.getAttribute29()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute25" type = "text" id = "attribute25" value = "<%=attriDTO.getAttribute25()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">TRX数</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute32" type = "text" id = "attribute32" value = "<%=attriDTO.getAttribute32()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute33" type = "text" id = "attribute33" value = "<%=attriDTO.getAttribute33()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute34" type = "text" id = "attribute34" value = "<%=attriDTO.getAttribute34()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">电源数（US）</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute35" type = "text" id = "attribute35" value = "<%=attriDTO.getAttribute35()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute31" type = "text" id = "attribute31" value = "<%=attriDTO.getAttribute31()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute37" type = "text" id = "attribute37" value = "<%=attriDTO.getAttribute37()%>" style = "width:100%"></td>
</tr>
<tr>
    <td nowrap width = "36%">风扇类型（DE34）</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute26" type = "text" id = "attribute26" value = "<%=attriDTO.getAttribute26()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute24" type = "text" id = "attribute24" value = "<%=attriDTO.getAttribute24()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute38" type = "text" id = "attribute38" value = "<%=attriDTO.getAttribute38()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">天线类型</td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute27" type = "text" id = "attribute27" value = "<%=attriDTO.getAttribute27()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute30" type = "text" id = "attribute30" value = "<%=attriDTO.getAttribute30()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute39" type = "text" id = "attribute39" value = "<%=attriDTO.getAttribute39()%>" style = "width:100%"></td>
</tr>
<tr>
    <td width = "36%">天线厂家</td>
    <td width = "20%" valign = "middle"><input readonly class = readonlyInput name = "attribute28" type = "text" id = "attribute28" value = "<%=attriDTO.getAttribute28()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute36" type = "text" id = "attribute36" value = "<%=attriDTO.getAttribute36()%>" style = "width:100%"></td>
    <td valign = "middle" width = "20%"><input readonly class = readonlyInput name = "attribute40" type = "text" id = "attribute40" value = "<%=attriDTO.getAttribute40()%>" style = "width:100%"></td>
</tr>
<tr>
    <td rowspan = "14" width = "4%">
        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">配</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">套</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">设</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">备</span></td>
    <td width = "36%">基站传输方式</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "58" name = "attribute41" type = "text" id = "attribute41" value = "<%=attriDTO.getAttribute41()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">现有传输设备能提供增加的2Mbps电路数</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute42" type = "text" id = "attribute42" value = "<%=attriDTO.getAttribute42()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">现有传输设备能否插盘扩容</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute43" type = "text" id = "attribute43" value = "<%=attriDTO.getAttribute43()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">基站电源类型</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute44" type = "text" id = "attribute44" value = "<%=attriDTO.getAttribute44()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">现有引入的外市电容量（Kwh） </td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute45" type = "text" id = "attribute45" value = "<%=attriDTO.getAttribute45()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">输出电流 （A）</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute46" type = "text" id = "attribute46" value = "<%=attriDTO.getAttribute46()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">输出电压 （V）</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute47" type = "text" id = "attribute47" value = "<%=attriDTO.getAttribute47()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle"> 　</td>
</tr>
<tr>
    <td width = "36%">开关电源模块容量及数量</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute48" type = "text" id = "attribute48" value = "<%=attriDTO.getAttribute48()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">蓄电池组容量及数量</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute49" type = "text" id = "attribute49" value = "<%=attriDTO.getAttribute49()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle"></td>
</tr>
<tr>
    <td width = "36%">是否明显满足机房承重</td>
    <td valign = "middle" width = "60%" colspan = "3"><p>
        <input readonly class = readonlyInput size = "55" name = "attribute50" type = "text" id = "attribute50" value = "<%=attriDTO.getAttribute50()%>" style = "width:100%"></p></td>
    <td valign = "middle">
        　</td>
</tr>
<tr>
    <td width = "36%">是否已采用承重槽钢加固</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput name = "attribute61" type = "text" id = "attribute61" value = "<%=attriDTO.getAttribute61()%>" size = "55" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">调查标识</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput name = "attribute62" type = "text" value = "<%=attriDTO.getAttribute62()%>" size = "55" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">空调型号及安全情况</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute51" type = "text" id = "attribute51" value = "<%=attriDTO.getAttribute51()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">备注</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><textarea class = readonlyInput name = "attribute52" cols = "55" rows = "3" id = "attribute52" style = "width:100%"><%=attriDTO.getAttribute52()%></textarea>
    </p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td rowspan = "5" width = "4%">
        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">覆</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">盖</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">部</span></p>

        <p class = "MsoNormal" align = "center" style = "text-align: center">
            <span style = "font-family: '宋体'">分</span></td>
    <td width = "36%">覆盖区域</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput size = "55" name = "attribute53" type = "text" id = "attribute53" value = "<%=attriDTO.getAttribute53()%>" style = "width:100%"></p>
    </td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">是否覆盖站</td>
    <td valign = "middle" width = "60%" colspan = "3"><p>
        <input readonly class = readonlyInput size = "55" name = "attribute54" type = "text" id = "attribute54" value = "<%=attriDTO.getAttribute54()%>" style = "width:100%"></p></td>
    <td valign = "middle">
        　</td>
</tr>
<tr>
    <td width = "36%">能否与3G共站</td>
    <td valign = "middle" width = "60%" colspan = "3"><p>
        <input readonly class = readonlyInput size = "55" name = "attribute55" type = "text" id = "attribute55" value = "<%=attriDTO.getAttribute55()%>" style = "width:100%"></p></td>
    <td valign = "middle">
        　</td>
</tr>
<tr>
    <td width = "36%">不能共站的决定因素</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><input readonly class = readonlyInput name = "attribute56" type = "text" id = "attribute56" value = "<%=attriDTO.getAttribute56()%>" size = "55" style = "width:100%"></p></td>
    <td valign = "middle">　</td>
</tr>
<tr>
    <td width = "36%">备注</td>
    <td valign = "middle" width = "60%" colspan = "3"><p><textarea class = readonlyInput name = "attribute57" cols = "55" rows = "3" id = "attribute57" style = "width:100%"><%=attriDTO.getAttribute57()%></textarea>
    </p></td>
    <td valign = "middle">　</td>
</tr>
</table>
</td>
</tr>
</table>
</div>
</fieldset>
<input readonly class = readonlyInput type = hidden name = "objectCategory" value = "<%=category%>">
<input readonly class = readonlyInput type = hidden name = "organizationId" value = "<%=spotDTO.getOrganizationId()%>">
<input readonly class = readonlyInput type = hidden name = "disableDate" value = "<%=spotDTO.getDisableDate()%>">
<input readonly class = readonlyInput name = "projectId" type = "hidden" id = "projectId" value = "<%=spotDTO.getProjectId()%>">
<input readonly class = readonlyInput type = "hidden" name = "act">
<input readonly class = readonlyInput name = "workorderObjectNo" type = "hidden" id = "workorderObjectNo"
       value = "<%=spotDTO.getWorkorderObjectNo()%>">
</form>
</BODY>
</HTML>
<iframe width = 174 height = 189 name = "gToday:normal:calendar.js" id = "gToday:normal:calendar.js" src = "" scrolling = "no"
        frameborder = "0" style = "visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<script type = "text/javascript">
    function do_close() {
        window.close();
    }

</SCRIPT>