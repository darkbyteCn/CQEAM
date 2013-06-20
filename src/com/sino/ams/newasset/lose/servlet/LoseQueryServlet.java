package com.sino.ams.newasset.lose.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.ams.newasset.lose.constant.LoseURLListConstant;
import com.sino.ams.newasset.lose.dto.LoseDTO;
import com.sino.ams.newasset.lose.model.LoseModel;
import com.sino.ams.newasset.lose.service.LoseService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 
 * @系统名称: 单据查询
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 19, 2011
 */
public class LoseQueryServlet extends LoseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException {
		String forwardURL = "";
		String msg = "";
		String action = null;
		Connection conn = null;
		LoseService service = null;
		LoseDTO loseDTO = null;
		SfUserDTO user = null;
		boolean isReturn = false;
		try {
			user =  (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			loseDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			action = loseDTO.getAct();
			service = new LoseService( user , loseDTO , conn );
			
			if( action.equals( "" ) ){
				req.setAttribute(QueryConstant.QUERY_DTO, loseDTO.getHeaderDTO() );
				forwardURL = LoseURLListConstant.LOSE_QUERY_PAGE ;
			}else if( action.equals( WebActionConstant.QUERY_ACTION ) ){
				BaseSQLProducer sqlProducer = new LoseModel(user, loseDTO.getHeaderDTO() );
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData(); 
				req.setAttribute(QueryConstant.QUERY_DTO, loseDTO.getHeaderDTO() );
				forwardURL = LoseURLListConstant.LOSE_QUERY_PAGE ;
			}else if( action.equals( WebActionConstant.DETAIL_ACTION ) ){
				service.prodData();
				loseDTO = service.getForm();
				
				super.doApproveContent(req, conn, loseDTO.getHeaderDTO().getTransId() );				
//				loseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                forwardURL = LoseURLListConstant.LOSE_DETAIL_PAGE ;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                File file = service.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
                isReturn = true;
            }
			msg = service.getMsg(); 
		} catch (PoolPassivateException ex) {
			ex.printLog();
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
//			DBManager.closeDBConnection( conn );
			closeDBConnection(conn); 
			if( isReturn ){
				return;
			}
			
			ServletForwarder forwarder = new ServletForwarder(req, res);

			if (!StrUtil.isEmpty(forwardURL)) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, msg);
			}
		}
	}

}
