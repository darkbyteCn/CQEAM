1、拷贝所有文件到目录/SinoFlow/ViewFlow/
2、index.jsp文件中/SinoFlow/ViewFlow/sinoflo.sfp内容需要改为目前sinoflo.sfp文件的存放位置
3、写servlet
	3.1参数：
		actid:当前准备查阅的申请的taskid(以下所有提到的taskid指的是sinoflo.sfp文件中存储的taskid)
	3.2将信息写入mflow里,mflow的数据存储内容如下：
	  	//[0]：过程名称procname
  		//[1]：上一任务id   [fromtaskid]
  		//[2]：当前的taskid
  		//[3]：所有历史办理过程的taskid的逗号分隔，sf_act_log表的记录
	3.3最后转向到页面：/SinoFlow/ViewFlow/index.jsp