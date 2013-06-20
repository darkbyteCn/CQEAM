package com.sino.ams.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import com.f1j.ss.BookModel;
import com.f1j.util.F1Exception;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.mis.srv.assetTagNumber.dto.SBFIFAInquiryAssetTagNumberDTO;
import com.sino.soa.mis.srv.assetTagNumber.srv.SBFIFAInquiryAssetTagNumberSrv;
import com.sino.soa.td.srv.assetTagNumber.dto.SBFIFATdInquiryAssetTagNumberDTO;
import com.sino.soa.td.srv.assetTagNumber.srv.SBFIFATdInquiryAssetTagNumberSrv;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-30
 */
public class BarcodePrintServlet extends BaseServlet {
    private SfUserDTO user = null;
    private Connection conn = null;
    ServletOutputStream out = null;
    String showMsg = "";
    protected com.f1j.ss.BookModelImpl book = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        user = (SfUserDTO) SessionUtil.getUserAccount(req);
        boolean success = false;

        String quantity = StrUtil.nullToString(req.getParameter("quantity"));
        String assetsType = StrUtil.nullToString(req.getParameter("assetType"));
        String act = StrUtil.nullToString(req.getParameter("act"));
        ServletConfigDTO serv = getServletConfig(req);
        boolean sxProvince = serv.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN);
        try {
        	conn = getDBConnection(req);
        	//栏目定义标头
			ResUtil.setAllResName(conn, req, ResNameConstant.BARCODE_PRINT );
			
            if (act.equals("")) {
                forwardURL = "/system/barcodePrint.jsp";
            } else if (act.equals(WebActionConstant.EXPORT_ACTION)) {
                res.setContentType("application/vnd.ms-excel");
                String header = "attachment; filename=" + new String(("NewBarcode.xls").getBytes(), "iso8859-1");
                res.setHeader("Content-Disposition", header);
                out = res.getOutputStream(); 
                success = doExport(assetsType, Integer.parseInt(quantity));
                if (success) {
                    out.flush();
                    out.close();
                } else {
                    forwardURL = "/system/barcodePrint.jsp";
                }
                req.setAttribute("showMsg", showMsg);
            }
        } catch (PoolPassivateException e) {
            Logger.logError(e);
        } catch (QueryException e) {
        	Logger.logError(e);
		} catch (ContainerException e) {
			Logger.logError(e);
		} finally {
            DBManager.closeDBConnection(conn);
            ServletForwarder sf = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                sf.forwardView(forwardURL);
            }
        }
    }

    /**
     * 导出标签列表至Excel
     * @param assetsType 标签类型
     * @param quantity   标签数量
     * @return boolean
     */
    private boolean doExport(String assetsType, int quantity) {
        boolean success = false;
        book = new com.f1j.ss.BookModelImpl();
        try {
            int firstAssetNumber = 0;
            String bookTypeCode = user.getBookTypeCode();
            String companyCode = user.getCompanyCode();
            String barcodePrefix = bookTypeCode.substring(bookTypeCode.length() - 4);
            firstAssetNumber = GenBarcode.getAssetTagNumber(conn, barcodePrefix, assetsType, quantity);
            book.initWorkbook();
            book.getLock();
            book.setBorder(true);
            String barcode = "";
            book.setText(0, 0, "资产编号");
            book.setText(0, 1, "资产标签号");
            if (assetsType.equals("")) {
                DTOSet ds = new DTOSet();
                if (user.getIsTd().equals("Y")) {
                    ds = genNewBarcodesOfTD(bookTypeCode, companyCode, quantity);
                    if (ds.getSize() > 0) {
                        for (int i = 0; i < ds.getSize(); i++) {
                            SBFIFATdInquiryAssetTagNumberDTO dto = (SBFIFATdInquiryAssetTagNumberDTO)ds.getDTO(i);
                            String barCode = dto.getTagNumber();
                            book.setText(i, 0, String.valueOf(i));
                            book.setText(i, 1, barCode);
                        }
                    }
                } else {
                    ds = genNewBarcodes(bookTypeCode, companyCode, quantity);
                    if (ds.getSize() > 0) {
                        for (int i = 0; i < ds.getSize(); i++) {
                            SBFIFAInquiryAssetTagNumberDTO dto = (SBFIFAInquiryAssetTagNumberDTO)ds.getDTO(i);
                            String barCode = dto.getTagNumber();
                            book.setText(i, 0, String.valueOf(i));
                            book.setText(i, 1, barCode);
                        }
                    }
                }
            } else {
                for (int i = 1; i <= quantity; i++) {
                    DecimalFormat df = new DecimalFormat("00000000");
                    if (assetsType.equals("MIS")) {
                        barcode = barcodePrefix + "-" + df.format(firstAssetNumber);
                    } else if (assetsType.equals("TD") || assetsType.equals("TT")) {
                        df = new DecimalFormat("00000000");
                        barcode = barcodePrefix + "-"  + df.format(firstAssetNumber);
                    } else {
                        df = new DecimalFormat("000000");
                        barcode = barcodePrefix + "-" + assetsType + df.format(firstAssetNumber);
                    }
                    book.setText(i, 0, String.valueOf(i));
                    book.setText(i, 1, barcode);
                    firstAssetNumber++;
                }
            }

            book.setColWidthAuto(0, 0, quantity, 2, true);
            book.recalc();
            book.write(out, new com.f1j.ss.WriteParams(BookModel.eFileExcel97));
            out.close();

            success = true;
        } catch (SQLException e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        } catch (IOException e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        } catch (F1Exception e) {
            Logger.logError(e);
            showMsg = e.getMessage();
        }

        return success;
    }

    private DTOSet genNewBarcodesOfTD(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFATdInquiryAssetTagNumberSrv srv = new SBFIFATdInquiryAssetTagNumberSrv();
        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }
        return ds;
    }

    private DTOSet genNewBarcodes(String bookTypeCode, String companyCode, int count) {
        DTOSet ds = new DTOSet();
        SBFIFAInquiryAssetTagNumberSrv srv = new SBFIFAInquiryAssetTagNumberSrv();
        try {
            srv.setBookTypeCode(bookTypeCode);
            srv.setAccount(count);
            srv.setSegment1(companyCode);
            srv.excute();
            if (srv.getReturnMessage().getErrorFlag().equalsIgnoreCase("Y")) {
                ds = srv.getDs();
            } else {
                Logger.logError(srv.getReturnMessage().getErrorMessage());
            }
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        }
        return ds;
    }

}
