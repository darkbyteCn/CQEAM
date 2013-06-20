<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<html>
<base target="left">
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/MzTreeView10.js"></script> 
</head>
<style type="text/css">
     A:LINK {
        FONT-SIZE: 12px;
        COLOR: #3366ff;
        TEXT-DECORATION: NONE;
    }
    A:VISITED{
        COLOR:#3366ff;
        TEXT-DECORATION: NONE;
    }
    A:HOVER {
        FONT-SIZE: 12px;
        COLOR: orange;
        TEXT-DECORATION: UNDERLINE;
    }
    A:ACTIVE{
        COLOR:yellow;
        TEXT-DECORATION: UNDERLINE;
    }
    DIV, SPAN {
        white-space: nowrap;
    }
</style>

<body leftmargin="1" topmargin="0" bgcolor="#EFEFEF" style= "overflow-x:auto;overflow-y:auto;" >
<%// String tmp= "127";%> 
<!-- 
<input type="button" value="expandAll" onclick="tree.expandAll()" />
<input type="button" value="expandID" onclick="tree.expand('2');tree.expand('3');tree.expand('<%//=tmp%>');"/>
 -->
<script type="text/javascript" >
<%
	String menuTree = (String)request.getAttribute(WebAttrConstant.MENU_TREE);
	out.print(menuTree);
%>
</script>
<!-- <input type="button" value="expandID" onclick="tree.expand('2');tree.expand('3');"/> -->
</body>
</html>

