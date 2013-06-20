package com.sino.pda.util;

/**
 * User: zhoujs
 * Date: 2007-10-25
 * Time: 16:52:10
 * Function:工单xml解析，从ETS移植过来
 */

import java.io.File;
import java.util.List;

import com.sino.base.log.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlUtil {
    private Document xmlDoc;

    public XmlUtil() {
    }

	private String UnixStringToGB(String oriStr)
	{
		//don't need deal with it
		return oriStr;

	}

    public boolean loadXmlFile(String xmlFile) {
        try {
            SAXBuilder builder = new SAXBuilder();
            xmlDoc = builder.build(new File(xmlFile));
            return true;
        } catch (org.jdom.JDOMException e) {
            Logger.logError("load xml file error:" + e.toString());
            return false;
        } catch (Exception e) {
             Logger.logError("load xml file error:" + e.toString());
            return false;
        }

    }

    public org.jdom.Element getRootElement() {
        return xmlDoc.getRootElement();
    }

    //取得根的所有子项
    public java.util.List getAllRootChildren() {
        Element foo = getRootElement();
        java.util.List allChildren = foo.getChildren();

        return allChildren;
    }

    //取得allChild的第nIndex子Child
    public java.util.List getAllChild(org.jdom.Element jelement) {
        java.util.List newChild = jelement.getChildren();
        return newChild;
    }

    //取得allChild的第nIndex子项元素
    public org.jdom.Element getNthElement(java.util.List allChild,
                                               int nIndex) {
        org.jdom.Element jelement = (org.jdom.Element) allChild.get(nIndex);

        return jelement;
    }

    //取得allChild的第nIndex子项的itemName的值
    public String getNthListItemValue(List allChild, int nIndex, String itemName) {
        org.jdom.Element jelement = getNthElement(allChild, nIndex);
        String m_v = jelement.getChild(itemName).getText();
        if (m_v == null)
            m_v = "";
        return UnixStringToGB(m_v);
    }

    public String getNthListAttrValue(List allChild, int nIndex, String attrName) {
        org.jdom.Element jelement = getNthElement(allChild, nIndex);
        String m_v = jelement.getAttributeValue(attrName);
        if (m_v == null)
            m_v = "";
        return UnixStringToGB(m_v);
    }

    public String getNthListName(List allChild, int nIndex) {
        org.jdom.Element jelement = getNthElement(allChild, nIndex);
        String m_v = jelement.getName();
        if (m_v == null)
            m_v = "";
        return UnixStringToGB(m_v);
    }

    public String getElementAttrValue(org.jdom.Element jelement,
                                      String attrName) {
        String m_v = jelement.getAttributeValue(attrName);
        if (m_v == null)
            m_v = "";
        return UnixStringToGB(m_v);
    }

    public String getElementName(org.jdom.Element jelement) {
        String m_v = jelement.getName();
        if (m_v == null)
            m_v = "";
        return UnixStringToGB(m_v);
    }

}

