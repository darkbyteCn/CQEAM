package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.service.BarCodeService;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AssetsTagNumberQueryDAO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.model.AssetsTagNumberQueryModel;
import com.sino.ams.newasset.print.dto.BarCodePrintDTO;
import com.sino.ams.print.dao.EtsBarcodePrintHistoryDAO;
import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ArrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * User: srf
 * Date: 2008-3-31
 * Time: 14:46:42
 * Function:
 */
public class AssetsTagNumberQueryServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AssetsTagNumberQueryDTO.class.getName());
			AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AssetsTagNumberQueryDAO dao = new AssetsTagNumberQueryDAO(user, dto, conn);
			String action = dto.getAct();
			OptionProducer op = new OptionProducer(user, conn);
			int organizationId = user.getOrganizationId();
			String companySelect = op.getOrganizationOpt(organizationId);
			req.setAttribute("OU", companySelect);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String opt = optProducer.getCostCenterOption(dto.getCostCenter());
			dto.setCostCenterOpt(opt);
			if (action.equals("")) {
				//栏目定义标头
				ResUtil.setAllResName(conn, req, ResNameConstant.TAG_NUMBER_QUERY );
				
				req.setAttribute("TAG_NUMBER", dto);
				forwardURL = "/newasset/assetsTagNumberQuery.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				//栏目定义标头
				ResUtil.setAllResName(conn, req, ResNameConstant.TAG_NUMBER_QUERY );
				
				BaseSQLProducer sqlProducer = new AssetsTagNumberQueryModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute("TAG_NUMBER", dto);
				forwardURL = "/newasset/assetsTagNumberQuery.jsp";
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				String excelType = req.getParameter("excelType");
				File file = dao.exportFile(excelType);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else if( action.equals(AssetsActionConstant.PRINT_BARCODE_ACTION) ){
				DTOSet lines = this.getDTOSetFromReq(req);
				String checkedBarcode = req.getParameter( "checkedBarcode" );
                if ( checkedBarcode.equals("")) {
                    message = getMessage(MsgKeyConstant.INVALID_REQ);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else {
                    DTOSet retDTOSet = this.handleLines(lines, checkedBarcode ,
                            req);
                    this.saveBarcodePrintHistory(user, retDTOSet, conn);
                    req.setAttribute(QueryConstant.QUERY_DTO, retDTOSet);
                    forwardURL = "/newasset/assetsTagNumberPrint.jsp";
                }
			}else if( action.equals( AssetsActionConstant.PRINT_BARCODE_ACTION + "2" ) ){
				DTOSet lines = this.getDTOSetFromReq(req);
				String checkedBarcode = req.getParameter( "checkedBarcode" );
                if ( checkedBarcode.equals("")) {
                    message = getMessage(MsgKeyConstant.INVALID_REQ);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else {
                    DTOSet retDTOSet = this.handleLines(lines, checkedBarcode ,
                            req);
                    this.saveBarcodePrintHistory(user, retDTOSet, conn);
                    req.setAttribute(QueryConstant.QUERY_DTO, retDTOSet);
                    forwardURL = "/newasset/assetsTagNumberSimplePrint.jsp";
                }
			}
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException ex) {
			Logger.logError( ex );
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
	
	
	
	@SuppressWarnings({"unchecked"})
    private DTOSet handleLines(DTOSet lines, String checkedBarcode ,
                               HttpServletRequest req) throws IOException, DTOException {
        DTOSet retDTOSet = new DTOSet();
        BarCodeService service = new BarCodeService(req);
        List<String> barcodes = ArrUtil.arrToList( checkedBarcode.split(","));
        BarCodePrintDTO line = null;
        String barcodeImg = null;
        for (int i = 0; i < lines.getSize(); i++) {
            line = (BarCodePrintDTO) lines.getDTO(i);
            if (barcodes.contains(line.getBarcodePrint())) {
                barcodeImg = service.createScancodePic(line.getBarcodePrint());
                line.setBarcodeImg(barcodeImg);
                retDTOSet.addDTO(line);
            }
        }
        return retDTOSet;
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
                historyDTO.setType("已有资产标签打印");
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
    
    public DTOSet getDTOSetFromReq(HttpServletRequest req) throws DTOException {
        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName( BarCodePrintDTO.class.getName());
        req2DTO.setIgnoreFields(AssetsTagNumberQueryDTO.class);
        return req2DTO.getDTOSet(req);
    }

}
