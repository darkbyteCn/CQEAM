package com.sino.foundation.web.listener;


import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.foundation.config.ConfigLoader;
import com.sino.foundation.exception.InitException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
* <p>Title: SinoApplication</p>
* <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
* <p>@todo：暂时位于该包下，经过实际项目的验证之后，将其加入基础库，并取代目前不灵活的配置管理</p>
* <p>Copyright: 北京思诺博版权所有Copyright (c) 2003~2008。
* <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
* <p>Company: 北京思诺博信息技术有限公司</p>
* @author 唐明胜
* @version 0.1
 */

public class SinoAppContextListener implements ServletContextListener {

    private final static String listenerName = "配置文件监听器";
    private String serverName = "";
    private ConfigListener listener = null;

    public void contextInitialized(ServletContextEvent contextEvent) {
        ServletContext context = contextEvent.getServletContext();
        try {
//            ConfigLoader.loadConfig(context.getInitParameter("contextConfigLocation"));
//            startListener(context);
//            serverName = context.getServletContextName();
//            if (StrUtil.isEmpty(serverName)) {
//                serverName = "Web";
//            }
            System.out.println("SinoProf " + serverName + " Server Context Listener Started Successfully...");
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new InitException(ex);
        }
    }


    /**
     * 功能：启动文件监听器
     *
     * @param context ServletConfig
     */
    private void startListener(ServletContext context) {/*
        String startListener = context.getInitParameter("startConfigListener");
        if (!StrUtil.isEmpty(startListener)) {
            if (startListener.equalsIgnoreCase("TRUE") ||
                    startListener.equalsIgnoreCase("T") ||
                    startListener.equalsIgnoreCase("YES") ||
                    startListener.equalsIgnoreCase("Y") ||
                    startListener.equalsIgnoreCase("ON") ||
                    startListener.equals("1")) {
                String strFrenquency = context.getInitParameter("listenFrequency");
                listener = new ConfigListener();
                listener.setName(listenerName);
                if (StrUtil.isInteger(strFrenquency)) {
                    listener.setListenFrequency(Integer.parseInt(strFrenquency));
                }
                listener.start();
            }
        }
    */}

    public void contextDestroyed(ServletContextEvent contextEvent) {/*
        try {
            System.out.println("SinoProf " + serverName + " Server Context Listener Started to Stop...");
            if (listener != null) {
                listener.interrupt();
                listener.stop();
            }
            ConfigLoader.unloadAllConfigs();
            System.out.println("SinoProf " + serverName + " Server Context Listener Stopped Successfully...");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    */}
}