package com.sino.ams.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.sinoflow.framework.resource.dto.SfResDefineDTO;
import com.sino.sinoflow.framework.resource.model.SfResDefineModel;

/**
 * 
 * @系统名称: 获取上一层的应用
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 9, 2011
 */
public class ResUtil {
	 
	public static void setAllResName(  Connection conn, HttpServletRequest req , String resName  ) throws QueryException, ContainerException{
		String allResName = ResUtil.getAllResNameByResName(conn, resName);
		req.setAttribute( WebAttrConstant.ALL_RES_NAME , allResName );
	}
	
	public static String getAllResNameByResName(Connection conn, String resName)
			throws QueryException, ContainerException {
		SfResDefineDTO res = getResIdByName(conn, resName);
		if( null != res ){
			String allRes = getAllResName(conn, res.getResId());
			if (StrUtil.isEmpty(allRes)) {
				return resName;
			} else {
				return allRes + ">>" + resName;
			} 
		}else{
			return resName;
		} 
	}

	public static String getAllResName(Connection conn, String resId)
			throws QueryException, ContainerException {
		return ResUtil.getAllResName(conn, resId, false);
	}

	/**
	 * 获取资源文件路径
	 * 
	 * @param conn
	 * @param resId
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public static String getAllResName(Connection conn, String resId,
			boolean addSelf) throws QueryException, ContainerException {
		StringBuilder resName = new StringBuilder();
		List res = ResUtil.getParents(resId, addSelf);
		Collections.reverse(res);

		String tmpResId = null;
		SfResDefineDTO singleResName = null;
		for (Iterator iterator = res.iterator(); iterator.hasNext();) {
			tmpResId = (String) iterator.next();

			singleResName = getResById(conn, tmpResId);
			if (null != singleResName && null != singleResName.getResName()) {
				resName.append(">>");
				resName.append(singleResName.getResName());
			}
		}

		if (resName.indexOf(">>") == 0) {
			return resName.substring(2);
		} else {
			return resName.toString();
		}

	}

	public static SfResDefineDTO getResById(Connection conn, String resId)
			throws QueryException {
		SfResDefineDTO res = null;
		SfResDefineModel model = new SfResDefineModel(null, null);
		SimpleQuery sq = new SimpleQuery(model.getResourceById(resId), conn);
		sq.setDTOClassName(SfResDefineDTO.class.getName());
		sq.executeQuery();
		if (sq.hasResult()) {
			res = (SfResDefineDTO) sq.getFirstDTO();
		}
		return res;
	}

	public static String getResName(Connection conn, String resId)
			throws QueryException, ContainerException {
		String resName = null;

		List args = new ArrayList();
		args.add(resId);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT \n ");
		sql.append("	SRD.RES_NAME \n ");
		sql.append("FROM \n ");
		sql.append("	SF_RES_DEFINE SRD \n ");
		sql.append("WHERE \n ");
		sql.append("	SRD.RES_ID = ?  \n ");
		SQLModel model = new SQLModel();
		model.setSqlStr(sql.toString());
		model.setArgs(args);
		SimpleQuery sq = new SimpleQuery(model, conn);
		sq.executeQuery();
		if (sq.hasResult()) {
			resName = sq.getFirstRow().getStrValue("RES_NAME");
		}

		return resName;
	}

	public static String getParentResId(String curResId) {
		if (hasParent(curResId)) {
			int pos = curResId.lastIndexOf(".");
			return curResId.substring(0, pos);
		} else {
			return null;
		}
	}

	public static boolean hasParent(String curResId) {
		return (curResId.indexOf(".") > -1);
	}

	public static List getParents(String curResId, boolean addSelf) {
		List parents = new ArrayList();
		String parent = curResId;
		boolean hasParent = true;
		if (addSelf) {
			parents.add(parent);
		}
		while (hasParent) {
			parent = getParentResId(parent);
			if (null == parent) {
				break;
			}
			parents.add(parent);
			hasParent = hasParent(parent);
		}
		return parents;
	}

	private static SfResDefineDTO getResIdByName(Connection conn, String resName)
			throws QueryException {
		SfResDefineDTO res = null;
		SfResDefineModel model = new SfResDefineModel(null, null);
		SimpleQuery sq = new SimpleQuery(model.getResourceByName(resName), conn);
		sq.setDTOClassName(SfResDefineDTO.class.getName());
		sq.executeQuery();
		if (sq.hasResult()) {
			res = (SfResDefineDTO) sq.getFirstDTO();
		}
		return res;
	}

	public static void main(String[] args) {
		String resId = "4.1.6";
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			String r = ResUtil.getAllResName(conn, resId);
			System.out.println(r);
		} catch (PoolException e) {
			
			e.printStackTrace();
		} catch (QueryException e) {
			
			e.printStackTrace();
		} catch (ContainerException e) {
			
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			System.exit(1);
		}

		// List p = getParents(resId);
		// Collections.reverse(p);
		// for (Iterator iterator = p.iterator(); iterator.hasNext();) {
		// String name = (String) iterator.next();
		// System.out.println(name);
		// }

	}
}
