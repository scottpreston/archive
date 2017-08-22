<?php
class AdminController extends BaseController{

    function __construct() {
		parent :: __construct();
	}

	public function index() {
		return $this->render("admin/index",array());
	}

	public function sample($test = "") {
        return $this->render("admin/index",array());
	}

	public function test() {

	}

}