package com.sino.ams.workorder.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: �����豸��ϸ��--�����ύʱ(EAM) EtsWorkorderDtl</p>
* <p>Description: �����Զ�����DTO���ݴ������</p>
* <p>Copyright: ����˼ŵ����Ϣ�Ƽ����޹�˾ Copyright (c) 2006</p>
* <p>Company: ����˼ŵ����Ϣ�Ƽ����޹�˾</p>
* @author ����ʤ
* @version 1.0
*/

public class EtsWorkorderDtlDTO extends CheckBoxDTO{

	private String workorderNo = "";
	private String barcode = "";
	private int itemStatus;
	private int itemQty;
	private String remark = "";
	private String itemCode = "";
    private int addressId;
    private String boxNo = "";
	private String netUnit = "";
	private String itemRemark = "";
	private SimpleCalendar creationDate = null;
	private int createdBy;
    private String parentBarcode="";

    private String prjectId;
    private String workorderObjectNo="";
    private String workorderType="";
    private SimpleCalendar uploadDate=null;
    private String itemName="";
    private String itemSpec="";
    private String itemCategory="";
    private String responsibilityDept = "";//���β���
    private String responsibilityUser;//������
    private String maintainUser = "";//ά����Ա����ά��Ա��

	private String specialityDept = "";


    public EtsWorkorderDtlDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
	}
	public void setSpecialityDept(String dept) {
    	this.specialityDept = dept;
    }

    protected String getSpecialityDept() {
    	return this.specialityDept;
    }
    
    public String getSpecialityDeptCode() {
    	int start = this.specialityDept.lastIndexOf("[");
    	int end = this.specialityDept.lastIndexOf("]");
    	String code = "";
    	
    	if(start != -1 && end != -1 && start < end - 1) {
    		code = this.specialityDept.substring(start + 1, end);
    	}
    	
    	return code;
    }
	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @param workorderNo String
	 */
	public void setWorkorderNo(String workorderNo){
		this.workorderNo = workorderNo;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ��ǩ��
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� �豸״̬
	 * @param itemStatus AdvancedNumber
	 */
	public void setItemStatus(int  itemStatus){
		this.itemStatus = itemStatus;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ����
	 * @param itemQty AdvancedNumber
	 */
	public void setItemQty(int  itemQty){
		this.itemQty = itemQty;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ��ע
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� �豸����
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @param boxNo String
	 */
	public void setBoxNo(String boxNo){
		this.boxNo = boxNo;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ��Ԫ���
	 * @param netUnit String
	 */
	public void setNetUnit(String netUnit){
		this.netUnit = netUnit;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ��ע����������רҵ�����ƣ��ͺ�
	 * @param itemRemark String
	 */
	public void setItemRemark(String itemRemark){
		this.itemRemark = itemRemark;
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ��������
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException ����ֵ������ȷ�����ڻ����ǻ����ⲻ��ʶ��ĸ�ʽʱ�׳����쳣
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * ���ܣ����ù����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @param createdBy AdvancedNumber
	 */
	public void setCreatedBy(int  createdBy){
		this.createdBy = createdBy;
	}


	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @return String
	 */
	public String getWorkorderNo() {
		return this.workorderNo;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ��ǩ��
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� �豸״̬
	 * @return AdvancedNumber
	 */
	public int  getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ����
	 * @return AdvancedNumber
	 */
	public int  getItemQty() {
		return this.itemQty;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ��ע
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� �豸����
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @return String
	 */
	public String getBoxNo() {
		return this.boxNo;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ��Ԫ���
	 * @return String
	 */
	public String getNetUnit() {
		return this.netUnit;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ��ע����������רҵ�����ƣ��ͺ�
	 * @return String
	 */
	public String getItemRemark() {
		return this.itemRemark;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ��������
	 * @return SimpleCalendar
	 * @throws CalendarException ���õ�������ʽ���Ϸ�ʱ�׳����쳣
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * ���ܣ���ȡ�����豸��ϸ��--�����ύʱ(EAM)���� ������
	 * @return AdvancedNumber
	 */
	public int  getCreatedBy() {
		return this.createdBy;
	}


    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getWorkorderType() {
        return workorderType;
    }

    public void setWorkorderType(String workorderType) {
        this.workorderType = workorderType;
    }


    public String getParentBarcode() {
        return parentBarcode;
    }

    public void setParentBarcode(String parentBarcode) {
        this.parentBarcode = parentBarcode;
    }


    public SimpleCalendar getUploadDate() throws CalendarException {
        uploadDate.setCalPattern(getCalPattern());
        return uploadDate;
    }

    public void setUploadDate(SimpleCalendar uploadDate) throws CalendarException {
        this.uploadDate.setCalendarValue( uploadDate);
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getPrjectId() {
        return prjectId;
    }

    public void setPrjectId(String prjectId) {
        this.prjectId = prjectId;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getResponsibilityUser() {
        return responsibilityUser;
    }

    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }
}