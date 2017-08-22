<?php
class HelloController extends BaseController {
  function __construct() {
    parent :: __construct();
  }
  public function index() {
    $response['worked'] = "It Worked!!!";
    return $this->render("hello",$response);
  }
}
