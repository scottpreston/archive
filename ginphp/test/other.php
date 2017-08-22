<?php
include("../phpheader.inc.php");
$other = getParameter("other");
include_once(WEB_ROOT.'/app/test/other/'.$other.'.class.php');
$otest = new $other();
$otest->test();
?>