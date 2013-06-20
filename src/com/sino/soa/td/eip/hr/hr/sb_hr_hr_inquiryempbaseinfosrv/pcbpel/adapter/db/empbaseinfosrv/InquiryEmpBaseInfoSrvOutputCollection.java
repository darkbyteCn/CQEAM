
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryEmpBaseInfoSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryEmpBaseInfoSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryEmpBaseInfoSrvOutputItem" type="{http://xmlns.oracle.com/pcbpel/adapter/db/EmpBaseInfoSrv}InquiryEmpBaseInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryEmpBaseInfoSrvOutputCollection", propOrder = {
    "inquiryEmpBaseInfoSrvOutputItem"
})
public class InquiryEmpBaseInfoSrvOutputCollection {

    @XmlElement(name = "InquiryEmpBaseInfoSrvOutputItem")
    protected List<InquiryEmpBaseInfoSrvOutputItem> inquiryEmpBaseInfoSrvOutputItem;

    /**
     * Gets the value of the inquiryEmpBaseInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryEmpBaseInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryEmpBaseInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryEmpBaseInfoSrvOutputItem }
     * 
     * 
     */
    public List<InquiryEmpBaseInfoSrvOutputItem> getInquiryEmpBaseInfoSrvOutputItem() {
        if (inquiryEmpBaseInfoSrvOutputItem == null) {
            inquiryEmpBaseInfoSrvOutputItem = new ArrayList<InquiryEmpBaseInfoSrvOutputItem>();
        }
        return this.inquiryEmpBaseInfoSrvOutputItem;
    }

}
