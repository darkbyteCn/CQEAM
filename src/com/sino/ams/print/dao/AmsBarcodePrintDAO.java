package com.sino.ams.print.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.print.dto.AmsBarcodePrintDTO;
import com.sino.ams.print.model.AmsBarcodePrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsBarcodePrintDAO</p>
 * <p>Description:程序自动生成服务程序“AmsBarcodePrintDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsBarcodePrintDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：条码打印信息表 AMS_BARCODE_PRINT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsBarcodePrintDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsBarcodePrintDAO(SfUserDTO userAccount, AmsBarcodePrintDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsBarcodePrintDTO dtoPara = (AmsBarcodePrintDTO)dtoParameter;
		super.sqlProducer = new AmsBarcodePrintModel((SfUserDTO)userAccount, dtoPara);
	}

     /**
     * 提交
     * @param
     * @return success
     * @throws java.sql.SQLException
      */
    public boolean submit(FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            AmsBarcodePrintDTO dtoPri = (AmsBarcodePrintDTO)dtoParameter;
            dtoPri.setApplyDate(CalendarUtil.getCurrDate());
            FlowAction fa =null;
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), "3");
                dtoPri.setBatchNo(ong.getOrderNum());
//                dtoPri.setBatchNo("2521-3-20080100016");
                SeqProducer seq = new SeqProducer(conn);
                String Id =  String.valueOf(seq.getStrNextSeq("AMS_BARCODE_PRINT_S"));
                dtoPri.setId(Id);
                createData();

                flowDTO.setApplyId(dtoPri.getId());
                flowDTO.setApplyNo(dtoPri.getBatchNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                fa = new FlowAction(conn, flowDTO);
                fa.flow();

//             userAccount.toString();


            conn.commit();
            prodMessage("SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }


      /**
     * 审批通过
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean approveOrder(FlowDTO flowDTO,String ApproveUser,String sectionRigth) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsBarcodePrintDTO tempDTO = (AmsBarcodePrintDTO) getDTOParameter();
            if(sectionRigth.equals("1")){     //标签审批人
            tempDTO.setApproveUser(StrUtil.strToInt(ApproveUser));
            tempDTO.setApproveDate(CalendarUtil.getCurrDate());
//            setDTOParameter(tempDTO);                                                           //重设dto
            super.updateData();
            }else if(sectionRigth.equals("2")){  //标签打印人
             tempDTO.setPrintUser(StrUtil.strToInt(ApproveUser));
             tempDTO.setPrintDate(CalendarUtil.getCurrDate());
             super.updateData();
            }else if(sectionRigth.equals("3")){  //标签申请人
             tempDTO.setStatus(2);   //状态:已完成
              super.updateData();
            }else if(sectionRigth.equals("4")){  //标签申请人（退回）
             tempDTO.setStatus(1);      //状态:已退回
               super.updateData();
            }


            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);
            fa.flow();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (CalendarException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }


      /**
     * 退回
     * @param flowDTO FlowDTO
     * @throws SQLException
     */
    public void reject(FlowDTO flowDTO) throws SQLException {
        try {
            conn.setAutoCommit(false);
            //业务处理
//             AmsBarcodePrintDTO tmpDTO = (AmsBarcodePrintDTO) getDTOParameter();
//            tmpDTO.setEnabled("N");                                                          //重设 ENABLED
//            setDTOParameter(tmpDTO);                                                           //重设dto
//            super.updateData();
            //流程处理

            //
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
            fb.reject();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}