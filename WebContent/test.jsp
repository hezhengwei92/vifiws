<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/23 0023
  Time: 下午 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传测试</title>
</head>
<body>
<form name="myform" id="myform" enctype="multipart/form-data"
      method="post" action="/vrsws/upload/avatar">
    编号：<input type="text" id="uid" name="uid"/><br/>
    文件：<input type="file" id="picture" name="picture"/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
