package com.sino.ams.system.item.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.item.assistant.SysItemDataHelper;
import com.sino.ams.system.item.dto.EtsSysitemDistributeDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.item.model.EtsSystemItemModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ValidateException;
import com.sino.base.log.Logger;
import com.sino.base.util.ConvertUtil;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsSystemItemDAO</p>
 * <p>Description:程序自动生成服务程序“EtsSystemItemDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsSystemItemDAO extends AMSBaseDAO {

    /**
	 * 功能：设备分类表(EAM) ETS_SYSTEM_ITEM 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSystemItemDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsSystemItemDAO(SfUserDTO userAccount, EtsSystemItemDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}


    /**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EtsSystemItemDTO dtoPara = (EtsSystemItemDTO)dtoParameter;
		super.sqlProducer = new EtsSystemItemModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入设备分类表(EAM)表“ETS_SYSTEM_ITEM”数据。
	 * @return boolean
	 */
	public boolean createData(String[] orgIds){
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
			String tableName = "ETS_SYSTEM_ITEM";
			DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
			datChecker.setDBAction(DBActionConstant.INSERT);
            datChecker.setServletConfig(servletConfig);
//            boolean isValid = datChecker.isDataValid();
//            if(!isValid){
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
//                getMessage().addParameterValue("设备分类");
//            } else {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                EtsSystemItemDTO tmpDTO = (EtsSystemItemDTO)getDTOParameter();
                tmpDTO.setItemCode(getNextItemCode());          //获取ItemCode
                setDTOParameter(tmpDTO);                        //重设dto
                super.createData();                             //插入数据
                DTOSet distrDatas = SysItemDataHelper.getDistriDatas(tmpDTO, orgIds);     //获取
                EtsSysitemDistributeDTO tmpDTO2 = (EtsSysitemDistributeDTO)distrDatas.getDTO(0);
                EtsSysitemDistributeDAO dao = new EtsSysitemDistributeDAO(userAccount,tmpDTO2,conn);
                dao.createDistriDatas(distrDatas);                                                  //根据itemcode进行插入操作
                operateResult = true;
                conn.commit();
                hasError = false;
                getMessage().addParameterValue("设备分类表");
//            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DTOException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        } catch (ValidateException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        } finally{
            try {
                if(hasError){
                    conn.rollback();                      //回滚
                }
                conn.setAutoCommit(autoCommit);          //恢复以前状态
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
	}

    private String getNextItemCode() throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getGUID();//ConvertUtil.int2String( seqProducer.getStrNextSeq("ETS_SYSTEM_ITEM") );
    }


    /**
	 * 功能：更新设备分类表(EAM)表“ETS_SYSTEM_ITEM”数据，。
     * @param  orgIds
	 * @return boolean
	 */
	public boolean updateData(String[] orgIds,String itemCode){
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try{
         	String tableName = "ETS_SYSTEM_ITEM";
			DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
			datChecker.setDBAction(DBActionConstant.UPDATE);
            datChecker.setServletConfig(servletConfig);
//            boolean isValid = datChecker.isDataValid();
//            if(!isValid){
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
//                getMessage().addParameterValue("设备分类");
//            } else {

                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                super.updateData();                                                              //ETS_SYSTEM_ITEM表修改操作

        //        EtsSysitemDistributeDTO DistrDTO = (EtsSysitemDistributeDTO)getDTOParameter();
        //        EtsSysitemDistributeDAO dao2 = new EtsSysitemDistributeDAO(SfUser,DistrDTO,conn);
        //        dao2.deleteData();                                                               //ETS_SYSITEM_DISTRIBUTE删除操作

                EtsSystemItemDTO tmpDTO = (EtsSystemItemDTO)getDTOParameter();                   // 获取本次的数据

                DTOSet distrDatas = SysItemDataHelper.getDistriDatas(tmpDTO, orgIds);
                EtsSysitemDistributeDTO tmpDTO2 = new EtsSysitemDistributeDTO();
                EtsSysitemDistributeDAO dao = new EtsSysitemDistributeDAO(userAccount,tmpDTO2,conn);
                dao.deleteData(itemCode);
                dao.createDistriDatas(distrDatas);                                                  //根据itemcode进行插入操作
                operateResult = true;

                conn.commit();
                hasError = false;
                getMessage().addParameterValue("设备分类表");
//            }
        }catch(SQLException ex){
          Logger.logError(ex);
          prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DTOException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
         } catch (ValidateException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        }finally{
           try{
              if(hasError){
                  conn.rollback();
              }
              conn.setAutoCommit(autoCommit);
           }catch(SQLException ex){
               Logger.logError(ex);
               prodMessage(MsgKeyConstant.SQL_ERROR);
           }
        }
		return operateResult;
	}

	/**
	 * 功能：使选中的 ETS_SYSTEM_ITEM 中的数据失效即使表ETS_SYSTEM_ITEM中的ENABLED为“N”。
     * @param  itemCode
	 * @return boolean
	 */
	public boolean deleteData(String itemCode){
		boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try{
        autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        EtsSystemItemDTO tmpDTO = (EtsSystemItemDTO)getDTOParameter();
        tmpDTO.setEnabled("N");                                                          //重设 ENABLED
        setDTOParameter(tmpDTO);                                                           //重设dto
        super.updateData();                                                              //ETS_SYSTEM_ITEM表修改操作
        EtsSysitemDistributeDTO tmpDTO2 = new EtsSysitemDistributeDTO();
        EtsSysitemDistributeDAO dao = new EtsSysitemDistributeDAO(userAccount,tmpDTO2,conn);
        dao.deleteData(itemCode);
        operateResult = true;
        conn.commit();
        hasError = false;
        getMessage().addParameterValue("设备分类表");
        }catch(SQLException ex){
          Logger.logError(ex);
          prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        }finally{
           try{
              if(hasError){
                  conn.rollback();
              }
              conn.setAutoCommit(autoCommit);
           }catch(SQLException ex){
               Logger.logError(ex);
               prodMessage(MsgKeyConstant.SQL_ERROR);
           }
        }
		return operateResult;
	}

}
