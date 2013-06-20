<%--
  User: zhoujs
  Date: 2008-3-28 15:22:38
  Function:
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>


<html>
<head>
    <title>差异处理</title>
    <script type="text/javascript">
        function save() {
            window.returnValue = getValue();
            window.close();
        }

        function getValue() {
            var radio = document.dealDiff.result;
            for (i = 0; i < radio.length; i++) {
                if (radio[i].checked) {
                    return radio[i].value;
                }
            }
            return "";
        }
    </script>
</head>
<%


%>
<base target="_self">

<body bgcolor="#FFFFFF" text="#000000">
<form name="dealDiff">
    <p>

        <br/>
    <fieldset style="width: auto">
        <legend style="color:#CECECE">差异处理</legend>
        <table width="100%" border="0"  cellspacing="1" bgcolor="#829AD5">
            <tr bgcolor="#FEFFE8">
                <td><input name="result" type="radio" checked  value="扫描结果为准">以扫描结果为准</td>
                <td><input name="result" type="radio" value="系统数据为准"> 以系统数据为准</td>
            </tr>
            <tr  bgcolor="#FEFFE8">
                <td align="center" width="40%"><input name="ok" type="button" onclick="save()" value="确定"></td>
                <td align="center" width="40%"><input name="cancle" type="button" onclick="window.close();" value="取消"></td>
            </tr>
        </table>
    </fieldset>

</form>
</body>
</html>