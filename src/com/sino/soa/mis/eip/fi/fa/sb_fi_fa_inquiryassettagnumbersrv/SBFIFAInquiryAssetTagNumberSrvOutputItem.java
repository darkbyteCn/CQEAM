
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SB_FI_FA_InquiryAssetTagNumberSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB_FI_FA_InquiryAssetTagNumberSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_FI_FA_InquiryAssetTagNumberSrvOutputItem", propOrder = {
    "booktypecode",
    "segment1",
    "segment2",
    "projectnum",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "tagnumber"
})
public class SBFIFAInquiryAssetTagNumberSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "SEGMENT2", required = true, nillable = true)
    protected String segment2;
    @XmlElement(name = "PROJECT_NUM", required = true, nillable = true)
    protected String projectnum;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "ATTRIBUTE2", required = true, nillable = true)
    protected String attribute2;
    @XmlElement(name = "ATTRIBUTE3", required = true, nillable = true)
    protected String attribute3;
    @XmlElement(name = "ATTRIBUTE4", required = true, nillable = true)
    protected String attribute4;
    @XmlElement(name = "ATTRIBUTE5", required = true, nillable = true)
    protected String attribute5;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;

    /**
     * Gets the value of the booktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKTYPECODE() {
        return booktypecode;
    }

    /**
     * Sets the value of the booktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKTYPECODE(String value) {
        this.booktypecode = value;
    }

    /**
     * Gets the value of the segment1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT1() {
        return segment1;
    }

    /**
     * Sets the value of the segment1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT1(String value) {
        this.segment1 = value;
    }

    /**
     * Gets the value of the segment2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT2() {
        return segment2;
    }

    /**
     * Sets the value of the segment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT2(String value) {
        this.segment2 = value;
    }

    /**
     * Gets the value of the projectnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTNUM() {
        return projectnum;
    }

    /**
     * Sets the value of the projectnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTNUM(String value) {
        this.projectnum = value;
    }

    /**
     * Gets the value of the attribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE1() {
        return attribute1;
    }

    /**
     * Sets the value of the attribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE1(String value) {
        this.attribute1 = value;
    }

    /**
     * Gets the value of the attribute2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE2() {
        return attribute2;
    }

    /**
     * Sets the value of the attribute2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE2(String value) {
        this.attribute2 = value;
    }

    /**
     * Gets the value of the attribute3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE3() {
        return attribute3;
    }

    /**
     * Sets the value of the attribute3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE3(String value) {
        this.attribute3 = value;
    }

    /**
     * Gets the value of the attribute4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE4() {
        return attribute4;
    }

    /**
     * Sets the value of the attribute4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE4(String value) {
        this.attribute4 = value;
    }

    /**
     * Gets the value of the attribute5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE5() {
        return attribute5;
    }

    /**
     * Sets the value of the attribute5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE5(String value) {
        this.attribute5 = value;
    }

    /**
     * Gets the value of the tagnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAGNUMBER() {
        return tagnumber;
    }

    /**
     * Sets the value of the tagnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAGNUMBER(String value) {
        this.tagnumber = value;
    }

}
