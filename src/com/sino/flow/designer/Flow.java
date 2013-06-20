package com.sino.flow.designer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;

public class Flow {

    private String procName;
    private int taskCount;
    private boolean opened;
    private Flowfile m_file;
    private Connection conn;

    public Flow(Connection conn) {
        procName = "";
        taskCount = 0;
        opened = false;
        this.conn = conn;
        m_file = new Flowfile(conn);
    }

    public String getProcName() {
        return procName;
    }

    public int getTaskCount() {
        return m_file.getTaskCount();
    }

    public boolean getOpened() {
        return opened;
    }

    //打开流程文件，取特定的流程（procName)
    public boolean openFlo(String fileName, String procName) {
        closeFlo();
        if (m_file.OpenFile(fileName, procName)) {
            opened = true;
            taskCount = m_file.getTaskCount();
            procName = m_file.getFlowName();
            return true;
        } else
            return false;
    }

    // 打开流程文件，取整个工程
    public boolean openFlo(String fileName) {
        closeFlo();
        if (m_file.openFile(fileName)) {
            opened = true;
            return true;
        } else {
            return false;
        }
    }

    public void closeFlo() {
        if (opened) {
            m_file.CloseFlo();
            procName = "";
            taskCount = 0;
            opened = false;
        }
    }

    //
    public TaskInfo getFirstTask() throws QueryException, ContainerException {
        return m_file.getFirstTask();
    }

    public TaskInfo getNextTask(TaskInfo ts, int nIndex) throws QueryException, ContainerException {
        return m_file.getNextTask(ts, nIndex);
    }

    public int getNextTaskCount(TaskInfo ts) {
        return m_file.getNextTaskCount(ts);
    }

    public boolean getImageFile(String floFile) {
        return m_file.getImageFile(floFile);
    }

    public TaskInfo getTask(int nIndex) throws QueryException, ContainerException {
        return m_file.getTask(nIndex);
    }

    public TaskInfo getTask(String taskID) throws QueryException, ContainerException {
        return m_file.getTask(taskID);
    }

    public MTaskFlowInfo getFlowInfo(String startTaskID, String endTaskID) {
        return m_file.getFlowInfo(startTaskID, endTaskID);
    }

    public String saveProcdure(SinoFloProcedure procedure) throws QueryException, ContainerException, DataHandleException, SQLException {
        String procdureId = getProcdureId(procedure);
        if (procdureId.equals("")) {
            SeqProducer sp = new SeqProducer(conn);
            procdureId = StrUtil.nullToString(sp.getStrNextSeq("SF_PROCEDURE_DEF_S"));
            String sql =
                    "INSERT INTO SF_PROCEDURE_DEF\n" +
                            "  (PROC_ID,\n" +
                            "   PROC_NAME,\n" +
                            "   DESCRIPTION,\n" +
                            "   ORGANIZATION_ID)\n" +
                            "VALUES\n" +
                            "  (?, ?, ?, ?)";
            SQLModel sm = new SQLModel();
            ArrayList al = new ArrayList();
            al.add(procdureId);
            al.add(procedure.getText("name"));   //流程名
            al.add(procedure.getText("Desc"));   //流程描述
            al.add(procedure.getText("Remarks")); //OU暂时用备注信息
            sm.setArgs(al);
            sm.setSqlStr(sql);
            DBOperator.updateRecord(sm, conn);
        }
        return procdureId;
    }

    /**
     * 判断该流程是否已经存过，如果已经存过，就不用存了。
     * 并返回procdure_id
     */
    private String getProcdureId(SinoFloProcedure procdure) throws QueryException, ContainerException {
        String procdureId = "";
//        m_flowName = curProc.getText("name");
//        m_createUser = curProc.getText("CreateUser");
//        m_createDate = curProc.getText("CreateDate");
//        m_modifyUser = curProc.getText("ModifyUser");
//        m_modifyDate = curProc.getText("ModifyDate");
//        procdureDesc = curProc.getText("Desc"); //流程描述       added by wwb
//        procdureRemark = curProc.getText("Remarks"); //流程备注，暂时存流程的OU   added by wwb
        String sql =
                "SELECT *\n" +
                        "  FROM SF_PROCEDURE_DEF SPD\n" +
                        " WHERE SPD.PROC_NAME = ?\n";
        // "   AND SPD.ORGANIZATION_ID = ?";
        ArrayList al = new ArrayList();
        al.add(procdure.getText("name")); //流程名
        //   al.add(procdure.getText("Remarks"));  //流程备注，暂时存流程的OU
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            procdureId = (String) row.getValue("PROC_ID");
            //isExist = true;
        }
        return procdureId;
    }

    public void saveTasks(String procdureId) throws DataHandleException, QueryException, ContainerException {
        //step1:将所有这个流程下的task的标志字段更新为没有被更新
        String sql1 =
                "UPDATE SF_TASK_DEFINE STD\n" +
                        "   SET STD.IS_UPDATED_BY_FILE = 'N'\n" +
                        " WHERE STD.PROC_ID = ?";
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql1);
        ArrayList al = new ArrayList();
        al.add(procdureId);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
        //step2: 将文件中的数据存到（或更新）数据库

        int taskCount = getTaskCount();
        String insertSql =
                "INSERT INTO SF_TASK_DEFINE\n" +
                        "  (TASK_ID,\n" +
                        "   PROC_ID,\n" +
                        "   TASK_NAME,\n" +
                        "   TASK_MSG,\n" +
                        "   SECTION_RIGHT,\n" +  //只读控制
                        "   HIDDEN_RIGHT,\n" +//隐藏字段
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "   IS_HAND_USER,\n" +
                        "   DEPT_ID,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   TASK_PROP,\n" +
                        "   FILE_TASK_ID,\n" +
                        "   IS_UPDATED_BY_FILE,\n" +
                        "   ROLE_ID,\n" +
                        "   DISABLED,\n" +
                        "   ATTRIBUTE1,\n" +
                        "   ATTRIBUTE2,\n" +
                        "   ATTRIBUTE3,\n" +
                        "   ATTRIBUTE4,\n" +
                        "   ATTRIBUTE5\n" +
                        ")\n" +
                        "VALUES\n" +
                        "  (SF_TASK_DEFINE_S.NEXTVAL, ?, ?, ?, ?,?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
        String updateSQL =
                "UPDATE SF_TASK_DEFINE STD\n" +
                        "   SET STD.TASK_NAME          = ?,\n" +
                        "       STD.TASK_MSG           = ?,\n" +
                        "       STD.SECTION_RIGHT      = ?,\n" +
                        "       STD.HIDDEN_RIGHT       = ?,\n" +
                        "       STD.IS_HAND_USER       = ?,\n" +
                        "       STD.DEPT_ID            = ?,\n" +
                        "       STD.ORGANIZATION_ID    = ?,\n" +
                        "       STD.TASK_PROP          = ?,\n" +
                        "       STD.FILE_TASK_ID       = ?,\n" +
                        "       STD.IS_UPDATED_BY_FILE = ?,\n" +
                        "       STD.DISABLED           = ?,\n" +
                        "       STD.ROLE_ID            = ?,\n" +
                        "       STD.ATTRIBUTE1         = ?,\n" +
                        "       STD.ATTRIBUTE2         = ?,\n" +
                        "       STD.ATTRIBUTE3         = ?,\n" +
                        "       STD.ATTRIBUTE4         = ?,\n" +
                        "       STD.ATTRIBUTE5         = ? \n" +
                        " WHERE STD.TASK_ID = ?";
        SQLModel sm2 = new SQLModel();
        ArrayList al2 = new ArrayList();
        TaskInfo task;
        TaskInfo firstTask = getFirstTask();
        for (int i = 0; i < taskCount; i++) {
            //取task
            task = getTask(i);
            String taskId = getTaskIdByFileTaskId(task.getTaskID(), procdureId);//为了和已有系统中的taskId对应起来，sfp文件产生的taskId用另一个字段来存
            String taskProp = "2";
            if (task.getTaskID().equals(firstTask.getTaskID())) {
                taskProp = "1";
            }
            String roleId = task.getRoleID();
            if (roleId == null) {
                roleId = "";
            }
            String groupId = task.getGroupID();
            if (groupId == null) {
                groupId = "";
            }
            if (taskId.equals("")) {  //插入
                al2.clear();//将已经有的垃圾数据清掉
                al2.add(procdureId); //流程ID
                al2.add(task.getTaskName());// 节点名称
                al2.add(task.getTaskDesc()); //节点描述
                al2.add(task.getReadOnlyFlag());//只读控制
                al2.add(task.getHiddenFlag());//隐藏控制
                al2.add("-1");  //创建人
                al2.add("");//是否为经办人
                al2.add(groupId); //部门ID

                al2.add(task.getForms()); //暂时存OU，（接口程序）
                al2.add(taskProp); //节点属性
                al2.add(task.getTaskID());//Designer中的taskId
                al2.add("Y");//是否已经更新
                al2.add(roleId);
                al2.add("N");//是否失效
                al2.add(task.getAttribute1());
                al2.add(task.getAttribute2());
                al2.add(task.getAttribute3());
                al2.add(task.getAttribute4());
                al2.add(task.getAttribute5());
                sm2.setSqlStr(insertSql);
                sm2.setArgs(al2);
            } else {//更新
                al2.clear();
                al2.add(task.getTaskName());
                al2.add(task.getTaskDesc());
                al2.add(task.getReadOnlyFlag());//只读控制
                al2.add(task.getHiddenFlag());//隐藏
                al2.add("");//是否经办人
                al2.add(groupId);//
                al2.add(task.getForms());//接口程序，暂时存OU
                al2.add(taskProp);
                al2.add(task.getTaskID());
                al2.add("Y");
                al2.add("N");
                al2.add(roleId);
                al2.add(task.getAttribute1());
                al2.add(task.getAttribute2());
                al2.add(task.getAttribute3());
                al2.add(task.getAttribute4());
                al2.add(task.getAttribute5());
                al2.add(taskId);//表中的主键
                sm2.setSqlStr(updateSQL);
                sm2.setArgs(al2);
            }
            DBOperator.updateRecord(sm2, conn);
        }
        //step3:将没有更新的节点Disabled
        disableTasks(procdureId);
    }

    //通过文件的taskId取流程的taskId
    private String getTaskIdByFileTaskId(String fileTaskId, String procdureId) throws QueryException, ContainerException {
        String taskId = "";
        String sql = "SELECT * FROM SF_TASK_DEFINE STD WHERE STD.FILE_TASK_ID = ? AND STD.PROC_ID=?";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(fileTaskId);
        al.add(procdureId);  //加上流程限制，因为暂不清楚Designer中的复制流程，task_id有没有改变？
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            taskId = (String) row.getValue("TASK_ID");
        }
        return taskId;
    }

    //将Disigner中，已经不存在的节点失效
    public void disableTasks(String procdureId) throws DataHandleException {
        String sql =
                "UPDATE SF_TASK_DEFINE STD\n" +
                        "   SET STD.DISABLED = 'Y'\n" +
                        " WHERE STD.IS_UPDATED_BY_FILE = 'N'\n" +
                        "   AND STD.PROC_ID = ?";
        ArrayList al = new ArrayList();
        al.add(procdureId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
    }

    //存流向表
    public void saveFlows(String procdureId) throws QueryException, ContainerException, DataHandleException {
        //step1:将所有的流向更新为未更新状态
        updateFlows(procdureId);

        //step2:更新sf_flow_define
        String inserSQL =
                "INSERT INTO SF_FLOW_DEFINE\n" +
                        "  (FLOW_ID,\n" +
                        "   PROC_ID,\n" +
                        "   FROM_TASK_ID,\n" +
                        "   TO_TASK_ID,\n" +
                        "   FLOW_DESC,\n" +
                        "   FLOW_CODE,\n" +
                        "   DISABLED,\n" +
                        "   FILE_FLOW_ID,\n" +
                        "   IS_UPDATED_BY_FILE)\n" +
                        "VALUES\n" +
                        "  (SF_FLOW_DEFINE_S.NEXTVAL,\n" +
                        "   ?,\n" +
                        "   SF_FLOW_PKG.GET_TASK_ID(?, ?),\n" +
                        "   SF_FLOW_PKG.GET_TASK_ID(?, ?),\n" +
                        "   ?,\n" +
                        "   ?,\n" +
                        "   'N',\n" +
                        "   ?,\n" +
                        "   'Y')";
        String updateSQL =
                "UPDATE SF_FLOW_DEFINE SFD\n" +
                        "   SET SFD.FROM_TASK_ID       = SF_FLOW_PKG.GET_TASK_ID(?, ?),\n" +
                        "       SFD.TO_TASK_ID         = SF_FLOW_PKG.GET_TASK_ID(?, ?),\n" +
                        "       SFD.FLOW_DESC          = ?,\n" +
                        "       SFD.FLOW_CODE          = ?,\n" +
                        "       SFD.DISABLED           = 'N',\n" +
                        "       SFD.FILE_FLOW_ID       = ?,\n" +
                        "       SFD.IS_UPDATED_BY_FILE = 'Y'\n" +
                        " WHERE SFD.FLOW_ID = ?";
        int taskCount = getTaskCount();
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        for (int i = 0; i < taskCount; i++) {
            TaskInfo fromTask = getTask(i);
            int nextTaskCount = getNextTaskCount(fromTask);
            for (int j = 0; j < nextTaskCount; j++) {
                TaskInfo toTask = getNextTask(fromTask, j); //取下一节点
                MTaskFlowInfo flow = getFlowInfo(fromTask.getTaskID(), toTask.getTaskID());
                String flowId = getFlowIdByFileFlowId(flow.mFlowID, procdureId);
                String flowCode = "1";
                if (flow.mFlowCode != null && !flow.mFlowCode.equals("")) {
                    flowCode = flow.mFlowCode;
                }
                if (flowId.equals("")) { //插入
                    al.clear();
                    al.add(procdureId);//流程ID
                    al.add(procdureId);
                    al.add(fromTask.getTaskID());
                    al.add(procdureId);
                    al.add(toTask.getTaskID());
                    al.add(flow.Description);
                    al.add(flowCode);
                    al.add(flow.mFlowID);
                    sm.setSqlStr(inserSQL);
                    sm.setArgs(al);
                } else { //更新
                    al.clear();
                    al.add(procdureId);
                    al.add(fromTask.getTaskID());  //
                    al.add(procdureId);
                    al.add(toTask.getTaskID());
                    al.add(flow.Description);
                    al.add(flowCode);
                    al.add(flow.mFlowID);
                    al.add(flowId);
                    sm.setSqlStr(updateSQL);
                    sm.setArgs(al);
                }
                DBOperator.updateRecord(sm, conn);
            }
        }
        //step3:将已经不用的流向disabled
        disableFlows(procdureId);
    }

    //将flow的表中某个流程的流向全部变成未更新状态
    private void updateFlows(String procdureId) throws DataHandleException {
        String sql =
                "UPDATE SF_FLOW_DEFINE SFD\n" +
                        "   SET SFD.IS_UPDATED_BY_FILE = 'N'\n" +
                        " WHERE SFD.PROC_ID = ?";
        ArrayList al = new ArrayList();
        al.add(procdureId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
    }

    private void disableFlows(String procdureId) throws DataHandleException {
        String sql =
                "UPDATE SF_FLOW_DEFINE SFD\n" +
                        "   SET SFD.DISABLED = 'Y'\n" +
                        " WHERE SFD.PROC_ID = ?\n" +
                        "   AND SFD.IS_UPDATED_BY_FILE = 'N'";
        ArrayList al = new ArrayList();
        al.add(procdureId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
    }

    public String getFlowIdByFileFlowId(String fileFlowId, String procdureId) throws QueryException, ContainerException {
        String flowId = "";
        String sql =
                "SELECT SFD.FLOW_ID\n" +
                        "  FROM SF_FLOW_DEFINE SFD\n" +
                        " WHERE SFD.FILE_FLOW_ID = ?\n" +
                        "   AND SFD.PROC_ID = ?";
        ArrayList al = new ArrayList();
        al.add(fileFlowId);
        al.add(procdureId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            flowId = (String) row.getValue("FLOW_ID");
        }
        return flowId;
    }

    //将所有的流程信息存到数据库
    public void save2DB() throws SQLException, DataHandleException, QueryException, ContainerException {
        //取整个工程
        SinoFloProject prj = m_file.getProj();
        //过程数
        int procdureCount = prj.getProcedureCount();
        for (int i = 0; i < procdureCount; i++) {
            SinoFloProcedure procdure = prj.getNthProcedure(i);
            String procdureId = saveProcdure(procdure);
            m_file.setCurProc(procdure); //将当前的过程设为此过程
            saveTasks(procdureId);
            saveFlows(procdureId);
        }
    }
}