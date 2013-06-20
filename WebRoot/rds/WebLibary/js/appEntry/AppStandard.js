function do_ShowHintMessage() {
    var hintObj = document.getElementById("$$$waitTipMsg$$$");
    if (hintObj != undefined && hintObj != null) {
        hintObj.style.visibility = "visible";
    }
}

/**
 * 功能：获取ACT对象
 */
function getActObject() {
    var actObj = document.getElementById("act");
    if (actObj == undefined || actObj == null) {
        alert("请在查询页面加入act隐藏域！");
    }
    return actObj;
}

/**
 * 功能：下载和导出的特殊处理
 * @param frm
 */
function do_PreProcessDownload(frm) {
    var downFrm = document.getElementById("downFrm");
    if (downFrm == null || downFrm == undefined || downFrm.tagName != "IFRAME") {
        downFrm = document.createElement("iframe");
        downFrm.id = "downFrm";
        downFrm.name = "downFrm";
        downFrm.style.display = "none";
        downFrm.width = 0;
        downFrm.height = 0;
        downFrm.frameborder = 0;
        document.body.appendChild(downFrm);
    }
    frm.target = downFrm.name;
}

/**
 * 功能：检查本次操作是否允许
 * @param actValue
 */
function do_CheckAction(actValue) {
    var isValid = false;
    if (actValue == "QUERY_ACTION") {
        isValid = do_Check_Search();
    } else if (actValue == "SAVE_ACTION") {
        isValid = do_Check_Save();
    } else if (actValue == "SUBMIT_ACTION") {
        isValid = do_Check_Submit();
    } else if (actValue == "DELETE_ACTION") {
        isValid = do_Check_Delete();
    } else if (actValue == "DISABLE_ACTION") {
        isValid = do_Check_Disable();
    } else if (actValue == "ENABLE_ACTION") {
        isValid = do_Check_Enable();
    } else if (actValue == "EXPORT_ACTION") {
        isValid = do_Check_Export();
    } else if (actValue == "DOWNLOAD_ACTION") {
        isValid = do_Check_Download();
    } else if (actValue == "PUBLISH_ACTION") {
        isValid = do_Check_Publish();
    }
    return isValid;
}

function do_Check_Search() {
    return true;
}

function do_Check_Save() {
    return true;
}

function do_Check_Publish() {
    return true;
}

function do_Check_Submit() {
    return true;
}

function do_Check_Delete() {
    return true;
}

function do_Check_Disable() {
    return true;
}

function do_Check_Enable() {
    return true;
}

function do_Check_Export() {
    return true;
}

function do_Check_Download() {
    return true;
}


/**
 * 功能：执行各种操作
 * @param actValue act值
 * @param preProcessor 提交前的预处理器
 */
function do_ProcessAction(actValue, preProcessor) {
    var actObj = getActObject();
    if (actObj != null) {
        var oldActValue = actObj.value;
        if (do_CheckAction(actValue)) {
            actObj.value = actValue;
            if (preProcessor != undefined && preProcessor != null) {
                eval(preProcessor + "()");
            }
            var frm = document.forms[0];
            var target = frm.target;
            if (actValue == "EXPORT_ACTION" || actValue == "DOWNLOAD_ACTION") {
                do_PreProcessDownload(frm);
            }
            frm.submit();
            if (actValue == "EXPORT_ACTION" || actValue == "DOWNLOAD_ACTION") {
                frm.target = target;
            }
        } else {
            actObj.value = oldActValue;
        }
    }
}

function do_preSearch(){
    do_ShowHintMessage();
}

/**
 * 功能：用于查询操作。
 */
function do_Search() {
    do_ProcessAction("QUERY_ACTION", "do_preSearch");
}

/**
 * 功能：执行保存操作
 */
function do_Save() {
    do_ProcessAction("SAVE_ACTION", null);
}

/**
 * 功能：执行发布操作
 */
function do_Publish() {
    do_ProcessAction("PUBLISH_ACTION", null);
}

/**
 * 功能：执行数据删除操作
 */
function do_Delete() {
    do_ProcessAction("DELETE_ACTION", null);
}

/**
 * 功能：执行数据失效操作
 */
function do_Disable() {
    do_ProcessAction("DISABLE_ACTION", null);
}

/**
 * 功能：数据生效操作
 */
function do_Enable() {
    do_ProcessAction("ENABLE_ACTION", null);
}


/**
 *  功能：导出EXCEL
 */
function do_Export() {
    if (confirm("确定要导出到EXCEL吗？继续请点击“确定”按钮，否则，请点击“取消”按钮")) {
        do_ProcessAction("EXPORT_ACTION", null);
    }
}

/**
 * 功能：文件下载操作
 */
function do_Download() {
    do_ProcessAction("DOWNLOAD_ACTION", null);
}


/**
 * 功能：用于新增或者修改操作时，由应用设置该类
 */
function DataPageConfig() {
    this.setActionURL = function(actionURL) {
        this.actionURL = actionURL;
    };
    this.setAct = function(act) {
        this.act = act;
    };
    this.setParameters = function(parameters) {
        this.parameters = parameters;
    };
    this.setWidthPercent = function(widthPercent) {
        if (isNaN(widthPercent) || Number(widthPercent) > 1 || Number(widthPercent) < 0) {
            this.widthPercent = 1;
        } else {
            this.widthPercent = widthPercent;
        }
    };
    this.setHeightPercent = function(heightPercent) {
        if (isNaN(heightPercent) || Number(heightPercent) > 1 || Number(heightPercent) < 0) {
            this.heightPercent = 1;
        } else {
            this.heightPercent = heightPercent;
        }
    };
    this.setOpenWindow = function(openWindow) {
        this.openWindow = openWindow;
    };
    this.setWindowName = function(windowName) {
        this.windowName = windowName;
    };
    this.setFullScreen = function(fullScreen) {
        this.fullScreen = (fullScreen != undefined && fullScreen);
    };
    this.setClientHeight = function(clientHeight) {
        if (isNaN(clientHeight) || clientHeight <= 0) {
            this.clientHeight = document.body.clientHeight;
        } else {
            this.clientHeight = clientHeight;
        }
    };
}

function configCreatePage() {
    alert("应用开发人员未正确配置pageConfig，系统将以以默认方式执行");
    var pageConfig = new DataPageConfig();
    pageConfig.setOpenWindow(true);
    return pageConfig;
}

function configDetailPage() {
    alert("应用开发人员未正确配置pageConfig，系统将以以默认方式执行");
    var pageConfig = new DataPageConfig();
    pageConfig.setOpenWindow(true);
    return pageConfig;
}

/**
 * 功能：用于创建数据操作
 */
function do_Create() {
    var pageConfig = configCreatePage();
    do_ProcessDataPage(pageConfig);
}

/**
 * 功能：用于查看数据详细信息操作。
 */
function do_ViewDetail() {
    var pageConfig = configDetailPage();
    do_ProcessDataPage(pageConfig);
}

function do_ProcessDataPage(pageConfig) {
    if (pageConfig == null || pageConfig == undefined) {
        return;
    }
    if (!pageConfig.widthPercent) {
        pageConfig.widthPercent = 1;
    }
    if (!pageConfig.heightPercent) {
        pageConfig.heightPercent = 1;
    }
    if (pageConfig.act == undefined || pageConfig.act == null) {
        pageConfig.act = "DETAIL_ACTION";
    }
    if (!pageConfig.actionURL) {
        pageConfig.actionURL = document.forms[0].action;
    }
    var style = "";
    if (pageConfig.fullScreen) {
        //        style = "fullscreen=yes";
        style = "channelmode";
    } else {
        var width = window.screen.availWidth * pageConfig.widthPercent;
        var height = window.screen.availHeight * pageConfig.heightPercent;
        var left = (window.screen.availWidth - width) / 2;
        var top = (window.screen.availHeight - height) / 2;
        style = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes";
    }
    var actionURL = pageConfig.actionURL;
    if (!actionURL) {
        actionURL = document.forms[0].action;
    }
    if (!actionURL || actionURL.length == 0) {
        alert("未设置目标URL");
        return;
    }
    if (actionURL.indexOf("?act") == -1 && actionURL.indexOf("&act") == -1) {
        if (actionURL.indexOf("?") > -1) {
            actionURL += "&act=" + pageConfig.act;
        } else {
            actionURL += "?act=" + pageConfig.act;
        }
    }
    var parameters = pageConfig.parameters;
    if (parameters) {
        if (parameters.indexOf("&") == 0) {
            actionURL += parameters;
        } else {
            actionURL += "&" + parameters;
        }
    }
    if (pageConfig.clientHeight) {
        actionURL += "&clientHeight=" + pageConfig.clientHeight;
    }
    if (pageConfig.openWindow == undefined || pageConfig.openWindow == null) {
        pageConfig.openWindow = true;
    }
    if (pageConfig.openWindow) {
        var currWindow = null;
        if (pageConfig.windowName) {
            currWindow = window.open(actionURL, pageConfig.windowName, style);
        } else {
            currWindow = window.open(actionURL, null, style);
        }
        currWindow.focus();
    } else {
        window.location.href = actionURL;
    }
}

/**
 * 功能：设置窗口关闭配置。
 * @para editPage 当前页面是否是可编辑页面。不传值时系统默认为是编辑页面
 * @para refreshOpener 是否刷新父窗口。不传值时根据isEditPage确定是否需要刷新父窗口
 */
function CloseConfig() {
    this.setEditPage = function(editPage) {
        this.editPage = editPage;
    };
    this.setRefreshOpener = function(refreshOpener) {
        this.refreshOpener = refreshOpener;
    };
    this.isEditPage = function() {
        if (this.editPage == undefined || this.editPage == null || this.editPage) {
            this.setEditPage(true);
        }
        return this.editPage;
    };
    this.isRefreshOpener = function() {
        if (this.refreshOpener == undefined || this.refreshOpener == null) {
            if (this.isEditPage()) {
                if (window.opener) {
                    this.setRefreshOpener(true);
                } else {
                    this.setRefreshOpener(false);
                }
            } else {
                this.setRefreshOpener(false);
            }
        }
        return this.refreshOpener;
    };
}

function do_CloseConfig() {
    alert("没有对关闭属性进行配置，系统将以默认方式执行");
    var cfg = new CloseConfig();
    cfg.setEditPage(true);
    cfg.setRefreshOpener(true);
    return cfg;
}

/**
 * 功能：关闭当前窗口。主要用于关闭打开的新建页面和详细信息查看页面
 */
function do_Close() {
    var needClose = false;
    try {
        var cfg = do_CloseConfig();
        if (cfg.isEditPage()) {
            if (confirm("请确保已经保存了你的工作！确实要关闭本页面吗？继续请点击“确定”按钮，点击“取消”按钮留在当前页面")) {
                needClose = true;
                if (cfg.isRefreshOpener()) {
                    if (window.opener.do_Search) {
                        window.opener.do_Search();
                    }
                }
            }
        } else {
            needClose = true;
            if (cfg.isRefreshOpener()) {
                if (window.opener.do_Search) {
                    window.opener.do_Search();
                }
            }
        }
    } finally {
        if (needClose) {
            window.close();
        }
    }
}

function do_ShowWaitMsg() {
    alert("请覆盖方法do_ShowWaitMsg");
}


/**
 * 功能：按下enter键时自动执行指定无参函数。例如：autoExeFunction(do_selectVendors());
 * @param {Object} functionName
 */
function autoExeFunction(functionName) {
	if (event.keyCode == 13) {
		eval(functionName);
	}
}
