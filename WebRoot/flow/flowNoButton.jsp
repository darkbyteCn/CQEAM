<%@ page import="com.sino.base.db.conn.DBManager" %>
<%@ page import="com.sino.base.log.Logger" %>
<%@ page import="com.sino.base.util.String2DTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.sinoflow.constant.ButtonMask" %>
<%@ page import="com.sino.sinoflow.dto.SfActInfoDTO" %>
<%@ page import="com.sino.sinoflow.dto.SfApplicationDTO" %>
<%@ page import="com.sino.sinoflow.user.dto.SfUserBaseDTO" %>
<%@ page import="com.sino.sinoflow.utilities.*" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.List" %>
<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/json.js"></script>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/api.js"></script>
<script type="text/javascript" src="/WebLibary/js/util2.js"></script>

<%
//            PrintWriter pw = response.getWriter();
//            SfActInfoDTO info = (SfActInfoDTO)request.getAttribute("ACT_INFO");
//            SfApplicationDTO appl = (SfApplicationDTO)request.getAttribute("APPLICATION");
//            int status = Integer.parseInt((String)request.getAttribute("FLAG"));
//           String fstr = request.getParameter(com.sino.sinoflow.constant.WebAttrConstant.SINOFLOW_WEB_OBJECT);
           String fstr = (String)request.getAttribute(com.sino.sinoflow.constant.WebAttrConstant.SINOFLOW_WEB_OBJECT);
           if(fstr == null || fstr.equals(""))
               fstr = (String)request.getParameter(com.sino.sinoflow.constant.WebAttrConstant.SINOFLOW_WEB_OBJECT);
           SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(request);
    
//            Connection conn = (Connection)request.getAttribute("CONNECTION");
            Connection conn = null;
            List funList = null;
            int bMask = 0, cMask = 0;
            String lock = "0";
            String fillValue = "";
            String autoValue = "";
            String validation = "";
            int status = 0;
            SfActInfoDTO info = null;
            String sfCaseId = null;
            String sfApp_Name = null;
            SfApplicationDTO appl = null;
            JSONObject f = null;
            try {
                f = new JSONObject(fstr);

//                info = (SfActInfoDTO)String2DTO.getDTO(f.get("act").toString());
                CaseRoutine cs = new CaseRoutine();
                String actId = f.get("actId").toString();
                int appId = Integer.parseInt(f.get("appId").toString());
                info = cs.getAct(actId, request, appId);
                sfCaseId = info.getSfactCaseId();

//                SfApplicationDTO appl = (SfApplicationDTO)String2DTO.getDTO(f.get("app").toString());
                appl = cs.getApp(appId);
                sfApp_Name = appl.getAppName();
                status = Integer.parseInt(f.get("flag").toString());

                conn = DBManager.getDBConnection();
//                Api.outApi(pw, info, conn);
                funList =  Api.getApiFunctions(info.getSfactTaskId(), conn);
//                FillValue.outFieldValue(pw, info, conn);
                fillValue = FillValue.getFieldValue(info, conn);
//                AutoValue.outFieldValue(pw, info.getSfactProcId(), info.getSfactTaskId(), conn);
                autoValue = AutoValue.getFieldValue(info.getSfactTaskId(), conn);
//                Validation.outValidation(pw, info.getSfactProcId(), info.getSfactTaskId(), conn);
                validation = Validation.getValidation(info.getSfactTaskId(), conn);

                int appMask = appl.getAllowOperation();
                if(appl.getFinishMessage() == 1)
                    appMask |= ButtonMask.FINISHMESSAGE_MASK;
                if(appl.getConfirmFinish() == 1)
                    appMask |= ButtonMask.CONFIRMMESSAGE_MASK;
                if((appMask & ButtonMask.CANCEL_MASK) != ButtonMask.CANCEL_MASK) {
                    bMask |= ButtonMask.CANCEL_MASK;
                }
                if((appMask & ButtonMask.SPECIALSEND_MASK) != ButtonMask.SPECIALSEND_MASK) {
                    bMask |= ButtonMask.SPECIALSEND_MASK;
                }
                if((appMask & ButtonMask.SENDBACK_MASK) != ButtonMask.SENDBACK_MASK) {
                    bMask |= ButtonMask.SENDBACK_MASK;
                }
                 if((appMask & ButtonMask.VIEWPROCESS_MASK) != ButtonMask.VIEWPROCESS_MASK) {
                    bMask |= ButtonMask.VIEWPROCESS_MASK;
                }
                if((appMask & ButtonMask.SENDTO_MASK) != ButtonMask.SENDTO_MASK) {
                    bMask |= ButtonMask.SENDTO_MASK;
                }
                if(appl.getFinishMessage() == 0) {
                    bMask |= ButtonMask.FINISHMESSAGE_MASK;
                }
                switch(status) {
                    case 0:
                        bMask |= ButtonMask.SIGN_MASK;
                        break;
                    case 1:
                        bMask |= ButtonMask.SAVE_MASK;
                        bMask |= ButtonMask.COMPLETE_MASK;
                        bMask |= ButtonMask.SPECIALSEND_MASK;
                        bMask |= ButtonMask.SENDBACK_MASK;
                        bMask |= ButtonMask.SENDTO_MASK;
                    break;
                    case 2:
                        bMask |= ButtonMask.SIGN_MASK;
                        bMask |= ButtonMask.SAVE_MASK;
                        bMask |= ButtonMask.COMPLETE_MASK;
                        if((appMask & ButtonMask.SPECIALSEND_MASK) != ButtonMask.SPECIALSEND_MASK) {
                            bMask |= ButtonMask.SPECIALSEND_MASK;
                        }
                        if((appMask & ButtonMask.SENDBACK_MASK) != ButtonMask.SENDBACK_MASK) {
                            bMask |= ButtonMask.SENDBACK_MASK;
                        }
                        if((appMask & ButtonMask.SENDTO_MASK) != ButtonMask.SENDTO_MASK) {
                            bMask |= ButtonMask.SENDTO_MASK;
                        }
                        break;
                }
                if(info.getSfactTaskCtl() != 3) {
                    bMask |= ButtonMask.REVIEW_MASK;
                    bMask |= ButtonMask.REVIEW_STATUS_MASK;
                } else {
                    if(info.getSfactCommentQty() == 0) {
                        bMask |= ButtonMask.REVIEW_MASK;
                        bMask |= ButtonMask.REVIEW_STATUS_MASK;
                    } else {
                        bMask |= ButtonMask.REVIEW_MASK;
                    }
                    if(info.getSfactCommentType() > 0) {
                        bMask |= ButtonMask.SENDBACK_MASK;
                        bMask |= ButtonMask.SPECIALSEND_MASK;
                    }
                }

                cMask = appMask;

                if(status == 2)
                    lock = "1";
                else
                    lock = "0";
            } catch (Exception ex) {
                Logger.logError(ex);
            } finally {
                DBManager.closeDBConnection(conn);
            }
%>
<script type="text/javascript">

function SFQueryOpen () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(0)%>
}
function SFPostOpen () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(1)%>
}
function SFQuerySign () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(2)%>
}
function SFPostSign () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(3)%>
}
function SFQueryComplete () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(4)%>
}
function SFGroupReview () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(5)%>
}
function SFQueryCycleReview () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(6)%>
}
function SFQueryConditionalFlow () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(7)%>
}
function SFQueryGroup () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(8)%>
}
function SFParallelFlow () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(9)%>
}
function SFQueryAssistFlow () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(10)%>
}
function SFQueryDistribute () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(11)%>
}
function SFQueryGoBack () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(12)%>
}
function SFQuerySave () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(13)%>
}
function SFPostSave () {
Error_Msg = "";
Launch_Continue = true;
<%=funList.get(14)%>
}

</script>
<input type="hidden" name="sf_fillApiData" value="<%=fillValue%>" readonly="readonly" />
<input type="hidden" name="sf_autoValue" value="<%=autoValue%>" readonly="readonly" />
<input type="hidden" name="sf_validation" value="<%=validation%>" readonly="readonly" />
<script type="text/javascript">buttonMask = <%=bMask%>;function scroll_init(){};function scroll_movemenulayer(){};</script>
<script type="text/javascript">completeMask = <%=cMask%>;</script>

<input type="hidden" name="sf_user" value="<%=user.getUsername()%>" readonly="readonly" />
<input type="hidden" name="sf_loginName" value="<%=user.getLoginName()%>" readonly="readonly" />
<input type="hidden" name="sf_lock" value="<%=lock%>" readonly="readonly" />
<%
    if((info.getSfactCommentQty() <= 0) ||
            (info.getSfactCommentType() & 0x20) == 0) {
%>
<input type="hidden" name="sf_divRight" value="<%=info.getSfactTaskDivRight()%>" readonly="readonly" />
<input type="hidden" name="sf_divHidden" value="<%=info.getSfactTaskHidden()%>" readonly="readonly" />
<%
    } else {
%>
<input type="hidden" name="sf_divRight" value="<%=info.getSfactTaskCommentDiv()%>" readonly="readonly" />
<input type="hidden" name="sf_divHidden" value="<%=info.getSfactTaskCommentHide()%>" readonly="readonly" />
<%
    }
%>

