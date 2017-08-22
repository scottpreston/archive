<?php
class DefaultController extends BaseController {

    function __construct($auth=false) {
        parent :: __construct($auth);
    }

    public function index() {
        return $this->render("index", array());
    }

    public function foo() {
        return $this->render("foo", array());
    }

    public function test() {

    }

}