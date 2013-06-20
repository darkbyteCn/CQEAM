package com.sino.flow.designer;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SinoFloProject extends SinoDocBase
{

    SinoFloProcedures procs;
    SinoFloGroups groups;
    SinoFloRoles roles;
    SinoFloUsers users;
    protected SinoDocField FIELDS[] = {
        new SinoDocField("Name", 1, ""), new SinoDocField("Desc", 1, ""), new SinoDocField("Admin", 1, ""), new SinoDocField("CreateUser", 1, ""), new SinoDocField("CreateDate", 1, ""), new SinoDocField("ModifyUser", 1, ""), new SinoDocField("ModifyDate", 1, ""), new SinoDocField("EffectiveDate", 1, "")
    };


    public SinoFloProject()
    {
        fields = FIELDS;
        procs = new SinoFloProcedures();
        groups = new SinoFloGroups();
        roles = new SinoFloRoles();
        users = new SinoFloUsers();
    }

    public int getProcedureCount()
    {
        return procs.getCount();
    }

    public int getGroupCount()
    {
        return groups.getCount();
    }

    public int getRoleCount()
    {
        return roles.getCount();
    }

    public SinoFloProcedure getFirstProcedure()
    {
        return (SinoFloProcedure)procs.getFirstItem();
    }

    public SinoFloProcedure getNextProcedure()
    {
        return (SinoFloProcedure)procs.getNextItem();
    }

    public SinoFloProcedure getNthProcedure(int i)
    {
        return (SinoFloProcedure)procs.getNthItem(i);
    }

    public SinoFloProcedure getProcByName(String s)
    {
        return (SinoFloProcedure)procs.getItemByName(s);
    }

    public SinoFloGroup getFirstGroup()
    {
        return (SinoFloGroup)groups.getFirstItem();
    }

    public SinoFloGroup getNextGroup()
    {
        return (SinoFloGroup)groups.getNextItem();
    }

    public SinoFloGroup getNthGroup(int i)
    {
        return (SinoFloGroup)groups.getNthItem(i);
    }

    public SinoFloRole getFirstRole()
    {
        return (SinoFloRole)roles.getFirstItem();
    }

    public SinoFloRole getNextRole()
    {
        return (SinoFloRole)roles.getNextItem();
    }

    public SinoFloRole getNthRole(int i)
    {
        return (SinoFloRole)roles.getNthItem(i);
    }

    public boolean readFrom(InputStream inputstream)
    {
    	//read head information
        try
        {
            byte abyte0[] = new byte[48];
            if(inputstream.read(abyte0) < 48)
                return false;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        if(!super.readFrom(inputstream))
            return false;
        
        //groups
//        com.sinoflow.debugBean.logDebugMsg("read groups from files");
        SinoTools.readAInteger(inputstream);
        
        if(!groups.readFrom(inputstream))
            return false;
        //roles
//        com.sinoflow.debugBean.logDebugMsg("read roles from files");
        SinoTools.readAInteger(inputstream);
        if(!roles.readFrom(inputstream))
            return false;
            
        //users
//        com.sinoflow.debugBean.logDebugMsg("read users from files");
        SinoTools.readAInteger(inputstream);
        if(!users.readFrom(inputstream))
            return false;
//        com.sinoflow.debugBean.logDebugMsg("read procs from files");
        return procs.readFrom(inputstream);
    }

 
    public boolean loadFromXmlDomDoc(Document document)
    {
        if(document == null)
            return false;
        try
        {
            Element element = document.getDocumentElement();
            NodeList nodelist = element.getElementsByTagName("sinoProject");
            if(nodelist == null)
                return false;
            nodelist = nodelist.item(0).getChildNodes();
            for(int i = 0; i < nodelist.getLength(); i++)
            {
                Node node = nodelist.item(i);
                if(node.getNodeType() == 1)
                {
                    NodeList nodelist1 = node.getChildNodes();
                    if(node.getNodeName().equalsIgnoreCase("Properties") && !super.loadFromDomNodeList(nodelist1))
                        return false;
                    if(node.getNodeName().equalsIgnoreCase("Procedures") && !procs.loadFromDomNodeList(nodelist1))
                        return false;
                    if(node.getNodeName().equalsIgnoreCase("Roles") && !roles.loadFromDomNodeList(nodelist1))
                        return false;
                    if(node.getNodeName().equalsIgnoreCase("Groups") && !groups.loadFromDomNodeList(nodelist1))
                        return false;
                }
            }

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    

    public void dump()
    {
        groups.dump();
        roles.dump();
        procs.dump();
    }
}
