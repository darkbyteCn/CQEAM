package com.sino.soa.td.srv.assetretire.servlet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.common.SrvType;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ErrorItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ImportAssetRetirmentSrvInputItem;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv.ResponseItem;
import com.sino.soa.td.srv.assetretire.dao.TDAssetRetirementDAO;
import com.sino.soa.td.srv.assetretire.dto.TDAssetRetirementDTO;
import com.sino.soa.td.srv.assetretire.model.TDAssetRetirementModel;
import com.sino.soa.td.srv.assetretire.srv.TDImportAssetRetirmentSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.XMLGregorianCalendarUtil;
import com.sino.soa.util.dto.EtsMisfaTransactionRespDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.SynLogDTO;

/**
 * User: wangzhipeng
 * Date: 2011-09-08
 * Function:资产报废(SOA)_TD
 */

public class TDImportAssetRetirmentServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/srv/assetRetire/SynAssetRetireTd.jsp";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TDAssetRetirementDTO.class.getName());
            TDAssetRetirementDTO dto = (TDAssetRetirementDTO) req2DTO.getDTO(req);
            TDAssetRetirementDAO dao = new TDAssetRetirementDAO(user, dto, conn);
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String orgOption = optionProducer.getSynOrgnizationOption(dto.getOrganizationId(), "Y");     //获取TD组织:Y (此处用于区分是否TD资产)
            req.setAttribute("OU_OPTION", orgOption);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) { //查询操作
                BaseSQLProducer sqlProducer = new TDAssetRetirementModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(servletConfig);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("SYSTEMID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
                File file = dao.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) { //同步操作
                long startTime = System.currentTimeMillis();
                SynLogUtil logUtil = new SynLogUtil();
                SynLogDTO logDTO = null;
                EtsMisfaUpdateBatchDTO updateBatchDTO = null;
                List<ImportAssetRetirmentSrvInputItem> inputItemList = new ArrayList<ImportAssetRetirmentSrvInputItem>();
                ImportAssetRetirmentSrvInputItem inputItem = null;
                String[] systemId = req.getParameterValues("systemId");    //报废资产信息
                EtsMisfaTransactionRespDTO rsRespDTO = logUtil.getMisfaResp(user.getOrganizationId(), user.getEmployeeNumber(), conn);
                if (rsRespDTO == null) {
                    String msgValue = "请设置同步用户后再操作！";
                    message = new Message();
                    message.setMessageValue(msgValue);
                    message.setIsError(true);
                } else {
                    if (systemId != null) {
                        RowSet rs = dao.getRetirementAssets(systemId);
                        logDTO = new SynLogDTO();
                        logDTO.setSynType(SrvType.SRV_TD_IMP_RETIRE);
                        logDTO.setCreatedBy(user.getUserId());
                        logDTO.setSynMsg("报废TD资产同步开始！");
                        logUtil.synLog(logDTO, conn);
                        SeqProducer sp = new SeqProducer(conn);
                        String batchSeq = sp.getGUID();
                        updateBatchDTO = new EtsMisfaUpdateBatchDTO();
                        updateBatchDTO.setBatchId(batchSeq);
                        updateBatchDTO.setOrganizationId(user.getOrganizationId());
                        updateBatchDTO.setCreatedBy(user.getUserId());
//                    updateBatchDTO.setTransStatus(2); //唐明胜注释，
                        updateBatchDTO.setTransStatus(1);//唐明胜添加，1标示正在同步
                        updateBatchDTO.setTransType(SrvType.SRV_TD_IMP_RETIRE);
                        updateBatchDTO.setRemark("本次共同步" + rs.getSize() + "条");
                        logUtil.createMisUpdateBatch(updateBatchDTO, conn);
//                    dao.logRetireAssets(systemId, batchSeq);//唐明胜注释，日志记录放到调用SOA服务之后
                        for (int i = 0; i < rs.getSize(); i++) {
                            Row row = rs.getRow(i);
                            inputItem = new ImportAssetRetirmentSrvInputItem();
                            inputItem.setPRIKEY(row.getStrValue("BARCODE"));
                            inputItem.setBOOKTYPECODE(row.getStrValue("BOOK_TYPE_CODE"));
                            inputItem.setTAGNUMBER(row.getStrValue("TAG_NUMBER"));
                            inputItem.setDATERRETIRED(XMLGregorianCalendarUtil.getXMLGregorianCalendar(row.getStrValue("DATE_RRETIRED")));
//                        inputItem.setRETIREMENTTYPECODE(row.getStrValue("REJECT_TYPE"));//NORMAL、TRANSFER 报废类型
                            inputItem.setRETIREMENTTYPECODE("");//NORMAL、TRANSFER 报废类型
                            inputItem.setCURRENTCOST(new BigDecimal(row.getStrValue("COST")).setScale(2, BigDecimal.ROUND_HALF_UP));
                            inputItem.setRETIREMENTCOST(new BigDecimal(row.getStrValue("COST")).setScale(2, BigDecimal.ROUND_HALF_UP));
                            inputItem.setCREATEDBY(rsRespDTO.getUserId());
//                        inputItem.setEMPLOYEENUMBER(rsRespDTO.getEmployeeNumber());
                            inputItemList.add(inputItem);
                        }
                        TDImportAssetRetirmentSrv srv = new TDImportAssetRetirmentSrv();
                        srv.setSrvInputItems(inputItemList);
                        srv.excute();
                        dao.logRetireAssets(systemId, batchSeq);//唐明胜添加，日志记录放到调用SOA服务之后
                        message = new Message();
                        String msgValue = "";
                        if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                            updateBatchDTO.setRemark("本次共同步'TD资产报废'" + rs.getSize() + "条,全部同步成功!");
                            updateBatchDTO.setErrmsg("");
//                    updateBatchDTO.setTransStatus(2); //唐明胜注释，
                            updateBatchDTO.setTransStatus(1);//唐明胜添加，1标示正在同步
                            logUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            List<ResponseItem> responseItemList = srv.getResponseItemList();
                            dao.updateResponseLog(batchSeq);
                            msgValue = "导入TD资产报废成功，耗时" + (System.currentTimeMillis() - startTime) + "毫秒。";
                        } else {
                            updateBatchDTO.setTransStatus(2);
                            updateBatchDTO.setErrmsg("同步失败，请点击行信息查看具体报错信息");
                            updateBatchDTO.setRemark("本次共同步'TD资产报废'" + rs.getSize() + "条,同步失败,失败信息:"+srv.getReturnMessage().getErrorMessage());
                            logUtil.updateMisUpdateBach(updateBatchDTO, conn);
                            List<ErrorItem> errorItemList = srv.getErrorItemList();
                            if (errorItemList != null && errorItemList.size() > 0) {
                                dao.updateErrorLog(errorItemList, batchSeq);
                            }
                            msgValue = "导入TD资产报废失败，耗时" + (System.currentTimeMillis() - startTime) + "毫秒。";
                            //导入TD资产报废成功，耗时

                            //如果同步失败的记录数少于同步记录数，上述日志记录全部记录为失败，则再次查询时将查询出本已同步成功的记录，再次同步就会报错
                        }
                        message.setMessageValue(msgValue);
                        logDTO = new SynLogDTO();
                        logDTO.setSynType(SrvType.SRV_TD_IMP_RETIRE);
                        logDTO.setCreatedBy(user.getUserId());
                        logDTO.setSynMsg("报废TD资产同步结束！");
                        logUtil.synLog(logDTO, conn);
                    }
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
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
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (SQLModelException e1) {
            e1.printStackTrace();
        } catch (WebFileDownException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}

