package com.sino.ams.workorderDefine.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorderDefine.dao.WorkorderDefineDAO;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;
import com.sino.ams.workorderDefine.model.WorkorderDefineModel;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class WorkorderDefineServlet extends BaseServlet {

    @Override
    public void performTask(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(WorkorderDefineDTO.class.getName());
            WorkorderDefineDTO dto = (WorkorderDefineDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            WorkorderDefineDAO dao = new WorkorderDefineDAO(user, dto, conn);

            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            if ("".equals(action)) {
                produceWebComponent(dto, user, optProducer);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/workorder/define/workorderDefineQuery.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                produceWebComponent(dto, user, optProducer);
                BaseSQLProducer sqlProducer = new WorkorderDefineModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/workorder/define/workorderDefineQuery.jsp";
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                produceWebComponent(dto, user, optProducer);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/workorder/define/workorderDefineEdit.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                WorkorderDefineDTO dataDto;
                if (dto != null && StrUtil.isNotEmpty(dto.getWorkorderDefineId())) {
                    dao.setDTOClassName(WorkorderDefineDTO.class.getName());
                    dataDto = (WorkorderDefineDTO) dao.getDataByPrimaryKey();
                } else {
                    dataDto = (WorkorderDefineDTO) req.getAttribute("DEFINE_WORKORDER");
                }
                produceWebComponent(dataDto, user, optProducer);
                req.setAttribute(QueryConstant.QUERY_DTO, dataDto);
                forwardURL = "/workorder/define/workorderDefineEdit.jsp";
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                if (dao.validateWorkorder(dto)) {
                    if (StrUtil.isEmpty(dto.getWorkorderDefineId())) {
                        SeqProducer seq = new SeqProducer(conn);
                        dto.setWorkorderDefineId(seq.getGUID());
                        dao.createData();
                    } else {
                        dao.updateData();
                    }
                    message = dao.getMessage();
                } else {
                    req.setAttribute("DEFINE_WORKORDER", dto);
                    message = new Message();
                    message.setIsError(true);
                    message.setMessageValue("该定义组合数量大于400,请重新定义！");
                }
                forwardURL = "/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet?act=" + WebActionConstant.DETAIL_ACTION;
            } else if (action.equals(AMSActionConstant.CHANGE_COUNTYS)) {
                String countyOptions = optProducer.getOuCountyOptions(dto.getCity(), "");
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print(countyOptions);
                out.close();
            } else if (action.equals("NOW_EXECUTE")) { //立即执行工单
                dto.setGroupId(user.getCurrGroupId());

                if (StrUtil.isEmpty(dto.getWorkorderDefineId())) {
                    SeqProducer seq = new SeqProducer(conn);
                    dto.setWorkorderDefineId(seq.getGUID());
                    dao.createData(); //如果新建的巡检自定义则西安保存
                } else {
                    dao.updateData(); //对已有的巡检自定义修改
                }
                //下发工单
                dao.createWorkorder(dto, conn);

                message = dao.getMessage();
                forwardURL = "/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet?act=" + WebActionConstant.QUERY_ACTION;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.SUBMIT_DATA_FAILURE);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }

    private void produceWebComponent(WorkorderDefineDTO dto, SfUserDTO user, AssetsOptProducer optProducer) throws QueryException {

        //公司
        String opt = optProducer.getAllOu(user.getOrganizationId());
        dto.setOrganizationName(opt);

        //地点专业
        String objectCateOpt = optProducer.getObjectCategoryOption(dto.getObjectCategory());
        dto.setObjectCategoryOption(objectCateOpt);

        //市
        String cityOpt = optProducer.getCityOption(dto.getCity());
        dto.setCityOption(cityOpt);

        //区县
        String countyOp = optProducer.getOuCountyOptions(dto.getCity(), dto.getCounty());
        dto.setCountyOption(countyOp);

        //成本中心
        String costCenterNameOpt = optProducer.getObjectCostCenterOption(user.getOrganizationId(), dto.getCostCenterCode());
        dto.setCostCenterName(costCenterNameOpt);

        //执行人
        String implementByOp = optProducer.getWorkorderImplementUserOptions(StrUtil.nullToString(dto.getImplementBy()));
        dto.setImplementByOption(implementByOp);

        //归档人
        String checkoverByOp = optProducer.getWorkorderCheckoverUserOptions(StrUtil.nullToString(dto.getCheckoverBy()));
        dto.setCheckoverByOption(checkoverByOp);

        //巡检周期
        String workorderCycleOpt = optProducer.getDictOption("WORKORDER_CYCLE", StrUtil.nullToString(dto.getWorkorderCycle()));
        dto.setWorkorderCycleOption(workorderCycleOpt);
    }
}
