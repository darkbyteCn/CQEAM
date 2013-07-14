package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.util.ReadAddressExcel;
import com.sino.ams.newasset.allocation.model.AmsAssetsAllocationHeaderModel;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsFaDictDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsFaDictDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckFaDAO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckFaModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.ReadAssetsExcel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * 
 * @author agenty 登陆 查询 确认 导入 导出
 * 
 *         任务完成进度： 新建盘点确认表：ETS_ITEM_INFO_CHECK
 */
public class AssetsYearCheckFaServlet extends BaseServlet {
	
	private static final int startRowNum = 4;
	private static final int columnNum = 5;

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemYearCheckDTO.class.getName());
			EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsYearCheckFaDAO assetsDAO = new AssetsYearCheckFaDAO(user,
					dto, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			assetsDAO.setServletConfig(getServletConfig(req));
			String checkStatusOption = setOpt(dto
					.getCheckStatus());
			dto.setCheckStatusOption(checkStatusOption);
			String pattern=req.getParameter("action");//页面跳转方式
			String orderNumber=req.getParameter("parentOrderNumber");//工单编码
			String orderType=req.getParameter("orderType");//工单类型
			String orderName=req.getParameter("orderName");//工单名称
			if(pattern==null){pattern="";}
            if(pattern.equals("fromRemain")){
            	dto.setOrderName(orderName);
            	dto.setOrderNumber(orderNumber);
            	dto.setOrderType(orderType);
            }			
            HttpSession session=req.getSession();
            session.setAttribute("req", "");
			
			if (action.equals("")) {
				dto.setCalPattern(LINE_PATTERN);
				DTOSet ds =new DTOSet();
				if(pattern.equals("fromRemain")){
					//come from page url
					ds = (DTOSet) assetsDAO.getLineData();
				}
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckFa.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				DTOSet ds = (DTOSet) req
						.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
				if (ds == null) {
					ds = (DTOSet) assetsDAO.getLineData();
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckFa.jsp";
			} else if (action.equals(AssetsActionConstant.CONFIRM_ACTION)) {
				req2DTO.setDTOClassName(EtsItemYearCheckLineDTO.class.getName());
			    req2DTO.setIgnoreFields(EtsItemYearCheckDTO.class);
			    DTOSet orderLines = req2DTO.getDTOSet(req);
		        String str=req.getParameter("typeStr");
		        assetsDAO.confirm(str);
		        DTOSet ds = (DTOSet) assetsDAO.getLineData();
		        req.setAttribute(QueryConstant.QUERY_DTO, dto);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckFa.jsp";
			} else if (action.equals("EXPORT")) { //导出全年非实地盘点地点资产信息 String
				String exportType = dto.getExportType();
                File file = null;
                file = assetsDAO.exportFile(user, dto, conn);
//                file = assetsDAO.exportClientFile(user, dto, conn);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
			}else if(action.equals("excel")){//导入全年非实地盘点地点资产信息
	                String excel2 = req.getParameter("excel");
	                DTOSet dtoSet = null;
	                boolean parseError = false;
	                try {
	                    dto.setExcel(excel2);
	                    dtoSet = readDataFrmExcel(req);
	                } catch (Throwable ex) {
	                    message = new Message();
	                    message.setMessageValue("解析Excel数据失败");
	                    message.setIsError(true);
	                    parseError = true;
	                }
	            	DTOSet errSet=new DTOSet();
	            	DTOSet succSet=new DTOSet();
	                if(!parseError){
	                	DTOSet ds = (DTOSet) assetsDAO.getLineData();
	                	Map<String,String> map=new HashMap<String,String>();
	                
	                	for (int i = 0; i < ds.getSize(); i++) {
	                		EtsItemYearCheckLineDTO lineDTO=(EtsItemYearCheckLineDTO) ds.getDTO(i);
	                		String barcode=lineDTO.getBarcode();
	                		map.put(barcode, barcode);
						}
	                	
	                	for (int i = 0; i < dtoSet.getSize(); i++) {
							EtsItemYearCheckLineDTO lineDTO=(EtsItemYearCheckLineDTO) dtoSet.getDTO(i);
							String barcode=lineDTO.getBarcode();
							if(map.containsKey(barcode)){
								lineDTO.setErrorMsg("资产不属于选择盘点任务下资产");
								errSet.addDTO(lineDTO);
							}else{
								succSet.addDTO(lineDTO);
							}
						}
	                }
	                if(errSet.getSize()==0){
	                	 message = new Message();
		                 message.setMessageValue("导入数据成功");
	                }else {
	                	message = new Message();
		                message.setMessageValue("成功导入数据"+succSet.getSize()+"条,失败"+errSet.getSize()+"条,请查看详细信息");
	                }
	                
	                req.setAttribute(QueryConstant.QUERY_DTO, dto);
	                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, succSet);
	                req.setAttribute("errorDTOSet", errSet);
	                session.setAttribute("errorDTOSet", errSet);
	                forwardURL = "/yearchecktaskmanager/assetsYearCheckFa.jsp";
			}else {
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
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (DataTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebFileDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	// 资产确认状态
	/*
	 * <option value='' selected >--请选择--</option> <option value='CHECKED'
	 * >盘到</option> <option value='CHECKED_LOSS' >盘亏</option> <option
	 * value='CHECKED_NOT_MACTH' >信息不符</option>
	 */
	private static String setOpt(String selectedValue) {
		StringBuffer str = new StringBuffer();
		ArrayList<String> checkedCode = new ArrayList<String>();
		checkedCode.add("");
		checkedCode.add(AssetsCheckTaskConstant.CHECKED);
		checkedCode.add(AssetsCheckTaskConstant.CHECKED_LOSS);
		checkedCode.add(AssetsCheckTaskConstant.CHECKED_NOT_MACTH);
		ArrayList<String> checkedName = new ArrayList<String>();
		checkedName.add("--请选择--");
		checkedName.add(AssetsCheckTaskConstant.CHECKED_NAME);
		checkedName.add(AssetsCheckTaskConstant.CHECKED_LOSS_NAME);
		checkedName.add(AssetsCheckTaskConstant.CHECKED_NOT_MACTH_NAME);
		for (int i = 0; i < checkedCode.size(); i++) {
			String code = checkedCode.get(i);
			if (code.equals(selectedValue)) {
				str.append("<option value='" + checkedCode.get(i)
						+ "' selected >" + checkedName.get(i) + "</option>");
			} else {
				str.append("<option value='" + checkedCode.get(i) + "' >"
						+ checkedName.get(i) + "</option>");
			}
		}

		return str.toString();
	}
	
	/**
     * 功能：读取Excel数据到DTOSet
     *
     * @param req 页面请求对象
     * @return DTOSet对象
     * @throws DTOException
     */
    private DTOSet readDataFrmExcel(HttpServletRequest req) throws DTOException {
        DTOSet dtoSet = null;
        try {
            String fileName = req.getParameter("excelPath");     //文件路径
            ReadAssetsExcel xlsUtil = new ReadAssetsExcel();
            xlsUtil.setFileName(fileName);
            xlsUtil.setNumberOfColumn(columnNum);      //设置列数8
            xlsUtil.setStartRowNum(startRowNum);       //开始行
            dtoSet = xlsUtil.readXls(0);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new DTOException(ex);
        }
        return dtoSet;
    }


}
