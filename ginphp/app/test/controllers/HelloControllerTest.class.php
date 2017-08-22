<?php
class HelloControllerTest {

    public function test() {
        $helloController = new HelloController();
        $helloController->setUnitTesting();
        assertValue("index routing", "hello", $helloController->index());
    }

}