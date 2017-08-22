<?php 
include("../phpheader.inc.php");
?>
<h1>All Controllers Test</h1>
<?php 
$dir = WEB_ROOT."/app/test/controllers/";
// Open a known directory, and proceed to read its contents
$controllers = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
        	if (ends_with($file, "class.php")) {
             include_once($dir.$file);
            	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($controllers, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
foreach ($controllers as $i=>$controller) {	
	echo "<h2>".$controller."</h2>";
	$ctest = new $controller();
	$ctest->integration_test();
}
?>