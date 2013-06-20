var extFlowObject = function(){};
var addInfoWin;
var msForm;
var flowDs,departDs,parsonDs;

var TOFLOWNAME = "toflow";
var TODEPARTMENTNAME = "toDepartment";
var TOPARSONNAME = "toParson";
var TPWRITECOMMENTName =  "toWriteComment";
var TOFLOWHEIGHT = 70;
var TODEPARTMENTHEIGHT = 80;
var TOPARSONHEIGHT = 70;
var isFlowMentHide = false;
var isDepartMentHide = false;
var isParsonHide = false;
var isDepartMentDisable = false;
var isParsonDisable = false;
var selectSubmitBtn;
Ext.onReady(function(){
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
   
    flowDs = new Ext.data.ArrayStore({
        data: [],
        fields: ['value','text','jvalue']
    });
    
    departDs = new Ext.data.ArrayStore({
        data: [],
        fields: ['value','text','jvalue']
    });

   parsonDs = new Ext.data.ArrayStore({
        data: [],
        fields: ['value','text']
    });

    selectSubmitBtn = new Ext.Button(
      {
            text: '确定',
	     name : 'selectSubmit',
            handler: function(){
	         do_selectFinish();
            }
        }
    );

    msForm = new Ext.form.FormPanel({
        bodyStyle: 'padding:5px;border-color:#cdec97;backgound-color:#cdec97',
        footerStyle:"background-color:#cdec97;",
	    buttonAlign : 'center',
        items:[{
            xtype: 'multiselect',
            fieldLabel: '请选择下一步流向',
	     labelStyle: "text-align: right;",
            name: TOFLOWNAME,
            id: TOFLOWNAME,
	     width : '100%',
	     height : 70,
         maxSelections : 1,
                     allowBlank : true,
	     isFormField : true,
	     displayField: 'text',
            valueField: 'value',
            store: flowDs,
            ddReorder: true,
	     listeners:{
                "change": function(){
		  	doFlowSelect();
			extFlowObject.HideSelectObj();
	          }                
            }	
        },{
            xtype: 'multiselect',
            fieldLabel: '办理部门',
	     labelStyle: "text-align: right;",
            name: TODEPARTMENTNAME,
            id: TODEPARTMENTNAME,
	     width : '100%',
	     height : 80,
            maxSelections : 1,
            allowBlank : false,
	     isFormField : true,
	     displayField: 'text',
	     disableClass: 'x-item-disabled2',	
            valueField: 'value',
            store: departDs,
            ddReorder: true,
	     listeners:{
                "change": function(){
		  	doGroupSelect();
			extFlowObject.HideSelectObj();
			if (isDepartMentDisable) {
			    extFlowObject.selectAllDepartmentItem(1);
			} 
	          }                
            }	
        },{
            xtype: 'multiselect',
            fieldLabel: '下一办理人',
	     labelStyle: "text-align: right;",
            name: TOPARSONNAME,
            id: TOPARSONNAME,
         width : '100%',
	     height : 70,
            maxSelections : 1,
	     displayField: 'text',
            valueField: 'value',
            allowBlank : false,
	     isFormField : true,
            store: parsonDs,
            ddReorder: true,
	      listeners:{
                "change": function(){
		  	doParticipantsSelect();
			if(isParsonDisable){
                        extFlowObject.selectAllParsonItem(1);   				
			}
	          }                
            }	
        },{
	 	xtype : 'textarea',
		fieldLabel : '请填写意见',
		labelStyle: "text-align: right;",
		name : TPWRITECOMMENTName,
		id: TPWRITECOMMENTName,
		width : '100%',
		height : 100
	 }
	 ],
	 
	 buttons: [selectSubmitBtn,{
            text: '取消',
	     name : 'selectCancel',
            handler: function(){
	         do_selectCancel();
		  extFlowObject.close();
            }
        }
	 ]
    });
    
    addInfoWin = new Ext.Window({
        title: '资产管理系统',
        bodyStyle : 'border-color:#fcffa3;background-color:#cdec97;',
            closable:true,
	     closeAction: 'hide',
            width:500,
            height:460,
            plain:true,
	     modal:true,
	     layout : 'anchor',
	     resizable : false,
	     items: msForm,
	     listeners: {
	     	 beforehide: function(){
		    do_selectCancel();
		}
	     }
        });
});

/**
 * add item from flowItem
 * @param {Object} itemValue
 * @param {Object} itemText
 */
extFlowObject.addFlowItem = function(itemJValue, itemValue,itemText){
   new extFlowObject.addOneFlowItem(TOFLOWNAME,itemJValue, itemValue,itemText);
}

extFlowObject.addOneFlowItem = function(itemName, itemJValue, itemValue, itemText) {
    var toFlowStore = msForm.getForm().findField(itemName).store;
    var record = new Object();
    record.jvalue = itemJValue;
    record.value = itemValue;
    record.text = itemText;
    var records = new Ext.data.Record(record);
    toFlowStore.add(records);
}

/**
 * add item from departmentItem
 * @param {Object} itemValue
 * @param {Object} itemText
 */
extFlowObject.addDepartmentItem = function(itemJValue, itemValue,itemText){
   new extFlowObject.addDepartItem(TODEPARTMENTNAME,itemJValue,itemValue,itemText);
}

/**
 * add item from parsonItem
 * @param {Object} itemValue
 * @param {Object} itemText
 */
extFlowObject.addparsonItem = function(itemValue,itemText){
   new extFlowObject.addItem(TOPARSONNAME,itemValue,itemText);
}

extFlowObject.setselectSubmitStatus = function(status){
	
   if( !status ){
   	selectSubmitBtn.disable();
   }else{
   	selectSubmitBtn.enable();
   }
	
}

/**
 * 
 * @param {Object} itemName
 * @param {Object} itemValue
 * @param {Object} itemText
 */
extFlowObject.addItem = function(itemName,itemValue,itemText){
   var toflowStore  = msForm.getForm().findField(itemName).store;
   var record = new Object();
   record.value = itemValue;
   record.text = itemText;
   var records = new Ext.data.Record(record);
   toflowStore.add(records); 	
}

extFlowObject.addDepartItem = function(itemName, itemJValue, itemValue, itemText) {
    var toDepartStore = msForm.getForm().findField(itemName).store;
    var record = new Object();
    record.jvalue = itemJValue;
    record.value = itemValue;
    record.text = itemText;
    var records = new Ext.data.Record(record);
    toDepartStore.add(records);
}

extFlowObject.setMaxSelection = function(itemName, value) {
    var temp = msForm.getForm().findField(itemName);
    msForm.getForm().findField(itemName).maxSelections = value;
}

/**
 * get flowItem's Count
 */
extFlowObject.GetFlowCount = function(){
   return extFlowObject.GetSelectCount(TOFLOWNAME);	
}

/**
 * get department's Count
 */
extFlowObject.GetDepartmentCount = function(){
   return extFlowObject.GetSelectCount(TODEPARTMENTNAME);	
}

/**
 * get parson's Count
 */
extFlowObject.GetParsonCount = function(){
   return extFlowObject.GetSelectCount(TOPARSONNAME);	
}

extFlowObject.GetSelectCount = function(itemName){
   var count = 0;
   var toflowStore  = msForm.getForm().findField(itemName).store;
   count = toflowStore.getCount();
   return count;
}

/**
 * Get comment's value
 */
extFlowObject.getCommentValue = function(){
   return msForm.getForm().findField(TPWRITECOMMENTName).getValue();	
}

/**
 * Set comment's value
 * @param {Object} value
 */
extFlowObject.setCommentValue = function(value){
   msForm.getForm().findField(TPWRITECOMMENTName).setValue(value);	
}

/**
 * select flowObject's Item 
 * @param {Object} index
 */
extFlowObject.selectFlowItem = function(index){
    msForm.getForm().findField(TOFLOWNAME).view.select(index);	
}

/**
 * select department's Item 
 * @param {Object} index
 */
extFlowObject.selectDepartmentItem = function(index){
    msForm.getForm().findField(TODEPARTMENTNAME).view.select(index);	
}

extFlowObject.selectAllDepartmentItem = function(type){
   var flowObj = msForm.getForm().findField(TODEPARTMENTNAME);  
   if (type == 1) {
       extFlowObject.setMaxSelection(TODEPARTMENTNAME, Number.MAX_VALUE);
   	var val = "";
       var record;
   	for (var i = 0; i < flowObj.view.store.getCount(); i++) {
	    record = flowObj.view.store.getAt(i);
	     val += record.data.value + ",";
	}
	flowObj.setValue(val);
	isDepartMentDisable = true;
   }else {
	isDepartMentDisable = false;
   }
}

extFlowObject.selectAllParsonItem = function(type){
   var flowObj = msForm.getForm().findField(TOPARSONNAME);  
   
   if(type == 1){
       extFlowObject.setMaxSelection(TOPARSONNAME, Number.MAX_VALUE);
      var val = "";
      var record;
      
      for(var i = 0;i < flowObj.view.store.getCount();i++){
      	  record = flowObj.view.store.getAt(i);
      	  val += record.data.value + ",";
      }
      
      flowObj.setValue(val);
      isParsonDisable = true;
   } else {
      isParsonDisable = false;
   }
}

/**
 * select parson's Item 
 * @param {Object} index
 */
extFlowObject.selectparsonItem = function(index){
    msForm.getForm().findField(TOPARSONNAME).view.select(index);	
}

/**
 * Remove all items of flow
 */
extFlowObject.removeAllFlowItem = function(){
   msForm.getForm().findField(TOFLOWNAME).store.removeAll();
}

/**
 * Remove all items of department
 */
extFlowObject.removeAlldepartmentItem = function(){
   msForm.getForm().findField(TODEPARTMENTNAME).store.removeAll();
   extFlowObject.setMaxSelection(TODEPARTMENTNAME, 1);
}

/**
 * Remove all items of parson
 */
extFlowObject.removeAllParsonItem = function(){
   msForm.getForm().findField(TOPARSONNAME).store.removeAll();
   extFlowObject.setMaxSelection(TOPARSONNAME, 1);
}

extFlowObject.getSelectFlowValue = function(){
   var val = "";
   var flowObj = msForm.getForm().findField(TOFLOWNAME);
   var selectionsArray = flowObj.view.getSelectedRecords();
   
   if(selectionsArray.length > 0 ){
   	var record = selectionsArray[0];
	val = record.data.jvalue;
   }
   
   return val;
}

extFlowObject.setSelectFlowValue = function(value){
   var val = "";
   var flowObj = msForm.getForm().findField(TOFLOWNAME);
   var selectionsArray = flowObj.view.getSelectedRecords();

   if(selectionsArray.length > 0 ){
   	var record = selectionsArray[0];
//	val = record.data.value;
    record.data.jvalue = value;
   }

   return val;
}

/**
 * Get selected of department's Text
 */
extFlowObject.getSelectDepartmentText = function(){
   var val = "";
   var flowObj = msForm.getForm().findField(TODEPARTMENTNAME);
   
   var selectionsArray = flowObj.view.getSelectedRecords();
   
//   if(selectionsArray.length > 0 ){
//   	var record = selectionsArray[0];
//	val = record.data.text;
//   }

    for(var i = 0; i < selectionsArray.length; i++) {
        var record = selectionsArray[i];
        if(val == "")
            val = record.data.text;
        else
            val += ";" + record.data.text;
    }
   
   return val;
}

/**
 * Get selected of department's Value
 */

extFlowObject.getSelectDepartmentValue = function(){
   var val = "";
   var flowObj = msForm.getForm().findField(TODEPARTMENTNAME);
   
   if(flowObj.store.getCount() > 0 ){
   	var record = flowObj.view.store.getAt(0);
	val = record.data.jvalue;
   }
   
   return val;
}

/**
 * Get selected of parson's Value
*/
extFlowObject.getSelectparsonValue = function(index,type){
   var val = "";
   var flowObj = msForm.getForm().findField(TOPARSONNAME);
   var selectionsArray = flowObj.view.getSelectedRecords();
   
   var record;
   
   if(selectionsArray.length > 0 ){
	record = selectionsArray[index];
	val = type == 1? record.data.value:record.data.text;
   }
   
   return val;
}

extFlowObject.getSelectedParsonCount = function(){
    var flowObj = msForm.getForm().findField(TOPARSONNAME);
    var selectionsArray = flowObj.view.getSelectedRecords();
    if(selectionsArray != "undefined")
       return selectionsArray.length;
    else
       return 0;	 
}

/**
 * Get selected of parson's Value
*/
extFlowObject.getSelectedParsonValue = function(type){
   var val = "";
   var flowObj = msForm.getForm().findField(TOPARSONNAME);
   var selectionsArray = flowObj.view.getSelectedRecords();
   
   var record;
   
   if(selectionsArray.length > 0 ){
     record = selectionsArray[0];
     val = type == 1? record.data.value:record.data.text;
   }
   
   return val;
}

extFlowObject.setCommentSelect = function(){
   Ext.get(TPWRITECOMMENTName).dom.select();	
}

extFlowObject.show = function(){
   addInfoWin.show();
}

extFlowObject.close = function(){
   addInfoWin.hide();	
}

extFlowObject.HideSelectObj = function(){
   var hide = extFlowObject.hideObj(TODEPARTMENTNAME);
   if(hide){
     if(isDepartMentHide){
     }	else{
          addInfoWin.setHeight(addInfoWin.getHeight() - TODEPARTMENTHEIGHT);   	
     }
     isDepartMentHide = true; 	
   } else{
     if(isDepartMentHide) addInfoWin.setHeight(addInfoWin.getHeight() + TODEPARTMENTHEIGHT);
     isDepartMentHide = false;
   }
     
   hide = extFlowObject.hideObj(TOPARSONNAME);
   if (hide) {
   	if (isParsonHide) {
	}else {
   	   addInfoWin.setHeight(addInfoWin.getHeight() - TOPARSONHEIGHT);
   	}
   	isParsonHide = true;
   }
   else {
     if (isParsonHide){ 
   	 addInfoWin.setHeight(addInfoWin.getHeight() + TOPARSONHEIGHT);
     }
     isParsonHide = false;
   }
}

extFlowObject.hideObj = function(obj){
    var obj = msForm.getForm().findField(obj);
    var objPar = obj.getEl().up(".x-form-item");
    
    if (obj.store.getCount() == 0) {
	objPar.setDisplayed(false);
	return true;	
    }else{
    	objPar.setDisplayed(true);
    }	
    
    return false;
}

extFlowObject.DisplayObj = function(){
    if(isFlowMentHide) {
    } else {
        addInfoWin.setHeight(addInfoWin.getHeight() - TOFLOWHEIGHT);
    }
    isFlowMentHide = true;

   var hide = extFlowObject.hideObj(TODEPARTMENTNAME);
   if(hide){
     if(isDepartMentHide){
     }	else{
          addInfoWin.setHeight(addInfoWin.getHeight() - TODEPARTMENTHEIGHT);
     }
     isDepartMentHide = true;
   } else{
     if(isDepartMentHide) addInfoWin.setHeight(addInfoWin.getHeight() + TODEPARTMENTHEIGHT);
     isDepartMentHide = false;
   }

   hide = extFlowObject.hideObj(TOPARSONNAME);
   if (hide) {
   	if (isParsonHide) {
	}else {
   	   addInfoWin.setHeight(addInfoWin.getHeight() - TOPARSONHEIGHT);
   	}
   	isParsonHide = true;
   }
   else {
     if (isParsonHide){
         addInfoWin.setHeight(addInfoWin.getHeight() + TOPARSONHEIGHT);
     }
     isParsonHide = false;
   }
}


