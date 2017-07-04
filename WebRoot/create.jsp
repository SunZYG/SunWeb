<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
<!--      <script type="text/javascript" src="js/jquery-1.6.min.js" ></script>  -->
     <script type="text/javascript" src="js/plupload.full.min.js"></script>
</head>
<body>
		<div>
        <button id="browse">x</button>
        <button id="start_upload">u</button>
        </div>
    <script>
    //实例化一个plupload上传对象
    var uploader = new plupload.Uploader({
    	runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
    	chunk_size : '1mb', 
    	dragdrop: true,
        browse_button : 'browse', //触发文件选择对话框的按钮，为那个元素id
        url : 'upload1', //服务器端的上传页面地址
        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : 'js/Moxie.xap' //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
    });    
    
    //在实例对象上调用init()方法进行初始化
    uploader.init();
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_upload').onclick = function(){
        uploader.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
    </script>
</body>
</html>