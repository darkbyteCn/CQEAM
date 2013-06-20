package com.sino.pda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDtlDTO;
import com.sino.ams.workorder.model.WorkorderModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2007-11-22
 * Time: 8:50:40
 * Function:Excel工单提交（代替PDA上传功能）
 */
public class XlsOrderSubmint extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        Request2DTO req2DTO = new Request2DTO();
        try {
            conn = DBManager.getDBConnection();
            boolean autoCommit=conn.getAutoCommit();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            String workorderNo = "";
            OrderModel orderModel=new OrderModel();
            WorkorderModel workorderModel=new WorkorderModel();
            SQLModel sqlModel=new SQLModel();
            WorkOrderUtil orderUtil=new WorkOrderUtil();

            req2DTO.setDTOClassName(EtsWorkorderDtlDTO.class.getName());
            DTOSet dtoSet = req2DTO.getDTOSet(req);
            boolean hasSubmit=false;

            for (int i = 0; i < dtoSet.getSize(); i++) {
                EtsWorkorderDtlDTO orderDtlDTO = (EtsWorkorderDtlDTO) dtoSet.getDTO(i);
                if(StrUtil.isEmpty(orderDtlDTO.getWorkorderNo())){
                      continue;
                }
                if(workorderNo.equals("")){
                    workorderNo=orderDtlDTO.getWorkorderNo();
                    hasSubmit=orderUtil.hasSubmit(workorderNo,conn);
                }else{
                    hasSubmit=orderUtil.hasSubmit(orderDtlDTO.getWorkorderNo(),conn);
                }
                if(hasSubmit){
                    continue;
                }

                /**
                 * 写dtl表和interface表
                 */
                sqlModel=orderModel.getInsertDtlModel(orderDtlDTO);
                DBOperator.updateRecord(sqlModel,conn);
                sqlModel=orderModel.getInsertInterfaceModel(orderDtlDTO);
                DBOperator.updateRecord(sqlModel,conn);
                /**
                 * 更新工单进度、工单状态
                 */
                if(!orderDtlDTO.getWorkorderNo().equals(workorderNo)){
                    if(!workorderNo.equals("")){
                        sqlModel=orderModel.getUpdateUploadOrderModel(workorderNo,"");
                        DBOperator.updateRecord(sqlModel,conn);
                        sqlModel=workorderModel.getUpdateOrderProcessModel(workorderNo, DictConstant.WORKORDER_NODE_UPLODADED,true);
                        DBOperator.updateRecord(sqlModel,conn);
                    }
                }
                workorderNo=orderDtlDTO.getWorkorderNo();

            }

            conn.commit();
            conn.setAutoCommit(autoCommit);

           forwardURL="/workorder/order/uploadFromXLS.jsp";
        } catch (PoolException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (DTOException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (SQLException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (DataHandleException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (QueryException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (ContainerException e) {
            forwardURL= URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}