
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="REQUEST_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RECORD_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseItem", propOrder = {
    "requestid",
    "recordnumber"
})
public class ResponseItem {

    @XmlElement(name = "REQUEST_ID", required = true)
    protected String requestid;
    @XmlElement(name = "RECORD_NUMBER", required = true)
    protected String recordnumber;

    /**
     * Gets the value of the requestid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREQUESTID() {
        return requestid;
    }

    /**
     * Sets the value of the requestid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREQUESTID(String value) {
        this.requestid = value;
    }

    /**
     * Gets the value of the recordnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECORDNUMBER() {
        return recordnumber;
    }

    /**
     * Sets the value of the recordnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECORDNUMBER(String value) {
        this.recordnumber = value;
    }

}
