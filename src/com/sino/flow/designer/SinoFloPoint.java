
package com.sino.flow.designer;

import java.io.InputStream;

public class SinoFloPoint extends SinoDocBase
{

    protected int mx;
    protected int my;

    SinoFloPoint()
    {
    }


    public boolean readFrom(InputStream inputstream)
    {
        SinoTools.readAInteger(inputstream);
        mx = SinoTools.readAInteger(inputstream);
        my = SinoTools.readAInteger(inputstream);
        return true;
    }


}
