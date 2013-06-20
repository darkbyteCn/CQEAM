<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
String sfpUrl = (String)request.getAttribute("sfpUrl");

sfpUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + sfpUrl;
String procName = (String)request.getAttribute("procName");
String fromTask = (String)request.getAttribute("fromTask");
String nowTask = (String)request.getAttribute("nowTask");
String mflow = (String)request.getAttribute("mflow");
%>
<HTML> 
<HEAD> 
<TITLE>查阅流程</TITLE>   
</HEAD>  

<SCRIPT LANGUAGE=javascript >                                                                
<!--   
 function LoadFile(){ 
//       alert("server = " + "e:/flowEngine/out/exploded/flowEngineWeb/flow/sinoflo.sfp");
//       SinoFlowViewer.ShowFlowEx("e:/flowEngine/out/exploded/flowEngineWeb/flow/sinoflo.sfp","市信访办来信","登录来信","校对打印");
        SinoFlowViewer.ShowFlowEx("<%=sfpUrl%>","<%=procName%>","<%=fromTask%>","<%=nowTask%>");
     // window.resizeTo(1024,768);
 }

function viewFlowProc(){
    var flow = "<%=mflow%>";
    if(flow != null && flow != "")
        SinoFlowViewer.ShowProcessLine(flow,30,60); 
 }
//-->  
</SCRIPT>
<BODY  bgcolor="#FAFAFA" onLoad="LoadFile()" >
<div align="center" style="CURSOR: pointer">
<font color="#006699" size="2">
<A href="#" onclick="viewFlowProc();">[流转过程]</A>
<a href="#" onclick="window.close();">[关闭窗口]</A> 
</font></div>
  <OBJECT id=SinoFlowViewer classid="clsid:730F3D4C-DB59-45C1-AE38-BF0AFA5C8BDC"
codeBase="/flow/SinoView.CAB" height=97%
style="HEIGHT:97%; LEFT: 0px; TOP: 0px; WIDTH: 100%" width=100% VIEWASTEXT>
<PARAM NAME="FileName" VALUE="">
    <embed height="97%" width="100%" _version="65536" _extentx="21167" _extenty="15875" _stockprops="0" scrollbars="0" curtask="" src="65536">
    </embed>    
  </OBJECT>   
</body>  
</HTML> 