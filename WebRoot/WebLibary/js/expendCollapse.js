
var cPath = "../images/buttonbar/Collapse.gif";
var ePath = "../images/buttonbar/Expand.gif";
var ep = ePath.substring(ePath.lastIndexOf("/")+1);
var cp = cPath.substring(cPath.lastIndexOf("/")+1);

 function instead(obj){//更改图标
    var op = obj.src.substring(obj.src.lastIndexOf("/")+1);
    var path = (op == ep) ? cPath : ePath;  
    obj.src = path;
    return path == ePath ? ep : cp;
 }
 
 function displayOrNone(obj,imgName){
    obj.style.display = (imgName == ep) ? "" : "none";
 }
 
 function expendOrColl(imgObj,childTrId){//一级
    var os = instead(imgObj);
    var childs = document.getElementsByName(childTrId);
    for(var i=0;i<childs.length;i++){
        displayOrNone(childs.item(i),os);
    }    
 }
         
 function expendOrColl2(imgObj,trId){//二级
    var os = instead(imgObj);
    for(var i=1;;i++){
        var childTrId = trId+"_"+i;
        var childObj = document.getElementById(childTrId);
        if(childObj == null) return;
        displayOrNone(childObj,os);
        
        childTrId = childTrId+"_tr";
        var childs = document.getElementsByName(childTrId);
        var img = childObj.getElementsByTagName("img")[0];
        var cs = img.src.substring(img.src.lastIndexOf("/")+1);
        for(var j=0;j<childs.length;j++){
            if(os == ep){
               displayOrNone(childs.item(j),cs);
            }else{
               displayOrNone(childs.item(j),os);
            }
        }
        
    }  
 }
     
 function operate(tag,path){//一级
 	for(i=1;;i++){
 		var trId = "tr_"+i;
 		var obj = document.getElementById(trId);
 		if(obj == null) return;
        var img = obj.getElementsByTagName("img")[0];
        img.src = path;
        var trId2 = trId + "_tr";
        var childObj = document.getElementsByName(trId2);
        for(var j=0;j<childObj.length;j++){
            childObj.item(j).style.display=tag;
       	}
 	}
 }
 
 function operate2(tag,path){//二级
    for(var i=1;;i++){
        var trId = "tr_" + i;
        var obj = document.getElementById(trId);
        if(obj == null) return;
        var img = obj.getElementsByTagName("img")[0];
        img.src = path;
        for(var j=1;;j++){
            var trId2 = trId + "_"+j;
            var childObj = document.getElementById(trId2);
            if(childObj == null) break;
            childObj.style.display = tag;
            var img = childObj.getElementsByTagName("img")[0];
            img.src = path;
            var trId3 = trId2 + "_tr";
            var childs = document.getElementsByName(trId3);
            for(var k=0;k<childs.length;k++){
                childs.item(k).style.display = tag;
            }
        }       
    }
 }
 
 function expendAll(){//展开全部
    operate("",ePath);
 }
 
 function collAll(){//折叠全部
    operate("none",cPath);
 }
 
 function expendAll2(){//二级展开全部
    operate2("",ePath);
 }
 
 function collAll2(){//二级折叠全部
    operate2("none",cPath);
 }
      
 function del(actionStr){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = actionStr;
					mainFrm.submit();
					return;
				}else{
					return;
				}	
			}
		}
	}

function sdAll(cbName){//全选或反选所有复选框
	var cn = document.getElementsByName(cbName);
	for(var i=0;i<cn.length;i++){
		if(cn.item(i).checked == false){
			for(var j=0;j<cn.length;j++){
				cn.item(j).checked = true;
			}
			return;
		}
		if(i == cn.length-1){
			for(var j=0;j<cn.length;j++){
				cn.item(j).checked = false;
			}
			return;
		}
	}
}
    