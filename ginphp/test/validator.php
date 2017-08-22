<?php
include("../phpheader.inc.php");
$other = getParameter("other");
include_once(WEB_ROOT.'/app/test/validators/'.$other.'.class.php');
$otest = new $other();
$otest->test();
?>