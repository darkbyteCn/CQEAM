package com.sino.rds.appbase.service;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.dto.DTO;
import com.sino.base.message.Message;
import com.sino.base.message.MessageManager;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseLocaleDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.RDSConstantConfigManager;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.foundation.db.structure.ConnParser;

import java.sql.Connection;

public class RDSBaseService implements CalendarConstant {
    protected BaseUserDTO userAccount = null;
    protected DTO dtoParameter = null;
    protected Connection conn = null;
    protected Message message = null;//功能，执行某个方法之后的消息。
    private RDSBaseDAO primaryDAO = null;
    protected ConnParser parser = null;

    public RDSBaseService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		this.userAccount = userAccount;
		this.dtoParameter = dtoParameter;
		this.conn = conn;
        parser = new ConnParser(conn);
		this.message = new Message();
    }

    protected void setPrimaryDAO(RDSBaseDAO primaryDAO){
        this.primaryDAO = primaryDAO;
    }

	public void setDTOParameter(DTO dtoParameter) {
		this.dtoParameter = dtoParameter;
	}

	/**
	 * 功能：根据消息键名获取消息对象
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
     * 功能：根据异常构造错误消息。
     *
     * @param ex Throwable
     */
    protected void prodMessage(Throwable ex) {
        if(ex != null && !StrUtil.isEmpty(ex.getMessage())){
            message = new Message();
            message.setMessageValue(ex.getMessage());
        } else {
            prodMessage("COMMON_ERROR");
        }
        message.setIsError(true);
    }

    protected String getUserId(){
        return RDSConstantConfigManager.getUserId(userAccount);
    }

    public boolean isOracleDBProduct(){
        return parser.isOracleDBProduct();
    }

    public BaseSQLProducer getSQLProducer(){
        BaseSQLProducer sqlProducer = null;
        if(primaryDAO != null){
            sqlProducer = primaryDAO.getSQLProducer();
        }
        return sqlProducer;
    }
}
