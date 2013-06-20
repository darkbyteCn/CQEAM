
package com.sino.flow.designer;

import java.util.Date;
import java.util.Vector;

public class SinoDocField
    implements Cloneable
{

    public static final int SINOINTEGER = 0;
    public static final int SINOSTRING = 1;
    public static final int SINODATE = 2;
    public static final int SINOBOOL = 3;
    public static final int SINOLIST = 4;
    public static final int SINOVECTOR = 5;
    public static final int SINONAME = 6;
    protected String name;
    protected int type;
    protected String sValue;
    protected int nValue;
    protected Date dValue;
    protected boolean bValue;
    protected String listValue[];
    protected Vector vValue;

    public Object clone()
    {
        try
        {
            SinoDocField sinofield = (SinoDocField)super.clone();
            sinofield.name = name;
            sinofield.type = type;
            sinofield.sValue = sValue;
            sinofield.nValue = nValue;
            if(dValue != null)
               // sinofield.dValue = (Date)dValue.clone();
               sinofield.dValue = dValue;
                
            sinofield.bValue = bValue;
            if(listValue != null)
                sinofield.listValue = listValue;
            if(vValue != null)
                sinofield.vValue = (Vector)vValue.clone();
            return sinofield;
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError("Unexpected CloneNotSUpportedException: " + clonenotsupportedexception.getMessage());
        }
    }

    public SinoDocField(String s, int i, String s1)
    {
        name = s;
        type = i;
        sValue = s1;
    }

    public SinoDocField(String s, int i, int j)
    {
        name = s;
        type = i;
        nValue = j;
    }

    public SinoDocField(String s, int i, Date date)
    {
        name = s;
        type = i;
        dValue = date;
    }

    public SinoDocField(String s, int i, boolean flag)
    {
        name = s;
        type = i;
        bValue = flag;
    }

    public SinoDocField(String s, int i, String as[])
    {
        name = s;
        type = i;
        listValue = as;
    }

    public SinoDocField(String s, int i, Vector vector)
    {
        name = s;
        type = i;
        vValue = vector;
    }

    public SinoDocField(String s, String s1)
    {
        name = s;
        type = 1;
        sValue = s1;
    }

    public SinoDocField(String s, int i)
    {
        name = s;
        type = 0;
        nValue = i;
    }

    public SinoDocField(String s, Date date)
    {
        name = s;
        type = 2;
        dValue = date;
    }

    public SinoDocField(String s, boolean flag)
    {
        name = s;
        type = 3;
        bValue = flag;
    }

    public SinoDocField(String s, String as[])
    {
        name = s;
        type = 4;
        listValue = as;
    }

    public SinoDocField(String s, Vector vector)
    {
        name = s;
        type = 4;
        vValue = vector;
    }

    public String getFieldName()
    {
        return name;
    }

    public int getFieldType()
    {
        return type;
    }

    public String getFieldValue()
    {
        return sValue;
    }

    public String getFieldStrValue()
    {
        return sValue;
    }

    public int getFieldIntValue()
    {
        return nValue;
    }

    public boolean getFieldBoolValue()
    {
        return bValue;
    }

    public Date getFieldDateValue()
    {
        return dValue;
    }

    public String[] getFieldListValue()
    {
        return listValue;
    }

    public Vector getFieldVectorValue()
    {
        return vValue;
    }

    public String getText()
    {
        String s = "";
        switch(type)
        {
        case 1: // '\001'
            s = sValue;
            break;

        case 0: // '\0'
            Integer integer = new Integer(nValue);
            s = integer.toString();
            break;

        case 2: // '\002'
            s = dValue.toString();
            break;

        case 3: // '\003'
            if(bValue)
                s = "1";
            else
                s = "0";
            break;

        case 4: // '\004'
            for(int i = 0; i < listValue.length; i++)
            {
                if(i > 0)
                    s = s + ";";
                s = s + listValue[i];
            }

            break;

        case 5: // '\005'
            s = SinoTools.Implode(vValue, ";");
            break;

        case 6: // '\006'
            s = sValue;
            break;

        default:
            s = "";
            break;
        }
        return s;
    }
}
