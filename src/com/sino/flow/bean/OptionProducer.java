package com.sino.flow.bean;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.dto.DTOSet;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dto.SfPositionDTO;
import com.sino.flow.dto.SfTaskDefineDTO;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OptionProducer {
    private HttpServletRequest req = null;
    private Connection conn = null;

    public OptionProducer(HttpServletRequest req, Connection conn) {
        super();
        this.req = req;
        this.conn = conn;
    }

    /**
     * 功能：产生下一节点下拉框.
     * @param tasks DTOSet
     */
    public void proTaskOptions(DTOSet tasks){
        StringBuffer taskOptions = new StringBuffer();
        SfTaskDefineDTO taskDTO = null;
        for(int i = 0; i < tasks.getSize(); i++){
            taskDTO = (SfTaskDefineDTO)tasks.getDTO(i);
            taskOptions.append("<option value=");
            taskOptions.append(taskDTO.getTaskId());
            taskOptions.append("&&");
            taskOptions.append(taskDTO.getDeptId());
            taskOptions.append("&&");
            taskOptions.append(taskDTO.getDeptName());
            taskOptions.append("&&");
            taskOptions.append(taskDTO.getProcId());
            taskOptions.append(">");
            taskOptions.append(taskDTO.getFlowDesc());
            taskOptions.append("</option>");
        }
        req.setAttribute(ReqAttributeList.TASK_OPTIONS, taskOptions.toString());
    }

    /**
     * 功能：产生下一节点审批人下拉框
     * @param users DTOSet
     */
    public void proAppUserOptions(DTOSet users){
        StringBuffer userOptions = new StringBuffer();
        SfPositionDTO positionDTO = null;
        for(int i = 0; i < users.getSize(); i++){
            positionDTO = (SfPositionDTO)users.getDTO(i);
            userOptions.append("<option value=");
            userOptions.append(positionDTO.getUserId()+"&&"+positionDTO.getUserName());
            userOptions.append(">");
            userOptions.append(positionDTO.getUserName());
//            userOptions.append("----");
//            userOptions.append(positionDTO.getPositionName());
            userOptions.append("</option>");
        }
        req.setAttribute(ReqAttributeList.APP_USER_OPTIONS, userOptions.toString());
    }
}
