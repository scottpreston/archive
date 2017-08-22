<?php 
include("../phpheader.inc.php");
?>
<h1>Controller Test</h1>
<?php 
$dir = WEB_ROOT."/app/test/controllers/";
// Open a known directory, and proceed to read its contents
$controllers = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
           	if (ends_with($file, "class.php")) {
            	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($controllers, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
?>
<ul>
<li><a href="allcontrollers.php">All</a></li>
<?php
foreach ($controllers as $i=>$controller) {	
?>
<li><a href="controller.php?controller=<?=$controller?>"><?=$controller?></a>
<?php }?>
</ul>