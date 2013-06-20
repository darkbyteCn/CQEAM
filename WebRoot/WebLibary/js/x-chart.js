
Histogram = function(data,width,height) {
    this.init(data,width,height);
};

Histogram.prototype = {
    
    id:null,
    
    _data:null,
    
    _thick:null,
    
    _pillar_width:null,
    
    _width:null,
    
    _height:null,
    
    _type:null,
    
    _color:null,
    
    _el:null,
    
    _type:null,
    
    _title:null,
    
    _nameLabel:null,
    
    _valueLabel:null,
    
    init: function(data,width,height) {
        
        this._data = data;
        this._width = width;
        this._height = height-35; 
        
        this._thick = 20;  // 默认的厚度和宽度
        
        
        // 默认的两组颜色
        var tmdColor1 = new Array();
        tmdColor1[0] = "#ffbbbb";
        tmdColor1[1] = "#d1ffd1";
        tmdColor1[2] = "#ffe3bb";
        tmdColor1[3] = "#cff4f3";
        tmdColor1[4] = "#d9d9e5";
        tmdColor1[5] = "#ffc7ab";
        tmdColor1[6] = "#ecffb7";
        
        var tmdColor2 = new Array();
        tmdColor2[0] = "#ff0000";
        tmdColor2[1] = "#00ff00";
        tmdColor2[2] = "#ff9900";
        tmdColor2[3] = "#33cccc";
        tmdColor2[4] = "#666699";
        tmdColor2[5] = "#993300";
        tmdColor2[6] = "#99cc00";
        
        this._color = new Array(tmdColor1,tmdColor2);
    },
    
    resize:function(width,height){
        this._width = width;
        this._height = height;
        this.draw(this.getEl(),this._type);
    },
    
    getMax:function(){
        var max = 0;
        for(var i = 0;i<this._data.length;i++) {
            if(max<parseInt(this._data[i].value)) {
                max = parseInt(this._data[i].value);
            }
        }
        return max;
    },
    
    getInterval:function(temp1){
        temp1 = parseInt(temp1+temp1/50)
        var temp2,temp3;
        if(temp1>9) {  
            temp2 = temp1.toString().substr(1,1)
            if(temp2>4) {
                temp3 = (parseInt((temp1/(Math.pow(10,(temp1.toString().length-1)))).toString())+1)*Math.pow(10,(temp1.toString().length-1));
            }
            else {
                temp3 = (parseInt((temp1/(Math.pow(10,(temp1.toString().length-1)))).toString())+0.5)*Math.pow(10,(temp1.toString().length-1));
            } 
        }else {
            if(temp1>4)
                temp3 = 10; 
            else 
                temp3 = 5;
        }
        return new Array(temp2,temp3);
    },
    
    setPillarWidth:function (pw){
        this._pillar_width = pw;
    },
    
    setTitle:function(title){
        this._title = title;
    },
    
    setNameLabel:function(nl){
        this._nameLabel = nl;
    },
    
    setValueLabel:function(vl){
        this._valueLabel = vl;	
    },
    
    getHtmlB:function(){
        
        var rtn = '';
        
        var line_color = "#69f";
        
        // 距离左边宽度
        var left_width = 60;
        if(!this._pillar_width){
            this._pillar_width = 15;
        }
        var length = this._thick/2;
        var total_no = this._data.length;
        var temp1 = this.getMax();
        
        var temp2,temp4,temp4;
        
        var space = 30;
        
        temp1 = parseInt(temp1.toString());
        
        var interval = this.getInterval(temp1);
        temp2 = interval[0];
        temp3 = interval[1];
        temp4=0;
        
        if(this._title){
            rtn += '<div style="width:'+this._width+';height:20px;text-align:center" class="title">'+this._title+'</div>';
            space += 20;
        }
        if(this._valueLabel){
            space += 20;
        }
        var nl_length = 0;
        if(this._nameLabel){
            nl_length += 15;
            rtn += '<div class="v_label" style="display:inline;layout-flow:vertical-ideographic;padding-top:20px;height:'+(this._height-space)+'px;letter-spacing:5px;">'+this._nameLabel+'</div>';
        }
        this._width -= nl_length;
        rtn += '<v:group style="WIDTH:'+this._width+'px;HEIGHT:'+this._height+'px" coordsize="'+this._width+','+this._height+'">';
        rtn += "<v:rect style='position:absolute;left:" + (left_width) + "px;top:0px;width:" + (this._width-left_width) + "px;height:" + (this._height-space) + "px;z-index:-1'  fillcolor='#9cf' stroked='f'>"; // 蓝色背景
        rtn += "<v:fill rotate='t' angle='-45' focus='100%' type='gradient'/>"; // 背景渐渐变化
        rtn += "</v:rect>";
        rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width) + "," + (this._height-space) + "' to='" + (this._width) + "," + (this._height-space) + "'/>";// 横坐标
        rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width) + ",0' to='" + (left_width) + "," + (this._height-space) + "'/>"; // 纵坐标
        
        var table_space = (this._height - space - this._pillar_width * total_no) / total_no;
        rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width+length) + "," + (this._height-length-space) + "' to='" + (this._width) + "," + (this._height-length-space) + "' strokecolor='" + line_color + "'/>"; // 三维图横线
        
        for(var i=0;i<=(this._width-left_width)-1;i += (this._width-left_width)/5) {
            temp4 = temp4 + temp3 / 5;
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width+i) + "," + (this._height-length-space) + "' to='" + (left_width+length+i) + "," + (this._height-space) + "' strokecolor='" + line_color + "'/>"; // 横坐标正斜线 /
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width+length+i) + ",0' to='" + (left_width+length+i) + "," + (this._height-length-space) + "' strokecolor='" + line_color + "'/>"; // 图中竖线
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width+i+(this._width-left_width)/5) + "," + (this._height-space) + "' to='" + (left_width+i+(this._width-left_width)/5) + "," + (this._height+15-space) + "'/>";
            rtn += "<v:shape style='position:absolute;left:" + (i+(this._width-left_width)/5) + ";top:" + (this._height-space) + ";width:" + left_width + ";height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right'>" + temp4 + "</td></tr></table></v:textbox>";
            rtn += "</v:shape>";
        }
        
        for(var i=0;i<total_no;i++) {
            var temp_space = table_space/2 + table_space * i + this._pillar_width * i;
            // 柱子
            rtn += "<v:rect"+this.getAnchorAttributes(this._data[i]) + " style='position:absolute;left:" +left_width+"px;top:"+ temp_space+"px;width:" + (this._width-left_width) * (this._data[i].value / temp3) + "px;height:" + this._pillar_width + "px;z-index:1' fillcolor='" + this._color[1][i] + "'>";
            rtn += "<v:fill color2='" + this._color[0][i] + "' rotate='t' angle='-90' focus='100%' type='gradient'/>";
            rtn += "<o:extrusion v:ext='view' backdepth='" + this._thick + "pt' color='" + this._color[1][i] + "' on='t'/>";
            rtn += "</v:rect>";
            //alert(rtn);
            rtn += "<v:shape style='position:absolute;left:" + (left_width + (this._width-left_width) * (this._data[i].value / temp3) + this._thick / 3) + "px;top:" + (temp_space-5) + "px;width:" + (table_space + 15) + "px;height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><div style='text-align:left'>" + this._data[i].value + "</div></v:textbox>";
            rtn += "</v:shape>";
            rtn += "<v:shape style='position:absolute;left:0px;top:" + (temp_space) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><div>" + this.getLabel(this._data[i]) + "</div></v:textbox>";
            rtn += "</v:shape>";
        }
        rtn += '</v:group> ';
        
        if(this._valueLabel){
            rtn += '<div style="width:'+this._width+';height:20px;text-align:center" class="h_label">'+this._valueLabel+'</div>';
        }
        this._width += nl_length;
        return rtn;
        
    },
    
    getHtmlA:function(){
        
        var rtn = '';
        
        var line_color = "#69f";
        
        // 距离左边宽度
        var left_width = 25;
        var space = 30;
        
        if(!this._pillar_width){
            this._pillar_width = 30;
        }
        var length = this._thick/2;
        
        var total_no = this._data.length;
        
        var temp1 = this.getMax();
        
        var temp2,temp4,temp4;
        
        
        if(temp1.toString().length>4){
            left_width += 5*(temp1.toString().length-4);
        }
        
        temp1 = parseInt(temp1.toString());
        
        var interval = this.getInterval(temp1);
        temp2 = interval[0];
        temp3 = interval[1];
        temp4=temp3;
        
        if(this._title){
            rtn += '<div style="width:'+this._width+';height:20px;text-align:center" class="title">'+this._title+'</div>';
            space += 20;
        }
        if(this._nameLabel){
            space += 10;
        }
        var nl_length = 0;
        if(this._valueLabel){
            nl_length += 15;
            rtn += '<div class="v_label" style="display:inline;padding-top:20px;height:'+(this._height-space)+'px;width:15px;">'+this._valueLabel+'</div>';
        }
        rtn += '<v:group style="WIDTH:'+this._width+'px;HEIGHT:'+this._height+'px" coordsize="'+this._width+','+this._height+'">';
        rtn += "<v:rect style='position:absolute;left:" + (left_width) + "px;top:" + 0 + "px;width:" + (this._width-left_width) + ";height:" + (this._height-space) + ";z-index:-1' fillcolor='#9cf' stroked='f'><v:fill rotate='t' angle='-45' focus='100%' type='gradient'/></v:rect>";
        rtn += "<v:line style='position:absolute;left:0;top:0;flip:y;z-index:-1' from='" + (left_width) + "," + (this._height-space) + "' to='" + (this._width) + "," + (this._height-space) + "'/>";
        rtn += "<v:line style='position:absolute;left:0;top:0;flip:y;z-index:-1' from='" + (left_width) + "," + 0 + "' to='" + (left_width) + "," + (this._height-space) + "'/>";
        
        var table_space = (this._width-left_width-this._pillar_width*total_no)/total_no;
        rtn += "<v:line style='position:absolute;left:0;top:0;flip:y;z-index:-1' from='" + (left_width+length) + ","+ 0 + "' to='" + (left_width+length) + "," + (this._height-length-space) + "' strokecolor='" + line_color + "'/>";
        
        for(var i=0;i<=(this._height-1-space);i+= (this._height-space)/5) {
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width)+ "," + (this._height-length-i-space) + "' to='" + (left_width+length) + "," + (this._height-i-space) +"' strokecolor='" + line_color + "'/>";
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width+length) + "," + (this._height-length-i-space) + "' to='" + (this._width) + "," + (this._height-length-i-space) + "' strokecolor='" + line_color + "'/>";
            rtn += "<v:line style='position:absolute;flip:y;z-index:-1' from='" + (left_width-15) + "," + (i) + "' to='" + (left_width) + "," + (i) + "'/>";
            
            rtn += "<v:shape style='position:absolute;left:0px;top:" + (i) + "px;width:" + left_width + "px;height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><table cellspacing='3' cellpadding='0' width='100%' height='100%'><tr><td align='right' >" + temp4 + "</td></tr></table></v:textbox></v:shape>";
            temp4 = temp4-temp3/5;
        }
        
        for(var i=0;i<total_no;i++) {
            
            var temp_space = table_space / 2 + table_space * i + this._pillar_width * i;  
            rtn += "<v:rect "+ this.getAnchorAttributes(this._data[i]) + " style='position:absolute;left:" + (temp_space+left_width) + "px;top:"+ ((this._height-space) * (1 - (this._data[i].value / temp3))) + "px;width:" + this._pillar_width + "px;height:" + ((this._height-space) * (this._data[i].value / temp3)) + "px;z-index:1' fillcolor='" + this._color[1][i] + "'>";
            rtn += "<v:fill color2='" + this._color[0][i] + "' rotate='t' type='gradient'/>";
            rtn += "<o:extrusion v:ext='view' backdepth='" + this._thick + "pt' color='" + this._color[1][i] + "' on='t'/>";
            rtn += "</v:rect>";
            var ttop = (this._height-space)*(1-(this._data[i].value/temp3))-this._pillar_width/1.1;
            rtn += "<v:shape style='position:absolute;left:" + (temp_space-table_space/2+left_width ) + "px;top:" + ttop + "px;width:" + (table_space+this._pillar_width*1.4) + "px;height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><div style='text-align:center'>" + this._data[i].value + "</div></v:textbox></v:shape>";
            rtn += "<v:shape style='position:absolute;left:" + (temp_space-table_space/2+left_width) + "px;top:" + (this._height+1-space) + "px;width:" + (table_space+this._pillar_width) + "px;height:18px;z-index:1'>";
            rtn += "<v:textbox inset='0px,0px,0px,0px'><div>"+this.getLabel(this._data[i])+"</div></v:textbox></v:shape>";
        }
        rtn += '</v:group> ';
        if(this._nameLabel){
            rtn += '<div style="width:'+this._width+';height:10px;text-align:center" class="h_label">'+this._nameLabel+'</div>';
        }
        this._width += nl_length;
        return rtn;
        
    },
    
    getAnchorAttributes:function(entry,anchor){
        return (entry.id?' id="'+(anchor?'n':'v')+'_'+entry.id+'"':'')+' title="'+entry.name+'" href="' + (entry.url&&!entry.behavior ? entry.url:"#") + '"' + (entry.url&&entry.target&&!entry.behavior?' target="'+entry.target+'"':'') + (entry.behavior?'onclick="return '+entry.behavior+';"':'');
    },
    
    getLabel:function(entry){
        var html = '';
        html += '<a' + this.getAnchorAttributes(entry,true) +'>';
        html += entry.name;
        html += '</a>';
        return html;
    },
    
    draw: function(id,type) {
        this.id = id;
        if(type){
            this._type = type;
        }
        
        if ("string" !== typeof id) {
            this._el = id;
        }
        
        if(this._type && this._type=='H'){
            this.getEl().innerHTML = this.getHtmlB();
        }else{
            this.getEl().innerHTML = this.getHtmlA();
        }
    },
    
    getEl: function() {
        if (! this._el) {
            this._el = document.getElementById(this.id);
        }
        return this._el;
    }
    
}