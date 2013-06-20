package com.sino.ams.workorderDefine.service;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;
import com.sino.ams.workorderDefine.servlet.WorkorderDefineCreate;

/**
 * 巡检自定义
 * 
 * @author Administrator
 */
public class WorkorderDefineService extends TimerTask {

	Connection conn = null;
	Map<WorkorderDefineDTO, Timer> tasks = new HashMap<WorkorderDefineDTO, Timer>();
	SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public WorkorderDefineService() {
		super();
	}

	public static void main(String[] args) {
		WorkorderDefineService workorderServer = new WorkorderDefineService();
		workorderServer.start();
	}

	//启动定时任务
	public void start() {
		Timer timerQueryDB = new Timer();
		timerQueryDB.schedule(new WorkorderDefineService(), 1000, 1000 * 60 * 60 * 24);//每天查询数据库看是否有新的巡检自定义
	}

	public void run() {
		try {
			conn = DBManager.getDBConnection();
			
			List<WorkorderDefineDTO> newTask = new ArrayList<WorkorderDefineDTO>();
			List<WorkorderDefineDTO> stopTask = new ArrayList<WorkorderDefineDTO>();
			
			List<WorkorderDefineDTO> taskModelBeans = sendMsg(conn);
			for (int i = 0; i < taskModelBeans.size(); i++) {
				WorkorderDefineDTO bean =  taskModelBeans.get(i);
				if (!tasks.containsKey(bean)) {
					newTask.add(bean);
				}
			}
			Iterator it = tasks.keySet().iterator();
			while (it.hasNext()) {
				WorkorderDefineDTO bean = (WorkorderDefineDTO) it.next();
				if (!taskModelBeans.contains(bean)) {
					stopTask.add(bean);
				}
			}
			
			//停止失效的任务
			for (int i = 0; i < stopTask.size(); i++) {
				WorkorderDefineDTO bean = stopTask.get(i);
				Timer timer = tasks.get(bean);
				timer.cancel();
				tasks.remove(bean);
			}
			
			for (int i = 0; i < newTask.size(); i++) {
				WorkorderDefineDTO bean = newTask.get(i);			
				Date time = timeFormat.parse(bean.getWorkorderExecDate().toString());
				Logger.logError("Start task:[WORKORDER_DEFINE_ID=" + bean.getWorkorderDefineId()+",time="+time+"]");
				Timer timer = new Timer();
//				timer.schedule(new WorkorderDefineCreate(bean), time,1000 * 60 * 60 * 24);
				timer.schedule(new WorkorderDefineCreate(bean), time);
				tasks.put(bean, timer);
			}
			Logger.logError("Total task count: " + tasks.size());
			
		} catch (PoolException ex) {
			ex.printLog();
		} catch (QueryException ex) {
			ex.printLog();
		} catch (ContainerException ex) {
			ex.printLog();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
		}
	}

	/**
	 * 查询有效的需要创建的自定义巡检
	 * @param conn
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	private List<WorkorderDefineDTO> sendMsg(Connection conn) throws QueryException,
			ContainerException {
		List<WorkorderDefineDTO> list = new ArrayList<WorkorderDefineDTO>();
		RowSet rowSet = null;
		SQLModel sqlModel = new SQLModel();
		String strSql = "SELECT WORKORDER_DEFINE_ID,\n"
				+ "       ORGANIZATION_ID,\n"
				+ "       OBJECT_CATEGORY,\n"
				+ "       COUNTY,\n"
				+ "       CITY,\n"
				+ "       COST_CENTER_CODE,\n"
				+ "       IMPLEMENT_BY,\n"
				+ "       CHECKOVER_BY,\n"
				+ "       WORKORDER_CYCLE,\n"
				+ "       ENABLED,\n"
				+ " 	  CASE WORKORDER_EXEC_DATE WHEN NULL THEN DATEADD(MONTH,1,CONVERT(VARCHAR,DATEPART(YY,CREATION_DATE)) +'/'+CONVERT(VARCHAR, DATEPART(MM,CREATION_DATE))+'/'+CONVERT(VARCHAR,WORKORDER_TIGGER_TIME)) ELSE DATEADD(MONTH,WORKORDER_CYCLE,WORKORDER_EXEC_DATE) END WORKORDER_EXEC_DATE,\n"
				+ "		  WORKORDER_TIGGER_TIME,"
				+ "       CREATION_DATE,\n" + "       CREATED_BY,\n"
				+ "       LAST_UPDATE_DATE,\n" + "       LAST_UPDATE_BY,GROUP_ID\n"
				+ "  FROM ETS_WORKORDER_DEFINE \n"
                + "  WHERE ENABLED = 'Y'\n" +
                "   AND DATEDIFF(dd,WORKORDER_EXEC_DATE,GETDATE())=0";
		sqlModel.setSqlStr(strSql);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		rowSet = sq.getSearchResult();
		Row row = null;		
		for (int i=0;i<rowSet.getSize();i++) {
			WorkorderDefineDTO defineDTO = new WorkorderDefineDTO();
			row = rowSet.getRow(i);
			defineDTO.setWorkorderDefineId(row.getValue("WORKORDER_DEFINE_ID").toString());
			defineDTO.setOrganizationId(StrUtil.strToInt(row.getValue("ORGANIZATION_ID").toString()));
			defineDTO.setObjectCategory(row.getValue("OBJECT_CATEGORY").toString());
			defineDTO.setCounty(row.getValue("COUNTY").toString());
			defineDTO.setCity(row.getValue("CITY").toString());
			defineDTO.setCostCenterCode(row.getValue("COST_CENTER_CODE").toString());
			defineDTO.setImplementBy(StrUtil.strToInt(row.getValue("IMPLEMENT_BY").toString()));
			defineDTO.setCheckoverBy(StrUtil.strToInt(row.getValue("CHECKOVER_BY").toString()));
			defineDTO.setWorkorderCycle(StrUtil.strToInt(row.getValue("WORKORDER_CYCLE").toString()));
			defineDTO.setCreationDate(row.getValue("CREATION_DATE").toString());
			defineDTO.setWorkorderExecDate(row.getValue("WORKORDER_EXEC_DATE").toString());
			defineDTO.setWorkorderTiggerTime(StrUtil.strToInt(row.getValue("WORKORDER_TIGGER_TIME").toString()));
			defineDTO.setGroupId(StrUtil.strToInt(row.getValue("GROUP_ID").toString()));
			list.add(defineDTO);
		}	
		return list;
	}
}
