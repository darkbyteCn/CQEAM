package com.sino.rds.appbase.dao;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.RDSConstantConfigManager;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.foundation.db.structure.ConnParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class RDSBaseDAO extends BaseDAO implements CalendarConstant {
    protected ConnParser parser = null;

    public RDSBaseDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        initConnParser();
    }

    private void initConnParser(){
        parser = new ConnParser(conn);
    }

    public void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        try {
            initConnParser();
            Class cls = getClass();
            String clsName = cls.getSimpleName();
            String packageName = cls.getPackage().getName();
            clsName = clsName.substring(0, clsName.length() - 3);
            clsName += "Model";
            packageName = packageName.substring(0, packageName.lastIndexOf(".") + 1);
            packageName += "model.";
            if (isOracleDBProduct()) {
                packageName += "oracle.";
                clsName = "Oracle" + clsName;
            } else if (isSybaseDBProduct()) {//SyBase数据库
                packageName += "sybase.";
                clsName = "Sybase" + clsName;
            }
            clsName = packageName + clsName;
            Object[] modelParameters = {userAccount, dtoParameter};
            sqlProducer = (DefaultRDSBaseSQLModelImpl) ReflectionUtil.getInstance(clsName, modelParameters);
        } catch (ReflectException ex) {
            ex.printLog();
            throw new RuntimeException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new RuntimeException(ex);
        }
    }

    public Map getExportMap() {
        return null;
    }

    /**
     * 功能：获取单独的DTO对象
     *
     * @param sqlModel 查询SQL
     * @param cls      欲构造的DTO类
     * @param <T>      DTO的实现类
     * @return DTO的实现类实例
     * @throws com.sino.base.exception.QueryException
     *          查询出错时抛出该异常
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
            if (data instanceof RDSBaseFrm) {
                RDSBaseFrm frm = (RDSBaseFrm) data;
                frm.setDataSaved("Y");
            }
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
     *
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
     * 功能：查询Row对象列表
     *
     * @param sqlModel 查询SQL
     * @return RowSet对象列表
     * @throws QueryException 查询出错时抛出该异常
     */
    public RowSet searchRowSetByModel(SQLModel sqlModel) throws QueryException {
        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
        splq.setCalPattern(getCalPattern());
        splq.executeQuery();
        return splq.getSearchResult();
    }

    /**
     * 功能：获取下一序列号
     *
     * @param sequenceName 序列号名称
     * @return 下一序列值
     * @throws java.sql.SQLException 生成序列出错时抛出该异常
     */
    public String getStrNextSeq(String sequenceName) throws SQLException {
        String seqNumber = "";
        SeqProducer sqlProducer = new SeqProducer(conn);
        if (parser.isOracleDBProduct()) {//Oracle数据库
            seqNumber = String.valueOf(sqlProducer.getStrNextSeq(sequenceName));
        } else if (parser.isSybaseDBProduct()) {//SyBase数据库
            seqNumber = sqlProducer.getGUID();
        }
        return seqNumber;
    }

    /**
     * 功能：执行SQL语句（插入、更新、删除）
     *
     * @param sqlModel
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    public void executeUpdate(SQLModel sqlModel) throws DataHandleException {
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：执行查询语句，判断是否有查询结果
     *
     * @param sqlModel
     * @return true表示有查询结果，false表示没有查询结果
     * @throws QueryException
     */
    public boolean hasSearchResult(SQLModel sqlModel) throws QueryException {
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }

    protected String getUserId() {
        return RDSConstantConfigManager.getUserId(userAccount);
    }

    protected boolean isOracleDBProduct(){
        return parser.isOracleDBProduct();
    }

    protected boolean isSybaseDBProduct(){
        return parser.isSybaseDBProduct();
    }

    public BaseSQLProducer getSQLProducer(){
        return sqlProducer;
    }
}