<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册失败</title>
<script type="text/javascript">
		window.onload = function(){
			var time = 5;
			var second = document.getElementById("second");
			var timer = setInterval(function(){
				time--;
				second.innerHTML = time;
				if(time == 0){
					clearInterval(timer);
					location.href = "register.jsp";
				}
			},1000)
		}
</script>
</head>
<body>
	<h2>注册失败,点击<a style="color:red" href="register.jsp">这里</a>返回注册页面</h2>
	<span id="second">5</span>秒后自动跳转。。。
</body>
</html>