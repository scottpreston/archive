<?php
include_once ("../../../phpheader.inc.php");

class BaseControllerTest {

    public function test() {
        // using default since can't instanciate abstract
        $defaultController = new DefaultController(BaseController::AUTHENTICATION_REQUIRED);
        $defaultController->setUnitTesting();
        assertValue("security restricted", BaseController::RESTRICTED, $defaultController->render("foo",array()));
        $defaultController->setAuthentication(true);
        assertValue("security restricted", "foo", $defaultController->render("foo",array()));
        $defaultController = new DefaultController(BaseController::AUTHENTICATION_NOTREQUIRED);
        $defaultController->setUnitTesting();
        assertValue("security not restricted", "foo", $defaultController->render("foo",array()));
    }
}

$controllertest = new BaseControllerTest();
$controllertest->test();