<?php
class Validator {

    public static function validateAlpha($value) {
        $value = trim($value);
        $pass = preg_match('/^[a-zA-Z]+$/', $value);
        return $pass;
    }

    public static function validateNumeric($value) {
        $value = trim($value);
        $pass = preg_match('/^[0-9]+$/', $value);
        return $pass;
    }

    public static function validateMixed($value) {
        $value = trim($value);
        $pass = preg_match('/^[a-zA-Z0-9\s]+$/', $value);
        return $pass;
    }

    public static function validatePhone($value) {
        $value = trim($value);
        $pass = preg_match('/^[\d]{3}[-]{1}[\d]{3}[-]{1}[\d]{4}$/', $value);
        return $pass;
    }

    public static function validatePassword($value) {
        if (Validator::validateMixed($value)) {

            if (strlen($value) > 5 && strlen($value) < 12) {
                return true;
            }
        }
        return false;
    }

    public static function validateUsername($value) {

        if (Validator::validateMixed($value) && Validator::validateNumeric($value) == false) {
            if (strlen($value) > 5 && strlen($value) < 12) {
                return true;
            }
        }
        return false;
    }

    public static function validateEmail($value) {
        $value = trim($value);
        $pass = preg_match('/^[\w-]+(?:\.[\w-]+)*@(?:[\w-]+\.)+[a-zA-Z]{2,7}$/', $value);
        return $pass;
    }

    public static function validateDate($str) {
        $time = strtotime($str);
        return Validator::
        validateNumeric($time);
    }

    public static function validateLength($min, $max, $str) {
        return (strlen($str) >= $min && strlen($str) <= $max);
    }

}

?>
