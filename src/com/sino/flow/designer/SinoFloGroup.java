package com.sino.flow.designer;


// Referenced classes of package com.sinoflow:
//            sinoItem, sinoField, constd

public class SinoFloGroup extends SinoDocBase
{

    protected SinoDocField FIELDS[] = {
        new SinoDocField("ID", 1, ""),new SinoDocField("Name", 1, ""),
        new SinoDocField("Desc", 1, ""),new SinoDocField("ParentID", 1, "")
    };

    public SinoFloGroup()
    {
        fields = FIELDS;
    }


}
