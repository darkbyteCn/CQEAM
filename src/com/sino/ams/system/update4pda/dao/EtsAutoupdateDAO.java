package com.sino.ams.system.update4pda.dao;


import java.sql.Connection;

import com.sino.ams.system.update4pda.dto.EtsAutoupdateDTO;
import com.sino.ams.system.update4pda.model.EtsAutoupdateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsAutoupdateDAO</p>
 * <p>Description:程序自动生成服务程序“EtsAutoupdateDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author aidy
 * @version 1.0
 */


public class EtsAutoupdateDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：PDA程序版本表(EAM) ETS_AUTOUPDATE 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsAutoupdateDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsAutoupdateDAO(SfUserDTO userAccount, EtsAutoupdateDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    public EtsAutoupdateDTO getDataByModule(String Module) throws QueryException {
        EtsAutoupdateDTO dto = null;
        EtsAutoupdateModel modelProducer = (EtsAutoupdateModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getDataByModuleModel(Module);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EtsAutoupdateDTO.class.getName());
        sq.executeQuery();
        DTOSet ds = sq.getDTOSet();
        dto = (EtsAutoupdateDTO) ds.getDTO(0);
        if (sq.hasResult()) {
            dto = (EtsAutoupdateDTO) sq.getFirstDTO();
        }
        return dto;

    }

    public void setDataByModule(EtsAutoupdateDTO dtoparameter) throws SQLModelException, DataHandleException {


            EtsAutoupdateModel etsObjectModel = (EtsAutoupdateModel) sqlProducer;
            SQLModel sqlModel = etsObjectModel.getDataUpdateModel(dtoparameter);
            DBOperator.updateRecord(sqlModel, conn);
        }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsAutoupdateDTO dtoPara = (EtsAutoupdateDTO) dtoParameter;
        super.sqlProducer = new EtsAutoupdateModel((SfUserDTO) userAccount, dtoPara);
    }

}