package com.sino.ams.system.rent.dto;

import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;

/**
* <p>Title: 租赁权限表 AmsRentPrivi</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsRentPriviDTO extends AmsAssetsPriviDTO {
    public AmsRentPriviDTO(){
        super();
    }
   private String rentPrivi = "";

    public String getRentPrivi() {
        return rentPrivi;
    }

    public void setRentPrivi(String rentPrivi) {
        this.rentPrivi = rentPrivi;
    }
}