package com.sino.ams.system.house.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dao.AmsLandInfoDAO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.dto.AmsLandInfoDTO;
import com.sino.ams.system.house.model.AmsLandInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsLandInfoServlet</p>
 * <p>Description:程序自动生成服务程序“AmsLandInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author Zyun
 * @version 1.0
 */


public class AmsLandInfoServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsLandInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsLandInfoDTO.class.getName());
            dtoParameter = (AmsLandInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsLandInfoDAO amsLandInfoDAO = new AmsLandInfoDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String hasCertificate=prd.getBooleanOption(dtoParameter.getHasCertficate());
            req.setAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION,hasCertificate);
            if (action.equals("")) {
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsLandInfoModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {

                dtoParameter = new AmsLandInfoDTO();

//                dtoParameter.setBarcode(user.getCompanyCode()+'-');

//                dtoParameter.setBarcode(amsLandInfoDAO.getOrderNum());

                String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, dtoParameter.getAreaUnit());
                req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
//				AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(amsLandInfo == null){
//					amsLandInfo= dtoParameter;
//				}

//                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, dtoParameter);
                forwardURL = URLDefineList.LAND_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsLandInfoDAO.setDTOClassName(AmsLandInfoDTO.class.getName());
                AmsLandInfoDTO amsLandInfo = (AmsLandInfoDTO) amsLandInfoDAO.getDataByPrimaryKey();
                amsLandInfo.setCalPattern(CalendarConstant.LINE_PATTERN);
                if (amsLandInfo == null) {
                    amsLandInfo = new AmsLandInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }

                 String files=prd.getAttachFile(amsLandInfo.getBarcode());
                 req.setAttribute(WebAttrConstant.ATTACH_FILES,files);

//                AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
//                fileDTO.setBarcode(dtoParameter.getBarcode());
//                AmsItemFilesDAO amsItemFilesDAO = new AmsItemFilesDAO(user, fileDTO, conn);
//                amsItemFilesDAO.setDTOClassName(AmsItemFilesDTO.class.getName());
//                DTOSet files = (DTOSet) amsItemFilesDAO.getDataByForeignKey("barcode");
//                req.setAttribute(WebAttrConstant.ATTACH_FILES, files);
//                amsLandInfo.setSystemId(dtoParameter.getSystemId());

                String payOption = prd.getDictOption(DictConstant.PAY_TYPE, amsLandInfo.getPayType());
                req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, amsLandInfo.getAreaUnit());
                req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
                amsLandInfo.setCalPattern(CalendarConstant.LINE_PATTERN);
                req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, amsLandInfo);
                forwardURL = URLDefineList.LAND_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {   //do_save操作
               String systemId = StrUtil.nullToString(req.getParameter("systemId"));
                req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
                EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);

                   String[]  filePaths= req.getParameterValues("affix");

                boolean operateResult = amsLandInfoDAO.createData(itemInfoDTO,filePaths);
//                 boolean operateResult = true;
//                 amsLandInfoDAO.createData(itemInfoDTO);
                message = amsLandInfoDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
//                    forwardURL = URLDefineList.LAND_DETAIL_SERVLET;
//                    forwardURL += "&barcode=" + dtoParameter.getBarcode();
//                    String isAttachFile = req.getParameter("isAttachFile");
//                    req.setAttribute(WebAttrConstant.ATTACH_FILE_ATTR, isAttachFile);
                    String isRent=prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION,isRent);
                     forwardURL = URLDefineList.LAND_QUERY_PAGE;
                } else {
                    String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                    req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                    String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, dtoParameter.getAreaUnit());
                    req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
                    req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, dtoParameter);
                    forwardURL = URLDefineList.LAND_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {

                req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
                EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);

                 AmsItemFilesDTO fileDTO= new AmsItemFilesDTO();
                  String[]  filePaths= req.getParameterValues("affix");

                boolean operateResult = amsLandInfoDAO.updateData(itemInfoDTO,fileDTO,filePaths);
//                amsLandInfoDAO.updateData(itemInfoDTO);
                message = amsLandInfoDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                    forwardURL = URLDefineList.LAND_QUERY_PAGE;
                } else {
                    String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                    req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                    String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, dtoParameter.getAreaUnit());
                    req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
                    req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, dtoParameter);
                    forwardURL = URLDefineList.LAND_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                boolean operateResult = true;
//                boolean operateResult = amsLandInfoDAO.deleteData();
                amsLandInfoDAO.deleteData();
                message = amsLandInfoDAO.getMessage();
                message.setIsError(!operateResult);
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = amsLandInfoDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("verifyBarcode")) {                                          //验证barcode是否存在
                String barcode = StrUtil.nullToString(req.getParameter("barcode"));
                boolean success = amsLandInfoDAO.verifyBarcode(barcode);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
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
        } catch (CalendarException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}