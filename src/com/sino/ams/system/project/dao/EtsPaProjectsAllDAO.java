package com.sino.ams.system.project.dao;


import java.sql.Connection;

import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.project.model.EtsPaProjectsAllModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ValidateException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsPaProjectsAllDAO</p>
 * <p>Description:程序自动生成服务程序“EtsPaProjectsAllDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsPaProjectsAllDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：项目维护表(EAM) ETS_PA_PROJECTS_ALL 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsPaProjectsAllDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsPaProjectsAllDAO(SfUserDTO userAccount, EtsPaProjectsAllDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsPaProjectsAllDTO dtoPara = (EtsPaProjectsAllDTO) dtoParameter;
        super.sqlProducer = new EtsPaProjectsAllModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入项目维护表(EAM)表“ETS_PA_PROJECTS_ALL”数据。
     * @return boolean
     */
    public void createData() throws DataHandleException {
        boolean operateResult = false;
//        try {
//            String tableName = "ETS_PA_PROJECTS_ALL";
//            DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
//            datChecker.setDBAction(DBActionConstant.INSERT);
//            datChecker.setServletConfig(servletConfig);
//            boolean isValid = datChecker.isDataValid();
//        	  boolean isValid = false;
//            if (!isValid) {
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
//                getMessage().addParameterValue(datChecker.getInValidData());
//            } else {
                super.createData();
                getMessage().addParameterValue("项目");
//            }
//        } catch (ValidateException ex) {
//            ex.printLog();
//            prodMessage(MsgKeyConstant.COMMON_ERROR);
//        }
    }

    /**
     * 功能：更新项目维护表(EAM)表“ETS_PA_PROJECTS_ALL”数据。
     * @return boolean
     */
    public void updateData() throws DataHandleException {
        boolean operateResult = false;
        try {
            String tableName = "ETS_PA_PROJECTS_ALL";
            DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
            datChecker.setDBAction(DBActionConstant.UPDATE);
            datChecker.setServletConfig(servletConfig);
//            boolean isValid = datChecker.isDataValid();
//            if (!isValid) {
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
//                getMessage().addParameterValue(datChecker.getInValidData());
//            } else {
                super.updateData();
                operateResult = true;
                getMessage().addParameterValue("项目");
//            }
        } catch (ValidateException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        }
    }

    /**
     * 功能：删除项目维护表(EAM)表“ETS_PA_PROJECTS_ALL”数据。
     * @return boolean
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("项目维护");
    }

}
