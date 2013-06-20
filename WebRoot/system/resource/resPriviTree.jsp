<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<html>
<base target="right">
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/cms_css/cms_css.css">
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
<body leftmargin="1" topmargin="0" bgcolor="#EFEFEF"style="overflow:auto;">
 <script type="text/javascript" >
<%
	String menuTree = (String)request.getAttribute(WebAttrConstant.MENU_TREE);
	out.print(menuTree);
%>
</script>
</body>
</html>
