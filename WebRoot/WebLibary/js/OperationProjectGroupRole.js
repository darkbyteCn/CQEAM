
var project_Arr;
var group_Arr;
var role_Arr; 
var dept_Arr;
var crs = new clientRowSet();

function getProject(){//获取工程

    crs.modelClassName = "com.sino.sinoflow.user.model.SfProjectModel";
    crs.methodName = "getOptionProjectModel";
    crs.methodParameterName = new Array();
    var res = crs.send_request();
     project_Arr = new Array();
     for(var i = 0; i < res.length; i++){//创建工程的option列表
     	project_Arr[i] = new Option(res[i].PROJECT_NAME,res[i].PROJECT_NAME);
     }
     return project_Arr;
}

function getGroup(projectName, orgId){//获取组别
     crs.modelClassName = "com.sino.sinoflow.user.model.SfGroupModel";
     crs.methodName = "getOptionGroupModel";

     if(!orgId)
        orgId = "";
     crs.methodParameterName = new Array(projectName, orgId);
     var res = crs.send_request();
     group_Arr = new Array();
     for(var i=0;i<res.length;i++){//创建组的option列表
     	group_Arr[i] = new Option(res[i].GROUP_NAME,res[i].GROUP_NAME);
     }
     return group_Arr;
}

 function getDept( orgId){//获取组别
     crs.modelClassName = "com.sino.sinoflow.user.model.SfGroupModel";
     crs.methodName = "getOptionDeptModel";

     if(!orgId)
        orgId = "";
     crs.methodParameterName = new Array( orgId);
     var res = crs.send_request();
     dept_Arr = new Array();
     for(var i=0;i<res.length;i++){//创建组的option列表
     	dept_Arr[i] = new Option(res[i].DEPT_NAME,res[i].DEPT_NAME);
     }
     return dept_Arr;
}
function getRole(projectName, sysAdmin){//获取角色
    crs.modelClassName = "com.sino.sinoflow.user.model.SfRoleModel";
    crs.methodName = "getRoleOptionModel";
    if(sysAdmin){
        crs.methodParameterName = new Array(projectName, sysAdmin);
    } else {
        crs.methodParameterName = new Array(projectName);        
    }
    var res = crs.send_request();
    role_Arr = new Array();
    for(var i=0;i<res.length;i++){//创建角色option列表
    role_Arr[i] = new Option(res[i].ROLE_NAME,res[i].ROLE_NAME);
    }
    return role_Arr;
}

function getUser(){//获取用户
	 crs.modelClassName = "com.sino.sinoflow.user.model.SfUserBaseModel";
     crs.methodName = "displayUsers";
     crs.methodParameterName = new Array();
     var res = crs.send_request();
	 var user_Arr = new Array();
     for(var i=0;i<res.length;i++){//创建角色option列表
     	var idName = res[i].USER_ID+","+res[i].LOGIN_NAME;
     	user_Arr[i] = new Option(res[i].LOGIN_NAME,idName);
     }
     return user_Arr;
}

function add_Option(selectObj,arr){//将选中的选项添加到另一下列拉表中,并返回添加的项的数组列表

	var tempArr = new Array(); 
	var index = 0;
	var option_length = selectObj.options.length;
    var obj = "";
    if(option_length == 0){
		for(var i = 0;i < arr.length; i++){
			selectObj.add(new Option(arr[i],arr[i]));
			tempArr[index++] = arr[i];
            obj = arr[i];
        }
	}else{
		for(var i = 0;i < arr.length; i++){
			for(j = 0;j < option_length;j++){
				if(arr[i] == selectObj.options[j].value){
                    obj = arr[i];
                    break;
				}
				if(arr[i] != selectObj.options[j].value && j == option_length-1){
					selectObj.add(new Option(arr[i],arr[i]));
					tempArr[index++] = arr[i];
                    obj = arr[i];
                }
			}
		}
	}
    option_length = selectObj.options.length;
    for(var i = 0; i < option_length; i++) {
        if(selectObj.options[i].value == obj) {
            selectObj.options[i].selected = true;
        } else {
            selectObj.options[i].selected = false;
        }
    }
    return tempArr;
}


function delete_option(select){//删除选中项，并返回删除的项

	var temp = new Array();
	var index = 0;
	for(var i = 0;i<select.length;i++){
		if(select[i].selected){	
			temp[temp.length] = select[i].value;
			select.remove(i);
			index = i;
			i--;
		}
	}
	if(select.length == 0){
		return temp;
	}else if(index >= select.length){
		select[select.length-1].selected = true;
	}else{
		select[index].selected = true;
	}
	return temp;
}
	
function procedure(){
	crs.modelClassName = "com.sino.sinoflow.model.SfProcedureModel";
    crs.methodName = "getProjectProcedureModel";
    crs.methodParameterName = new Array(mainFrm.projectName.value);
    var res = crs.send_request();
	return res;
}

function getProcedure(){//工程改变时，设置相对应的过程
        
	var res = procedure();
   	mainFrm.procedureName.options.length = 1;
    if(res.length == 0){
    	return;
    }
	for(var i = 0;i<res.length;i++){
		mainFrm.procedureName.add( 
			new Option(res[i].PROCEDURE_NAME,res[i].PROCEDURE_NAME));
	} 
}
	
function getProcedure2(){//工程改变时，设置相对应的过程
    
	var res = procedure();
   	mainFrm.procedureName.options.length = 1;
    if(res.length == 0){
    	mainFrm.procedureName.onchange();
    	return;
    }
	for(var i = 0;i<res.length;i++){
		mainFrm.procedureName.add( 
			new Option(res[i].PROCEDURE_NAME,res[i].PROCEDURE_ID));
	} 
}

function getTask(){//过程改变时，找相应的任务
	crs.modelClassName = "com.sino.sinoflow.model.SfTaskModel";
    crs.methodName = "getProcedureTaskModel";
    crs.methodParameterName = new Array(mainFrm.procedureName.value);
    mainFrm.taskTid.options.length = 1;
    var res = crs.send_request();
      if(res.length == 0){
    	return;
    }
	for(var i = 0;i<res.length;i++){
		mainFrm.taskTid.add(
			new Option(res[i].TASK_NAME,res[i].TID));
	} 
}

function getTask2(){//过程改变时，找相应的任务
	crs.modelClassName = "com.sino.sinoflow.model.SfTaskModel";
    crs.methodName = "getProcedureTaskModel3";
    crs.methodParameterName = new Array(mainFrm.procedureName.value);
    mainFrm.taskTid.options.length = 1;
    var res = crs.send_request();
      if(res.length == 0){
    	return;
    }
	for(var i = 0;i<res.length;i++){
		mainFrm.taskTid.add(
			new Option(res[i].TASK_NAME,res[i].TID));
	}
}

function getApi() { //过程改变时，找相应的接口程序名称
    crs.modelClassName = "com.sino.sinoflow.model.SfApiModel";
    crs.methodName = "getApiNameModel";
    crs.methodParameterName = new Array(mainFrm.projectName.value);
    mainFrm.apiNameS.options.length = 1;
    var res = crs.send_request();
      if(res.length == 0){
        return;
    }
    for(var i = 0;i<res.length;i++){
        mainFrm.apiNameS.add(
            new Option(res[i].API,res[i].API));
    }
}

function JJR(str){//节假日变化，获取相对应的年份
  	crs.modelClassName = "com.sino.sinoflow.model.SfHolidaysModel";
    crs.methodName = "getYear";
    crs.methodParameterName = new Array(str);	
    return crs.send_request();	
}

function NF(str,arg){//年份变化后获取相应的日期
	crs.modelClassName = "com.sino.sinoflow.model.SfHolidaysModel";
    crs.methodName = "getRQ";
    crs.methodParameterName = new Array(str,arg);
    return crs.send_request();
}

function GZSJ(str){//获取详细工作时间
	crs.modelClassName = "com.sino.sinoflow.model.SfWorkHourModel";
    crs.methodName = "getGZSJ";
    crs.methodParameterName = new Array(str);
    return crs.send_request();
}

function changeValue(obj){//改变值，将选中的项的文本，改为值
	for(var i=0;i<obj.length;i++){
		if(obj.options[i].value == obj.value){
			obj.options[i].value = obj.options[i].innerText;
			return;
		}
	}
}

 function plusValue(name){//计算所的值的总和
 	var count = 0;
 	var cb = document.getElementsByName(name);
 	for(var i =0;i<cb.length;i++){
 		if(cb.item(i).checked) count += parseInt(cb.item(i).value);
 	}
 	return count;
 }


