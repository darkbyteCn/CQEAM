// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package com.sino.flow.designer;

import java.util.Vector;

// Referenced classes of package com.sinoflow:
//            sinoItem, Util, constd

public abstract class SinoDocBases
    implements Cloneable
{

    protected Vector ISNULList;
    protected boolean bLoad;
    protected int ncurrent;
    protected String formname;
    protected String query;

    public SinoDocBases()
    {
        bLoad = false;
        ncurrent = 0;
        ISNULList = new Vector();
    }

    public Object clone()
    {
        try
        {
            SinoDocBases sinoitems = (SinoDocBases)super.clone();
            sinoitems.ISNULList = (Vector)ISNULList.clone();
            sinoitems.bLoad = false;
            sinoitems.ncurrent = ncurrent;
            sinoitems.formname = formname;
            sinoitems.query = query;
            return sinoitems;
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError("Unexpected CloneNotSUpportedException: " + clonenotsupportedexception.getMessage());
        }
    }

    public void queryAdd(String s)
    {
        if(query.length() > 0)
        {
            if(s.length() > 0)
                query = query + " & " + s;
        } else
        if(s.length() > 0)
            query = s;
    }

    public int getCount()
    {
        return ISNULList.size();
    }

    public boolean init()
    {
        ISNULList.removeAllElements();
        int i = load();
        if(i >= 0)
            bLoad = true;
        else
            bLoad = false;
        return bLoad;
    }

    public boolean initByUnid(String s)
    {
        ISNULList = new Vector();
        bLoad = false;
        if(SinoTools.VAL(s))
            bLoad = loadByUnid(s);
        return bLoad;
    }

    public SinoDocBase getFirstItem()
    {
        if(bLoad)
        {
            ncurrent = 0;
            return (SinoDocBase)ISNULList.firstElement();
        } else
        {
            return null;
        }
    }

    public SinoDocBase getNextItem()
    {
        if(bLoad)
        {
            ncurrent++;
            if(ncurrent < getCount())
                return (SinoDocBase)ISNULList.elementAt(ncurrent);
            else
                return null;
        } else
        {
            return null;
        }
    }

    public SinoDocBase getNthItem(int i)
    {
        if(bLoad)
        {
            if(i > ISNULList.size() - 1)
                return null;
            if(i < 0)
            {
                return null;
            } else
            {
                SinoDocBase sinoitem = (SinoDocBase)ISNULList.elementAt(i);
                return sinoitem;
            }
        } else
        {
            return null;
        }
    }


    public SinoDocBase getItemByKey(String s)
    {
        SinoDocBase sinoitem = null;
        for(int i = 0; i < ISNULList.size(); i++)
        {
            sinoitem = (SinoDocBase)ISNULList.elementAt(i);
            if(sinoitem.key.equalsIgnoreCase(s))
                break;
        }

        return sinoitem;
    }

    public SinoDocBase getItemByName(String s)
    {
        SinoDocBase sinoitem = null;
        for(int i = 0; i < ISNULList.size(); i++)
        {
            sinoitem = (SinoDocBase)ISNULList.elementAt(i);
            if(sinoitem.getValue("name").equalsIgnoreCase(s))
                break;
        }

        return sinoitem;
    }

    public SinoDocBase getItemByValue(String s, String s1)
    {
        SinoDocBase sinoitem = null;
        for(int i = 0; i < ISNULList.size(); i++)
        {
            sinoitem = (SinoDocBase)ISNULList.elementAt(i);
            if(sinoitem.getValue(s).equalsIgnoreCase(s1))
                break;
        }

        return sinoitem;
    }

    public SinoDocBase getItemByValue(String s, int i)
    {
        SinoDocBase sinoitem = null;
        for(int j = 0; j < ISNULList.size(); j++)
        {
            sinoitem = (SinoDocBase)ISNULList.elementAt(j);
            if(sinoitem.getFieldType(s) == 0 && sinoitem.getIntValue(s) == i)
                break;
        }

        return sinoitem;
    }

    protected abstract int load();

    protected abstract boolean loadByUnid(String s);
}
