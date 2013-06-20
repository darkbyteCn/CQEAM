
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_transretiredassetdetailsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ENV_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_RETIRE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_RETIRE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "envcode",
    "booktypecode",
    "startretiredate",
    "endretiredate"
})
@XmlRootElement(name = "SB_FI_FA_TransRetiredAssetDetailSrvProcessRequest")
public class SBFIFATransRetiredAssetDetailSrvProcessRequest {

    @XmlElement(name = "ENV_CODE", required = true, nillable = true)
    protected String envcode;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "START_RETIRE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startretiredate;
    @XmlElement(name = "END_RETIRE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endretiredate;

    /**
     * Gets the value of the envcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENVCODE() {
        return envcode;
    }

    /**
     * Sets the value of the envcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENVCODE(String value) {
        this.envcode = value;
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
     * Gets the value of the startretiredate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTRETIREDATE() {
        return startretiredate;
    }

    /**
     * Sets the value of the startretiredate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTRETIREDATE(XMLGregorianCalendar value) {
        this.startretiredate = value;
    }

    /**
     * Gets the value of the endretiredate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDRETIREDATE() {
        return endretiredate;
    }

    /**
     * Sets the value of the endretiredate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDRETIREDATE(XMLGregorianCalendar value) {
        this.endretiredate = value;
    }

}
