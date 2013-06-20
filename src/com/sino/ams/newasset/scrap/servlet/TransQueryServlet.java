package com.sino.ams.newasset.scrap.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.dao.AmsAssetsTransHeaderDAO;
import com.sino.ams.newasset.scrap.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.scrap.dao.OrderApproveDAO;
import com.sino.ams.newasset.scrap.model.TransModel;
import com.sino.ams.newasset.scrap.servlet.TransServlet.ForwardDTO;
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
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
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
import com.sino.sinoflow.constant.WebAttrConstant;

public class TransQueryServlet extends BaseServlet  {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forwardURL = "";
        String msg = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try { 
        	ServletConfigDTO servletConfig = getServletConfig(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);

        	Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName()); 
            // 处理头参数
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            dto.setTransType("ASS-DIS"); 
            dto.setServletConfig(servletConfig);
             
            conn = getDBConnection(req);
            
            
            AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            
            String action = dto.getAct();
            // 流程信息
            String transType = dto.getTransType();
            String sf_isNew=(String)req.getAttribute(WebAttrConstant.SINOFLOW_NEW_CASE);
            if(sf_isNew != null && sf_isNew.equals("1")){
            	action="NEW_ACTION";
            }
            
            // 下拉处理
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String option = "";
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                option = optProducer.getTransferOption(dto.getTransferType());
                dto.setTransferTypeOption(option);
            }
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            dto.setFaContentOption(option);
            option=optProducer.getRejectTypeOption(dto.getRejectHType());
            dto.setRejectTypeHOpt(option);
            
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID+"");
            boolean isFinanceGroup = headerDAO.isFinanceGroup();
            req.setAttribute(AssetsWebAttributes.IS_FINANCE_GROUP, isFinanceGroup+"");
            
            
            ForwardDTO forward = null;
            if (action.equals("")) {
            	forwardURL = this.toQuery(req, res, dto);
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
            	forwardURL = this.doQuery(req, res, dto, conn);
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) { 
            	forward = this.doNew(req, res, dto, conn);
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
            	forward = this.doEdit(req, res, dto, conn);
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
            	forward = this.doDetail(req, res, dto, conn);
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { // 导出
            	this.doExport(req, res, dto, conn);
            } else if (action.equals(AssetsActionConstant.GET_TARGET_OU)) { // 获取目标OU
                this.getTargetOU(req, res, dto, conn);
            } else if(action.equals("excel")) {
            	forward = this.doExcel(req, res, dto, conn);
            } else if (action.equals(AMSActionConstant.VALIDATE_ACTION)) {// 验证barcode是否存在
				this.validBarcode(req, res, dto, conn);
			} else if (action.equals("DO_THRED_DEPT")) {
                this.prodDept(req, res, dto, conn);
			} else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
            
            if( null != forward ){
            	forwardURL = forward.getForwardURL();
            	message = forward.getMessage();
            	msg = forward.getMsg();
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
        }  catch (DataTransException ex) {
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
        	e.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
//            if (!StrUtil.isEmpty(forwardURL)) { 
            forwarder.forwardView(forwardURL);
//            }else{
//                forwarder.forwardOpenerView(forwardURL, msg);
//            }
        }
	}
	
	
	 /**
	 * 功能:填充DTO数据
	 * 
	 * @param dto
	 *            AmsAssetsTransHeaderDTO
	 * @param user
	 *            SfUserDTO
	 * @param conn
	 *            Connection
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
                }
            } else {
                if (assetsGroups.getSize() == 1) {
                    sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                }
            }
        } else if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)) {// 山西特殊处理
            String transType = dto.getTransType();
            if(transType.equals(AssetsDictConstant.ASS_RED)){
                if (assetsGroups.getSize() == 1) {
                    SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setGroupProp(sfGRoup.getGroupProp());
                }
            } else {
                if(!user.isProvinceUser()){
                    assetsGroups = getSpecGroups(user);
                }
                if (assetsGroups.getSize() == 1) {
                    SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                    dto.setFromGroup(sfGRoup.getGroupId());
                    dto.setFromGroupName(sfGRoup.getGroupname());
                    dto.setGroupProp(sfGRoup.getGroupProp());
                }
            }
        } else {
            if ( null != assetsGroups && assetsGroups.getSize() == 1) {
                SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                dto.setFromGroup(sfGRoup.getGroupId());
                dto.setFromGroupName(sfGRoup.getGroupname());
                dto.setGroupProp(sfGRoup.getGroupProp());
            }
        }
        dto = fillOptions(dto, user, conn);
        return dto;
    }

    /**
	 * 功能：获取用户的专业组别
	 * 
	 * @param user
	 *            SfUserDTO
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
	 * 
	 * @param user
	 *            SfUserDTO
	 * @param servletConfig
	 *            servlet配置信息
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
                    if ( sfUserRight.getGroupId() != sfGroup.getGroupId() ) {
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
	 * @param dto
	 *            AmsAssetsTransHeaderDTO
	 * @param user
	 *            SfUserDTO
	 * @param conn
	 *            Connection
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
                    if (fromOrgId==0) {
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
        if (transType.equals(AssetsDictConstant.ASS_RED)) {
            transOption = optProducer.getTransferFaOption(dto.getFaContentCode(),
                    dto.getTransferType());
        } else {
            transOption = optProducer.getFAContentOption(dto.getFaContentCode());
        }
        dto.setFaContentOption(transOption);
        return dto;
    } 
    
    
    public String toQuery( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto   ){
		req.setAttribute(QueryConstant.QUERY_DTO, dto);
        return "/newasset/assetsDisTrans.jsp";
	}
	public String doQuery( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn  ) throws StrException, QueryException{
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
		return "/newasset/assetsDisTrans.jsp";
	}
 
	public ForwardDTO doDetail( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn  ) throws QueryException, ServletException{
		ForwardDTO forwardDTO = new ForwardDTO();
		String forwardURL = null;   
		SfUserDTO user = (SfUserDTO) getUserAccount(req);
		ServletConfigDTO servletConfig = getServletConfig(req);
		AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
		headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)headerDAO.getDataByPrimaryKey();
        OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
        String accessSheet = approveDAO.getAccessSheet();// 附件张数
        headerDTO.setAccessSheet(accessSheet);

        if (headerDTO == null) {
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
             
            req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
            forwardURL="/newasset/assetsDisTransDetail.jsp";
        }
        forwardDTO.setForwardURL(forwardURL);
        return forwardDTO;
	}
	
	public ForwardDTO doEdit( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws QueryException, CalendarException, DTOException, ServletException{
		ForwardDTO forwardDTO = new ForwardDTO();
		String forwardURL = null;   
		SfUserDTO user = (SfUserDTO) getUserAccount(req);
		ServletConfigDTO servletConfig = getServletConfig(req);
		AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String option = null;
		
		String headerId =  req.getParameter("sf_appDataID");
		if(headerId!=null&&!headerId.equals("")){
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
	    	if(ds != null && !ds.isEmpty()){
	    		for(int i = 0; i < ds.getSize(); i++){
	    			lineDTO = (AmsAssetsTransLineDTO)ds.getDTO(i);
	    			option=optProducer.getRejectTypeOption(lineDTO.getRejectType());
	    			lineDTO.setRejectTypeOpt(option);
	    		} 
	    	}
	    	option=optProducer.getRejectTypeOption(headerDTO.getRejectHType());
	    	headerDTO.setRejectTypeHOpt(option);
	    	req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
	    	req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
	    	forwardURL =  "/newasset/assetsDisTransEdit.jsp";
	    }
	    forwardDTO.setForwardURL(forwardURL);
	    return forwardDTO;
	}
	
	//创建申请
	public ForwardDTO doNew( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn  ) throws QueryException, CalendarException, DTOException{
		ForwardDTO forwardDTO = new ForwardDTO();
		String forwardURL = null;   
		SfUserDTO user = (SfUserDTO) getUserAccount(req);
		ServletConfigDTO servletConfig = getServletConfig(req);
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		
		AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
        if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn); 
        } else {
            String option = optProducer.getFAContentOption(dto.getFaContentCode());
            headerDTO.setFaContentOption(option);
            String deptOptions = optProducer.getUserAsssetsDeptOption("");
            dto.setFromDeptOption(deptOptions);
        }
        headerDTO.setServletConfig(servletConfig);
        String provinceCode = servletConfig.getProvinceCode();
        headerDTO.setCalPattern(LINE_PATTERN);
        req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
        forwardURL =  "/newasset/assetsDisTransEdit.jsp";
        
        forwardDTO.setForwardURL(forwardURL);
//        forwardDTO.setMessage(message);
        return forwardDTO;
	}
	
	public ForwardDTO invokeDelete( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn  ) throws DTOException{
		ForwardDTO forwardDTO = new ForwardDTO();
		String forwardURL = null;   
		
		Request2DTO req2DTO = new Request2DTO();
		req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req); 
        dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
        
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
        String sf_appFieldValue = StrUtil.nullToString(req.getParameter("sf_appFieldValue"));
        
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
	public ForwardDTO doSubmit( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws DTOException{
		String status = "IN_PROCESS";
		return doSave(req, res, dto, conn, status);
	}

	public ForwardDTO doSave( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn , String status ) throws DTOException{
		ForwardDTO forwardDTO = new ForwardDTO();
		String forwardURL = null;   
		Request2DTO req2DTO = new Request2DTO();
		req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req);
 
        dto.setTransStatus( status );
//        dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
        
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
        String sf_appFieldValue = StrUtil.nullToString(req.getParameter("sf_appFieldValue"));
        
        AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        
        headerDAO.setDTOParameter(dto);
        headerDAO.saveOrder(orderLines);
        
        Message message = headerDAO.getMessage();
        dto = (AmsAssetsTransHeaderDTO) headerDAO.getDTOParameter();
        String transId = dto.getTransId();
        forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsDisTransHeaderServlet";
        if (transId.equals("")) {
            forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
            req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
        } else {
            forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
            forwardURL += "&transId=" + dto.getTransId();
        }
        forwardDTO.setForwardURL(forwardURL);
        forwardDTO.setMessage(message);
        return forwardDTO;
	}
	
	public void doExport( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws DataTransException, WebFileDownException{
		SfUserDTO user = (SfUserDTO) getUserAccount(req);
		AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        
		File file = headerDAO.exportFile();
        headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
        WebFileDownload fileDown = new WebFileDownload(req, res);
        fileDown.setFilePath(file.getAbsolutePath());
        fileDown.download();
        file.delete();
	}
	
	public void getTargetOU( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws IOException, QueryException{
		SfUserDTO user = (SfUserDTO) getUserAccount(req);
		res.setContentType("text/html;charset=GBK");
	    PrintWriter out = res.getWriter();
	    AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
	    String opt = optProducer.getTargetOrganization(dto.getFromOrganizationId(), 0);
	    out.print(opt);
	    out.close();
	}
	
	public ForwardDTO doExcel( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn  ) throws DTOException, QueryException, CalendarException, ServletException{
		ForwardDTO forward = new ForwardDTO();
		SfUserDTO user = (SfUserDTO) getUserAccount(req); 
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String option = null;
		ServletConfigDTO servletConfig = getServletConfig(req);
		DTOSet lineDTOSetALL = new DTOSet();
		Message message = null;
		
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
         TransModel assetsTransHeaderModel = new TransModel(user, dto);
         String excel= StrUtil.nullToString(req.getParameter("excel"));
         SQLModel sqlModel = new SQLModel();
         sqlModel = assetsTransHeaderModel.getQueryBarcodeExcelModel(excel, headerDTO);
         SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//simpleQuery.executeQuery();
//RowSet rowSet = simpleQuery.getSearchResult();
         simpleQuery.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
         simpleQuery.executeQuery();
         lineDTOSet = simpleQuery.getDTOSet();

         // EXCEL导入时导入不成功的DTOSET(此方法找不到系统中不存在的BARCODE，必须要用循环来取得！)
//String returnModel = assetsTransHeaderModel.getBarcodeReturnModel(excel, headerDTO);
//sqlModel = assetsTransHeaderModel.getQueryBarcodeAllExcelModel(excel, headerDTO, returnModel);
//SimpleQuery simpleQuery2 = new SimpleQuery(sqlModel, conn);
//simpleQuery2.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
//simpleQuery2.executeQuery();
//DTOSet lineDTOSetALL = simpleQuery2.getDTOSet();

         // EXCEL导入时导入不成功的DTOSET
         lineDTOSetALL = new DTOSet();
         Map mp1=new HashMap();
         for (int j=0;j<lineDTOSet.getSize();j++){
             String barcode=((AmsAssetsTransLineDTO)lineDTOSet.getDTO(j)).getBarcode();
             mp1.put(barcode,barcode);
         }
//lineDTOSetALL.clearData();
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
//req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet);
         req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA,lineDTOSet);
         req.setAttribute(AssetsWebAttributes.PRIVI_DATA,lineDTOSetALL);
         req.setAttribute("REMARK_LIST",list);
         
         forward.setMessage(message);
         forward.setForwardURL( AssetsURLList.TRANS_EDIT_PAGE );
         return forward;
	}
	
	//// 验证barcode是否存在
	public void validBarcode( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws IOException{
		res.setContentType("text/html;charset=GBK");
		PrintWriter out = res.getWriter();
		String fromGroup = req.getParameter("fromGroup");
        boolean result = false;
		if(fromGroup != null && !fromGroup.equals("")){
		}
        out.print(result);
		out.close();
	}
	
	public void prodDept( HttpServletRequest req, HttpServletResponse res , AmsAssetsTransHeaderDTO dto , Connection conn ) throws QueryException, IOException{
		String fDept = StrUtil.nullToString(req.getParameter("fDept"));
        String tDept = StrUtil.nullToString(req.getParameter("tDept"));
        
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
		AmsAssetsTransHeaderDAO headerDAO = new AmsAssetsTransHeaderDAO(user, dto, conn);
        
        boolean isThredDept = headerDAO.findThredDept(fDept, tDept);
		PrintWriter out = res.getWriter();
		if (isThredDept) {
			out.print("Y");
		} else {
            out.print("N");
        }
		out.flush();
		out.close();
	}
	 
	public class ForwardDTO {
		String msg = "" ;
		String forwardURL = "" ;
		Message message = null;
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
	}

}
