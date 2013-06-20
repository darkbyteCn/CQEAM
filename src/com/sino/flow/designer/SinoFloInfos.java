package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.NodeList;


public class SinoFloInfos extends SinoDocBases
{

    public SinoFloInfos()
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

    public void add(SinoFloInfo wfflow)
    {
        ISNULList.addElement(wfflow);
    }

    public boolean remove(SinoFloInfo wfflow)
    {
        return ISNULList.removeElement(wfflow);
    }

    public void removeAll()
    {
        ISNULList.removeAllElements();
    }

    public boolean readFrom(InputStream inputstream)
    {
        int i = SinoTools.readAInteger(inputstream);
        if(i < 0)
            return false;
        for(int j = 0; j < i; j++)
        {
            SinoFloInfo wfflow = new SinoFloInfo();
            if(!wfflow.readFrom(inputstream))
                return false;
            ISNULList.addElement(wfflow);
        }

        if(i > 0)
            bLoad = true;
        return true;
    }



    public boolean loadFromDomNodeList(NodeList nodelist, boolean flag)
    {
        int i = 0;
        boolean flag1 = false;
        try
        {
            for(int j = 0; j < nodelist.getLength(); j++)
            {
                SinoFloInfo wfflow = new SinoFloInfo();
                if(nodelist.item(j).getNodeType() == 1 && wfflow.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                {
                    ISNULList.addElement(wfflow);
                    i++;
                }
            }

            flag1 = true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            flag1 = false;
        }
        finally
        {
            if(i > 0)
                bLoad = true;
           
        }
         return flag1;
    }

    public void dump()
    {
        for(int i = 0; i < getCount(); i++)
        {
            SinoFloInfo wfflow = (SinoFloInfo)getNthItem(i);
            wfflow.dump();
        }

    }
}
