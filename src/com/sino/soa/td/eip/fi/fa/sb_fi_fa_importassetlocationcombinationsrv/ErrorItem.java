
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ENTITY_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RECORD_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERROR_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorItem", propOrder = {
    "entityname",
    "recordnumber",
    "errormessage"
})
public class ErrorItem {

    @XmlElement(name = "ENTITY_NAME", required = true)
    protected String entityname;
    @XmlElement(name = "RECORD_NUMBER", required = true)
    protected String recordnumber;
    @XmlElement(name = "ERROR_MESSAGE", required = true)
    protected String errormessage;

    /**
     * Gets the value of the entityname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENTITYNAME() {
        return entityname;
    }

    /**
     * Sets the value of the entityname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENTITYNAME(String value) {
        this.entityname = value;
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

    /**
     * Gets the value of the errormessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERRORMESSAGE() {
        return errormessage;
    }

    /**
     * Sets the value of the errormessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERRORMESSAGE(String value) {
        this.errormessage = value;
    }

}
