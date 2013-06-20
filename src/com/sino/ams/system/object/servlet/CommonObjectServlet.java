package com.sino.ams.system.object.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.appbase.service.BarCodeService;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.print.dao.EtsBarcodePrintHistoryDAO;
import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.dao.CommonObjectDAO;
import com.sino.ams.system.object.dto.CommonObjectPrintDTO;
import com.sino.ams.system.object.model.CommonObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.config.SinoConfig;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CommonObjectServlet extends BaseServlet {
	private final static String AJAX_RES_TXT = "1";
    private final static String AJAX_RES_HTM = "2";
    private final static String AJAX_RES_XML = "3";
    
	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this com.sino.base.PubServlet method
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			String isTd = user.getIsTd();
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
			String provinceCode = SinoConfig.getProvinceCode();
			EtsObjectDTO dto = (EtsObjectDTO) req2DTO.getDTO(req);
			dto.setIsTd(isTd);
			dto.setProvinceCode(provinceCode);
            ServletConfigDTO servletConfig = getServletConfig(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			if (dto.getOrganizationId() == 0||dto.getOrganizationId() == -1) {
				dto.setOrganizationId(user.getOrganizationId());
			}
            String opt = "";
            
            OptionProducer op = new OptionProducer(user, conn);
			dto.setOrganizationOption(op.getAllOrganization(String.valueOf(dto.getOrganizationId()), true));
            
            opt = optProducer.getAreaOptions(dto.getOrganizationId(), dto.getCountyCode());

			dto.setCountyOption(opt);
			opt = optProducer.getObjectCategoryOption( dto.getObjectCategory());
			dto.setObjCategoryOption(opt);
			String cityOpt = optProducer.getCityOption( ConvertUtil.int2String( dto.getOrganizationId() )  );
			dto.setCityOption(cityOpt);
			String areaType = optProducer.getDictOption("ADDR_AREA_TYPE", dto.getAreaType());
			dto.setAreaTypeOption(areaType);
			CommonObjectDAO objectDAO = new CommonObjectDAO(user, dto, conn);
			if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.OBJECT_QUERY_PAGE;
			} else if (action.equals(AMSActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new CommonObjectModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("WORKORDER_OBJECT_NO");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.OBJECT_QUERY_PAGE;
			} else if (action.equals(AMSActionConstant.EXPORT_ACTION)) {      //导出到Excel
				File file = objectDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AMSActionConstant.DOWNLOAD_ACTION)) {
				File file = objectDAO.getExportFile2();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AMSActionConstant.CHANGE_COUNTY)) { //改变所属区域
                String countyOption="";
                 if(servletConfig.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_NM)){
	                countyOption = optProducer.getNMAreaOptions(dto.getOrganizationId(), dto.getCountyCode());
	            }else{
	              countyOption = optProducer.getAreaOptions(dto.getOrganizationId(), dto.getCountyCode());
	            }
                processAjaxResponse(res, countyOption, AJAX_RES_HTM);
			} else if (action.equals("CHANGE_ORG")) {//改变所属区域
                String countyOption = objectDAO.getOrgChangeResponse();
                processAjaxResponse(res, countyOption, AJAX_RES_HTM);
            } else if (action.equals(AMSActionConstant.CHANGE_COUNTYS)) {
				String countyOptions = optProducer.getOuCountyOptions(dto.getCity(),"");
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print(countyOptions);
				out.close();
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] objectNos = parser.getParameterValues("workorderObjectNo");
				objectDAO.disableObjects(objectNos);
				message = objectDAO.getMessage();
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.ENABLE_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] objectNos = parser.getParameterValues("workorderObjectNo");
				objectDAO.enableObjects(objectNos);
				message = objectDAO.getMessage();
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.DETAIL_ACTION)) {
				EtsObjectDTO objectDTO = (EtsObjectDTO)req.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
				
				if(objectDTO == null){
					String objectNo = dto.getWorkorderObjectNo();
					if(objectNo.equals("")){
						objectDTO = dto;
                        objectDTO.setAreaTypeOption(optProducer.getDictOption("ADDR_AREA_TYPE", objectDTO.getAreaType()));
                    } else {
                        objectDAO.setDTOClassName(EtsObjectDTO.class.getName());
                        objectDTO = (EtsObjectDTO)objectDAO.getDataByPrimaryKey();
                        opt = optProducer.getAllOu(objectDTO.getOrganizationId());
                        objectDTO.setOrganizationOption(opt);
			            opt = optProducer.getAreaOptions(objectDTO.getOrganizationId(), objectDTO.getCountyCode());
                        objectDTO.setCountyOption(opt);
                        
            			cityOpt = optProducer.getCityOption(objectDTO.getCity());
            			objectDTO.setCityOption(cityOpt);
            			opt = optProducer.getOuCountyOptions(objectDTO.getCity(),objectDTO.getCounty());
            			objectDTO.setCountyOptions(opt);
            			
                        objectDTO.setAreaTypeOption(optProducer.getDictOption("ADDR_AREA_TYPE", objectDTO.getAreaType()));
						opt = optProducer.getObjectCategoryOption( objectDTO.getObjectCategory() );
						objectDTO.setObjCategoryOption(opt);
					}
				}
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, objectDTO);
				forwardURL = URLDefineList.OBJECT_DETAIL_PAGE;
			} else if (action.equals(AMSActionConstant.VALIDATE_ACTION)) {                                          //验证barcode是否存在
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				String accessType = req.getParameter("accessType");				
				if(accessType != null && !accessType.equals("") && accessType.equals("validateworkorderObjectName") && (dto.getWorkorderObjectName().indexOf("'")) < 0){
					String result = objectDAO.getAddress(dto.getWorkorderObjectName());
					out.print(result);
				}else {
					boolean isExist = objectDAO.existObject();
					System.out.println("isExist = " + isExist);
					if (isExist) {
						out.print("Y");
					} else {
						out.print("N");
					}					
				}
				out.close();
			} else if (action.equals(AMSActionConstant.VALIDATE_BTSNO)) {
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				boolean isExist = objectDAO.existBtsNo();
				System.out.println("isExist = " + isExist);
				if (isExist) {
					out.print("Y");
				} else {
					out.print("N");
				}
			} else if (action.equals(AMSActionConstant.VALIDATE_WORKORDEROBJECTNAME)) {
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				boolean isExist = objectDAO.existWorkorderObjectName();
				if (isExist) {
					out.print("Y");
				} else {
					out.print("N");
				}
			} else if (action.equals(AMSActionConstant.VALIDATE_LOCATION_ACTION)) {
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				String result = objectDAO.getLocation(dto.getLocation());
				out.print(result);
				out.close();
			} else if (action.equals(AMSActionConstant.SAVE_ACTION)) {
				if(objectDAO.saveObject()){
					dto = (EtsObjectDTO) objectDAO.getDTOParameter();
				}
				message = objectDAO.getMessage();
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dto);
				forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.DETAIL_ACTION;
				forwardURL += "&workorderObjectNo=" + dto.getWorkorderObjectNo();
			}
//			else if (action.equals("SELECT_ADDRESS")){
//				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dto);
//				RowSet rows = objectDAO.getAddress(dto.getWorkorderObjectName());
//				req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
//				forwardURL = URLDefineList.OBJECT_LIST_PAGE;
//			}

			else if (action.equals("SYN")) {
                String[] objectNos = getObjectNos(req);
                objectDAO.insertToData(objectNos);
                message = objectDAO.getMessage();
                forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
                forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
            } else if (action.equals("TRANSFER_ACTION")) {  //地点传送功能
                String[] objectNos = getObjectNos(req);
                objectDAO.transferToData(objectNos);
                message = objectDAO.getMessage();
                forwardURL = URLDefineList.COMM_OBJECT_SERVLET;
                forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
            } else if (action.equals("GET_MAX_OBJECT_CODE")) {
                processAjaxResponse(res, objectDAO.getNextWorkorderObjectCode(SinoConfig.getProvinceCode()), AJAX_RES_HTM);
            } else if (action.equals( "PRINT_ACTION" )) {      //地点打印
				DTOSet lines = this.getDTOSetFromReq(req);
				String checkObjectNo = StrUtil.nullToString( req.getParameter( "checkedObjectNo" ) );
				if ( checkObjectNo.equals("")) {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					DTOSet retDTOSet = this.handleLines(lines, checkObjectNo , req);
					this.saveBarcodePrintHistory( user, retDTOSet, conn);
					req.setAttribute(QueryConstant.QUERY_DTO, retDTOSet);
					forwardURL = URLDefineList.OBJECT_PRINT_DETAIL_PAGE;
				}
			} else if (action.equals("CHOSE_LOCDESC_ACTION")){
				//dto.setLoc3Code("在用");
				if (!user.getIsTt().equals("Y")) {
					if (provinceCode.equals("25")||provinceCode.equals("33")) {
						dto.setLoc3Code(SinoConfig.getEamLoc3ASet_Name());
					} else {
						dto.setLoc3Code(SinoConfig.getEamLoc3Set_Name());
					}
				} else {
					if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
						dto.setLoc3Code(SinoConfig.getEamLoc3TdSet_Name());
					} else {
						dto.setLoc3Code(SinoConfig.getEamLoc3Set_Name());
					}
				}
				if (StrUtil.isNotEmpty(dto.getWorkorderObjectNo())) {
					EtsObjectDTO objectDTO = objectDAO.getEtsObjectLocInfo();
					dto.setCountyCode(objectDTO.getCountyCode());
					dto.setLoc2Code(objectDTO.getLoc2Code());
					dto.setCountyName(objectDTO.getCountyName());
					dto.setLocation(objectDTO.getLocation());
				}
				
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dto);
				forwardURL = "/system/object/choseLocation.jsp";
			} else if (action.equals("CONFIRM_CHOSE_LOCDESC_ACTION")) {
				String strCode = "000";
				if (!user.getIsTt().equals("Y")) {
					if (provinceCode.equals("25")||provinceCode.equals("33")) {
						strCode = "A01" ;
					}
				} 
				String workorderObjectCode = dto.getCountyCode() + "." + dto.getLoc2Code() + "." + strCode; //组合地点代码
				String workorderObjectName = dto.getCountyName() + "." + dto.getLocation() + "." + dto.getLoc3Code(); //组合地点名称
				dto.setWorkorderObjectCode(workorderObjectCode);
				dto.setWorkorderObjectName(workorderObjectName);
				boolean isExistWorkorderObjectName = objectDAO.existWorkorderObjectName();
				boolean isExistWorkorderObjectCode = objectDAO.existObject();
				String objectNo = "";
				if (!isExistWorkorderObjectName && !isExistWorkorderObjectCode) {
					if (objectDAO.addObject()) {
						objectNo = objectDAO.getWorkorderObjectNo();
					}
				} else {
					objectNo = objectDAO.getWorkorderObjectNo();
				}
				processAjaxResponse(res, objectNo, AJAX_RES_TXT);
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			if(!forwardURL.equals("")){
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
	
	/**
     * 功能：处理Ajax请求
     *
     * @param res        Http响应对象
     * @param resContent Http响应内容
     * @param resType    AJAX类型
     * @throws ServletException 响应Ajax出错时抛出该异常
     */
    private static void processAjaxResponse(HttpServletResponse res, Object resContent, String resType) throws ServletException {
        PrintWriter out = null;
        try {
            if (resContent != null) {
                resType = resType.toUpperCase();
                if (resType.equals(AJAX_RES_TXT)) {
                    res.setContentType("text/plain;charset=GBK");
                } else if (resType.equals(AJAX_RES_XML)) {
                    res.setContentType("text/xml;charset=GBK");
                } else if (resType.equals(AJAX_RES_HTM)) {
                    res.setContentType("text/html;charset=GBK");
                }
                out = res.getWriter();
                out.print(resContent);
            }
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new ServletException(ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
	private String[] getObjectNos(HttpServletRequest req) throws UploadException {
        String[] objectNos = null;
        try {
            RequestParser parser = new RequestParser();
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            parser.setCheckBoxProp(checkProp);
            parser.transData(req);
            objectNos = parser.getParameterValues("workorderObjectNo");
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        }
        return objectNos;
    }
	
	public DTOSet getDTOSetFromReq(HttpServletRequest req) throws DTOException {
		Request2DTO req2DTO = new Request2DTO();
		req2DTO.setDTOClassName( CommonObjectPrintDTO.class.getName() );
		req2DTO.setIgnoreFields( EtsObjectDTO.class );
		return req2DTO.getDTOSet(req);
	}

	@SuppressWarnings( { "unchecked" })
	private DTOSet handleLines(DTOSet lines, String objectNos,
			HttpServletRequest req) throws IOException, DTOException, DataHandleException {
		DTOSet retDTOSet = new DTOSet();
		BarCodeService service = new BarCodeService(req);
		List<String> objectNoLst = ArrUtil.arrToList(objectNos.split(","));
		CommonObjectPrintDTO line = null;
		String barcodeImg = null;
		for (int i = 0; i < lines.getSize(); i++) {
			line = (CommonObjectPrintDTO) lines.getDTO(i);
			if ( objectNoLst.contains(line.getObjectNo())) {
				barcodeImg = service.createScancodePic( getSimpleObjectCode( line.getObjectNo() ) );
				line.setObjectNoImg( barcodeImg );
				retDTOSet.addDTO(line);
			}
		}
		return retDTOSet;
	}
	
	/**
	 * 标签打印历史
	 * @param userAccount
	 * @param retDTOSet
	 * @param conn
	 * @throws SQLException
	 */
	private void saveBarcodePrintHistory(SfUserDTO userAccount,
			DTOSet retDTOSet, Connection conn) throws SQLException {
		boolean isAuto = conn.getAutoCommit();
		conn.setAutoCommit(false);
		boolean isSuccess = false;
		try {
			EtsBarcodePrintHistoryDTO historyDTO = null;
			CommonObjectPrintDTO dtoParameter = null;
			EtsBarcodePrintHistoryDAO historyDAO = null;
			for (int i = 0; i < retDTOSet.getSize(); i++) {
				dtoParameter = (CommonObjectPrintDTO) retDTOSet.getDTO(i);
				historyDTO = new EtsBarcodePrintHistoryDTO();
				historyDTO.setBarcode( dtoParameter.getObjectNo() );
				historyDTO.setType("地点标签打印");
				historyDAO = new EtsBarcodePrintHistoryDAO(userAccount,
						historyDTO, conn);
				historyDAO.createData();
			}
			isSuccess = true;
		} catch (DataHandleException e) {
			e.printStackTrace();
		} finally {
			if (isSuccess) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(isAuto);
		}
	}
	
	public String getSimpleObjectCode(String objectNo ) throws DataHandleException{
		int startPos = objectNo.indexOf( "." );
		int endPos = objectNo.lastIndexOf( "." );
		if( startPos == endPos || startPos == -1 || endPos == -1 ){
			return objectNo;
		}else{
			return objectNo.substring( startPos + 1 , endPos );
		}
	}
}
