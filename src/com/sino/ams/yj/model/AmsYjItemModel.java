package com.sino.ams.yj.model;


import java.util.*;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.ams.yj.dto.AmsYjItemDTO;


/**
 * <p>Title: AmsYjItemModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsYjItemModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障装备名称维护
 */


public class AmsYjItemModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急保障设备名称字典表 AMS_YJ_ITEM 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjItemDTO 本次操作的数据
     */
    public AmsYjItemModel(SfUserDTO userAccount, AmsYjItemDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成应急保障设备名称字典表 AMS_YJ_ITEM数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_YJ_ITEM("
                + " ITEM_CODE,"
                + " ITEM_NAME,"
                + " ITEM_CATEGORY,"
                + " CREATION_DATE,"
                + " CREATE_USER"
                + ") VALUES ("
                + " CONVERT(FLOAT,?), ?, '装备', GETDATE(), ?)";

        sqlArgs.add(amsYjItem.getItemCode());
        sqlArgs.add(amsYjItem.getItemName());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急保障设备名称字典表 AMS_YJ_ITEM数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_YJ_ITEM"
                + " SET"
                + " ITEM_NAME = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ITEM_CODE = CONVERT(FLOAT,?)";

        sqlArgs.add(amsYjItem.getItemName());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsYjItem.getItemCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 生效
     */
    public SQLModel getEnableModel(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "UPDATE"
                + " AMS_YJ_ITEM"
                + " SET"
                + " DISABLE_DATE = NULL,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ITEM_CODE =CONVERT(FLOAT,?)";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsYjItem.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：失效 
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_YJ_ITEM\n" +
                "SET DISABLE_DATE=GETDATE(),"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ITEM_CODE = CONVERT(FLOAT,?)";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsYjItem.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急保障设备名称字典表 AMS_YJ_ITEM数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " ITEM_CODE,"
                + " ITEM_NAME,"
                + " ITEM_CATEGORY,"
                + " CREATION_DATE,"
                + " CREATE_USER,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_USER"
                + " FROM"
                + " AMS_YJ_ITEM"
                + " WHERE"
                + " ITEM_CODE = CONVERT(FLOAT,?)";
        sqlArgs.add(amsYjItem.getItemCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急保障设备名称字典表 AMS_YJ_ITEM多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " ITEM_CODE,"
                    + " ITEM_NAME,"
                    + " ITEM_CATEGORY,"
                    + " CREATION_DATE,"
                    + " CREATE_USER,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_USER"
                    + " FROM"
                    + " AMS_YJ_ITEM"
                    + " WHERE"
                    + " (? IS NULL OR ITEM_CODE LIKE ?)"
                    + " AND (? IS NULL OR ITEM_NAME LIKE ?)"
                    + " AND (? IS NULL OR ITEM_CATEGORY LIKE ?)"
                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
                    + " AND (? IS NULL OR CREATE_USER LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_USER LIKE ?)";
            sqlArgs.add(amsYjItem.getItemCode());
            sqlArgs.add(amsYjItem.getItemCode());
            sqlArgs.add(amsYjItem.getItemName());
            sqlArgs.add(amsYjItem.getItemName());
            sqlArgs.add(amsYjItem.getItemCategory());
            sqlArgs.add(amsYjItem.getItemCategory());
            sqlArgs.add(amsYjItem.getCreationDate());
            sqlArgs.add(amsYjItem.getCreationDate());
            sqlArgs.add(amsYjItem.getCreateUser());
            sqlArgs.add(amsYjItem.getCreateUser());
            sqlArgs.add(amsYjItem.getLastUpdateDate());
            sqlArgs.add(amsYjItem.getLastUpdateDate());
            sqlArgs.add(amsYjItem.getLastUpdateUser());
            sqlArgs.add(amsYjItem.getLastUpdateUser());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急保障设备名称字典表 AMS_YJ_ITEM页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjItemDTO amsYjItem = (AmsYjItemDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " CONVERT(VARCHAR,ITEM_CODE) ITEM_CODE,"
                + " ITEM_NAME,"
                + " ITEM_CATEGORY,"
                + " CREATION_DATE,"
                + " dbo.APP_GET_USER_NAME(CREATE_USER) CREATE_USER,"
                + " LAST_UPDATE_DATE,"
                + " dbo.APP_GET_USER_NAME(LAST_UPDATE_USER) LAST_UPDATE_USER,"
                + " DISABLE_DATE"
                + " FROM"
                + " AMS_YJ_ITEM"
                + " WHERE"
                + " ("+ SyBaseSQLUtil.isNull() +" OR ITEM_NAME LIKE ?)";
                if(!amsYjItem.getItemCode().equals("")){
                    sqlStr+=" AND ITEM_CODE = CONVERT(FLOAT,?)";
                }else{
                    sqlStr+=" AND CONVERT(FLOAT,?) =0 ";
                }
        sqlArgs.add(amsYjItem.getItemName());
        sqlArgs.add(amsYjItem.getItemName());
        sqlArgs.add(amsYjItem.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
   

     public SQLModel doVerify(String itemName) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql ="SELECT 1 FROM AMS_YJ_ITEM AYI WHERE AYI.ITEM_NAME = ?";
        strArg.add(itemName);
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }
}