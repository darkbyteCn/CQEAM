package com.sino.ams.newasset.servlet;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemAllocationHeaderDAO;
import com.sino.ams.newasset.dao.AmsItemAllocationLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.ItemOrderQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ItemOrderQueryServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String action = "";
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            dto.setServletConfig(getServletConfig(req));
            action = dto.getAct();
            dto.setCalPattern(LINE_PATTERN);
            conn = getDBConnection(req);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            dto = optProducer.fillTransStatus(dto);
            String transType = dto.getTransType();
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                String option = optProducer.getTransferOption(dto.getTransferType());
                dto.setTransferTypeOption(option);
            }
            String statusOpt = optProducer.getDictOption(AssetsDictConstant.ORDER_STATUS, dto.getTransStatus());
            dto.setStatusOption(statusOpt);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL ="/newasset/itemOrderQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new ItemOrderQueryModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
              forwardURL ="/newasset/itemOrderQuery.jsp";
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = exportFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals(AssetsActionConstant.EPT_DTL_ACTION)) {//导出调拨单明细信息
                export(req, res, dto, user, conn);
                return;
            } else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
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
        } finally {
            closeDBConnection(conn);
            if (!action.equals(AssetsActionConstant.EPT_DTL_ACTION)) {
                setHandleMessage(req, message);
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }

    /**
     * 导出模版
     * @param
     * @param
     * @param
     * @param
     * @return
     */

    public void export(HttpServletRequest req, HttpServletResponse res, AmsAssetsTransHeaderDTO dto,
                       SfUserDTO user, Connection conn) {
        InputStream ins = null;// 输入流对象
        HSSFWorkbook mcBook = null;// 可写工作薄对象
        HSSFSheet mcSheet = null;// //可写工作表对象
        String opartor = System.getProperty("file.separator");
        dto.setTransId(req.getParameter("transId"));
        try {
            BaseSQLProducer sqlProducer = new ItemOrderQueryModel(user, dto);
            SQLModel sqlModel = sqlProducer.getPrimaryKeyDataModel();
            AmsItemAllocationHeaderDAO headerDAO = new AmsItemAllocationHeaderDAO(user, dto, conn);
            headerDAO.setServletConfig(getServletConfig(req));
            headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
            String transType = StrUtil.nullToString(dto.getTransType());
            String sheetName = "";
            String modelName = "";
            int rowIndex = 6;
            if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {//公司间调拨
                modelName = "export.xls";
                rowIndex = 7;
            } else if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {//部门间调拨
                modelName = "export2.xls";
            } else if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)) {//部门内调拨
                modelName = "export1.xls";
            }
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                sheetName = "调拨单据表";
            }
            this.beforeExport(req, res, modelName);
            ins = new FileInputStream(this.getFile(req, modelName, opartor));// 读取模版XLS文件
            mcBook = new HSSFWorkbook(ins); // NEW一个工作簿对象
            mcSheet = mcBook.getSheetAt(0);// 得到第一个工作表
            mcBook.setSheetName(0, sheetName);
            if (headerDTO != null) {
                HSSFRow row = null;
                HSSFCell cell = null;
                mcSheet.getRow(1).createCell((short) 1).setCellValue(headerDTO.getTransNo());
                mcSheet.getRow(1).createCell((short) 3).setCellValue(headerDTO.getTransTypeValue());
                if (!headerDTO.getCreationDate().getCalendarValue().equals("")) {
                    SimpleCalendar sc = headerDTO.getCreationDate();
                    mcSheet.getRow(1).createCell((short) 5).setCellValue(sc.getCalendarValue().substring(0, 10));
                }
                mcSheet.getRow(1).createCell((short) 7).setCellValue(headerDTO.getFromGroupName());

                mcSheet.getRow(2).createCell((short) 1).setCellValue(headerDTO.getCreated());
                mcSheet.getRow(2).createCell((short) 3).setCellValue(headerDTO.getFromCompanyName());
                mcSheet.getRow(2).createCell((short) 5).setCellValue(headerDTO.getUserDeptName());
                mcSheet.getRow(2).createCell((short) 7).setCellValue(headerDTO.getBookTypeName());

                mcSheet.getRow(3).createCell((short) 1).setCellValue(headerDTO.getPhoneNumber());
                mcSheet.getRow(3).createCell((short) 3).setCellValue(headerDTO.getEmail());

                if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {//公司间调拨
                    mcSheet.getRow(3).createCell((short) 5).setCellValue(headerDTO.getFromCompanyName());
                    mcSheet.getRow(3).createCell((short) 7).setCellValue(headerDTO.getToCompanyName());
                    mcSheet.getRow(4).createCell((short) 1).setCellValue(headerDTO.getFaContentName());
                    mcSheet.getRow(4).createCell((short) 3).setCellValue(headerDTO.getAccessSheet());
                    mcSheet.getRow(4).createCell((short) 5).setCellValue(headerDTO.getCreatedReason());
                } else if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT) || headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {//部门内调拨或者部门间调拨
                    mcSheet.getRow(3).createCell((short) 5).setCellValue(headerDTO.getFromDeptName());
                    mcSheet.getRow(3).createCell((short) 7).setCellValue(headerDTO.getFaContentName());
                    mcSheet.getRow(4).createCell((short) 1).setCellValue(headerDTO.getCreatedReason());
                }

                AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                lineDTO.setTransType(headerDTO.getTransType());
                AmsItemAllocationLineDAO lineDAO = new AmsItemAllocationLineDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (ds == null) {
                    ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                if (ds != null && !ds.isEmpty()) {
                    int size = ds.getSize();
                    for (int i = 0; i < size; i++) {
                        lineDTO = (AmsAssetsTransLineDTO) ds.getDTO(i);
                        row = mcSheet.createRow(rowIndex);
                        if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {//公司间调拨
                            cell = row.createCell((short) 0);
                            cell.setCellValue(lineDTO.getBarcode());
                            cell = row.createCell((short) 1);
                            cell.setCellValue(lineDTO.getNewBarcode());
                            cell = row.createCell((short) 2);
                            cell.setCellValue(lineDTO.getAssetsDescription());
                            cell = row.createCell((short) 3);
                            cell.setCellValue(lineDTO.getModelNumber());
                            cell = row.createCell((short) 4);
                            cell.setCellValue(lineDTO.getCurrentUnits());
                            cell = row.createCell((short) 5);
                            cell.setCellValue(lineDTO.getCost());
                            cell = row.createCell((short) 6);
                            cell.setCellValue(lineDTO.getDepreciation());
                            cell = row.createCell((short) 7);
                            cell.setCellValue(lineDTO.getImpairReserve());
                            cell = row.createCell((short) 8);
                            cell.setCellValue(lineDTO.getScrapValue());
                            cell = row.createCell((short) 9);
                            cell.setCellValue(lineDTO.getDeprnCost());
                            cell = row.createCell((short) 10);
                            cell.setCellValue(lineDTO.getManufacturerName());
                            if (!lineDTO.getDatePlacedInService().getCalendarValue().equals("")) {
                                cell = row.createCell((short) 11);
                                cell.setCellValue(lineDTO.getDatePlacedInService().getCalendarValue().substring(0, 10));
                            }
                            cell = row.createCell((short) 12);
                            cell.setCellValue(lineDTO.getOldResponsibilityDeptName());
                            cell = row.createCell((short) 13);
                            cell.setCellValue(lineDTO.getOldLocationName());
                            cell = row.createCell((short) 14);
                            cell.setCellValue(lineDTO.getOldResponsibilityUserName());
                            cell = row.createCell((short) 15);
                            cell.setCellValue(lineDTO.getOldDepreciationAccount());
                            cell = row.createCell((short) 16);
                            cell.setCellValue(lineDTO.getOldFaCategoryCode());
                            cell = row.createCell((short) 17);
                            cell.setCellValue(lineDTO.getResponsibilityDeptName());
                            cell = row.createCell((short) 18);
                            cell.setCellValue(lineDTO.getAssignedToLocationName());
                            cell = row.createCell((short) 19);
                            cell.setCellValue(lineDTO.getResponsibilityUserName());
                            cell = row.createCell((short) 20);
                            cell.setCellValue(lineDTO.getDepreciationAccount());
                            if (!lineDTO.getLineTransDate().getCalendarValue().equals("")) {
                                cell = row.createCell((short) 21);
                                cell.setCellValue(lineDTO.getLineTransDate().getCalendarValue().substring(0, 10));
                            }
                            cell = row.createCell((short) 22);
                            cell.setCellValue(lineDTO.getRemark());
                        } else if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)) {//部门内调拨
                            cell = row.createCell((short) 0);
                            cell.setCellValue(lineDTO.getBarcode());
                            cell = row.createCell((short) 1);
                            cell.setCellValue(lineDTO.getAssetNumber());
                            cell = row.createCell((short) 2);
                            cell.setCellValue(lineDTO.getAssetsDescription());
                            cell = row.createCell((short) 3);
                            cell.setCellValue(lineDTO.getModelNumber());
                            cell = row.createCell((short) 4);
                            cell.setCellValue(lineDTO.getCurrentUnits());
                            cell = row.createCell((short) 5);
                            cell.setCellValue(lineDTO.getOldLocationName());
                            cell = row.createCell((short) 6);
                            cell.setCellValue(lineDTO.getOldResponsibilityUserName());
                            cell = row.createCell((short) 7);
                            cell.setCellValue(lineDTO.getAssignedToLocationName());
                            cell = row.createCell((short) 8);
                            cell.setCellValue(lineDTO.getResponsibilityUserName());
                            if (!lineDTO.getLineTransDate().getCalendarValue().equals("")) {
                                cell = row.createCell((short) 9);
                                cell.setCellValue(lineDTO.getLineTransDate().getCalendarValue().substring(0, 10));
                            }
                            cell = row.createCell((short) 10);
                            cell.setCellValue(lineDTO.getRemark());
                        } else if (headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {////部门间调拨
                            cell = row.createCell((short) 0);
                            cell.setCellValue(lineDTO.getBarcode());
                            cell = row.createCell((short) 1);
                            cell.setCellValue(lineDTO.getAssetNumber());
                            cell = row.createCell((short) 2);
                            cell.setCellValue(lineDTO.getAssetsDescription());
                            cell = row.createCell((short) 3);
                            cell.setCellValue(lineDTO.getModelNumber());
                            cell = row.createCell((short) 4);
                            cell.setCellValue(lineDTO.getCurrentUnits());
                            cell = row.createCell((short) 5);
                            cell.setCellValue(lineDTO.getOldLocationName());
                            cell = row.createCell((short) 6);
                            cell.setCellValue(lineDTO.getOldResponsibilityUserName());
                            cell = row.createCell((short) 7);
                            cell.setCellValue(lineDTO.getResponsibilityDeptName());
                            cell = row.createCell((short) 8);
                            cell.setCellValue(lineDTO.getAssignedToLocationName());
                            cell = row.createCell((short) 9);
                            cell.setCellValue(lineDTO.getResponsibilityUserName());
                            if (!lineDTO.getLineTransDate().getCalendarValue().equals("")) {
                                cell = row.createCell((short) 10);
                                cell.setCellValue(lineDTO.getLineTransDate().getCalendarValue().substring(0, 10));
                            }
                            cell = row.createCell((short) 10);
                            cell.setCellValue(lineDTO.getRemark());
                        }
                        rowIndex++;
                    }
                }
            }
            mcBook.write(res.getOutputStream());
            // 释放资源
            this.afterExport(res, ins, mcBook, mcSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void beforeExport(HttpServletRequest request,
                                HttpServletResponse response, String exportFileName) {
        if (exportFileName == null || "".equals(exportFileName)) {
            exportFileName = request.getParameter("exportTmpName") + ".xls";// 如果没有导出名称则取报表模版名称
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=\""
                + exportFileName + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
    }

    protected void afterExport(HttpServletResponse response, InputStream ins,
                               HSSFWorkbook wbook, HSSFSheet wsheet) throws Exception {
        wbook = null;
        wsheet = null;
        if (ins != null)
            ins.close();
        if (response != null) {
            if (response.getOutputStream() != null)
                response.getOutputStream().flush();
            if (response.getOutputStream() != null)
                response.getOutputStream().close();
        }
    }

    protected String getFile(HttpServletRequest request, String filename, String opartor)
            throws Exception {
        if (filename == null || "".equals(filename))
            return "";
        else
            return request.getRealPath(opartor) + opartor + "tools" + opartor + "report" + opartor + "data" + opartor + filename;
    }

    public File exportFile(SfUserDTO user, AmsAssetsTransHeaderDTO dto, Connection conn) throws DataTransException {
        File file = null;
        try {
            DataTransfer transfer = null;
            BaseSQLProducer sqlProducer = new ItemOrderQueryModel(user, dto);
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String transType = StrUtil.nullToString(dto.getTransType());
            String fileName = "";
            if (transType.equals(AssetsDictConstant.ITEM_RED)) {
                fileName = "实物调拨单据表.xls";
            } else {
                fileName = "其它单据表.xls";
            }
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();

            if (transType.equals(AssetsDictConstant.ITEM_RED)) {
                fieldMap.put("TRANS_NO", "调拨单号");
                fieldMap.put("TRANS_STATUS_DESC", "单据状态");
                fieldMap.put("FROM_DEPT_NAME", "调出部门");
                fieldMap.put("CREATED", "申请人");
                fieldMap.put("CREATION_DATE", "申请日期");
                //fieldMap.put("TRANSFER_TYPE_DESC", "单据类型");
            } else {
                fieldMap.put("TRANS_NO", "其它单号");
                fieldMap.put("TRANS_STATUS_DESC", "单据状态");
                fieldMap.put("FROM_DEPT_NAME", "申请部门");
                fieldMap.put("CREATED", "申请人");
                fieldMap.put("CREATION_DATE", "创建日期");
            }


            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(user.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}