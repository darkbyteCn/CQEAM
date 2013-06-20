package com.sino.flow.designer;

import java.io.InputStream;


public class SinoFloProjects extends SinoDocBases
{

    public SinoFloProjects()
    {
    }
    public int load()
    {
        return 1;
    }
    public boolean loadByUnid(String s)
    {
        return true;
    }

    public void add(SinoFloProject wfproj)
    {
        ISNULList.addElement(wfproj);
    }

    public boolean remove(SinoFloProject wfproj)
    {
        return ISNULList.removeElement(wfproj);
    }

    public void removeAll()
    {
        ISNULList.removeAllElements();
    }

    public boolean addFrom(InputStream inputstream)
    {
        SinoFloProject wfproj = new SinoFloProject();
        if(!wfproj.readFrom(inputstream))
        {
            DebugBean.log("Load project error!!!!!");
            return false;
        } else
        {
            add(wfproj);
            bLoad = true;
            return bLoad;
        }
    }

    public void dump()
    {
        for(int i = 0; i < getCount(); i++)
        {
            SinoFloProject wfproj = (SinoFloProject)getNthItem(i);
            wfproj.dump();
        }

    }
}
