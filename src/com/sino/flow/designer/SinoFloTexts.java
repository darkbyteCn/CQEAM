package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.NodeList;

public class SinoFloTexts extends SinoDocBases
{

    public SinoFloTexts()
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

    public void add(SinoFloText wflabel)
    {
        ISNULList.addElement(wflabel);
    }

    public boolean remove(SinoFloText wflabel)
    {
        return ISNULList.removeElement(wflabel);
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
            SinoFloText wflabel = new SinoFloText();
            if(!wflabel.readFrom(inputstream))
                return false;
            ISNULList.addElement(wflabel);
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
                SinoFloText wflabel = new SinoFloText();
                if(nodelist.item(j).getNodeType() == 1 && wflabel.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                {
                    ISNULList.addElement(wflabel);
                    i++;
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
            SinoFloText wflabel = (SinoFloText)getNthItem(i);
            wflabel.dump();
        }

    }
}
