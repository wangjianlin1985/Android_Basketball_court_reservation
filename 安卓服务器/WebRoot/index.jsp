<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>D453基于Android的篮球教学与场地预订APP设计与开发-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户</a></li> 
			<li><a href="<%=basePath %>Video/Video_FrontQueryVideo.action" target="OfficeMain">篮球教学</a></li> 
			<li><a href="<%=basePath %>VideoType/VideoType_FrontQueryVideoType.action" target="OfficeMain">视频类型</a></li> 
			<li><a href="<%=basePath %>Place/Place_FrontQueryPlace.action" target="OfficeMain">篮球场地</a></li> 
			<li><a href="<%=basePath %>PlaceOrder/PlaceOrder_FrontQueryPlaceOrder.action" target="OfficeMain">场地预订</a></li> 
			<li><a href="<%=basePath %>TimeSection/TimeSection_FrontQueryTimeSection.action" target="OfficeMain">时段信息</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">篮球新闻</a></li> 
			<li><a href="<%=basePath %>Leaveword/Leaveword_FrontQueryLeaveword.action" target="OfficeMain">约战留言</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
