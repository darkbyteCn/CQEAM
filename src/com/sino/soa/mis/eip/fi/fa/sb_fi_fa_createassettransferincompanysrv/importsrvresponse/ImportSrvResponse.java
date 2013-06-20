
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.importsrvresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportSrvResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorCollection" type="{http://eip.zte.com/common/ImportSrvResponse}ErrorCollection"/>
 *         &lt;element name="ResponseCollecion" type="{http://eip.zte.com/common/ImportSrvResponse}ResponseCollecion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportSrvResponse", propOrder = {
    "errorFlag",
    "errorMessage",
    "errorCollection",
    "responseCollecion"
})
public class ImportSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true)
    protected String errorMessage;
    @XmlElement(name = "ErrorCollection", required = true)
    protected ErrorCollection errorCollection;
    @XmlElement(name = "ResponseCollecion", required = true)
    protected ResponseCollecion responseCollecion;

    /**
     * Gets the value of the errorFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorFlag() {
        return errorFlag;
    }

    /**
     * Sets the value of the errorFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorFlag(String value) {
        this.errorFlag = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the errorCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorCollection }
     *     
     */
    public ErrorCollection getErrorCollection() {
        return errorCollection;
    }

    /**
     * Sets the value of the errorCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorCollection }
     *     
     */
    public void setErrorCollection(ErrorCollection value) {
        this.errorCollection = value;
    }

    /**
     * Gets the value of the responseCollecion property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseCollecion }
     *     
     */
    public ResponseCollecion getResponseCollecion() {
        return responseCollecion;
    }

    /**
     * Sets the value of the responseCollecion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseCollecion }
     *     
     */
    public void setResponseCollecion(ResponseCollecion value) {
        this.responseCollecion = value;
    }

}
