package com.sino.ams.system.rent.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.rent.dao.AMSRentChangeDAO;
import com.sino.ams.system.rent.dao.AMSRentChangeLDAO;
import com.sino.ams.system.rent.model.AMSRentChangeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.constant.WebAttrConstant;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-10
 * Time: 9:56:29
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String msg = "";
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            dto.setServletConfig(servletConfig);
            FlowDTO flowDTO = new FlowDTO();
            String action = dto.getAct();
            conn = getDBConnection(req);
            AMSRentChangeDAO headerDAO = new AMSRentChangeDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            String transType = dto.getTransType();
            String transferype = dto.getTransferType();
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            OptionProducer op = new OptionProducer(user, conn);
            String option = "";
             String sf_appFieldValue = StrUtil.nullToString(req
                        .getParameter("sf_appFieldValue"));
            String to_end = StrUtil.nullToString(req
                        .getParameter("sf_end"));
                     String headerId =  StrUtil.nullToString(req.getParameter("sf_appDataID"));
            if(!headerId.equals("")){
                 dto.setTransId(headerId);
            }
           String   sf_isNew=(String)req.getAttribute(WebAttrConstant.SINOFLOW_NEW_CASE);
              if(sf_isNew != null && sf_isNew.equals("1")){
                     action="NEW_ACTION";
                }
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                option = optProducer.getTransferOption(dto.getTransferType());
                dto.setTransferTypeOption(option);
            }
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            dto.setFaContentOption(option);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/system/rent/amsRentChangeQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                BaseSQLProducer sqlProducer = new AMSRentChangeModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/system/rent/amsRentChangeQuery.jsp";
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
                if (headerDTO == null) {
                    headerDTO = dto;
                }
                headerDTO.setTransNo("完成时自动生成"); //设置单据号
                headerDTO.setCreatedBy(user.getUserId()); //设置创建人
                headerDTO.setCreated(user.getUsername()); //设置创建人
                headerDTO.setFromOrganizationId(user.getOrganizationId());
                headerDTO.setCurrCreationDate();
                headerDTO.setTransType("SW-RED");
                headerDTO.setTransStatusDesc("未完成");
                headerDTO.setUserDeptName(user.getDeptName());
                headerDTO.setFromCompanyName(user.getCompany());
                headerDTO.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
                headerDTO.setEmail(user.getEmail());
                headerDTO.setPhoneNumber(user.getMobilePhone());
                headerDTO.setUserDeptName(user.getDeptName());
                String deptOptions = optProducer.getAllDeptOption("");
                String group = dto.getToGroupOption();
                String groupOp = op.getAllGroup3(group, user.getOrganizationId(), false, true);
                headerDTO.setToGroupOption(groupOp);
                headerDTO.setFromDeptOption(deptOptions);
                headerDTO.setToDeptOption(deptOptions);
                headerDTO.setServletConfig(servletConfig);
                headerDTO.setCalPattern(LINE_PATTERN);
                if(!headerId.equals("")){
                  headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                 headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                  AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                  req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                }
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                forwardURL = "/system/rent/amsRentChangeCreat.jsp";
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsAssetsTransHeaderDTO();
                }
                headerDTO.setCalPattern(LINE_PATTERN);
                AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (ds == null) {
                    ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                String deptOptions = optProducer.getAllDeptOption(  headerDTO.getToDept()  );
                String group = dto.getToGroupOption();
                String groupOp = op.getAllGroup3(headerDTO.getToGroup(), user.getOrganizationId(), false, true);
                headerDTO.setToGroupOption(groupOp);
                headerDTO.setFromDeptOption(deptOptions);
                headerDTO.setToDeptOption(deptOptions);

                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                forwardURL = "/system/rent/amsRentChangeCreat.jsp";
            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                r2.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                headerDAO.setDTOParameter(dto);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean Smsg = headerDAO.submitNewOrder(lineSet, flowDTO,sf_appFieldValue);
                if (Smsg) {
                    msg = "单据" + dto.getTransNo() + "提交成功！";
                    forwardURL = "/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet?act=" + AssetsActionConstant.QUERY_ACTION;
                } else {
                    msg = "单据处理失败！";
                    forwardURL = "/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet?act=" + AssetsActionConstant.NEW_ACTION;
                }
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsAssetsTransHeaderDTO();
                }
                headerDTO.setCalPattern(LINE_PATTERN);
                AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (ds == null) {
                    ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                String deptOptions = optProducer.getAllDeptOption(headerDTO.getToDept());
                String group = dto.getToGroupOption();
                String groupOp = op.getAllGroup3(headerDTO.getToGroup(), user.getOrganizationId(), false, true);
                headerDTO.setToGroupOption(groupOp);
                headerDTO.setFromDeptOption(deptOptions);
                headerDTO.setToDeptOption(deptOptions);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                forwardURL = "/system/rent/amsRentChangeApprove.jsp";
            } else if (action.equals(AssetsActionConstant.APPROVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                r2.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
//                List ignorList = new ArrayList();
//                ignorList.add("transId");
//                ignorList.add("transferType");
//                ignorList.add("oldResponsibilityUser");
//                ignorList.add("attribute2");
//                ignorList.add("itemSpec");
//                ignorList.add("itemName");
//                ignorList.add("transType");
//                r2.setIgnoreFields(ignorList);
                DTOSet lineSet = r2.getDTOSet(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
                String sectionRight = req.getParameter("sectionRight");
//                boolean isFlowToEnd

                String operateResult = headerDAO.approveNewOrder(lineSet, flowDTO, sectionRight,sf_appFieldValue,to_end);
                if (operateResult.equals("")) {
                    msg = "非资产调拨未成功！";
                    forwardURL = "/servlet/com.sino.flow.servlet.InboxServlet";
                } else {
                    msg = "非资产调拨成功！";
                    forwardURL = "/servlet/com.sino.flow.servlet.InboxServlet";
                }
                message = headerDAO.getMessage();
            } else if (action.equals(AssetsActionConstant.REJECT_ACTION)) {
                flowDTO.setSessionUserId(user.getUserId());
                String appName="非资产调拨流程";
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
                flowDTO.setApproveContent(content);
                headerDAO.reject(dto, flowDTO,appName,sf_appFieldValue);
                msg = "非资产调拨单已退回!";
                forwardURL = "/servlet/com.sino.flow.servlet.InboxServlet";
            }else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) {
                flowDTO.setSessionUserId(user.getUserId());
                String appName="非资产调拨流程";
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
                flowDTO.setApproveContent(content);
                headerDAO.cancle(dto, flowDTO,appName,sf_appFieldValue);
                msg = "非资产调拨单已撤销!";
                forwardURL = "/servlet/com.sino.flow.servlet.InboxServlet";
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
        } catch (ContainerException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {

			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}catch (DataHandleException ex) {

			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}catch (ParseException ex) {
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                if (msg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardParentView(forwardURL, msg);
                }

            }
        }
    }
}
