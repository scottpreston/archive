<?php
include("../phpheader.inc.php");
?>
<h1>Valdator Test</h1>
<?php
$dir = WEB_ROOT."/app/test/other/";
// Open a known directory, and proceed to read its contents
$others = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
           	if (ends_with($file, "class.php")) {
            	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($others, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
?>
<ul>
<?php
foreach ($others as $i=>$other) {
?>
<li><a href="other.php?other=<?=$other?>"><?=$other?></a>
<?php }?>
</ul>