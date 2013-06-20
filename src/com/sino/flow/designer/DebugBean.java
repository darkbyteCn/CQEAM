package com.sino.flow.designer;

import java.text.MessageFormat;
import java.util.Date;

public class DebugBean
{ 

    public static final boolean DEBUG = false;
    public static final boolean VERBOSE = true;
    private static int sm_logLevel = 2;
    public static final boolean NSDEBUG = false;
    public static final boolean CKDEBUG = false;
    public static final boolean TIMEDEBUG = false;
    public static final int LOGLEVEL_DEBUG = 1;
    public static final int LOGLEVEL_STATUS = 2;
    public static final int LOGLEVEL_ERROR = 3;
    public static final int LOGLEVEL_NONE = 4;

    public DebugBean()
    {
    }


    public static int stringToLevel(String s, int i)
    {
        int j = i;
        String as[] = {
            "Debug", "Status", "Error", "None"
        };
        for(int k = 0; k < as.length; k++)
            if(s.equalsIgnoreCase(as[k]))
                j = k + 1;

        try
        {
            int l = Integer.parseInt(s);
            j = l;
        }
        catch(NumberFormatException numberformatexception) { }
        return j;
    }

    public static void logMsg(String s)
    {
        logAllMsg(2, s);
    }

    public static void logMsg(String s, Object aobj[])
    {
        logAllMsg(2, s, aobj);
    }

    public static void setLogLevel(int i)
    {
        if(i >= 1 && i <= 4)
            sm_logLevel = i;
    }

    public static int getLogLevel()
    {
        return sm_logLevel;
    }

    public static void logError(String s)
    {
        logAllMsg(3, s);
    }

    public static void logError(String s, Object aobj[])
    {
        logAllMsg(3, s, aobj);
    }

    public static void logDebugMsg(String s)
    {
        logAllMsg(1, s);
    }

    public static void logDebugMsg(String s, Object aobj[])
    {
        logAllMsg(1, s, aobj);
    }

    private static void logAllMsg(int i, String s)
    {
        System.out.println(s);
    }

    private static void logAllMsg(int i, String s, Object aobj[])
    {
        for(int j = 0; j < aobj.length; j++)
            if(aobj[j] == null)
                aobj[j] = "";

        String s1 = MessageFormat.format(s, aobj);
        logAllMsg(i, s1);
    }


    static long logTime(String s, long l)
    {
        long l1 = 0L;
        return l1;
    }

    public static void log(String s)
    {
        logDebugMsg(s);
    }

    public static void log(Object obj)
    {
        System.out.println((new Date()).toString() + " -log(Object)- \n" + obj);
    }

    public static void debug(String s)
    {
    }

    public static void exp(String s, Exception exception)
    {
        if(exception != null)
        {
            System.out.println((new Date()).toString() + " -exp- \n" + s);
            if(exception.getMessage() == null)
                exception.printStackTrace();
        }
    }

}
