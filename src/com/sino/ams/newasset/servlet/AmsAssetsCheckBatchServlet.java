package com.sino.ams.newasset.servlet;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsCheckBatchDAO;
import com.sino.ams.newasset.dao.AmsAssetsCheckHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckBatchModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.pda.PDAUtil;
import com.sino.sinoflow.constant.WebAttrConstant;

/**
 * <p>Title: AmsAssetsCheckBatchServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckBatchServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCheckBatchServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String msg = "";
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
            AmsAssetsCheckBatchDTO dtoParameter = (AmsAssetsCheckBatchDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsAssetsCheckBatchDAO batchDAO = new AmsAssetsCheckBatchDAO(user, dtoParameter, conn);
            batchDAO.setServletConfig(getServletConfig(req));
            String orderType = dtoParameter.getOrderType();
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);

            String sf_isNew = (String) req.getAttribute(WebAttrConstant.SINOFLOW_NEW_CASE);
            if (sf_isNew != null && sf_isNew.equals("1")) {
                action = "NEW_ACTION";
            }
            if (orderType.equals("")) {
                dtoParameter.setOrderType(AssetsDictConstant.ASS_CHK);
                dtoParameter.setOrderTypeName(AssetsDictConstant.ASS_CHK_SRV);
            }
            if (action.equals("")) {
                if (dtoParameter.getSrcPage().equals(AssetsActionConstant.QUERY_ACTION)) {
                    dtoParameter = optProducer.fillBatchStatus(dtoParameter);
                }
                String deptOptions = optProducer.getUserAsssetsDeptOption(StrUtil.nullToString(dtoParameter.getCheckDept()));
                dtoParameter.setCheckDeptOption(deptOptions);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.BATCH_QRY_PAGE;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsCheckBatchModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("BATCH_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                if (dtoParameter.getSrcPage().equals(AssetsActionConstant.QUERY_ACTION)) {
                    dtoParameter = optProducer.fillBatchStatus(dtoParameter);
                }
                String deptOptions = optProducer.getUserAsssetsDeptOption(StrUtil.nullToString(dtoParameter.getCheckDept()));
                dtoParameter.setCheckDeptOption(deptOptions);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.BATCH_QRY_PAGE;
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) req.getAttribute(AssetsWebAttributes.CHECK_BATCH_DATA);
                DTOSet chkOrders = getCheckOrders(req, req2DTO);
                if (dto == null) {
                    dto = dtoParameter;
                    dto = fillData(user, dto, conn);
                }
                String chkCategoryOpt = optProducer.getChkCategoryOption(dto.getCheckCategory());
                dto.setCheckCategoryOpt(chkCategoryOpt);
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                if (chkOrders != null) {
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, chkOrders);
                }
                forwardURL = AssetsURLList.BATCH_EDIT_PAGE;
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) req.getAttribute(AssetsWebAttributes.CHECK_BATCH_DATA);
                DTOSet chkOrders = (DTOSet) req.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS);
                if (dto == null) {//如果不存在上次操作返回的非法数据，则从数据库获取数据
                    batchDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                    batchDAO.setCalPattern(LINE_PATTERN);
                    if(dtoParameter.getBatchId().equals("")) {
                        dtoParameter.setBatchId(dtoParameter.getApp_dataID());
                    }
                    dto = (AmsAssetsCheckBatchDTO) batchDAO.getDataByPrimaryKey();

                    AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    chkOrders = (DTOSet) orderDAO.getDataByForeignKey("batchId");
                }

                dto = fillDept2DTO(user, dto, conn);
                String chkCategoryOpt = optProducer.getChkCategoryOption(dto.getCheckCategory());
                dto.setCheckCategoryOpt(chkCategoryOpt);

                req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, chkOrders);
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_EDIT_PAGE;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
            	if(StrUtil.isEmpty( dtoParameter.getBatchId() )){
            		dtoParameter.setBatchId( dtoParameter.getApp_dataID() );
            	}
            	
        		batchDAO = new AmsAssetsCheckBatchDAO(user, dtoParameter, conn);
        		
                batchDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                batchDAO.setCalPattern(LINE_PATTERN);
                if(dtoParameter.getBatchId().equals("")) {
                    dtoParameter.setBatchId(dtoParameter.getApp_dataID());
                }
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) batchDAO.getDataByPrimaryKey();
                if (dto == null) {
                    dto = dtoParameter;
                    dto = fillData(user, dto, conn);
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                } else {
                    dto = fillDept2DTO(user, dto, conn);
                    AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    DTOSet orders = (DTOSet) orderDAO.getDataByForeignKey("batchId");
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, orders);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_DETAIL_PAGE;
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
                DTOSet chkOrders = getCheckOrders(req, req2DTO);
                if (batchDAO.hasPrevOrders(chkOrders)) {//有地点存在未归档工单，获取数据返回页面，页面展示并提示
                    chkOrders = batchDAO.getSubmitedOrders();
                    message = batchDAO.getMessage();
                    req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dtoParameter);
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, chkOrders);
                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&batchId=" + dtoParameter.getBatchId();
                } else {
                    dtoParameter.setBatchStatus(AssetsDictConstant.SAVE_TEMP);
                    batchDAO.setDTOParameter(dtoParameter);
                    boolean operateResult = batchDAO.saveNewCheckOrders(chkOrders);
                    message = batchDAO.getMessage();
                    if (operateResult) {
                        dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO.getDTOParameter();
                        dtoParameter = fillData(user, dtoParameter, conn);
                        forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                        forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                        forwardURL += "&batchId=" + dtoParameter.getBatchId();
                    } else {
                        dtoParameter = fillData(user, dtoParameter, conn);
                        req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dtoParameter);
                        forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                        forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                    }
                }
            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
                DTOSet chkOrders = getCheckOrders(req, req2DTO);
                if (batchDAO.hasPrevOrders(chkOrders)) {//有地点存在未归档工单，获取数据返回页面，页面展示并提示
                    chkOrders = batchDAO.getSubmitedOrders();
                    message = batchDAO.getMessage();
                    req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dtoParameter);
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, chkOrders);
                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&batchId=" + dtoParameter.getBatchId();
                } else {
                    dtoParameter.setBatchStatus(AssetsDictConstant.IN_PROCESS);
                    batchDAO.setDTOParameter(dtoParameter);
                    boolean operateResult = batchDAO.submitNewCheckOrders(chkOrders);
                    message = batchDAO.getMessage();
                    if (operateResult) {
                        dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO.getDTOParameter();
                        dtoParameter = fillData(user, dtoParameter, conn);
                        forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                        forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                        forwardURL += "&batchId=" + dtoParameter.getBatchId();
                    } else {
                        dtoParameter = fillData(user, dtoParameter, conn);
                        req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dtoParameter);
                        forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                        forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                    }
                }
            } else if (action.equals(AssetsActionConstant.DISTRIBUTE_ACTION)) {
                dtoParameter.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
                batchDAO.setDTOParameter(dtoParameter);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("BATCH_ID");
                RequestParser parser = new RequestParser();
                parser.setCheckBoxProp(checkProp);
                parser.transData(req);
                String[] batchIds = parser.getParameterValues("batchId");
                batchDAO.distributeChkOrder(batchIds);
                message = batchDAO.getMessage();
                if (parser.contains("groupId")) {
                    dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO.getDTOParameter();
                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&batchId=" + dtoParameter.getBatchId();
                } else {
                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                    forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
                    forwardURL += "&srcPage=" + AssetsActionConstant.DISTRIBUTE_ACTION;
                }
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) {
                boolean operateResult = batchDAO.cancelCheckTask();
                message = batchDAO.getMessage();
                forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
                if (operateResult) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                }
                forwardURL += "&batchId=" + dtoParameter.getBatchId();
            } else if (action.equals("ImportLocation")) {
                req.setAttribute("NOLOCATIOND_DATA", null);
                req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, null);
                forwardURL = "/newasset/importCheckLocation.jsp";
            } else if (action.equals(AssetsActionConstant.IMP_LOCATION_CODE_ACTION)) {
                Logger.logInfo("Excel submit servlet begin....");
                int startRowNum = 1;
                int columnNum = 20;
                RequestParser reqPar = new RequestParser();
                reqPar.transData(req);
                UploadFile[] upFiles = null;
                UploadRow uploadRow;
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
                uploadFileSaver.saveFiles(conFilePath);
                uploadRow = uploadFileSaver.getRow();
                upFiles = uploadRow.getFiles();
                if (upFiles == null) {
                    return;
                } else if (upFiles.length != 1 || upFiles[0].getFileName().equals("")) {
                    return;
                }
                UploadFile uploadFile = upFiles[0];
                String fileName = uploadFile.getAbsolutePath();
                FileInputStream fileIn = new FileInputStream(fileName);
                POIFSFileSystem fs = new POIFSFileSystem(fileIn);
                HSSFWorkbook book = new HSSFWorkbook(fs);
                HSSFSheet hssfSheet = book.getSheetAt(0);
                HSSFRow hssfRow = null;
                HSSFCell hssfCell = null;
                int row = hssfSheet.getLastRowNum();
                String strValue = "";
                List lst = new ArrayList();

                for (int i = startRowNum; i <= row; i++) {
                    hssfRow = hssfSheet.getRow(i);
                    if (hssfRow != null) {
                        hssfCell = hssfRow.getCell((short) 0);
                        if (hssfCell != null) {
                            strValue += getStringValue(hssfCell) + "','";
                            lst.add(getStringValue(hssfCell));
                        }
                    }
                }
                if (!strValue.equals("")) {
                    RowSet rows = batchDAO.getImpLocation(strValue.substring(0, strValue.length() - 3));
                    req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
                    for (int i = 0; i < rows.getSize(); i++) {
                        String locCodeString = rows.getRow(i).getStrValue("OBJECT_CODE");
                        if (lst.contains(locCodeString)) {
                            lst.remove(locCodeString);
                        }
                    }
                }

                req.setAttribute("NOLOCATIOND_DATA", lst);
                forwardURL = "/newasset/importCheckLocation.jsp";

            } else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException ex) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ParseException ex) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException ex) {
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (FileSizeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (msg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardOpenerView(forwardURL, msg);
            }
        }
    }

    /**
     * 对新建时的DTO数据进行补充
     * @param user SfUserDTO
     * @param dto  AmsAssetsCheckBatchDTO
     * @param conn Connection
     * @return AmsAssetsCheckBatchDTO
     * @throws DTOException
     */
    private AmsAssetsCheckBatchDTO fillData(SfUserDTO user, AmsAssetsCheckBatchDTO dto, Connection conn) throws
            DTOException {
        try {
            dto.setBatchNo(AssetsWebAttributes.ORDER_AUTO_PROD);
            dto.setCreatedBy(user.getUserId());
            dto.setCreatedUser(user.getUsername());
            dto.setOrganizationId(user.getOrganizationId());
            dto.setCompanyName(user.getCompany());
            dto.setCurrCreationDate();
            dto.setCalPattern(LINE_PATTERN);
            fillDept2DTO(user, dto, conn);
            DTOSet assetsGroups = user.getUserGroups();
            if (assetsGroups.getSize() == 1) {
                SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                dto.setGroupId(sfGRoup.getGroupId());
                dto.setGroupName(sfGRoup.getGroupname());
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DTOException(ex);
        }
        return dto;
    }

    /**
     * 功能：对DTO数据构造盘点部门下拉列表
     * @param user SfUserDTO
     * @param dto  AmsAssetsCheckBatchDTO
     * @param conn Connection
     * @return AmsAssetsCheckBatchDTO
     * @throws QueryException
     */
    private AmsAssetsCheckBatchDTO fillDept2DTO(SfUserDTO user, AmsAssetsCheckBatchDTO dto, Connection conn) throws QueryException {
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String deptCode = StrUtil.nullToString(dto.getCheckDept());
        if (deptCode.equals("") && dto.getBatchId().equals("")) {
            deptCode = user.getDeptCode();
        }
        String deptOptions = optProducer.getUserAsssetsDeptOption(deptCode);
        dto.setCheckDeptOption(deptOptions);
        return dto;
    }

    /**
     * 功能：构造盘点工单
     * @param req     HttpServletRequest
     * @param req2DTO Request2DTO
     * @return DTOSet
     * @throws DTOException
     */
    private DTOSet getCheckOrders(HttpServletRequest req, Request2DTO req2DTO) throws DTOException {
        DTOSet orders = new DTOSet();
        List excFields = new ArrayList();
        excFields.add("checkCategory");
        req2DTO.setIgnoreFields(AmsAssetsCheckBatchDTO.class, excFields);
        req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
        orders = req2DTO.getDTOSet(req);
        return orders;
    }

    private String getStringValue(HSSFCell cell) {
        String strValue = "";
        if (cell != null) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                strValue = "";
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                strValue = String.valueOf(cell.getNumericCellValue());
            } else {
                strValue = cell.getRichStringCellValue().toString();
            }
        }
        return strValue;
    }
}
