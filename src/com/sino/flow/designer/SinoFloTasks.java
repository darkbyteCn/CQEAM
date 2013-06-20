package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.NodeList;

public class SinoFloTasks extends SinoDocBases
{

    public SinoFloTasks()
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

    public void add(SinoFloTask wftask)
    {
        ISNULList.addElement(wftask);
    }

    public boolean remove(SinoFloTask wftask)
    {
        return ISNULList.removeElement(wftask);
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
            SinoFloTask wftask = new SinoFloTask();
            if(!wftask.readFrom(inputstream))
                return false;
            ISNULList.addElement(wftask);
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
                SinoFloTask wftask = new SinoFloTask();
                if(nodelist.item(j).getNodeType() == 1 && wftask.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                {
                    ISNULList.addElement(wftask);
                    i++;
                }
            }

            flag = true;
        }
        catch(Exception exception)
        {
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
            SinoFloTask wftask = (SinoFloTask)getNthItem(i);
            wftask.dump();
        }

    }
}
