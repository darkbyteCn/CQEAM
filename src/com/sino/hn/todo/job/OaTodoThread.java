package com.sino.hn.todo.job;


import com.sino.base.log.Logger;
import com.sino.hn.todo.service.EamToOaService;
import com.sino.hn.todo.util.HnOAConfig;

/**
 * 
 * 
 * @系统名称:  
 * @功能描述: OA待办以及已办任务循环推送
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sangjun
 * @创建时间: Dec 7, 2011
 */
public class OaTodoThread implements Runnable {

	public void run() {
		// TODO Auto-generated method stub
		EamToOaService service = new EamToOaService();
		while( true ){
			Logger.logInfo( "start loop" );
			if( JobControl.todoStart ){
				JobControl.setTodoStart( false );
				service.sendOatodo();
			}
			if( JobControl.todoDeleteStart  ){
				JobControl.setTodoDeleteStart( false );
				service.sendOatodoDele();
			}
			Logger.logInfo( "end loop" );
			
			try {
				Thread.sleep( HnOAConfig.getOaThreadSleepTime() );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		OaTodoThread myJob = new OaTodoThread();
		Thread thread = new Thread( myJob );
		thread.start();
	}

}
