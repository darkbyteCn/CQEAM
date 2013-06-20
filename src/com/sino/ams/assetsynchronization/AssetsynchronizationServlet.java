package com.sino.ams.assetsynchronization;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.manufacturer.EtsManufacturerDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.assetsynchronization.Model.AssetsynchronizationModel;
import com.sino.ams.assetsynchronization.dao.AssetsynchronizationCreateDao;
import com.sino.ams.assetsynchronization.dto.AssetsynchronizationDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class AssetsynchronizationServlet extends BaseServlet{

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
	        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
	        Request2DTO req2DTO = new Request2DTO();
	        req2DTO.setDTOClassName(AssetsynchronizationDTO.class.getName());
	        AssetsynchronizationDTO dtoParameter = (AssetsynchronizationDTO) req2DTO.getDTO(req);
	        conn = getDBConnection(req);
	        String action=dtoParameter.getAct();
	        OptionProducer  op = new OptionProducer(user, conn);
	        String cityOption1 = op.getAllOrganization(dtoParameter.getOrganizationId(),true);        
	        req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);
	        if(action.equals("")){
	        	forwardURL = "/assetsynchronization/assetsynchronization.jsp";
	        }else if (action.equals("query")){
	        	BaseSQLProducer model=new AssetsynchronizationModel(user, dtoParameter);
	        	PageQueryDAO pageDAO = new PageQueryDAO(req, conn, model);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData(); 
	        	forwardURL = "/assetsynchronization/assetsynchronization.jsp";
	        }else if(action.equals("create")){
	        	forwardURL = "/assetsynchronization/assetsynchronizationCreate.jsp";
			}else if(action.equals("delete")){
				AssetsynchronizationCreateDao assetsynchronizationCreateDao=new AssetsynchronizationCreateDao(user, dtoParameter, conn);
				this.getManufacturerIds(req, dtoParameter,assetsynchronizationCreateDao);
				forwardURL = "/assetsynchronization/assetsynchronization.jsp";
			}else if(action.equals("createDate")){
				AssetsynchronizationCreateDao assetsynchronizationCreateDao=new AssetsynchronizationCreateDao(user, dtoParameter, conn);
				assetsynchronizationCreateDao.AssetsynchronizationCreateDao(dtoParameter);
				forwardURL = "/assetsynchronization/assetsynchronization.jsp";
			}else if(action.equals("detail")){
				BaseSQLProducer model=new AssetsynchronizationModel(user, dtoParameter);
	        	PageQueryDAO pageDAO = new PageQueryDAO(req, conn, model);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData(); 
				forwardURL = "/assetsynchronization/assetsynchronizationDetail.jsp";
			}else if(action.equals("UPDATE")){
				AssetsynchronizationCreateDao assetsynchronizationCreateDao=new AssetsynchronizationCreateDao(user, dtoParameter, conn);
				assetsynchronizationCreateDao.updateData(dtoParameter);
				forwardURL = "/assetsynchronization/assetsynchronization.jsp";
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
        }
        catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }    
	}
	private void getManufacturerIds(HttpServletRequest req, AssetsynchronizationDTO dtoParameter ,AssetsynchronizationCreateDao assetsynchronizationCreateDao) throws UploadException {
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            String[] exarr = reqParser.getParameterValues("check");
            if (exarr != null) {
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < exarr.length; i++) {
                    dtoParameter.setEmployeeNumber(exarr[i]);
    				try {
						assetsynchronizationCreateDao.deleteData(dtoParameter);
					} catch (DataHandleException e) {
						e.printStackTrace();
					}
                }  
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        }
  }
}
