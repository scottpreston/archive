<?php
class DefaultControllerTest {

    public function test() {
        $defaultController = new DefaultController();
        $defaultController->setUnitTesting();
        assertValue("index routing", "index", $defaultController->index());

    }

}