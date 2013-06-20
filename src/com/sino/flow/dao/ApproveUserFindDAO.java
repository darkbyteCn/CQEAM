package com.sino.flow.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.flow.dto.FlowParmDTO;
import com.sino.flow.model.ApproveUserFindModel;


/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @version 1.2
 */
public class ApproveUserFindDAO extends BaseProcessorDAO {
    private ApproveUserFindModel userFindModel = null;
    private FlowParmDTO dto = null;

    public ApproveUserFindDAO(Connection conn, HttpServletRequest req, FlowParmDTO dto) {
        super(conn, req);
        this.dto = dto;
        userFindModel = new ApproveUserFindModel(dto, req);
    }

    /**
     * 功能：获取当前节点的下一节点.如果下一节点有多个，则为分流；否则为直流.
     * @return DTOSet
     * @throws QueryException
     */
    public JSONArray getPositionUsers() throws QueryException, UploadException, ContainerException, JSONException {
        JSONArray arr = new JSONArray();
        //

        return arr;
    }

//    /**
//     * 获取该审批任务的节点信息。
//     * @return SfTaskDefineDTO
//     * @throws QueryException
//     */
//    public SfTaskDefineDTO getPositionTask() throws QueryException{
//        SfTaskDefineDTO taskDTO = new SfTaskDefineDTO();
//        try {
//            SQLModel sqlModel = userFindModel.getPositionTaskModel();
//            SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
//            queryBean.setDTOClassName(SfTaskDefineDTO.class.getName());
//            queryBean.executeQuery();
//            taskDTO = (SfTaskDefineDTO)queryBean.getFirstDTO();
//        } catch (UploadException ex) {
//           // setInvalidLevel(FlowConstant.INVALID_LEVEL_ERROR);
//            setProcessMsg(MessageDefineList.PARSER_ERROR);
//            throw new QueryException(ex.getMessage());
//        }
//        return taskDTO;
//    }
//
//    /**
//     * 查找节点类型，是按职位找人还是按userId找人
//     * @return
//     * @throws UploadException
//     * @throws QueryException
//     * @throws ContainerException
//     */
//    public String getTaskType() throws UploadException, QueryException, ContainerException {
//        String taskType="";
//        SimpleQuery sq=new SimpleQuery(userFindModel.getTaskTypeModel(),conn);
//        sq.executeQuery();
//        RowSet rs=sq.getSearchResult();
//        if(rs!=null&&!rs.isEmpty()){
//            Row row=rs.getRow(0);
//            taskType= (String) row.getValue("TASK_TYPE");
//        }
//        return taskType;
//    }
//       public DTOSet getUsers() throws UploadException, QueryException, ContainerException, DTOException {
//        DTOSet ds=new DTOSet();
//        SfPositionDTO dto=new SfPositionDTO();
//        SimpleQuery sq=new SimpleQuery(userFindModel.getUsersModel(),conn);
//        sq.executeQuery();
//        RowSet rs=sq.getSearchResult();
//        if(rs!=null&&!rs.isEmpty()){
//            Row row=rs.getRow(0);
//            String userId= (String) row.getValue("TASK_USER_ID");
//            String userName=(String) row.getValue("USER_NAME") ;
//            dto.setUserId(userId);
//            dto.setUserName(userName);
//            ds.set(0,dto);
//        }
//        return ds;
//    }
}
