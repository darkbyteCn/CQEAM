package com.sino.framework.security.bean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.query.GridPageSession;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class SessionUtil {
    private SessionUtil() {
        super();
    }

    /**
     * 功能：获取用户基本信息。
     * @param req HttpServletRequest
     * @return BaseUserDTO
     */
    public static BaseUserDTO getUserAccount(HttpServletRequest req){
		BaseUserDTO userAccount = null;
		HttpSession session = req.getSession();
		userAccount = (BaseUserDTO) session.getAttribute(WebConstant.USER_INFO);
		return userAccount;
	}

	/**
	 * 功能：保存用户信息到会话对象
	 * @param req HttpServletRequest
	 * @param userAccount BaseUserDTO
	 */
	public static void saveUserSession(HttpServletRequest req, BaseUserDTO userAccount){
		HttpSession session = req.getSession();
		session.setAttribute(WebConstant.USER_INFO, userAccount);
	}

	/**
	 * 功能：使会话对象失效
	 * @param req HttpServletRequest
	 */
	public static void invalidateSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
	}

	/**
	 * 功能：保存filterDTO到全局属性
	 * @param serContext ServletContext
	 * @param filterDTO FilterConfigDTO
	 */
	public static void saveFilterConfig(ServletContext serContext, FilterConfigDTO filterDTO) {
		serContext.setAttribute(WebConstant.FILTER_DTO, filterDTO);
	}

	/**
	 * 功能：获取Filter配置信息DTO对象。
	 * @param req HttpServletRequest
	 * @return FilterConfigDTO
	 */
	public static FilterConfigDTO getFilterConfigDTO(HttpServletRequest req){
		HttpSession session = req.getSession();
		return getFilterConfigDTO(session);
	}


	/**
	 * 功能：获取Filter配置信息DTO对象。
	 * @param session HttpSession
	 * @return FilterConfigDTO
	 */
	public static FilterConfigDTO getFilterConfigDTO(HttpSession session){
		ServletContext context = session.getServletContext();
		return getFilterConfigDTO(context);
	}


	/**
	 * 功能：获取Filter配置信息DTO对象。
	 * @param context ServletContext
	 * @return FilterConfigDTO
	 */
	public static FilterConfigDTO getFilterConfigDTO(ServletContext context){
		FilterConfigDTO filterDTO = (FilterConfigDTO) context.getAttribute(WebConstant.FILTER_DTO);
		return filterDTO;
	}
	/**
	 * 功能：保存初始化Servlet配置到全局属性
	 * @param serContext ServletContext
	 * @param servletDTO ServletConfigDTO
	 */
	public static void saveServletConfig(ServletContext serContext, ServletConfigDTO servletDTO) {
		serContext.setAttribute(WebConstant.SERVLET_DTO, servletDTO);
	}

	/**
	 * 功能：获取初始化Servlet配置信息DTO对象。
	 * @param req HttpServletRequest
	 * @return ServletConfigDTO
	 */
	public static ServletConfigDTO getServletConfigDTO(HttpServletRequest req){
		HttpSession session = req.getSession();
		return getServletConfigDTO(session);
	}

	/**
	 * 功能：获取初始化Servlet配置信息DTO对象。
	 * @param session HttpSession
	 * @return ServletConfigDTO
	 */
	public static ServletConfigDTO getServletConfigDTO(HttpSession session){
		ServletContext serContext = session.getServletContext();
		return getServletConfigDTO(serContext);
	}


	/**
	 * 功能：获取初始化Servlet配置信息DTO对象。
	 * @param context ServletContext
	 * @return ServletConfigDTO
	 */
	public static ServletConfigDTO getServletConfigDTO(ServletContext context){
		ServletConfigDTO servletDTO = (ServletConfigDTO) context.getAttribute(WebConstant.SERVLET_DTO);
		return servletDTO;
	}

	/**
	 * 功能：保存LookUpProp对象到Session
	 * @param req HttpServletRequest
	 * @param lookProp LookUpProp
	 */
	public static void saveLoopUpProp(HttpServletRequest req, LookUpProp lookProp){
		HttpSession session = req.getSession();
		session.setAttribute(WebConstant.LOOP_UP_PROP, lookProp);
	}

	/**
	 * 功能：保存字符编码
	 * @param serContext ServletContext
	 * @param encoding String
	 */
	public static void saveEncoding(ServletContext serContext, String encoding){
		serContext.setAttribute(WebConstant.CHAR_ENCODING, encoding);
	}

	/**
	 * 功能：返回字符编码
	 * @param serContext ServletContext
	 * @return String
	 */
	public static String getEncoding(ServletContext serContext) {
		return (String)serContext.getAttribute(WebConstant.CHAR_ENCODING);
	}

	/**
	 * 功能：获取用户基本信息。
	 * @param req HttpServletRequest
	 * @return LookUpProp
	 */
	public static LookUpProp getLookUpProp(HttpServletRequest req){
		LookUpProp lookProp = null;
		HttpSession session = req.getSession();
		lookProp = (LookUpProp)session.getAttribute(WebConstant.LOOP_UP_PROP);
		return lookProp;
	}

	/**
	 * 功能：获取request对象中设置的提示消息
	 * @param req HttpServletRequest
	 * @return Message
	 */
	public static Message getMessage(HttpServletRequest req){
		Message message = (Message) req.getAttribute(MessageConstant.MESSAGE_DATA);
		return message;
	}

	/**
	 * 功能：保存GridPageSession对象。
	 * @param pageSession GridPageSession
	 * @param req HttpServletRequest
	 */
	public static void saveGridPageSession(GridPageSession pageSession, HttpServletRequest req){
		HttpSession session = req.getSession();
		session.setAttribute(QueryConstant.GRID_PAGE_SESSION, pageSession);
	}

	/**
	 * 功能：获取用于统计的GridPageSession对象。
	 * @param req HttpServletRequest
	 * @return GridPageSession
	 */
	public static GridPageSession getGridPageSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		GridPageSession pageSession = null;
		pageSession = (GridPageSession) session.getAttribute(QueryConstant.GRID_PAGE_SESSION);
		return pageSession;
	}


	/**
	 * 功能：获取用于统计的GridPageSession对象。
	 * @param session HttpSession
	 * @return GridPageSession
	 */
	public static GridPageSession getGridPageSession(HttpSession session) {
		GridPageSession pageSession = null;
		pageSession = (GridPageSession) session.getAttribute(QueryConstant.GRID_PAGE_SESSION);
		return pageSession;
	}

        /**
     * 功能：设置页面标题
     * @param req
     * @param pageTitle
     */
    public static void setPageTitle(HttpServletRequest req, String pageTitle) {
        HttpSession session = req.getSession();
        session.setAttribute(WebAttrConstant.PAGE_TITLE, pageTitle);
    }

    /**
     * 功能：获取页面标题
     * @param req
     */
    public static String getPageTile(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return StrUtil.nullToString(session.getAttribute(WebAttrConstant.PAGE_TITLE));
    }
}
