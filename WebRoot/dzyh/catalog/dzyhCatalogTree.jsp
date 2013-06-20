<%@ page language="java" contentType="text/html;charset=GBK"%>
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
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
</head>
<body leftmargin="1" topmargin="0">
<%
	String dictTree = (String)request.getAttribute("DZYH_TREE_HTML");
	String dictJs = (String)request.getAttribute("DZYH_TREE_JS");
	out.print(dictTree);
	out.print(dictJs);
%>
</body>
</html>
