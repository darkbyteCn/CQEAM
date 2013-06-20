package com.sino.ams.dzyh.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.dzyh.model.EamDhBillLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhBillLDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhBillLDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhBillLDAO extends AMSBaseDAO {

	private EamDhBillLDTO edblDto;

	/**
	 * 功能：表结构定义-L(EAM) EAM_DH_BILL_L 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhBillLDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhBillLDAO(SfUserDTO userAccount, EamDhBillLDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		edblDto = null;
		edblDto = dtoParameter;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EamDhBillLDTO dtoPara = (EamDhBillLDTO)dtoParameter;
		super.sqlProducer = new EamDhBillLModel((SfUserDTO)userAccount, dtoPara);
	}
	
	public void createData() throws DataHandleException {
	    super.createData();
	    getMessage().addParameterValue("管理低值易耗行表(EAM)");
	}
	
	/**
	 * 提交数据
	 * @param lineSet
	 * @return
	 * @throws SQLException
	 * @throws SQLModelException
	 */
	public boolean submitSaveData(DTOSet lineSet) throws SQLException, SQLModelException {
	    boolean success;
	    success = false;
	    boolean autoCommit = true;
	    try {
	        autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        String headId = edblDto.getBillHeaderId();
	        edblDto.setCreationDate(CalendarUtil.getCurrDate());
	        OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "DZYH");
	        System.out.println("\n=====================" + ong.getOrderNum());
	        edblDto.setBillNo(ong.getOrderNum());
	        if(headId.equals(""))
	        {
	            SeqProducer seq = new SeqProducer(conn);
	            //TODO 
	            headId =  "" + seq.getStrNextSeq("EAM_DH_BILL_H");
	            edblDto.setBillHeaderId(headId);
	            edblDto.setBillStatus("1");
	            saveHeaders(edblDto);
	            System.out.println("=======saveHeaders===---------------->>>>>>>>>>>>>" + lineSet.getSize());
	            createData();
	        } else {
	            edblDto.setBillStatus("1");
	            updateData();
	            deleteLines(headId);
	        }
	        saveLines(lineSet);
	        System.out.println("=======saveLines===---------------->>>>>>>>>>>>>" + lineSet.getSize());
	        saveEIILines(lineSet);
	        System.out.println("=======saveEIILines===--------------->>>>>>>>>>>>>" + lineSet.getSize());
	        success = true;
	    }
	    catch(SQLException e) {
	        Logger.logError(e);
	    }
	    catch(DataHandleException e) {
	        e.printLog();
	    }
	    catch(CalendarException e) {
	        e.printLog();
	    }
	    finally {
	        if(success) {
	            prodMessage("SPARE_SAVE_SUCCESS");
	            conn.commit();
	        } else {
	            prodMessage("SPARE_SAVE_FAILURE");
	            conn.rollback();
	        }
	        conn.setAutoCommit(autoCommit);
	    }
	    return success;
	}
	
	/**
	 * 保存头信息
	 * @param lineData
	 * @throws DataHandleException
	 * @throws SQLModelException
	 */
	public void saveHeaders(EamDhBillLDTO lineData) throws DataHandleException, SQLModelException
	{
	    EamDhBillLDAO lineDAO = new EamDhBillLDAO(userAccount, null, conn);
	    EamDhBillLModel model = new EamDhBillLModel(userAccount, null);
	    lineData.setBillHeaderId(edblDto.getBillHeaderId());
	    lineDAO.setDTOParameter(lineData);
	    DBOperator.updateRecord(model.getDataCreateHeaderModel(lineData), conn);
	}
	
	/**
	 * 保存行信息
	 * @param lineSet
	 * @throws DataHandleException
	 */
	public void saveLines(DTOSet lineSet) throws DataHandleException {
	    if(lineSet != null && !lineSet.isEmpty()) {
	        EamDhBillLDAO edblDao = new EamDhBillLDAO(userAccount, null, conn);
	        for(int i = 0; i < lineSet.getSize(); i++) {
	            EamDhBillLDTO lineData = (EamDhBillLDTO)lineSet.getDTO(i);
	            lineData.setBillHeaderId(edblDto.getBillHeaderId());
	            edblDao.setDTOParameter(lineData);
	            edblDao.createData();
	        }
	
	    }
	}
	
	/**
	 * 刪除行信息
	 * @param billHeaderId
	 * @throws DataHandleException
	 */
	public void deleteLines(String billHeaderId) throws DataHandleException {
	    EamDhBillLModel model = new EamDhBillLModel(userAccount, null);
	    DBOperator.updateRecord(model.getDeleteByBillHeaderIdModel(billHeaderId), conn);
	}
	
	/**
	 * 将数据写入ETS_ITEM_INFO
	 * @param lineSet
	 * @throws DataHandleException
	 * @throws SQLModelException
	 */
	public void saveEIILines(DTOSet lineSet) throws DataHandleException, SQLModelException {
	    if(lineSet != null && !lineSet.isEmpty()) {
	        EamDhBillLDAO lineDAO = new EamDhBillLDAO(userAccount, null, conn);
	        EamDhBillLModel model = new EamDhBillLModel(userAccount, null);
	        for(int i = 0; i < lineSet.getSize(); i++) {
	            EamDhBillLDTO lineData = (EamDhBillLDTO)lineSet.getDTO(i);
	            lineData.setBillHeaderId(edblDto.getBillHeaderId());
	            lineDAO.setDTOParameter(lineData);
	            DBOperator.updateRecord(model.getCreateEIIModel(lineData), conn);
	        }
	
	    }
	}
	
}