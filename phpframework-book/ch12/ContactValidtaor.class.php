<?
class ContactValidator {

    public static function validate_form($email, $name, $comments) {
        $form_errors = array();
        if (!Validator::validateEmail($email)) {
            array_push($form_errors, "Email is not valid.");
        }
        if (!Validator::validateLength(2,50,$name)) {
            array_push($form_errors, "Name is not valid.");
        }
       if (!Validator::validateLength(2,2000,$comments)) {
            array_push($form_errors, "Comments are not valid.");
        }
        //print_r($form_errors);
        return $form_errors;
    }
}
