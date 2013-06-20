
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetretirmentsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ImportAssetRetirmentSrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportAssetRetirmentSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_RRETIRED" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RETIREMENT_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RETIREMENT_PRORATE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURRENT_COST" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RETIREMENT_COST" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UNITS_RETIRED" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
@XmlType(name = "ImportAssetRetirmentSrvInputItem", propOrder = {
    "prikey",
    "booktypecode",
    "tagnumber",
    "description",
    "daterretired",
    "retirementtypecode",
    "retirementprorate",
    "currentcost",
    "retirementcost",
    "unitsretired",
    "createdby",
    "employeenumber"
})
public class ImportAssetRetirmentSrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true)
    protected String prikey;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true)
    protected String booktypecode;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "DATE_RRETIRED", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar daterretired;
    @XmlElement(name = "RETIREMENT_TYPE_CODE", required = true, nillable = true)
    protected String retirementtypecode;
    @XmlElement(name = "RETIREMENT_PRORATE", required = true, nillable = true)
    protected String retirementprorate;
    @XmlElement(name = "CURRENT_COST", required = true, nillable = true)
    protected BigDecimal currentcost;
    @XmlElement(name = "RETIREMENT_COST", required = true, nillable = true)
    protected BigDecimal retirementcost;
    @XmlElement(name = "UNITS_RETIRED", required = true, nillable = true)
    protected BigDecimal unitsretired;
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
     * Gets the value of the daterretired property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATERRETIRED() {
        return daterretired;
    }

    /**
     * Sets the value of the daterretired property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATERRETIRED(XMLGregorianCalendar value) {
        this.daterretired = value;
    }

    /**
     * Gets the value of the retirementtypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTTYPECODE() {
        return retirementtypecode;
    }

    /**
     * Sets the value of the retirementtypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTTYPECODE(String value) {
        this.retirementtypecode = value;
    }

    /**
     * Gets the value of the retirementprorate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTPRORATE() {
        return retirementprorate;
    }

    /**
     * Sets the value of the retirementprorate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTPRORATE(String value) {
        this.retirementprorate = value;
    }

    /**
     * Gets the value of the currentcost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCURRENTCOST() {
        return currentcost;
    }

    /**
     * Sets the value of the currentcost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCURRENTCOST(BigDecimal value) {
        this.currentcost = value;
    }

    /**
     * Gets the value of the retirementcost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRETIREMENTCOST() {
        return retirementcost;
    }

    /**
     * Sets the value of the retirementcost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRETIREMENTCOST(BigDecimal value) {
        this.retirementcost = value;
    }

    /**
     * Gets the value of the unitsretired property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUNITSRETIRED() {
        return unitsretired;
    }

    /**
     * Sets the value of the unitsretired property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUNITSRETIRED(BigDecimal value) {
        this.unitsretired = value;
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
