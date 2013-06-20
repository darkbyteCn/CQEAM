
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportVSetValueInfoSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportVSetValueInfoSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ImportVSetValueInfoSrvInputItem" type="{http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv}ImportVSetValueInfoSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportVSetValueInfoSrvInputCollection", propOrder = {
    "importVSetValueInfoSrvInputItem"
})
public class ImportVSetValueInfoSrvInputCollection {

    @XmlElement(name = "ImportVSetValueInfoSrvInputItem")
    protected List<ImportVSetValueInfoSrvInputItem> importVSetValueInfoSrvInputItem;

    /**
     * Gets the value of the importVSetValueInfoSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importVSetValueInfoSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportVSetValueInfoSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportVSetValueInfoSrvInputItem }
     * 
     * 
     */
    public List<ImportVSetValueInfoSrvInputItem> getImportVSetValueInfoSrvInputItem() {
        if (importVSetValueInfoSrvInputItem == null) {
            importVSetValueInfoSrvInputItem = new ArrayList<ImportVSetValueInfoSrvInputItem>();
        }
        return this.importVSetValueInfoSrvInputItem;
    }

}
