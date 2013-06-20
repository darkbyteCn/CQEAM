package com.sino.ams.sampling.constant;

import com.sino.ams.newasset.constant.AssetsWebAttributes;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface SamplingWebAttributes extends AssetsWebAttributes {
	String TASK_DTO = "TASK_DTO";
	String BATCH_DTO = "BATCH_DTO";
	String ORDER_DTO = "ORDER_DTO";
	String ORDER_HEADERS = "ORDER_HEADERS";//创建工单页面，工单以多行的形式显示
	String ORDER_LINES = "ORDER_LINES";//工单修改页面，工单设备的多行显示
	String TASK_TREE = "TASK_TREE";//任务的树形展示
	String NO_TASK_REMARK = "还未创建抽查任务，请先创建任务";
	String WEB_WAIT_TIP = "<div id=\"$$$disableMsg$$$\" style=\"position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%\">"
						  + "<table width=\"100%\" height=\"100%\" style=\"background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)\">"
						  + "<tr>"
						  + "<td colspan=\"3\"></td>"
						  + "</tr>"
						  + "<tr>"
						  + "<td width=\"30%\"></td>"
						  + "<td bgcolor=\"#ff9900\"  height=\"60\">"
						  + "<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">"
						  + "<tr>"
						  + "<td bgcolor=\"#FFFFCC\" align=\"center\"><font color=\"#008000\" size=\"2\">正在提交数据，请稍候......</font><img src=\"/images/wait.gif\" alt=\"\"></td>"
						  + "</tr>"
						  + "</table>"
						  + "</td>"
						  + "<td width=\"30%\"></td>"
						  + "</tr>"
						  + "<tr>"
						  + "<td colspan=\"3\"></td>"
						  + "</tr>"
						  + "</table>"
						  + "</div>";
}
