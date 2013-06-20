package com.sino.ams.task.scheduler.soa;

import com.sino.ams.task.service.soa.mis.read.ProjectReadService;
import com.sino.ams.task.service.soa.td.read.TDProjectReadService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 通过SOA服务读取MIS系统项目信息</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class ProjectReadTask {


    /**
     * <p>功能说明：从MIS系统读取项目信息到EAM系统：</p>
     *
     * @throws DataHandleException 同步数据出错时抛出数据处理异常
     */
    public void readProjects() throws DataHandleException {
        ProjectReadService service = new ProjectReadService();
        service.readProjects();

        TDProjectReadService tdProjectReadService = new TDProjectReadService();
        tdProjectReadService.readTDProjects();

    }
}
