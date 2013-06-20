package com.sino.ams.task.scheduler.soa;

import com.sino.ams.task.service.soa.mis.read.OrgStructureReadService;
import com.sino.ams.task.service.soa.td.read.TDOrgStructureReadService;
import com.sino.base.exception.DataHandleException;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 通过SOA服务读取MIS系统组织结构(部门)信息</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class OrgStructureReadTask {

    /**
     * <p>功能说明：从MIS系统读取组织结构(部门)信息到EAM系统</p>
     *
     * @throws DataHandleException 读取部门信息出错时抛数据处理异常
     */
    public void readOrgStructure() throws DataHandleException {
        OrgStructureReadService service = new OrgStructureReadService();
        service.readOrgStructure();

        TDOrgStructureReadService tdService = new TDOrgStructureReadService();
        tdService.readTDOrgStructure();
    }
}
