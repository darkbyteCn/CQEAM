package com.sino.ams.newasset.servlet;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dao.AmsAssetsCheckBatchDAO;
import com.sino.ams.newasset.dao.AmsAssetsCheckHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckBatchModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.util.CommonUseUtil;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.pda.PDAUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class AmsAssetsCheckBatchServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletForwarder forwarder;
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String msg = "";
		Connection conn = null;
		try {
			AmsAssetsCheckBatchDTO dto;
			DTOSet chkOrders;
			boolean operateResult;
			String chkCategoryOpt;
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
			AmsAssetsCheckBatchDTO dtoParameter = (AmsAssetsCheckBatchDTO) req2DTO
					.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			AmsAssetsCheckBatchDAO batchDAO = new AmsAssetsCheckBatchDAO(user,
					dtoParameter, conn);
			batchDAO.setServletConfig(getServletConfig(req));
			String orderType = dtoParameter.getOrderType();
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);

			String taskType = req.getParameter("taskType");
			if ((taskType == null) || (taskType.isEmpty())) {
				taskType = "";
				System.out.println("非年度盘点任务");
			} else {
				System.out.println("年度盘点任务");
			}

			String pattern = req.getParameter("action");
			String orderNumber = req.getParameter("parentOrderNumber");
			String orderTypes = req.getParameter("orderTypes");
			String orderName = req.getParameter("orderName");

			String sf_isNew = (String) req.getAttribute("SINOFLOW_NEW_CASE");
			if ((sf_isNew != null) && (sf_isNew.equals("1")))
				action = "NEW_ACTION";

			if (orderType.equals("")) {
				dtoParameter.setOrderType("ASS-CHK");
				dtoParameter.setOrderTypeName("资产盘点");
			}
			if (action.equals("")) {
				if (dtoParameter.getSrcPage().equals("QUERY_ACTION"))
					dtoParameter = optProducer.fillBatchStatus(dtoParameter);

				String deptOptions = optProducer
						.getUserAsssetsDeptOption(StrUtil
								.nullToString(dtoParameter.getCheckDept()));
				dtoParameter.setCheckDeptOption(deptOptions);
				req.setAttribute("QUERY_DTO", dtoParameter);
				forwardURL = "/newasset/checkBatchQuery.jsp";
			}
			if (action.equals("QUERY_ACTION")) {
				BaseSQLProducer sqlProducer = new AmsAssetsCheckBatchModel(
						user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BATCH_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern("YYYY-MM-DD");
				pageDAO.produceWebData();
				if (dtoParameter.getSrcPage().equals("QUERY_ACTION"))
					dtoParameter = optProducer.fillBatchStatus(dtoParameter);

				String deptOptions = optProducer
						.getUserAsssetsDeptOption(StrUtil
								.nullToString(dtoParameter.getCheckDept()));
				dtoParameter.setCheckDeptOption(deptOptions);
				req.setAttribute("QUERY_DTO", dtoParameter);
				forwardURL = "/newasset/checkBatchQuery.jsp";
			}
			if (action.equals("NEW_ACTION")) {
				dto = (AmsAssetsCheckBatchDTO) req
						.getAttribute("CHECK_BATCH_DATA");
				chkOrders = getCheckOrders(req, req2DTO);
				if (dto == null) {
					dto = dtoParameter;
					dto = fillData(user, dto, conn);
				}
				chkCategoryOpt = optProducer.getChkCategoryOption(dto
						.getCheckCategory());
				dto.setCheckCategoryOpt(chkCategoryOpt);
				req.setAttribute("CHECK_BATCH_DATA", dto);
				if (chkOrders != null) {
					req.setAttribute("CHECK_HEADER_DATA", chkOrders);
				}

				if (taskType.equals("y")) {
					if (pattern == null)
						pattern = "";
					if (pattern.equals("fromRemain")) {
						dto.setTaskName(orderName);
						dto.setTaskNumber(orderNumber);
						dto.setTaskType(orderTypes);

						if (orderTypes != null) {
							String taskTypeName = CommonUseUtil
									.getNameByType(orderTypes);
							dto.setTaskTypeName(taskTypeName);
						}
					}
					forwardURL = "/yearchecktaskmanager/assetsYearCheckBatchEdit.jsp";
				}else {
				    forwardURL = "/newasset/checkBatchEdit.jsp";
				}
			}
			if (action.equals("EDIT_ACTION")) {
				dto = (AmsAssetsCheckBatchDTO) req
						.getAttribute("CHECK_BATCH_DATA");
				chkOrders = (DTOSet) req.getAttribute("CHECK_HEADER_DATA");
				if (dto == null) {
					batchDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class
							.getName());
					batchDAO.setCalPattern("YYYY-MM-DD");
					if (dtoParameter.getBatchId().equals(""))
						dtoParameter.setBatchId(dtoParameter.getApp_dataID());

					dto = (AmsAssetsCheckBatchDTO) batchDAO
							.getDataByPrimaryKey();

					AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
					orderDTO.setBatchId(dtoParameter.getBatchId());
					AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(
							user, orderDTO, conn);
					orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class
							.getName());
					orderDAO.setCalPattern("YYYY-MM-DD");
					chkOrders = (DTOSet) orderDAO
							.getDataByForeignKey("batchId");
				}

				dto = fillDept2DTO(user, dto, conn);
				chkCategoryOpt = optProducer.getChkCategoryOption(dto
						.getCheckCategory());
				dto.setCheckCategoryOpt(chkCategoryOpt);

				req.setAttribute("CHECK_HEADER_DATA", chkOrders);
				req.setAttribute("CHECK_BATCH_DATA", dto);
				forwardURL = "/newasset/checkBatchEdit.jsp";
			}
			if (action.equals("DETAIL_ACTION")) {
				if (StrUtil.isEmpty(dtoParameter.getBatchId())) {
					dtoParameter.setBatchId(dtoParameter.getApp_dataID());
				}

				batchDAO = new AmsAssetsCheckBatchDAO(user, dtoParameter, conn);

				batchDAO
						.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
				batchDAO.setCalPattern("YYYY-MM-DD");
				if (dtoParameter.getBatchId().equals(""))
					dtoParameter.setBatchId(dtoParameter.getApp_dataID());

				dto = (AmsAssetsCheckBatchDTO) batchDAO.getDataByPrimaryKey();
				if (dto == null) {
					dto = dtoParameter;
					dto = fillData(user, dto, conn);
					message = getMessage("DATA_NOT_EXIST");
				} else {
					dto = fillDept2DTO(user, dto, conn);
					AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
					orderDTO.setBatchId(dtoParameter.getBatchId());
					AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(
							user, orderDTO, conn);
					orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class
							.getName());
					orderDAO.setCalPattern("YYYY-MM-DD");
					DTOSet orders = (DTOSet) orderDAO
							.getDataByForeignKey("batchId");
					req.setAttribute("CHECK_HEADER_DATA", orders);
				}
				req.setAttribute("CHECK_BATCH_DATA", dto);
				forwardURL = "/newasset/checkBatchDetail.jsp";
			}
			if (action.equals("SAVE_ACTION")) {
				chkOrders = getCheckOrders(req, req2DTO);
				if (batchDAO.hasPrevOrders(chkOrders)) {
					chkOrders = batchDAO.getSubmitedOrders();
					message = batchDAO.getMessage();
					req.setAttribute("CHECK_BATCH_DATA", dtoParameter);
					req.setAttribute("CHECK_HEADER_DATA", chkOrders);
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
					forwardURL = forwardURL + "?act=EDIT_ACTION";
					forwardURL = forwardURL + "&batchId="
							+ dtoParameter.getBatchId();
				}
				dtoParameter.setBatchStatus("SAVE_TEMP");
				batchDAO.setDTOParameter(dtoParameter);
				operateResult = batchDAO.saveNewCheckOrders(chkOrders);
				message = batchDAO.getMessage();
				if (operateResult) {
					dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO
							.getDTOParameter();
					dtoParameter = fillData(user, dtoParameter, conn);
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
					forwardURL = forwardURL + "?act=EDIT_ACTION";
					forwardURL = forwardURL + "&batchId="
							+ dtoParameter.getBatchId();
				}
				dtoParameter = fillData(user, dtoParameter, conn);
				req.setAttribute("CHECK_BATCH_DATA", dtoParameter);
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
				forwardURL = forwardURL + "?act=NEW_ACTION";
			}

			if (action.equals("SUBMIT_ACTION")) {
				chkOrders = getCheckOrders(req, req2DTO);
				if (batchDAO.hasPrevOrders(chkOrders)) {
					chkOrders = batchDAO.getSubmitedOrders();
					message = batchDAO.getMessage();
					req.setAttribute("CHECK_BATCH_DATA", dtoParameter);
					req.setAttribute("CHECK_HEADER_DATA", chkOrders);
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
					forwardURL = forwardURL + "?act=EDIT_ACTION";
					forwardURL = forwardURL + "&batchId="
							+ dtoParameter.getBatchId();
				}
				dtoParameter.setBatchStatus("IN_PROCESS");
				batchDAO.setDTOParameter(dtoParameter);
				operateResult = batchDAO.submitNewCheckOrders(chkOrders);
				message = batchDAO.getMessage();
				if (operateResult) {
					dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO
							.getDTOParameter();
					dtoParameter = fillData(user, dtoParameter, conn);
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
					forwardURL = forwardURL + "?act=DETAIL_ACTION";
					forwardURL = forwardURL + "&batchId="
							+ dtoParameter.getBatchId();
				}
				dtoParameter = fillData(user, dtoParameter, conn);
				req.setAttribute("CHECK_BATCH_DATA", dtoParameter);
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
				forwardURL = forwardURL + "?act=NEW_ACTION";
			}

			if (action.equals("DISTRIBUTE_ACTION")) {
				dtoParameter.setBatchStatus("DISTRIBUTED");
				batchDAO.setDTOParameter(dtoParameter);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BATCH_ID");
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] batchIds = parser.getParameterValues("batchId");
				batchDAO.distributeChkOrder(batchIds);
				message = batchDAO.getMessage();
				if (parser.contains("groupId")) {
					dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO
							.getDTOParameter();
					forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
					forwardURL = forwardURL + "?act=DETAIL_ACTION";
					forwardURL = forwardURL + "&batchId="
							+ dtoParameter.getBatchId();
				}
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
				forwardURL = forwardURL + "?act=QUERY_ACTION";
				forwardURL = forwardURL + "&srcPage=DISTRIBUTE_ACTION";
			}
			if (action.equals("CANCEL_ACTION")) {
				boolean operateResult1 = batchDAO.cancelCheckTask();
				message = batchDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
				if (operateResult1)
					forwardURL = forwardURL + "?act=DETAIL_ACTION";
				else
					forwardURL = forwardURL + "?act=EDIT_ACTION";

				forwardURL = forwardURL + "&batchId="
						+ dtoParameter.getBatchId();
			}
			if (action.equals("ImportLocation")) {
				req.setAttribute("NOLOCATIOND_DATA", null);
				req.setAttribute("SPLIT_DATA_VIEW", null);
				forwardURL = "/newasset/importCheckLocation.jsp";
			}
			if (action.equals("IMP_LOCATION_CODE_ACTION")) {
				Logger.logInfo("Excel submit servlet begin....");
                int startRowNum = 1;
                int columnNum = 20;
                RequestParser reqPar = new RequestParser();
                reqPar.transData(req);
                UploadFile[] upFiles = null;
                UploadRow uploadRow;
                String conFilePath = PDAUtil.getCurUploadFilePath(conn);
                UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
                uploadFileSaver.saveFiles(conFilePath);
                uploadRow = uploadFileSaver.getRow();
                upFiles = uploadRow.getFiles();
                if (upFiles == null) {
                    return;
                } else if (upFiles.length != 1 || upFiles[0].getFileName().equals("")) {
                    return;
                }
                UploadFile uploadFile = upFiles[0];
                String fileName = uploadFile.getAbsolutePath();
                FileInputStream fileIn = new FileInputStream(fileName);
                POIFSFileSystem fs = new POIFSFileSystem(fileIn);
                HSSFWorkbook book = new HSSFWorkbook(fs);
                HSSFSheet hssfSheet = book.getSheetAt(0);
                HSSFRow hssfRow = null;
                HSSFCell hssfCell = null;
                int row = hssfSheet.getLastRowNum();
                String strValue = "";
                List lst = new ArrayList();

                for (int i = startRowNum; i <= row; i++) {
                    hssfRow = hssfSheet.getRow(i);
                    if (hssfRow != null) {
                        hssfCell = hssfRow.getCell((short) 0);
                        if (hssfCell != null) {
                            strValue += getStringValue(hssfCell) + "','";
                            lst.add(getStringValue(hssfCell));
                        }
                    }
                }
                if (!strValue.equals("")) {
                    RowSet rows = batchDAO.getImpLocation(strValue.substring(0, strValue.length() - 3));
                    req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
                    for (int i = 0; i < rows.getSize(); i++) {
                        String locCodeString = rows.getRow(i).getStrValue("OBJECT_CODE");
                        if (lst.contains(locCodeString)) {
                            lst.remove(locCodeString);
                        }
                    }
                }

                req.setAttribute("NOLOCATIOND_DATA", lst);
                forwardURL = "/newasset/importCheckLocation.jsp";

            }/* else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }*/
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage("POOL_PASSIVATE_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage("DTO_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage("QUERY_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (SQLException ex) {
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (SQLModelException ex) {
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (DataHandleException ex) {
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (ParseException ex) {
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (ContainerException ex) {
			message = getMessage("COMMON_ERROR");
			message.setIsError(true);
			forwardURL = "/servlet/com.sino.framework.servlet.MessageProcessServlet";

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} catch (FileSizeException e) {
			e.printStackTrace();

			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals("")) {
				forwarder.forwardView(forwardURL);
				return;
			}
			forwarder.forwardOpenerView(forwardURL, msg);
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			forwarder = new ServletForwarder(req, res);
			if (msg.equals(""))
				forwarder.forwardView(forwardURL);
			else
				forwarder.forwardOpenerView(forwardURL, msg);
		}
	}

	private AmsAssetsCheckBatchDTO fillData(SfUserDTO user,
			AmsAssetsCheckBatchDTO dto, Connection conn) throws DTOException {
		try {
			dto.setBatchNo("完成时自动生成");
			dto.setCreatedBy(user.getUserId());
			dto.setCreatedUser(user.getUsername());
			dto.setOrganizationId(user.getOrganizationId());
			dto.setCompanyName(user.getCompany());
			dto.setCurrCreationDate();
			dto.setCalPattern("YYYY-MM-DD");
			System.out.println("User:"+user);
			fillDept2DTO(user, dto, conn);
			System.out.println("Come here");
			System.out.println("error:"+user.getUserGroups());
			DTOSet assetsGroups = user.getUserGroups();
			System.out.println("No error");
			if (assetsGroups.getSize() != 1) {
				SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
				dto.setGroupId(sfGRoup.getGroupId());
				dto.setGroupName(sfGRoup.getGroupname());
			}
		} catch (QueryException ex) {
			ex.printLog();
			throw new DTOException(ex);
		}
		return dto;
	}

	private AmsAssetsCheckBatchDTO fillDept2DTO(SfUserDTO user,
			AmsAssetsCheckBatchDTO dto, Connection conn) throws QueryException {
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String deptCode = StrUtil.nullToString(dto.getCheckDept());
		if ((deptCode.equals("")) && (dto.getBatchId().equals("")))
			deptCode = user.getDeptCode();

		String deptOptions = optProducer.getUserAsssetsDeptOption(deptCode);
		dto.setCheckDeptOption(deptOptions);
		return dto;
	}

	private DTOSet getCheckOrders(HttpServletRequest req, Request2DTO req2DTO)
			throws DTOException {
		DTOSet orders = new DTOSet();
		List excFields = new ArrayList();
		excFields.add("checkCategory");
		req2DTO.setIgnoreFields(AmsAssetsCheckBatchDTO.class, excFields);
		req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
		orders = req2DTO.getDTOSet(req);
		return orders;
	}

	private String getStringValue(HSSFCell cell) {
		String strValue = "";
		if (cell != null)
			if (cell.getCellType() == 3)
				strValue = "";
			else if (cell.getCellType() == 0)
				strValue = String.valueOf(cell.getNumericCellValue());
			else
				strValue = cell.getRichStringCellValue().toString();

		return strValue;
	}
}