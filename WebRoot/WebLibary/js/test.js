function do_Complete_Validate(){
	return (  validateData() && validateDate() && checkUpFeeItem() && doVerifyVendorItem() );
}

function do_Save_Validate(){
	return (  validateData() && checkUpFeeItem() );
}

function do_Save_flow() {
    autoValue(AV_SAVE_MASK);
    if(!do_Save_Validate())
        return;
    SFQuerySave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    clearDivRight();
    saveInfo();
    isSave = true;
    do_Save_app();
}

/**
 * 流程提交
**/
function do_Complete_flow() {

        SFQueryComplete();
        if(!Launch_Continue) {
	        if(Error_Msg != "")
	            alert(Error_Msg);
	        return;
	    }
        var str = getNextTask();
        if(str != "") {
	        autoValue(AV_COMPLETE_MASK);
	        if(!validation())
	            return;
	        SFQuerySave();
            if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }

            //var flowDesc = getJsonData( str , "flowHint:");

	        //if( getAttribute3() == "PLAN_APPROVE" || ( getAttribute3() == "NETWORK_APPROVE" && Launch_Code == "C1"  )  ){
	        //	try{
	        //		var tempStr = inputOpinion2( flowDesc );
	        //		document.getElementById("sf_opinion").value = tempStr ;
	        //	}catch(ex){
	        //		alert( ex.message );
	        //	}
	        //}else{
	        //	var tempStr = inputOpinion( flowDesc );
	        //	document.getElementById("sf_opinion").value = tempStr ;
	        //}

	        var flowDesc = getJsonData( str , "flowHint:");
	        var tempStr = inputOpinion( flowDesc );
            document.getElementById("sf_opinion").value = tempStr ;
	        if(document.getElementById("sf_opinion").value == "undefined" || document.getElementById("sf_opinion").value == "" ) {
	            document.getElementById("sf_opinion").value = "";
	            return;
	        }

	        document.getElementById("sf_nextTaskData").value = str;
	        if(finishMessage() == "") {
	            restoreNextTaskData();
	            return;
	        }

            setNextFlowDesc();
	        SFPostSave();
	        if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }

            clearDivRight();
	        saveInfo();
	        isSave = true;
            do_Complete_app_yy();
	    }
}

function do_NewApproveOrder() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能完成");
        return;
    }
    var copyUsers = "";
    if(document.getElementById("sf_caseID").value.indexOf(":") > 0) {
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseComplete?send='0'";
        document.forms[0].submit();
        return;
    }

    if(isAbandon()) {
        return;
    }

    //此处添加内容是内蒙合同项目为"变更、解除、作废、移交、借阅"等流程专门添加。
    //目的在于使得页面验证工作在选人之前进行，（注意:原来在选人之后的验证工作已经注销）
    //其它地方可根据需要进行斟酌,作者盖连功。
    //关联AppStandard.js中147行
    var isAppValidatePass=false;		//通过OR NO
    var sfProcedureValue=document.getElementById("sf_procedure").value;
    if(sfProcedureValue.indexOf("变更") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }else if (sfProcedureValue.indexOf("解除") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }else if (sfProcedureValue.indexOf("作废") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }else if (sfProcedureValue.indexOf("移交") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }else if (sfProcedureValue.indexOf("借阅") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }else if (sfProcedureValue.indexOf("履行督办") >= 0){
    	isAppValidatePass=do_App_Validate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
    }

    SFQueryComplete();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    getNextTask2();
//        do_Complete_app()
//        if( do_InsertMark() ){
//        	do_Complete_app();
//        }
    
}

function do_Complete_app() {
	if(true){
        try{
//			disabledBtn();
            var actObj = document.getElementById("act");
			actObj.value = "APPROVE_ACTION";
            //setFrmEnable("mainFrm");
//			document.forms[0].action="/servlet/com.sino.ies.inv.ywypmgr.servlet.CtmsForwardServlet";
			document.forms[0].submit();
		}catch(ex){
			alert( ex.message );
		}finally{
			enableBtn();
		}
	}
}
//铺货单独处理
 function do_phComplete_flow() {

        SFQueryComplete();
	    if(!Launch_Continue) {
	        if(Error_Msg != "")
	            alert(Error_Msg);
	        return;
	    }
        var str = getNextTask();
        if(str != "") {
	        autoValue(AV_COMPLETE_MASK);
	        if(!validation())
	            return;
	        SFQuerySave();
            if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }

            //var flowDesc = getJsonData( str , "flowHint:");

	        //if( getAttribute3() == "PLAN_APPROVE" || ( getAttribute3() == "NETWORK_APPROVE" && Launch_Code == "C1"  )  ){
	        //	try{
	        //		var tempStr = inputOpinion2( flowDesc );
	        //		document.getElementById("sf_opinion").value = tempStr ;
	        //	}catch(ex){
	        //		alert( ex.message );
	        //	}
	        //}else{
	        //	var tempStr = inputOpinion( flowDesc );
	        //	document.getElementById("sf_opinion").value = tempStr ;
	        //}

	        var flowDesc = getJsonData( str , "flowHint:");
	        var tempStr = inputOpinion( flowDesc );
            document.getElementById("sf_opinion").value = tempStr ;
	        if(document.getElementById("sf_opinion").value == "undefined" || document.getElementById("sf_opinion").value == "" ) {
	            document.getElementById("sf_opinion").value = "";
	            return;
	        }

	        document.getElementById("sf_nextTaskData").value = str;
	        if(finishMessage() == "") {
	            restoreNextTaskData();
	            return;
	        }

            setNextFlowDesc();
	        SFPostSave();
	        if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }

            clearDivRight();
	        saveInfo();
	        isSave = true;
            do_phComplete_app_yy();
	    }
}
function do_phComplete_app_yy() {
	if(true){
        try{
//			disabledBtn();
            var actObj = document.getElementById("act");
			actObj.value = "SAVE_PHONE";
            itemListFrm.mainFrm.sf_appFieldValue.value=  document.getElementById("sf_appFieldValue").value;
//            alert(sf_flow)
             itemListFrm.mainFrm.action = "/servlet/com.sino.ies.po.orderform.distribution.CtmsPhForwardServlet?act="+actObj.value;
            itemListFrm.mainFrm.target = "_self";
            itemListFrm.mainFrm.submit();
            //setFrmEnable("mainFrm");
//			document.forms[0].action="/servlet/com.sino.ies.inv.ywypmgr.servlet.CtmsForwardServlet";
//			document.forms[0].submit();
		}catch(ex){
			alert( ex.message );
		}finally{
			enableBtn();
		}
	}
}
function do_Complete_app_validate(){
	return true;
}
function do_Save_app_validate(){
	return true;
}

function do_Complete_app_yy() {
	if(true){
        try{
//			disabledBtn();
            var actObj = document.getElementById("act");
			actObj.value = "SUBMIT_ACTION";
            //setFrmEnable("mainFrm");
//			document.forms[0].action="/servlet/com.sino.ies.inv.ywypmgr.servlet.CtmsForwardServlet";
			document.forms[0].submit();
		}catch(ex){
			alert( ex.message );
		}finally{
			enableBtn();
		}
	}
}
function do_app_flow() {

        SFQueryComplete();
	    if(!Launch_Continue) {
	        if(Error_Msg != "")
	            alert(Error_Msg);
	        return;
	    }
        var str = getNextTask();
        if(str != "") {
	        autoValue(AV_COMPLETE_MASK);
	        if(!validation())
	            return;
	        SFQuerySave();
            if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }
            //var flowDesc = getJsonData( str , "flowHint:");

	        //if( getAttribute3() == "PLAN_APPROVE" || ( getAttribute3() == "NETWORK_APPROVE" && Launch_Code == "C1"  )  ){
	        //	try{
	        //		var tempStr = inputOpinion2( flowDesc );
	        //		document.getElementById("sf_opinion").value = tempStr ;
	        //	}catch(ex){
	        //		alert( ex.message );
	        //	}
	        //}else{
	        //	var tempStr = inputOpinion( flowDesc );
	        //	document.getElementById("sf_opinion").value = tempStr ;
	        //}

	        var flowDesc = getJsonData( str , "flowHint:");
	        var tempStr = inputOpinion( flowDesc );
            document.getElementById("sf_opinion").value = tempStr ;
	        if(document.getElementById("sf_opinion").value == "undefined" || document.getElementById("sf_opinion").value == "" ) {
	            document.getElementById("sf_opinion").value = "";
	            return;
	        }

	        document.getElementById("sf_nextTaskData").value = str;
	        if(finishMessage() == "") {
	            restoreNextTaskData();
	            return;
	        }

            setNextFlowDesc();
	        SFPostSave();
	        if(!Launch_Continue) {
	            if(Error_Msg != "")
	                alert(Error_Msg);
	            return;
	        }

            clearDivRight();
	        saveInfo();
	        isSave = true;
            do_app_yy();
	    }
}
  function do_app_yy() {
	if(true){
        try{
//			disabledBtn();
            var actObj = document.getElementById("act");
			actObj.value = "approve";
            //setFrmEnable("mainFrm");
//			document.forms[0].action="/servlet/com.sino.ies.inv.ywypmgr.servlet.CtmsForwardServlet";
			document.forms[0].submit();
		}catch(ex){
			alert( ex.message );
		}finally{
			enableBtn();
		}
	}
}
/** 模块通用，申请创建流程不用这个方法  **/
function do_Save_app() {
	//alert( "save app" );
	if( true ){
//		var saveBtnObj = document.getElementById("saveBtn");
		try{
//			disabledBtn();
//			saveBtnObj.disabled = true;
			var actObj = document.getElementById("act");
			actObj.value = "SAVE_ACTION";
			//setFrmEnable("mainFrm");
			document.forms[0].submit();
		}catch(ex){
			//alert( ex.message );
		}finally{
//			saveBtnObj.disabled = false;
//			enableBtn();
		}
	}
}

function disabledBtn(){
	var nextBtnObj = document.getElementById("nextBtn");
	nextBtnObj.disabled = true;
}

function enableBtn(){
	var nextBtnObj = document.getElementById("nextBtn");
    if(nextBtnObj){
        nextBtnObj.disabled = false;
    }
}

/**
function doInit(){
 	calculatePrice();
 	autoSelected();
 	autoFromToSpanDis('mtlTable', 9, 10, 21);

 	document.getElementById("procId").value = document.getElementById("sf_procedureid").value ;
 	document.getElementById("progressbar").style.display="none";
}
**/
function doInit(){
	window.focus();
	calculatePrice();
	autoSelected();
	autoFromToSpanDis('mtlTable', 10, 11, 21);
	document.getElementById("progressbar").style.display="none";
}

function showOpinionDlg() {
    var opinionURL = "/servlet/com.sino.sinoflow.servlet.GetSaveStatus?sf_caseID='"
        document.getElementById("sf_caseID").value;

    h = window.screen.height;
    w = window.screen.width;
    alert(h)
    alert(w)
    var f1 = "dialogWidth:" + w
            + ";dialogHeight:" + h
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(opinionURL, null, f1);
}
