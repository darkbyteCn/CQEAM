package com.sino.flow.designer;

import java.io.InputStream;

public class SinoFloUsers extends SinoDocBases
{

    public SinoFloUsers()
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

    public void add(SinoFloUser wfuser)
    {
        ISNULList.addElement(wfuser);
    }

    public boolean remove(SinoFloUser wfuser)
    {
        return ISNULList.removeElement(wfuser);
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
            SinoFloUser wfuser = new SinoFloUser();
            if(!wfuser.readFrom(inputstream))
                return false;
            ISNULList.addElement(wfuser);
        }

        if(i > 0)
            bLoad = true;
        return true;
    }


    public void dump()
    {
        for(int i = 0; i < getCount(); i++)
        {
            SinoFloUser wfuser = (SinoFloUser)getNthItem(i);
            wfuser.dump();
        }

    }
}
