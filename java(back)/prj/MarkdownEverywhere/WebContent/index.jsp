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

<title>管理页面</title>
</head>
<body >
	<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
	</script>
	<script>
	curday="";
	curmonth="";
	curyear="";
	curtype="";
	$(document).ready(function(){  
		//getnian();
		resetshow();
	}); 
	function hide(){
		document.getElementById("wrong_tip").style.visibility="hidden";
	}
	function getnian(){
		$.post("/faceRecogManageIOT/dataServlet",{
			timetype:'year',
			type:curtype,
		},
		function(data,status){
			//alert("数据: \n" + data + "\n状态: " + status);
			var yearTags = '';
			var jsonArray = JSON.parse(data);
			//console.log(jsonArray);
			for(var i=0;i<jsonArray.length;i++)
			{
				yearTags = yearTags +'<option value='+jsonArray[i]+'>'+jsonArray[i]+'</option>';
				
				//console.log(jsonArray[0].name);
				//console.log(jsonArray[0].url);
			}
			// 为下拉框拼接子标签
			$('#year').append(yearTags);
		});
		
		
	}
	
	function getyue(){
		$.post("/faceRecogManageIOT/dataServlet",{
			timetype:'month',
			type:curtype,
			year:curyear,
		},
		function(data,status){
			//alert("数据: \n" + data + "\n状态: " + status);
			var monthTags = '';
			var jsonArray = JSON.parse(data);
			//console.log(jsonArray);
			for(var i=0;i<jsonArray.length;i++)
			{
				monthTags = monthTags +'<option value='+jsonArray[i]+'>'+jsonArray[i]+'</option>';
				
				//console.log(jsonArray[0].name);
				//console.log(jsonArray[0].url);
			}
			// 为下拉框拼接子标签
			$('#month').append(monthTags);
		});
		
	}
	function getri(){
		$.post("/faceRecogManageIOT/dataServlet",{
			timetype:'day',
			type:curtype,
			year:curyear,
			month:curmonth,
		},
		function(data,status){
			//alert("数据: \n" + data + "\n状态: " + status);
			var dayTags = '';
			var jsonArray = JSON.parse(data);
			//console.log(jsonArray);
			for(var i=0;i<jsonArray.length;i++)
			{
				dayTags = dayTags +'<option value='+jsonArray[i]+'>'+jsonArray[i]+'</option>';
				
				//console.log(jsonArray[0].name);
				//console.log(jsonArray[0].url);
			}
			// 为下拉框拼接子标签
			$('#day').append(dayTags);
		});
	}
	function getdata(){
		$.post("/faceRecogManageIOT/dataServlet",{
			timetype:'data',
			type:curtype,
			year:curyear,
			month:curmonth,
			day:curday,
		},
		function(data,status){
			//alert("数据: \n" + data + "\n状态: " + status);
			var dayTags = '';
			var jsonArray = JSON.parse(data);
			//console.log(jsonArray);
			for(var i=0;i<jsonArray.length;i++)
			{
				var arr = jsonArray[i].split("-");
				
				dayTags = dayTags +'<img class="pic" src=\\faceRecogManageIOT\\face\\record\\'+curtype+"\\20"+curyear+"\\"+curmonth+"\\"+curday+"\\"+jsonArray[i]+'>';
				if(curtype=="succ"){
					var name = arr[3].split(".")[0];
					dayTags = dayTags +'<div class="remark">'+arr[0]+"时"+arr[1]+"分"+arr[2]+"秒"+'  '+name+'</div>'
				}else{
					var sec = arr[2].split(".")[0];
					dayTags = dayTags +'<div class="remark">'+arr[0]+"时"+arr[1]+"分"+sec+"秒"+'</div>'
				}
				
				
				//console.log(jsonArray[0].name);
				//console.log(jsonArray[0].url);
			}
			// 为下拉框拼接子标签
			$('#pics').append(dayTags);
		});
	}
	function typeChanged(type){
		curtype=type;
		resetnian();
		resetyue();
		resetri();
		resetshow();
		$('#typehead').remove();
		getnian();
	}
	function yearChanged(year){
		curyear=year;
		resetyue();
		resetri();
		resetshow();
		$('#yearhead').remove();
		getyue();
	}
	function monthChanged(month){
		curmonth=month;
		resetri();
		resetshow()
		$('#monthhead').remove();
		getri();
	}
	function dayChanged(day){
		curday=day;
		$('#pics *').remove();
		$('#dayhead').remove();
		
		getdata();
	}
	function resetshow(){
		$('#pics *').remove();
		
		// 为下拉框拼接子标签
		$('#pics').append('<div class="remark">请选择类型及时间OWO</div>');
	}
	function resetnian(){
		var yearTags = '<option value="年"  id="yearhead">年</option>';
		// 将下拉框中除了第一个选项，全部移除
		$('#year option').remove();
		// 为下拉框拼接子标签
		$('#year').append(yearTags);
	}
	function resetyue(){
		var monthTags = '<option value="月"  id="monthhead">月</option>';
// 将下拉框中除了第一个选项，全部移除
		$('#month option').remove();
		// 为下拉框拼接子标签
		$('#month').append(monthTags);
	}
	function resetri(){
		var dayTags = '<option value="日" id="dayhead">日</option>'
// 将下拉框中除了第一个选项，全部移除
		
		$('#day option').remove();
		// 为下拉框拼接子标签
		$('#day').append(dayTags);
	}
    </script>
    <div id="top_bar">
    	<div>
    		
    		<div id="container">
				<div class="select typesel">
					<select id="type" onchange="typeChanged(this.options[this.options.selectedIndex].value)">
						<option id="typehead" value="typeselect">类型选择</option>
						<option value="succ">识别成功</option>
						<option value="fail">识别失败</option>
					</select>
				</div>
				<div class="select date">
					<select id="year" onchange="yearChanged(this.options[this.options.selectedIndex].value)">
					<option value="年" id="yearhead">年</option>
					</select>
					
				</div>
				<div class="select date">
					<select id="month" onchange="monthChanged(this.options[this.options.selectedIndex].value)">
					<option value="月"  id="monthhead">月</option>
					</select>
					
				</div>
				<div class="select date">
					<select id="day" onchange="dayChanged(this.options[this.options.selectedIndex].value)">
					<option value="日"  id="dayhead">日</option>
					</select>
				</div>
			</div>
    		
    	</div>
	    
	</div>
    <div  align="center" class="manager_bg" id="pics">
	    
	    
    
	</div>
	
</body>
</html>