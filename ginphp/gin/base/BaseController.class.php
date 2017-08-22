<?php
abstract class BaseController {

     var $testing;
    var $auth = false;
    var $current_auth = false;

    const AUTHENTICATION_REQUIRED = true;
    const AUTHENTICATION_NOTREQUIRED = false;
    const RESTRICTED = "restricted";

    function __construct($auth = false) {
        $this->auth = $auth;
    }

    public function setAuthentication($current_auth = false) {
        $this->current_auth = $current_auth;
    }

    public function isAuthenticated() {
        return $this->current_auth;
    }

    public function setUnitTesting() {
        $this->testing = true;
    }

    public function render($view, $data = null) {
        // ensures pages that are authenticated can be tested.
        if ($this->auth === true) { // if auth required
            if ($this->current_auth === false) {
                return BaseController::RESTRICTED;
            }
        }

        // allows for unit testing return type vs rendering
        if ($this->testing) {
            return $view;
        }

        if (starts_with($view, "redirect:")) {
            $newview = explode(":", $view);
            redirect($newview[1]);
        }

        $full_file = WEB_ROOT . '/app/views/' . $view . '.php';
        if (file_exists($full_file)) {
            // extracts the associative array put in data to actual variables for the page/view
            if ($data != null) extract($data);
            include $full_file;
        } else {
            include WEB_ROOT . '/error/404.php';
        }
        return "";
    }

    public function renderJSON($data) {
        echo json_encode($data);
    }

}