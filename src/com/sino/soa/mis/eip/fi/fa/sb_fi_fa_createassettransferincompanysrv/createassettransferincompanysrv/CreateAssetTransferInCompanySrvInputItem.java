
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAssetTransferInCompanySrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferInCompanySrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_COMBINATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EXPENSE_DEPRN_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EXPENSE_DEPRN_CCID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_UNIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSIGNED_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSIGNED_TO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferInCompanySrvInputItem", propOrder = {
    "prikey",
    "assetid",
    "assetnumber",
    "tagnumber",
    "description",
    "locationcombinationcode",
    "locationid",
    "expensedeprncode",
    "expensedeprnccid",
    "assetunit",
    "assignednumber",
    "assignedto",
    "createdby",
    "employeenumber"
})
public class CreateAssetTransferInCompanySrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true, nillable = true)
    protected String prikey;
    @XmlElement(name = "ASSET_ID", required = true, nillable = true)
    protected BigDecimal assetid;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "LOCATION_COMBINATION_CODE", required = true, nillable = true)
    protected String locationcombinationcode;
    @XmlElement(name = "LOCATION_ID", required = true, nillable = true)
    protected BigDecimal locationid;
    @XmlElement(name = "EXPENSE_DEPRN_CODE", required = true, nillable = true)
    protected String expensedeprncode;
    @XmlElement(name = "EXPENSE_DEPRN_CCID", required = true, nillable = true)
    protected BigDecimal expensedeprnccid;
    @XmlElement(name = "ASSET_UNIT", required = true, nillable = true)
    protected BigDecimal assetunit;
    @XmlElement(name = "ASSIGNED_NUMBER", required = true, nillable = true)
    protected String assignednumber;
    @XmlElement(name = "ASSIGNED_TO", required = true, nillable = true)
    protected BigDecimal assignedto;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;

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
     * Gets the value of the assetid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETID() {
        return assetid;
    }

    /**
     * Sets the value of the assetid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETID(BigDecimal value) {
        this.assetid = value;
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
     * Gets the value of the locationcombinationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONCOMBINATIONCODE() {
        return locationcombinationcode;
    }

    /**
     * Sets the value of the locationcombinationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONCOMBINATIONCODE(String value) {
        this.locationcombinationcode = value;
    }

    /**
     * Gets the value of the locationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLOCATIONID() {
        return locationid;
    }

    /**
     * Sets the value of the locationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLOCATIONID(BigDecimal value) {
        this.locationid = value;
    }

    /**
     * Gets the value of the expensedeprncode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXPENSEDEPRNCODE() {
        return expensedeprncode;
    }

    /**
     * Sets the value of the expensedeprncode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPENSEDEPRNCODE(String value) {
        this.expensedeprncode = value;
    }

    /**
     * Gets the value of the expensedeprnccid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEXPENSEDEPRNCCID() {
        return expensedeprnccid;
    }

    /**
     * Sets the value of the expensedeprnccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEXPENSEDEPRNCCID(BigDecimal value) {
        this.expensedeprnccid = value;
    }

    /**
     * Gets the value of the assetunit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETUNIT() {
        return assetunit;
    }

    /**
     * Sets the value of the assetunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETUNIT(BigDecimal value) {
        this.assetunit = value;
    }

    /**
     * Gets the value of the assignednumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNEDNUMBER() {
        return assignednumber;
    }

    /**
     * Sets the value of the assignednumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNEDNUMBER(String value) {
        this.assignednumber = value;
    }

    /**
     * Gets the value of the assignedto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSIGNEDTO() {
        return assignedto;
    }

    /**
     * Sets the value of the assignedto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSIGNEDTO(BigDecimal value) {
        this.assignedto = value;
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

}
