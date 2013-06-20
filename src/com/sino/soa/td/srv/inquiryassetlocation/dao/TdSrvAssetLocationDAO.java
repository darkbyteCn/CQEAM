package com.sino.soa.td.srv.inquiryassetlocation.dao;

import java.sql.Connection;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;
import com.sino.soa.td.srv.inquiryassetlocation.dto.TdSrvAssetLocationDTO;
import com.sino.soa.td.srv.inquiryassetlocation.model.TdSrvAssetLocationModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

/**
 * <p>Title: SrvAssetCategoryDAO</p>
 * <p>Description:程序自动生成服务程序“SrvAssetCategoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * date：Sep 15, 2011 9:39:57 PM
 * author：wzp
 * function：同步MIS资产地点_TD
 */


public class TdSrvAssetLocationDAO extends AMSBaseDAO {

    /**
     * 功能：资产类别服务 SRV_ASSET_CATEGORY 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvAssetCategoryDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public TdSrvAssetLocationDAO(SfUserDTO userAccount, TdSrvAssetLocationDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        TdSrvAssetLocationDTO dtoPara = (TdSrvAssetLocationDTO) dtoParameter;
        super.sqlProducer = new TdSrvAssetLocationModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：对地址信息的更新是否成功。(非TD)
     *
     * @param ds                   DTOSet 传入的数据对象
     * @param segment1：部门(资产地点第一段)
     * @return 成功更新的记录数
     */
//    public int synAssetLocation(DTOSet ds, String segment1) {
//        SynLogUtil log = new SynLogUtil();
//        SQLModel sqlModel = null;
//        int count = 0;
//        try {
//            TdSrvAssetLocationDTO firstLocation = (TdSrvAssetLocationDTO) ds.getDTO(0);
//            Row row = getEcomCode(firstLocation.getLocationCombinationCode());
//            String orgId = row.getStrValue("ORGANIZATION_ID");
//            String bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
//            TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
//            for (int i = 0; i < ds.getSize(); i++) {
//                SrvAssetLocationDTO dto = (SrvAssetLocationDTO) ds.getDTO(i);
//                dto.setOrgId(orgId);
//                dto.setBookTypeCode(bookTypeCode);
//                setDTOParameter(dto);
//                if (getOuInfom() < 1) {
//                    sqlModel = modelProducer.getDataCreateModel();
//                } else {
//                    sqlModel = modelProducer.getDataUpdateModel();
//                }
//                if (DBOperator.updateRecord(sqlModel, conn)) {
//                    count++;
//                }
//            }
//
//        } catch (Throwable e) {
//            Logger.logError(e);
//            SimpleCalendar s = new SimpleCalendar();
//            SynLogDTO logDto = new SynLogDTO();
//            logDto.setSynType(SrvType.SRV_FA_LOCATION);
//            logDto.setSynMsg(e.getMessage());
//            logDto.setCreatedBy(userAccount.getUserId());
//            try {
//                logDto.setCreationDate(s.getCalendarValue());
//            } catch (CalendarException e1) {
//                e1.printLog();
//            }
//            try {
//                log.synLog(logDto, conn);
//            } catch (DataHandleException e1) {
//                e1.printLog();
//            }
//        }
//        return count;
//    }

    /**
     * 功能：TD地址信息的更新是否成功。
     * @param ds  DTOSet 传入的数据对象
     * @param segment1：部门(资产地点第一段)
     * @return 成功更新的记录数
     */
    public int synTdAssetLocation(DTOSet ds, String segment1) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        int count = 0;
        Row row =null;
        String orgId ="";
        String bookTypeCode ="";
        try {
        	TdSrvAssetLocationDTO firstLocation = (TdSrvAssetLocationDTO) ds.getDTO(0);
            row = getEcomCode(firstLocation.getLocationCombinationCode());
            if(row!=null){ //4110
                 orgId = row.getStrValue("ORGANIZATION_ID");
                 bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
            }else{    //8110
            	row=getEcomCode2(firstLocation.getLocationCombinationCode());
            	if(row!=null){
                	orgId = row.getStrValue("ORGANIZATION_ID");
                    bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
            	}
            }
            TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
            for (int i = 0; i < ds.getSize(); i++) {
            	TdSrvAssetLocationDTO dto = (TdSrvAssetLocationDTO) ds.getDTO(i);
                dto.setOrgId(orgId);
                dto.setBookTypeCode(bookTypeCode);
                setDTOParameter(dto);
                if (isExistTdAssetsLocation() < 1) {
                    sqlModel = modelProducer.getTdDataCreateModel();
                } else {
                    sqlModel = modelProducer.getTdDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                }
            }
        } catch (Throwable e) {
            Logger.logError(e);
            SimpleCalendar s = new SimpleCalendar();
            SynLogDTO logDto = new SynLogDTO();
            logDto.setSynType(SrvType.SRV_TD_FA_LOCATION);
            logDto.setSynMsg(e.getMessage());
            logDto.setCreatedBy(userAccount.getUserId());
            try {
                logDto.setCreationDate(s.getCalendarValue());
            } catch (CalendarException e1) {
                e1.printLog();
            }
            try {
                log.synLog(logDto, conn);
            } catch (DataHandleException e1) {
                e1.printLog();
            }
        }
        return count;
    }

//    public int getOuInfom() {
//        TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
//        SQLModel sqlModel = modelProducer.getEcouInforModel();
//        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//        RowSet rs = null;
//        try {
//            simp.executeQuery();
//            if (simp.hasResult()) {
//                rs = simp.getSearchResult();
//                return rs.getSize();
//            }
//
//        } catch (QueryException e) {
//            e.printLog();
//        }
//        return 0;
//    }

    /**
     * 功能：判断是否已有此TD地址
     *
     * @return
     */
    public int isExistTdAssetsLocation() {
        TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
        SQLModel sqlModel = modelProducer.isExistTdAssetsLocation();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        RowSet rs = null;
        try {
            simp.executeQuery();
            if (simp.hasResult()) {
                rs = simp.getSearchResult();
                return rs.getSize();
            }

        } catch (QueryException e) {
            e.printLog();
        }
        return 0;
    }

    /**
     * 功能：根据地点代码第二段获取OU组织ID和资产账簿代码 (OU1_4110)
     * @param objectCode 地点代码
     * @return row 行数据对象
     */
    private Row getEcomCode(String objectCode) {
        Row row = null;
        TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcomCodeModel(objectCode);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        try {
            simp.executeQuery();
            if (simp.hasResult()) {
                row = simp.getFirstRow();
            }
        } catch (QueryException e) {
            e.printLog();
        }
        return row;
    }
    
    /**
     * 功能：根据地点代码第二段获取OU组织ID和资产账簿代码 (OU2_8110)
     * @param objectCode 地点代码
     * @return row 行数据对象
     */
    private Row getEcomCode2(String objectCode) {
        Row row = null;
        TdSrvAssetLocationModel modelProducer = (TdSrvAssetLocationModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcomCodeModel2(objectCode);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        try {
            simp.executeQuery();
            if (simp.hasResult()) {
                row = simp.getFirstRow();
            }
        } catch (QueryException e) {
            e.printLog();
        }
        return row;
    }
    
    
}