package com.sino.ams.important.business;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.important.dto.ImpInfoDTO;
import com.sino.ams.important.model.ImpInfoModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: yu
 * Date: 2007-5-28
 * Time: 14:39:21
 * To change this template use File | Settings | File Templates.
 */
public class ImpInfoDAO {
    SQLModel sqlModel = null;
    Connection conn = null;
    ImpInfoModel impModel = null;
    HttpServletRequest req = null;
    private ImpInfoDTO dto = null;

    public ImpInfoDAO(HttpServletRequest req, Connection conn) {
        super();
        sqlModel = new SQLModel();
        impModel = new ImpInfoModel();
        this.conn = conn;
        this.req = req;
    }

    public void setModelDTO(ImpInfoDTO dto) {
        impModel.setParaDTO(dto);
        this.dto = dto;
    }

    public void ProdAllUsersData(HttpServletRequest req, Connection conn) throws QueryException {
        sqlModel = impModel.getLocationModel(req);
        WebPageView webPageView = new WebPageView(req, conn);
        webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
        webPageView.produceWebData(sqlModel);
    }

    public void ProdAllTitleData(HttpServletRequest req, Connection conn) throws QueryException {
        sqlModel = impModel.getLocationTitle();
        WebPageView webPageView = new WebPageView(req, conn);
        webPageView.setPageSize(23);
        webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
        webPageView.produceWebData(sqlModel);
    }

    public ImpInfoDTO getInfoDetail(HttpServletRequest req, Connection conn) throws QueryException {
        sqlModel = impModel.getInfoDetailModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.setDTOClassName(ImpInfoDTO.class.getName());
        sq.executeQuery();
        ImpInfoDTO impDTO = (ImpInfoDTO) sq.getFirstDTO();
        return impDTO;
    }

    private void updateInfo() throws DataHandleException {
        sqlModel = impModel.updateInfo();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public boolean savePublishInfo() throws DataHandleException, SQLException {
    	boolean isSuccess = false;
        try {
            String publishId = dto.getPublishId();
            if (publishId.length() == 0) {
                SeqProducer seqProd = new SeqProducer(conn);
                dto.setPublishId(seqProd.getGUID());
                setModelDTO(dto);
                insertNewInfo();
            } else {
                if (!hasPublishExist()) {
                    insertNewInfo();
                } else {
                    updateInfo();
                }
            }
            isSuccess = true ;
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally{
        	if( isSuccess  ){
        		conn.commit();
        	}else{
        		conn.rollback();
        	}
        	return isSuccess ;
        }
    }

    private void insertNewInfo() throws DataHandleException {
        sqlModel = impModel.getInsertNewAcceptanceModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    private boolean hasPublishExist() throws QueryException {
        sqlModel = impModel.getInfoDetailModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }

	public ImpInfoDTO getDto() {
		return dto;
	}

	public void setDto(ImpInfoDTO dto) {
		this.dto = dto;
	}
}
