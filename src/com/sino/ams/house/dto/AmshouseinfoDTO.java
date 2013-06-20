package com.sino.ams.house.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * 房屋土地报表信息统计领域模型
 * @author kouzh
 * 
 */
public class AmshouseinfoDTO extends SinoBaseObject implements DTO {

	private String company;// 公司
	private long assetnum;// 资产数量
	private long officeHousenum;// 办公营业房屋数量
	private long officeLandnum;// 办公营业土地数量
	private long landnum;// 基站土地数量
	private long housenum;// 基站房屋数量
    private long officeHouseLandNum;//办公房地合一数量
    private long houseLandNum;//基站房地合一数量
    private long houseCertificateNum;// 办理权证数量
    private long houseLandCertificateNum;//房地合一权证数量
    private double houseArea;// 房屋面积
	private double occupyArea;// 土地面积
	private double cost;// 资产原值
	private double deprnReserve;// 累计折旧
	private double netAssetValue;// 资产净值
    private double landCertificateNum;

    public double getLandCertificateNum() {
        return landCertificateNum;
    }

    public void setLandCertificateNum(double landCertificateNum) {
        this.landCertificateNum = landCertificateNum;
    }

    public long getHouseLandCertificateNum() {
        return houseLandCertificateNum;
    }

    public void setHouseLandCertificateNum(long houseLandCertificateNum) {
        this.houseLandCertificateNum = houseLandCertificateNum;
    }

    public long getOfficeHouseLandNum() {
        return officeHouseLandNum;
    }

    public void setOfficeHouseLandNum(long officeHouseLandNum) {
        this.officeHouseLandNum = officeHouseLandNum;
    }

    public long getHouseLandNum() {
        return houseLandNum;
    }

    public void setHouseLandNum(long houseLandNum) {
        this.houseLandNum = houseLandNum;
    }

    public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getAssetnum() {
		return assetnum;
	}

	public void setAssetnum(long assetnum) {
		this.assetnum = assetnum;
	}

	public long getHouseCertificateNum() {
		return houseCertificateNum;
	}

	public void setHouseCertificateNum(long houseCertificateNum) {
		this.houseCertificateNum = houseCertificateNum;
	}

	public double getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(double houseArea) {
		this.houseArea = houseArea;
	}

	public double getOccupyArea() {
		return occupyArea;
	}

	public void setOccupyArea(double occupyArea) {
		this.occupyArea = occupyArea;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getDeprnReserve() {
		return deprnReserve;
	}

	public void setDeprnReserve(double deprnReserve) {
		this.deprnReserve = deprnReserve;
	}

	public double getNetAssetValue() {
		return netAssetValue;
	}

	public void setNetAssetValue(double netAssetValue) {
		this.netAssetValue = netAssetValue;
	}

	public long getOfficeHousenum() {
		return officeHousenum;
	}

	public void setOfficeHousenum(long officeHousenum) {
		this.officeHousenum = officeHousenum;
	}

	public long getOfficeLandnum() {
		return officeLandnum;
	}

	public void setOfficeLandnum(long officeLandnum) {
		this.officeLandnum = officeLandnum;
	}

	public long getLandnum() {
		return landnum;
	}

	public void setLandnum(long landnum) {
		this.landnum = landnum;
	}

	public long getHousenum() {
		return housenum;
	}

	public void setHousenum(long housenum) {
		this.housenum = housenum;
	}
}
