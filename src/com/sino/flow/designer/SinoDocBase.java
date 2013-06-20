package com.sino.flow.designer;

import java.io.InputStream;
import java.util.Date;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class SinoDocBase
    implements Cloneable
{ 

    protected SinoDocField fields[];
    protected String key;
    protected String FormName;
    protected boolean isNew;

    public SinoDocBase()
    {
        isNew = true;
    }

    public Object clone()
    {
        try
        {
            SinoDocBase sinoitem = (SinoDocBase)super.clone();
            if(fields != null && fields.length > 0)
            {
                sinoitem.fields = new SinoDocField[fields.length];
                for(int i = 0; i < fields.length; i++)
                    sinoitem.fields[i] = (SinoDocField)fields[(i)].clone();

            }
            sinoitem.key = key;
            sinoitem.FormName = FormName;
            sinoitem.isNew = isNew;
            return sinoitem;
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError("Unexpected CloneNotSUpportedException: " + clonenotsupportedexception.getMessage());
        }
    }
    public int getFieldType(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldType();

        return -1;
    }

    public String getStringValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldStrValue();

        return "";
    }

    public String getNameValue(String s)
    {
        return getStringValue(s);
    }

    public int getIntValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldIntValue();

        return 0;
    }

    public boolean getBoolValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldBoolValue();

        return false;
    }

    public Date getDateValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldDateValue();

        return null;
    }

    public String[] getListValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldListValue();

        return null;
    }

    public Vector getVectorValue(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getFieldVectorValue();

        return null;
    }

    public String getValue(String s)
    {
        return getText(s);
    }

    public String getText(String s)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(s))
                return fields[i].getText();

        return "";
    }

    public SinoDocField replaceFieldValue(SinoDocField sinofield)
    {
        for(int i = 0; i < fields.length; i++)
            if(fields[i].getFieldName().equalsIgnoreCase(sinofield.getFieldName()))
            {
                fields[i] = sinofield;
                return fields[i];
            }

        return null;
    }

    public SinoDocField replaceFieldValue(String s, String s1)
    {
        SinoDocField sinofield = new SinoDocField(s, s1);
        return replaceFieldValue(sinofield);
    }

    public SinoDocField replaceFieldValue(String s, int i)
    {
        SinoDocField sinofield = new SinoDocField(s, i);
        return replaceFieldValue(sinofield);
    }

    public boolean readFrom(InputStream inputstream)
    {
    	
        int i = SinoTools.readAInteger(inputstream);
        if(i < 0)
            return false;
        
        
        while(i-- > 0) 
        {
            String s = SinoTools.readAString(inputstream);
            
            
            if(s == null)
                return false;
            int j =SinoTools.readAInteger(inputstream);
            switch(j)
            {
            case 0: // '\0'
                int k = SinoTools.readAInteger(inputstream);
                replaceFieldValue(s, k);
                break;

            case 1: // '\001'
                String s1 = SinoTools.readAString(inputstream);
                if(s1 == null)
                    s1 = "";
                replaceFieldValue(s, s1);
                break;

            default:
                return false;
            }
        }
        return true;
    }

 

    public boolean loadFromDomNodeList(NodeList nodelist)
    {
        boolean flag = false;
        if(nodelist == null)
            return false;
        try
        {
            for(int j = 0; j < nodelist.getLength(); j++)
            {
                Node node = nodelist.item(j);
                if(node.getNodeType() == 1)
                {
                    String s = node.getNodeName();
                    String s1 = SinoTools.getDomItemText(node);
                    int i = getFieldType(s);
                    switch(i)
                    {
                    default:
                        break;

                    case 0: // '\0'
                        if(SinoTools.VAL(s1))
                            replaceFieldValue(s, Integer.parseInt(s1));
                        else
                            replaceFieldValue(s, 0);
                        break;

                    case 1: // '\001'
                        if(!SinoTools.VAL(s1))
                            s1 = "";
                        replaceFieldValue(s, s1);
                        break;
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
            
        }
        return flag;
    }

    public void dump()
    {
        for(int i = 0; i < fields.length; i++)
           DebugBean.log(fields[i].getFieldName() + ": " + fields[i].getText());

    }
}
