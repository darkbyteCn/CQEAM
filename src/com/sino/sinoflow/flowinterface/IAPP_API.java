package com.sino.sinoflow.flowinterface;

import java.sql.Connection;

import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.sinoflow.dto.SfActInfoDTO;

/**
 * Created by Yung, Kam Hing
 * Description: interface define which must used by application to exchange data with flow engine
 * Date: 2009-7-22
 * Time: 09:05:08
  */
public interface IAPP_API {

    // 取得流向
    public String getConditionalStr(String str);

    public String getConditionalFlow(String actId, String str, Connection conn) throws SQLModelException, QueryException,
            ContainerException;

    // 取得组别
    public String getGroups(String loginName, SfActInfoDTO act, String flowCode, Connection conn) throws SQLModelException, QueryException,
            ContainerException;

    // 取得办理人
    public String getParticipants(String loginName, SfActInfoDTO act, String flowCode, String group, Connection conn) throws SQLModelException, QueryException,
            ContainerException;

    public boolean needSaveApproval();
}
