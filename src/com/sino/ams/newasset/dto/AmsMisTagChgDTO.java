package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: 记录MIS标签号变更历史 AmsMisTagChg</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsMisTagChgDTO extends CommonRecordDTO {

	private String id = "";
	private int fromOrganizationId;
	private int toOrganizationId;
	private String fromOrganizationName = "";
	private String toOrganizationName = "";
	private String tagNumberFrom = "";
	private String tagNumberTo = "";
	private String refNumber = "";
	private String transId = "";

	public AmsMisTagChgDTO() {
		super();
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 AMS_MIS_TAG_CHG_S.nextval
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 调出OU
	 * @param fromOrganizationId String
	 */
	public void setFromOrganizationId(int fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 调入OU
	 * @param toOrganizationId String
	 */
	public void setToOrganizationId(int toOrganizationId) {
		this.toOrganizationId = toOrganizationId;
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 TAG_NUMBER_FROM
	 * @param tagNumberFrom String
	 */
	public void setTagNumberFrom(String tagNumberFrom) {
		this.tagNumberFrom = tagNumberFrom;
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 TAG_NUMBER_TO
	 * @param tagNumberTo String
	 */
	public void setTagNumberTo(String tagNumberTo) {
		this.tagNumberTo = tagNumberTo;
	}

	/**
	 * 功能：设置记录MIS标签号变更历史属性 参考信息（如：调拨单号……）
	 * @param refNumber String
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}


	/**
	 * 功能：获取记录MIS标签号变更历史属性 AMS_MIS_TAG_CHG_S.nextval
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 功能：获取记录MIS标签号变更历史属性 调出OU
	 * @return String
	 */
	public int getFromOrganizationId() {
		return this.fromOrganizationId;
	}

	/**
	 * 功能：获取记录MIS标签号变更历史属性 调入OU
	 * @return String
	 */
	public int getToOrganizationId() {
		return this.toOrganizationId;
	}

	/**
	 * 功能：获取记录MIS标签号变更历史属性 TAG_NUMBER_FROM
	 * @return String
	 */
	public String getTagNumberFrom() {
		return this.tagNumberFrom;
	}

	/**
	 * 功能：获取记录MIS标签号变更历史属性 TAG_NUMBER_TO
	 * @return String
	 */
	public String getTagNumberTo() {
		return this.tagNumberTo;
	}

	/**
	 * 功能：获取记录MIS标签号变更历史属性 参考信息（如：调拨单号……）
	 * @return String
	 */
	public String getRefNumber() {
		return this.refNumber;
	}

	public String getFromOrganizationName() {
		return fromOrganizationName;
	}

	public void setFromOrganizationName(String fromOrganizationName) {
		this.fromOrganizationName = fromOrganizationName;
	}

	public String getToOrganizationName() {
		return toOrganizationName;
	}

	public void setToOrganizationName(String toOrganizationName) {
		this.toOrganizationName = toOrganizationName;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}
}
