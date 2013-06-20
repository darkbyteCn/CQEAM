package com.sino.ams.newasset.urgenttrans.pda.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.model.UrgentModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.XMLParseException;
import com.sino.base.log.Logger;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.pda.dao.OrderCreateDAO;

/**
 * 
 * @系统名称: 
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Aug 3, 2011
 */
public class UrgentOrderCreateDAO extends OrderCreateDAO{
	/**
	 *
	 * @param conn 数据库连接
	 * @param filterConfig FilterConfigDTO 配置文件信息
	 * @param filePath String 上载的工单文件
	 */
	public UrgentOrderCreateDAO(Connection conn, FilterConfigDTO filterConfig, String filePath) {
		super(conn, filterConfig, filePath);
	}
	
	/**
	 * 功能：初始化工单数据
	 * @throws XMLParseException
	 */
	protected void initOrderData() throws XMLParseException {
		UrgentOrderXMLParser chkParser = new UrgentOrderXMLParser();
		chkParser.parseXML(filterConfig, conn, filePath);
		orderParameter = chkParser.getOrder();
		userAccount = chkParser.getCreatedUser();
		if(  userAccount.getUserId() <= 0 ){
			throw new XMLParseException( "用户信息为空" );
		}
		UrgentHeaderDTO order = (UrgentHeaderDTO)orderParameter; 
		order.setCreatedBy( userAccount.getUserId() );
		sqlProducer = new UrgentModel( userAccount,  order );
//		AmsAssetsCheckHeaderDTO checkOrder = (AmsAssetsCheckHeaderDTO)orderParameter;
//		sqlProducer = new ChkOrderPDACreateModel(userAccount, checkOrder);
	}

	@Override
	protected void createOrderBatch() throws DataHandleException { 
	}

	@Override
	protected void createOrderHeader() throws DataHandleException {
		try {
			UrgentHeaderDTO dto = (UrgentHeaderDTO)orderParameter; 
			SeqProducer seqProducer = new SeqProducer(conn);
			String transId = seqProducer.getGUID();
			dto.setTransId( transId ); 
			 
			
			 
	        dto.setCreatedBy( userAccount.getUserId()); // 设置创建人
	        dto.setCreated( userAccount.getUsername()); // 设置创建人
	        dto.setFromOrganizationId( userAccount.getOrganizationId());
	        dto.setFromCompanyName( userAccount.getCompany()); 
	        dto.setTransTypeValue( UrgentAppConstant.PDA_CREATE_TYPE_NAME );
	        dto.setTransType( UrgentAppConstant.TRANS_TYPE );
	        dto.setCurrCreationDate(); 
	        dto.setTransStatus( AssetsDictConstant.DISTRIBUTED  ); 
	        dto.setImplementBy( userAccount.getUserId() );
	          
	        String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
			String transType = dto.getTransType();
			OrderNumGenerator numberProducer = new OrderNumGenerator(conn,companyCode, transType);
			orderNo = numberProducer.getOrderNum();
			dto.setTransNo( orderNo ); 
			
			UrgentModel modelProducer = (UrgentModel)sqlProducer;
			modelProducer.setDTOParameter( dto );
			SQLModel sqlModel = modelProducer.createHeaderModel(dto);
			DBOperator.updateRecord(sqlModel, conn);

			setOrderData( dto );
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} 
	}

	@Override
	protected void createOrderLine() throws DataHandleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasPreviousOrder() {
		// TODO Auto-generated method stub
		return false;
	}


}
