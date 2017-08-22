<?
class AdminControllerTest {

    public function test() {
        $adminController = new AdminController();
        $adminController->setUnitTesting();
        assertValue("admin index routing", "admin/index", $adminController->index());
    }

}