package com.sino.ams.appbase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class AMSBaseDAO extends BaseDAO {//该类的存在是为了免去其他DAO类处处定义用户对象
	protected SfUserDTO userAccount = null;

	public AMSBaseDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.userAccount = (SfUserDTO)userAccount;
	}


    /**
     * 功能：获取单独的DTO对象
     *
     * @param sqlModel 查询SQL
     * @param cls      欲构造的DTO类
     * @param <T>      DTO的实现类
     * @return DTO的实现类实例
     * @throws com.sino.base.exception.QueryException 查询出错时抛出该异常
     */
    public <T extends DTO> T searchDTOByModel(SQLModel sqlModel, Class cls) throws QueryException {
        setDTOClassName(cls.getName());
        return (T) getDataBySQLModelKey(sqlModel);
    }

    /**
     * 功能：根据主键查询单独的DTO对象
     *
     * @param <T> DTO的实现类
     * @return DTO的实现类实例
     * @throws QueryException 查询出错时抛出该异常
     */
    public <T extends DTO> T searchDTOByPrimaryKey() throws QueryException {
        T data = null;
        try {
            data = (T) searchDTOByModel(sqlProducer.getPrimaryKeyDataModel(), dtoParameter.getClass());
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return data;
    }

    /**
     * 功能：查询DTO对象列表
     *
     * @param sqlModel 查询SQL
     * @param cls      欲构造的DTO类
     * @return DTO对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public <T extends DTO> List<T> searchListByModel(SQLModel sqlModel, Class cls) throws QueryException {
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.setDTOClassName(cls.getName());
        splq.executeQuery();
        return splq.getListResult();
    }

    /**
     * 功能：查询DTO对象列表
     * @param foreignKey 外键字段名称
     * @return DTO对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public <T extends DTO> List<T> searchListByForeignKey(String foreignKey) throws QueryException {
        List<T> listData = null;
        try {
            SQLModel sqlModel = sqlProducer.getDataByForeignKeyModel(foreignKey);
            listData = searchListByModel(sqlModel, dtoParameter.getClass());
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return listData;
    }

    /**
     * 功能：查询DTO对象列表
     *
     * @param sqlModel 查询SQL
     * @return DTO对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public DTOSet searchDTOSetByModel(SQLModel sqlModel) throws QueryException {
        return searchDTOSetByModel(sqlModel, dtoParameter.getClass());
    }

    /**
     * 功能：查询DTO对象列表
     *
     * @param sqlModel 查询SQL
     * @param cls      欲构造的DTO类
     * @return DTO对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public DTOSet searchDTOSetByModel(SQLModel sqlModel, Class cls) throws QueryException {
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.setDTOClassName(cls.getName());
        splq.executeQuery();
        return splq.getDTOSet();
    }

    /**
     * 功能：查询DTO对象列表
     * @param foreignKey 外键字段名称
     * @return DTO对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public DTOSet searchDTOSetByForeignKey(String foreignKey) throws QueryException {
        DTOSet dtos = null;
        try {
            SQLModel sqlModel = sqlProducer.getDataByForeignKeyModel(foreignKey);
            dtos = searchDTOSetByModel(sqlModel, dtoParameter.getClass());
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return dtos;
    }

    /**
     * 功能：执行SQL语句（插入、更新、删除）
     *
     * @param sqlModel SQL插入、更新、删除语句
     * @throws com.sino.base.exception.DataHandleException 当发生数据库执行错误时抛出该异常
     */
    public void executeUpdate(SQLModel sqlModel) throws DataHandleException {
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：根据外键执行删除
     * @param foreignKey 外键字段名称
     * @throws com.sino.base.exception.DataHandleException 当发生数据库执行错误时抛出该异常
     */
    public void deleteByForeignKey(String foreignKey) throws DataHandleException {
        try {
            SQLModel sqlModel = sqlProducer.getDeleteByForeignKeyModel(foreignKey);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataHandleException();
        }
    }

    /**
     * 功能：执行查询语句，判断是否有查询结果
     *
     * @param sqlModel SQL查询语句
     * @return true表示有查询结果，false表示没有查询结果
     * @throws QueryException  当发生数据库查询错误时抛出该异常
     */
    public boolean hasSearchResult(SQLModel sqlModel) throws QueryException {
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }

    public String getNextGUID(){
        SeqProducer seqProducer = new SeqProducer(conn);
        return seqProducer.getGUID();        
    }
}
