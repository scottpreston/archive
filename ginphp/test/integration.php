<?php
$time_start = microtime();
include("../phpheader.inc.php");
$controller = getParameter("controller");
include_once(WEB_ROOT."/app/test/controllers/".$controller.".class.php");
$ctest = new $controller();
try {
    $ctest->integration_test();
} catch (Exception $w) {
    echo 'integration test does not exist';
}
$time_end = microtime();
$time = $time_start - $time_end;

echo "<hr/>Time to complete test: $time ";