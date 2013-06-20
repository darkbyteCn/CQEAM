
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportAssetLocationCombinationSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportAssetLocationCombinationSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ImportAssetLocationCombinationSrvInputItem" type="{http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv}ImportAssetLocationCombinationSrvInputItem"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportAssetLocationCombinationSrvInputCollection", propOrder = {
    "importAssetLocationCombinationSrvInputItem"
})
public class ImportAssetLocationCombinationSrvInputCollection {

    @XmlElement(name = "ImportAssetLocationCombinationSrvInputItem", required = true)
    protected ImportAssetLocationCombinationSrvInputItem importAssetLocationCombinationSrvInputItem;

    /**
     * Gets the value of the importAssetLocationCombinationSrvInputItem property.
     * 
     * @return
     *     possible object is
     *     {@link ImportAssetLocationCombinationSrvInputItem }
     *     
     */
    public ImportAssetLocationCombinationSrvInputItem getImportAssetLocationCombinationSrvInputItem() {
        return importAssetLocationCombinationSrvInputItem;
    }

    /**
     * Sets the value of the importAssetLocationCombinationSrvInputItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportAssetLocationCombinationSrvInputItem }
     *     
     */
    public void setImportAssetLocationCombinationSrvInputItem(ImportAssetLocationCombinationSrvInputItem value) {
        this.importAssetLocationCombinationSrvInputItem = value;
    }

}
