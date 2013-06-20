function TabBox(tabname, type) {
    var tabbox = this;
    this.tabname = tabname;
    this.lasttab = 1;
    this.tabarray = new Array();
    this.tabPad = null;
    this.itemProcessorArr = new Array();
    this.hasProcessor = false;

    //----保存tab项目数祖----
    this.item = function(tabid, caption, dis) {
        this.tabid = tabid;
        this.caption = caption;
        this.dis = dis;
    };

    //----添加tab项目----
    this.addtab = function(tabid, caption, dis) {
        tabbox.tabarray[tabbox.tabarray.length] = new tabbox.item(tabid, caption, dis);
    };

    //--程序初试化----
    this.init = function() {
        if (tabbox.tabarray.length == 0) {
            alert("没有任何项目");
            return;
        }
        document.write("<div id='" + tabbox.tabname + "div'>");
        tabbox.tabPad = eval("document.all." + tabbox.tabname + "div");
        var tbBoard = document.createElement("table");
        tabbox.tabPad.insertAdjacentElement("beforeEnd", tbBoard);
        tbBoard.style.cssText = "border-collapse: collapse";
        tbBoard.border = "0";
        tbBoard.cellSpacing = "0";
        tbBoard.cellPadding = "0";
        tbBoard.height = "24";
        tbBoard.width = "100%";
        var trRow = tbBoard.insertRow(0);
        trRow.height = "3";

        var tl = 0;
        var tbCell = trRow.insertCell(tl);
        tbCell.id = tabbox.tabname + '_tabbar1';
        tbCell.rowSpan = "2";
        tbCell.className = "tbottom3";
        tbCell.width = "3";
        tbCell.noWrap = true;

        tl++;
        tbCell = trRow.insertCell(tl);
        tbCell.id = tabbox.tabname + '_tabtop1';
        tbCell.noWrap = true;

        for (var i = 1; i < tabbox.tabarray.length; i++) {
            tl++;
            tbCell = trRow.insertCell(tl);
            tbCell.id = tabbox.tabname + '_tabbar' + (i + 1);
            tbCell.rowSpan = "2";
            tbCell.className = "tbottom";
            tbCell.width = "3";
            tbCell.noWrap = true;

            tl++;
            tbCell = trRow.insertCell(tl);
            tbCell.id = tabbox.tabname + '_tabtop' + (i + 1);
            tbCell.noWrap = true;
        }

        tl++;
        tbCell = trRow.insertCell(tl);
        tbCell.id = tabbox.tabname + '_tabbar' + (tabbox.tabarray.length + 1);
        tbCell.rowSpan = "2";
        tbCell.className = "tbottom2";
        tbCell.width = "3";
        tbCell.noWrap = true;

        tl++;
        tbCell = trRow.insertCell(tl);
        tbCell.rowSpan = "2";
        tbCell.width = "100%";
        tbCell.noWrap = true;
        tbCell.style.cssText = "border-bottom:1px #000000 solid;";
        trRow = tbBoard.insertRow(1);
        for (i = 0; i < tabbox.tabarray.length; i++) {
            tbCell = trRow.insertCell(i);
            tbCell.id = tabbox.tabname + '_tabcon' + (i + 1);
            tbCell.className = "lostfouce";
            if (tabbox.tabarray[i].dis == 1) {
                tbCell.innerHTML = "<b>" + tabbox.tabarray[i].caption + "&nbsp;" + "</b>";
            } else {
                tbCell.innerHTML = tabbox.tabarray[i].caption + "&nbsp;";
            }
            tbCell.tabnum = i + 1;
            tbCell.noWrap = true;
            tbCell.onclick = function() {
                tabbox.doclick(this.tabnum);
            };
            tbCell.onmouseover = function() {

            };
            tbCell.onmouseout = function() {
                tabbox.doout(this);
            };
        }
    };
    this.inithidetab = function(num) {
        for (var i = 0; i < tabbox.tabarray.length; i++) {
            eval(tabbox.tabarray[i].tabid).style.display = "none";
        }
        if(!num){
            num = 1;
        }
        tabbox.doclick(num);
    };
    this.doclick = function(num) {
        var preProcessor = null;
        var postProcessor = null;
        if(tabbox.hasProcessor){
            var itemName = tabbox.tabarray[num - 1].tabid;
            for(var i = 0; i < tabbox.itemProcessorArr.length; i++){
                var itemProcessor = tabbox.itemProcessorArr[i];
                if(itemName == itemProcessor.itemName){
                    preProcessor = itemProcessor.preProcessor;
                    postProcessor = itemProcessor.postProcessor;
                    break;
                }
            }
        }
        if(preProcessor){
            preProcessor.apply(this, new Array());
        }
        var lastbar1 = eval(tabbox.tabname + "_tabbar" + this.lasttab);
        var nlasttab = this.lasttab + 1;
        var lastbar2 = eval(tabbox.tabname + "_tabbar" + nlasttab);

        var tpbar1 = eval(tabbox.tabname + "_tabbar" + 1);
        var tpbar2 = eval(tabbox.tabname + "_tabbar" + (tabbox.tabarray.length + 1));

        var tabtop = eval(tabbox.tabname + "_tabtop" + this.lasttab);
        var tabcon = eval(tabbox.tabname + "_tabcon" + this.lasttab);
        tabtop.className = "";
        tabcon.className = "lostfouce";
        lastbar1.className = 'tbottom';
        lastbar2.className = 'tbottom';
        tpbar1.className = 'tbottom3';
        tpbar2.className = 'tbottom2';
        eval(tabbox.tabarray[this.lasttab - 1].tabid).style.display = "none";

        lastbar1 = eval(tabbox.tabname + "_tabbar" + num);
        var nnum = num + 1;
        lastbar2 = eval(tabbox.tabname + "_tabbar" + nnum);
        tabtop = eval(tabbox.tabname + "_tabtop" + num);
        tabcon = eval(tabbox.tabname + "_tabcon" + num);
        lastbar1.className = 'tleft';
        lastbar2.className = 'tright';
        tabtop.className = "ttop";
        tabcon.className = "getfouce";
        eval(tabbox.tabarray[num - 1].tabid).style.display = "block";
        this.lasttab = num;

        if(postProcessor){
            postProcessor.apply(this, new Array());
        }
    };

    this.doover = function(obj) {
        if (obj.className == "lostfouce") {
            obj.className = "lostfouce_over";
        }
    };

    this.doout = function(obj) {
        if (obj.className == "lostfouce_over") {
            obj.className = "lostfouce";
        }
    };

    this.itemProcessor=function(itemName, preProcessor, postProcessor){
        this.itemName = itemName;
        this.preProcessor = preProcessor;
        this.postProcessor = postProcessor;
    };

    this.addItemProcessor=function(itemName, preProcessor, postProcessor){
        if(!itemName){
            alert("必须指定Tab选项的名称");
            return;
        }
        var tabItemArr = tabbox.tabarray;
        if(tabItemArr){
            var foundItem = false;
            for(var i = 0; i < tabItemArr.length; i++){
                var item = tabItemArr[i];
                if(item.tabid == itemName){
                    foundItem = true;
                    break;
                }
            }
            if(!foundItem){
                alert("不存在名称为“" + itemName + "”的Tab选项卡");
                return;
            }
        }
        if(!(preProcessor) && !(postProcessor)){
            alert("选项卡的前置或后置处理器必须指定一个");
            return;
        }
        tabbox.itemProcessorArr[tabbox.itemProcessorArr.length] = new tabbox.itemProcessor(itemName, preProcessor, postProcessor);
        tabbox.hasProcessor = true;
    };
}
