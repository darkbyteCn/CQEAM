package com.sino.ams.newasset.allocation.servlet;

import com.sino.ams.newasset.allocation.dao.AmsAssetsAllocationHeaderDAO;
import com.sino.ams.newasset.allocation.dao.AmsAssetsAllocationLineDAO;
import com.sino.ams.newasset.allocation.model.AmsAssetsAllocationHeaderModel;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.dao.OrderApproveDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmsAssetsAllocationHeaderServlet extends BaseServlet
{
  public void performTask(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String forwardURL = "";
    Message message = SessionUtil.getMessage(req);
    Connection conn = null;
//    ServletForwarder forwarder;
    try
    {
      SfUserDTO user = (SfUserDTO)getUserAccount(req);
      Request2DTO req2DTO = new Request2DTO();
      req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
      AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO)req2DTO.getDTO(req);
      ServletConfigDTO servletConfig = getServletConfig(req);
      dto.setServletConfig(servletConfig);
      String action = dto.getAct();
      conn = getDBConnection(req);
      if (!dto.getApp_dataID().equals("")) {
        dto.setTransId(dto.getApp_dataID());
      }
      AmsAssetsAllocationHeaderDAO headerDAO = new AmsAssetsAllocationHeaderDAO(user, dto, conn);
      headerDAO.setServletConfig(servletConfig);
      String transferype = dto.getTransferType();
      AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
      String option = optProducer.getTransferOption(dto.getTransferType());
      dto.setTransferTypeOption(option);
      option = optProducer.getFAContentOption(dto.getFaContentCode());
      dto.setFaContentOption(option);

      if ("".equals(dto.getEmergentLevel())) {
        dto.setEmergentLevel("0");
      }
      String emergentLevelOption = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
      dto.setEmergentLevelOption(emergentLevelOption);

      DTOSet lineDTOSetALL = new DTOSet();
      if (action.equals("")) {
        if (dto.getApp_dataID().equals("")) {
          AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)req.getAttribute("ORDER_HEAD_DATA");
          if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn);
          } else {
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            headerDTO.setFaContentOption(option);
            String deptOptions = optProducer.getUserAsssetsDeptOption("");
            dto.setFromDeptOption(deptOptions);
          }
          headerDTO.setTransferType(transferype);
          headerDTO.setServletConfig(servletConfig);
          headerDTO.setCalPattern("YYYY-MM-DD");
          req.setAttribute("ORDER_HEAD_DATA", headerDTO);
          forwardURL = "/newasset/allocation/assetsAllocationEdit.jsp";
        } else {
          headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
          AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)headerDAO.getDataByPrimaryKey();
          if (headerDTO == null) {
            headerDTO = fillData(dto, user, conn);
            message = getMessage("DATA_NOT_EXIST");
            message.setIsError(true);
            forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
          } else {
            headerDTO.setServletConfig(servletConfig);
            headerDTO = fillOptions(headerDTO, user, conn);
            headerDTO.setCalPattern("YYYY-MM-DD");
            AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
            lineDTO.setTransId(headerDTO.getTransId());
            lineDTO.setTransType(headerDTO.getTransType());
            AmsAssetsAllocationLineDAO lineDAO = new AmsAssetsAllocationLineDAO(user, lineDTO, conn);
            lineDAO.setCalPattern("YYYY-MM-DD");
            lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
            DTOSet ds = (DTOSet)req.getAttribute("ORDER_LINE_DATA");
            if (ds == null) {
              ds = (DTOSet)lineDAO.getDataByForeignKey("transId");
            }
            headerDTO.setEmergentLevelOption(dto.getEmergentLevelOption());
            req.setAttribute("ORDER_HEAD_DATA", headerDTO);
            req.setAttribute("ORDER_LINE_DATA", ds);
            forwardURL = "/newasset/allocation/assetsAllocationEdit.jsp";
          }
        }
      } else if (action.equals("QUERY_ACTION")) {
        dto.setTransStatus("SAVE_TEMP");
        BaseSQLProducer sqlProducer = new AmsAssetsAllocationHeaderModel(user, dto);
        PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
        CheckBoxProp checkProp = new CheckBoxProp("subCheck");
        checkProp.addDbField("TRANS_ID");
        pageDAO.setWebCheckProp(checkProp);
        pageDAO.setCalPattern("YYYY-MM-DD");
        pageDAO.produceWebData();
        req.setAttribute("QUERY_DTO", dto);
        forwardURL = "/newasset/allocation/assetsAllocationQuery.jsp";
      } else if (action.equals("NEW_ACTION")) {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)req.getAttribute("ORDER_HEAD_DATA");
        if (headerDTO == null) {
          headerDTO = fillData(dto, user, conn);
        } else {
          option = optProducer.getFAContentOption(dto.getFaContentCode());
          headerDTO.setFaContentOption(option);
          String deptOptions = optProducer.getUserAsssetsDeptOption("");
          dto.setFromDeptOption(deptOptions);
        }
        headerDTO.setTransferType(transferype);
        headerDTO.setServletConfig(servletConfig);
        headerDTO.setCalPattern("YYYY-MM-DD");
        req.setAttribute("ORDER_HEAD_DATA", headerDTO);
        forwardURL = "/newasset/allocation/assetsAllocationEdit.jsp";
      } else if (action.equals("EDIT_ACTION")) {
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)headerDAO.getDataByPrimaryKey();
        if (headerDTO == null) {
          headerDTO = fillData(dto, user, conn);
          req.setAttribute("ORDER_HEAD_DATA", headerDTO);
          message = getMessage("DATA_NOT_EXIST");
          message.setIsError(true);
          forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
        } else {
          headerDTO.setServletConfig(servletConfig);
          headerDTO = fillOptions(headerDTO, user, conn);
          headerDTO.setCalPattern("YYYY-MM-DD");
          AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
          lineDTO.setTransId(headerDTO.getTransId());
          lineDTO.setTransType(headerDTO.getTransType());
          AmsAssetsAllocationLineDAO lineDAO = new AmsAssetsAllocationLineDAO(user, lineDTO, conn);
          lineDAO.setCalPattern("YYYY-MM-DD");
          lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
          DTOSet ds = (DTOSet)req.getAttribute("ORDER_LINE_DATA");
          if (ds == null) {
            ds = (DTOSet)lineDAO.getDataByForeignKey("transId");
          }
          req.setAttribute("ORDER_HEAD_DATA", headerDTO);
          req.setAttribute("ORDER_LINE_DATA", ds);
          forwardURL = "/newasset/allocation/assetsAllocationEdit.jsp";
        }
      } else if (action.equals("DETAIL_ACTION")) {
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)headerDAO.getDataByPrimaryKey();

        OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
        String accessSheet = approveDAO.getAccessSheet();
        headerDTO.setAccessSheet(accessSheet);

        req.setAttribute("IS_FINANCE_GROUP", "");
        req.setAttribute("IS_SPECIAL_GROUP", "");
        if (headerDTO == null);
        headerDTO.setServletConfig(servletConfig);
        headerDTO = fillOptions(headerDTO, user, conn);
        headerDTO.setCalPattern("YYYY-MM-DD");
        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
        lineDTO.setTransId(headerDTO.getTransId());
        lineDTO.setTransType(headerDTO.getTransType());
        AmsAssetsAllocationLineDAO lineDAO = new AmsAssetsAllocationLineDAO(user, lineDTO, conn);
        lineDAO.setCalPattern("YYYY-MM-DD");
        lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        DTOSet ds = (DTOSet)req.getAttribute("ORDER_LINE_DATA");
        if (ds == null) {
          ds = (DTOSet)lineDAO.getDataByForeignKey("transId");
        }
        req.setAttribute("ORDER_HEAD_DATA", headerDTO);
        req.setAttribute("ORDER_LINE_DATA", ds);
        forwardURL = "/newasset/allocation/assetsAllocationDetail.jsp";
      }
      else if (action.equals("SAVE_ACTION")) {
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req);
        dto.setTransStatus("SAVE_TEMP");
        headerDAO.setDTOParameter(dto);
        headerDAO.saveOrder(orderLines);
        message = headerDAO.getMessage();
        dto = (AmsAssetsTransHeaderDTO)headerDAO.getDTOParameter();
        String transId = dto.getTransId();
        forwardURL = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationHeaderServlet";
        if (transId.equals("")) {
          forwardURL = forwardURL + "?act=NEW_ACTION";
          req.setAttribute("ORDER_LINE_DATA", orderLines);
        } else {
          forwardURL = forwardURL + "?act=EDIT_ACTION";
          forwardURL = forwardURL + "&transId=" + dto.getTransId();
        }
      } else if (action.equals("SUBMIT_ACTION")) {
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req);
        dto.setTransStatus("IN_PROCESS");
        headerDAO.setDTOParameter(dto);
        headerDAO.submitOrder(orderLines);
        if (dto.isFlowOver()) {
          headerDAO.logItemChgHistory(orderLines);
        }
        message = headerDAO.getMessage();
        dto = (AmsAssetsTransHeaderDTO)headerDAO.getDTOParameter();
        String transId = dto.getTransId();
        forwardURL = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationHeaderServlet";
        if (transId.equals("")) {
          forwardURL = forwardURL + "?act=NEW_ACTION";
        } else {
          forwardURL = forwardURL + "?act=DETAIL_ACTION";
          forwardURL = forwardURL + "&transId=" + dto.getTransId();
        }
      } else if (action.equals("DELETE_ACTION")) {
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet orderLines = req2DTO.getDTOSet(req);
        dto.setTransStatus("IN_PROCESS");
        headerDAO.setDTOParameter(dto);
        headerDAO.doDelete(orderLines);
        message = headerDAO.getMessage();
        dto = (AmsAssetsTransHeaderDTO)headerDAO.getDTOParameter();
        String transId = dto.getTransId();
        forwardURL = "/servlet/com.sino.ams.newasset.servlet.OrderApproveServlet";
        if (transId.equals("")) {
          forwardURL = forwardURL + "?act=NEW_ACTION";
        } else {
          forwardURL = forwardURL + "?act=EDIT_ACTION";
          forwardURL = forwardURL + "&transId=" + dto.getTransId();
        }
      } else if (action.equals("CANCEL_ACTION")) {
        boolean operateResult = headerDAO.cancelOrders();
        message = headerDAO.getMessage();
        forwardURL = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationHeaderServlet";
        if (operateResult)
          forwardURL = forwardURL + "?act=DETAIL_ACTION";
        else {
          forwardURL = forwardURL + "?act=EDIT_ACTION";
        }
        forwardURL = forwardURL + "&transId=" + dto.getTransId();
      }
      else if (action.equals("EXPORT_ACTION")) {
        File file = headerDAO.exportFile();
        headerDAO.setCalPattern("YYYY-MM-DD");
        WebFileDownload fileDown = new WebFileDownload(req, res);
        fileDown.setFilePath(file.getAbsolutePath());
        fileDown.download();
        file.delete();
      } else if (action.equals("GET_TARGET_OU")) {
        res.setContentType("text/html;charset=GBK");
        PrintWriter out = res.getWriter();
        String opt = optProducer.getTargetOrganization(dto.getFromOrganizationId(), 0);
        out.print(opt);
        out.close();
      } else if (action.equals("excel")) {
    	  AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)req.getAttribute("ORDER_HEAD_DATA");
        
        if (headerDTO == null) {
          headerDTO = fillData2(dto, user, conn);
        } else {
          option = optProducer.getFAContentOption(dto.getFaContentCode());
          headerDTO.setFaContentOption(option);
          String deptOptions = optProducer.getUserAsssetsDeptOption("");
          dto.setFromDeptOption(deptOptions);
        }
        headerDTO.setServletConfig(servletConfig);
        headerDTO.setCalPattern("YYYY-MM-DD");

        DTOSet lineDTOSet = new DTOSet();
        AmsAssetsAllocationHeaderModel assetsTransHeaderModel = new AmsAssetsAllocationHeaderModel(user, dto);
        String excel = StrUtil.nullToString(req.getParameter("excel"));
        String[] arr = StrUtil.splitStr(excel, ",");

        SQLModel delSqlModel = new SQLModel();
        delSqlModel = assetsTransHeaderModel.getImpBarcodeDeleteModel();
        DBOperator.updateRecord(delSqlModel, conn);

        SQLModel insertSqlModel = new SQLModel();

        Collection c = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
          String s = arr[i].replaceAll("'", "");
          if (!c.contains(s)) {
            insertSqlModel = assetsTransHeaderModel.getDataInsertModel(s);
            DBOperator.updateRecord(insertSqlModel, conn);
            c.add(s);
          }
        }
        SQLModel sqlModel = new SQLModel();
        sqlModel = assetsTransHeaderModel.getQueryBarcodeExcelModel(excel, headerDTO);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
          lineDTOSet = simpleQuery.getDTOSet();
        }

        lineDTOSetALL = new DTOSet();
        Map mp1 = new HashMap();
        for (int j = 0; j < lineDTOSet.getSize(); j++) {
          String barcode = ((AmsAssetsTransLineDTO)lineDTOSet.getDTO(j)).getBarcode();
          mp1.put(barcode, barcode);
        }
        for (int i = 0; i < arr.length; i++) {
          String s = arr[i].replaceAll("'", "");
          if (!mp1.containsKey(s)) {
            AmsAssetsTransLineDTO dtoa = new AmsAssetsTransLineDTO();

            SQLModel queryModel = new SQLModel();
            queryModel = assetsTransHeaderModel.queryBarcode(s);
            SimpleQuery qeuryQuery = new SimpleQuery(queryModel, conn);
            qeuryQuery.executeQuery();
            RowSet rs = qeuryQuery.getSearchResult();
            int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
            String erroMsg = "";
            if (n == 0)
              erroMsg = "系统中不存在该条码";
            else {
              erroMsg = "该条码不属于您的权限范围";
            }
            String excelLineId = String.valueOf(i + 2);
            dtoa.setBarcode(s);
            dtoa.setErrorMsg(erroMsg);
            dtoa.setExcelLineId(excelLineId);
            lineDTOSetALL.addDTO(dtoa);
          }
        }
        if (lineDTOSetALL.isEmpty()) {
          message = getMessage("EXPORT_SUCCESS");
          message.setIsError(false);
        } else {
          message = getMessage("EXPORT_FAILURE");
          message.setIsError(true);
        }
        List list = new ArrayList();
        list.add(0, "1、系统中可能不存在该条码");
        list.add(1, "2、该条码可能不属于本公司");
        list.add(2, "3、该条码可能不属于您的权限范围");
        list.add(3, "4、该条码可能存在于单据中(盘点、调拨、报废等)");

        req.setAttribute("ORDER_HEAD_DATA", headerDTO);
        req.setAttribute("ORDER_LINE_DATA", lineDTOSet);
        req.setAttribute("PRIVI_DATA", lineDTOSetALL);
        req.setAttribute("REMARK_LIST", list);

        forwardURL = "/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=" + headerDTO.getSf_appName() + "&transferType=" + headerDTO.getTransferType() + "&fromExcel=Y&act=" + "NEW_ACTION";
      }
      else if (action.equals("VALIDATE_ACTION")) {
        res.setContentType("text/html;charset=GBK");
        PrintWriter out = res.getWriter();
        String fromGroup = req.getParameter("fromGroup");
        boolean result = false;
        if (fromGroup != null) fromGroup.equals("");

        out.print(result);
        out.close();
      } else if (action.equals("DO_THRED_DEPT")) {
        String fDept = StrUtil.nullToString(req.getParameter("fDept"));
        String tDept = StrUtil.nullToString(req.getParameter("tDept"));
        boolean isThredDept = headerDAO.findThredDept(fDept, tDept);
        PrintWriter out = res.getWriter();
        if (isThredDept)
          out.print("Y");
        else {
          out.print("N");
        }
        out.flush();
        out.close();
      } else {
        message = getMessage("INVALID_REQ");
        message.setIsError(true);
        forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
      }
    } catch (PoolPassivateException ex) {
      ex.printLog();
      message = getMessage("POOL_PASSIVATE_ERROR");
      message.setIsError(true);
      forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
    }
    catch (QueryException ex)
    {
      ServletForwarder forwarder;
      ex.printLog();
      message = getMessage("QUERY_ERROR");
      message.setIsError(true);
      forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
    }
    catch (Throwable ex)
    {
      ServletForwarder forwarder;
      Logger.logError(ex);
      message = getMessage("COMMON_ERROR");
      message.setIsError(true);
      forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
    }
    finally
    {
      ServletForwarder forwarder;
      closeDBConnection(conn);
      setHandleMessage(req, message);
      if (!StrUtil.isEmpty(forwardURL)) {
        forwarder = new ServletForwarder(req, res);
        forwarder.forwardView(forwardURL);
      }
    }
  }

  private AmsAssetsTransHeaderDTO fillData(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException
  {
    dto.setTransNo("完成时自动生成");
    dto.setCreatedBy(user.getUserId());
    dto.setCreated(user.getUsername());
    dto.setFromOrganizationId(user.getOrganizationId());
    dto.setCurrCreationDate();
    dto.setFromCompanyName(user.getCompany());
    dto.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
    dto.setEmail(user.getEmail());
    dto.setPhoneNumber(user.getMobilePhone());

    dto.setUserDeptName(dto.getUserDeptName());
    DTOSet assetsGroups = user.getUserGroups();
    if (assetsGroups.getSize() == 1) {
      SfGroupDTO sfGRoup = (SfGroupDTO)assetsGroups.getDTO(0);
      dto.setFromGroup(sfGRoup.getGroupId());
      dto.setFromGroupName(sfGRoup.getGroupname());
      dto.setGroupProp(sfGRoup.getGroupProp());
    }
    dto = fillOptions(dto, user, conn);
    return dto;
  }
  private AmsAssetsTransHeaderDTO fillData2(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn)
  throws DTOException, QueryException, CalendarException
{
  dto.setTransNo("完成时自动生成");
  dto.setCreatedBy(user.getUserId());
  dto.setCreated(user.getUsername());
  dto.setFromOrganizationId(user.getOrganizationId());
  dto.setCurrCreationDate();
  dto.setFromCompanyName(user.getCompany());
  dto.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
  dto.setEmail(user.getEmail());
  dto.setPhoneNumber(user.getMobilePhone());

  dto.setUserDeptName(dto.getUserDeptName());
  DTOSet assetsGroups = user.getUserGroups();
  if (assetsGroups.getSize() == 1) {
    SfGroupDTO sfGRoup = (SfGroupDTO)assetsGroups.getDTO(0);
    dto.setFromGroup(sfGRoup.getGroupId());
    dto.setFromGroupName(sfGRoup.getGroupname());
    dto.setGroupProp(sfGRoup.getGroupProp());
  }
  dto = fillOptions2(dto, user, conn);
  return dto;
}

  private AmsAssetsTransHeaderDTO fillOptions(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn)
    throws QueryException
  {
    FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
    dto.setTransTypeValue(flexUtil.getFlexValue("ORDER_TYPE_ASSETS", dto.getTransType()));
    AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
    String fromDept = StrUtil.nullToString(dto.getFromDept());

    String specialityDeptOptions = "";
    if (StrUtil.isEmpty(fromDept)) {
      fromDept = user.getDeptCode();
    }
    String deptOptions = optProducer.getUserAsssetsDeptOption(fromDept);
    dto.setFromDeptOption(deptOptions);
    if (dto.getTransferType().equals("BTW_COMP")) {
      String opt = optProducer.getTargetOrganization(user.getOrganizationId(), dto.getToOrganizationId());
      dto.setToCompanyOption(opt);
    }
    else if (dto.getTransId().equals("")) {
      dto.setToOrganizationId(user.getOrganizationId());
    }

    String specialityDept = dto.getSpecialityDept();
    specialityDeptOptions = optProducer.getSpecialAsssetsDeptOption(specialityDept);
    dto.setSpecialityDeptOption(specialityDeptOptions);

    String transOption = optProducer.getTransferFaOption(dto.getFaContentCode(), dto.getTransferType());
    dto.setFaContentOption(transOption);

    String emergentLevelOption = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
    dto.setEmergentLevelOption(emergentLevelOption);

    return dto;
  }
  private AmsAssetsTransHeaderDTO fillOptions2(AmsAssetsTransHeaderDTO dto, SfUserDTO user, Connection conn)
  throws QueryException
{
	  AmsAssetsAllocationHeaderDAO headerDAO = new AmsAssetsAllocationHeaderDAO(user, dto, conn);
  FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
  dto.setTransTypeValue(flexUtil.getFlexValue("ORDER_TYPE_ASSETS", dto.getTransType()));
  AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
  String fromDept = StrUtil.nullToString(dto.getFromDept());

  String specialityDeptOptions = "";
  if (StrUtil.isEmpty(fromDept)) {
    fromDept =headerDAO.getDeptCode(dto.getUserDeptName()) ;
    dto.setFromDept(fromDept);
    dto.setFromDeptName(dto.getUserDeptName());
  }
  String deptOptions = optProducer.getUserAsssetsDeptOptiont(dto.getUserDeptName());
  dto.setFromDeptOption(deptOptions);
  if (dto.getTransferType().equals("BTW_COMP")) {
    String opt = optProducer.getTargetOrganization(user.getOrganizationId(), dto.getToOrganizationId());
    dto.setToCompanyOption(opt);
  }
  else if (dto.getTransId().equals("")) {
    dto.setToOrganizationId(user.getOrganizationId());
  }

  String specialityDept = dto.getSpecialityDept();
  specialityDeptOptions = optProducer.getSpecialAsssetsDeptOption(specialityDept);
  dto.setSpecialityDeptOption(specialityDeptOptions);

  String transOption = optProducer.getTransferFaOption(dto.getFaContentCode(), dto.getTransferType());
  dto.setFaContentOption(transOption);

  String emergentLevelOption = optProducer.getAmsEmergentLevel(dto.getEmergentLevel());
  dto.setEmergentLevelOption(emergentLevelOption);

  return dto;
}
}