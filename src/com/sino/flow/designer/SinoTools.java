package com.sino.flow.designer;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SinoTools
{

    private static ResourceBundle m_resBundle = null;
    public static String MOD_sino = "/servlet/SinoFlow";
    public static final String SCRIPT_MATE = "/servlet/SinoFlow";
    public static String SCRIPT_NOW = "/servlet/SinoFlow";
    public static final String _GB = "gb2312";
    public static final String _ISO = "iso-8859-1";
    public static final String _UTF8 = "UTF8";

    public SinoTools()
    {
    }

    String ConvertToUnicode(String s)
    {
        try
        {
            byte abyte0[] = s.getBytes("ISO-8859-1");
            String s1 = new String(abyte0, "EUC_CN");
            return s1;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }
        return null;
    }

    public static synchronized String getText(String s, String s1)
    {
        String s2 = null;
        if(m_resBundle != null)
            try
            {
                s2 = m_resBundle.getString(s);
                if(s2 != null)
                {
                    byte abyte0[] = s2.getBytes("ISO-8859-1");
                    s2 = new String(abyte0);
                }
            }
            catch(Exception exception)
            {
                System.out.println(exception.toString());
            }
        if(s2 == null)
            return s1;
        else
            return s2;
    }

    public static synchronized String getText(int i, String s)
    {
        return getText(Integer.toString(i), s);
    }

    public static synchronized String getText(String s)
    {
        String s1 = null;
        if(m_resBundle != null)
            try
            {
                s1 = m_resBundle.getString(s);
                if(s1 != null)
                {
                    byte abyte0[] = s1.getBytes("ISO-8859-1");
                    s1 = new String(abyte0);
                }
            }
            catch(Exception exception)
            {
                DebugBean.logError(exception.toString());
            }
        if(s1 == null)
            return s;
        else
            return s1;
    }

    public static String getTextValue(String s)
    {
        String s1 = null;
        if(m_resBundle != null)
            try
            {
                s1 = m_resBundle.getString(s);
                if(s1 != null)
                {
                    byte abyte0[] = s1.getBytes("ISO-8859-1");
                    s1 = new String(abyte0);
                }
            }
            catch(Exception exception)
            {
                DebugBean.logError(exception.toString());
            }
        return s1;
    }


    public static int Range(int i, int j, int k)
    {
        if(i < j)
            return j;
        if(i > k)
            return k;
        else
            return i;
    }

    public static Vector Explode(String s, String s1)
    {
        Vector vector = new Vector();
        for(String s2 = s; s2 != null && s2 != ""; s2 = "")
        {
            for(int i = 0; i <= s2.length() - s1.length(); i++)
                if(s1.compareTo(s2.substring(i, i + s1.length())) == 0)
                {
                    String s3 = s2.substring(0, i);
                    s3.trim();
                    if(s3 == null || s3.compareTo("") == 0)
                        s3 = " ";
                    vector.addElement(s3);
                    s2 = s2.substring(i + s1.length(), s2.length());
                    i = -1;
                }

            s2.trim();
            if(s2 != null && s2.compareTo("") != 0)
                vector.addElement(s2);
        }

        return vector;
    }

    public static Vector Explode(StringBuffer stringbuffer, String s)
    {
        return Explode(stringbuffer.toString(), s);
    }

    public static String Implode(Vector vector, String s)
    {
        String s1 = "";
        int i = vector.size();
        for(int j = 0; j < i; j++)
            if(s1.length() == 0)
                s1 = vector.elementAt(j).toString();
            else
                s1 = s1 + s + vector.elementAt(j).toString();

        return s1;
    }

    public static boolean IsInList(Vector vector, String s)
    {
        int i = vector.size();
        for(int j = 0; j < i; j++)
            if(vector.elementAt(j).toString().equalsIgnoreCase(s))
                return true;

        return false;
    }

    public static boolean HasJoin(Vector vector, Vector vector1)
    {
        for(int i = 0; i < vector.size(); i++)
        {
            for(int j = 0; j < vector1.size(); j++)
                if(vector.elementAt(i).equals(vector1.elementAt(j)))
                    return true;

        }

        return false;
    }

    public static String GetValueString(Vector vector, String s)
    {
        int i = vector.size() / 2;
        for(int j = 0; j < i; j++)
            if(vector.elementAt(2 * j).toString().equalsIgnoreCase(s))
                return vector.elementAt(2 * j + 1).toString().trim();

        return "";
    }

    public static Long GetValueLong(Vector vector, String s)
    {
        try
        {
            int i = vector.size() / 2;
            for(int j = 0; j < i; j++)
                if(vector.elementAt(2 * j).toString().equalsIgnoreCase(s))
                    return Long.valueOf(vector.elementAt(2 * j + 1).toString());

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return new Long(0L);
    }

    public static boolean VAL(String s)
    {
        if(s == null)
            return false;
        return !s.equalsIgnoreCase("") && !s.equalsIgnoreCase("(null)");
    }

    public static boolean VAL(Object obj)
    {
        return obj != null;
    }


    public static String implode(Vector vector, String s)
    {
        String s1 = "";
        for(int i = 0; i < vector.size(); i++)
        {
            if(s1.length() > 0)
                s1 = s1 + s;
            s1 = s1 + (String)vector.elementAt(i);
        }

        return s1;
    }

    public static boolean isMember(Vector vector, String s)
    {
        for(int i = 0; i < vector.size(); i++)
            if(((String)vector.elementAt(i)).equalsIgnoreCase(s))
                return true;

        return false;
    }

    public static Vector getUnique(Vector vector)
    {
        Vector vector1 = new Vector();
        for(int i = 0; i < vector.size(); i++)
            if(!isMember(vector1, (String)vector.elementAt(i)))
                vector1.addElement(vector.elementAt(i));

        return vector1;
    }



    public static String encodeParam(String s)
    {
        StringCharacterIterator stringcharacteriterator = new StringCharacterIterator(s);
        StringBuffer stringbuffer = new StringBuffer();
        for(char c = stringcharacteriterator.first(); c != '\uFFFF'; c = stringcharacteriterator.next())
            if(c == ' ')
                stringbuffer.append("%20");
            else
            if(c == '\'')
                stringbuffer.append("%27");
            else
            if(c == '&')
                stringbuffer.append("%26");
            else
            if(c == '#')
                stringbuffer.append('*');
            else
                stringbuffer.append(c);

        return stringbuffer.toString();
    }

    public static String parseName(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.setLength(0);
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
label0:
            switch(c)
            {
            case 43: // '+'
                stringbuffer.append(' ');
                break;

            case 37: // '%'
                int j;
                try
                {
                    j = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                }
                catch(NumberFormatException numberformatexception)
                {
                    throw new IllegalArgumentException();
                }
                i += 2;
                switch(j >> 4)
                {
                case 8: // '\b'
                case 9: // '\t'
                case 10: // '\n'
                case 11: // '\013'
                default:
                    break label0;

                case 0: // '\0'
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                case 7: // '\007'
                    stringbuffer.append((char)j);
                    break label0;

                case 12: // '\f'
                case 13: // '\r'
                    i++;
                    int k;
                    try
                    {
                        k = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                    }
                    catch(NumberFormatException numberformatexception1)
                    {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    stringbuffer.append((char)((j & 0x1f) << 6 | k & 0x3f));
                    break label0;

                case 14: // '\016'
                    i++;
                    break;
                }
                int l;
                try
                {
                    l = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                }
                catch(NumberFormatException numberformatexception2)
                {
                    throw new IllegalArgumentException();
                }
                i += 3;
                int i1;
                try
                {
                    i1 = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                }
                catch(NumberFormatException numberformatexception3)
                {
                    throw new IllegalArgumentException();
                }
                i += 2;
                stringbuffer.append((char)((j & 0xf) << 12 | (l & 0x3f) << 6 | i1 & 0x3f));
                break;

            default:
                stringbuffer.append(c);
                break;
            }
        }

        return stringbuffer.toString();
    }



    public static String replace(String s, String s1, String s2)
    {
        String s3 = s;
        int i = 100;
        for(int j = s3.indexOf(s1); j != -1 && i-- > 0; j = s3.indexOf(s1))
            s3 = s3.substring(0, j) + s2 + s3.substring(j + s1.length());

        return s3;
    }

    public static Date createDate(String s)
    {
        Date date = null;
        DateFormat dateformat = DateFormat.getDateInstance();
        try
        {
            date = dateformat.parse(s);
        }
        catch(ParseException parseexception)
        {
            DebugBean.logError("Unable to parse " + s);
        }
        return date;
    }

    public static Integer getStringLength(String s)
    {
        try
        {
            byte abyte0[] = s.getBytes("EUC_CN");
            return new Integer(abyte0.length);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return new Integer(0);
    }

    public static final String iso2gb(String s)
    {
        try
        {
            if(s != null)
            {
                byte abyte0[] = s.getBytes("iso-8859-1");
                return new String(abyte0, "gb2312");
            }
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    public static final String utf2gb(String s)
    {
        try
        {
            if(s != null)
            {
                byte abyte0[] = s.getBytes("UTF8");
                return new String(abyte0, "gb2312");
            }
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    public static final String gb2iso(String s)
    {
        try
        {
            if(s != null)
            {
                byte abyte0[] = s.getBytes("gb2312");
                return new String(abyte0, "iso-8859-1");
            }
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s;
    }

    public static boolean CompareWithMask(Vector vector, Vector vector1)
    {
        if(vector.size() != vector1.size())
            return false;
        int i = vector.size();
        for(int j = 0; j < i; j++)
            if(!vector1.elementAt(j).toString().equalsIgnoreCase("*") && !vector1.elementAt(j).toString().equalsIgnoreCase("***") && !vector1.elementAt(j).toString().equalsIgnoreCase(vector.elementAt(j).toString()))
                return false;

        return true;
    }

    public static boolean CompareWithMask(String s, String s1)
    {
        Vector vector = Explode(s, ".");
        Vector vector1 = Explode(s1, ".");
        return CompareWithMask(vector, vector1);
    }

    public static boolean IsInSet(Vector vector, String s)
    {
        if(s == "*****")
            return true;
        for(int i = 0; i < vector.size(); i++)
            if(CompareWithMask(vector.elementAt(i).toString(), s))
                return true;

        return false;
    }

    public static String GetParseValueString(Vector vector, String s)
    {
        int i = vector.size() / 2;
        for(int j = 0; j < i; j++)
            if(vector.elementAt(2 * j).toString().equalsIgnoreCase(s))
                return vector.elementAt(2 * j + 1).toString().trim();

        return "";
    }

    public static Long GetParseValueLong(Vector vector, String s)
    {
        try
        {
            int i = vector.size() / 2;
            for(int j = 0; j < i; j++)
                if(vector.elementAt(2 * j).toString().equalsIgnoreCase(s))
                    return Long.valueOf(vector.elementAt(2 * j + 1).toString());

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return new Long(0L);
    }

    public static int GetParseValueInt(Vector vector, String s)
    {
        return GetParseValueLong(vector, s).intValue();
    }

    public static Date GetParseValueDate(Vector vector, String s)
    {
        return null;
    }

    public static Vector GetParseValueVector(Vector vector, String s)
    {
        return Explode(GetParseValueString(vector, s), ";");
    }







    public static String DEFAULT(String s, String s1)
    {
        if(VAL(s))
            return s;
        if(VAL(s1))
            return s1;
        else
            return "";
    }

    public static int DEFAULT(int i, int j)
    {
        if(i > 0)
            return i;
        if(j > 0)
            return j;
        else
            return 0;
    }

    public static String MaskString(String s, int i)
    {
        if(VAL(s))
        {
            if(s.length() > i)
                s = s.substring(0, i) + "..";
        } else
        {
            s = " (null) ";
        }
        return s;
    }

/*
----under function just for windows system
    public static int readAInteger(InputStream inputstream)
    {
        try
        {
            int i = 0;
            byte abyte0[] = new byte[4];
            if(inputstream.read(abyte0) < 4)
                return -1;
            for(int k = 0; k < 4; k++)
                if(abyte0[k] >= 0)
                {
                    byte byte0 = abyte0[k];
                    i += byte0 << k * 8;
                } else
                {
                    int j = abyte0[k] & 0x7f;
                    i += j << k * 8;
                    j = 128;
                    i += j << k * 8;
                }
            return i ^ 0xEF;//NEW SFP
            
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return -1;
    }
*/


/*
	under readAInteger for unix system
*/
    public static int readAInteger(InputStream inputstream)
    {
        try
        {
            int i = 0;
            int m=0;
            byte abyte0[] = new byte[4];
            if(inputstream.read(abyte0) < 4)
                return -1;

           /* byte tmpbyte[]=new byte[4];
            
            for( m = 0; m < 4; m++)
            {
            	tmpbyte[m]=abyte0[m];
            }
            abyte0[0]=tmpbyte[3];
            abyte0[1]=tmpbyte[2];
            abyte0[2]=tmpbyte[1];
            abyte0[3]=tmpbyte[0];
*/

            for(int k = 0; k < 4; k++)
                if(abyte0[k] >= 0)
                {
                    byte byte0 = abyte0[k];
                    i += byte0 << k * 8;
                } else
                {
                    int j = abyte0[k] & 0x7f;
                    i += j << k * 8;
                    j = 128;
                    i += j << k * 8;
                }

            return i ^ 0xEF;//NEW SFP

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return -1;
    }

    public static String readAString(InputStream inputstream)
    {

        try
        {
            int i = readAInteger(inputstream);
            if(i > 0)
            {
                byte abyte0[] = new byte[i];
                if(inputstream.read(abyte0) == i){
                	//NEW SFP
                	for (int j=0;j<i;j++){
                		abyte0[j]=(byte)(abyte0[j]^(byte)0xFF);
                	}
                	PubString pub=new PubString();
                	String oriStr=new String(abyte0, 0, i);

                	//zzj add for unix
                	String m_SEPARATOR = System.getProperty("file.separator");
					if (m_SEPARATOR.compareTo("/")==0)
					{
						PubString pub2=new PubString();
						return pub2.GB2Unicode(oriStr);
					}else{
						return oriStr;
					}


                }
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return null;
    }


    public static String getDomItemText(Node node)
    {
        String s = "";
        NodeList nodelist = node.getChildNodes();
        for(int i = 0; i < nodelist.getLength(); i++)
        {
            Node node1 = nodelist.item(i);
            switch(node1.getNodeType())
            {
            case 1: // '\001'
            case 9: // '\t'
                s = s + getDomItemText(node1);
                break;

            case 3: // '\003'
            case 4: // '\004'
                s = s + node1.getNodeValue();
                break;
            }
        }

        return s;
    }


    static
    {
        try
        {
            m_resBundle = ResourceBundle.getBundle("com.sinoflow.res.SinoFlow");
        }
        catch(Exception exception)
        {
            Object aobj[] = {
                exception.toString()
            };
            DebugBean.logError("Util::init: Exception occurred loading resources: {0}");
            m_resBundle = null;
        }
    }
}
