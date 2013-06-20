package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f1j.ss.BookModel;
import com.f1j.util.F1Exception;
import com.sino.ams.appbase.service.BarCodeService;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.dao.AssetsAddDAO;
import com.sino.ams.newasset.dao.AssetsAddLDAO;
import com.sino.ams.newasset.dto.AssetsAddDTO;
import com.sino.ams.newasset.dto.AssetsAddLDTO;
import com.sino.ams.newasset.model.AssetsAddModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsItemDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author ai
 * @date: 2008-3-13
 * 新增管理资产
 */
public class AssetsAddServlet extends BaseServlet {
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
			AssetsAddDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AssetsAddDTO.class.getName());
			dtoParameter = (AssetsAddDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AssetsAddDAO assetsAddDAO = new AssetsAddDAO(user, dtoParameter,
					conn);
			if (action.equals("")) {
				forwardURL = "/newasset/assetsAdd.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) { //查询
				BaseSQLProducer sqlProducer = new AssetsAddModel(user,
						dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/newasset/assetsAdd.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) { //新增
				AssetsAddDTO assetsAddH = (AssetsAddDTO) req.getAttribute("ASSETS_HEADER");
				if (assetsAddH == null) {
					assetsAddH = dtoParameter;
				}
				assetsAddH.setBillNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
				assetsAddH.setCreatedBy(user.getUserId());
				assetsAddH.setCreateUser(user.getUsername());
				assetsAddH.setCreatedDate(CalendarUtil.getCurrDate());
				assetsAddH.setStatus("未完成");
				
				AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
				String specialityDept = StrUtil.nullToString(dtoParameter.getSpecialityDept());
		        String specialityDeptOptions = "";
		        specialityDeptOptions = optProducer.getSpecialAsssetsDeptOption(specialityDept);
		        dtoParameter.setSpecialityDeptOption(specialityDeptOptions);
				
				req.setAttribute("ASSETS_HEADER", assetsAddH);
				forwardURL = "/newasset/assetsAddDetail.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AssetsAddLDTO.class.getName());
				r2.setIgnoreFields(AssetsAddDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				
				String specDeptL = dtoParameter.getSpecialityDept();
				AssetsAddLDAO lineDAO = new AssetsAddLDAO(user, null, conn);
				for (int i = 0; i < lineSet.getSize(); i++) {
					AssetsAddLDTO lineData = (AssetsAddLDTO) lineSet.getDTO(i);
					lineData.setSpecialityDept(specDeptL);
					lineDAO.setDTOParameter(lineData);
				}	
				
				//FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				//flowDTO.setSessionUserId(user.getUserId());
				//flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = assetsAddDAO.submitData(lineSet);
				message = assetsAddDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?act=" + WebActionConstant.QUERY_ACTION;
					showMsg = "单据" + dtoParameter.getBillNo() + "已完成!";
				} else {
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getHeadId();
				}
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) { //点save操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AssetsAddLDTO.class.getName());
				r2.setIgnoreFields(AssetsAddDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				
				String specDeptL = dtoParameter.getSpecialityDept();
				AssetsAddLDAO lineDAO = new AssetsAddLDAO(user, null, conn);
				for (int i = 0; i < lineSet.getSize(); i++) {
					AssetsAddLDTO lineData = (AssetsAddLDTO) lineSet.getDTO(i);
					lineData.setSpecialityDept(specDeptL);
					lineDAO.setDTOParameter(lineData);
				}		
				
				//FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				//flowDTO.setSessionUserId(user.getUserId());
				//flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = assetsAddDAO.saveData(lineSet);
				message = assetsAddDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&headId=" + dtoParameter.getHeadId();
				} else {
					req.setAttribute("ASSETS_HEADER", dtoParameter);
					forwardURL = "/newasset/assetsAddDetail.jsp";
				}
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) { //点明细操作
				assetsAddDAO.setDTOClassName(AssetsAddDTO.class.getName());
				AssetsAddDTO assetsAdd = (AssetsAddDTO) assetsAddDAO.getDataByPrimaryKey();
				if (assetsAdd == null) {
					assetsAdd = new AssetsAddDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				
				if (assetsAdd.getSpecialityDept().equals("")) {
					AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
					String specialityDept = StrUtil.nullToString(dtoParameter.getSpecialityDept());
					String specialityDeptOptions = "";
			        specialityDeptOptions = optProducer.getSpecialAsssetsDeptOption(specialityDept);
			        assetsAdd.setSpecialityDeptOption(specialityDeptOptions);
				}
		        
				req.setAttribute("ASSETS_HEADER", assetsAdd);
				//查询行信息
				AssetsAddLDAO ldao = new AssetsAddLDAO(user, null, conn);
				req.setAttribute("ASSETS_LINES", ldao.getLines(assetsAdd.getHeadId()));
				forwardURL = "/newasset/assetsAddDetail.jsp";
			} else if(action.equals("ADRESS")){
				File file = assetsAddDAO.exportAdressFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();   
            } else if(action.equals("DEPT")){
                File file = assetsAddDAO.exportDeptFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if(action.equals("ITEM")){
                File file = assetsAddDAO.exportItmeFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出
				File file = assetsAddDAO.exportFile();
				assetsAddDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
            } else  if(action.equals("PRINT_BARCODE")){

                Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AssetsAddLDTO.class.getName());
				r2.setIgnoreFields(AssetsAddDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
                   String itemNameList = req.getParameter("itemNameList");
                String[] itemNameList1 = itemNameList.split(";");
                String itemSpecList = req.getParameter("itemSpecList");
                String[] itemSpecList1 = itemSpecList.split(";");
                   DTOSet retDTOSet = this.handleLines(lineSet,itemNameList1, itemSpecList1,
                            req);
                    req.setAttribute(QueryConstant.QUERY_DTO, retDTOSet);
                   forwardURL = "/newasset/assetsaddPrintDetail.jsp";


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
        }  finally {
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
     private DTOSet handleLines(DTOSet lines,String []itemNameList1, String[]itemSpecList1,
                               HttpServletRequest req) throws IOException, DTOException {
        DTOSet retDTOSet = new DTOSet();
        BarCodeService service = new BarCodeService(req);

        AssetsAddLDTO line = null;
        String barcodeImg = null;
        for (int i = 0; i < lines.getSize(); i++) {
            line = (AssetsAddLDTO) lines.getDTO(i);

                barcodeImg = service.createScancodePic(line.getBarcode());
                line.setBarcodeImg(barcodeImg);
            line.setItemName(itemNameList1[i]);
            line.setItemSpec(itemSpecList1[i]);
                retDTOSet.addDTO(line);

        }
        return retDTOSet;
    }
}
