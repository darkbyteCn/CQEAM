package com.sino.ams.task.scheduler.sms;

import com.sino.base.exception.DataHandleException;
import com.sino.config.SinoConfig;
import com.sino.hn.constant.HNConstant;
import com.sino.hn.sms.service.HnSendMsg;
import com.sino.sms.service.EamMsgSend;
import com.sino.sms.service.SMSMsgCreate;

/**
 * <p>标题: SinoEAM中国移动资产管理系统后台同步任务：任务层对象</p>
 * <p>描述: 定时发送EAM系统的手机短信</p>
 * <p>版权: 根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约审定</p>
 * <p>开发商: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class EAMShortMessageTask {
    private static final String provinceCode = SinoConfig.getProvinceCode();

    /**
     * <p>功能说明：发送手机短信 </p>
     * <p>特殊说明：主要完成以下两项 </p>
     * <li>生成手机短信；</li>
     * <li>发送手机短信；</li>
     *
     * @throws com.sino.base.exception.DataHandleException
     *          处理请求出错时抛出数据处理异常
     */
    public void sendShortMessage() throws DataHandleException {
        SMSMsgCreate sc = new SMSMsgCreate();
        sc.AutoCreateMsg(); // 收集
        if (provinceCode.equals(HNConstant.PROVINCE_CODE)) {
            HnSendMsg es = new HnSendMsg();
            es.sendMsg(); // 发送
        } else {
            EamMsgSend es = new EamMsgSend();
            es.sendMsg();
        }
    }
}
