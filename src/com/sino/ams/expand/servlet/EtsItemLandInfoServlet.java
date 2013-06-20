package com.sino.ams.expand.servlet;


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
import com.sino.ams.expand.dao.EtsItemLandInfoDAO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.expand.dto.EtsItemLandInfoDTO;
import com.sino.ams.expand.model.EtsItemLandInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: EtsItemLandInfoServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemLandInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 张星
 * @version 1.0
 */


public class EtsItemLandInfoServlet extends BaseServlet {

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
            EtsItemLandInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemLandInfoDTO.class.getName());
            dtoParameter = (EtsItemLandInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EtsItemLandInfoDAO landInfoDAO = new EtsItemLandInfoDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String hasCertificate=prd.getBooleanOption(dtoParameter.getHasCertficate());
            req.setAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION,hasCertificate);
            if (action.equals("")) {
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.ETS_EX_LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EtsItemLandInfoModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.ETS_EX_LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {

                dtoParameter = new EtsItemLandInfoDTO();

//                dtoParameter.setBarcode(user.getCompanyCode()+'-');

//                dtoParameter.setBarcode(landInfoDAO.getOrderNum());

                String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, dtoParameter.getAreaUnit());
                req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
//				EtsItemLandInfoDTO amsLandInfo = (EtsItemLandInfoDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(amsLandInfo == null){
//					amsLandInfo= dtoParameter;
//				}

//                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, dtoParameter);
                forwardURL = URLDefineList.ETS_EX_LAND_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                landInfoDAO.setDTOClassName(EtsItemLandInfoDTO.class.getName());
                EtsItemLandInfoDTO amsLandInfo = (EtsItemLandInfoDTO) landInfoDAO.getDataByPrimaryKey();
                amsLandInfo.setCalPattern(CalendarConstant.LINE_PATTERN);
                if (amsLandInfo == null) {
                    amsLandInfo = new EtsItemLandInfoDTO();
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
                forwardURL = URLDefineList.ETS_EX_LAND_DETAIL_PAGE;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {

//                req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());

                AmsItemFilesDTO fileDTO= new AmsItemFilesDTO();
                String[]  filePaths= req.getParameterValues("affix");

                boolean operateResult = landInfoDAO.updateData(fileDTO,filePaths);
                
                System.out.println("\n============operateResult ============="+operateResult);
                
                message = landInfoDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                    forwardURL = URLDefineList.ETS_EX_LAND_QUERY_PAGE;
                } else {
                    String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                    req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                    String areaOption = prd.getDictOption(DictConstant.LAND_AREA_UNIT, dtoParameter.getAreaUnit());
                    req.setAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION, areaOption);
                    req.setAttribute(WebAttrConstant.AMS_LAND_INFO_DTO, dtoParameter);
                    forwardURL = URLDefineList.ETS_EX_LAND_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                boolean operateResult = true;
//                boolean operateResult = landInfoDAO.deleteData();
                landInfoDAO.deleteData();
                message = landInfoDAO.getMessage();
                message.setIsError(!operateResult);
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                forwardURL = URLDefineList.ETS_EX_LAND_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = landInfoDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("verifyBarcode")) {                                          //验证barcode是否存在
                String barcode = StrUtil.nullToString(req.getParameter("barcode"));
                boolean success = landInfoDAO.verifyBarcode(barcode);
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