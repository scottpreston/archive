<?php
class HelloController extends BaseController{

    function __construct() {
		parent :: __construct();
	}

	public function index() {
		$response['worked'] = "It Worked!!!";
		return $this->render("hello",$response);
	}

	public function add() {

	}

	public function edit($id = "") {

	}

	public function something($arg1, $arg2, $arg3) {

	}

	public function test() {

	}

}
