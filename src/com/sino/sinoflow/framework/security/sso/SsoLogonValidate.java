package com.sino.sinoflow.framework.security.sso;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.SinoEncryptor;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.framework.security.model.UserLoginModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.utilities.CaseRoutine;

public class SsoLogonValidate extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private final String PINDINGTRAYCOUNT = "pindingtrayCount"; 
	private final String LOGINNAME = "loginName";
	public static final String UID = "uid";
	public static final String EKEY = "sinocms";
	private final String SOURCE = "source";
	
	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String loginName = req.getParameter(UID);
		if(loginName == null) loginName = "";
		
		//decryption loginName
		try{
			loginName = SinoEncryptor.decode(EKEY, loginName);	
		}catch(Exception e){	
			e.printStackTrace();
		}
	
		String ptCount = req.getParameter(PINDINGTRAYCOUNT);
		
		if(ptCount != null && ptCount.equals("Y")){
			parsePindingtrayCount(req,res,loginName);
		}else{
			req.setAttribute("loginSourceType", "SSO");
			FilterConfigDTO filterConfig = SessionUtil.getFilterConfigDTO(req);
			String urlHead = filterConfig.getLoginServlet() + "?" + LOGINNAME + "=";			
			String forwardURL = urlHead + loginName;
			String source = req.getParameter(SOURCE);
			if(source != null && source.equals("selectRole")){
			}else{
				OaUserMatchService matchService = new OaUserMatchService();
				DTOSet dtoSet = matchService.getCmsLoginNames(loginName);
				if(dtoSet != null){
					if(dtoSet.getSize() == 1){
					   OaUserMatchForm oForm = (OaUserMatchForm)dtoSet.getDTO(0);
					   loginName = oForm.getCmsLoginName();	
					   forwardURL = urlHead + loginName;
					}else{
					   req.setAttribute("dtoSet",dtoSet);	
					   forwardURL = "/sso/select_role.jsp";
					}
				}else{}
			}
						
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}	
	}
	
	private void parsePindingtrayCount(HttpServletRequest req,
			 HttpServletResponse res,String loginName){
		Connection conn = null;
		int count = 0;
		
		try {
			conn = getDBConnection(req);
			SfUserBaseDTO bDTO = new SfUserBaseDTO();
			bDTO.setLoginName(loginName);
						
			SQLModel sqlModel = new UserLoginModel(bDTO).getUserLoginModelForSSO();			
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.setDTOClassName(SfUserBaseDTO.class.getName());
			simp.executeQuery();
			DTOSet ds = simp.getDTOSet();
			if(!ds.isEmpty()){
				SfUserBaseDTO suDTO = (SfUserBaseDTO)ds.getDTO(0);
				CaseRoutine cr = new CaseRoutine();
				count = cr.getPendingTrayCount(suDTO.getUserId(), conn);	
			}else{}
		} catch (PoolPassivateException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeDBConnection(conn);
		}
		
		try {
			ServletOutputStream sos = res.getOutputStream();
			sos.print(getPedningCountStr(count));
			sos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private String getPedningCountStr(int count){
		StringBuffer sb = new StringBuffer("");
		
		sb.append("try{");
		sb.append("var heTongCount=" + Integer.toString(count) + ";");
		sb.append("}catch(e){}");
		
		return sb.toString();
	}
	
	public static void main(String[] args){
		SsoLogonValidate sv = new SsoLogonValidate();
		String cc  = SinoEncryptor.encode(sv.EKEY,"cmsadmin");
		System.out.println("cc =" + cc);
	}
}
