
function helpInit(helpId) {
   	document.getElementById("helpId").value = helpId;
	document.onkeydown = showKeyDown;
}

function showKeyDown(evt) {
	evt = (evt) ? evt : window.event
	if (evt.keyCode == 121){
		var helpCode = document.getElementById("helpId").value;
	    //var url = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?helpCode=" + helpCode + ".htm";
	    var url = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?helpCode=" + helpCode ;
	    //var pop = "dialogWidth=900px;dialogHeight=480px;scrollbars=yes;resizable=yes;";
	    var pop = "width=900,height=580,top=150,left=160,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,"
	    window.open(url, "", pop);
	    //var retArr = window.showModalDialog(url, '', pop);
	}
}