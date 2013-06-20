package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f1j.ss.BookModel;
import com.f1j.util.F1Exception;
import com.sino.ams.appbase.service.BarCodeService;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.model.AmsMisTagChgModel;
import com.sino.ams.newasset.print.dto.BarCodePrintDTO;
import com.sino.ams.print.dao.EtsBarcodePrintHistoryDAO;
import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>
 * Title: AmsMisTagChgServlet
 * </p>
 * <p>
 * Description:打印新旧标签对照表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author Herry
 * @version 1.0
 */

public class AmsMisTagChgServlet extends BaseServlet {

	/**
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
			AmsAssetsTransHeaderDTO dtoParameter = (AmsAssetsTransHeaderDTO) req2DTO
					.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			if (action.equals("")) {
				forwardURL = "/newasset/report/amsMisTagChgQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsMisTagChgModel(user,
						dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "/newasset/report/amsMisTagChgQuery.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				AmsMisTagChgModel model = new AmsMisTagChgModel(user,
						dtoParameter);
				WebPageView wpv = new WebPageView(req, conn);
				wpv.setPrintProp(false);
				wpv.produceWebData(model.getMuxDataModel());
				forwardURL = "/newasset/report/amsMisTagChgDetail.jsp";
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { // 打印调入方标签
				DTOSet dtos = exportData(user, dtoParameter, conn);
				boolean success = doExport(dtos, res );
				if (success) {
					forwardURL = "";
				} else {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setMessageValue( "导出失败" );
					message.setIsError(true);
					forwardURL = "/newasset/report/amsMisTagChgDetail.jsp";
				}
			} else if( action.equals(AssetsActionConstant.PRINT_BARCODE_ACTION) ){
//				String transNo = StrUtil.nullToString( req.getParameter( "transNo" ) );
				DTOSet lines = getBarCodesByTransNo(user, dtoParameter, conn);
				if( null != lines && !lines.isEmpty() ){
					this.saveBarcodePrintHistory(user, lines, conn);
					lines = this.handleLines(lines,  req);
                    req.setAttribute(QueryConstant.QUERY_DTO, lines);
                    forwardURL = "/newasset/assetsTagNumberPrint.jsp";
				}else{
					String msg = "找不到新标签对应的资产信息";
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setMessageValue( msg );
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (F1Exception e) {
			Logger.logError( e );
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			Logger.logError( e );
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
				forwarder.forwardView(forwardURL);
			}
		}
	}

	private DTOSet exportData( SfUserDTO user , AmsAssetsTransHeaderDTO dtoParameter , Connection conn ) throws SQLModelException, QueryException{
		AmsMisTagChgModel model = new AmsMisTagChgModel(user,
				dtoParameter);
		SimpleQuery splq = new SimpleQuery(model.getMuxDataModel(),
				conn);
		splq.setDTOClassName(AmsMisTagChgDTO.class.getName());
		splq.executeQuery();
		DTOSet dtos = splq.getDTOSet();
		return dtos;
	}
	
	
	private DTOSet getBarCodesByTransNo( SfUserDTO user , AmsAssetsTransHeaderDTO dtoParameter , Connection conn ) throws QueryException, SQLModelException{
		AmsMisTagChgModel model = new AmsMisTagChgModel(user,
				dtoParameter);
		SimpleQuery query = new SimpleQuery( model.getBarCodePrintModel() , conn );
		query.setDTOClassName( BarCodePrintDTO.class.getName() );
		query.executeQuery();
		DTOSet lines = null;
		if( query.hasResult() ){
			 lines = query.getDTOSet();
		}
		return lines;
	}	

	private boolean doExport(DTOSet dtos, HttpServletResponse res )
			throws IOException, F1Exception {
		boolean success = false;
		
		if( null!=dtos && !dtos.isEmpty() ){
			ServletOutputStream out = null ;
			int dataSize = dtos.getSize();
			
			res.setContentType("application/vnd.ms-excel");
			String header = "attachment; filename="
					+ new String(("ToBarcode.xls").getBytes(), "iso8859-1");
			res.setHeader("Content-Disposition", header);
			out = res.getOutputStream();
			com.f1j.ss.BookModelImpl book = new com.f1j.ss.BookModelImpl();
			try {
				book.initWorkbook();
				book.getLock();
				book.setBorder(true);
				String barcode = "";
				// set report content
				book.setText(0, 0, "资产编号");
				book.setText(0, 1, "资产标签号");
				AmsMisTagChgDTO dto = null;
				
				for (int i = 1; i < dataSize + 1 ; i++) {
					dto = (AmsMisTagChgDTO) dtos.getDTO(i-1);
					barcode = dto.getTagNumberTo();
					book.setText(i, 0, String.valueOf(i));
					book.setText(i, 1, barcode);
				}
	
				book.setColWidthAuto(0, 0, dataSize, 2, true);
				// //////////////////////////////
				// since we change the contents of the book we need to force a recalculation
				book.recalc();
				// writes the book to the output stream in an Excel file format
				book.write(out, new com.f1j.ss.WriteParams(BookModel.eFileExcel97));
				// must close the outputstream to flush the buffer contents
				success = true;
			} catch (IOException e) {
				throw e;
			} catch (F1Exception e) {
				throw e;
			} finally{
				if( null != out ){
					out.flush();
					out.close();
				}
			}
		}
		return success;
	}
	
	
	public DTOSet getDTOSetFromReq(HttpServletRequest req) throws DTOException {
        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName( BarCodePrintDTO.class.getName());
        req2DTO.setIgnoreFields(AssetsTagNumberQueryDTO.class);
        return req2DTO.getDTOSet(req);
    }
	
	/**
	 * 标签打印历史
	 * 
	 * @param userAccount
	 * @param retDTOSet
	 * @param conn
	 * @throws SQLException
	 */
    private void saveBarcodePrintHistory(SfUserDTO userAccount,
                                         DTOSet retDTOSet, Connection conn) throws SQLException {
        boolean isAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        boolean isSuccess = false;
        try {
            EtsBarcodePrintHistoryDTO historyDTO = null;
            BarCodePrintDTO dtoParameter = null;
            EtsBarcodePrintHistoryDAO historyDAO = null;
            for (int i = 0; i < retDTOSet.getSize(); i++) {
                dtoParameter = (BarCodePrintDTO) retDTOSet.getDTO(i);
                historyDTO = new EtsBarcodePrintHistoryDTO();
                historyDTO.setBarcode(dtoParameter.getBarcodePrint());
                historyDTO.setType("调拨资产调入方标签打印");
                historyDAO = new EtsBarcodePrintHistoryDAO(userAccount,
                        historyDTO, conn);
                historyDAO.createData();
            }
            isSuccess = true;
        } catch (DataHandleException e) {
            e.printStackTrace();
        } finally {
            if (isSuccess) {
                conn.commit();
            } else {
                conn.rollback();
            }
            conn.setAutoCommit(isAuto);
        }
    }
    
    @SuppressWarnings({"unchecked"})
    private DTOSet handleLines(DTOSet lines, HttpServletRequest req) throws IOException, DTOException {
        DTOSet retDTOSet = new DTOSet();
        BarCodeService service = new BarCodeService(req);
        BarCodePrintDTO line = null;
        String barcodeImg = null;
        for (int i = 0; i < lines.getSize(); i++) {
            line = (BarCodePrintDTO) lines.getDTO(i);
            barcodeImg = service.createScancodePic(line.getBarcodePrint());
            line.setBarcodeImg(barcodeImg);
            retDTOSet.addDTO(line);
        }
        return retDTOSet;
    }
}