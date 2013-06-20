package com.sino.ams.system.update4pda.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.update4pda.dto.EtsAutoupdateDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsAutoupdateModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsAutoupdateModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author aidy
 * @version 1.0
 */


public class EtsAutoupdateModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：PDA程序版本表(EAM) ETS_AUTOUPDATE 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsAutoupdateDTO 本次操作的数据
     */
    public EtsAutoupdateModel(SfUserDTO userAccount, EtsAutoupdateDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成PDA程序版本表(EAM) ETS_AUTOUPDATE数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */


    /**
     * 功能：框架自动生成PDA程序版本表(EAM) ETS_AUTOUPDATE数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel(EtsAutoupdateDTO etsAutoupdate) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_AUTOUPDATE"
                + " SET"
                + " VERSION = ?,"
                //+ " DESCRIPTION = GETDATE(),"
                + " FILESIZE = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " MODULE = ?";
        sqlArgs.add(etsAutoupdate.getVersion());
        //sqlArgs.add(String.valueOf(etsAutoupdate.getFilesize()));
        sqlArgs.add(etsAutoupdate.getFilesize());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsAutoupdate.getModule());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成PDA程序版本表(EAM) ETS_AUTOUPDATE数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDataByModuleModel(String Module) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
/*        String sqlStr = "SELECT "
                + " VERSION"
                + " FROM"
                + " ETS_AUTOUPDATE"
                + " WHERE"
                + " MODULE = ?";
        sqlArgs.add(Module);*/
        
        String sqlStr = 
        "SELECT MODULE, VERSION, DESCRIPTION, FILESIZE \n" +
        "  FROM ETS_AUTOUPDATE \n" +
        " WHERE MODULE = ?";
        sqlArgs.add(Module);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
            "SELECT MODULE, VERSION, DESCRIPTION, FILESIZE \n" +
            "  FROM ETS_AUTOUPDATE \n" ;
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            return sqlModel;
    }
    
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
            "SELECT MODULE, VERSION, DESCRIPTION, FILESIZE \n" +
            "  FROM ETS_AUTOUPDATE \n" ;
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            return sqlModel;
    }
}