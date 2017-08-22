<?php
include_once('phpheader.inc.php');

$instance; // the class name of controller to invoke
$req_uri = getParameter("route");

$router = new Router();
$newuri = $router->replace($req_uri,$controllerMappings);
if ($newuri != "") $req_uri = $newuri;
$route = $router->parse($req_uri);

// assign to module/controller/method
$controller = $route->controller;
$subdir = $route->subdir;
$method = $route->method;
$arguments = $route->arguments;

// debugging the MVC pattern uncomment below
//echo "request_uri = ". $req_uri . "<br>";
//echo "Controller = ".$controller."<br>";
//echo "Method = ".$method."<br>";
//echo "Arguments = ";
//print_r($arguments);
//die();

if ($subdir == "") {
    $classFile = WEB_ROOT . '/app/controllers/' . $controller . EXT;
} else {
    $classFile = WEB_ROOT . '/app/controllers/' . $subdir . '/' . $controller . EXT;
}

if (file_exists($classFile)) {
    require_once ($classFile);
    if (class_exists($controller)) {
        $instance = new $controller();
    } else {
        die("class [$controller] does not exist.");
    }
} else {
    die('[' . $classFile . '] DOES NOT EXIST	');
}
if (method_exists($controller, $method)) {
    // this does all the work of calling the controller
    call_user_func_array(array($instance, $method), $arguments);
} else {
    if (method_exists($controller, "index")) {
        call_user_func_array(array($instance, "index"), array($method));
    } else {
        die('[' . $controller . '->' . $method . '] DOES NOT EXIST	');
    }
}