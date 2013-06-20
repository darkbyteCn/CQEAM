
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryEmpBaseInfoSrvResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryEmpBaseInfoSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InquiryEmpBaseInfoSrvOutputCollection" type="{http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv}InquiryEmpBaseInfoSrvOutputCollection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryEmpBaseInfoSrvResponse", propOrder = {
    "errorFlag",
    "errorMessage",
    "inquiryEmpBaseInfoSrvOutputCollection"
})
public class InquiryEmpBaseInfoSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true, nillable = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true, nillable = true)
    protected String errorMessage;
    @XmlElement(name = "InquiryEmpBaseInfoSrvOutputCollection")
    protected List<InquiryEmpBaseInfoSrvOutputCollection> inquiryEmpBaseInfoSrvOutputCollection;

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
     * Gets the value of the inquiryEmpBaseInfoSrvOutputCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryEmpBaseInfoSrvOutputCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryEmpBaseInfoSrvOutputCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryEmpBaseInfoSrvOutputCollection }
     * 
     * 
     */
    public List<InquiryEmpBaseInfoSrvOutputCollection> getInquiryEmpBaseInfoSrvOutputCollection() {
        if (inquiryEmpBaseInfoSrvOutputCollection == null) {
            inquiryEmpBaseInfoSrvOutputCollection = new ArrayList<InquiryEmpBaseInfoSrvOutputCollection>();
        }
        return this.inquiryEmpBaseInfoSrvOutputCollection;
    }

}
