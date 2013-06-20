
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem" type="{http://mss.cmcc.com/SB_FI_GL_PageInquiryAccountBalanceSrv}SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection", propOrder = {
    "sbfiglPageInquiryAccountBalanceSrvOutputItem"
})
public class SBFIGLPageInquiryAccountBalanceSrvOutputCollection {

    @XmlElement(name = "SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem")
    protected List<SBFIGLPageInquiryAccountBalanceSrvOutputItem> sbfiglPageInquiryAccountBalanceSrvOutputItem;

    /**
     * Gets the value of the sbfiglPageInquiryAccountBalanceSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sbfiglPageInquiryAccountBalanceSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSBFIGLPageInquiryAccountBalanceSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SBFIGLPageInquiryAccountBalanceSrvOutputItem }
     * 
     * 
     */
    public List<SBFIGLPageInquiryAccountBalanceSrvOutputItem> getSBFIGLPageInquiryAccountBalanceSrvOutputItem() {
        if (sbfiglPageInquiryAccountBalanceSrvOutputItem == null) {
            sbfiglPageInquiryAccountBalanceSrvOutputItem = new ArrayList<SBFIGLPageInquiryAccountBalanceSrvOutputItem>();
        }
        return this.sbfiglPageInquiryAccountBalanceSrvOutputItem;
    }

}
