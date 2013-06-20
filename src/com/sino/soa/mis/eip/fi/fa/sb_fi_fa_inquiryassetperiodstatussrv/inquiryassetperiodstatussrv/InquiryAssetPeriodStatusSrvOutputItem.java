
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryAssetPeriodStatusSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetPeriodStatusSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PERIOD_OPEN_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PERIOD_CLOSE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="GL_TRANSFER_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetPeriodStatusSrvOutputItem", propOrder = {
    "booktypecode",
    "periodname",
    "startdate",
    "enddate",
    "periodopendate",
    "periodclosedate",
    "gltransferflag",
    "periodstatus"
})
public class InquiryAssetPeriodStatusSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "PERIOD_NAME", required = true, nillable = true)
    protected String periodname;
    @XmlElement(name = "START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdate;
    @XmlElement(name = "END_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enddate;
    @XmlElement(name = "PERIOD_OPEN_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodopendate;
    @XmlElement(name = "PERIOD_CLOSE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodclosedate;
    @XmlElement(name = "GL_TRANSFER_FLAG", required = true, nillable = true)
    protected String gltransferflag;
    @XmlElement(name = "PERIOD_STATUS", required = true, nillable = true)
    protected String periodstatus;

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
     * Gets the value of the periodname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODNAME() {
        return periodname;
    }

    /**
     * Sets the value of the periodname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODNAME(String value) {
        this.periodname = value;
    }

    /**
     * Gets the value of the startdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTDATE() {
        return startdate;
    }

    /**
     * Sets the value of the startdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTDATE(XMLGregorianCalendar value) {
        this.startdate = value;
    }

    /**
     * Gets the value of the enddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDDATE() {
        return enddate;
    }

    /**
     * Sets the value of the enddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDDATE(XMLGregorianCalendar value) {
        this.enddate = value;
    }

    /**
     * Gets the value of the periodopendate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPERIODOPENDATE() {
        return periodopendate;
    }

    /**
     * Sets the value of the periodopendate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPERIODOPENDATE(XMLGregorianCalendar value) {
        this.periodopendate = value;
    }

    /**
     * Gets the value of the periodclosedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPERIODCLOSEDATE() {
        return periodclosedate;
    }

    /**
     * Sets the value of the periodclosedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPERIODCLOSEDATE(XMLGregorianCalendar value) {
        this.periodclosedate = value;
    }

    /**
     * Gets the value of the gltransferflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGLTRANSFERFLAG() {
        return gltransferflag;
    }

    /**
     * Sets the value of the gltransferflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGLTRANSFERFLAG(String value) {
        this.gltransferflag = value;
    }

    /**
     * Gets the value of the periodstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODSTATUS() {
        return periodstatus;
    }

    /**
     * Sets the value of the periodstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODSTATUS(String value) {
        this.periodstatus = value;
    }

}
