package com.sino.ams.newasset.scrap.servlet;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.constant.ScrapAppConstant;
import com.sino.ams.newasset.scrap.constant.ScrapURLListConstant;
import com.sino.ams.newasset.scrap.dao.AmsAssetsTransHeaderDAO;
import com.sino.ams.newasset.scrap.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.scrap.dao.OrderApproveDAO;
import com.sino.ams.newasset.scrap.dto.TransDTO;
import com.sino.ams.newasset.scrap.model.TransModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.constant.WebAttrConstant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @系统名称: 资产管理系统
 * @功能描述: 其他资产报废申请
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 4, 2011
 */
public class TransServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String msg = "";
        boolean forwardOpen = true;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);

            TransDTO transDTO = this.getDTOFromReq(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            AmsAssetsTransHeaderDTO headerDTO = transDTO.getHeaderDTO();
            headerDTO.setTransType(ScrapAppConstant.TRANS_TYPE); //ASS-DIS-OTHER
            headerDTO.setServletConfig(servletConfig);

            String action = headerDTO.getAct();
            String sf_isNew = (String) req.getAttribute(WebAttrConstant.SINOFLOW_NEW_CASE);
            if (sf_isNew != null && sf_isNew.equals("1")) {
                action = "NEW_ACTION";
            }
            // 下拉处理
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = optProducer.getRejectTypeOption(headerDTO.getRejectHType());
            headerDTO.setRejectTypeHOpt(option);

            option = optProducer.getFinanceProp2Option(headerDTO.getFaContentCode());
            headerDTO.setFaContentOption(option);
            String emergentLevelOption = optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel());
            headerDTO.setEmergentLevelOption(emergentLevelOption);

            ForwardDTO forward = null;

            AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, headerDTO, conn);
            headerDAO.setServletConfig(servletConfig);
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID + "");
            boolean isFinanceGroup = headerDAO.isFinanceGroup();
            req.setAttribute(AssetsWebAttributes.IS_FINANCE_GROUP, isFinanceGroup + "");

            if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                forward = this.doNew(req, headerDTO, conn);
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                forward = this.doEdit(req, headerDTO, conn);
            } else if (action.equals(AssetsActionConstant.APPROVE_ACTION)) {
                forward = this.doApprove(req, headerDTO, conn);
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                forwardOpen = false;
                forward = this.doDetail(req, headerDTO, conn);
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
                String status = AssetsDictConstant.SAVE_TEMP;
                forward = this.doSave(req, transDTO, conn, status);
            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
                forward = this.doSubmit(req, transDTO, conn);
            } else if (action.equals(AssetsActionConstant.DELETE_ACTION)) {
                forward = this.invokeDelete(req, headerDTO, conn);
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) { // 撤销暂存的单据
                forward = this.doCancel(req, headerDTO, conn);
            } else if (action.equals("REJECT_ACTION")) {
                forward = this.doReject(req, headerDTO, conn);
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }


            if (null != forward) {
                forwardURL = forward.getForwardURL();
                msg = forward.getMsg();
                if (!StrUtil.isEmpty(msg)) {
                    message.setMessageValue(msg);
                    req.setAttribute(MessageConstant.MESSAGE_DATA, message);
                }
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
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (CalendarException e) {
            e.printLog();
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
        } finally {
            closeDBConnection(conn);
            //setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);

            if (!StrUtil.isEmpty(forwardURL)) {
                forwarder.forwardView(forwardURL);
            } else {
                if (forwardOpen) {
                    forwarder.forwardOpenerView(forwardURL, msg);
                } else {
                    forwarder.forwardView(forwardURL, msg);
                }
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
     * @throws DTOException
     * @throws QueryException
     * @throws CalendarException
     */
    private AmsAssetsTransHeaderDTO fillData(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn) throws
            DTOException, QueryException, CalendarException {
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); // 设置单据号
        dto.setCreatedBy(user.getUserId()); // 设置创建人
        dto.setCreated(user.getUsername()); // 设置创建人
        dto.setFromOrganizationId(user.getOrganizationId());
        dto.setCurrCreationDate();
        dto.setFromCompanyName(user.getCompany());
        dto.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getMobilePhone());
        dto.setUserDeptName(user.getDeptName());
        DTOSet assetsGroups = user.getUserGroups();
        String provinceCode = dto.getServletConfig().getProvinceCode();
        if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)) { // 内蒙古特殊处理
            String transferType = dto.getTransferType();
            SfGroupDTO sfGRoup = null;
            if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
                DTOSet specGroups = getSpecGroups(user);
                if (specGroups.getSize() == 1) {
                    sfGRoup = (SfGroupDTO) specGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setFromDept(sfGRoup.getDeptId());
                    dto.setFromDeptName(sfGRoup.getDeptName());
                }
            } else {
                if (assetsGroups.getSize() == 1) {
                    sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setFromDept(sfGRoup.getDeptId());
                    dto.setFromDeptName(sfGRoup.getDeptName());
                }
            }
        } else if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)) {// 山西特殊处理
            String transType = dto.getTransType();
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                if (assetsGroups.getSize() == 1) {
                    SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setGroupProp(sfGRoup.getGroupProp());
                    dto.setFromDept(sfGRoup.getDeptId());
                    dto.setFromDeptName(sfGRoup.getDeptName());
                }
            } else {
                if (!user.isProvinceUser()) {
                    assetsGroups = getSpecGroups(user);
                }
                if (assetsGroups.getSize() == 1) {
                    SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setGroupProp(sfGRoup.getGroupProp());
                    dto.setFromDept(sfGRoup.getDeptId());
                    dto.setFromDeptName(sfGRoup.getDeptName());
                }
            }
        } else {

            if (null != assetsGroups && assetsGroups.getSize() == 1) {
                SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                dto.setFromGroup(sfGRoup.getGroupId());
                dto.setFromGroupName(sfGRoup.getGroupname());
                dto.setGroupProp(sfGRoup.getGroupProp());
                dto.setFromDept(sfGRoup.getDeptId());
                dto.setFromDeptName(sfGRoup.getDeptName());
            }

        }
        dto = fillOptions(dto, user, conn);
        return dto;
    }

    /**
     * 功能：获取用户的专业组别
     *
     * @param user SfUserDTO
     * @return DTOSet
     * @throws DTOException
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
     * 功能：补充资产来源部门数据下拉框
     *
     * @param dto  AmsAssetsTransHeaderDTO
     * @param user SfUserDTO
     * @param conn Connection
     * @return AmsAssetsTransHeaderDTO
     * @throws QueryException
     */
    private AmsAssetsTransHeaderDTO fillOptions(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn) throws
            QueryException {
        FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
        dto.setTransTypeValue(flexUtil.getFlexValue(AssetsDictConstant.ORDER_TYPE_ASSETS, dto.getTransType()));
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String fromDept = StrUtil.nullToString(dto.getFromDept());
        if (StrUtil.isEmpty(fromDept)) {
            fromDept = user.getDeptCode();
        }
        String deptOptions = optProducer.getUserAsssetsDeptOption(fromDept);
        dto.setFromDeptOption(deptOptions);
        String transType = dto.getTransType();
        if (transType.equals(AssetsDictConstant.ASS_RED)) {
            if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {
                String opt = optProducer.getTargetOrganization(user.getOrganizationId(), dto.getToOrganizationId());
                dto.setToCompanyOption(opt);
                ServletConfigDTO config = dto.getServletConfig();
                String provinceCode = config.getProvinceCode();
                if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)) {
                    int fromOrgId = dto.getFromOrganizationId();
                    if (fromOrgId == 0) {
                        fromOrgId = user.getOrganizationId();
                    }
                    opt = optProducer.getAllOrganization(fromOrgId);
                    dto.setFromCompanyOption(opt);
                    opt = optProducer.getBookTypeOption2(String.valueOf(fromOrgId));
                    dto.setBookTypeOption(opt);
                }
            } else {
                if (dto.getTransId().equals("")) {
                    dto.setToOrganizationId(user.getOrganizationId());
                }
            }
        }
        String transOption = "";

        transOption = optProducer.getFinanceProp2Option(dto.getFaContentCode());
        dto.setFaContentOption(transOption);

        return dto;
    }


    public String toQuery(HttpServletRequest req, HttpServletResponse res, AmsAssetsTransHeaderDTO dto) {
        req.setAttribute(QueryConstant.QUERY_DTO, dto);
        return ScrapURLListConstant.TRANS_QUERY_PAGE;
    }

    public String doQuery(HttpServletRequest req, HttpServletResponse res, AmsAssetsTransHeaderDTO dto, Connection conn) throws StrException, QueryException {
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
        BaseSQLProducer sqlProducer = new TransModel(user, dto);
        PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
        CheckBoxProp checkProp = new CheckBoxProp("subCheck");
        checkProp.addDbField("TRANS_ID");
        pageDAO.setWebCheckProp(checkProp);
        pageDAO.setCalPattern(LINE_PATTERN);
        pageDAO.produceWebData();
        req.setAttribute(QueryConstant.QUERY_DTO, dto);
        return ScrapURLListConstant.TRANS_QUERY_PAGE;
    }

    public ForwardDTO doDetail(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws QueryException, ServletException {
        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        ServletConfigDTO servletConfig = getServletConfig(req);
        if (StrUtil.isEmpty(dto.getTransId())) {
            dto.setTransId(dto.getApp_dataID());
        }
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
        if (headerDTO == null) {
            Message message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
            message.setIsError(true);
            forwardDTO.setMessage(message);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } else {
            OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
            String accessSheet = approveDAO.getAccessSheet();// 附件张数
            headerDTO.setAccessSheet(accessSheet);

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
                //TODO SJ
                ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
            }
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
//            forwardURL="/newasset/assetsDisTransDetail.jsp";
            forwardURL = ScrapURLListConstant.TRANS_DETAIL_PAGE;
        }
        forwardDTO.setForwardURL(forwardURL);
        return forwardDTO;
    }

    public ForwardDTO doApprove(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws QueryException, CalendarException, DTOException, ServletException {
        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        ServletConfigDTO servletConfig = getServletConfig(req);
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String option = null;

        String headerId = req.getParameter("sf_appDataID");
        if (headerId != null && !headerId.equals("")) {
            dto.setTransId(headerId);
        }
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
        if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn);
            Message message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
            message.setIsError(true);
            forwardDTO.setMessage(message);
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

            if (ds != null && !ds.isEmpty()) {
                for (int i = 0; i < ds.getSize(); i++) {
                    lineDTO = (AmsAssetsTransLineDTO) ds.getDTO(i);
                    option = optProducer.getRejectTypeOption(lineDTO.getRejectType());
                    lineDTO.setRejectTypeOpt(option);
                }
            }
            option = optProducer.getRejectTypeOption(headerDTO.getRejectHType());
            headerDTO.setRejectTypeHOpt(option);
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
            forwardURL = ScrapURLListConstant.TRANS_APPROVE_PAGE;
        }


        forwardDTO.setForwardURL(forwardURL);
        return forwardDTO;
    }

    public ForwardDTO doEdit(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws QueryException, CalendarException, DTOException, ServletException {
        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        ServletConfigDTO servletConfig = getServletConfig(req);
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String option = null;

        String headerId = req.getParameter("sf_appDataID");
        if (headerId != null && !headerId.equals("")) {
            dto.setTransId(headerId);
        }
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
        if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn);
            Message message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
            message.setIsError(true);
            forwardDTO.setMessage(message);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } else {
            headerDTO.setServletConfig(servletConfig);

            headerDTO = fillOptions(headerDTO, user, conn);
            String emergentLevel = headerDTO.getEmergentLevel();
            headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(emergentLevel));

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

            if (ds != null && !ds.isEmpty()) {
                for (int i = 0; i < ds.getSize(); i++) {
                    lineDTO = (AmsAssetsTransLineDTO) ds.getDTO(i);
                    option = optProducer.getRejectTypeOption(lineDTO.getRejectType());
                    lineDTO.setRejectTypeOpt(option);
                }
            }
            option = optProducer.getRejectTypeOption(headerDTO.getRejectHType());
            headerDTO.setRejectTypeHOpt(option);

            String sf_task_attribute3 = req.getParameter("sf_task_attribute3");
            if (sf_task_attribute3.equals(ScrapAppConstant.ATT3_APPROVING)) {
                OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
                String accessSheet = approveDAO.getAccessSheet();//附件张数
                forwardURL = ScrapURLListConstant.TRANS_APPROVE_PAGE;
                headerDTO.setAccessSheet(accessSheet);
            } else {
                forwardURL = ScrapURLListConstant.TRANS_EDIT_PAGE;
            }
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
        }


        forwardDTO.setForwardURL(forwardURL);
        return forwardDTO;
    }

    //创建申请
    public ForwardDTO doNew(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws QueryException, CalendarException, DTOException {
        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        ServletConfigDTO servletConfig = getServletConfig(req);
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);

        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);

        if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn);
        } else {
            String option = null;

            option = optProducer.getFinanceProp2Option(dto.getFaContentCode());
            headerDTO.setFaContentOption(option);

            String deptOptions = optProducer.getUserAsssetsDeptOption("");
            dto.setFromDeptOption(deptOptions);
        }


        headerDTO.setServletConfig(servletConfig);
        String provinceCode = servletConfig.getProvinceCode();
        headerDTO.setCalPattern(LINE_PATTERN);
        req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
        forwardURL = ScrapURLListConstant.TRANS_EDIT_PAGE;

        forwardDTO.setForwardURL(forwardURL);
//        forwardDTO.setMessage(message);
        return forwardDTO;
    }

    public ForwardDTO invokeDelete(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws DTOException {
        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;

        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req);
        dto.setTransStatus(AssetsDictConstant.IN_PROCESS);

        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        headerDAO.setDTOParameter(dto);
        headerDAO.doDelete(orderLines);

        Message message = headerDAO.getMessage();
        dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
        String transId = dto.getTransId();
        forwardURL = "/servlet/com.sino.ams.newasset.servlet.OrderApproveServlet";
        if (transId.equals("")) {
            forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
        } else {
            forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
        }
        forwardDTO.setForwardURL(forwardURL);
        forwardDTO.setMessage(message);
        return forwardDTO;
    }

    public ForwardDTO doSubmit(HttpServletRequest req, TransDTO dto, Connection conn) throws DTOException {
        String status = "IN_PROCESS";
        return doSave(req, dto, conn, status);
    }

    public ForwardDTO doSave(HttpServletRequest req, TransDTO dto, Connection conn, String status) throws DTOException {

        AmsAssetsTransHeaderDTO headerDTO = dto.getHeaderDTO();

        ForwardDTO forwardDTO = new ForwardDTO();
        String forwardURL = null;
        DTOSet orderLines = dto.getLines();

        headerDTO.setTransStatus(status);

        SfUserDTO user = (SfUserDTO) getUserAccount(req);

        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, headerDTO, conn);
        headerDAO.setDTOParameter(headerDTO);
        boolean isSuccess = headerDAO.doSave(orderLines);

        Message message = headerDAO.getMessage();
        String msg = headerDAO.getMsg();

        headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
        String transId = headerDTO.getTransId();
        forwardURL = ScrapURLListConstant.SCRAP_SERVELT;
        if (transId.equals("")) {
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
            forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
        } else {
            if (status.equals(AssetsDictConstant.SAVE_TEMP) || status.equals("")) {
                forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                forwardURL += "&transId=" + headerDTO.getTransId();
            } else {
                if (isSuccess) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + headerDTO.getTransId();
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&transId=" + headerDTO.getTransId();
                }
            }

        }

        if (!isSuccess) {
            if (StrUtil.isEmpty(msg)) {
                msg = "保存失败";
            }
        }

        forwardDTO.setMsg(msg);
        forwardDTO.setForwardURL(forwardURL);
        forwardDTO.setMessage(message);

        return forwardDTO;
    }


    /**
     * 退回
     *
     * @param req
     * @param dto
     * @param conn
     * @return
     * @throws StrException
     * @throws UploadException
     * @throws DataHandleException
     * @throws QueryException
     * @throws SQLModelException
     * @throws ContainerException
     * @throws SQLException
     * @throws ParseException
     */
    private ForwardDTO doReject(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws StrException, UploadException, DataHandleException, QueryException, SQLModelException, ContainerException, SQLException, ParseException {
        ForwardDTO forward = new ForwardDTO();
        String forwardURL = "";

        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);

        boolean operateResult = headerDAO.rejectProcedure();
        String msg = null;
        if (operateResult) {
            msg = "单据退回成功!";
            forwardURL = ScrapURLListConstant.SCRAP_SERVELT;
            forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
        } else {
            forwardURL = ScrapURLListConstant.SCRAP_SERVELT;
            forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
            msg = "单据退回失败!";
        }
        forward.setForwardURL(forwardURL);
        forward.setMsg(msg);
        return forward;
    }

    private ForwardDTO doCancel(HttpServletRequest req, AmsAssetsTransHeaderDTO dto, Connection conn) throws StrException, UploadException, DataHandleException, QueryException, SQLModelException, ContainerException, SQLException, ParseException {
        ForwardDTO forward = new ForwardDTO();
        String forwardURL = "";
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        boolean operateResult = headerDAO.cancelOrders();

        String msg = null;
        if (operateResult) {
            msg = "单据撤销成功!";
            forwardURL = ScrapURLListConstant.SCRAP_SERVELT;
            forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
        } else {
            forwardURL = ScrapURLListConstant.SCRAP_SERVELT;
            forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
            msg = "单据撤销失败!";
        }
        forward.setForwardURL(forwardURL);
        forward.setMsg(msg);
        return forward;
    }

    public class ForwardDTO {
        String msg = "";
        String forwardURL = "";
        Message message = null;
        boolean isClose = false;

        public String getForwardURL() {
            return forwardURL;
        }

        public void setForwardURL(String forwardURL) {
            this.forwardURL = forwardURL;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isClose() {
            return isClose;
        }

        public void setClose(boolean isClose) {
            this.isClose = isClose;
        }
    }

    /**
     * 取参数
     *
     * @param req
     * @return
     * @throws DTOException
     */
    private TransDTO getDTOFromReq(HttpServletRequest req) throws DTOException {
        AmsAssetsTransHeaderDTO headerDTO = null;
        DTOSet lines = null;

        Request2DTO req2DTO = new Request2DTO();
        req2DTO.setDTOClassName(TransDTO.class.getName());
        TransDTO dto = (TransDTO) req2DTO.getDTO(req);

        // 处理头参数
        req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        headerDTO = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
        dto.setHeaderDTO(headerDTO);

        // 处理行参数
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        lines = req2DTO.getDTOSet(req);
        dto.setLines(lines);

        return dto;
    }
}