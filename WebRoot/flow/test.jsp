<%@ page contentType="text/html; charset=GBK" language="java"%>

<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
 
<c:set var="num" value="1" scope="page"></c:set>
<%
	String[][] str = new String[][]{
									new String[]{"主分类：","关键字域名："},
									new String[]{"次分类：","主题域名："},
									new String[]{"其它分类：","其它域名："}};
    pageContext.setAttribute("str",str);
	
	String[][] str2 = new String[][]{
									new String[]{"域名1：","域名4："},
									new String[]{"域名2：","域名5"},
									new String[]{"域名3：","域名6："}};
	pageContext.setAttribute("str2",str2);
	
%>

<html>
	<head>
	</head>
	
	<body>
		<form name="form1" method="post" action="">
 			<div id="appType">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td width="40%">*应用类型：</td>
						<td width="60%">
							<input type="radio" name="radiobutton" value="radiobutton">流程任务
							<input type="radio" name="radiobutton" value="radiobutton">非流程任务
						</td>
					</tr>
					
					<tr>
						<td>*工程名称：</td>
						<td>
							<select name="select"></select>
						</td>
					</tr>
					
					<tr>
						<td>*可使用者组别：</td>
						<td>
							<select name="select2"></select>
						</td>
					</tr>
					
					<tr>
						<td>*可使用者角色：</td>
						<td>
							<select name="select3"></select>
						</td>
					</tr>
					
					<tr>
						<td>*过程名称：</td>
						<td>
							<input type="text" name="textfield">
						</td>
					</tr>
					
					<tr>
						<td>*应用分类名称</td>
						<td>
							<input type="text" name="textfield2">
						</td>
					</tr>
					
					<tr>
						<td>*应用显示窗口类型：</td>
						<td>
							<input type="radio" name="radiobutton" value="radiobutton">右框架中
							<input type="radio" name="radiobutton" value="radiobutton">新窗口
						</td>
					</tr>
				</table>
			</div>
		
			<div id="flowDefine">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td width="40%">*应用名称：</td>
						<td width="60%">
							<input type="text"/>
						</td>
					</tr>
					
						<tr>
						<td>流程是否应用：</td>
						<td>
							<input type="radio" name="radiobutton" value="radiobutton">是
							<input type="radio" name="radiobutton" value="radiobutton">否
						</td>
					</tr>
					
					<tr>
						<td>确认信息框：</td>
						<td>
							<input type="radio" name="radiobutton" value="radiobutton">禁止
							<input type="radio" name="radiobutton" value="radiobutton">允许
						</td>
					</tr>
					
					<tr>
						<td>完成时传送信息:</td>
						<td>
							<input type="radio" name="radiobutton" value="radiobutton">是
							<input type="radio" name="radiobutton" value="radiobutton">否
						</td>
					</tr>
					
						<tr>
						<td>允许操作项：</td>
						<td>
							<input type="checkbox" name="radiobutton" value="radiobutton">
							<input type="checkbox" name="radiobutton" value="radiobutton">
							<input type="checkbox" name="radiobutton" value="radiobutton">
							<input type="checkbox" name="radiobutton" value="radiobutton">
							<input type="checkbox" name="radiobutton" value="radiobutton">
							<input type="checkbox" name="radiobutton" value="radiobutton">
						</td>
					</tr>
					
					<tr>
						<td>*应用数据接口类名:</td>
						<td>
							<input type="text"/>
						</td>
					</tr>
					
					<tr>
						<td>应用数据 SQLModel:</td>
						<td>
							<input type="text"/>
						</td>
					</tr>
					
					<tr>
						<td>应用URL：</td>
						<td>
							<input type="text">
						</td>
					</tr>
					
				</table>
			</div>
				
			
			<div id="jobDefine">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td colspan="2">工作栏三种分类对应表单域名</td>
						<td colspan="2">应用文档列入工作栏对应用表单域名</td>
					</tr>
					<c:forEach items="${pageScope.str}" var="kk">
                        <tr>
							<td width=""><c:out value="${kk[0]}"/></td>
							<td width="">
								<input type="text"/>
							</td>
							
							<td width=""><c:out value="${kk[1]}"/></td>
							<td width="">
								<input type="text"/>
							</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="4">应用文档插入工作栏文档应用表单域名</td>
					</tr>
					
					<c:forEach items="${pageScope.str2}" var="kk">
						<tr>
							<td width=""><c:out value="${kk[0]}"/></td>
							<td width="">
								<input type="text"/>
							</td>
							
							<td width=""><c:out value="${kk[1]}"/></td>
							<td width="">
								<input type="text"/>
							</td>
						</tr>					
					</c:forEach>
				</table>
			</div>
			
			<div id="apiDefine">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td></td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
