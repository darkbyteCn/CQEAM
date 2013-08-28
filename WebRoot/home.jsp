<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.exception.QueryException" %>
<%@ page import="com.sino.base.log.Logger" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.log.dao.EtsFavoritesDAO" %>
<%@ page import="com.sino.ams.system.important.servlet.PublishInfoServlet" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.yearchecktaskmanager.dao.*" %>
<%@ page import="com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>



<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>ϵͳ��ҳ</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/right.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/site.css">
</head>
<%
    SfUserDTO userDTO=(SfUserDTO) SessionUtil.getUserAccount(request);
    RowSet rs = null;
    try {
        rs = PublishInfoServlet.getHomeImportantInfo();
    } catch (QueryException e) {
        Logger.logError(e);
    }
    String contentPath = request.getRealPath("/document/help/");
    HashMap mp = PublishInfoServlet.getHelpDocs(contentPath);
    HashMap navMap = (HashMap) mp.get(new Integer(0));
    HashMap helpMap = (HashMap) mp.get(new Integer(1));

    EtsFavoritesDAO favoritesDAO=new EtsFavoritesDAO(userDTO,null,null,request);
    RowSet rowSet=favoritesDAO.getAllData();
    int favoritesCount=rowSet.getSize();
     Row favRow=new Row();
    String imgArr[]=new String[]{"",};
    

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = null;
    Date date2 = new Date();
    int days = 0;
    int rowCount = 0;
    int count = 2;
    if (rs != null && !rs.isEmpty()) {
        rowCount = rs.getSize()>10?10:rs.getSize();
    }
    String inforAction ="/servlet/com.sino.ams.system.important.servlet.NoticeServlet";
    Row row = null;
    String publishId = "";
    String title = "";
    String date = "";
    
    //-------
    ASSCHKTaskRemainDAO taskRemainDAO = new ASSCHKTaskRemainDAO(userDTO,null,null,request);
    RowSet taskRowSet = taskRemainDAO.getAllData();
    int taskRowCount = 0;
    int defautlTaskCount = 3;
    String taskBeginDate ="";
    String taskEndDate ="";
    String orderTypeName="";
    String code = "";
    String levle ="";//level
    if (taskRowSet != null && !taskRowSet.isEmpty()) {
    	taskRowCount = rs.getSize()>10?10:taskRowSet.getSize();
    }
    String taskRemainAction="/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet";
    //
    
%>
<body bgcolor="#F3FAFF" topMargin=5 leftMargin=0>
<table border="1" cellpadding="20" height="100%"  width="98%" style="border:#666666;table-layout:fixed;word-wrap:break-word">
    <tr>
        <td valign="top">

            <table width="98%" border="0" align="center" style="table-layout:fixed;word-wrap:break-word">
               <tr style="background-color:#DBEBFB;border:1;border-color:#D4CCB0">
                    <td width="20" align="left">&nbsp;</td>
                    <td width="20" align="justify" background="/images/main/tb_bg.jpg" nowrap>&nbsp; </td>
                    <td width="60" align="justify" background="/images/main/tb_bg.jpg" nowrap> ��Ϣ����</td>
                    <td align="right">
                        <a href="#" onClick="do_more_publish()" alt="�鿴������Ϣ"><img src="/images/main/more.gif"  border="0px"></a>
                    </td>
                </tr>
                <tr style="border-color:RED">
                    <td>&nbsp;</td>
                    <td colspan="3" valign="top">

                    <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
                <%
                    for (int i = 0; i < count; i++) {
                %>
                        <tr height="24px" >
                    <%
                        for(int j=0;j<2;j++){
                            if (rowCount>0&&rowCount>(i*2+j)) {
                                row = rs.getRow(i*2+j);
                                publishId=row.getStrValue("PUBLISH_ID");
                                title = StrUtil.nullToString(row.getValue("TITLE"));
                                date = StrUtil.nullToString(row.getValue("PUBLISH_DATE"));
                                date1 = f.parse(date);
                                days = (int) ((date2.getTime() - date1.getTime()) / 86400000);     %>
                            <td width="35%" class="font-hui" style="border-bottom-style:inherit;"  title="<%=title%>">
                            <img src="/images/main/right-04.gif" width="15px" height="15"/><a href="#" onClick="showDetail('<%=publishId%>');">
                            <%=title.length() < 18 ? title : title.substring(0, 17) + "..."%>
                            </a><%if (days < 7) { %><img src="/images/main/new.jpg" width="32" height="11"/><%} %>
                            </td>
                            <td width="15%" class="font-hui" style="border-bottom-style:inherit;" align="right">
                                [<%=date%>]
                            </td>
                            <%
                            }else{

                    %>
                             <td width="35%" class="font-hui" style="border-bottom-style:inherit;"  title="<%=title%>">&nbsp;

                             </td>
                            <td width="15%" class="font-hui" style="border-bottom-style:inherit;" align="center">&nbsp;
                            </td>
                    <%}
                    title="";
                    }%>
                        </tr>
                <%
                    }
                %>
                        </table>
                    </td>
                </tr>
                <!-- �������������б� -->
                <tr style="background-color:#DBEBFB;border:1;border-color:#D4CCB0">
                    <td width="20" align="left">&nbsp;</td>
                    <td width="20" align="justify" background="/images/main/tb_bg.jpg" nowrap>&nbsp; </td>
                    <td width="60" align="justify" background="/images/main/tb_bg.jpg" nowrap> �������</td>
                    <td align="right">
                        <a href="#" onClick="do_more_taskRemain()" alt="�鿴������Ϣ"><img src="/images/main/more.gif"  border="0px"></a>
                    </td>
                </tr>
                <tr style="border-color:RED">
                    <td>&nbsp;</td>
                    <td colspan="3" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="overflow-y:scroll;overflow-x:hidden;" >
                    <%
                    	if(taskRowCount>0){
                    		if(taskRowCount<defautlTaskCount){
                    			defautlTaskCount = taskRowCount;
                    		}
                    %>
                    <tr height="24px" >
                    	<td width="25%">��������</td>
                    	<td width="25%">�������</td>
                    	<td width="25%">���񷢲�����</td>
                    	<td width="25%">��������</td>
                    </tr>
                    </table>
                    <%
                    	}
                    %>
                <%
                    for (int i = 0; i <defautlTaskCount; i++) {
                %>
                        
                    <%
                       if(taskRowSet.getSize()>0){
                                row = taskRowSet.getRow(i);
                                title = StrUtil.nullToString(row.getValue("ORDER_NAME"));
                                code = StrUtil.nullToString(row.getValue("ORDER_NUMBER"));
                                date = StrUtil.nullToString(row.getValue("CREATION_DATE"));
                                String orderType=StrUtil.nullToString(row.getValue("ORDER_TYPE"));
                                orderTypeName=StrUtil.nullToString(row.getValue("ORDER_TYPE_NAME"));
                                String roleName =StrUtil.nullToString(row.getValue("IMPLEMNET_ROLE_NAME"));
                                date1 = f.parse(date);
                                levle=StrUtil.nullToString(row.getValue("ORDER_LEVEL"));
                                days = (int) ((date2.getTime() - date1.getTime()) / 86400000);    
                    %>
                     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="overflow:scroll;" >
                     <tr height="24px" onclick="do_showTaskDetail('<%=code%>','<%=orderType%>','<%=title%>','<%=roleName%>','<%=levle%>');"
                                title="����鿴����<%=title%>����ϸ��Ϣ">
                            <td width="25%" class="font-hui" style="cursor:pointer"  
                               >
                            <%=title.length() < 18 ? title : title.substring(0, 17) + "..."%>
                           
                            <%if (days < 7) { %><img src="/images/main/new.jpg" width="32" height="11"/><%} %>
                            </td>
                            <td width="25%" class="font-hui" style="cursor:pointer" align="left">
                                [<%=code%>]
                            </td>
                            <td width="25%" class="font-hui" style="cursor:pointer" align="left">
                                [<%=date%>]
                            </td>
                            <td width="25%" class="font-hui" style="cursor:pointer" align="left">
                                [<%=orderTypeName%>]
                            </td>
                            <%--
                             <td width="20%" class="font-hui" style="cursor:pointer" align="left">
                                [<%=taskBeginDate%>]
                            </td>
                              <td width="20%" class="font-hui" style="cursor:pointer" align="left">
                                [<%=taskEndDate%>]
                            </td>
                            --%>
                            <input type="hidden" name="orderType" value="<%=orderType%>"/>
                            <input type="hidden" name="roleName" value="<%=roleName%>"/>
                            <input type="hidden" name="level" value="<%=levle%>"/>
                       </tr>
                <%
                       }
	                title="";
                    code="";
                    date="";
                    orderTypeName="";
                }
                %>
                        </table>
                    </td>
                </tr>
                <!-- �������������б� end -->
                <tr >
                    <td width="20"align="left">&nbsp;</td>
                    <td colspan="3">
                        <table width="100%" height=150px  cellpadding="0" align="left"   bgcolor="#D9EFFD" border="1" >
                            <tr height=20px>
                                <td align="center">
                                    <table  height=120px width="95%"   class="nav">

                                        <tr>
                                             <td width="2px" class="nav_left">&nbsp;</td>
                                            <%for(int kx=0;kx<6;kx++){
                                                 if (kx<favoritesCount) {
                                                    favRow=rowSet.getRow(kx);
                                                } else {
                                                    favRow=new Row();
                                                    favRow.setField("RES_NAME","������");
                                                }
                                            %>
                                            <td width="16%" align="center"  valign="middle"><img onclick="go('<%=kx%>');" style="cursor:hand" src="/images/main/nav_bar0<%=kx+1%>.png" alt="<%=favRow.getStrValue("RES_NAME")%>" align="middle"></td>
                                            <%}%>
                                            <td width="2px" class="nav_right">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td width="2px" class="nav_left">&nbsp;</td>
                                              <%for(int ky=0;ky<6;ky++){
                                                if (ky<favoritesCount) {
                                                    favRow=rowSet.getRow(ky);
                                                } else {
                                                    favRow=new Row();
                                                    favRow.setField("RES_NAME","������");
                                                }
                                            %>
                                            <td width="16%" align="center" valign="middle"><%=favRow.getStrValue("RES_NAME")%></td>
                                            <%}%>
                                        	
                                            <td width="2px" class="nav_right">&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                      </table>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr style="background-color:#DBEBFB;border:1;border-color:#D4CCB0">
                    <td width="20" align="left">&nbsp;</td>
                    <td width="20" align="left" background="/images/main/tb_bg.jpg" nowrap>&nbsp;</td>
                    <td width="60" align="left" background="/images/main/tb_bg.jpg" nowrap>��������</td>
                    <td align="right">
                        <a href="#" onClick="do_more()" alt="�鿴������Ϣ"><img src="/images/main/more.gif"  border="0px"></a>
                    </td>
                </tr>

                <tr>
                    <td width="20" align="left">&nbsp;</td>
                    <td valign="top" colspan="3">
                        <table width="100%">
                            <%
                                String fileName="";
                                String fileUrl="";
                                for (int lineNum = 0; lineNum < 3; lineNum++) {
                            %>
                            <tr>
                                <% if(lineNum==0){%>
                                <td width="70" rowspan="3" align="left" valign="middle"><img src="/images/main/caigou.jpg" align="left" width="80px" height="71"> </td>
                                <%}%>
                                <%for(int k=0;k<3;k++){
                                    int idx=lineNum*3+k;
                                %>
                                <td width="30%">
                                    <%
                                        if (idx < navMap.size()) {
                                            fileName=navMap.get(new Integer(idx)).toString();
//                                            System.out.println(navMap.get(idx).toString());
                                            fileUrl ="/document/help/"+fileName;
                                    %>
                                       <img src="/images/main/arrow.gif" width="15px" height="15"/><a href="/servlet/com.sino.ams.util.HelpDownLoadServlet?fileName=<%=fileName%>" style="color:black"><%=helpMap.get(fileName)%></a>
                                    <%}else{%>
                                      &nbsp;  
                                    <%}%>
                                </td>
                                <%}%>

                            </tr>
                            <%  }%>
                        </table>
                    </td>
               </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
<script type="text/javascript">

    function showDetail(publishId) {
        var url = "<%=inforAction%>?forward=show_detail&publishId=" + publishId;
        window.open(url);
    }
    function openApp(url, isPopup, popscript) {
        if (isPopup == 'Y') {
            window.open(url, '', popscript);
        } else {
            document.location.href = url;
        }

    }

    function do_more_publish(){
        var url = "<%=inforAction%>?forward=show_all";
        window.open(url);
    }

    function do_more_taskRemain(){
    	 var url = "<%=taskRemainAction%>?forward=show_all";
         window.open(url);
    }

    function do_showTaskDetail(orderNumber,orderType,orderName,roleName,levle){
	   //ʵ��
	   //��ʵ��
	   //��ʡ��˾�·�
	   //�ӵ��й�˾�·�
	   //�Ӳ��ž����·�
       
    //���й�˾�ʲ�����Ա����ʡ�·��̵�����
    var url="";
    if(orderType=="ASS-CHK-TASK"){
    	var url ="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskCityServlet"
    //ʵ������[�̵�������]
    }else if(orderType=="ADDRESS-WIRELESS"){
    	var url ="/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=checkapp&taskType=y"
    //ʵ�ط�����[�ȵ����ţ������ڴ���]
    }else if(orderType=="ADDRESS-NON-WIRELESS"){
        var url ="/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=checkapp&taskType=y"
    //��ʵ��[]
    }else if(
    	    (orderType=="NON-ADDRESS-SOFTWARE"||orderType=="NON-ADDRESS-CLIENT"||orderType=="NON-ADDRESS-PIPELINE")
           )
        {
   		 if(roleName=="�����ʲ�����Ա"){
    			var url="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskDeptServlet";
   		 }else {
   			    var url="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckFaServlet";
   	   	 }
    }
    if(!url==""){
    	  if(orderType=="ADDRESS-WIRELESS"||orderType=="ADDRESS-NON-WIRELESS"){
			url+="&action=fromRemain&parentOrderNumber="+orderNumber+"&orderTypes="+orderType+"&orderName="+orderName+"&roleName="+roleName;
    	  }else{
           url+="?action=fromRemain&parentOrderNumber="+orderNumber+"&orderType="+orderType+"&orderName="+orderName+"&roleName="+roleName;
    	  }
    	  window.open(url);
      }
   }
    
    function do_more(){
    }
    function do_fav(){
        window.open("/servlet/com.sino.ams.log.servlet.EtsFavoritesServlet");
    }

    function go(idx) {
        var isOpen = "N";
        var arr = new Array();
        var popArr = new Array();
    <%for(int kz=0;kz<6;kz++){
    if(kz<favoritesCount){%>
        arr[<%=kz%>] = "<%=rowSet.getRow(kz).getStrValue("RES_URL")%>";
        popArr[<%=kz%>] = "<%=rowSet.getRow(kz).getStrValue("IS_POPUP")%>";
    <%}else{ %>
        arr[<%=kz%>] = "/servlet/com.sino.ams.log.servlet.EtsFavoritesServlet";
        popArr[<%=kz%>] = "Y";
    <%}
    %>
    <%}%>

    <%%>
        if (popArr[idx] == "Y") {
            window.open(arr[idx]);
        } else {
            document.location.href = arr[idx];
        }
    }
    function to_tdeam(){
          var uname="<%=userDTO.getLoginName()%>";
        var istd="<%=userDTO.getIsTd()%>"
        if(istd =="Y"){
             uname= uname.substring(3,888);
        }else{
           uname="TD_"+ uname;
        }

//         var url="http://10.218.64.84:8201/servlet/emos.EmosLogin?uid="+uname+"&toType="+""+"&fromType="+"emos";
         var url="http://10.217.227.121:8008/servlet/oa.login?uid="+uname;

//        mainForm.submit();
       var popscript = 'width=1024,height=768,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes';
    window.open(url,null,popscript);
//       parent.location.URL="http://10.220.11.102:8300/servlet/com.sino.ams.system.user.servlet.AmsLoginServlet";
    }
</script>