<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>���������б�</title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
    <script language="javascript" src="/WebLibary/js/IESTitleBar.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/login/loginValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/lookup/lookup.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/site.css">
</head>
<script type="text/javascript">
    //printTitleBar("��Ҫ��Ϣ�鿴")
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String orderNumber= "";
    String orderType="";
    String roleName="";
    String title="";
    String level="";
%>

<div id="$$$waitTipMsg$$$" style="position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900">
				<table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td bgcolor="#eeeeee" align="center">�����������ݣ����Ժ�......<img
                                src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
	</table>
</div>
<body leftmargin="0" topmargin="0">
<form action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet" name="impForm1" method="post">
    <input type="hidden" name="forward" value="show_all">
    <table border="0" class="searchBg" >
        <tr>
            <td width="30%" align="right">�������ƣ�</td>
            <td width="60%">
                <input style="width:90%" type="text" name="taskName" maxlength="100"
                 value="<%=StrUtil.nullToString(request.getAttribute("taskName"))%>">
            </td>
            <td>
                <input type="button" name = 'button' class="btn_long" title="�����ѯ"  align="left"
                 value=" �� ѯ " id="queryImg" style="cursor:'hand'" onClick="query(); return false;"/>
            </td>
        </tr>
    </table>
    <table  id="headTb" style="display:none;">
        <tr height="20">
            <td width="25%" align=center>��������</td>
            <td width="25%" align=center>�������</td>
            <td width="25%" align=center>���񴴽�����</td>
            <td width="25%" align=center>��������</td>
        </tr>
    </table>


    <table   border="1" id="listTb" style="display:none;" >
        <%
            RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    title       = StrUtil.nullToString(row.getValue("ORDER_NAME"));
                    orderNumber = StrUtil.nullToString(row.getValue("ORDER_NUMBER"));
                    orderType   = StrUtil.nullToString(row.getValue("ORDER_TYPE"));
                    roleName    = StrUtil.nullToString(row.getValue("IMPLEMNET_ROLE_NAME"));
                    level 		= StrUtil.nullToString(row.getValue("ORDER_LEVEL"));
        %>
        <tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
          onclick="do_showTaskDetail('<%=orderNumber%>','<%=orderType%>','<%=title%>','<%=roleName%>','<%=level%>');" 
          title="����鿴����<%=title%>����ϸ��Ϣ" >
            <td width="25%" height="22" class="font-hui" style="cursor:pointer"><%=row.getValue("ORDER_NAME") %>
            </td>
            <td width="25%" height="22" class="font-hui" style="cursor:pointer"><%=row.getValue("ORDER_NUMBER") %>
            </td>
            <td width="25%" height="22" class="font-hui" style="cursor:pointer"><%=row.getValue("CREATION_DATE") %>
            </td>
             <td width="25%" height="22" class="font-hui" style="cursor:pointer"><%=row.getValue("ORDER_TYPE_NAME") %>
            </td>
        <%
                }
            }
        %>
    </table>
<script type="text/javascript">
	var obj = new Object();
	obj.headTbId = "headTb";
	obj.listTbId = "listTb";
    obj.topHeight = 57; //div��absolute : top ֵ
    //obj.divWidth = "822"; //div�Ŀ�� :
    obj.lTbHeight = "75%"
    printUnitTable( obj );
</script>
</form>
</body>
<div style="position:absolute;top:94%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</html>
<script>
    var flag;
    function show_user_detail(publishId, docType) {
        var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet?forward=show_detail";
        var popscript =getpopscript();
        window.open(url, '', popscript);
    }
    function query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = 'visible';
        var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet?forward=show_all" ;
        impForm1.action = url;
        document.getElementById("queryImg").disabled = true;
        impForm1.submit();
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
    
</script>