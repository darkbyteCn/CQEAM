package com.sino.ams.important.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.important.business.ImpInfoDAO;
import com.sino.ams.important.dto.ImpInfoDTO;
import com.sino.ams.system.manufacturer.EtsManufacturerDTO;
import com.sino.ams.system.manufacturer.dao.ManufacturerDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: yu
 * Date: 2007-5-28
 * Time: 14:10:19
 * To change this template use File | Settings | File Templates.
 * update User : wangzhipeng
 * Date: 2011-04-06
 *  修改：路径跳转方向
 */
public class ImpInfoServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
        Connection conn = null;
        String forward = StrUtil.nullToString(req.getParameter("forward"));
        String message = "";
        SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(req);
        boolean operateResult = false;
        try {
            conn = DBManager.getDBConnection();
            ImpInfoDAO impDAO = new ImpInfoDAO(req, conn);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ImpInfoDTO.class.getName());
            ImpInfoDTO dto = (ImpInfoDTO) req2DTO.getDTO(req);
            dto.setPublishUserId( userAccount.getUserId() );
            impDAO.setModelDTO(dto);      

			req.setAttribute(WebAttrConstant.IMPINFO_DTO, dto);
   
            if (forward.equals("")) {
                forwardURL = "/system/important/impInfo.jsp";
            }            
            else if (forward.equals("CREATE_ACTION")) {  //创建
                forwardURL = "/system/important/impInfoAdd.jsp";
            } else if (forward.equals("show_info")) {    //  查询
                impDAO.ProdAllUsersData(req, conn);
                forwardURL = "/system/important/impInfo.jsp";
            } else if (forward.equals("show_detail")) {  //首页显示
                impDAO.getInfoDetail(req, conn);
                forwardURL = "/system/important/impInfoShow.jsp";
            } else if (forward.equals("show")) {         //显示
                impDAO.getInfoDetail(req, conn);
                forwardURL = "/system/important/impInfoEdit.jsp";
            } else if (forward.equals("show_title")) {    //首页显示所有标题                              
                impDAO.ProdAllTitleData(req,conn);
                forwardURL = "/system/important/impInfoMore.jsp";
            } else if (forward.equals("show_all")) {
                impDAO.ProdAllTitleData(req, conn);
                forwardURL = "/system/important/impInfoMore.jsp";
            } else if (forward.equals("edit")) {           //显示修改页面
            	dto = impDAO.getInfoDetail(req, conn);
                req.setAttribute(WebAttrConstant.IMPINFO_DTO, dto);
                forwardURL = "/system/important/impInfoSave.jsp";
            } else if (forward.equals("save")) {           //修改页面的保存按钮
                impDAO.savePublishInfo();
                dto = impDAO.getDto();
                req.setAttribute(WebAttrConstant.IMPINFO_DTO, dto);
                message = "修改成功";
                forwardURL = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=edit";
            } else if (forward.equals("add_User")) {        //增加按钮
            	message = "新增失败";
            	boolean isSuccess = impDAO.savePublishInfo();
            	dto = impDAO.getDto();
            	if( isSuccess ){
            		message = "新增成功";
            		req.setAttribute(WebAttrConstant.IMPINFO_DTO, dto);
            		forwardURL = "/system/important/impInfoSave.jsp";
//            		forwardURL = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=edit";
            	}else{
            		message = "新增失败";
            		forwardURL = "/system/important/impInfoAdd.jsp";
            	}
            }
            operateResult = true;
        } catch (Throwable e) {
        	Logger.logError(e);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                Logger.logError(e);
            }
            DBManager.closeDBConnection( conn );
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }
    }
	
	/**
     * 首页取得重要信息
     * @return RowSet
     * @throws QueryException
     */
    public static RowSet getHomeImportantInfo() throws QueryException {
        Connection conn = null;
        SimpleQuery gq = null;
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = new SQLModel();
            String sqlStr =
                    "SELECT *\n" +
                            "  FROM (SELECT PUBLISH_ID, TITLE, PUBLISH_DATE\n" +
                            "          FROM AMS_INFO_PUBLISH\n" +
                            "         WHERE INFO_TYPE = '1' " +
                            "          AND DISABLED='N' \n" +
                            //"          AND PURCHASE_GUIDE_TYP " + SyBaseSQLUtil.isNull() + "  \n" +
                            //"          AND SEE_USER_TYPE = ?\n" +
                            "         ORDER BY PUBLISH_DATE DESC)\n" +
                            " WHERE ROWNUM < 12";

            List sqlArgs = new ArrayList();
            //sqlArgs.add(userType);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            gq = new SimpleQuery(sqlModel, conn);
            gq.setCalPattern(CalendarConstant.LINE_PATTERN);
            gq.executeQuery();
        } catch (PoolException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);

        }
        if (gq != null) {
            return gq.getSearchResult();
        } else {
            return new RowSet();
        }
    }
}
