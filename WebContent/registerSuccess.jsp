<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册成功</title>
<script type="text/javascript">
		window.onload = function(){
			var time = 5;
			var second = document.getElementById("second");
			var timer = setInterval(function(){
				time--;
				second.innerHTML = time;
				if(time == 0){
					clearInterval(timer);
					location.href = "login.jsp";
				}
			},1000)
		}
</script>
</head>
<body>
	<h2>恭喜您注册成功，请赶快前往注册邮箱激活您的帐户</h2>
	<div><span id="second">5</span>秒后自动跳转到登录页面</div>
</body>
</html>