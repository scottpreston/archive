<?php
include_once ("../../../phpheader.inc.php");

class BaseRouterTest {

    public function test() {
        // using default since can't instanciate abstract
        $router = new Router();
        $route1 = $router->parse("");
        assertValue("1 - assert controller", "DefaultController", $route1->controller);
        assertValue("1 - assert method", "index", $route1->method);
        assertValue("1 - assert arguments", 0, count($route1->arguments));

        $route2 = $router->parse("");
        assertValue("2 - assert controller", "DefaultController", $route2->controller);
        assertValue("2 - assert method", "index", $route2->method);
        assertValue("2 - assert arguments", 0, count($route2->arguments));

        $route2b = $router->parse("foo");
        assertValue("2b - assert controller", "DefaultController", $route2b->controller);
        assertValue("2b - assert method", "foo", $route2b->method);
        assertValue("2b - assert arguments", 0, count($route2b->arguments));

        $route3 = $router->parse("hello");
        assertValue("3 - assert controller", "HelloController", $route3->controller);
        assertValue("3 - assert method", "index", $route3->method);
        assertValue("3 - assert arguments", 0, count($route3->arguments));

        $route4 = $router->parse("hello/add");
        assertValue("4 - assert controller", "HelloController", $route4->controller);
        assertValue("4 - assert method", "add", $route4->method);
        assertValue("4 - assert arguments", 0, count($route4->arguments));

        $route5 = $router->parse("hello/edit/1");
        assertValue("5 - assert controller", "HelloController", $route5->controller);
        assertValue("5 - assert method", "edit", $route5->method);
        assertValue("5 - assert arguments", 1, count($route5->arguments));

        $route6 = $router->parse("hello/something/1/2/3");
        assertValue("6 - assert controller", "HelloController", $route6->controller);
        assertValue("6 - assert method", "something", $route6->method);
        assertValue("6 - assert arguments", 3, count($route6->arguments));

        $route7 = $router->parse("admin");
        assertValue("7 - assert controller", "AdminController", $route7->controller);
        assertValue("7 - assert method", "index", $route7->method);
        assertValue("7 - assert arguments", 0, count($route7->arguments));

        $route7b = $router->parse("admin/1");
        assertValue("7b - assert controller", "AdminController", $route7b->controller);
        assertValue("7b - assert method", "index", $route7b->method);
        assertValue("7b - assert arguments", 1, count($route7b->arguments));

        $route7c = $router->parse("admin/sample/1");
        assertValue("7c - assert controller", "AdminController", $route7c->controller);
        assertValue("7c - assert method", "sample", $route7c->method);
        assertValue("7c - assert arguments", 1, count($route7c->arguments));

        $routeArray = array();
        $routeArray['newurl/something'] = 'somecontroller/method';
        $routeArray['newurl/something2'] = 'hello/edit/1';
        assertValue("testing router replacement 1", "somecontroller/method", $router->replace('newurl/something', $routeArray));
        assertValue("testing router replacement 2", "", $router->replace('/not-real', $routeArray));

        $route8 = $router->parse($router->replace('newurl/something2', $routeArray));
        assertValue("8 - assert controller", "HelloController", $route8->controller);
        assertValue("8 - assert method", "edit", $route8->method);
        assertValue("8 - assert arguments", 1, count($route8->arguments));

    }
}

$routertest = new BaseRouterTest();
$routertest->test();