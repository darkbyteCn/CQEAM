package com.sino.ams.task.scheduler.soa;

import com.sino.ams.task.service.soa.mis.write.AssetsAllocationWriteService;
import com.sino.ams.task.service.soa.td.write.TDAssetsAllocationWriteService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 通过SOA服务同步EAM系统公司内资产调拨的变动到MIS系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsAllocationWriteTask {

    /**
     * <p>功能说明：EAM系统同步公司内资产调拨的变动到MIS系统 </p>
     * <p>特殊说明： </p>
     * <li>同步部门内调拨</li>
     * <li>同步部门间调拨</li>
     * <li>同步紧急汇总调拨</li>
     * <li>其他变动的同步本任务不负责，由界面手工操作</li>
     * @throws com.sino.base.exception.DataHandleException
     *          同步数据出错时抛出数据处理异常
     */
    public void writeAssetsAllocations() throws DataHandleException {
        AssetsAllocationWriteService service = new AssetsAllocationWriteService();
        service.writeAssetsAllocations();

        TDAssetsAllocationWriteService tdService = new TDAssetsAllocationWriteService();
        tdService.writeTDAssetsAllocations();
    }
}
