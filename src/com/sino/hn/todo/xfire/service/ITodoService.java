package com.sino.hn.todo.xfire.service;

import java.util.List;

public interface ITodoService {
/**
 * 推送待办信息
 * 
 * 参数：list里面压入Open实例。
 * 返回：成功xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>1</resultCode>
		 *				</result>
	    *		失败xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>-1</resultCode>
		 *					<resultDesc>错误信息详细</resultDesc>
		 *				</result>
 */

	public String saveOpen(List list);
	/**
	 * 推送已办信息
	 * 
	 * 参数：list里面压入Close实例。
	 * 返回：成功xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>1</resultCode>
		 *				</result>
	    *		失败xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>-1</resultCode>
		 *					<resultDesc>错误信息详细</resultDesc>
		 *				</result>
	 */
	public String saveClose(List list);
	/**
	 * 推送注销信息
	 * 
	 * 参数：list里面压入Cancel实例。
	 * 返回：成功xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>1</resultCode>
		 *				</result>
	    *		失败xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>-1</resultCode>
		 *					<resultDesc>错误信息详细</resultDesc>
		 *				</result>
	 */
	public String saveCancel(List list);
	/**
	 * 绩效更新状态接口
	 * @param state 更新成多少 
				1 待办
				2 已办
				3 待阅
				4 已阅
				5 草稿
				6 注销
	 * @param title 待办标题
	 * @param list 用户姓名集合
	 * 返回：成功xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>1</resultCode>
		 *				</result>
	    *		失败xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>-1</resultCode>
		 *					<resultDesc>错误信息详细</resultDesc>
		 *				</result>
	 */
	public String updateTodo(String state,String title,List list);
	/**
	 * 绩效删除接口
	 * @param title 待办标题
	 * @param list 用户姓名集合
	 * 返回：成功xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>1</resultCode>
		 *				</result>
	    *		失败xml=<?xml version="1.0" encoding="UTF-8"?>
		 * 				<result>
		 *					<resultCode>-1</resultCode>
		 *					<resultDesc>错误信息详细</resultDesc>
		 *				</result>
	 */
	public String deleteTodo(String title,List list);
	
}
