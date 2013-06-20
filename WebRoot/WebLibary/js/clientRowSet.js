
/**
  * 功能:以ajax方式向服务器发送请求，并提交服务器端所需要的参数
  * @version 1.0
  * @time 2008-8-8
  */


 var http_request;
 var fg;
 function send_request(path){
     http_request = false;
       if(window.XMLHttpRequest) { 						             //Mozilla 浏览器
               http_request = new XMLHttpRequest();
               if (http_request.overrideMimeType) {			        //设置MiME类别
                       http_request.overrideMimeType('text/xml');
               }
       }
       else if (window.ActiveXObject) { 					         // IE浏览器
               try {
                       http_request = new ActiveXObject("Msxml2.XMLHTTP");
               } catch (e) {
                       try {
                               http_request = new ActiveXObject("Microsoft.XMLHTTP");
                       } catch (e) {}
               }
       }

       if (!http_request) { 							                  // 异常，创建对象实例失败
               throw "不能创建XMLHttpRequest对象实例.";
               return false;
       }
       http_request.onreadystatechange = processRequest;               // 设定关联的结果的函数
       http_request.open("POST", path, false);				          // 确定发送请求的方式和URL以及是否同步执行下段代码
       http_request.send(null);                                       // 设置请求参数
 }


 /*
  * 关联处理结果集的函数
  */
 function processRequest(){
     if (http_request.readyState == 4) { 				         //  判断对象状态
         if (http_request.status == 200) { 				         // 信息已经成功返回，开始处理信息
             var result = http_request.responseText;
             fg = eval( "("+ result +")");
         } else { 							                     //页面不正常
             alert("请求的页面有异常。");
         }
       }
 }


 /*
  * ajax主构造器
  */
 function clientRowSet(){

     this.modelClassName;                                                         //类名
     this.methodName;                                                        //方法名
     this.methodParameterName = new Array();                               //方法参数列表
 
     this.send_request = function(){                                       //发送请求并返回结果集
         try{
             var url = this.joinURL();
             send_request(url);
         }catch(e){
             throw e;
         }
         return fg;
     }

     /*
      * 拼接URL
      */
     this.joinURL = function(){

         if(!this.modelClassName){
             throw "请求modelClassName未设置";
             return;
         }
         if(!this.methodName){
             throw "请求methodName未设置";
             return;
         }
        if(!this.methodParameterName){
             throw "请求methodParameterName未设置";
             return;
         }

       var url = "/servlet/com.sino.base.service.ClientRowSetService";
       url +=
             "?modelClassName="+this.modelClassName+
             "&methodName="+this.methodName;
       for(var i=0;i<this.methodParameterName.length;i++){
          url += "&methodParameterName="+this.methodParameterName[i];
       }
       return url;
     }
 }
