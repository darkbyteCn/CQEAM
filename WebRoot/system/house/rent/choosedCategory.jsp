<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-24
  Time: 13:09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>选择房屋土地类型</title></head>
 <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
  <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
<%
    String userOfGroupOpt = (String) request.getAttribute("userOfGroup");
    String groupOpt = (String) request.getAttribute("GROUPATTR");
%>

<body topMargin=0 leftMargin=0>
<form action="choosedCategory.jsp" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("选择房屋土地类型")
    </script>
    <table width="100%" align="center" bgcolor="#eeeeee">
          <tr height=20px>
            <td width="70px" align="right">条码：</td>
                <%--<input type = "text" name="barcode" class="noEmptyInput" style="width:80%;">--%>
            <td style="border:none"><%=request.getParameter("barcode")%>
            </td>
        </tr>
        <tr height=20px>
            <td width="70px" align="right">用途：</td>
            <td style="border:none">
                <select name="bts"  style="width:80%;" >
                    <option value="基站">基站</option>
                    <option value="非基站">非基站</option>
                </select>
            </td>
        </tr>
        <tr height=20px>
            <td width="70px" align="right">类型：</td>
            <td style="border:none">
                <select name="category"    style="width:80%;">
                    <option value="房屋">房屋</option>
                    <option value="土地">土地</option>
                    <option value="房地合一">房地合一</option>
                </select>
            </td>
        </tr>
        <tr height=20px align=right>
            <td width="50px" style="border:none">&nbsp;</td>
            <td style="border:none">
                <a href="#" onclick="do_submit()"
                   style="cursor:pointer;text-decoration:underline;color:blue">[确定]</a>
                <a href="#" onclick="window.close();"
                   style="cursor:pointer;text-decoration:underline;color:blue">[关闭]</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
        <tr height=20px>
            <td width="70px"></td>
            <td style="border:none">
            </td>
        </tr>
    </table>
    <input type = "hidden" name = "barcode" value="<%=request.getParameter("barcode")%>">
</form>
</body>
</html>
<script type="text/javascript">
    function choose() {
        var bts = mainFrm.bts.value;
        var groupId = mainFrm.category.value;
//        window.returnValue = userId;
//        window.close();
    }

       function do_submit(){
           var bts = mainFrm.bts.value;
           var category = mainFrm.category.value;
           var barcode = mainFrm.barcode.value;
           var height = "350px";
          if (((bts == "非基站") && (category == "房屋")) || ((bts == "非基站") && (category == "房地合一"))) {
            height = "450px";
           }
//           if ((category!=="")&& (bts!=="")&&(barcode!=="")) {
           if ((category!=="")&& (bts!=="")) {

               var url = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=NEW_ACTION&bts=" + bts + "&category="+category+"&barcode="+document.mainFrm.barcode.value;
               var winStyle = "height="+height+",width=620px,top=150, left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes,help=no";
               window.open(url, "_bss", winStyle);
//               window.close();
           } else {
               alert("请先输入条码！");
           }
        }

    var xmlHttp;
    function getImpMenu() {
        var implement = document.getElementById("implement")  ;
        if (implement.options.length > 0) {
            dropOption('implement', false);
        }

        var dept = document.getElementById("dept").value ;
        var url = "/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet?act=implement&dept=" + dept;
        xmlHttp = GetXmlHttpObject(setImpMenu);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("a=1");


    }
    function setImpMenu() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var flexValues = new Array();
            var descriptions = new Array();
            var resText = xmlHttp.responseText;
            var resArray = resText.parseJSON();
            if (resArray == "ERROR") {
                alert(resText);
            } else {
                if (resArray.length > 0) {

                    var implement = document.getElementById("implement");
                    //                var emptyOption = new Option("--请选择--", "");
                    //                littleCategoryObj.add(emptyOption)
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        var arr = retStr.split("$");
                        var option = new Option(arr[1], arr[0]);
                        implement.add(option)
                    }
                }
            }
            xmlHttp = null;
        }
    }


  function do_search() {
//          window.top.main.document.do_search();
//         alert("222222");
//       window.opener.document.do_search();
        window.top.opener.do_search();
        window.close();
//       window.opener.location.reload();
//        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        <%--mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";--%>
//        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.DispositonHouserServlet";
//        mainFrm.submit();

    }
</script>