<?php 
include("../phpheader.inc.php");
?>
<h1>All Models Test</h1>
<?php 
$dir = WEB_ROOT."/app/test/models/";
// Open a known directory, and proceed to read its contents
$models = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
        	if (ends_with($file, "class.php")) {
                include_once($dir.$file);
            	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($models, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
foreach ($models as $i=>$model) {	
	echo "<h2>".$model."</h2>";
	$mtest = new $model();
	$mtest->test();	
	$mtest = NULL;
}
?>