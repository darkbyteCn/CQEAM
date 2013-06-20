package com.sino.flow.designer;

import java.io.InputStream;

public class SinoFloText extends SinoDocBase
{

    protected SinoFloPoint mpt;
    protected SinoDocField FIELDS[] = {
        new SinoDocField("Label", 1, ""), new SinoDocField("ptAtX", 0, 0), new SinoDocField("ptAtY", 0, 0), new SinoDocField("LabelOffsetX", 0, 0), new SinoDocField("LabelOffsetY", 0, 0), new SinoDocField("lfHeight", 0, 0), new SinoDocField("lfWidth", 0, 0), new SinoDocField("lfEscapement", 0, 0), new SinoDocField("lfOrientation", 0, 0), new SinoDocField("lfWeight", 0, 3),
        new SinoDocField("lfItalic", 0, 3), new SinoDocField("lfUnderline", 0, 0), new SinoDocField("lfStrikeOut", 0, 0), new SinoDocField("lfCharSet", 0, 0), new SinoDocField("lfOutPrecision", 0, 0), new SinoDocField("lfClipPrecision", 0, 0), new SinoDocField("lfQuality", 0, 3), new SinoDocField("TextColor", 0, 3), new SinoDocField("lfFaceName", 1, "")
    };

    public SinoFloText()
    {
        fields = FIELDS;
        mpt = new SinoFloPoint();
    }



    public boolean readFrom(InputStream inputstream)
    {
        if(!super.readFrom(inputstream))
            return false;
        return mpt.readFrom(inputstream);
    }


}
