<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>


<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-11-21
  Time: 19:45:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.match.ETSMISLocationMatch" %>
<!--本页面是显示未匹配的地点信息和匹配地点-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>EAM-MIS地点匹配</title>

<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script language="javascript">
		var ArrAction1=new Array(true,"显示已匹配地点","act_query.gif","显示已匹配地点","show");
		var ArrActions=new Array(ArrAction1);
		var ArrSinoViews=new Array();
</script>

<%
    //    MUserInfo user = (MUserInfo) session.getAttribute("user_info");
//    String orgid = user.orgid;
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgid = userAccount.getOrganizationId();

    ETSMISLocationMatch elm = new ETSMISLocationMatch();
    //未匹配的EAM地点
    RowSet etsgrid = elm.listETSLocation(orgid);
    //未匹配的MIS地点
    RowSet misgrid = elm.listMISLocation(orgid);
    String match = request.getParameter("match");
    String[] location = request.getParameterValues("ets_mis");
    if (match != null && match.equals("1")) {
        boolean success = elm.matchETSMIS(location, orgid,userAccount.getUserId());
        if (success) {
%>
			<script language="javascript">
				alert("匹配成功！");
			</script>
			<%
			}else{
			%>
			<script language="javascript">
				alert("匹配失败！");
			</script>
			<%
			}
			etsgrid=elm.listETSLocation(orgid);
			misgrid=elm.listMISLocation(orgid);

	}
%>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<script language="javascript">
	printTitleBar("EAM-MIS地点匹配");
	printToolBar();
</script>

<script language="javascript">
	//显示已匹配地点信息
	function show(){
		var screenHeight = window.screen.height;
		var screenWidth = window.screen.width;
		var top=(screenHeight-600)/2;
		var left=(screenWidth-800)/2;
			url="/match/amsMisLocMatch/amsMisLocInfo.jsp";
			var winstyle = "width=800,height=450"
			+ 	",top="+top+",left="+left+",help=yes,status=yes,resizable=no,scrollbars=yes,toolbar=yes,menubar=yes,location=yes,center=yes";
			window.open(url, "", winstyle);
	}
	function lookup(){
	var ets_sel=document.forms[0].ets;
	var mis_sel=document.forms[0].mis;
	var mis_len=mis_sel.length;
	var index=ets_sel.selectedIndex;
	var index_mis=mis_sel.selectedIndex;
	var etsNo=ets_sel.options[index].value;
	var ets_desc=ets_sel.options[index].text;
	if(ets_desc.indexOf(".")<0){
		return;
	}
	var etsArr=ets_desc.split(".");
	var ets_bts;
	if(etsArr.length==3)
			{
				var nIndex=etsArr[1].indexOf("BTS");
				if(nIndex>-1)
				{
					ets_bts=etsArr[1].substring(nIndex+3,etsArr[1].length);

					if(ets_bts.indexOf("0")>-1)
					{
						ets_bts=ets_bts.substring(1,ets_bts.length);
					}
					//compare to mis
					for(j=0;j<mis_len;j++)
					{
						var mis_desc=mis_sel.options[j].text;

						if(mis_desc.indexOf(".")<0)
						{
							continue;
						}

						var misArr=mis_desc.split(".");
						if(misArr.length==3)
						{
							misArr[1]=misArr[1].toUpperCase();

							var n_misIndex=misArr[1].indexOf('BTS'+ets_bts);
							if(n_misIndex<0)
							{
								n_misIndex=misArr[1].indexOf('BTS0'+ets_bts);
							}

							if(n_misIndex>-1)
							{
								//ets_sel.options[i].selected=true;
								mis_sel.options[j].selected=true;
							}
						}
					}
				}
			}

	//alert(ets.options[index].text+"value:"+ets.options[index].value);

	}
	/*
	* 在页面上删除已经匹配的地点
	*/
	function del(){
		var mis=document.forms[0].mis;
		var ets_mis=document.forms[0].ets_mis;
		var index=ets_mis.selectedIndex;
		if(index<0){
			alert("请选择要删除的待匹配地点！");
			return;
		}
		var location=ets_mis.options[index].text;
		var array=location.split("----");
		var misLocation=array[1];
		//恢复未匹配的mis表中的数据
		var oOption = document.createElement("OPTION");
		mis.options.add(oOption);
		oOption.innerText =misLocation;
		oOption.value =misLocation;

		//删除待匹配select中的option;
		ets_mis.options.remove(index);


	}
	/*
	* 在页面上增加已匹配的地点
	*/
	function add(){
		var ets=document.forms[0].ets;
		var mis=document.forms[0].mis;
		var ets_mis=document.forms[0].ets_mis;
		//alert("1");
		var index_ets=ets.selectedIndex;
		var index_mis=mis.selectedIndex;
		//alert("2");
		//判断是否选择了ETS地点
		if(index_ets<0){
			alert("请选择EAM地点！");
			return;
		}
		//判断是否选择了MIS地点
		if(index_mis<0){
			alert("请选择MIS地点！");
			return;
		}
		//alert("3");
		var etsNO=ets.options[index_ets].value;
		var etsLocation=ets.options[index_ets].text;
		var misLocation=mis.options[index_mis].text;
		var flag=false;
		//判断是否已经插入过

		for(i=0;i<ets_mis.length;i++){
			if(ets_mis.options[i].text==etsLocation+"----"+misLocation){
				flag=true;
				break;
			}
		}
		if(flag){
			alert("该地点已存在！");
			flag=false;
			return;
		}

		//向待匹配中插入数据。
		var oOption = document.createElement("OPTION");
		ets_mis.options.add(oOption);
		oOption.innerText =etsLocation+"----"+misLocation;
		oOption.value = etsNO+"----"+misLocation;
		//在未匹配的MIS地点中删除数据。
		mis.options.remove(index_mis);

	}

	/*
	* 提交到数据库
	*/
	function ok(){
		if(document.forms[0].ets_mis.length>0){
              if (confirm("确定要匹配？")) {
            for(i=0;i<document.forms[0].ets_mis.length;i++){
				document.forms[0].ets_mis.options[i].selected=true;
			}
			document.forms[0].match.value="1";
			document.forms[0].match.value="1";
            document.forms[0].action ="/match/amsMisLocMatch/amsMisLocMatch.jsp";
            document.forms[0].submit();
            document.body.innerHtml = "<center> Waiting...</center>";
//             setInterval("ok()",10000);
        }}else{
			alert("没有待匹配的地点！");
		}
	}

    function findETS( flag)
    {
        var str=document.forms[0].ets1.value;
        var ets_sel=document.forms[0].ets;
	    var index=0
	    var len=ets_sel.length;
        if(flag==1)
            index=ets_sel.selectedIndex+1;
        var bflag=false;
        for(i=index;i<len;i++)
        {
            var v=ets_sel.options[i].text;

            if( v.indexOf(str)<0)
            {
                continue;
            }else{
                ets_sel.options[i].selected=true;
                bflag=true;
                break;
            }
        }
        if(!bflag){
            alert("已搜索到结尾！");
        }
    }
    function findMIS(flag)
    {
        var str=document.forms[0].mis1.value;
        var mis_sel=document.forms[0].mis;
	    var index=0;
	    var len=mis_sel.length;
        if(flag==1)
            index=mis_sel.selectedIndex+1;
        var bflag=false;

        for(i=index;i<len;i++)
        {
            var v=mis_sel.options[i].text;

            if( v.indexOf(str)<0)
            {
                continue;
            }else{
                mis_sel.options[i].selected=true;
                bflag=true;
                break;
            }
        }
        if(!bflag){
            alert("已搜索到结尾！");
        }
    }
</script>

<table  border="1" align="left" bgcolor="#EFEFEF">
<form>
	<input name="match" type="hidden" value="">
	  <tr>
		<td width="350">未匹配的EAM地点：<input type="text" name="ets1" value="">
            <input type="button" name="查找" onclick="javascript:findETS(0);" value="查找">
            <input type="button" name="下一个" onclick="javascript:findETS(1);" value="下一个">
            </td>
		<td width="350">未匹配的MIS地点:<input type="text" name="mis1" value="">
            <input type="button" name="查找" onclick="javascript:findMIS(0);" value="查找">
            <input type="button" name="下一个" onclick="javascript:findMIS(1);" value="下一个">

            </td>
	  </tr>
	  <tr>
		<td width="350" height="150">
			<select name="ets" size="13"  style="width:349"
			onclick="javascript:lookup()">
			<%
                Row row =null;
                for(int i=0;i<etsgrid.getSize();i++){
                 row=  etsgrid.getRow(i) ;
             %>
				<option value="<%=row.getValue("ETSNO")%>" >
				<%=row.getValue("ETSLOCATION")%>				</option>
			<%
				}
			%>
            </select>		</td>
		<td width="350" height="150">
			<select name="mis" size="13" style="width:349">
				<% Row row2 = null;
					for(int i=0;i<misgrid.getSize();i++){
                        row2 = misgrid.getRow(i);
                %>
					<option value="<%=row2.getValue("MISLOCATION")%>">
					<%=row2.getValue("MISLOCATION")%>					</option>
				<%
					}
				%>
        	</select>		</td>
	  </tr>
	  <tr>
	    <td colspan="2">
		  	<div align="right">
		  	  <input type="button" value="增加" onClick="javascript:add()">
			  <input  type="button" value="删除"  onclick="javascript:del()">
		  	  <input type="button" id="clickButton" value="确定"onclick="javascript:ok()">&nbsp;&nbsp;&nbsp;
  	        </div></td>
	  </tr>
	  <tr>
		<td colspan="2">待匹配的地点:</td>
	  </tr>
	  <tr>
		<td width="350" height="150" colspan="2">
			<select name="ets_mis" size="13" multiple  style="width:698" >
	  	  </select>		</td>
	  </tr>
</form>
</table>

</body>
</html>
