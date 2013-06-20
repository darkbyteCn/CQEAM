
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryAssetBookSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetBookSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BOOK_TYPE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SET_OF_BOOKS_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PRORATE_CALENDAR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORG_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DATE_INEFFECTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LAST_DEPRN_RUN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DEPRN_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetBookSrvOutputItem", propOrder = {
    "booktypecode",
    "booktypename",
    "orgname",
    "setofbooksname",
    "proratecalendar",
    "orgid",
    "dateineffective",
    "lastdeprnrundate",
    "deprnstatus",
    "lastupdatedate"
})
public class InquiryAssetBookSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "BOOK_TYPE_NAME", required = true, nillable = true)
    protected String booktypename;
    @XmlElement(name = "ORG_NAME", required = true, nillable = true)
    protected String orgname;
    @XmlElement(name = "SET_OF_BOOKS_NAME", required = true, nillable = true)
    protected String setofbooksname;
    @XmlElement(name = "PRORATE_CALENDAR", required = true, nillable = true)
    protected String proratecalendar;
    @XmlElement(name = "ORG_ID", required = true, nillable = true)
    protected BigDecimal orgid;
    @XmlElement(name = "DATE_INEFFECTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateineffective;
    @XmlElement(name = "LAST_DEPRN_RUN_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastdeprnrundate;
    @XmlElement(name = "DEPRN_STATUS", required = true, nillable = true)
    protected String deprnstatus;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

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
     * Gets the value of the booktypename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKTYPENAME() {
        return booktypename;
    }

    /**
     * Sets the value of the booktypename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKTYPENAME(String value) {
        this.booktypename = value;
    }

    /**
     * Gets the value of the orgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGNAME() {
        return orgname;
    }

    /**
     * Sets the value of the orgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGNAME(String value) {
        this.orgname = value;
    }

    /**
     * Gets the value of the setofbooksname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSETOFBOOKSNAME() {
        return setofbooksname;
    }

    /**
     * Sets the value of the setofbooksname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSETOFBOOKSNAME(String value) {
        this.setofbooksname = value;
    }

    /**
     * Gets the value of the proratecalendar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRORATECALENDAR() {
        return proratecalendar;
    }

    /**
     * Sets the value of the proratecalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRORATECALENDAR(String value) {
        this.proratecalendar = value;
    }

    /**
     * Gets the value of the orgid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGID() {
        return orgid;
    }

    /**
     * Sets the value of the orgid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGID(BigDecimal value) {
        this.orgid = value;
    }

    /**
     * Gets the value of the dateineffective property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEINEFFECTIVE() {
        return dateineffective;
    }

    /**
     * Sets the value of the dateineffective property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEINEFFECTIVE(XMLGregorianCalendar value) {
        this.dateineffective = value;
    }

    /**
     * Gets the value of the lastdeprnrundate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTDEPRNRUNDATE() {
        return lastdeprnrundate;
    }

    /**
     * Sets the value of the lastdeprnrundate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTDEPRNRUNDATE(XMLGregorianCalendar value) {
        this.lastdeprnrundate = value;
    }

    /**
     * Gets the value of the deprnstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPRNSTATUS() {
        return deprnstatus;
    }

    /**
     * Sets the value of the deprnstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPRNSTATUS(String value) {
        this.deprnstatus = value;
    }

    /**
     * Gets the value of the lastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * Sets the value of the lastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
    }

}
