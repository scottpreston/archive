<?php 
include("../phpheader.inc.php");
?>
<h1>Models Test</h1>
<?php 
$dir = WEB_ROOT."/app/test/models/";
// Open a known directory, and proceed to read its contents
$models = array();
if (is_dir($dir)) {
    if ($dh = opendir($dir)) {
        while (($file = readdir($dh)) !== false) {
        	if (ends_with($file, "class.php")) {
                	//echo "filename: $file : filetype: " . filetype($dir . $file) . "<br>";
            	$cls = explode(".",$file);
            	array_push($models, $cls[0]);
        	}
        }
        closedir($dh);
    }
}
?>
<ul>
<li><a href="allmodels.php">All</a></li>
<?php 
foreach ($models as $i=>$model) {
?>
<li><a href="model.php?model=<?=$model?>"><?=$model?></a>
<?php }?>
</ul>