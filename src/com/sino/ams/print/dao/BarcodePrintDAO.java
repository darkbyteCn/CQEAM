package com.sino.ams.print.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.print.dto.BarcodeReceiveDTO;
import com.sino.ams.print.model.BarcodePrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

public class BarcodePrintDAO extends AMSBaseDAO {

	/**
	 *
	 * Title: 			SinoApplication
	 * @param userAccount		SfUserDTO  代表本系统的最终操作用户对象
	 * @param dtoParameter		LabelDTO   装在本次操作的数据对象
	 * @param conn		Connection  数据库连接,有调用者传入
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright:		李轶版权所有Copyright (c)2009~2022。
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 * Function			ETS_ROLL_CALL_BARCODE 数据访问层构造函数
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Apr 26, 2009
	 */
	public BarcodePrintDAO(SfUserDTO userAccount, BarcodeReceiveDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		barcodePrintModel = new BarcodePrintModel((SfUserDTO) userAccount, dtoParameter);
	}

	private BarcodePrintModel barcodePrintModel = null;


	/**
	 *
	 * Title: 			SinoApplication
	 * @param userAccount		SfUserDTO  代表本系统的最终操作用户对象
	 * @param dtoParameter		LabelDTO   装在本次操作的数据对象
	 * @param conn		Connection  数据库连接,有调用者传入
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright:		李轶版权所有Copyright (c)2009~2022。
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 * Function			SQL生成器BaseSQLProducer的初始化。
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Apr 26, 2009
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		BarcodeReceiveDTO dtoPara = (BarcodeReceiveDTO)dtoParameter;
		sqlProducer = new BarcodePrintModel(userAccount, dtoPara);
	}

	public boolean getBarcodeIsExist() throws QueryException{
		SQLModel sqlModel = barcodePrintModel.getBarcodeIsExist();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        boolean exist = simpleQuery.hasResult();;
        return exist;
	}

    public boolean getBarcodeIsExistInPrint() throws QueryException{
		SQLModel sqlModel = barcodePrintModel.getBarcodeIsExistInPrint();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        boolean exist = simpleQuery.hasResult();;
        return exist;
	}


}