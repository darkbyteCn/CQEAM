package com.sino.ams.appbase.dto;

import com.sino.base.util.StrUtil;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;

/**
 * Created by IntelliJ IDEA.
 * User: mshtang
 * Date: 2011-7-6
 * Time: 15:49:41
 * To change this template use File | Settings | File Templates.
 */
public class AMSFlowDTO extends AppFlowBaseDTO {

    private String sfProcedure = "";
    private String sf_caseID = "";
    private String sf_loginName = "";
    private String sf_copyID = "";
    private String sf_group = "";

    public String getSf_group() {
        return sf_group;
    }

    public void setSf_group(String sf_group) {
        this.sf_group = sf_group;
    }

    public void setSfProcedure(String sfProcedure) {
        this.sfProcedure = sfProcedure;
    }

    public String getSf_caseID() {
        return sf_caseID;
    }

    public void setSf_caseID(String sf_caseID) {
        this.sf_caseID = sf_caseID;
    }

    public String getSf_loginName() {
        return sf_loginName;
    }

    public void setSf_loginName(String sf_loginName) {
        this.sf_loginName = sf_loginName;
    }

    public String getSf_copyID() {
        return sf_copyID;
    }

    public void setSf_copyID(String sf_copyID) {
        this.sf_copyID = sf_copyID;
    }


    public String getFlowCode() {
        String flowCode = super.getFlowCode();
        if (flowCode.equals("")) {
            String[] flowValueArr = StrUtil.splitStr(getSf_appFieldValue(), ",");
            for (String field : flowValueArr) {
                if (field.indexOf("flowCode") > -1) {
                    flowCode = field.substring(field.indexOf(":") + 1);
                    flowCode = StrUtil.trim(flowCode, "'");
                    break;
                }
            }
        }
        return flowCode;
    }

    public boolean isFlowOver() {
        return super.getSf_end().equals("1");
    }

    public String getSfProcedure() {
        if (sfProcedure.equals("")) {
            String[] flowValueArr = StrUtil.splitStr(getSf_appFieldValue(), ",");
            for (String field : flowValueArr) {
                if (field.indexOf("sf_procedure") > -1) {
                    sfProcedure = field.substring(field.indexOf(":") + 1);
                    sfProcedure = StrUtil.trim(sfProcedure, "\"");
                    break;
                }
            }
        }
        return sfProcedure;
    }

    public String getProcdureName() {
        if (sfProcedure.equals("")) {
            String[] flowValueArr = StrUtil.splitStr(getSf_appFieldValue(), ",");
            for (String field : flowValueArr) {
                if (field.indexOf("sf_procedure") > -1) {
                    sfProcedure = field.substring(field.indexOf(":") + 1);
                    sfProcedure = StrUtil.trim(sfProcedure, "\"");
                    break;
                }
            }
        }
        return sfProcedure;
    }

    public String getSfOpinion() {
        String sfOpinion = super.getSfOpinion();
        if (sfOpinion.equals("")) {
            String[] flowValueArr = StrUtil.splitStr(getSf_appFieldValue(), ",");
            for (String field : flowValueArr) {
                if (field.contains("sf_opinion")) {
                    sfOpinion = field.substring(field.indexOf(":") + 1);
                    sfOpinion = StrUtil.trim(sfOpinion, "'");
                    break;
                }
            }
        }
        if(sfOpinion.equals("")){
            sfOpinion = getSf_opinion();
        }
        return sfOpinion;
    }
}

