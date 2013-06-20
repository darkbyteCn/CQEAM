package com.sino.framework.sql;

import com.sino.base.SinoBaseObject;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public abstract class BaseSQLProducer extends SinoBaseObject implements CalendarConstant{
    protected DTO dtoParameter = null;
    protected BaseUserDTO userAccount = null;
	protected ServletConfigDTO servletConfig = null;

    public BaseSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        this.userAccount = userAccount;
        this.setDTOParameter(dtoParameter);
    }

	/**
	 * 功能：设置初始化配置参数
	 * @param servletConfig ServletConfigDTO
	 */
	public void setServletConfig(ServletConfigDTO servletConfig) {
		if(servletConfig != null){
			this.servletConfig = servletConfig;
		}
	}

    /**
     * 功能：设置新的参数，可以进行新的SQL构造。
     * @param dtoParameter DTO
     */
    public void setDTOParameter(DTO dtoParameter){
        this.dtoParameter = dtoParameter;
    }

    /**
     * 功能：获取用户信息。
     * @return BaseUserDTO 具体的应用应当将返回的用户信息转型至相应的继承类。
     */
    public BaseUserDTO getUserAccount(){
        return userAccount;
    }

    /**
     * 功能：返回数据新增的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getDataCreateModel() throws SQLModelException{
        return null;
    }

    /**
     * 功能：返回数据修改的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getDataUpdateModel() throws SQLModelException{
        return null;
    }

    /**
     * 功能：返回数据删除的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getDataDeleteModel() throws SQLModelException{
        return null;
    }

	/**
	 * 功能：返回根据主键进行数据删除的SQLModel
	 * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDeleteByPrimaryKeyModel() throws SQLModelException{
		return null;
    }

    /**
     * 功能：返回数据详细信息的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getPrimaryKeyDataModel() throws SQLModelException{
        return null;
    }

    /**
     * 功能：根据外键获取数据
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @param foreignKey 外键字段名称对应的DTO属性。
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) throws SQLModelException{
        return null;
    }

	/**
	 * 功能：根据外键删除数据
	 * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
	 * @param foreignKey 外键字段名称对应的DTO属性。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) throws SQLModelException{
		return null;
	}

    /**
     * 功能：返回多行数据的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getMuxDataModel() throws SQLModelException{
        return null;
    }

    /**
     * 功能：返回页面翻页查询时所需要的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现</B>
     * @return SQLModel
	 * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException{
        return null;
    }


    /**
     * 功能：将传入的参数值清空。
     * <B>使用场合：当对数据进行相应操作后，需要进行新的查询，而此时不想要原参数其作用时可以调用此方法</B>
     * @throws DTOException
     */
    public void clearDTOParameter() throws DTOException{
        try {
            String dtoName = DTO.class.getName();
            dtoParameter = (DTO) Class.forName(dtoName).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        } catch (IllegalAccessException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        } catch (InstantiationException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        }
    }
    
    public SQLModel getExportModel() throws SQLModelException{
        SQLModel sm = null;
        try {
            sm =  getPageQueryModel();
        } catch (SQLModelException e) {
            Logger.logError(e);
        }
        return sm;
    }
}
