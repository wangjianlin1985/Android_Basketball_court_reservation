<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.News" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    News news = (News)request.getAttribute("news");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改篮球新闻</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var newsType = document.getElementById("news.newsType").value;
    if(newsType=="") {
        alert('请输入新闻分类!');
        return false;
    }
    var title = document.getElementById("news.title").value;
    if(title=="") {
        alert('请输入新闻标题!');
        return false;
    }
    var content = document.getElementById("news.content").value;
    if(content=="") {
        alert('请输入新闻内容!');
        return false;
    }
    var publishTime = document.getElementById("news.publishTime").value;
    if(publishTime=="") {
        alert('请输入发布时间!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="News/News_ModifyNews.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>新闻id:</td>
    <td width=70%><input id="news.newsId" name="news.newsId" type="text" value="<%=news.getNewsId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>新闻分类:</td>
    <td width=70%><input id="news.newsType" name="news.newsType" type="text" size="20" value='<%=news.getNewsType() %>'/></td>
  </tr>

  <tr>
    <td width=30%>新闻标题:</td>
    <td width=70%><input id="news.title" name="news.title" type="text" size="80" value='<%=news.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>新闻主图:</td>
    <td width=70%><img src="<%=basePath %><%=news.getNewsPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="news.newsPhoto" value="<%=news.getNewsPhoto() %>" />
    <input id="newsPhotoFile" name="newsPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>新闻内容:</td>
    <td width=70%><textarea id="news.content" name="news.content" rows=5 cols=50><%=news.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>发布时间:</td>
    <td width=70%><input id="news.publishTime" name="news.publishTime" type="text" size="30" value='<%=news.getPublishTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
