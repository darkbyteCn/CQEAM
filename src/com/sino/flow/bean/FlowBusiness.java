package com.sino.flow.bean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.exception.FlowException;
import com.sino.flow.model.FlowModel;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;
import com.sino.sms.service.MessageProcessService;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-19
 * Time: 11:40:56
 * @version 1.2
 * @deprecated
 */
public class FlowBusiness {
    private Connection conn;
    private HttpServletRequest req;
    private FlowModel flowModel;
    private String applyId = "";
    private String applyNumber = "";
    private String userId = "";
    private String userName = "";
    private String currTaskId = "";

    /**
     * @param conn        连接
     * @param req
     * @param applyId     应用ID
     * @param applyNumber 应用编号
     * @param i      当前用户的ID
     */
    public FlowBusiness(Connection conn, HttpServletRequest req, String applyId, String applyNumber, String userId, String userName) throws FlowException {
        this.conn = conn;
        this.req = req;
        this.applyId = applyId;
        this.applyNumber = applyNumber;
        this.userId = userId;
        this.userName = userName;
        this.currTaskId = StrUtil.nullToString(req.getParameter("currTaskId"));
        try {
            this.flowModel = new FlowModel(req, applyId, applyNumber, StrUtil.strToInt(userId));
        } catch (UploadException e) {
            throw new FlowException(e);
        }
    }


    //isSendMsg,是否发送短信通知下一办理人。
    public void flow2Next(String approveContent, boolean isSendMsg) throws FlowException {
        try {
            String actId = req.getParameter("actId");
            if (actId == null || actId.equals("")) {//表明是第一次提交审批
                add2Flow(isSendMsg);
            } else {
                //step1：更新流程记录
                passApply(actId, approveContent, isSendMsg);
            }
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }


    //退到第一个节点
    public void reject2Begin(String approveContent, boolean isSendMsg) throws FlowException {
        String actId = req.getParameter("actId");
        //step1:保存审批意见
        if (approveContent.equals("")) {
            approveContent = "不同意";
        }
        try {
            if (!lockAct(actId)) {
                throw new DataHandleException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
            }
            //能退，肯定不是第一个节点。
            //step1:保存审批意见
            addApproveContent(actId, approveContent, false);
            //step2:完成sf_act
            completeSfAct4reject2Begin();
            //step3:完成actLog中的下一个办理人
            completeActLogNextUserId(actId);
            //step4:将ACT记录备份到LOG中
            act2log(FlowConstant.FLOW_CODE_PREV);
            //step5:更新ACT
            updateAct42Begin();
            //step6:完成当前节点的短信
            String cellPhone = getCellphoneByUserId(userId);
            if (!cellPhone.equals("")) {
                MessageProcessService msgService = new MessageProcessService();
                msgService.finishPhoneMessage(conn, actId, cellPhone);
            }
            HashMap hm = getAgentUsers(getCreatedBy(actId));
            //step7: save actInfo
            saveActInfo(actId, hm, "N");
            //step8: save msg
            if (isSendMsg) {
                saveMsg(hm, actId);
            }
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }

    //正常退回
    public void reject(String approveContent, boolean isSendMsg) throws FlowException {
        String actId = req.getParameter("actId");
        //step1:保存审批意见
        if (approveContent.equals("")) {
            approveContent = "不同意";
        }
        try {
            if (!lockAct(actId)) {
                throw new DataHandleException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
            }
            addApproveContent(actId, approveContent, false);
            //step2：将sf_act表改为完成状态
            completeSfAct();
            //step2:因为现在下一个办理人有可能是多个人，所以在上一个节点的时候不能确定，只有此时才能确定。
            completeActLogNextUserId(actId);
            //step3:将act记录保存到日志中
            act2log(FlowConstant.FLOW_CODE_PREV);
            //step4:更新ACT表
            String prevUserId = req.getParameter("prevUserId");//退到前一个办理人，前一个办理人只有一个，但为了统一。还是转换成HashMap形式
            HashMap hm = getAgentUsers(prevUserId);
            updateActForReject();
            //step5:完成actInfo表
            saveActInfo(actId, hm, "N");
            //step6:完成当前节点的短信。
            String cellPhone = getCellphoneByUserId(userId);
            if (!cellPhone.equals("")) {
                MessageProcessService msgService = new MessageProcessService();
                msgService.finishPhoneMessage(conn, actId, cellPhone);
            }
            if (isSendMsg) {//如果需要发短信，发短信。
                saveMsg(hm, actId);
            }
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }

    /**
     * 申请取消时调用，
     * @throws FlowException
     */
    public void cancelFlow() throws FlowException {
        try {
            String actId = StrUtil.nullToString(req.getParameter("actId"));
            if (actId.equals("")) {
                return;
            }
            if (!lockAct(actId)) {
                throw new DataHandleException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
            }
            addApproveContent(actId, "撤消申请", false);
            //step1:完成当前节点信息
            completeSfAct();
            //step2:完成上一个
            completeActLogNextUserId(actId);
            //step2: 备份日志表
            act2log(FlowConstant.FLOW_CODE_NEXT);
            //step3:删除act表
            deleteActInfo(actId);
            deleteAct("");
            //step4:完成短信
            String cellPhone = getCellphoneByUserId(userId);
            if (!cellPhone.equals("")) {
                MessageProcessService msgService = new MessageProcessService();
                msgService.finishPhoneMessage(conn, actId, cellPhone);
            }
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }

    //流程暂存
    public void saveTemp() throws FlowException {
        String actId = req.getParameter("actId");
        try {
            if (actId == null || actId.equals("")) {//如果actId为空，表明是第一次走流程，
                //看是否重复提交
                if (isAdded()) {
                    throw new FlowException("本单据已经提交，不能重复提交！");
                }
                //取actId
                actId = getActId();
                //取当前节点（即第一个节点）
                currTaskId = getFirstTaskId();
                //取流程名
                String procName = getProcNameById();
                //本节点只是暂存，仍就在本节点，因此此处不是取下一办理人，而是当前办理人
                //String nextUserId = req.getParameter("nextUserId");
                HashMap agentUser = new HashMap();
                agentUser.put(userId, "");
                flowModel.setProcName(procName);
                flowModel.setCurrTaskId(currTaskId);
                flowModel.setActId(actId);
                createSfAct();
                //createSfActLog();
                //updateSfAct();
                addApproveContent(actId, "创建申请", true);
                saveActInfo(actId, agentUser, "Y");
            } else {
                //中间的节点，暂存不用处理什么
            }
        } catch (SQLException e) {
            throw new FlowException(e);
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }

    /**
     * 新增提交审批时，将应用加到流程上
     * @param isSendMsg 是否短信通知
     * @throws FlowException
     */
    private void add2Flow(boolean isSendMsg) throws FlowException {
        try {
            if (isAdded()) {
                throw new FlowException("本单据已经提交，不能重复提交！");
            }
            //取actId
            String actId = getActId();
            //取当前节点（即第一个节点）
            currTaskId = getFirstTaskId();
            //取流程名
            String procName = getProcNameById();
            String nextUserId = req.getParameter("nextUserId");

            //取下一个人的代理人，如果无，返回“”；多个下一办理人的多个代理
            HashMap agentUser = getAgentUsers(nextUserId);
            flowModel.setProcName(procName);
            flowModel.setCurrTaskId(currTaskId);
            flowModel.setActId(actId);
            createSfAct();
            createSfActLog();
            updateSfAct();
            addApproveContent(actId, "创建申请", true);
            saveActInfo(actId, agentUser, "N");
            if (isSendMsg) {//如果需要发短信。短信通知
                saveMsg(agentUser, actId);
            }
        } catch (SQLException e) {
            throw new FlowException(e);
        } catch (DataHandleException e) {
            throw new FlowException(e);
        } catch (UploadException e) {
            throw new FlowException(e);
        } catch (QueryException e) {
            throw new FlowException(e);
        } catch (ContainerException e) {
            throw new FlowException(e);
        }
    }

    /**
     * @param msgContent    短消息内容
     * @param userId        用户ID
     * @param actId         流程记录ID
     * @param msgCategoryId 短消息类型
     * @throws DataHandleException
     * @throws QueryException
     * @throws ContainerException
     */
    private void saveMsg(String msgContent, String userId, String actId, String msgCategoryId) throws DataHandleException, QueryException, ContainerException {
        MessageProcessService msgService = new MessageProcessService();
        SfMsgDefineDTO msg = new SfMsgDefineDTO();
        msg.setMsgCategoryId(msgCategoryId);
        msg.setMsgContent(msgContent);
        msg.setActId(actId);
        String cellPhone = getCellphoneByUserId(userId);
        msg.addCellPhone(cellPhone);
        msgService.saveMessage(conn, msg);
    }

    /**
     * 通过请求
     */
    private void passApply(String actId, String approveContent, boolean isSendMsg) throws DataHandleException, UploadException, QueryException, ContainerException {
        String taskProp = req.getParameter("taskProp");  //结点属性，开始结点1，其他节点为2
        String nextUserId = req.getParameter("nextUserId");
        String nextTaskId = req.getParameter("nextTaskId");
        HashMap agentUser = null;
        //step0:lock sf_act
        if (!lockAct(actId)) {
            throw new DataHandleException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
        }
        //step1:添加审批意见到数据库
        boolean isFirstTask = false;
        if (approveContent.equals("")) {
            if (taskProp.equals(FlowConstant.TASK_PROP_START)) {
                approveContent = "再次提交申请";
                isFirstTask = true;
            } else {
                approveContent = "同意";
            }
        }
        addApproveContent(actId, approveContent, isFirstTask);
        //step2: 将sf_act表中记录状态改为完成,
        completeSfAct();
        //step2:因为现在下一个办理人有可能是多个人，所以在上一个节点的时候不能确定，只有此时才能确定。
        completeActLogNextUserId(actId);
        //step3:将记录转到日志表
        act2log(FlowConstant.FLOW_CODE_NEXT);
        //step4:  更新sf_act
        if (isEndTask(nextTaskId)) {
            //如果是最后一个结点，删除ACT记录
            deleteActInfo(actId);
            deleteAct(actId);
        } else {
            //i不是最后一个节点，更新ACT记录
            //取下一个人的代理人，如果无，返回“”；
            agentUser = getAgentUsers(nextUserId);
            updateActForPass();
            saveActInfo(actId, agentUser, "N");
        }
        //step5:取消当前人的短信通知 ，（如果当前办理人是多个人办理，此时也应该只有一人，因为在签收的时候，已经取消了别人的短信通知。
        String cellPhone = getCellphoneByUserId(userId);
        if (!cellPhone.equals("")) {
            MessageProcessService msgService = new MessageProcessService();
            msgService.finishPhoneMessage(conn, actId, cellPhone);
        }
        //step6:如果不是最后一个人，继续发短信通知下一办理人
        if (isSendMsg) {//如果需要发短信，发短信。
            if (agentUser != null) { //如果最后已经没人了，这些值都是空。
                saveMsg(agentUser, actId);
            }
        }
    }

    //流程直接走向结束且流程不再跑了，一般应用不需要调用
    public void flow2End(String actId, String procId, String approveContent, String currTaskId) throws DataHandleException, UploadException, QueryException, ContainerException {
        if (!lockAct(actId)) {
            throw new DataHandleException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
        }
        //step1 add approve opinion
        addApproveContent4Flow2End(actId, approveContent, currTaskId, procId);
        //step2 complete sf_act
        updateAct4flow2End(actId, procId);
        //step3 complete sf_act_log's next_user_id
        completeActLogNextUserId(actId);
        //step4: backup sf_act table
        act2Log4flow2End(actId);
        //step5: delete sf_act_info table
        deleteActInfo(actId);
        //step6: delte sf_act table's record
        deleteAct(actId);
        //step7:finish the msg
        String cellPhone = getCellphoneByUserId(userId);
        if (!cellPhone.equals("")) {
            MessageProcessService msgService = new MessageProcessService();
            msgService.finishPhoneMessage(conn, actId, cellPhone);
        }
    }

    //update sf_act table for method flow to end.
    private void updateAct4flow2End(String actId, String procId) throws DataHandleException {
        DBOperator.updateRecord(flowModel.completeAct2EndModel(actId, procId), conn);
    }

    private void act2Log4flow2End(String actId) throws UploadException, DataHandleException {
        DBOperator.updateRecord(flowModel.getAct2Log4flow2EndModel(FlowConstant.FLOW_CODE_NEXT, actId), conn);
    }

    private void updateActForReject() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getUpdateSfActForRejectModel(), conn);
    }

    private void updateActForPass() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getUpdateSfActForPassModel(), conn);
    }

    //update sf_act for reject to begin
    private void updateAct42Begin() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.updateAct42Begin(), conn);
    }

    /**
     * 添加审批意见
     */
    private void addApproveContent(String actId, String approveContent, boolean isFirstTask) throws UploadException, DataHandleException, QueryException, ContainerException {
        if (isFirstTask) {
            if (approveContent == null || approveContent.equals("")) {
                approveContent = "再次提交申请";
            }
            DBOperator.updateRecord(flowModel.getAddApproveContentModle(actId, userId, approveContent), conn);
        } else {
            //取当前办理人，不从session中取，因为session中有可能是当前办理人的委托人
            String currUser = getCurrUser(actId);

            DBOperator.updateRecord(flowModel.getAddApproveContentModle(actId, currUser, approveContent), conn);
        }
    }

    public void addApproveContent4Flow2End(String actId, String approveContent, String currTaskId, String procId) throws QueryException, ContainerException, DataHandleException {
        String currUser = getCurrUser(actId);
        DBOperator.updateRecord(flowModel.addAprroveContent4Flow2End(actId, applyId, currUser, approveContent, currTaskId, procId), conn);
    }

    /**
     * 更新SF_ACT表
     */
    private void completeSfAct() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getCompleteSfActModel(), conn);
    }

    /**
     * 将SF_ACT记录转到日志表   备份记录
     */
    private void act2log(String flowCode) throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getCreateSfActLogModel(flowCode), conn);
    }

    private void deleteAct(String actId) throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getDeleteSfActModel(actId), conn);
    }


    /**
     * 在当前流转信息中插入记录
     * 第一次提交时。
     * @throws UploadException
     * @throws DataHandleException
     */
    private void createSfAct() throws UploadException, DataHandleException {
        DBOperator.updateRecord(flowModel.getAddActModel(), conn);
    }

    /**
     * 流转信息日志表中插入记录
     * 第一次插日志表调用
     * @throws UploadException
     * @throws DataHandleException
     */
    private void createSfActLog() throws UploadException, DataHandleException {
        DBOperator.updateRecord(flowModel.getSfActLogCreateModel(), conn);
    }

    /**
     * 更新流转信息表
     * @throws DataHandleException
     * @throws UploadException
     */
    private void updateSfAct() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getSfActUpdateModel(), conn);
    }

    /**
     * 新增加的流程从序列号中取出ACTID
     * @return
     * @throws SQLException
     */
    private String getActId() throws SQLException {
        String actId = "";
        SeqProducer sp = new SeqProducer(conn);
        actId = StrUtil.nullToString(sp.getStrNextSeq("SF_ACT_S"));
        return actId;
    }

    /**
     * 新增加的流程页面没有currTaskId,
     * 按流程类型和OU取出该流程的第一个TASKID
     */
    private String getFirstTaskId() throws QueryException, ContainerException {
        String currTaskId = "";
        SimpleQuery sq = new SimpleQuery(flowModel.getFirstTaskIdModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            currTaskId = (String) row.getValue("TASK_ID");
        }
        return currTaskId;
    }

    private String getProcNameById() throws QueryException, ContainerException {
        String procName = "";
        SimpleQuery sq = new SimpleQuery(flowModel.getProcNameByIdModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            procName = (String) row.getValue("PROC_NAME");
        }
        return procName;
    }

    /**
     * 公司内部人员，根据userId取电话
     * @param userId
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private String getCellphoneByUserId(String userId) throws QueryException, ContainerException {
        String cellphone = "";
        String sql =
                "SELECT IUP.MSG_CELL_PHONE FROM SF_USER_PHONE IUP WHERE IUP.USER_ID = ? AND  IUP.SEND_TYPE = '1'";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(userId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            cellphone = (String) row.getValue("MSG_CELL_PHONE");
        }
        return cellphone;
    }

    /**
     * 供应商联系人，根据userId和vendowSiteId取CellPhone
     * @param userId
     * @param vendorSiteId
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private String getCellphoneByUserId(String userId, String vendorSiteId) throws QueryException, ContainerException {
        String cellphone = "";
        String sql =
                "SELECT IUP.MSG_CELL_PHONE FROM IES_USER_PHONE IUP WHERE IUP.USER_ID = ? AND IUP.VENDOR_SITE_ID=?";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(userId);
        al.add(vendorSiteId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            cellphone = (String) row.getValue("MSG_CELL_PHONE");
        }
        return cellphone;
    }

    /**
     * 通过employeeId取电话号码
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private String getCellphoneByEmployeeId(String employeeId) throws QueryException, ContainerException {
        String cellphone = "";
        String sql =
                "SELECT IUP.MSG_CELL_PHONE\n" +
                        "  FROM IES_USER_PHONE IUP\n" +
                        " WHERE IUP.USER_ID = (SELECT IUV.USER_ID\n" +
                        "                        FROM IES_USER_V IUV\n" +
                        "                       WHERE IUV.EMPLOYEE_ID = ?\n" +
                        "                         AND ROWNUM < 2)\n" +
                        "   AND IUP.VENDOR_SITE_ID " + SyBaseSQLUtil.isNullNoParam() + " ";

        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(employeeId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            cellphone = (String) row.getValue("MSG_CELL_PHONE");
        }
        return cellphone;
    }

    private String getApplyNumberByActId(String actId) throws QueryException, ContainerException {
        String applyNumber = "";
        String sql =
                "SELECT SA.APPLY_NUMBER FROM SF_ACT SA WHERE SA.ACTID = ?";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(actId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            applyNumber = (String) row.getValue("APPLY_NUMBER");
        }
        return applyNumber;
    }

    //userId,多个分号分隔的。
    //返回的hashMap是userId-agentUserId(key-value)
    private HashMap getAgentUsers(String userId) throws QueryException, ContainerException {
        HashMap hm = new HashMap();
        int inndex = userId.lastIndexOf(";");
        if (inndex == -1) {
            String agent = getAgentUserId(userId);
            String[] strArr2 = StrUtil.splitStr(agent, ";");
            if (strArr2.length == 2) {
                hm.put(strArr2[0], strArr2[1]);
            } else {
                hm.put(strArr2[0], "");
            }
        } else {
            String[] strArr = StrUtil.splitStr(userId, ";");
            for (int i = 0; i < strArr.length; i++) {
                String agent = getAgentUserId(strArr[i]);
                String[] strArr2 = StrUtil.splitStr(agent, ";");
                if (strArr2.length == 2) {
                    hm.put(strArr2[0], strArr2[1]);
                } else {
                    hm.put(strArr2[0], "");
                }
            }
        }
        return hm;
    }

    //取userId对应的代理人
    private String getAgentUserId(String userId) throws QueryException, ContainerException {
        String agentUserId = "";
        if (userId != null && !userId.equals("")) {
            String sql =
                    "SELECT SFA.USER_ID, SFA.AGENT_USER_ID\n" +
                            "  FROM SF_FLOW_AGENT SFA\n" +
                            " WHERE SFA.USER_ID = ?\n" +
                            "   AND (GETDATE() BETWEEN SFA.ACTIVE_START_DATE AND SFA.ACTIVE_END_DATE)\n" +
                            "   AND SFA.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + " ";

            SQLModel sm = new SQLModel();
            ArrayList al = new ArrayList();
            al.add(userId);
            sm.setArgs(al);
            sm.setSqlStr(sql);
            SimpleQuery sq = new SimpleQuery(sm, conn);
            sq.executeQuery();
            RowSet rs = sq.getSearchResult();
            if (rs != null && !rs.isEmpty()) {
                Row row = rs.getRow(0);
                agentUserId = (String) row.getValue("AGENT_USER_ID");
            }
        }
        return userId + ";" + agentUserId;
    }

    //通过ActId取当前代理人，得到的值UserId&&userName
    private String getCurrUser(String actId) throws QueryException, ContainerException {
        String currUser = "";
        String sql =
                "SELECT SAI.USER_ID\n" +
                        "  FROM SF_ACT_INFO SAI\n" +
                        " WHERE SAI.ACT_ID = ?\n" +
                        "   AND (SAI.USER_ID = ? OR SAI.AGENT_USER_ID = ?)";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(actId);
        al.add(userId);
        al.add(userId);
        sm.setArgs(al);
        sm.setSqlStr(sql);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            currUser = (String) row.getValue("USER_ID");
        } else {
            //如果没找到，表明系统有问题，尤其是已经提交过一次
            throw new QueryException("本单据已经办理，请重新刷新&lt;待办单据&gt;！");
        }
        return currUser;
    }

    /**
     * 保存信息到actinfo表里
     * @param actId
     * @param hm
     */
    private void saveActInfo(String actId, HashMap hm, String signFlag) throws DataHandleException {
        String sql = "DELETE FROM SF_ACT_INFO SAI WHERE SAI.ACT_ID = ?";
        ArrayList al = new ArrayList();
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
        if (hm == null) {
            return;
        }
        Iterator iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            String userId = (String) iter.next();
            String agentUserId = (String) hm.get(userId);
            String insertSql = "INSERT INTO SF_ACT_INFO (ACT_ID, USER_ID, AGENT_USER_ID,SIGN_FLAG) VALUES (?, ?, ?,?)";
            ArrayList al2 = new ArrayList();
            al2.add(actId);
            al2.add(userId);
            al2.add(agentUserId);
            al2.add(signFlag);
            SQLModel sm2 = new SQLModel();
            sm2.setSqlStr(insertSql);
            sm2.setArgs(al2);
            DBOperator.updateRecord(sm2, conn);
        }
    }

    private boolean isFirstTask(String taskId) throws QueryException, ContainerException {
        boolean isFirst = false;
        String sql = "SELECT STD.TASK_PROP FROM SF_TASK_DEFINE STD WHERE STD.TASK_ID = ?";
        ArrayList al = new ArrayList();
        al.add(taskId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            String taskProp = (String) row.getValue("TASK_PROP");
            if (taskProp.equals(FlowConstant.TASK_PROP_START)) {
                isFirst = true;
            }
        }
        return isFirst;
    }

    private boolean isEndTask(String nextTaskId) throws QueryException, ContainerException {
        boolean isEndTask = false;
        String sql =
                "SELECT STD.TASK_NAME\n" +
                        "  FROM SF_TASK_DEFINE STD\n" +
                        " WHERE STD.TASK_ID = ?";

        ArrayList al = new ArrayList();
        al.add(nextTaskId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            String taskProp = (String) row.getValue("TASK_NAME");
            if (taskProp.equals("结束")) {
                isEndTask = true;
            }
        }
        return isEndTask;
    }

    private void completeActLogNextUserId(String actId) throws DataHandleException {
        DBOperator.updateRecord(flowModel.completeActLogNextUserIdModel(actId), conn);
    }

    private void deleteActInfo(String actId) throws DataHandleException {
        DBOperator.updateRecord(flowModel.deleteActInfoModel(actId), conn);
    }

    //hm是userId-agentUserId表式
    private void saveMsg(HashMap hm, String actId) throws DataHandleException, QueryException, ContainerException {
        String msgContent = FlowConstant.MSG_AT_SYSTEM + applyNumber + "。\n转发人：" + userName;
        //如果代理人不为空，短信提醒代理人
        Iterator iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            String userId = (String) iter.next();
            String annouceUserId = userId;
            String agentUserId = (String) hm.get(userId);
            if (agentUserId != null && !agentUserId.equals("")) {
                annouceUserId = agentUserId;//短信通知代理人
            }
            saveMsg(msgContent, annouceUserId, actId, SMSConstant.MSG_CATEGORY_FLOW);
        }
    }

    private String getCreatedBy(String actId) throws QueryException, ContainerException {
        String createdby = "";
        SimpleQuery sq = new SimpleQuery(flowModel.getCreatedByModel(actId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            createdby = (String) row.getValue("CREATED_BY");
        }
        return createdby;
    }

    //更新SF_ACT for 退到创建人
    private void completeSfAct4reject2Begin() throws DataHandleException, UploadException {
        DBOperator.updateRecord(flowModel.getCompleteSfAct4RejectBeginModel(), conn);
    }

    private boolean lockAct(String actId) throws DataHandleException, QueryException, ContainerException {
        boolean success = false;
        //step1 :加锁，如果是重复提交，下一次的修改必须等到上一次完成后,一旦完成后就可以select出来currTaskId,
        //这里的更新主要是为了等待上一次的commit;
        String sql = " UPDATE SF_ACT SA SET SA.LOCKED_BY = ?, SA.LOCKED_DATE = GETDATE()\n" +
                " WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(userId);
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
        String currTaskId = StrUtil.nullToString(req.getParameter("currTaskId"));
        String tableTaskId = "";
        String querysql = "  SELECT SA.CUR_TASK_ID FROM SF_ACT SA WHERE SA.ACTID = ?";
        ArrayList queryAl = new ArrayList();
        queryAl.add(actId);
        SQLModel querySM = new SQLModel();
        querySM.setSqlStr(querysql);
        querySM.setArgs(queryAl);
        SimpleQuery sq = new SimpleQuery(querySM, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            tableTaskId = (String) row.getValue("CUR_TASK_ID");
        }
        //当页面中的currTaskId和数据库中的currTaskId一致时，返回true;
        //表时不是重复提交
        if (tableTaskId.equals(currTaskId)) {
            clearLock(actId);
            success = true;
        } else {
            //如果是重复提交 外面调用此方法的时候要回滚事务
        }
        return success;
    }

    private void clearLock(String actId) throws DataHandleException {
        String sql = " UPDATE SF_ACT SA SET SA.LOCKED_BY = NULL, SA.LOCKED_DATE =NULL\n" +
                " WHERE SA.ACTID = ?";
        ArrayList al = new ArrayList();
        al.add(actId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        DBOperator.updateRecord(sm, conn);
    }

    //提交到流程，查看是否重复提交，即，数据库中是否有记录，如果有，就表明已经增加到流程中
    public boolean isAdded() throws QueryException {
        boolean isAdded = false;
        SimpleQuery sq = new SimpleQuery(flowModel.isAddedModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            isAdded = true;
        }
        return isAdded;
    }
    //第一个节点处理时，判断是提交申请还是再次提交申请
    //  public boolean isAddOpinion(){

    //}
}
