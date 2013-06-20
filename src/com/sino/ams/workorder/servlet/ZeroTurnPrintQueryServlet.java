package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.service.BarCodeService;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.print.dao.ZeroBarcodePrintHistoryDAO;
import com.sino.ams.print.dto.ZeroBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.ZeroTurnListPrintQueryDAO;
import com.sino.ams.workorder.dto.EtsItemDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.ZeroTrunListPrintModel;
import com.sino.ams.workorder.model.ZeroTrunListPrintQueryModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class ZeroTurnPrintQueryServlet extends BaseServlet{
	 @Override
	    public void performTask(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {
	        String forwardURL = "";
	        Message message = SessionUtil.getMessage(req);
	        Connection conn = null;
	        String action = StrUtil.nullToString(req.getParameter("act"));
	        try {
	            conn = DBManager.getDBConnection();
	            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
	            ZeroTurnLineDTO dtoParameter = getDTOFromReq(req);

	            int organizationId = StrUtil.isEmpty(dtoParameter
	                    .getOrganizationId()) ? user.getOrganizationId()
	                    : dtoParameter.getOrganizationId();
	            // 组织
	            OptionProducer prd = new OptionProducer(user, conn);
	            String ouoption = "";

	            if ("82".equals(user.getOrganizationId())) {
	                if ("".equals(dtoParameter.getOrganizationId())) {
	                    ouoption = prd.getAllOrganization(0, true);
	                } else {
	                    ouoption = prd.getAllOrganization(organizationId, true);
	                }
	            } else {
	                ouoption = prd.getAllOrganization(organizationId);
	            }
	            req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);

	            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY,
	                    dtoParameter.getObjectNo());
	            req.setAttribute("CATEGORY", cat);

	            if (action.equals("")) {
	                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
	                forwardURL = "/workorder/zeroTurnPrintQuery.jsp";
	            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
	                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
	                BaseSQLProducer sqlProducer = new ZeroTrunListPrintQueryModel(user,
	                        dtoParameter);
	                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
	                pageDAO.produceWebData();
	                forwardURL = "/workorder/zeroTurnPrintQuery.jsp";
	            } else if (action.equals(AssetsActionConstant.PRINT_BARCODE_ACTION)) {
	                DTOSet lines = this.getDTOSetFromReq(req);
	                if (dtoParameter.getCheckedBarcode().equals("")) {
	                    message = getMessage(MsgKeyConstant.INVALID_REQ);
	                    message.setIsError(true);
	                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
	                } else {
	                    String printType = StrUtil.nullToString(req
	                            .getParameter("printType"));
	                    DTOSet retDTOSet = this.handleLines(lines, dtoParameter,
	                            req);
	                    this.saveBarcodePrintHistory(user, retDTOSet, conn);
	                    req.setAttribute(QueryConstant.QUERY_DTO, retDTOSet);
	                    if (printType.equals("1")) {
	                        forwardURL = "/workorder/zeroTrunListPrintDetail.jsp";
	                    } else {
	                        forwardURL ="/workorder/zeroTrunListPrintSimpleDetail.jsp";
	                    }
	                }
	            }else if(action.equals(AssetsActionConstant.DETAIL_ACTION)){
					Request2DTO req2DTO = new Request2DTO();
					req2DTO.setDTOClassName(ZeroBarcodePrintHistoryDTO.class.getName());
					ZeroBarcodePrintHistoryDTO dto = (ZeroBarcodePrintHistoryDTO)req2DTO.getDTO(req);
					
					BaseSQLProducer sqlProducer = new ZeroTrunListPrintModel(user,	dto);
					PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
	                pageDAO.setDTOClassName(ZeroBarcodePrintHistoryDTO.class.getName());
					pageDAO.produceWebData();
					req.setAttribute(AssetsWebAttributes.ITEM_INFO_DTO, dto);
	                forwardURL = "/workorder/zeroTrunListPrintHistoryDetail.jsp";
				}else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {               //导出
					String barcode=req.getParameter("partExport");
					String type=req.getParameter("opinionType");
					List barcodeList=new ArrayList();
					int j=0;
					boolean boolType=true;
			        for(int i=0;i<barcode.length();i++){
			        	if(barcode.charAt(i)=='_'){
			        		if(boolType){
			        			barcodeList.add(barcode.substring(j, i));
			        			boolType=false;
			        			j=i;
			        			continue;
			        		}else{
			        			barcodeList.add(barcode.substring(j+1, i));
			        			j=i;
			        		}
			            }
			        }
			        ZeroTurnListPrintQueryDAO dao = new ZeroTurnListPrintQueryDAO(user, dtoParameter, conn);
					File file = dao.exportFile(dtoParameter,type,barcodeList);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
				}else {
	                message = getMessage(MsgKeyConstant.INVALID_REQ);
	                message.setIsError(true);
	                forwardURL = MessageConstant.MSG_PRC_SERVLET;
	            }
	        } catch (PoolException e) {
	            message = getMessage(MsgKeyConstant.INVALID_REQ);
	            message.setIsError(true);
	            forwardURL = MessageConstant.MSG_PRC_SERVLET;
	        } catch (QueryException e) {
	            message = getMessage(MsgKeyConstant.INVALID_REQ);
	            message.setIsError(true);
	            forwardURL = MessageConstant.MSG_PRC_SERVLET;
	        } catch (DTOException e) {
	            message = getMessage(MsgKeyConstant.INVALID_REQ);
	            message.setIsError(true);
	            forwardURL = MessageConstant.MSG_PRC_SERVLET;
	        } catch (DataTransException e) {
				e.printStackTrace();
			} catch (IOException e) {
	            message = getMessage(MsgKeyConstant.INVALID_REQ);
	            message.setIsError(true);
	            forwardURL = MessageConstant.MSG_PRC_SERVLET;
	        }catch (WebFileDownException e) {
				e.printStackTrace();
			}  catch (SQLException e) {
	            message = getMessage(MsgKeyConstant.INVALID_REQ);
	            message.setIsError(true);
	            forwardURL = MessageConstant.MSG_PRC_SERVLET;
	        } finally {
	            DBManager.closeDBConnection(conn);
	            setHandleMessage(req, message);
	            ServletForwarder forwarder = new ServletForwarder(req, res);
	            forwarder.forwardView(forwardURL);
	        }

	    }

	    public DTOSet getDTOSetFromReq(HttpServletRequest req) throws DTOException {
	        Request2DTO req2DTO = new Request2DTO();
	        req2DTO.setDTOClassName(EtsItemDTO.class.getName());
	        req2DTO.setIgnoreFields(EtsWorkorderDTO.class);
	        return req2DTO.getDTOSet(req);
	    }

	    public ZeroTurnLineDTO getDTOFromReq(HttpServletRequest req)
	            throws DTOException {
	        Request2DTO req2DTO = new Request2DTO();
	        req2DTO.setDTOClassName(ZeroTurnLineDTO.class.getName());
	        return (ZeroTurnLineDTO) req2DTO.getDTO(req);
	    }

	    @SuppressWarnings({"unchecked"})
	    private DTOSet handleLines(DTOSet lines, ZeroTurnLineDTO dtoParameter,
	                               HttpServletRequest req) throws IOException, DTOException {
	        DTOSet retDTOSet = new DTOSet();
	        BarCodeService service = new BarCodeService(req);
	        List<String> barcodes = ArrUtil.arrToList(dtoParameter
	                .getCheckedBarcode().split(","));
	        EtsItemDTO line = null;
	        String barcodeImg = null;
	        for (int i = 0; i < lines.getSize(); i++) {
	            line = (EtsItemDTO) lines.getDTO(i);
	            if (barcodes.contains(line.getBarcode2())) {
	                barcodeImg = service.createScancodePic(line.getBarcode2());
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
	            ZeroBarcodePrintHistoryDTO historyDTO = null;
	            EtsItemDTO dtoParameter = null;
	            ZeroBarcodePrintHistoryDAO historyDAO = null;
	            for (int i = 0; i < retDTOSet.getSize(); i++) {
	                dtoParameter = (EtsItemDTO) retDTOSet.getDTO(i);
	                historyDTO = new ZeroBarcodePrintHistoryDTO();
	                historyDTO.setBarcode(dtoParameter.getBarcode2());
	                historyDTO.setType("条码打印");
	                historyDAO = new ZeroBarcodePrintHistoryDAO(userAccount,
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

	    /**
	     * 功能：构造是否下拉框
	     *
	     * @param selectedValue String
	     * @return String
	     */
	    public String getFAOption(String selectedValue) {
	        StringBuffer strOpt = new StringBuffer();
	        if (selectedValue == null) {
	            selectedValue = "";
	        }
	        strOpt.append("<option value=\"");
	        strOpt.append("ASSETS");
	        strOpt.append("\"");
	        if (selectedValue.equals("ASSETS")) {
	            strOpt.append(" selected");
	        }
	        strOpt.append(">资产</option>");
	        strOpt.append("<option value=\"");
	        strOpt.append("PRE_ASSETS");
	        strOpt.append("\"");
	        if (selectedValue.equals("PRE_ASSETS")) {
	            strOpt.append(" selected");
	        }
	        strOpt.append(">预转资</option>");
	        strOpt.append("<option value=\"");
	        strOpt.append("TO_ASSETS");
	        strOpt.append("\"");
	        if (selectedValue.equals("TO_ASSETS")) {
	            strOpt.append(" selected");
	        }
	        strOpt.append(">待转资</option>");
			return strOpt.toString();
		}


}
