package com.sino.sso.dao;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.dto.DTOSet;
import com.sino.sso.constant.SSOConstant;

/**
 * Created by IntelliJ IDEA.
 * User: zhoujs
 * Date: 2009-2-19
 * Time: 10:21:52
 * Functiion:
 */
public class OAUtil {
    /**
     * 检查用户是否是资产同步员
     * @param rightsDTOSet
     * @return
     */
    public boolean isSyncUser(DTOSet rightsDTOSet){
        boolean isSyncUser=false;
        if(rightsDTOSet!=null&&rightsDTOSet.getSize()>0){
            List rightArr=new ArrayList();
           for(int i=0;i<rightsDTOSet.getSize()-1;i++){
               SfUserRightDTO rightDTO=(SfUserRightDTO)rightsDTOSet.getDTO(i);
               rightArr.add(rightDTO.getRoleName());
           }
            isSyncUser = rightArr.contains(SSOConstant.SYNC_ROLE);
        }
        return isSyncUser;
    }
}
