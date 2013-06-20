package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.TrunListQueryDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.TrunListQueryModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Author:		李轶
 * Date: 2009-6-4
 * Time: 9:29:22
 * Function:	转资清单查询Servlet
 */
public class TrunListQueryServlet extends BaseServlet {
	@SuppressWarnings("unchecked")
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		boolean bo=false;
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);//从session中获取数据，根据实际情况自行修改。
			EtsWorkorderDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
			dtoParameter = (EtsWorkorderDTO) req2DTO.getDTO(req);
			int organizationId = StrUtil.isEmpty(dtoParameter.getOrganizationId()) ? user.getOrganizationId() : dtoParameter.getOrganizationId();
			//组织
			OptionProducer prd = new OptionProducer(user, conn);
			String ouoption = "";
			
			if("82".equals(user.getOrganizationId())){
				if("".equals(dtoParameter.getOrganizationId())){
					ouoption = prd.getAllOrganization(0, true);
				}else{
					ouoption = prd.getAllOrganization(organizationId, true);
				}					
			}else{
				ouoption = prd.getAllOrganization(organizationId);
			}
			req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);
			
			String faOption = this.getFAOption( dtoParameter.getFinanceProp() );
			req.setAttribute("FA_OPTION" , faOption );
			
            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());
			req.setAttribute("CATEGORY", cat);

			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			if (action.equals("")) {
				String deptOption = prd.getDeptOptionByOrgId(organizationId, dtoParameter.getDeptCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				forwardURL = URLDefineList.TRUN_LIST_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String deptOption = prd.getDeptOptionByOrgId(organizationId, dtoParameter.getDeptCode());//得到地市下的所有领用部门
				req.setAttribute(WebAttrConstant.COUNTY_OPTION, deptOption);
				req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
				BaseSQLProducer sqlProducer = new TrunListQueryModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.TRUN_LIST_QUERY;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				TrunListQueryDAO dao = new TrunListQueryDAO(user, dtoParameter, conn);
				File file = dao.getExportFile(dtoParameter);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else if (action.equals("addressQy")) {
				try {
					List addList = new ArrayList();
					req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
					TrunListQueryModel trunListQueryModel = new TrunListQueryModel(user, dtoParameter);
					//TrunListQueryDAO dao = new TrunListQueryDAO(user, dtoParameter, conn);
					//添加当前用户要导入的BARCODE记录到表IMP_BARCODE中
//					SQLModel insertSqlModel = new SQLModel();
					//可能需要做重复判断
					String sqlAddressQy = trunListQueryModel.getAddressDataQueryModel(dtoParameter.getPrjId(),dtoParameter.getContentCode());
					Statement statement = null;
					ResultSet set = null; 
					
					try {
						statement = conn.createStatement();
						set =statement.executeQuery(sqlAddressQy); 					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new SQLException(e);
					}   
					Map<String,String> map=new HashMap<String,String>();
					while (set.next()) {
						EtsWorkorderDTO dtoPa  = new EtsWorkorderDTO();
						dtoPa.setContentCode(set.getString("ADDRESS_ID"));//操作参数1
						dtoPa.setPrjId(set.getString("PROJECTID"));//操作参数2
						dtoPa.setWorkorderObjectCode(set.getString("WORKORDER_OBJECT_CODE"));//地点编码
						dtoPa.setWorkorderObjectName(set.getString("WORKORDER_OBJECT_NAME"));//地点名称
						dtoPa.setProjectCode(set.getString("SEGMENT1"));//工程编码
						dtoPa.setProjectName(set.getString("NAME"));//工程名称
						String workCode=dtoPa.getWorkorderObjectCode();
						String proCode=dtoPa.getProjectCode();
						String key=workCode.trim()+proCode.trim();
						if (!map.containsKey(key)) {
							map.put(key, key);
							addList.add(dtoPa);
						}
						
						
					}
					
					req.setAttribute("addList", addList);
					
//					DBOperator.updateRecord(insertSqlModel, conn);
					forwardURL = "/workorder/trunListAddressQuery.jsp";
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            }else if (action.equals("CONFIRM")) {
				try {
					TrunListQueryModel trunListQueryModel = new TrunListQueryModel(user, dtoParameter);
					//TrunListQueryDAO dao = new TrunListQueryDAO(user, dtoParameter, conn);
					//添加当前用户要导入的BARCODE记录到表IMP_BARCODE中
//					SQLModel insertSqlModel = new SQLModel();\
					String str[]= req.getParameterValues("params");
					
					for (int i = 0; i < str.length; i++) {
						String index=str[i];
						String arry[]=index.split("-");
						String prjId=arry[0];
						String contextId=arry[1];
						
						if (prjId!=null&&prjId!=""
							 &&contextId!=null&&contextId!="") {
							//可能需要做重复判断
							String sqlInsert = trunListQueryModel.getDataInsertModel(prjId,contextId);
							Statement statement = null;
							try {
								statement = conn.createStatement();
								statement.execute(sqlInsert);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new SQLException(e);
							} 
							
							String sqlDelete = trunListQueryModel.getDelInsertModel(prjId,contextId);
							
							Statement statementDel = null;
							try {
								statementDel = conn.createStatement();
								statementDel.execute(sqlDelete);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new SQLException(e);
							}  
						}
						
						
					}
//					DBOperator.updateRecord(insertSqlModel, conn);
//					forwardURL = "/servlet/com.sino.ams.workorder.servlet.TrunListQueryServlet";
//					forwardURL += "?act=" + "";
					forwardURL = "/workorder/trunListAddressQuery.jsp";
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            }else{
                message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
	
	/**
	 * 功能：构造是否下拉框
	 * @param selectedValue String
	 * @return String
	 */
	public String getFAOption(String selectedValue) {
		StringBuffer strOpt = new StringBuffer();
		if (selectedValue == null) {
			selectedValue = "";
		} 
		strOpt.append("<option value=\"");
		strOpt.append( "ASSETS" );
		strOpt.append("\"");
		if (selectedValue.equals( "ASSETS" )) {
			strOpt.append(" selected");
		}
		strOpt.append(">资产</option>");
		strOpt.append("<option value=\"");
		strOpt.append( "PRJ_MTL" );
		strOpt.append("\"");
		if (selectedValue.equals( "PRJ_MTL" )) {
			strOpt.append(" selected");
		}
		strOpt.append(">预转资</option>");
		
		
		//待确认转资资产
		strOpt.append("<option value=\"");
		strOpt.append( "CFM_PRJ_MTL" );
		strOpt.append("\"");
		if (selectedValue.equals( "CFM_PRJ_MTL" )) {
			strOpt.append(" selected");
		}
		strOpt.append(">确认预转资</option>");
		
		return strOpt.toString();
	}
	
}
