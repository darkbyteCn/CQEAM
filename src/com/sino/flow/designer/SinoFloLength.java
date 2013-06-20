package com.sino.flow.designer;

import java.io.InputStream;


public class SinoFloLength extends SinoDocBase
{

    protected int mcx;
    protected int mcy;

    SinoFloLength()
    {
    }

    public boolean readFrom(InputStream inputstream)
    {
        SinoTools.readAInteger(inputstream);
        mcx = SinoTools.readAInteger(inputstream);
        mcy = SinoTools.readAInteger(inputstream);
        return true;
    }


}
