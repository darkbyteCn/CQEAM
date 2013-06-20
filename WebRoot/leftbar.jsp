<%--User: °×¼Î--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
  <head>
  		<title>×ó±ß²Ëµ¥</title>
		<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function dis(){//Òþ²Ø»òÏÔÊ¾×ó±ß²Ëµ¥
				var obj = parent.document.getElementById("mainSet");
				if(obj.cols == "195,8,*"){
					barImg.title = "ÏÔÊ¾²Ëµ¥";
					barImg.src="/images/frame_open_02.gif";
					obj.cols="0,8,*";
				} else{
					barImg.title = "Òþ²Ø²Ëµ¥";
					barImg.src="/images/frame_close_02.gif";
					obj.cols="195,8,*";
				}
			}
		</script>
  </head>
  
  <body style="background-color: #08377B;">
  		<div style="position:absolute; bottom:50%;">
			<img title = "Òþ²Ø²Ëµ¥" name="barImg" src="/images/frame_close_02.gif" style="margin-top: 50px;" onclick="dis();">
		</div>
  </body>
</html>
