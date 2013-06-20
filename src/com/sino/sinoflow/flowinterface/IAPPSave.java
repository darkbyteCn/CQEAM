package com.sino.sinoflow.flowinterface;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yung, Kam Hing
 * Description: interface define which must used by application to exchange data with flow engine
 * Date: 2008-7-29
 * Time: 17:03:08
  */
public interface IAPPSave {

 /**
 * Description£ºGet data from request and save it to database
 * @param req request of the page
 * @param appDataId application id of the data, -1 means a new gerenate page without previous data created
 * @param flag -- save status flag, 0:save 1:complete 2:sendBack 3:specialSend
 * @param conn database connection
 * @return String id of application data, -1 means saving error
 */
    public String save(HttpServletRequest req, String appDataId, int flag, Connection conn);

}
