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
* <p>Description: Java Enterprise Edition ƽ̨Ӧ�ÿ����������</p>
* <p>@todo����ʱλ�ڸð��£�����ʵ����Ŀ����֤֮�󣬽����������⣬��ȡ��Ŀǰ���������ù���</p>
* <p>Copyright: ����˼ŵ����Ȩ����Copyright (c) 2003~2008��
* <p>Copyright: ����ʹ�õ��ĵ���������������л����񹲺͹���ط����Լ��л����񹲺͹��������ع��ʹ�Լ����Ȩ��ԭ�������С�</p>
* <p>Company: ����˼ŵ����Ϣ�������޹�˾</p>
* @author ����ʤ
* @version 0.1
 */

public class SinoAppContextListener implements ServletContextListener {

    private final static String listenerName = "�����ļ�������";
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
     * ���ܣ������ļ�������
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