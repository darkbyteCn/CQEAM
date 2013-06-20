package com.sino.sinoflow.flowinterface;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.sino.sinoflow.dto.SfActInfoDTO;

/**
 * Created by Yung, Kam Hing
 * Description: interface define which must used by application to exchange data with flow engine
 * Date: 2008-7-29
 * Time: 17:03:08
  */
public interface IAPPLoad {

 /**
 * Description£ºGet data from request and save it to database
 * @param req request of the page
 * @param appDataId application id of the data, -1 means a new gerenate page without previous data created
 * @param conn database connection
 * @return String json string of list of (fieldName, fieldValue) 
 */
    public String load(HttpServletRequest req, String appDataId, SfActInfoDTO act, Connection conn);

}
