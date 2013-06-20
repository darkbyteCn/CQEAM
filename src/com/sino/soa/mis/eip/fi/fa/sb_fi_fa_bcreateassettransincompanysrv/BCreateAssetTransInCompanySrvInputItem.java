
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BCreateAssetTransInCompanySrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BCreateAssetTransInCompanySrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LINE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COUNTRY_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STATUS_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSIGNED_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST_CC_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST_AC_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESPONSIBILITY_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RESPONSIBILITY_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BCreateAssetTransInCompanySrvInputItem", propOrder = {
    "prikey",
    "linenumber",
    "assetnumber",
    "tagnumber",
    "description",
    "countryto",
    "locationto",
    "statusto",
    "assignedto",
    "costccto",
    "costacto",
    "createdby",
    "employeenumber",
    "responsibilityid",
    "responsibilityname"
})
public class BCreateAssetTransInCompanySrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true, nillable = true)
    protected String prikey;
    @XmlElement(name = "LINE_NUMBER", required = true, nillable = true)
    protected BigDecimal linenumber;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "COUNTRY_TO", required = true, nillable = true)
    protected String countryto;
    @XmlElement(name = "LOCATION_TO", required = true, nillable = true)
    protected String locationto;
    @XmlElement(name = "STATUS_TO", required = true, nillable = true)
    protected String statusto;
    @XmlElement(name = "ASSIGNED_TO", required = true, nillable = true)
    protected String assignedto;
    @XmlElement(name = "COST_CC_TO", required = true, nillable = true)
    protected String costccto;
    @XmlElement(name = "COST_AC_TO", required = true, nillable = true)
    protected String costacto;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "RESPONSIBILITY_ID", required = true, nillable = true)
    protected BigDecimal responsibilityid;
    @XmlElement(name = "RESPONSIBILITY_NAME", required = true, nillable = true)
    protected String responsibilityname;

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
     * Gets the value of the linenumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLINENUMBER() {
        return linenumber;
    }

    /**
     * Sets the value of the linenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLINENUMBER(BigDecimal value) {
        this.linenumber = value;
    }

    /**
     * Gets the value of the assetnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETNUMBER() {
        return assetnumber;
    }

    /**
     * Sets the value of the assetnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETNUMBER(String value) {
        this.assetnumber = value;
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
     * Gets the value of the countryto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOUNTRYTO() {
        return countryto;
    }

    /**
     * Sets the value of the countryto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOUNTRYTO(String value) {
        this.countryto = value;
    }

    /**
     * Gets the value of the locationto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONTO() {
        return locationto;
    }

    /**
     * Sets the value of the locationto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONTO(String value) {
        this.locationto = value;
    }

    /**
     * Gets the value of the statusto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUSTO() {
        return statusto;
    }

    /**
     * Sets the value of the statusto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUSTO(String value) {
        this.statusto = value;
    }

    /**
     * Gets the value of the assignedto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNEDTO() {
        return assignedto;
    }

    /**
     * Sets the value of the assignedto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNEDTO(String value) {
        this.assignedto = value;
    }

    /**
     * Gets the value of the costccto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOSTCCTO() {
        return costccto;
    }

    /**
     * Sets the value of the costccto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOSTCCTO(String value) {
        this.costccto = value;
    }

    /**
     * Gets the value of the costacto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOSTACTO() {
        return costacto;
    }

    /**
     * Sets the value of the costacto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOSTACTO(String value) {
        this.costacto = value;
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

}
