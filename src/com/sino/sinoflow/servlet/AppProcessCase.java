package com.sino.sinoflow.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.UUIDHexGenerator;
import com.sino.sinoflow.util;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dto.SfActInfoDTO;
import com.sino.sinoflow.dto.SfApplicationDTO;
import com.sino.sinoflow.dto.SfProcedureDTO;
import com.sino.sinoflow.flowinterface.IAPPLoad;
import com.sino.sinoflow.model.AppProcessModel;
import com.sino.sinoflow.model.SfActInfoModel;
import com.sino.sinoflow.model.SfProcedureModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.utilities.CaseRoutine;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-3-28
 * Time: 15:56:10
 * To change this template use File | Settings | File Templates.
 */
public class AppProcessCase extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";
//    private Connection conn = null;
//    private SfActInfoDTO actInfo = null;

    /**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/flow/taskNotFound.jsp";
        Message message = SessionUtil.getMessage(req);

        String appName = util.getReqPara(req, "appName");
        String appId = util.getReqPara(req, "appId");

        res.setContentType(m_sContentType);
        HttpSession sess = req.getSession();

        Connection conn = null;

        Calendar now = Calendar.getInstance();
        Date curDate = new Date();
        now.setTime(curDate);
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = dformat.format(now.getTime());
        CaseRoutine cs = new CaseRoutine();
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);

            String flag;
            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            SfActInfoDTO actInfo = null;
          /*   SQLModel md= (new AppProcessModel(null, null)).getAppNameModel(appName, appId);
              SimpleQuery sq = new SimpleQuery(md, conn);
            sq.executeQuery();
            if(sq.hasResult()){
              	Row row = sq.getFirstRow();
			appName= row.getValue("PROCEDURE_NAME").toString();
            }*/
            SQLModel sqlModel = (new AppProcessModel(null, null)).getActInfoModel(appName, appId);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.setDTOClassName(SfActInfoDTO.class.getName());
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()) {
                actInfo = (SfActInfoDTO)simpleQuery.getFirstDTO();
            } else {
                forwardURL = "/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=" + appName;
                return;
            }

            String actId = actInfo.getSfactActId();
            String actIDs = (String)sess.getAttribute(WebAttrConstant.SF_ACTIDS_INFO);
            if(actIDs == null || actIDs.indexOf(actId) < 0) {
                if(actIDs == null)
                    sess.setAttribute(WebAttrConstant.SF_ACTIDS_INFO, actId);
                else
                    //抄送
                    sess.setAttribute(WebAttrConstant.SF_ACTIDS_INFO, actIDs + ";" + actId);
            }

            String caseId = actInfo.getSfactCaseId();
            String signAct;
            if(actInfo.getSfactSignStatus()==1) {
                signAct = "0";
            } else {
                signAct = "1";
            }

            SfActInfoDTO lockAct = null;
            if(!(caseId == null || caseId.equals("")))
                lockAct = getLockCase(caseId, conn);
            if(lockAct == null) {
                flag = signAct;
            } else {
                if(lockAct.getSfactCaseLockUser().equals(user.getLoginName()) && lockAct.getSfactActId().equals(actId)) {
//                    Date lockDate = dformat.parse(lockAct.getSfactCaseLockDate());
//                    long timediff = curDate.getTime() - lockDate.getTime();
//                    if(timediff > 1800000) {
                        // only allow same user open same activity after half an hour for abnormal situration
                        flag = signAct;
//                    } else {
//                        forwardURL = "/flow/lockCase.jsp";
//                        return;
//                    }
                } else {
                    // Lock by other users or same user but difference activities, open as read only
                    String lockCase = req.getSession().getServletContext().getInitParameter("lockCase");
                    if(!lockCase.equals("1"))
                        flag = "2";
                    else
                        flag = signAct;
                }
            }

            SfApplicationDTO appl = cs.getApp(actInfo.getSfactAppdefId(), conn);
//            SfApplicationDTO appl = new SfApplicationDTO();
            if(appl == null) {
                return;
            }

            String outStr;
            try {
                IAPPLoad app = (IAPPLoad)Class.forName(appl.getAppDataSqlmodel()).newInstance();
                outStr = app.load(req, actInfo.getSfactApplId(), actInfo, conn);
            } catch (InstantiationException ex) {
                outStr = "";
            }  catch (ClassNotFoundException ex) {
                outStr = "";
            } catch (IllegalAccessException ex) {
                outStr = "";
            }

            String token = UUIDHexGenerator.getInstance().generate();
            req.getSession().setAttribute("$$sf_token$$", token);

            actInfo.setSfactCaseLockStatus(1);
            actInfo.setSfactCaseLockUser(user.getLoginName());
            actInfo.setSfactCaseLockDate(today);
            if(actInfo.getSfactSignStatus()==1) {
                actInfo.setSfactPickUser(user.getLoginName());
                actInfo.setSfactPickDate(today);
                if(actInfo.getSfactPickStatus()!=1)
                    actInfo.setSfactPickStatus(1);
            }
            sqlModel = (new SfActInfoModel(user, actInfo)).getDataUpdateModel();
            DBOperator.updateRecord(sqlModel, conn);

            Map flowObject = new HashMap();
            flowObject.put("actId", actInfo.getSfactActId());
            flowObject.put("appId", appl.getAppId());
            flowObject.put("loadStr", outStr);
            flowObject.put("flag", flag);
            flowObject.put("trayType", appl.getTrayType());
            flowObject.put("token", token);
            JSONObject f = new JSONObject(flowObject);
            String fstr = f.toString();

            if(actInfo.getSfactTaskUrl().equals("")) {
                SfProcedureDTO procDTO = new SfProcedureDTO();
                procDTO.setProcedureId(actInfo.getSfactProcId());
                sqlModel = (new SfProcedureModel(user, procDTO).getPrimaryKeyDataModel());
                simpleQuery=new SimpleQuery(sqlModel,conn);
                simpleQuery.setDTOClassName(SfProcedureDTO.class.getName());
                simpleQuery.executeQuery();
                if(simpleQuery.hasResult()) {
                    procDTO = (SfProcedureDTO)simpleQuery.getFirstDTO();
                } else {
                    return;
                }
                forwardURL = procDTO.getDefaultUrl();
            } else
                forwardURL = actInfo.getSfactTaskUrl();

            if(forwardURL.indexOf("?") >= 0)
                forwardURL += "&";
            else
                forwardURL += "?";
            forwardURL += WebAttrConstant.APP_DATAID + "=" + actInfo.getSfactApplId() + "&" +
                    WebAttrConstant.SF_APPDATAID + "=" + actInfo.getSfactApplId() + "&sf_task_attribute1=" +
                    actInfo.getSfactTaskAttribute1() + "&sf_task_attribute2=" + actInfo.getSfactTaskAttribute2() +
                    "&sf_task_attribute3=" + actInfo.getSfactTaskAttribute3() + "&sf_task_attribute4" +
                    actInfo.getSfactTaskAttribute4() + "&sf_task_attribute5=" + actInfo.getSfactTaskAttribute5();

            req.setAttribute(WebAttrConstant.SINOFLOW_WEB_OBJECT, fstr);
            req.setAttribute(WebAttrConstant.APP_DATAID, actInfo.getSfactApplId());
            req.setAttribute(WebAttrConstant.SINOFLOW_NEW_CASE, "0");

            commitFlag = true;
        } catch (PoolPassivateException ex) {
			Logger.logError(ex);
        } catch (PoolException ex) {
            Logger.logError(ex);
        } catch (QueryException ex) {
			Logger.logError(ex);
        } catch (SQLModelException ex) {
            Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (ConfigException ex) {
            Logger.logError(ex);
        }catch(ContainerException e){
            Logger.logError(e);
    } finally {
            if(conn != null) {
                try {
                    if(commitFlag)
                        conn.commit();
                    else
                        conn.rollback();
                    conn.setAutoCommit(autocommit);
                } catch(SQLException ex) {
                    Logger.logError(ex);
                }
	    		DBManager.closeDBConnection(conn);
            }
            //根据实际情况修改页面跳转代码。
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
		}
    }
    private SfActInfoDTO getLockCase(String caseid, Connection conn) throws SQLModelException, QueryException {

        SfActInfoDTO act = new SfActInfoDTO();
        act.setSfactCaseId(caseid);
        act.setSfactCaseLockStatus(1);
        SQLModel sqlModel = (new SfActInfoModel(null, act)).getMuxDataModel();
        SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
        simpleQuery.setDTOClassName(SfActInfoDTO.class.getName());
        simpleQuery.executeQuery();
        DTOSet dto;

        if (simpleQuery.hasResult()) {
            dto=simpleQuery.getDTOSet();
            act = (SfActInfoDTO) dto.getLastDTO();
            return act;
        } else {
            return null;
        }
    }
}