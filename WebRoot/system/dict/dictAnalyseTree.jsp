<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<html>
<base target="right">
<head>
<style type="text/css">
    BODY, TD {
        OVERFLOW-Y: AUTO;
        FONT-FACE: Courier New;
        FONT-SIZE: 12px
    }
     A:LINK {
        FONT-SIZE: 12px;
        COLOR: #FFFFFF;
        TEXT-DECORATION: NONE;
    }
    A:VISITED{
        COLOR:#FFFFFF;
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
</head>
<body leftmargin="1" topmargin="0">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<%
	String dictTree = (String)request.getAttribute("TREE_HTML");
	String dictJs = (String)request.getAttribute("TREE_JS");
	out.print(dictTree);
	out.print(dictJs);
%>
</body>
</html>
