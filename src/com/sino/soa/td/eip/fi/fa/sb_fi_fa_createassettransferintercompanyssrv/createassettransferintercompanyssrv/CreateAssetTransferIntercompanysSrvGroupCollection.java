
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAssetTransferIntercompanysSrvGroupCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferIntercompanysSrvGroupCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateAssetTransferIntercompanysSrvInputCollection" type="{http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv}CreateAssetTransferIntercompanysSrvInputCollection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferIntercompanysSrvGroupCollection", propOrder = {
    "createAssetTransferIntercompanysSrvInputCollection"
})
public class CreateAssetTransferIntercompanysSrvGroupCollection {

    @XmlElement(name = "CreateAssetTransferIntercompanysSrvInputCollection")
    protected List<CreateAssetTransferIntercompanysSrvInputCollection> createAssetTransferIntercompanysSrvInputCollection;

    /**
     * Gets the value of the createAssetTransferIntercompanysSrvInputCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createAssetTransferIntercompanysSrvInputCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateAssetTransferIntercompanysSrvInputCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateAssetTransferIntercompanysSrvInputCollection }
     * 
     * 
     */
    public List<CreateAssetTransferIntercompanysSrvInputCollection> getCreateAssetTransferIntercompanysSrvInputCollection() {
        if (createAssetTransferIntercompanysSrvInputCollection == null) {
            createAssetTransferIntercompanysSrvInputCollection = new ArrayList<CreateAssetTransferIntercompanysSrvInputCollection>();
        }
        return this.createAssetTransferIntercompanysSrvInputCollection;
    }

}
