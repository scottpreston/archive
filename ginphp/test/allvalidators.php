<?php
include("../phpheader.inc.php");
?>
<h1>Valdator Test</h1>
<?php
$dir = WEB_ROOT."/app/test/validators/";
// Open a known directory, and proceed to read its contents
$others = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
           	if (ends_with($file, "class.php")) {
           	    include_once($dir.$file);
            	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($others, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
?>

<?php
foreach ($others as $i=>$other) {
	echo "<h2>".$other."</h2>";
	$otest = new $other();
	$otest->test();
	$otest = NULL;
 }?>
