
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_importassetretirmentsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportAssetRetirmentSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportAssetRetirmentSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ImportAssetRetirmentSrvInputItem" type="{http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv}ImportAssetRetirmentSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportAssetRetirmentSrvInputCollection", propOrder = {
    "importAssetRetirmentSrvInputItem"
})
public class ImportAssetRetirmentSrvInputCollection {

    @XmlElement(name = "ImportAssetRetirmentSrvInputItem")
    protected List<ImportAssetRetirmentSrvInputItem> importAssetRetirmentSrvInputItem;

    /**
     * Gets the value of the importAssetRetirmentSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importAssetRetirmentSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportAssetRetirmentSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportAssetRetirmentSrvInputItem }
     * 
     * 
     */
    public List<ImportAssetRetirmentSrvInputItem> getImportAssetRetirmentSrvInputItem() {
        if (importAssetRetirmentSrvInputItem == null) {
            importAssetRetirmentSrvInputItem = new ArrayList<ImportAssetRetirmentSrvInputItem>();
        }
        return this.importAssetRetirmentSrvInputItem;
    }

}
