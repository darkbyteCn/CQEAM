
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateAssetRetirmentSrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateAssetRetirmentSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MANUFACTURER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SERIAL_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MODEL_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESPONSIBILITY_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RESPONSIBILITY_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateAssetRetirmentSrvInputItem", propOrder = {
    "prikey",
    "booktypecode",
    "tagnumber",
    "description",
    "manufacturername",
    "serialnumber",
    "modelnumber",
    "createdby",
    "employeenumber",
    "responsibilityid",
    "responsibilityname",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11"
})
public class UpdateAssetRetirmentSrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true, nillable = true)
    protected String prikey;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "MANUFACTURER_NAME", required = true, nillable = true)
    protected String manufacturername;
    @XmlElement(name = "SERIAL_NUMBER", required = true, nillable = true)
    protected String serialnumber;
    @XmlElement(name = "MODEL_NUMBER", required = true, nillable = true)
    protected String modelnumber;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "RESPONSIBILITY_ID", required = true, nillable = true)
    protected BigDecimal responsibilityid;
    @XmlElement(name = "RESPONSIBILITY_NAME", required = true, nillable = true)
    protected String responsibilityname;
    @XmlElement(name = "ATTRIBUTE8", required = true, nillable = true)
    protected String attribute8;
    @XmlElement(name = "ATTRIBUTE9", required = true, nillable = true)
    protected String attribute9;
    @XmlElement(name = "ATTRIBUTE10", required = true, nillable = true)
    protected String attribute10;
    @XmlElement(name = "ATTRIBUTE11", required = true, nillable = true)
    protected String attribute11;

    /**
     * Gets the value of the prikey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIKEY() {
        return prikey;
    }

    /**
     * Sets the value of the prikey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIKEY(String value) {
        this.prikey = value;
    }

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

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIPTION() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIPTION(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the manufacturername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANUFACTURERNAME() {
        return manufacturername;
    }

    /**
     * Sets the value of the manufacturername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANUFACTURERNAME(String value) {
        this.manufacturername = value;
    }

    /**
     * Gets the value of the serialnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERIALNUMBER() {
        return serialnumber;
    }

    /**
     * Sets the value of the serialnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERIALNUMBER(String value) {
        this.serialnumber = value;
    }

    /**
     * Gets the value of the modelnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELNUMBER() {
        return modelnumber;
    }

    /**
     * Sets the value of the modelnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELNUMBER(String value) {
        this.modelnumber = value;
    }

    /**
     * Gets the value of the createdby property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCREATEDBY() {
        return createdby;
    }

    /**
     * Sets the value of the createdby property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCREATEDBY(BigDecimal value) {
        this.createdby = value;
    }

    /**
     * Gets the value of the employeenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENUMBER() {
        return employeenumber;
    }

    /**
     * Sets the value of the employeenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENUMBER(String value) {
        this.employeenumber = value;
    }

    /**
     * Gets the value of the responsibilityid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRESPONSIBILITYID() {
        return responsibilityid;
    }

    /**
     * Sets the value of the responsibilityid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRESPONSIBILITYID(BigDecimal value) {
        this.responsibilityid = value;
    }

    /**
     * Gets the value of the responsibilityname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESPONSIBILITYNAME() {
        return responsibilityname;
    }

    /**
     * Sets the value of the responsibilityname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESPONSIBILITYNAME(String value) {
        this.responsibilityname = value;
    }

    /**
     * Gets the value of the attribute8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE8() {
        return attribute8;
    }

    /**
     * Sets the value of the attribute8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE8(String value) {
        this.attribute8 = value;
    }

    /**
     * Gets the value of the attribute9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE9() {
        return attribute9;
    }

    /**
     * Sets the value of the attribute9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE9(String value) {
        this.attribute9 = value;
    }

    /**
     * Gets the value of the attribute10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE10() {
        return attribute10;
    }

    /**
     * Sets the value of the attribute10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE10(String value) {
        this.attribute10 = value;
    }

    /**
     * Gets the value of the attribute11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE11() {
        return attribute11;
    }

    /**
     * Sets the value of the attribute11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE11(String value) {
        this.attribute11 = value;
    }

}
