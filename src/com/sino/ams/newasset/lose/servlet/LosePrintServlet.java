package com.sino.ams.newasset.lose.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.ams.newasset.lose.constant.LoseURLListConstant;
import com.sino.ams.newasset.lose.dto.LoseDTO;
import com.sino.ams.newasset.lose.dto.LoseHeaderDTO;
import com.sino.ams.newasset.lose.model.LoseModel;
import com.sino.ams.newasset.lose.service.LoseService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
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
public class LosePrintServlet extends LoseServlet {

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
		try {
			user =  (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			loseDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			action = loseDTO.getAct();
			service = new LoseService( user , loseDTO , conn );
			
			if( action.equals( "" ) ){
				req.setAttribute(QueryConstant.QUERY_DTO, loseDTO.getHeaderDTO() );
				forwardURL = LoseURLListConstant.LOSE_PRINT_PAGE ;
			}else if( action.equals( WebActionConstant.QUERY_ACTION ) ){
				LoseHeaderDTO header = loseDTO.getHeaderDTO();
				header.setTransStatus( AssetsDictConstant.COMPLETED );
				BaseSQLProducer sqlProducer = new LoseModel(user, header );
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData(); 
				req.setAttribute(QueryConstant.QUERY_DTO, loseDTO.getHeaderDTO() );
				forwardURL = LoseURLListConstant.LOSE_PRINT_PAGE ;
			}else if( action.equals( WebActionConstant.DETAIL_ACTION ) ){
				service.prodData();
				loseDTO = service.getForm(); 
//				loseDTO = setOption(conn, req, userInfo, applyDTO);
                req.setAttribute( AssetsWebAttributes.ORDER_HEAD_DATA , loseDTO );
                
                String tableName = "AMS_ASSETS_TRANS_HEADER";
                RowSet rows = null;
				rows = ApproveContentDAO.getApproveContent(conn, loseDTO.getHeaderDTO().getTransId() , tableName);
				req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT, rows);
				
                forwardURL = LoseURLListConstant.LOSE_PRINT_DETAIL_PAGE ;
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
		} finally {
//			DBManager.closeDBConnection( conn );
			closeDBConnection(conn);
			ServletForwarder forwarder = new ServletForwarder(req, res);

			if (!StrUtil.isEmpty(forwardURL)) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, msg);
			}
		}
	}

}
