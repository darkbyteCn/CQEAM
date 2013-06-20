package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SinoFloGroups extends SinoDocBases
{

    public SinoFloGroups()
    {
    }

    public boolean loadByUnid(String s)
    {
        return true;
    }

    public int load()
    {
        return 1;
    }

    public void add(SinoFloGroup wfgroup)
    {
        ISNULList.addElement(wfgroup);
    }

    public boolean remove(SinoFloGroup wfgroup)
    {
        return ISNULList.removeElement(wfgroup);
    }

    public void removeAll()
    {
        ISNULList.removeAllElements();
    }

    public boolean readFrom(InputStream inputstream)
    {
        int i =SinoTools.readAInteger(inputstream);
        if(i < 0)
            return false;
        for(int j = 0; j < i; j++)
        {
            SinoFloGroup wfgroup = new SinoFloGroup();
            if(!wfgroup.readFrom(inputstream))
                return false;
            ISNULList.addElement(wfgroup);
        }

        if(i > 0)
            bLoad = true;
        return true;
    }


    public boolean loadFromDomNodeList(NodeList nodelist)
    {
        int i = 0;
        boolean flag = false;
        try
        {
            for(int j = 0; j < nodelist.getLength(); j++)
            {
                SinoFloGroup wfgroup = new SinoFloGroup();
                if(nodelist.item(j).getNodeType() == 1)
                {
                    NodeList nodelist1 = nodelist.item(j).getChildNodes();
                    for(int k = 0; k < nodelist1.getLength(); k++)
                    {
                        Node node = nodelist1.item(k);
                    }

                    if(wfgroup.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                    {
                        ISNULList.addElement(wfgroup);
                        i++;
                    }
                }
            }

            flag = true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            flag = false;
        }
        finally
        {
            if(i > 0)
                bLoad = true;
            
        }
        return flag;
    }

    public void dump()
    {
        for(int i = 0; i < getCount(); i++)
        {
            SinoFloGroup wfgroup = (SinoFloGroup)getNthItem(i);
            wfgroup.dump();
        }

    }
}
