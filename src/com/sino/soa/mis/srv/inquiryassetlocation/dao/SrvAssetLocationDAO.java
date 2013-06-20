package com.sino.soa.mis.srv.inquiryassetlocation.dao;


import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;
import com.sino.soa.mis.srv.inquiryassetlocation.model.SrvAssetLocationModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;


/**
 * <p>Title: SrvAssetCategoryDAO</p>
 * <p>Description:程序自动生成服务程序“SrvAssetCategoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author wzp
 * @version 1.0
 *          update:2011-09-08
 */


public class SrvAssetLocationDAO extends AMSBaseDAO {

    /**
     * 功能：资产类别服务 SRV_ASSET_CATEGORY 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvAssetCategoryDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public SrvAssetLocationDAO(SfUserDTO userAccount, SrvAssetLocationDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SrvAssetLocationDTO dtoPara = (SrvAssetLocationDTO) dtoParameter;
        super.sqlProducer = new SrvAssetLocationModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：对地址信息的更新是否成功。(非TD)
     *
     * @param ds                   DTOSet 传入的数据对象
     * @param segment1：部门(资产地点第一段)
     * @return 成功更新的记录数
     */
    public int synAssetLocation(DTOSet ds, String segment1) {
        SynLogUtil log = new SynLogUtil();
        SQLModel sqlModel = null;
        String orgId ="";
        String bookTypeCode ="";
        int count = 0;
        try {
            SrvAssetLocationDTO firstLocation = (SrvAssetLocationDTO) ds.getDTO(0);
            Row row = getEcomCode(firstLocation.getLocationCombinationCode());
            if(row!=null){
                orgId = row.getStrValue("ORGANIZATION_ID");
                bookTypeCode = row.getStrValue("BOOK_TYPE_CODE");
            }
            SrvAssetLocationModel modelProducer = (SrvAssetLocationModel) sqlProducer;
            for (int i = 0; i < ds.getSize(); i++) {
                SrvAssetLocationDTO dto = (SrvAssetLocationDTO) ds.getDTO(i);
                dto.setOrgId(orgId);
                dto.setBookTypeCode(bookTypeCode);
//                initSQLProducer(userAccount, dto);//唐明胜注释，该方法会再次实例化模型对象，占用资源
                setDTOParameter(dto);//唐明胜修改
                if (getOuInfom() < 1) {  //不存在
                    sqlModel = modelProducer.getDataCreateModel();
                } else {
                    sqlModel = modelProducer.getDataUpdateModel();
                }
                if (DBOperator.updateRecord(sqlModel, conn)) {
                    count++;
                }
            }
        } catch (Throwable e) {
            Logger.logError(e);
            SimpleCalendar s = new SimpleCalendar();
            SynLogDTO logDto = new SynLogDTO();
            logDto.setSynType(SrvType.SRV_FA_LOCATION);
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

    /**
     * 功能:判断MIS固定资产地点表中是否存在地点三段组合代码
     *
     * @return
     */
    public int getOuInfom() {
        SrvAssetLocationModel modelProducer = (SrvAssetLocationModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcouInforModel();
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
     * 功能：判断是否已有此TD地址
     *
     * @return
     */
    public int isExistTdAssetsLocation() {
        SrvAssetLocationModel modelProducer = (SrvAssetLocationModel) sqlProducer;
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
     * 功能：根据地点代码第二段获取OU组织ID和资产账簿代码
     *
     * @param objectCode 地点代码
     * @return row 行数据对象
     */
    private Row getEcomCode(String objectCode) {
        Row row = null;
        SrvAssetLocationModel modelProducer = (SrvAssetLocationModel) sqlProducer;
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
}