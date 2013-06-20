package com.sino.pda.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.newasset.dao.ChkOrderPDACreateDAO;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.pda.dao.UrgentOrderCreateDAO;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dao.SampOrderPDACreateDAO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.ReflectionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderCreateFactory {
	private static OrderCreateFactory instance = null;
	private static Object[] daoArgs = new Object[3];

	private static Map typeDAOMap = new HashMap();

	static{
		typeDAOMap.put(SamplingDicts.ASS_SAM_PDA, SampOrderPDACreateDAO.class.getName());
		typeDAOMap.put(SamplingDicts.ASS_CHK_PAD, ChkOrderPDACreateDAO.class.getName());
		typeDAOMap.put(SamplingDicts.INS_CHK_PAD, ChkOrderPDACreateDAO.class.getName());
		typeDAOMap.put(SamplingDicts.RNT_CHK_PAD, ChkOrderPDACreateDAO.class.getName());
		
		typeDAOMap.put(UrgentAppConstant.PDA_CREATE_TYPE_NAME , UrgentOrderCreateDAO.class.getName());
	}

	private OrderCreateFactory (FilterConfigDTO filterConfig) {
		daoArgs[1] = filterConfig;
	}

	/**
	 * 功能：获取工单上载工厂类
	 * @param filterConfig FilterConfigDTO
	 * @return OrderCreateFactory
	 */
	public static OrderCreateFactory getInstance(FilterConfigDTO filterConfig) {
		if(instance == null){
			instance = new OrderCreateFactory(filterConfig);
		}
		return instance;
	}

	/**
	 * 功能：获取工单上载DAO类。
	 * @param conn 数据库连接
	 * @param filePath 上载工单在服务器的保存路径
	 * @param orderType String 工单类型：盘点、抽查
	 * @return OrderCreateDAO 成功则返回创建对应工单的DAO程序，否则返回null
	 */
	public OrderCreateDAO getOrderCreator(Connection conn, String filePath, String orderType){
		OrderCreateDAO createDAO = null;
		try {
			daoArgs[0] = conn;
			daoArgs[2] = filePath;
			String daoClass = (String) typeDAOMap.get(orderType);
			createDAO = (OrderCreateDAO) ReflectionUtil.getInstance(daoClass, daoArgs);
		} catch (ReflectException ex) {
			ex.printLog();
		}
		return createDAO;
	}

	public static void main(String[] args) throws Exception {
		Connection conn = null;
		try {
			FilterConfigDTO filterConfig = new FilterConfigDTO();
			filterConfig.setLoginName("loginName");
			OrderCreateFactory fac = OrderCreateFactory.getInstance(filterConfig);
			String sFile = "C:\\workorder.xml";
			String orderType = "抽查";
			conn = DBManager.getDBConnection();
			OrderCreateDAO createDAO = fac.getOrderCreator(conn, sFile, orderType);
			if (createDAO.hasPreviousOrder()) {
				System.out.println("该地点有未归档工单存在");
			} else {
				String orderNo = createDAO.createOrder();
				System.out.println("新建工单编号是" + orderNo);
			}
		} finally{
			DBManager.closeDBConnection(conn);
//			System.exit(0);
		}
	}
}
