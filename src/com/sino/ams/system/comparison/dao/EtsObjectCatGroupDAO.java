package com.sino.ams.system.comparison.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.system.comparison.dto.EtsObjectCatGroupDTO;
import com.sino.ams.system.comparison.model.EtsObjectCatGroupModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsObjectCatGroupDAO</p>
 * <p>Description:程序自动生成服务程序“EtsObjectCatGroupDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectCatGroupDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectCatGroupDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsObjectCatGroupDAO(SfUserDTO userAccount, EtsObjectCatGroupDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsObjectCatGroupDTO dtoPara = (EtsObjectCatGroupDTO)dtoParameter;
		super.sqlProducer = new EtsObjectCatGroupModel((SfUserDTO)userAccount, dtoPara);
	}



    /**
	 * 功能：更新资产地点表(EAM)表“ETS_OBJECT”数据。
	 * @return boolean
	 */
    public String updateData2(String groupId, String[] codes) throws DataHandleException {
        String operateResult = "";
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            EtsObjectCatGroupDTO tmpDTO = (EtsObjectCatGroupDTO) getDTOParameter();                   // 获取本次的数据
            EtsObjectCatGroupModel model = new EtsObjectCatGroupModel((SfUserDTO) userAccount, tmpDTO);
            DBOperator.updateRecord(model.getDataDeleteModel(groupId), conn);      //删除操作
            for (int i = 0; i < codes.length; i++) {
                DBOperator.updateRecord(model.getDataCreateModel(groupId, codes[i]), conn);    //增加操作
            }
//            createData(objectCategory, codes);
            operateResult = "Y";
            conn.commit();
            hasError = false;
            getMessage().addParameterValue("专业表");
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SQL_ERROR);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        } catch (SQLModelException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hasError) {
                    operateResult ="N";
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(MsgKeyConstant.SQL_ERROR);
            }
        }
        return operateResult;
    }



}