package com.sino.ams.spare.part.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.part.dao.AmsSpareCategoryDAO;
import com.sino.ams.spare.part.dto.AmsSpareCategoryDTO;
import com.sino.ams.spare.part.model.AmsSpareCategoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class AmsSpareCategoryServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsSpareCategoryDTO.class.getName());
            AmsSpareCategoryDTO dtoParameter = (AmsSpareCategoryDTO)req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsSpareCategoryDAO amsSpareCategoryDAO = new AmsSpareCategoryDAO(user, dtoParameter, conn);
            OptionProducer prd = new OptionProducer(user, conn);
            String vendorOption = prd.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoQuery.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsSpareCategoryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoQuery.jsp";
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = amsSpareCategoryDAO.exportFile();
				amsSpareCategoryDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
                dtoParameter.setNew("true");
                String itemUnitOpt = prd.getDictOption(DictConstant.UNIT_OF_MEASURE, dtoParameter.getItemUnit());
                req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoInfo.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsSpareCategoryDAO.setDTOClassName(AmsSpareCategoryDTO.class.getName());
                AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)amsSpareCategoryDAO.getDataByPrimaryKey();
                if(amsSpareCategory == null){
                    amsSpareCategory = new AmsSpareCategoryDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                vendorOption = prd.getSpareVendorOption(amsSpareCategory.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                String itemUnitOpt = prd.getDictOption(DictConstant.UNIT_OF_MEASURE, amsSpareCategory.getItemUnit());
                req.setAttribute(WebAttrConstant.ITEM_UNIT_OPTION, itemUnitOpt);
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, amsSpareCategory);
                forwardURL = "/spare/part/partNoInfo.jsp";
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                SeqProducer seqProducer = new SeqProducer(conn);
                String barcode = StrUtil.nullToString(seqProducer.getStrNextSeq("AMS_SPARE_CATEGORY"));
                dtoParameter.setBarcode(barcode);
                amsSpareCategoryDAO.createData();
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoQuery.jsp";
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsSpareCategoryDAO.updateData();
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoQuery.jsp";
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                amsSpareCategoryDAO.deleteData();
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, dtoParameter);
                forwardURL = "/spare/part/partNoQuery.jsp";
            } else if (action.equals("verifyworkNo")) {//验证该地点下是否存在设备
                boolean success = amsSpareCategoryDAO.doVerifyBarcode(dtoParameter);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
            }
            else {
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
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (WebFileDownException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}