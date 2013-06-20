package com.sino.ams.inv.storeman.base.constant.web;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 0.1
 */
public interface WebInvConstant {

	String SYSTEM_NAME = "SYSTEM_NAME";//Web应用系统名称
	String USER_INFO = "USER_INFO";//session对象中存储用户对象使用的属性名
	String FORM_DATA = "FORM_DATA";//会话过期时保存的会话数据属性
	String REDIRECT_URL = "REDIRECT_URL";//重定向的URL
	String FILTER_DTO = "FILTER_DTO";//过滤器DTO类
	String SERVLET_DTO = "SERVLET_DTO";//初始化Servlet配置DTO类
	String LOOP_UP_PROP = "LOOP_UP_PROP";//LookUpModel属性对象

	String CHAR_ENCODING = "CHAR_ENCODING";//编码属性
	String LOOK_MODEL_CLASS = "LOOK_MODEL_CLASS";//LookUpModel对象的继承类
	String LOOK_UP_DTO = "LOOK_UP_DTO";//LookUpModel所需的DTO查询参数对象的继承类
//	String LOOK_UP_NAME = "LOOK_UP_NAME";//查找类型
    String CHOOSE_DICT="[…]";//页面选择

    String LOOK_UP_PAGE = "/lookUp.jsp";
    String LOOK_UP_INV_PAGE = "/inv/storeman/lookUpStoremanDetail.jsp";
	String LOOK_UP_SERVLET = "/servlet/com.sino.base.lookup.LoopUpServlet";

	String LOGIN_SERVLET = "/servlet/com.sino.framework.security.servlet.UserLoginServlet"; //登录验证Servlet
	String WAIT_TIP_MSG = "<div id=\"$$$waitTipMsg$$$\" style=\"position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden\">\n"
						  + "\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
						  + "\t\t<tr>\n"
						  + "\t\t\t<td width=\"30%\"></td>\n"
						  + "\t\t\t<td bgcolor=\"#ff9900\">\n"
						  + "\t\t\t\t<table width=\"100%\" height=\"60\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">\n"
						  + "\t\t\t\t\t<tr>\n"
						  + "\t\t\t\t\t\t<td bgcolor=\"#eeeeee\" align=\"center\">正在请求数据，请稍候......<img\n" +
            "                                src=\"/images/wait.gif\" alt=\"\"></td>\n"
						  + "\t\t\t\t\t</tr>\n"
						  + "\t\t\t\t</table>\n"
						  + "\t\t\t</td>\n"
						  + "\t\t\t<td width=\"30%\"></td>\n"
						  + "\t\t</tr>\n"
						  + "\t</table>\n"
						  + "</div>";//查询时使用的提示信息
}
