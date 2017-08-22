<?
public function integration_test() {
  $contactController = new ContactController();
  $contactController->setUnitTesting();
  $test_msg = "this is a test message";

  assertValue("testing email return", "redirect:/contact/thanks",
  $contactController->submit("name test", "test123@test.com", $test_msg));

  $contactModel = new ContactModel();
  $contactModel->load_by_email("test123@test.com");
  assertValue("testing email insert", $test_msg, $contactModel->comments);
  $contactModel->delete();
}
