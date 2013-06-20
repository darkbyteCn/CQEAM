<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-12-07
  Time: 14:12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>屏蔽原因</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
   <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body topMargin=0 leftMargin=0 rightMargin=0>
<form>
    <script language="javascript">
    printTitleBar("屏蔽原因");
</script>
    <table style="margin-left:0;margin-right:0;width:100%" bgcolor="#eeeeee">
        <tr>
            <td>&nbsp;&nbsp;&nbsp;<font size="2">请选择屏蔽原因：</font>
            </td>
            <td>
                <select name="remark" >
                    <option value="">--请选择--</option>
                    <option value="非网络资产">非网络资产</option>
                    <option value="暂估资产">暂估资产</option>
                    <option value="名称型号不明资产">名称型号不明资产</option>
                    <option value="MIS分类过细资产">MIS分类过细资产</option>
                    <option value="费用型资产">费用型资产</option>
                    <option value="ETS不管理资产">AMS不管理资产</option>
                    <option value="无法找到资产">无法找到资产</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">&nbsp;&nbsp;&nbsp;<a name="return" style="cursor:'hand'" class="hei" onClick="ret();"><font color="Blue"
                                                                                                        size="2">
                [确定]</font></a>&nbsp;</td>
            <td align="left" height="50">&nbsp;&nbsp;&nbsp;<a name="closeit" style="cursor:'hand'" class="hei" onClick="window.close();"><font
                    color="Blue" size="2">[关闭]</font></a></td>

        </tr>
    </table>
    <script language="javascript">

        function ret()
        {
            var retVal = document.forms[0].remark.value;
            if(retVal==null||retVal=="")
            {
                alert("请选择屏蔽原因！");
                return false;
            }
//            if (retVal.length > 200)
//                alert("不能超过200字符！");
//            else
//            {
                window.returnValue = retVal;
                close();
//            }
        }
    </script>
</form>
</body>
</html>