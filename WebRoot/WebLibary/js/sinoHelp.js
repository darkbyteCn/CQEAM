function showKeyDown(evt) {
	evt = (evt) ? evt : window.event;
	if (evt.keyCode == 121){
        var helpCode="";
         if(document.getElementById("helpCode")){
             helpCode=document.getElementById("helpCode").value;
         }else{
             helpCode="ams31";
         }
	    var url = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?helpCode=" + helpCode + ".htm";
	    var pop = "dialogWidth=900px;dialogHeight=480px";
	     window.open(url, '', pop);
	}
}