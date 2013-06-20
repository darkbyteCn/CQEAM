package com.sino.ams.system.house.servlet;

import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.util.CalendarUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.base.exception.*;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.dao.RentHouseDAO;
import com.sino.ams.system.house.model.RentHouseModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-14
 * Time: 18:16:03
 * Function:租赁房屋土地维护.
 */
public class RentHouseInfoServlet extends BaseServlet {
    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String showMsg = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsHouseInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsHouseInfoDTO.class.getName());
            dtoParameter = (AmsHouseInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            RentHouseDAO rentHouseDAO = new RentHouseDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String hasCertificate = prd.getBooleanOption(dtoParameter.getCertificate());
            req.setAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION, hasCertificate);
            String hasLandNo = prd.getBooleanOption(dtoParameter.getHasLandNo());
            req.setAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION, hasLandNo);
            if (action.equals("")) {
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                dtoParameter.setTemp("未处理");
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                forwardURL = "/system/house/rent/rentHouseSearch.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                RentHouseModel sqlProducer = new RentHouseModel(user, dtoParameter);
                String isProvince = rentHouseDAO.isProvince();
                dtoParameter.setProvince(isProvince);
                SQLModel sqlModel = sqlProducer.getDispositionModel();

                WebPageView wpv = new WebPageView(req, conn);
                wpv.setPageSize(15);
                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                wpv.produceWebData(sqlModel);
//                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
//                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                forwardURL = "/system/house/rent/rentHouseSearch.jsp";
            } else if(action.equals(WebActionConstant.CREATE_ACTION)){
//               String barcode= rentHouseDAO.doExport("ZL",1);
                String barcode = "完成时自动生成";
               forwardURL = "/system/house/rent/choosedCategory.jsp?barcode="+barcode;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsHouseUsesDTO dto = new AmsHouseUsesDTO();
                String use = prd.getDictOption(DictConstant.HOUSE_USAGE, dtoParameter.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
                rentHouseDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
                AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) rentHouseDAO.getDataByPrimaryKey();
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
                req.setAttribute("AMSHOUSEUSESDTO", dto);
                if(!StrUtil.isEmpty(amsHouseInfo)){
                dtoParameter.setItemCode(amsHouseInfo.getItemCode());
                dtoParameter.setItemName(amsHouseInfo.getItemName());
                dtoParameter.setItemSpec(amsHouseInfo.getItemSpec());
                }
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                if (dtoParameter.getBts().equals("基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/rent/rentJH.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/rent/rentJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/rent/rentJU.jsp";
                    }
                } else if (dtoParameter.getBts().equals("非基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/rent/rentNJH.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/rent/rentNJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/rent/rentNJU.jsp";
                    }
                }
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) { //查询页面详细操作
                    forwardURL = "/system/house/rent/chooseRentCategory.jsp?barcode=" + dtoParameter.getBarcode();
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {  //房屋土地详细页面

                AmsHouseUsesDTO dto = new AmsHouseUsesDTO();
                String use = prd.getDictOption(DictConstant.HOUSE_USAGE, dtoParameter.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
                rentHouseDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
                AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) rentHouseDAO.getDataByPrimaryKey();
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
                dtoParameter.setItemCode(amsHouseInfo.getItemCode());
                dtoParameter.setItemName(amsHouseInfo.getItemName());
                dtoParameter.setItemSpec(amsHouseInfo.getItemSpec());
                dtoParameter.setHouseAddress(amsHouseInfo.getHouseAddress());
                dtoParameter.setLandType(amsHouseInfo.getLandType());
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                if (amsHouseInfo == null) {
                    amsHouseInfo = new AmsHouseInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String files = prd.getAttachFile(amsHouseInfo.getBarcode());
                req.setAttribute(WebAttrConstant.ATTACH_FILES, files);
                use = prd.getDictOption(DictConstant.HOUSE_USAGE, amsHouseInfo.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
                req.setAttribute("AMSHOUSEUSESDTODETAIL", rentHouseDAO.getUses());
                amsHouseInfo.setTemp(dtoParameter.getTemp());
                if ((amsHouseInfo.getOfficeUsage().equals("")) && (amsHouseInfo.getOfficeType().equals(""))) {
                    amsHouseInfo.setOfficeUsage(dtoParameter.getBts());
                    amsHouseInfo.setOfficeType(dtoParameter.getCategory());
                }
                amsHouseInfo.setCalPattern(CalendarConstant.LINE_PATTERN);       
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, amsHouseInfo);
               if (dtoParameter.getBts().equals("基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/rent/rentJH.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/rent/rentJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/rent/rentJU.jsp";
                    }
                } else if (dtoParameter.getBts().equals("非基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/rent/rentNJH.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/rent/rentNJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/rent/rentNJU.jsp";
                    }
                }
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {       //修改操作
                req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
                EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);
                AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
                String[] filePaths = req.getParameterValues("affix");
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsHouseUsesDTO.class.getName());
                List ignoreList = new ArrayList();
                ignoreList.add("barcode");
                ignoreList.add("act");
                r2.setIgnoreFields(ignoreList);
                DTOSet lineSet = r2.getDTOSet(req);

                if(dtoParameter.getBarcode().equals("完成时自动生成")){
                  String barcode= rentHouseDAO.doExport("ZL",1);
                  dtoParameter.setBarcode(barcode);
                  itemInfoDTO.setBarcode(barcode);  
                  boolean operateResult = rentHouseDAO.updateData(itemInfoDTO, fileDTO, filePaths, lineSet);
                  message = rentHouseDAO.getMessage();
                  message.setIsError(!operateResult);
                 if (operateResult) {
                    showMsg = "操作成功！条码为："+dtoParameter.getBarcode()+"！";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
//                   forwardURL="/public/windowClose.jsp?retValue=order";
                    } else {
                        req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                        showMsg = "未处理成功！";
                        forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
                    }
                }else{
                boolean operateResult = rentHouseDAO.updateData(itemInfoDTO, fileDTO, filePaths, lineSet);
                message = rentHouseDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "信息修改成功！";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
//                   forwardURL="/public/windowClose.jsp?retValue=order";
                } else {
                    req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                    showMsg = "未处理成功！";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
                }
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {       //删除操作
                req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
                EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);
                AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
                String[] filePaths = req.getParameterValues("affix");
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsHouseUsesDTO.class.getName());
                List ignoreList = new ArrayList();
                ignoreList.add("barcode");
                ignoreList.add("act");
                r2.setIgnoreFields(ignoreList);
                DTOSet lineSet = r2.getDTOSet(req);
//                boolean operateResult = rentHouseDAO.deleteNullData(itemInfoDTO, fileDTO, filePaths ,lineSet);
                boolean operateResult = rentHouseDAO.deleteNullData();
                message = rentHouseDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                    showMsg = "删除成功！";
//                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?act=QUERY_ACTION";
                    forwardURL = "/public/windowClose.jsp?retValue=order";
                } else {
                    String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                    req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                    String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
                    req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
                    req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                    showMsg = "未处理成功！";
                    forwardURL = URLDefineList.HOUSE_DETAIL_PAGE;
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
                File file = rentHouseDAO.exportFileM();
                rentHouseDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
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
        } catch (CalendarException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
//        } catch (DataTransException ex) {
//            ex.printLog();
//            message = getMessage(MsgKeyConstant.COMMON_ERROR);
//            message.setIsError(true);
//            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                if (showMsg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
        }
    }

}
