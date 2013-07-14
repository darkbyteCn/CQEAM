<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
  <head>
  <title>Excel导入个人非实地资产</title>
      <base target="_self">
    <script type="text/javascript">
        printTitleBar("Excel非实地资产导入");
        var ArrAction0 = new Array(true, "导入", "action_sign.gif", "导入", "do_sub");
        var ArrAction1 = new Array(true, "模版下载", "action_save.gif", "模版下载", "do_DownloadTemplate");
        var ArrAction2 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
        printToolBar();
    </script>
  </head>
  <body>
  <form name="mainFrm" method="post" action="/servlet/com.sino.ams.newSite.servlet.ImpEamAddressServlet" enctype="multipart/form-data" >

      <table id="dataTable" width="100%" border="0" align="center">
          <tr style="width:100%;height:23px">
              <td style="width:100%" align="center"><input type="file" name="fl"  style="width:80%"></td>
          </tr>
      </table>
      <p></p>
      <table width="80%" border="0" align="center">
          <tr style="width:100%;height:23px">
              <td style="width:100%" align="center">导入规则说明如下：</td>
          </tr>
          <tr style="width:100%;height:23px">
              <td style="width:100%" align="center"><hr size="1" width="100%" color="green"></td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">1：此处导入非实地资产相关业务；</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">2：导入文件必须是Excel文件；</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">3：目前仅支持Excel2003格式；</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">4：文件名必须是由英文与下划线组成且不允许包含中文和全角字符；</td>
          </tr>
      </table>

  </form>
  </body>
<script type="text/javascript">
    function do_sub(){
        if(do_Validate(true)){
            mainFrm.submit();
        }
    }

    function do_Close(){
        if(do_Validate(false)){
            if(confirm("确定不导入你选择的文件吗?")){
                self.close();
            }
        } else {
            self.close();
        }
    }

    function do_DownloadTemplate(){
        window.open('/document/template/assetsYearCheckFa.zip');
        return false;
    }

    function do_Validate(showHint){
        var isValid = true;
        with(mainFrm){
            var fileName = fl.value;
            if(fileName == ""){
                isValid = false;
                if(showHint){
                    alert("未选择文件，不能执行导入");
                }
            } else {
                var index = fileName.lastIndexOf(".");
                if(index == -1){
                    isValid = false;
                    if(showHint){
                        alert("上载文件不含后缀名，系统无法判断是否Excel格式");
                    }
                } else {
                    var rightName = fileName.substring(index + 1);
                    if(rightName.toUpperCase() != "XLS"){
                        isValid = false;
                        if(showHint){
                            alert("上载文件不是合法的Excel2003格式");
                        }
                    }
                }
            }
        }
        return isValid;
    }
</script>
</html>