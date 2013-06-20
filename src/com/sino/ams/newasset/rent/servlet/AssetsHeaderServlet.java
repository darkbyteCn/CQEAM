package com.sino.ams.newasset.rent.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.OrderApproveDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.rent.dao.AssetsHeaderDAO;
import com.sino.ams.newasset.rent.model.AssetsHeaderModel;
import com.sino.ams.newasset.servlet.AssetsTransFlowBaseServlet;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class AssetsHeaderServlet extends AssetsTransFlowBaseServlet {

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

            AssetsHeaderDAO headerDAO = new AssetsHeaderDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            String transType = dto.getTransType();

//            String isTd = headerDAO.isTd();
            String isTd = user.getIsTd();
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = "";
            option = optProducer.getFAContentOptionT(dto.getFaContentCode(), transType, isTd);
            dto.setFaContentOption(option);
            String emergentLevelOption = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
            dto.setEmergentLevelOption(emergentLevelOption);
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID + "");
            boolean isFinanceGroup = headerDAO.isFinanceGroup();
            req.setAttribute(AssetsWebAttributes.IS_FINANCE_GROUP, isFinanceGroup + "");
            boolean isSpecialityDept = false;
            option = optProducer.getRejectTypeOption(dto.getRejectHType());
            dto.setRejectTypeHOpt(option);

            action = StrUtil.isEmpty(action) ? AssetsActionConstant.EDIT_ACTION : action;
            DTOSet lineDTOSetALL = new DTOSet();
            if (action.equals(AssetsActionConstant.INIT_ACTION)) {
                dto.setCalPattern(LINE_PATTERN);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/newasset/rent/asstesQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                BaseSQLProducer sqlProducer = new AssetsHeaderModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/newasset/rent/asstesQuery.jsp";
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsTransHeaderDTO headerDTO = fillData(dto, user, conn);
                headerDTO.setServletConfig(servletConfig);
                headerDTO.setCalPattern(LINE_PATTERN);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                req.setAttribute("isSpecialityDept", isSpecialityDept);
                forwardURL = AssetsURLList.ASSETS_RENT_PAGE;
                
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = exportFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
                
            } else if (action.equals("excel")) {
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
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
                AssetsHeaderModel assetsTransHeaderModel = new AssetsHeaderModel(user, dto);
                String excel = StrUtil.nullToString(req.getParameter("excel"));
                String[] arr = StrUtil.splitStr(excel, ",");

                try {
                    //删除IMP_BARCODE表中属于当前用户导入的BARCODE记录
                    SQLModel delSqlModel = new SQLModel();
                    delSqlModel = assetsTransHeaderModel.getImpBarcodeDeleteModel();
                    DBOperator.updateRecord(delSqlModel, conn);
                    //添加当前用户要导入的BARCODE记录到表IMP_BARCODE中
                    SQLModel insertSqlModel = new SQLModel();
                    //默认去掉excel中的重复条码
                    Collection c = new ArrayList<String>();
                    for (int i = 0; i < arr.length; i++) {
                        String s = arr[i].replaceAll("'", "");
                        if (!c.contains(s)) {
                            insertSqlModel = assetsTransHeaderModel.getDataInsertModel(s);
                            DBOperator.updateRecord(insertSqlModel, conn);
                            c.add(s);
                        }
                    }
                } catch (DataHandleException e) {
                    
                    e.printStackTrace();
                }

                SQLModel sqlModel = new SQLModel();
                sqlModel = assetsTransHeaderModel.getQueryBarcodeExcelModel(excel, headerDTO);
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                simpleQuery.executeQuery();
                lineDTOSet = simpleQuery.getDTOSet();

                //EXCEL导入时导入不成功的DTOSET
                lineDTOSetALL = new DTOSet();
                Map mp1 = new HashMap();
                for (int j = 0; j < lineDTOSet.getSize(); j++) {
                    AmsAssetsTransLineDTO lineDTO = (AmsAssetsTransLineDTO) lineDTOSet.getDTO(j);
                    String barcode = lineDTO.getBarcode();
                    mp1.put(barcode, barcode);
                    option = optProducer.getRejectTypeOption(lineDTO.getRejectType());
                    lineDTO.setRejectTypeOpt(option);
                }
                for (int i = 0; i < arr.length; i++) {
                    String s = arr[i].replaceAll("'", "");
                    if (!mp1.containsKey(s)) {
                        AmsAssetsTransLineDTO dtoa = new AmsAssetsTransLineDTO();
                        //查询ETS_ITEM_INFO中的barcode
                        SQLModel queryModel = new SQLModel();
                        queryModel = assetsTransHeaderModel.queryBarcode(s);
                        SimpleQuery qeuryQuery = new SimpleQuery(queryModel, conn);
                        qeuryQuery.executeQuery();
                        RowSet rs = qeuryQuery.getSearchResult();
                        int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
                        String erroMsg = "";
                        if (n == 0) {
                            erroMsg = "系统中不存在该条码";
                        } else {
                            erroMsg = "该条码不属于您的权限范围";
                        }
                        String excelLineId = String.valueOf(i + 2);
                        dtoa.setBarcode(s);
                        dtoa.setErrorMsg(erroMsg);
                        dtoa.setExcelLineId(excelLineId);
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
                list.add(0, "1、系统中可能不存在该条码");
                list.add(1, "2、该条码可能不属于本公司");
                list.add(2, "3、该条码可能不属于您的权限范围");
                list.add(3, "4、该条码可能存在于单据中(盘点、调拨、报废等)");
                req.setAttribute("REMARK_LIST", list);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, lineDTOSet);
                req.setAttribute(AssetsWebAttributes.PRIVI_DATA, lineDTOSetALL);
                forwardURL = "/newasset/rent/assetsEdit.jsp";
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

                    if (ds != null && !ds.isEmpty()) {
                        for (int i = 0; i < ds.getSize(); i++) {
                            lineDTO = (AmsAssetsTransLineDTO) ds.getDTO(i);
                            option = optProducer.getRejectTypeOption(lineDTO.getRejectType());
                            lineDTO.setRejectTypeOpt(option);
                        }
                    }
                    option = optProducer.getRejectTypeOption(headerDTO.getRejectHType());
                    headerDTO.setRejectTypeHOpt(option);
                    headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel()));

                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                    forwardURL = AssetsURLList.ASSETS_DETAIL_PAGE;
                }
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                if (StrUtil.isEmpty(dto.getTransId())) {
                    AmsAssetsTransHeaderDTO headerDTO = fillData(dto, user, conn);
                    headerDTO.setServletConfig(servletConfig);
                    headerDTO.setCalPattern(LINE_PATTERN);
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                    isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                    req.setAttribute("isSpecialityDept", isSpecialityDept);
                    forwardURL = AssetsURLList.ASSETS_RENT_PAGE;
                } else {
                    headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                    AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                    OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
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

                        if (ds != null && !ds.isEmpty()) {
                            for (int i = 0; i < ds.getSize(); i++) {
                                lineDTO = (AmsAssetsTransLineDTO) ds.getDTO(i);
                                option = optProducer.getRejectTypeOption(lineDTO.getRejectType());
                                lineDTO.setRejectTypeOpt(option);
                            }
                        }
                        option = optProducer.getRejectTypeOption(headerDTO.getRejectHType());
                        headerDTO.setRejectTypeHOpt(option);
                        headerDTO.setEmergentLevelOption(optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel()));

                        req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                        req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);

                        isSpecialityDept = headerDAO.isSpecialGroup(headerDTO.getFromGroup());
                        req.setAttribute("isSpecialityDept", isSpecialityDept);

                        forwardURL = AssetsURLList.ASSETS_RENT_PAGE;
                    }
                }
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
                forwardURL = "/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet";
                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
            } else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) {
                boolean operateResult = headerDAO.cancelOrders();
                message = headerDAO.getMessage();
                dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
                forwardURL = "/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet";
                if (operateResult) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                }
                forwardURL += "&transId=" + dto.getTransId();
            } else if (action.equals(AssetsActionConstant.REJECT_ACTION)) {
                headerDAO.rejectOrder();
                message = headerDAO.getMessage();
                dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
                forwardURL = "/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet";
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + dto.getTransId();
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
                forwardURL = "/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet";

                if (transId.equals("")) {
                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                }
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
        } catch (CalendarException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        AssetsHeaderDAO headerDAO = new AssetsHeaderDAO(user, dto, conn);
        String isTd = headerDAO.isTd();
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
        
        StringBuffer transOptHtml = new StringBuffer();
        String transType = dto.getTransType();
        String transTypeDefine = dto.getTransTypeDefine();

        if ("ASS-RENT".equals(transType)) { //出租
            dto.setTransTypeValue("出租单");
        } else if ("ASS-DONATE".equals(transType)) { //捐赠
//        	dto.setTransTypeValue("捐赠单");
            if (transTypeDefine.equals("DISCARD")) {
                transOptHtml.append("<option value=\"NORMAL\">正常资产捐赠</option>");
                transOptHtml.append("<option value=\"DISCARD\" selected>报废资产捐赠</option>");
            } else {
                transOptHtml.append("<option value=\"NORMAL\" selected>正常资产捐赠</option>");
                transOptHtml.append("<option value=\"DISCARD\">报废资产捐赠</option>");
            }
            dto.setTransTypeValue(transOptHtml.toString());
        } else if ("ASS-REPAIRRETURN".equals(transType)) { //送修返还
            dto.setTransTypeValue("送修返还单");
        } else if ("ASS-REPAIR".equals(transType)) { //送修
            dto.setTransTypeValue("送修单");
        } else if ("ASS-SELL".equals(transType)) { //销售
//    		dto.setTransTypeValue("销售单");
            if (transTypeDefine.equals("DISCARD")) {
                transOptHtml.append("<option value=\"NORMAL\">正常资产销售</option>");
                transOptHtml.append("<option value=\"DISCARD\" selected>报废资产销售</option>");
            } else {
                transOptHtml.append("<option value=\"NORMAL\" selected>正常资产销售</option>");
                transOptHtml.append("<option value=\"DISCARD\">报废资产销售</option>");
            }
            dto.setTransTypeValue(transOptHtml.toString());
        } else if ("ASS-WASTEFORECAST".equals(transType)) { //预报废
            dto.setTransTypeValue("预报废单");
        } else if ("ASS-REPEAL".equals(transType)) {
            dto.setTransTypeValue("预报废撤销单");
        } else if ("ASS-EXCHANGE".equals(transType)) { //置换
            dto.setTransTypeValue("置换单");
        } else if ("ASS-BORROW".equals(transType)) {
            dto.setTransTypeValue("借用单");
        } else if ("ASS-RETURN".equals(transType)) {
            dto.setTransTypeValue("送还单");
        }

        String option = "";
        option = optProducer.getFAContentOptionT(dto.getFaContentCode(), transType, isTd);
        dto.setFaContentOption(option);
//        String transOption = "";
//        transOption = optProducer.getFAContentOption(dto.getFaContentCode());
//        dto.setFaContentOption(transOption);
        return dto;
    }
    
    
    public File exportFile(SfUserDTO user, AmsAssetsTransHeaderDTO dto, Connection conn) throws DataTransException {
        File file = null;
        try {
            DataTransfer transfer = null;
            BaseSQLProducer sqlProducer = new AssetsHeaderModel(user, dto); //OrderQueryModel
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);            
            String transType = StrUtil.nullToString(dto.getTransType());
            String fileName = "";
            if (transType.equals("ASS-WASTEFORECAST")) {
                fileName = "预报废单据表.xls";
            } else if (transType.equals("ASS-REPEAL")) {
                fileName = "预报废撤销单据表.xls";
            } else if (transType.equals("ASS-RENT")) {
                fileName = "出租单据表.xls";
            } else if (transType.equals("ASS-DONATE")) {
                fileName = "捐赠单据表.xls";
            } else if (transType.equals("ASS-REPAIR")) {
                fileName = "送修单据表.xls";
            } else if (transType.equals("ASS-REPAIRRETURN")) {
                fileName = "送修返还单据表.xls";
            } else if (transType.equals("ASS-SELL")) {
                fileName = "销售单据表.xls";
            } else if (transType.equals("ASS-EXCHANGE") || transType.equals("ASS-REPLACEMENT")) {
                fileName = "置换单据表.xls";
            } else if (transType.equals("ASS-BORROW")) {
                fileName = "借用单据表.xls";
            } else if (transType.equals("ASS-RETURN")) {
                fileName = "送还单据表.xls";
            } else {
            	fileName = "其它单据表.xls";
            }
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();

            if (transType.equals("ASS-WASTEFORECAST")) {
                fieldMap.put("TRANS_NO", "预报废单号");
                fieldMap.put("FROM_DEPT_NAME", "预报废部门");
            } else if (transType.equals("ASS-REPEAL")) {
                fieldMap.put("TRANS_NO", "预报废撤销单号");
                fieldMap.put("FROM_DEPT_NAME", "预报废撤销部门");
            } else if (transType.equals("ASS-RENT")) {
                fieldMap.put("TRANS_NO", "出租单号");
                fieldMap.put("FROM_DEPT_NAME", "出租部门"); 
            } else if (transType.equals("ASS-DONATE")) {
                fieldMap.put("TRANS_NO", "捐赠单号");
                fieldMap.put("FROM_DEPT_NAME", "捐赠部门");
            } else if (transType.equals("ASS-REPAIR")) {
                fieldMap.put("TRANS_NO", "送修单号");
                fieldMap.put("FROM_DEPT_NAME", "送修部门");
            } else if (transType.equals("ASS-REPAIRRETURN")) {
                fieldMap.put("TRANS_NO", "送修返还单号");
                fieldMap.put("FROM_DEPT_NAME", "送修返还部门");  
            } else if (transType.equals("ASS-SELL")) {
                fieldMap.put("TRANS_NO", "销售单号");
                fieldMap.put("FROM_DEPT_NAME", "销售部门"); 
            } else if (transType.equals("ASS-EXCHANGE") || transType.equals("ASS-REPLACEMENT")) {
                fieldMap.put("TRANS_NO", "置换单号");
                fieldMap.put("FROM_DEPT_NAME", "置换部门");
            } else if (transType.equals("ASS-BORROW")) {
                fieldMap.put("TRANS_NO", "借用单号");
                fieldMap.put("FROM_DEPT_NAME", "借用部门");
            } else if (transType.equals("ASS-RETURN")) {
                fieldMap.put("TRANS_NO", "送还单号");
                fieldMap.put("FROM_DEPT_NAME", "送还部门");        
            } else if (transType.equals("ASS-RETURN")) {
                fieldMap.put("TRANS_NO", "送还单号");
                fieldMap.put("FROM_DEPT_NAME", "送还部门");
			} else {
                fieldMap.put("TRANS_NO", "其它单号");
                fieldMap.put("FROM_DEPT_NAME", "部门");
            }
        	fieldMap.put("TRANS_STATUS_DESC", "单据状态");
            //fieldMap.put("TRANSFER_TYPE_DESC", "单据类型");
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
