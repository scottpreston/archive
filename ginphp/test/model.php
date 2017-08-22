<?php 
include("../phpheader.inc.php");

$model = getParameter("model");
include_once(WEB_ROOT."/app/test/models/".$model.".class.php");
$mtest = new $model();
$mtest->test();	
$mtest = NULL;
?>
