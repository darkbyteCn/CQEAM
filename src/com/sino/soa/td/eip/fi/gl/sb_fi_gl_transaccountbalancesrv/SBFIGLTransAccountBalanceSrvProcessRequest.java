
package com.sino.soa.td.eip.fi.gl.sb_fi_gl_transaccountbalancesrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="SET_OF_BOOKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "setofbooks",
    "periodname"
})
@XmlRootElement(name = "SB_FI_GL_TransAccountBalanceSrvProcessRequest")
public class SBFIGLTransAccountBalanceSrvProcessRequest {

    @XmlElement(name = "ENV_CODE", required = true, nillable = true)
    protected String envcode;
    @XmlElement(name = "SET_OF_BOOKS", required = true, nillable = true)
    protected String setofbooks;
    @XmlElement(name = "PERIOD_NAME", required = true, nillable = true)
    protected String periodname;

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
     * Gets the value of the setofbooks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSETOFBOOKS() {
        return setofbooks;
    }

    /**
     * Sets the value of the setofbooks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSETOFBOOKS(String value) {
        this.setofbooks = value;
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

}
