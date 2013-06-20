<%@ page contentType="text/html; charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
  <head>
    <title>(部门/公司/副总)选择...</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
    <link type="text/css" rel="stylesheet" href="/WebLibary/js/xyTree/xtree.css" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/cms_css/cms_css.css">
     
    <script src="/WebLibary/js/xyTree/TreeNormal.js"></script>
    <script src="/WebLibary/js/xyTree/NodeNormal.js"></script>
    <script src="/WebLibary/js/xyTree/DivTreeNormal.js"></script>

    <script src="/WebLibary/js/xyTree/DivTree.js"></script>
    <script src="/WebLibary/js/xyTree/Tree.js"></script>
    <script src="/WebLibary/js/xyTree/Node.js"></script>
  </head>
  
<body>
<%=request.getAttribute("deptsTree")%>
<script type="text/javascript">
var currentTreeDept,selectOriginalType = 0;
var currentObj = null;
var STUFF_TYPE = 3;

function init(){
  document.getElementById('ceshi1').appendChild(treeDept.div);
  treeDept.init(huidiao_Example1_Node, huidiao_Example1_RootNode); 
  
  //validate Wether treeParson tree is existent
  if(typeof(treeParson) == "undefined"){
     var selectOption = document.getElementById("selectOption");
     
     if(typeof(selectOption) != "undefined"){
         selectOption.style.display = "none";
     }else{
         alert("selectOption Object was undefined!");
     }
     currentTreeDept = treeDept;
  }else{
     document.getElementById('personTree').appendChild(treeParson.div);
     treeParson.init(huidiao_Example1_Node, huidiao_Example1_RootNode);
     //Setup default Current Tree
     currentTreeDept = treeParson;
     onSelect();
     
  }
   
  initOfValues();
}

function initOfValues(){
  if(currentObj == null){
    currentObj = window.dialogArguments;
    selectOriginalType = currentObj.selectType;
  }else{} 
}

function createTitle(titleValue){
    var mytitle = document.createElement("TITLE");
    mytitle.innerHTML = titleValue;      
    document.documentElement.childNodes[0].appendChild(mytitle);
}

function huidiao_Example1_Node(node){
  var retArray = new Array();
  initOfValues();
  var obj = currentObj;
   
  if(obj != undefined && obj.optionType == 1){
      var value = node.id;
          
      var deptObj = getValArr(value);
      if(deptObj == null) return;
      retArray.push(deptObj);
      window.returnValue = retArray;
      closeWindow();
  }else{}
}

function huidiao_Example1_RootNode(){
}

function selectDept(){
   initOfValues();
   var obj = currentObj;          
   var retArray = new Array();
     
   if(obj != undefined && obj.optionType == 1){
      window.returnValue = null;
   }else{
      var selectDepts = currentTreeDept.getNodesMoji();
      
      if(selectDepts.length == 0) {
         window.returnValue = null;
      }else{
         var valArr,selObj;
         var isStuff = false;
         
         for(i = 0;i < selectDepts.length;i++){
           if(obj.selectType == undefined || (obj.selectType == 1)){ //current option is for organization
               deptObj = getValArrOfOrg(selectDepts[i].id);
            }else if(obj.selectType == STUFF_TYPE){ ////current option is for Stuffs
                selObj = selectDepts[i].id;
                varArr = selObj.split(",");
                
                if(varArr.length >= 4 && varArr[3] == 'stuff'){ //current Option Is for Stuffs
                   deptObj = getValOfStuff(selObj);  
                }else{ 
                   deptObj = getValArr(selObj);
                }                              
            }else{ //current option is for department
               deptObj = getValArr(selectDepts[i].id);
            }
           
            retArray.push(deptObj);
         }
         
         window.returnValue = retArray;
      }     
   }
   
   closeWindow();
}

function getValOfStuff(selectOrg){
    if(selectOrg == undefined || selectOrg == '' || selectOrg == null){ 
       return null;
    }
    
    if(typeof(selectOrg) != 'string') return null;
    var valArr = selectOrg.split(",");
    if(valArr.length < 3) return null;
    var deptObj = new Object();
    deptObj.loginName = valArr[0];
    deptObj.deptName = valArr[1];
    deptObj.username = valArr[2];
    deptObj.retType = "person";
    return deptObj;
}

function getValArrOfOrg(selectOrg){
    if(selectOrg == undefined || selectOrg == '' || selectOrg == null){ 
       return null;
    }
    if(typeof(selectOrg) != 'string') return null;
    var valArr = selectOrg.split(",");
    if(valArr.length < 3) return null;
    
    var deptObj = new Object();
    deptObj.orgId = valArr[0];
    deptObj.orgName = valArr[1];
    deptObj.orgCode = valArr[2];
   
    return deptObj;
}

function getValArr(selectDept){    
    if(selectDept == undefined || selectDept == '' || selectDept == null){ 
       return null;
    }
   
    if(typeof(selectDept) != 'string') return null;
    var valArr = selectDept.split(","); 
    if(valArr.length < 7){
        return null;
    }else{}
    
    var deptObj = new Object();
    deptObj.deptId = valArr[0];
    deptObj.deptName = valArr[1];
    deptObj.orgId = valArr[2];
    deptObj.orgName = valArr[3];
    deptObj.groupId = valArr[4];
    deptObj.groupName = valArr[5];
    deptObj.deptProperty = valArr[6];
    
    return deptObj;
}

function closeWindow(){
  window.opener=null;
  window.close();
}

function onSelect(){
  var deptIdDiv = document.getElementById("deptId");
  var personIdDiv = document.getElementById("personId");
  var selectItems = document.getElementsByName("selectDept");
  
  if(!typeof(selectItems)){
      alert("Names of selectDept are undefined!");
      return;
  }else{
	  if(selectItems[1].checked){
	      deptIdDiv.style.display = "block";
	      personIdDiv.style.display ="none";
	      currentTreeDept = treeDept;
	      currentTreeDept.selectType = selectOriginalType;
	  }else{
	      deptIdDiv.style.display = "none";
	      personIdDiv.style.display ="block";
	      currentTreeDept = treeParson;
	      currentTreeDept.selectType = STUFF_TYPE;
	  }
  }
}
document.onkeydown = keyDown; 
function keyDown(){
 	var e=event.keyCode; 
  	if( e ==27){
	    self.close();
  	} 
}
window.onload = init;
</script>
<body style="font-family: 'Microsoft YaHei', '宋体', 'Segoe UI', sans-serif;FONT-SIZE: 12px;">
<div id="selectOption">
    <input type="radio" name="selectDept" id="selectDeputy" value="0" checked onclick="onSelect();"/>选择副总&nbsp;
    <input type="radio" name="selectDept" id="selectDept" value="1"  onclick="onSelect();"/>选择部门  
</div>
    
<div id="deptId" style="top:10px;left:2px">
    <table width="100%" height="95%">
    <tr height="98%">
      <td width="100%" height="100%">
      <div id="ceshi1" style="width:100%; height:100%;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;">
      </div>
      </td>
    </tr>
    
    <tr height="2%">
      <td>
      <input name="button" type="submit" class="but4"  align="left" id="button" value="确定" onclick="selectDept();"/>
      &nbsp;&nbsp;<input name="button" type="submit" class="but4"  align="left" id="button2" value="取消" onclick="closeWindow();"/>
      </td>
    </tr>
</table>
</div>

<div id="personId" style="display:none;top:10px;left:2px">
    <table width="100%" height="95%">
    <tr height="98%">
      <td width="100%" height="100%">
      <div id="personTree" style="width:100%; height:100%;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;">
      </div>
      </td>
    </tr>
    
    <tr height="2%">
      <td>
      <input name="button" type="submit" class="but4"  align="left" id="button3" value="确定" onclick="selectDept();"/>
      &nbsp;&nbsp;<input name="button" type="submit" class="but4"  align="left" id="button4" value="取消" onclick="closeWindow();"/>
      </td>
    </tr>
  </table>  
</div>
 </body>
</html>

