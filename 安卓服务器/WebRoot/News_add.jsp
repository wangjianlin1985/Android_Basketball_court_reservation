<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����������</TITLE> 
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
/*��֤��*/
function checkForm() {
    var newsType = document.getElementById("news.newsType").value;
    if(newsType=="") {
        alert('���������ŷ���!');
        return false;
    }
    var title = document.getElementById("news.title").value;
    if(title=="") {
        alert('���������ű���!');
        return false;
    }
    var content = document.getElementById("news.content").value;
    if(content=="") {
        alert('��������������!');
        return false;
    }
    var publishTime = document.getElementById("news.publishTime").value;
    if(publishTime=="") {
        alert('�����뷢��ʱ��!');
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
    <TD align="left" vAlign=top >
    <s:form action="News/News_AddNews.action" method="post" id="newsAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>���ŷ���:</td>
    <td width=70%>
    	<select id="news.newsType" name="news.newsType">
    		<option value="��������Ȥ��">��������Ȥ��</option>
    		<option value="��������Ȥ��">��������Ȥ��</option>
    	</select> 
  </tr>

  <tr>
    <td width=30%>���ű���:</td>
    <td width=70%><input id="news.title" name="news.title" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>������ͼ:</td>
    <td width=70%><input id="newsPhotoFile" name="newsPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="news.content" name="news.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="news.publishTime" name="news.publishTime" type="text" size="30" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
