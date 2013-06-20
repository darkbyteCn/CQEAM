<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/grid.css" />
<div class="dataLinesOfHead" id="dataLinesOfHead" >
	<table id="dataHead">
		<colgroup>
			<col width="8%"></col>
			<col width="10%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
		</colgroup>
		<thead>
			<tr>
				<th>
					物料编码
				</th>
				<th>
					物料描述
				</th>
				<th>
					订单数量
				</th>
				<th>
					累计计划备货数量
				</th>
				<th>
					已有效备货数量
				</th>
				<th>
					已接收数量
				</th>
				<th>
					本次计划备货数量
				</th>
				<th>
					可用计划备货数量
				</th>
			</tr>
		</thead>
	</table>
</div>

<div class="dataLines"
	onscroll="document.getElementById('dataLinesOfHead').scrollLeft =  this.scrollLeft;" style="height:350px;">
	<table id="dataBody" >
		<colgroup>
			<col width="8%"></col>
			<col width="10%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
		</colgroup>
		<tbody>
			<%
				for (int i = 0; i < 30; i++) {
			%>
			<tr>
				<td>
					160401010000002
				</td>
				<td>
					铜芯阻燃聚氯乙烯绝缘软电缆（ZA-RV）线径(MM2)
				</td>
				<td>
					200
				</td>
				<td>
					20
				</td>
				<td>
					2
				</td>
				<td>
					120
				</td>
				<td>
					20
				</td>
				<td>
					10
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</div>