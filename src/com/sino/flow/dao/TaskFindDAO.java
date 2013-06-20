package com.sino.flow.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.constant.MessageDefineList;
import com.sino.flow.dto.FlowParmDTO;
import com.sino.flow.dto.SfTaskDefineDTO;
import com.sino.flow.model.TaskFindModel;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TaskFindDAO extends BaseProcessorDAO {
    private TaskFindModel taskFindModel = null;

    public TaskFindDAO(Connection conn, HttpServletRequest req, FlowParmDTO dto) {
        super(conn, req);
        taskFindModel = new TaskFindModel(dto, req);
    }

    /**
     * 功能：获取第一个节点
     *
     * @return SfTaskDefineDTO
     * @throws QueryException
     */
    public SfTaskDefineDTO getBeginTask() throws QueryException {
        SfTaskDefineDTO beginTask = null;
        SQLModel sqlModel = taskFindModel.getBeginTaskModel();
        SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
        queryBean.setDTOClassName(SfTaskDefineDTO.class.getName());
        queryBean.executeQuery();
        if (queryBean.hasResult()) {
            beginTask = (SfTaskDefineDTO) queryBean.getFirstDTO();
        } else {
            setProcessMsg(MessageDefineList.DATA_NOT_EXISTS);
        }
        return beginTask;
    }

    /**
     * 功能：获取当前节点的下一节点.如果下一节点有多个，则为分流；否则为直流.
     *
     * @return DTOSet
     * @throws QueryException
     */
    public JSONArray getNextTasks(String flowCode) throws QueryException, UploadException, JSONException, ContainerException {
        JSONArray arr = new JSONArray();
        SQLModel sqlModel = taskFindModel.getNextTaskModel(flowCode);
        SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
        queryBean.executeQuery();
        RowSet rs = queryBean.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);
                JSONObject obj = new JSONObject();
                obj.put("nextTaskId", row.getValue("TASK_ID")); //下一节点
                obj.put("nextDeptId", row.getValue("DEPT_ID"));//下一办理部门
                obj.put("procId", row.getValue("PROC_ID"));//流程ID
                obj.put("flowDesc", row.getValue("FLOW_DESC"));//流向描述
                obj.put("nextTaskName", row.getValue("TASK_NAME"));//节点名
                obj.put("nextHiddenRight", row.getValue("HIDDEN_RIGHT"));//下一节点的隐藏属性，此处要的目的是，看下一节点是否为多办理人的节点
                arr.put(obj);
            }
        }
        return arr;
    }

    public String getTaskProp() throws QueryException, UploadException, ContainerException {
        String taskProp = "";
        SQLModel sqlModel = null;
        sqlModel = taskFindModel.getTaskPropModel();
        SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
        queryBean.executeQuery();
        if (queryBean.hasResult()) {
            Row row = queryBean.getFirstRow();
            taskProp = String.valueOf(row.getValue("TASK_PROP"));
        }
        return taskProp;
    }

    /**
     * 功能：判断当前Task是否起始Task。
     *
     * @return boolean
     * @throws QueryException
     */
    public boolean isBeginTask() throws QueryException, UploadException, ContainerException {
        String taskProp = getTaskProp();
        return taskProp.equals(FlowConstant.TASK_PROP_START);
    }

    /**
     * 功能：判断当前节点是否流程中的终止节点。
     *
     * @return boolean
     * @throws QueryException
     */
    public boolean isEndTask() throws QueryException, UploadException, ContainerException {
        String taskProp = getTaskProp();
        return taskProp.equals(FlowConstant.TASK_PROP_START);
    }

    public void setProcId2DTO() throws QueryException, ContainerException {
        SQLModel sm = taskFindModel.getProcIdModel();
        SimpleQuery sq = new SimpleQuery(sm, conn);
        String procId = "";
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            procId = (String) row.getValue("PROC_ID");
        }
        taskFindModel.setProcId(procId);

    }

    /**
     * 将当前流程记录中
     *
     * @param actId
     * @throws QueryException
     * @throws ContainerException
     */
    public void setCurrAct2DTO(String actId) throws QueryException, ContainerException {
        String currTaskId = req.getParameter("currTaskId");
        String procId = req.getParameter("procId");
        taskFindModel.setCurrTaskId(currTaskId);
        taskFindModel.setProcId(procId);
//        SQLModel sm = taskFindModel.getActDetail(actId);
//        SimpleQuery sq = new SimpleQuery(sm, conn);
//        sq.executeQuery();
//        RowSet rs = sq.getSearchResult();
//        if (rs != null && !rs.isEmpty()) {
//            Row row = rs.getRow(0);
//            String currTaskId = (String) row.getValue("CUR_TASK_ID");
//            String procId = (String) row.getValue("PROC_ID");
//
//        }
    }

    public int getNextTaskCount() throws QueryException, UploadException, ContainerException {
        int count = 0;
        SQLModel sm = taskFindModel.getNextTaskCount();
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            String total = (String) row.getValue("TOTAL");
            count = Integer.parseInt(total);
        }
        return count;
    }

}
