
package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.NodeList;

public class SinoFloProcedures extends SinoDocBases
{

    public SinoFloProcedures()
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

    public void add(SinoFloProcedure wfproc)
    {
        ISNULList.addElement(wfproc);
    }

    public boolean remove(SinoFloProcedure wfproc)
    {
        return ISNULList.removeElement(wfproc);
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
            SinoFloProcedure wfproc = new SinoFloProcedure();
            if(!wfproc.readFrom(inputstream))
                return false;
            ISNULList.addElement(wfproc);
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
                SinoFloProcedure wfproc = new SinoFloProcedure();
                if(nodelist.item(j).getNodeType() == 1 && wfproc.loadFromDomNodeList(nodelist.item(j).getChildNodes()))
                {
                    ISNULList.addElement(wfproc);
                    i++;
                }
            }

            flag = true;
        }
        catch(Exception exception)
        {
            flag = false;
            exception.printStackTrace();
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
            SinoFloProcedure wfproc = (SinoFloProcedure)getNthItem(i);
            wfproc.dump();
        }

    }
}
