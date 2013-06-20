package com.sino.sinoflow.flowinterface;

import java.sql.Connection;

import com.sino.base.data.RowSet;

/**
 * Created by Yung, Kam Hing
 * Description: interface define which must used by application to exchange data with flow engine
 * Date: 2009-8-04
 * Time: 14:05:12
  */
public interface IAPPNotices {
    public RowSet getNotices(int userId, String URL, String keyword, String subject, String createby, Connection conn);
    public int getCount(int userId, Connection conn);
    public boolean delNotice(int noticeId, Connection conn);
}
