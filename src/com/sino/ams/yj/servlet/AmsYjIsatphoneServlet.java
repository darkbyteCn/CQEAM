package com.sino.ams.yj.servlet;

import java.sql.Connection;
import java.io.IOException;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.base.constant.message.MsgKeyConstant;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import com.sino.ams.yj.dto.AmsYjIsatphoneDTO;
import com.sino.ams.yj.model.AmsYjIsatphoneModel;
import com.sino.ams.yj.util.YjManagerUtil;
import com.sino.ams.yj.dao.AmsYjIsatphoneDAO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;

/**
 * <p>Title: AmsYjIsatphoneServlet</p>
 * <p>Description:程序自动生成服务程序“AmsYjIsatphoneServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-卫星电话维护
 */
public class AmsYjIsatphoneServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsYjIsatphoneDTO.class.getName());
			AmsYjIsatphoneDTO dtoParameter = (AmsYjIsatphoneDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			AmsYjIsatphoneDAO amsYjIsatphoneDAO = new AmsYjIsatphoneDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
            String ouoption = prd.getAllOrganizationYj(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);

            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String opt = optProducer.getAllOu(dtoParameter.getOrganizationId());
            dtoParameter.setOrganizationOption(opt);

            if (action.equals("")) {
				forwardURL = "/yj/yjIsatPhoneSearch.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsYjIsatphoneModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "/yj/yjIsatPhoneSearch.jsp";
			}else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsYjIsatphoneDTO amsYjIsatphone = new AmsYjIsatphoneDTO();
                amsYjIsatphone.setOrganizationOption(prd.getAllOrganization(dtoParameter.getOrganizationId(), false));
                req.setAttribute("AMS_YJ_ISATPHONE", amsYjIsatphone);
                forwardURL = "/yj/yjIsatPhoneDetail.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsYjIsatphoneDAO.setDTOClassName(AmsYjIsatphoneDTO.class.getName());
                AmsYjIsatphoneDTO amsYjIsatphone = (AmsYjIsatphoneDTO) amsYjIsatphoneDAO.getDataByPrimaryKey();
                if (amsYjIsatphone == null) {
                    amsYjIsatphone = new AmsYjIsatphoneDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                opt = optProducer.getAllOu(amsYjIsatphone.getOrganizationId());
                amsYjIsatphone.setOrganizationOption(opt);
                req.setAttribute("AMS_YJ_ISATPHONE", amsYjIsatphone);
                forwardURL = "/yj/yjIsatPhoneDetail.jsp";
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                boolean isNew = StrUtil.isEmpty(dtoParameter.getIsatphoneId());
              	YjManagerUtil yjUtil= new YjManagerUtil(user, conn);
                if (isNew) {
                    dtoParameter.setIsatphoneId(String.valueOf(yjUtil.getYjManagerMax("AMS_YJ_ISATPHONE_SEQ")));
                }
                amsYjIsatphoneDAO.createData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?act=" + WebActionConstant.QUERY_ACTION + "&isatphoneId=" + dtoParameter.getIsatphoneId();
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsYjIsatphoneDAO.updateData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?act=" + WebActionConstant.QUERY_ACTION + "&isatphoneId=" + dtoParameter.getIsatphoneId();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {       //删除
                amsYjIsatphoneDAO.deleteData();
                forwardURL = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = amsYjIsatphoneDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
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
		} catch (DataHandleException ex) {
			ex.printLog();
			//请根据实际情况处理消息
			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
		} catch (WebFileDownException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}