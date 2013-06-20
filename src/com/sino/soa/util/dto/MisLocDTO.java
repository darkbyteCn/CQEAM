package com.sino.soa.util.dto;

/**
 * User: zhoujs
 * Date: 2009-5-7 16:43:04
 * Function:
 */
public class MisLocDTO {
    private String key="";
    private String segment1="";
    private String segment2="";
    private String segment3="";
    private String segment1Desc="";
    private String segment2Desc="";
    private String segment3Desc="";
    private String organizationId = "";    

    private String code="";
    private String name="";

    private boolean isValidate=true;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSegment1() {
        return segment1;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    public String getSegment2() {
        return segment2;
    }

    public void setSegment2(String segment2) {
        this.segment2 = segment2;
    }

    public String getSegment3() {
        return segment3;
    }

    public void setSegment3(String segment3) {
        this.segment3 = segment3;
    }

    public String getSegment1Desc() {
        return segment1Desc;
    }

    public void setSegment1Desc(String segment1Desc) {
        this.segment1Desc = segment1Desc;
    }

    public String getSegment2Desc() {
        return segment2Desc;
    }

    public void setSegment2Desc(String segment2Desc) {
        this.segment2Desc = segment2Desc;
    }

    public String getSegment3Desc() {
        return segment3Desc;
    }

    public void setSegment3Desc(String segment3Desc) {
        this.segment3Desc = segment3Desc;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean validate) {
        isValidate = validate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}
