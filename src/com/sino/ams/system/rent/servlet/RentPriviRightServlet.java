package com.sino.ams.system.rent.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.rent.dao.AmsRentPriviDAO;
import com.sino.ams.system.rent.dto.AmsRentPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

   /**
     * <p>Title: SinoEAM</p>
     * <p>Description: 山西移动实物资产管理系统</p>
     * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
     * <p>Company: 北京思诺博信息技术有限公司</p>
     * @author 唐明胜
     * @version 1.0
     */
public class RentPriviRightServlet  extends BaseServlet{
        /**
         * 所有的Servlet都必须实现的方法。
         * @param req HttpServletRequest
         * @param res HttpServletResponse
         * @throws javax.servlet.ServletException
         * @throws java.io.IOException
         */
        public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            String forwardURL = "";
            Message message = SessionUtil.getMessage(req);
            Connection conn = null;
            try {
                SfUserDTO user = (SfUserDTO) getUserAccount(req);
                Request2DTO req2DTO = new Request2DTO();
                req2DTO.setDTOClassName(AmsRentPriviDTO.class.getName());
                AmsRentPriviDTO dto = (AmsRentPriviDTO)req2DTO.getDTO(req);
                String roleName = dto.getRoleName();
                String companyName = dto.getCompanyName();
                String deptName = dto.getDeptName();
                ServletConfigDTO servletConfig = getServletConfig(req);
                if (StrUtil.isEmpty(deptName) && roleName.indexOf(servletConfig.getDeptAssetsMgr()) > -1) {
                    message = getMessage(AssetsMessageKeys.NOT_CHECK_DEPT);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else if (StrUtil.isEmpty(companyName) && roleName.indexOf(servletConfig.getCompAssetsMgr()) > -1) {
                    message = getMessage(AssetsMessageKeys.NOT_CHECK_COMPANY);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                } else {
                    conn = getDBConnection(req);
                    AmsRentPriviDAO priviDAO = new AmsRentPriviDAO(user, dto, conn);
                    priviDAO.setServletConfig(servletConfig);
                    String allUserOptions = priviDAO.getAllUsersOption();
                    String[] userIds = req.getParameterValues("userIds");
                    String existUserOptions = priviDAO.getExistUsersOption(userIds);
                    req.setAttribute(AssetsWebAttributes.ALL_USER_OPTION, allUserOptions);
                    req.setAttribute(AssetsWebAttributes.EXIST_USER_OPTION, existUserOptions);
                    req.setAttribute(AssetsWebAttributes.PRIVI_DATA, dto);
//                    forwardURL = AssetsURLList.ASSETS_PRIVI_RIGHT;
                    forwardURL = "/system/rent/rentPriviRight.jsp";
                }
            } catch (PoolPassivateException ex) {
                ex.printLog();
                message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            } catch (QueryException ex) {
                ex.printLog();
                message = getMessage(AssetsMessageKeys.QUERY_ERROR);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            } catch (DTOException ex) {
                ex.printLog();
                message = getMessage(AssetsMessageKeys.DTO_ERROR);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            } finally {
                closeDBConnection(conn);
                setHandleMessage(req, message);
                if(!StrUtil.isEmpty(forwardURL)){
                    ServletForwarder forwarder = new ServletForwarder(req, res);
                    forwarder.forwardView(forwardURL);
                }
            }
        }
    }

