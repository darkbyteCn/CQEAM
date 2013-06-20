package com.sino.flow.designer;

import java.io.InputStream;
import java.util.Vector;

public class SinoFloInfo extends SinoDocBase
{

    protected Vector vPoint;
    protected SinoDocField FIELDS[] = {
        new SinoDocField("ProcName", 1, ""), new SinoDocField("Name", 1, ""), new SinoDocField("Desc", 1, ""), new SinoDocField("Type", 1, ""), new SinoDocField("LineType", 0, 0), new SinoDocField("FlowID", 0, 0), new SinoDocField("LabelOffsetX", 0, 0), new SinoDocField("LabelOffsetY", 0, 0), new SinoDocField("PrevTaskID", 0, 0), new SinoDocField("NextTaskID", 0, 0),
        new SinoDocField("ApplMsg", 1, ""), new SinoDocField("SubProcName", 1, ""), new SinoDocField("SelectionCode", 1, ""), new SinoDocField("PickMode", 0, 0), new SinoDocField("DistributeMode", 0, 0), new SinoDocField("CycleMode", 0, 0), new SinoDocField("BeginPort", 0, 3), new SinoDocField("EndPort", 0, 3)
    };

    public SinoFloInfo()
    {
        fields = FIELDS;
        vPoint = new Vector();
    }


    public boolean readFrom(InputStream inputstream)
    {
        if(!super.readFrom(inputstream))
            return false;
        int i =SinoTools.readAInteger(inputstream);
        for(int j = 0; j < i; j++)
        {
            SinoFloPoint wfpoint = new SinoFloPoint();
            if(!wfpoint.readFrom(inputstream))
                return false;
            vPoint.addElement(wfpoint);
        }

        return true;
    }

	public String getNextTaskID()
	{
		return getStringValue("NextTaskID");

	}
	
	public String getPreTaskID()
	{
		return getStringValue("PrevTaskID");
	}	
}
