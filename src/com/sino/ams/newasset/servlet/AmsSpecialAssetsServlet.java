package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.AmsSpecialAssetsDAO;
import com.sino.ams.newasset.dao.SpecialOrderApproveDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsSpecialAssetsDTO;
import com.sino.ams.newasset.model.AmsSpecialAssetsModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsSpecialAssetsServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsSpecialAssetsServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws	ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
            AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) req2DTO.getDTO(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            dto.setServletConfig(servletConfig);
            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AmsSpecialAssetsDAO headerDAO = new AmsSpecialAssetsDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = "";
            option = optProducer.getSpecialAccetsTypeOption(dto.getTransType());
            dto.setTransTypeOption(option);
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            dto.setFaContentOption(option);
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID+"");
            DTOSet lineDTOSetALL = new DTOSet();
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.SPECIAL_ASSETS_QUERY_PAGE;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                BaseSQLProducer sqlProducer = new AmsSpecialAssetsModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.SPECIAL_ASSETS_QUERY_PAGE;
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
            	AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
                if (headerDTO == null) {
                    headerDTO = fillData(dto, user, conn);
                } else {
                    option = optProducer.getFAContentOption(dto.getFaContentCode());
                    headerDTO.setFaContentOption(option);
                    String deptOptions = optProducer.getUserAsssetsDeptOption("");
                    dto.setFromDeptOption(deptOptions);
                }
                headerDTO.setServletConfig(servletConfig);
                headerDTO.setCalPattern(LINE_PATTERN);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                forwardURL = AssetsURLList.SPECIAL_ASSETS_EDIT_PAGE;
                
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                headerDAO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
                AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = fillData(dto, user, conn);
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
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                    forwardURL = AssetsURLList.SPECIAL_ASSETS_EDIT_PAGE;
                }
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                headerDAO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
                AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO)headerDAO.getDataByPrimaryKey();

                SpecialOrderApproveDAO approveDAO = new SpecialOrderApproveDAO(user, dto, conn);
                String accessSheet = approveDAO.getAccessSheet();//附件张数
                headerDTO.setAccessSheet(accessSheet);

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
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                    forwardURL = AssetsURLList.SPECIAL_ASSETS_DETAIL_PAGE;
                }
            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
                req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsSpecialAssetsDTO.class);
                DTOSet orderLines = req2DTO.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                headerDAO.setDTOParameter(dto);
                headerDAO.saveOrder(orderLines, flowDTO);
                message = headerDAO.getMessage();
                dto = (AmsSpecialAssetsDTO) headerDAO.getDTOParameter();
                String transId = dto.getTransId();
                forwardURL = AssetsURLList.SPECIAL_ASSETS_SERVLET;
                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
                req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsSpecialAssetsDTO.class);
                DTOSet orderLines = req2DTO.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                headerDAO.setDTOParameter(dto);
                headerDAO.submitOrder(orderLines, flowDTO);
                message = headerDAO.getMessage();
                dto = (AmsSpecialAssetsDTO) headerDAO.getDTOParameter();
                String transId = dto.getTransId();
                forwardURL = AssetsURLList.SPECIAL_ASSETS_SERVLET;
                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if(action.equals(AssetsActionConstant.DELETE_ACTION)){
                req2DTO.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
                req2DTO.setIgnoreFields(AmsSpecialAssetsDTO.class);
                DTOSet orderLines = req2DTO.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                headerDAO.setDTOParameter(dto);
                headerDAO.doDelete(orderLines, flowDTO);
                message = headerDAO.getMessage();
                dto = (AmsSpecialAssetsDTO) headerDAO.getDTOParameter();
                String transId = dto.getTransId();
                forwardURL = "/servlet/com.sino.ams.newasset.servlet.SpecialOrderApproveServlet";
                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) { //撤销暂存的单据
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                RequestParser parser = new RequestParser();
                parser.setCheckBoxProp(checkProp);
                parser.transData(req);
                String[] transIds = parser.getParameterValues("transId");
                boolean operateResult = headerDAO.cancelOrders(transIds);
                message = headerDAO.getMessage();
                forwardURL = AssetsURLList.SPECIAL_ASSETS_SERVLET;
                if (parser.contains("fromGroup")) {
                    if (operateResult) {
                        forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    } else {
                        forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    }
                    forwardURL += "&transId=" + dto.getTransId();
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
                }
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出
                File file = headerDAO.exportFile();
                headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(AssetsActionConstant.GET_TARGET_OU)) { //获取目标OU
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                String opt = optProducer.getTargetOrganization(dto.getFromOrganizationId(), 0);
                out.print(opt);
                out.close();
            } else if(action.equals("excel")) {
            	AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
                if (headerDTO == null) {
                    headerDTO = fillData(dto, user, conn);
                } else {
                    option = optProducer.getFAContentOption(dto.getFaContentCode());
                    headerDTO.setFaContentOption(option);
                    String deptOptions = optProducer.getUserAsssetsDeptOption("");
                    dto.setFromDeptOption(deptOptions);
                }
                headerDTO.setServletConfig(servletConfig);
                headerDTO.setCalPattern(LINE_PATTERN);

                DTOSet lineDTOSet = new DTOSet();
                AmsSpecialAssetsModel assetsTransHeaderModel = new AmsSpecialAssetsModel(user, dto);
                String excel= StrUtil.nullToString(req.getParameter("excel"));
                SQLModel sqlModel = new SQLModel();
                sqlModel = assetsTransHeaderModel.getQueryBarcodeExcelModel(excel, headerDTO);
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.setDTOClassName(AmsSpecialAssetsDTO.class.getName());
                simpleQuery.executeQuery();
                lineDTOSet = simpleQuery.getDTOSet();
                lineDTOSetALL = new DTOSet();
                Map mp1=new HashMap();
                for (int j=0;j<lineDTOSet.getSize();j++){
                    String barcode=((AmsAssetsTransLineDTO)lineDTOSet.getDTO(j)).getBarcode();
                    mp1.put(barcode,barcode);
                }
                String[] arr=StrUtil.splitStr(excel,",");
                for (int i = 0; i < arr.length; i++) {
                    String s = arr[i].replaceAll("'","");
                    if(!mp1.containsKey(s)){
                      AmsAssetsTransLineDTO dtoa=new AmsAssetsTransLineDTO();
                      dtoa.setBarcode(s);
                      lineDTOSetALL.addDTO(dtoa);
                    }
                }
                if (lineDTOSetALL.isEmpty()) {
                    message = getMessage(CustMessageKey.EXPORT_SUCCESS);
                    message.setIsError(false);
                } else {
                    message = getMessage(CustMessageKey.EXPORT_FAILURE);
                    message.setIsError(true);
                }
                List list = new ArrayList();
                list.add(0,"1、系统中可能不存在该条码");
                list.add(1,"2、该条码可能不属于本公司");
                list.add(2,"3、该条码可能不属于您的权限范围");
                list.add(3,"4、该条码可能存在于单据中(盘点、调拨、报废等)");

                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
//                req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA,lineDTOSet);
                req.setAttribute(AssetsWebAttributes.PRIVI_DATA,lineDTOSetALL);
                req.setAttribute("REMARK_LIST",list);
                forwardURL = AssetsURLList.TRANS_EDIT_PAGE;
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
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }

    /**
     * 功能:填充DTO数据
     * @param dto  AmsSpecialAssetsDTO
     * @param user SfUserDTO
     * @param conn Connection
     * @return AmsSpecialAssetsDTO
     * @throws DTOException
     * @throws QueryException
     * @throws CalendarException
     */
    private AmsSpecialAssetsDTO fillData(AmsSpecialAssetsDTO dto, SfUserDTO user, Connection conn) throws
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
        }
        dto = fillOptions(dto, user, conn);
        return dto;
    }

    /**
     * 功能：获取用户的专业组别
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
     * 功能：获取用户的专业组别
     * @param user          SfUserDTO
     * @param servletConfig servlet配置信息
     * @return DTOSet
     * @throws DTOException
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
                    if (sfUserRight.getGroupId()!=sfGroup.getGroupId()) {
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
     * @param dto  AmsSpecialAssetsDTO
     * @param user SfUserDTO
     * @param conn Connection
     * @return AmsSpecialAssetsDTO
     * @throws QueryException
     */
    private AmsSpecialAssetsDTO fillOptions(AmsSpecialAssetsDTO dto, SfUserDTO user, Connection conn) throws
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
        dto.setToOrganizationId(user.getOrganizationId());
        String transOption = "";
        transOption = optProducer.getFAContentOption(dto.getFaContentCode());
        dto.setFaContentOption(transOption);
        String assetsTypeOption = "";
        assetsTypeOption = optProducer.getAccetsTypeOption(dto.getAssetsType());
        dto.setAssetsTypeOption(assetsTypeOption);
        return dto;
    }
}
