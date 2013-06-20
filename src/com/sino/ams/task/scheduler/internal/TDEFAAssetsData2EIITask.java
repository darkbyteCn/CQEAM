package com.sino.ams.task.scheduler.internal;

import com.sino.ams.task.service.internal.td.TDEFAAssetsData2EAMService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 将数据从ZTE表转移到ETS_FA_ASSETS表</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class TDEFAAssetsData2EIITask {


    /**
     * <p>功能说明：将ODI插入到ZTE表的资产数据转移到EAM系统TD相关应用表。</p>
     * <p>特殊说明：主要完成下列两项任务</p>
     * <li>将EFA_TD资产数据转移到EII等表</li>
     * <li>写入各相关匹配表</li>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          数据转移中出错时抛出数据处理异常
     */
    public void readTDEFAAssetsData2EII() throws DataHandleException {
        TDEFAAssetsData2EAMService faService = new TDEFAAssetsData2EAMService();
        faService.readTDFAAssets2EAM();
    }

}
