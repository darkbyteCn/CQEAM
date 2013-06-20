package com.sino.ams.task.scheduler.internal;

import com.sino.ams.task.service.internal.ReportDataProduceService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 报表跑数的后台服务，该任务仅用于启动调用存储过程的服务</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class ReportDataProduceTask {

    /**
     * <p>功能说明：从各相关应用表生成报表数据到报表相关模型表(含TD)表</p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          资产报废数据转移中出错时抛出数据处理异常
     */
    public void produceReportData() throws DataHandleException {
        ReportDataProduceService zteService = new ReportDataProduceService();
        zteService.produceReportData();
    }
}
