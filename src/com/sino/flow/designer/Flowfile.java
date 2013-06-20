package com.sino.flow.designer;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.flow.constant.FlowConstant;

/**
 * Title:        operate file utility
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      beijing SinoProf.
 *
 * @author
 * @version 1.0
 */

public class Flowfile {
    private static SinoFloEngine en = null;
    public static String FLOW_FILE_PATH = FlowConstant.FLOW_FILE_PATH;
    private String fileName;

    private byte nMajor, nMinor;
    private byte bFlag;
    private boolean m_opened;
    private String m_flowName;  //floinfo structure.
    private String m_createUser;
    private String m_modifyUser;
    private String m_createDate;
    private String m_modifyDate;
    private String errMsg = "";
    private Connection conn;
    private SinoFloProcedure curProc;  //当前过程
    private String procdureDesc = "";//流程描述  added by wwb
    private String procdureRemark = "";//流程备注，暂时存流程的OU//属于哪个OU added by wwb

    private SinoFloProject proj;//工程 added by wwb

    public String getErrorMsg() {
        return errMsg;
    }

    public Flowfile(Connection conn) {
        m_opened = false;
        m_flowName = "";
        m_createUser = "";
        m_modifyUser = "";
        m_createDate = "";
        m_modifyDate = "";
        //m_dataOffset = 0;
        //m_fileOffset = 0;
        //m_fileLen = 0;
        this.conn = conn;//added by wwb
    }

    private String getGroupIDByName(String gName) throws QueryException, ContainerException {
        if (gName.equals("*")) {
            return "-1";
        }
        String sql = "SELECT SG.GROUP_ID FROM SF_GROUP SG WHERE SG.GROUP_NAME = ?";
        String retStr = null;
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        ArrayList al = new ArrayList();
        al.add(gName);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs.getSize() > 0) {
            Row row = rs.getRow(0);
            retStr = (String) row.getValue("GROUP_ID");
        }
        return retStr;
    }

    private String getRoleIDByName(String rName) throws QueryException, ContainerException {

        String retStr = "";
        String sql =
                "SELECT SR.ROLE_ID FROM SF_ROLE SR WHERE SR.ROLE_NAME = ?";
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        ArrayList al = new ArrayList();
        al.add(rName);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            retStr = (String) row.getValue("ROLE_ID");
        }
        return retStr;
    }

    public String getFlowName() {
        if (!m_opened) return null;
        return m_flowName;
    }

    public String getFileName() {
        if (!m_opened) return null;
        return fileName;
    }

    public TaskInfo getFirstTask() throws QueryException, ContainerException {
        if (!m_opened) return null;

        SinoFloTask sinoTask = curProc.getStartTask();

        TaskInfo ts;

        ts = getTaskInfo(sinoTask);

        return ts;

    }

    private int getInt(String inStr) {
        if (inStr == null)
            return -1;

        if (inStr.compareTo("") == 0) {
            return -1;
        }
        return Integer.valueOf(inStr).intValue();
    }

    public MTaskFlowInfo getFlowInfo(String fromTaskID, String endTaskID) {
        if (!m_opened) return null;

        String nextTaskId = endTaskID;
        SinoFloTask sinots = curProc.getTaskByID(getInt(fromTaskID));

        Vector v = getAllOutFlow(sinots);

        int nSize = v.size();
        if (nSize < 1) {
            errMsg = "can't find any flow by taskid " + endTaskID;
            return null;
        }
        for (int i = 0; i < nSize; i++) {
            MTaskFlowInfo mflow = (MTaskFlowInfo) v.elementAt(i);
            if (mflow.NextTaskID.compareTo(nextTaskId) == 0) {
                return mflow;
            }
        }
        return null;
    }

    public Vector getAllOutFlow(SinoFloTask sinots) {
        Vector retV = new Vector();
        if (!m_opened || sinots == null) return null;

        //sinoFloTask sinots=curProc.getTaskByID(getInt(ts.getTaskID()));
        Vector v = curProc.getAllOutFlow(sinots);

        int nFlowCount = v.size();
        if (nFlowCount < 1)
            return retV;

        for (int i = 0; i < nFlowCount; i++) {
            SinoFloInfo wfflow = (SinoFloInfo) v.elementAt(i);
            MTaskFlowInfo mflow = new MTaskFlowInfo();

            mflow.Name = wfflow.getText("Desc");
            mflow.Description = wfflow.getText("ApplMsg");
            mflow.lineType = wfflow.getText("LineType");
            mflow.mFlowType = wfflow.getText("Type");
            mflow.mFlowID = wfflow.getText("FlowID");
            mflow.mFlowCode = wfflow.getText("SelectionCode");
            mflow.mFlowControl = wfflow.getText("PickMode");
            mflow.PrevTaskID = wfflow.getText("PrevTaskID");
            mflow.NextTaskID = wfflow.getText("NextTaskID");
            retV.addElement(mflow);
        }
        return retV;

    }

    public TaskInfo getNextTask(TaskInfo ts, int nPos) throws QueryException, ContainerException {
        if (!m_opened || ts == null) return null;

        SinoFloTask sinots = curProc.getTaskByID(getInt(ts.getTaskID()));

        Vector v = curProc.getAllNextTask(sinots);

        if (nPos > v.size()) {
            return null;
        }

        SinoFloTask nts = (SinoFloTask) v.elementAt(nPos);

        return getTaskInfo(nts);

    }

    public TaskInfo getTask(String taskId) throws QueryException, ContainerException {
        if (!m_opened) return null;

        SinoFloTask sinots;


        if (taskId.compareToIgnoreCase("key-1") == 0) {
            sinots = curProc.getStartTask();
        }
        if (taskId.compareToIgnoreCase("key-2") == 0) {
            sinots = curProc.getTaskByName("结束");
        } else {
            sinots = curProc.getTaskByID(getInt(taskId));
        }
        TaskInfo ts;

        ts = getTaskInfo(sinots);

        return ts;
    }

    public TaskInfo getTask(int nIndex) throws QueryException, ContainerException {
        if (!m_opened) return null;

        SinoFloTask sinots = curProc.getNthTask(nIndex);

        TaskInfo ts;

        ts = getTaskInfo(sinots);

        return ts;

    }

    public int getTaskCount() {
        if (!m_opened) return 0;

        return curProc.getTaskCount();

    }

    public int getNextTaskCount(TaskInfo ts) {
        if (!m_opened || ts == null) return 0;
        SinoFloTask sinots = curProc.getTaskByID(getInt(ts.getTaskID()));
        Vector v = curProc.getAllNextTask(sinots);
        return v.size();
    }

    private TaskInfo getTaskInfo(SinoFloTask sinots) throws QueryException, ContainerException {
        if (!m_opened) return null;

        String taskid, taskname, taskdesc, groupid, roleid, szSection, szHidden, szForms, szNextID;
        String m_CaseHandleMode;
        int nDuration;

        if (sinots == null) {
            return null;
        }
        //新增加的属性，只能用于新版的designer added by wwb 2007-11-07
        String attribute1, attribute2, attribute3, attribute4, attribute5;
        //sinoDocField[] f=sinots.FIELDS;

        taskid = sinots.getText("TaskID");
        taskname = sinots.getText("name");
        taskdesc = sinots.getText("Desc");
        groupid = getGroupIDByName(sinots.getText("TaskGroup"));
        roleid = getRoleIDByName(sinots.getText("TaskRole"));
        nDuration = Integer.valueOf(sinots.getText("TaskDuration")).intValue();
        szSection = sinots.getText("SectionValue");
        szHidden = sinots.getText("HiddenValue");
        szForms = sinots.getText("LaunchProg");
        m_CaseHandleMode = sinots.getText("CaseHandleMode");
        attribute1 = sinots.getText("Attribute1");
        attribute2 = sinots.getText("Attribute2");
        attribute3 = sinots.getText("Attribute3");
        attribute4 = sinots.getText("Attribute4");
        attribute5 = sinots.getText("Attribute5");
        Vector v = curProc.getAllNextTask(sinots);
        String tmpstr = null;
        for (int i = 0; i < v.size(); i++) {
            SinoFloTask nts = (SinoFloTask) v.elementAt(i);

            if (tmpstr == null) {
                tmpstr = nts.getText("TaskID");
            } else {
                tmpstr = tmpstr + ";" + nts.getText("TaskID");
            }
        }
        szNextID = tmpstr;
        TaskInfo ts = new TaskInfo(taskid, taskname, taskdesc, nDuration, roleid, groupid, szSection, szHidden, szForms, szNextID);

        ts.setTaskControl(m_CaseHandleMode);
        ts.setAttribute1(attribute1);
        ts.setAttribute2(attribute2);
        ts.setAttribute3(attribute3);
        ts.setAttribute4(attribute4);
        ts.setAttribute5(attribute5);
        return ts;
    }

    private boolean getFlowInfo() {
        boolean retval = false;

        m_flowName = curProc.getText("name");
        m_createUser = curProc.getText("CreateUser");
        m_createDate = curProc.getText("CreateDate");
        m_modifyUser = curProc.getText("ModifyUser");
        m_modifyDate = curProc.getText("ModifyDate");
        procdureDesc = curProc.getText("Desc"); //流程描述       added by wwb
        procdureRemark = curProc.getText("Remarks"); //流程备注，暂时存流程的OU   added by wwb

        retval = true;
        return retval;
    }

    public void CloseFlo() {
        m_opened = false;
        m_flowName = "";
        m_createUser = "";
        m_modifyUser = "";
        m_createDate = "";
        m_modifyDate = "";
        procdureDesc = "";
        procdureRemark = "";
        //m_dataOffset = 0;
        //m_fileData = null;
        //m_fileOffset = 0;
        // m_fileLen = 0;
        System.gc();
    }

    //取指定的某个过程
    public boolean OpenFile(String szFileName, String procName) {
        try {

            fileName = szFileName;

            m_flowName = procName;
            nMajor = 1;
            nMinor = 0;
            bFlag = 1;
            if (en == null) {
                en = new SinoFloEngine(szFileName);
            }

            int prjnum = en.getProjNum();

            if (prjnum == 0) {
                Logger.logError("Can't find project info from " + szFileName);
                return false;
            }
            SinoFloProject prj = en.getNthProj(0);

            if (prj.getProcedureCount() == 0) {
                Logger.logError("Can't find procname info from project ");
                return false;
            }
            curProc = prj.getProcByName(procName);

            if (curProc.getFlowCount() == 0) {
                Logger.logError("Can't find procedure " + procName + " from flow file ");
                return false;
            }

            getFlowInfo();

            m_opened = true;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //added by wwb ,openFile and get All procdure
    public boolean openFile(String fileName) {
        this.fileName = fileName;
        try {
            if (en == null) {
                en = new SinoFloEngine(fileName);
            }
            //通过流程引擎取工程数
            int prjnum = en.getProjNum();
            //如果工程数为0
            if (prjnum == 0) {
                Logger.logError("Can't find project info from " + fileName);
                return false;
            }
            //取第一个工程，暂时只用建一个工程，因此只用取第一个
            SinoFloProject proj = en.getNthProj(0);
            //如果该工程下的过程数目为0
            if (proj.getProcedureCount() == 0) {
                Logger.logError("Can't find procname info from project ");
                return false;
            }
            m_opened = true;
            this.proj = proj;
            return true;
        } catch (IOException e) {
            Logger.logError(e);
            return false;
        }
    }

    public boolean getImageFile(String floFile) {
        /*
        boolean retval = false;
      try
      {
        FileOutputStream fous = new FileOutputStream(floFile);
        fous.write(m_fileData,m_fileOffset,m_fileLen);
        fous.close();
        retval = true;
      }catch(Exception e){
        retval = false;

      }
      return retval;*/
        return false;
    }

    public String getProcdureRemark() {
        return procdureRemark;
    }

    public void setProcdureRemark(String procdureRemark) {
        this.procdureRemark = procdureRemark;
    }

    public String getProcdureDesc() {
        return procdureDesc;
    }

    public void setProcdureDesc(String procdureDesc) {
        this.procdureDesc = procdureDesc;
    }

    public SinoFloProject getProj() {
        return proj;
    }

    public void setProj(SinoFloProject proj) {
        this.proj = proj;
    }

    public SinoFloProcedure getCurProc() {
        return curProc;
    }

    public void setCurProc(SinoFloProcedure curProc) {
        this.curProc = curProc;
    }
}
