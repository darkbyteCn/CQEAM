package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.NodeList;

public class SinoFloRoles extends SinoDocBases
{

    public SinoFloRoles()
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

    public void add(SinoFloRole wfrole)
    {
        ISNULList.addElement(wfrole);
    }

    public boolean remove(SinoFloRole wfrole)
    {
        return ISNULList.removeElement(wfrole);
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
            SinoFloRole wfrole = new SinoFloRole();
            if(!wfrole.readFrom(inputstream))
                return false;
            ISNULList.addElement(wfrole);
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
                SinoFloRole wfrole = new SinoFloRole();
                if(nodelist.item(j).getNodeType() == 1)
                {
                    NodeList nodelist1 = nodelist.item(j).getChildNodes();
                    if(wfrole.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                    {
                        ISNULList.addElement(wfrole);
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
            SinoFloRole wfrole = (SinoFloRole)getNthItem(i);
            wfrole.dump();
        }

    }
}
