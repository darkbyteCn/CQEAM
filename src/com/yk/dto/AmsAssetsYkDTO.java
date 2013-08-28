package com.yk.dto;

public class AmsAssetsYkDTO {
	private int uuid;
	private String barcode;
	private String description;

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static void main(String[] args) {
		AmsAssetsYkDTO aykDTO=new AmsAssetsYkDTO();
		aykDTO.setBarcode("3910-10001211");
		System.out.println(aykDTO.getBarcode());
	}

}
