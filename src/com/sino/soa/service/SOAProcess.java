package com.sino.soa.service;

import java.sql.Connection;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.soa.common.MIS_CONSTANT;
import com.sino.sso.dao.SSOUserLoginDAO;

/**
 * User: zhoujs
 * Date: 2009-5-31 17:16:47
 * Function:SOA服务处理
 */
public class SOAProcess {
    private String startTime = "";
    private String endTime = "";

    private SrvProcessModel processModel = null;

    private SfUserDTO userAccount = new SfUserDTO();

    public SOAProcess() {
        ServletConfigDTO configDTO=new ServletConfigDTO();
        SSOUserLoginDAO ssoUserLoginDAO = new SSOUserLoginDAO(configDTO);
        userAccount = ssoUserLoginDAO.validateUser("SINOADMIN");
        processModel = new SrvProcessModel();
    }

    /**
     * 同步MIS信息
     */
    public void synMisInfo() {
        System.out.print("开始进行自动SOA同步");
        Connection conn = null;
        SrvDAO srvDAO = new SrvDAO();
        try {
            conn = DBManager.getDBConnection();
            //查询资产类别
            srvDAO.synAssetCategory(conn, userAccount);
            //查询员工信息
            srvDAO.synEmployeeInfo(conn, userAccount);
            //查询值集信息
            srvDAO.synSetValue(conn, userAccount, MIS_CONSTANT.SOURCE_MIS);
            //查询组织结构
            srvDAO.synOrgstructure(conn, userAccount);
            //查询项目信息
            srvDAO.synProjectInfo(conn, userAccount);
            //查询供应商信息
            srvDAO.synVendorInfo(conn, userAccount);
            //查询会计期间
            srvDAO.synPeriodStatus(conn, userAccount);
            //查询资产地点
            srvDAO.synFaLocation(conn, userAccount);
            //查询资产头基本信息(ODI)
            srvDAO.synAssetHeaderInfo(conn, userAccount);
            //查询资产分配行信息(ODI)
            srvDAO.synAssetDistribute(conn, userAccount);
            //查询资产财务信息服务(ODI)
//            srvDAO.synAssetInfo(conn, userAccount);
            //查询科目余额(ODI)
//            srvDAO.synAccountBlance(conn, userAccount);
            //查询项目任务信息(ODI)
            srvDAO.synTransTaskInfo(conn, userAccount);
            //同步公司内调拨
            srvDAO.synTransInCompany(conn, userAccount);
            //同步公司间调拨
            srvDAO.synTransBtwCompany(conn, userAccount);

        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        System.out.print("结束自动SOA同步");

    }

    public void synTDInfo() {
        System.out.print("开始进行TD自动SOA同步");
        TDSrvDAO srvDAO = new TDSrvDAO();
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            //查询资产类别
            srvDAO.synAssetCategory(conn, userAccount);
            //查询员工信息
            srvDAO.synEmployeeInfo(conn, userAccount);
            //查询值集信息
            srvDAO.synSetValue(conn, userAccount, MIS_CONSTANT.SOURCE_TD);
            //查询组织结构
            srvDAO.synOrgstructure(conn, userAccount);
            //查询项目信息
            srvDAO.synProjectInfo(conn, userAccount);
            //查询供应商信息
            srvDAO.synVendorInfo(conn, userAccount);
            //查询会计期间
            srvDAO.synPeriodStatus(conn, userAccount);
            //查询资产地点
            srvDAO.synFaLocation(conn, userAccount);
            //查询资产头基本信息(ODI)
            srvDAO.synAssetHeaderInfo(conn, userAccount);
            //查询资产分配行信息(ODI)
            srvDAO.synAssetDistribute(conn, userAccount);
            //查询资产财务信息服务(ODI)
//            srvDAO.synAssetInfo(conn, userAccount);
            //查询科目余额(ODI)
//            srvDAO.synAccountBlance(conn, userAccount);
            //同步公司内调拨
            srvDAO.synTransInCompany(conn, userAccount);
            //同步公司间调拨
            srvDAO.synTransBtwCompany(conn, userAccount);

        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);

        }

        System.out.print("结束TD自动SOA同步");

    }

}