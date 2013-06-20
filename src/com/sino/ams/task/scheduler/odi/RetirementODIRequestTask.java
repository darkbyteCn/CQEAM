package com.sino.ams.task.scheduler.odi;

import com.sino.ams.task.service.odi.mis.RetireODIRequestService;
import com.sino.ams.task.service.odi.td.TDRetireODIRequestService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 通过ODI服务读取MIS系统和TD系统报废资产信息</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class RetirementODIRequestTask {

    /**
     * <p>功能说明：读取MIS系统和TD系统报废资产信息</p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          处理请求出错时抛出数据处理异常
     */
    public void requestODI2ProcessRetire() throws DataHandleException {
        RetireODIRequestService service = new RetireODIRequestService();
        service.requestODI2ProcessRetire();

        TDRetireODIRequestService tdService = new TDRetireODIRequestService();
        tdService.requestODI2ProcessTDRetire();
    }
}
