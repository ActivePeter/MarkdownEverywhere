<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="./css/set.css">
<link rel="stylesheet" type="text/css" href="./css/demo.css">
<link rel="stylesheet" type="text/css" href="./css/demo(1).css">

<title>用户登录页面</title>
</head>
<body >
	<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
	</script>
	<script>
	function hide(){
		document.getElementById("wrong_tip").style.visibility="hidden";

	}
    function login() {
    	console.log('log tapped');
    	name= document.getElementById("input-id").value
    	pw= document.getElementById("input-pw").value
    	$.post("/faceRecogManageIOT/loginServlet",{
			name:name,
			pw:pw
		},
		function(data,status){
			//alert("数据: \n" + data + "\n状态: " + status);
			if(data=='right'){
				window.location.href='/faceRecogManageIOT/index.jsp';
			}else if(data=='wrong'){
				document.getElementById("wrong_tip").style.visibility="visible";
				setTimeout("hide()",5000);
			}
			//var jsonArray = JSON.parse(data);
			//console.log(jsonArray);
			//for(var i=0;i<jsonArray.length;i++)
			//{
				//console.log(jsonArray[0].name);
				//console.log(jsonArray[0].url);
			//}
		});
        //
        

    }
    </script>
    
    <div class="login_bg">
    	<div class="middle_div">
		    <span class="input input--nariko">
				<input class="input__field input__field--nariko" type="text" id="input-id">
				<label class="input__label input__label--nariko" for="input-20">
					<span class="input__label-content input__label-content--nariko">用户名</span>
				</label>
			</span>
			<span class="input input--nariko">
				<input class="input__field input__field--nariko" type="password" id="input-pw">
				<label class="input__label input__label--nariko" for="input-20">
					<span class="input__label-content input__label-content--nariko">密码</span>
				</label>
			</span>
			<div style="text-align:center">
				<button id="button5" onclick="login()">登入</button>
			</div>
			<div id="wrong_tip" style="text-align:center">
				密码或账号错误!
			</div>
			
	    </div>
    </div>
    
	
	
</body>
</html>