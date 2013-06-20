var constTitleBarBGColor="#0073BF";
var constTitleBarHeight=20;
var constTitleBarFont="style=\"font-size: 10pt\" color=#FFFFFF";

function printTitleBar(desc)
{
	document.write("<table height="+constTitleBarHeight+" width=100% bgcolor="+constTitleBarBGColor+" border=0 cellpadding=0 cellspacing=0>\n");
	document.write("<tr>\n");
	document.write("<td nowrap align=left valign=bottom ><font  "+constTitleBarFont+"><b>>>"+desc+"</b></font> </td>\n");
	document.write("<td nowrap align=right valign=bottom ></td>\n");
	document.write("<td nowrap align=left valign=middle width=20><img src=\"/images/bull.gif\" width=16 height=16></td>\n");
	document.write("</tr></table>\n");
}