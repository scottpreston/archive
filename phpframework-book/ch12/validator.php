<?
public static function validateEmail($value) {
  $value = trim($value);
  $pass = preg_match('/^[\w-]+(?:\.[\w-]+)*@(?:[\w-]+\.)+[a-zA-Z]{2,7}$/', $value);
   return $pass;
}
