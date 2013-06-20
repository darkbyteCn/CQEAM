package com.sino.ams.task.scheduler.odi;

import com.sino.ams.task.service.odi.AssetsODIRequestService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 通过ODI服务读取上市公司和TD公司MIS系统财务信息</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsODIRequestTask {

    /**
     * <p>功能说明：向SOA发起ODI请求，通过ODI服务方式将上市公司和TD公司MIS系统下列数据插入EAM系统的ZTE表</p>
     * 1：资产头基本信息；
     * 2：资产分配行信息；
     *
     * @throws DataHandleException 处理请求出错时抛出数据处理异常
     */
    public void requestODI2ProcessAssets() throws DataHandleException {
        AssetsODIRequestService service = new AssetsODIRequestService();
        service.requestODI2ProcessAssets();
    }
}
