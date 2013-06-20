package com.sino.sinoflow.servlet;


//import com.sino.base.constant.message.MessageConstant;
//import com.sino.base.constant.message.MsgKeyConstant;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.log.Logger;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.dto.SfActInfoDTO;
import com.sino.sinoflow.dto.SfActLogDTO;
import com.sino.sinoflow.model.SfActArchiveModel;
import com.sino.sinoflow.model.SfActLogModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.utilities.TimeUtil2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class ChangeCompleteWork extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";
    /**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();
        Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            SfActInfoDTO act = new SfActInfoDTO();
            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

//            SQLModel sqlModel = (new SfActInfoModel(user, act)).getPrimaryKeyDataModel();
            SQLModel sqlModel = (new SfActLogModel(null, null)).getAllAct();;
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.setDTOClassName(SfActInfoDTO.class.getName());
            simpleQuery.executeQuery();
            DTOSet actSet;
            Calendar createDate = Calendar.getInstance();
            Calendar completeDate = Calendar.getInstance();
            DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(simpleQuery.hasResult()) {
                    actSet = simpleQuery.getDTOSet();
                    for(int i = 0; i < actSet.getSize(); i++) {
                        act = (SfActInfoDTO)actSet.getDTO(i);
                        createDate.setTime(dformat.parse(act.getSfactCreateDt()));
                        completeDate.setTime(dformat.parse(act.getSfactCompleteDate()));
                        long processTime;
                        TimeUtil2 timeService = new TimeUtil2(user, conn);
                        processTime = timeService.getWorkDuration(createDate, completeDate) / 1000;
                        act.setSfactPrjFileId((int)processTime);

                        sqlModel = (new SfActLogModel(user, act)).getDataUpdateModel();
                        DBOperator.updateRecord(sqlModel, conn);
                }

                sqlModel = (new SfActArchiveModel(null, null)).getAllAct();
                simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.setDTOClassName(SfActInfoDTO.class.getName());
                simpleQuery.executeQuery();
                if(simpleQuery.hasResult()) {
                    actSet = simpleQuery.getDTOSet();
                    for(int i = 0; i < actSet.getSize(); i++) {
                        act = (SfActInfoDTO)actSet.getDTO(i);
                        createDate.setTime(dformat.parse(act.getSfactCreateDt()));
                        completeDate.setTime(dformat.parse(act.getSfactCompleteDate()));
                        long processTime;
                        TimeUtil2 timeService = new TimeUtil2(user, conn);
                        processTime = timeService.getWorkDuration(createDate, completeDate) / 1000;
                        act.setSfactPrjFileId((int)processTime);

                        sqlModel = (new SfActArchiveModel(user, act)).getDataUpdateModel();
                        DBOperator.updateRecord(sqlModel, conn);
                    }
                }

                resout.write("更改完成工作时间成功!");

            } else {
                resout.write("[{status:ERROR, message:'更改完成工作时间找不到任何案件!");
            }
            commitFlag = true;
        } catch (Exception ex) {
            Logger.logError(ex);
            resout.write("[{status:ERROR, message:'更改完成工作时间发生 Exception!'}]");
        } finally {
            resout.flush();
            resout.close();
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
        }
/*
        SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
        try {
            Connection conn = getDBConnection(req);
            TimeUtil2 timeService = new TimeUtil2(user, conn);
            Calendar signDate = Calendar.getInstance();
            DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            signDate.setTime(dformat.parse("2009-2-18 16:26:12"));
            Calendar completeCal = Calendar.getInstance();
            completeCal.setTime(dformat.parse("2009-2-18 16:26:12"));
            long completeReal = (timeService.getRealDuration(signDate, completeCal)) / 1000; // 以秒为单位
            long completeWork = (timeService.getWorkDuration(signDate, completeCal)) / 1000; // 以秒为单位
            int test = 0;
        } catch (Exception e) {

        }
*/
    }
}