
package com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorItem" type="{http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv}ErrorItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorCollection", propOrder = {
    "errorItem"
})
public class ErrorCollection {

    @XmlElement(name = "ErrorItem", nillable = true)
    protected List<ErrorItem> errorItem;

    /**
     * Gets the value of the errorItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorItem }
     * 
     * 
     */
    public List<ErrorItem> getErrorItem() {
        if (errorItem == null) {
            errorItem = new ArrayList<ErrorItem>();
        }
        return this.errorItem;
    }

}
