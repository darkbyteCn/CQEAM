package com.sino.ams.task.scheduler.oa;


import com.sino.hn.todo.service.EamToOaService;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 推送EAM系统代办信息到OA系统</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class Eam2OATask {

    /**
     * <p>功能说明：推送EAM系统代办信息到OA系统</p>
     */
    public void processHNOATasks() {
        EamToOaService service = new EamToOaService();
        service.sendOatodo();
        service.sendOatodoDele();
    }
}
