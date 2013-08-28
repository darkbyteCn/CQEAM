package com.sino.ams.zz.proj2mgr.mapping.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MappingUtility {

	public static String getAuthorizedProjects(int userId, Connection conn) throws SQLException {
		StringBuilder sb = new StringBuilder();
		String sql = "SELECT PROJECT_ID FROM SF_PROJECT_MANAGER_MAPPING WHERE USER_ID=" + userId;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			sb.append(rs.getString("PROJECT_ID").trim());
			sb.append(",");
		}
		return sb.toString();
	}
}
