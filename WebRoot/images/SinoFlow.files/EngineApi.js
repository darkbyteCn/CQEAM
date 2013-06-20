/*hppage status="protected"*/
function hp_d10(s){return s}
//****SinoFlow JavaScipt Bridge Lib****
//

var engine;
function GetEngine()
{
	LoadEngine();
	return engine;
}
function LoadEngine()
{
	var openwin;
	if(!eval(engine)){
		if(window.opener)
		{
	              engine = window.opener.parent.sinoapi.document.api;
		}else{
			if(window.parent.sinoapi)
			{
				engine = window.parent.sinoapi.document.api;
			}else{
				engine=null;
			}
		}
	}
}
function SetEngine(newengine)
{
 	if(eval(newengine)){
 		engine = newengine;
 	}
}
function GetLastEngineError()
{
	if(eval(engine))
		return engine.GetLastErrorMsg();
}
function InitPW(url, apppath, username)
{
	if(eval(engine)){
		window.status = "SinoFlow 正在初始化，请稍候 ...";
		engine.InitPW(url, apppath, username);
		window.status = "";
	}
}

function GetApppath()
{
	if(eval(engine)) 
		return engine.Apppath;
}
function GetPrevurl()
{
	if(eval(engine))
		return engine.Prevurl;
}
function GetServer()
{
	if(eval(engine))
		return engine.Server;
}
function GetUserName()
{
	if(eval(engine))
		return engine.Username;
}
function GetAllProjectNames()
{
	if(eval(engine))
		return engine.GetAllProjectNames();
}
function GetAllProcedureNames(projname)
{
	if(eval(engine))
		return engine.GetAllProcedureNames(projname);
}
function GetAllTaskNames(projname, procname)
{
	if(eval(engine))
		return engine.GetAllTaskNames(projname, procname);
}
function GetAllFlowNames(projname, procname)
{
	if(eval(engine))
		return engine.GetAllFlowNames(projname, procname);
}
function GetAllUsers(projname)
{
	if(eval(engine))
		return engine.GetAllUsers(projname);
}
function GetAllGroups(projname)
{
	if(eval(engine))
		return engine.GetAllGroups(projname);
}
function GetAllRoles(projname)
{
	if(eval(engine))
		return engine.GetAllRoles(projname);
}
function GetUserGroups(projname, username)
{
	if(eval(engine))
		return engine.GetUserGroups(projname, username);
}
function GetUserRoles(projname, username)
{
	if(eval(engine)){
		return engine.GetUserRoles(projname, username);
        }
}
function GetAllUsersByGroupAndRole(projname,groupname,rolename)
{
	if(eval(engine))
		return engine.GetAllUsersByGroupAndRole(projname,groupname,rolename);
}

function GetTaskGroup(projname, procname, taskname)
{
	if(eval(engine))
		return engine.GetTaskGroup(projname, procname, taskname);
}
function GetTaskRole(projname, procname,taskname)
{
	if(eval(engine))
		return engine.GetTaskRole(projname, procname, taskname);
}
function OpenProcedureMap(projname, procname)
{
	if(eval(engine))
	{
		
		FlowName=procname;
		
		url="/sinoFlow/sinoact.nsf/(SinoViewFlowGraph)?openagent&flofile=url"
		   +"&curTask=0"
		   +"&FromTask=0"
		   +"&FlowName="+escape(FlowName)
		   +"&caseid=0";

		window.open(url ,'查阅流程','top=0,left=0,width=1020,height=750,scrollbars=no,resizable=no,center=yes');
		
	}
	//if(eval(engine))
		//return engine.OpenProcedureMap(projname, procname);
}
function NewApplication()
{
	if(eval(engine))
		return engine.NewApplication();
}
function NewApplicationEx(appname)
{	
	if(eval(engine))
	{
		return engine.NewAppStart(appname);
	}else{
		alert('not find engine');
	}
}
function ShowAlertInfo(actid)
{
	if(eval(engine))
		return engine.ShowSpeedInfo(actid);
}
function TakeBack(actid)
{
	if(eval(engine))
		return engine.TakeBack(actid, document.URL);
}
function OpenCase(actid)
{
	if(eval(engine))
		return engine.OpenApplication(actid, document.URL);
}
function OpenMail()
{
	if(eval(engine))
		return engine.OpenMail();
}
function GetAllApplnames()
{
	if(eval(engine))
		return engine.GetAllApplnames();
}
function GetAllApplnamesWithFlow()
{
	if(eval(engine))
		return engine.GetAllApplnamesWithFlow();
}
function GetAllApplnamesNoFlow()
{
	if(eval(engine))
		return engine.GetAllApplnamesNoFlow();
}
function GetAllApplnamesByProcname(procname)
{
	if(eval(engine))
		return engine.GetAllApplnamesByProcname(procname);
}


function OpenApplication(actid)
{
	engine.OpenApplication(actid, document.URL);
}
function ShowSpeedInfo(unid)
{
	engine.ShowSpeedInfo(unid);
}
function TakeBack(actid)
{
	if (engine.TakeBack(actid, document.URL))
		location.reload(true);
}
function GetServerTime()
{
	if(eval(engine))
		return engine.GetServerTime();
}
function GetSysNo(NoName)
{
	if(eval(engine)){
		return engine.GetSysNo(NoName);
	}
}
function SignAct(actid)
{
	if(engine.SignAct(actid, "", ""))
		location.reload(true);
}
function SignActs()
{
	var listform=document.listform;
	var ServerTime = engine.GetServerTime();
	for (listone in listform) {
		form=listform[listone];
		if(navigator.appVersion.substring(0,1)=="2")
			s=form.name
		else
			s=listone;
		if(s==null) break;
		if(s.substring(0,5)=="msgid" ) {
			if(form.status) engine.SignAct(form.value, "", ServerTime);
		}
	}
	location.reload(true);
}
function ShowMsgInfo(actid)
{
	if(eval(engine))
		return engine.ShowMsgInfo(actid);
}
function ShowSpeedInfo(actid)
{
	if(eval(engine))
		return engine.ShowSpeedInfo(actid);
}
function SuspendAct(actid, desc)
{
	if(eval(engine))
		return engine.SuspendAct(actid, desc);
}
function UnSuspendAct(actid, desc)
{
	if(eval(engine))
		return engine.SuspendAct(actid, desc);
}
function SuspendActs()
{
	var listform=document.listform;
	for (listone in listform) {
		form=listform[listone];
		if(navigator.appVersion.substring(0,1)=="2")
			s=form.name
		else
			s=listone;
		if(s==null) break;
		if(s.substring(0,5)=="msgid" ) {
			if(form.status) engine.SuspendAct(form.value, "");
		}
	}
	location.reload(true);
}
function UnSuspendActs()
{
	var listform=document.listform;
	for (listone in listform) {
		form=listform[listone];
		if(navigator.appVersion.substring(0,1)=="2")
			s=form.name
		else
			s=listone;
		if(s==null) break;
		if(s.substring(0,5)=="msgid" ) {
			if(form.status) engine.UnSuspendAct(form.value);
		}
	}
	location.reload(true);
}
function OpenWindow(url, name, prop)
{
	newwindow = window.open("",name,prop);
	newwindow.opener = window;
	newwindow.location.href = url;
}
function doActSignActs()
{
	var arr=GetSelectedKey();

	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选准条目后签收!");
		return;
	}
        if(!confirm('确定要批签收吗?'))
 		return;

	var ServerTime = engine.GetServerTime();        

 	if(IsSingleKey(arr)){
                engine.SignAct(arr,"", ServerTime);
              }else{   

alert(arr.length);
               for(i=0;i<arr.length;i++)
	       {
alert('bbbb='+arr[i]);
              	engine.SignAct(arr[i],"", ServerTime);
alert('cccccccc');
      	       }
        }  
alert('c');
		    location.reload(true);
	
}
function doActSuspend()
{

	var arr=GetSelectedKey();

	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选准条目后暂停!");
		return;
	}
	
	if(!confirm('确定要暂停条目吗?'))
 		return;
         
        if(IsSingleKey(arr)){
               	engine.SuspendAct(arr,"");
        }else{   
          	for(i=0;i<arr.length;i++) for(i=0;i<arr.length;i++)
            	  engine.SuspendAct(arr[i],"");
         }
	        location.reload(true);
}
function doActUnsuspend(){
	
        var arr=GetSelectedKey();
	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选准条目后继续!");
		return;
	}
        if(!confirm('确定要继续办理条目吗?'))
 		return;
        if(IsSingleKey(arr)){
               	engine.UnSuspendAct(arr);
         }else{ 
              for(i=0;i<arr.length;i++)
               engine.UnSuspendAct(arr[i]);
	}
        location.reload(true);

}

if(!eval(engine)){
	if(window.opener){
	    if (window.opener.parent.sinoapi)	
              engine = window.opener.parent.sinoapi.document.api;
            else 
              engine=null;
	}	
	else if(window.parent.sinoapi){
		engine = window.parent.sinoapi.document.api;
	}else
	{
		engine=null;
	}
}


