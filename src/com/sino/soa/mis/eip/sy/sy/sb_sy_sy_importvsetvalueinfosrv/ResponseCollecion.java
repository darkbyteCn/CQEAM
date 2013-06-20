
package com.sino.soa.mis.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseCollecion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseCollecion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseItem" type="{http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv}ResponseItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseCollecion", propOrder = {
    "responseItem"
})
public class ResponseCollecion {

    @XmlElement(name = "ResponseItem")
    protected List<ResponseItem> responseItem;

    /**
     * Gets the value of the responseItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responseItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponseItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponseItem }
     * 
     * 
     */
    public List<ResponseItem> getResponseItem() {
        if (responseItem == null) {
            responseItem = new ArrayList<ResponseItem>();
        }
        return this.responseItem;
    }

}
