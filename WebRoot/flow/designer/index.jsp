<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<jsp:useBean id="mflow" class="java.util.Vector" scope="page" />
<HTML> 
<HEAD> 
<TITLE>查阅流程</TITLE>   
</HEAD>  

<%
  HttpServletRequest req;
  req=(HttpServletRequest)request;
  mflow=(java.util.Vector)req.getAttribute("mflow");
  String mIndex= StrUtil.nullToString(req.getAttribute("curFileID"));
  String[] mflowarr;
  mflowarr=new String[4];
  //[0]：procname
  //[1]：fromtaskid
  //[2]：taskid
  //[3]：所有logtaskid的逗号分隔
  int n=-1;
    for (java.util.Enumeration  e = mflow.elements() ; e.hasMoreElements() ;) {
		n++;
		if(n>4){break;}
         mflowarr[n]=(java.lang.String)e.nextElement();
	
     }
     
 %>
 
<SCRIPT LANGUAGE=javascript >                                                                
<!--   
 function LoadFile(){ 
       SinoFlowViewer.ShowFlowEx("http://<%=req.getServerName()+":"+req.getServerPort()%>/sinoflo<%=mIndex%>.sfp","<%=mflowarr[0]%>","<%=mflowarr[1]%>","<%=mflowarr[2]%>");
      // window.resizeTo(1024,768);
 }
 
 function viewFlowProc(){
<%       
       if (mflowarr[3]!=null)
       {
%>
     	 SinoFlowViewer.ShowProcessLine("<%=mflowarr[3]%>",30,60);
<%
	}
%> 
 } 
//-->  
</SCRIPT>
<BODY  bgcolor="#FAFAFA" onLoad="LoadFile()" >
<div align="center" style="CURSOR: hand">
<font color="#006699" size="2">
<A href="javascript:viewFlowProc();">[流转过程]</A>
<a href="javascript:window.close();">[关闭窗口]</A> 
</font></div>     
  <OBJECT id=SinoFlowViewer classid="clsid:3D851027-40CC-4183-BC6D-B8F661751D05"  
codeBase="/flow/view/SFView.CAB" height=520                   
style="HEIGHT:700px; LEFT: 0px; TOP: 0px; WIDTH: 980px" width=980 VIEWASTEXT>   
<PARAM NAME="FileName" VALUE="">
    <embed height="520" width="970" _version="65536" _extentx="21167" _extenty="15875" _stockprops="0" scrollbars="0" curtask="" src="65536"> 
    </embed>    
  </OBJECT>   
</body>  
</HTML>  