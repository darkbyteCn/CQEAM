package com.sino.flow.designer;

public class SinoFloRole extends SinoDocBase
{

    protected SinoDocField FIELDS[] = {
        new SinoDocField("ID", 1, ""),new SinoDocField("Name", 1, ""), new SinoDocField("Desc", 1, "")
    };

    public SinoFloRole()
    {
        fields = FIELDS;
    }

}
