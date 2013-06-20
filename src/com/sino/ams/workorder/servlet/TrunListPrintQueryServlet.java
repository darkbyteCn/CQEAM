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
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.print.dao.EtsBarcodePrintHistoryDAO;
import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.TrunListPrintQueryDAO;
import com.sino.ams.workorder.dto.EtsItemDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.TrunListPrintModel;
import com.sino.ams.workorder.model.TrunListPrintQueryModel;
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

/**
 * @系统名称: 转资条码打印
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Sep 14, 2011
 */
public class TrunListPrintQueryServlet extends BaseServlet {

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
            EtsWorkorderDTO dtoParameter = getDTOFromReq(req);

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


            String faOption = this.getFAOption(dtoParameter.getFinanceProp());
            req.setAttribute("FA_OPTION", faOption);

            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY,
                    dtoParameter.getObjectCategory());
            req.setAttribute("CATEGORY", cat);

            if (action.equals("")) {
                String deptOption = prd.getDeptOptionByOrgId(organizationId,
                        dtoParameter.getDeptCode());// 得到地市下的所有领用部门
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = URLDefineList.TRUN_LIST_PRINT_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                String deptOption = prd.getDeptOptionByOrgId(organizationId,
                        dtoParameter.getDeptCode());// 得到地市下的所有领用部门
                req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                BaseSQLProducer sqlProducer = new TrunListPrintQueryModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.TRUN_LIST_PRINT_QUERY;
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
                        forwardURL = URLDefineList.TRUN_LIST_PRINT_DETAIL;
                    } else {
                        forwardURL = URLDefineList.TRUN_LIST_PRINT_SIMPLE_DETAIL;
                    }
                }
            }else if(action.equals(AssetsActionConstant.DETAIL_ACTION)){
				Request2DTO req2DTO = new Request2DTO();
				req2DTO.setDTOClassName(EtsBarcodePrintHistoryDTO.class.getName());
				EtsBarcodePrintHistoryDTO dto = (EtsBarcodePrintHistoryDTO)req2DTO.getDTO(req);
				
				BaseSQLProducer sqlProducer = new TrunListPrintModel(user,	dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setDTOClassName(EtsBarcodePrintHistoryDTO.class.getName());
				pageDAO.produceWebData();
				req.setAttribute(AssetsWebAttributes.ITEM_INFO_DTO, dto);
                forwardURL = URLDefineList.TRUN_LIST_PRINT_HISTORY_DETAIL;
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
				TrunListPrintQueryDAO dao = new TrunListPrintQueryDAO(user, dtoParameter, conn);
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

    public EtsWorkorderDTO getDTOFromReq(HttpServletRequest req)
            throws DTOException {
        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
        return (EtsWorkorderDTO) req2DTO.getDTO(req);
    }

    @SuppressWarnings({"unchecked"})
    private DTOSet handleLines(DTOSet lines, EtsWorkorderDTO dtoParameter,
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
            EtsBarcodePrintHistoryDTO historyDTO = null;
            EtsItemDTO dtoParameter = null;
            EtsBarcodePrintHistoryDAO historyDAO = null;
            for (int i = 0; i < retDTOSet.getSize(); i++) {
                dtoParameter = (EtsItemDTO) retDTOSet.getDTO(i);
                historyDTO = new EtsBarcodePrintHistoryDTO();
                historyDTO.setBarcode(dtoParameter.getBarcode2());
                historyDTO.setType("条码打印");
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
