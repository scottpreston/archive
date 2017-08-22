<?
$cam = $_GET['cam'];
$url = "http://$cam/IMAGE.JPG?" . date('U');
$image = file_get_contents($url);
header('Access-Control-Allow-Origin:*');
header('access-control-allow-credentials:true');
header('Content-type: image/jpeg');
echo $image;