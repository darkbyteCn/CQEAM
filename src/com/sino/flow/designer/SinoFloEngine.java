package com.sino.flow.designer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class SinoFloEngine
{
    public static long LastUpdateTime;
    private static String SINOFLOFILE = "SINOFLO.SFP";
    private static boolean bInit = false;
    protected static SinoFloProjects projs;
//    protected static ApplDefs profiles;
 //   protected static SysUsers users;
//    protected static AutoSignItems signdefs;
//    protected static SysdbAliases sysdbs;
    private static final String strSuspend = "SuspendFlag!=\"1\"";
    protected static String _companyname;
    protected static String _serialno;
    protected static int _regusers;
    protected static String _key;
    protected static String FlowFileName;

    public SinoFloEngine()
        throws IOException
    {
        ReloadConfig();
    }

    public SinoFloEngine(String flowFile)
        throws IOException
    {
        FlowFileName=flowFile;
        ReloadConfig();
    }

    public void destroy()
    {
    }

    public static synchronized void InitProp()
    {
        projs = new SinoFloProjects();
/*        profiles = new ApplDefs("SuspendFlag!=\"1\"");
        users = new SysUsers("SuspendFlag!=\"1\"");
        signdefs = new AutoSignItems("SuspendFlag!=\"1\"");
        sysdbs = new SysdbAliases("SuspendFlag!=\"1\"");*/
    }

    public static synchronized void ReloadConfig()
        throws IOException
    {
        InitProp();
        init();
        Date date = new Date();
        LastUpdateTime = date.getTime();
    }

    public static long GetLastUpdateTime()
    {
        return LastUpdateTime;
    }

    public static synchronized void init()
        throws IOException
    {
        bInit = false;
        if(loadEffectiveProjects())
        {
            DebugBean.logMsg("Load Projects OK.");
            SinoFloProject proj = (SinoFloProject)projs.getNthItem(0);  //zzj has modify it from 1 to 0

        } else
        {
            DebugBean.logError("Load Projects Error!");
            DebugBean.logError("Please Check your system, SinoFlow Halt!!!");
            return;
        }



        DebugBean.logMsg("Load Application Profiles begin.......");
 /*       if(profiles.init())
        {
            com.sinoflow.debugBean.logMsg("Load Application Profiles OK.");
        } else
        {
            com.sinoflow.debugBean.logError("Load Application Profiles Error!");
            com.sinoflow.debugBean.logError("Please Check your system, SinoFlow Halt!!!");
            return;
        }
        
        com.sinoflow.debugBean.logMsg("Load Users Information begin.........");
        
        if(users.init())
        {
            com.sinoflow.debugBean.logMsg("Load Users Information OK.");
        } else
        {
            com.sinoflow.debugBean.logError("Load Users Information Error!");
            com.sinoflow.debugBean.logError("Please Check your system, SinoFlow Halt!!!");
            return;
        }
       
        com.sinoflow.debugBean.logMsg("Load Sign Defines  begin.........");
        if(signdefs.init())
        {
            com.sinoflow.debugBean.logMsg("Load Sign Defines OK.");
        } else
        {
            com.sinoflow.debugBean.logError("Load Sign Defines Error!");
            com.sinoflow.debugBean.logError("Please Check your system, SinoFlow Halt!!!");
        }
        
        if(sysdbs.init())
        {
            com.sinoflow.debugBean.logMsg("Load SysdbProfiles Defines OK.");
        } else
        {
            com.sinoflow.debugBean.logError("Load SysdbProfiles Defines Error!");
            com.sinoflow.debugBean.logError("Please Check your system, SinoFlow Halt!!!");
        }
        
        com.sinoflow.debugBean.logMsg("init license  begin.........");
        initLicense();
        com.sinoflow.debugBean.logMsg("init license  over.........");
        */
        bInit = true;
    }

    public static boolean isInit()
    {
        return bInit;
    }

    protected static boolean loadEffectiveProjects()
    {
        boolean flag=false;
        try{

            FileInputStream inputstream = new FileInputStream(FlowFileName);
            if(projs.addFrom(inputstream))
                DebugBean.logMsg("Project load OK.");
            else
                DebugBean.logError("Project  load Error.");

            inputstream.close();
            flag = true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {

        }
        return flag;
    }

    protected static synchronized void reloadLicense()
    {
        initLicense();
    }



    protected static void initLicense()
    {

    }


    public static int getProjNum()
    {
        return projs.getCount();
    }

    public static SinoFloProject getNthProj(int i)
    {
        return (SinoFloProject)projs.getNthItem(i);
    }

    public static SinoFloProject getProjByName(String s)
    {
        return (SinoFloProject)projs.getItemByName(s);
    }

    public static int getProfilesNum()
    {
        return 0;//profiles.getCount();
    }

    public static SinoFloProjects getProjs()
    {
        return projs;
    }

    public static void setProjs()
    {
        synchronized(projs)
        {
            projs = new SinoFloProjects();
            projs.init();
        }
    }

/*    public static ApplDef getNthProfile(int i)
    {
        return (ApplDef)profiles.getNthItem(i);
    }

    public static ApplDef getProfileByName(String s)
    {
        return profiles.getProfileByName(s);
    }

    public static ApplDefs getProfiles()
    {
        return profiles;
    }

    public static void setProfiles()
    {
        synchronized(profiles)
        {
            profiles = new ApplDefs("SuspendFlag!=\"1\"");
            profiles.init();
        }
    }

    public static int getUsersNum()
    {
        return users.getCount();
    }

    public static SysUser getNthUser(int i)
    {
        return (SysUser)users.getNthItem(i);
    }

    public static SysUsers getUsers()
    {
        return users;
    }

    public static void setUsers()
    {
        synchronized(users)
        {
            users = new SysUsers("SuspendFlag!=\"1\"");
            users.init();
        }
    }

    public static boolean IsAdmin(String s)
    {
        boolean flag = false;
        for(int i = 0; i < projs.getCount(); i++)
        {
            sinoFloProject wfproj = (sinoFloProject)projs.getNthItem(i);
            Vector vector = users.GetUserRoles(wfproj.getStringValue("name"), s);
            if(!com.sinoflow.sinoTools.VAL(vector) || !com.sinoflow.sinoTools.IsInList(vector, "系统管理员") && !com.sinoflow.sinoTools.IsInList(vector, "System Administrators") && !com.sinoflow.sinoTools.IsInList(vector, "Administrators"))
                continue;
            flag = true;
            break;
        }

        return flag;
    }
*/
    public static boolean IsInRole(String s, String s1, String s2)
    {
        boolean flag = false;
        SinoFloProject wfproj = (SinoFloProject)projs.getItemByName(s);
        //if(users.isInRole(wfproj.getStringValue("name"), s1, s2))
        //    flag = true;
        return flag;
    }

    public static boolean IsInRole(String s, String s1)
    {
        boolean flag = false;
        for(int i = 0; i < projs.getCount(); i++)
        {
            SinoFloProject wfproj = (SinoFloProject)projs.getNthItem(i);
          //  if(!users.isInRole(wfproj.getStringValue("name"), s, s1))
           //     continue;
            flag = true;
            break;
        }

        return flag;
    }

    public static boolean IsInGroup(String s, String s1, String s2)
    {
        boolean flag = false;
        SinoFloProject wfproj = (SinoFloProject)projs.getItemByName(s);
//        if(users.isInGroup(wfproj.getStringValue("name"), s1, s2))
            flag = true;
        return flag;
    }

    public static boolean IsInGroup(String s, String s1)
    {
        boolean flag = false;
        for(int i = 0; i < projs.getCount(); i++)
        {
            SinoFloProject wfproj = (SinoFloProject)projs.getNthItem(i);
//            if(!users.isInGroup(wfproj.getStringValue("name"), s, s1))
               // continue;
            flag = true;
            break;
        }

        return flag;
    }
/*
    public static SysUser getUser(String s, String s1)
    {
        return users.getUser(s, s1);
    }

    public static Vector getAllUsers(String s)
    {
        return users.GetAllUsers(s);
    }

    public static int getAllUsers(String s, StringBuffer stringbuffer)
    {
        return users.GetAllUsers(s, stringbuffer);
    }

    public static Vector getUserGroups(String s, String s1)
    {
        return users.GetUserGroups(s, s1);
    }

    public static int getUserGroups(String s, String s1, StringBuffer stringbuffer)
    {
        return users.GetUserGroups(s, s1, stringbuffer);
    }

    public static Vector getUserRoles(String s, String s1)
    {
        return users.GetUserRoles(s, s1);
    }

    public static int getUserRoles(String s, String s1, StringBuffer stringbuffer)
    {
        return users.GetUserRoles(s, s1, stringbuffer);
    }

    public static int getUsersByGroupAndRole(String s, String s1, String s2, StringBuffer stringbuffer)
    {
        return users.GetUsersByGroupAndRole(s, s1, s2, stringbuffer);
    }

    public static Vector getUsersByGroupAndRole(String s, String s1, String s2)
    {
        return users.GetUsersByGroupAndRole(s, s1, s2);
    }

    public static AutoSignItems getSignDefs()
    {
        return signdefs;
    }

    public static void setSignDefs()
    {
        synchronized(signdefs)
        {
            signdefs = new AutoSignItems("SuspendFlag!=\"1\"");
            signdefs.init();
        }
    }

    public static SysdbAliases getSysdbs()
    {
        return sysdbs;
    }

    public static void setSysdbs()
    {
        synchronized(sysdbs)
        {
            sysdbs = new SysdbAliases("SuspendFlag!=\"1\"");
            sysdbs.init();
        }
    }
*/
}
