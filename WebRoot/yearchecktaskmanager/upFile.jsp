<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
  <head>
  <title>Excel������˷�ʵ���ʲ�</title>
      <base target="_self">
    <script type="text/javascript">
        printTitleBar("Excel��ʵ���ʲ�����");
        var ArrAction0 = new Array(true, "����", "action_sign.gif", "����", "do_sub");
        var ArrAction1 = new Array(true, "ģ������", "action_save.gif", "ģ������", "do_DownloadTemplate");
        var ArrAction2 = new Array(true, "�ر�", "action_cancel.gif", "�ر�", "do_Close");
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
              <td style="width:100%" align="center">�������˵�����£�</td>
          </tr>
          <tr style="width:100%;height:23px">
              <td style="width:100%" align="center"><hr size="1" width="100%" color="green"></td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">1���˴������ʵ���ʲ����ҵ��</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">2�������ļ�������Excel�ļ���</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">3��Ŀǰ��֧��Excel2003��ʽ��</td>
          </tr>
          <tr style="width:100%;height:30px">
              <td style="width:100%">4���ļ�����������Ӣ�����»�������Ҳ�����������ĺ�ȫ���ַ���</td>
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
            if(confirm("ȷ����������ѡ����ļ���?")){
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
                    alert("δѡ���ļ�������ִ�е���");
                }
            } else {
                var index = fileName.lastIndexOf(".");
                if(index == -1){
                    isValid = false;
                    if(showHint){
                        alert("�����ļ�������׺����ϵͳ�޷��ж��Ƿ�Excel��ʽ");
                    }
                } else {
                    var rightName = fileName.substring(index + 1);
                    if(rightName.toUpperCase() != "XLS"){
                        isValid = false;
                        if(showHint){
                            alert("�����ļ����ǺϷ���Excel2003��ʽ");
                        }
                    }
                }
            }
        }
        return isValid;
    }
</script>
</html>