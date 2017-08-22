<?php
include("../gin/base/Validator.class.php");
$validator = new Validator();
if ($validator->validateNumeric('a') == false) {
    echo 'ok';
}

if ($validator->validateLength(1, 5, "a") == false) {
    echo 'bad';
}