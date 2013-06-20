package com.sino.framework.security.servlet;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.sino.base.constant.web.WebConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.web.listener.SessionListener;
import com.sino.ams.system.user.dto.SfUserDTO;
import org.apache.commons.httpclient.util.DateUtil;

/**
 * @系统名称:
 * @功能描述: 记录当前在线登录用户
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 31, 2011
 */
public class SessionHandle extends SessionListener implements
        HttpSessionAttributeListener {
    private static Map<String, SfUserDTO> userList = new HashMap<String, SfUserDTO>();

    @Override
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        // // TODO Auto-generated method stub
        super.attributeAdded(arg0);
        HttpSession session = arg0.getSession();
        SfUserDTO userAccount = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
        if (null != userAccount&&userAccount.getUserId()>0) {
            String key = session.getId();
            userAccount.setLogonTime(DateUtil.formatDate(new Date()));
            userList.put(key, userAccount);
        }

//		Iterator<String> it = userList.keySet().iterator();
//		String key = null;
//		System.out.println("after add .........");
//		while (it.hasNext()) {
//			key = it.next();
//			System.out.println(key);
//			System.out.println(userList.get(key));
//		}

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
        HttpSession session = arg0.getSession();
        String key = session.getId();

        if (userList.containsKey(key)) {
            userList.remove(key);
        }

//		Iterator<String> it = userList.keySet().iterator();
//		key = null;
//		System.out.println("after remove .........");
//		while (it.hasNext()) {
//			key = it.next();
//			System.out.println(key);
//			System.out.println(userList.get(key));
//		}

        super.attributeRemoved(arg0);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
        super.attributeReplaced(arg0);
    }

    public static DTOSet getOnLineUser() throws DTOException {
        DTOSet users = new DTOSet();
        SfUserDTO user = null;

        Iterator<SfUserDTO> it = userList.values().iterator();
        while (it.hasNext()) {
            user = it.next();
            users.addDTO(user);
        }
        return users;
    }

    public static int getOnLineUserCount() {
        return userList.size();
    }

}
