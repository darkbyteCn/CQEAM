package com.sino.ams.match.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.ams.match.dto.EtsItemMatchRecDTO;
import com.sino.ams.match.model.EtsItemMatchRecModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsItemMatchRecDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemMatchRecDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class EtsItemMatchRecDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：ETS_ITEM_MATCH_REC 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchRecDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsItemMatchRecDAO(SfUserDTO userAccount, EtsItemMatchRecDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemMatchRecDTO dtoPara = (EtsItemMatchRecDTO) dtoParameter;
        super.sqlProducer = new EtsItemMatchRecModel((SfUserDTO) userAccount, dtoPara);
    }

    //--------------------------------------------------------------------------------------------------
    //插入多条记录
    public void saveDTOs(DTOSet dtos) throws DataHandleException {
        if (dtos != null && dtos.getSize() > 0) {
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                EtsItemMatchRecDTO dto = (EtsItemMatchRecDTO) dtos.getDTO(i);
                saveDTO(dto);
            }
        }
    }

    public void saveDTO(EtsItemMatchRecDTO dto) throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            //更新 ETS_ITEM_INFO.FINANCE_PROP
            SQLModel sqlModel = modelProducer.updateFinanceProp(dto);
            DBOperator.updateRecord(sqlModel, conn);
            //查询ETS_ITEM_MATCH_REC，是否存在该设备的记录
            sqlModel = modelProducer.getHasBeenModel(dto);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            //如果没有插入,如果有更新.
            if (!sq.hasResult()) {
                insertIntoEIMR(dto);
            } else {
                updateEIMR(dto);
            }
            //插入ETS_ITEM_MATCH_REC_LOG
            insertIntoEIMRL(dto);
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }

    }

    //插入ETS_ITEM_MATCH_REC
    public void insertIntoEIMR(EtsItemMatchRecDTO dto) throws DataHandleException {
        EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRModel(dto);
        DBOperator.updateRecord(sqlModel, conn);
    }

    //更新ETS_ITEM_MATCH_REC
    public void updateEIMR(EtsItemMatchRecDTO dto) throws DataHandleException {
        EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMRModel(dto);
        DBOperator.updateRecord(sqlModel, conn);
    }

    //插入ETS_ITEM_MATCH_REC_LOG
    public void insertIntoEIMRL(EtsItemMatchRecDTO dto) throws DataHandleException {
        EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRLModel(dto);
        DBOperator.updateRecord(sqlModel, conn);
    }

    //--------------------------------------------------------------------------------------------------
    //删除多条记录
    public void delDTOs(DTOSet dtos) throws DataHandleException {
        if (dtos != null && dtos.getSize() > 0) {
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                EtsItemMatchRecDTO dto = (EtsItemMatchRecDTO) dtos.getDTO(i);
                delDTO(dto);
            }
        }
    }

    // 删除具体操作
            //文档说明:
//               1、	将选中的设备属性标记为原设备类别
//               2、	删除ets_item_match表的相应记录
//               3、	记录匹配记录到表ETS_ITEM_MATCH_REC（id、匹配时间、匹配人、system_id、asset_id,匹配方式、原设备类别、新设备类别）
//               4、	记录匹配记录到表ETS_ITEM_MATCH_REC_LOG（id、匹配时间、匹配人、system_id、asset_id,匹配方式、原设备类别、新设备类别）
//               5、	ETS_ITEM_MATCH_COMPLET.CURRENT_UNITS-1
//               6、	更改ETS_FA_ASSETS信息
//               7、	更改FA_ADDITIONS_B@MIS_FA相关信息
    public void delDTO(EtsItemMatchRecDTO dto) throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        String tagNumber = "";
        String misTagNumber = "";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;

            //更新 ETS_ITEM_INFO.FINANCE_PROP
            SQLModel sqlModel = modelProducer.updateFinanceProp(dto);
            DBOperator.updateRecord(sqlModel, conn);

           //更新ETS_ITEM_MATCH_COMPLET
            sqlModel = modelProducer.updateeimcModel(dto);
            DBOperator.updateRecord(sqlModel, conn);

            //删除ets_item_match表的相应记录
            sqlModel = modelProducer.delFromMatchTbl(dto);
            DBOperator.updateRecord(sqlModel, conn);

            //查询ETS_ITEM_MATCH_REC，是否存在该设备的记录
            sqlModel = modelProducer.getHasBeenModel(dto);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            //如果没有插入,如果有更新.
            if (!sq.hasResult()) {
                insertIntoEIMR(dto);
            } else {
                updateEIMR(dto);
            }
            //插入ETS_ITEM_MATCH_REC_LOG
            insertIntoEIMRL(dto);
            //更新ETS_FA_ASSETS信息
            sqlModel = modelProducer.getTagNumber(dto.getAssetId());
            sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            tagNumber = sq.getFirstRow().getStrValue("TAG_NUMBER");
            misTagNumber = sq.getFirstRow().getStrValue("MIS_TAG_NUMBER");

            sqlModel = modelProducer.updateFAInfo(dto.getAssetId(), tagNumber, misTagNumber);
            DBOperator.updateRecord(sqlModel, conn);

            //更新MIS信息
//            sqlModel = modelProducer.updateFA_MIS(tagNumber);
//            DBOperator.updateRecord(sqlModel,conn);
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        }
        finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }

    }

    /**
     * 确认租赁资产：
     * 更改ets_tem_info FINANCE_PROP="OTHERS" ,ATTRIBUTE1="RENT"
     * @param systemIds
     */
    public void confirmRentAssets(String[] systemIds) throws DataHandleException {
        if ((systemIds != null) && (systemIds.length > 0)) {
            String inSqlStr = ArrUtil.arrToSqlStr(systemIds);

            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            SQLModel sqlModel = modelProducer.updateRentInfoModel(inSqlStr);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    /**
     * 确认代管资产：
     * 更改ets_tem_info FINANCE_PROP="OTHERS" ,ATTRIBUTE1="DG"
     * @param systemIds
     * @throws DataHandleException
     */
    public void confirmDGAssets(String[] systemIds) throws DataHandleException {
        if ((systemIds != null) && (systemIds.length > 0)) {
            String inSqlStr = ArrUtil.arrToSqlStr(systemIds);

            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            SQLModel sqlModel = modelProducer.updateDGModel(inSqlStr);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    /**
     * 确认低值易耗资产：
     * 更改ets_tem_info FINANCE_PROP="OTHERS" ,ATTRIBUTE1="DG"
     * @param systemIds
     * @throws DataHandleException
     */
    public void confirmLCAssets(String[] systemIds) throws DataHandleException {
        if ((systemIds != null) && (systemIds.length > 0)) {
            String inSqlStr = ArrUtil.arrToSqlStr(systemIds);

            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            SQLModel sqlModel = modelProducer.updateLCModel(inSqlStr);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    /**
     * 确认低值易耗资产：
     * 更改ets_tem_info FINANCE_PROP="OTHERS" ,ATTRIBUTE1="DG"
     * @param systemIds
     * @throws DataHandleException
     */
    public void confirmCTAssets(String[] systemIds) throws DataHandleException {
        if ((systemIds != null) && (systemIds.length > 0)) {
            String inSqlStr = ArrUtil.arrToSqlStr(systemIds);

            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            SQLModel sqlModel = modelProducer.updateCTModel(inSqlStr);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }
    
    
    public boolean getDistributePrj(String prjId, DTOSet dtos) throws DataHandleException, SQLException {
        boolean operateResult = false;
        if (dtos != null && dtos.getSize() > 0) {
            operateResult = true;
            int dtoCount = dtos.getSize();
            EtsItemMatchRecModel modelProducer = (EtsItemMatchRecModel) sqlProducer;
            try{
            	String params = "(";
            	for (int i = 0; i < dtoCount; i++) {
            		EtsItemMatchRecDTO dto = (EtsItemMatchRecDTO) dtos.getDTO(i);
            		params += "'" + dto.getSystemId() + "',";
            	}
            	params = params.substring(0, params.length()-1) + ")";
            	SQLModel sqlModel = modelProducer.getUpdateDistributePrj(prjId, params);
            	DBOperator.updateRecord(sqlModel, conn);
            	conn.commit();
            }catch(Exception e){
            	e.printStackTrace();
            	conn.rollback();
            }
            if(conn != null){
            	conn.close();
            }
        }
        return operateResult;
    }
}