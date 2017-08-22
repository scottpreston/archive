<?
$test = array();
try {
  if ($test[2]=="foo") {
    echo 'test';
  }
} catch (Exception $e) {
  die($e->getMessage());
}
