package com.sino.ams.newasset.devalue.servlet;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.OrderApproveDAO;
import com.sino.ams.newasset.devalue.dao.DevalueAssetsHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AmsAssetsTransHeaderModel;
import com.sino.ams.newasset.model.OrderQueryModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.file.parse.XLSParser;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.DTOSetUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.pda.PDAUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevalueAssetsHeaderServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            ServletConfigDTO servletConfig = getServletConfig(req);
            dto.setServletConfig(servletConfig);

            if (!dto.getApp_dataID().equals("")) {
                dto.setTransId(dto.getApp_dataID());
            }

            DevalueAssetsHeaderDAO headerDAO = new DevalueAssetsHeaderDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            String transType = dto.getTransType();
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = "";
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                option = optProducer.getTransferOption(dto.getTransferType());
                dto.setTransferTypeOption(option);
            }
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            dto.setFaContentOption(option);
            if ("".equals(dto.getEmergentLevel())) {
                dto.setEmergentLevel("0");
            }
            String emergentLevelOption = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
            dto.setEmergentLevelOption(emergentLevelOption);
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID + "");
            boolean isFinanceGroup = headerDAO.isFinanceGroup();
            req.setAttribute(AssetsWebAttributes.IS_FINANCE_GROUP, isFinanceGroup + "");
            boolean isTdProvinceUser = user.getCompanyCode().equals(servletConfig.getTdProCompanyCode());
            req.setAttribute("isTdProvinceUser", isTdProvinceUser);
            DTOSet lineDTOSetALL = new DTOSet();

            action = StrUtil.isEmpty(action) ? AssetsActionConstant.EDIT_ACTION : action;
			if(action.equals("''")){
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.DEVALUE_QUERY_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransType("ASS-DEVALUE");
                BaseSQLProducer sqlProducer = new AmsAssetsTransHeaderModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.DEVALUE_QUERY_PAGE;
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsTransHeaderDTO headerDTO = fillData(dto, user, conn);
                headerDTO.setServletConfig(servletConfig);
                headerDTO.setCalPattern(LINE_PATTERN);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                boolean isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                req.setAttribute("isSpecialityDept", isSpecialityDept);
                forwardURL = AssetsURLList.DEVALUE_EDIT_PAGE;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = fillData(dto, user, conn);
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else {
                    headerDTO.setServletConfig(servletConfig);
                    headerDTO = fillOptions(headerDTO, user, conn);
                    headerDTO.setCalPattern(LINE_PATTERN);
                    AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                    lineDTO.setTransId(headerDTO.getTransId());
                    lineDTO.setTransType(headerDTO.getTransType());
                    AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
                    lineDAO.setCalPattern(LINE_PATTERN);
                    lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                    DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                    if (ds == null) {
                        ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                    }
                    headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel()));
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                    forwardURL = AssetsURLList.DEVALUE_DETAIL_PAGE;
                }
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                if (StrUtil.isEmpty(dto.getTransId())) {
                    AmsAssetsTransHeaderDTO headerDTO = fillData(dto, user, conn);
                    headerDTO.setServletConfig(servletConfig);
                    headerDTO.setCalPattern(LINE_PATTERN);
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    boolean isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                    req.setAttribute("isSpecialityDept", isSpecialityDept);
                    forwardURL = AssetsURLList.DEVALUE_EDIT_PAGE;
                } else {
                    headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                    AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                    OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
                    String accessSheet = approveDAO.getAccessSheet();//附件张数
                    headerDTO.setAccessSheet(accessSheet);
                    boolean isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                    req.setAttribute("isSpecialityDept", isSpecialityDept);

                    if (headerDTO == null) {
                        message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                        message.setIsError(true);
                        forwardURL = MessageConstant.MSG_PRC_SERVLET;
                    } else {
                        headerDTO.setServletConfig(servletConfig);
                        headerDTO = fillOptions(headerDTO, user, conn);

                        headerDTO.setCalPattern(LINE_PATTERN);
                        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                        lineDTO.setTransId(headerDTO.getTransId());
                        lineDTO.setTransType(headerDTO.getTransType());
                        AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
                        lineDAO.setCalPattern(LINE_PATTERN);
                        lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                        DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                        if (ds == null) {
                            ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                        }
                        headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel()));

                        req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                        req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);

                        forwardURL = AssetsURLList.DEVALUE_EDIT_PAGE;
                    }
                }
                
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) {
                boolean operateResult = headerDAO.cancelOrders();
                message = headerDAO.getMessage();
                dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
                forwardURL = "/servlet/com.sino.ams.newasset.devalue.servlet.DevalueAssetsHeaderServlet";
                if (operateResult) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                }
                forwardURL += "&transId=" + dto.getTransId();
                
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
                req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
                DTOSet orderLines = req2DTO.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                headerDAO.setDTOParameter(dto);
                headerDAO.saveOrder(orderLines);
                message = headerDAO.getMessage();
                dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
                String transId = dto.getTransId();
                forwardURL = AssetsURLList.ASSETS_DEVALUE_SERVLET;
                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
                req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
                DTOSet orderLines = req2DTO.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                headerDAO.setDTOParameter(dto);
                headerDAO.submitOrder(orderLines);
                message = headerDAO.getMessage();
                dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
                String transId = dto.getTransId();

                forwardURL = AssetsURLList.ASSETS_DEVALUE_SERVLET;

                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if (action.equals(AssetsActionConstant.REJECT_ACTION)) {
                headerDAO.rejectOrder();
                message = headerDAO.getMessage();
                forwardURL = AssetsURLList.ASSETS_DEVALUE_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + dto.getTransId();
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出
                File file = exportFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(AMSActionConstant.VALIDATE_ACTION)) {//验证barcode是否存在
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                String fromGroup = req.getParameter("fromGroup");
                boolean result = false;
                if (fromGroup != null && !fromGroup.equals("")) {
                }
                out.print(result);
                out.close();
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
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
//        } catch (UploadException ex) {
//            ex.printLog();
//            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
//            message.setIsError(true);
//            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (CalendarException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);

            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!StrUtil.isEmpty(forwardURL)) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardOpenerView(forwardURL, message.getMessageValue());
            }
        }
    }

    /**
     * 功能:填充DTO数据
     *
     * @param dto  AmsAssetsTransHeaderDTO
     * @param user SfUserDTO
     * @param conn Connection
     * @return AmsAssetsTransHeaderDTO
     * @throws com.sino.base.exception.DTOException
     *
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.CalendarException
     *
     */
    private AmsAssetsTransHeaderDTO fillData(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn) throws
            DTOException, QueryException, CalendarException {
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); //设置单据号
        dto.setCreatedBy(user.getUserId()); //设置创建人
        dto.setCreated(user.getUsername()); //设置创建人
        dto.setFromOrganizationId(user.getOrganizationId());
        dto.setCurrCreationDate();
        dto.setFromCompanyName(user.getCompany());
        dto.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getMobilePhone());
        dto.setUserDeptName(user.getDeptName());
        DTOSet assetsGroups = user.getUserGroups();

        if (assetsGroups.getSize() == 1) {
            SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
            dto.setFromGroup(sfGRoup.getGroupId());
            dto.setFromGroupName(sfGRoup.getGroupname());
            dto.setGroupProp(sfGRoup.getGroupProp());
            dto.setFromDept(sfGRoup.getDeptId());
            dto.setFromDeptName(sfGRoup.getDeptName());
        }

        dto = fillOptions(dto, user, conn);
        return dto;
    }

    /**
     * 功能：获取用户的专业组别
     *
     * @param user SfUserDTO
     * @return DTOSet
     * @throws com.sino.base.exception.DTOException
     *
     */
    private DTOSet getSpecGroups(SfUserDTO user) throws DTOException {
        DTOSet specGroups = new DTOSet();
        DTOSet assetsGroups = user.getUserGroups();
        SfGroupDTO sfGroup = null;
        for (int i = 0; i < assetsGroups.getSize(); i++) {
            sfGroup = (SfGroupDTO) assetsGroups.getDTO(i);
            if (!sfGroup.getGroupProp().equals(AssetsDictConstant.GROUP_PROP_SPEC)) {
                continue;
            }
            specGroups.addDTO(sfGroup);
        }
        return specGroups;
    }

    /**
     * 功能：获取用户的专业组别
     *
     * @param user          SfUserDTO
     * @param servletConfig servlet配置信息
     * @return DTOSet
     * @throws com.sino.base.exception.DTOException
     *
     */
    private boolean hasSpecRole(SfUserDTO user, ServletConfigDTO servletConfig) throws
            DTOException {
        boolean hasSpecRole = false;
        DTOSet specGroups = getSpecGroups(user);
        if (specGroups != null && !specGroups.isEmpty()) {
            DTOSet groupRoles = user.getUserRight();
            SfGroupDTO sfGroup = null;
            SfUserRightDTO sfUserRight = null;
            for (int i = 0; i < specGroups.getSize(); i++) {
                sfGroup = (SfGroupDTO) specGroups.getDTO(i);
                for (int j = 0; j < groupRoles.getSize(); j++) {
                    sfUserRight = (SfUserRightDTO) groupRoles.getDTO(j);
                    if (sfUserRight.getGroupId() != sfGroup.getGroupId()) {
                        continue;
                    }
                    if (!sfUserRight.getRoleName().equals(servletConfig.
                            getMtlAssetsMgr())) {
                        continue;
                    }
                    hasSpecRole = true;
                    break;
                }
                if (hasSpecRole) {
                    break;
                }
            }
        }
        return hasSpecRole;
    }

    /**
     * 功能：补充资产来源部门数据下拉框
     *
     * @param dto  AmsAssetsTransHeaderDTO
     * @param user SfUserDTO
     * @param conn Connection
     * @return AmsAssetsTransHeaderDTO
     * @throws com.sino.base.exception.QueryException
     *
     */
    private AmsAssetsTransHeaderDTO fillOptions(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn) throws QueryException {
        FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
        dto.setTransTypeValue(flexUtil.getFlexValue(AssetsDictConstant.ORDER_TYPE_ASSETS, dto.getTransType()));
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String fromDept = StrUtil.nullToString(dto.getFromDept());
        String specialityDept = StrUtil.nullToString(dto.getSpecialityDept());
        if (StrUtil.isEmpty(fromDept)) {
            fromDept = user.getDeptCode();
        }
        String deptOptions = "";
        if (dto.getSf_task_attribute1().equals("from")) {
            deptOptions = optProducer.getUserAsssetsDeptOption(fromDept);
        } else {
            deptOptions = optProducer.getSelectedDeptOption(fromDept);
        }
        dto.setFromDeptOption(deptOptions);
        
        String specialityDeptOptions = "";
        specialityDeptOptions = optProducer.getSpecialAsssetsDeptOption(specialityDept);
        dto.setSpecialityDeptOption(specialityDeptOptions);
        
        String transType = dto.getTransType();

        String transOption = "";
        if (transType.equals(AssetsDictConstant.ASS_RED)) {
            transOption = optProducer.getTransferFaOption(dto.getFaContentCode(),
                    dto.getTransferType());
        } else {
            transOption = optProducer.getFAContentOption(dto.getFaContentCode());
        }
        dto.setFaContentOption(transOption);
        return dto;
    }

    private String getUploadFile(HttpServletRequest req, Connection conn) {
        String fileName = "";
        try {
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            UploadFile[] upFiles = null;
            UploadRow uploadRow;
            String conFilePath = PDAUtil.getCurUploadFilePath(conn);
            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);
            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();
            if (upFiles != null && upFiles.length == 1 && !upFiles[0].getFileName().equals("")) {
                UploadFile uploadFile = upFiles[0];
                fileName = uploadFile.getAbsolutePath();
            }
        } catch (UploadException e) {
            Logger.logError(e);
        } catch (FileSizeException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        }

        return fileName;

    }


    private void processExcel(String fileName) throws XLSException, ContainerException, ReflectException, DTOException {
        XLSParser xlsPorter = new XLSParser();
        RowSet rows = xlsPorter.parseXLSData(fileName);
        List lst = new ArrayList();
        if (rows != null && rows.getSize() > 0) {
            List fieldNames = getImpFieldNames();
            rows.set(fieldNames, rows.getRowValues(), rows.getFieldTypes());
            lst = DTOSetUtil.getListFromRowSet(rows, AmsAssetsTransLineDTO.class.getName());
        }
    }

    private List getImpFieldNames() {
        List fieldNames = new ArrayList();
        fieldNames.add("SHIP_LINE_NUM");
        fieldNames.add("STATION_CODE");
        fieldNames.add("SEGMENT10");
        fieldNames.add("ITEM_SPEC_DESCRIPTION");
        fieldNames.add("UNIT_OF_MEASURE");
        fieldNames.add("SHIP_QUANTITY");
        fieldNames.add("UNIT_PRICE");
        fieldNames.add("SHIP_NEED_BY_DATE");
        fieldNames.add("SHIP_PROMISED_DATE");
        fieldNames.add("TO_ORGANIZATION_ID");
        fieldNames.add("TO_LOCATION_ID");
        fieldNames.add("SHIP_PROJECT_ID");
        fieldNames.add("TASK_ID");
        fieldNames.add("EXPENDITURE_TYPE");
        fieldNames.add("EXPENDITURE_ORG_ID");
        fieldNames.add("EXPENDED_DATE");
        return fieldNames;
    }
    
    public File exportFile(SfUserDTO user, AmsAssetsTransHeaderDTO dto, Connection conn) throws DataTransException {
        File file = null;
        try {
            DataTransfer transfer = null;
            dto.setTransType("ASS-DEVALUE");
            BaseSQLProducer sqlProducer = new OrderQueryModel(user, dto);
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);            
            String fileName = "减值单据表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();

        	fieldMap.put("TRANS_NO", "减值单号");
            fieldMap.put("TRANS_STATUS_DESC", "单据状态");
            fieldMap.put("FROM_DEPT_NAME", "减值部门");
            fieldMap.put("CREATED", "申请人");
            fieldMap.put("CREATION_DATE", "创建日期");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(user.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
