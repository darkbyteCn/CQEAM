package com.sino.ams.sampling.constant;

import com.sino.base.constant.web.WebActionConstant;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface SamplingActions extends WebActionConstant {
	String OPEN_TASK = "OPEN_TASK";//打开任务
	String CLOSE_TASK = "CLOSE_TASK";//关闭任务
	String PUBLISH_TASK = "PUBLISH_TASK";//发布任务
	String CLOSE_SELECTED_TASK = "CLOSE_SELECTED_TASK";//关闭选择的多个任务中的开放中的任务
	String OPEN_SELECTED_TASK = "OPEN_SELECTED_TASK";//打开选择的多个任务中的已关闭中的任务
	String CANCEL_SELECTED_TASK = "CANCEL_SELECTED_TASK";//取消选择的多个任务中的暂存的任务
	String PUBLISH_SELECTED_TASK = "PUBLISH_SELECTED_TASK";//发布选择的多个任务中暂存的任务

	String DISTRIBUTE_ORDER = "DISTRIBUTE_ORDER";//下发工单
}
