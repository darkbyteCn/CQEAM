package com.sino.framework.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dto.AMSBaseDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.message.MessageManager;

import com.sino.framework.dto.BaseLocaleDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoCMS</p>
 * <p>Description: 河南移动合同管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class BaseService {
    protected BaseUserDTO userAccount = null;
    protected DTO dtoParameter = null;
    protected Connection conn = null;
    protected Message message = null;//功能，执行某个方法之后的消息。
    protected ServletConfigDTO servletConfig = null;

    public BaseService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        this.userAccount = userAccount;
        this.dtoParameter = dtoParameter;
        this.conn = conn;
        this.message = new Message();
    }

    public void setDTOParameter(DTO dtoParameter) {
        this.dtoParameter = dtoParameter;
    }

    /**
     * 功能：设置Web.xml配置的相关信息
     *
     * @param servletConfig ServletConfigDTO
     */
    public void setServletConfig(ServletConfigDTO servletConfig) {
        this.servletConfig = servletConfig;
    }

    /**
     * 功能：根据消息键名获取消息对象
     *
     * @param messageKey String
     */
    protected void prodMessage(String messageKey) {
        message = new Message();
        Message refMessage = null;
        if (dtoParameter instanceof BaseLocaleDTO) {
            BaseLocaleDTO localeDTO = (BaseLocaleDTO) dtoParameter;
            refMessage = MessageManager.getMessage(localeDTO.getLocale(), messageKey);
        } else {
            refMessage = MessageManager.getMessage(messageKey);
        }
        if (refMessage != null) {
            message.setMessageKey(messageKey);
            message.setMessageValue(refMessage.getMessageValue(false));
            message.setParent(refMessage.getParent());
        } else {
            message = Message.getUndefinedMessage();
        }
    }

    public Message getMessage() {
        return message;
    }

    public DTO getDTOParameter() {
        return dtoParameter;
    }


    /**
     * 功能：结束事务控制。
     * @param operateResult 应用执行的结果
     * @param defaultCommit 数据库服务器的默认提交方式
     * @throws DataHandleException
     */
    public void endTransaction(boolean operateResult, boolean defaultCommit) throws DataHandleException {
        try {
            boolean autoCommit = conn.getAutoCommit();
            if (autoCommit) {
                throw new DataHandleException("本次会话未处于事务控制中...");
            }
            if (operateResult) {
                conn.commit();
            } else {
                conn.rollback();
            }
            conn.setAutoCommit(defaultCommit);
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

    /**
     * 功能：处理数据主键。
     * @param operateResult 应用执行的结果
     * @param isNew 提交的DTO是否为新数据
     */
    public void processPrimaryKey(boolean operateResult, boolean isNew) {
        if(!operateResult && isNew){
            if(dtoParameter instanceof AMSBaseDTO){
                AMSBaseDTO dto = (AMSBaseDTO)dtoParameter;
                dto.clearPrimaryKey();
            }
        }
    }

    /**
     * 功能：结束事务控制。
     * @param operateResult 应用执行的结果
     * @throws DataHandleException
     */
    public void endTransaction(boolean operateResult) throws DataHandleException {
        endTransaction(operateResult, true);
    }
}
