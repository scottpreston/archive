<?php
class Router {

    var $routes = array();

    function __construct($auth = false) {
        $this->auth = $auth;
    }

    function replace($req_uri, $routeArray) {
        return $routeArray[$req_uri];
    }

    function parse($uri) {

        if (ends_with($uri, '/')) $uri = substr($uri, 0, -1); // gets rid of trailing slash
        if ($uri == "") {
            return new Route("DefaultController", "index", array());
        }

        $segments = explode('/', $uri);
        $subdir = "";
        $class = "";
        $method = "";

        $controller = ucfirst($segments[0] . 'Controller');

        $classFile = WEB_ROOT . '/app/controllers/' . $controller . EXT;

        // check to see if in default
        if ($segments[0] != "" && file_exists($classFile)) {
            $class = $segments[0];
            $segments = array_slice($segments, 1);
        } else {
            $class = "default";
        }

        $controller = ucfirst($class . 'Controller');
        // since greater than 1
        if (count($segments) > 0) {
            $method = $segments[0];
            if (method_exists($controller,$method)==false) {
                $method = "index";
            } else {
                $segments = array_slice($segments, 1);
            }
        } else {
            // will never be arguments
            $method = "index";
            $segments = array();
        }

        return new Route($controller, $method, $segments);
    }

}

class Route {
    var $subdir;
    var $controller;
    var $method;
    var $arguments;

    function __construct($controller, $method, $arguments) {
        $this->controller = $controller;
        $this->method = $method;
        $this->arguments = $arguments;
    }
}
