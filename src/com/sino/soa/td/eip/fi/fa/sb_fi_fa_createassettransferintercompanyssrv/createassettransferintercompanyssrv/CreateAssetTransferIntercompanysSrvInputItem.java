
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CreateAssetTransferIntercompanysSrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferIntercompanysSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FROM_BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TO_BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COMPANY_FROM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COMPANY_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CATEGORY_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CONCATENATED_SEGMENTS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TRANSACTION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LOCATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EXPENSE_DEPRN_CCID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EXPENSE_DEPRN_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "CreateAssetTransferIntercompanysSrvInputItem", propOrder = {
    "prikey",
    "frombooktypecode",
    "tobooktypecode",
    "companyfrom",
    "companyto",
    "categoryid",
    "concatenatedsegments",
    "assetid",
    "assetnumber",
    "tagnumber",
    "transactiondate",
    "locationcode",
    "locationid",
    "expensedeprnccid",
    "expensedeprncode",
    "assetunit",
    "assignednumber",
    "assignedto",
    "createdby",
    "employeenumber"
})
public class CreateAssetTransferIntercompanysSrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true)
    protected String prikey;
    @XmlElement(name = "FROM_BOOK_TYPE_CODE", required = true)
    protected String frombooktypecode;
    @XmlElement(name = "TO_BOOK_TYPE_CODE", required = true)
    protected String tobooktypecode;
    @XmlElement(name = "COMPANY_FROM", required = true)
    protected String companyfrom;
    @XmlElement(name = "COMPANY_TO", required = true)
    protected String companyto;
    @XmlElement(name = "CATEGORY_ID", required = true)
    protected BigDecimal categoryid;
    @XmlElement(name = "CONCATENATED_SEGMENTS", required = true)
    protected String concatenatedsegments;
    @XmlElement(name = "ASSET_ID", required = true)
    protected BigDecimal assetid;
    @XmlElement(name = "ASSET_NUMBER", required = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true)
    protected String tagnumber;
    @XmlElement(name = "TRANSACTION_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactiondate;
    @XmlElement(name = "LOCATION_CODE", required = true)
    protected String locationcode;
    @XmlElement(name = "LOCATION_ID", required = true)
    protected BigDecimal locationid;
    @XmlElement(name = "EXPENSE_DEPRN_CCID", required = true)
    protected String expensedeprnccid;
    @XmlElement(name = "EXPENSE_DEPRN_CODE", required = true)
    protected String expensedeprncode;
    @XmlElement(name = "ASSET_UNIT", required = true)
    protected BigDecimal assetunit;
    @XmlElement(name = "ASSIGNED_NUMBER", required = true)
    protected String assignednumber;
    @XmlElement(name = "ASSIGNED_TO", required = true)
    protected BigDecimal assignedto;
    @XmlElement(name = "CREATED_BY", required = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true)
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
     * Gets the value of the frombooktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFROMBOOKTYPECODE() {
        return frombooktypecode;
    }

    /**
     * Sets the value of the frombooktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFROMBOOKTYPECODE(String value) {
        this.frombooktypecode = value;
    }

    /**
     * Gets the value of the tobooktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOBOOKTYPECODE() {
        return tobooktypecode;
    }

    /**
     * Sets the value of the tobooktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOBOOKTYPECODE(String value) {
        this.tobooktypecode = value;
    }

    /**
     * Gets the value of the companyfrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPANYFROM() {
        return companyfrom;
    }

    /**
     * Sets the value of the companyfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPANYFROM(String value) {
        this.companyfrom = value;
    }

    /**
     * Gets the value of the companyto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPANYTO() {
        return companyto;
    }

    /**
     * Sets the value of the companyto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPANYTO(String value) {
        this.companyto = value;
    }

    /**
     * Gets the value of the categoryid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCATEGORYID() {
        return categoryid;
    }

    /**
     * Sets the value of the categoryid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCATEGORYID(BigDecimal value) {
        this.categoryid = value;
    }

    /**
     * Gets the value of the concatenatedsegments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCATENATEDSEGMENTS() {
        return concatenatedsegments;
    }

    /**
     * Sets the value of the concatenatedsegments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCATENATEDSEGMENTS(String value) {
        this.concatenatedsegments = value;
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
     * Gets the value of the transactiondate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTRANSACTIONDATE() {
        return transactiondate;
    }

    /**
     * Sets the value of the transactiondate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTRANSACTIONDATE(XMLGregorianCalendar value) {
        this.transactiondate = value;
    }

    /**
     * Gets the value of the locationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONCODE() {
        return locationcode;
    }

    /**
     * Sets the value of the locationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONCODE(String value) {
        this.locationcode = value;
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
     * Gets the value of the expensedeprnccid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXPENSEDEPRNCCID() {
        return expensedeprnccid;
    }

    /**
     * Sets the value of the expensedeprnccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXPENSEDEPRNCCID(String value) {
        this.expensedeprnccid = value;
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
