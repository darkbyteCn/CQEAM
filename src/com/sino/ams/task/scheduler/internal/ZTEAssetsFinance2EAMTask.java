package com.sino.ams.task.scheduler.internal;

import com.sino.ams.task.service.internal.mis.ZTEAssetsFinance2EFAService;
import com.sino.ams.task.service.internal.td.TDZTEAssetsFinance2EFAService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 将资产财务数据从ZTE表更新到ETS_FA_ASSETS(含TD)表</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class ZTEAssetsFinance2EAMTask {


    /**
     * <p>功能说明：将ODI插入到ZTE表的资产资产财务数据更新到EFA(含TD)表</p>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          资产财务数据转移中出错时抛出资产财务数据处理异常
     */
    public void updateFAFinanceFromZTE() throws DataHandleException {
        ZTEAssetsFinance2EFAService zteService = new ZTEAssetsFinance2EFAService();
        zteService.updateEFAFinanceFromZTE();

        TDZTEAssetsFinance2EFAService tdService = new TDZTEAssetsFinance2EFAService();
        tdService.updateTDEFAFinanceFromZTE();
    }
}
