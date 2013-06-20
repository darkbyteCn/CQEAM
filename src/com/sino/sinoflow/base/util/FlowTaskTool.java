package com.sino.sinoflow.base.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;

public class FlowTaskTool {

	/**
	 * 功能：解析所有事件，并找出事件内容
	 * 
	 * @param num
	 *            事件总和
	 */
	public static List findNum(int num) {
		List list = new ArrayList();
		if (num == 0)
			return list;
		int index = 1;
		int reslut = 0;
		int count = 0;
		while (true) {
			if ((reslut = num & index) != 0) {
				list.add(new Integer(reslut));
				if (num == (count += reslut)) {
					return list;
				}
			}
			index *= 2;
		}
	}

	/**
	 * 功能：查询记录是否已存在
	 * 
	 * @param tabName
	 *            表名
	 * @param couName
	 *            列名
	 * @param likeName
	 *            要查询列的条件
	 * @return
	 * @throws QueryException
	 * @throws DataHandleException
	 */
	public static boolean isExist(String tabName, String couName,
			String likeName, Connection conn) throws QueryException {
		SQLModel sqlModel = getSqlModel(tabName, couName, likeName);
		return hasNext(sqlModel, conn);
	}

	/**
	 * 功能：查询记录是否已存在
	 * 
	 * @param tabName
	 *            表名
	 * @param couName
	 *            列名
	 * @param likeName
	 *            要查询列的条件
	 * @return
	 * @throws QueryException
	 * @throws QueryException
	 * @throws DataHandleException
	 */
	public static boolean isExist(String tabName, List list, Connection conn)
			throws QueryException {
		return hasNext(getSqlModel(tabName, list), conn);
	}

	private static boolean hasNext(SQLModel sqlModel, Connection conn)
			throws QueryException {
		SimpleQuery splq = new SimpleQuery(sqlModel, conn);
		try {
			splq.executeQuery();
			return splq.hasResult();
		} catch (QueryException e) {
			throw e;
		} finally {
			if (conn != null)
				DBManager.closeDBConnection(conn);
		}
	}

	private static SQLModel getSqlModel(String tabName, String couName,
			String likeName) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT " + " NULL" + " FROM " + tabName.toUpperCase()
				+ " WHERE " + couName.toUpperCase() + " LIKE '" + likeName
				+ "'";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	private static SQLModel getSqlModel(String tabName, List list) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT " + " NULL" + " FROM " + tabName.toUpperCase()
				+ " WHERE ";
		for (int i = 0; i < list.size(); i++) {
			SelectEmpty se = (SelectEmpty) list.get(i);
			if (se.isInt()) {
				sqlStr += " " + se.getColumnName().toUpperCase() + " = "
						+ se.getLikeName() + " AND";
			} else {
				sqlStr += " " + se.getColumnName().toUpperCase() + " LIKE '"
						+ se.getLikeName() + "' AND";
			}
		}
		sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("AND"));
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 将字符串cn变成系统所能识别的数据库字段列名
	 * 
	 * @param cn
	 * @return
	 */
	public static String paseColumnName(String cn) {
		String[] arr = cn.split("_");
		String newStr = "";
		if (arr.length == 1 && arr[0].equals(cn)) {
			return cn.toLowerCase();
		} else {
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					newStr += arr[i].toLowerCase();
				} else {
					newStr += arr[i].substring(0, 1).toUpperCase();
					newStr += arr[i].substring(1).toLowerCase();
				}
			}
		}
		return newStr;
	}

	/**
	 * 将带有特殊字符的字符串格式化
	 * 
	 * @param s
	 * @return
	 */
	public static final String escapeHTML(String s) {
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&#039;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
