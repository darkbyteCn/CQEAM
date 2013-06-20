package com.sino.soa.td.srv.InquiryRetiredAssetDetail.dao;

import java.sql.Connection;
import com.sino.soa.common.SrvType;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.InquiryRetiredAssetDetail.dto.PageRetiredAssetDTO;
import com.sino.soa.td.srv.InquiryRetiredAssetDetail.model.TDPageRetiredAssetDetailModel;

/**
 * <p>Title: SrvAssetBookDAO</p>
 * <p>Description:程序自动生成服务程序“SrvAssetBookDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * user:wangzp
 * function:查询报废资产基本信息（分页）_TD
 */


public class TDPagRetiredAssetDetailDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private int errorCount = 0;

    /**
     * 功能：资产账簿服务 SRV_ASSET_BOOK 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvAssetBookDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public TDPagRetiredAssetDetailDAO(SfUserDTO userAccount, PageRetiredAssetDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        PageRetiredAssetDTO dtoPara = (PageRetiredAssetDTO) dtoParameter;
        super.sqlProducer = new TDPageRetiredAssetDetailModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：更新报废资产信息。
     * @param ds 传入的数据
     */
    public int saveRetireAssets(DTOSet ds) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        int count = 0;
        try {
            for (int i = 0; i < ds.getSize(); i++) {
                PageRetiredAssetDTO dto = (PageRetiredAssetDTO) ds.getDTO(i);
                initSQLProducer(sfUser, dto);
                TDPageRetiredAssetDetailModel modelProducer = (TDPageRetiredAssetDetailModel) sqlProducer;                
                if (!isAssetExists()) {
                    sqlModel = modelProducer.getDataCreateModel();   //增加
                } else {
                    sqlModel = modelProducer.getDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                } 
                
            }
        } catch (DataHandleException e) {
        	errorCount++;
            e.printLog();
            try {
                SimpleCalendar s = new SimpleCalendar();
                SynLogDTO logDto = new SynLogDTO();
                logDto.setSynType(SrvType.SRV_FA_RETIRE);
                logDto.setSynMsg(e.getMessage());
                logDto.setCreatedBy(sfUser.getUserId());
                logDto.setCreationDate(s.getCalendarValue());
                log.synLog(logDto, conn);
            } catch (CalendarException e1) {
                e1.printLog();
            } catch (DataHandleException e1) {
                e1.printLog();
            }
        } catch (QueryException e) {
            count = -1;
            e.printStackTrace();
            try {
                SimpleCalendar s = new SimpleCalendar();
                SynLogDTO logDto = new SynLogDTO();
                logDto.setSynType(SrvType.SRV_FA_RETIRE);
                logDto.setSynMsg(e.getMessage());
                logDto.setCreatedBy(sfUser.getUserId());
                logDto.setCreationDate(s.getCalendarValue());
                log.synLog(logDto, conn);
            } catch (CalendarException e1) {
                e1.printLog();
            } catch (DataHandleException e1) {
                e1.printLog();
            }
        }
        return count;
    }

    /**
     * 检查资产是否存在
     * @retur 存在：true;  不存在:false
     * @throws QueryException
     */
    public boolean isAssetExists() throws QueryException {
        TDPageRetiredAssetDetailModel modelProducer = (TDPageRetiredAssetDetailModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAssetExistsModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}