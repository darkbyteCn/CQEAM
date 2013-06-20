<%@ page contentType="application/x-download;charset=GBK" %>        
<%@ page import = "com.sino.base.web.request.download.WebFileDownload" %>
<%
//·ÀÖ¹IE»º´æ        
	response.setHeader("pragma","no-cache");        
	response.setHeader("cache-control","no-cache");        
	response.setDateHeader("Expires",0);       

	String fileName = request.getParameter("fileName");

    if (!com.sino.base.util.StrUtil.valPara(fileName)) {
        String filePath = request.getSession().getServletContext().getRealPath("/help/");
        filePath += "/" + fileName;
        WebFileDownload fileDown = new WebFileDownload(request, response);
        fileDown.setFilePath(filePath);
        fileDown.setFileName(fileName);
        fileDown.download();
    }
%>    
