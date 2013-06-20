package com.sino.flow.designer;

import java.io.InputStream;
import java.util.Vector;

public class SinoFloTask extends SinoDocBase {

    protected SinoFloPoint mpt;
    protected SinoFloLength msz;
    protected SinoDocField FIELDS[] = {
            new SinoDocField("ProcName", 1, ""), new SinoDocField("Name", 1, ""), new SinoDocField("Desc", 1, ""), new SinoDocField("DIBID", 0, 0), new SinoDocField("ptAtX", 0, 0), new SinoDocField("ptAtY", 0, 0), new SinoDocField("TaskID", 0, 0), new SinoDocField("ForwardType", 0, 0), new SinoDocField("TaskGroup", 1, ""), new SinoDocField("TaskRole", 1, ""),
            new SinoDocField("TaskDuration", 0, 999), new SinoDocField("LeadDayMode", 0, 0), new SinoDocField("LaunchProg", 1, ""), new SinoDocField("CaseHandleMode", 0, 0), new SinoDocField("CycleMode", 0, 0), new SinoDocField("CommentMode", 0, 0), new SinoDocField("CommentGroup", 1, ""), new SinoDocField("CommentRole", 1, ""), new SinoDocField("SectionValue", 1, ""), new SinoDocField("HiddenValue", 1, ""),
            new SinoDocField("CommentSectionValue", 1, ""), new SinoDocField("CommentHiddenValue", 1, ""), new SinoDocField("CommentApplmsg", 1, ""), new SinoDocField("CycleType", 0, 0), new SinoDocField("JoinConfig", 0, 0), new SinoDocField("Attribute1", 1, ""), new SinoDocField("Attribute2", 1, ""), new SinoDocField("Attribute3", 1, ""), new SinoDocField("Attribute4", 1, ""), new SinoDocField("Attribute5", 1, "")
    };

    public SinoFloTask() {
        fields = FIELDS;
        mpt = new SinoFloPoint();
        msz = new SinoFloLength();
    }

    public Vector getTaskGroups() {
        return SinoTools.Explode(getStringValue("TaskGroup"), ";");
    }

    public String getTaskGroupString() {
        return getStringValue("TaskGroup");
    }

    public Vector getTaskRoles() {
        return SinoTools.Explode(getStringValue("TaskRole"), ";");
    }

    public String getTaskRoleString() {
        return getStringValue("TaskRole");
    }

    public boolean readFrom(InputStream inputstream) {
        if (!super.readFrom(inputstream)) {
            return false;
        } else {
            mpt.readFrom(inputstream);
            msz.readFrom(inputstream);
            return true;
        }
    }


}
