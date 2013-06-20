package com.sino.ams.system.house.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.util.CalendarUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.dao.GetMisHousInfoDAO;
import com.sino.ams.system.house.model.GetMisHousInfoModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.AMSActionConstant;

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
 * User: Zyun
 * Date: 2008-5-29
 * Time: 14:02:58
 * Function:房屋土地信息维护.
 */
public class DispositonHouserServlet extends BaseServlet {

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
            GetMisHousInfoDAO getMisHousInfoDAO = new GetMisHousInfoDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String hasCertificate = prd.getBooleanOption(dtoParameter.getCertificate());
            req.setAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION, hasCertificate);
            String hasLandNo = prd.getBooleanOption(dtoParameter.getHasLandNo());
            req.setAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION, hasLandNo);
            if (action.equals("")) {
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                dtoParameter.setTemp("未处理");
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO,dtoParameter);
                forwardURL = "/system/house/dispositionHouseSearch.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                GetMisHousInfoModel sqlProducer = new GetMisHousInfoModel(user, dtoParameter);
                String isProvince = getMisHousInfoDAO.isProvince();
                dtoParameter.setProvince(isProvince);
                SQLModel sqlModel =  sqlProducer.getDispositionModel();
                WebPageView wpv= new WebPageView(req, conn);
                wpv.setPageSize(15);
                wpv.produceWebData(sqlModel);
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO,dtoParameter);
                forwardURL = "/system/house/dispositionHouseSearch.jsp";
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                if(getMisHousInfoDAO.isTempSave(dtoParameter.getBarcode())){
//                    forwardURL = "/system/house/chooseHouseCategory.jsp?barcode="+dtoParameter.getBarcode();
                }else{   //删除操作
                     getMisHousInfoDAO.deleteNullData();
                }
                AmsHouseUsesDTO dto=new AmsHouseUsesDTO();
                String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
                req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
                String status = prd.getDictOption(DictConstant.HOUSE_STATUS, dtoParameter.getHouseStatus());
                req.setAttribute(WebAttrConstant.HOUSE_STATUS_OPTION, status);
                String use = prd.getDictOption(DictConstant.HOUSE_USAGE, dtoParameter.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
                getMisHousInfoDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
                AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) getMisHousInfoDAO.getDataByPrimaryKey();
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
                req.setAttribute("AMSHOUSEUSESDTO",dto);
                dtoParameter.setItemCode(amsHouseInfo.getItemCode());  
                dtoParameter.setItemName(amsHouseInfo.getItemName());
                dtoParameter.setItemSpec(amsHouseInfo.getItemSpec());
                dtoParameter.setHouseAddress(amsHouseInfo.getHouseAddress());
                dtoParameter.setHremark(amsHouseInfo.getHremark());
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                if (dtoParameter.getBts().equals("基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/dispositionHouseInfo.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/dispositionJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/dispositionJU.jsp";
                    }
                } else if (dtoParameter.getBts().equals("非基站")) {
                    if (dtoParameter.getCategory().equals("房屋")) {
                        forwardURL = "/system/house/dispositionNJH.jsp";
                    } else if (dtoParameter.getCategory().equals("土地")) {
                        forwardURL = "/system/house/dispositionNJL.jsp";
                    } else if (dtoParameter.getCategory().equals("房地合一")) {
                        forwardURL = "/system/house/dispositionNJU.jsp";
                    }
                }
              } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) { //查询页面详细操作
                if(getMisHousInfoDAO.isTempSave(dtoParameter.getBarcode())){
                    forwardURL = "/system/house/chooseHouseCategory.jsp?barcode="+dtoParameter.getBarcode();
                }else{
                    forwardURL = "/system/house/choosedHLCategory.jsp?barcode=" +dtoParameter.getBarcode()+"&bts="+dtoParameter.getBts()+"&category="+dtoParameter.getCategory();
                }
              } else if (action.equals(WebActionConstant.DETAIL_ACTION)){  //房屋土地详细页面

                AmsHouseUsesDTO dto=new AmsHouseUsesDTO();
                String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
                req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
                String status = prd.getDictOption(DictConstant.HOUSE_STATUS, dtoParameter.getHouseStatus());
                req.setAttribute(WebAttrConstant.HOUSE_STATUS_OPTION, status);
                String use = prd.getDictOption(DictConstant.HOUSE_USAGE, dtoParameter.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
//                dtoParameter = new AmsHouseInfoDTO();
                getMisHousInfoDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
                AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) getMisHousInfoDAO.getDataByPrimaryKey();
                dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
                dtoParameter.setRentDate(CalendarUtil.getCurrDate());
//                req.setAttribute("AMSHOUSEUSESDTO",dto);
                dtoParameter.setItemCode(amsHouseInfo.getItemCode());
                dtoParameter.setItemName(amsHouseInfo.getItemName());
                dtoParameter.setItemSpec(amsHouseInfo.getItemSpec());
                dtoParameter.setHouseAddress(amsHouseInfo.getHouseAddress());
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);

//                amsHouseInfoDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
//                AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) amsHouseInfoDAO.getDataByPrimaryKey();
//                amsHouseInfo.setCalPattern(CalendarConstant.LINE_PATTERN);   //
                if (amsHouseInfo == null) {
                    amsHouseInfo = new AmsHouseInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String files = prd.getAttachFile(amsHouseInfo.getBarcode());
                req.setAttribute(WebAttrConstant.ATTACH_FILES, files);

                status = prd.getDictOption(DictConstant.HOUSE_STATUS, amsHouseInfo.getHouseStatus());
                use = prd.getDictOption(DictConstant.HOUSE_USAGE, amsHouseInfo.getHouseUsage());
                req.setAttribute(WebAttrConstant.HOUSE_STATUS_OPTION, status);
                req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
                req.setAttribute("AMSHOUSEUSESDTODETAIL",getMisHousInfoDAO.getUses());

                 amsHouseInfo.setTemp(dtoParameter.getTemp());

                 if((amsHouseInfo.getOfficeUsage().equals(""))&&(amsHouseInfo.getOfficeType().equals(""))){
                    amsHouseInfo.setOfficeUsage(dtoParameter.getBts());
                    amsHouseInfo.setOfficeType(dtoParameter.getCategory());
                 }

                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, amsHouseInfo);
                if (amsHouseInfo.getOfficeUsage().equals("基站")) {
                    if (amsHouseInfo.getOfficeType().equals("房屋")) {
                        forwardURL = "/system/house/dispositionHouseInfo.jsp";
                    } else if (amsHouseInfo.getOfficeType().equals("土地")) {
                        forwardURL = "/system/house/dispositionJL.jsp";
                    } else if (amsHouseInfo.getOfficeType().equals("房地合一")) {
                        forwardURL = "/system/house/dispositionJU.jsp";
                    }
                } else if (amsHouseInfo.getOfficeUsage().equals("非基站")) {
                    if (amsHouseInfo.getOfficeType().equals("房屋")) {
                        forwardURL = "/system/house/dispositionNJH.jsp";
                    } else if (amsHouseInfo.getOfficeType().equals("土地")) {
                        forwardURL = "/system/house/dispositionNJL.jsp";
                    } else if (amsHouseInfo.getOfficeType().equals("房地合一")) {
                        forwardURL = "/system/house/dispositionNJU.jsp";
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
                boolean operateResult = getMisHousInfoDAO.updateData(itemInfoDTO, fileDTO, filePaths ,lineSet);
                message = getMisHousInfoDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                    showMsg = "处理成功！";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.DispositonHouserServlet?act=QUERY_ACTION";
//                   forwardURL="/public/windowClose.jsp?retValue=order";
                } else {
                    String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
                    req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
                    String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
                    req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
                    req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
                    showMsg = "未处理成功！";
                    forwardURL = "/servlet/com.sino.ams.system.house.servlet.DispositonHouserServlet?act=QUERY_ACTION";
                }
            }  else if (action.equals(WebActionConstant.DELETE_ACTION)) {       //删除操作
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
//                boolean operateResult = getMisHousInfoDAO.deleteNullData(itemInfoDTO, fileDTO, filePaths ,lineSet);
                boolean operateResult = getMisHousInfoDAO.deleteNullData();
                message = getMisHousInfoDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                    req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                   showMsg = "删除成功！";
                   forwardURL = "/servlet/com.sino.ams.system.house.servlet.DispositonHouserServlet?act=QUERY_ACTION";
                   forwardURL="/public/windowClose.jsp?retValue=order";
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
                 String isProvince = getMisHousInfoDAO.isProvince();
                 dtoParameter.setProvince(isProvince);
	             File file = getMisHousInfoDAO.exportFileM();
	             getMisHousInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
	             WebFileDownload fileDown = new WebFileDownload(req, res);
	             fileDown.setFilePath(file.getAbsolutePath());
	             fileDown.download();
	             file.delete();
           	} else if (action.equals(AMSActionConstant.INSTEAD_ACTION)) {      //查询页面 重置ets_item_info的信息
				String[] barcodes = req.getParameterValues("barcodes");

                getMisHousInfoDAO.efficientData(barcodes);
				message = getMisHousInfoDAO.getMessage();

//                forwardURL = "/servlet/com.sino.ams.system.house.servlet.DispositonHouserServlet?act=";
                String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
                dtoParameter.setTemp("未处理");
                req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
                req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO,dtoParameter);
                forwardURL = "/system/house/dispositionHouseSearch.jsp";
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
