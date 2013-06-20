package com.sino.nm.ams.mss.servlet;

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
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.house.dao.AmsHouseInfoDAO;
import com.sino.nm.ams.mss.dto.MssAssetsAddDTO;
import com.sino.nm.ams.mss.dto.MssAssetsAddLDTO;
import com.sino.nm.ams.mss.dao.MssAssetsAddDAO;
import com.sino.nm.ams.mss.dao.MssAssetsAddLDAO;
import com.sino.nm.ams.mss.model.MssAssetsAddModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.f1j.ss.BookModel;
import com.f1j.util.F1Exception;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:56:53
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddServlet extends BaseServlet {
     ServletOutputStream out = null;
    protected com.f1j.ss.BookModelImpl book = null;
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {

        String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			MssAssetsAddDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(MssAssetsAddDTO.class.getName());
			dtoParameter = (MssAssetsAddDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);

            MssAssetsAddDAO assetsAddDAO = new MssAssetsAddDAO(user, dtoParameter,
					conn);
            OptionProducer prd = new OptionProducer(user, conn);
            if (action.equals("")) {
				forwardURL = "/nm/mss/MssAssetsAdd.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) { //查询
				BaseSQLProducer sqlProducer = new MssAssetsAddModel(user,
						dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/nm/mss/MssAssetsAdd.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) { //新增
				MssAssetsAddDTO assetsAddH = (MssAssetsAddDTO) req.getAttribute(
						"ASSETS_HEADER");
				if (assetsAddH == null) {
					assetsAddH = dtoParameter;
				}
                String status = prd.getDictOption(DictConstant.HOUSE_STATUS, dtoParameter.getItemUsageStatus1());
                assetsAddH.setItemUsageStatusOp(status);
                 String officeUsage = prd.getDictOption("OFFICE_USAGE", dtoParameter.getItemUsage1());
                assetsAddH.setItemUsageOp(officeUsage);

                assetsAddH.setBillNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
				assetsAddH.setCreatedBy(user.getUserId());
				assetsAddH.setCreateUser(user.getUsername());
				assetsAddH.setCreatedDate(CalendarUtil.getCurrDate());
				assetsAddH.setStatus(0); // daizb 20111108
//				assetsAddH.setStatus("未完成");				
				req.setAttribute("ASSETS_HEADER", assetsAddH);
				forwardURL = "/nm/mss/MssAssetsAddDetail.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(MssAssetsAddLDTO.class.getName());
				r2.setIgnoreFields(MssAssetsAddDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
//                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = assetsAddDAO.submitData(lineSet);
				message = assetsAddDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL =
							"/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet?act=" +
							WebActionConstant.QUERY_ACTION;
					showMsg = "单据" + dtoParameter.getBillNo() + "已完成!";
				} else {
					forwardURL =
							"/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet?act=" +
							WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getHeadId();
				}
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) { //点save操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(MssAssetsAddLDTO.class.getName());
				r2.setIgnoreFields(MssAssetsAddDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
//                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = assetsAddDAO.saveData(lineSet);
				message = assetsAddDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL =
							"/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet?act=" +
							WebActionConstant.DETAIL_ACTION
							+ "&headId=" + dtoParameter.getHeadId();
				} else {
					req.setAttribute("ASSETS_HEADER", dtoParameter);
					forwardURL = "/nm/mss/MssAssetsAddDetail.jsp";
				}
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) { //点明细操作
				assetsAddDAO.setDTOClassName(MssAssetsAddDTO.class.getName());
				MssAssetsAddDTO assetsAdd = (MssAssetsAddDTO) assetsAddDAO.
										 getDataByPrimaryKey();
				if (assetsAdd == null) {
					assetsAdd = new MssAssetsAddDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("ASSETS_HEADER", assetsAdd);
				//查询行信息
				MssAssetsAddLDAO ldao = new MssAssetsAddLDAO(user, null, conn);
				req.setAttribute("ASSETS_LINES",
								 ldao.getLines(assetsAdd.getHeadId()));

                   String status = prd.getDictOption(DictConstant.HOUSE_STATUS, dtoParameter.getItemUsageStatus1());
                assetsAdd.setItemUsageStatusOp(status);
                 String officeUsage = prd.getDictOption("OFFICE_USAGE", dtoParameter.getItemUsage1());
                assetsAdd.setItemUsageOp(officeUsage);

                forwardURL = "/nm/mss/MssAssetsAddDetail.jsp";
			}else if(action.equals("ADRESS")){
             File file = assetsAddDAO.exportAdressFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }else if(action.equals("DEPT")){
                  File file = assetsAddDAO.exportDeptFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }else if(action.equals("ITEM")){
                   File file = assetsAddDAO.exportItmeFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出
				File file = assetsAddDAO.exportFile();
				assetsAddDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else if(action.equals("barcode")){
                AmsHouseInfoDAO    amsHouseInfoDAO=new AmsHouseInfoDAO(user,null,conn);
                int count=Integer.parseInt(req.getParameter("count"));
                String barcode="";
                for(int i=0;i<count;i++){
                  barcode += amsHouseInfoDAO.getOrderNum()+";";
                }
                res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print(barcode);
				out.flush();
				out.close();
            }else  if(action.equals("EXPORT_BARCODE")){
                out = res.getOutputStream();
                res.setContentType("application/vnd.ms-excel");
                String header = "attachment; filename=" + new String(("新增资产标签.xls").getBytes(), "iso8859-1");
                res.setHeader("Content-Disposition", header);
                int count = Integer.parseInt(req.getParameter("count"));
                String barcodeList = req.getParameter("barcodeList");
                String[] barcodeList1 = barcodeList.split(";");
                String itemNameList = req.getParameter("itemNameList");
                String[] itemNameList1 = itemNameList.split(";");
                String itemSpecList = req.getParameter("itemSpecList");
                String[] itemSpecList1 = itemSpecList.split(";");
                book = new com.f1j.ss.BookModelImpl();
                book.initWorkbook();
                book.getLock();
                book.setBorder(true);
                book.setText(0, 0, "资产编号");
                book.setText(0, 1, "资产条码");
                book.setText(0, 2, "资产名称");
                book.setText(0, 3, "资产型号");
                book.setText(0, 4, "启用日期");
                for (int i = 0; i < count; i++) {

                    book.setText(i, 0, String.valueOf(i));
                    book.setText(i, 1, barcodeList1[i]);
                    book.setText(i, 2, itemNameList1[i]);
                    book.setText(i, 3, itemSpecList1[i]);
                    book.setText(i, 4, CalendarUtil.getCurrDate());
                }
                book.setColWidthAuto(0, 0, count, 5, true);
                book.recalc();
                book.write(out, new com.f1j.ss.WriteParams(BookModel.eFileExcel97));
                out.flush();
                out.close();
            }else if(action.equals("EXPORT_BARCODE_OK")){
               File file = assetsAddDAO.exportBarcodeFile();
				assetsAddDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
            }else {
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
		} catch (CalendarException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}catch (F1Exception e) {
            Logger.logError(e);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, showMsg);
			}
			//根据实际情况修改页面跳转代码。
		}
	}
}