package com.sino.ams.house.dao;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.ams.house.dto.AmshouseinfoDTO;
import com.sino.ams.house.model.AmshouseinfoModel;

/**
 * 房屋土地统计报表持久层处理
 * @author kouzh
 * 
 */
public class AmshouseinfoDAO {

	private Connection conn;

	private HttpServletRequest req;

	public AmshouseinfoDAO() {
	}

	public AmshouseinfoDAO(Connection conn, HttpServletRequest req) {
		this.conn = conn;
		this.req = req;
	}

	/**
	 * 获取基站土地报表统计信息
	 *
	 */
	public DTOSet getAmslandInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getAmslandInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}

	/**
	 * 获取基站房屋报表统计信息
	 *
	 */
	public DTOSet getAmshouseInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getAmshouseInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}

	/**
	 * 获取办公土地报表统计信息
	 *
	 */
	public DTOSet getAmsofficelandInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getAmsofficelandInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}

	/**
	 * 获取办公房屋报表统计信息
	 *
	 */
	public DTOSet getAmsofficehouseInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getAmsofficehouseInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}

    /**
	 * 获取办公房地合一报表统计信息
	 *
	 */
	public DTOSet getHouseLandInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getHouseLandInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}

    /**
	 * 获取基站房地合一报表统计信息
	 *
	 */
	public DTOSet getBtsHouseLandInfo(Connection conn) throws QueryException {
		DTOSet result = null;
		AmshouseinfoModel daoModel = new AmshouseinfoModel();
		SQLModel sqlModel = daoModel.getBtsHouseLandInfo();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.setDTOClassName(AmshouseinfoDTO.class.getName());
		simpleQuery.executeQuery();
		result = simpleQuery.getDTOSet();
		return result;
	}
}
