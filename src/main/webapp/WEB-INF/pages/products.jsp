<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products page</title>
</head>
<body>
	
	<input id="click" type="button" value="点击" onclick="doOnClick();"/>
<script type="text/javascript" async="async" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<!--  
<script>
    if(!window.jQuery)
    document.write('<script type="text/javascript" async="async" src="${pageContext.request.contextPath}static/js/2.1.1/jquery.min.js"><'+'/script>');
</script>
-->
<script type="text/javascript">
/*
jQuery(function() { 
    jQuery("#click").click(function() { 
        jQuery.ajax( { 
            type : "GET", 
            url : "getJSONList", 
            data : "name=iphone&img_src=static/imgs/june.png", 
            dataType: "text", 
            success : function(msg) { 
                alert(msg); 
            } 
        }); 
    }); 
}); 
*/
	function doOnClick(){
		jQuery.ajax( { 
	        type : "GET", 
	        url : "getJSONList", 
	        data : "name=iphone&img_src=static/imgs/june.png", 
	        dataType: "text", 
	        success : function(msg) { 
	            alert(msg); 
	        } 
	    }); 
	}
</script>	
</body>
</html>