<%@ page pageEncoding="GBK" %>
<div id="app_$$$flowSelect$$$" style="height:450px;width:490px">
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
       <td colspan="2" height="10px"></td>
       </tr> 
        <tr>
          <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
                <td align="right" width="30%" valign="top">请选择流向&nbsp</td>
                <td width="70%">
                      <select name="flowSelect" size="4" style="width:400px" onchange="doFlowSelect()">
                      </select>
                </td>
            
          </tr>
          <tr>
              <td align="right" valign="top">办理部门&nbsp</td>
              <td>
                    <select name="groupSelect" size="5" style="width:400px" onchange="doGroupSelect()">
                    </select>
              </td>  <td></td>
           </tr>
            <tr>
                  <td align="right" valign="top">办理人&nbsp</td>
                <td>
                      <select name="participants" size="6" style="width:400px" onchange="doParticipantsSelect()">
                      </select>
                </td>
            
             </tr>
           <tr>
                <td align="right" valign="top">请填写意见&nbsp</td>
                    <td>
                        <label>
                        <textarea class="input_5" name="approveContentSS" style="height:120px;width:400px"></textarea>
                        
                    </label>
                    </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr><td colspan="3">&nbsp;</td></tr>
                <tr>
                  <td></td>
                  <td align="right" width="50%">
                    <input name="selectSubmit" class="but4" type="button" id="button" value="确定" onClick="do_selectFinish()"/>
                  </td>
                  <td align="center" width="50%"><input name="selectCancel" class="but4" type="button" id="button2" value="取消" onClick="do_selectCancel()"/></td>
                </tr>
             </table>
            </td>
           <td width="20px"></td> 
        </tr>
    </table>
</div>

