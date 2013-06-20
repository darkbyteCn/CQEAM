package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
* <p>Title: 低值易耗品类别表(EAM) EamDhCatalogSet</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamDhCatalogSetDTO extends CommonRecordDTO{

	private String catlogSetId = "";
	private String setCode = "";
	private String setName = "";
	private int enabled = 1;

	/**
	 * 功能：设置低值易耗品类别表(EAM)属性 EAM_DH_CATALOG_SET_S.NEXTVAL
	 * @param catlogSetId String
	 */
	public void setCatlogSetId(String catlogSetId){
		this.catlogSetId = catlogSetId;
	}

	/**
	 * 功能：设置低值易耗品类别表(EAM)属性 类别编号
	 * @param setCode String
	 */
	public void setSetCode(String setCode){
		this.setCode = setCode;
	}

	/**
	 * 功能：设置低值易耗品类别表(EAM)属性 类别名称
	 * @param setName String
	 */
	public void setSetName(String setName){
		this.setName = setName;
	}

	/**
	 * 功能：设置低值易耗品类别表(EAM)属性 是否生效
	 * @param enabled String
	 */
	public void setEnabled(int enabled){
		this.enabled = enabled;
	}

	/**
	 * 功能：获取低值易耗品类别表(EAM)属性 EAM_DH_CATALOG_SET_S.NEXTVAL
	 * @return String
	 */
	public String getCatlogSetId() {
		return this.catlogSetId;
	}

	/**
	 * 功能：获取低值易耗品类别表(EAM)属性 类别编号
	 * @return String
	 */
	public String getSetCode() {
		return this.setCode;
	}

	/**
	 * 功能：获取低值易耗品类别表(EAM)属性 类别名称
	 * @return String
	 */
	public String getSetName() {
		return this.setName;
	}

	/**
	 * 功能：获取低值易耗品类别表(EAM)属性 是否生效
	 * @return String
	 */
	public int getEnabled() {
		return this.enabled;
	}
}
