
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAssetTransferIntercompanysSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferIntercompanysSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateAssetTransferIntercompanysSrvInputItem" type="{http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv}CreateAssetTransferIntercompanysSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferIntercompanysSrvInputCollection", propOrder = {
    "createAssetTransferIntercompanysSrvInputItem"
})
public class CreateAssetTransferIntercompanysSrvInputCollection {

    @XmlElement(name = "CreateAssetTransferIntercompanysSrvInputItem")
    protected List<CreateAssetTransferIntercompanysSrvInputItem> createAssetTransferIntercompanysSrvInputItem;

    /**
     * Gets the value of the createAssetTransferIntercompanysSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createAssetTransferIntercompanysSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateAssetTransferIntercompanysSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateAssetTransferIntercompanysSrvInputItem }
     * 
     * 
     */
    public List<CreateAssetTransferIntercompanysSrvInputItem> getCreateAssetTransferIntercompanysSrvInputItem() {
        if (createAssetTransferIntercompanysSrvInputItem == null) {
            createAssetTransferIntercompanysSrvInputItem = new ArrayList<CreateAssetTransferIntercompanysSrvInputItem>();
        }
        return this.createAssetTransferIntercompanysSrvInputItem;
    }

}
